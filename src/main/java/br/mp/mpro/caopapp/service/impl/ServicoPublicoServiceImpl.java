package br.mp.mpro.caopapp.service.impl;

import br.mp.mpro.caopapp.service.ServicoPublicoService;
import br.mp.mpro.caopapp.domain.ServicoPublico;
import br.mp.mpro.caopapp.repository.ServicoPublicoRepository;
import br.mp.mpro.caopapp.service.dto.ServicoPublicoDTO;
import br.mp.mpro.caopapp.service.mapper.ServicoPublicoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ServicoPublico.
 */
@Service
@Transactional
public class ServicoPublicoServiceImpl implements ServicoPublicoService {

    private final Logger log = LoggerFactory.getLogger(ServicoPublicoServiceImpl.class);

    private final ServicoPublicoRepository servicoPublicoRepository;

    private final ServicoPublicoMapper servicoPublicoMapper;

    public ServicoPublicoServiceImpl(ServicoPublicoRepository servicoPublicoRepository, ServicoPublicoMapper servicoPublicoMapper) {
        this.servicoPublicoRepository = servicoPublicoRepository;
        this.servicoPublicoMapper = servicoPublicoMapper;
    }

    /**
     * Save a servicoPublico.
     *
     * @param servicoPublicoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ServicoPublicoDTO save(ServicoPublicoDTO servicoPublicoDTO) {
        log.debug("Request to save ServicoPublico : {}", servicoPublicoDTO);
        ServicoPublico servicoPublico = servicoPublicoMapper.toEntity(servicoPublicoDTO);
        servicoPublico = servicoPublicoRepository.save(servicoPublico);
        return servicoPublicoMapper.toDto(servicoPublico);
    }

    /**
     * Get all the servicoPublicos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServicoPublicoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServicoPublicos");
        return servicoPublicoRepository.findAll(pageable)
            .map(servicoPublicoMapper::toDto);
    }

    /**
     * Get one servicoPublico by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ServicoPublicoDTO findOne(Long id) {
        log.debug("Request to get ServicoPublico : {}", id);
        ServicoPublico servicoPublico = servicoPublicoRepository.findOne(id);
        return servicoPublicoMapper.toDto(servicoPublico);
    }

    /**
     * Delete the servicoPublico by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServicoPublico : {}", id);
        servicoPublicoRepository.delete(id);
    }
}
