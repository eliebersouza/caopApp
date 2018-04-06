package br.mp.mpro.caopapp.service.mapper;

import br.mp.mpro.caopapp.domain.*;
import br.mp.mpro.caopapp.service.dto.EspacoLazerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EspacoLazer and its DTO EspacoLazerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EspacoLazerMapper extends EntityMapper<EspacoLazerDTO, EspacoLazer> {



    default EspacoLazer fromId(Long id) {
        if (id == null) {
            return null;
        }
        EspacoLazer espacoLazer = new EspacoLazer();
        espacoLazer.setId(id);
        return espacoLazer;
    }
}
