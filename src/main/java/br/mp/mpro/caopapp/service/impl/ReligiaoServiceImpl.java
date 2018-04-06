package br.mp.mpro.caopapp.service.impl;

import br.mp.mpro.caopapp.service.ReligiaoService;
import br.mp.mpro.caopapp.domain.Religiao;
import br.mp.mpro.caopapp.repository.ReligiaoRepository;
import br.mp.mpro.caopapp.service.dto.ReligiaoDTO;
import br.mp.mpro.caopapp.service.mapper.ReligiaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Religiao.
 */
@Service
@Transactional
public class ReligiaoServiceImpl implements ReligiaoService {

    private final Logger log = LoggerFactory.getLogger(ReligiaoServiceImpl.class);

    private final ReligiaoRepository religiaoRepository;

    private final ReligiaoMapper religiaoMapper;

    public ReligiaoServiceImpl(ReligiaoRepository religiaoRepository, ReligiaoMapper religiaoMapper) {
        this.religiaoRepository = religiaoRepository;
        this.religiaoMapper = religiaoMapper;
    }

    /**
     * Save a religiao.
     *
     * @param religiaoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReligiaoDTO save(ReligiaoDTO religiaoDTO) {
        log.debug("Request to save Religiao : {}", religiaoDTO);
        Religiao religiao = religiaoMapper.toEntity(religiaoDTO);
        religiao = religiaoRepository.save(religiao);
        return religiaoMapper.toDto(religiao);
    }

    /**
     * Get all the religiaos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReligiaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Religiaos");
        return religiaoRepository.findAll(pageable)
            .map(religiaoMapper::toDto);
    }

    /**
     * Get one religiao by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ReligiaoDTO findOne(Long id) {
        log.debug("Request to get Religiao : {}", id);
        Religiao religiao = religiaoRepository.findOne(id);
        return religiaoMapper.toDto(religiao);
    }

    /**
     * Delete the religiao by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Religiao : {}", id);
        religiaoRepository.delete(id);
    }
}
