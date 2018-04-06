package br.mp.mpro.caopapp.web.rest;

import br.mp.mpro.caopapp.CaopApp;

import br.mp.mpro.caopapp.domain.ResponsavelLar;
import br.mp.mpro.caopapp.repository.ResponsavelLarRepository;
import br.mp.mpro.caopapp.service.ResponsavelLarService;
import br.mp.mpro.caopapp.service.dto.ResponsavelLarDTO;
import br.mp.mpro.caopapp.service.mapper.ResponsavelLarMapper;
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
 * Test class for the ResponsavelLarResource REST controller.
 *
 * @see ResponsavelLarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaopApp.class)
public class ResponsavelLarResourceIntTest {

    private static final String DEFAULT_RESPONSAVEL = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSAVEL = "BBBBBBBBBB";

    @Autowired
    private ResponsavelLarRepository responsavelLarRepository;

    @Autowired
    private ResponsavelLarMapper responsavelLarMapper;

    @Autowired
    private ResponsavelLarService responsavelLarService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restResponsavelLarMockMvc;

    private ResponsavelLar responsavelLar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResponsavelLarResource responsavelLarResource = new ResponsavelLarResource(responsavelLarService);
        this.restResponsavelLarMockMvc = MockMvcBuilders.standaloneSetup(responsavelLarResource)
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
    public static ResponsavelLar createEntity(EntityManager em) {
        ResponsavelLar responsavelLar = new ResponsavelLar()
            .responsavel(DEFAULT_RESPONSAVEL);
        return responsavelLar;
    }

    @Before
    public void initTest() {
        responsavelLar = createEntity(em);
    }

    @Test
    @Transactional
    public void createResponsavelLar() throws Exception {
        int databaseSizeBeforeCreate = responsavelLarRepository.findAll().size();

        // Create the ResponsavelLar
        ResponsavelLarDTO responsavelLarDTO = responsavelLarMapper.toDto(responsavelLar);
        restResponsavelLarMockMvc.perform(post("/api/responsavel-lars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsavelLarDTO)))
            .andExpect(status().isCreated());

        // Validate the ResponsavelLar in the database
        List<ResponsavelLar> responsavelLarList = responsavelLarRepository.findAll();
        assertThat(responsavelLarList).hasSize(databaseSizeBeforeCreate + 1);
        ResponsavelLar testResponsavelLar = responsavelLarList.get(responsavelLarList.size() - 1);
        assertThat(testResponsavelLar.getResponsavel()).isEqualTo(DEFAULT_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void createResponsavelLarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = responsavelLarRepository.findAll().size();

        // Create the ResponsavelLar with an existing ID
        responsavelLar.setId(1L);
        ResponsavelLarDTO responsavelLarDTO = responsavelLarMapper.toDto(responsavelLar);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponsavelLarMockMvc.perform(post("/api/responsavel-lars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsavelLarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelLar in the database
        List<ResponsavelLar> responsavelLarList = responsavelLarRepository.findAll();
        assertThat(responsavelLarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkResponsavelIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsavelLarRepository.findAll().size();
        // set the field null
        responsavelLar.setResponsavel(null);

        // Create the ResponsavelLar, which fails.
        ResponsavelLarDTO responsavelLarDTO = responsavelLarMapper.toDto(responsavelLar);

        restResponsavelLarMockMvc.perform(post("/api/responsavel-lars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsavelLarDTO)))
            .andExpect(status().isBadRequest());

        List<ResponsavelLar> responsavelLarList = responsavelLarRepository.findAll();
        assertThat(responsavelLarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResponsavelLars() throws Exception {
        // Initialize the database
        responsavelLarRepository.saveAndFlush(responsavelLar);

        // Get all the responsavelLarList
        restResponsavelLarMockMvc.perform(get("/api/responsavel-lars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsavelLar.getId().intValue())))
            .andExpect(jsonPath("$.[*].responsavel").value(hasItem(DEFAULT_RESPONSAVEL.toString())));
    }

    @Test
    @Transactional
    public void getResponsavelLar() throws Exception {
        // Initialize the database
        responsavelLarRepository.saveAndFlush(responsavelLar);

        // Get the responsavelLar
        restResponsavelLarMockMvc.perform(get("/api/responsavel-lars/{id}", responsavelLar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(responsavelLar.getId().intValue()))
            .andExpect(jsonPath("$.responsavel").value(DEFAULT_RESPONSAVEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResponsavelLar() throws Exception {
        // Get the responsavelLar
        restResponsavelLarMockMvc.perform(get("/api/responsavel-lars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResponsavelLar() throws Exception {
        // Initialize the database
        responsavelLarRepository.saveAndFlush(responsavelLar);
        int databaseSizeBeforeUpdate = responsavelLarRepository.findAll().size();

        // Update the responsavelLar
        ResponsavelLar updatedResponsavelLar = responsavelLarRepository.findOne(responsavelLar.getId());
        // Disconnect from session so that the updates on updatedResponsavelLar are not directly saved in db
        em.detach(updatedResponsavelLar);
        updatedResponsavelLar
            .responsavel(UPDATED_RESPONSAVEL);
        ResponsavelLarDTO responsavelLarDTO = responsavelLarMapper.toDto(updatedResponsavelLar);

        restResponsavelLarMockMvc.perform(put("/api/responsavel-lars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsavelLarDTO)))
            .andExpect(status().isOk());

        // Validate the ResponsavelLar in the database
        List<ResponsavelLar> responsavelLarList = responsavelLarRepository.findAll();
        assertThat(responsavelLarList).hasSize(databaseSizeBeforeUpdate);
        ResponsavelLar testResponsavelLar = responsavelLarList.get(responsavelLarList.size() - 1);
        assertThat(testResponsavelLar.getResponsavel()).isEqualTo(UPDATED_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingResponsavelLar() throws Exception {
        int databaseSizeBeforeUpdate = responsavelLarRepository.findAll().size();

        // Create the ResponsavelLar
        ResponsavelLarDTO responsavelLarDTO = responsavelLarMapper.toDto(responsavelLar);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restResponsavelLarMockMvc.perform(put("/api/responsavel-lars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsavelLarDTO)))
            .andExpect(status().isCreated());

        // Validate the ResponsavelLar in the database
        List<ResponsavelLar> responsavelLarList = responsavelLarRepository.findAll();
        assertThat(responsavelLarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteResponsavelLar() throws Exception {
        // Initialize the database
        responsavelLarRepository.saveAndFlush(responsavelLar);
        int databaseSizeBeforeDelete = responsavelLarRepository.findAll().size();

        // Get the responsavelLar
        restResponsavelLarMockMvc.perform(delete("/api/responsavel-lars/{id}", responsavelLar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ResponsavelLar> responsavelLarList = responsavelLarRepository.findAll();
        assertThat(responsavelLarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponsavelLar.class);
        ResponsavelLar responsavelLar1 = new ResponsavelLar();
        responsavelLar1.setId(1L);
        ResponsavelLar responsavelLar2 = new ResponsavelLar();
        responsavelLar2.setId(responsavelLar1.getId());
        assertThat(responsavelLar1).isEqualTo(responsavelLar2);
        responsavelLar2.setId(2L);
        assertThat(responsavelLar1).isNotEqualTo(responsavelLar2);
        responsavelLar1.setId(null);
        assertThat(responsavelLar1).isNotEqualTo(responsavelLar2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponsavelLarDTO.class);
        ResponsavelLarDTO responsavelLarDTO1 = new ResponsavelLarDTO();
        responsavelLarDTO1.setId(1L);
        ResponsavelLarDTO responsavelLarDTO2 = new ResponsavelLarDTO();
        assertThat(responsavelLarDTO1).isNotEqualTo(responsavelLarDTO2);
        responsavelLarDTO2.setId(responsavelLarDTO1.getId());
        assertThat(responsavelLarDTO1).isEqualTo(responsavelLarDTO2);
        responsavelLarDTO2.setId(2L);
        assertThat(responsavelLarDTO1).isNotEqualTo(responsavelLarDTO2);
        responsavelLarDTO1.setId(null);
        assertThat(responsavelLarDTO1).isNotEqualTo(responsavelLarDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(responsavelLarMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(responsavelLarMapper.fromId(null)).isNull();
    }
}
