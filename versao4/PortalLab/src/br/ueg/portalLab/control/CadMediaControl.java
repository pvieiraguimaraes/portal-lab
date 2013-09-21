package br.ueg.portalLab.control;

import java.util.ArrayList;
import java.util.List;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.control.SubControllerManager;
import br.ueg.builderSoft.util.control.ListingControl;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.control.SubController;
import br.ueg.portalLab.model.EntityMedia;
import br.ueg.portalLab.util.control.CadMediaValidatorControl;

public class CadMediaControl<T extends EntityMedia<?>> extends
		Control<T> {

	protected String textClassName = "";

	public CadMediaControl(MessagesControl pMessagesControl) {
		super(pMessagesControl);
	}

	public CadMediaControl() {
		super();
	}

	@Override
	public boolean actionDelete(SubControllerManager<T> subControllerManager) {
		T entity = (T) this.getMapFields().get("entity");
		
		int deleteImagemFromDiscReturn = entity.deleteMediaFromDisk();
		
		boolean imageExcluida = false;
		
		if(deleteImagemFromDiscReturn==1){
			imageExcluida = true;
		}else if(deleteImagemFromDiscReturn==0){
			this.getMessagesControl().addMessageError(textClassName+"_remove");
			imageExcluida = false;
		}else if(deleteImagemFromDiscReturn==2){
			this.getMessagesControl().addMessageError(textClassName+"_remove_nao_existe");
			imageExcluida = true;
		}
		
		if(imageExcluida){
			return super.actionDelete(subControllerManager);
		}
		
		return false;
	}

	@Override
	public boolean actionSave(SubControllerManager<T> subControllerManager) {
		T entity = (T) this.getMapFields().get("entity");
		entity.writeMediaToDisk();
		return super.actionSave(subControllerManager);
	}

	@Override
	public List<SubController> getListValidator() {
		List<SubController> validators = super.getListValidator();
	
		validators.add((SubController) new CadMediaValidatorControl(this.getMessagesControl(),1));
		return validators;
	}

	protected ArrayList<String> addFilterTextToActionFindByCriteria(T entity) {
		ArrayList<String> condicoes = new ArrayList<String>();
		return condicoes;
	}

	@Override
	public boolean actionFindByCriteria(SubControllerManager<T> subControllerManager) {
		
		ArrayList<String> condicoes = new ArrayList<String>();
		
		if ( this.getMapFields().get("searchEntity") != null ) {
			ListingControl<T> listingControl = (ListingControl<T>) subControllerManager.getListingControl();			
			T entity = (T) this.getMapFields().get("searchEntity");
			textClassName = entity.getClass().getSimpleName();
			String sql = "from "+textClassName+" e";
			
			condicoes.addAll(this.addFilterTextToActionFindByCriteria(entity));
			
			String nome = entity.getFileName();
			if(nome!=null && !nome.equals("")){
				condicoes.add("nome like '%".concat(nome).concat("%'"));			
			}
			
			
			if(condicoes.size()>0){
				boolean primeiro = true;
				for (String cond : condicoes) {
					if(primeiro){
						sql = sql.concat(" where ");
						sql = sql.concat(" ").concat(cond);
						primeiro = false;
					}else{
						sql = sql.concat(" and ").concat(cond);
					}
				}
			}
			
			listingControl.setList(getPersistence().findByHQL(sql));
			listingControl.setListing(true);
			return true;
		} else {
			subControllerManager.getMessagesControl().addMessageError("Busca");
			return false;
		}
	}

}