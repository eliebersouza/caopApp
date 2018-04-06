package br.mp.mpro.caopapp.web.rest;

import br.mp.mpro.caopapp.CaopApp;

import br.mp.mpro.caopapp.domain.ServicoPublico;
import br.mp.mpro.caopapp.repository.ServicoPublicoRepository;
import br.mp.mpro.caopapp.service.ServicoPublicoService;
import br.mp.mpro.caopapp.service.dto.ServicoPublicoDTO;
import br.mp.mpro.caopapp.service.mapper.ServicoPublicoMapper;
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
 * Test class for the ServicoPublicoResource REST controller.
 *
 * @see ServicoPublicoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaopApp.class)
public class ServicoPublicoResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private ServicoPublicoRepository servicoPublicoRepository;

    @Autowired
    private ServicoPublicoMapper servicoPublicoMapper;

    @Autowired
    private ServicoPublicoService servicoPublicoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restServicoPublicoMockMvc;

    private ServicoPublico servicoPublico;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServicoPublicoResource servicoPublicoResource = new ServicoPublicoResource(servicoPublicoService);
        this.restServicoPublicoMockMvc = MockMvcBuilders.standaloneSetup(servicoPublicoResource)
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
    public static ServicoPublico createEntity(EntityManager em) {
        ServicoPublico servicoPublico = new ServicoPublico()
            .descricao(DEFAULT_DESCRICAO);
        return servicoPublico;
    }

    @Before
    public void initTest() {
        servicoPublico = createEntity(em);
    }

    @Test
    @Transactional
    public void createServicoPublico() throws Exception {
        int databaseSizeBeforeCreate = servicoPublicoRepository.findAll().size();

        // Create the ServicoPublico
        ServicoPublicoDTO servicoPublicoDTO = servicoPublicoMapper.toDto(servicoPublico);
        restServicoPublicoMockMvc.perform(post("/api/servico-publicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicoPublicoDTO)))
            .andExpect(status().isCreated());

        // Validate the ServicoPublico in the database
        List<ServicoPublico> servicoPublicoList = servicoPublicoRepository.findAll();
        assertThat(servicoPublicoList).hasSize(databaseSizeBeforeCreate + 1);
        ServicoPublico testServicoPublico = servicoPublicoList.get(servicoPublicoList.size() - 1);
        assertThat(testServicoPublico.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createServicoPublicoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = servicoPublicoRepository.findAll().size();

        // Create the ServicoPublico with an existing ID
        servicoPublico.setId(1L);
        ServicoPublicoDTO servicoPublicoDTO = servicoPublicoMapper.toDto(servicoPublico);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServicoPublicoMockMvc.perform(post("/api/servico-publicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicoPublicoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServicoPublico in the database
        List<ServicoPublico> servicoPublicoList = servicoPublicoRepository.findAll();
        assertThat(servicoPublicoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = servicoPublicoRepository.findAll().size();
        // set the field null
        servicoPublico.setDescricao(null);

        // Create the ServicoPublico, which fails.
        ServicoPublicoDTO servicoPublicoDTO = servicoPublicoMapper.toDto(servicoPublico);

        restServicoPublicoMockMvc.perform(post("/api/servico-publicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicoPublicoDTO)))
            .andExpect(status().isBadRequest());

        List<ServicoPublico> servicoPublicoList = servicoPublicoRepository.findAll();
        assertThat(servicoPublicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServicoPublicos() throws Exception {
        // Initialize the database
        servicoPublicoRepository.saveAndFlush(servicoPublico);

        // Get all the servicoPublicoList
        restServicoPublicoMockMvc.perform(get("/api/servico-publicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicoPublico.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    public void getServicoPublico() throws Exception {
        // Initialize the database
        servicoPublicoRepository.saveAndFlush(servicoPublico);

        // Get the servicoPublico
        restServicoPublicoMockMvc.perform(get("/api/servico-publicos/{id}", servicoPublico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(servicoPublico.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingServicoPublico() throws Exception {
        // Get the servicoPublico
        restServicoPublicoMockMvc.perform(get("/api/servico-publicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServicoPublico() throws Exception {
        // Initialize the database
        servicoPublicoRepository.saveAndFlush(servicoPublico);
        int databaseSizeBeforeUpdate = servicoPublicoRepository.findAll().size();

        // Update the servicoPublico
        ServicoPublico updatedServicoPublico = servicoPublicoRepository.findOne(servicoPublico.getId());
        // Disconnect from session so that the updates on updatedServicoPublico are not directly saved in db
        em.detach(updatedServicoPublico);
        updatedServicoPublico
            .descricao(UPDATED_DESCRICAO);
        ServicoPublicoDTO servicoPublicoDTO = servicoPublicoMapper.toDto(updatedServicoPublico);

        restServicoPublicoMockMvc.perform(put("/api/servico-publicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicoPublicoDTO)))
            .andExpect(status().isOk());

        // Validate the ServicoPublico in the database
        List<ServicoPublico> servicoPublicoList = servicoPublicoRepository.findAll();
        assertThat(servicoPublicoList).hasSize(databaseSizeBeforeUpdate);
        ServicoPublico testServicoPublico = servicoPublicoList.get(servicoPublicoList.size() - 1);
        assertThat(testServicoPublico.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingServicoPublico() throws Exception {
        int databaseSizeBeforeUpdate = servicoPublicoRepository.findAll().size();

        // Create the ServicoPublico
        ServicoPublicoDTO servicoPublicoDTO = servicoPublicoMapper.toDto(servicoPublico);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restServicoPublicoMockMvc.perform(put("/api/servico-publicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicoPublicoDTO)))
            .andExpect(status().isCreated());

        // Validate the ServicoPublico in the database
        List<ServicoPublico> servicoPublicoList = servicoPublicoRepository.findAll();
        assertThat(servicoPublicoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteServicoPublico() throws Exception {
        // Initialize the database
        servicoPublicoRepository.saveAndFlush(servicoPublico);
        int databaseSizeBeforeDelete = servicoPublicoRepository.findAll().size();

        // Get the servicoPublico
        restServicoPublicoMockMvc.perform(delete("/api/servico-publicos/{id}", servicoPublico.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ServicoPublico> servicoPublicoList = servicoPublicoRepository.findAll();
        assertThat(servicoPublicoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServicoPublico.class);
        ServicoPublico servicoPublico1 = new ServicoPublico();
        servicoPublico1.setId(1L);
        ServicoPublico servicoPublico2 = new ServicoPublico();
        servicoPublico2.setId(servicoPublico1.getId());
        assertThat(servicoPublico1).isEqualTo(servicoPublico2);
        servicoPublico2.setId(2L);
        assertThat(servicoPublico1).isNotEqualTo(servicoPublico2);
        servicoPublico1.setId(null);
        assertThat(servicoPublico1).isNotEqualTo(servicoPublico2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServicoPublicoDTO.class);
        ServicoPublicoDTO servicoPublicoDTO1 = new ServicoPublicoDTO();
        servicoPublicoDTO1.setId(1L);
        ServicoPublicoDTO servicoPublicoDTO2 = new ServicoPublicoDTO();
        assertThat(servicoPublicoDTO1).isNotEqualTo(servicoPublicoDTO2);
        servicoPublicoDTO2.setId(servicoPublicoDTO1.getId());
        assertThat(servicoPublicoDTO1).isEqualTo(servicoPublicoDTO2);
        servicoPublicoDTO2.setId(2L);
        assertThat(servicoPublicoDTO1).isNotEqualTo(servicoPublicoDTO2);
        servicoPublicoDTO1.setId(null);
        assertThat(servicoPublicoDTO1).isNotEqualTo(servicoPublicoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(servicoPublicoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(servicoPublicoMapper.fromId(null)).isNull();
    }
}
