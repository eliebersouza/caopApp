package br.mp.mpro.caopapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mp.mpro.caopapp.service.CicloEscolarService;
import br.mp.mpro.caopapp.web.rest.errors.BadRequestAlertException;
import br.mp.mpro.caopapp.web.rest.util.HeaderUtil;
import br.mp.mpro.caopapp.web.rest.util.PaginationUtil;
import br.mp.mpro.caopapp.service.dto.CicloEscolarDTO;
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
 * REST controller for managing CicloEscolar.
 */
@RestController
@RequestMapping("/api")
public class CicloEscolarResource {

    private final Logger log = LoggerFactory.getLogger(CicloEscolarResource.class);

    private static final String ENTITY_NAME = "cicloEscolar";

    private final CicloEscolarService cicloEscolarService;

    public CicloEscolarResource(CicloEscolarService cicloEscolarService) {
        this.cicloEscolarService = cicloEscolarService;
    }

    /**
     * POST  /ciclo-escolars : Create a new cicloEscolar.
     *
     * @param cicloEscolarDTO the cicloEscolarDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cicloEscolarDTO, or with status 400 (Bad Request) if the cicloEscolar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ciclo-escolars")
    @Timed
    public ResponseEntity<CicloEscolarDTO> createCicloEscolar(@Valid @RequestBody CicloEscolarDTO cicloEscolarDTO) throws URISyntaxException {
        log.debug("REST request to save CicloEscolar : {}", cicloEscolarDTO);
        if (cicloEscolarDTO.getId() != null) {
            throw new BadRequestAlertException("A new cicloEscolar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CicloEscolarDTO result = cicloEscolarService.save(cicloEscolarDTO);
        return ResponseEntity.created(new URI("/api/ciclo-escolars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ciclo-escolars : Updates an existing cicloEscolar.
     *
     * @param cicloEscolarDTO the cicloEscolarDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cicloEscolarDTO,
     * or with status 400 (Bad Request) if the cicloEscolarDTO is not valid,
     * or with status 500 (Internal Server Error) if the cicloEscolarDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ciclo-escolars")
    @Timed
    public ResponseEntity<CicloEscolarDTO> updateCicloEscolar(@Valid @RequestBody CicloEscolarDTO cicloEscolarDTO) throws URISyntaxException {
        log.debug("REST request to update CicloEscolar : {}", cicloEscolarDTO);
        if (cicloEscolarDTO.getId() == null) {
            return createCicloEscolar(cicloEscolarDTO);
        }
        CicloEscolarDTO result = cicloEscolarService.save(cicloEscolarDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cicloEscolarDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ciclo-escolars : get all the cicloEscolars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cicloEscolars in body
     */
    @GetMapping("/ciclo-escolars")
    @Timed
    public ResponseEntity<List<CicloEscolarDTO>> getAllCicloEscolars(Pageable pageable) {
        log.debug("REST request to get a page of CicloEscolars");
        Page<CicloEscolarDTO> page = cicloEscolarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ciclo-escolars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ciclo-escolars/:id : get the "id" cicloEscolar.
     *
     * @param id the id of the cicloEscolarDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cicloEscolarDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ciclo-escolars/{id}")
    @Timed
    public ResponseEntity<CicloEscolarDTO> getCicloEscolar(@PathVariable Long id) {
        log.debug("REST request to get CicloEscolar : {}", id);
        CicloEscolarDTO cicloEscolarDTO = cicloEscolarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cicloEscolarDTO));
    }

    /**
     * DELETE  /ciclo-escolars/:id : delete the "id" cicloEscolar.
     *
     * @param id the id of the cicloEscolarDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ciclo-escolars/{id}")
    @Timed
    public ResponseEntity<Void> deleteCicloEscolar(@PathVariable Long id) {
        log.debug("REST request to delete CicloEscolar : {}", id);
        cicloEscolarService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
