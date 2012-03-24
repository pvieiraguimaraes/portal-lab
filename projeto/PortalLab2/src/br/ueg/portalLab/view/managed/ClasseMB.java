package br.ueg.portalLab.view.managed;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import br.ueg.builderSoft.view.managed.MB;
import br.ueg.portalLab.model.Classe;
import br.ueg.portalLab.model.Filo;


@ManagedBean
@SessionScoped
public class ClasseMB extends MB<Classe> {
	
	//TODO criar hashMap com key da entidade, e value da lista
	private List<Filo> filos;
	//private Control<Filo> localControl;	
	
	//Criar entidades não persistidas que será o que vai aparecer na visão
	//e depois essas entidades transformarão nas entidades que serão persistidas.
	
	public ClasseMB() {
		super();
	}

	@Override
	protected Classe initializeEntity() {
		return new Classe();
	}

	public List<Filo> getFilos() {
		return filos;
	}

	public void setFilos(List<Filo> filos) {
		this.filos = filos;
	}
	
	
	public void listFilo (ActionEvent event) {
		//filos = localControl.getListAll(new Filo());
	}
	
	//pesquisar de como fazer comboBox dinâmico
	/*public SelectItemGroup getComboBox(String id) {
		SelectItemGroup se = new SelectItemGroup();
		se.set
	}*/
	

}
