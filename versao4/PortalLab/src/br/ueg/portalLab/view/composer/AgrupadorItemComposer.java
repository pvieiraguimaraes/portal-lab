package br.ueg.portalLab.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Window;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.builderSoft.view.zk.composer.ComposerController;
import br.ueg.portalLab.control.AgrupadorItemControl;
import br.ueg.portalLab.control.ItemTaxonomicoControl;
import br.ueg.portalLab.model.Agrupador;
import br.ueg.portalLab.model.AgrupadorItensTaxonomicos;
import br.ueg.portalLab.model.ItemTaxonomico;
import br.ueg.portalLab.view.utils.ItemTaxonomicoTreeModel;

@SuppressWarnings({ "rawtypes", "serial" })
@org.springframework.stereotype.Component
@Scope("prototype")
public class AgrupadorItemComposer extends ComposerController<AgrupadorItensTaxonomicos> {
	
	private ComposerController parentComposer;

	@AttributeView(key = "agrupador", isEntityValue = true, fieldType = Agrupador.class, isVisible=true, caption="agrupador_nomeColumn")
	private Agrupador fldAgrupador;
	
	@AttributeView(key = "item", isEntityValue = true, fieldType = ItemTaxonomico.class, isVisible=true, caption="agrupador_nomeColumn")
	private ItemTaxonomico fldItem;
	
	@Wire
	private Tree treeItemTaxonomico;
	
	private Window winItensTaxonomicos;

	public ComposerController getParentComposer() {
		return parentComposer;
	}

	public void setParentComposer(ComposerController parentComposer) {
		this.parentComposer = parentComposer;
	}

	public Agrupador getFldAgrupador() {
		return fldAgrupador;
	}

	public void setFldAgrupador(Agrupador fldAgrupador) {
		this.fldAgrupador = fldAgrupador;
	}

	public ItemTaxonomico getFldItem() {
		return fldItem;
	}

	public void setFldItem(ItemTaxonomico fldItem) {
		this.fldItem = fldItem;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		setFldAgrupador((Agrupador) arg.get("agrupador"));
		setParentComposer((ComposerController) arg.get("parentComposer"));
	}
	
	public void saveAgrupadorItemTaxonomico(){
		this.selectEntity();
		Events.sendEvent(treeItemTaxonomico,new Event("onSelect",treeItemTaxonomico));
		if(this.doAction("SAVE")){
			getParentComposer().doAction("SEARCH");
			getParentComposer().getBinder().loadAll();
			winItensTaxonomicos.detach();
		}
	}
	
	public ItemTaxonomicoTreeModel getItemTaxonomicoTreeModel(){
		@SuppressWarnings("unchecked")
		ItemTaxonomicoControl<ItemTaxonomico> itemTaxonomicoControl = (ItemTaxonomicoControl<ItemTaxonomico>)SpringFactory.getInstance().getBean(ItemTaxonomicoControl.class);
		return new ItemTaxonomicoTreeModel(itemTaxonomicoControl.getRootClasseAtividade());
	}
	
	@Override
	public void selectEntity(){
		this.setFldItem((ItemTaxonomico) this.treeItemTaxonomico.getSelectedItem().getValue());
	}
	
	@Override
	public void hideEditForm() {
		return;
	}
	
	@Override
	public Control<AgrupadorItensTaxonomicos> getNewControl(
			MessagesControl pMessagesControl) {
		return new AgrupadorItemControl(pMessagesControl);
	}

	@Override
	public Class getEntityClass() {
		return AgrupadorItensTaxonomicos.class;
	}

	@Override
	public Window getEditForm() {
		return null;
	}

	@Override
	public void setEditForm(Window form) { }

}
