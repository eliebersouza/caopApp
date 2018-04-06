package br.mp.mpro.caopapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import br.mp.mpro.caopapp.domain.enumeration.SimNao;
import br.mp.mpro.caopapp.domain.enumeration.SimNao;
import br.mp.mpro.caopapp.domain.enumeration.SimNao;
import br.mp.mpro.caopapp.domain.enumeration.PresencialDistancia;
import br.mp.mpro.caopapp.domain.enumeration.TipoCurso;

/**
 * A DTO for the ResidenteForaDaEscola entity.
 */
public class ResidenteForaDaEscolaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3)
    private String nomeDoCidadaoForaDaEscola;

    @NotNull
    private Integer idade;

    @NotNull
    private SimNao deficiencia;

    @NotNull
    private SimNao sabeLer;

    @NotNull
    private SimNao retomarEstudos;

    private String nomeCursoDesejado;

    private PresencialDistancia presencialDistancia;

    private TipoCurso tipoCurso;

    private String observacaoOutrosMotivos;

    private Long cicloEscolarId;

    private String cicloEscolarCiclo;

    private Long motivoEvasaoId;

    private String motivoEvasaoMotivo;

    private Long formId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDoCidadaoForaDaEscola() {
        return nomeDoCidadaoForaDaEscola;
    }

    public void setNomeDoCidadaoForaDaEscola(String nomeDoCidadaoForaDaEscola) {
        this.nomeDoCidadaoForaDaEscola = nomeDoCidadaoForaDaEscola;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public SimNao getDeficiencia() {
        return deficiencia;
    }

    public void setDeficiencia(SimNao deficiencia) {
        this.deficiencia = deficiencia;
    }

    public SimNao getSabeLer() {
        return sabeLer;
    }

    public void setSabeLer(SimNao sabeLer) {
        this.sabeLer = sabeLer;
    }

    public SimNao getRetomarEstudos() {
        return retomarEstudos;
    }

    public void setRetomarEstudos(SimNao retomarEstudos) {
        this.retomarEstudos = retomarEstudos;
    }

    public String getNomeCursoDesejado() {
        return nomeCursoDesejado;
    }

    public void setNomeCursoDesejado(String nomeCursoDesejado) {
        this.nomeCursoDesejado = nomeCursoDesejado;
    }

    public PresencialDistancia getPresencialDistancia() {
        return presencialDistancia;
    }

    public void setPresencialDistancia(PresencialDistancia presencialDistancia) {
        this.presencialDistancia = presencialDistancia;
    }

    public TipoCurso getTipoCurso() {
        return tipoCurso;
    }

    public void setTipoCurso(TipoCurso tipoCurso) {
        this.tipoCurso = tipoCurso;
    }

    public String getObservacaoOutrosMotivos() {
        return observacaoOutrosMotivos;
    }

    public void setObservacaoOutrosMotivos(String observacaoOutrosMotivos) {
        this.observacaoOutrosMotivos = observacaoOutrosMotivos;
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

    public Long getMotivoEvasaoId() {
        return motivoEvasaoId;
    }

    public void setMotivoEvasaoId(Long motivoEvasaoId) {
        this.motivoEvasaoId = motivoEvasaoId;
    }

    public String getMotivoEvasaoMotivo() {
        return motivoEvasaoMotivo;
    }

    public void setMotivoEvasaoMotivo(String motivoEvasaoMotivo) {
        this.motivoEvasaoMotivo = motivoEvasaoMotivo;
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

        ResidenteForaDaEscolaDTO residenteForaDaEscolaDTO = (ResidenteForaDaEscolaDTO) o;
        if(residenteForaDaEscolaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), residenteForaDaEscolaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResidenteForaDaEscolaDTO{" +
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
