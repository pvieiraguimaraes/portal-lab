package br.ueg.portalLab.security.view.composer;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.view.zk.composer.TabelaComposerController;
import br.ueg.portalLab.security.model.Funcionalidade;


@Component
@Scope("desktop")
public class FuncionalidadeComposer extends TabelaComposerController<Funcionalidade> {

	private static final long serialVersionUID = 2708123019095565737L;

	@AttributeView(key = "nome", isEntityValue = true, fieldType = String.class, isVisible=true, caption="funcionalidade_nomeColumn")
	private String fldNome;
	
	@AttributeView(key = "descricao", isEntityValue = true, fieldType = String.class, isVisible=true, caption="funcionalidade_descricaoColumn")
	private String fldDescricao;
	
	@AttributeView(key = "status", isEntityValue = true, fieldType = Boolean.class, isVisible=true, caption="funcionalidade_statusColumn")
	private Boolean fldStatus;
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return Funcionalidade.class;
	}

	@Override
	public Control<Funcionalidade> getNewControl(MessagesControl pMessagesControl) {
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

}
