package br.mp.mpro.caopapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mp.mpro.caopapp.service.PeriodicidadeService;
import br.mp.mpro.caopapp.web.rest.errors.BadRequestAlertException;
import br.mp.mpro.caopapp.web.rest.util.HeaderUtil;
import br.mp.mpro.caopapp.web.rest.util.PaginationUtil;
import br.mp.mpro.caopapp.service.dto.PeriodicidadeDTO;
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
 * REST controller for managing Periodicidade.
 */
@RestController
@RequestMapping("/api")
public class PeriodicidadeResource {

    private final Logger log = LoggerFactory.getLogger(PeriodicidadeResource.class);

    private static final String ENTITY_NAME = "periodicidade";

    private final PeriodicidadeService periodicidadeService;

    public PeriodicidadeResource(PeriodicidadeService periodicidadeService) {
        this.periodicidadeService = periodicidadeService;
    }

    /**
     * POST  /periodicidades : Create a new periodicidade.
     *
     * @param periodicidadeDTO the periodicidadeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new periodicidadeDTO, or with status 400 (Bad Request) if the periodicidade has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/periodicidades")
    @Timed
    public ResponseEntity<PeriodicidadeDTO> createPeriodicidade(@Valid @RequestBody PeriodicidadeDTO periodicidadeDTO) throws URISyntaxException {
        log.debug("REST request to save Periodicidade : {}", periodicidadeDTO);
        if (periodicidadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new periodicidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeriodicidadeDTO result = periodicidadeService.save(periodicidadeDTO);
        return ResponseEntity.created(new URI("/api/periodicidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /periodicidades : Updates an existing periodicidade.
     *
     * @param periodicidadeDTO the periodicidadeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated periodicidadeDTO,
     * or with status 400 (Bad Request) if the periodicidadeDTO is not valid,
     * or with status 500 (Internal Server Error) if the periodicidadeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/periodicidades")
    @Timed
    public ResponseEntity<PeriodicidadeDTO> updatePeriodicidade(@Valid @RequestBody PeriodicidadeDTO periodicidadeDTO) throws URISyntaxException {
        log.debug("REST request to update Periodicidade : {}", periodicidadeDTO);
        if (periodicidadeDTO.getId() == null) {
            return createPeriodicidade(periodicidadeDTO);
        }
        PeriodicidadeDTO result = periodicidadeService.save(periodicidadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, periodicidadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /periodicidades : get all the periodicidades.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of periodicidades in body
     */
    @GetMapping("/periodicidades")
    @Timed
    public ResponseEntity<List<PeriodicidadeDTO>> getAllPeriodicidades(Pageable pageable) {
        log.debug("REST request to get a page of Periodicidades");
        Page<PeriodicidadeDTO> page = periodicidadeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/periodicidades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /periodicidades/:id : get the "id" periodicidade.
     *
     * @param id the id of the periodicidadeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the periodicidadeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/periodicidades/{id}")
    @Timed
    public ResponseEntity<PeriodicidadeDTO> getPeriodicidade(@PathVariable Long id) {
        log.debug("REST request to get Periodicidade : {}", id);
        PeriodicidadeDTO periodicidadeDTO = periodicidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(periodicidadeDTO));
    }

    /**
     * DELETE  /periodicidades/:id : delete the "id" periodicidade.
     *
     * @param id the id of the periodicidadeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/periodicidades/{id}")
    @Timed
    public ResponseEntity<Void> deletePeriodicidade(@PathVariable Long id) {
        log.debug("REST request to delete Periodicidade : {}", id);
        periodicidadeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
