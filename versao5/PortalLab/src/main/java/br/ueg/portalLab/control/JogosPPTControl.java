package br.ueg.portalLab.control;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import br.ueg.portalLab.model.JogoPPT;

@Service
public class JogosPPTControl extends CadMediaControl<JogoPPT> {

	@Override
	protected ArrayList<String> addFilterTextToActionFindByCriteria(JogoPPT entity) {
		ArrayList<String> condicoes = new ArrayList<String>();
		String nome = entity.getNome();
		if(nome!=null && !nome.equals("")){
			condicoes.add("nome like '%".concat(nome).concat("%'"));			
		}
		return condicoes;
	}
}
