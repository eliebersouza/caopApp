package br.mp.mpro.caopapp.service;

import br.mp.mpro.caopapp.service.dto.FormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Form.
 */
public interface FormService {

    /**
     * Save a form.
     *
     * @param formDTO the entity to save
     * @return the persisted entity
     */
    FormDTO save(FormDTO formDTO);

    /**
     * Get all the forms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FormDTO> findAll(Pageable pageable);

    /**
     * Get the "id" form.
     *
     * @param id the id of the entity
     * @return the entity
     */
    FormDTO findOne(Long id);

    /**
     * Delete the "id" form.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
