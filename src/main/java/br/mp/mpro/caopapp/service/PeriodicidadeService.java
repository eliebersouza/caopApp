package br.mp.mpro.caopapp.service;

import br.mp.mpro.caopapp.service.dto.PeriodicidadeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Periodicidade.
 */
public interface PeriodicidadeService {

    /**
     * Save a periodicidade.
     *
     * @param periodicidadeDTO the entity to save
     * @return the persisted entity
     */
    PeriodicidadeDTO save(PeriodicidadeDTO periodicidadeDTO);

    /**
     * Get all the periodicidades.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PeriodicidadeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" periodicidade.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PeriodicidadeDTO findOne(Long id);

    /**
     * Delete the "id" periodicidade.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
