package br.mp.mpro.caopapp.web.rest;

import br.mp.mpro.caopapp.CaopApp;

import br.mp.mpro.caopapp.domain.FaixaEtaria;
import br.mp.mpro.caopapp.repository.FaixaEtariaRepository;
import br.mp.mpro.caopapp.service.FaixaEtariaService;
import br.mp.mpro.caopapp.service.dto.FaixaEtariaDTO;
import br.mp.mpro.caopapp.service.mapper.FaixaEtariaMapper;
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
 * Test class for the FaixaEtariaResource REST controller.
 *
 * @see FaixaEtariaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaopApp.class)
public class FaixaEtariaResourceIntTest {

    private static final String DEFAULT_FAIXA = "AAAAAAAAAA";
    private static final String UPDATED_FAIXA = "BBBBBBBBBB";

    @Autowired
    private FaixaEtariaRepository faixaEtariaRepository;

    @Autowired
    private FaixaEtariaMapper faixaEtariaMapper;

    @Autowired
    private FaixaEtariaService faixaEtariaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFaixaEtariaMockMvc;

    private FaixaEtaria faixaEtaria;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FaixaEtariaResource faixaEtariaResource = new FaixaEtariaResource(faixaEtariaService);
        this.restFaixaEtariaMockMvc = MockMvcBuilders.standaloneSetup(faixaEtariaResource)
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
    public static FaixaEtaria createEntity(EntityManager em) {
        FaixaEtaria faixaEtaria = new FaixaEtaria()
            .faixa(DEFAULT_FAIXA);
        return faixaEtaria;
    }

    @Before
    public void initTest() {
        faixaEtaria = createEntity(em);
    }

    @Test
    @Transactional
    public void createFaixaEtaria() throws Exception {
        int databaseSizeBeforeCreate = faixaEtariaRepository.findAll().size();

        // Create the FaixaEtaria
        FaixaEtariaDTO faixaEtariaDTO = faixaEtariaMapper.toDto(faixaEtaria);
        restFaixaEtariaMockMvc.perform(post("/api/faixa-etarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faixaEtariaDTO)))
            .andExpect(status().isCreated());

        // Validate the FaixaEtaria in the database
        List<FaixaEtaria> faixaEtariaList = faixaEtariaRepository.findAll();
        assertThat(faixaEtariaList).hasSize(databaseSizeBeforeCreate + 1);
        FaixaEtaria testFaixaEtaria = faixaEtariaList.get(faixaEtariaList.size() - 1);
        assertThat(testFaixaEtaria.getFaixa()).isEqualTo(DEFAULT_FAIXA);
    }

    @Test
    @Transactional
    public void createFaixaEtariaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = faixaEtariaRepository.findAll().size();

        // Create the FaixaEtaria with an existing ID
        faixaEtaria.setId(1L);
        FaixaEtariaDTO faixaEtariaDTO = faixaEtariaMapper.toDto(faixaEtaria);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFaixaEtariaMockMvc.perform(post("/api/faixa-etarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faixaEtariaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FaixaEtaria in the database
        List<FaixaEtaria> faixaEtariaList = faixaEtariaRepository.findAll();
        assertThat(faixaEtariaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFaixaIsRequired() throws Exception {
        int databaseSizeBeforeTest = faixaEtariaRepository.findAll().size();
        // set the field null
        faixaEtaria.setFaixa(null);

        // Create the FaixaEtaria, which fails.
        FaixaEtariaDTO faixaEtariaDTO = faixaEtariaMapper.toDto(faixaEtaria);

        restFaixaEtariaMockMvc.perform(post("/api/faixa-etarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faixaEtariaDTO)))
            .andExpect(status().isBadRequest());

        List<FaixaEtaria> faixaEtariaList = faixaEtariaRepository.findAll();
        assertThat(faixaEtariaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFaixaEtarias() throws Exception {
        // Initialize the database
        faixaEtariaRepository.saveAndFlush(faixaEtaria);

        // Get all the faixaEtariaList
        restFaixaEtariaMockMvc.perform(get("/api/faixa-etarias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(faixaEtaria.getId().intValue())))
            .andExpect(jsonPath("$.[*].faixa").value(hasItem(DEFAULT_FAIXA.toString())));
    }

    @Test
    @Transactional
    public void getFaixaEtaria() throws Exception {
        // Initialize the database
        faixaEtariaRepository.saveAndFlush(faixaEtaria);

        // Get the faixaEtaria
        restFaixaEtariaMockMvc.perform(get("/api/faixa-etarias/{id}", faixaEtaria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(faixaEtaria.getId().intValue()))
            .andExpect(jsonPath("$.faixa").value(DEFAULT_FAIXA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFaixaEtaria() throws Exception {
        // Get the faixaEtaria
        restFaixaEtariaMockMvc.perform(get("/api/faixa-etarias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFaixaEtaria() throws Exception {
        // Initialize the database
        faixaEtariaRepository.saveAndFlush(faixaEtaria);
        int databaseSizeBeforeUpdate = faixaEtariaRepository.findAll().size();

        // Update the faixaEtaria
        FaixaEtaria updatedFaixaEtaria = faixaEtariaRepository.findOne(faixaEtaria.getId());
        // Disconnect from session so that the updates on updatedFaixaEtaria are not directly saved in db
        em.detach(updatedFaixaEtaria);
        updatedFaixaEtaria
            .faixa(UPDATED_FAIXA);
        FaixaEtariaDTO faixaEtariaDTO = faixaEtariaMapper.toDto(updatedFaixaEtaria);

        restFaixaEtariaMockMvc.perform(put("/api/faixa-etarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faixaEtariaDTO)))
            .andExpect(status().isOk());

        // Validate the FaixaEtaria in the database
        List<FaixaEtaria> faixaEtariaList = faixaEtariaRepository.findAll();
        assertThat(faixaEtariaList).hasSize(databaseSizeBeforeUpdate);
        FaixaEtaria testFaixaEtaria = faixaEtariaList.get(faixaEtariaList.size() - 1);
        assertThat(testFaixaEtaria.getFaixa()).isEqualTo(UPDATED_FAIXA);
    }

    @Test
    @Transactional
    public void updateNonExistingFaixaEtaria() throws Exception {
        int databaseSizeBeforeUpdate = faixaEtariaRepository.findAll().size();

        // Create the FaixaEtaria
        FaixaEtariaDTO faixaEtariaDTO = faixaEtariaMapper.toDto(faixaEtaria);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFaixaEtariaMockMvc.perform(put("/api/faixa-etarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faixaEtariaDTO)))
            .andExpect(status().isCreated());

        // Validate the FaixaEtaria in the database
        List<FaixaEtaria> faixaEtariaList = faixaEtariaRepository.findAll();
        assertThat(faixaEtariaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFaixaEtaria() throws Exception {
        // Initialize the database
        faixaEtariaRepository.saveAndFlush(faixaEtaria);
        int databaseSizeBeforeDelete = faixaEtariaRepository.findAll().size();

        // Get the faixaEtaria
        restFaixaEtariaMockMvc.perform(delete("/api/faixa-etarias/{id}", faixaEtaria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FaixaEtaria> faixaEtariaList = faixaEtariaRepository.findAll();
        assertThat(faixaEtariaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FaixaEtaria.class);
        FaixaEtaria faixaEtaria1 = new FaixaEtaria();
        faixaEtaria1.setId(1L);
        FaixaEtaria faixaEtaria2 = new FaixaEtaria();
        faixaEtaria2.setId(faixaEtaria1.getId());
        assertThat(faixaEtaria1).isEqualTo(faixaEtaria2);
        faixaEtaria2.setId(2L);
        assertThat(faixaEtaria1).isNotEqualTo(faixaEtaria2);
        faixaEtaria1.setId(null);
        assertThat(faixaEtaria1).isNotEqualTo(faixaEtaria2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FaixaEtariaDTO.class);
        FaixaEtariaDTO faixaEtariaDTO1 = new FaixaEtariaDTO();
        faixaEtariaDTO1.setId(1L);
        FaixaEtariaDTO faixaEtariaDTO2 = new FaixaEtariaDTO();
        assertThat(faixaEtariaDTO1).isNotEqualTo(faixaEtariaDTO2);
        faixaEtariaDTO2.setId(faixaEtariaDTO1.getId());
        assertThat(faixaEtariaDTO1).isEqualTo(faixaEtariaDTO2);
        faixaEtariaDTO2.setId(2L);
        assertThat(faixaEtariaDTO1).isNotEqualTo(faixaEtariaDTO2);
        faixaEtariaDTO1.setId(null);
        assertThat(faixaEtariaDTO1).isNotEqualTo(faixaEtariaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(faixaEtariaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(faixaEtariaMapper.fromId(null)).isNull();
    }
}
