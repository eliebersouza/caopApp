package br.mp.mpro.caopapp.web.rest;

import br.mp.mpro.caopapp.CaopApp;

import br.mp.mpro.caopapp.domain.FaixaEtariaForm;
import br.mp.mpro.caopapp.domain.Form;
import br.mp.mpro.caopapp.domain.FaixaEtaria;
import br.mp.mpro.caopapp.repository.FaixaEtariaFormRepository;
import br.mp.mpro.caopapp.service.FaixaEtariaFormService;
import br.mp.mpro.caopapp.service.dto.FaixaEtariaFormDTO;
import br.mp.mpro.caopapp.service.mapper.FaixaEtariaFormMapper;
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
 * Test class for the FaixaEtariaFormResource REST controller.
 *
 * @see FaixaEtariaFormResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaopApp.class)
public class FaixaEtariaFormResourceIntTest {

    private static final Integer DEFAULT_QUANTIDADE_FAIXA = 1;
    private static final Integer UPDATED_QUANTIDADE_FAIXA = 2;

    @Autowired
    private FaixaEtariaFormRepository faixaEtariaFormRepository;

    @Autowired
    private FaixaEtariaFormMapper faixaEtariaFormMapper;

    @Autowired
    private FaixaEtariaFormService faixaEtariaFormService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFaixaEtariaFormMockMvc;

    private FaixaEtariaForm faixaEtariaForm;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FaixaEtariaFormResource faixaEtariaFormResource = new FaixaEtariaFormResource(faixaEtariaFormService);
        this.restFaixaEtariaFormMockMvc = MockMvcBuilders.standaloneSetup(faixaEtariaFormResource)
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
    public static FaixaEtariaForm createEntity(EntityManager em) {
        FaixaEtariaForm faixaEtariaForm = new FaixaEtariaForm()
            .quantidadeFaixa(DEFAULT_QUANTIDADE_FAIXA);
        // Add required entity
        Form form = FormResourceIntTest.createEntity(em);
        em.persist(form);
        em.flush();
        faixaEtariaForm.setForm(form);
        // Add required entity
        FaixaEtaria faixaEtaria = FaixaEtariaResourceIntTest.createEntity(em);
        em.persist(faixaEtaria);
        em.flush();
        faixaEtariaForm.setFaixaEtaria(faixaEtaria);
        return faixaEtariaForm;
    }

    @Before
    public void initTest() {
        faixaEtariaForm = createEntity(em);
    }

    @Test
    @Transactional
    public void createFaixaEtariaForm() throws Exception {
        int databaseSizeBeforeCreate = faixaEtariaFormRepository.findAll().size();

        // Create the FaixaEtariaForm
        FaixaEtariaFormDTO faixaEtariaFormDTO = faixaEtariaFormMapper.toDto(faixaEtariaForm);
        restFaixaEtariaFormMockMvc.perform(post("/api/faixa-etaria-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faixaEtariaFormDTO)))
            .andExpect(status().isCreated());

        // Validate the FaixaEtariaForm in the database
        List<FaixaEtariaForm> faixaEtariaFormList = faixaEtariaFormRepository.findAll();
        assertThat(faixaEtariaFormList).hasSize(databaseSizeBeforeCreate + 1);
        FaixaEtariaForm testFaixaEtariaForm = faixaEtariaFormList.get(faixaEtariaFormList.size() - 1);
        assertThat(testFaixaEtariaForm.getQuantidadeFaixa()).isEqualTo(DEFAULT_QUANTIDADE_FAIXA);
    }

    @Test
    @Transactional
    public void createFaixaEtariaFormWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = faixaEtariaFormRepository.findAll().size();

        // Create the FaixaEtariaForm with an existing ID
        faixaEtariaForm.setId(1L);
        FaixaEtariaFormDTO faixaEtariaFormDTO = faixaEtariaFormMapper.toDto(faixaEtariaForm);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFaixaEtariaFormMockMvc.perform(post("/api/faixa-etaria-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faixaEtariaFormDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FaixaEtariaForm in the database
        List<FaixaEtariaForm> faixaEtariaFormList = faixaEtariaFormRepository.findAll();
        assertThat(faixaEtariaFormList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkQuantidadeFaixaIsRequired() throws Exception {
        int databaseSizeBeforeTest = faixaEtariaFormRepository.findAll().size();
        // set the field null
        faixaEtariaForm.setQuantidadeFaixa(null);

        // Create the FaixaEtariaForm, which fails.
        FaixaEtariaFormDTO faixaEtariaFormDTO = faixaEtariaFormMapper.toDto(faixaEtariaForm);

        restFaixaEtariaFormMockMvc.perform(post("/api/faixa-etaria-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faixaEtariaFormDTO)))
            .andExpect(status().isBadRequest());

        List<FaixaEtariaForm> faixaEtariaFormList = faixaEtariaFormRepository.findAll();
        assertThat(faixaEtariaFormList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFaixaEtariaForms() throws Exception {
        // Initialize the database
        faixaEtariaFormRepository.saveAndFlush(faixaEtariaForm);

        // Get all the faixaEtariaFormList
        restFaixaEtariaFormMockMvc.perform(get("/api/faixa-etaria-forms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(faixaEtariaForm.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidadeFaixa").value(hasItem(DEFAULT_QUANTIDADE_FAIXA)));
    }

    @Test
    @Transactional
    public void getFaixaEtariaForm() throws Exception {
        // Initialize the database
        faixaEtariaFormRepository.saveAndFlush(faixaEtariaForm);

        // Get the faixaEtariaForm
        restFaixaEtariaFormMockMvc.perform(get("/api/faixa-etaria-forms/{id}", faixaEtariaForm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(faixaEtariaForm.getId().intValue()))
            .andExpect(jsonPath("$.quantidadeFaixa").value(DEFAULT_QUANTIDADE_FAIXA));
    }

    @Test
    @Transactional
    public void getNonExistingFaixaEtariaForm() throws Exception {
        // Get the faixaEtariaForm
        restFaixaEtariaFormMockMvc.perform(get("/api/faixa-etaria-forms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFaixaEtariaForm() throws Exception {
        // Initialize the database
        faixaEtariaFormRepository.saveAndFlush(faixaEtariaForm);
        int databaseSizeBeforeUpdate = faixaEtariaFormRepository.findAll().size();

        // Update the faixaEtariaForm
        FaixaEtariaForm updatedFaixaEtariaForm = faixaEtariaFormRepository.findOne(faixaEtariaForm.getId());
        // Disconnect from session so that the updates on updatedFaixaEtariaForm are not directly saved in db
        em.detach(updatedFaixaEtariaForm);
        updatedFaixaEtariaForm
            .quantidadeFaixa(UPDATED_QUANTIDADE_FAIXA);
        FaixaEtariaFormDTO faixaEtariaFormDTO = faixaEtariaFormMapper.toDto(updatedFaixaEtariaForm);

        restFaixaEtariaFormMockMvc.perform(put("/api/faixa-etaria-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faixaEtariaFormDTO)))
            .andExpect(status().isOk());

        // Validate the FaixaEtariaForm in the database
        List<FaixaEtariaForm> faixaEtariaFormList = faixaEtariaFormRepository.findAll();
        assertThat(faixaEtariaFormList).hasSize(databaseSizeBeforeUpdate);
        FaixaEtariaForm testFaixaEtariaForm = faixaEtariaFormList.get(faixaEtariaFormList.size() - 1);
        assertThat(testFaixaEtariaForm.getQuantidadeFaixa()).isEqualTo(UPDATED_QUANTIDADE_FAIXA);
    }

    @Test
    @Transactional
    public void updateNonExistingFaixaEtariaForm() throws Exception {
        int databaseSizeBeforeUpdate = faixaEtariaFormRepository.findAll().size();

        // Create the FaixaEtariaForm
        FaixaEtariaFormDTO faixaEtariaFormDTO = faixaEtariaFormMapper.toDto(faixaEtariaForm);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFaixaEtariaFormMockMvc.perform(put("/api/faixa-etaria-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faixaEtariaFormDTO)))
            .andExpect(status().isCreated());

        // Validate the FaixaEtariaForm in the database
        List<FaixaEtariaForm> faixaEtariaFormList = faixaEtariaFormRepository.findAll();
        assertThat(faixaEtariaFormList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFaixaEtariaForm() throws Exception {
        // Initialize the database
        faixaEtariaFormRepository.saveAndFlush(faixaEtariaForm);
        int databaseSizeBeforeDelete = faixaEtariaFormRepository.findAll().size();

        // Get the faixaEtariaForm
        restFaixaEtariaFormMockMvc.perform(delete("/api/faixa-etaria-forms/{id}", faixaEtariaForm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FaixaEtariaForm> faixaEtariaFormList = faixaEtariaFormRepository.findAll();
        assertThat(faixaEtariaFormList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FaixaEtariaForm.class);
        FaixaEtariaForm faixaEtariaForm1 = new FaixaEtariaForm();
        faixaEtariaForm1.setId(1L);
        FaixaEtariaForm faixaEtariaForm2 = new FaixaEtariaForm();
        faixaEtariaForm2.setId(faixaEtariaForm1.getId());
        assertThat(faixaEtariaForm1).isEqualTo(faixaEtariaForm2);
        faixaEtariaForm2.setId(2L);
        assertThat(faixaEtariaForm1).isNotEqualTo(faixaEtariaForm2);
        faixaEtariaForm1.setId(null);
        assertThat(faixaEtariaForm1).isNotEqualTo(faixaEtariaForm2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FaixaEtariaFormDTO.class);
        FaixaEtariaFormDTO faixaEtariaFormDTO1 = new FaixaEtariaFormDTO();
        faixaEtariaFormDTO1.setId(1L);
        FaixaEtariaFormDTO faixaEtariaFormDTO2 = new FaixaEtariaFormDTO();
        assertThat(faixaEtariaFormDTO1).isNotEqualTo(faixaEtariaFormDTO2);
        faixaEtariaFormDTO2.setId(faixaEtariaFormDTO1.getId());
        assertThat(faixaEtariaFormDTO1).isEqualTo(faixaEtariaFormDTO2);
        faixaEtariaFormDTO2.setId(2L);
        assertThat(faixaEtariaFormDTO1).isNotEqualTo(faixaEtariaFormDTO2);
        faixaEtariaFormDTO1.setId(null);
        assertThat(faixaEtariaFormDTO1).isNotEqualTo(faixaEtariaFormDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(faixaEtariaFormMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(faixaEtariaFormMapper.fromId(null)).isNull();
    }
}
