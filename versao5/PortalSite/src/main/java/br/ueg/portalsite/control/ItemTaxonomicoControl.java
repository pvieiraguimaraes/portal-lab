package br.ueg.portalsite.control;

import java.math.BigDecimal;
import java.util.List;

import br.ueg.portalLab.model.Colecao;
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
	
	public Colecao getColectionName(Long idColection){
		String sql = "select * from colecao where id_colecao = '" + idColection + "'";
		List<?> listByNativeSQL = getListByNativeSQL(sql);
		Colecao colecao = new Colecao();;
		for (Object item : listByNativeSQL) {
			colecao.setNome((String) ((Object[])item)[1]);
		}
		return colecao;
	}
	
	public List<?> getItensOfColection(BigDecimal idColection, Integer pagina, Integer nPagina){
		pagina = pagina - 1;
//		SQL QUE DEVERÁ SER UTILIZADA QUANDO A QUANTIDADE DE ITENS REPRESENTATIVOS FOR SUFICIENTE
//		String sql = "select * from (select ROW_NUMBER() over(order by linha) as ln, path, nome_estacao, id, id_colecao, id_especime, reino, filo, " +
//				"classe, ordem, family, genery, eptespecify, especime_image, id_nigeo_ittax, id_mult,caminho, id_esta_mult, id_itta_mult, representativa, rank " +
//				"from detalhe_colecao_imagem where representativa = 1 and id_colecao = " + idColection + ")x where"
//						+ " ln between (" + pagina + "*" + nPagina + "+1) and (" + pagina + "*" + nPagina + "+" + nPagina + ")";
		String sql = "select * from (select ROW_NUMBER() over(order by linha) as ln, path, nome_estacao, id, id_colecao, id_especime, reino, filo, " +
				"classe, ordem, family, genery, eptespecify, especime_image, id_nigeo_ittax, id_mult,caminho, id_esta_mult, id_itta_mult, representativa, rank " +
				"from detalhe_colecao_imagem where id_colecao = " + idColection + ")x where"
						+ " ln between (" + pagina + "*" + nPagina + "+1) and (" + pagina + "*" + nPagina + "+" + nPagina + ")";

		return getListByNativeSQL(sql);
	}
	
	public List<?> getCountItensColection(BigDecimal idColection){
//		SQL QUE DEVERÁ SER UTILIZADA QUANDO A QUANTIDADE DE ITENS REPRESENTATIVOS FOR SUFICIENTE
//		String sql = "select COUNT(*) from detalhe_colecao_imagem where representativa = 1 and id_colecao = "+idColection;
		String sql = "select COUNT(*) from detalhe_colecao_imagem where id_colecao = "+idColection;
		return getListByNativeSQL(sql);
	}
}
