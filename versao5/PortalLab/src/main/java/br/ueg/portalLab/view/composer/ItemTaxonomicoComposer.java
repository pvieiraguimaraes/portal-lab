package br.ueg.portalLab.view.composer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Window;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.Utils;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.builderSoft.view.zk.composer.ComposerController;
import br.ueg.portalLab.control.ItemTaxonomicoControl;
import br.ueg.portalLab.model.ItemTaxonomico;
import br.ueg.portalLab.model.NivelTaxonomico;
import br.ueg.portalLab.view.utils.ItemTaxonomicoTreeModel;

@Component
@Scope("desktop")
public class ItemTaxonomicoComposer extends ComposerController<ItemTaxonomico> {
	
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -4667702849324491887L;

	@AttributeView(key = "nome", isEntityValue = true, fieldType = String.class, isVisible=true, caption="itemtaxonomico_nomeColumn")
	private String fldNome;
	
	@AttributeView(key = "nomePopular", isEntityValue = true, fieldType = String.class, isVisible=true, caption="itemtaxonomico_nomePopularColumn")
	private String fldNomePopular;
	
	@AttributeView(key = "nivelTaxonomico", isEntityValue = true, fieldType =NivelTaxonomico.class, isVisible=true, caption="itemtaxonomico_nivelColumn")
	private NivelTaxonomico fldNivelTaxonomico;
	
	@AttributeView(key = "pai", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible=true, caption="itemtaxonomico_paiColumn")
	private ItemTaxonomico fldPai;
	
	@AttributeView(key = "filhosItensTaxonomicos", isEntityValue = true, fieldType = Set.class, isVisible=true, caption="itemtaxonomico_filhosColum")
	private Set<ItemTaxonomico> fldFilhosItensTaxonomicos;
	
	@AttributeView(key = "caracteristica", isEntityValue = true, fieldType = String.class, isVisible=true, caption="itemtaxonomico_caracteristicaColum")
	private String fldCaracteristica;

	@Autowired
	protected Control<ItemTaxonomico> controlItemTaxonomico;
	
	//para acesso ao formulario de edicao
	@Wire
	protected Window winItemTaxonomico;
	
	@Wire
	private Tree treeItemTaxonomico;
	

	private String action = "novo";
	
	public ItemTaxonomicoTreeModel getItemTaxonomicoTreeModel(){
		return new ItemTaxonomicoTreeModel(((ItemTaxonomicoControl<ItemTaxonomico>)this.getControl()).getRootClasseAtividade());
	}
	
	@Override
	public Window getEditForm() {
		//if(this.crudFormWindow!=null){
			return this.winItemTaxonomico;
		//}
		//return (Window) this.editFormCrudWindow;
	}
	
	//TODO gambiarra porque nao estava fazendo wire
	@Override
	public void setEditForm(Window form) {
		this.winItemTaxonomico  =  form;
	}

	@Override
	public void selectEntity(){
		this.setSelectedEntity((ItemTaxonomico) this.treeItemTaxonomico.getSelectedItem().getValue());
		super.selectEntity();
	}

	

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return ItemTaxonomico.class;
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
	 * @return the fldPai
	 */
	public ItemTaxonomico getFldPai() {
		return fldPai;
	}
	public NivelTaxonomico getFldNivelTaxonomico() {
		return fldNivelTaxonomico;
	}

	public void setFldNivelTaxonomico(NivelTaxonomico fldNivelTaxonomico) {
		this.fldNivelTaxonomico = Utils.initializeAndUnproxy(fldNivelTaxonomico);
	}

	/**
	 * @param fldPai the fldPai to set
	 */
	public void setFldPai(ItemTaxonomico fldPai) {
		this.fldPai = fldPai;
	}
	
	
	/**
	 * @return the filhos
	 */
	public Set<ItemTaxonomico> getFldFilhosItensTaxonomicos() {
		return fldFilhosItensTaxonomicos;
	}
	/**
	 * @param filhos the filhos to set
	 */
	public void setFldFilhosItensTaxonomicos(Set<ItemTaxonomico> filhos) {
		this.fldFilhosItensTaxonomicos = filhos;
	}

	/**
	 * @return the fldCaracteristica
	 */
	public String getFldCaracteristica() {
		return fldCaracteristica;
	}

	/**
	 * @param fldCaracteristica the fldCaracteristica to set
	 */
	public void setFldCaracteristica(String fldCaracteristica) {
		this.fldCaracteristica = fldCaracteristica;
	}

	public String getFldNomePopular() {
		return fldNomePopular;
	}

	public void setFldNomePopular(String fldNomePopular) {
		this.fldNomePopular = fldNomePopular;
	}

	public boolean getPodeExcluir(){
		return (this.getFldId()!=null && this.getFldFilhosItensTaxonomicos()!=null && this.getFldFilhosItensTaxonomicos().size()==0);
	}
	
	@Override
	public void newEntity() {
		this.action="novo";
		this.selectedEntity =this.initializeEntity();
		//this.selectedEntity.setNivelTaxonomico(((ItemTaxonomicoControl<ItemTaxonomico>) this.control).getNivelTaxonomicoRaiz());
		this.genericControl.associateEntityToAttributeView(this.selectedEntity);
		binder.loadAll();
		binder.saveAll();
		this.showEditForm();
	}
	
	@Override
	public boolean editEntity() {
		this.action="editar";
		return super.editEntity();
	}

	public void newEntityFilha() {
		this.action="novo";
		ItemTaxonomico tempSelectedEntity = this.selectedEntity;
		super.newEntity();
		this.selectedEntity.setPai(tempSelectedEntity);
		this.selectedEntity.setNivelTaxonomico(tempSelectedEntity.getNivelTaxonomico());
		this.genericControl.associateEntityToAttributeView(this.selectedEntity);
		binder.loadComponent(this.winItemTaxonomico);
	}
	
	public BindingListModelList<Entity> getListNivelTaxonomico(){
		return this.getFKEntityModel("fldNivelTaxonomico");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BindingListModelList<NivelTaxonomico> getListNivelTaxonomicoFilhos(){
		
		Set<Entity>	listPais=null;
		if(this.fldNivelTaxonomico!=null && this.action.equalsIgnoreCase("novo")){
			listPais = ((ItemTaxonomicoControl<ItemTaxonomico>)this.genericControl.getControl()).getListNivelTaxonomicoFilhos(this.fldNivelTaxonomico);
		}else if(this.fldNivelTaxonomico!=null && this.action.equalsIgnoreCase("editar")) {
			listPais = ((ItemTaxonomicoControl<ItemTaxonomico>)this.genericControl.getControl()).getListNivelTaxonomicoFilhos(this.fldNivelTaxonomico.getPai());
		}else{
			listPais = ((ItemTaxonomicoControl<ItemTaxonomico>)this.genericControl.getControl()).getListNivelTaxonomicoFilhos(null);
		}
		
		BindingListModelList<NivelTaxonomico> nivelTaxonomicoModel;
		ArrayList<NivelTaxonomico> listFKEntity=null;
		if(listPais!=null && listPais.size()>0){
			listFKEntity = new ArrayList(listPais); 
			Collections.sort( listFKEntity, new Comparator<Entity>(){
			    public int compare( Entity e1, Entity e2 ) {
			      return e1.compareTo(e2);
			    }
			  });
		}else{
			listFKEntity = new ArrayList<NivelTaxonomico>();
		}
	
		nivelTaxonomicoModel = new BindingListModelList<NivelTaxonomico>(listFKEntity,true);
		
		return nivelTaxonomicoModel;
	} 

	@Override
	public Control<ItemTaxonomico> getNewControl(MessagesControl pMessagesControl) {
		return new ItemTaxonomicoControl<ItemTaxonomico>(pMessagesControl); 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Control<ItemTaxonomico> getControl(){
		if(controlItemTaxonomico==null){
			this.controlItemTaxonomico = (ItemTaxonomicoControl<ItemTaxonomico>) SpringFactory.getInstance().getBean("itemTaxonomicoControl",ItemTaxonomicoControl.class);
		}
		return this.controlItemTaxonomico;
	}


}
