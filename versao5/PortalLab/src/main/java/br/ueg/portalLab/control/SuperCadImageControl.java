package br.ueg.portalLab.control;

import java.util.ArrayList;
import java.util.List;

import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.reflection.Reflection;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.model.EspecieMultimidia;
//import br.ueg.portalLab.model.EspecieImagem;
import br.ueg.portalLab.model.ItemTaxonomico;

@SuppressWarnings("rawtypes")
public class SuperCadImageControl<T extends EspecieMultimidia> extends CadMediaControl<T> {

	GenericDAO<ItemTaxonomico> itemTaxonomicoDAO;

	public SuperCadImageControl(MessagesControl pMessagesControl) {
		super(pMessagesControl);
	}
	public SuperCadImageControl(){
		super();
		try {
			textClassName = Reflection.getParameterizedTypeClass(this.getClass(), 0).getSimpleName();
		} catch (Exception e) {
			System.out.println("ERRO: CadImageControlConstructor");
			e.printStackTrace();
		}
	}
	protected ArrayList<String> addFilterTextToActionFindByCriteria(T entity) {
		ArrayList<String> condicoes = super.addFilterTextToActionFindByCriteria(entity);
		condicoes.addAll(this.addFilterTextToActionFindByCriteriaItemTaxonomico(entity));
		return condicoes;
	}

	@SuppressWarnings("unchecked")
	public GenericDAO<ItemTaxonomico> getItemTaxonomicoDAO() {
		if (this.itemTaxonomicoDAO ==null){
			this.itemTaxonomicoDAO = (GenericDAO<ItemTaxonomico>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		}
		return itemTaxonomicoDAO;
	}
	
	protected ArrayList<String> addFilterTextToActionFindByCriteriaItemTaxonomico(T entity){
		String retorno = "";
		ArrayList<String> condicoes = new ArrayList<String>();
		String listIdItemTaxonomico = "";			
		ItemTaxonomico itemTaxonomico = entity.getItemTaxonomico();
		if(itemTaxonomico!=null){
			listIdItemTaxonomico=this.search(itemTaxonomico, retorno);
		}
		if(!listIdItemTaxonomico.equals("")){
			condicoes.add("e.itemTaxonomico in(".concat(listIdItemTaxonomico).concat(" )") );
		}
		return condicoes;
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