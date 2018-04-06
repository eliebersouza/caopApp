package br.mp.mpro.caopapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A FaixaEtariaForm.
 */
@Entity
@Table(name = "faixa_etaria_form")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FaixaEtariaForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "quantidade_faixa", nullable = false)
    private Integer quantidadeFaixa;

    @ManyToOne(optional = false)
    @NotNull
    private Form form;

    @ManyToOne(optional = false)
    @NotNull
    private FaixaEtaria faixaEtaria;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidadeFaixa() {
        return quantidadeFaixa;
    }

    public FaixaEtariaForm quantidadeFaixa(Integer quantidadeFaixa) {
        this.quantidadeFaixa = quantidadeFaixa;
        return this;
    }

    public void setQuantidadeFaixa(Integer quantidadeFaixa) {
        this.quantidadeFaixa = quantidadeFaixa;
    }

    public Form getForm() {
        return form;
    }

    public FaixaEtariaForm form(Form form) {
        this.form = form;
        return this;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public FaixaEtaria getFaixaEtaria() {
        return faixaEtaria;
    }

    public FaixaEtariaForm faixaEtaria(FaixaEtaria faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
        return this;
    }

    public void setFaixaEtaria(FaixaEtaria faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
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
        FaixaEtariaForm faixaEtariaForm = (FaixaEtariaForm) o;
        if (faixaEtariaForm.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), faixaEtariaForm.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FaixaEtariaForm{" +
            "id=" + getId() +
            ", quantidadeFaixa=" + getQuantidadeFaixa() +
            "}";
    }
}
