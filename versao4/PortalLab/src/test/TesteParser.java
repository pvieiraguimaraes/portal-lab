package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;

public class TesteParser {
	HashMap<String,String> glossario = new HashMap();
	
	public TesteParser(){
		glossario.put("bioma", "� um conjunto de ecossitemas de uma �rea do espa�o geogr�fico que apresenta ecologia pr�pria e uniformidade de um macroclima definido. Cada bioma possui caracter�sticas pr�prias sobre a forma��o vegetal, a fauna e outros organismos vivos associados e outras condi��es ambientais como a altitude, o solo, alagamentos, o fogo, a salinidade, entre outros. ");
		glossario.put("biodiversidade","� a diversidade de organimos vivos, ou seja, � a rela��o da riqueza (n�mero de esp�cies) e a abund�ncia (quantidade de indiv�duos de determinada esp�cie) que ocorre em um local ou determinada amostra.");
		glossario.put("hotspots", "� s�o regi�es de elevado �ndice de biodiversidade que s�o priorit�rias para conserva��o devido ao alto grau de amea�a. Para ser um Hotspot, uma regi�o deve pelo menos abrigar no m�nimo 1500 esp�cies de plantas vasculares end�micas e ter 30% ou menos da sua vegeta��o original mantida.");
	}
	 public String readFile(Reader reader) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    BufferedReader br = new BufferedReader(reader);
		    String line;
		    while ( (line=br.readLine()) != null) {
		      sb.append(line);
		    }
		    //String textOnly =sb.toString();// Jsoup.parse(sb.toString()).text();
		    return sb.toString();
	}
	public String extractText(String html){
		return Jsoup.parse(html).text();
	}

	public String getGlossario(String palavra){
		palavra = palavra.replace( " " , ""); //tira espa�o em branco  
		palavra = palavra.replace( "." , ""); //tira ponto  
		palavra = palavra.replace( "/" , ""); //tira barra  
		palavra = palavra.replace( "-" , ""); //tira h�fen
		return glossario.get(palavra.toLowerCase());
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
		for (int i=0; i< palavra.length;i++) {
			glossarioValue=this.getGlossario(palavra[i]);
			if(glossarioValue!=null && !glossarioValue.isEmpty()){
				conteudoHtml=conteudoHtml.replaceAll(palavra[i], palavra[i].concat("<spam>".concat(glossarioValue).concat("</spam>")));
			}
			
		}
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
