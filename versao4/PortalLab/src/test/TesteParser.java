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
		glossario.put("bioma", "É um conjunto de ecossitemas de uma área do espaço geográfico que apresenta ecologia própria e uniformidade de um macroclima definido. Cada bioma possui características próprias sobre a formação vegetal, a fauna e outros organismos vivos associados e outras condições ambientais como a altitude, o solo, alagamentos, o fogo, a salinidade, entre outros. ");
		glossario.put("biodiversidade","É a diversidade de organimos vivos, ou seja, é a relação da riqueza (número de espécies) e a abundância (quantidade de indivíduos de determinada espécie) que ocorre em um local ou determinada amostra.");
		glossario.put("hotspots", "– são regiões de elevado índice de biodiversidade que são prioritárias para conservação devido ao alto grau de ameaça. Para ser um Hotspot, uma região deve pelo menos abrigar no mínimo 1500 espécies de plantas vasculares endêmicas e ter 30% ou menos da sua vegetação original mantida.");
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
		palavra = palavra.replace( " " , ""); //tira espaço em branco  
		palavra = palavra.replace( "." , ""); //tira ponto  
		palavra = palavra.replace( "/" , ""); //tira barra  
		palavra = palavra.replace( "-" , ""); //tira hífen
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
