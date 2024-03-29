package br.ueg.portalLab.control;


import java.util.ArrayList;

import org.springframework.stereotype.Service;

import br.ueg.portalLab.model.EspecieImagem;


@SuppressWarnings("rawtypes")
@Service
public class CadImagemControl extends SuperCadImageControl<EspecieImagem> {
	
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
		ArrayList<String> list = super.addFilterTextToActionFindByCriteria(entity);
		String idEstacao = entity.getEstacao()==null?"":String.valueOf(entity.getEstacao().getId());
		Boolean representativo = entity.getRepresentativa();
		
		if(!idEstacao.equals("")){
			list.add("e.estacao=".concat(idEstacao));
		}
		if(representativo!=null){
			list.add("e.representativa="+representativo.toString());
		}

		
		return list;
		
	} 
}
