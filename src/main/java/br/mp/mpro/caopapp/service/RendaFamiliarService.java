package br.mp.mpro.caopapp.service;

import br.mp.mpro.caopapp.service.dto.RendaFamiliarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RendaFamiliar.
 */
public interface RendaFamiliarService {

    /**
     * Save a rendaFamiliar.
     *
     * @param rendaFamiliarDTO the entity to save
     * @return the persisted entity
     */
    RendaFamiliarDTO save(RendaFamiliarDTO rendaFamiliarDTO);

    /**
     * Get all the rendaFamiliars.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RendaFamiliarDTO> findAll(Pageable pageable);

    /**
     * Get the "id" rendaFamiliar.
     *
     * @param id the id of the entity
     * @return the entity
     */
    RendaFamiliarDTO findOne(Long id);

    /**
     * Delete the "id" rendaFamiliar.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
