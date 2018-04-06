package br.mp.mpro.caopapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the FaixaEtariaForm entity.
 */
public class FaixaEtariaFormDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer quantidadeFaixa;

    private Long formId;

    private Long faixaEtariaId;

    private String faixaEtariaFaixa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidadeFaixa() {
        return quantidadeFaixa;
    }

    public void setQuantidadeFaixa(Integer quantidadeFaixa) {
        this.quantidadeFaixa = quantidadeFaixa;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public Long getFaixaEtariaId() {
        return faixaEtariaId;
    }

    public void setFaixaEtariaId(Long faixaEtariaId) {
        this.faixaEtariaId = faixaEtariaId;
    }

    public String getFaixaEtariaFaixa() {
        return faixaEtariaFaixa;
    }

    public void setFaixaEtariaFaixa(String faixaEtariaFaixa) {
        this.faixaEtariaFaixa = faixaEtariaFaixa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FaixaEtariaFormDTO faixaEtariaFormDTO = (FaixaEtariaFormDTO) o;
        if(faixaEtariaFormDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), faixaEtariaFormDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FaixaEtariaFormDTO{" +
            "id=" + getId() +
            ", quantidadeFaixa=" + getQuantidadeFaixa() +
            "}";
    }
}
