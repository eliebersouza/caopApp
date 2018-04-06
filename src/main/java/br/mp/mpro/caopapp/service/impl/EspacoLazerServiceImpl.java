package br.mp.mpro.caopapp.service.impl;

import br.mp.mpro.caopapp.service.EspacoLazerService;
import br.mp.mpro.caopapp.domain.EspacoLazer;
import br.mp.mpro.caopapp.repository.EspacoLazerRepository;
import br.mp.mpro.caopapp.service.dto.EspacoLazerDTO;
import br.mp.mpro.caopapp.service.mapper.EspacoLazerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing EspacoLazer.
 */
@Service
@Transactional
public class EspacoLazerServiceImpl implements EspacoLazerService {

    private final Logger log = LoggerFactory.getLogger(EspacoLazerServiceImpl.class);

    private final EspacoLazerRepository espacoLazerRepository;

    private final EspacoLazerMapper espacoLazerMapper;

    public EspacoLazerServiceImpl(EspacoLazerRepository espacoLazerRepository, EspacoLazerMapper espacoLazerMapper) {
        this.espacoLazerRepository = espacoLazerRepository;
        this.espacoLazerMapper = espacoLazerMapper;
    }

    /**
     * Save a espacoLazer.
     *
     * @param espacoLazerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EspacoLazerDTO save(EspacoLazerDTO espacoLazerDTO) {
        log.debug("Request to save EspacoLazer : {}", espacoLazerDTO);
        EspacoLazer espacoLazer = espacoLazerMapper.toEntity(espacoLazerDTO);
        espacoLazer = espacoLazerRepository.save(espacoLazer);
        return espacoLazerMapper.toDto(espacoLazer);
    }

    /**
     * Get all the espacoLazers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EspacoLazerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EspacoLazers");
        return espacoLazerRepository.findAll(pageable)
            .map(espacoLazerMapper::toDto);
    }

    /**
     * Get one espacoLazer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EspacoLazerDTO findOne(Long id) {
        log.debug("Request to get EspacoLazer : {}", id);
        EspacoLazer espacoLazer = espacoLazerRepository.findOne(id);
        return espacoLazerMapper.toDto(espacoLazer);
    }

    /**
     * Delete the espacoLazer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EspacoLazer : {}", id);
        espacoLazerRepository.delete(id);
    }
}
