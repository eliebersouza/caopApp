package br.mp.mpro.caopapp.service.impl;

import br.mp.mpro.caopapp.service.RendaFamiliarService;
import br.mp.mpro.caopapp.domain.RendaFamiliar;
import br.mp.mpro.caopapp.repository.RendaFamiliarRepository;
import br.mp.mpro.caopapp.service.dto.RendaFamiliarDTO;
import br.mp.mpro.caopapp.service.mapper.RendaFamiliarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing RendaFamiliar.
 */
@Service
@Transactional
public class RendaFamiliarServiceImpl implements RendaFamiliarService {

    private final Logger log = LoggerFactory.getLogger(RendaFamiliarServiceImpl.class);

    private final RendaFamiliarRepository rendaFamiliarRepository;

    private final RendaFamiliarMapper rendaFamiliarMapper;

    public RendaFamiliarServiceImpl(RendaFamiliarRepository rendaFamiliarRepository, RendaFamiliarMapper rendaFamiliarMapper) {
        this.rendaFamiliarRepository = rendaFamiliarRepository;
        this.rendaFamiliarMapper = rendaFamiliarMapper;
    }

    /**
     * Save a rendaFamiliar.
     *
     * @param rendaFamiliarDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RendaFamiliarDTO save(RendaFamiliarDTO rendaFamiliarDTO) {
        log.debug("Request to save RendaFamiliar : {}", rendaFamiliarDTO);
        RendaFamiliar rendaFamiliar = rendaFamiliarMapper.toEntity(rendaFamiliarDTO);
        rendaFamiliar = rendaFamiliarRepository.save(rendaFamiliar);
        return rendaFamiliarMapper.toDto(rendaFamiliar);
    }

    /**
     * Get all the rendaFamiliars.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RendaFamiliarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RendaFamiliars");
        return rendaFamiliarRepository.findAll(pageable)
            .map(rendaFamiliarMapper::toDto);
    }

    /**
     * Get one rendaFamiliar by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RendaFamiliarDTO findOne(Long id) {
        log.debug("Request to get RendaFamiliar : {}", id);
        RendaFamiliar rendaFamiliar = rendaFamiliarRepository.findOne(id);
        return rendaFamiliarMapper.toDto(rendaFamiliar);
    }

    /**
     * Delete the rendaFamiliar by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RendaFamiliar : {}", id);
        rendaFamiliarRepository.delete(id);
    }
}
