/**
 * Utils.java - 21/12/2012
 */
package br.ueg.builderSoft.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.zkoss.zk.ui.Executions;

/**
 * Classe de utilitarios
 * 
 * @author Diego Carlos Rezende - Analista de sistemas 
 *         <diego.rezende@unievangelica.edu.br>
 *
 */
public class Utils {
	
	/**
	 * M�todo que retorna string com a primeira letra em mai�scula, o restante permanece o mesmo.
	 * 
	 * @param str valor
	 * @return valor com a primeira letra em mai�sculo
	 */
	public static String firstToUpperCase(String str) {
		return str.substring(0, 1).toUpperCase().concat(str.substring(1));
	}
	
	/**
	 * M�todo que retorna string com a primeira letra em min�scula, o restante permanece o mesmo.
	 * 
	 * @param str valor
	 * @return valor com a primeira letra em min�scula
	 */
	public static String firstToLowerCase(String str) {
		return str.substring(0, 1).toLowerCase().concat(str.substring(1));
	}
	
	/**
	 * M�todo que adiciona "." no final da palavra caso ela n�o seja vazia
	 * 
	 * @param str string
	 * @return palavra com "." concatenado
	 */
	public static String stringNotEmptyDotConcat(String str) {
		if (str == null) {
			str = "";
		}
		if (!str.isEmpty()) {
			return str.concat(".");
		}
		return str;
	}
	
	/**
	 * M�todo que criptografa palavra em MD5
	 * 
	 * @param source palavra
	 * @return palavra criptografada
	 */
	public static String encrypt(String source) {
		String md5 = null;
		try {
			MessageDigest mdEnc = MessageDigest.getInstance("MD5"); // Encryption algorithm
			mdEnc.update(source.getBytes(), 0, source.length());
			md5 = new BigInteger(1, mdEnc.digest()).toString(16); // Encrypted string
		} catch (Exception ex) {
			return null;
		}
		return md5;
	}
	
	/**
	 * M�todo que retira todos caracteres especiais e acentua��o
	 * 
	 * @param source palavra
	 * @return palavra sem caracteres especiais e acentos
	 */
	public static String replaceAllSpecialCharacter(String source) {
		String text = Normalizer.normalize(source, Normalizer.Form.NFD);
        return text.replaceAll("[^\\p{ASCII}]", "");
	}
	
	/**
	 * M�todo que retira todos "_" (underline) e coloca as palavras
	 * que seguem em case sensitive
	 * 
	 * @param source palavra
	 * @return palavra sem underline
	 */
	public static String replaceUnderlineToCaseSensitive(String source) {
		if (source.contains("_")) {
			String splited[] = source.toLowerCase().split(Pattern.quote("_"));
			String newString = splited[0];
			for (int i = 1; i < splited.length; i++) {
				newString = newString.concat(firstToUpperCase(splited[i]));
			}
			return newString;
		} 
		return source;
	}
	
	/**
	 * M�todo que retorna sempre uma string, mesmo que vazia
	 * 
	 * @param str palavra
	 * @return palavra ou string vazia
	 */
	public static String getStringOrEmpty(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}
	
	/**
	 * M�todo que faz a subtra��oo dos meses, ou seja,
	 * retorna a diferen�a entre duas datas por meses
	 * 
	 * @param start data inicial
	 * @param end data final
	 * @return diferen�a de meses entre as datas
	 */
	public static int subtractMonths(Date start, Date end) {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(start);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(end);
		int startMonths = (startCalendar.get(Calendar.YEAR) * 12) + startCalendar.get(Calendar.MONTH);
		int endMonths = (endCalendar.get(Calendar.YEAR) * 12) + endCalendar.get(Calendar.MONTH);
		return endMonths - startMonths;
	}
	
	
	/**
	 * M�todo que retorna o valor em moeda, inserindo o simbolo antes
	 * 
	 * @param symbol simbolo a ser inserido
	 * @param value valor
	 * @return valor em moeda
	 */
	public static String getMonetaryValue(String symbol, Double value) {
		DecimalFormat df = new DecimalFormat("#.00");
		return symbol+" "+df.format(value);
	}
	
	/**
	 * M�todo que formata a data no padr�o enviado
	 * 
	 * @param format formato da data (padr�o)
	 * @param date data a ser formatada
	 * @return data formatada
	 */
	public static String formatDate(String format, Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);  
		return dateFormat.format(date);
	}
	
	/**
	 * M�todo que inicializa o proxy do hibernate.
	 * 
	 * @param entity entidade com proxy do hibernate
	 * @return entidade instanciada
	 */
    @SuppressWarnings("unchecked")
	public static <T> T initializeAndUnproxy(T entity) {
        if (entity == null) {
                return null;
        }

        if (entity instanceof HibernateProxy) {
                Hibernate.initialize(entity);
                entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
        }
        return entity;
    }
    
    /**Método que codifica a String para o padrão ISO-8859-1
     * @param name
     * @return String codificada.
     */
    public static String translateName(String name){
		String aux = null;
		try {
			aux = URLEncoder.encode(name, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return aux;
	}

}
