package br.mp.mpro.caopapp.service;

import br.mp.mpro.caopapp.service.dto.ServicoPublicoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ServicoPublico.
 */
public interface ServicoPublicoService {

    /**
     * Save a servicoPublico.
     *
     * @param servicoPublicoDTO the entity to save
     * @return the persisted entity
     */
    ServicoPublicoDTO save(ServicoPublicoDTO servicoPublicoDTO);

    /**
     * Get all the servicoPublicos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ServicoPublicoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" servicoPublico.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ServicoPublicoDTO findOne(Long id);

    /**
     * Delete the "id" servicoPublico.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
