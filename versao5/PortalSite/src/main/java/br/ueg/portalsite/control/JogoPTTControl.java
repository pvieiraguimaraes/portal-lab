package br.ueg.portalsite.control;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.ueg.portalLab.model.JogoPPT;
import br.ueg.portalLab.model.jogo.cruzada.CrossWord;
import br.ueg.portalsite.SiteControl;

/**
 * Controle de acesso aos jogos criados em Powerpoint e cadastrados no Portal
 * 
 * @author pedro
 * 
 */
public class JogoPTTControl extends SiteControl<JogoPPT> {

	/**
	 * Busca todos os jogos em ordem alfabética
	 * 
	 * @return lista de todos os jogos cadastrados
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<?> getJogosPTT() {
//		JogoPPT jogo = new JogoPPT();
//		List<JogoPPT> list = getListEntity(jogo);
//		Collections.sort(list, new Comparator() {
//			public int compare(Object o1, Object o2) {
//				CrossWord p1 = (CrossWord) o1;
//				CrossWord p2 = (CrossWord) o2;
//				return p1.getName().compareToIgnoreCase(p2.getName());
//			}
//		});
//		return list;
		String sql = "select * from jogos_ppt";
		return getListByNativeSQL(sql);
	}

}
