package br.ueg.portalLab.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zkplus.databind.BindingListModelList;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.view.zk.composer.TabelaComposerController;
import br.ueg.portalLab.model.GrupoEnderecoFisico;
import br.ueg.portalLab.model.Laboratorio;
import br.ueg.portalLab.model.Usuario;

@org.springframework.stereotype.Component
@Scope("desktop")
public class GrupoEnderecoFisicoComposer extends TabelaComposerController<Usuario> {
	

	private static final long serialVersionUID = 2603898097401656666L;


	@AttributeView(key = "nome", isEntityValue = true, fieldType = String.class, isVisible=true, caption="grupoenderecofisico_nomeColumn")
	private String fldNome;
		
	
	@AttributeView(key = "laboratorio", isEntityValue = true, fieldType = Laboratorio.class, isVisible=true, caption="grupoenderecofisico_LaboratorioColumn")
	private Laboratorio fldLaboratorio;
	

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return GrupoEnderecoFisico.class; 
	}

	
	public String getFldNome() {
		return fldNome;
	}


	public void setFldNome(String fldNome) {
		this.fldNome = fldNome;
	}


	public Laboratorio getFldLaboratorio() {
		return fldLaboratorio;
	}


	public void setFldLaboratorio(Laboratorio fldLaboratorio) {
		this.fldLaboratorio = fldLaboratorio;
	}

	
	public BindingListModelList<Entity> getListLaboratorio(){
		return this.getFKEntityModel("fldLaboratorio");
	}


	@Override
	public Control<Usuario> getNewControl(MessagesControl pMessagesControl) {
		return null;
	}

}
