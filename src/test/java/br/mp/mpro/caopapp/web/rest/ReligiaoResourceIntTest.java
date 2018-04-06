package br.mp.mpro.caopapp.web.rest;

import br.mp.mpro.caopapp.CaopApp;

import br.mp.mpro.caopapp.domain.Religiao;
import br.mp.mpro.caopapp.repository.ReligiaoRepository;
import br.mp.mpro.caopapp.service.ReligiaoService;
import br.mp.mpro.caopapp.service.dto.ReligiaoDTO;
import br.mp.mpro.caopapp.service.mapper.ReligiaoMapper;
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
 * Test class for the ReligiaoResource REST controller.
 *
 * @see ReligiaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaopApp.class)
public class ReligiaoResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private ReligiaoRepository religiaoRepository;

    @Autowired
    private ReligiaoMapper religiaoMapper;

    @Autowired
    private ReligiaoService religiaoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReligiaoMockMvc;

    private Religiao religiao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReligiaoResource religiaoResource = new ReligiaoResource(religiaoService);
        this.restReligiaoMockMvc = MockMvcBuilders.standaloneSetup(religiaoResource)
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
    public static Religiao createEntity(EntityManager em) {
        Religiao religiao = new Religiao()
            .descricao(DEFAULT_DESCRICAO);
        return religiao;
    }

    @Before
    public void initTest() {
        religiao = createEntity(em);
    }

    @Test
    @Transactional
    public void createReligiao() throws Exception {
        int databaseSizeBeforeCreate = religiaoRepository.findAll().size();

        // Create the Religiao
        ReligiaoDTO religiaoDTO = religiaoMapper.toDto(religiao);
        restReligiaoMockMvc.perform(post("/api/religiaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(religiaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Religiao in the database
        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeCreate + 1);
        Religiao testReligiao = religiaoList.get(religiaoList.size() - 1);
        assertThat(testReligiao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createReligiaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = religiaoRepository.findAll().size();

        // Create the Religiao with an existing ID
        religiao.setId(1L);
        ReligiaoDTO religiaoDTO = religiaoMapper.toDto(religiao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReligiaoMockMvc.perform(post("/api/religiaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(religiaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Religiao in the database
        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = religiaoRepository.findAll().size();
        // set the field null
        religiao.setDescricao(null);

        // Create the Religiao, which fails.
        ReligiaoDTO religiaoDTO = religiaoMapper.toDto(religiao);

        restReligiaoMockMvc.perform(post("/api/religiaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(religiaoDTO)))
            .andExpect(status().isBadRequest());

        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReligiaos() throws Exception {
        // Initialize the database
        religiaoRepository.saveAndFlush(religiao);

        // Get all the religiaoList
        restReligiaoMockMvc.perform(get("/api/religiaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(religiao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    public void getReligiao() throws Exception {
        // Initialize the database
        religiaoRepository.saveAndFlush(religiao);

        // Get the religiao
        restReligiaoMockMvc.perform(get("/api/religiaos/{id}", religiao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(religiao.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReligiao() throws Exception {
        // Get the religiao
        restReligiaoMockMvc.perform(get("/api/religiaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReligiao() throws Exception {
        // Initialize the database
        religiaoRepository.saveAndFlush(religiao);
        int databaseSizeBeforeUpdate = religiaoRepository.findAll().size();

        // Update the religiao
        Religiao updatedReligiao = religiaoRepository.findOne(religiao.getId());
        // Disconnect from session so that the updates on updatedReligiao are not directly saved in db
        em.detach(updatedReligiao);
        updatedReligiao
            .descricao(UPDATED_DESCRICAO);
        ReligiaoDTO religiaoDTO = religiaoMapper.toDto(updatedReligiao);

        restReligiaoMockMvc.perform(put("/api/religiaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(religiaoDTO)))
            .andExpect(status().isOk());

        // Validate the Religiao in the database
        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeUpdate);
        Religiao testReligiao = religiaoList.get(religiaoList.size() - 1);
        assertThat(testReligiao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingReligiao() throws Exception {
        int databaseSizeBeforeUpdate = religiaoRepository.findAll().size();

        // Create the Religiao
        ReligiaoDTO religiaoDTO = religiaoMapper.toDto(religiao);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReligiaoMockMvc.perform(put("/api/religiaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(religiaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Religiao in the database
        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReligiao() throws Exception {
        // Initialize the database
        religiaoRepository.saveAndFlush(religiao);
        int databaseSizeBeforeDelete = religiaoRepository.findAll().size();

        // Get the religiao
        restReligiaoMockMvc.perform(delete("/api/religiaos/{id}", religiao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Religiao.class);
        Religiao religiao1 = new Religiao();
        religiao1.setId(1L);
        Religiao religiao2 = new Religiao();
        religiao2.setId(religiao1.getId());
        assertThat(religiao1).isEqualTo(religiao2);
        religiao2.setId(2L);
        assertThat(religiao1).isNotEqualTo(religiao2);
        religiao1.setId(null);
        assertThat(religiao1).isNotEqualTo(religiao2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReligiaoDTO.class);
        ReligiaoDTO religiaoDTO1 = new ReligiaoDTO();
        religiaoDTO1.setId(1L);
        ReligiaoDTO religiaoDTO2 = new ReligiaoDTO();
        assertThat(religiaoDTO1).isNotEqualTo(religiaoDTO2);
        religiaoDTO2.setId(religiaoDTO1.getId());
        assertThat(religiaoDTO1).isEqualTo(religiaoDTO2);
        religiaoDTO2.setId(2L);
        assertThat(religiaoDTO1).isNotEqualTo(religiaoDTO2);
        religiaoDTO1.setId(null);
        assertThat(religiaoDTO1).isNotEqualTo(religiaoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(religiaoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(religiaoMapper.fromId(null)).isNull();
    }
}
