package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;



public class TesteParser {
	Map glossario = new HashMap();
	
	public TesteParser(){
		glossario.put("bioma", "� um conjunto de ecossitemas de uma �rea do espa�o geogr�fico que apresenta ecologia pr�pria e uniformidade de um macroclima definido. Cada bioma possui caracter�sticas pr�prias sobre a forma��o vegetal, a fauna e outros organismos vivos associados e outras condi��es ambientais como a altitude, o solo, alagamentos, o fogo, a salinidade, entre outros. ");
		glossario.put("biodiversidade","� a diversidade de organimos vivos, ou seja, � a rela��o da riqueza (n�mero de esp�cies) e a abund�ncia (quantidade de indiv�duos de determinada esp�cie) que ocorre em um local ou determinada amostra.");
		glossario.put("hotspots", "� s�o regi�es de elevado �ndice de biodiversidade que s�o priorit�rias para conserva��o devido ao alto grau de amea�a. Para ser um Hotspot, uma regi�o deve pelo menos abrigar no m�nimo 1500 esp�cies de plantas vasculares end�micas e ter 30% ou menos da sua vegeta��o original mantida.");
		glossario.put("campestres","Forma��es Campestres s�o um dos tipos de forma��es (forma de vegeta��o) presentes no bioma Cerrado, onde se enquadram v�rios tipos principais de vegeta��o (Fitofisionomias) caracterizadas pela presen�a de arbustos e subarbustos.");
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
		palavra = palavra.replace( " " , ""); //tira espa�o em branco  
		palavra = palavra.replace( "." , ""); //tira ponto  
		palavra = palavra.replace( "/" , ""); //tira barra  
		palavra = palavra.replace( "-" , ""); //tira h�fen
		return palavra;
	}
	
	public void printProcessado(){

	try {
		BufferedReader in = new BufferedReader(new FileReader("F:/temp/portal/conheca_o_cerrado.htm"));
		String conteudoHtml = this.readFile(in);
		String conteudoText = this.extractText(conteudoHtml);
		
		String str;
		String strProcessada="";
		String glossarioValue="";
		//System.out.println(str);
		String[] palavra = conteudoText.split(" ");
		Pattern pattern = Pattern.compile("<p.*?>(.*?)</p>");
		System.out.println(conteudoHtml);
		for (int i=0; i< palavra.length;i++) {
			glossarioValue=this.getGlossario(palavra[i]);
			if(glossarioValue!=null && !glossarioValue.isEmpty()){
				Matcher m = pattern.matcher(conteudoHtml);
				while(m.find()){
					String s = m.group(0);
					String saux = s;
					System.out.println(s);
					s = s.replaceAll(this.tiraPontos(palavra[i]), "<a href=\"#\" class=\"tooltip\"><em><strong>"+codificaGlossario(palavra[i])+"</em></strong><span>"+glossarioValue+"</span></a>");
					conteudoHtml=conteudoHtml.replaceAll(saux,s);
				}
				//conteudoHtml=conteudoHtml.replaceAll(this.tiraPontos(palavra[i]), "<a href=\"#\" class=\"tooltip\"><em><strong>"+codificaGlossario(palavra[i])+"</em></strong><span>"+glossarioValue+"</span></a>");
			}
			
		}
		conteudoHtml = conteudoHtml.replaceAll("###", "");
		System.out.println(conteudoHtml);

		in.close();
		} catch (IOException e) {
		}
	}
	
	public static void main(String[] args) {
		TesteParser t = new TesteParser();
		t.printProcessado();
	}
}
