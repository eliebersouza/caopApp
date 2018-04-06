package br.mp.mpro.caopapp.web.rest;

import br.mp.mpro.caopapp.CaopApp;

import br.mp.mpro.caopapp.domain.CicloEscolarForm;
import br.mp.mpro.caopapp.domain.CicloEscolar;
import br.mp.mpro.caopapp.domain.Form;
import br.mp.mpro.caopapp.repository.CicloEscolarFormRepository;
import br.mp.mpro.caopapp.service.CicloEscolarFormService;
import br.mp.mpro.caopapp.service.dto.CicloEscolarFormDTO;
import br.mp.mpro.caopapp.service.mapper.CicloEscolarFormMapper;
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
 * Test class for the CicloEscolarFormResource REST controller.
 *
 * @see CicloEscolarFormResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaopApp.class)
public class CicloEscolarFormResourceIntTest {

    private static final Integer DEFAULT_IDADE_RESIDENTE = 1;
    private static final Integer UPDATED_IDADE_RESIDENTE = 2;

    @Autowired
    private CicloEscolarFormRepository cicloEscolarFormRepository;

    @Autowired
    private CicloEscolarFormMapper cicloEscolarFormMapper;

    @Autowired
    private CicloEscolarFormService cicloEscolarFormService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCicloEscolarFormMockMvc;

    private CicloEscolarForm cicloEscolarForm;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CicloEscolarFormResource cicloEscolarFormResource = new CicloEscolarFormResource(cicloEscolarFormService);
        this.restCicloEscolarFormMockMvc = MockMvcBuilders.standaloneSetup(cicloEscolarFormResource)
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
    public static CicloEscolarForm createEntity(EntityManager em) {
        CicloEscolarForm cicloEscolarForm = new CicloEscolarForm()
            .idadeResidente(DEFAULT_IDADE_RESIDENTE);
        // Add required entity
        CicloEscolar cicloEscolar = CicloEscolarResourceIntTest.createEntity(em);
        em.persist(cicloEscolar);
        em.flush();
        cicloEscolarForm.setCicloEscolar(cicloEscolar);
        // Add required entity
        Form form = FormResourceIntTest.createEntity(em);
        em.persist(form);
        em.flush();
        cicloEscolarForm.setForm(form);
        return cicloEscolarForm;
    }

    @Before
    public void initTest() {
        cicloEscolarForm = createEntity(em);
    }

    @Test
    @Transactional
    public void createCicloEscolarForm() throws Exception {
        int databaseSizeBeforeCreate = cicloEscolarFormRepository.findAll().size();

        // Create the CicloEscolarForm
        CicloEscolarFormDTO cicloEscolarFormDTO = cicloEscolarFormMapper.toDto(cicloEscolarForm);
        restCicloEscolarFormMockMvc.perform(post("/api/ciclo-escolar-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cicloEscolarFormDTO)))
            .andExpect(status().isCreated());

        // Validate the CicloEscolarForm in the database
        List<CicloEscolarForm> cicloEscolarFormList = cicloEscolarFormRepository.findAll();
        assertThat(cicloEscolarFormList).hasSize(databaseSizeBeforeCreate + 1);
        CicloEscolarForm testCicloEscolarForm = cicloEscolarFormList.get(cicloEscolarFormList.size() - 1);
        assertThat(testCicloEscolarForm.getIdadeResidente()).isEqualTo(DEFAULT_IDADE_RESIDENTE);
    }

    @Test
    @Transactional
    public void createCicloEscolarFormWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cicloEscolarFormRepository.findAll().size();

        // Create the CicloEscolarForm with an existing ID
        cicloEscolarForm.setId(1L);
        CicloEscolarFormDTO cicloEscolarFormDTO = cicloEscolarFormMapper.toDto(cicloEscolarForm);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCicloEscolarFormMockMvc.perform(post("/api/ciclo-escolar-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cicloEscolarFormDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CicloEscolarForm in the database
        List<CicloEscolarForm> cicloEscolarFormList = cicloEscolarFormRepository.findAll();
        assertThat(cicloEscolarFormList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdadeResidenteIsRequired() throws Exception {
        int databaseSizeBeforeTest = cicloEscolarFormRepository.findAll().size();
        // set the field null
        cicloEscolarForm.setIdadeResidente(null);

        // Create the CicloEscolarForm, which fails.
        CicloEscolarFormDTO cicloEscolarFormDTO = cicloEscolarFormMapper.toDto(cicloEscolarForm);

        restCicloEscolarFormMockMvc.perform(post("/api/ciclo-escolar-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cicloEscolarFormDTO)))
            .andExpect(status().isBadRequest());

        List<CicloEscolarForm> cicloEscolarFormList = cicloEscolarFormRepository.findAll();
        assertThat(cicloEscolarFormList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCicloEscolarForms() throws Exception {
        // Initialize the database
        cicloEscolarFormRepository.saveAndFlush(cicloEscolarForm);

        // Get all the cicloEscolarFormList
        restCicloEscolarFormMockMvc.perform(get("/api/ciclo-escolar-forms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cicloEscolarForm.getId().intValue())))
            .andExpect(jsonPath("$.[*].idadeResidente").value(hasItem(DEFAULT_IDADE_RESIDENTE)));
    }

    @Test
    @Transactional
    public void getCicloEscolarForm() throws Exception {
        // Initialize the database
        cicloEscolarFormRepository.saveAndFlush(cicloEscolarForm);

        // Get the cicloEscolarForm
        restCicloEscolarFormMockMvc.perform(get("/api/ciclo-escolar-forms/{id}", cicloEscolarForm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cicloEscolarForm.getId().intValue()))
            .andExpect(jsonPath("$.idadeResidente").value(DEFAULT_IDADE_RESIDENTE));
    }

    @Test
    @Transactional
    public void getNonExistingCicloEscolarForm() throws Exception {
        // Get the cicloEscolarForm
        restCicloEscolarFormMockMvc.perform(get("/api/ciclo-escolar-forms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCicloEscolarForm() throws Exception {
        // Initialize the database
        cicloEscolarFormRepository.saveAndFlush(cicloEscolarForm);
        int databaseSizeBeforeUpdate = cicloEscolarFormRepository.findAll().size();

        // Update the cicloEscolarForm
        CicloEscolarForm updatedCicloEscolarForm = cicloEscolarFormRepository.findOne(cicloEscolarForm.getId());
        // Disconnect from session so that the updates on updatedCicloEscolarForm are not directly saved in db
        em.detach(updatedCicloEscolarForm);
        updatedCicloEscolarForm
            .idadeResidente(UPDATED_IDADE_RESIDENTE);
        CicloEscolarFormDTO cicloEscolarFormDTO = cicloEscolarFormMapper.toDto(updatedCicloEscolarForm);

        restCicloEscolarFormMockMvc.perform(put("/api/ciclo-escolar-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cicloEscolarFormDTO)))
            .andExpect(status().isOk());

        // Validate the CicloEscolarForm in the database
        List<CicloEscolarForm> cicloEscolarFormList = cicloEscolarFormRepository.findAll();
        assertThat(cicloEscolarFormList).hasSize(databaseSizeBeforeUpdate);
        CicloEscolarForm testCicloEscolarForm = cicloEscolarFormList.get(cicloEscolarFormList.size() - 1);
        assertThat(testCicloEscolarForm.getIdadeResidente()).isEqualTo(UPDATED_IDADE_RESIDENTE);
    }

    @Test
    @Transactional
    public void updateNonExistingCicloEscolarForm() throws Exception {
        int databaseSizeBeforeUpdate = cicloEscolarFormRepository.findAll().size();

        // Create the CicloEscolarForm
        CicloEscolarFormDTO cicloEscolarFormDTO = cicloEscolarFormMapper.toDto(cicloEscolarForm);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCicloEscolarFormMockMvc.perform(put("/api/ciclo-escolar-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cicloEscolarFormDTO)))
            .andExpect(status().isCreated());

        // Validate the CicloEscolarForm in the database
        List<CicloEscolarForm> cicloEscolarFormList = cicloEscolarFormRepository.findAll();
        assertThat(cicloEscolarFormList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCicloEscolarForm() throws Exception {
        // Initialize the database
        cicloEscolarFormRepository.saveAndFlush(cicloEscolarForm);
        int databaseSizeBeforeDelete = cicloEscolarFormRepository.findAll().size();

        // Get the cicloEscolarForm
        restCicloEscolarFormMockMvc.perform(delete("/api/ciclo-escolar-forms/{id}", cicloEscolarForm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CicloEscolarForm> cicloEscolarFormList = cicloEscolarFormRepository.findAll();
        assertThat(cicloEscolarFormList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CicloEscolarForm.class);
        CicloEscolarForm cicloEscolarForm1 = new CicloEscolarForm();
        cicloEscolarForm1.setId(1L);
        CicloEscolarForm cicloEscolarForm2 = new CicloEscolarForm();
        cicloEscolarForm2.setId(cicloEscolarForm1.getId());
        assertThat(cicloEscolarForm1).isEqualTo(cicloEscolarForm2);
        cicloEscolarForm2.setId(2L);
        assertThat(cicloEscolarForm1).isNotEqualTo(cicloEscolarForm2);
        cicloEscolarForm1.setId(null);
        assertThat(cicloEscolarForm1).isNotEqualTo(cicloEscolarForm2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CicloEscolarFormDTO.class);
        CicloEscolarFormDTO cicloEscolarFormDTO1 = new CicloEscolarFormDTO();
        cicloEscolarFormDTO1.setId(1L);
        CicloEscolarFormDTO cicloEscolarFormDTO2 = new CicloEscolarFormDTO();
        assertThat(cicloEscolarFormDTO1).isNotEqualTo(cicloEscolarFormDTO2);
        cicloEscolarFormDTO2.setId(cicloEscolarFormDTO1.getId());
        assertThat(cicloEscolarFormDTO1).isEqualTo(cicloEscolarFormDTO2);
        cicloEscolarFormDTO2.setId(2L);
        assertThat(cicloEscolarFormDTO1).isNotEqualTo(cicloEscolarFormDTO2);
        cicloEscolarFormDTO1.setId(null);
        assertThat(cicloEscolarFormDTO1).isNotEqualTo(cicloEscolarFormDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cicloEscolarFormMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cicloEscolarFormMapper.fromId(null)).isNull();
    }
}
