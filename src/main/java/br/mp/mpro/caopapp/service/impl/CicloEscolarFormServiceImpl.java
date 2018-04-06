package br.mp.mpro.caopapp.service.impl;

import br.mp.mpro.caopapp.service.CicloEscolarFormService;
import br.mp.mpro.caopapp.domain.CicloEscolarForm;
import br.mp.mpro.caopapp.repository.CicloEscolarFormRepository;
import br.mp.mpro.caopapp.service.dto.CicloEscolarFormDTO;
import br.mp.mpro.caopapp.service.mapper.CicloEscolarFormMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CicloEscolarForm.
 */
@Service
@Transactional
public class CicloEscolarFormServiceImpl implements CicloEscolarFormService {

    private final Logger log = LoggerFactory.getLogger(CicloEscolarFormServiceImpl.class);

    private final CicloEscolarFormRepository cicloEscolarFormRepository;

    private final CicloEscolarFormMapper cicloEscolarFormMapper;

    public CicloEscolarFormServiceImpl(CicloEscolarFormRepository cicloEscolarFormRepository, CicloEscolarFormMapper cicloEscolarFormMapper) {
        this.cicloEscolarFormRepository = cicloEscolarFormRepository;
        this.cicloEscolarFormMapper = cicloEscolarFormMapper;
    }

    /**
     * Save a cicloEscolarForm.
     *
     * @param cicloEscolarFormDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CicloEscolarFormDTO save(CicloEscolarFormDTO cicloEscolarFormDTO) {
        log.debug("Request to save CicloEscolarForm : {}", cicloEscolarFormDTO);
        CicloEscolarForm cicloEscolarForm = cicloEscolarFormMapper.toEntity(cicloEscolarFormDTO);
        cicloEscolarForm = cicloEscolarFormRepository.save(cicloEscolarForm);
        return cicloEscolarFormMapper.toDto(cicloEscolarForm);
    }

    /**
     * Get all the cicloEscolarForms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CicloEscolarFormDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CicloEscolarForms");
        return cicloEscolarFormRepository.findAll(pageable)
            .map(cicloEscolarFormMapper::toDto);
    }

    /**
     * Get one cicloEscolarForm by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CicloEscolarFormDTO findOne(Long id) {
        log.debug("Request to get CicloEscolarForm : {}", id);
        CicloEscolarForm cicloEscolarForm = cicloEscolarFormRepository.findOne(id);
        return cicloEscolarFormMapper.toDto(cicloEscolarForm);
    }

    /**
     * Delete the cicloEscolarForm by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CicloEscolarForm : {}", id);
        cicloEscolarFormRepository.delete(id);
    }
}
