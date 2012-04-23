package br.ueg.portalLab.view.composer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.record.TextObjectRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.zkoss.bind.AnnotateBinder;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.databind.BindingListModelSet;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.builderSoft.view.zk.composer.ComposerController;
import br.ueg.portalLab.control.EspecimeControl;
import br.ueg.portalLab.model.Coletor;
import br.ueg.portalLab.model.Especime;
import br.ueg.portalLab.model.EstagioDesenvolvimento;
import br.ueg.portalLab.model.Fenologia;
import br.ueg.portalLab.model.GrupoEnderecoFisico;
import br.ueg.portalLab.model.Laboratorio;
import br.ueg.portalLab.model.MetodoColeta;
import br.ueg.portalLab.model.Sexo;
import br.ueg.portalLab.model.TipoDeMontagem;

@org.springframework.stereotype.Component
@Scope("desktop")
public class EspecimeComposer extends ComposerController<Especime> {

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
				
				this.formEspecime.setParent(this.controlEspecime);
				this.controlEspecime.appendChild(this.formEspecime);
				
				this.binderForm = new AnnotateDataBinder(this.formEspecime);
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
		
		//binder.loadComponent(this.getEditForm());
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

	/* Fim tratar guia coelta */

}
