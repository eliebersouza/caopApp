package br.mp.mpro.caopapp.service.mapper;

import br.mp.mpro.caopapp.domain.*;
import br.mp.mpro.caopapp.service.dto.ResidenteForaDaEscolaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ResidenteForaDaEscola and its DTO ResidenteForaDaEscolaDTO.
 */
@Mapper(componentModel = "spring", uses = {CicloEscolarMapper.class, MotivoEvasaoMapper.class, FormMapper.class})
public interface ResidenteForaDaEscolaMapper extends EntityMapper<ResidenteForaDaEscolaDTO, ResidenteForaDaEscola> {

    @Mapping(source = "cicloEscolar.id", target = "cicloEscolarId")
    @Mapping(source = "cicloEscolar.ciclo", target = "cicloEscolarCiclo")
    @Mapping(source = "motivoEvasao.id", target = "motivoEvasaoId")
    @Mapping(source = "motivoEvasao.motivo", target = "motivoEvasaoMotivo")
    @Mapping(source = "form.id", target = "formId")
    ResidenteForaDaEscolaDTO toDto(ResidenteForaDaEscola residenteForaDaEscola);

    @Mapping(source = "cicloEscolarId", target = "cicloEscolar")
    @Mapping(source = "motivoEvasaoId", target = "motivoEvasao")
    @Mapping(source = "formId", target = "form")
    ResidenteForaDaEscola toEntity(ResidenteForaDaEscolaDTO residenteForaDaEscolaDTO);

    default ResidenteForaDaEscola fromId(Long id) {
        if (id == null) {
            return null;
        }
        ResidenteForaDaEscola residenteForaDaEscola = new ResidenteForaDaEscola();
        residenteForaDaEscola.setId(id);
        return residenteForaDaEscola;
    }
}
