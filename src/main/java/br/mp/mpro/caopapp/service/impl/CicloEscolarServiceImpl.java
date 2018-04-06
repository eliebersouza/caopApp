package br.mp.mpro.caopapp.service.impl;

import br.mp.mpro.caopapp.service.CicloEscolarService;
import br.mp.mpro.caopapp.domain.CicloEscolar;
import br.mp.mpro.caopapp.repository.CicloEscolarRepository;
import br.mp.mpro.caopapp.service.dto.CicloEscolarDTO;
import br.mp.mpro.caopapp.service.mapper.CicloEscolarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CicloEscolar.
 */
@Service
@Transactional
public class CicloEscolarServiceImpl implements CicloEscolarService {

    private final Logger log = LoggerFactory.getLogger(CicloEscolarServiceImpl.class);

    private final CicloEscolarRepository cicloEscolarRepository;

    private final CicloEscolarMapper cicloEscolarMapper;

    public CicloEscolarServiceImpl(CicloEscolarRepository cicloEscolarRepository, CicloEscolarMapper cicloEscolarMapper) {
        this.cicloEscolarRepository = cicloEscolarRepository;
        this.cicloEscolarMapper = cicloEscolarMapper;
    }

    /**
     * Save a cicloEscolar.
     *
     * @param cicloEscolarDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CicloEscolarDTO save(CicloEscolarDTO cicloEscolarDTO) {
        log.debug("Request to save CicloEscolar : {}", cicloEscolarDTO);
        CicloEscolar cicloEscolar = cicloEscolarMapper.toEntity(cicloEscolarDTO);
        cicloEscolar = cicloEscolarRepository.save(cicloEscolar);
        return cicloEscolarMapper.toDto(cicloEscolar);
    }

    /**
     * Get all the cicloEscolars.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CicloEscolarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CicloEscolars");
        return cicloEscolarRepository.findAll(pageable)
            .map(cicloEscolarMapper::toDto);
    }

    /**
     * Get one cicloEscolar by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CicloEscolarDTO findOne(Long id) {
        log.debug("Request to get CicloEscolar : {}", id);
        CicloEscolar cicloEscolar = cicloEscolarRepository.findOne(id);
        return cicloEscolarMapper.toDto(cicloEscolar);
    }

    /**
     * Delete the cicloEscolar by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CicloEscolar : {}", id);
        cicloEscolarRepository.delete(id);
    }
}
