package br.mp.mpro.caopapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mp.mpro.caopapp.service.RendaFamiliarService;
import br.mp.mpro.caopapp.web.rest.errors.BadRequestAlertException;
import br.mp.mpro.caopapp.web.rest.util.HeaderUtil;
import br.mp.mpro.caopapp.web.rest.util.PaginationUtil;
import br.mp.mpro.caopapp.service.dto.RendaFamiliarDTO;
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
 * REST controller for managing RendaFamiliar.
 */
@RestController
@RequestMapping("/api")
public class RendaFamiliarResource {

    private final Logger log = LoggerFactory.getLogger(RendaFamiliarResource.class);

    private static final String ENTITY_NAME = "rendaFamiliar";

    private final RendaFamiliarService rendaFamiliarService;

    public RendaFamiliarResource(RendaFamiliarService rendaFamiliarService) {
        this.rendaFamiliarService = rendaFamiliarService;
    }

    /**
     * POST  /renda-familiars : Create a new rendaFamiliar.
     *
     * @param rendaFamiliarDTO the rendaFamiliarDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rendaFamiliarDTO, or with status 400 (Bad Request) if the rendaFamiliar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/renda-familiars")
    @Timed
    public ResponseEntity<RendaFamiliarDTO> createRendaFamiliar(@Valid @RequestBody RendaFamiliarDTO rendaFamiliarDTO) throws URISyntaxException {
        log.debug("REST request to save RendaFamiliar : {}", rendaFamiliarDTO);
        if (rendaFamiliarDTO.getId() != null) {
            throw new BadRequestAlertException("A new rendaFamiliar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RendaFamiliarDTO result = rendaFamiliarService.save(rendaFamiliarDTO);
        return ResponseEntity.created(new URI("/api/renda-familiars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /renda-familiars : Updates an existing rendaFamiliar.
     *
     * @param rendaFamiliarDTO the rendaFamiliarDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rendaFamiliarDTO,
     * or with status 400 (Bad Request) if the rendaFamiliarDTO is not valid,
     * or with status 500 (Internal Server Error) if the rendaFamiliarDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/renda-familiars")
    @Timed
    public ResponseEntity<RendaFamiliarDTO> updateRendaFamiliar(@Valid @RequestBody RendaFamiliarDTO rendaFamiliarDTO) throws URISyntaxException {
        log.debug("REST request to update RendaFamiliar : {}", rendaFamiliarDTO);
        if (rendaFamiliarDTO.getId() == null) {
            return createRendaFamiliar(rendaFamiliarDTO);
        }
        RendaFamiliarDTO result = rendaFamiliarService.save(rendaFamiliarDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rendaFamiliarDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /renda-familiars : get all the rendaFamiliars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rendaFamiliars in body
     */
    @GetMapping("/renda-familiars")
    @Timed
    public ResponseEntity<List<RendaFamiliarDTO>> getAllRendaFamiliars(Pageable pageable) {
        log.debug("REST request to get a page of RendaFamiliars");
        Page<RendaFamiliarDTO> page = rendaFamiliarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/renda-familiars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /renda-familiars/:id : get the "id" rendaFamiliar.
     *
     * @param id the id of the rendaFamiliarDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rendaFamiliarDTO, or with status 404 (Not Found)
     */
    @GetMapping("/renda-familiars/{id}")
    @Timed
    public ResponseEntity<RendaFamiliarDTO> getRendaFamiliar(@PathVariable Long id) {
        log.debug("REST request to get RendaFamiliar : {}", id);
        RendaFamiliarDTO rendaFamiliarDTO = rendaFamiliarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rendaFamiliarDTO));
    }

    /**
     * DELETE  /renda-familiars/:id : delete the "id" rendaFamiliar.
     *
     * @param id the id of the rendaFamiliarDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/renda-familiars/{id}")
    @Timed
    public ResponseEntity<Void> deleteRendaFamiliar(@PathVariable Long id) {
        log.debug("REST request to delete RendaFamiliar : {}", id);
        rendaFamiliarService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
