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
	
	//Criar entidades nao persistidas que sera o que vai aparecer na visao
	//e depois essas entidades transformarao nas entidades que serao persistidas.
	
	public ClasseMB() {
		super();
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

	public Class getEntityClass() {
		return Classe.class;
	}
	
	//pesquisar de como fazer comboBox dinamico
	/*public SelectItemGroup getComboBox(String id) {
		SelectItemGroup se = new SelectItemGroup();
		se.set
	}*/
	

}
