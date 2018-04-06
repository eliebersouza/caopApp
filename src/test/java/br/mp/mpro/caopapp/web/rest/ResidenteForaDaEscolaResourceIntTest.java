package br.mp.mpro.caopapp.web.rest;

import br.mp.mpro.caopapp.CaopApp;

import br.mp.mpro.caopapp.domain.ResidenteForaDaEscola;
import br.mp.mpro.caopapp.domain.CicloEscolar;
import br.mp.mpro.caopapp.domain.MotivoEvasao;
import br.mp.mpro.caopapp.domain.Form;
import br.mp.mpro.caopapp.repository.ResidenteForaDaEscolaRepository;
import br.mp.mpro.caopapp.service.ResidenteForaDaEscolaService;
import br.mp.mpro.caopapp.service.dto.ResidenteForaDaEscolaDTO;
import br.mp.mpro.caopapp.service.mapper.ResidenteForaDaEscolaMapper;
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

import br.mp.mpro.caopapp.domain.enumeration.SimNao;
import br.mp.mpro.caopapp.domain.enumeration.SimNao;
import br.mp.mpro.caopapp.domain.enumeration.SimNao;
import br.mp.mpro.caopapp.domain.enumeration.PresencialDistancia;
import br.mp.mpro.caopapp.domain.enumeration.TipoCurso;
/**
 * Test class for the ResidenteForaDaEscolaResource REST controller.
 *
 * @see ResidenteForaDaEscolaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaopApp.class)
public class ResidenteForaDaEscolaResourceIntTest {

    private static final String DEFAULT_NOME_DO_CIDADAO_FORA_DA_ESCOLA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_DO_CIDADAO_FORA_DA_ESCOLA = "BBBBBBBBBB";

    private static final Integer DEFAULT_IDADE = 1;
    private static final Integer UPDATED_IDADE = 2;

    private static final SimNao DEFAULT_DEFICIENCIA = SimNao.SIM;
    private static final SimNao UPDATED_DEFICIENCIA = SimNao.NAO;

    private static final SimNao DEFAULT_SABE_LER = SimNao.SIM;
    private static final SimNao UPDATED_SABE_LER = SimNao.NAO;

    private static final SimNao DEFAULT_RETOMAR_ESTUDOS = SimNao.SIM;
    private static final SimNao UPDATED_RETOMAR_ESTUDOS = SimNao.NAO;

    private static final String DEFAULT_NOME_CURSO_DESEJADO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_CURSO_DESEJADO = "BBBBBBBBBB";

    private static final PresencialDistancia DEFAULT_PRESENCIAL_DISTANCIA = PresencialDistancia.PRESENCIAL;
    private static final PresencialDistancia UPDATED_PRESENCIAL_DISTANCIA = PresencialDistancia.DISTANCIA;

    private static final TipoCurso DEFAULT_TIPO_CURSO = TipoCurso.TECNICO;
    private static final TipoCurso UPDATED_TIPO_CURSO = TipoCurso.GRADUACAO;

    private static final String DEFAULT_OBSERVACAO_OUTROS_MOTIVOS = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO_OUTROS_MOTIVOS = "BBBBBBBBBB";

    @Autowired
    private ResidenteForaDaEscolaRepository residenteForaDaEscolaRepository;

    @Autowired
    private ResidenteForaDaEscolaMapper residenteForaDaEscolaMapper;

    @Autowired
    private ResidenteForaDaEscolaService residenteForaDaEscolaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restResidenteForaDaEscolaMockMvc;

    private ResidenteForaDaEscola residenteForaDaEscola;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResidenteForaDaEscolaResource residenteForaDaEscolaResource = new ResidenteForaDaEscolaResource(residenteForaDaEscolaService);
        this.restResidenteForaDaEscolaMockMvc = MockMvcBuilders.standaloneSetup(residenteForaDaEscolaResource)
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
    public static ResidenteForaDaEscola createEntity(EntityManager em) {
        ResidenteForaDaEscola residenteForaDaEscola = new ResidenteForaDaEscola()
            .nomeDoCidadaoForaDaEscola(DEFAULT_NOME_DO_CIDADAO_FORA_DA_ESCOLA)
            .idade(DEFAULT_IDADE)
            .deficiencia(DEFAULT_DEFICIENCIA)
            .sabeLer(DEFAULT_SABE_LER)
            .retomarEstudos(DEFAULT_RETOMAR_ESTUDOS)
            .nomeCursoDesejado(DEFAULT_NOME_CURSO_DESEJADO)
            .presencialDistancia(DEFAULT_PRESENCIAL_DISTANCIA)
            .tipoCurso(DEFAULT_TIPO_CURSO)
            .observacaoOutrosMotivos(DEFAULT_OBSERVACAO_OUTROS_MOTIVOS);
        // Add required entity
        CicloEscolar cicloEscolar = CicloEscolarResourceIntTest.createEntity(em);
        em.persist(cicloEscolar);
        em.flush();
        residenteForaDaEscola.setCicloEscolar(cicloEscolar);
        // Add required entity
        MotivoEvasao motivoEvasao = MotivoEvasaoResourceIntTest.createEntity(em);
        em.persist(motivoEvasao);
        em.flush();
        residenteForaDaEscola.setMotivoEvasao(motivoEvasao);
        // Add required entity
        Form form = FormResourceIntTest.createEntity(em);
        em.persist(form);
        em.flush();
        residenteForaDaEscola.setForm(form);
        return residenteForaDaEscola;
    }

    @Before
    public void initTest() {
        residenteForaDaEscola = createEntity(em);
    }

    @Test
    @Transactional
    public void createResidenteForaDaEscola() throws Exception {
        int databaseSizeBeforeCreate = residenteForaDaEscolaRepository.findAll().size();

        // Create the ResidenteForaDaEscola
        ResidenteForaDaEscolaDTO residenteForaDaEscolaDTO = residenteForaDaEscolaMapper.toDto(residenteForaDaEscola);
        restResidenteForaDaEscolaMockMvc.perform(post("/api/residente-fora-da-escolas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(residenteForaDaEscolaDTO)))
            .andExpect(status().isCreated());

        // Validate the ResidenteForaDaEscola in the database
        List<ResidenteForaDaEscola> residenteForaDaEscolaList = residenteForaDaEscolaRepository.findAll();
        assertThat(residenteForaDaEscolaList).hasSize(databaseSizeBeforeCreate + 1);
        ResidenteForaDaEscola testResidenteForaDaEscola = residenteForaDaEscolaList.get(residenteForaDaEscolaList.size() - 1);
        assertThat(testResidenteForaDaEscola.getNomeDoCidadaoForaDaEscola()).isEqualTo(DEFAULT_NOME_DO_CIDADAO_FORA_DA_ESCOLA);
        assertThat(testResidenteForaDaEscola.getIdade()).isEqualTo(DEFAULT_IDADE);
        assertThat(testResidenteForaDaEscola.getDeficiencia()).isEqualTo(DEFAULT_DEFICIENCIA);
        assertThat(testResidenteForaDaEscola.getSabeLer()).isEqualTo(DEFAULT_SABE_LER);
        assertThat(testResidenteForaDaEscola.getRetomarEstudos()).isEqualTo(DEFAULT_RETOMAR_ESTUDOS);
        assertThat(testResidenteForaDaEscola.getNomeCursoDesejado()).isEqualTo(DEFAULT_NOME_CURSO_DESEJADO);
        assertThat(testResidenteForaDaEscola.getPresencialDistancia()).isEqualTo(DEFAULT_PRESENCIAL_DISTANCIA);
        assertThat(testResidenteForaDaEscola.getTipoCurso()).isEqualTo(DEFAULT_TIPO_CURSO);
        assertThat(testResidenteForaDaEscola.getObservacaoOutrosMotivos()).isEqualTo(DEFAULT_OBSERVACAO_OUTROS_MOTIVOS);
    }

    @Test
    @Transactional
    public void createResidenteForaDaEscolaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = residenteForaDaEscolaRepository.findAll().size();

        // Create the ResidenteForaDaEscola with an existing ID
        residenteForaDaEscola.setId(1L);
        ResidenteForaDaEscolaDTO residenteForaDaEscolaDTO = residenteForaDaEscolaMapper.toDto(residenteForaDaEscola);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResidenteForaDaEscolaMockMvc.perform(post("/api/residente-fora-da-escolas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(residenteForaDaEscolaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ResidenteForaDaEscola in the database
        List<ResidenteForaDaEscola> residenteForaDaEscolaList = residenteForaDaEscolaRepository.findAll();
        assertThat(residenteForaDaEscolaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeDoCidadaoForaDaEscolaIsRequired() throws Exception {
        int databaseSizeBeforeTest = residenteForaDaEscolaRepository.findAll().size();
        // set the field null
        residenteForaDaEscola.setNomeDoCidadaoForaDaEscola(null);

        // Create the ResidenteForaDaEscola, which fails.
        ResidenteForaDaEscolaDTO residenteForaDaEscolaDTO = residenteForaDaEscolaMapper.toDto(residenteForaDaEscola);

        restResidenteForaDaEscolaMockMvc.perform(post("/api/residente-fora-da-escolas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(residenteForaDaEscolaDTO)))
            .andExpect(status().isBadRequest());

        List<ResidenteForaDaEscola> residenteForaDaEscolaList = residenteForaDaEscolaRepository.findAll();
        assertThat(residenteForaDaEscolaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = residenteForaDaEscolaRepository.findAll().size();
        // set the field null
        residenteForaDaEscola.setIdade(null);

        // Create the ResidenteForaDaEscola, which fails.
        ResidenteForaDaEscolaDTO residenteForaDaEscolaDTO = residenteForaDaEscolaMapper.toDto(residenteForaDaEscola);

        restResidenteForaDaEscolaMockMvc.perform(post("/api/residente-fora-da-escolas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(residenteForaDaEscolaDTO)))
            .andExpect(status().isBadRequest());

        List<ResidenteForaDaEscola> residenteForaDaEscolaList = residenteForaDaEscolaRepository.findAll();
        assertThat(residenteForaDaEscolaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeficienciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = residenteForaDaEscolaRepository.findAll().size();
        // set the field null
        residenteForaDaEscola.setDeficiencia(null);

        // Create the ResidenteForaDaEscola, which fails.
        ResidenteForaDaEscolaDTO residenteForaDaEscolaDTO = residenteForaDaEscolaMapper.toDto(residenteForaDaEscola);

        restResidenteForaDaEscolaMockMvc.perform(post("/api/residente-fora-da-escolas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(residenteForaDaEscolaDTO)))
            .andExpect(status().isBadRequest());

        List<ResidenteForaDaEscola> residenteForaDaEscolaList = residenteForaDaEscolaRepository.findAll();
        assertThat(residenteForaDaEscolaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSabeLerIsRequired() throws Exception {
        int databaseSizeBeforeTest = residenteForaDaEscolaRepository.findAll().size();
        // set the field null
        residenteForaDaEscola.setSabeLer(null);

        // Create the ResidenteForaDaEscola, which fails.
        ResidenteForaDaEscolaDTO residenteForaDaEscolaDTO = residenteForaDaEscolaMapper.toDto(residenteForaDaEscola);

        restResidenteForaDaEscolaMockMvc.perform(post("/api/residente-fora-da-escolas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(residenteForaDaEscolaDTO)))
            .andExpect(status().isBadRequest());

        List<ResidenteForaDaEscola> residenteForaDaEscolaList = residenteForaDaEscolaRepository.findAll();
        assertThat(residenteForaDaEscolaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRetomarEstudosIsRequired() throws Exception {
        int databaseSizeBeforeTest = residenteForaDaEscolaRepository.findAll().size();
        // set the field null
        residenteForaDaEscola.setRetomarEstudos(null);

        // Create the ResidenteForaDaEscola, which fails.
        ResidenteForaDaEscolaDTO residenteForaDaEscolaDTO = residenteForaDaEscolaMapper.toDto(residenteForaDaEscola);

        restResidenteForaDaEscolaMockMvc.perform(post("/api/residente-fora-da-escolas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(residenteForaDaEscolaDTO)))
            .andExpect(status().isBadRequest());

        List<ResidenteForaDaEscola> residenteForaDaEscolaList = residenteForaDaEscolaRepository.findAll();
        assertThat(residenteForaDaEscolaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResidenteForaDaEscolas() throws Exception {
        // Initialize the database
        residenteForaDaEscolaRepository.saveAndFlush(residenteForaDaEscola);

        // Get all the residenteForaDaEscolaList
        restResidenteForaDaEscolaMockMvc.perform(get("/api/residente-fora-da-escolas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(residenteForaDaEscola.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeDoCidadaoForaDaEscola").value(hasItem(DEFAULT_NOME_DO_CIDADAO_FORA_DA_ESCOLA.toString())))
            .andExpect(jsonPath("$.[*].idade").value(hasItem(DEFAULT_IDADE)))
            .andExpect(jsonPath("$.[*].deficiencia").value(hasItem(DEFAULT_DEFICIENCIA.toString())))
            .andExpect(jsonPath("$.[*].sabeLer").value(hasItem(DEFAULT_SABE_LER.toString())))
            .andExpect(jsonPath("$.[*].retomarEstudos").value(hasItem(DEFAULT_RETOMAR_ESTUDOS.toString())))
            .andExpect(jsonPath("$.[*].nomeCursoDesejado").value(hasItem(DEFAULT_NOME_CURSO_DESEJADO.toString())))
            .andExpect(jsonPath("$.[*].presencialDistancia").value(hasItem(DEFAULT_PRESENCIAL_DISTANCIA.toString())))
            .andExpect(jsonPath("$.[*].tipoCurso").value(hasItem(DEFAULT_TIPO_CURSO.toString())))
            .andExpect(jsonPath("$.[*].observacaoOutrosMotivos").value(hasItem(DEFAULT_OBSERVACAO_OUTROS_MOTIVOS.toString())));
    }

    @Test
    @Transactional
    public void getResidenteForaDaEscola() throws Exception {
        // Initialize the database
        residenteForaDaEscolaRepository.saveAndFlush(residenteForaDaEscola);

        // Get the residenteForaDaEscola
        restResidenteForaDaEscolaMockMvc.perform(get("/api/residente-fora-da-escolas/{id}", residenteForaDaEscola.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(residenteForaDaEscola.getId().intValue()))
            .andExpect(jsonPath("$.nomeDoCidadaoForaDaEscola").value(DEFAULT_NOME_DO_CIDADAO_FORA_DA_ESCOLA.toString()))
            .andExpect(jsonPath("$.idade").value(DEFAULT_IDADE))
            .andExpect(jsonPath("$.deficiencia").value(DEFAULT_DEFICIENCIA.toString()))
            .andExpect(jsonPath("$.sabeLer").value(DEFAULT_SABE_LER.toString()))
            .andExpect(jsonPath("$.retomarEstudos").value(DEFAULT_RETOMAR_ESTUDOS.toString()))
            .andExpect(jsonPath("$.nomeCursoDesejado").value(DEFAULT_NOME_CURSO_DESEJADO.toString()))
            .andExpect(jsonPath("$.presencialDistancia").value(DEFAULT_PRESENCIAL_DISTANCIA.toString()))
            .andExpect(jsonPath("$.tipoCurso").value(DEFAULT_TIPO_CURSO.toString()))
            .andExpect(jsonPath("$.observacaoOutrosMotivos").value(DEFAULT_OBSERVACAO_OUTROS_MOTIVOS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResidenteForaDaEscola() throws Exception {
        // Get the residenteForaDaEscola
        restResidenteForaDaEscolaMockMvc.perform(get("/api/residente-fora-da-escolas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResidenteForaDaEscola() throws Exception {
        // Initialize the database
        residenteForaDaEscolaRepository.saveAndFlush(residenteForaDaEscola);
        int databaseSizeBeforeUpdate = residenteForaDaEscolaRepository.findAll().size();

        // Update the residenteForaDaEscola
        ResidenteForaDaEscola updatedResidenteForaDaEscola = residenteForaDaEscolaRepository.findOne(residenteForaDaEscola.getId());
        // Disconnect from session so that the updates on updatedResidenteForaDaEscola are not directly saved in db
        em.detach(updatedResidenteForaDaEscola);
        updatedResidenteForaDaEscola
            .nomeDoCidadaoForaDaEscola(UPDATED_NOME_DO_CIDADAO_FORA_DA_ESCOLA)
            .idade(UPDATED_IDADE)
            .deficiencia(UPDATED_DEFICIENCIA)
            .sabeLer(UPDATED_SABE_LER)
            .retomarEstudos(UPDATED_RETOMAR_ESTUDOS)
            .nomeCursoDesejado(UPDATED_NOME_CURSO_DESEJADO)
            .presencialDistancia(UPDATED_PRESENCIAL_DISTANCIA)
            .tipoCurso(UPDATED_TIPO_CURSO)
            .observacaoOutrosMotivos(UPDATED_OBSERVACAO_OUTROS_MOTIVOS);
        ResidenteForaDaEscolaDTO residenteForaDaEscolaDTO = residenteForaDaEscolaMapper.toDto(updatedResidenteForaDaEscola);

        restResidenteForaDaEscolaMockMvc.perform(put("/api/residente-fora-da-escolas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(residenteForaDaEscolaDTO)))
            .andExpect(status().isOk());

        // Validate the ResidenteForaDaEscola in the database
        List<ResidenteForaDaEscola> residenteForaDaEscolaList = residenteForaDaEscolaRepository.findAll();
        assertThat(residenteForaDaEscolaList).hasSize(databaseSizeBeforeUpdate);
        ResidenteForaDaEscola testResidenteForaDaEscola = residenteForaDaEscolaList.get(residenteForaDaEscolaList.size() - 1);
        assertThat(testResidenteForaDaEscola.getNomeDoCidadaoForaDaEscola()).isEqualTo(UPDATED_NOME_DO_CIDADAO_FORA_DA_ESCOLA);
        assertThat(testResidenteForaDaEscola.getIdade()).isEqualTo(UPDATED_IDADE);
        assertThat(testResidenteForaDaEscola.getDeficiencia()).isEqualTo(UPDATED_DEFICIENCIA);
        assertThat(testResidenteForaDaEscola.getSabeLer()).isEqualTo(UPDATED_SABE_LER);
        assertThat(testResidenteForaDaEscola.getRetomarEstudos()).isEqualTo(UPDATED_RETOMAR_ESTUDOS);
        assertThat(testResidenteForaDaEscola.getNomeCursoDesejado()).isEqualTo(UPDATED_NOME_CURSO_DESEJADO);
        assertThat(testResidenteForaDaEscola.getPresencialDistancia()).isEqualTo(UPDATED_PRESENCIAL_DISTANCIA);
        assertThat(testResidenteForaDaEscola.getTipoCurso()).isEqualTo(UPDATED_TIPO_CURSO);
        assertThat(testResidenteForaDaEscola.getObservacaoOutrosMotivos()).isEqualTo(UPDATED_OBSERVACAO_OUTROS_MOTIVOS);
    }

    @Test
    @Transactional
    public void updateNonExistingResidenteForaDaEscola() throws Exception {
        int databaseSizeBeforeUpdate = residenteForaDaEscolaRepository.findAll().size();

        // Create the ResidenteForaDaEscola
        ResidenteForaDaEscolaDTO residenteForaDaEscolaDTO = residenteForaDaEscolaMapper.toDto(residenteForaDaEscola);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restResidenteForaDaEscolaMockMvc.perform(put("/api/residente-fora-da-escolas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(residenteForaDaEscolaDTO)))
            .andExpect(status().isCreated());

        // Validate the ResidenteForaDaEscola in the database
        List<ResidenteForaDaEscola> residenteForaDaEscolaList = residenteForaDaEscolaRepository.findAll();
        assertThat(residenteForaDaEscolaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteResidenteForaDaEscola() throws Exception {
        // Initialize the database
        residenteForaDaEscolaRepository.saveAndFlush(residenteForaDaEscola);
        int databaseSizeBeforeDelete = residenteForaDaEscolaRepository.findAll().size();

        // Get the residenteForaDaEscola
        restResidenteForaDaEscolaMockMvc.perform(delete("/api/residente-fora-da-escolas/{id}", residenteForaDaEscola.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ResidenteForaDaEscola> residenteForaDaEscolaList = residenteForaDaEscolaRepository.findAll();
        assertThat(residenteForaDaEscolaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResidenteForaDaEscola.class);
        ResidenteForaDaEscola residenteForaDaEscola1 = new ResidenteForaDaEscola();
        residenteForaDaEscola1.setId(1L);
        ResidenteForaDaEscola residenteForaDaEscola2 = new ResidenteForaDaEscola();
        residenteForaDaEscola2.setId(residenteForaDaEscola1.getId());
        assertThat(residenteForaDaEscola1).isEqualTo(residenteForaDaEscola2);
        residenteForaDaEscola2.setId(2L);
        assertThat(residenteForaDaEscola1).isNotEqualTo(residenteForaDaEscola2);
        residenteForaDaEscola1.setId(null);
        assertThat(residenteForaDaEscola1).isNotEqualTo(residenteForaDaEscola2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResidenteForaDaEscolaDTO.class);
        ResidenteForaDaEscolaDTO residenteForaDaEscolaDTO1 = new ResidenteForaDaEscolaDTO();
        residenteForaDaEscolaDTO1.setId(1L);
        ResidenteForaDaEscolaDTO residenteForaDaEscolaDTO2 = new ResidenteForaDaEscolaDTO();
        assertThat(residenteForaDaEscolaDTO1).isNotEqualTo(residenteForaDaEscolaDTO2);
        residenteForaDaEscolaDTO2.setId(residenteForaDaEscolaDTO1.getId());
        assertThat(residenteForaDaEscolaDTO1).isEqualTo(residenteForaDaEscolaDTO2);
        residenteForaDaEscolaDTO2.setId(2L);
        assertThat(residenteForaDaEscolaDTO1).isNotEqualTo(residenteForaDaEscolaDTO2);
        residenteForaDaEscolaDTO1.setId(null);
        assertThat(residenteForaDaEscolaDTO1).isNotEqualTo(residenteForaDaEscolaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(residenteForaDaEscolaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(residenteForaDaEscolaMapper.fromId(null)).isNull();
    }
}
