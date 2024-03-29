package br.ueg.builderSoft.security.access.voter;

import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticatedVoter extends BaseVoter {
	
	/**
	 * Metodo que verifica se o usuario contem a a UseCase necessaria para acessar a URL.
	 * O  access do security:intercept-url deve conter o nome da UseCase necessaria para o usario acessar a url.
	 * Caso o usuario contenha a UseCase em uma de suas Profiles o acesso sera permitido, caso contrario sera negado.  
	 */
	@Override
	public int vote(Authentication authentication, Object object, Collection attributes) {
		Boolean granted = false;
		String useCase = getUseCaseAttributes(attributes);
		if (useCase != null) {
			for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
				String username = "";
				if(authentication.getPrincipal() instanceof String){
					username = (String) authentication.getPrincipal();
				}else{
					username = ((UserDetails)authentication.getPrincipal()).getUsername();
				}
				granted = isGrantedPermission(username, grantedAuthority.getAuthority(), useCase);
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
	 * Metodo que retorna o atributo access definido na security:intercept-url.
	 * @param attributes Variaver fornecida pelo spring.
	 * @return O atributo.
	 */
	private String getUseCaseAttributes(Collection<ConfigAttribute> attributes) {
		ConfigAttribute configAttribute = attributes.iterator().next();
		String useCase = null;
		if (configAttribute != null) {
			useCase = configAttribute.getAttribute();
		}
		return useCase;
	}

	private Boolean isGrantedPermission(String user, String profile, String useCase) {
		return getSpringSecurityManager().isGrantedUseCase(user, profile, useCase);
	}

	@Override
	public boolean supports(Class arg0) {
		// TODO Auto-generated method stub
		return true;
	}
}
