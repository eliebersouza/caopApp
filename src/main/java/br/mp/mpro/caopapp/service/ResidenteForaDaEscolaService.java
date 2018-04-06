package br.mp.mpro.caopapp.service;

import br.mp.mpro.caopapp.service.dto.ResidenteForaDaEscolaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ResidenteForaDaEscola.
 */
public interface ResidenteForaDaEscolaService {

    /**
     * Save a residenteForaDaEscola.
     *
     * @param residenteForaDaEscolaDTO the entity to save
     * @return the persisted entity
     */
    ResidenteForaDaEscolaDTO save(ResidenteForaDaEscolaDTO residenteForaDaEscolaDTO);

    /**
     * Get all the residenteForaDaEscolas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ResidenteForaDaEscolaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" residenteForaDaEscola.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ResidenteForaDaEscolaDTO findOne(Long id);

    /**
     * Delete the "id" residenteForaDaEscola.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
