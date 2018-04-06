package br.mp.mpro.caopapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import br.mp.mpro.caopapp.domain.enumeration.LocalizacaoDomicilio;

import br.mp.mpro.caopapp.domain.enumeration.SituacaoResidencia;

import br.mp.mpro.caopapp.domain.enumeration.SimNao;

import br.mp.mpro.caopapp.domain.enumeration.OpcaoAvaliacao;

/**
 * A Form.
 */
@Entity
@Table(name = "form")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Form implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "responsavel_pela_informacao", nullable = false)
    private String responsavelPelaInformacao;

    @Column(name = "telefone")
    private String telefone;

    @NotNull
    @Column(name = "rua", nullable = false)
    private String rua;

    @Column(name = "numero")
    private String numero;

    @Column(name = "bairro_setor")
    private String bairroSetor;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "localizacao_do_domicilio", nullable = false)
    private LocalizacaoDomicilio localizacaoDoDomicilio;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "situacao_da_residencia", nullable = false)
    private SituacaoResidencia situacaoDaResidencia;

    @Column(name = "numero_da_quadra")
    private String numeroDaQuadra;

    @Column(name = "numero_da_casa")
    private String numeroDaCasa;

    @NotNull
    @Min(value = 1)
    @Column(name = "quantidade_de_residentes", nullable = false)
    private Integer quantidadeDeResidentes;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "residente_frenquentado_escola", nullable = false)
    private SimNao residenteFrenquentadoEscola;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "privado_de_liberdade", nullable = false)
    private SimNao privadoDeLiberdade;

    @Enumerated(EnumType.STRING)
    @Column(name = "recebe_auxilio_reclusao")
    private SimNao recebeAuxilioReclusao;

    @Enumerated(EnumType.STRING)
    @Column(name = "familia_visitando_preso")
    private SimNao familiaVisitandoPreso;

    @Enumerated(EnumType.STRING)
    @Column(name = "cumprimento_semi_aberto")
    private SimNao cumprimentoSemiAberto;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "avaliacao_policia_militar", nullable = false)
    private OpcaoAvaliacao avaliacaoPoliciaMilitar;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "avaliacao_policia_civel", nullable = false)
    private OpcaoAvaliacao avaliacaoPoliciaCivel;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nao_atendido_telefone_policia", nullable = false)
    private SimNao naoAtendidoTelefonePolicia;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nao_atendido_delegacia", nullable = false)
    private SimNao naoAtendidoDelegacia;

    @Enumerated(EnumType.STRING)
    @Column(name = "avaliacao_agente_saude")
    private OpcaoAvaliacao avaliacaoAgenteSaude;

    @Enumerated(EnumType.STRING)
    @Column(name = "avaliacao_ubs")
    private OpcaoAvaliacao avaliacaoUBS;

    @ManyToOne(optional = false)
    @NotNull
    private RendaFamiliar rendaFamiliar;

    @ManyToOne(optional = false)
    @NotNull
    private Periodicidade periodoVisitaAgente;

    @ManyToOne(optional = false)
    @NotNull
    private Religiao religiao;

    @ManyToOne(optional = false)
    @NotNull
    private ResponsavelLar responsavelLar;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "form_servico_publico",
               joinColumns = @JoinColumn(name="forms_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="servico_publicos_id", referencedColumnName="id"))
    private Set<ServicoPublico> servicoPublicos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "form_espaco_lazer",
               joinColumns = @JoinColumn(name="forms_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="espaco_lazers_id", referencedColumnName="id"))
    private Set<EspacoLazer> espacoLazers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponsavelPelaInformacao() {
        return responsavelPelaInformacao;
    }

    public Form responsavelPelaInformacao(String responsavelPelaInformacao) {
        this.responsavelPelaInformacao = responsavelPelaInformacao;
        return this;
    }

    public void setResponsavelPelaInformacao(String responsavelPelaInformacao) {
        this.responsavelPelaInformacao = responsavelPelaInformacao;
    }

    public String getTelefone() {
        return telefone;
    }

    public Form telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getRua() {
        return rua;
    }

    public Form rua(String rua) {
        this.rua = rua;
        return this;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public Form numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairroSetor() {
        return bairroSetor;
    }

    public Form bairroSetor(String bairroSetor) {
        this.bairroSetor = bairroSetor;
        return this;
    }

    public void setBairroSetor(String bairroSetor) {
        this.bairroSetor = bairroSetor;
    }

    public LocalizacaoDomicilio getLocalizacaoDoDomicilio() {
        return localizacaoDoDomicilio;
    }

    public Form localizacaoDoDomicilio(LocalizacaoDomicilio localizacaoDoDomicilio) {
        this.localizacaoDoDomicilio = localizacaoDoDomicilio;
        return this;
    }

    public void setLocalizacaoDoDomicilio(LocalizacaoDomicilio localizacaoDoDomicilio) {
        this.localizacaoDoDomicilio = localizacaoDoDomicilio;
    }

    public SituacaoResidencia getSituacaoDaResidencia() {
        return situacaoDaResidencia;
    }

    public Form situacaoDaResidencia(SituacaoResidencia situacaoDaResidencia) {
        this.situacaoDaResidencia = situacaoDaResidencia;
        return this;
    }

    public void setSituacaoDaResidencia(SituacaoResidencia situacaoDaResidencia) {
        this.situacaoDaResidencia = situacaoDaResidencia;
    }

    public String getNumeroDaQuadra() {
        return numeroDaQuadra;
    }

    public Form numeroDaQuadra(String numeroDaQuadra) {
        this.numeroDaQuadra = numeroDaQuadra;
        return this;
    }

    public void setNumeroDaQuadra(String numeroDaQuadra) {
        this.numeroDaQuadra = numeroDaQuadra;
    }

    public String getNumeroDaCasa() {
        return numeroDaCasa;
    }

    public Form numeroDaCasa(String numeroDaCasa) {
        this.numeroDaCasa = numeroDaCasa;
        return this;
    }

    public void setNumeroDaCasa(String numeroDaCasa) {
        this.numeroDaCasa = numeroDaCasa;
    }

    public Integer getQuantidadeDeResidentes() {
        return quantidadeDeResidentes;
    }

    public Form quantidadeDeResidentes(Integer quantidadeDeResidentes) {
        this.quantidadeDeResidentes = quantidadeDeResidentes;
        return this;
    }

    public void setQuantidadeDeResidentes(Integer quantidadeDeResidentes) {
        this.quantidadeDeResidentes = quantidadeDeResidentes;
    }

    public SimNao getResidenteFrenquentadoEscola() {
        return residenteFrenquentadoEscola;
    }

    public Form residenteFrenquentadoEscola(SimNao residenteFrenquentadoEscola) {
        this.residenteFrenquentadoEscola = residenteFrenquentadoEscola;
        return this;
    }

    public void setResidenteFrenquentadoEscola(SimNao residenteFrenquentadoEscola) {
        this.residenteFrenquentadoEscola = residenteFrenquentadoEscola;
    }

    public SimNao getPrivadoDeLiberdade() {
        return privadoDeLiberdade;
    }

    public Form privadoDeLiberdade(SimNao privadoDeLiberdade) {
        this.privadoDeLiberdade = privadoDeLiberdade;
        return this;
    }

    public void setPrivadoDeLiberdade(SimNao privadoDeLiberdade) {
        this.privadoDeLiberdade = privadoDeLiberdade;
    }

    public SimNao getRecebeAuxilioReclusao() {
        return recebeAuxilioReclusao;
    }

    public Form recebeAuxilioReclusao(SimNao recebeAuxilioReclusao) {
        this.recebeAuxilioReclusao = recebeAuxilioReclusao;
        return this;
    }

    public void setRecebeAuxilioReclusao(SimNao recebeAuxilioReclusao) {
        this.recebeAuxilioReclusao = recebeAuxilioReclusao;
    }

    public SimNao getFamiliaVisitandoPreso() {
        return familiaVisitandoPreso;
    }

    public Form familiaVisitandoPreso(SimNao familiaVisitandoPreso) {
        this.familiaVisitandoPreso = familiaVisitandoPreso;
        return this;
    }

    public void setFamiliaVisitandoPreso(SimNao familiaVisitandoPreso) {
        this.familiaVisitandoPreso = familiaVisitandoPreso;
    }

    public SimNao getCumprimentoSemiAberto() {
        return cumprimentoSemiAberto;
    }

    public Form cumprimentoSemiAberto(SimNao cumprimentoSemiAberto) {
        this.cumprimentoSemiAberto = cumprimentoSemiAberto;
        return this;
    }

    public void setCumprimentoSemiAberto(SimNao cumprimentoSemiAberto) {
        this.cumprimentoSemiAberto = cumprimentoSemiAberto;
    }

    public OpcaoAvaliacao getAvaliacaoPoliciaMilitar() {
        return avaliacaoPoliciaMilitar;
    }

    public Form avaliacaoPoliciaMilitar(OpcaoAvaliacao avaliacaoPoliciaMilitar) {
        this.avaliacaoPoliciaMilitar = avaliacaoPoliciaMilitar;
        return this;
    }

    public void setAvaliacaoPoliciaMilitar(OpcaoAvaliacao avaliacaoPoliciaMilitar) {
        this.avaliacaoPoliciaMilitar = avaliacaoPoliciaMilitar;
    }

    public OpcaoAvaliacao getAvaliacaoPoliciaCivel() {
        return avaliacaoPoliciaCivel;
    }

    public Form avaliacaoPoliciaCivel(OpcaoAvaliacao avaliacaoPoliciaCivel) {
        this.avaliacaoPoliciaCivel = avaliacaoPoliciaCivel;
        return this;
    }

    public void setAvaliacaoPoliciaCivel(OpcaoAvaliacao avaliacaoPoliciaCivel) {
        this.avaliacaoPoliciaCivel = avaliacaoPoliciaCivel;
    }

    public SimNao getNaoAtendidoTelefonePolicia() {
        return naoAtendidoTelefonePolicia;
    }

    public Form naoAtendidoTelefonePolicia(SimNao naoAtendidoTelefonePolicia) {
        this.naoAtendidoTelefonePolicia = naoAtendidoTelefonePolicia;
        return this;
    }

    public void setNaoAtendidoTelefonePolicia(SimNao naoAtendidoTelefonePolicia) {
        this.naoAtendidoTelefonePolicia = naoAtendidoTelefonePolicia;
    }

    public SimNao getNaoAtendidoDelegacia() {
        return naoAtendidoDelegacia;
    }

    public Form naoAtendidoDelegacia(SimNao naoAtendidoDelegacia) {
        this.naoAtendidoDelegacia = naoAtendidoDelegacia;
        return this;
    }

    public void setNaoAtendidoDelegacia(SimNao naoAtendidoDelegacia) {
        this.naoAtendidoDelegacia = naoAtendidoDelegacia;
    }

    public OpcaoAvaliacao getAvaliacaoAgenteSaude() {
        return avaliacaoAgenteSaude;
    }

    public Form avaliacaoAgenteSaude(OpcaoAvaliacao avaliacaoAgenteSaude) {
        this.avaliacaoAgenteSaude = avaliacaoAgenteSaude;
        return this;
    }

    public void setAvaliacaoAgenteSaude(OpcaoAvaliacao avaliacaoAgenteSaude) {
        this.avaliacaoAgenteSaude = avaliacaoAgenteSaude;
    }

    public OpcaoAvaliacao getAvaliacaoUBS() {
        return avaliacaoUBS;
    }

    public Form avaliacaoUBS(OpcaoAvaliacao avaliacaoUBS) {
        this.avaliacaoUBS = avaliacaoUBS;
        return this;
    }

    public void setAvaliacaoUBS(OpcaoAvaliacao avaliacaoUBS) {
        this.avaliacaoUBS = avaliacaoUBS;
    }

    public RendaFamiliar getRendaFamiliar() {
        return rendaFamiliar;
    }

    public Form rendaFamiliar(RendaFamiliar rendaFamiliar) {
        this.rendaFamiliar = rendaFamiliar;
        return this;
    }

    public void setRendaFamiliar(RendaFamiliar rendaFamiliar) {
        this.rendaFamiliar = rendaFamiliar;
    }

    public Periodicidade getPeriodoVisitaAgente() {
        return periodoVisitaAgente;
    }

    public Form periodoVisitaAgente(Periodicidade periodicidade) {
        this.periodoVisitaAgente = periodicidade;
        return this;
    }

    public void setPeriodoVisitaAgente(Periodicidade periodicidade) {
        this.periodoVisitaAgente = periodicidade;
    }

    public Religiao getReligiao() {
        return religiao;
    }

    public Form religiao(Religiao religiao) {
        this.religiao = religiao;
        return this;
    }

    public void setReligiao(Religiao religiao) {
        this.religiao = religiao;
    }

    public ResponsavelLar getResponsavelLar() {
        return responsavelLar;
    }

    public Form responsavelLar(ResponsavelLar responsavelLar) {
        this.responsavelLar = responsavelLar;
        return this;
    }

    public void setResponsavelLar(ResponsavelLar responsavelLar) {
        this.responsavelLar = responsavelLar;
    }

    public Set<ServicoPublico> getServicoPublicos() {
        return servicoPublicos;
    }

    public Form servicoPublicos(Set<ServicoPublico> servicoPublicos) {
        this.servicoPublicos = servicoPublicos;
        return this;
    }

    public Form addServicoPublico(ServicoPublico servicoPublico) {
        this.servicoPublicos.add(servicoPublico);
        return this;
    }

    public Form removeServicoPublico(ServicoPublico servicoPublico) {
        this.servicoPublicos.remove(servicoPublico);
        return this;
    }

    public void setServicoPublicos(Set<ServicoPublico> servicoPublicos) {
        this.servicoPublicos = servicoPublicos;
    }

    public Set<EspacoLazer> getEspacoLazers() {
        return espacoLazers;
    }

    public Form espacoLazers(Set<EspacoLazer> espacoLazers) {
        this.espacoLazers = espacoLazers;
        return this;
    }

    public Form addEspacoLazer(EspacoLazer espacoLazer) {
        this.espacoLazers.add(espacoLazer);
        return this;
    }

    public Form removeEspacoLazer(EspacoLazer espacoLazer) {
        this.espacoLazers.remove(espacoLazer);
        return this;
    }

    public void setEspacoLazers(Set<EspacoLazer> espacoLazers) {
        this.espacoLazers = espacoLazers;
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
        Form form = (Form) o;
        if (form.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), form.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Form{" +
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
