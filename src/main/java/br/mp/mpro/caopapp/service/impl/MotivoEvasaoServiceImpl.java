package br.mp.mpro.caopapp.service.impl;

import br.mp.mpro.caopapp.service.MotivoEvasaoService;
import br.mp.mpro.caopapp.domain.MotivoEvasao;
import br.mp.mpro.caopapp.repository.MotivoEvasaoRepository;
import br.mp.mpro.caopapp.service.dto.MotivoEvasaoDTO;
import br.mp.mpro.caopapp.service.mapper.MotivoEvasaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MotivoEvasao.
 */
@Service
@Transactional
public class MotivoEvasaoServiceImpl implements MotivoEvasaoService {

    private final Logger log = LoggerFactory.getLogger(MotivoEvasaoServiceImpl.class);

    private final MotivoEvasaoRepository motivoEvasaoRepository;

    private final MotivoEvasaoMapper motivoEvasaoMapper;

    public MotivoEvasaoServiceImpl(MotivoEvasaoRepository motivoEvasaoRepository, MotivoEvasaoMapper motivoEvasaoMapper) {
        this.motivoEvasaoRepository = motivoEvasaoRepository;
        this.motivoEvasaoMapper = motivoEvasaoMapper;
    }

    /**
     * Save a motivoEvasao.
     *
     * @param motivoEvasaoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MotivoEvasaoDTO save(MotivoEvasaoDTO motivoEvasaoDTO) {
        log.debug("Request to save MotivoEvasao : {}", motivoEvasaoDTO);
        MotivoEvasao motivoEvasao = motivoEvasaoMapper.toEntity(motivoEvasaoDTO);
        motivoEvasao = motivoEvasaoRepository.save(motivoEvasao);
        return motivoEvasaoMapper.toDto(motivoEvasao);
    }

    /**
     * Get all the motivoEvasaos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MotivoEvasaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MotivoEvasaos");
        return motivoEvasaoRepository.findAll(pageable)
            .map(motivoEvasaoMapper::toDto);
    }

    /**
     * Get one motivoEvasao by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MotivoEvasaoDTO findOne(Long id) {
        log.debug("Request to get MotivoEvasao : {}", id);
        MotivoEvasao motivoEvasao = motivoEvasaoRepository.findOne(id);
        return motivoEvasaoMapper.toDto(motivoEvasao);
    }

    /**
     * Delete the motivoEvasao by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MotivoEvasao : {}", id);
        motivoEvasaoRepository.delete(id);
    }
}
