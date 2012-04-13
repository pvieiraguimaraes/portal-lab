package com.mondoprice.security.access.decision;

import java.util.Collection;


import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

public class MethodsAccessDecisionManager extends AbstractAccessDecisionManager {

	@Override
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		int deny = 0;
		for (AccessDecisionVoter voter : getDecisionVoters()) {
			int result = voter.vote(authentication, object, configAttributes);
			switch (result) {
            case AccessDecisionVoter.ACCESS_GRANTED:
                return;
            case AccessDecisionVoter.ACCESS_DENIED:
                deny++;
                break;
            default:
                break;
            }
		}
		if (deny > 0) {
			throw new AccessDeniedException("<#MENSAGEM DE ACESSO NEGADO#>");
		}
	}

}
