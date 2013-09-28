package br.ueg.portalLab.control;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.zkoss.image.Image;

import br.ueg.builderSoft.control.SubControllerManager;
import br.ueg.portalLab.model.IntegranteEquipe;

@Service
public class CadIntegranteControl extends CadMediaControl<IntegranteEquipe<Image>> {

	@Override
	protected ArrayList<String> addFilterTextToActionFindByCriteria(IntegranteEquipe<Image> entity) {
		ArrayList<String> condicoes = new ArrayList<String>();
		String nome = entity.getNome();
		if(nome!=null && !nome.equals("")){
			condicoes.add("nome like '%".concat(nome).concat("%'"));			
		}
		return condicoes;
	}
	
	@SuppressWarnings({ "unchecked", "static-access" })
	@Override
	public boolean actionSave(SubControllerManager<IntegranteEquipe<Image>> subControllerManager) {
		
		IntegranteEquipe<Image> entity = (IntegranteEquipe<Image>) this.getMapFields().get("entity");
		entity.setFileName(entity.getNome()+"."+entity.getExtension(entity.getMedia().getName()));
		
		if(entity.getLinkLates()!=null && !entity.getLinkLates().substring(0, 7).equalsIgnoreCase("http://") && entity.getLinkLates().length()>0){
			entity.setLinkLates("http://"+entity.getLinkLates());
		}
		return super.actionSave(subControllerManager);
	}
}
