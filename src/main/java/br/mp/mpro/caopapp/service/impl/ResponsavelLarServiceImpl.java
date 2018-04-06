package br.mp.mpro.caopapp.service.impl;

import br.mp.mpro.caopapp.service.ResponsavelLarService;
import br.mp.mpro.caopapp.domain.ResponsavelLar;
import br.mp.mpro.caopapp.repository.ResponsavelLarRepository;
import br.mp.mpro.caopapp.service.dto.ResponsavelLarDTO;
import br.mp.mpro.caopapp.service.mapper.ResponsavelLarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ResponsavelLar.
 */
@Service
@Transactional
public class ResponsavelLarServiceImpl implements ResponsavelLarService {

    private final Logger log = LoggerFactory.getLogger(ResponsavelLarServiceImpl.class);

    private final ResponsavelLarRepository responsavelLarRepository;

    private final ResponsavelLarMapper responsavelLarMapper;

    public ResponsavelLarServiceImpl(ResponsavelLarRepository responsavelLarRepository, ResponsavelLarMapper responsavelLarMapper) {
        this.responsavelLarRepository = responsavelLarRepository;
        this.responsavelLarMapper = responsavelLarMapper;
    }

    /**
     * Save a responsavelLar.
     *
     * @param responsavelLarDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ResponsavelLarDTO save(ResponsavelLarDTO responsavelLarDTO) {
        log.debug("Request to save ResponsavelLar : {}", responsavelLarDTO);
        ResponsavelLar responsavelLar = responsavelLarMapper.toEntity(responsavelLarDTO);
        responsavelLar = responsavelLarRepository.save(responsavelLar);
        return responsavelLarMapper.toDto(responsavelLar);
    }

    /**
     * Get all the responsavelLars.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ResponsavelLarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResponsavelLars");
        return responsavelLarRepository.findAll(pageable)
            .map(responsavelLarMapper::toDto);
    }

    /**
     * Get one responsavelLar by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ResponsavelLarDTO findOne(Long id) {
        log.debug("Request to get ResponsavelLar : {}", id);
        ResponsavelLar responsavelLar = responsavelLarRepository.findOne(id);
        return responsavelLarMapper.toDto(responsavelLar);
    }

    /**
     * Delete the responsavelLar by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResponsavelLar : {}", id);
        responsavelLarRepository.delete(id);
    }
}
