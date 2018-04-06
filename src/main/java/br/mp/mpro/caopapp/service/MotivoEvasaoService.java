package br.mp.mpro.caopapp.service;

import br.mp.mpro.caopapp.service.dto.MotivoEvasaoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MotivoEvasao.
 */
public interface MotivoEvasaoService {

    /**
     * Save a motivoEvasao.
     *
     * @param motivoEvasaoDTO the entity to save
     * @return the persisted entity
     */
    MotivoEvasaoDTO save(MotivoEvasaoDTO motivoEvasaoDTO);

    /**
     * Get all the motivoEvasaos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MotivoEvasaoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" motivoEvasao.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MotivoEvasaoDTO findOne(Long id);

    /**
     * Delete the "id" motivoEvasao.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
