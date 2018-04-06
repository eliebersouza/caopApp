package br.mp.mpro.caopapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CicloEscolar entity.
 */
public class CicloEscolarDTO implements Serializable {

    private Long id;

    @NotNull
    private String ciclo;

    @NotNull
    private Integer idadeMinima;

    @NotNull
    private Integer idadeMaxima;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public Integer getIdadeMinima() {
        return idadeMinima;
    }

    public void setIdadeMinima(Integer idadeMinima) {
        this.idadeMinima = idadeMinima;
    }

    public Integer getIdadeMaxima() {
        return idadeMaxima;
    }

    public void setIdadeMaxima(Integer idadeMaxima) {
        this.idadeMaxima = idadeMaxima;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CicloEscolarDTO cicloEscolarDTO = (CicloEscolarDTO) o;
        if(cicloEscolarDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cicloEscolarDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CicloEscolarDTO{" +
            "id=" + getId() +
            ", ciclo='" + getCiclo() + "'" +
            ", idadeMinima=" + getIdadeMinima() +
            ", idadeMaxima=" + getIdadeMaxima() +
            "}";
    }
}
