package br.ueg.portalLab.view.composer;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.image.Image;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.control.CadFichaTecnicaControl;
import br.ueg.portalLab.control.CadImagemControl;
import br.ueg.portalLab.control.CadMediaControl;
import br.ueg.portalLab.model.EspecieImagem;
import br.ueg.portalLab.model.FichaTecnica;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class CadFichaTecnicaComposer extends SuperCadImagemComposer {

	@Override
	public FichaTecnica<Image> getGenericTypeImage() {
		return new FichaTecnica<Image>();
	}
	protected String getPathToFormZulFile() {
		return "/pages/cadfichatecnica" ;//+ this.getEntityClass().getSimpleName().toLowerCase();
	}
	@Override
	public Class getEntityClass() {
		return FichaTecnica.class;
	}
	
	@Override
	public Control<FichaTecnica<Image>> getControl() {
		if (this.cadImagemControl == null) {
			this.cadImagemControl = (CadMediaControl) SpringFactory
					.getInstance().getBean("cadFichaTecnicaControl",CadFichaTecnicaControl.class);
		}
		return this.cadImagemControl;
	}
}
