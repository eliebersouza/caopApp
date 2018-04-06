package br.mp.mpro.caopapp.service.mapper;

import br.mp.mpro.caopapp.domain.*;
import br.mp.mpro.caopapp.service.dto.ResponsavelLarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ResponsavelLar and its DTO ResponsavelLarDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ResponsavelLarMapper extends EntityMapper<ResponsavelLarDTO, ResponsavelLar> {



    default ResponsavelLar fromId(Long id) {
        if (id == null) {
            return null;
        }
        ResponsavelLar responsavelLar = new ResponsavelLar();
        responsavelLar.setId(id);
        return responsavelLar;
    }
}
