package br.mp.mpro.caopapp.web.rest;

import br.mp.mpro.caopapp.CaopApp;

import br.mp.mpro.caopapp.domain.RendaFamiliar;
import br.mp.mpro.caopapp.repository.RendaFamiliarRepository;
import br.mp.mpro.caopapp.service.RendaFamiliarService;
import br.mp.mpro.caopapp.service.dto.RendaFamiliarDTO;
import br.mp.mpro.caopapp.service.mapper.RendaFamiliarMapper;
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
 * Test class for the RendaFamiliarResource REST controller.
 *
 * @see RendaFamiliarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaopApp.class)
public class RendaFamiliarResourceIntTest {

    private static final String DEFAULT_FAIXA_RENDA = "AAAAAAAAAA";
    private static final String UPDATED_FAIXA_RENDA = "BBBBBBBBBB";

    @Autowired
    private RendaFamiliarRepository rendaFamiliarRepository;

    @Autowired
    private RendaFamiliarMapper rendaFamiliarMapper;

    @Autowired
    private RendaFamiliarService rendaFamiliarService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRendaFamiliarMockMvc;

    private RendaFamiliar rendaFamiliar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RendaFamiliarResource rendaFamiliarResource = new RendaFamiliarResource(rendaFamiliarService);
        this.restRendaFamiliarMockMvc = MockMvcBuilders.standaloneSetup(rendaFamiliarResource)
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
    public static RendaFamiliar createEntity(EntityManager em) {
        RendaFamiliar rendaFamiliar = new RendaFamiliar()
            .faixaRenda(DEFAULT_FAIXA_RENDA);
        return rendaFamiliar;
    }

    @Before
    public void initTest() {
        rendaFamiliar = createEntity(em);
    }

    @Test
    @Transactional
    public void createRendaFamiliar() throws Exception {
        int databaseSizeBeforeCreate = rendaFamiliarRepository.findAll().size();

        // Create the RendaFamiliar
        RendaFamiliarDTO rendaFamiliarDTO = rendaFamiliarMapper.toDto(rendaFamiliar);
        restRendaFamiliarMockMvc.perform(post("/api/renda-familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rendaFamiliarDTO)))
            .andExpect(status().isCreated());

        // Validate the RendaFamiliar in the database
        List<RendaFamiliar> rendaFamiliarList = rendaFamiliarRepository.findAll();
        assertThat(rendaFamiliarList).hasSize(databaseSizeBeforeCreate + 1);
        RendaFamiliar testRendaFamiliar = rendaFamiliarList.get(rendaFamiliarList.size() - 1);
        assertThat(testRendaFamiliar.getFaixaRenda()).isEqualTo(DEFAULT_FAIXA_RENDA);
    }

    @Test
    @Transactional
    public void createRendaFamiliarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rendaFamiliarRepository.findAll().size();

        // Create the RendaFamiliar with an existing ID
        rendaFamiliar.setId(1L);
        RendaFamiliarDTO rendaFamiliarDTO = rendaFamiliarMapper.toDto(rendaFamiliar);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRendaFamiliarMockMvc.perform(post("/api/renda-familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rendaFamiliarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RendaFamiliar in the database
        List<RendaFamiliar> rendaFamiliarList = rendaFamiliarRepository.findAll();
        assertThat(rendaFamiliarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFaixaRendaIsRequired() throws Exception {
        int databaseSizeBeforeTest = rendaFamiliarRepository.findAll().size();
        // set the field null
        rendaFamiliar.setFaixaRenda(null);

        // Create the RendaFamiliar, which fails.
        RendaFamiliarDTO rendaFamiliarDTO = rendaFamiliarMapper.toDto(rendaFamiliar);

        restRendaFamiliarMockMvc.perform(post("/api/renda-familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rendaFamiliarDTO)))
            .andExpect(status().isBadRequest());

        List<RendaFamiliar> rendaFamiliarList = rendaFamiliarRepository.findAll();
        assertThat(rendaFamiliarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRendaFamiliars() throws Exception {
        // Initialize the database
        rendaFamiliarRepository.saveAndFlush(rendaFamiliar);

        // Get all the rendaFamiliarList
        restRendaFamiliarMockMvc.perform(get("/api/renda-familiars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rendaFamiliar.getId().intValue())))
            .andExpect(jsonPath("$.[*].faixaRenda").value(hasItem(DEFAULT_FAIXA_RENDA.toString())));
    }

    @Test
    @Transactional
    public void getRendaFamiliar() throws Exception {
        // Initialize the database
        rendaFamiliarRepository.saveAndFlush(rendaFamiliar);

        // Get the rendaFamiliar
        restRendaFamiliarMockMvc.perform(get("/api/renda-familiars/{id}", rendaFamiliar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rendaFamiliar.getId().intValue()))
            .andExpect(jsonPath("$.faixaRenda").value(DEFAULT_FAIXA_RENDA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRendaFamiliar() throws Exception {
        // Get the rendaFamiliar
        restRendaFamiliarMockMvc.perform(get("/api/renda-familiars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRendaFamiliar() throws Exception {
        // Initialize the database
        rendaFamiliarRepository.saveAndFlush(rendaFamiliar);
        int databaseSizeBeforeUpdate = rendaFamiliarRepository.findAll().size();

        // Update the rendaFamiliar
        RendaFamiliar updatedRendaFamiliar = rendaFamiliarRepository.findOne(rendaFamiliar.getId());
        // Disconnect from session so that the updates on updatedRendaFamiliar are not directly saved in db
        em.detach(updatedRendaFamiliar);
        updatedRendaFamiliar
            .faixaRenda(UPDATED_FAIXA_RENDA);
        RendaFamiliarDTO rendaFamiliarDTO = rendaFamiliarMapper.toDto(updatedRendaFamiliar);

        restRendaFamiliarMockMvc.perform(put("/api/renda-familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rendaFamiliarDTO)))
            .andExpect(status().isOk());

        // Validate the RendaFamiliar in the database
        List<RendaFamiliar> rendaFamiliarList = rendaFamiliarRepository.findAll();
        assertThat(rendaFamiliarList).hasSize(databaseSizeBeforeUpdate);
        RendaFamiliar testRendaFamiliar = rendaFamiliarList.get(rendaFamiliarList.size() - 1);
        assertThat(testRendaFamiliar.getFaixaRenda()).isEqualTo(UPDATED_FAIXA_RENDA);
    }

    @Test
    @Transactional
    public void updateNonExistingRendaFamiliar() throws Exception {
        int databaseSizeBeforeUpdate = rendaFamiliarRepository.findAll().size();

        // Create the RendaFamiliar
        RendaFamiliarDTO rendaFamiliarDTO = rendaFamiliarMapper.toDto(rendaFamiliar);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRendaFamiliarMockMvc.perform(put("/api/renda-familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rendaFamiliarDTO)))
            .andExpect(status().isCreated());

        // Validate the RendaFamiliar in the database
        List<RendaFamiliar> rendaFamiliarList = rendaFamiliarRepository.findAll();
        assertThat(rendaFamiliarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRendaFamiliar() throws Exception {
        // Initialize the database
        rendaFamiliarRepository.saveAndFlush(rendaFamiliar);
        int databaseSizeBeforeDelete = rendaFamiliarRepository.findAll().size();

        // Get the rendaFamiliar
        restRendaFamiliarMockMvc.perform(delete("/api/renda-familiars/{id}", rendaFamiliar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RendaFamiliar> rendaFamiliarList = rendaFamiliarRepository.findAll();
        assertThat(rendaFamiliarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RendaFamiliar.class);
        RendaFamiliar rendaFamiliar1 = new RendaFamiliar();
        rendaFamiliar1.setId(1L);
        RendaFamiliar rendaFamiliar2 = new RendaFamiliar();
        rendaFamiliar2.setId(rendaFamiliar1.getId());
        assertThat(rendaFamiliar1).isEqualTo(rendaFamiliar2);
        rendaFamiliar2.setId(2L);
        assertThat(rendaFamiliar1).isNotEqualTo(rendaFamiliar2);
        rendaFamiliar1.setId(null);
        assertThat(rendaFamiliar1).isNotEqualTo(rendaFamiliar2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RendaFamiliarDTO.class);
        RendaFamiliarDTO rendaFamiliarDTO1 = new RendaFamiliarDTO();
        rendaFamiliarDTO1.setId(1L);
        RendaFamiliarDTO rendaFamiliarDTO2 = new RendaFamiliarDTO();
        assertThat(rendaFamiliarDTO1).isNotEqualTo(rendaFamiliarDTO2);
        rendaFamiliarDTO2.setId(rendaFamiliarDTO1.getId());
        assertThat(rendaFamiliarDTO1).isEqualTo(rendaFamiliarDTO2);
        rendaFamiliarDTO2.setId(2L);
        assertThat(rendaFamiliarDTO1).isNotEqualTo(rendaFamiliarDTO2);
        rendaFamiliarDTO1.setId(null);
        assertThat(rendaFamiliarDTO1).isNotEqualTo(rendaFamiliarDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(rendaFamiliarMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(rendaFamiliarMapper.fromId(null)).isNull();
    }
}
