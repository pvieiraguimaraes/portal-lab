package br.ueg.portalLab.control;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.ueg.builderSoft.util.control.SubController;
import br.ueg.portalLab.model.FichaTecnica;
import br.ueg.portalLab.util.control.CadFichaTecnicaValidatorControl;
@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class CadFichaTecnicaControl extends SuperCadImageControl<FichaTecnica> {
	@Override
	public List<SubController> getListValidator() {
		List<SubController> validators = super.getListValidator();
	
		validators.add((SubController) new CadFichaTecnicaValidatorControl(this.getMessagesControl(),1));
		return validators;
	}
}
