package br.ueg.builderSoft.view.zk.component.composite.duallistbox;

import java.util.HashSet;
import java.util.Set;

import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.BindingListModelSet;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;

import br.ueg.builderSoft.view.zk.component.composite.BaseComponent;



public class DualListbox<E> extends BaseComponent {

	private static final long serialVersionUID = -8948309067427866919L;

	@Wire
	protected Listbox candidateLb;

	@Wire
	protected Listbox chosenLb;

	@Wire
	protected Listheader candidateHeader;

	@Wire
	protected Listheader chosenHeader;

	@Wire
	protected Button chooseBtn;

	@Wire
	protected Button removeBtn;

	@Wire
	protected Button chooseAllBtn;

	@Wire
	protected Button removeAllBtn;

	@Listen("onClick = #chooseBtn")
	public void chooseAction() {
		Set<E> set = new HashSet<E>(0);
		set.addAll(getCandidateModel().getSelection());
		getChosenModel().addAll(set);
		getCandidateModel().removeAll(set);
		renderDualListbox();
	}

	@Listen("onClick = #removeBtn")
	public void removeAction() {
		Set<E> set = new HashSet<E>(0);
		set.addAll(getChosenModel().getSelection());
		getCandidateModel().addAll(set);
		getChosenModel().removeAll(set);
		renderDualListbox();
	}

	@Listen("onClick = #chooseAllBtn")
	public void chooseAllAction() {
		for (int i = 0, j = getCandidateModel().getSize(); i < j; i++) {
			getChosenModel().add(getCandidateModel().getElementAt(i));
		}
		getCandidateModel().clear();
		renderDualListbox();
	}

	@Listen("onClick = #removeAllBtn")
	public void removeAllAction() {
		for (int i = 0, j = getChosenModel().getSize(); i < j; i++) {
			getCandidateModel().add(getChosenModel().getElementAt(i));
		}
		getChosenModel().clear();
		renderDualListbox();
	}

	@SuppressWarnings("unchecked")
	private BindingListModelSet<E> getCandidateModel() {
		return (BindingListModelSet<E>) this.candidateLb.getModel();
	}

	@SuppressWarnings("unchecked")
	private BindingListModelSet<E> getChosenModel() {
		return (BindingListModelSet<E>) this.chosenLb.getModel();
	}

	public void setCandidateLabel(String candidateHeader) {
		this.candidateHeader.setLabel(candidateHeader);
	}

	public void setChosenLabel(String chosenHeader) {
		this.chosenHeader.setLabel(chosenHeader);
	}

	public void setCandidateModel(BindingListModelSet<E> candidateModel) {
		this.candidateLb.setModel(candidateModel);
	}

	public void setChosenModel(BindingListModelSet<E> chosenModel) {
		this.chosenLb.setModel(chosenModel);
	}

	/**
	 * Atualiza os elementos da duas listas do componente.
	 */
	public void renderDualListbox() {
		candidateLb.renderAll();
		chosenLb.renderAll();
	}

	/**
	 * Valor atribuido ao label do botão responsável por adicionar um elemento selecionado da candidateLb para a
	 * chosenLb.
	 * 
	 * @param value
	 */
	public void setAddOneLabel(String value) {
		if (value == null || value.isEmpty())
			chooseBtn.setLabel("Adicionar");
		else
			chooseBtn.setLabel(value);

	}

	/**
	 * Valor atribuido ao label do botão responsável por adicionar todos os elementos da candidateLb para a chosenLb.
	 * 
	 * @param value
	 */
	public void setAddAllLabel(String value) {
		if (value == null || value.isEmpty())
			chooseAllBtn.setLabel("Adicionar Todos");
		else
			chooseAllBtn.setLabel(value);
	}

	/**
	 * Valor atribuido ao label do botão responsável por remover o elemento selecionado da chosenLb.
	 * 
	 * @param value
	 */
	public void setRemoveOneLabel(String value) {
		if (value == null || value.isEmpty())
			removeBtn.setLabel("Remover");
		else
			removeBtn.setLabel(value);
	}

	/**
	 * Valor atribuido ao label do botão responsável por remover todos elementos da chosenLb.
	 * 
	 * @param value
	 */
	public void setRemoveAllLabel(String value) {
		if (value == null || value.isEmpty())
			removeAllBtn.setLabel("Remover Todos");
		else
			removeAllBtn.setLabel(value);

	}

	/**
	 * Define se as listbox utilizadas no componente permitirão múltipla seleção.
	 * 
	 * @param b
	 */
	public void setMultipleSelection(Boolean b) {
		if (b == null)
			b = Boolean.FALSE;
		candidateLb.setCheckmark(b);
		candidateLb.setMultiple(b);
		chosenLb.setCheckmark(b);
		chosenLb.setMultiple(b);
		renderDualListbox();
	}

}