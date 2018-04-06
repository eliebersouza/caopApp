package br.mp.mpro.caopapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mp.mpro.caopapp.service.ResidenteForaDaEscolaService;
import br.mp.mpro.caopapp.web.rest.errors.BadRequestAlertException;
import br.mp.mpro.caopapp.web.rest.util.HeaderUtil;
import br.mp.mpro.caopapp.web.rest.util.PaginationUtil;
import br.mp.mpro.caopapp.service.dto.ResidenteForaDaEscolaDTO;
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
 * REST controller for managing ResidenteForaDaEscola.
 */
@RestController
@RequestMapping("/api")
public class ResidenteForaDaEscolaResource {

    private final Logger log = LoggerFactory.getLogger(ResidenteForaDaEscolaResource.class);

    private static final String ENTITY_NAME = "residenteForaDaEscola";

    private final ResidenteForaDaEscolaService residenteForaDaEscolaService;

    public ResidenteForaDaEscolaResource(ResidenteForaDaEscolaService residenteForaDaEscolaService) {
        this.residenteForaDaEscolaService = residenteForaDaEscolaService;
    }

    /**
     * POST  /residente-fora-da-escolas : Create a new residenteForaDaEscola.
     *
     * @param residenteForaDaEscolaDTO the residenteForaDaEscolaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new residenteForaDaEscolaDTO, or with status 400 (Bad Request) if the residenteForaDaEscola has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/residente-fora-da-escolas")
    @Timed
    public ResponseEntity<ResidenteForaDaEscolaDTO> createResidenteForaDaEscola(@Valid @RequestBody ResidenteForaDaEscolaDTO residenteForaDaEscolaDTO) throws URISyntaxException {
        log.debug("REST request to save ResidenteForaDaEscola : {}", residenteForaDaEscolaDTO);
        if (residenteForaDaEscolaDTO.getId() != null) {
            throw new BadRequestAlertException("A new residenteForaDaEscola cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResidenteForaDaEscolaDTO result = residenteForaDaEscolaService.save(residenteForaDaEscolaDTO);
        return ResponseEntity.created(new URI("/api/residente-fora-da-escolas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /residente-fora-da-escolas : Updates an existing residenteForaDaEscola.
     *
     * @param residenteForaDaEscolaDTO the residenteForaDaEscolaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated residenteForaDaEscolaDTO,
     * or with status 400 (Bad Request) if the residenteForaDaEscolaDTO is not valid,
     * or with status 500 (Internal Server Error) if the residenteForaDaEscolaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/residente-fora-da-escolas")
    @Timed
    public ResponseEntity<ResidenteForaDaEscolaDTO> updateResidenteForaDaEscola(@Valid @RequestBody ResidenteForaDaEscolaDTO residenteForaDaEscolaDTO) throws URISyntaxException {
        log.debug("REST request to update ResidenteForaDaEscola : {}", residenteForaDaEscolaDTO);
        if (residenteForaDaEscolaDTO.getId() == null) {
            return createResidenteForaDaEscola(residenteForaDaEscolaDTO);
        }
        ResidenteForaDaEscolaDTO result = residenteForaDaEscolaService.save(residenteForaDaEscolaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, residenteForaDaEscolaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /residente-fora-da-escolas : get all the residenteForaDaEscolas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of residenteForaDaEscolas in body
     */
    @GetMapping("/residente-fora-da-escolas")
    @Timed
    public ResponseEntity<List<ResidenteForaDaEscolaDTO>> getAllResidenteForaDaEscolas(Pageable pageable) {
        log.debug("REST request to get a page of ResidenteForaDaEscolas");
        Page<ResidenteForaDaEscolaDTO> page = residenteForaDaEscolaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/residente-fora-da-escolas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /residente-fora-da-escolas/:id : get the "id" residenteForaDaEscola.
     *
     * @param id the id of the residenteForaDaEscolaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the residenteForaDaEscolaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/residente-fora-da-escolas/{id}")
    @Timed
    public ResponseEntity<ResidenteForaDaEscolaDTO> getResidenteForaDaEscola(@PathVariable Long id) {
        log.debug("REST request to get ResidenteForaDaEscola : {}", id);
        ResidenteForaDaEscolaDTO residenteForaDaEscolaDTO = residenteForaDaEscolaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(residenteForaDaEscolaDTO));
    }

    /**
     * DELETE  /residente-fora-da-escolas/:id : delete the "id" residenteForaDaEscola.
     *
     * @param id the id of the residenteForaDaEscolaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/residente-fora-da-escolas/{id}")
    @Timed
    public ResponseEntity<Void> deleteResidenteForaDaEscola(@PathVariable Long id) {
        log.debug("REST request to delete ResidenteForaDaEscola : {}", id);
        residenteForaDaEscolaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
