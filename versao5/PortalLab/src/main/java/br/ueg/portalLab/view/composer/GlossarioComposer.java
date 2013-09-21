package br.ueg.portalLab.view.composer;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.view.zk.composer.TabelaComposerController;
import br.ueg.portalLab.model.Glossario;

@SuppressWarnings("serial")
@Component
@Scope("desktop")
public class GlossarioComposer extends TabelaComposerController<Glossario> {

	@AttributeView(key = "nome", isEntityValue = true, fieldType = String.class, isVisible=true, caption="glossario_nomeColumn")
	private String fldNome;
	
	@AttributeView(key = "descricao", isEntityValue = true, fieldType = String.class, isVisible=true, caption="glossario_descColumn")
	private String fldDescricao;
	
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
	
	@Override
	public Control<Glossario> getNewControl(MessagesControl pMessagesControl) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return Glossario.class;
	}

}
