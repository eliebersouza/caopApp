package br.mp.mpro.caopapp.service.impl;

import br.mp.mpro.caopapp.service.FaixaEtariaFormService;
import br.mp.mpro.caopapp.domain.FaixaEtariaForm;
import br.mp.mpro.caopapp.repository.FaixaEtariaFormRepository;
import br.mp.mpro.caopapp.service.dto.FaixaEtariaFormDTO;
import br.mp.mpro.caopapp.service.mapper.FaixaEtariaFormMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing FaixaEtariaForm.
 */
@Service
@Transactional
public class FaixaEtariaFormServiceImpl implements FaixaEtariaFormService {

    private final Logger log = LoggerFactory.getLogger(FaixaEtariaFormServiceImpl.class);

    private final FaixaEtariaFormRepository faixaEtariaFormRepository;

    private final FaixaEtariaFormMapper faixaEtariaFormMapper;

    public FaixaEtariaFormServiceImpl(FaixaEtariaFormRepository faixaEtariaFormRepository, FaixaEtariaFormMapper faixaEtariaFormMapper) {
        this.faixaEtariaFormRepository = faixaEtariaFormRepository;
        this.faixaEtariaFormMapper = faixaEtariaFormMapper;
    }

    /**
     * Save a faixaEtariaForm.
     *
     * @param faixaEtariaFormDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FaixaEtariaFormDTO save(FaixaEtariaFormDTO faixaEtariaFormDTO) {
        log.debug("Request to save FaixaEtariaForm : {}", faixaEtariaFormDTO);
        FaixaEtariaForm faixaEtariaForm = faixaEtariaFormMapper.toEntity(faixaEtariaFormDTO);
        faixaEtariaForm = faixaEtariaFormRepository.save(faixaEtariaForm);
        return faixaEtariaFormMapper.toDto(faixaEtariaForm);
    }

    /**
     * Get all the faixaEtariaForms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FaixaEtariaFormDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FaixaEtariaForms");
        return faixaEtariaFormRepository.findAll(pageable)
            .map(faixaEtariaFormMapper::toDto);
    }

    /**
     * Get one faixaEtariaForm by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FaixaEtariaFormDTO findOne(Long id) {
        log.debug("Request to get FaixaEtariaForm : {}", id);
        FaixaEtariaForm faixaEtariaForm = faixaEtariaFormRepository.findOne(id);
        return faixaEtariaFormMapper.toDto(faixaEtariaForm);
    }

    /**
     * Delete the faixaEtariaForm by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FaixaEtariaForm : {}", id);
        faixaEtariaFormRepository.delete(id);
    }
}
