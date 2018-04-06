package br.mp.mpro.caopapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mp.mpro.caopapp.service.ServicoPublicoService;
import br.mp.mpro.caopapp.web.rest.errors.BadRequestAlertException;
import br.mp.mpro.caopapp.web.rest.util.HeaderUtil;
import br.mp.mpro.caopapp.web.rest.util.PaginationUtil;
import br.mp.mpro.caopapp.service.dto.ServicoPublicoDTO;
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
 * REST controller for managing ServicoPublico.
 */
@RestController
@RequestMapping("/api")
public class ServicoPublicoResource {

    private final Logger log = LoggerFactory.getLogger(ServicoPublicoResource.class);

    private static final String ENTITY_NAME = "servicoPublico";

    private final ServicoPublicoService servicoPublicoService;

    public ServicoPublicoResource(ServicoPublicoService servicoPublicoService) {
        this.servicoPublicoService = servicoPublicoService;
    }

    /**
     * POST  /servico-publicos : Create a new servicoPublico.
     *
     * @param servicoPublicoDTO the servicoPublicoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new servicoPublicoDTO, or with status 400 (Bad Request) if the servicoPublico has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/servico-publicos")
    @Timed
    public ResponseEntity<ServicoPublicoDTO> createServicoPublico(@Valid @RequestBody ServicoPublicoDTO servicoPublicoDTO) throws URISyntaxException {
        log.debug("REST request to save ServicoPublico : {}", servicoPublicoDTO);
        if (servicoPublicoDTO.getId() != null) {
            throw new BadRequestAlertException("A new servicoPublico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServicoPublicoDTO result = servicoPublicoService.save(servicoPublicoDTO);
        return ResponseEntity.created(new URI("/api/servico-publicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /servico-publicos : Updates an existing servicoPublico.
     *
     * @param servicoPublicoDTO the servicoPublicoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated servicoPublicoDTO,
     * or with status 400 (Bad Request) if the servicoPublicoDTO is not valid,
     * or with status 500 (Internal Server Error) if the servicoPublicoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/servico-publicos")
    @Timed
    public ResponseEntity<ServicoPublicoDTO> updateServicoPublico(@Valid @RequestBody ServicoPublicoDTO servicoPublicoDTO) throws URISyntaxException {
        log.debug("REST request to update ServicoPublico : {}", servicoPublicoDTO);
        if (servicoPublicoDTO.getId() == null) {
            return createServicoPublico(servicoPublicoDTO);
        }
        ServicoPublicoDTO result = servicoPublicoService.save(servicoPublicoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, servicoPublicoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /servico-publicos : get all the servicoPublicos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of servicoPublicos in body
     */
    @GetMapping("/servico-publicos")
    @Timed
    public ResponseEntity<List<ServicoPublicoDTO>> getAllServicoPublicos(Pageable pageable) {
        log.debug("REST request to get a page of ServicoPublicos");
        Page<ServicoPublicoDTO> page = servicoPublicoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/servico-publicos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /servico-publicos/:id : get the "id" servicoPublico.
     *
     * @param id the id of the servicoPublicoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the servicoPublicoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/servico-publicos/{id}")
    @Timed
    public ResponseEntity<ServicoPublicoDTO> getServicoPublico(@PathVariable Long id) {
        log.debug("REST request to get ServicoPublico : {}", id);
        ServicoPublicoDTO servicoPublicoDTO = servicoPublicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(servicoPublicoDTO));
    }

    /**
     * DELETE  /servico-publicos/:id : delete the "id" servicoPublico.
     *
     * @param id the id of the servicoPublicoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/servico-publicos/{id}")
    @Timed
    public ResponseEntity<Void> deleteServicoPublico(@PathVariable Long id) {
        log.debug("REST request to delete ServicoPublico : {}", id);
        servicoPublicoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
