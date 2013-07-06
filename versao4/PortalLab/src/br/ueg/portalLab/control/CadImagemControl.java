package br.ueg.portalLab.control;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.control.SubControllerManager;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.control.ListingControl;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.model.EspecieImagem;
import br.ueg.portalLab.model.Especime;
import br.ueg.portalLab.model.ItemTaxonomico;

@Service
public class CadImagemControl extends Control<EspecieImagem> {
	
	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.control.Control#actionDelete(br.ueg.builderSoft.control.SubControllerManager)
	 */
	@Override
	public boolean actionDelete(
			SubControllerManager<EspecieImagem> subControllerManager) {
		EspecieImagem entity = (EspecieImagem) this.getMapFields().get("entity");
		
		int deleteImagemFromDiscReturn = entity.deleteMediaFromDisk();
		
		boolean imageExcluida = false;
		
		if(deleteImagemFromDiscReturn==1){
			imageExcluida = true;
		}else if(deleteImagemFromDiscReturn==0){
			this.getMessagesControl().addMessageError("especieImage_remove");
			imageExcluida = false;
		}else if(deleteImagemFromDiscReturn==2){
			this.getMessagesControl().addMessageError("especieImage_remove_ano_existe");
			imageExcluida = true;
		}
		
		if(imageExcluida){
			return super.actionDelete(subControllerManager);
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.control.Control#actionSave(br.ueg.builderSoft.control.SubControllerManager)
	 */
	@Override
	public boolean actionSave(SubControllerManager<EspecieImagem> subControllerManager) {
		EspecieImagem entity = (EspecieImagem) this.getMapFields().get("entity");
		entity.writeMediaToDisk();
		return super.actionSave(subControllerManager);
	}
	
	GenericDAO<ItemTaxonomico> itemTaxonomicoDAO ;
	@SuppressWarnings("unchecked")
	public GenericDAO<ItemTaxonomico> getItemTaxonomicoDAO(){
		if (this.itemTaxonomicoDAO ==null){
			this.itemTaxonomicoDAO = (GenericDAO<ItemTaxonomico>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		}
		return itemTaxonomicoDAO;
	}
	
	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.control.Control#actionFindByCriteria(br.ueg.builderSoft.control.SubControllerManager)
	 */
	@Override
	public boolean actionFindByCriteria(
			SubControllerManager<EspecieImagem> subControllerManager) {
		
		ArrayList<String> condicoes = new ArrayList<String>();
		
		if ( this.getMapFields().get("searchEntity") != null ) {
			ListingControl<EspecieImagem> listingControl = (ListingControl<EspecieImagem>) subControllerManager.getListingControl();			
			EspecieImagem entity = (EspecieImagem) this.getMapFields().get("searchEntity");
			String sql = "from EspecieImagem e";
			String retorno = "";
			String idEstacao = entity.getEstacao()==null?"":String.valueOf(entity.getEstacao().getId());
			String nome = entity.getNome();
			String listIdItemTaxonomico = "";
			
			ItemTaxonomico itemTaxonomico = entity.getItemTaxonomico();
			if(itemTaxonomico!=null){
				listIdItemTaxonomico=this.search(itemTaxonomico, retorno);
			}
			

			if(!idEstacao.equals("")){	
				condicoes.add("e.estacao=".concat(idEstacao));
			}
			
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
