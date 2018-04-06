package br.mp.mpro.caopapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mp.mpro.caopapp.service.ResponsavelLarService;
import br.mp.mpro.caopapp.web.rest.errors.BadRequestAlertException;
import br.mp.mpro.caopapp.web.rest.util.HeaderUtil;
import br.mp.mpro.caopapp.web.rest.util.PaginationUtil;
import br.mp.mpro.caopapp.service.dto.ResponsavelLarDTO;
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
 * REST controller for managing ResponsavelLar.
 */
@RestController
@RequestMapping("/api")
public class ResponsavelLarResource {

    private final Logger log = LoggerFactory.getLogger(ResponsavelLarResource.class);

    private static final String ENTITY_NAME = "responsavelLar";

    private final ResponsavelLarService responsavelLarService;

    public ResponsavelLarResource(ResponsavelLarService responsavelLarService) {
        this.responsavelLarService = responsavelLarService;
    }

    /**
     * POST  /responsavel-lars : Create a new responsavelLar.
     *
     * @param responsavelLarDTO the responsavelLarDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new responsavelLarDTO, or with status 400 (Bad Request) if the responsavelLar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/responsavel-lars")
    @Timed
    public ResponseEntity<ResponsavelLarDTO> createResponsavelLar(@Valid @RequestBody ResponsavelLarDTO responsavelLarDTO) throws URISyntaxException {
        log.debug("REST request to save ResponsavelLar : {}", responsavelLarDTO);
        if (responsavelLarDTO.getId() != null) {
            throw new BadRequestAlertException("A new responsavelLar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResponsavelLarDTO result = responsavelLarService.save(responsavelLarDTO);
        return ResponseEntity.created(new URI("/api/responsavel-lars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /responsavel-lars : Updates an existing responsavelLar.
     *
     * @param responsavelLarDTO the responsavelLarDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated responsavelLarDTO,
     * or with status 400 (Bad Request) if the responsavelLarDTO is not valid,
     * or with status 500 (Internal Server Error) if the responsavelLarDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/responsavel-lars")
    @Timed
    public ResponseEntity<ResponsavelLarDTO> updateResponsavelLar(@Valid @RequestBody ResponsavelLarDTO responsavelLarDTO) throws URISyntaxException {
        log.debug("REST request to update ResponsavelLar : {}", responsavelLarDTO);
        if (responsavelLarDTO.getId() == null) {
            return createResponsavelLar(responsavelLarDTO);
        }
        ResponsavelLarDTO result = responsavelLarService.save(responsavelLarDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, responsavelLarDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /responsavel-lars : get all the responsavelLars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of responsavelLars in body
     */
    @GetMapping("/responsavel-lars")
    @Timed
    public ResponseEntity<List<ResponsavelLarDTO>> getAllResponsavelLars(Pageable pageable) {
        log.debug("REST request to get a page of ResponsavelLars");
        Page<ResponsavelLarDTO> page = responsavelLarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/responsavel-lars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /responsavel-lars/:id : get the "id" responsavelLar.
     *
     * @param id the id of the responsavelLarDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the responsavelLarDTO, or with status 404 (Not Found)
     */
    @GetMapping("/responsavel-lars/{id}")
    @Timed
    public ResponseEntity<ResponsavelLarDTO> getResponsavelLar(@PathVariable Long id) {
        log.debug("REST request to get ResponsavelLar : {}", id);
        ResponsavelLarDTO responsavelLarDTO = responsavelLarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(responsavelLarDTO));
    }

    /**
     * DELETE  /responsavel-lars/:id : delete the "id" responsavelLar.
     *
     * @param id the id of the responsavelLarDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/responsavel-lars/{id}")
    @Timed
    public ResponseEntity<Void> deleteResponsavelLar(@PathVariable Long id) {
        log.debug("REST request to delete ResponsavelLar : {}", id);
        responsavelLarService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
