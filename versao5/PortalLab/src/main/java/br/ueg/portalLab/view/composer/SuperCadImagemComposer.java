package br.ueg.portalLab.view.composer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Window;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.reflection.Reflection;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.builderSoft.view.zk.composer.ComposerController;
import br.ueg.portalLab.control.CadImagemControl;
import br.ueg.portalLab.control.SuperCadImageControl;
import br.ueg.portalLab.control.ItemTaxonomicoControl;
import br.ueg.portalLab.model.EspecieImagem;
import br.ueg.portalLab.model.ItemTaxonomico;
import br.ueg.portalLab.model.SuperEspecieImagem;
import br.ueg.portalLab.view.utils.ItemTaxonomicoTreeModel;

public abstract class SuperCadImagemComposer<TYPE extends SuperEspecieImagem<Image>> extends
		ComposerController<TYPE> {

//	@Autowired
	protected SuperCadImageControl cadImagemControl;
	@Wire
	protected Window crudCadMedia;
	@Wire
	private Tree treeItemTaxonomico;
	@Wire
	protected Window formImagem;
	@Wire
	protected Window showImage;
	protected AnnotateDataBinder binderForm;
	private ItemTaxonomico itemTaxonomicoSelected;
	@AttributeView(key = "itemTaxonomico", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible = true, caption = "especieImagem_Column", isSearchField = true)
	private ItemTaxonomico fldItemTaxonomico;
	@AttributeView(key = "fileName", isEntityValue = true, fieldType = String.class, isVisible = true, caption = "especieImagem_nome_Column", isSearchField = true)
	private String fldNome;
	private Treeitem treeSelectedItem;
	protected TYPE superEspecieImagem = null;
	@AttributeView(key = "media", isEntityValue = true, fieldType = Media.class, isVisible = true, caption = "especieImagem_Column", isSearchField = false)
	private Media fldMedia;

	public SuperCadImagemComposer() {
		super();
		this.superEspecieImagem = this.getGenericTypeImage();
		
	}
	public abstract TYPE getGenericTypeImage();
		

	public ItemTaxonomicoTreeModel getItemTaxonomicoTreeModel() {
		@SuppressWarnings("unchecked")
		ItemTaxonomicoControl<ItemTaxonomico> itemTaxonomicoControl = (ItemTaxonomicoControl<ItemTaxonomico>)SpringFactory.getInstance().getBean(ItemTaxonomicoControl.class);
		return new ItemTaxonomicoTreeModel(itemTaxonomicoControl.getRootClasseAtividade());
	}

	@Override
	public Control<TYPE> getNewControl(MessagesControl pMessagesControl) {
		return null;
	}

	

	

	@Override
	public Window getEditForm() {
		try {
			if (this.formImagem == null) {
				this.formImagem = (Window) Executions
						.createComponentsDirectly(getZulReader(), null,
								this.crudCadMedia, null);
	
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
		InputStream resourceAsStream = this.crudCadMedia.getDesktop()
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

	public void selectItemTaxonomicoBusca() {
		Tree treeField = (Tree)this.crudCadMedia.getFellow("treeItemTaxonomicoBusca");
		this.selectItemTaxonomico(treeField);
	}

	public void selectItemTaxonomicoForm() {
		Tree treeField = (Tree)this.getEditForm().getFellow("treeItemTaxonomicoForm");
		this.selectItemTaxonomico(treeField); 
	}

	public void selectItemTaxonomico(Tree treeField) {		
		treeSelectedItem = treeField.getSelectedItem();
		if(treeSelectedItem != null){
			this.itemTaxonomicoSelected = (ItemTaxonomico) treeSelectedItem.getValue();
		}else{
			this.itemTaxonomicoSelected = null;
		}
		this.fldItemTaxonomico = this.itemTaxonomicoSelected;
	}

	@Override
	public boolean searchEntity() {
		// TODO Auto-generated method stub
		this.selectItemTaxonomicoBusca();
		boolean retorno = super.searchEntity();
		return retorno;		
	}

	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void newEntity() {
		this.superEspecieImagem = this.getGenericTypeImage();
		super.newEntity();
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void showEditForm() {
		superEspecieImagem = this.getGenericTypeImage();
		super.showEditForm();
		this.binderForm.loadComponent(this.getEditForm().getFellow(
				"imgEspecie"));
	 }

	@Override
	public void hideEditForm() {
		resetFields();
		this.binder.loadAll();
		getEditForm().setVisible(false);
		getEditForm().detach();
		crudCadMedia.removeChild(getEditForm());
		setEditForm(null);		
	}

	public boolean addImage(UploadEvent event) {
	
		org.zkoss.util.media.Media media = event.getMedia();
		((Button) event.getTarget()).setLabel(media.getName());
		if (media instanceof org.zkoss.image.Image) {
	
			TYPE ei = this.getGenericTypeImage();//this.getEspecieImagem();
			ei.setMedia((AImage) media);
	
			this.setEspecieImagem(ei);
			this.binderForm.loadComponent(this.getEditForm().getFellow("imgEspecie"));
	
		} else {
			Messagebox.show("Somente imagem podem ser inclu�das");
		}
	
		return true;
	}

	public TYPE getEspecieImagem() {
		if(this.getFldMedia()==null){
			superEspecieImagem.setMedia(superEspecieImagem.getFileFromCaminho());
		}else{
			superEspecieImagem.setMedia((Image) this.getFldMedia());
		}
		return superEspecieImagem;
	}

	public void setEspecieImagem(TYPE imagem) {
		this.superEspecieImagem = imagem;
		
		this.setFldMedia(this.superEspecieImagem.getMedia());
		this.setFldNome(this.superEspecieImagem.getFileName());
		
		//this.binderForm.loadComponent(this.getEditForm().getFellow("imgEspecie"));
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
	public boolean getIsNew() {
		if(this.getSelectedEntity()==null){
			return true;
		}else{		
			return false;
		}
	}

	public boolean getIsEdit() {
		if(this.getSelectedEntity()==null){
			return false;
		}else{
			return !this.getSelectedEntity().isNew();		
		}
	}

}