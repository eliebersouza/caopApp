package br.mp.mpro.caopapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mp.mpro.caopapp.service.FormService;
import br.mp.mpro.caopapp.web.rest.errors.BadRequestAlertException;
import br.mp.mpro.caopapp.web.rest.util.HeaderUtil;
import br.mp.mpro.caopapp.web.rest.util.PaginationUtil;
import br.mp.mpro.caopapp.service.dto.FormDTO;
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
 * REST controller for managing Form.
 */
@RestController
@RequestMapping("/api")
public class FormResource {

    private final Logger log = LoggerFactory.getLogger(FormResource.class);

    private static final String ENTITY_NAME = "form";

    private final FormService formService;

    public FormResource(FormService formService) {
        this.formService = formService;
    }

    /**
     * POST  /forms : Create a new form.
     *
     * @param formDTO the formDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new formDTO, or with status 400 (Bad Request) if the form has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/forms")
    @Timed
    public ResponseEntity<FormDTO> createForm(@Valid @RequestBody FormDTO formDTO) throws URISyntaxException {
        log.debug("REST request to save Form : {}", formDTO);
        if (formDTO.getId() != null) {
            throw new BadRequestAlertException("A new form cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormDTO result = formService.save(formDTO);
        return ResponseEntity.created(new URI("/api/forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /forms : Updates an existing form.
     *
     * @param formDTO the formDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated formDTO,
     * or with status 400 (Bad Request) if the formDTO is not valid,
     * or with status 500 (Internal Server Error) if the formDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/forms")
    @Timed
    public ResponseEntity<FormDTO> updateForm(@Valid @RequestBody FormDTO formDTO) throws URISyntaxException {
        log.debug("REST request to update Form : {}", formDTO);
        if (formDTO.getId() == null) {
            return createForm(formDTO);
        }
        FormDTO result = formService.save(formDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, formDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /forms : get all the forms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of forms in body
     */
    @GetMapping("/forms")
    @Timed
    public ResponseEntity<List<FormDTO>> getAllForms(Pageable pageable) {
        log.debug("REST request to get a page of Forms");
        Page<FormDTO> page = formService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/forms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /forms/:id : get the "id" form.
     *
     * @param id the id of the formDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the formDTO, or with status 404 (Not Found)
     */
    @GetMapping("/forms/{id}")
    @Timed
    public ResponseEntity<FormDTO> getForm(@PathVariable Long id) {
        log.debug("REST request to get Form : {}", id);
        FormDTO formDTO = formService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(formDTO));
    }

    /**
     * DELETE  /forms/:id : delete the "id" form.
     *
     * @param id the id of the formDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/forms/{id}")
    @Timed
    public ResponseEntity<Void> deleteForm(@PathVariable Long id) {
        log.debug("REST request to delete Form : {}", id);
        formService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
