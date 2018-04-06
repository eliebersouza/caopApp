package br.mp.mpro.caopapp.service;

import br.mp.mpro.caopapp.service.dto.CicloEscolarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CicloEscolar.
 */
public interface CicloEscolarService {

    /**
     * Save a cicloEscolar.
     *
     * @param cicloEscolarDTO the entity to save
     * @return the persisted entity
     */
    CicloEscolarDTO save(CicloEscolarDTO cicloEscolarDTO);

    /**
     * Get all the cicloEscolars.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CicloEscolarDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cicloEscolar.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CicloEscolarDTO findOne(Long id);

    /**
     * Delete the "id" cicloEscolar.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
