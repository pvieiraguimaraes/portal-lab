package br.ueg.portalLab.view.composer;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.image.Image;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Tree;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.control.CadImagemControl;
import br.ueg.portalLab.control.SuperCadImageControl;
import br.ueg.portalLab.model.EspecieImagem;
import br.ueg.portalLab.model.EspecieMultimidia;
import br.ueg.portalLab.model.Estacao;
import br.ueg.portalLab.model.Laboratorio;
import br.ueg.portalLab.model.SuperEspecieImagem;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class CadImagemComposer extends SuperCadImagemComposer {

	private Estacao fldEstacaoBusca;
	
	@AttributeView(key = "estacao", isEntityValue = true, fieldType = Estacao.class, isVisible = true, caption = "especieImagem_estacao_Column", isSearchField=true)
	private Estacao fldEstacao;
	
	@AttributeView(key = "representativa", isEntityValue = true, fieldType = Boolean.class, isVisible = true, caption = "especieImagem_representativa_Column", isSearchField=true)
	private Boolean fldRepresentativa;
	
	@AttributeView(key = "representativaBusca", isEntityValue = false, fieldType = Boolean.class, isVisible = true, caption = "especieImagem_representativa_Column", isSearchField=true)
	private Boolean fldRepresentativaBusca;
	
	/**
	 * @return the fldEstacao
	 */
	public Estacao getFldEstacao() {
		return fldEstacao;
	}

	/**
	 * @return the fldEstacaoForm
	 */
	public Estacao getFldEstacaoBusca() {
		return fldEstacaoBusca;
	}

	/**
	 * @param fldEstacaoBusca the fldEstacaoForm to set
	 */
	public void setFldEstacaoBusca(Estacao fldEstacaoBusca) {
		this.fldEstacaoBusca = fldEstacaoBusca;
	}

	/**
	 * @param fldEstacao the fldEstacao to set
	 */
	public void setFldEstacao(Estacao fldEstacao) {
		this.fldEstacao = fldEstacao;
	}

	public Boolean getFldRepresentativa() {
		return fldRepresentativa;
	}

	public void setFldRepresentativa(Boolean fldRepresentativa) {
		this.fldRepresentativa = fldRepresentativa;
	}

	public void selectEstacaoBusca(){
		Combobox CBEstacao = (Combobox)this.crudCadMedia.getFellow("cmbEstacaoBusca");
		Estacao estacaoField;
		if(CBEstacao.getSelectedItem()!=null){
			estacaoField = (Estacao)CBEstacao.getSelectedItem().getValue();
		}else{
			estacaoField = null;
		}
		this.setFldEstacao(estacaoField);	
	}
	public BindingListModelList<Entity> getEstacaoList() {
		return this.getFKEntityModel("fldEstacao");
	}

	public Boolean getFldRepresentativaBusca() {
		return fldRepresentativaBusca;
	}

	public void setFldRepresentativaBusca(Boolean fldRepresentativaBusca) {
		this.fldRepresentativaBusca = fldRepresentativaBusca;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public SuperEspecieImagem getGenericTypeImage() {
		return new EspecieImagem();
	}
	@Override
	public Class getEntityClass() {
		return EspecieImagem.class;
	}
	
	@Override
	public Control<EspecieImagem<Image>> getControl() {
		if (this.cadImagemControl == null) {
			this.cadImagemControl = (SuperCadImageControl) SpringFactory
					.getInstance().getBean("cadImagemControl",CadImagemControl.class);
		}
		return this.cadImagemControl;
	}
	
	public boolean searchEntity() {
		
		Checkbox represen = (Checkbox)this.crudCadMedia.getFellow("representatividadeBusca");
		this.setFldRepresentativa(represen.isChecked());
		
		boolean retorno = super.searchEntity();
		return retorno;		
	}
}
