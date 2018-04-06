package br.mp.mpro.caopapp.service.mapper;

import br.mp.mpro.caopapp.domain.*;
import br.mp.mpro.caopapp.service.dto.RendaFamiliarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RendaFamiliar and its DTO RendaFamiliarDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RendaFamiliarMapper extends EntityMapper<RendaFamiliarDTO, RendaFamiliar> {



    default RendaFamiliar fromId(Long id) {
        if (id == null) {
            return null;
        }
        RendaFamiliar rendaFamiliar = new RendaFamiliar();
        rendaFamiliar.setId(id);
        return rendaFamiliar;
    }
}
