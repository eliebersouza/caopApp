package br.mp.mpro.caopapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RendaFamiliar entity.
 */
public class RendaFamiliarDTO implements Serializable {

    private Long id;

    @NotNull
    private String faixaRenda;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFaixaRenda() {
        return faixaRenda;
    }

    public void setFaixaRenda(String faixaRenda) {
        this.faixaRenda = faixaRenda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RendaFamiliarDTO rendaFamiliarDTO = (RendaFamiliarDTO) o;
        if(rendaFamiliarDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rendaFamiliarDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RendaFamiliarDTO{" +
            "id=" + getId() +
            ", faixaRenda='" + getFaixaRenda() + "'" +
            "}";
    }
}
