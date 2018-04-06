package br.mp.mpro.caopapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mp.mpro.caopapp.service.MotivoEvasaoService;
import br.mp.mpro.caopapp.web.rest.errors.BadRequestAlertException;
import br.mp.mpro.caopapp.web.rest.util.HeaderUtil;
import br.mp.mpro.caopapp.web.rest.util.PaginationUtil;
import br.mp.mpro.caopapp.service.dto.MotivoEvasaoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MotivoEvasao.
 */
@RestController
@RequestMapping("/api")
public class MotivoEvasaoResource {

    private final Logger log = LoggerFactory.getLogger(MotivoEvasaoResource.class);

    private static final String ENTITY_NAME = "motivoEvasao";

    private final MotivoEvasaoService motivoEvasaoService;

    public MotivoEvasaoResource(MotivoEvasaoService motivoEvasaoService) {
        this.motivoEvasaoService = motivoEvasaoService;
    }

    /**
     * POST  /motivo-evasaos : Create a new motivoEvasao.
     *
     * @param motivoEvasaoDTO the motivoEvasaoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new motivoEvasaoDTO, or with status 400 (Bad Request) if the motivoEvasao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/motivo-evasaos")
    @Timed
    public ResponseEntity<MotivoEvasaoDTO> createMotivoEvasao(@Valid @RequestBody MotivoEvasaoDTO motivoEvasaoDTO) throws URISyntaxException {
        log.debug("REST request to save MotivoEvasao : {}", motivoEvasaoDTO);
        if (motivoEvasaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new motivoEvasao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MotivoEvasaoDTO result = motivoEvasaoService.save(motivoEvasaoDTO);
        return ResponseEntity.created(new URI("/api/motivo-evasaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /motivo-evasaos : Updates an existing motivoEvasao.
     *
     * @param motivoEvasaoDTO the motivoEvasaoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated motivoEvasaoDTO,
     * or with status 400 (Bad Request) if the motivoEvasaoDTO is not valid,
     * or with status 500 (Internal Server Error) if the motivoEvasaoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/motivo-evasaos")
    @Timed
    public ResponseEntity<MotivoEvasaoDTO> updateMotivoEvasao(@Valid @RequestBody MotivoEvasaoDTO motivoEvasaoDTO) throws URISyntaxException {
        log.debug("REST request to update MotivoEvasao : {}", motivoEvasaoDTO);
        if (motivoEvasaoDTO.getId() == null) {
            return createMotivoEvasao(motivoEvasaoDTO);
        }
        MotivoEvasaoDTO result = motivoEvasaoService.save(motivoEvasaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, motivoEvasaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /motivo-evasaos : get all the motivoEvasaos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of motivoEvasaos in body
     */
    @GetMapping("/motivo-evasaos")
    @Timed
    public ResponseEntity<List<MotivoEvasaoDTO>> getAllMotivoEvasaos(Pageable pageable) {
        log.debug("REST request to get a page of MotivoEvasaos");
        Page<MotivoEvasaoDTO> page = motivoEvasaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/motivo-evasaos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /motivo-evasaos/:id : get the "id" motivoEvasao.
     *
     * @param id the id of the motivoEvasaoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the motivoEvasaoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/motivo-evasaos/{id}")
    @Timed
    public ResponseEntity<MotivoEvasaoDTO> getMotivoEvasao(@PathVariable Long id) {
        log.debug("REST request to get MotivoEvasao : {}", id);
        MotivoEvasaoDTO motivoEvasaoDTO = motivoEvasaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(motivoEvasaoDTO));
    }

    /**
     * DELETE  /motivo-evasaos/:id : delete the "id" motivoEvasao.
     *
     * @param id the id of the motivoEvasaoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/motivo-evasaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteMotivoEvasao(@PathVariable Long id) {
        log.debug("REST request to delete MotivoEvasao : {}", id);
        motivoEvasaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
