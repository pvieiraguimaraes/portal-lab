package br.ueg.portalLab.view.composer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Window;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.builderSoft.view.zk.composer.ComposerController;
import br.ueg.portalLab.control.CadImagemControl;
import br.ueg.portalLab.control.ItemTaxonomicoControl;
import br.ueg.portalLab.model.EspecieImagem;
import br.ueg.portalLab.model.EspecieMultimidia;
import br.ueg.portalLab.model.Estacao;
import br.ueg.portalLab.model.ItemTaxonomico;
import br.ueg.portalLab.model.Laboratorio;
import br.ueg.portalLab.view.utils.ItemTaxonomicoTreeModel;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class CadImagemComposer extends ComposerController<EspecieImagem> {

	@Autowired
	protected CadImagemControl cadImagemControl;
	
	@Wire
	protected Window crudCadImagem;
	
	@Wire
	private Tree treeItemTaxonomico;
	
	@Wire
	protected Window formImagem;
	
	@Wire
	protected Window showImage;
	
	protected AnnotateDataBinder binderForm;

	private ItemTaxonomico itemTaxonomicoSelected;
	
	private Estacao fldEstacaoBusca;
	
	@AttributeView(key = "itemTaxonomico", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true, caption = "especieImagem_Column", isSearchField=true)
	private ItemTaxonomico fldItemTaxonomico;
	
	@AttributeView(key = "estacao", isEntityValue = true, fieldType = Estacao.class, isVisible = true, caption = "especieImagem_estacao_Column", isSearchField=true)
	private Estacao fldEstacao;
	
	@AttributeView(key = "nome", isEntityValue = true, fieldType = String.class, isVisible = true, caption = "especieImagem_nome_Column", isSearchField=true)
	private String fldNome;

	private Treeitem treeSelectedItem;

	private EspecieImagem especieImagem = new EspecieImagem();
	
	@AttributeView(key = "media", isEntityValue = true, fieldType = Media.class, isVisible = true, caption = "especieImagem_Column", isSearchField=false)
	private Media fldMedia;
	
	public ItemTaxonomicoTreeModel getItemTaxonomicoTreeModel(){
		@SuppressWarnings("unchecked")
		ItemTaxonomicoControl<ItemTaxonomico> itemTaxonomicoControl = (ItemTaxonomicoControl<ItemTaxonomico>)SpringFactory.getInstance().getBean(ItemTaxonomicoControl.class);
		return new ItemTaxonomicoTreeModel(itemTaxonomicoControl.getRootClasseAtividade());
	}
	
	@Override
	public Control<EspecieImagem> getNewControl(MessagesControl pMessagesControl) {
		return null;
	}

	@Override
	public Class getEntityClass() {
		return EspecieImagem.class;
	}
	
	@Override
	public Control<EspecieImagem> getControl() {
		if (this.cadImagemControl == null) {
			this.cadImagemControl = (CadImagemControl) SpringFactory
					.getInstance().getBean("cadImagemControl",CadImagemControl.class);
		}
		return this.cadImagemControl;
	}

	@Override
	public Window getEditForm() {
		try {
			if (this.formImagem == null) {
				this.formImagem = (Window) Executions
						.createComponentsDirectly(getZulReader(), null,
								this.crudCadImagem, null);

				this.binderForm = new AnnotateDataBinder(this.formImagem);
				this.binderForm.setLoadOnSave(false);
				this.binderForm.bindBean("controller", this);
				
				this.binderForm.loadComponent(this.formImagem);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this.formImagem;
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
		InputStream resourceAsStream = this.crudCadImagem.getDesktop()
				.getWebApp().getResourceAsStream(name);
		return new InputStreamReader(resourceAsStream, "UTF-8");
	}

	protected String getPathToFormZulFile() {
		return "/pages/cadimagem" ;//+ this.getEntityClass().getSimpleName().toLowerCase();
	}

	/**
	 * @return the fldItemTaxonomico
	 */
	public ItemTaxonomico getFldItemTaxonomico() {
		return fldItemTaxonomico;
	}

	/**
	 * @param fldItemTaxonomico the fldItemTaxonomico to set
	 */
	public void setFldItemTaxonomico(ItemTaxonomico fldItemTaxonomico) {
		this.fldItemTaxonomico = fldItemTaxonomico;
	}

	/**
	 * @return the fldEstacao
	 */
	public Estacao getFldEstacao() {
		return fldEstacao;
	}

	/**
	 * @return the fldEstacaoForm
	 */
	public Estacao getFldEstacaoBusca() {
		return fldEstacaoBusca;
	}

	/**
	 * @param fldEstacaoBusca the fldEstacaoForm to set
	 */
	public void setFldEstacaoBusca(Estacao fldEstacaoBusca) {
		this.fldEstacaoBusca = fldEstacaoBusca;
	}

	/**
	 * @param fldEstacao the fldEstacao to set
	 */
	public void setFldEstacao(Estacao fldEstacao) {
		this.fldEstacao = fldEstacao;
	}

	/**
	 * @return the fldNome
	 */
	public String getFldNome() {
		return fldNome;
	}

	/**
	 * @param fldNome the fldNome to set
	 */
	public void setFldNome(String fldNome) {
		this.fldNome = fldNome;
	}

	/**
	 * @return the fldMedia
	 */
	public Media getFldMedia() {
		return fldMedia;
	}

	/**
	 * @param fldMedia the fldMedia to set
	 */
	public void setFldMedia(Media fldMedia) {
		this.fldMedia = fldMedia;
	}

	@Override
	public void setEditForm(Window form) {
		this.formImagem = form;
	}
	public void selectItemTaxonomicoBusca(){
		Tree treeField = (Tree)this.crudCadImagem.getFellow("treeItemTaxonomicoBusca");
		this.selectItemTaxonomico(treeField);
	}
	public void selectItemTaxonomicoForm(){
		Tree treeField = (Tree)this.getEditForm().getFellow("treeItemTaxonomicoForm");
		this.selectItemTaxonomico(treeField); 
	}
	public void selectItemTaxonomico(Tree treeField){		
		treeSelectedItem = treeField.getSelectedItem();
		if(treeSelectedItem != null){
			this.itemTaxonomicoSelected = (ItemTaxonomico) treeSelectedItem.getValue();
		}else{
			this.itemTaxonomicoSelected = null;
		}
		this.fldItemTaxonomico = this.itemTaxonomicoSelected;
	}
	
	public void selectEstacaoBusca(){
		Combobox CBEstacao = (Combobox)this.crudCadImagem.getFellow("cmbEstacaoBusca");
		Estacao estacaoField;
		if(CBEstacao.getSelectedItem()!=null){
			estacaoField = (Estacao)CBEstacao.getSelectedItem().getValue();
		}else{
			estacaoField = null;
		}
		this.setFldEstacao(estacaoField);	
	}
	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.zk.composer.ComposerController#searchEntity()
	 */
	@Override
	public boolean searchEntity() {
		// TODO Auto-generated method stub
		this.selectItemTaxonomicoBusca();
		boolean retorno = super.searchEntity();
		return retorno;		
	}
	
	public BindingListModelList<Entity> getEstacaoList() {
		return this.getFKEntityModel("fldEstacao");
	}

	/**
	 * @return the treeSelectedItem
	 */
	public Treeitem getTreeSelectedItem() {
		return treeSelectedItem;
	}

	/**
	 * @param treeSelectedItem the treeSelectedItem to set
	 */
	public void setTreeSelectedItem(Treeitem treeSelectedItem) {
		this.treeSelectedItem = treeSelectedItem;
	}
	
	 /* (non-Javadoc)
	 * 
	 * @see
	 * br.ueg.builderSoft.view.zk.composer.ComposerController#showEditForm()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void showEditForm() {
		especieImagem = new EspecieImagem();
		super.showEditForm();
		this.binderForm.loadComponent(this.getEditForm().getFellow(
				"imgEspecie"));
	 }
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.ueg.builderSoft.view.zk.composer.ComposerController#hideEditForm()
	 */
	@Override
	public void hideEditForm() {
		resetFields();
		this.binder.loadAll();
		getEditForm().setVisible(false);
		getEditForm().detach();
		crudCadImagem.removeChild(getEditForm());
		setEditForm(null);		
	}


	/* inicio guia multimidia */
	public boolean addImage(UploadEvent event) {

		org.zkoss.util.media.Media media = event.getMedia();
		((Button) event.getTarget()).setLabel(media.getName());
		if (media instanceof org.zkoss.image.Image) {
			org.zkoss.zul.Image image = new org.zkoss.zul.Image();
			image.setContent((Image) media);

			EspecieImagem ei = new EspecieImagem();
			ei.setMedia((AImage) media);

			this.setEspecieImagem(ei);
			this.binderForm.loadComponent(this.getEditForm().getFellow(
					"imgEspecie"));

		} else {
			Messagebox.show("Somente imagem podem ser inclu�das");
		}

		return true;
	}

	public EspecieImagem getEspecieImagem() {
		if(this.getFldMedia()==null){
			especieImagem.setMedia(especieImagem.getFileFromCaminho());
		}else{
			especieImagem.setMedia((Image) this.getFldMedia());
		}
		return especieImagem;
	}

	public void setEspecieImagem(EspecieImagem imagem) {
		this.especieImagem = imagem;
		
		this.setFldMedia(this.especieImagem.getMedia());
		this.setFldNome(this.especieImagem.getNome());
		
		this.binderForm.loadComponent(this.getEditForm()
				.getFellow("imgEspecie"));
	}
	
	public void openImage() {
		if (this.showImage == null) {
			this.showImage = getFormByName("imageView");

			// this.formEspecime.setParent(this.controlEspecime);
			// this.controlEspecime.appendChild(this.formEspecime);

			AnnotateDataBinder binder = new AnnotateDataBinder(this.showImage);
			binder.setLoadOnSave(false);
			binder.bindBean("controller", this);
			binder.loadComponent(this.showImage);
			showImage.setMode(Window.MODAL);
			showImage.setVisible(true);
			showImage.doModal();

		}

	}
	
	public void closeImage() {
		if (this.showImage != null) {
			showImage.setVisible(false);
			showImage.detach();
			//control.removeChild(showImage);
			showImage = null;
		}
	}
	@Override
	public boolean getIsNew(){
		if(this.getSelectedEntity()==null){
			return true;
		}else{		
			return false;
		}
	}
	public boolean getIsEdit(){
		if(this.getSelectedEntity()==null){
			return false;
		}else{
			return !this.getSelectedEntity().isNew();		
		}
	}
}
