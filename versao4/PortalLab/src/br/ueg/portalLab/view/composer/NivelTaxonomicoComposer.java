package br.ueg.portalLab.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zkplus.databind.BindingListModelList;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.view.zk.composer.TabelaComposerController;
import br.ueg.portalLab.model.NivelTaxonomico;

@org.springframework.stereotype.Component
@Scope("desktop")
public class NivelTaxonomicoComposer extends TabelaComposerController<NivelTaxonomico> {
	
	
	@AttributeView(key="pai", isEntityValue = true, fieldType = NivelTaxonomico.class, isVisible = true, caption ="niveltaxonomico_paiColumn")
	private NivelTaxonomico fldPai;


	@AttributeView(key = "nome", isEntityValue = true, fieldType = String.class, isVisible=true, caption="niveltaxonomico_nomeColumn")
	private String fldNome;
	
	@AttributeView(key = "obrigatorio", isEntityValue = true, fieldType = Boolean.class, isVisible=true, caption="niveltaxonomico_obrigatorioColumn")
	private Boolean fldObrigatorio=new Boolean(true);		
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 6642681501922054728L;
	

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return NivelTaxonomico.class; 
	}

	
	public NivelTaxonomico getFldPai() {
		return fldPai;
	}


	public void setFldPai(NivelTaxonomico fldPai) {
		this.fldPai = fldPai;
	}


	public String getFldNome() {
		return fldNome;
	}


	public void setFldNome(String fldNome) {
		this.fldNome = fldNome;
	}


	public Boolean getFldObrigatorio() {
		return fldObrigatorio;
	}


	public void setFldObrigatorio(Boolean fldObrigatorio) {
		this.fldObrigatorio = fldObrigatorio;
	}
	
	public BindingListModelList<Entity> getListPai(){
		return this.getFKEntityModel("fldPai");
	}


	@Override
	public Control<NivelTaxonomico> getNewControl(MessagesControl pMessagesControl) {
		return null;
	}

}
