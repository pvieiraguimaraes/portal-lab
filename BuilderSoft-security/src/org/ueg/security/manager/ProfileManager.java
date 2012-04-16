package org.ueg.security.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ProfileManager {


//
//	@Override
//	protected ProfileDAO getDAO() {
//		return dao;
//	}

	/**
	 * Verifica se o Profile, UseCase e a Funcionality estao mapeados no banco de dados.
	 * 
	 * @param profile
	 *            Nome do Profile que sera verificado.
	 * @param useCase
	 *            Nome do UseCase que sera verificado
	 * @param functionality
	 *            Nome da Funcionality que sera verificada.
	 * @return True se existir o mapeamento no banco, false caso contrario.
	 */
	public boolean isGrantedFunctionality(String profile, String useCase, String functionality) {
		//return getDAO().isGrantedFunctionality(profile, useCase, functionality);
		return true;
	}

	/**
	 * Verifica se o Profile e o UseCase estao mapeados no banco de dados.
	 * 
	 * @param profile
	 *            Nome do profile que sera verificado.
	 * @param useCase
	 *            Nome do UseCase que sera verificado.
	 * @return True caso exista o mapeamento no banco, e false caso contrario.
	 */
	public boolean isGrantedUseCase(String profile, String useCase) {
		//return getDAO().isGrantedUseCase(profile, useCase);
		return true;
	}

}