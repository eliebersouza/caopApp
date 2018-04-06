package br.mp.mpro.caopapp.service;

import br.mp.mpro.caopapp.service.dto.FaixaEtariaFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FaixaEtariaForm.
 */
public interface FaixaEtariaFormService {

    /**
     * Save a faixaEtariaForm.
     *
     * @param faixaEtariaFormDTO the entity to save
     * @return the persisted entity
     */
    FaixaEtariaFormDTO save(FaixaEtariaFormDTO faixaEtariaFormDTO);

    /**
     * Get all the faixaEtariaForms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FaixaEtariaFormDTO> findAll(Pageable pageable);

    /**
     * Get the "id" faixaEtariaForm.
     *
     * @param id the id of the entity
     * @return the entity
     */
    FaixaEtariaFormDTO findOne(Long id);

    /**
     * Delete the "id" faixaEtariaForm.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
