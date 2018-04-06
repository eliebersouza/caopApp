package br.mp.mpro.caopapp.service.mapper;

import br.mp.mpro.caopapp.domain.*;
import br.mp.mpro.caopapp.service.dto.FaixaEtariaFormDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FaixaEtariaForm and its DTO FaixaEtariaFormDTO.
 */
@Mapper(componentModel = "spring", uses = {FormMapper.class, FaixaEtariaMapper.class})
public interface FaixaEtariaFormMapper extends EntityMapper<FaixaEtariaFormDTO, FaixaEtariaForm> {

    @Mapping(source = "form.id", target = "formId")
    @Mapping(source = "faixaEtaria.id", target = "faixaEtariaId")
    @Mapping(source = "faixaEtaria.faixa", target = "faixaEtariaFaixa")
    FaixaEtariaFormDTO toDto(FaixaEtariaForm faixaEtariaForm);

    @Mapping(source = "formId", target = "form")
    @Mapping(source = "faixaEtariaId", target = "faixaEtaria")
    FaixaEtariaForm toEntity(FaixaEtariaFormDTO faixaEtariaFormDTO);

    default FaixaEtariaForm fromId(Long id) {
        if (id == null) {
            return null;
        }
        FaixaEtariaForm faixaEtariaForm = new FaixaEtariaForm();
        faixaEtariaForm.setId(id);
        return faixaEtariaForm;
    }
}
