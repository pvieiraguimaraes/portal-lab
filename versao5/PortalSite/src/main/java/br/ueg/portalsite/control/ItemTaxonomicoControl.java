package br.ueg.portalsite.control;

import java.math.BigDecimal;
import java.util.List;

import br.ueg.builderSoft.model.Entity;
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
		Colecao colecao = new Colecao();
		colecao.setId(idColection);
		genericDAO.getSession().close();
		List<Entity> result = genericDAO.getList(colecao, null);
		return (Colecao) result.get(0);
	}
	
	public List<?> getItensOfColection(BigDecimal idColection){
		//TODO Substituir esta query gigante por uma VIEW no BD, ver como pegar a imagem.
		String sql = "SELECT TOP 10 itr.nome_ittax AS reino, itfl.nome_ittax AS filo, itc.nome_ittax AS classe, ito.nome_ittax AS ordem, itf.nome_ittax AS family, itg.nome_ittax AS genery " + 
		"FROM especime as e " +
			"INNER JOIN item_taxonomico as itf " +
			"ON itf.id_ittax = e.id_familia " +
			"INNER JOIN item_taxonomico as itg " +
			"ON itg.id_ittax = e.id_genero " +
			"INNER JOIN item_taxonomico as itfl " +
			"ON itfl.id_ittax = e.id_filo " +
			"INNER JOIN item_taxonomico as itc " +
			"ON itc.id_ittax = e.id_classe " +
			"INNER JOIN item_taxonomico as ito " +
			"ON ito.id_ittax = e.id_ordem " +
			"INNER JOIN item_taxonomico as itr " +
			"ON itr.id_ittax = e.id_reino " +

		"WHERE e.id_colecao = '" + idColection + "'";

		return getListByNativeSQL(sql);
	}
}
