package br.mp.mpro.caopapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the MotivoEvasao entity.
 */
public class MotivoEvasaoDTO implements Serializable {

    private Long id;

    @NotNull
    private String motivo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MotivoEvasaoDTO motivoEvasaoDTO = (MotivoEvasaoDTO) o;
        if(motivoEvasaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), motivoEvasaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MotivoEvasaoDTO{" +
            "id=" + getId() +
            ", motivo='" + getMotivo() + "'" +
            "}";
    }
}
