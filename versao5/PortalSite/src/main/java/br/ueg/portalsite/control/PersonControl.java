package br.ueg.portalsite.control;

import java.util.List;

import br.ueg.portalLab.model.IntegranteEquipe;
import br.ueg.portalsite.SiteControl;

@SuppressWarnings("rawtypes")
public class PersonControl extends SiteControl<IntegranteEquipe> {

	public List<IntegranteEquipe> getTeam() {
		IntegranteEquipe integranteEquipe = new IntegranteEquipe<>();
		List<IntegranteEquipe> list = getListEntity(integranteEquipe);
		return list;
	}
}
