package br.ueg.portalLab.view.composer;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Window;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.constant.ComponentType;
import br.ueg.builderSoft.view.zk.composer.ComposerController;
import br.ueg.builderSoft.view.zk.composer.TabelaComposerController;
import br.ueg.portalLab.control.TesteControl;
import br.ueg.portalLab.control.UsuarioControl;
import br.ueg.portalLab.model.CategoriaUsuario;
import br.ueg.portalLab.model.Reino;
import br.ueg.portalLab.model.Usuario;

@Component
@Scope("desktop")
public class UsuarioComposer extends TabelaComposerController<Usuario> {
	
	



	@AttributeView(key = "nome", isEntityValue = true, fieldType = String.class, isVisible=true, caption="usuario_nomeColumn")
	private String fldNome;
	
	@AttributeView(key = "CPF", isEntityValue = true, fieldType = String.class, isVisible=true, caption="usuario_CPFColumn")
	private String fldCPF;
	
	@AttributeView(key = "email", isEntityValue = true, fieldType = String.class, isVisible=true, caption="usuario_emailColumn")
	private String fldEmail;
	
	@AttributeView(key = "telefone1", isEntityValue = true, fieldType = String.class, isVisible=true, caption="usuario_telefone1Column")
	private String fldTelefone1;
	
	@AttributeView(key = "telefone2", isEntityValue = true, fieldType = String.class, isVisible=true, caption="usuario_telefone2Column")
	private String fldTelefone2;
	
	@AttributeView(key = "login", isEntityValue = true, fieldType = String.class, isVisible=true, caption="usuario_loginColumn")
	private String fldLogin;
	
	@AttributeView(key = "senha", isEntityValue = true, fieldType = String.class, isVisible=true, caption="usuario_senhaColumn", component = ComponentType.INPUT_SECRET)
	private String fldSenha;
	
	@AttributeView(key = "categoria", isEntityValue = true, fieldType = CategoriaUsuario.class, isVisible=true, caption="usuario_categoriaColumn")
	private CategoriaUsuario fldCategoria;
			

	private UsuarioControl<Usuario> usuarioControl ;
	/**
	 * 
	 */
	private static final long serialVersionUID = 6642681501922054728L;
	
	public UsuarioComposer() {
		super();
		usuarioControl = new UsuarioControl<Usuario>(this.control);
		this.control.setControl(usuarioControl);
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ListModel<CategoriaUsuario> getListCategoriaUsuario(){
		SimpleListModel model = new SimpleListModel(this.usuarioControl.getListCategoriaUsuario().toArray());
		return model;
	}

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


	public String getFldCPF() {
		return fldCPF;
	}


	public void setFldCPF(String fldCPF) {
		this.fldCPF = fldCPF;
	}


	public String getFldEmail() {
		return fldEmail;
	}


	public void setFldEmail(String fldEmail) {
		this.fldEmail = fldEmail;
	}


	public String getFldTelefone1() {
		return fldTelefone1;
	}


	public void setFldTelefone1(String fldTelefone1) {
		this.fldTelefone1 = fldTelefone1;
	}


	public String getFldTelefone2() {
		return fldTelefone2;
	}


	public void setFldTelefone2(String fldTelefone2) {
		this.fldTelefone2 = fldTelefone2;
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


	public CategoriaUsuario getFldCategoria() {
		return fldCategoria;
	}


	public void setFldCategoria(CategoriaUsuario fldCategoria) {
		this.fldCategoria = fldCategoria;
	}
}
