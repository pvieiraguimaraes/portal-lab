package br.ueg.portalLab.security.view.composer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.BindingListModelSet;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Window;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.builderSoft.view.zk.component.composite.duallistbox.DualListbox;
import br.ueg.builderSoft.view.zk.composer.TabelaComposerController;
import br.ueg.portalLab.security.control.CasoDeUsoControl;
import br.ueg.portalLab.security.model.CasoDeUso;
import br.ueg.portalLab.security.model.CasoDeUsoFuncionalidade;
import br.ueg.portalLab.security.model.Funcionalidade;


@Component
@Scope("desktop")
public class CasoDeUsoComposer extends TabelaComposerController<CasoDeUso> {

	private static final long serialVersionUID = 2708123019095565737L;

	@AttributeView(key = "nome", isEntityValue = true, fieldType = String.class, isVisible=true, caption="casodeuso_nomeColumn")
	private String fldNome;
	
	@AttributeView(key = "descricao", isEntityValue = true, fieldType = String.class, isVisible=true, caption="casodeuso_descricaoColumn")
	private String fldDescricao;
	
	@AttributeView(key = "status", isEntityValue = true, fieldType = Boolean.class, isVisible=true, caption="casodeuso_statusColumn")
	private Boolean fldStatus;
	
	@Autowired
	protected CasoDeUsoControl casoDeUsoControl;
	
	private BindingListModelSet<Funcionalidade> funcionalidadeList = new BindingListModelSet<Funcionalidade>(
			new HashSet<Funcionalidade>(0), true);
	
	
	@Wire
	private Window casoDeUsoFuncionalidades;
	
	@Wire
	private DualListbox<CasoDeUsoFuncionalidade> dualList;
	
	
	

	
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return CasoDeUso.class;
	}

	@Override
	public Control<CasoDeUso> getNewControl(MessagesControl pMessagesControl) {
		return null;
	}

	public String getFldNome() {
		return fldNome;
	}

	public void setFldNome(String fldNome) {
		this.fldNome = fldNome;
	}

	public String getFldDescricao() {
		return fldDescricao;
	}

	public void setFldDescricao(String fldDescricao) {
		this.fldDescricao = fldDescricao;
	}

	public Boolean getFldStatus() {
		return fldStatus;
	}

	public void setFldStatus(Boolean fldStatus) {
		this.fldStatus = fldStatus;
	}
	@Override
	public Control<CasoDeUso> getControl(){
		if(this.casoDeUsoControl==null){
			this.casoDeUsoControl = (CasoDeUsoControl) SpringFactory.getInstance().getBean("casoDeUsoControl", CasoDeUsoControl.class);
		}
		return this.casoDeUsoControl;
	}
	
	
	/**
	 * Lista todas as funcionalidades cadastrados para o caso de uso selecionado.
	 * 
	 * @return List<FunctionalityUseCase> Lista de funcionalidades.
	 */
	public BindingListModelSet<Funcionalidade> getFuncionalidadeList() {
		Set<Funcionalidade> funcList = new HashSet<Funcionalidade>(0);
		try {
			CasoDeUsoControl cc = (CasoDeUsoControl)this.getControl();
			funcList.addAll(cc.getFuncionalidadeList());
		
		} catch (Throwable e) {
			e.printStackTrace();
		}
		funcionalidadeList.clear();
		funcionalidadeList.addAll(funcList);
		return funcionalidadeList;
	}
	public void teste(){
		Messagebox.show("coisa");
	}
	
	/**
	 * Lista todas as funcionalidades cadastrados para a entidade do composer.
	 * 
	 * @return List<FunctionalityUseCase> Lista de funcionalidades.
	 */
	@SuppressWarnings("unchecked")
	public ListModel<CasoDeUsoFuncionalidade> getCasoDeUsoFuncionalidadeList() {
		BindingListModelSet<CasoDeUsoFuncionalidade> result = null;
		if (selectedEntity != null && selectedEntity.getId()!=0) {
			Set<CasoDeUsoFuncionalidade> funcionalidades = selectedEntity.getFuncionalidades();
			if(funcionalidades==null) funcionalidades = new HashSet<CasoDeUsoFuncionalidade>();
			result = new BindingListModelSet<CasoDeUsoFuncionalidade>(funcionalidades, true);
		}
		return result;
	}
	
	public void setCasoDeUsoFuncionalidadeList(ListModel<CasoDeUsoFuncionalidade> chosenList) {
		Set<CasoDeUsoFuncionalidade> chosenItens = ((BindingListModelSet<CasoDeUsoFuncionalidade>) chosenList).getSelection();
		if(selectedEntity.getFuncionalidades()!=null){
			for (CasoDeUsoFuncionalidade fuc : chosenItens) {
				selectedEntity.getFuncionalidades().add(fuc);
			}
		}
	}
	
	public void editPermissoes(){
		binder.saveAll();
		this.casoDeUsoFuncionalidades.setMode(Window.MODAL);
		this.casoDeUsoFuncionalidades.setVisible(true);
		this.casoDeUsoFuncionalidades.doModal();
		binder.loadAll();
	}
	public void savePermissoes(){
		binder.saveAll();
		this.genericControl.associateEntityToAttributeView(selectedEntity);
		this.genericControl.doAction("SAVE", this.selectedEntity);
		binder.loadAll();
		Messagebox.show("fazer o salva permissoes");
	}
	public void cancelEditFuncionalidades(){
		this.casoDeUsoFuncionalidades.setVisible(false);
	}
}