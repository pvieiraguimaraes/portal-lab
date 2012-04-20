package br.ueg.builderSoft.security.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.metainfo.Annotation;
import org.zkoss.zk.ui.sys.ComponentCtrl;
import org.zkoss.zul.Button;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("desktop")
public class ApplySecurityVisibilityComposer  extends BaseComposer{

	private String casoDeUso = null;


	/**
	 * Método recursivo que configura a visibilidade do menu do usuário logado. O processamento olha diretamente para o
	 * ID do componente na tela, ou seja, os itens a serem analisados devem possuir em seu ID o valor da sua permissão.
	 * Caso o item não tenha ID ou seu ID esteja em branco, ele será renderizado para todos os usuários do sistema.
	 * 
	 * @param comp
	 *            Componente menu a ser analizado as permissões.
	 * @return 0 caso o agrupamento de itens de menu não tenha nenhum item a ser visualizado e maior que 0 caso o
	 *         agrupamento possua ao menos um item a ser renderizado.
	 */
	private int setCompVisibility(Component comp) {
		int access = 0;
		if (comp instanceof Button) {
			if (isCompVisible(comp)) {
				comp.setVisible(Boolean.TRUE);
				access += 1;
			} else {
				comp.setVisible(Boolean.FALSE);
			}
		} else {
			for (Component item : comp.getChildren()) {
				access += setCompVisibility(item);
			}
			if (!(comp instanceof Menupopup)) {
				comp.setVisible(access > 0);
			}
		}
		return access;
	}

	/**
	 * Analisa as permissões dos itens do menu, renderizando apenas os permitidos para o perfil de usuário logado ou
	 * aqueles que não possuirem ID (id nulo ou vazio).
	 * 
	 * @param item Menuitem
	 * @return true para permitido e false para bloqueado.
	 */
	private Boolean isCompVisible(Component item) {
		if (item.getId() == null || item.getId().isEmpty())
			return Boolean.TRUE;
				
		    if (this.getCasoDeUso() != null) {     
		      return this.isFunctionalityGranted(this.getCasoDeUso(),item.getId());
		    }else{
		    	return this.isUseCaseGranted(item.getId());
		    }
		    
		
	}
	public void confCasoDeUso(Component comp){
		 Annotation annot = ((ComponentCtrl)comp).getAnnotation(null, "casoDeUso");		    
	    if (annot != null) {
	    	this.setCasoDeUso(annot.getAttribute("value"));
	    }
	}

	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);		
		confCasoDeUso(comp);
		setCompVisibility(comp);
	}

	/**
	 * @return the casoDeUso
	 */
	public String getCasoDeUso() {
		return casoDeUso;
	}

	/**
	 * @param casoDeUso the casoDeUso to set
	 */
	public void setCasoDeUso(String casoDeUso) {
		this.casoDeUso = casoDeUso;
	}

}
