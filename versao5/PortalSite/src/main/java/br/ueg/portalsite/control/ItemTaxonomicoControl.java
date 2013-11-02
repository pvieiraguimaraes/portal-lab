package br.ueg.portalsite.control;

import java.math.BigDecimal;
import java.util.List;

import br.ueg.portalLab.model.ItemTaxonomico;
import br.ueg.portalsite.SiteControl;

public class ItemTaxonomicoControl extends SiteControl<ItemTaxonomico>{
	
	public List<?> getMastersItens(){
		String sql = "select * from reino_imagem"; //Pega a lista de todos os Itens Pais.. Reinos
		return getListByNativeSQL(sql);
	}
	
	public List<?> getSubItens(BigDecimal idParent){
		String sql = "select * from colecao_reino where id_reino = '" + idParent + "'";
		return getListByNativeSQL(sql);
	}
	
}
