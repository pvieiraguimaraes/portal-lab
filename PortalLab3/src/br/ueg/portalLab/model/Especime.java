package br.ueg.portalLab.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;

@javax.persistence.Entity
@SuppressWarnings("serial")
@Table(name="especime")
public class Especime extends Entity {

	@Id()
	@GeneratedValue
	@Column(name = "id_especime")
	private Long id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_laboratorio", insertable = true, updatable = true, nullable=false)
	@Attribute(Required = true, SearchField = false)
	private Laboratorio laboratorio;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_grupo_endereco_fisico", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	private GrupoEnderecoFisico grupoEnderecoFisico; 
	
	@Column(name = "codigo_catalogo", length=20, nullable=false)
	@Attribute(Required = true, SearchField = true)
	private String codigoCatalogo;
	
	@Column(name = "codigo_coleta", length=20, nullable=true)
	@Attribute(Required = false, SearchField = true)
	private String codigoColeta;
	
	@Column(name = "data_coleta")
	@Attribute(Required = false, SearchField = false)
	private Date dataColeta;
		
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estagio_desenvolvimento", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	private EstagioDesenvolvimento estagioDesenvolvimento; 
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_fenologia", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	private Fenologia fenologia;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_metodo_coleta", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	private MetodoColeta metodoColeta;
		
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_sexo", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	private Sexo sexo;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_de_montagem", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	private TipoDeMontagem tipoDeMontagem;
	
	@Attribute(Required = true, SearchField = false)
	@Column(name="numero_individuo")
	private Integer numeroIndividuo = new Integer(1);
	
	@ManyToMany(cascade ={CascadeType.PERSIST})
	@JoinTable(name="especime_coletor", 
			joinColumns			={@JoinColumn(name = "id_espe_esco", nullable = false, updatable = false)}, 
			inverseJoinColumns	={@JoinColumn(name = "id_cole_esco", nullable = false, updatable = false)}
			)
	private Set<Coletor> coletores = new HashSet<Coletor>(0);
	
	@Column(name = "observacao", length=20, nullable=true)
	@Attribute(Required = false, SearchField = true)
	private String observacao;
	
	
	
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoCatalogo() {
		return codigoCatalogo;
	}

	public void setCodigoCatalogo(String codigoCatalogo) {
		this.codigoCatalogo = codigoCatalogo;
	}

	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	public GrupoEnderecoFisico getGrupoEnderecoFisico() {
		return grupoEnderecoFisico;
	}

	public void setGrupoEnderecoFisico(GrupoEnderecoFisico grupoEnderecoFisico) {
		this.grupoEnderecoFisico = grupoEnderecoFisico;
	}

	public String getCodigoColeta() {
		return codigoColeta;
	}

	public void setCodigoColeta(String codigoColeta) {
		this.codigoColeta = codigoColeta;
	}

	public Date getDataColeta() {
		return dataColeta;
	}

	public void setDataColeta(Date dataColeta) {
		this.dataColeta = dataColeta;
	}

	public EstagioDesenvolvimento getEstagioDesenvolvimento() {
		return estagioDesenvolvimento;
	}

	public void setEstagioDesenvolvimento(
			EstagioDesenvolvimento estagioDesenvolvimento) {
		this.estagioDesenvolvimento = estagioDesenvolvimento;
	}

	public Fenologia getFenologia() {
		return fenologia;
	}

	public void setFenologia(Fenologia fenologia) {
		this.fenologia = fenologia;
	}

	public MetodoColeta getMetodoColeta() {
		return metodoColeta;
	}

	public void setMetodoColeta(MetodoColeta metodoColeta) {
		this.metodoColeta = metodoColeta;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public TipoDeMontagem getTipoDeMontagem() {
		return tipoDeMontagem;
	}

	public void setTipoDeMontagem(TipoDeMontagem tipoDeMontagem) {
		this.tipoDeMontagem = tipoDeMontagem;
	}

	public Integer getNumeroIndividuo() {
		return numeroIndividuo;
	}

	public void setNumeroIndividuo(Integer numeroIndividuo) {
		this.numeroIndividuo = numeroIndividuo;
	}

	public Set<Coletor> getColetores() {
		return coletores;
	}

	public void setColetores(Set<Coletor> coletores) {
		this.coletores = coletores;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}
