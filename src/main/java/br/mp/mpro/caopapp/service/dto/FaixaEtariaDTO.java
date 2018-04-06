package br.mp.mpro.caopapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the FaixaEtaria entity.
 */
public class FaixaEtariaDTO implements Serializable {

    private Long id;

    @NotNull
    private String faixa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFaixa() {
        return faixa;
    }

    public void setFaixa(String faixa) {
        this.faixa = faixa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FaixaEtariaDTO faixaEtariaDTO = (FaixaEtariaDTO) o;
        if(faixaEtariaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), faixaEtariaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FaixaEtariaDTO{" +
            "id=" + getId() +
            ", faixa='" + getFaixa() + "'" +
            "}";
    }
}
