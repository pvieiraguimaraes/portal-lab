package br.ueg.portalLab.view.composer;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zkplus.databind.BindingListModelList;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.constant.ComponentType;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.view.zk.composer.TabelaComposerController;
import br.ueg.portalLab.control.UsuarioControl;
import br.ueg.portalLab.model.CategoriaUsuario;
import br.ueg.portalLab.model.Colecao;
import br.ueg.portalLab.model.Coletor;
import br.ueg.portalLab.model.Laboratorio;
import br.ueg.portalLab.model.Usuario;

@Component
@Scope("desktop")
public class ColecaoComposer extends TabelaComposerController<Colecao> {
	
	
	@AttributeView(key = "nome", isEntityValue = true, fieldType = String.class, isVisible=true, caption="colecao_nomeColumn")
	private String fldNome;
	
	@AttributeView(key = "sigla", isEntityValue = true, fieldType = String.class, isVisible=true, caption="colecao_siglaColumn", component=ComponentType.INPUT_TEXT)
	private String fldSigla;
	
	@AttributeView(key = "laboratorio", isEntityValue = true, fieldType = Laboratorio.class, isVisible=true, caption="colecao_laboratorioColumn")
	private Laboratorio fldLaboratorio;
			
	@AttributeView(key = "descricao", isEntityValue = true, fieldType = String.class, isVisible=true, caption="colecao_siglaColumn", component=ComponentType.INPUT_TEXT_AREA)
	private String fldDescricao;

	/**
	 * 
	 */
	private static final long serialVersionUID = 6642681501922054728L;

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return Colecao.class; 
	}

	
	public String getFldNome() {
		return fldNome;
	}


	public void setFldNome(String fldNome) {
		this.fldNome = fldNome;
	}


	public String getFldSigla() {
		return fldSigla;
	}


	public void setFldSigla(String fldSigla) {
		this.fldSigla = fldSigla;
	}


	public Laboratorio getFldLaboratorio() {
		return fldLaboratorio;
	}


	public void setFldLaboratorio(Laboratorio fldLaboratorio) {
		this.fldLaboratorio = fldLaboratorio;
	}


	@Override
	public Control<Colecao> getNewControl(MessagesControl pMessagesControl) {
		return null;
	}
	
	
	public BindingListModelList<Entity> getLaboratorioList() {
		return this.getFKEntityModel("fldLaboratorio");
	}


	public String getFldDescricao() {
		return fldDescricao;
	}


	public void setFldDescricao(String fldDescricao) {
		this.fldDescricao = fldDescricao;
	}

}
