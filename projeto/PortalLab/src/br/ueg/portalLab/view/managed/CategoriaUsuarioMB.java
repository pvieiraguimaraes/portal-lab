package br.ueg.portalLab.view.managed;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.ueg.portalLab.model.CategoriaUsuario;
import br.ueg.portalLab.util.annotation.AttributeView;
import br.ueg.portalLab.util.constant.ComponentType;


@ManagedBean
@SessionScoped
public class CategoriaUsuarioMB extends TabelaMB {
	
	@AttributeView(key = "nome", isEntityValue = true, entityType = String.class, isVisible=true, caption="categoriausuario_nomeColumn")
	private String fldNome;
	
	@AttributeView(key = "isCPFObrigatorio", isEntityValue = true, entityType = Boolean.class, isVisible=true, caption="categoriausuario_cpfObrigatorioColumn", component=ComponentType.RADIO_BUTTON)
	private boolean fldIsCPFObrigatorio;
	
	@AttributeView(key = "observacoes", isEntityValue = true, entityType = String.class, isVisible=true, caption="categoriausuario_observacoesColumn")
	private String fldObservacoes;
	

	public CategoriaUsuarioMB(){
		super();
	}
	
	public String getFldNome() {
		return fldNome;
	}


	public void setFldNome(String fldNome) {
		this.fldNome = fldNome;
	}


	public boolean isFldIsCPFObrigatorio() {
		return fldIsCPFObrigatorio;
	}
	
	// compatibilização coma a forma de execução do parse
	//TODO mudar no mapBean para chamar conforme o tipo no caso de boolean
	public boolean getFldIsCPFObrigatorio(){
		return this.isFldIsCPFObrigatorio();
	}


	public void setFldIsCPFObrigatorio(boolean fldIsCPFObrigatorio) {
		this.fldIsCPFObrigatorio = fldIsCPFObrigatorio;
	}


	public String getFldObservacoes() {
		return fldObservacoes;
	}


	public void setFldObservacoes(String fldObservacoes) {
		this.fldObservacoes = fldObservacoes;
	}


	@SuppressWarnings("rawtypes")
	@Override
	public Class getTabelaBasica() {
		return CategoriaUsuario.class; 
	}

}
