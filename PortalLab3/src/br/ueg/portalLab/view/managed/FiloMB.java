package br.ueg.portalLab.view.managed;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItemGroup;

import br.ueg.builderSoft.view.managed.MB;
import br.ueg.builderSoft.control.Control;
import br.ueg.portalLab.model.Filo;
import br.ueg.portalLab.model.Reino;

@ManagedBean
@SessionScoped
public class FiloMB extends MB<Filo> {
	
	//TODO criar hashMap com key da entidade, e value da lista
	private List<Reino> reinos;
	//private Control<Reino> localControl;	
	
	//Criar entidades n�o persistidas que ser� o que vai aparecer na vis�o
	//e depois essas entidades transformar�o nas entidades que ser�o persistidas.
	
	public FiloMB() {
		super();
	}

	public List<Reino> getReinos() {
		return reinos;
	}

	public void setReinos(List<Reino> reinos) {
		this.reinos = reinos;
	}
	
	public void listReino (ActionEvent event) {
		//reinos = localControl.getListAll(new Reino());
	}

	public Class getEntityClass() {
		return Filo.class;
	}
	
	//pesquisar de como fazer comboBox din�mico
	/*public SelectItemGroup getComboBox(String id) {
		SelectItemGroup se = new SelectItemGroup();
		se.set
	}*/
	

}
