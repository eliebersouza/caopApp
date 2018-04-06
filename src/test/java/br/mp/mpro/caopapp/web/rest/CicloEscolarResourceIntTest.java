package br.mp.mpro.caopapp.web.rest;

import br.mp.mpro.caopapp.CaopApp;

import br.mp.mpro.caopapp.domain.CicloEscolar;
import br.mp.mpro.caopapp.repository.CicloEscolarRepository;
import br.mp.mpro.caopapp.service.CicloEscolarService;
import br.mp.mpro.caopapp.service.dto.CicloEscolarDTO;
import br.mp.mpro.caopapp.service.mapper.CicloEscolarMapper;
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
 * Test class for the CicloEscolarResource REST controller.
 *
 * @see CicloEscolarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaopApp.class)
public class CicloEscolarResourceIntTest {

    private static final String DEFAULT_CICLO = "AAAAAAAAAA";
    private static final String UPDATED_CICLO = "BBBBBBBBBB";

    private static final Integer DEFAULT_IDADE_MINIMA = 1;
    private static final Integer UPDATED_IDADE_MINIMA = 2;

    private static final Integer DEFAULT_IDADE_MAXIMA = 1;
    private static final Integer UPDATED_IDADE_MAXIMA = 2;

    @Autowired
    private CicloEscolarRepository cicloEscolarRepository;

    @Autowired
    private CicloEscolarMapper cicloEscolarMapper;

    @Autowired
    private CicloEscolarService cicloEscolarService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCicloEscolarMockMvc;

    private CicloEscolar cicloEscolar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CicloEscolarResource cicloEscolarResource = new CicloEscolarResource(cicloEscolarService);
        this.restCicloEscolarMockMvc = MockMvcBuilders.standaloneSetup(cicloEscolarResource)
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
    public static CicloEscolar createEntity(EntityManager em) {
        CicloEscolar cicloEscolar = new CicloEscolar()
            .ciclo(DEFAULT_CICLO)
            .idadeMinima(DEFAULT_IDADE_MINIMA)
            .idadeMaxima(DEFAULT_IDADE_MAXIMA);
        return cicloEscolar;
    }

    @Before
    public void initTest() {
        cicloEscolar = createEntity(em);
    }

    @Test
    @Transactional
    public void createCicloEscolar() throws Exception {
        int databaseSizeBeforeCreate = cicloEscolarRepository.findAll().size();

        // Create the CicloEscolar
        CicloEscolarDTO cicloEscolarDTO = cicloEscolarMapper.toDto(cicloEscolar);
        restCicloEscolarMockMvc.perform(post("/api/ciclo-escolars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cicloEscolarDTO)))
            .andExpect(status().isCreated());

        // Validate the CicloEscolar in the database
        List<CicloEscolar> cicloEscolarList = cicloEscolarRepository.findAll();
        assertThat(cicloEscolarList).hasSize(databaseSizeBeforeCreate + 1);
        CicloEscolar testCicloEscolar = cicloEscolarList.get(cicloEscolarList.size() - 1);
        assertThat(testCicloEscolar.getCiclo()).isEqualTo(DEFAULT_CICLO);
        assertThat(testCicloEscolar.getIdadeMinima()).isEqualTo(DEFAULT_IDADE_MINIMA);
        assertThat(testCicloEscolar.getIdadeMaxima()).isEqualTo(DEFAULT_IDADE_MAXIMA);
    }

    @Test
    @Transactional
    public void createCicloEscolarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cicloEscolarRepository.findAll().size();

        // Create the CicloEscolar with an existing ID
        cicloEscolar.setId(1L);
        CicloEscolarDTO cicloEscolarDTO = cicloEscolarMapper.toDto(cicloEscolar);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCicloEscolarMockMvc.perform(post("/api/ciclo-escolars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cicloEscolarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CicloEscolar in the database
        List<CicloEscolar> cicloEscolarList = cicloEscolarRepository.findAll();
        assertThat(cicloEscolarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCicloIsRequired() throws Exception {
        int databaseSizeBeforeTest = cicloEscolarRepository.findAll().size();
        // set the field null
        cicloEscolar.setCiclo(null);

        // Create the CicloEscolar, which fails.
        CicloEscolarDTO cicloEscolarDTO = cicloEscolarMapper.toDto(cicloEscolar);

        restCicloEscolarMockMvc.perform(post("/api/ciclo-escolars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cicloEscolarDTO)))
            .andExpect(status().isBadRequest());

        List<CicloEscolar> cicloEscolarList = cicloEscolarRepository.findAll();
        assertThat(cicloEscolarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdadeMinimaIsRequired() throws Exception {
        int databaseSizeBeforeTest = cicloEscolarRepository.findAll().size();
        // set the field null
        cicloEscolar.setIdadeMinima(null);

        // Create the CicloEscolar, which fails.
        CicloEscolarDTO cicloEscolarDTO = cicloEscolarMapper.toDto(cicloEscolar);

        restCicloEscolarMockMvc.perform(post("/api/ciclo-escolars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cicloEscolarDTO)))
            .andExpect(status().isBadRequest());

        List<CicloEscolar> cicloEscolarList = cicloEscolarRepository.findAll();
        assertThat(cicloEscolarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdadeMaximaIsRequired() throws Exception {
        int databaseSizeBeforeTest = cicloEscolarRepository.findAll().size();
        // set the field null
        cicloEscolar.setIdadeMaxima(null);

        // Create the CicloEscolar, which fails.
        CicloEscolarDTO cicloEscolarDTO = cicloEscolarMapper.toDto(cicloEscolar);

        restCicloEscolarMockMvc.perform(post("/api/ciclo-escolars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cicloEscolarDTO)))
            .andExpect(status().isBadRequest());

        List<CicloEscolar> cicloEscolarList = cicloEscolarRepository.findAll();
        assertThat(cicloEscolarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCicloEscolars() throws Exception {
        // Initialize the database
        cicloEscolarRepository.saveAndFlush(cicloEscolar);

        // Get all the cicloEscolarList
        restCicloEscolarMockMvc.perform(get("/api/ciclo-escolars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cicloEscolar.getId().intValue())))
            .andExpect(jsonPath("$.[*].ciclo").value(hasItem(DEFAULT_CICLO.toString())))
            .andExpect(jsonPath("$.[*].idadeMinima").value(hasItem(DEFAULT_IDADE_MINIMA)))
            .andExpect(jsonPath("$.[*].idadeMaxima").value(hasItem(DEFAULT_IDADE_MAXIMA)));
    }

    @Test
    @Transactional
    public void getCicloEscolar() throws Exception {
        // Initialize the database
        cicloEscolarRepository.saveAndFlush(cicloEscolar);

        // Get the cicloEscolar
        restCicloEscolarMockMvc.perform(get("/api/ciclo-escolars/{id}", cicloEscolar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cicloEscolar.getId().intValue()))
            .andExpect(jsonPath("$.ciclo").value(DEFAULT_CICLO.toString()))
            .andExpect(jsonPath("$.idadeMinima").value(DEFAULT_IDADE_MINIMA))
            .andExpect(jsonPath("$.idadeMaxima").value(DEFAULT_IDADE_MAXIMA));
    }

    @Test
    @Transactional
    public void getNonExistingCicloEscolar() throws Exception {
        // Get the cicloEscolar
        restCicloEscolarMockMvc.perform(get("/api/ciclo-escolars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCicloEscolar() throws Exception {
        // Initialize the database
        cicloEscolarRepository.saveAndFlush(cicloEscolar);
        int databaseSizeBeforeUpdate = cicloEscolarRepository.findAll().size();

        // Update the cicloEscolar
        CicloEscolar updatedCicloEscolar = cicloEscolarRepository.findOne(cicloEscolar.getId());
        // Disconnect from session so that the updates on updatedCicloEscolar are not directly saved in db
        em.detach(updatedCicloEscolar);
        updatedCicloEscolar
            .ciclo(UPDATED_CICLO)
            .idadeMinima(UPDATED_IDADE_MINIMA)
            .idadeMaxima(UPDATED_IDADE_MAXIMA);
        CicloEscolarDTO cicloEscolarDTO = cicloEscolarMapper.toDto(updatedCicloEscolar);

        restCicloEscolarMockMvc.perform(put("/api/ciclo-escolars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cicloEscolarDTO)))
            .andExpect(status().isOk());

        // Validate the CicloEscolar in the database
        List<CicloEscolar> cicloEscolarList = cicloEscolarRepository.findAll();
        assertThat(cicloEscolarList).hasSize(databaseSizeBeforeUpdate);
        CicloEscolar testCicloEscolar = cicloEscolarList.get(cicloEscolarList.size() - 1);
        assertThat(testCicloEscolar.getCiclo()).isEqualTo(UPDATED_CICLO);
        assertThat(testCicloEscolar.getIdadeMinima()).isEqualTo(UPDATED_IDADE_MINIMA);
        assertThat(testCicloEscolar.getIdadeMaxima()).isEqualTo(UPDATED_IDADE_MAXIMA);
    }

    @Test
    @Transactional
    public void updateNonExistingCicloEscolar() throws Exception {
        int databaseSizeBeforeUpdate = cicloEscolarRepository.findAll().size();

        // Create the CicloEscolar
        CicloEscolarDTO cicloEscolarDTO = cicloEscolarMapper.toDto(cicloEscolar);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCicloEscolarMockMvc.perform(put("/api/ciclo-escolars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cicloEscolarDTO)))
            .andExpect(status().isCreated());

        // Validate the CicloEscolar in the database
        List<CicloEscolar> cicloEscolarList = cicloEscolarRepository.findAll();
        assertThat(cicloEscolarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCicloEscolar() throws Exception {
        // Initialize the database
        cicloEscolarRepository.saveAndFlush(cicloEscolar);
        int databaseSizeBeforeDelete = cicloEscolarRepository.findAll().size();

        // Get the cicloEscolar
        restCicloEscolarMockMvc.perform(delete("/api/ciclo-escolars/{id}", cicloEscolar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CicloEscolar> cicloEscolarList = cicloEscolarRepository.findAll();
        assertThat(cicloEscolarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CicloEscolar.class);
        CicloEscolar cicloEscolar1 = new CicloEscolar();
        cicloEscolar1.setId(1L);
        CicloEscolar cicloEscolar2 = new CicloEscolar();
        cicloEscolar2.setId(cicloEscolar1.getId());
        assertThat(cicloEscolar1).isEqualTo(cicloEscolar2);
        cicloEscolar2.setId(2L);
        assertThat(cicloEscolar1).isNotEqualTo(cicloEscolar2);
        cicloEscolar1.setId(null);
        assertThat(cicloEscolar1).isNotEqualTo(cicloEscolar2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CicloEscolarDTO.class);
        CicloEscolarDTO cicloEscolarDTO1 = new CicloEscolarDTO();
        cicloEscolarDTO1.setId(1L);
        CicloEscolarDTO cicloEscolarDTO2 = new CicloEscolarDTO();
        assertThat(cicloEscolarDTO1).isNotEqualTo(cicloEscolarDTO2);
        cicloEscolarDTO2.setId(cicloEscolarDTO1.getId());
        assertThat(cicloEscolarDTO1).isEqualTo(cicloEscolarDTO2);
        cicloEscolarDTO2.setId(2L);
        assertThat(cicloEscolarDTO1).isNotEqualTo(cicloEscolarDTO2);
        cicloEscolarDTO1.setId(null);
        assertThat(cicloEscolarDTO1).isNotEqualTo(cicloEscolarDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cicloEscolarMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cicloEscolarMapper.fromId(null)).isNull();
    }
}
