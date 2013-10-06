package br.ueg.portalLab.view.composer;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Window;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.control.CadFichaTecnicaControl;
import br.ueg.portalLab.control.CadImagemControl;
import br.ueg.portalLab.control.SuperCadImageControl;
import br.ueg.portalLab.model.EspecieImagem;
import br.ueg.portalLab.model.FichaTecnica;
import br.ueg.portalLab.model.SuperEspecieImagem;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class CadFichaTecnicaComposer extends SuperCadImagemComposer<FichaTecnica> {
	
	@AttributeView(key = "fileNameVerso", isEntityValue = true, fieldType = String.class, isVisible = true, caption = "especieImagem_nome_Column", isSearchField = true)
	private String fldFileNameVerso;

	@AttributeView(key = "mediaVerso", isEntityValue = true, fieldType = Image.class, isVisible = true, caption = "especieImagem_Column", isSearchField = false)
	private Image fldMediaVerso;

	@Override
	public FichaTecnica getGenericTypeImage() {
		return new FichaTecnica();
	}
	protected String getPathToFormZulFile() {
		return "/pages/cadfichatecnica" ;//+ this.getEntityClass().getSimpleName().toLowerCase();
	}
	public String getFldFileNameVerso() {
		return fldFileNameVerso;
	}
	public void setFldFileNameVerso(String fldFileNameVerso) {
		this.fldFileNameVerso = fldFileNameVerso;
	}
	public Image getFldMediaVerso() {
		return fldMediaVerso;
	}
	public void setFldMediaVerso(Image fldMediaVerso) {
		this.fldMediaVerso = fldMediaVerso;
	}
	@Override
	public Class getEntityClass() {
		return FichaTecnica.class;
	}
	
	@Override
	public CadFichaTecnicaControl getControl() {
		if (this.cadImagemControl == null) {
			this.cadImagemControl = (CadFichaTecnicaControl) SpringFactory
					.getInstance().getBean("cadFichaTecnicaControl",CadFichaTecnicaControl.class);
		}
		return (CadFichaTecnicaControl) this.cadImagemControl;
	}
	
	public boolean addImageVerso(UploadEvent event) {
		
		org.zkoss.util.media.Media media = event.getMedia();
		((Button) event.getTarget()).setLabel(media.getName());
		if (media instanceof org.zkoss.image.Image) {
			
			
						
			FichaTecnica ft = (FichaTecnica) this.getEspecieImagem();
			

			FichaTecnica ft2 = this.getGenericTypeImage();//this.getEspecieImagem();
			ft2.setMedia(ft.getMedia());
			ft2.setFileName(ft.getFileName());
	
			
			ft2.setMediaVerso((AImage) media);
			ft2.setThumbMediaVerso((AImage) media);
			ft2.setFileNameVerso(media.getName());
			
			//ft.setFileNameVerso(media.getName());
	
			this.setEspecieImagem(ft2);
			this.binderForm.loadComponent(this.getEditForm().getFellow(
					"imgEspecieVerso"));
	
		} else {
			Messagebox.show("Somente imagem podem ser incluídas");
		}
	
		return true;
	}

	public FichaTecnica getEspecieImagem() {
		superEspecieImagem = super.getEspecieImagem();
		if(this.getFldMediaVerso()==null){
			superEspecieImagem.setMediaVerso(superEspecieImagem.getFileVersoFromCaminho());
		}else{
			superEspecieImagem.setMediaVerso((Image) this.getFldMediaVerso());
		}
		
		return superEspecieImagem;
	}

	public void setEspecieImagem(FichaTecnica imagem) {
		super.setEspecieImagem(imagem);
		
		this.setFldMediaVerso(this.superEspecieImagem.getMediaVerso());
		this.setFldFileNameVerso(this.superEspecieImagem.getFileNameVerso());
		
		//this.binderForm.loadComponent(this.getEditForm().getFellow("imgEspecieVerso"));
	}

	public void openImageVerso() {
		if (this.showImage == null) {
			this.showImage = getFormByName("imageViewVerso");
	
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
}
