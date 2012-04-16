package br.ueg.builderSoft.security.access.decision;

import java.util.Collection;
import java.util.List;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

/**
 * Classe responsavel por verificar os voters definidos pelo applicationContext.xml e definir se o usuario tem ou nao permissao de acessar o item.
 * @author Johnys
 *
 */
public class AccessDecisionManager extends AbstractAccessDecisionManager{

	/**
	 * Metodo que define se o usuario tem permissao de acessar o item, se pelo menos um Voter, definido pelo usuario, de ACCESS_GRANTED ao usuario. 
	 */
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> collection)
			throws AccessDeniedException, InsufficientAuthenticationException {
		List<AccessDecisionVoter> voters = getDecisionVoters();
		int accessDecision = AccessDecisionVoter.ACCESS_DENIED;
		for(AccessDecisionVoter voter : voters){
			accessDecision = voter.vote(authentication, object, collection);
			if(accessDecision == AccessDecisionVoter.ACCESS_GRANTED){
				break;
			}
		}
		if(accessDecision == AccessDecisionVoter.ACCESS_DENIED){
			throw new AccessDeniedException("ACESSO NAO AUTORIZADO");
		}
		
	}

}
