package br.ueg.portalLab.view.composer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.databind.BindingListModelSet;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.builderSoft.view.zk.composer.ComposerController;
import br.ueg.portalLab.control.EspecimeControl;
import br.ueg.portalLab.model.Autor;
import br.ueg.portalLab.model.Colecao;
import br.ueg.portalLab.model.Coletor;
import br.ueg.portalLab.model.Datum;
import br.ueg.portalLab.model.Determinador;
import br.ueg.portalLab.model.Especime;
import br.ueg.portalLab.model.EspecimeDeterminador;
import br.ueg.portalLab.model.EstagioDesenvolvimento;
import br.ueg.portalLab.model.Fenologia;
import br.ueg.portalLab.model.ItemGeografico;
import br.ueg.portalLab.model.ItemTaxonomico;
import br.ueg.portalLab.model.Laboratorio;
import br.ueg.portalLab.model.MassaDAgua;
import br.ueg.portalLab.model.MetodoColeta;
import br.ueg.portalLab.model.NivelTaxonomico;
import br.ueg.portalLab.model.Sexo;
import br.ueg.portalLab.model.TipoDeMontagem;

@Component
@Scope("prototype")
public class EspecimeComposer extends ComposerController<Especime> {

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.zk.composer.ComposerController#searchEntity()
	 */
	@Override
	public boolean searchEntity() {
		return super.searchEntity();
	}

	@Override
	public void doAfterCompose(org.zkoss.zk.ui.Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		this.setEditForm(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.ueg.builderSoft.view.zk.composer.ComposerController#cancelEditEntity()
	 */
	@Override
	public void cancelEditEntity() {
		// TODO Auto-generated method stub
		super.cancelEditEntity();
	}	

	@AttributeView(key = "laboratorio", isEntityValue = true, fieldType = Laboratorio.class, isVisible = true, caption = "especime_laboratorioColumn", isSearchField=true)
	private Laboratorio fldLaboratorio;

	@AttributeView(key = "colecao", isEntityValue = true, fieldType = Colecao.class, isVisible = true, caption = "especime_colecaoColumn", isSearchField=true)
	private Colecao fldColecao;

	@AttributeView(key = "codigoCatalogo", isEntityValue = true, fieldType = String.class, isVisible = true, caption = "especime_codigoCatalogoColumn", isSearchField=true)
	private String fldCodigoCatalogo;

	@AttributeView(key = "codigoColeta", isEntityValue = true, fieldType = String.class, isVisible = true, caption = "especime_codigoColetaColumn", isSearchField=true)
	private String fldCodigoColeta;
	
	@AttributeView(key = "numeroDeTombo", isEntityValue = true, fieldType = String.class, isVisible = true, caption = "especime_numeroDeTomboColumn", isSearchField=true)
	private String fldNumeroDeTombo;

	@AttributeView(key = "dataColeta", isEntityValue = true, fieldType = Date.class, isVisible = true, caption = "especime_dataColetaColumn")
	private Date fldDataColeta;

	@AttributeView(key = "estagioDesenvolvimento", isEntityValue = true, fieldType = EstagioDesenvolvimento.class, isVisible = true, caption = "especime_estagioDesenvolvimentoColumn")
	private EstagioDesenvolvimento fldEstagioDesenvolvimento;

	@AttributeView(key = "fenologia", isEntityValue = true, fieldType = Fenologia.class, isVisible = true, caption = "especime_fenologiaColumn")
	private Fenologia fldFenologia;

	@AttributeView(key = "metodoColeta", isEntityValue = true, fieldType = MetodoColeta.class, isVisible = true, caption = "especime_metodoColetaColumn")
	private MetodoColeta fldMetodoColeta;

	@AttributeView(key = "sexo", isEntityValue = true, fieldType = Sexo.class, isVisible = true, caption = "especime_sexoColetaColumn")
	private Sexo fldSexo;

	@AttributeView(key = "tipoDeMontagem", isEntityValue = true, fieldType = TipoDeMontagem.class, isVisible = true, caption = "especime_tipoDeMontagemColumn")
	private TipoDeMontagem fldTipoDeMontagem;

	@AttributeView(key = "numeroIndividuo", isEntityValue = true, fieldType = Integer.class, isVisible = true, caption = "especime_numeroIndividuoColumn")
	private Integer fldNumeroIndividuo;// = new Integer(1);

	@AttributeView(key = "coletores", isEntityValue = true, fieldType = Set.class, isVisible = true, caption = "especime_coletoresColumn")
	private Set<Coletor> fldColetores = new HashSet<Coletor>(0);

	@AttributeView(key = "observacao", isEntityValue = true, fieldType = String.class, isVisible = true, caption = "especime_observacaoColumn")
	private String fldObservacao;

	@AttributeView(key = "buscaLocal", isEntityValue = false, fieldType = String.class, isVisible = true, caption = "especime_buscaLocalColumn")
	private String fldBuscaLocal;

	@AttributeView(key = "buscaEspecime", isEntityValue = false, fieldType = String.class, isVisible = true, caption = "especime_buscaEspecimeColumn")
	private String fldBuscaEspecime;

	@AttributeView(key = "buscaCatalogo", isEntityValue = false, fieldType = String.class, isVisible = true, caption = "especime_buscaCatalogoColumn")
	private String fldBuscaCatalogo;

	/* geografia */
	@AttributeView(key = "pais", isEntityValue = true, fieldType = ItemGeografico.class, isVisible = true, caption = "especime_paisColumn")
	private ItemGeografico fldPais;

	@AttributeView(key = "estado", isEntityValue = true, fieldType = ItemGeografico.class, isVisible = true, caption = "especime_estadoColumn")
	private ItemGeografico fldEstado;

	@AttributeView(key = "municipio", isEntityValue = true, fieldType = ItemGeografico.class, isVisible = true, caption = "especime_municipioColumn")
	private ItemGeografico fldMunicipio;

	@AttributeView(key = "localidade", isEntityValue = true, fieldType = String.class, isVisible = true, caption = "especime_localidadeColumn")
	private String fldLocalidade;

	@AttributeView(key = "latitude", isEntityValue = true, fieldType = String.class, isVisible = true, caption = "especime_latitudeColumn")
	private String fldLatitude;

	@AttributeView(key = "longitude", isEntityValue = true, fieldType = String.class, isVisible = true, caption = "especime_longitudeColumn")
	private String fldLongitude;

	@AttributeView(key = "altitude", isEntityValue = true, fieldType = String.class, isVisible = true, caption = "especime_altitudeColumn")
	private String fldAltitude;

	@AttributeView(key = "profundidade", isEntityValue = true, fieldType = String.class, isVisible = true, caption = "especime_profundidadeColumn")
	private String fldProfundidade;

	@AttributeView(key = "precisao", isEntityValue = true, fieldType = String.class, isVisible = true, caption = "especime_precisaoColumn")
	private String fldPrecisao;

	@AttributeView(key = "massaDAgua", isEntityValue = true, fieldType = MassaDAgua.class, isVisible = true, caption = "especime_massaDAguaColumn")
	private MassaDAgua fldMassaDAgua;

	@AttributeView(key = "datum", isEntityValue = true, fieldType = Datum.class, isVisible = true, caption = "especime_datumColumn")
	private Datum fldDatum;

	@AttributeView(key = "observacaoGeografria", isEntityValue = true, fieldType = String.class, isVisible = true, caption = "especime_observacaoGeografriaColumn")
	private String fldObservacaoGeografria;

	// Atributos guia taxonomia

	@AttributeView(key = "reino", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true, caption = "especime_reinoColumn")
	private ItemTaxonomico fldReino;

	@AttributeView(key = "filo", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true, caption = "especime_filoColumn")
	private ItemTaxonomico fldFilo;

	@AttributeView(key = "classe", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true, caption = "especime_classeColumn")
	private ItemTaxonomico fldClasse;

	@AttributeView(key = "subClasse", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true, caption = "especime_subClasseColumn")
	private ItemTaxonomico fldSubClasse;

	@AttributeView(key = "familia", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true, caption = "especime_familiaColumn")
	private ItemTaxonomico fldFamilia;

	@AttributeView(key = "subFamilia", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true, caption = "especime_subFamiliaColumn")
	private ItemTaxonomico fldSubFamilia;
	
	@AttributeView(key = "tribo", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true, caption = "especime_triboColumn")
	private ItemTaxonomico fldTribo;
	
	@AttributeView(key = "subTribo", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true, caption = "especime_subTriboColumn")
	private ItemTaxonomico fldSubTribo;

	@AttributeView(key = "ordem", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true, caption = "especime_ordemColumn")
	private ItemTaxonomico fldOrdem;

	@AttributeView(key = "subOrdem", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true, caption = "especime_subOrdemColumn")
	private ItemTaxonomico fldSubOrdem;

	@AttributeView(key = "genero", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true, caption = "especime_generoColumn")
	private ItemTaxonomico fldGenero;
	
	@AttributeView(key = "subGenero", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true, caption = "especime_subGeneroColumn")
	private ItemTaxonomico fldSubGenero;

	@AttributeView(key = "epitetoEspecifico", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true, caption = "especime_epitetoEspecificoColumn")
	private ItemTaxonomico fldEpitetoEspecifico;

	@AttributeView(key = "especimeDeterminadores", isEntityValue = true, fieldType = Set.class, isVisible = true, caption = "especime_especimeDeterminadoresColumn")
	private Set<EspecimeDeterminador> fldEspecimeDeterminadores = new HashSet<EspecimeDeterminador>(
			0);

	@AttributeView(key = "imprecisao", isEntityValue = true, fieldType = String.class, isVisible = true, caption = "especime_imprecisaoColumn")
	private String fldImprecisao;

	@AttributeView(key = "autores", isEntityValue = true, fieldType = Set.class, isVisible = true, caption = "especime_autoresColumn")
	private Set<Autor> fldAutores = new HashSet<Autor>(0);

	@AttributeView(key = "observacaoTaxonomia", isEntityValue = true, fieldType = String.class, isVisible = true, caption = "especime_observacaoTaxonomiaColumn")
	private String fldObservacaoTaxonomia;
	
	@AttributeView(key = "publicarColeta", isEntityValue = true, fieldType = Boolean.class, isVisible = true, caption = "especime_publicarColetaColumn")
	private Boolean fldPublicarColeta;
	
	@AttributeView(key = "publicarGeografia", isEntityValue = true, fieldType = Boolean.class, isVisible = true, caption = "especime_publicarGeografiaColumn")
	private Boolean fldPublicarGeografia;

	@Autowired
	protected EspecimeControl especimeControl;

	@Wire
	protected Window formEspecime;

	@Wire
	protected Window showImage;

	@Wire
	protected Window controlEspecime;

	@Wire
	protected Combobox cmbColecao;
	
	@Wire
	protected Listbox divListBoxColetores;
	
	@Wire
	protected Textbox fldCatalogoCodigo;

	protected AnnotateDataBinder binderForm;
	/**
	 * Filo Cnidaria
	 */
	private static final long serialVersionUID = 6642681501922054728L;

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return Especime.class;
	}

	@Override
	public Control<Especime> getControl() {
		if (this.especimeControl == null) {
			this.especimeControl = (EspecimeControl) SpringFactory
					.getInstance().getBean("especimeControl",
							EspecimeControl.class);
		}
		return this.especimeControl;
	}

	public EspecimeControl getEspecimeControl() {
		return (EspecimeControl) this.getControl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.ueg.builderSoft.view.zk.composer.ComposerController#setSelectedEntity
	 * (br.ueg.builderSoft.model.Entity)
	 */
	@Override
	public void setSelectedEntity(Especime selectedEntity) {
		if (selectedEntity != null) {
			super.setSelectedEntity(selectedEntity);
			this.getEspecimeControl().setSelectedEspecime(selectedEntity);
			// if(selectedEntity!=null && cmbColecao!=null){
			// binder.loadAttribute(cmbColecao, "model");
			// }
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.ueg.builderSoft.view.zk.composer.ComposerController#getSelectedEntity
	 * ()
	 */
	@Override
	public Especime getSelectedEntity() {
		return this.getEspecimeControl().getSelectedEspecime();
	}

	public Laboratorio getFldLaboratorio() {
		return fldLaboratorio;
	}

	public void setFldLaboratorio(Laboratorio fldLaboratorio) {
		this.fldLaboratorio = fldLaboratorio;
	}

	public Colecao getFldColecao() {
		return fldColecao;
	}

	public void setFldColecao(
			Colecao fldColecao) {
		this.fldColecao = fldColecao;
	}

	public String getFldCodigoCatalogo() {
		return fldCodigoCatalogo;
	}

	public void setFldCodigoCatalogo(String fldCodigoCatalogo) {
		this.fldCodigoCatalogo = fldCodigoCatalogo;
	}

	public String getFldCodigoColeta() {
		return fldCodigoColeta;
	}

	public void setFldCodigoColeta(String fldCodigoColeta) {
		this.fldCodigoColeta = fldCodigoColeta;
	}

	/**
	 * @return the fldNumeroDeTombo
	 */
	public String getFldNumeroDeTombo() {
		return fldNumeroDeTombo;
	}

	/**
	 * @param fldNumeroDeTombo the fldNumeroDeTombo to set
	 */
	public void setFldNumeroDeTombo(String fldNumeroDeTombo) {
		this.fldNumeroDeTombo = fldNumeroDeTombo;
	}

	public Date getFldDataColeta() {
		return fldDataColeta;
	}

	public void setFldDataColeta(Date fldDataColeta) {
		this.fldDataColeta = fldDataColeta;
	}

	public EstagioDesenvolvimento getFldEstagioDesenvolvimento() {
		return fldEstagioDesenvolvimento;
	}

	public void setFldEstagioDesenvolvimento(
			EstagioDesenvolvimento fldEstagioDesenvolvimento) {
		this.fldEstagioDesenvolvimento = fldEstagioDesenvolvimento;
	}

	public Fenologia getFldFenologia() {
		return fldFenologia;
	}

	public void setFldFenologia(Fenologia fldFenologia) {
		this.fldFenologia = fldFenologia;
	}

	public MetodoColeta getFldMetodoColeta() {
		return fldMetodoColeta;
	}

	public void setFldMetodoColeta(MetodoColeta fldMetodoColeta) {
		this.fldMetodoColeta = fldMetodoColeta;
	}

	public Sexo getFldSexo() {
		return fldSexo;
	}

	public void setFldSexo(Sexo fldSexo) {
		this.fldSexo = fldSexo;
	}

	public TipoDeMontagem getFldTipoDeMontagem() {
		return fldTipoDeMontagem;
	}

	public void setFldTipoDeMontagem(TipoDeMontagem fldTipoDeMontagem) {
		this.fldTipoDeMontagem = fldTipoDeMontagem;
	}

	public Integer getFldNumeroIndividuo() {
		return fldNumeroIndividuo;
	}

	public void setFldNumeroIndividuo(Integer fldNumeroIndividuo) {
		this.fldNumeroIndividuo = fldNumeroIndividuo;
	}

	public Set<Coletor> getFldColetores() {
		return fldColetores;
	}

	public void setFldColetores(Set<Coletor> fldColetores) {
		this.fldColetores = fldColetores;
	}

	public String getFldObservacao() {
		return fldObservacao;
	}

	public void setFldObservacao(String fldObservacao) {
		this.fldObservacao = fldObservacao;
	}

	public ItemGeografico getFldPais() {
		return fldPais;
	}

	public void setFldPais(ItemGeografico fldPais) {
		this.fldPais = fldPais;
	}

	public ItemGeografico getFldEstado() {
		return fldEstado;
	}

	public void setFldEstado(ItemGeografico fldEstado) {
		this.fldEstado = fldEstado;
	}

	public ItemGeografico getFldMunicipio() {
		return fldMunicipio;
	}

	public void setFldMunicipio(ItemGeografico fldMunicipio) {
		this.fldMunicipio = fldMunicipio;
	}

	public String getFldLocalidade() {
		return fldLocalidade;
	}

	public void setFldLocalidade(String fldLocalidade) {
		this.fldLocalidade = fldLocalidade;
	}

	public String getFldLatitude() {
		return fldLatitude;
	}

	public void setFldLatitude(String fldLatitude) {
		this.fldLatitude = fldLatitude;
	}

	public String getFldLongitude() {
		return fldLongitude;
	}

	public void setFldLongitude(String longitude) {
		this.fldLongitude = longitude;
	}

	public String getFldAltitude() {
		return fldAltitude;
	}

	public void setFldAltitude(String fldAltitude) {
		this.fldAltitude = fldAltitude;
	}

	public String getFldProfundidade() {
		return fldProfundidade;
	}

	public void setFldProfundidade(String fldProfundidade) {
		this.fldProfundidade = fldProfundidade;
	}

	public String getFldPrecisao() {
		return fldPrecisao;
	}

	public void setFldPrecisao(String precisao) {
		this.fldPrecisao = precisao;
	}

	public MassaDAgua getFldMassaDAgua() {
		return fldMassaDAgua;
	}

	public void setFldMassaDAgua(MassaDAgua fldMassaDAgua) {
		this.fldMassaDAgua = fldMassaDAgua;
	}

	public Datum getFldDatum() {
		return fldDatum;
	}

	public void setFldDatum(Datum fldDatum) {
		this.fldDatum = fldDatum;
	}

	public String getFldObservacaoGeografria() {
		return fldObservacaoGeografria;
	}

	public void setFldObservacaoGeografria(String observacaoGeografria) {
		this.fldObservacaoGeografria = observacaoGeografria;
	}

	public ItemTaxonomico getFldReino() {
		return fldReino;
	}

	public void setFldReino(ItemTaxonomico fldReino) {
		this.fldReino = fldReino;
	}

	public ItemTaxonomico getFldFilo() {
		return fldFilo;
	}

	public void setFldFilo(ItemTaxonomico fldFilo) {
		this.fldFilo = fldFilo;
	}

	public ItemTaxonomico getFldClasse() {
		return fldClasse;
	}

	public void setFldClasse(ItemTaxonomico fldClasse) {
		this.fldClasse = fldClasse;
	}

	public ItemTaxonomico getFldSubClasse() {
		return fldSubClasse;
	}

	public void setFldSubClasse(ItemTaxonomico fldSubClasse) {
		this.fldSubClasse = fldSubClasse;
	}

	public ItemTaxonomico getFldFamilia() {
		return fldFamilia;
	}

	public void setFldFamilia(ItemTaxonomico fldFamilia) {
		this.fldFamilia = fldFamilia;
	}

	public ItemTaxonomico getFldSubFamilia() {
		return fldSubFamilia;
	}

	public void setFldSubFamilia(ItemTaxonomico fldSubFamilia) {
		this.fldSubFamilia = fldSubFamilia;
	}

	/**
	 * @return the fldTribo
	 */
	public ItemTaxonomico getFldTribo() {
		return fldTribo;
	}

	/**
	 * @param fldTribo the fldTribo to set
	 */
	public void setFldTribo(ItemTaxonomico fldTribo) {
		this.fldTribo = fldTribo;
	}

	/**
	 * @return the fldSubTribo
	 */
	public ItemTaxonomico getFldSubTribo() {
		return fldSubTribo;
	}

	/**
	 * @param fldSubTribo the fldSubTribo to set
	 */
	public void setFldSubTribo(ItemTaxonomico fldSubTribo) {
		this.fldSubTribo = fldSubTribo;
	}

	/**
	 * @return the fldSubGenero
	 */
	public ItemTaxonomico getFldSubGenero() {
		return fldSubGenero;
	}

	/**
	 * @param fldSubGenero the fldSubGenero to set
	 */
	public void setFldSubGenero(ItemTaxonomico fldSubGenero) {
		this.fldSubGenero = fldSubGenero;
	}

	public ItemTaxonomico getFldOrdem() {
		return fldOrdem;
	}

	public void setFldOrdem(ItemTaxonomico fldOrdem) {
		this.fldOrdem = fldOrdem;
	}

	public ItemTaxonomico getFldSubOrdem() {
		return fldSubOrdem;
	}

	public void setFldSubOrdem(ItemTaxonomico fldSubOrdem) {
		this.fldSubOrdem = fldSubOrdem;
	}

	public ItemTaxonomico getFldGenero() {
		return fldGenero;
	}

	public void setFldGenero(ItemTaxonomico fldGenero) {
		this.fldGenero = fldGenero;
	}

	public ItemTaxonomico getFldEpitetoEspecifico() {
		return fldEpitetoEspecifico;
	}

	public void setFldEpitetoEspecifico(ItemTaxonomico fldEpitetoEspecifico) {
		this.fldEpitetoEspecifico = fldEpitetoEspecifico;
	}

	public Set<EspecimeDeterminador> getFldEspecimeDeterminadores() {
		return fldEspecimeDeterminadores;
	}

	public void setFldEspecimeDeterminadores(
			Set<EspecimeDeterminador> fldEspecimeDeterminadores) {
		this.fldEspecimeDeterminadores = fldEspecimeDeterminadores;
	}

	public Set<Autor> getFldAutores() {
		return fldAutores;
	}

	public void setFldAutores(Set<Autor> fldAutores) {
		this.fldAutores = fldAutores;
	}

	public String getFldObservacaoTaxonomia() {
		return fldObservacaoTaxonomia;
	}

	public void setFldObservacaoTaxonomia(String fldObservacaoTaxonomia) {
		this.fldObservacaoTaxonomia = fldObservacaoTaxonomia;
	}

	public Boolean getFldPublicarColeta() {
		return fldPublicarColeta;
	}

	public void setFldPublicarColeta(Boolean fldPublicarColeta) {
		this.fldPublicarColeta = fldPublicarColeta;
	}

	public Boolean getFldPublicarGeografia() {
		return fldPublicarGeografia;
	}

	public void setFldPublicarGeografia(Boolean fldPublicarGeografia) {
		this.fldPublicarGeografia = fldPublicarGeografia;
	}

	public String getFldBuscaLocal() {
		return fldBuscaLocal;
	}

	public void setFldBuscaLocal(String fldBuscaLocal) {
		this.fldBuscaLocal = fldBuscaLocal;
	}

	public String getFldBuscaEspecime() {
		return fldBuscaEspecime;
	}

	public void setFldBuscaEspecime(String fldBuscaEspecime) {
		this.fldBuscaEspecime = fldBuscaEspecime;
	}

	public String getFldBuscaCatalogo() {
		return fldBuscaCatalogo;
	}

	public void setFldBuscaCatalogo(String fldBuscaCatalogo) {
		this.fldBuscaCatalogo = fldBuscaCatalogo;
	}

	@Override
	public Control<Especime> getNewControl(MessagesControl pMessagesControl) {
		return null;
	}

	@Override
	public Window getEditForm() {
		try {
			if (this.formEspecime == null) {
				this.formEspecime = (Window) Executions
						.createComponentsDirectly(getZulReader(), null,
								this.controlEspecime, null);

				// this.formEspecime.setParent(this.controlEspecime);
				// this.controlEspecime.appendChild(this.formEspecime);

				this.binderForm = new AnnotateDataBinder(this.formEspecime);
				this.binderForm.setLoadOnSave(false);
				this.binderForm.bindBean("controller", this);
				

/*				this.setFldSelectedItemTaxonomicoMedia(null);
				Combobox cmbSelected = ((Combobox)this.formEspecime.getFellow("cmbSelectedItemTaxonomicoMedia"));
				cmbSelected.setValue(null);
				cmbSelected.setModel(null);
				cmbSelected.setModel(new ListModelSet<>(this.getListItemTaxonomicoMedia()));*/
				
				this.binderForm.loadComponent(this.formEspecime);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this.formEspecime;
	}

	/**
	 * Obtém o nome do arquivo zul ao qual o componente está assossiado. Por
	 * padrão, o nome adotado para os arquivos zul dos componentes devem ter o
	 * mesmo nome da classe que o representa. Caso decida utilizar um nome
	 * diferente, este método deve ser sobreescrito na classe que herdar esta.
	 * 
	 * @return Reader
	 * @throws UnsupportedEncodingException
	 */
	protected Reader getZulReader() throws UnsupportedEncodingException {
		String name = this.getPathToFormZulFile() + "/form"
				+ this.getEntityClass().getSimpleName() + ".zul";
		InputStream resourceAsStream = this.controlEspecime.getDesktop()
				.getWebApp().getResourceAsStream(name);
		return new InputStreamReader(resourceAsStream, "UTF-8");
	}

	protected String getPathToFormZulFile() {
		return "/pages/" + this.getEntityClass().getSimpleName().toLowerCase();
	}

	@Override
	public void setEditForm(Window form) {
		this.formEspecime = form;
	}

	/* Para tratar a Guia Coleata */
	public BindingListModelList<Entity> getColetorListAvaliable() {
		Set<Coletor> fldColetores2 = this.getSelectedEntity() != null ? this
				.getSelectedEntity().getColetores() : null;

		if (fldColetores2 == null
				|| (fldColetores2 != null && fldColetores2.size() == 0)) {
			fldColetores2 = new HashSet<Coletor>();
			Coletor e = new Coletor();
			e.setId(0L);
			fldColetores2.add(e);
		}
		return this.getEntityModel(this.getEspecimeControl()
				.getListToEntityField(new HashSet<Entity>(fldColetores2)));
	}

	public ListModel<Coletor> getColetorList() {
		BindingListModelSet<Coletor> coletoresList = new BindingListModelSet<Coletor>(
				new HashSet<Coletor>(0), true);

		if (this.getSelectedEntity() != null
				&& this.getSelectedEntity().getId() != null) {
			coletoresList = new BindingListModelSet<Coletor>(
					this.selectedEntity.getColetores(), true);
		} else {
			coletoresList = new BindingListModelSet<Coletor>(
					this.getFldColetores(), true);
		}

		return coletoresList;
	}

	public ListModel<Colecao> getColecaoList() {
		BindingListModelSet<Colecao> resultList = new BindingListModelSet<Colecao>(
				new HashSet<Colecao>(0), true);		
		List<Colecao> colecaoList = this
				.getEspecimeControl().getColecaoList(
						this.getFldLaboratorio());

		resultList = new BindingListModelSet<Colecao>(
				new HashSet<Colecao>(colecaoList), true);

		return resultList;
	}

	public BindingListModelList<Entity> getLaboratorioList() {
		return this.getFKEntityModel("fldLaboratorio");
	}
	
	public void setCodigoCatalogoOnZul(){
		Colecao col = this.getFldColecao();
		setFldCodigoCatalogo(this.getEspecimeControl().getLastCollectionList(col));
		binderForm.loadAll();
	}

	@Override
	public boolean editEntity() {
		binder.saveAll();

		// this.doAction("ASSOCIATE");
		this.genericControl.associateEntityToAttributeView(this
				.getSelectedEntity());

		binder.loadComponent(this.getEditForm());
		// TODO descobrir uma forma de n�o fazer isso(ler tudo, deveria
		// funcionar s� com o comando acima,
		// quando o formul�rio � construido automaticamente.
		binder.loadAll();
		this.setDisabled("cmbLaboratorio", true);
		this.setDisabled("cmbColecao", true);
		// binder.saveAll();
		this.showEditForm();

		// if(this.getSelectedEntity()!=null&&
		// this.getSelectedEntity().getColecao()!=null){
		// cmbColecao.setValue(this.getSelectedEntity().getColecao().getNome());
		// }
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.ueg.builderSoft.view.zk.composer.ComposerController#doAction(java.
	 * lang.String)
	 */
	@Override
	public boolean doAction(String action) {
		boolean result = false;
		//binder.saveAll();
		if (genericControl.doAction(action, initializeEntity())) {
			verifyListing(action);
			// hideEditForm();
			result = true;
		}
		binder.loadAll();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ueg.builderSoft.view.zk.composer.ComposerController#newEntity()
	 */
	@Override
	public void newEntity() {
		// TODO Auto-generated method stub
		this.newSearch();
		super.newEntity();
	}
	
	public void newSearch(){
		this.setSelectedEntity(this.initializeEntity());
		this.genericControl.associateEntityToAttributeView(this.getSelectedEntity());
		binder.loadAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.ueg.builderSoft.view.zk.composer.ComposerController#showEditForm()
	 */
	@SuppressWarnings({ })
	@Override
	public void showEditForm() {
		super.showEditForm();

		Textbox tb = (Textbox) this.getEditForm().getFellow(
				"fldColecaoHidden");
		Combobox cb = (Combobox) this.getEditForm().getFellow(
				"cmbColecao");
		cb.setValue(tb.getValue());

		// estado
		tb = (Textbox) this.getEditForm().getFellow("fldEstadoHidden");
		cb = (Combobox) this.getEditForm().getFellow("cmbEstado");
		cb.setValue(tb.getValue());

		// municipio
		tb = (Textbox) this.getEditForm().getFellow("fldMunicipioHidden");
		cb = (Combobox) this.getEditForm().getFellow("cmbMunicipio");
		cb.setValue(tb.getValue());

		// Filo
		tb = (Textbox) this.getEditForm().getFellow("fldFiloHidden");
		cb = (Combobox) this.getEditForm().getFellow("cmbFilo");
		cb.setValue(tb.getValue());

		// Classe
		tb = (Textbox) this.getEditForm().getFellow("fldClasseHidden");
		cb = (Combobox) this.getEditForm().getFellow("cmbClasse");
		cb.setValue(tb.getValue());

		// SubClasse
		tb = (Textbox) this.getEditForm().getFellow("fldSubClasseHidden");
		cb = (Combobox) this.getEditForm().getFellow("cmbSubClasse");
		cb.setValue(tb.getValue());

		// Familia
		tb = (Textbox) this.getEditForm().getFellow("fldFamiliaHidden");
		cb = (Combobox) this.getEditForm().getFellow("cmbFamilia");
		cb.setValue(tb.getValue());

		// SubFamilia
		tb = (Textbox) this.getEditForm().getFellow("fldSubFamiliaHidden");
		cb = (Combobox) this.getEditForm().getFellow("cmbSubFamilia");
		cb.setValue(tb.getValue());

		// Ordem
		tb = (Textbox) this.getEditForm().getFellow("fldOrdemHidden");
		cb = (Combobox) this.getEditForm().getFellow("cmbOrdem");
		cb.setValue(tb.getValue());

		// SubOrdem
		tb = (Textbox) this.getEditForm().getFellow("fldSubOrdemHidden");
		cb = (Combobox) this.getEditForm().getFellow("cmbSubOrdem");
		cb.setValue(tb.getValue());

		// Genero
		tb = (Textbox) this.getEditForm().getFellow("fldGeneroHidden");
		cb = (Combobox) this.getEditForm().getFellow("cmbGenero");
		cb.setValue(tb.getValue());

		// EpitetoEspecifico
		tb = (Textbox) this.getEditForm().getFellow(
				"fldEpitetoEspecificoHidden");
		cb = (Combobox) this.getEditForm().getFellow("cmbEpitetoEspecifico");
		cb.setValue(tb.getValue());

		// this.binder.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.ueg.builderSoft.view.zk.composer.ComposerController#hideEditForm()
	 */
	@Override
	public void hideEditForm() {
		getEditForm().setVisible(false);
		getEditForm().detach();
		controlEspecime.removeChild(getEditForm());
		setEditForm(null);
	}

	public void addColetor() {
		Combobox cb = (Combobox) this.getEditForm().getFellow(
				"cmbColetorListAvaliable");
		Listbox lb = (Listbox) this.getEditForm().getFellow("lbColetores");
		addItemInListBoxFromCombobox(cb, lb);
	}

	public void addAutor() {
		Combobox cb = (Combobox) this.getEditForm().getFellow(
				"cmbAutorListAvaliable");
		Listbox lb = (Listbox) this.getEditForm().getFellow("lbAutores");
		addItemInListBoxFromCombobox(cb, lb);
	}

	public void removeAutor() {
		Combobox cb = (Combobox) this.getEditForm().getFellow(
				"cmbAutorListAvaliable");
		Listbox lb = (Listbox) this.getEditForm().getFellow("lbAutores");

		removeSelectedItemFromListbox(cb, lb);
	}

	public void removeImagem() {
		Listbox lb = (Listbox) this.getEditForm().getFellow("lbEspecimeImagem");

		BindingListModelSet<Object> especieImagens = (BindingListModelSet<Object>) lb
				.getModel();

		Entity entity = (Entity) lb.getSelectedItem().getValue();
		especieImagens.remove(entity);

	}

	/**
	 * Recebe um combobox e um listbox, pega o item selecionado do combobox e
	 * adicionar no model do listbox.
	 * 
	 * @param cb
	 * @param lb
	 */
	private void addItemInListBoxFromCombobox(Combobox cb, Listbox lb) {
		BindingListModelSet<Object> blm = (BindingListModelSet<Object>) lb
				.getModel();
		if (cb.getSelectedItem() == null) {
			alert("Selecione um item primeiro!");
		}
		blm.add(cb.getSelectedItem().getValue());

		removeSelectedItem(cb);
	}
	
	public void removeColetor() {
		Combobox cb = (Combobox) this.getEditForm().getFellow(
				"cmbColetorListAvaliable");
		Listbox lb = (Listbox) this.getEditForm().getFellow("lbColetores");

		removeSelectedItemFromListbox(cb, lb);
	}

	/**
	 * remove o item selecionado do listbox e adiciona o item removido ao
	 * combobox
	 * 
	 * @param cb
	 * @param lb
	 */
	private void removeSelectedItemFromListbox(Combobox cb, Listbox lb) {
		BindingListModelSet<Object> modelDeterminadores = (BindingListModelSet<Object>) lb
				.getModel();
		BindingListModelList<Object> modelDeterminadoresAvaliable = (BindingListModelList<Object>) cb
				.getModel();

		for (Object e : modelDeterminadores) {
			Entity entity = (Entity) lb.getSelectedItem().getValue();
			if (((Entity) e).getId().equals(entity.getId())) {
				modelDeterminadores.remove(entity);
				modelDeterminadoresAvaliable.add(entity);
				break;
			}
		}
	}

	/**
	 * @param cb
	 */
	private void removeSelectedItem(Combobox cb) {
		BindingListModelList<Object> model = (BindingListModelList<Object>) cb
				.getModel();
		for (Object e : model) {
			Entity e2 = (Entity) e;
			Entity determinador = (Entity) cb.getSelectedItem().getValue();
			if (e2.getId().equals(determinador.getId())) {
				model.remove(determinador);
				break;
			}
		}
	}

	public void addDeterminador() {
		Combobox cb = (Combobox) this.getEditForm().getFellow(
				"cmbDeterminadorAvaliable");
		Listbox lb = (Listbox) this.getEditForm().getFellow("lbDeterminadores");
		Date dta = ((Datebox) this.getEditForm().getFellow("dateBoxData"))
				.getValue();
		BindingListModelSet<Object> blm = (BindingListModelSet<Object>) lb
				.getModel();
		EspecimeDeterminador ed = new EspecimeDeterminador();
		ed.setControleInsercaoPadroa(false);
		ed.setId(Long.valueOf(blm.size() + 1));
		ed.setData(dta);
		ed.setEspecime(this.getSelectedEntity());
		ed.setDeterminador((Determinador) cb.getSelectedItem().getValue());
		blm.add(ed);
		((Datebox) this.getEditForm().getFellow("dateBoxData")).setValue(null);
		removeSelectedItem(cb);

	}

	public void removeDeterminador() {
		Combobox cb = (Combobox) this.getEditForm().getFellow(
				"cmbDeterminadorAvaliable");
		Listbox lb = (Listbox) this.getEditForm().getFellow("lbDeterminadores");

		BindingListModelSet<Object> modelDeterminadores = (BindingListModelSet<Object>) lb
				.getModel();
		BindingListModelList<Object> modelDeterminadoresAvaliable = (BindingListModelList<Object>) cb
				.getModel();

		for (Object e : modelDeterminadores) {
			EspecimeDeterminador especimeDeterminador = (EspecimeDeterminador) lb
					.getSelectedItem().getValue();
			if (((Entity) e).getId().equals(especimeDeterminador.getId())) {
				modelDeterminadores.remove(especimeDeterminador);
				modelDeterminadoresAvaliable.add(especimeDeterminador
						.getDeterminador());
				break;
			}
		}
	}

	/* Fim tratar guia coleta */

	/* inicio geografia */
	public List<ItemGeografico> getPaisList() {
		return this.getEspecimeControl().getPaisList();
	}

	public List<ItemGeografico> getEstadoList() {
		return this.getEspecimeControl().getFilhoList(this.getFldPais());
	}

	public List<ItemGeografico> getMunicipioList() {
		return this.getEspecimeControl().getFilhoList(this.getFldEstado());
	}

	/* fim geografia */

	/* inicio guia taxonomia */

	public List<ItemTaxonomico> getReinoList() {
		return this.getEspecimeControl().getReinoList();
	}

	public List<ItemTaxonomico> getFiloList() {
		return this.getEspecimeControl().getFilhoList(this.getFldReino(), NivelTaxonomico.ID_NIVEL_FILO);
	}

	public List<ItemTaxonomico> getClasseList() {
		return this.getEspecimeControl().getFilhoList(this.getFldFilo(), NivelTaxonomico.ID_NIVEL_CLASSE);
	}

	public List<ItemTaxonomico> getSubClasseList() {
		return this.getEspecimeControl().getFilhoList(this.getFldClasse(), NivelTaxonomico.ID_NIVEL_SUB_CLASSE);
	}

	public List<ItemTaxonomico> getOrdemList() {
		ArrayList<ItemTaxonomico> resultList = new ArrayList<ItemTaxonomico>(0);

		List<ItemTaxonomico> filhoList2 = this.getEspecimeControl()
				.getFilhoList(this.getFldSubClasse(), NivelTaxonomico.ID_NIVEL_ORDEM);
		if (filhoList2 != null && filhoList2.size()>0) {
			resultList.addAll(filhoList2);
		} else {
			List<ItemTaxonomico> filhoList = this.getEspecimeControl()
					.getFilhoList(this.getFldClasse(), NivelTaxonomico.ID_NIVEL_ORDEM);
			if (filhoList != null)
				resultList.addAll(filhoList);
		}

		return resultList;
	}

	public List<ItemTaxonomico> getSubOrdemList() {
		return this.getEspecimeControl().getFilhoList(this.getFldOrdem(), NivelTaxonomico.ID_NIVEL_SUB_ORDEM);
	}

	public List<ItemTaxonomico> getFamiliaList() {
		ArrayList<ItemTaxonomico> resultList = new ArrayList<ItemTaxonomico>(0);

		List<ItemTaxonomico> filhoList2 = this.getEspecimeControl()
				.getFilhoList(this.getFldSubOrdem(), NivelTaxonomico.ID_NIVEL_FAMILIA);
		if (filhoList2 != null && filhoList2.size()>0) {
			resultList.addAll(filhoList2);
		} else {
			List<ItemTaxonomico> filhoList = this.getEspecimeControl()
					.getFilhoList(this.getFldOrdem(), NivelTaxonomico.ID_NIVEL_FAMILIA);
			if (filhoList != null)
				resultList.addAll(filhoList);
		}

		return resultList;
	}

	public List<ItemTaxonomico> getSubFamiliaList() {
		return this.getEspecimeControl().getFilhoList(this.getFldFamilia(), NivelTaxonomico.ID_NIVEL_SUB_FAMILIA);
	}
	
	public List<ItemTaxonomico> getTriboList() {
		ArrayList<ItemTaxonomico> resultList = new ArrayList<ItemTaxonomico>(0);

		List<ItemTaxonomico> filhoList2 = this.getEspecimeControl()
				.getFilhoList(this.getFldSubFamilia(), NivelTaxonomico.ID_NIVEL_TRIBO);
		if (filhoList2 != null && filhoList2.size()>0) {
			resultList.addAll(filhoList2);
		} else {
			List<ItemTaxonomico> filhoList = this.getEspecimeControl()
					.getFilhoList(this.getFldFamilia(), NivelTaxonomico.ID_NIVEL_TRIBO);
			if (filhoList != null)
				resultList.addAll(filhoList);
		}

		return resultList;
	}
	
	public List<ItemTaxonomico> getSubTriboList() {
		return this.getEspecimeControl().getFilhoList(this.getFldTribo(), NivelTaxonomico.ID_NIVEL_SUB_TRIBO);
	}

	public List<ItemTaxonomico> getGeneroList() {
		ArrayList<ItemTaxonomico> resultList = new ArrayList<ItemTaxonomico>(0);

		List<ItemTaxonomico> filhoList = this.getEspecimeControl()
				.getFilhoList(this.getFldSubTribo(), NivelTaxonomico.ID_NIVEL_GENERO);
		
		if (filhoList != null && filhoList.size()>0) {
			resultList.addAll(filhoList);
		} 
		
		if(filhoList != null && filhoList.size()==0 || filhoList==null) {
			filhoList = this.getEspecimeControl()
					.getFilhoList(this.getFldTribo(), NivelTaxonomico.ID_NIVEL_GENERO);
			if (filhoList != null)
				resultList.addAll(filhoList);
		}
		
		if(filhoList != null && filhoList.size()==0|| filhoList==null) {
			filhoList = this.getEspecimeControl()
					.getFilhoList(this.getFldSubFamilia(), NivelTaxonomico.ID_NIVEL_GENERO);
			if (filhoList != null)
				resultList.addAll(filhoList);
		}
		
		if(filhoList != null && filhoList.size()==0|| filhoList==null) {
			filhoList = this.getEspecimeControl()
					.getFilhoList(this.getFldFamilia(), NivelTaxonomico.ID_NIVEL_GENERO);
			if (filhoList != null)
				resultList.addAll(filhoList);
		}

		return resultList;
	}
	
	public List<ItemTaxonomico> getSubGeneroList() {
		return this.getEspecimeControl().getFilhoList(this.getFldGenero(), NivelTaxonomico.ID_NIVEL_SUB_GENERO);
	}

	public List<ItemTaxonomico> getEpitetoEspecificoList() {
		ArrayList<ItemTaxonomico> resultList = new ArrayList<ItemTaxonomico>(0);

		List<ItemTaxonomico> filhoList = this.getEspecimeControl()
				.getFilhoList(this.getFldSubGenero(), NivelTaxonomico.ID_NIVEL_EPITETO_ESPECIFICO);
		
		if (filhoList != null && filhoList.size()>0) {
			resultList.addAll(filhoList);
		} 
		
		if(filhoList != null && filhoList.size()==0 || filhoList==null) {
			filhoList = this.getEspecimeControl()
					.getFilhoList(this.getFldGenero(), NivelTaxonomico.ID_NIVEL_EPITETO_ESPECIFICO);
			if (filhoList != null)
				resultList.addAll(filhoList);
		}
		return filhoList;
	}

	public ListModel<EspecimeDeterminador> getEspecimeDeterminadorList() {
		BindingListModelSet<EspecimeDeterminador> coletoresList = new BindingListModelSet<EspecimeDeterminador>(
				new HashSet<EspecimeDeterminador>(0), true);

		if (this.getSelectedEntity() != null
				&& this.getSelectedEntity().getId() != null) {
			coletoresList = new BindingListModelSet<EspecimeDeterminador>(
					this.selectedEntity.getEspecimeDeterminadores(), true);
		} else {
			coletoresList = new BindingListModelSet<EspecimeDeterminador>(
					this.getFldEspecimeDeterminadores(), true);
		}

		return coletoresList;
	}

	public BindingListModelList<Entity> getEspecimeDeterminadorListAvaliable() {
		Set<EspecimeDeterminador> fldDeterminadores2 = this.getSelectedEntity() != null ? this
				.getSelectedEntity().getEspecimeDeterminadores() : null;

		if (fldDeterminadores2 == null
				|| (fldDeterminadores2 != null && fldDeterminadores2.size() == 0)) {
			fldDeterminadores2 = new HashSet<EspecimeDeterminador>();
			EspecimeDeterminador e = new EspecimeDeterminador();
			e.setId(0L);
			fldDeterminadores2.add(e);
		}
		return this.getEntityModel(this.getEspecimeControl()
				.getListToEntityField(new HashSet<Entity>(fldDeterminadores2),
						Determinador.class));
	}

	public String getFldImprecisao() {
		return fldImprecisao;
	}

	public void setFldImprecisao(String fldImprecisao) {
		this.fldImprecisao = fldImprecisao;
	}

	public ListModel<Autor> getAutorList() {
		BindingListModelSet<Autor> coletoresList = new BindingListModelSet<Autor>(
				new HashSet<Autor>(0), true);

		if (this.getSelectedEntity() != null
				&& this.getSelectedEntity().getId() != null) {
			coletoresList = new BindingListModelSet<Autor>(
					this.selectedEntity.getAutores(), true);
		} else {
			coletoresList = new BindingListModelSet<Autor>(
					this.getFldAutores(), true);
		}

		return coletoresList;
	}

	public BindingListModelList<Entity> getAutorListAvaliable() {
		Set<Autor> autor2 = this.getSelectedEntity() != null ? this
				.getSelectedEntity().getAutores() : new HashSet<Autor>(0);

		return this
				.getEntityModel(this.getEspecimeControl().getListToEntityField(
						new HashSet<Entity>(autor2), Autor.class));
	}

	/* fim guia taxonomia */

	public BindingListModelList<Entity> getEstacaoList() {
		return this.getEntityModel(this.getEspecimeControl().getEstacaoList());
	}

	@Override
	public boolean saveEntity() {		
		boolean retorno =  super.saveEntity();
		//binderForm.saveAll();
		return retorno;
	}

	public void setButtonsNextTab() {
		Tabbox tabBox = getTabboxEspecime();
		Tab tabSelected = tabBox.getSelectedTab();
		if (tabSelected.getId().equals("cadTaxonomia")) {
			formEspecime.getFellow("btnSalvar").setVisible(true);
			formEspecime.getFellow("btnNextTab").setVisible(false);
		} else {
			formEspecime.getFellow("btnSalvar").setVisible(false);
			formEspecime.getFellow("btnNextTab").setVisible(true);
		}

		if (tabSelected.getId().equals("cadTaxonomia"))
			((Button) getFellow(formEspecime, "btnNextTab")).setDisabled(true);
		else
			((Button) getFellow(formEspecime, "btnNextTab")).setDisabled(false);
	}

	public Tabbox getTabboxEspecime() {
		return (Tabbox) this.getEditForm().getFellow("tb");
	}

	public void nextTab() {
		Tabbox tabBox = getTabboxEspecime();
		Tab tabSelected = tabBox.getSelectedTab();
		tabBox.setSelectedTab((Tab) tabSelected.getNextSibling());
		setButtonsNextTab();
		if (tabSelected.getNextSibling() != null)
			tabBox.setSelectedTab((Tab) tabSelected.getNextSibling());
	}

	public List<ItemTaxonomico> getListItemTaxonomicoMedia() {
		ArrayList<ItemTaxonomico> resultList = new ArrayList<ItemTaxonomico>(0);
		if (this.getFldReino() != null) {
			resultList.add(this.getFldReino());
		} else {
			return resultList;
		}

		if (this.getFldFilo() != null) {
			resultList.add(this.getFldFilo());
		} else {
			return resultList;
		}

		if (this.getFldClasse() != null) {
			resultList.add(this.getFldClasse());
		} else {
			return resultList;
		}

		if (this.getFldSubClasse() != null) {
			resultList.add(this.getFldSubClasse());
		}

		if (this.getFldOrdem() != null) {
			resultList.add(this.getFldOrdem());
		} else {
			return resultList;
		}

		if (this.getFldSubOrdem() != null) {
			resultList.add(this.getFldSubOrdem());
		}

		if (this.getFldFamilia() != null) {
			resultList.add(this.getFldFamilia());
		} else {
			return resultList;
		}

		if (this.getFldSubFamilia() != null) {
			resultList.add(this.getFldSubFamilia());
		}

		if (this.getFldGenero() != null) {
			resultList.add(this.getFldGenero());
		} else {
			return resultList;
		}

		if (this.getFldEpitetoEspecifico() != null) {
			resultList.add(this.getFldEpitetoEspecifico());
		} else {
			return resultList;
		}
		return resultList;
	}	
}
