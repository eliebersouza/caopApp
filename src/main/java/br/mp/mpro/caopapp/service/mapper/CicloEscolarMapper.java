package br.mp.mpro.caopapp.service.mapper;

import br.mp.mpro.caopapp.domain.*;
import br.mp.mpro.caopapp.service.dto.CicloEscolarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CicloEscolar and its DTO CicloEscolarDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CicloEscolarMapper extends EntityMapper<CicloEscolarDTO, CicloEscolar> {



    default CicloEscolar fromId(Long id) {
        if (id == null) {
            return null;
        }
        CicloEscolar cicloEscolar = new CicloEscolar();
        cicloEscolar.setId(id);
        return cicloEscolar;
    }
}
