package br.ueg.portalsite;

import java.io.IOException;
import java.util.List;

import biz.source_code.miniTemplator.MiniTemplator;
import br.ueg.builderSoft.util.Utils;
import br.ueg.portalLab.model.IntegranteEquipe;
import br.ueg.portalsite.control.PersonControl;

public class GeneratorPage {

	protected MiniTemplator templator;

	protected String page;
	protected String path;
	protected String pathMedia;
	
	protected String folderTeam = "integrante_equipe/";

	public GeneratorPage() {
	}

	public GeneratorPage(String page, String path, MiniTemplator templator) {
		this.page = page;
		this.path = path;
		this.templator = templator;
	}

	public GeneratorPage(String page, String path, MiniTemplator templator, String pathMedia) {
		this.page = page;
		this.path = path;
		this.templator = templator;
		this.pathMedia = pathMedia;
	}

	public static MiniTemplator initTemplator(String file) {
		MiniTemplator t = null;
		try {
			t = new MiniTemplator(file);
		} catch (IOException e) {
			System.err.println("Erro de sintaxe no template");
			System.err.println(e.getStackTrace());
			e.printStackTrace();
		}
		return t;
	}

	public MiniTemplator generatePage() {
		switch (page) {
		case "equipe":
			templator = generateTeam();
			break;
		default:
			templator = generateDefault();
			break;
		}
		return templator;
	}

	@SuppressWarnings("rawtypes")
	protected MiniTemplator generateTeam() {
		MiniTemplator temp = generateDefault();
		PersonControl control = new PersonControl();
		List<IntegranteEquipe> list = control.getTeam();
		for (IntegranteEquipe integrante : list) {
			temp.setVariable("curriculum", integrante.getLinkLates());
			temp.setVariable("picture", pathMedia + folderTeam + Utils.translateName(integrante.getFileName()));
			temp.setVariable("description", integrante.getDescricao());
			temp.addBlock("person");
		}
		return temp;
	}

	protected MiniTemplator generateDefault() {
		return GeneratorPage.initTemplator(path);
	}

}
