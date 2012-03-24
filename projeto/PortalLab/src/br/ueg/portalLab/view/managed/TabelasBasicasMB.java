package br.ueg.portalLab.view.managed;

import br.ueg.portalLab.control.Control;
import br.ueg.portalLab.model.EntityTabelaBasica;
import br.ueg.portalLab.util.annotation.AttributeView;
import br.ueg.portalLab.util.reflection.Reflection;

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
