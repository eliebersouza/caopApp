package br.mp.mpro.caopapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MotivoEvasao.
 */
@Entity
@Table(name = "motivo_evasao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MotivoEvasao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "motivo", nullable = false)
    private String motivo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotivo() {
        return motivo;
    }

    public MotivoEvasao motivo(String motivo) {
        this.motivo = motivo;
        return this;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
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
        MotivoEvasao motivoEvasao = (MotivoEvasao) o;
        if (motivoEvasao.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), motivoEvasao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MotivoEvasao{" +
            "id=" + getId() +
            ", motivo='" + getMotivo() + "'" +
            "}";
    }
}
