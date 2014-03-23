package br.ueg.portalsite;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import biz.source_code.miniTemplator.MiniTemplator;
import br.ueg.builderSoft.util.Utils;
import br.ueg.portalLab.model.Colecao;
import br.ueg.portalLab.model.IntegranteEquipe;
import br.ueg.portalsite.control.CrossWordControl;
import br.ueg.portalsite.control.ItemTaxonomicoControl;
import br.ueg.portalsite.control.JogoPTTControl;
import br.ueg.portalsite.control.PersonControl;

public class GeneratorPage {

	protected MiniTemplator templator;

	protected String page;
	protected String path;
	protected String pathMedia;

	protected HashMap<String, Object> parameters;

	protected String folderTeam = "integrante_equipe/";
	
	private ItemTaxonomicoControl itemTaxonomicoControl;

	private PersonControl personControl;

	private CrossWordControl crossWordControl;
	
	private JogoPTTControl jogoPTTControl;
	
	public JogoPTTControl getJogoPTTControl() {
		if(jogoPTTControl == null)
			jogoPTTControl = new JogoPTTControl();
		return jogoPTTControl;
	}

	public void setJogoPTTControl(JogoPTTControl jogoPTTControl) {
		this.jogoPTTControl = jogoPTTControl;
	}

	public CrossWordControl getCrossWordControl() {
		if (crossWordControl == null)
			crossWordControl = new CrossWordControl();
		return crossWordControl;
	}

	public void setCrossWordControl(CrossWordControl crossWordControl) {
		this.crossWordControl = crossWordControl;
	}

	public PersonControl getPersonControl() {
		if (personControl == null)
			personControl = new PersonControl();
		return personControl;
	}

	public void setPersonControl(PersonControl personControl) {
		this.personControl = personControl;
	}

	public ItemTaxonomicoControl getItemTaxonomicoControl() {
		if (itemTaxonomicoControl == null)
			itemTaxonomicoControl = new ItemTaxonomicoControl();
		return itemTaxonomicoControl;
	}

	public void setItemTaxonomicoControl(
			ItemTaxonomicoControl itemTaxonomicoControl) {
		this.itemTaxonomicoControl = itemTaxonomicoControl;
	}

	public GeneratorPage() {
	}

	public GeneratorPage(String page, String path, MiniTemplator templator) {
		this.page = page;
		this.path = path;
		this.templator = templator;
	}

	public GeneratorPage(String page, String path, MiniTemplator templator,
			String pathMedia) {
		this.page = page;
		this.path = path;
		this.templator = templator;
		this.pathMedia = pathMedia;
	}

	public GeneratorPage(String page, String path, MiniTemplator templator,
			String pathMedia, HashMap<String, Object> parameters) {
		this.page = page;
		this.path = path;
		this.templator = templator;
		this.pathMedia = pathMedia;
		this.parameters = parameters;
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
		case "detalhecolecao":
			templator = generateDetailCollection();
			break;
		case "detalheitemcolecao":
			templator = generateDetailItemCollection();
		case "jogos":
			templator = generateGames();
			break;
		default:
			templator = generateDefault();
			break;
		}
		return templator;
	}

	private MiniTemplator generateDetailItemCollection() {
		MiniTemplator temp = generateDefault();
		return null;
	}

	@SuppressWarnings("null")
	protected MiniTemplator generateColections() {
		MiniTemplator temp = generateDefault();
		List<?> list = getItemTaxonomicoControl().getMastersItens();
		for (Object itemTaxonomico : list) {
			temp.setVariable("nameItem",
					(String) ((Object[]) itemTaxonomico)[1]);
			temp.setVariable(
					"imageItem",
					createPathImage((String) ((Object[]) itemTaxonomico)[1],
							(String) ((Object[]) itemTaxonomico)[4]));
			temp.setVariable(
					"descriptionItem",
					(String) ((Object[]) itemTaxonomico)[2] != null ? (String) ((Object[]) itemTaxonomico)[2]
							: (String) ((Object[]) itemTaxonomico)[1]);
			BigDecimal idParent = (BigDecimal) ((Object[]) itemTaxonomico)[0];
			List<?> subItens = getItemTaxonomicoControl().getSubItens(idParent);
			if (subItens != null || !subItens.isEmpty()) {
				for (Object object : subItens) {
					BigDecimal idSubItem = (BigDecimal) ((Object[]) object)[0];
					temp.setVariable("idSubItem", String.valueOf(idSubItem));
					temp.setVariable("subNameItem",
							(String) ((Object[]) object)[1]);
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
		List<IntegranteEquipe> list = getPersonControl().getTeam();
		for (IntegranteEquipe integrante : list) {
			temp.setVariable("curriculum", integrante.getLinkLates());
			temp.setVariable(
					"picture",
					pathMedia
							+ folderTeam
							+ Utils.translateName("thumb_"
									+ integrante.getFileName()));
			temp.setVariable("description", integrante.getDescricao());
			temp.addBlock("person");
		}
		return temp;
	}

	private MiniTemplator generateGames() {
		MiniTemplator temp = generateDefault();
		List<?> list = getCrossWordControl().getCrossWords();
		for (Object item : list) {
			String link = String.valueOf((BigInteger) ((Object[]) item)[0]);
			String nome = (String) ((Object[]) item)[3];
			temp.setVariable("linkcruzadinha", link);
			temp.setVariable("nomecruzadinha", nome);
			temp.addBlock("cruzadinha");
		}
		
		
		list = getJogoPTTControl().getJogosPTT();
		String jogosPpt = pathMedia + "jogos_ppt/";
		for (Object object : list) {
			String link = String.valueOf((BigInteger) ((Object[]) object)[0]);
			String caminho = (String) ((Object[]) object)[1];
			String nome = (String) ((Object[]) object)[3];
			temp.setVariable("linkjogoppt", jogosPpt + link + "/" + caminho.replace(".zip", "")	+ "/index.htm");
			temp.setVariable("nomejogoppt", nome);
			temp.addBlock("jogoppt");
		}
		
		return temp;
	}
	
//	private MiniTemplator generateGames() {
//		MiniTemplator temp = generateDefault();
//
//		List<CrossWord> crossWords = getCrossWordControl().getCrossWords();
//		for (CrossWord crossWord : crossWords) {
//			temp.setVariable("linkcruzadinha",
//					String.valueOf(crossWord.getId()));
//			temp.setVariable("nomecruzadinha", crossWord.getName());
//			temp.addBlock("cruzadinha");
//		}
//
//		List<JogoPPT> jogosPPT = getJogoPTTControl().getJogosPTT();
//		String jogosPptUrl = pathMedia + "jogos_ppt/";
//		for (JogoPPT jogoPPT : jogosPPT) {
//			temp.setVariable("linkjogoppt", jogosPptUrl + jogoPPT.getId() + "/"
//					+ jogoPPT.getMedia().getName().replace(".zip", "")
//					+ "/index.htm");
//			temp.setVariable("nomejogoppt", jogoPPT.getName());
//			temp.addBlock("jogoppt");
//		}
//
//		return temp;
//	}

	protected MiniTemplator generateDetailCollection() {
		MiniTemplator temp = generateDefault();
		String idColection = (String) parameters.get("itemid");
		BigDecimal bigDecimal = new BigDecimal(idColection);
		Colecao col = getItemTaxonomicoControl().getColectionName(Long.parseLong(idColection));
		temp.setVariable("colectionName", col.getNome());
		List<?> list = getItemTaxonomicoControl().getItensOfColection(bigDecimal);
		for (Object item : list) {
			String path = (String) ((Object[]) item)[0];
			String estacao = (String) ((Object[]) item)[1];
			String caminho = (String) ((Object[]) item)[15];
			String pathFotoCompleto = path
					+ (!estacao.equalsIgnoreCase("") ? "/" + estacao : "");
			temp.setVariable("itemPathFoto", "/PortalSite/" + pathMedia
					+ pathFotoCompleto + "/image/thumb_" + caminho);

			temp.setVariable("itemReino", (String) ((Object[]) item)[5]);
			temp.setVariable("itemFilo", (String) ((Object[]) item)[6]);
			temp.setVariable("itemClasse", (String) ((Object[]) item)[7]);
			temp.setVariable("itemOrder", (String) ((Object[]) item)[8]);
			temp.setVariable("itemFamily", (String) ((Object[]) item)[9]);
			temp.setVariable("itemGenery", (String) ((Object[]) item)[10]);

			temp.setVariable("itemEptEspecify", (String) ((Object[]) item)[11]);

			temp.addBlock("itensColection");
		}
		return temp;
	}

	protected MiniTemplator generateDefault() {
		templator.setVariable("absoluteSitePath", "/PortalSite/");
		return GeneratorPage.initTemplator(path);
	}

	protected String createPathImage(String nameItem, String nameImage) {
		return pathMedia + nameItem + "/image" + "/" + nameImage;
	}

}
