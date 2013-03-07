package br.ueg.portalLab.security.control;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.zkoss.image.Image;
import org.zkoss.util.media.Media;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.model.EspecieImagem;
import br.ueg.portalLab.model.EspecieMultimidia;
import br.ueg.portalLab.model.Especime;
import br.ueg.portalLab.model.ItemTaxonomico;

public class MediaControl<TYPE extends EspecieMultimidia<? extends Media>> extends Control<TYPE> {
	
	
	public boolean saveEspecieMedia(Set<TYPE> especieMediasToSaveSet,ItemTaxonomico selectedItemTaxonomicoMedia,Set<TYPE> especieMediaSavedSet){
		boolean retorno = true;
		if(selectedItemTaxonomicoMedia==null ) return false;
		if(selectedItemTaxonomicoMedia.getId().equals(0L)) return true;
		
		GenericDAO<TYPE> especieMediaDAO = (GenericDAO<TYPE>) SpringFactory.getInstance().getBean("genericDAO",GenericDAO.class);
		GenericDAO<ItemTaxonomico> itemTaxonomicoDAO = (GenericDAO<ItemTaxonomico>) SpringFactory.getInstance().getBean("genericDAO",GenericDAO.class);
		
		
		if(especieMediasToSaveSet!=null){
			for(TYPE ei: especieMediasToSaveSet){
				if(ei.isNew()){
					ei.setItemTaxonomico(selectedItemTaxonomicoMedia);
					
					ei.setNome(ei.getMedia().getName());
					
					int writeImagemToDiskReturn = ei.writeMediaToDisk();
					if(writeImagemToDiskReturn==1){
						try {
							//especieImagemDAO.save(ei);
						} catch (Exception e) {
							this.getMessagesControl().addMessageError("especieImage_salvar");
							e.printStackTrace();
							return false;
						}
					}else{
						if(writeImagemToDiskReturn==0)
							this.getMessagesControl().addMessageError("especieImage_salvar");
						if(writeImagemToDiskReturn==2)
							this.getMessagesControl().addMessageError("especieImage_salvar_existe");
						return false;
					}
				}								
			}
			
			boolean especieMediaExiste = false;;
			try {
				itemTaxonomicoDAO.update(selectedItemTaxonomicoMedia);
			} catch (Exception e) {
				// TODO Auto-generated catch block				
				this.getMessagesControl().addMessageError("especieImage_salvar");
				e.printStackTrace();
				return false;
				
			}
			//remove as imagens removidas
			itemTaxonomicoDAO.refresh(selectedItemTaxonomicoMedia);
			for(TYPE itemEspecieMedia : new ArrayList<TYPE>(especieMediaSavedSet)){
				for(TYPE vEspecieImagem: especieMediasToSaveSet){
					if(itemEspecieMedia.getId().equals(vEspecieImagem.getId())){
						especieMediaExiste = true;
						break;
					}
				}
				if(!especieMediaExiste){
					int deleteMediaFromDiscReturn = itemEspecieMedia.deleteMediaFromDisk();
					if(deleteMediaFromDiscReturn==1){
						especieMediaDAO.delete(itemEspecieMedia);
					}else if(deleteMediaFromDiscReturn==0){
						this.getMessagesControl().addMessageError("especie"+itemEspecieMedia.getTypeMediaSimpleName()+"_remove");
						return false;
					}else if(deleteMediaFromDiscReturn==2){
						this.getMessagesControl().addMessageError("especie"+itemEspecieMedia.getTypeMediaSimpleName()+"_remove_ano_existe");
						return false;
					}
					//TODO EspecieImagem remover imagem do diret�rio
				}
				especieMediaExiste = false;
			}
		}
		return retorno;
	}
	
}
