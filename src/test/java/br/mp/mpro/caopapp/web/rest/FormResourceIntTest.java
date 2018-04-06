package br.mp.mpro.caopapp.web.rest;

import br.mp.mpro.caopapp.CaopApp;

import br.mp.mpro.caopapp.domain.Form;
import br.mp.mpro.caopapp.domain.RendaFamiliar;
import br.mp.mpro.caopapp.domain.Periodicidade;
import br.mp.mpro.caopapp.domain.Religiao;
import br.mp.mpro.caopapp.domain.ResponsavelLar;
import br.mp.mpro.caopapp.domain.ServicoPublico;
import br.mp.mpro.caopapp.repository.FormRepository;
import br.mp.mpro.caopapp.service.FormService;
import br.mp.mpro.caopapp.service.dto.FormDTO;
import br.mp.mpro.caopapp.service.mapper.FormMapper;
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

import br.mp.mpro.caopapp.domain.enumeration.LocalizacaoDomicilio;
import br.mp.mpro.caopapp.domain.enumeration.SituacaoResidencia;
import br.mp.mpro.caopapp.domain.enumeration.SimNao;
import br.mp.mpro.caopapp.domain.enumeration.SimNao;
import br.mp.mpro.caopapp.domain.enumeration.SimNao;
import br.mp.mpro.caopapp.domain.enumeration.SimNao;
import br.mp.mpro.caopapp.domain.enumeration.SimNao;
import br.mp.mpro.caopapp.domain.enumeration.OpcaoAvaliacao;
import br.mp.mpro.caopapp.domain.enumeration.OpcaoAvaliacao;
import br.mp.mpro.caopapp.domain.enumeration.SimNao;
import br.mp.mpro.caopapp.domain.enumeration.SimNao;
import br.mp.mpro.caopapp.domain.enumeration.OpcaoAvaliacao;
import br.mp.mpro.caopapp.domain.enumeration.OpcaoAvaliacao;
/**
 * Test class for the FormResource REST controller.
 *
 * @see FormResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaopApp.class)
public class FormResourceIntTest {

    private static final String DEFAULT_RESPONSAVEL_PELA_INFORMACAO = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSAVEL_PELA_INFORMACAO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_RUA = "AAAAAAAAAA";
    private static final String UPDATED_RUA = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO_SETOR = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO_SETOR = "BBBBBBBBBB";

    private static final LocalizacaoDomicilio DEFAULT_LOCALIZACAO_DO_DOMICILIO = LocalizacaoDomicilio.URBANA;
    private static final LocalizacaoDomicilio UPDATED_LOCALIZACAO_DO_DOMICILIO = LocalizacaoDomicilio.RURAL;

    private static final SituacaoResidencia DEFAULT_SITUACAO_DA_RESIDENCIA = SituacaoResidencia.ABERTA;
    private static final SituacaoResidencia UPDATED_SITUACAO_DA_RESIDENCIA = SituacaoResidencia.FECHADA;

    private static final String DEFAULT_NUMERO_DA_QUADRA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DA_QUADRA = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_DA_CASA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DA_CASA = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTIDADE_DE_RESIDENTES = 1;
    private static final Integer UPDATED_QUANTIDADE_DE_RESIDENTES = 2;

    private static final SimNao DEFAULT_RESIDENTE_FRENQUENTADO_ESCOLA = SimNao.SIM;
    private static final SimNao UPDATED_RESIDENTE_FRENQUENTADO_ESCOLA = SimNao.NAO;

    private static final SimNao DEFAULT_PRIVADO_DE_LIBERDADE = SimNao.SIM;
    private static final SimNao UPDATED_PRIVADO_DE_LIBERDADE = SimNao.NAO;

    private static final SimNao DEFAULT_RECEBE_AUXILIO_RECLUSAO = SimNao.SIM;
    private static final SimNao UPDATED_RECEBE_AUXILIO_RECLUSAO = SimNao.NAO;

    private static final SimNao DEFAULT_FAMILIA_VISITANDO_PRESO = SimNao.SIM;
    private static final SimNao UPDATED_FAMILIA_VISITANDO_PRESO = SimNao.NAO;

    private static final SimNao DEFAULT_CUMPRIMENTO_SEMI_ABERTO = SimNao.SIM;
    private static final SimNao UPDATED_CUMPRIMENTO_SEMI_ABERTO = SimNao.NAO;

    private static final OpcaoAvaliacao DEFAULT_AVALIACAO_POLICIA_MILITAR = OpcaoAvaliacao.EXCELENTE;
    private static final OpcaoAvaliacao UPDATED_AVALIACAO_POLICIA_MILITAR = OpcaoAvaliacao.BOM;

    private static final OpcaoAvaliacao DEFAULT_AVALIACAO_POLICIA_CIVEL = OpcaoAvaliacao.EXCELENTE;
    private static final OpcaoAvaliacao UPDATED_AVALIACAO_POLICIA_CIVEL = OpcaoAvaliacao.BOM;

    private static final SimNao DEFAULT_NAO_ATENDIDO_TELEFONE_POLICIA = SimNao.SIM;
    private static final SimNao UPDATED_NAO_ATENDIDO_TELEFONE_POLICIA = SimNao.NAO;

    private static final SimNao DEFAULT_NAO_ATENDIDO_DELEGACIA = SimNao.SIM;
    private static final SimNao UPDATED_NAO_ATENDIDO_DELEGACIA = SimNao.NAO;

    private static final OpcaoAvaliacao DEFAULT_AVALIACAO_AGENTE_SAUDE = OpcaoAvaliacao.EXCELENTE;
    private static final OpcaoAvaliacao UPDATED_AVALIACAO_AGENTE_SAUDE = OpcaoAvaliacao.BOM;

    private static final OpcaoAvaliacao DEFAULT_AVALIACAO_UBS = OpcaoAvaliacao.EXCELENTE;
    private static final OpcaoAvaliacao UPDATED_AVALIACAO_UBS = OpcaoAvaliacao.BOM;

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private FormMapper formMapper;

    @Autowired
    private FormService formService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFormMockMvc;

    private Form form;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormResource formResource = new FormResource(formService);
        this.restFormMockMvc = MockMvcBuilders.standaloneSetup(formResource)
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
    public static Form createEntity(EntityManager em) {
        Form form = new Form()
            .responsavelPelaInformacao(DEFAULT_RESPONSAVEL_PELA_INFORMACAO)
            .telefone(DEFAULT_TELEFONE)
            .rua(DEFAULT_RUA)
            .numero(DEFAULT_NUMERO)
            .bairroSetor(DEFAULT_BAIRRO_SETOR)
            .localizacaoDoDomicilio(DEFAULT_LOCALIZACAO_DO_DOMICILIO)
            .situacaoDaResidencia(DEFAULT_SITUACAO_DA_RESIDENCIA)
            .numeroDaQuadra(DEFAULT_NUMERO_DA_QUADRA)
            .numeroDaCasa(DEFAULT_NUMERO_DA_CASA)
            .quantidadeDeResidentes(DEFAULT_QUANTIDADE_DE_RESIDENTES)
            .residenteFrenquentadoEscola(DEFAULT_RESIDENTE_FRENQUENTADO_ESCOLA)
            .privadoDeLiberdade(DEFAULT_PRIVADO_DE_LIBERDADE)
            .recebeAuxilioReclusao(DEFAULT_RECEBE_AUXILIO_RECLUSAO)
            .familiaVisitandoPreso(DEFAULT_FAMILIA_VISITANDO_PRESO)
            .cumprimentoSemiAberto(DEFAULT_CUMPRIMENTO_SEMI_ABERTO)
            .avaliacaoPoliciaMilitar(DEFAULT_AVALIACAO_POLICIA_MILITAR)
            .avaliacaoPoliciaCivel(DEFAULT_AVALIACAO_POLICIA_CIVEL)
            .naoAtendidoTelefonePolicia(DEFAULT_NAO_ATENDIDO_TELEFONE_POLICIA)
            .naoAtendidoDelegacia(DEFAULT_NAO_ATENDIDO_DELEGACIA)
            .avaliacaoAgenteSaude(DEFAULT_AVALIACAO_AGENTE_SAUDE)
            .avaliacaoUBS(DEFAULT_AVALIACAO_UBS);
        // Add required entity
        RendaFamiliar rendaFamiliar = RendaFamiliarResourceIntTest.createEntity(em);
        em.persist(rendaFamiliar);
        em.flush();
        form.setRendaFamiliar(rendaFamiliar);
        // Add required entity
        Periodicidade periodoVisitaAgente = PeriodicidadeResourceIntTest.createEntity(em);
        em.persist(periodoVisitaAgente);
        em.flush();
        form.setPeriodoVisitaAgente(periodoVisitaAgente);
        // Add required entity
        Religiao religiao = ReligiaoResourceIntTest.createEntity(em);
        em.persist(religiao);
        em.flush();
        form.setReligiao(religiao);
        // Add required entity
        ResponsavelLar responsavelLar = ResponsavelLarResourceIntTest.createEntity(em);
        em.persist(responsavelLar);
        em.flush();
        form.setResponsavelLar(responsavelLar);
        // Add required entity
        ServicoPublico servicoPublico = ServicoPublicoResourceIntTest.createEntity(em);
        em.persist(servicoPublico);
        em.flush();
        form.getServicoPublicos().add(servicoPublico);
        return form;
    }

    @Before
    public void initTest() {
        form = createEntity(em);
    }

    @Test
    @Transactional
    public void createForm() throws Exception {
        int databaseSizeBeforeCreate = formRepository.findAll().size();

        // Create the Form
        FormDTO formDTO = formMapper.toDto(form);
        restFormMockMvc.perform(post("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isCreated());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeCreate + 1);
        Form testForm = formList.get(formList.size() - 1);
        assertThat(testForm.getResponsavelPelaInformacao()).isEqualTo(DEFAULT_RESPONSAVEL_PELA_INFORMACAO);
        assertThat(testForm.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testForm.getRua()).isEqualTo(DEFAULT_RUA);
        assertThat(testForm.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testForm.getBairroSetor()).isEqualTo(DEFAULT_BAIRRO_SETOR);
        assertThat(testForm.getLocalizacaoDoDomicilio()).isEqualTo(DEFAULT_LOCALIZACAO_DO_DOMICILIO);
        assertThat(testForm.getSituacaoDaResidencia()).isEqualTo(DEFAULT_SITUACAO_DA_RESIDENCIA);
        assertThat(testForm.getNumeroDaQuadra()).isEqualTo(DEFAULT_NUMERO_DA_QUADRA);
        assertThat(testForm.getNumeroDaCasa()).isEqualTo(DEFAULT_NUMERO_DA_CASA);
        assertThat(testForm.getQuantidadeDeResidentes()).isEqualTo(DEFAULT_QUANTIDADE_DE_RESIDENTES);
        assertThat(testForm.getResidenteFrenquentadoEscola()).isEqualTo(DEFAULT_RESIDENTE_FRENQUENTADO_ESCOLA);
        assertThat(testForm.getPrivadoDeLiberdade()).isEqualTo(DEFAULT_PRIVADO_DE_LIBERDADE);
        assertThat(testForm.getRecebeAuxilioReclusao()).isEqualTo(DEFAULT_RECEBE_AUXILIO_RECLUSAO);
        assertThat(testForm.getFamiliaVisitandoPreso()).isEqualTo(DEFAULT_FAMILIA_VISITANDO_PRESO);
        assertThat(testForm.getCumprimentoSemiAberto()).isEqualTo(DEFAULT_CUMPRIMENTO_SEMI_ABERTO);
        assertThat(testForm.getAvaliacaoPoliciaMilitar()).isEqualTo(DEFAULT_AVALIACAO_POLICIA_MILITAR);
        assertThat(testForm.getAvaliacaoPoliciaCivel()).isEqualTo(DEFAULT_AVALIACAO_POLICIA_CIVEL);
        assertThat(testForm.getNaoAtendidoTelefonePolicia()).isEqualTo(DEFAULT_NAO_ATENDIDO_TELEFONE_POLICIA);
        assertThat(testForm.getNaoAtendidoDelegacia()).isEqualTo(DEFAULT_NAO_ATENDIDO_DELEGACIA);
        assertThat(testForm.getAvaliacaoAgenteSaude()).isEqualTo(DEFAULT_AVALIACAO_AGENTE_SAUDE);
        assertThat(testForm.getAvaliacaoUBS()).isEqualTo(DEFAULT_AVALIACAO_UBS);
    }

    @Test
    @Transactional
    public void createFormWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formRepository.findAll().size();

        // Create the Form with an existing ID
        form.setId(1L);
        FormDTO formDTO = formMapper.toDto(form);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormMockMvc.perform(post("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkResponsavelPelaInformacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = formRepository.findAll().size();
        // set the field null
        form.setResponsavelPelaInformacao(null);

        // Create the Form, which fails.
        FormDTO formDTO = formMapper.toDto(form);

        restFormMockMvc.perform(post("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isBadRequest());

        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRuaIsRequired() throws Exception {
        int databaseSizeBeforeTest = formRepository.findAll().size();
        // set the field null
        form.setRua(null);

        // Create the Form, which fails.
        FormDTO formDTO = formMapper.toDto(form);

        restFormMockMvc.perform(post("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isBadRequest());

        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocalizacaoDoDomicilioIsRequired() throws Exception {
        int databaseSizeBeforeTest = formRepository.findAll().size();
        // set the field null
        form.setLocalizacaoDoDomicilio(null);

        // Create the Form, which fails.
        FormDTO formDTO = formMapper.toDto(form);

        restFormMockMvc.perform(post("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isBadRequest());

        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSituacaoDaResidenciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = formRepository.findAll().size();
        // set the field null
        form.setSituacaoDaResidencia(null);

        // Create the Form, which fails.
        FormDTO formDTO = formMapper.toDto(form);

        restFormMockMvc.perform(post("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isBadRequest());

        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantidadeDeResidentesIsRequired() throws Exception {
        int databaseSizeBeforeTest = formRepository.findAll().size();
        // set the field null
        form.setQuantidadeDeResidentes(null);

        // Create the Form, which fails.
        FormDTO formDTO = formMapper.toDto(form);

        restFormMockMvc.perform(post("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isBadRequest());

        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResidenteFrenquentadoEscolaIsRequired() throws Exception {
        int databaseSizeBeforeTest = formRepository.findAll().size();
        // set the field null
        form.setResidenteFrenquentadoEscola(null);

        // Create the Form, which fails.
        FormDTO formDTO = formMapper.toDto(form);

        restFormMockMvc.perform(post("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isBadRequest());

        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrivadoDeLiberdadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = formRepository.findAll().size();
        // set the field null
        form.setPrivadoDeLiberdade(null);

        // Create the Form, which fails.
        FormDTO formDTO = formMapper.toDto(form);

        restFormMockMvc.perform(post("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isBadRequest());

        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAvaliacaoPoliciaMilitarIsRequired() throws Exception {
        int databaseSizeBeforeTest = formRepository.findAll().size();
        // set the field null
        form.setAvaliacaoPoliciaMilitar(null);

        // Create the Form, which fails.
        FormDTO formDTO = formMapper.toDto(form);

        restFormMockMvc.perform(post("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isBadRequest());

        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAvaliacaoPoliciaCivelIsRequired() throws Exception {
        int databaseSizeBeforeTest = formRepository.findAll().size();
        // set the field null
        form.setAvaliacaoPoliciaCivel(null);

        // Create the Form, which fails.
        FormDTO formDTO = formMapper.toDto(form);

        restFormMockMvc.perform(post("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isBadRequest());

        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNaoAtendidoTelefonePoliciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = formRepository.findAll().size();
        // set the field null
        form.setNaoAtendidoTelefonePolicia(null);

        // Create the Form, which fails.
        FormDTO formDTO = formMapper.toDto(form);

        restFormMockMvc.perform(post("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isBadRequest());

        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNaoAtendidoDelegaciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = formRepository.findAll().size();
        // set the field null
        form.setNaoAtendidoDelegacia(null);

        // Create the Form, which fails.
        FormDTO formDTO = formMapper.toDto(form);

        restFormMockMvc.perform(post("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isBadRequest());

        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllForms() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get all the formList
        restFormMockMvc.perform(get("/api/forms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(form.getId().intValue())))
            .andExpect(jsonPath("$.[*].responsavelPelaInformacao").value(hasItem(DEFAULT_RESPONSAVEL_PELA_INFORMACAO.toString())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())))
            .andExpect(jsonPath("$.[*].rua").value(hasItem(DEFAULT_RUA.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].bairroSetor").value(hasItem(DEFAULT_BAIRRO_SETOR.toString())))
            .andExpect(jsonPath("$.[*].localizacaoDoDomicilio").value(hasItem(DEFAULT_LOCALIZACAO_DO_DOMICILIO.toString())))
            .andExpect(jsonPath("$.[*].situacaoDaResidencia").value(hasItem(DEFAULT_SITUACAO_DA_RESIDENCIA.toString())))
            .andExpect(jsonPath("$.[*].numeroDaQuadra").value(hasItem(DEFAULT_NUMERO_DA_QUADRA.toString())))
            .andExpect(jsonPath("$.[*].numeroDaCasa").value(hasItem(DEFAULT_NUMERO_DA_CASA.toString())))
            .andExpect(jsonPath("$.[*].quantidadeDeResidentes").value(hasItem(DEFAULT_QUANTIDADE_DE_RESIDENTES)))
            .andExpect(jsonPath("$.[*].residenteFrenquentadoEscola").value(hasItem(DEFAULT_RESIDENTE_FRENQUENTADO_ESCOLA.toString())))
            .andExpect(jsonPath("$.[*].privadoDeLiberdade").value(hasItem(DEFAULT_PRIVADO_DE_LIBERDADE.toString())))
            .andExpect(jsonPath("$.[*].recebeAuxilioReclusao").value(hasItem(DEFAULT_RECEBE_AUXILIO_RECLUSAO.toString())))
            .andExpect(jsonPath("$.[*].familiaVisitandoPreso").value(hasItem(DEFAULT_FAMILIA_VISITANDO_PRESO.toString())))
            .andExpect(jsonPath("$.[*].cumprimentoSemiAberto").value(hasItem(DEFAULT_CUMPRIMENTO_SEMI_ABERTO.toString())))
            .andExpect(jsonPath("$.[*].avaliacaoPoliciaMilitar").value(hasItem(DEFAULT_AVALIACAO_POLICIA_MILITAR.toString())))
            .andExpect(jsonPath("$.[*].avaliacaoPoliciaCivel").value(hasItem(DEFAULT_AVALIACAO_POLICIA_CIVEL.toString())))
            .andExpect(jsonPath("$.[*].naoAtendidoTelefonePolicia").value(hasItem(DEFAULT_NAO_ATENDIDO_TELEFONE_POLICIA.toString())))
            .andExpect(jsonPath("$.[*].naoAtendidoDelegacia").value(hasItem(DEFAULT_NAO_ATENDIDO_DELEGACIA.toString())))
            .andExpect(jsonPath("$.[*].avaliacaoAgenteSaude").value(hasItem(DEFAULT_AVALIACAO_AGENTE_SAUDE.toString())))
            .andExpect(jsonPath("$.[*].avaliacaoUBS").value(hasItem(DEFAULT_AVALIACAO_UBS.toString())));
    }

    @Test
    @Transactional
    public void getForm() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get the form
        restFormMockMvc.perform(get("/api/forms/{id}", form.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(form.getId().intValue()))
            .andExpect(jsonPath("$.responsavelPelaInformacao").value(DEFAULT_RESPONSAVEL_PELA_INFORMACAO.toString()))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE.toString()))
            .andExpect(jsonPath("$.rua").value(DEFAULT_RUA.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.bairroSetor").value(DEFAULT_BAIRRO_SETOR.toString()))
            .andExpect(jsonPath("$.localizacaoDoDomicilio").value(DEFAULT_LOCALIZACAO_DO_DOMICILIO.toString()))
            .andExpect(jsonPath("$.situacaoDaResidencia").value(DEFAULT_SITUACAO_DA_RESIDENCIA.toString()))
            .andExpect(jsonPath("$.numeroDaQuadra").value(DEFAULT_NUMERO_DA_QUADRA.toString()))
            .andExpect(jsonPath("$.numeroDaCasa").value(DEFAULT_NUMERO_DA_CASA.toString()))
            .andExpect(jsonPath("$.quantidadeDeResidentes").value(DEFAULT_QUANTIDADE_DE_RESIDENTES))
            .andExpect(jsonPath("$.residenteFrenquentadoEscola").value(DEFAULT_RESIDENTE_FRENQUENTADO_ESCOLA.toString()))
            .andExpect(jsonPath("$.privadoDeLiberdade").value(DEFAULT_PRIVADO_DE_LIBERDADE.toString()))
            .andExpect(jsonPath("$.recebeAuxilioReclusao").value(DEFAULT_RECEBE_AUXILIO_RECLUSAO.toString()))
            .andExpect(jsonPath("$.familiaVisitandoPreso").value(DEFAULT_FAMILIA_VISITANDO_PRESO.toString()))
            .andExpect(jsonPath("$.cumprimentoSemiAberto").value(DEFAULT_CUMPRIMENTO_SEMI_ABERTO.toString()))
            .andExpect(jsonPath("$.avaliacaoPoliciaMilitar").value(DEFAULT_AVALIACAO_POLICIA_MILITAR.toString()))
            .andExpect(jsonPath("$.avaliacaoPoliciaCivel").value(DEFAULT_AVALIACAO_POLICIA_CIVEL.toString()))
            .andExpect(jsonPath("$.naoAtendidoTelefonePolicia").value(DEFAULT_NAO_ATENDIDO_TELEFONE_POLICIA.toString()))
            .andExpect(jsonPath("$.naoAtendidoDelegacia").value(DEFAULT_NAO_ATENDIDO_DELEGACIA.toString()))
            .andExpect(jsonPath("$.avaliacaoAgenteSaude").value(DEFAULT_AVALIACAO_AGENTE_SAUDE.toString()))
            .andExpect(jsonPath("$.avaliacaoUBS").value(DEFAULT_AVALIACAO_UBS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingForm() throws Exception {
        // Get the form
        restFormMockMvc.perform(get("/api/forms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateForm() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);
        int databaseSizeBeforeUpdate = formRepository.findAll().size();

        // Update the form
        Form updatedForm = formRepository.findOne(form.getId());
        // Disconnect from session so that the updates on updatedForm are not directly saved in db
        em.detach(updatedForm);
        updatedForm
            .responsavelPelaInformacao(UPDATED_RESPONSAVEL_PELA_INFORMACAO)
            .telefone(UPDATED_TELEFONE)
            .rua(UPDATED_RUA)
            .numero(UPDATED_NUMERO)
            .bairroSetor(UPDATED_BAIRRO_SETOR)
            .localizacaoDoDomicilio(UPDATED_LOCALIZACAO_DO_DOMICILIO)
            .situacaoDaResidencia(UPDATED_SITUACAO_DA_RESIDENCIA)
            .numeroDaQuadra(UPDATED_NUMERO_DA_QUADRA)
            .numeroDaCasa(UPDATED_NUMERO_DA_CASA)
            .quantidadeDeResidentes(UPDATED_QUANTIDADE_DE_RESIDENTES)
            .residenteFrenquentadoEscola(UPDATED_RESIDENTE_FRENQUENTADO_ESCOLA)
            .privadoDeLiberdade(UPDATED_PRIVADO_DE_LIBERDADE)
            .recebeAuxilioReclusao(UPDATED_RECEBE_AUXILIO_RECLUSAO)
            .familiaVisitandoPreso(UPDATED_FAMILIA_VISITANDO_PRESO)
            .cumprimentoSemiAberto(UPDATED_CUMPRIMENTO_SEMI_ABERTO)
            .avaliacaoPoliciaMilitar(UPDATED_AVALIACAO_POLICIA_MILITAR)
            .avaliacaoPoliciaCivel(UPDATED_AVALIACAO_POLICIA_CIVEL)
            .naoAtendidoTelefonePolicia(UPDATED_NAO_ATENDIDO_TELEFONE_POLICIA)
            .naoAtendidoDelegacia(UPDATED_NAO_ATENDIDO_DELEGACIA)
            .avaliacaoAgenteSaude(UPDATED_AVALIACAO_AGENTE_SAUDE)
            .avaliacaoUBS(UPDATED_AVALIACAO_UBS);
        FormDTO formDTO = formMapper.toDto(updatedForm);

        restFormMockMvc.perform(put("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isOk());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeUpdate);
        Form testForm = formList.get(formList.size() - 1);
        assertThat(testForm.getResponsavelPelaInformacao()).isEqualTo(UPDATED_RESPONSAVEL_PELA_INFORMACAO);
        assertThat(testForm.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testForm.getRua()).isEqualTo(UPDATED_RUA);
        assertThat(testForm.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testForm.getBairroSetor()).isEqualTo(UPDATED_BAIRRO_SETOR);
        assertThat(testForm.getLocalizacaoDoDomicilio()).isEqualTo(UPDATED_LOCALIZACAO_DO_DOMICILIO);
        assertThat(testForm.getSituacaoDaResidencia()).isEqualTo(UPDATED_SITUACAO_DA_RESIDENCIA);
        assertThat(testForm.getNumeroDaQuadra()).isEqualTo(UPDATED_NUMERO_DA_QUADRA);
        assertThat(testForm.getNumeroDaCasa()).isEqualTo(UPDATED_NUMERO_DA_CASA);
        assertThat(testForm.getQuantidadeDeResidentes()).isEqualTo(UPDATED_QUANTIDADE_DE_RESIDENTES);
        assertThat(testForm.getResidenteFrenquentadoEscola()).isEqualTo(UPDATED_RESIDENTE_FRENQUENTADO_ESCOLA);
        assertThat(testForm.getPrivadoDeLiberdade()).isEqualTo(UPDATED_PRIVADO_DE_LIBERDADE);
        assertThat(testForm.getRecebeAuxilioReclusao()).isEqualTo(UPDATED_RECEBE_AUXILIO_RECLUSAO);
        assertThat(testForm.getFamiliaVisitandoPreso()).isEqualTo(UPDATED_FAMILIA_VISITANDO_PRESO);
        assertThat(testForm.getCumprimentoSemiAberto()).isEqualTo(UPDATED_CUMPRIMENTO_SEMI_ABERTO);
        assertThat(testForm.getAvaliacaoPoliciaMilitar()).isEqualTo(UPDATED_AVALIACAO_POLICIA_MILITAR);
        assertThat(testForm.getAvaliacaoPoliciaCivel()).isEqualTo(UPDATED_AVALIACAO_POLICIA_CIVEL);
        assertThat(testForm.getNaoAtendidoTelefonePolicia()).isEqualTo(UPDATED_NAO_ATENDIDO_TELEFONE_POLICIA);
        assertThat(testForm.getNaoAtendidoDelegacia()).isEqualTo(UPDATED_NAO_ATENDIDO_DELEGACIA);
        assertThat(testForm.getAvaliacaoAgenteSaude()).isEqualTo(UPDATED_AVALIACAO_AGENTE_SAUDE);
        assertThat(testForm.getAvaliacaoUBS()).isEqualTo(UPDATED_AVALIACAO_UBS);
    }

    @Test
    @Transactional
    public void updateNonExistingForm() throws Exception {
        int databaseSizeBeforeUpdate = formRepository.findAll().size();

        // Create the Form
        FormDTO formDTO = formMapper.toDto(form);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFormMockMvc.perform(put("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formDTO)))
            .andExpect(status().isCreated());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteForm() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);
        int databaseSizeBeforeDelete = formRepository.findAll().size();

        // Get the form
        restFormMockMvc.perform(delete("/api/forms/{id}", form.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Form.class);
        Form form1 = new Form();
        form1.setId(1L);
        Form form2 = new Form();
        form2.setId(form1.getId());
        assertThat(form1).isEqualTo(form2);
        form2.setId(2L);
        assertThat(form1).isNotEqualTo(form2);
        form1.setId(null);
        assertThat(form1).isNotEqualTo(form2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormDTO.class);
        FormDTO formDTO1 = new FormDTO();
        formDTO1.setId(1L);
        FormDTO formDTO2 = new FormDTO();
        assertThat(formDTO1).isNotEqualTo(formDTO2);
        formDTO2.setId(formDTO1.getId());
        assertThat(formDTO1).isEqualTo(formDTO2);
        formDTO2.setId(2L);
        assertThat(formDTO1).isNotEqualTo(formDTO2);
        formDTO1.setId(null);
        assertThat(formDTO1).isNotEqualTo(formDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(formMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(formMapper.fromId(null)).isNull();
    }
}
