package br.mp.mpro.caopapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mp.mpro.caopapp.service.ReligiaoService;
import br.mp.mpro.caopapp.web.rest.errors.BadRequestAlertException;
import br.mp.mpro.caopapp.web.rest.util.HeaderUtil;
import br.mp.mpro.caopapp.web.rest.util.PaginationUtil;
import br.mp.mpro.caopapp.service.dto.ReligiaoDTO;
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
 * REST controller for managing Religiao.
 */
@RestController
@RequestMapping("/api")
public class ReligiaoResource {

    private final Logger log = LoggerFactory.getLogger(ReligiaoResource.class);

    private static final String ENTITY_NAME = "religiao";

    private final ReligiaoService religiaoService;

    public ReligiaoResource(ReligiaoService religiaoService) {
        this.religiaoService = religiaoService;
    }

    /**
     * POST  /religiaos : Create a new religiao.
     *
     * @param religiaoDTO the religiaoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new religiaoDTO, or with status 400 (Bad Request) if the religiao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/religiaos")
    @Timed
    public ResponseEntity<ReligiaoDTO> createReligiao(@Valid @RequestBody ReligiaoDTO religiaoDTO) throws URISyntaxException {
        log.debug("REST request to save Religiao : {}", religiaoDTO);
        if (religiaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new religiao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReligiaoDTO result = religiaoService.save(religiaoDTO);
        return ResponseEntity.created(new URI("/api/religiaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /religiaos : Updates an existing religiao.
     *
     * @param religiaoDTO the religiaoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated religiaoDTO,
     * or with status 400 (Bad Request) if the religiaoDTO is not valid,
     * or with status 500 (Internal Server Error) if the religiaoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/religiaos")
    @Timed
    public ResponseEntity<ReligiaoDTO> updateReligiao(@Valid @RequestBody ReligiaoDTO religiaoDTO) throws URISyntaxException {
        log.debug("REST request to update Religiao : {}", religiaoDTO);
        if (religiaoDTO.getId() == null) {
            return createReligiao(religiaoDTO);
        }
        ReligiaoDTO result = religiaoService.save(religiaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, religiaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /religiaos : get all the religiaos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of religiaos in body
     */
    @GetMapping("/religiaos")
    @Timed
    public ResponseEntity<List<ReligiaoDTO>> getAllReligiaos(Pageable pageable) {
        log.debug("REST request to get a page of Religiaos");
        Page<ReligiaoDTO> page = religiaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/religiaos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /religiaos/:id : get the "id" religiao.
     *
     * @param id the id of the religiaoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the religiaoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/religiaos/{id}")
    @Timed
    public ResponseEntity<ReligiaoDTO> getReligiao(@PathVariable Long id) {
        log.debug("REST request to get Religiao : {}", id);
        ReligiaoDTO religiaoDTO = religiaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(religiaoDTO));
    }

    /**
     * DELETE  /religiaos/:id : delete the "id" religiao.
     *
     * @param id the id of the religiaoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/religiaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteReligiao(@PathVariable Long id) {
        log.debug("REST request to delete Religiao : {}", id);
        religiaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
