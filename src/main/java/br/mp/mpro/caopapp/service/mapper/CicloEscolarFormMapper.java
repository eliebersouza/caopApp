package br.mp.mpro.caopapp.service.mapper;

import br.mp.mpro.caopapp.domain.*;
import br.mp.mpro.caopapp.service.dto.CicloEscolarFormDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CicloEscolarForm and its DTO CicloEscolarFormDTO.
 */
@Mapper(componentModel = "spring", uses = {CicloEscolarMapper.class, FormMapper.class})
public interface CicloEscolarFormMapper extends EntityMapper<CicloEscolarFormDTO, CicloEscolarForm> {

    @Mapping(source = "cicloEscolar.id", target = "cicloEscolarId")
    @Mapping(source = "cicloEscolar.ciclo", target = "cicloEscolarCiclo")
    @Mapping(source = "form.id", target = "formId")
    CicloEscolarFormDTO toDto(CicloEscolarForm cicloEscolarForm);

    @Mapping(source = "cicloEscolarId", target = "cicloEscolar")
    @Mapping(source = "formId", target = "form")
    CicloEscolarForm toEntity(CicloEscolarFormDTO cicloEscolarFormDTO);

    default CicloEscolarForm fromId(Long id) {
        if (id == null) {
            return null;
        }
        CicloEscolarForm cicloEscolarForm = new CicloEscolarForm();
        cicloEscolarForm.setId(id);
        return cicloEscolarForm;
    }
}
