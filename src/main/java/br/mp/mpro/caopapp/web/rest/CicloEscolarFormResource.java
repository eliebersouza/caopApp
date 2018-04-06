package br.mp.mpro.caopapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mp.mpro.caopapp.service.CicloEscolarFormService;
import br.mp.mpro.caopapp.web.rest.errors.BadRequestAlertException;
import br.mp.mpro.caopapp.web.rest.util.HeaderUtil;
import br.mp.mpro.caopapp.web.rest.util.PaginationUtil;
import br.mp.mpro.caopapp.service.dto.CicloEscolarFormDTO;
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
 * REST controller for managing CicloEscolarForm.
 */
@RestController
@RequestMapping("/api")
public class CicloEscolarFormResource {

    private final Logger log = LoggerFactory.getLogger(CicloEscolarFormResource.class);

    private static final String ENTITY_NAME = "cicloEscolarForm";

    private final CicloEscolarFormService cicloEscolarFormService;

    public CicloEscolarFormResource(CicloEscolarFormService cicloEscolarFormService) {
        this.cicloEscolarFormService = cicloEscolarFormService;
    }

    /**
     * POST  /ciclo-escolar-forms : Create a new cicloEscolarForm.
     *
     * @param cicloEscolarFormDTO the cicloEscolarFormDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cicloEscolarFormDTO, or with status 400 (Bad Request) if the cicloEscolarForm has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ciclo-escolar-forms")
    @Timed
    public ResponseEntity<CicloEscolarFormDTO> createCicloEscolarForm(@Valid @RequestBody CicloEscolarFormDTO cicloEscolarFormDTO) throws URISyntaxException {
        log.debug("REST request to save CicloEscolarForm : {}", cicloEscolarFormDTO);
        if (cicloEscolarFormDTO.getId() != null) {
            throw new BadRequestAlertException("A new cicloEscolarForm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CicloEscolarFormDTO result = cicloEscolarFormService.save(cicloEscolarFormDTO);
        return ResponseEntity.created(new URI("/api/ciclo-escolar-forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ciclo-escolar-forms : Updates an existing cicloEscolarForm.
     *
     * @param cicloEscolarFormDTO the cicloEscolarFormDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cicloEscolarFormDTO,
     * or with status 400 (Bad Request) if the cicloEscolarFormDTO is not valid,
     * or with status 500 (Internal Server Error) if the cicloEscolarFormDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ciclo-escolar-forms")
    @Timed
    public ResponseEntity<CicloEscolarFormDTO> updateCicloEscolarForm(@Valid @RequestBody CicloEscolarFormDTO cicloEscolarFormDTO) throws URISyntaxException {
        log.debug("REST request to update CicloEscolarForm : {}", cicloEscolarFormDTO);
        if (cicloEscolarFormDTO.getId() == null) {
            return createCicloEscolarForm(cicloEscolarFormDTO);
        }
        CicloEscolarFormDTO result = cicloEscolarFormService.save(cicloEscolarFormDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cicloEscolarFormDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ciclo-escolar-forms : get all the cicloEscolarForms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cicloEscolarForms in body
     */
    @GetMapping("/ciclo-escolar-forms")
    @Timed
    public ResponseEntity<List<CicloEscolarFormDTO>> getAllCicloEscolarForms(Pageable pageable) {
        log.debug("REST request to get a page of CicloEscolarForms");
        Page<CicloEscolarFormDTO> page = cicloEscolarFormService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ciclo-escolar-forms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ciclo-escolar-forms/:id : get the "id" cicloEscolarForm.
     *
     * @param id the id of the cicloEscolarFormDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cicloEscolarFormDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ciclo-escolar-forms/{id}")
    @Timed
    public ResponseEntity<CicloEscolarFormDTO> getCicloEscolarForm(@PathVariable Long id) {
        log.debug("REST request to get CicloEscolarForm : {}", id);
        CicloEscolarFormDTO cicloEscolarFormDTO = cicloEscolarFormService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cicloEscolarFormDTO));
    }

    /**
     * DELETE  /ciclo-escolar-forms/:id : delete the "id" cicloEscolarForm.
     *
     * @param id the id of the cicloEscolarFormDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ciclo-escolar-forms/{id}")
    @Timed
    public ResponseEntity<Void> deleteCicloEscolarForm(@PathVariable Long id) {
        log.debug("REST request to delete CicloEscolarForm : {}", id);
        cicloEscolarFormService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
