package br.ueg.portalLab.control;

import java.util.ArrayList;
import java.util.List;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.control.SubControllerManager;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.control.ListingControl;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.control.SubController;
import br.ueg.builderSoft.util.reflection.Reflection;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.model.EspecieMultimidia;
//import br.ueg.portalLab.model.EspecieImagem;
import br.ueg.portalLab.model.ItemTaxonomico;
import br.ueg.portalLab.util.control.CadMediaValidatorControl;
import br.ueg.portalLab.util.control.EspecimeValidatorControl;

public class CadMediaControl<T extends EspecieMultimidia<?>> extends Control<T> {

	protected String textClassName = "";
	GenericDAO<ItemTaxonomico> itemTaxonomicoDAO;

	public CadMediaControl(MessagesControl pMessagesControl) {
		super(pMessagesControl);
	}
	public CadMediaControl(){
		super();
		try {
			textClassName = Reflection.getParameterizedTypeClass(this.getClass(), 0).getSimpleName();
		} catch (Exception e) {
			System.out.println("ERRO: CadImageControlConstructor");
			e.printStackTrace();
		}
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


	@SuppressWarnings("unchecked")
	public GenericDAO<ItemTaxonomico> getItemTaxonomicoDAO() {
		if (this.itemTaxonomicoDAO ==null){
			this.itemTaxonomicoDAO = (GenericDAO<ItemTaxonomico>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		}
		return itemTaxonomicoDAO;
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.control.Control#getListValidator()
	 */
	@Override
	public List<SubController> getListValidator() {
		List<SubController> validators = super.getListValidator();

		validators.add((SubController) new CadMediaValidatorControl(this.getMessagesControl(),1));
		return validators;
	}

	protected ArrayList<String> addFilterTextToActionFindByCriteria(T entity){
		return new ArrayList<>(0);
	}
	@Override
	public boolean actionFindByCriteria(SubControllerManager<T> subControllerManager) {
		
		ArrayList<String> condicoes = new ArrayList<String>();
		
		if ( this.getMapFields().get("searchEntity") != null ) {
			ListingControl<T> listingControl = (ListingControl<T>) subControllerManager.getListingControl();			
			T entity = (T) this.getMapFields().get("searchEntity");
			textClassName = entity.getClass().getSimpleName();
			String sql = "from "+textClassName+" e";
			String retorno = "";
			
			String nome = entity.getNome();
			String listIdItemTaxonomico = "";
			
			ItemTaxonomico itemTaxonomico = entity.getItemTaxonomico();
			if(itemTaxonomico!=null){
				listIdItemTaxonomico=this.search(itemTaxonomico, retorno);
			}
			
	
			condicoes.addAll(this.addFilterTextToActionFindByCriteria(entity));
			
			if(!nome.equals("")){
				condicoes.add("nome like '%".concat(nome).concat("%'"));			
			}
			if(!listIdItemTaxonomico.equals("")){
				condicoes.add("e.itemTaxonomico in(".concat(listIdItemTaxonomico).concat(" )") );
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

	public String search(ItemTaxonomico itax, String retorno) {
		String qry = "from ItemTaxonomico i where i.pai ="
				+ itax.getId().toString() + " order by i.nome";
		
		List<ItemTaxonomico> findByHQL = this.getItemTaxonomicoDAO().findByHQL(qry);
		retorno = itax.getId().toString();
		for (ItemTaxonomico itemTaxonomico : findByHQL) {
			retorno = retorno.concat(",").concat(this.search(itemTaxonomico, retorno));
		}
	
		return retorno;
	}

}