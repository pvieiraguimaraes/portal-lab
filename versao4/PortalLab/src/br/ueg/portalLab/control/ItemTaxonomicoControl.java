package br.ueg.portalLab.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.control.SubControllerManager;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.control.SubController;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.model.ItemTaxonomico;
import br.ueg.portalLab.model.NivelGeografico;
import br.ueg.portalLab.model.NivelTaxonomico;
import br.ueg.portalLab.util.control.ItemTaxonomicoValidatorControl;

@Service
public class ItemTaxonomicoControl<E extends Entity>  extends Control<E> {

	public ItemTaxonomicoControl(MessagesControl pMessagesControl) {
		super(pMessagesControl);
	}
	
	public ItemTaxonomicoControl(){
		
	}
	
	/**
	 * Metodo utilizado para obter a classe root da hierarquia.
	 * @return ItemTaxonomico
	 */
	@SuppressWarnings("unchecked")
	public ItemTaxonomico getRootClasseAtividade(){
		GenericDAO<ItemTaxonomico> clasDAO = (GenericDAO<ItemTaxonomico>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		ItemTaxonomico clas = new ItemTaxonomico();
		//List<ClasseAtividade>  roots = (List<ClasseAtividade>) clasDAO.findByHQL(clas,"%");
		List<ItemTaxonomico>  roots ;
		try{
			roots = (ArrayList<ItemTaxonomico>) clasDAO.findByHQL("from ItemTaxonomico it where it.pai is null order by it.nome asc");
		}catch (Exception e) {
			roots = new ArrayList<ItemTaxonomico>();
		}
		 Collections.sort( roots);
		
		clas.setFilhosItensTaxonomicos(new HashSet<ItemTaxonomico>(roots));
		return clas;
	}
	@SuppressWarnings("unchecked")
	public NivelTaxonomico getNivelTaxonomicoRaiz(){
		GenericDAO<NivelTaxonomico> clasDAO = (GenericDAO<NivelTaxonomico>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		NivelTaxonomico nt = null;
		List<NivelTaxonomico> roots;
		try{
			roots = clasDAO.findByHQL("from NivelTaxonomico nt where nt.pai is null");
			nt = roots.get(0);
		}catch (Exception e) {
			e.printStackTrace();
		}				
		return nt;
		
	}
	
	@SuppressWarnings("unchecked")
	public Set<Entity> getListNivelTaxonomicoFilhos(NivelTaxonomico ntPai){
		GenericDAO<NivelTaxonomico> clasDAO = (GenericDAO<NivelTaxonomico>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		
		String qry = "";
		if(ntPai!=null){
			qry = "from NivelTaxonomico i where i.pai="+ntPai.getId();
		}else{
			qry = "from NivelTaxonomico i where i.pai is null";
		}
		Set<Entity> list = new HashSet<Entity>(clasDAO.findByHQL(qry));
		
		if(ntPai!=null && ntPai.getPai()!=null && list!=null && list.size()==0){
			list = this.getListNivelTaxonomicoFilhos(ntPai.getPai());
		}
		
		return list;
	}
	
	@Override
	public boolean actionDelete(SubControllerManager<E> subControllerManager) {
		ItemTaxonomico auxClasse = (ItemTaxonomico) this.getMapFields().get("selectedEntity");
		if(auxClasse.getPai()!=null){
			auxClasse.getPai().getFilhosItensTaxonomicos().remove(auxClasse);
		}
		return super.actionDelete(subControllerManager);
	}


	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.control.Control#getListValidator()
	 */
	@Override
	public List<SubController> getListValidator() {
		List<SubController> validators = super.getListValidator();

		validators.add((SubController) new ItemTaxonomicoValidatorControl(this.getMessagesControl(), 1,Arrays.asList("DELETE")));
		return validators;
	}

}
