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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.databind.BindingListModelSet;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.builderSoft.view.zk.composer.ComposerController;
import br.ueg.portalLab.control.EspecimeControl;
import br.ueg.portalLab.model.Autor;
import br.ueg.portalLab.model.Coletor;
import br.ueg.portalLab.model.Datum;
import br.ueg.portalLab.model.Especime;
import br.ueg.portalLab.model.EspecimeDeterminador;
import br.ueg.portalLab.model.EstagioDesenvolvimento;
import br.ueg.portalLab.model.Fenologia;
import br.ueg.portalLab.model.GrupoEnderecoFisico;
import br.ueg.portalLab.model.ItemGeografico;
import br.ueg.portalLab.model.ItemTaxonomico;
import br.ueg.portalLab.model.Laboratorio;
import br.ueg.portalLab.model.MassaDAgua;
import br.ueg.portalLab.model.MetodoColeta;
import br.ueg.portalLab.model.Sexo;
import br.ueg.portalLab.model.TipoDeMontagem;

@Component
@Scope("desktop")
public class EspecimeComposer extends ComposerController<Especime> {

	@Override
	public void doAfterCompose(org.zkoss.zk.ui.Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		this.setEditForm(null);
	}

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.zk.composer.ComposerController#cancelEditEntity()
	 */
	@Override
	public void cancelEditEntity() {
		// TODO Auto-generated method stub
		super.cancelEditEntity();
	}

	@AttributeView(key = "laboratorio", isEntityValue = true, fieldType = Laboratorio.class, isVisible = true, caption = "especime_laboratorioColumn")
	private Laboratorio fldLaboratorio;


	@AttributeView(key = "grupoEnderecoFisico", isEntityValue = true, fieldType = GrupoEnderecoFisico.class, isVisible = true, caption = "especime_grupoEnderecoFisicoColumn")
	private GrupoEnderecoFisico fldGrupoEnderecoFisico;

	@AttributeView(key = "codigoCatalogo", isEntityValue = true, fieldType = String.class, isVisible = true, caption = "especime_codigoCatalogoColumn")
	private String fldCodigoCatalogo;

	@AttributeView(key = "codigoColeta", isEntityValue = true, fieldType = String.class, isVisible = true, caption = "especime_codigoColetaColumn")
	private String fldCodigoColeta;

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
	private Integer fldNumeroIndividuo = new Integer(1);

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
	
	
	//Atributos guia taxonomia
	
	@AttributeView(key = "reino", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true, 		caption = "especime_reinoColumn")
	private ItemTaxonomico fldReino;
	
	@AttributeView(key = "filo", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true,	 		caption = "especime_filoColumn")
	private ItemTaxonomico fldFilo;
	
	@AttributeView(key = "classe", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true, 		caption = "especime_classeColumn")
	private ItemTaxonomico fldClasse;
	
	@AttributeView(key = "subClasse", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true,		caption = "especime_subClasseColumn")
	private ItemTaxonomico fldSubClasse;
	
	@AttributeView(key = "familia", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true, 		caption = "especime_familiaColumn")
	private ItemTaxonomico fldFamilia;
	
	@AttributeView(key = "subFamilia", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true,	caption = "especime_subFamiliaColumn")
	private ItemTaxonomico fldSubFamilia;
	
	@AttributeView(key = "ordem", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true,			caption = "especime_ordemColumn")
	private ItemTaxonomico fldOrdem;
	
	@AttributeView(key = "subOrdem", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true,		 caption = "especime_subOrdemColumn")
	private ItemTaxonomico fldSubOrdem;
	
	@AttributeView(key = "genero", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true,		 caption = "especime_generoColumn")
	private ItemTaxonomico fldGenero;
	
	@AttributeView(key = "epitetoEspecifico", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true,		 caption = "especime_epitetoEspecificoColumn")
	private ItemTaxonomico fldEpitetoEspecifico;
	
	@AttributeView(key = "especimeDeterminadores", isEntityValue = true, fieldType = Set.class, isVisible = true,		 caption = "especime_especimeDeterminadoresColumn")
	private Set<EspecimeDeterminador> fldEspecimeDeterminadores = new HashSet<EspecimeDeterminador>(0);

	@AttributeView(key = "imprecisao", isEntityValue = true, fieldType = String.class, isVisible = true,		 caption = "especime_imprecisaoColumn")
	private String fldImprecisao;
	
	@AttributeView(key = "autores", isEntityValue = true, fieldType = Set.class, isVisible = true,		 caption = "especime_autoresColumn")
	private Set<Autor> fldAutores = new HashSet<Autor>(0);
	
	@AttributeView(key = "observacaoTaxonomia", isEntityValue = true, fieldType = String.class, isVisible = true,		 caption = "especime_observacaoTaxonomiaColumn")
	private String fldObservacaoTaxonomia;
	
	

	@Autowired
	protected EspecimeControl especimeControl;

	@Wire
	protected Window formEspecime;
	
	@Wire
	protected Window controlEspecime;
	
	@Wire
	protected Combobox cmbGrupoEnderecoFisico;
	
	@Wire
	protected Listbox divListBoxColetores;
	
	protected AnnotateDataBinder binderForm;
	/**
	 * 
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
		super.setSelectedEntity(selectedEntity);
		this.getEspecimeControl().setSelectedEspecime(selectedEntity);
//		if(selectedEntity!=null && cmbGrupoEnderecoFisico!=null){
//			binder.loadAttribute(cmbGrupoEnderecoFisico, "model");
//		}
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

	public GrupoEnderecoFisico getFldGrupoEnderecoFisico() {
		return fldGrupoEnderecoFisico;
	}

	public void setFldGrupoEnderecoFisico(
			GrupoEnderecoFisico fldGrupoEnderecoFisico) {
		this.fldGrupoEnderecoFisico = fldGrupoEnderecoFisico;
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
			if(this.formEspecime==null){
				this.formEspecime = (Window) Executions.createComponentsDirectly(getZulReader(), null, this.controlEspecime, null);
				
				//this.formEspecime.setParent(this.controlEspecime);
				//this.controlEspecime.appendChild(this.formEspecime);
				
				this.binderForm = new AnnotateDataBinder(this.formEspecime);
				this.binderForm.setLoadOnSave(false);
				this.binderForm.bindBean("controller", this);
				this.binderForm.loadComponent(this.formEspecime);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this.formEspecime;
	}
	/**
	 * Obt√©m o nome do arquivo zul ao qual o componente est√° assossiado. Por padr√£o, o nome adotado para os arquivos zul
	 * dos componentes devem ter o mesmo nome da classe que o representa. Caso decida utilizar um nome diferente, este
	 * m√©todo deve ser sobreescrito na classe que herdar esta.
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	protected Reader getZulReader() throws UnsupportedEncodingException {
		String name = this.getPathToFormZulFile()+"/form"+this.getEntityClass().getSimpleName() +".zul";
		InputStream resourceAsStream = this.controlEspecime.getDesktop().getWebApp().getResourceAsStream(name);
		return new InputStreamReader(resourceAsStream, "UTF-8");
	}
	protected String getPathToFormZulFile(){
		return "/pages/"+this.getEntityClass().getSimpleName().toLowerCase();
	}

	@Override
	public void setEditForm(Window form) {
		this.formEspecime = form;
	}


	/* Para tratar a Guia Coleata */
	public BindingListModelList<Entity> getColetorListAvaliable() {
		Set<Coletor> fldColetores2 = this.getSelectedEntity()!=null?this.getSelectedEntity().getColetores():null;
		
		if(fldColetores2==null ||(fldColetores2!=null && fldColetores2.size()==0)){
			fldColetores2 = new HashSet<Coletor>();
			Coletor e = new Coletor();
			e.setId(0L);
			fldColetores2.add(e);
		}
		return this.getEntityModel(this.getEspecimeControl()
				.getListToEntityField(
						new HashSet<Entity>(fldColetores2)));
	}

	/**
	 * Lista todas as funcionalidades cadastrados para a entidade do composer.
	 * 
	 * @return List<UsuarioPermissao> Lista de funcionalidades.
	 */
	public ListModel<Coletor> getColetorList() {
		BindingListModelSet<Coletor> coletoresList = new BindingListModelSet<Coletor>(
				new HashSet<Coletor>(0), true);

		if (this.getSelectedEntity() != null
				&& this.getSelectedEntity().getId() != null) {
			coletoresList = new BindingListModelSet<Coletor>(
					this.selectedEntity.getColetores(), true);
		}

		return coletoresList;
	}

	public ListModel<GrupoEnderecoFisico> getGrupoEnderecoFisicoList() {
		BindingListModelSet<GrupoEnderecoFisico> resultList = new BindingListModelSet<GrupoEnderecoFisico>(
				new HashSet<GrupoEnderecoFisico>(0), true);
		;
			List<GrupoEnderecoFisico> grupoEnderecoFisicoList = this
					.getEspecimeControl().getGrupoEnderecoFisicoList(
							this.getFldLaboratorio());
			
			resultList = new BindingListModelSet<GrupoEnderecoFisico>(
					new HashSet<GrupoEnderecoFisico>(grupoEnderecoFisicoList),
					true);
		

		return resultList;
	}
	public BindingListModelList<Entity> getLaboratorioList(){
		return this.getFKEntityModel("fldLaboratorio");
	}


	@Override
	public boolean editEntity() {
		binder.saveAll();
		
		//this.doAction("ASSOCIATE");
		this.genericControl.associateEntityToAttributeView(this.getSelectedEntity());
		
		binder.loadComponent(this.getEditForm());
		//TODO descobrir uma forma de n„o fazer isso(ler tudo, deveria funcionar sÛ com o comando acima, 
		//quando o formul·rio È construido automaticamente.
		binder.loadAll();
		//binder.saveAll();
		this.showEditForm();
		




//		if(this.getSelectedEntity()!=null&& this.getSelectedEntity().getGrupoEnderecoFisico()!=null){
//			cmbGrupoEnderecoFisico.setValue(this.getSelectedEntity().getGrupoEnderecoFisico().getNome());
//		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.zk.composer.ComposerController#doAction(java.lang.String)
	 */
	@Override
	public boolean doAction(String action) {
		boolean result=false;
		binder.saveAll();	
		if (genericControl.doAction(action, initializeEntity())) {
			verifyListing(action);
			//hideEditForm();
			result = true;				
		}
		binder.loadAll();
		return result;
	}

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.zk.composer.ComposerController#newEntity()
	 */
	@Override
	public void newEntity() {
		// TODO Auto-generated method stub
		super.newEntity();
	}

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.zk.composer.ComposerController#showEditForm()
	 */
	@Override
	public void showEditForm() {
		// TODO Auto-generated method stub
		super.showEditForm();
		Textbox tb = (Textbox)this.getEditForm().getFellow("fldGrupoEnderecoFisicoHidden");
		Combobox cb = (Combobox) this.getEditForm().getFellow("cmbGrupoEnderecoFisico");		
		cb.setValue(tb.getValue());
		
		//estado
		tb = (Textbox)this.getEditForm().getFellow("fldEstadoHidden");
		cb = (Combobox) this.getEditForm().getFellow("cmbEstado");		
		cb.setValue(tb.getValue());
		
		//municipio
		tb = (Textbox)this.getEditForm().getFellow("fldMunicipioHidden");
		cb = (Combobox) this.getEditForm().getFellow("cmbMunicipio");		
		cb.setValue(tb.getValue());
		
		//Filo
		tb = (Textbox)this.getEditForm().getFellow("fldFiloHidden");
		cb = (Combobox) this.getEditForm().getFellow("cmbFilo");		
		cb.setValue(tb.getValue());
		
		//Classe
		tb = (Textbox)this.getEditForm().getFellow("fldClasseHidden");
		cb = (Combobox) this.getEditForm().getFellow("cmbClasse");		
		cb.setValue(tb.getValue());

		//SubClasse
		tb = (Textbox)this.getEditForm().getFellow("fldSubClasseHidden");
		cb = (Combobox) this.getEditForm().getFellow("cmbSubClasse");		
		cb.setValue(tb.getValue());		

		//Familia
		tb = (Textbox)this.getEditForm().getFellow("fldFamiliaHidden");
		cb = (Combobox) this.getEditForm().getFellow("cmbFamilia");		
		cb.setValue(tb.getValue());		

		//SubFamilia
		tb = (Textbox)this.getEditForm().getFellow("fldSubFamiliaHidden");
		cb = (Combobox) this.getEditForm().getFellow("cmbSubFamilia");		
		cb.setValue(tb.getValue());		

		//Ordem
		tb = (Textbox)this.getEditForm().getFellow("fldOrdemHidden");
		cb = (Combobox) this.getEditForm().getFellow("cmbOrdem");		
		cb.setValue(tb.getValue());		
		
		//SubOrdem
		tb = (Textbox)this.getEditForm().getFellow("fldSubOrdemHidden");
		cb = (Combobox) this.getEditForm().getFellow("cmbSubOrdem");		
		cb.setValue(tb.getValue());		
		
		//Genero
		tb = (Textbox)this.getEditForm().getFellow("fldGeneroHidden");
		cb = (Combobox) this.getEditForm().getFellow("cmbGenero");		
		cb.setValue(tb.getValue());		

		//EpitetoEspecifico
		tb = (Textbox)this.getEditForm().getFellow("fldEpitetoEspecificoHidden");
		cb = (Combobox) this.getEditForm().getFellow("cmbEpitetoEspecifico");		
		cb.setValue(tb.getValue());
		
		//this.binder.
	}

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.zk.composer.ComposerController#hideEditForm()
	 */
	@Override
	public void hideEditForm() {
		getEditForm().setVisible(false);
		getEditForm().detach();
		controlEspecime.removeChild(getEditForm());
		setEditForm(null);
	}

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.zk.composer.ComposerController#saveEntity()
	 */
	@Override
	public boolean saveEntity() {
		// TODO Auto-generated method stub
		return super.saveEntity();
	}

	public void addColetor(){
		Combobox cb = (Combobox)this.getEditForm().getFellow("cmbColetorListAvaliable");
		Listbox lb = (Listbox)this.getEditForm().getFellow("lbColetores");
		BindingListModelSet<Object> blm = (BindingListModelSet<Object>) lb.getModel();
		blm.add(cb.getSelectedItem().getValue());

		
	}

	/* Fim tratar guia coleta */
	
	/* inicio geografia */
	public List<ItemGeografico> getPaisList(){		
		return this.getEspecimeControl().getPaisList();
	}
	
	public List<ItemGeografico> getEstadoList(){
		return this.getEspecimeControl().getFilhoList(this.getFldPais());
	}
	public List<ItemGeografico> getMunicipioList(){
		return this.getEspecimeControl().getFilhoList(this.getFldEstado());
	}
	
	
	/* fim geografia */
	
	/* inicio guia taxonomia */
	
	public List<ItemTaxonomico> getReinoList(){		
		return this.getEspecimeControl().getReinoList();
	}
	
	public List<ItemTaxonomico> getFiloList(){
		return this.getEspecimeControl().getFilhoList(this.getFldReino());
	}
	public List<ItemTaxonomico> getClasseList(){
		return this.getEspecimeControl().getFilhoList(this.getFldFilo());
	}
	public List<ItemTaxonomico> getSubClasseList(){
		return this.getEspecimeControl().getFilhoList(this.getFldClasse());
	}
	public List<ItemTaxonomico> getOrdemList(){
		ArrayList<ItemTaxonomico> resultList = new ArrayList<ItemTaxonomico>(0);
		List<ItemTaxonomico> filhoList = this.getEspecimeControl().getFilhoList(this.getFldClasse());
		if(filhoList!=null)
			resultList.addAll(filhoList);
		
		List<ItemTaxonomico> filhoList2 = this.getEspecimeControl().getFilhoList(this.getFldSubClasse());
		if(filhoList2!=null)
			resultList.addAll(filhoList2);
		return resultList;
	}
	public List<ItemTaxonomico> getSubOrdemList(){
		return this.getEspecimeControl().getFilhoList(this.getFldOrdem());
	}
	public List<ItemTaxonomico> getFamiliaList(){
		ArrayList<ItemTaxonomico> resultList = new ArrayList<ItemTaxonomico>(0);
		List<ItemTaxonomico> filhoList = this.getEspecimeControl().getFilhoList(this.getFldOrdem());
		if(filhoList!=null)
			resultList.addAll(filhoList);
		
		List<ItemTaxonomico> filhoList2 = this.getEspecimeControl().getFilhoList(this.getFldSubOrdem());
		if(filhoList2!=null)
			resultList.addAll(filhoList2);
		return resultList;
	}
	public List<ItemTaxonomico> getSubFamiliaList(){
		return this.getEspecimeControl().getFilhoList(this.getFldFamilia());
	}	
	public List<ItemTaxonomico> getGeneroList(){
		ArrayList<ItemTaxonomico> resultList = new ArrayList<ItemTaxonomico>(0);
		List<ItemTaxonomico> filhoList = this.getEspecimeControl().getFilhoList(this.getFldFamilia());
		if(filhoList!=null)
			resultList.addAll(filhoList);
		
		List<ItemTaxonomico> filhoList2 = this.getEspecimeControl().getFilhoList(this.getFldSubFamilia());
		if(filhoList2!=null)
			resultList.addAll(filhoList2);
		return resultList;
	}
	public List<ItemTaxonomico> getEpitetoEspecificoList(){
		return this.getEspecimeControl().getFilhoList(this.getFldGenero());
	}
	
	public ListModel<EspecimeDeterminador> getEspecimeDeterminadorList() {
		BindingListModelSet<EspecimeDeterminador> coletoresList = new BindingListModelSet<EspecimeDeterminador>(
				new HashSet<EspecimeDeterminador>(0), true);

		if (this.getSelectedEntity() != null
				&& this.getSelectedEntity().getId() != null) {
			coletoresList = new BindingListModelSet<EspecimeDeterminador>(
					this.selectedEntity.getEspecimeDeterminadores(), true);
		}

		return coletoresList;
	}
	
	public BindingListModelList<Entity> getEspecimeDeterminadorListAvaliable() {
		Set<EspecimeDeterminador> fldDeterminadores2 = this.getSelectedEntity()!=null?this.getSelectedEntity().getEspecimeDeterminadores():null;
		
		if(fldDeterminadores2==null ||(fldDeterminadores2!=null && fldDeterminadores2.size()==0)){
			fldDeterminadores2 = new HashSet<EspecimeDeterminador>();
			EspecimeDeterminador e = new EspecimeDeterminador();
			e.setId(0L);
			fldDeterminadores2.add(e);
		}
		return this.getEntityModel(this.getEspecimeControl()
										.getListToEntityField(new HashSet<Entity>(fldDeterminadores2))
				);
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
		}

		return coletoresList;
	}
	
	public BindingListModelList<Entity> getAutorListAvaliable() {
		Set<Autor> fldAutores2 = this.getSelectedEntity()!=null?this.getSelectedEntity().getAutores():null;
		
		if(fldAutores2==null ||(fldAutores2!=null && fldAutores2.size()==0)){
			fldAutores2 = new HashSet<Autor>();
			Autor e = new Autor();
			e.setId(0L);
			fldAutores2.add(e);
		}
		return this.getEntityModel(this.getEspecimeControl()
										.getListToEntityField(new HashSet<Entity>(fldAutores2))
				);
	}
	
	/* fim guia taxonomia */
	
}
