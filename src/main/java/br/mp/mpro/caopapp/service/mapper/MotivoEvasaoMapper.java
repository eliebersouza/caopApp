package br.mp.mpro.caopapp.service.mapper;

import br.mp.mpro.caopapp.domain.*;
import br.mp.mpro.caopapp.service.dto.MotivoEvasaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MotivoEvasao and its DTO MotivoEvasaoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MotivoEvasaoMapper extends EntityMapper<MotivoEvasaoDTO, MotivoEvasao> {



    default MotivoEvasao fromId(Long id) {
        if (id == null) {
            return null;
        }
        MotivoEvasao motivoEvasao = new MotivoEvasao();
        motivoEvasao.setId(id);
        return motivoEvasao;
    }
}
