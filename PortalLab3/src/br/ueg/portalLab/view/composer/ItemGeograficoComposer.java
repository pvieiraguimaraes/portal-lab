package br.ueg.portalLab.view.composer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.zkoss.zkplus.databind.BindingListModelList;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.view.zk.composer.TabelaComposerController;
import br.ueg.portalLab.control.ItemGeograficoControl;
import br.ueg.portalLab.model.ItemGeografico;
import br.ueg.portalLab.model.NivelGeografico;

@org.springframework.stereotype.Component
@Scope("desktop")
public class ItemGeograficoComposer extends TabelaComposerController<ItemGeografico> {
	
	



	@AttributeView(key = "nome", isEntityValue = true, fieldType = String.class, isVisible=true, caption="itemgeografico_nomeColumn")
	private String fldNome;
	
	@AttributeView(key = "pai", isEntityValue = true, fieldType = ItemGeografico.class, isVisible=true, caption="itemgeografico_paiColumn")
	private ItemGeografico fldPai;
	
	@AttributeView(key = "nivelGeografico", isEntityValue = true, fieldType = NivelGeografico.class, isVisible=true, caption="itemgeografico_nivelGeograficoColumn")
	private NivelGeografico fldNivelGeografico;
	
	
	
	private Set<Entity> listPais;

	/**
	 * 
	 */
	private static final long serialVersionUID = 6642681501922054728L;
	

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return ItemGeografico.class; 
	}

	
	public String getFldNome() {
		return fldNome;
	}


	public void setFldNome(String fldNome) {
		this.fldNome = fldNome;
	}


	
	public ItemGeografico getFldPai() {
		return fldPai;
	}


	public void setFldPai(ItemGeografico fldPai) {
		this.fldPai = fldPai;
	}


	public NivelGeografico getFldNivelGeografico() {
		return fldNivelGeografico;
	}


	public void setFldNivelGeografico(NivelGeografico fldNivelGeografico) {
		this.fldNivelGeografico = fldNivelGeografico;
	}


	public BindingListModelList<Entity> getListNivelGeografico(){
		return this.getFKEntityModel("fldNivelGeografico");
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BindingListModelList<ItemGeografico> getListItemGeograficoPai(){
		if(this.fldNivelGeografico!=null){
			listPais = ((ItemGeograficoControl<ItemGeografico>)this.genericControl.getControl()).getListItemGeograficosPai(this.fldNivelGeografico);
		}
		
		BindingListModelList<ItemGeografico> itemGeograficoModel;
		ArrayList<ItemGeografico> listFKEntity;
		if(listPais!=null && listPais.size()>0){
			listFKEntity = new ArrayList(listPais); 
			Collections.sort( listFKEntity, new Comparator<Entity>(){
			    public int compare( Entity e1, Entity e2 ) {
			      return e1.compare(e2);
			    }
			  });
		}else{
			listFKEntity = new ArrayList<ItemGeografico>();
		}
	
		itemGeograficoModel = new BindingListModelList<ItemGeografico>(listFKEntity,true);
		
		return itemGeograficoModel;
	}


	@Override
	public Control<ItemGeografico> getNewControl(MessagesControl pMessagesControl) {
		return new ItemGeograficoControl<ItemGeografico>(pMessagesControl);
	}
}
