package br.ueg.portalsite.control;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.ueg.portalLab.model.jogo.cruzada.CrossWord;
import br.ueg.portalsite.SiteControl;

/**
 * Controle que acessa o controlador do jogos do tipo cruzadinha
 * 
 * @author pedro
 * 
 */
public class CrossWordControl extends SiteControl<CrossWord> {

	/**
	 * Busca todas as cruzadinhas do sistema
	 * 
	 * @return retorna uma lista com as cruzadinhas
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<?> getCrossWords() {
//		CrossWord crossWord = new CrossWord();
//		List<CrossWord> list = getListEntity(crossWord);
//		Collections.sort(list, new Comparator() {
//			public int compare(Object o1, Object o2) {
//				CrossWord p1 = (CrossWord) o1;
//				CrossWord p2 = (CrossWord) o2;
//				return p1.getName().compareToIgnoreCase(p2.getName());
//			}
//		});
//		return list;
		String sql = "select * from cruzadinha";
		return getListByNativeSQL(sql);
	}

}
