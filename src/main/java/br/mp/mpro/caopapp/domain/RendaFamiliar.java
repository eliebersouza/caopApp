package br.mp.mpro.caopapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A RendaFamiliar.
 */
@Entity
@Table(name = "renda_familiar")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RendaFamiliar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "faixa_renda", nullable = false)
    private String faixaRenda;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFaixaRenda() {
        return faixaRenda;
    }

    public RendaFamiliar faixaRenda(String faixaRenda) {
        this.faixaRenda = faixaRenda;
        return this;
    }

    public void setFaixaRenda(String faixaRenda) {
        this.faixaRenda = faixaRenda;
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
        RendaFamiliar rendaFamiliar = (RendaFamiliar) o;
        if (rendaFamiliar.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rendaFamiliar.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RendaFamiliar{" +
            "id=" + getId() +
            ", faixaRenda='" + getFaixaRenda() + "'" +
            "}";
    }
}
