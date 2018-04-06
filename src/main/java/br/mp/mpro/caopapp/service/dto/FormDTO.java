package br.mp.mpro.caopapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import br.mp.mpro.caopapp.domain.enumeration.LocalizacaoDomicilio;
import br.mp.mpro.caopapp.domain.enumeration.SituacaoResidencia;
import br.mp.mpro.caopapp.domain.enumeration.SimNao;
import br.mp.mpro.caopapp.domain.enumeration.SimNao;
import br.mp.mpro.caopapp.domain.enumeration.SimNao;
import br.mp.mpro.caopapp.domain.enumeration.SimNao;
import br.mp.mpro.caopapp.domain.enumeration.SimNao;
import br.mp.mpro.caopapp.domain.enumeration.OpcaoAvaliacao;
import br.mp.mpro.caopapp.domain.enumeration.OpcaoAvaliacao;
import br.mp.mpro.caopapp.domain.enumeration.SimNao;
import br.mp.mpro.caopapp.domain.enumeration.SimNao;
import br.mp.mpro.caopapp.domain.enumeration.OpcaoAvaliacao;
import br.mp.mpro.caopapp.domain.enumeration.OpcaoAvaliacao;

/**
 * A DTO for the Form entity.
 */
public class FormDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3)
    private String responsavelPelaInformacao;

    private String telefone;

    @NotNull
    private String rua;

    private String numero;

    private String bairroSetor;

    @NotNull
    private LocalizacaoDomicilio localizacaoDoDomicilio;

    @NotNull
    private SituacaoResidencia situacaoDaResidencia;

    private String numeroDaQuadra;

    private String numeroDaCasa;

    @NotNull
    @Min(value = 1)
    private Integer quantidadeDeResidentes;

    @NotNull
    private SimNao residenteFrenquentadoEscola;

    @NotNull
    private SimNao privadoDeLiberdade;

    private SimNao recebeAuxilioReclusao;

    private SimNao familiaVisitandoPreso;

    private SimNao cumprimentoSemiAberto;

    @NotNull
    private OpcaoAvaliacao avaliacaoPoliciaMilitar;

    @NotNull
    private OpcaoAvaliacao avaliacaoPoliciaCivel;

    @NotNull
    private SimNao naoAtendidoTelefonePolicia;

    @NotNull
    private SimNao naoAtendidoDelegacia;

    private OpcaoAvaliacao avaliacaoAgenteSaude;

    private OpcaoAvaliacao avaliacaoUBS;

    private Long rendaFamiliarId;

    private String rendaFamiliarFaixaRenda;

    private Long periodoVisitaAgenteId;

    private String periodoVisitaAgenteDescricao;

    private Long religiaoId;

    private String religiaoDescricao;

    private Long responsavelLarId;

    private String responsavelLarResponsavel;

    private Set<ServicoPublicoDTO> servicoPublicos = new HashSet<>();

    private Set<EspacoLazerDTO> espacoLazers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponsavelPelaInformacao() {
        return responsavelPelaInformacao;
    }

    public void setResponsavelPelaInformacao(String responsavelPelaInformacao) {
        this.responsavelPelaInformacao = responsavelPelaInformacao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairroSetor() {
        return bairroSetor;
    }

    public void setBairroSetor(String bairroSetor) {
        this.bairroSetor = bairroSetor;
    }

    public LocalizacaoDomicilio getLocalizacaoDoDomicilio() {
        return localizacaoDoDomicilio;
    }

    public void setLocalizacaoDoDomicilio(LocalizacaoDomicilio localizacaoDoDomicilio) {
        this.localizacaoDoDomicilio = localizacaoDoDomicilio;
    }

    public SituacaoResidencia getSituacaoDaResidencia() {
        return situacaoDaResidencia;
    }

    public void setSituacaoDaResidencia(SituacaoResidencia situacaoDaResidencia) {
        this.situacaoDaResidencia = situacaoDaResidencia;
    }

    public String getNumeroDaQuadra() {
        return numeroDaQuadra;
    }

    public void setNumeroDaQuadra(String numeroDaQuadra) {
        this.numeroDaQuadra = numeroDaQuadra;
    }

    public String getNumeroDaCasa() {
        return numeroDaCasa;
    }

    public void setNumeroDaCasa(String numeroDaCasa) {
        this.numeroDaCasa = numeroDaCasa;
    }

    public Integer getQuantidadeDeResidentes() {
        return quantidadeDeResidentes;
    }

    public void setQuantidadeDeResidentes(Integer quantidadeDeResidentes) {
        this.quantidadeDeResidentes = quantidadeDeResidentes;
    }

    public SimNao getResidenteFrenquentadoEscola() {
        return residenteFrenquentadoEscola;
    }

    public void setResidenteFrenquentadoEscola(SimNao residenteFrenquentadoEscola) {
        this.residenteFrenquentadoEscola = residenteFrenquentadoEscola;
    }

    public SimNao getPrivadoDeLiberdade() {
        return privadoDeLiberdade;
    }

    public void setPrivadoDeLiberdade(SimNao privadoDeLiberdade) {
        this.privadoDeLiberdade = privadoDeLiberdade;
    }

    public SimNao getRecebeAuxilioReclusao() {
        return recebeAuxilioReclusao;
    }

    public void setRecebeAuxilioReclusao(SimNao recebeAuxilioReclusao) {
        this.recebeAuxilioReclusao = recebeAuxilioReclusao;
    }

    public SimNao getFamiliaVisitandoPreso() {
        return familiaVisitandoPreso;
    }

    public void setFamiliaVisitandoPreso(SimNao familiaVisitandoPreso) {
        this.familiaVisitandoPreso = familiaVisitandoPreso;
    }

    public SimNao getCumprimentoSemiAberto() {
        return cumprimentoSemiAberto;
    }

    public void setCumprimentoSemiAberto(SimNao cumprimentoSemiAberto) {
        this.cumprimentoSemiAberto = cumprimentoSemiAberto;
    }

    public OpcaoAvaliacao getAvaliacaoPoliciaMilitar() {
        return avaliacaoPoliciaMilitar;
    }

    public void setAvaliacaoPoliciaMilitar(OpcaoAvaliacao avaliacaoPoliciaMilitar) {
        this.avaliacaoPoliciaMilitar = avaliacaoPoliciaMilitar;
    }

    public OpcaoAvaliacao getAvaliacaoPoliciaCivel() {
        return avaliacaoPoliciaCivel;
    }

    public void setAvaliacaoPoliciaCivel(OpcaoAvaliacao avaliacaoPoliciaCivel) {
        this.avaliacaoPoliciaCivel = avaliacaoPoliciaCivel;
    }

    public SimNao getNaoAtendidoTelefonePolicia() {
        return naoAtendidoTelefonePolicia;
    }

    public void setNaoAtendidoTelefonePolicia(SimNao naoAtendidoTelefonePolicia) {
        this.naoAtendidoTelefonePolicia = naoAtendidoTelefonePolicia;
    }

    public SimNao getNaoAtendidoDelegacia() {
        return naoAtendidoDelegacia;
    }

    public void setNaoAtendidoDelegacia(SimNao naoAtendidoDelegacia) {
        this.naoAtendidoDelegacia = naoAtendidoDelegacia;
    }

    public OpcaoAvaliacao getAvaliacaoAgenteSaude() {
        return avaliacaoAgenteSaude;
    }

    public void setAvaliacaoAgenteSaude(OpcaoAvaliacao avaliacaoAgenteSaude) {
        this.avaliacaoAgenteSaude = avaliacaoAgenteSaude;
    }

    public OpcaoAvaliacao getAvaliacaoUBS() {
        return avaliacaoUBS;
    }

    public void setAvaliacaoUBS(OpcaoAvaliacao avaliacaoUBS) {
        this.avaliacaoUBS = avaliacaoUBS;
    }

    public Long getRendaFamiliarId() {
        return rendaFamiliarId;
    }

    public void setRendaFamiliarId(Long rendaFamiliarId) {
        this.rendaFamiliarId = rendaFamiliarId;
    }

    public String getRendaFamiliarFaixaRenda() {
        return rendaFamiliarFaixaRenda;
    }

    public void setRendaFamiliarFaixaRenda(String rendaFamiliarFaixaRenda) {
        this.rendaFamiliarFaixaRenda = rendaFamiliarFaixaRenda;
    }

    public Long getPeriodoVisitaAgenteId() {
        return periodoVisitaAgenteId;
    }

    public void setPeriodoVisitaAgenteId(Long periodicidadeId) {
        this.periodoVisitaAgenteId = periodicidadeId;
    }

    public String getPeriodoVisitaAgenteDescricao() {
        return periodoVisitaAgenteDescricao;
    }

    public void setPeriodoVisitaAgenteDescricao(String periodicidadeDescricao) {
        this.periodoVisitaAgenteDescricao = periodicidadeDescricao;
    }

    public Long getReligiaoId() {
        return religiaoId;
    }

    public void setReligiaoId(Long religiaoId) {
        this.religiaoId = religiaoId;
    }

    public String getReligiaoDescricao() {
        return religiaoDescricao;
    }

    public void setReligiaoDescricao(String religiaoDescricao) {
        this.religiaoDescricao = religiaoDescricao;
    }

    public Long getResponsavelLarId() {
        return responsavelLarId;
    }

    public void setResponsavelLarId(Long responsavelLarId) {
        this.responsavelLarId = responsavelLarId;
    }

    public String getResponsavelLarResponsavel() {
        return responsavelLarResponsavel;
    }

    public void setResponsavelLarResponsavel(String responsavelLarResponsavel) {
        this.responsavelLarResponsavel = responsavelLarResponsavel;
    }

    public Set<ServicoPublicoDTO> getServicoPublicos() {
        return servicoPublicos;
    }

    public void setServicoPublicos(Set<ServicoPublicoDTO> servicoPublicos) {
        this.servicoPublicos = servicoPublicos;
    }

    public Set<EspacoLazerDTO> getEspacoLazers() {
        return espacoLazers;
    }

    public void setEspacoLazers(Set<EspacoLazerDTO> espacoLazers) {
        this.espacoLazers = espacoLazers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FormDTO formDTO = (FormDTO) o;
        if(formDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FormDTO{" +
            "id=" + getId() +
            ", responsavelPelaInformacao='" + getResponsavelPelaInformacao() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", rua='" + getRua() + "'" +
            ", numero='" + getNumero() + "'" +
            ", bairroSetor='" + getBairroSetor() + "'" +
            ", localizacaoDoDomicilio='" + getLocalizacaoDoDomicilio() + "'" +
            ", situacaoDaResidencia='" + getSituacaoDaResidencia() + "'" +
            ", numeroDaQuadra='" + getNumeroDaQuadra() + "'" +
            ", numeroDaCasa='" + getNumeroDaCasa() + "'" +
            ", quantidadeDeResidentes=" + getQuantidadeDeResidentes() +
            ", residenteFrenquentadoEscola='" + getResidenteFrenquentadoEscola() + "'" +
            ", privadoDeLiberdade='" + getPrivadoDeLiberdade() + "'" +
            ", recebeAuxilioReclusao='" + getRecebeAuxilioReclusao() + "'" +
            ", familiaVisitandoPreso='" + getFamiliaVisitandoPreso() + "'" +
            ", cumprimentoSemiAberto='" + getCumprimentoSemiAberto() + "'" +
            ", avaliacaoPoliciaMilitar='" + getAvaliacaoPoliciaMilitar() + "'" +
            ", avaliacaoPoliciaCivel='" + getAvaliacaoPoliciaCivel() + "'" +
            ", naoAtendidoTelefonePolicia='" + getNaoAtendidoTelefonePolicia() + "'" +
            ", naoAtendidoDelegacia='" + getNaoAtendidoDelegacia() + "'" +
            ", avaliacaoAgenteSaude='" + getAvaliacaoAgenteSaude() + "'" +
            ", avaliacaoUBS='" + getAvaliacaoUBS() + "'" +
            "}";
    }
}
