package br.ueg.portalLab.view.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.ext.TreeSelectableModel;

import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.model.ItemTaxonomico;

public class ItemTaxonomicoTreeModel extends AbstractTreeModel<ItemTaxonomico> implements TreeSelectableModel {

	

	private static final long serialVersionUID = 7275066525411104204L;

	private Map<ItemTaxonomico, List<ItemTaxonomico>> mapChildren = new HashMap<ItemTaxonomico, List<ItemTaxonomico>>();

	private Map<ItemTaxonomico, Integer> mapChildrenCount = new HashMap<ItemTaxonomico, Integer>();

	@SuppressWarnings("unchecked")
	public ItemTaxonomicoTreeModel(ItemTaxonomico root) {
		super(root);
		mapChildren.put(root,  new ArrayList<ItemTaxonomico>(root.getFilhosItensTaxonomicos()));
		mapChildrenCount.put(root, root.getFilhosItensTaxonomicos().size());
	}

	
	@Override
	public boolean isLeaf(ItemTaxonomico node) {
		return getChildCount(node) == 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ItemTaxonomico getChild(ItemTaxonomico parent, int index) {
		if (mapChildren.get(parent) == null) {
			try {
				
				GenericDAO<ItemTaxonomico> clasDAO = (GenericDAO<ItemTaxonomico>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
				ItemTaxonomico clas = new ItemTaxonomico();	
				clas  = (ItemTaxonomico) clasDAO.getByID(clas, parent.getId());
				
				mapChildren.put(parent,new ArrayList<ItemTaxonomico>(clas.getFilhosItensTaxonomicos()));
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mapChildren.get(parent).get(index);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getChildCount(ItemTaxonomico parent) {
		if (mapChildrenCount.get(parent) == null) {
			try {
				
				GenericDAO<ItemTaxonomico> clasDAO = (GenericDAO<ItemTaxonomico>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
				ItemTaxonomico clas = new ItemTaxonomico();	
				clas  = (ItemTaxonomico) clasDAO.getByID(clas, parent.getId());
				
				mapChildrenCount.put(parent, clas.getFilhosItensTaxonomicos().size());
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mapChildrenCount.get(parent);
	}

}
