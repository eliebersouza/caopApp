package br.mp.mpro.caopapp.service.mapper;

import br.mp.mpro.caopapp.domain.*;
import br.mp.mpro.caopapp.service.dto.FaixaEtariaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FaixaEtaria and its DTO FaixaEtariaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FaixaEtariaMapper extends EntityMapper<FaixaEtariaDTO, FaixaEtaria> {



    default FaixaEtaria fromId(Long id) {
        if (id == null) {
            return null;
        }
        FaixaEtaria faixaEtaria = new FaixaEtaria();
        faixaEtaria.setId(id);
        return faixaEtaria;
    }
}
