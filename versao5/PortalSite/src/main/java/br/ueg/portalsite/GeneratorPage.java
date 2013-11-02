package br.ueg.portalsite;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import biz.source_code.miniTemplator.MiniTemplator;
import br.ueg.builderSoft.util.Utils;
import br.ueg.portalLab.model.IntegranteEquipe;
import br.ueg.portalsite.control.ItemTaxonomicoControl;
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
		case "colecoes":
			templator = generateColections();
			break;
		default:
			templator = generateDefault();
			break;
		}
		return templator;
	}

	@SuppressWarnings("null")
	protected MiniTemplator generateColections() {
		MiniTemplator temp = generateDefault();
		ItemTaxonomicoControl control = new ItemTaxonomicoControl();
		List<?> list = control.getMastersItens();
		for (Object itemTaxonomico : list) {
			temp.setVariable("nameItem", (String) ((Object[])itemTaxonomico)[1]);
			temp.setVariable("imageItem", createPathImage((String) ((Object[])itemTaxonomico)[1], (String) ((Object[])itemTaxonomico)[4]));
			temp.setVariable("descriptionItem", (String) ((Object[])itemTaxonomico)[2] != null ? (String) ((Object[])itemTaxonomico)[2] : (String) ((Object[])itemTaxonomico)[1]);
			BigDecimal idParent = (BigDecimal) ((Object[])itemTaxonomico)[0];
			List<?> subItens = control.getSubItens(idParent);
			if(subItens != null || !subItens.isEmpty()){
				for (Object object : subItens) {
					temp.setVariable("subNameItem", (String) ((Object[])object)[1]);
					temp.addBlock("subColection");
				}
			}
			temp.addBlock("colection");
		}
		return temp;
	}

	@SuppressWarnings("rawtypes")
	protected MiniTemplator generateTeam() {
		MiniTemplator temp = generateDefault();
		PersonControl control = new PersonControl();
		List<IntegranteEquipe> list = control.getTeam();
		for (IntegranteEquipe integrante : list) {
			temp.setVariable("curriculum", integrante.getLinkLates());
			temp.setVariable("picture", pathMedia + folderTeam + Utils.translateName("thumb_" + integrante.getFileName()));
			temp.setVariable("description", integrante.getDescricao());
			temp.addBlock("person");
		}
		return temp;
	}

	protected MiniTemplator generateDefault() {
		return GeneratorPage.initTemplator(path);
	}
	
	protected String createPathImage(String nameItem, String nameImage){
		return pathMedia + nameItem + "/image" + "/" + nameImage;
	}

}
