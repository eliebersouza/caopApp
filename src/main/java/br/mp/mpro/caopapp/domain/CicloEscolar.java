package br.mp.mpro.caopapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CicloEscolar.
 */
@Entity
@Table(name = "ciclo_escolar")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CicloEscolar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ciclo", nullable = false)
    private String ciclo;

    @NotNull
    @Column(name = "idade_minima", nullable = false)
    private Integer idadeMinima;

    @NotNull
    @Column(name = "idade_maxima", nullable = false)
    private Integer idadeMaxima;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCiclo() {
        return ciclo;
    }

    public CicloEscolar ciclo(String ciclo) {
        this.ciclo = ciclo;
        return this;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public Integer getIdadeMinima() {
        return idadeMinima;
    }

    public CicloEscolar idadeMinima(Integer idadeMinima) {
        this.idadeMinima = idadeMinima;
        return this;
    }

    public void setIdadeMinima(Integer idadeMinima) {
        this.idadeMinima = idadeMinima;
    }

    public Integer getIdadeMaxima() {
        return idadeMaxima;
    }

    public CicloEscolar idadeMaxima(Integer idadeMaxima) {
        this.idadeMaxima = idadeMaxima;
        return this;
    }

    public void setIdadeMaxima(Integer idadeMaxima) {
        this.idadeMaxima = idadeMaxima;
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
        CicloEscolar cicloEscolar = (CicloEscolar) o;
        if (cicloEscolar.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cicloEscolar.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CicloEscolar{" +
            "id=" + getId() +
            ", ciclo='" + getCiclo() + "'" +
            ", idadeMinima=" + getIdadeMinima() +
            ", idadeMaxima=" + getIdadeMaxima() +
            "}";
    }
}
