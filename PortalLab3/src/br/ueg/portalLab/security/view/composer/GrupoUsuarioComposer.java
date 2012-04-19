package br.ueg.portalLab.security.view.composer;

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
import br.ueg.portalLab.security.control.GrupoUsuarioControl;
import br.ueg.portalLab.security.model.CasoDeUso;
import br.ueg.portalLab.security.model.CasoDeUsoFuncionalidade;
import br.ueg.portalLab.security.model.GrupoUsuario;


@Component
@Scope("desktop")
public class GrupoUsuarioComposer extends TabelaComposerController<GrupoUsuario> {

	private static final long serialVersionUID = 2708123019095565737L;

	@AttributeView(key = "nome", isEntityValue = true, fieldType = String.class, isVisible=true, caption="grupousuario_nomeColumn")
	private String fldNome;
	
	@AttributeView(key = "status", isEntityValue = true, fieldType = Boolean.class, isVisible=true, caption="grupousuario_statusColumn")
	private Boolean fldStatus;
	
	@AttributeView(key = "funcionalidades", isEntityValue = true, fieldType = Set.class, isVisible=true, caption="grupousuario_funcionalidadesColumn")
	private Set<CasoDeUsoFuncionalidade> fldFuncionalidades;
	
	
	@Autowired
	protected GrupoUsuarioControl grupoUsuarioControl;
	
	private BindingListModelSet<CasoDeUsoFuncionalidade> funcionalidadeList = new BindingListModelSet<CasoDeUsoFuncionalidade>(
			new HashSet<CasoDeUsoFuncionalidade>(0), true);
	
	@Wire
	private Window casoDeUsoFuncionalidades;
	
	@Wire
	private DualListbox<CasoDeUsoFuncionalidade> dualList;
	
	private CasoDeUso selectedCasoDeUso;
	
	@Override
	public Control<GrupoUsuario> getControl(){
		if(this.grupoUsuarioControl==null){
			this.grupoUsuarioControl = (GrupoUsuarioControl) SpringFactory.getInstance().getBean("grupoUsuarioControl", GrupoUsuarioControl.class);
		}
		return this.grupoUsuarioControl;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return GrupoUsuario.class;
	}

	@Override
	public Control<GrupoUsuario> getNewControl(MessagesControl pMessagesControl) {
		return null;
	}

	public String getFldNome() {
		return fldNome;
	}

	public void setFldNome(String fldNome) {
		this.fldNome = fldNome;
	}	

	public Boolean getFldStatus() {
		return fldStatus;
	}

	public void setFldStatus(Boolean fldStatus) {
		this.fldStatus = fldStatus;
	}

	public Set<CasoDeUsoFuncionalidade> getFldFuncionalidades() {
		return fldFuncionalidades;
	}

	public void setFldFuncionalidades(
			Set<CasoDeUsoFuncionalidade> fldFuncionalidades) {
		this.fldFuncionalidades = fldFuncionalidades;
	}
	
	/**
	 * Lista todas as funcionalidades cadastrados para o caso de uso selecionado.
	 * 
	 * @return List<FunctionalityUseCase> Lista de funcionalidades.
	 */
	public BindingListModelSet<CasoDeUsoFuncionalidade> getFuncionalidadeList() {
		Set<CasoDeUsoFuncionalidade> funcList = new HashSet<CasoDeUsoFuncionalidade>(0);
		try {
			
			funcList.addAll(getGrupoUsuarioControl().getFuncionalidadeList());
		
		} catch (Throwable e) {
			e.printStackTrace();
		}
		funcionalidadeList.clear();
		funcionalidadeList.addAll(funcList);
		return funcionalidadeList;
	}

	
	/**
	 * Lista todas as funcionalidades cadastrados para a entidade do composer.
	 * 
	 * @return List<FunctionalityUseCase> Lista de funcionalidades.
	 */
	public ListModel<CasoDeUsoFuncionalidade> getCasoDeUsoFuncionalidadeList() {
		BindingListModelSet<CasoDeUsoFuncionalidade> result = null;
		if (this.getSelectedEntity() != null && this.getSelectedEntity().getId()!=null) {
			
//			for(CasoDeUsoFuncionalidade cafuList :  list){
//				if(!this.getSelectedCasoDeUso().getFuncionalidades().contains(cafuList)){
//					this.getSelectedCasoDeUso().getFuncionalidades().add(cafuList);
//				}
//			}
//			Set<CasoDeUsoFuncionalidade> list = getCasoDeUsoControl().getCasoDeUsoFuncionalidades();		
			
			result = new BindingListModelSet<CasoDeUsoFuncionalidade>(this.selectedEntity.getFuncionalidades(), true);
		}
		return result;
	}
	/**
	 * Lista todas os casos de uso cadastrados.
	 * 
	 * @return List<FunctionalityUseCase> Lista de funcionalidades.
	 */
	public ListModel<CasoDeUso> getCasoDeUsoList() {
		BindingListModelSet<CasoDeUso> result = null;
		
		result = new BindingListModelSet<CasoDeUso>(this.getGrupoUsuarioControl().getCasoDeUsoList(), true);
		
		return result;
	}
	
	public void setCasoDeUsoFuncionalidadeList(ListModel<CasoDeUsoFuncionalidade> chosenList) {
		Set<CasoDeUsoFuncionalidade> chosenItens = ((BindingListModelSet<CasoDeUsoFuncionalidade>) chosenList).getSelection();
		if(this.getSelectedEntity().getFuncionalidades()!=null){
			for (CasoDeUsoFuncionalidade fuc : chosenItens) {
				this.getSelectedEntity().getFuncionalidades().add(fuc);
			}
		}
	}
	
	public void editPermissoes(){
		binder.saveAll();
		binder.loadComponent(dualList);
		this.casoDeUsoFuncionalidades.setMode(Window.MODAL);
		this.casoDeUsoFuncionalidades.setVisible(true);
		this.casoDeUsoFuncionalidades.doModal();
		
		
	}
	public void savePermissoes(){
		binder.saveAll();
		this.genericControl.associateEntityToAttributeView(this.getSelectedEntity());
		this.genericControl.doAction("SAVE", this.getSelectedEntity());
		this.casoDeUsoFuncionalidades.setVisible(false);
		this.doAction("SEARCH");
	}
	public void cancelEditFuncionalidades(){
		this.casoDeUsoFuncionalidades.setVisible(false);
	}
	
	public GrupoUsuarioControl getGrupoUsuarioControl(){
		return (GrupoUsuarioControl) this.getControl();
	}

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.zk.composer.ComposerController#setSelectedEntity(br.ueg.builderSoft.model.Entity)
	 */
	@Override
	public void setSelectedEntity(GrupoUsuario selectedEntity) {
		super.setSelectedEntity(selectedEntity);
		this.getGrupoUsuarioControl().setSelectedGrupoUsuario(selectedEntity);
	}

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.zk.composer.ComposerController#getSelectedEntity()
	 */
	@Override
	public GrupoUsuario getSelectedEntity() {		
		return this.getGrupoUsuarioControl().getSelectedGrupoUsuario();
	}

	public CasoDeUso getSelectedCasoDeUso() {
		return selectedCasoDeUso;
	}

	public void setSelectedCasoDeUso(CasoDeUso selectedCasoDeUso) {
		this.selectedCasoDeUso = selectedCasoDeUso;
		getGrupoUsuarioControl().setSelectedCasoDeUso(selectedCasoDeUso);
		this.binder.loadComponent(dualList);
	}

}
