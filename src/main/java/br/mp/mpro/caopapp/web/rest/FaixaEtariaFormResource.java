package br.mp.mpro.caopapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mp.mpro.caopapp.service.FaixaEtariaFormService;
import br.mp.mpro.caopapp.web.rest.errors.BadRequestAlertException;
import br.mp.mpro.caopapp.web.rest.util.HeaderUtil;
import br.mp.mpro.caopapp.web.rest.util.PaginationUtil;
import br.mp.mpro.caopapp.service.dto.FaixaEtariaFormDTO;
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
 * REST controller for managing FaixaEtariaForm.
 */
@RestController
@RequestMapping("/api")
public class FaixaEtariaFormResource {

    private final Logger log = LoggerFactory.getLogger(FaixaEtariaFormResource.class);

    private static final String ENTITY_NAME = "faixaEtariaForm";

    private final FaixaEtariaFormService faixaEtariaFormService;

    public FaixaEtariaFormResource(FaixaEtariaFormService faixaEtariaFormService) {
        this.faixaEtariaFormService = faixaEtariaFormService;
    }

    /**
     * POST  /faixa-etaria-forms : Create a new faixaEtariaForm.
     *
     * @param faixaEtariaFormDTO the faixaEtariaFormDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new faixaEtariaFormDTO, or with status 400 (Bad Request) if the faixaEtariaForm has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/faixa-etaria-forms")
    @Timed
    public ResponseEntity<FaixaEtariaFormDTO> createFaixaEtariaForm(@Valid @RequestBody FaixaEtariaFormDTO faixaEtariaFormDTO) throws URISyntaxException {
        log.debug("REST request to save FaixaEtariaForm : {}", faixaEtariaFormDTO);
        if (faixaEtariaFormDTO.getId() != null) {
            throw new BadRequestAlertException("A new faixaEtariaForm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FaixaEtariaFormDTO result = faixaEtariaFormService.save(faixaEtariaFormDTO);
        return ResponseEntity.created(new URI("/api/faixa-etaria-forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /faixa-etaria-forms : Updates an existing faixaEtariaForm.
     *
     * @param faixaEtariaFormDTO the faixaEtariaFormDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated faixaEtariaFormDTO,
     * or with status 400 (Bad Request) if the faixaEtariaFormDTO is not valid,
     * or with status 500 (Internal Server Error) if the faixaEtariaFormDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/faixa-etaria-forms")
    @Timed
    public ResponseEntity<FaixaEtariaFormDTO> updateFaixaEtariaForm(@Valid @RequestBody FaixaEtariaFormDTO faixaEtariaFormDTO) throws URISyntaxException {
        log.debug("REST request to update FaixaEtariaForm : {}", faixaEtariaFormDTO);
        if (faixaEtariaFormDTO.getId() == null) {
            return createFaixaEtariaForm(faixaEtariaFormDTO);
        }
        FaixaEtariaFormDTO result = faixaEtariaFormService.save(faixaEtariaFormDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, faixaEtariaFormDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /faixa-etaria-forms : get all the faixaEtariaForms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of faixaEtariaForms in body
     */
    @GetMapping("/faixa-etaria-forms")
    @Timed
    public ResponseEntity<List<FaixaEtariaFormDTO>> getAllFaixaEtariaForms(Pageable pageable) {
        log.debug("REST request to get a page of FaixaEtariaForms");
        Page<FaixaEtariaFormDTO> page = faixaEtariaFormService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/faixa-etaria-forms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /faixa-etaria-forms/:id : get the "id" faixaEtariaForm.
     *
     * @param id the id of the faixaEtariaFormDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the faixaEtariaFormDTO, or with status 404 (Not Found)
     */
    @GetMapping("/faixa-etaria-forms/{id}")
    @Timed
    public ResponseEntity<FaixaEtariaFormDTO> getFaixaEtariaForm(@PathVariable Long id) {
        log.debug("REST request to get FaixaEtariaForm : {}", id);
        FaixaEtariaFormDTO faixaEtariaFormDTO = faixaEtariaFormService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(faixaEtariaFormDTO));
    }

    /**
     * DELETE  /faixa-etaria-forms/:id : delete the "id" faixaEtariaForm.
     *
     * @param id the id of the faixaEtariaFormDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/faixa-etaria-forms/{id}")
    @Timed
    public ResponseEntity<Void> deleteFaixaEtariaForm(@PathVariable Long id) {
        log.debug("REST request to delete FaixaEtariaForm : {}", id);
        faixaEtariaFormService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
