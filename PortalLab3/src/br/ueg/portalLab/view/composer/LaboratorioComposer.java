package br.ueg.portalLab.view.composer;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.constant.ComponentType;
import br.ueg.builderSoft.view.zk.composer.ComposerController;
import br.ueg.builderSoft.view.zk.composer.TabelaComposerController;
import br.ueg.portalLab.model.CategoriaUsuario;
import br.ueg.portalLab.model.Laboratorio;

@Component
@Scope("desktop")
public class LaboratorioComposer extends TabelaComposerController<Entity> {
	
	
	@AttributeView(key = "nome", isEntityValue = true, fieldType = String.class, isVisible=true, caption="laboratorio_nomeColumn")
	private String fldNome;
	
	@AttributeView(key = "responsavel", isEntityValue = true, fieldType = String.class, isVisible=true, caption="laboratorio_responsavelColumn")
	private String fldResponsavel;
	
	@AttributeView(key = "telefone", isEntityValue = true, fieldType = String.class, isVisible=true, caption="laboratorio_telefoneColumn")
	private String fldTelefone;
			

	/**
	 * 
	 */
	private static final long serialVersionUID = 6642681501922054728L;

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return Laboratorio.class; 
	}

	
	public String getFldNome() {
		return fldNome;
	}


	public void setFldNome(String fldNome) {
		this.fldNome = fldNome;
	}


	public String getFldResponsavel() {
		return fldResponsavel;
	}


	public void setFldResponsavel(String fldResponsavel) {
		this.fldResponsavel = fldResponsavel;
	}


	public String getFldTelefone() {
		return fldTelefone;
	}


	public void setFldTelefone(String fldTelefone) {
		this.fldTelefone = fldTelefone;
	}



}
