package br.mp.mpro.caopapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mp.mpro.caopapp.service.EspacoLazerService;
import br.mp.mpro.caopapp.web.rest.errors.BadRequestAlertException;
import br.mp.mpro.caopapp.web.rest.util.HeaderUtil;
import br.mp.mpro.caopapp.web.rest.util.PaginationUtil;
import br.mp.mpro.caopapp.service.dto.EspacoLazerDTO;
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
 * REST controller for managing EspacoLazer.
 */
@RestController
@RequestMapping("/api")
public class EspacoLazerResource {

    private final Logger log = LoggerFactory.getLogger(EspacoLazerResource.class);

    private static final String ENTITY_NAME = "espacoLazer";

    private final EspacoLazerService espacoLazerService;

    public EspacoLazerResource(EspacoLazerService espacoLazerService) {
        this.espacoLazerService = espacoLazerService;
    }

    /**
     * POST  /espaco-lazers : Create a new espacoLazer.
     *
     * @param espacoLazerDTO the espacoLazerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new espacoLazerDTO, or with status 400 (Bad Request) if the espacoLazer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/espaco-lazers")
    @Timed
    public ResponseEntity<EspacoLazerDTO> createEspacoLazer(@Valid @RequestBody EspacoLazerDTO espacoLazerDTO) throws URISyntaxException {
        log.debug("REST request to save EspacoLazer : {}", espacoLazerDTO);
        if (espacoLazerDTO.getId() != null) {
            throw new BadRequestAlertException("A new espacoLazer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EspacoLazerDTO result = espacoLazerService.save(espacoLazerDTO);
        return ResponseEntity.created(new URI("/api/espaco-lazers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /espaco-lazers : Updates an existing espacoLazer.
     *
     * @param espacoLazerDTO the espacoLazerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated espacoLazerDTO,
     * or with status 400 (Bad Request) if the espacoLazerDTO is not valid,
     * or with status 500 (Internal Server Error) if the espacoLazerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/espaco-lazers")
    @Timed
    public ResponseEntity<EspacoLazerDTO> updateEspacoLazer(@Valid @RequestBody EspacoLazerDTO espacoLazerDTO) throws URISyntaxException {
        log.debug("REST request to update EspacoLazer : {}", espacoLazerDTO);
        if (espacoLazerDTO.getId() == null) {
            return createEspacoLazer(espacoLazerDTO);
        }
        EspacoLazerDTO result = espacoLazerService.save(espacoLazerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, espacoLazerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /espaco-lazers : get all the espacoLazers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of espacoLazers in body
     */
    @GetMapping("/espaco-lazers")
    @Timed
    public ResponseEntity<List<EspacoLazerDTO>> getAllEspacoLazers(Pageable pageable) {
        log.debug("REST request to get a page of EspacoLazers");
        Page<EspacoLazerDTO> page = espacoLazerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/espaco-lazers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /espaco-lazers/:id : get the "id" espacoLazer.
     *
     * @param id the id of the espacoLazerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the espacoLazerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/espaco-lazers/{id}")
    @Timed
    public ResponseEntity<EspacoLazerDTO> getEspacoLazer(@PathVariable Long id) {
        log.debug("REST request to get EspacoLazer : {}", id);
        EspacoLazerDTO espacoLazerDTO = espacoLazerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(espacoLazerDTO));
    }

    /**
     * DELETE  /espaco-lazers/:id : delete the "id" espacoLazer.
     *
     * @param id the id of the espacoLazerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/espaco-lazers/{id}")
    @Timed
    public ResponseEntity<Void> deleteEspacoLazer(@PathVariable Long id) {
        log.debug("REST request to delete EspacoLazer : {}", id);
        espacoLazerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
