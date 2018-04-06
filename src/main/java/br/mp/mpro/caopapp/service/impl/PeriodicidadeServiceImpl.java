package br.mp.mpro.caopapp.service.impl;

import br.mp.mpro.caopapp.service.PeriodicidadeService;
import br.mp.mpro.caopapp.domain.Periodicidade;
import br.mp.mpro.caopapp.repository.PeriodicidadeRepository;
import br.mp.mpro.caopapp.service.dto.PeriodicidadeDTO;
import br.mp.mpro.caopapp.service.mapper.PeriodicidadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Periodicidade.
 */
@Service
@Transactional
public class PeriodicidadeServiceImpl implements PeriodicidadeService {

    private final Logger log = LoggerFactory.getLogger(PeriodicidadeServiceImpl.class);

    private final PeriodicidadeRepository periodicidadeRepository;

    private final PeriodicidadeMapper periodicidadeMapper;

    public PeriodicidadeServiceImpl(PeriodicidadeRepository periodicidadeRepository, PeriodicidadeMapper periodicidadeMapper) {
        this.periodicidadeRepository = periodicidadeRepository;
        this.periodicidadeMapper = periodicidadeMapper;
    }

    /**
     * Save a periodicidade.
     *
     * @param periodicidadeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PeriodicidadeDTO save(PeriodicidadeDTO periodicidadeDTO) {
        log.debug("Request to save Periodicidade : {}", periodicidadeDTO);
        Periodicidade periodicidade = periodicidadeMapper.toEntity(periodicidadeDTO);
        periodicidade = periodicidadeRepository.save(periodicidade);
        return periodicidadeMapper.toDto(periodicidade);
    }

    /**
     * Get all the periodicidades.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PeriodicidadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Periodicidades");
        return periodicidadeRepository.findAll(pageable)
            .map(periodicidadeMapper::toDto);
    }

    /**
     * Get one periodicidade by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PeriodicidadeDTO findOne(Long id) {
        log.debug("Request to get Periodicidade : {}", id);
        Periodicidade periodicidade = periodicidadeRepository.findOne(id);
        return periodicidadeMapper.toDto(periodicidade);
    }

    /**
     * Delete the periodicidade by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Periodicidade : {}", id);
        periodicidadeRepository.delete(id);
    }
}
