package br.mp.mpro.caopapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ResponsavelLar entity.
 */
public class ResponsavelLarDTO implements Serializable {

    private Long id;

    @NotNull
    private String responsavel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResponsavelLarDTO responsavelLarDTO = (ResponsavelLarDTO) o;
        if(responsavelLarDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), responsavelLarDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResponsavelLarDTO{" +
            "id=" + getId() +
            ", responsavel='" + getResponsavel() + "'" +
            "}";
    }
}
