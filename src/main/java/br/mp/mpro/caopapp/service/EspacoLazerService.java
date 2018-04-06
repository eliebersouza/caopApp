package br.mp.mpro.caopapp.service;

import br.mp.mpro.caopapp.service.dto.EspacoLazerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing EspacoLazer.
 */
public interface EspacoLazerService {

    /**
     * Save a espacoLazer.
     *
     * @param espacoLazerDTO the entity to save
     * @return the persisted entity
     */
    EspacoLazerDTO save(EspacoLazerDTO espacoLazerDTO);

    /**
     * Get all the espacoLazers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EspacoLazerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" espacoLazer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EspacoLazerDTO findOne(Long id);

    /**
     * Delete the "id" espacoLazer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
