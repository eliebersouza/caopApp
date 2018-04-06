package br.mp.mpro.caopapp.service.mapper;

import br.mp.mpro.caopapp.domain.*;
import br.mp.mpro.caopapp.service.dto.ServicoPublicoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ServicoPublico and its DTO ServicoPublicoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServicoPublicoMapper extends EntityMapper<ServicoPublicoDTO, ServicoPublico> {



    default ServicoPublico fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServicoPublico servicoPublico = new ServicoPublico();
        servicoPublico.setId(id);
        return servicoPublico;
    }
}
