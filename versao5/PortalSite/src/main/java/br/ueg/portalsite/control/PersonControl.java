package br.ueg.portalsite.control;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.ueg.portalLab.model.IntegranteEquipe;
import br.ueg.portalsite.SiteControl;

@SuppressWarnings("rawtypes")
public class PersonControl extends SiteControl<IntegranteEquipe> {

	@SuppressWarnings("unchecked")
	public List<IntegranteEquipe> getTeam() {
		IntegranteEquipe integranteEquipe = new IntegranteEquipe<>();
		List<IntegranteEquipe> list = getListEntity(integranteEquipe);
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				IntegranteEquipe p1 = (IntegranteEquipe) o1;
				IntegranteEquipe p2 = (IntegranteEquipe) o2;
				return p1.getNome().compareToIgnoreCase(p2.getNome());
			}
		});
		return list;
	}
}
