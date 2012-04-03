package br.ueg.builderSoft.view.zk.composer;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.AttributeView;

@SuppressWarnings("serial")
public abstract class TabelaBasicaComposerController<E extends Entity> extends TabelaComposerController<E> {

	@AttributeView(key = "descricao", isEntityValue = true, entityType = String.class, caption="tb_descricaoColumn")
	private String fldDescricao;
	
		
	public String getFldDescricao() {
		return fldDescricao;
	}


	public void setFldDescricao(String fldDescricao) {
		this.fldDescricao = fldDescricao;
	}	
}
