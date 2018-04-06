package br.mp.mpro.caopapp.service.impl;

import br.mp.mpro.caopapp.service.ResidenteForaDaEscolaService;
import br.mp.mpro.caopapp.domain.ResidenteForaDaEscola;
import br.mp.mpro.caopapp.repository.ResidenteForaDaEscolaRepository;
import br.mp.mpro.caopapp.service.dto.ResidenteForaDaEscolaDTO;
import br.mp.mpro.caopapp.service.mapper.ResidenteForaDaEscolaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ResidenteForaDaEscola.
 */
@Service
@Transactional
public class ResidenteForaDaEscolaServiceImpl implements ResidenteForaDaEscolaService {

    private final Logger log = LoggerFactory.getLogger(ResidenteForaDaEscolaServiceImpl.class);

    private final ResidenteForaDaEscolaRepository residenteForaDaEscolaRepository;

    private final ResidenteForaDaEscolaMapper residenteForaDaEscolaMapper;

    public ResidenteForaDaEscolaServiceImpl(ResidenteForaDaEscolaRepository residenteForaDaEscolaRepository, ResidenteForaDaEscolaMapper residenteForaDaEscolaMapper) {
        this.residenteForaDaEscolaRepository = residenteForaDaEscolaRepository;
        this.residenteForaDaEscolaMapper = residenteForaDaEscolaMapper;
    }

    /**
     * Save a residenteForaDaEscola.
     *
     * @param residenteForaDaEscolaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ResidenteForaDaEscolaDTO save(ResidenteForaDaEscolaDTO residenteForaDaEscolaDTO) {
        log.debug("Request to save ResidenteForaDaEscola : {}", residenteForaDaEscolaDTO);
        ResidenteForaDaEscola residenteForaDaEscola = residenteForaDaEscolaMapper.toEntity(residenteForaDaEscolaDTO);
        residenteForaDaEscola = residenteForaDaEscolaRepository.save(residenteForaDaEscola);
        return residenteForaDaEscolaMapper.toDto(residenteForaDaEscola);
    }

    /**
     * Get all the residenteForaDaEscolas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ResidenteForaDaEscolaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResidenteForaDaEscolas");
        return residenteForaDaEscolaRepository.findAll(pageable)
            .map(residenteForaDaEscolaMapper::toDto);
    }

    /**
     * Get one residenteForaDaEscola by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ResidenteForaDaEscolaDTO findOne(Long id) {
        log.debug("Request to get ResidenteForaDaEscola : {}", id);
        ResidenteForaDaEscola residenteForaDaEscola = residenteForaDaEscolaRepository.findOne(id);
        return residenteForaDaEscolaMapper.toDto(residenteForaDaEscola);
    }

    /**
     * Delete the residenteForaDaEscola by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResidenteForaDaEscola : {}", id);
        residenteForaDaEscolaRepository.delete(id);
    }
}
