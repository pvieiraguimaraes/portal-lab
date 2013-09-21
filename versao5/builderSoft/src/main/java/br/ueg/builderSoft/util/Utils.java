/**
 * Utils.java - 21/12/2012
 */
package br.ueg.builderSoft.util;

import java.math.BigInteger;
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
	 * Método que retorna string com a primeira letra em maiúscula, o restante permanece o mesmo.
	 * 
	 * @param str valor
	 * @return valor com a primeira letra em maiúsculo
	 */
	public static String firstToUpperCase(String str) {
		return str.substring(0, 1).toUpperCase().concat(str.substring(1));
	}
	
	/**
	 * Método que retorna string com a primeira letra em minúscula, o restante permanece o mesmo.
	 * 
	 * @param str valor
	 * @return valor com a primeira letra em minúscula
	 */
	public static String firstToLowerCase(String str) {
		return str.substring(0, 1).toLowerCase().concat(str.substring(1));
	}
	
	/**
	 * Método que adiciona "." no final da palavra caso ela não seja vazia
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
	 * Método que criptografa palavra em MD5
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
	 * Método que retira todos caracteres especiais e acentuação
	 * 
	 * @param source palavra
	 * @return palavra sem caracteres especiais e acentos
	 */
	public static String replaceAllSpecialCharacter(String source) {
		String text = Normalizer.normalize(source, Normalizer.Form.NFD);
        return text.replaceAll("[^\\p{ASCII}]", "");
	}
	
	/**
	 * Método que retira todos "_" (underline) e coloca as palavras
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
	 * Método que retorna sempre uma string, mesmo que vazia
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
	 * Método que faz a subtraçãoo dos meses, ou seja,
	 * retorna a diferença entre duas datas por meses
	 * 
	 * @param start data inicial
	 * @param end data final
	 * @return diferença de meses entre as datas
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
	 * Método que retorna o valor em moeda, inserindo o simbolo antes
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
	 * Método que formata a data no padrão enviado
	 * 
	 * @param format formato da data (padrão)
	 * @param date data a ser formatada
	 * @return data formatada
	 */
	public static String formatDate(String format, Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);  
		return dateFormat.format(date);
	}
	
	/**
	 * Método que inicializa o proxy do hibernate.
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

}
