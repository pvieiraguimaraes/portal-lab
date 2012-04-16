package br.ueg.builderSoft.security.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ProfileManager {


//
//	@Override
//	protected ProfileDAO getDAO() {
//		return dao;
//	}
	
	@Autowired
	protected IProfile profiler;

	/**
	 * @return the profiler
	 */
	public IProfile getProfiler() {
		return profiler;
	}

	/**
	 * @param profiler the profiler to set
	 */
	public void setProfiler(IProfile profiler) {
		this.profiler = profiler;
	}

	/**
	 * Verifica se o Profile, UseCase e a Funcionality estao mapeados no banco de dados.
	 * 
	 * @param profile
	 *            Nome do Profile que sera verificado.
	 * @param useCase
	 *            Nome do UseCase que sera verificado
	 * @param functionality
	 *            Nome da Funcionality que sera verificada.
	 * @param user
	 * 			  Nome do usuario que sera verificado
	 * @return True se existir o mapeamento no banco, false caso contrario.
	 */
	public boolean isGrantedFunctionality(String user, String profile, String useCase, String functionality) {
		return this.getProfiler().isGrantedFunctionality(user, profile, useCase, functionality);
		//return true;
	}

	/**
	 * Verifica se o Profile e o UseCase estao mapeados no banco de dados.
	 * 
	 * @param profile
	 *            Nome do profile que sera verificado.
	 * @param useCase
	 *            Nome do UseCase que sera verificado.
	 * @param user
	 * 			  Nome do usuario que sera verificado
	 * @return True caso exista o mapeamento no banco, e false caso contrario.
	 */
	public boolean isGrantedUseCase(String user, String profile, String useCase) {
		return this.getProfiler().isGrantedUseCase(user,profile, useCase);
		//return true;
	}

}