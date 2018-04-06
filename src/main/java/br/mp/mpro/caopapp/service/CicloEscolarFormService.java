package br.mp.mpro.caopapp.service;

import br.mp.mpro.caopapp.service.dto.CicloEscolarFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CicloEscolarForm.
 */
public interface CicloEscolarFormService {

    /**
     * Save a cicloEscolarForm.
     *
     * @param cicloEscolarFormDTO the entity to save
     * @return the persisted entity
     */
    CicloEscolarFormDTO save(CicloEscolarFormDTO cicloEscolarFormDTO);

    /**
     * Get all the cicloEscolarForms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CicloEscolarFormDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cicloEscolarForm.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CicloEscolarFormDTO findOne(Long id);

    /**
     * Delete the "id" cicloEscolarForm.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
