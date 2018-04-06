package br.mp.mpro.caopapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import br.mp.mpro.caopapp.domain.enumeration.SimNao;

import br.mp.mpro.caopapp.domain.enumeration.PresencialDistancia;

import br.mp.mpro.caopapp.domain.enumeration.TipoCurso;

/**
 * A ResidenteForaDaEscola.
 */
@Entity
@Table(name = "residente_fora_da_escola")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ResidenteForaDaEscola implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "nome_do_cidadao_fora_da_escola", nullable = false)
    private String nomeDoCidadaoForaDaEscola;

    @NotNull
    @Column(name = "idade", nullable = false)
    private Integer idade;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "deficiencia", nullable = false)
    private SimNao deficiencia;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sabe_ler", nullable = false)
    private SimNao sabeLer;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "retomar_estudos", nullable = false)
    private SimNao retomarEstudos;

    @Column(name = "nome_curso_desejado")
    private String nomeCursoDesejado;

    @Enumerated(EnumType.STRING)
    @Column(name = "presencial_distancia")
    private PresencialDistancia presencialDistancia;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_curso")
    private TipoCurso tipoCurso;

    @Column(name = "observacao_outros_motivos")
    private String observacaoOutrosMotivos;

    @ManyToOne(optional = false)
    @NotNull
    private CicloEscolar cicloEscolar;

    @ManyToOne(optional = false)
    @NotNull
    private MotivoEvasao motivoEvasao;

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

    public String getNomeDoCidadaoForaDaEscola() {
        return nomeDoCidadaoForaDaEscola;
    }

    public ResidenteForaDaEscola nomeDoCidadaoForaDaEscola(String nomeDoCidadaoForaDaEscola) {
        this.nomeDoCidadaoForaDaEscola = nomeDoCidadaoForaDaEscola;
        return this;
    }

    public void setNomeDoCidadaoForaDaEscola(String nomeDoCidadaoForaDaEscola) {
        this.nomeDoCidadaoForaDaEscola = nomeDoCidadaoForaDaEscola;
    }

    public Integer getIdade() {
        return idade;
    }

    public ResidenteForaDaEscola idade(Integer idade) {
        this.idade = idade;
        return this;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public SimNao getDeficiencia() {
        return deficiencia;
    }

    public ResidenteForaDaEscola deficiencia(SimNao deficiencia) {
        this.deficiencia = deficiencia;
        return this;
    }

    public void setDeficiencia(SimNao deficiencia) {
        this.deficiencia = deficiencia;
    }

    public SimNao getSabeLer() {
        return sabeLer;
    }

    public ResidenteForaDaEscola sabeLer(SimNao sabeLer) {
        this.sabeLer = sabeLer;
        return this;
    }

    public void setSabeLer(SimNao sabeLer) {
        this.sabeLer = sabeLer;
    }

    public SimNao getRetomarEstudos() {
        return retomarEstudos;
    }

    public ResidenteForaDaEscola retomarEstudos(SimNao retomarEstudos) {
        this.retomarEstudos = retomarEstudos;
        return this;
    }

    public void setRetomarEstudos(SimNao retomarEstudos) {
        this.retomarEstudos = retomarEstudos;
    }

    public String getNomeCursoDesejado() {
        return nomeCursoDesejado;
    }

    public ResidenteForaDaEscola nomeCursoDesejado(String nomeCursoDesejado) {
        this.nomeCursoDesejado = nomeCursoDesejado;
        return this;
    }

    public void setNomeCursoDesejado(String nomeCursoDesejado) {
        this.nomeCursoDesejado = nomeCursoDesejado;
    }

    public PresencialDistancia getPresencialDistancia() {
        return presencialDistancia;
    }

    public ResidenteForaDaEscola presencialDistancia(PresencialDistancia presencialDistancia) {
        this.presencialDistancia = presencialDistancia;
        return this;
    }

    public void setPresencialDistancia(PresencialDistancia presencialDistancia) {
        this.presencialDistancia = presencialDistancia;
    }

    public TipoCurso getTipoCurso() {
        return tipoCurso;
    }

    public ResidenteForaDaEscola tipoCurso(TipoCurso tipoCurso) {
        this.tipoCurso = tipoCurso;
        return this;
    }

    public void setTipoCurso(TipoCurso tipoCurso) {
        this.tipoCurso = tipoCurso;
    }

    public String getObservacaoOutrosMotivos() {
        return observacaoOutrosMotivos;
    }

    public ResidenteForaDaEscola observacaoOutrosMotivos(String observacaoOutrosMotivos) {
        this.observacaoOutrosMotivos = observacaoOutrosMotivos;
        return this;
    }

    public void setObservacaoOutrosMotivos(String observacaoOutrosMotivos) {
        this.observacaoOutrosMotivos = observacaoOutrosMotivos;
    }

    public CicloEscolar getCicloEscolar() {
        return cicloEscolar;
    }

    public ResidenteForaDaEscola cicloEscolar(CicloEscolar cicloEscolar) {
        this.cicloEscolar = cicloEscolar;
        return this;
    }

    public void setCicloEscolar(CicloEscolar cicloEscolar) {
        this.cicloEscolar = cicloEscolar;
    }

    public MotivoEvasao getMotivoEvasao() {
        return motivoEvasao;
    }

    public ResidenteForaDaEscola motivoEvasao(MotivoEvasao motivoEvasao) {
        this.motivoEvasao = motivoEvasao;
        return this;
    }

    public void setMotivoEvasao(MotivoEvasao motivoEvasao) {
        this.motivoEvasao = motivoEvasao;
    }

    public Form getForm() {
        return form;
    }

    public ResidenteForaDaEscola form(Form form) {
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
        ResidenteForaDaEscola residenteForaDaEscola = (ResidenteForaDaEscola) o;
        if (residenteForaDaEscola.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), residenteForaDaEscola.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResidenteForaDaEscola{" +
            "id=" + getId() +
            ", nomeDoCidadaoForaDaEscola='" + getNomeDoCidadaoForaDaEscola() + "'" +
            ", idade=" + getIdade() +
            ", deficiencia='" + getDeficiencia() + "'" +
            ", sabeLer='" + getSabeLer() + "'" +
            ", retomarEstudos='" + getRetomarEstudos() + "'" +
            ", nomeCursoDesejado='" + getNomeCursoDesejado() + "'" +
            ", presencialDistancia='" + getPresencialDistancia() + "'" +
            ", tipoCurso='" + getTipoCurso() + "'" +
            ", observacaoOutrosMotivos='" + getObservacaoOutrosMotivos() + "'" +
            "}";
    }
}
