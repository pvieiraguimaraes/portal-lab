package br.ueg.portalLab.control;


import java.util.ArrayList;

import org.springframework.stereotype.Service;

import br.ueg.builderSoft.util.reflection.Reflection;
import br.ueg.portalLab.model.EspecieImagem;


@Service
public class CadImagemControl extends CadMediaControl<EspecieImagem> {
	
	/*public CadImagemControl() {
		super();
		try {
			textClassName = Reflection.getParameterizedTypeClass(this.getClass(), 0).getName();
		} catch (Exception e) {
			System.out.println("ERRO: CadImageControlConstructor");
			e.printStackTrace();
		}		
	}*/
	@Override
	protected ArrayList<String> addFilterTextToActionFindByCriteria(EspecieImagem entity){
		String idEstacao = entity.getEstacao()==null?"":String.valueOf(entity.getEstacao().getId());
		ArrayList<String> list = new ArrayList<String>(0);
		if(!idEstacao.equals("")){
			list.add("e.estacao=".concat(idEstacao));
		}
		
		return list;
		
	} 
}
