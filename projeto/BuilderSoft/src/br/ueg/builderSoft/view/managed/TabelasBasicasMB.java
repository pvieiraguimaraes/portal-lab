package br.ueg.builderSoft.view.managed;

import br.ueg.builderSoft.util.annotation.AttributeView;

public abstract class TabelasBasicasMB extends TabelaMB {

	@AttributeView(key = "descricao", isEntityValue = true, entityType = String.class, caption="tb_descricaoColumn")
	private String fldDescricao;
	
		
	public String getFldDescricao() {
		return fldDescricao;
	}


	public void setFldDescricao(String fldDescricao) {
		this.fldDescricao = fldDescricao;
	}	

}
