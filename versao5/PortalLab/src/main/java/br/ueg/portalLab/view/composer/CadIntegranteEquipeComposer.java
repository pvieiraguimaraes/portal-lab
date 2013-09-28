package br.ueg.portalLab.view.composer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
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
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.builderSoft.view.zk.composer.ComposerController;
import br.ueg.portalLab.control.CadImagemControl;
import br.ueg.portalLab.control.CadIntegranteControl;
import br.ueg.portalLab.control.ItemTaxonomicoControl;
import br.ueg.portalLab.control.SuperCadImageControl;
import br.ueg.portalLab.model.EspecieImagem;
import br.ueg.portalLab.model.IntegranteEquipe;
import br.ueg.portalLab.model.ItemTaxonomico;
import br.ueg.portalLab.model.SuperEspecieImagem;
import br.ueg.portalLab.view.utils.ItemTaxonomicoTreeModel;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class CadIntegranteEquipeComposer  extends
		ComposerController<IntegranteEquipe<Image>> {

//	@Autowired
	protected CadIntegranteControl cadIntegranteControl;
	@Wire
	protected Window crudCadMedia;
	@Wire
	protected Window formImagem;
	@Wire
	protected Window showImage;
	protected AnnotateDataBinder binderForm;
	
	@AttributeView(key = "nome", isEntityValue = true, fieldType = String.class, isVisible = true, caption = "integrante_nome_Column", isSearchField = true)
	private String fldNome;
	
	@AttributeView(key = "searchValue", isEntityValue = false, fieldType = String.class, isVisible = true, caption = "integrante_nome_Column", isSearchField = true)
	private String fldNomeBusca;
	
	@AttributeView(key = "fileName", isEntityValue = true, fieldType = String.class, isVisible = true, caption = "integrante_filename_Column", isSearchField = true)
	private String fldFileName;
	
	@AttributeView(key = "descricao", isEntityValue = true, fieldType = String.class, isVisible = true, caption = "integrante_descricao_Column", isSearchField = true)
	private String fldDescricao;
	
	@AttributeView(key = "linkLates", isEntityValue = true, fieldType = String.class, isVisible = true, caption = "integrante_linklates_Column", isSearchField = true)
	private String fldLinkLates;
	
	private IntegranteEquipe<Image> integranteEquipe = null;

	@AttributeView(key = "media", isEntityValue = true, fieldType = Media.class, isVisible = true, caption = "especieImagem_Column", isSearchField = false)
	private Media fldMedia;

	/**
	 * @return the fldFileName
	 */
	public String getFldFileName() {
		return fldFileName;
	}

	/**
	 * @param fldFileName the fldFileName to set
	 */
	public void setFldFileName(String fldFileName) {
		this.fldFileName = fldFileName;
	}

	/**
	 * @return the fldDescricao
	 */
	public String getFldDescricao() {
		return fldDescricao;
	}

	/**
	 * @param fldDescricao the fldDescricao to set
	 */
	public void setFldDescricao(String fldDescricao) {
		this.fldDescricao = fldDescricao;
	}

	public CadIntegranteEquipeComposer() {
		super();
		this.integranteEquipe = new IntegranteEquipe<Image>();
		
	}

	@Override
	public CadIntegranteControl getNewControl(MessagesControl pMessagesControl) {
		return null;
	}

	@Override
	public boolean searchEntity() {
		this.setFldNome(this.getFldNomeBusca());
		boolean retorno = super.searchEntity();
		return retorno;		
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
		return "/pages/cadintegrante" ;//+ this.getEntityClass().getSimpleName().toLowerCase();
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


	/**
	 * @return the fldLinkLates
	 */
	public String getFldLinkLates() {
		return fldLinkLates;
	}

	/**
	 * @param fldLinkLates the fldLinkLates to set
	 */
	public void setFldLinkLates(String fldLinkLates) {
		this.fldLinkLates = fldLinkLates;
	}

	/**
	 * @return the fldNomeBusca
	 */
	public String getFldNomeBusca() {
		return fldNomeBusca;
	}

	/**
	 * @param fldNomeBusca the fldNomeBusca to set
	 */
	public void setFldNomeBusca(String fldNomeBusca) {
		this.fldNomeBusca = fldNomeBusca;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void showEditForm() {
		integranteEquipe = new IntegranteEquipe<>();
		super.showEditForm();
		this.binderForm.loadComponent(this.getEditForm().getFellow(
				"imgIntegrante"));
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
			org.zkoss.zul.Image image = new org.zkoss.zul.Image();
			image.setContent((Image) media);
	
			IntegranteEquipe<Image> ei = new IntegranteEquipe<Image>();
			ei.setMedia((AImage) media);
	
			this.setIntegranteEquipe(ei);
			this.binderForm.loadComponent(this.getEditForm().getFellow(
					"imgIntegrante"));
	
		} else {
			Messagebox.show("Somente imagem podem ser inclu�das");
		}
	
		return true;
	}

	public IntegranteEquipe<Image> getIntegranteEquipe() {
		if(this.getFldMedia()==null){
			integranteEquipe.setMedia(integranteEquipe.getFileFromCaminho());
		}else{
			integranteEquipe.setMedia((Image) this.getFldMedia());
		}
		return integranteEquipe;
	}

	public void setIntegranteEquipe(IntegranteEquipe<Image> imagem) {
		this.integranteEquipe = imagem;
		
		this.setFldMedia(this.integranteEquipe.getMedia());
		this.setFldFileName(this.integranteEquipe.getFileName());
		
		this.binderForm.loadComponent(this.getEditForm()
				.getFellow("imgIntegrante"));
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

	@Override
	public Class getEntityClass() {
		return IntegranteEquipe.class;
	}
	
	@Override
	public CadIntegranteControl getControl() {
		if (this.cadIntegranteControl == null) {
			this.cadIntegranteControl = (CadIntegranteControl) SpringFactory
					.getInstance().getBean("cadIntegranteControl",CadIntegranteControl.class);
		}
		return this.cadIntegranteControl;
	}

}