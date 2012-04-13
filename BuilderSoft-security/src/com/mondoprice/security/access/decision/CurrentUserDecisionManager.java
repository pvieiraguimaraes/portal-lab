package com.mondoprice.security.access.decision;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mondoprice.security.manager.SpringSecurityManager;

/**
 * @author Johnys
 */
@Service("CurrentUserDecisionManager")
public class CurrentUserDecisionManager implements Serializable {

	private static final long serialVersionUID = -6169343334349430012L;

	@Autowired
	private SpringSecurityManager securityManager;

	public Boolean decide(String useCase) {
		Boolean granted = false;
		for (GrantedAuthority grantedAuthority : getCurrentUserAuthentication().getAuthorities()) {
			if (isGrantedPermission(grantedAuthority.getAuthority(), useCase)) {
				granted = true;
				break;
			}
		}
		return granted;
	}

	public Boolean decide(String useCase, String functionality) {
		Boolean granted = false;
		for (GrantedAuthority grantedAuthority : getCurrentUserAuthentication().getAuthorities()) {
			if (isGrantedPermission(grantedAuthority.getAuthority(), useCase, functionality)) {
				granted = true;
				break;
			}
		}
		return granted;
	}

	private Authentication getCurrentUserAuthentication(){
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	private Boolean isGrantedPermission(String profile, String useCase) {
		return this.securityManager.isGrantedUseCase(profile, useCase);
	}

	private Boolean isGrantedPermission(String profile, String useCase, String functionality) {
		return this.securityManager.isGrantedFunctionality(profile, useCase, functionality);
	}

	public void setSecurityManager(SpringSecurityManager securityManager) {
		this.securityManager = securityManager;
	}

}
