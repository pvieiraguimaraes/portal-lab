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

@Component
@Scope("desktop")
public class CategoriaUsuarioComposer extends TabelaComposerController<Entity> {
	
	
	@AttributeView(key = "nome", isEntityValue = true, fieldType = String.class, isVisible=true, caption="categoriausuario_nomeColumn")
	private String fldNome;
	
	@AttributeView(key = "isCPFObrigatorio", isEntityValue = true, fieldType = Boolean.class, isVisible=true, caption="categoriausuario_cpfObrigatorioColumn", component=ComponentType.RADIO_BUTTON)
	private boolean fldIsCPFObrigatorio;
	
	@AttributeView(key = "observacoes", isEntityValue = true, fieldType = String.class, isVisible=true, caption="categoriausuario_observacoesColumn")
	private String fldObservacoes;
			

	/**
	 * 
	 */
	private static final long serialVersionUID = 6642681501922054728L;

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return CategoriaUsuario.class; 
	}

	
	public String getFldNome() {
		return fldNome;
	}


	public void setFldNome(String fldNome) {
		this.fldNome = fldNome;
	}


	public boolean isFldIsCPFObrigatorio() {
		return fldIsCPFObrigatorio;
	}
	
	// compatibilização coma a forma de execução do parse
	//TODO mudar no mapBean para chamar conforme o tipo no caso de boolean
	public boolean getFldIsCPFObrigatorio(){
		return this.isFldIsCPFObrigatorio();
	}


	public void setFldIsCPFObrigatorio(boolean fldIsCPFObrigatorio) {
		this.fldIsCPFObrigatorio = fldIsCPFObrigatorio;
	}


	public String getFldObservacoes() {
		return fldObservacoes;
	}


	public void setFldObservacoes(String fldObservacoes) {
		this.fldObservacoes = fldObservacoes;
	}

}
