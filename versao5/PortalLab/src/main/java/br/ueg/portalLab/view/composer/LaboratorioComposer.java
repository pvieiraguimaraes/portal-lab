package br.ueg.portalLab.view.composer;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.view.zk.composer.TabelaComposerController;
import br.ueg.portalLab.model.Laboratorio;

@Component
@Scope("desktop")
public class LaboratorioComposer extends TabelaComposerController<Entity> {
	
	
	@AttributeView(key = "nome", isEntityValue = true, fieldType = String.class, isVisible=true, caption="laboratorio_nomeColumn")
	private String fldNome;
	
	@AttributeView(key = "sigla", isEntityValue = true, fieldType = String.class, isVisible=true, caption="laboratorio_siglaColumn")
	private String fldSigla;
	
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


	public String getFldSigla() {
		return fldSigla;
	}


	public void setFldSigla(String fldSigla) {
		this.fldSigla = fldSigla;
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


	@Override
	public Control<Entity> getNewControl(MessagesControl pMessagesControl) {
		return null;
	}
}
