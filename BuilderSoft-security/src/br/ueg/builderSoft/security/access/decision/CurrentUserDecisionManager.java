package br.ueg.builderSoft.security.access.decision;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import br.ueg.builderSoft.security.manager.SpringSecurityManager;


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
		Authentication authentication = getCurrentUserAuthentication();
		for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
			if (isGrantedPermission(((UserDetails)authentication.getPrincipal()).getUsername(),grantedAuthority.getAuthority(), useCase)) {
				granted = true;
				break;
			}
		}
		return granted;
	}

	public Boolean decide(String useCase, String functionality) {
		Boolean granted = false;
		Authentication authentication = getCurrentUserAuthentication();
		for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
			if (isGrantedPermission(((UserDetails)authentication.getPrincipal()).getUsername(),grantedAuthority.getAuthority(), useCase, functionality)) {
				granted = true;
				break;
			}
		}
		return granted;
	}

	private Authentication getCurrentUserAuthentication(){
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	private Boolean isGrantedPermission(String user, String profile, String useCase) {
		return this.securityManager.isGrantedUseCase(user, profile, useCase);
	}

	private Boolean isGrantedPermission(String user, String profile, String useCase, String functionality) {
		return this.securityManager.isGrantedFunctionality(user, profile, useCase, functionality);
	}

	public void setSecurityManager(SpringSecurityManager securityManager) {
		this.securityManager = securityManager;
	}

}
