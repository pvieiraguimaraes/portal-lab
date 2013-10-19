package br.ueg.portalsite.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Jsoup;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.model.Glossario;
import br.ueg.portalsite.SiteControl;

import test.TesteParser;

public class ParserGlossario extends SiteControl<Glossario> {
Map glossario = new HashMap();
	
	public ParserGlossario(){
		initGlossario();
	}
	private void initGlossario() {
		this.glossario = new HashMap();
		List<Glossario> list = this.getListEntity(new Glossario());
		for (Glossario gloss : list) {
			this.glossario.put(gloss.getNome(),gloss.getDescricao());
		}
		this.codificaGlossario();
	}
	public void codificaGlossario(){
		
		for (Iterator i = glossario.entrySet().iterator(); i.hasNext();) {
			Entry entry = (Entry) i.next();
			String key = (String) entry.getKey();
			String value = (String) glossario.get(key);
			
			for (Iterator<String> i2 = glossario.keySet().iterator(); i2.hasNext();) {
				String key2 = i2.next();
				
				value = value.replaceAll(key2, codificaGlossario(key2));
			}
			
			entry.setValue(value);
		}			
	}
	private String codificaGlossario(String key2) {
		return key2.substring(0,2)+"###"+key2.substring(2,key2.length());
	}
	public String readFile(Reader reader) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    BufferedReader br = new BufferedReader(reader);
		    String line;
		    while ( (line=br.readLine()) != null) {
		      sb.append(line.concat(" "));
		    }
		    //String textOnly =sb.toString();// Jsoup.parse(sb.toString()).text();
		    return sb.toString();
	}
	public String extractText(String html){
		return Jsoup.parse(html).text();
	}
	

	public String getGlossario(String palavra){
		palavra = tiraPontos(palavra);
		return (String) glossario.get(palavra.toLowerCase());
	}
	private String tiraPontos(String palavra) {
		palavra = palavra.replace( " " , ""); //tira espaço em branco  
		palavra = palavra.replace( "." , ""); //tira ponto  
		palavra = palavra.replace( "/" , ""); //tira barra  
		palavra = palavra.replace( "-" , ""); //tira hífen
		return palavra;
	}
	
	public void printProcessado(){
	try {
		BufferedReader in = new BufferedReader(new FileReader("F:/temp/portal/conheca_o_cerrado.htm"));
		String conteudoHtml = this.readFile(in);
		conteudoHtml = this.processaHTML(conteudoHtml);
		System.out.println(conteudoHtml);

		in.close();
		} catch (IOException e) {
		}
	}
	public String processaHTML(String html){
		String conteudoHtml = html;
		String conteudoText = this.extractText(conteudoHtml);
		
		String str;
		String strProcessada="";
		String glossarioValue="";
		//System.out.println(str);
		initGlossario();
		String[] palavra = conteudoText.split(" ");
		for (int i=0; i< palavra.length;i++) {
			glossarioValue=this.getGlossario(palavra[i]);
			if(glossarioValue!=null && !glossarioValue.isEmpty()){
				conteudoHtml=conteudoHtml.replaceAll(this.tiraPontos(palavra[i]), "<a href=\"#\" class=\"tooltipGlossario\"><em><strong>"+codificaGlossario(palavra[i])+"</em></strong><span>"+glossarioValue+"</span></a>");
			}
			
		}
		conteudoHtml = conteudoHtml.replaceAll("###", "");
		return conteudoHtml;
	}
	
	public static void main(String[] args) {
		TesteParser t = new TesteParser();
		t.printProcessado();
	}
}
