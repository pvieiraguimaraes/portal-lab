package test;

import java.util.List;

import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.model.ItemTaxonomico;
import br.ueg.portalLab.security.model.CasoDeUso;

public class BuscaFilhos {
	GenericDAO<ItemTaxonomico> itemTaxonomicoDAO = (GenericDAO<ItemTaxonomico>) SpringFactory
			.getInstance().getBean("genericDAO", GenericDAO.class);

	public String search(ItemTaxonomico itax, String retorno) {
		String qry = "from ItemTaxonomico i where i.pai ="
				+ itax.getId().toString() + " order by i.nome";
		
		List<ItemTaxonomico> findByHQL = itemTaxonomicoDAO.findByHQL(qry);
		retorno = itax.getId().toString();
		for (ItemTaxonomico itemTaxonomico : findByHQL) {
			retorno = retorno.concat(",").concat(this.search(itemTaxonomico, retorno));
		}

		return retorno;
	}

	public static void main(String[] args) {
		BuscaFilhos bf = new BuscaFilhos();
		ItemTaxonomico it2 = new ItemTaxonomico();
		it2.setId((long) 1336);
		String retorno = "";
		System.out.println(bf.search(it2, retorno));
	}
}
