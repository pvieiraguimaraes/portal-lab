package br.ueg.portalLab.view.composer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.springframework.context.annotation.Scope;
import org.zkoss.zkplus.databind.BindingListModelList;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.constant.ComponentType;
import br.ueg.builderSoft.view.zk.composer.TabelaComposerController;
import br.ueg.portalLab.control.UsuarioControl;
import br.ueg.portalLab.model.CategoriaUsuario;
import br.ueg.portalLab.model.Usuario;

@org.springframework.stereotype.Component
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
			
	public UsuarioComposer(){
		super();
		this.control.setControl(new UsuarioControl<Usuario>(this.control));
	}
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
	public BindingListModelList<Entity> getListCategoriaUsuario(){
		return this.getFKEntityModel("fldCategoria");
	}
}
