package br.ueg.portalLab.view.composer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.builderSoft.view.zk.composer.ComposerController;
import br.ueg.portalLab.control.AgrupadorControl;
import br.ueg.portalLab.model.Agrupador;
import br.ueg.portalLab.model.AgrupadorItensTaxonomicos;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class AgrupadorComposer extends ComposerController<Agrupador> {

	@AttributeView(key = "nome", isEntityValue = true, fieldType = String.class, isVisible=true, caption="agrupador_nomeColumn")
	private String fldNome;
	
	@AttributeView(key = "itens", isEntityValue = true, fieldType = Set.class, isVisible=true, caption="agrupador_itensColum")
	private Set<AgrupadorItensTaxonomicos> fldItens;
	
	@AttributeView(key = "searchField", isEntityValue = false, fieldType = String.class, isVisible = true)
	private String searchField;
	
	@AttributeView(key = "selectedAgrupadorItem", isEntityValue = false, fieldType = AgrupadorItensTaxonomicos.class, isVisible=true)
	private AgrupadorItensTaxonomicos selectedAgrupadorItem;
	
	private String titleItensList = "Lista de Itens Taxonomicos";
	
	protected Window winAgrupador;
	
	protected Listbox listAgrupador;
	
	public String getFldNome() {
		return fldNome;
	}

	public void setFldNome(String fldNome) {
		this.fldNome = fldNome;
	}

	public Set<AgrupadorItensTaxonomicos> getFldItens() {
		return fldItens;
	}

	public void setFldItens(Set<AgrupadorItensTaxonomicos> fldItens) {
		this.fldItens = fldItens;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	
	public String getTitleItensList() {
		return titleItensList;
	}

	public void setTitleItensList(String titleItensList) {
		this.titleItensList = titleItensList;
	}

	public AgrupadorItensTaxonomicos getSelectedAgrupadorItem() {
		return selectedAgrupadorItem;
	}

	public void setSelectedAgrupadorItem(
			AgrupadorItensTaxonomicos selectedAgrupadorItem) {
		this.selectedAgrupadorItem = selectedAgrupadorItem;
	}

	public void searchAgrupadores(){
		
	}
	
	public void initSetItens(){
		setFldItens(getSelectedEntity().getItens());
		setTitleItensList("Lista de Itens Taxonomicos do Agrupador " + getSelectedEntity().getNome());
		binder.loadAll();
	}
	
	public void showWinItensTaxonomicos(){
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("agrupador", getSelectedEntity());
		data.put("parentComposer", this);
		
		Executions.createComponents("/pages/agrupador/treeItemTaxonomico.zul", null, data);
	}
	
	public void delteAgrupadorItem(){
		if(doAction("DELETEAGRUPADORITEM")){
			getFldItens().remove(getSelectedAgrupadorItem());
			binder.loadAll();
		}
	}
	
	@Override
	public Control<Agrupador> getControl() {
		return SpringFactory.getInstance().getBean("agrupadorControl",	AgrupadorControl.class);
	}
	
	@Override
	public Control<Agrupador> getNewControl(MessagesControl pMessagesControl) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return Agrupador.class;
	}

	@Override
	public Window getEditForm() {
		return winAgrupador;
	}

	@Override
	public void setEditForm(Window form) {
		this.winAgrupador = form;
	}

}
