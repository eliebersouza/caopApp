package br.mp.mpro.caopapp.service;

import br.mp.mpro.caopapp.service.dto.ReligiaoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Religiao.
 */
public interface ReligiaoService {

    /**
     * Save a religiao.
     *
     * @param religiaoDTO the entity to save
     * @return the persisted entity
     */
    ReligiaoDTO save(ReligiaoDTO religiaoDTO);

    /**
     * Get all the religiaos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ReligiaoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" religiao.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ReligiaoDTO findOne(Long id);

    /**
     * Delete the "id" religiao.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
