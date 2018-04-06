package br.mp.mpro.caopapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CicloEscolarForm.
 */
@Entity
@Table(name = "ciclo_escolar_form")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CicloEscolarForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idade_residente", nullable = false)
    private Integer idadeResidente;

    @ManyToOne(optional = false)
    @NotNull
    private CicloEscolar cicloEscolar;

    @ManyToOne(optional = false)
    @NotNull
    private Form form;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdadeResidente() {
        return idadeResidente;
    }

    public CicloEscolarForm idadeResidente(Integer idadeResidente) {
        this.idadeResidente = idadeResidente;
        return this;
    }

    public void setIdadeResidente(Integer idadeResidente) {
        this.idadeResidente = idadeResidente;
    }

    public CicloEscolar getCicloEscolar() {
        return cicloEscolar;
    }

    public CicloEscolarForm cicloEscolar(CicloEscolar cicloEscolar) {
        this.cicloEscolar = cicloEscolar;
        return this;
    }

    public void setCicloEscolar(CicloEscolar cicloEscolar) {
        this.cicloEscolar = cicloEscolar;
    }

    public Form getForm() {
        return form;
    }

    public CicloEscolarForm form(Form form) {
        this.form = form;
        return this;
    }

    public void setForm(Form form) {
        this.form = form;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CicloEscolarForm cicloEscolarForm = (CicloEscolarForm) o;
        if (cicloEscolarForm.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cicloEscolarForm.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CicloEscolarForm{" +
            "id=" + getId() +
            ", idadeResidente=" + getIdadeResidente() +
            "}";
    }
}
