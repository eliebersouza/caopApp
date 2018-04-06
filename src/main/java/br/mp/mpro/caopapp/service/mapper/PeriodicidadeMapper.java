package br.mp.mpro.caopapp.service.mapper;

import br.mp.mpro.caopapp.domain.*;
import br.mp.mpro.caopapp.service.dto.PeriodicidadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Periodicidade and its DTO PeriodicidadeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PeriodicidadeMapper extends EntityMapper<PeriodicidadeDTO, Periodicidade> {



    default Periodicidade fromId(Long id) {
        if (id == null) {
            return null;
        }
        Periodicidade periodicidade = new Periodicidade();
        periodicidade.setId(id);
        return periodicidade;
    }
}
