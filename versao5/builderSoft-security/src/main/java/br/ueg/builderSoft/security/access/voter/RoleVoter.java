package br.ueg.builderSoft.security.access.voter;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Johnys
 *Classe responsavel por verificar a autenticacao com base na UseCase e na Functionality. 
 *
 *
 */
public class RoleVoter extends BaseVoter{
	
	public static final String USE_CASE_PREFIX = "UC_";
	public static final String FUNCTIONALITY_PREFIX = "FUNC_";
	
	/**
	 * Metodo que verifica se o usuario contem a a UseCase necessaria para acessar a URL.
	 * O metodo devera ser anotado com @Secured com o UseCase e a Functionality necessaria para acessar o metodo.
	 * Ex: @Secured({"UC_CATEGORY", "FUNC_PERSIST"})
	 * Caso o usuario contenha a UseCase e a Functionality em uma de suas Profiles o acesso sera permitido, caso contrario sera negado.  
	 */
	@Override
	public int vote(Authentication authentication, Object object, Collection attributes) {
		Boolean granted = false;
		String[] useCaseFunctionality = getUseCaseFunctionalityAttributes(attributes);
		if (useCaseFunctionality != null && useCaseFunctionality.length == 2) {
			for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
				granted = isGrantedPermission(((UserDetails)authentication.getPrincipal()).getUsername(),grantedAuthority.getAuthority(), useCaseFunctionality[0], useCaseFunctionality[1]);
				if(granted) break;
			}
		}
		if(granted){
			return ACCESS_GRANTED;
		}else{
			return ACCESS_DENIED;
		}
	}

	/**
	 * Metodo responsavel por selecionar os dois atributos definidos na annotation @Secured({"PARAM1","PARAM2"}).
	 * Pode ser gerado uma excessao de NullPointerException caso nao exista os parametros no annotation.
	 * @param attributes Atributos inseridos pelo spring.
	 * @return Vetor de 2 posicoes contendo os dois parametros.
	 * 
	 */
	private String[] getUseCaseFunctionalityAttributes(Collection<ConfigAttribute> attributes) {
		Iterator<ConfigAttribute> iterator = attributes.iterator();
		String[] useCaseFunctionality = new String[2];
		useCaseFunctionality[0] = iterator.next().getAttribute();
		useCaseFunctionality[1] = iterator.next().getAttribute();
		return organizeUseCaseFunctionality(useCaseFunctionality);
	}

	/**
	 * Metodo responsavel por organizar os parametros, de modo que o atributo que defina o nome da UseCase venha na primeira posicao do vetor.
	 * @param useCaseFunctionality Vetor contendo os dois atributos.
	 * @return O vetor organizado.
	 */
	private String[] organizeUseCaseFunctionality(String[] useCaseFunctionality) {
		String aux = useCaseFunctionality[0];
		if(aux.startsWith(FUNCTIONALITY_PREFIX)){
			useCaseFunctionality[0] = useCaseFunctionality[1];
			useCaseFunctionality[1] = aux;
		}
		return useCaseFunctionality;
	}

	private Boolean isGrantedPermission(String user, String profile, String useCase, String functionality) {
		return getSpringSecurityManager().isGrantedFunctionality(user, profile, useCase, functionality);
	}

	@Override
	public boolean supports(Class arg0) {
		// TODO Auto-generated method stub
		return true;
	}


}
