package org.ueg.security.manager;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ueg.security.cache.SecurityCache;



/**
 * @author Johnys
 * 
 */
@Service
public class SpringSecurityManager implements Serializable {

	private static final long serialVersionUID = -5243287417602002606L;

	private SecurityCache securityCache = SecurityCache.getInstance();

	@Autowired
	private ProfileManager profileManager;

	/**
	 * @author Johnys Verifica se um profile contem uma UseCase.
	 * @param profile
	 *            Nome do Profile que sera verificado.
	 * @param useCase
	 *            Nome do UseCase que sera verificado.
	 * @return True caso o Profile contenha a UseCase, false caso contrario.
	 */
	public boolean isGrantedUseCase(String profile, String useCase) {
		int responseCache = securityCache.isUseCaseAccessible(profile, useCase);
		boolean granted = responseCache == SecurityCache.CACHE_GRANTED;
		if (responseCache == SecurityCache.CACHE_FAULT) {
			granted = isPermissionForUseCase(profile, useCase);
		}
		return granted;
	}

	/**
	 * @author Johnys Verifica se a Profile contem a Functionality para a UseCase especificada.
	 * @param profile
	 *            Nome da Profile que sera verificada.
	 * @param useCase
	 *            Nome da UseCase que sera verificada.
	 * @param functionality
	 *            Nome da Functionality que sera verificada.
	 * @return True caso a Profile contenha a functionality para a UseCase especificada, False caso contrario.
	 */
	public boolean isGrantedFunctionality(String profile, String useCase, String functionality) {
		int responseCache = securityCache.isFunctionalityAccessible(profile, useCase, functionality);
		boolean granted = responseCache == SecurityCache.CACHE_GRANTED;
		if (responseCache == SecurityCache.CACHE_FAULT) {
			granted = isPermissionForFunctionality(profile, useCase, functionality);
		}
		return granted;
	}

	/**
	 * @author Johnys Verifica se a Profile contem a Functionality para a UseCase especificada no banco de dados.
	 * @param profile
	 *            Nome da Profile que sera verificada.
	 * @param useCase
	 *            Nome da UseCase que sera verificada.
	 * @param functionality
	 *            Nome da Functionality que sera verificada.
	 * @return True caso a Profile contenha a functionality para a UseCase especificada, False caso contrario.
	 */
	private boolean isPermissionForFunctionality(String profile, String useCase, String functionality) {
		boolean granted = getProfileManager().isGrantedFunctionality(profile, useCase, functionality);
		securityCache.addCache(profile, useCase, functionality, granted);
		return granted;
	}

	/**
	 * @author Johnys Verifica se a Profile contem a UseCase no banco de dados.
	 * @param profile
	 *            Nome da Profile que sera verificada.
	 * @param useCase
	 *            Nome da UseCase que sera verificada.
	 * @return True caso o Profile contenha a UseCase, false caso contrario.
	 */
	private boolean isPermissionForUseCase(String profile, String useCase) {
		boolean granted = getProfileManager().isGrantedUseCase(profile, useCase);
		securityCache.addCache(profile, useCase, null, granted);
		return granted;
	}

	public ProfileManager getProfileManager() {
		return profileManager;
	}

	public void setProfileManager(ProfileManager profileManager) {
		this.profileManager = profileManager;
	}

}
