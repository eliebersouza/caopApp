package br.mp.mpro.caopapp.service.mapper;

import br.mp.mpro.caopapp.domain.*;
import br.mp.mpro.caopapp.service.dto.ReligiaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Religiao and its DTO ReligiaoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReligiaoMapper extends EntityMapper<ReligiaoDTO, Religiao> {



    default Religiao fromId(Long id) {
        if (id == null) {
            return null;
        }
        Religiao religiao = new Religiao();
        religiao.setId(id);
        return religiao;
    }
}
