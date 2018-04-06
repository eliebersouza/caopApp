package br.mp.mpro.caopapp.web.rest;

import br.mp.mpro.caopapp.CaopApp;

import br.mp.mpro.caopapp.domain.MotivoEvasao;
import br.mp.mpro.caopapp.repository.MotivoEvasaoRepository;
import br.mp.mpro.caopapp.service.MotivoEvasaoService;
import br.mp.mpro.caopapp.service.dto.MotivoEvasaoDTO;
import br.mp.mpro.caopapp.service.mapper.MotivoEvasaoMapper;
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
 * Test class for the MotivoEvasaoResource REST controller.
 *
 * @see MotivoEvasaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaopApp.class)
public class MotivoEvasaoResourceIntTest {

    private static final String DEFAULT_MOTIVO = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVO = "BBBBBBBBBB";

    @Autowired
    private MotivoEvasaoRepository motivoEvasaoRepository;

    @Autowired
    private MotivoEvasaoMapper motivoEvasaoMapper;

    @Autowired
    private MotivoEvasaoService motivoEvasaoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMotivoEvasaoMockMvc;

    private MotivoEvasao motivoEvasao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MotivoEvasaoResource motivoEvasaoResource = new MotivoEvasaoResource(motivoEvasaoService);
        this.restMotivoEvasaoMockMvc = MockMvcBuilders.standaloneSetup(motivoEvasaoResource)
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
    public static MotivoEvasao createEntity(EntityManager em) {
        MotivoEvasao motivoEvasao = new MotivoEvasao()
            .motivo(DEFAULT_MOTIVO);
        return motivoEvasao;
    }

    @Before
    public void initTest() {
        motivoEvasao = createEntity(em);
    }

    @Test
    @Transactional
    public void createMotivoEvasao() throws Exception {
        int databaseSizeBeforeCreate = motivoEvasaoRepository.findAll().size();

        // Create the MotivoEvasao
        MotivoEvasaoDTO motivoEvasaoDTO = motivoEvasaoMapper.toDto(motivoEvasao);
        restMotivoEvasaoMockMvc.perform(post("/api/motivo-evasaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motivoEvasaoDTO)))
            .andExpect(status().isCreated());

        // Validate the MotivoEvasao in the database
        List<MotivoEvasao> motivoEvasaoList = motivoEvasaoRepository.findAll();
        assertThat(motivoEvasaoList).hasSize(databaseSizeBeforeCreate + 1);
        MotivoEvasao testMotivoEvasao = motivoEvasaoList.get(motivoEvasaoList.size() - 1);
        assertThat(testMotivoEvasao.getMotivo()).isEqualTo(DEFAULT_MOTIVO);
    }

    @Test
    @Transactional
    public void createMotivoEvasaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = motivoEvasaoRepository.findAll().size();

        // Create the MotivoEvasao with an existing ID
        motivoEvasao.setId(1L);
        MotivoEvasaoDTO motivoEvasaoDTO = motivoEvasaoMapper.toDto(motivoEvasao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMotivoEvasaoMockMvc.perform(post("/api/motivo-evasaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motivoEvasaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MotivoEvasao in the database
        List<MotivoEvasao> motivoEvasaoList = motivoEvasaoRepository.findAll();
        assertThat(motivoEvasaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMotivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = motivoEvasaoRepository.findAll().size();
        // set the field null
        motivoEvasao.setMotivo(null);

        // Create the MotivoEvasao, which fails.
        MotivoEvasaoDTO motivoEvasaoDTO = motivoEvasaoMapper.toDto(motivoEvasao);

        restMotivoEvasaoMockMvc.perform(post("/api/motivo-evasaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motivoEvasaoDTO)))
            .andExpect(status().isBadRequest());

        List<MotivoEvasao> motivoEvasaoList = motivoEvasaoRepository.findAll();
        assertThat(motivoEvasaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMotivoEvasaos() throws Exception {
        // Initialize the database
        motivoEvasaoRepository.saveAndFlush(motivoEvasao);

        // Get all the motivoEvasaoList
        restMotivoEvasaoMockMvc.perform(get("/api/motivo-evasaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(motivoEvasao.getId().intValue())))
            .andExpect(jsonPath("$.[*].motivo").value(hasItem(DEFAULT_MOTIVO.toString())));
    }

    @Test
    @Transactional
    public void getMotivoEvasao() throws Exception {
        // Initialize the database
        motivoEvasaoRepository.saveAndFlush(motivoEvasao);

        // Get the motivoEvasao
        restMotivoEvasaoMockMvc.perform(get("/api/motivo-evasaos/{id}", motivoEvasao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(motivoEvasao.getId().intValue()))
            .andExpect(jsonPath("$.motivo").value(DEFAULT_MOTIVO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMotivoEvasao() throws Exception {
        // Get the motivoEvasao
        restMotivoEvasaoMockMvc.perform(get("/api/motivo-evasaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMotivoEvasao() throws Exception {
        // Initialize the database
        motivoEvasaoRepository.saveAndFlush(motivoEvasao);
        int databaseSizeBeforeUpdate = motivoEvasaoRepository.findAll().size();

        // Update the motivoEvasao
        MotivoEvasao updatedMotivoEvasao = motivoEvasaoRepository.findOne(motivoEvasao.getId());
        // Disconnect from session so that the updates on updatedMotivoEvasao are not directly saved in db
        em.detach(updatedMotivoEvasao);
        updatedMotivoEvasao
            .motivo(UPDATED_MOTIVO);
        MotivoEvasaoDTO motivoEvasaoDTO = motivoEvasaoMapper.toDto(updatedMotivoEvasao);

        restMotivoEvasaoMockMvc.perform(put("/api/motivo-evasaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motivoEvasaoDTO)))
            .andExpect(status().isOk());

        // Validate the MotivoEvasao in the database
        List<MotivoEvasao> motivoEvasaoList = motivoEvasaoRepository.findAll();
        assertThat(motivoEvasaoList).hasSize(databaseSizeBeforeUpdate);
        MotivoEvasao testMotivoEvasao = motivoEvasaoList.get(motivoEvasaoList.size() - 1);
        assertThat(testMotivoEvasao.getMotivo()).isEqualTo(UPDATED_MOTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingMotivoEvasao() throws Exception {
        int databaseSizeBeforeUpdate = motivoEvasaoRepository.findAll().size();

        // Create the MotivoEvasao
        MotivoEvasaoDTO motivoEvasaoDTO = motivoEvasaoMapper.toDto(motivoEvasao);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMotivoEvasaoMockMvc.perform(put("/api/motivo-evasaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motivoEvasaoDTO)))
            .andExpect(status().isCreated());

        // Validate the MotivoEvasao in the database
        List<MotivoEvasao> motivoEvasaoList = motivoEvasaoRepository.findAll();
        assertThat(motivoEvasaoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMotivoEvasao() throws Exception {
        // Initialize the database
        motivoEvasaoRepository.saveAndFlush(motivoEvasao);
        int databaseSizeBeforeDelete = motivoEvasaoRepository.findAll().size();

        // Get the motivoEvasao
        restMotivoEvasaoMockMvc.perform(delete("/api/motivo-evasaos/{id}", motivoEvasao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MotivoEvasao> motivoEvasaoList = motivoEvasaoRepository.findAll();
        assertThat(motivoEvasaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MotivoEvasao.class);
        MotivoEvasao motivoEvasao1 = new MotivoEvasao();
        motivoEvasao1.setId(1L);
        MotivoEvasao motivoEvasao2 = new MotivoEvasao();
        motivoEvasao2.setId(motivoEvasao1.getId());
        assertThat(motivoEvasao1).isEqualTo(motivoEvasao2);
        motivoEvasao2.setId(2L);
        assertThat(motivoEvasao1).isNotEqualTo(motivoEvasao2);
        motivoEvasao1.setId(null);
        assertThat(motivoEvasao1).isNotEqualTo(motivoEvasao2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MotivoEvasaoDTO.class);
        MotivoEvasaoDTO motivoEvasaoDTO1 = new MotivoEvasaoDTO();
        motivoEvasaoDTO1.setId(1L);
        MotivoEvasaoDTO motivoEvasaoDTO2 = new MotivoEvasaoDTO();
        assertThat(motivoEvasaoDTO1).isNotEqualTo(motivoEvasaoDTO2);
        motivoEvasaoDTO2.setId(motivoEvasaoDTO1.getId());
        assertThat(motivoEvasaoDTO1).isEqualTo(motivoEvasaoDTO2);
        motivoEvasaoDTO2.setId(2L);
        assertThat(motivoEvasaoDTO1).isNotEqualTo(motivoEvasaoDTO2);
        motivoEvasaoDTO1.setId(null);
        assertThat(motivoEvasaoDTO1).isNotEqualTo(motivoEvasaoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(motivoEvasaoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(motivoEvasaoMapper.fromId(null)).isNull();
    }
}
