package br.mp.mpro.caopapp.web.rest;

import br.mp.mpro.caopapp.CaopApp;

import br.mp.mpro.caopapp.domain.Periodicidade;
import br.mp.mpro.caopapp.repository.PeriodicidadeRepository;
import br.mp.mpro.caopapp.service.PeriodicidadeService;
import br.mp.mpro.caopapp.service.dto.PeriodicidadeDTO;
import br.mp.mpro.caopapp.service.mapper.PeriodicidadeMapper;
import br.mp.mpro.caopapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static br.mp.mpro.caopapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PeriodicidadeResource REST controller.
 *
 * @see PeriodicidadeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaopApp.class)
public class PeriodicidadeResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private PeriodicidadeRepository periodicidadeRepository;

    @Autowired
    private PeriodicidadeMapper periodicidadeMapper;

    @Autowired
    private PeriodicidadeService periodicidadeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPeriodicidadeMockMvc;

    private Periodicidade periodicidade;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PeriodicidadeResource periodicidadeResource = new PeriodicidadeResource(periodicidadeService);
        this.restPeriodicidadeMockMvc = MockMvcBuilders.standaloneSetup(periodicidadeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Periodicidade createEntity(EntityManager em) {
        Periodicidade periodicidade = new Periodicidade()
            .descricao(DEFAULT_DESCRICAO);
        return periodicidade;
    }

    @Before
    public void initTest() {
        periodicidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeriodicidade() throws Exception {
        int databaseSizeBeforeCreate = periodicidadeRepository.findAll().size();

        // Create the Periodicidade
        PeriodicidadeDTO periodicidadeDTO = periodicidadeMapper.toDto(periodicidade);
        restPeriodicidadeMockMvc.perform(post("/api/periodicidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodicidadeDTO)))
            .andExpect(status().isCreated());

        // Validate the Periodicidade in the database
        List<Periodicidade> periodicidadeList = periodicidadeRepository.findAll();
        assertThat(periodicidadeList).hasSize(databaseSizeBeforeCreate + 1);
        Periodicidade testPeriodicidade = periodicidadeList.get(periodicidadeList.size() - 1);
        assertThat(testPeriodicidade.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createPeriodicidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = periodicidadeRepository.findAll().size();

        // Create the Periodicidade with an existing ID
        periodicidade.setId(1L);
        PeriodicidadeDTO periodicidadeDTO = periodicidadeMapper.toDto(periodicidade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodicidadeMockMvc.perform(post("/api/periodicidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodicidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Periodicidade in the database
        List<Periodicidade> periodicidadeList = periodicidadeRepository.findAll();
        assertThat(periodicidadeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodicidadeRepository.findAll().size();
        // set the field null
        periodicidade.setDescricao(null);

        // Create the Periodicidade, which fails.
        PeriodicidadeDTO periodicidadeDTO = periodicidadeMapper.toDto(periodicidade);

        restPeriodicidadeMockMvc.perform(post("/api/periodicidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodicidadeDTO)))
            .andExpect(status().isBadRequest());

        List<Periodicidade> periodicidadeList = periodicidadeRepository.findAll();
        assertThat(periodicidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPeriodicidades() throws Exception {
        // Initialize the database
        periodicidadeRepository.saveAndFlush(periodicidade);

        // Get all the periodicidadeList
        restPeriodicidadeMockMvc.perform(get("/api/periodicidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodicidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    public void getPeriodicidade() throws Exception {
        // Initialize the database
        periodicidadeRepository.saveAndFlush(periodicidade);

        // Get the periodicidade
        restPeriodicidadeMockMvc.perform(get("/api/periodicidades/{id}", periodicidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(periodicidade.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPeriodicidade() throws Exception {
        // Get the periodicidade
        restPeriodicidadeMockMvc.perform(get("/api/periodicidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeriodicidade() throws Exception {
        // Initialize the database
        periodicidadeRepository.saveAndFlush(periodicidade);
        int databaseSizeBeforeUpdate = periodicidadeRepository.findAll().size();

        // Update the periodicidade
        Periodicidade updatedPeriodicidade = periodicidadeRepository.findOne(periodicidade.getId());
        // Disconnect from session so that the updates on updatedPeriodicidade are not directly saved in db
        em.detach(updatedPeriodicidade);
        updatedPeriodicidade
            .descricao(UPDATED_DESCRICAO);
        PeriodicidadeDTO periodicidadeDTO = periodicidadeMapper.toDto(updatedPeriodicidade);

        restPeriodicidadeMockMvc.perform(put("/api/periodicidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodicidadeDTO)))
            .andExpect(status().isOk());

        // Validate the Periodicidade in the database
        List<Periodicidade> periodicidadeList = periodicidadeRepository.findAll();
        assertThat(periodicidadeList).hasSize(databaseSizeBeforeUpdate);
        Periodicidade testPeriodicidade = periodicidadeList.get(periodicidadeList.size() - 1);
        assertThat(testPeriodicidade.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingPeriodicidade() throws Exception {
        int databaseSizeBeforeUpdate = periodicidadeRepository.findAll().size();

        // Create the Periodicidade
        PeriodicidadeDTO periodicidadeDTO = periodicidadeMapper.toDto(periodicidade);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPeriodicidadeMockMvc.perform(put("/api/periodicidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodicidadeDTO)))
            .andExpect(status().isCreated());

        // Validate the Periodicidade in the database
        List<Periodicidade> periodicidadeList = periodicidadeRepository.findAll();
        assertThat(periodicidadeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePeriodicidade() throws Exception {
        // Initialize the database
        periodicidadeRepository.saveAndFlush(periodicidade);
        int databaseSizeBeforeDelete = periodicidadeRepository.findAll().size();

        // Get the periodicidade
        restPeriodicidadeMockMvc.perform(delete("/api/periodicidades/{id}", periodicidade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Periodicidade> periodicidadeList = periodicidadeRepository.findAll();
        assertThat(periodicidadeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Periodicidade.class);
        Periodicidade periodicidade1 = new Periodicidade();
        periodicidade1.setId(1L);
        Periodicidade periodicidade2 = new Periodicidade();
        periodicidade2.setId(periodicidade1.getId());
        assertThat(periodicidade1).isEqualTo(periodicidade2);
        periodicidade2.setId(2L);
        assertThat(periodicidade1).isNotEqualTo(periodicidade2);
        periodicidade1.setId(null);
        assertThat(periodicidade1).isNotEqualTo(periodicidade2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodicidadeDTO.class);
        PeriodicidadeDTO periodicidadeDTO1 = new PeriodicidadeDTO();
        periodicidadeDTO1.setId(1L);
        PeriodicidadeDTO periodicidadeDTO2 = new PeriodicidadeDTO();
        assertThat(periodicidadeDTO1).isNotEqualTo(periodicidadeDTO2);
        periodicidadeDTO2.setId(periodicidadeDTO1.getId());
        assertThat(periodicidadeDTO1).isEqualTo(periodicidadeDTO2);
        periodicidadeDTO2.setId(2L);
        assertThat(periodicidadeDTO1).isNotEqualTo(periodicidadeDTO2);
        periodicidadeDTO1.setId(null);
        assertThat(periodicidadeDTO1).isNotEqualTo(periodicidadeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(periodicidadeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(periodicidadeMapper.fromId(null)).isNull();
    }
}
