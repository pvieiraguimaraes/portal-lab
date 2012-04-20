package br.ueg.builderSoft.security.view.composer;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.databind.BindingListModelSet;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Window;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.security.control.UsuarioControl;
import br.ueg.builderSoft.security.model.CasoDeUso;
import br.ueg.builderSoft.security.model.CasoDeUsoFuncionalidade;
import br.ueg.builderSoft.security.model.GrupoUsuario;
import br.ueg.builderSoft.security.model.Usuario;
import br.ueg.builderSoft.security.model.UsuarioPermissao;
import br.ueg.builderSoft.security.view.listener.ListboxListenUsuarioPermissao;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.constant.ComponentType;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.builderSoft.view.zk.component.composite.duallistbox.DualListbox;
import br.ueg.builderSoft.view.zk.composer.TabelaComposerController;

@org.springframework.stereotype.Component
@Scope("desktop")
public class UsuarioComposer extends TabelaComposerController<Usuario> {
	
	



	@AttributeView(key = "nome", isEntityValue = true, fieldType = String.class, isVisible=true, caption="usuario_nomeColumn")
	private String fldNome;
	
	@AttributeView(key = "login", isEntityValue = true, fieldType = String.class, isVisible=true, caption="usuario_loginColumn")
	private String fldLogin;
	
	@AttributeView(key = "senha", isEntityValue = true, fieldType = String.class, isVisible=true, caption="usuario_senhaColumn", component = ComponentType.INPUT_SECRET)
	private String fldSenha;
	
	@AttributeView(key = "Status", isEntityValue = true, fieldType = Boolean.class, isVisible=true, caption="usuario_statusColumn")
	private Boolean fldStatus;
	
	@AttributeView(key = "permissoes", isEntityValue = true, fieldType = Set.class, isVisible=true, caption="usuario_permissoesColumn")
	private Set<UsuarioPermissao> fldPermissoes;
	
	@AttributeView(key = "grupos", isEntityValue = true, fieldType = Set.class, isVisible=true, caption="usuario_gruposColumn")
	private Set<GrupoUsuario> fldGrupos;
	
	
	private BindingListModelSet<UsuarioPermissao> usuarioPermissaoList = new BindingListModelSet<UsuarioPermissao>(new HashSet<UsuarioPermissao>(0), true);
	
	private BindingListModelSet<GrupoUsuario> grupoUsuarioList = new BindingListModelSet<GrupoUsuario>(new HashSet<GrupoUsuario>(0), true);
	
	
	
	@Wire
	private Checkbox checkBoxPermitido;  
	
	
	@SuppressWarnings("unused")
	@Wire
	private Window casoDeUsoFuncionalidades;
	
	@Wire
	private DualListbox<CasoDeUsoFuncionalidade> dualList;
	
	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.zk.composer.ComposerController#doAfterCompose(org.zkoss.zk.ui.Component)
	 */
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		dualList.setCandidateOnSelectListen(new ListboxListenUsuarioPermissao(this.checkBoxPermitido));
	}

	private CasoDeUso selectedCasoDeUso;
	
	@Autowired
	protected UsuarioControl usuarioControl;
	/**
	 * 
	 */
	private static final long serialVersionUID = 6642681501922054728L;
	

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return Usuario.class; 
	}

	
	public String getFldNome() {
		return fldNome;
	}


	public void setFldNome(String fldNome) {
		this.fldNome = fldNome;
	}


	public String getFldLogin() {
		return fldLogin;
	}


	public void setFldLogin(String fldLogin) {
		this.fldLogin = fldLogin;
	}


	public String getFldSenha() {
		return fldSenha;
	}


	public void setFldSenha(String fldSenha) {
		this.fldSenha = fldSenha;
	}

	/**
	 * @return the fldStatus
	 */
	public Boolean getFldStatus() {
		return fldStatus;
	}


	/**
	 * @param fldStatus the fldStatus to set
	 */
	public void setFldStatus(Boolean fldStatus) {
		this.fldStatus = fldStatus;
	}


	/**
	 * @return the fldPermissoes
	 */
	public Set<UsuarioPermissao> getFldPermissoes() {
		return fldPermissoes;
	}


	/**
	 * @param fldPermissoes the fldPermissoes to set
	 */
	public void setFldPermissoes(Set<UsuarioPermissao> fldPermissoes) {
		this.fldPermissoes = fldPermissoes;
	}


	/**
	 * @return the fldGrupos
	 */
	public Set<GrupoUsuario> getFldGrupos() {
		return fldGrupos;
	}


	/**
	 * @param fldGrupos the fldGrupos to set
	 */
	public void setFldGrupos(Set<GrupoUsuario> fldGrupos) {
		this.fldGrupos = fldGrupos;
	}


	public BindingListModelList<Entity> getListCategoriaUsuario(){
		return this.getFKEntityModel("fldCategoria");
	}


	@Override
	public Control<Usuario> getNewControl(MessagesControl pMessagesControl) {
		return null;
	}
	@Override
	public Control<Usuario> getControl(){
		if(this.usuarioControl==null){
			this.usuarioControl = (UsuarioControl) SpringFactory.getInstance().getBean("usuarioControl", UsuarioControl.class);
		}
		return this.usuarioControl;
	}
	
	/* metodod de permissão */
	
	
	/**
	 * Lista todas as permissões do removendo as que o usuário já possua
	 * 
	 * @return List<FunctionalityUseCase> Lista de funcionalidades.
	 */
	public BindingListModelSet<UsuarioPermissao> getUsuarioPermissaoList() {
		Set<UsuarioPermissao> funcList = new HashSet<UsuarioPermissao>(0);
		try {
			
			funcList.addAll(getUsuarioControl().getUsuarioPermissaoList());
		
		} catch (Throwable e) {
			e.printStackTrace();
		}
		usuarioPermissaoList.clear();
		usuarioPermissaoList.addAll(funcList);
		return usuarioPermissaoList;
	}

	
	/**
	 * Lista todas as funcionalidades cadastrados para a entidade do composer.
	 * 
	 * @return List<UsuarioPermissao> Lista de funcionalidades.
	 */
	public ListModel<UsuarioPermissao> getCasoDeUsoFuncionalidadeList() {
		BindingListModelSet<UsuarioPermissao> userPermList = new BindingListModelSet<UsuarioPermissao>(new HashSet<UsuarioPermissao>(),true);
		
		if (this.getSelectedEntity() != null && this.getSelectedEntity().getId()!=null) {
			userPermList = new BindingListModelSet<UsuarioPermissao>(this.selectedEntity.getPermissoes(),true);		
		}
		
		return userPermList;
	}
	
	/**
	 * Lista todas os Grupos de Usuários  do removendo as que o usuário já esteja
	 * 
	 * @return List<GrupoUsuario> Lista de funcionalidades.
	 */
	public BindingListModelSet<GrupoUsuario> getGrupoUsuarioList() {
		Set<GrupoUsuario> funcList = new HashSet<GrupoUsuario>(0);
		try {
			
			funcList.addAll(getUsuarioControl().getGrupoUsuarioList());
		
		} catch (Throwable e) {
			e.printStackTrace();
		}
		grupoUsuarioList.clear();
		grupoUsuarioList.addAll(funcList);
		return grupoUsuarioList;
	}

	
	/**
	 * Lista todas os grupos de usuario cadastrados para a entidade do composer.
	 * 
	 * @return List<GrupoUsuario> Lista de grupo de usuários.
	 */
	public ListModel<GrupoUsuario> getUsuarioGrupoUsuarioList() {
		BindingListModelSet<GrupoUsuario> listGrupos =  new BindingListModelSet<GrupoUsuario>(new HashSet<GrupoUsuario>(0),true);;
		if (this.getSelectedEntity() != null && this.getSelectedEntity().getId()!=null) {
			listGrupos = new BindingListModelSet<GrupoUsuario>(this.getSelectedEntity().getGrupos(),true);			
		}
		
		return listGrupos;
	}
	
	
	/**
	 * Lista todas os casos de uso cadastrados.
	 * 
	 * @return List<FunctionalityUseCase> Lista de funcionalidades.
	 */
	public ListModel<CasoDeUso> getCasoDeUsoList() {
		BindingListModelSet<CasoDeUso> result = null;
		
		result = new BindingListModelSet<CasoDeUso>(this.getUsuarioControl().getCasoDeUsoList(), true);
		
		return result;
	}
	
	public void setCasoDeUsoFuncionalidadeList(ListModel<UsuarioPermissao> chosenList) {
		Set<UsuarioPermissao> chosenItens = ((BindingListModelSet<UsuarioPermissao>) chosenList).getSelection();
		if(this.getSelectedEntity().getPermissoes()!=null){
			for (UsuarioPermissao fuc : chosenItens) {
				this.getSelectedEntity().getPermissoes().add(fuc);
			}
		}
	}
	
	
	public UsuarioControl getUsuarioControl(){
		return (UsuarioControl) this.getControl();
	}

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.zk.composer.ComposerController#setSelectedEntity(br.ueg.builderSoft.model.Entity)
	 */
	@Override
	public void setSelectedEntity(Usuario selectedEntity) {
		super.setSelectedEntity(selectedEntity);
		this.getUsuarioControl().setSelectedUsuario(selectedEntity);
	}

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.view.zk.composer.ComposerController#getSelectedEntity()
	 */
	@Override
	public Usuario getSelectedEntity() {		
		return this.getUsuarioControl().getSelectedUsuario();
	}

	public CasoDeUso getSelectedCasoDeUso() {
		return selectedCasoDeUso;
	}

	public void setSelectedCasoDeUso(CasoDeUso selectedCasoDeUso) {
		this.selectedCasoDeUso = selectedCasoDeUso;
		getUsuarioControl().setSelectedCasoDeUso(selectedCasoDeUso);
		this.binder.loadComponent(dualList);
	}
}
