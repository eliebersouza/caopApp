package br.mp.mpro.caopapp.service.mapper;

import br.mp.mpro.caopapp.domain.*;
import br.mp.mpro.caopapp.service.dto.FormDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Form and its DTO FormDTO.
 */
@Mapper(componentModel = "spring", uses = {RendaFamiliarMapper.class, PeriodicidadeMapper.class, ReligiaoMapper.class, ResponsavelLarMapper.class, ServicoPublicoMapper.class, EspacoLazerMapper.class})
public interface FormMapper extends EntityMapper<FormDTO, Form> {

    @Mapping(source = "rendaFamiliar.id", target = "rendaFamiliarId")
    @Mapping(source = "rendaFamiliar.faixaRenda", target = "rendaFamiliarFaixaRenda")
    @Mapping(source = "periodoVisitaAgente.id", target = "periodoVisitaAgenteId")
    @Mapping(source = "periodoVisitaAgente.descricao", target = "periodoVisitaAgenteDescricao")
    @Mapping(source = "religiao.id", target = "religiaoId")
    @Mapping(source = "religiao.descricao", target = "religiaoDescricao")
    @Mapping(source = "responsavelLar.id", target = "responsavelLarId")
    @Mapping(source = "responsavelLar.responsavel", target = "responsavelLarResponsavel")
    FormDTO toDto(Form form);

    @Mapping(source = "rendaFamiliarId", target = "rendaFamiliar")
    @Mapping(source = "periodoVisitaAgenteId", target = "periodoVisitaAgente")
    @Mapping(source = "religiaoId", target = "religiao")
    @Mapping(source = "responsavelLarId", target = "responsavelLar")
    Form toEntity(FormDTO formDTO);

    default Form fromId(Long id) {
        if (id == null) {
            return null;
        }
        Form form = new Form();
        form.setId(id);
        return form;
    }
}
