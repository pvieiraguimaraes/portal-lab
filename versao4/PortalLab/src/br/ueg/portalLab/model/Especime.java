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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;
import br.ueg.portalLab.security.model.UsuarioPermissao;

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
	@Attribute(Required = true, SearchField = true)
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
	
	@Column(name = "numero_de_tombo", length=20, nullable=true)
	@Attribute(Required = false, SearchField = true)
	private String numeroDeTombo;
	
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
	private Integer numeroIndividuo;// = new Integer(1);
	
	@ManyToMany(cascade ={CascadeType.PERSIST})
	@JoinTable(name="especime_coletor", 
			joinColumns			={@JoinColumn(name = "id_espe_esco", nullable = false, updatable = false)}, 
			inverseJoinColumns	={@JoinColumn(name = "id_cole_esco", nullable = false, updatable = false)}
			)
	private Set<Coletor> coletores = new HashSet<Coletor>(0);
	
	@Column(name = "observacao", length=500, nullable=true)
	@Attribute(Required = false, SearchField = true)
	private String observacao;
	
	/* geografia */
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pais", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	private ItemGeografico pais;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estado", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	private ItemGeografico estado;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_municipio", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	private ItemGeografico municipio;
	
	@Column(name = "localidade", length=200, nullable=true)
	@Attribute(Required = false, SearchField = true)
	private String localidade;
	
	@Column(name = "latitude", length=50, nullable=true)
	@Attribute(Required = false, SearchField = true)
	private String latitude;
	
	@Column(name = "longitude", length=50, nullable=true)
	@Attribute(Required = false, SearchField = true)
	private String longitude;
	
	@Column(name = "altitude", length=50, nullable=true)
	@Attribute(Required = false, SearchField = true)
	private String altitude;
	
	@Column(name = "profundidade", length=50, nullable=true)
	@Attribute(Required = false, SearchField = true)
	private String profundidade;
	
	@Column(name = "precisao", length=50, nullable=true)
	@Attribute(Required = false, SearchField = true)
	private String precisao;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_massaDAgua", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	private MassaDAgua massaDAgua;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_datum", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	private Datum datum;
	
	@Column(name = "observacao_geografia", length=500, nullable=true)
	@Attribute(Required = false, SearchField = true)
	private String observacaoGeografria;
	
	// atributos guia txonomia
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_reino", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	private ItemTaxonomico reino;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_filo", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	private ItemTaxonomico filo;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_classe", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	private ItemTaxonomico classe;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_sub_classe", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	private ItemTaxonomico subClasse;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_familia", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	private ItemTaxonomico familia;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_sub_familia", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	private ItemTaxonomico subFamilia;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ordem", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	private ItemTaxonomico ordem;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_sub_ordem", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	private ItemTaxonomico subOrdem;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_genero", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	private ItemTaxonomico genero;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_epiteto_especifico", insertable = true, updatable = true, nullable=true)
	@Attribute(Required = false, SearchField = false)
	private ItemTaxonomico epitetoEspecifico;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "especime",cascade = CascadeType.ALL, orphanRemoval=true)// mappedBy indica o atributo da entidade many
	@Attribute(Required = false, SearchField = false)
	private Set<EspecimeDeterminador> especimeDeterminadores = new HashSet<EspecimeDeterminador>(0);
	
	@Column(name = "imprecisao", length=100, nullable=true)
	@Attribute(Required = false, SearchField = true)
	private String imprecisao;
	
	@ManyToMany(cascade ={CascadeType.PERSIST})
	@JoinTable(name="especime_autor", 
			joinColumns			={@JoinColumn(name = "id_espe_esau", nullable = false, updatable = false)}, 
			inverseJoinColumns	={@JoinColumn(name = "id_auto_esau", nullable = false, updatable = false)}
			)
	private Set<Autor> autores = new HashSet<Autor>(0);
	
	@Column(name = "observacao_taxonomia", length=500, nullable=true)
	@Attribute(Required = false, SearchField = true)
	private String observacaoTaxonomia;
	
	
	
	
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

	/**
	 * @return the numeroDeTombo
	 */
	public String getNumeroDeTombo() {
		return numeroDeTombo;
	}

	/**
	 * @param numeroDeTombo the numeroDeTombo to set
	 */
	public void setNumeroDeTombo(String numeroDeTombo) {
		this.numeroDeTombo = numeroDeTombo;
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

	public ItemGeografico getPais() {
		return pais;
	}

	public void setPais(ItemGeografico pais) {
		this.pais = pais;
	}

	public ItemGeografico getEstado() {
		return estado;
	}

	public void setEstado(ItemGeografico estado) {
		this.estado = estado;
	}

	public ItemGeografico getMunicipio() {
		return municipio;
	}

	public void setMunicipio(ItemGeografico municipio) {
		this.municipio = municipio;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public String getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(String profundidade) {
		this.profundidade = profundidade;
	}

	public String getPrecisao() {
		return precisao;
	}

	public void setPrecisao(String precisao) {
		this.precisao = precisao;
	}

	public MassaDAgua getMassaDAgua() {
		return massaDAgua;
	}

	public void setMassaDAgua(MassaDAgua massaDAgua) {
		this.massaDAgua = massaDAgua;
	}

	public Datum getDatum() {
		return datum;
	}

	public void setDatum(Datum datum) {
		this.datum = datum;
	}

	public String getObservacaoGeografria() {
		return observacaoGeografria;
	}

	public void setObservacaoGeografria(String observacaoGeografria) {
		this.observacaoGeografria = observacaoGeografria;
	}

	public ItemTaxonomico getReino() {
		return reino;
	}

	public void setReino(ItemTaxonomico reino) {
		this.reino = reino;
	}

	public ItemTaxonomico getFilo() {
		return filo;
	}

	public void setFilo(ItemTaxonomico filo) {
		this.filo = filo;
	}

	public ItemTaxonomico getClasse() {
		return classe;
	}

	public void setClasse(ItemTaxonomico classe) {
		this.classe = classe;
	}

	public ItemTaxonomico getSubClasse() {
		return subClasse;
	}

	public void setSubClasse(ItemTaxonomico subClasse) {
		this.subClasse = subClasse;
	}

	public ItemTaxonomico getFamilia() {
		return familia;
	}

	public void setFamilia(ItemTaxonomico familia) {
		this.familia = familia;
	}

	public ItemTaxonomico getSubFamilia() {
		return subFamilia;
	}

	public void setSubFamilia(ItemTaxonomico subFamilia) {
		this.subFamilia = subFamilia;
	}

	public ItemTaxonomico getOrdem() {
		return ordem;
	}

	public void setOrdem(ItemTaxonomico ordem) {
		this.ordem = ordem;
	}

	public ItemTaxonomico getSubOrdem() {
		return subOrdem;
	}

	public void setSubOrdem(ItemTaxonomico subOrdem) {
		this.subOrdem = subOrdem;
	}

	public ItemTaxonomico getGenero() {
		return genero;
	}

	public void setGenero(ItemTaxonomico genero) {
		this.genero = genero;
	}

	public ItemTaxonomico getEpitetoEspecifico() {
		return epitetoEspecifico;
	}

	public void setEpitetoEspecifico(ItemTaxonomico epitetoEspecifico) {
		this.epitetoEspecifico = epitetoEspecifico;
	}

	public Set<EspecimeDeterminador> getEspecimeDeterminadores() {
		return especimeDeterminadores;
	}

	public void setEspecimeDeterminadores(
			Set<EspecimeDeterminador> especimeDeterminadores) {
		this.especimeDeterminadores = especimeDeterminadores;
	}

	public String getImprecisao() {
		return imprecisao;
	}

	public void setImprecisao(String imprecisao) {
		this.imprecisao = imprecisao;
	}

	public Set<Autor> getAutores() {
		return autores;
	}

	public void setAutores(Set<Autor> autores) {
		this.autores = autores;
	}

	public String getObservacaoTaxonomia() {
		return observacaoTaxonomia;
	}

	public void setObservacaoTaxonomia(String observacaoTaxonomia) {
		this.observacaoTaxonomia = observacaoTaxonomia;
	}
}
