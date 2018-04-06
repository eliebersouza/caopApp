package br.mp.mpro.caopapp.service;

import br.mp.mpro.caopapp.service.dto.ResponsavelLarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ResponsavelLar.
 */
public interface ResponsavelLarService {

    /**
     * Save a responsavelLar.
     *
     * @param responsavelLarDTO the entity to save
     * @return the persisted entity
     */
    ResponsavelLarDTO save(ResponsavelLarDTO responsavelLarDTO);

    /**
     * Get all the responsavelLars.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ResponsavelLarDTO> findAll(Pageable pageable);

    /**
     * Get the "id" responsavelLar.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ResponsavelLarDTO findOne(Long id);

    /**
     * Delete the "id" responsavelLar.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
