package br.ueg.builderSoft.security.access.voter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;

import br.ueg.builderSoft.security.manager.SpringSecurityManager;



public abstract class BaseVoter implements AccessDecisionVoter {
	@Autowired
	private SpringSecurityManager springSecurityManager;

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	public SpringSecurityManager getSpringSecurityManager() {
		return springSecurityManager;
	}

	public void setSpringSecurityManager(SpringSecurityManager springSecurityManager) {
		this.springSecurityManager = springSecurityManager;
	}


	
}
