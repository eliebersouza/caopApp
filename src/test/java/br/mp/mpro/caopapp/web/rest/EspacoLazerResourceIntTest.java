package br.mp.mpro.caopapp.web.rest;

import br.mp.mpro.caopapp.CaopApp;

import br.mp.mpro.caopapp.domain.EspacoLazer;
import br.mp.mpro.caopapp.repository.EspacoLazerRepository;
import br.mp.mpro.caopapp.service.EspacoLazerService;
import br.mp.mpro.caopapp.service.dto.EspacoLazerDTO;
import br.mp.mpro.caopapp.service.mapper.EspacoLazerMapper;
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
 * Test class for the EspacoLazerResource REST controller.
 *
 * @see EspacoLazerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaopApp.class)
public class EspacoLazerResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private EspacoLazerRepository espacoLazerRepository;

    @Autowired
    private EspacoLazerMapper espacoLazerMapper;

    @Autowired
    private EspacoLazerService espacoLazerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEspacoLazerMockMvc;

    private EspacoLazer espacoLazer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EspacoLazerResource espacoLazerResource = new EspacoLazerResource(espacoLazerService);
        this.restEspacoLazerMockMvc = MockMvcBuilders.standaloneSetup(espacoLazerResource)
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
    public static EspacoLazer createEntity(EntityManager em) {
        EspacoLazer espacoLazer = new EspacoLazer()
            .descricao(DEFAULT_DESCRICAO);
        return espacoLazer;
    }

    @Before
    public void initTest() {
        espacoLazer = createEntity(em);
    }

    @Test
    @Transactional
    public void createEspacoLazer() throws Exception {
        int databaseSizeBeforeCreate = espacoLazerRepository.findAll().size();

        // Create the EspacoLazer
        EspacoLazerDTO espacoLazerDTO = espacoLazerMapper.toDto(espacoLazer);
        restEspacoLazerMockMvc.perform(post("/api/espaco-lazers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(espacoLazerDTO)))
            .andExpect(status().isCreated());

        // Validate the EspacoLazer in the database
        List<EspacoLazer> espacoLazerList = espacoLazerRepository.findAll();
        assertThat(espacoLazerList).hasSize(databaseSizeBeforeCreate + 1);
        EspacoLazer testEspacoLazer = espacoLazerList.get(espacoLazerList.size() - 1);
        assertThat(testEspacoLazer.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createEspacoLazerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = espacoLazerRepository.findAll().size();

        // Create the EspacoLazer with an existing ID
        espacoLazer.setId(1L);
        EspacoLazerDTO espacoLazerDTO = espacoLazerMapper.toDto(espacoLazer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEspacoLazerMockMvc.perform(post("/api/espaco-lazers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(espacoLazerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EspacoLazer in the database
        List<EspacoLazer> espacoLazerList = espacoLazerRepository.findAll();
        assertThat(espacoLazerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = espacoLazerRepository.findAll().size();
        // set the field null
        espacoLazer.setDescricao(null);

        // Create the EspacoLazer, which fails.
        EspacoLazerDTO espacoLazerDTO = espacoLazerMapper.toDto(espacoLazer);

        restEspacoLazerMockMvc.perform(post("/api/espaco-lazers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(espacoLazerDTO)))
            .andExpect(status().isBadRequest());

        List<EspacoLazer> espacoLazerList = espacoLazerRepository.findAll();
        assertThat(espacoLazerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEspacoLazers() throws Exception {
        // Initialize the database
        espacoLazerRepository.saveAndFlush(espacoLazer);

        // Get all the espacoLazerList
        restEspacoLazerMockMvc.perform(get("/api/espaco-lazers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(espacoLazer.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    public void getEspacoLazer() throws Exception {
        // Initialize the database
        espacoLazerRepository.saveAndFlush(espacoLazer);

        // Get the espacoLazer
        restEspacoLazerMockMvc.perform(get("/api/espaco-lazers/{id}", espacoLazer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(espacoLazer.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEspacoLazer() throws Exception {
        // Get the espacoLazer
        restEspacoLazerMockMvc.perform(get("/api/espaco-lazers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEspacoLazer() throws Exception {
        // Initialize the database
        espacoLazerRepository.saveAndFlush(espacoLazer);
        int databaseSizeBeforeUpdate = espacoLazerRepository.findAll().size();

        // Update the espacoLazer
        EspacoLazer updatedEspacoLazer = espacoLazerRepository.findOne(espacoLazer.getId());
        // Disconnect from session so that the updates on updatedEspacoLazer are not directly saved in db
        em.detach(updatedEspacoLazer);
        updatedEspacoLazer
            .descricao(UPDATED_DESCRICAO);
        EspacoLazerDTO espacoLazerDTO = espacoLazerMapper.toDto(updatedEspacoLazer);

        restEspacoLazerMockMvc.perform(put("/api/espaco-lazers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(espacoLazerDTO)))
            .andExpect(status().isOk());

        // Validate the EspacoLazer in the database
        List<EspacoLazer> espacoLazerList = espacoLazerRepository.findAll();
        assertThat(espacoLazerList).hasSize(databaseSizeBeforeUpdate);
        EspacoLazer testEspacoLazer = espacoLazerList.get(espacoLazerList.size() - 1);
        assertThat(testEspacoLazer.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingEspacoLazer() throws Exception {
        int databaseSizeBeforeUpdate = espacoLazerRepository.findAll().size();

        // Create the EspacoLazer
        EspacoLazerDTO espacoLazerDTO = espacoLazerMapper.toDto(espacoLazer);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEspacoLazerMockMvc.perform(put("/api/espaco-lazers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(espacoLazerDTO)))
            .andExpect(status().isCreated());

        // Validate the EspacoLazer in the database
        List<EspacoLazer> espacoLazerList = espacoLazerRepository.findAll();
        assertThat(espacoLazerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEspacoLazer() throws Exception {
        // Initialize the database
        espacoLazerRepository.saveAndFlush(espacoLazer);
        int databaseSizeBeforeDelete = espacoLazerRepository.findAll().size();

        // Get the espacoLazer
        restEspacoLazerMockMvc.perform(delete("/api/espaco-lazers/{id}", espacoLazer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EspacoLazer> espacoLazerList = espacoLazerRepository.findAll();
        assertThat(espacoLazerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EspacoLazer.class);
        EspacoLazer espacoLazer1 = new EspacoLazer();
        espacoLazer1.setId(1L);
        EspacoLazer espacoLazer2 = new EspacoLazer();
        espacoLazer2.setId(espacoLazer1.getId());
        assertThat(espacoLazer1).isEqualTo(espacoLazer2);
        espacoLazer2.setId(2L);
        assertThat(espacoLazer1).isNotEqualTo(espacoLazer2);
        espacoLazer1.setId(null);
        assertThat(espacoLazer1).isNotEqualTo(espacoLazer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EspacoLazerDTO.class);
        EspacoLazerDTO espacoLazerDTO1 = new EspacoLazerDTO();
        espacoLazerDTO1.setId(1L);
        EspacoLazerDTO espacoLazerDTO2 = new EspacoLazerDTO();
        assertThat(espacoLazerDTO1).isNotEqualTo(espacoLazerDTO2);
        espacoLazerDTO2.setId(espacoLazerDTO1.getId());
        assertThat(espacoLazerDTO1).isEqualTo(espacoLazerDTO2);
        espacoLazerDTO2.setId(2L);
        assertThat(espacoLazerDTO1).isNotEqualTo(espacoLazerDTO2);
        espacoLazerDTO1.setId(null);
        assertThat(espacoLazerDTO1).isNotEqualTo(espacoLazerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(espacoLazerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(espacoLazerMapper.fromId(null)).isNull();
    }
}
