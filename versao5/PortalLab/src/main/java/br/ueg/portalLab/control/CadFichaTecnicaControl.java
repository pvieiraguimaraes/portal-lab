package br.ueg.portalLab.control;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.image.Image;

import br.ueg.portalLab.model.FichaTecnica;
@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class CadFichaTecnicaControl extends SuperCadImageControl<FichaTecnica<Image>> {
	
}
