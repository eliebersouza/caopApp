package br.mp.mpro.caopapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CicloEscolarForm entity.
 */
public class CicloEscolarFormDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer idadeResidente;

    private Long cicloEscolarId;

    private String cicloEscolarCiclo;

    private Long formId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdadeResidente() {
        return idadeResidente;
    }

    public void setIdadeResidente(Integer idadeResidente) {
        this.idadeResidente = idadeResidente;
    }

    public Long getCicloEscolarId() {
        return cicloEscolarId;
    }

    public void setCicloEscolarId(Long cicloEscolarId) {
        this.cicloEscolarId = cicloEscolarId;
    }

    public String getCicloEscolarCiclo() {
        return cicloEscolarCiclo;
    }

    public void setCicloEscolarCiclo(String cicloEscolarCiclo) {
        this.cicloEscolarCiclo = cicloEscolarCiclo;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CicloEscolarFormDTO cicloEscolarFormDTO = (CicloEscolarFormDTO) o;
        if(cicloEscolarFormDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cicloEscolarFormDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CicloEscolarFormDTO{" +
            "id=" + getId() +
            ", idadeResidente=" + getIdadeResidente() +
            "}";
    }
}
