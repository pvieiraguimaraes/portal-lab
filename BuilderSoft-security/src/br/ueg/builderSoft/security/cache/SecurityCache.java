package br.ueg.builderSoft.security.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecurityCache {

	private static SecurityCache instance;
	public static final int CACHE_FAULT = -1;
	public static final int CACHE_DENIED = 0;
	public static final int CACHE_GRANTED = 1;
	private Map<String, Map<String, Map<String, List<String>>>> grantedCache;
	private Map<String, Map<String, Map<String, List<String>>>> deniedCache;

	/**
	 * Construtor privado para impedir que esta classe seja instanciada fora do método <code>getInstance</code>
	 */
	private SecurityCache() {
		grantedCache = new HashMap<String, Map<String, Map<String, List<String>>>>(0);
		deniedCache =  new HashMap<String, Map<String, Map<String, List<String>>>>(0);
	}

	/**
	 * Método que segue o padrão de projeto singleton
	 * 
	 * @return SecurityCache
	 */
	public static SecurityCache getInstance() {
		if (instance == null)
			instance = new SecurityCache();
		return instance;
	}

	/**
	 * Verifica se o <code>UseCase</code> está disponível no cache da aplicação para o profile especificado, retornando
	 * uma das três constantes definidas na classe.
	 * 
	 * @param profile
	 *            role atribuida ao usuário
	 * @param useCase
	 *            role atribuida ao caso de uso ou à página do sistema
	 * @return int CACHE_FAULT - caso não tenha registro do profile no cache; CACHE_DENIED - caso o profile tenha acesso
	 *         bloqueado ao useCase gravado no cache; CACHE_GRANTED - caso o profile tenha acesso liberado ao useCase
	 *         gravado no cache.
	 */
	public int isUseCaseAccessible(String user, String profile, String useCase) {
		Map<String, Map<String, List<String>>> userUseCaseMap = grantedCache.get(user);
		if(userUseCaseMap!=null && userUseCaseMap.get(profile)!=null){
			Map<String, List<String>> useCaseMap = userUseCaseMap.get(profile);
			if (useCaseMap != null && useCaseMap.get(useCase) != null)
				return SecurityCache.CACHE_GRANTED;
			
			Map<String, Map<String, List<String>>> userUseCaseMapDenied = grantedCache.get(user);
			useCaseMap = userUseCaseMapDenied.get(profile);
			if (useCaseMap != null && useCaseMap.get(useCase) != null)
				return SecurityCache.CACHE_DENIED;
		}
		return SecurityCache.CACHE_FAULT;
	}

	/**
	 * Verifica se a <code>Functionality</code> está disponível no cache da aplicação para o profile especificado,
	 * retornando uma das três constantes definidas na classe.
	 * 
	 * @param profile
	 *            role atribuida ao usuário
	 * @param useCase
	 *            role atribuida ao caso de uso ou à página do sistema
	 * @param functionality
	 *            role atribuida a um método (funcionalidade) do sistema
	 * @return int CACHE_FAULT - caso não tenha registro do profile no cache; CACHE_DENIED - caso o profile tenha acesso
	 *         bloqueado ao useCase gravado no cache; CACHE_GRANTED - caso o profile tenha acesso liberado ao useCase
	 *         gravado no cache.
	 */
	public int isFunctionalityAccessible(String user, String profile, String useCase, String functionality) {
		Map<String, Map<String, List<String>>> userUseCaseMap = grantedCache.get(user);
		if(userUseCaseMap!=null && userUseCaseMap.get(profile)!=null){
			Map<String, List<String>> useCaseMap = userUseCaseMap.get(profile);
			if (useCaseMap != null && useCaseMap.get(useCase) != null && useCaseMap.get(useCase).contains(functionality))
				return SecurityCache.CACHE_GRANTED;
			
			Map<String, Map<String, List<String>>> userUseCaseMapDenied = grantedCache.get(user);
			useCaseMap = userUseCaseMapDenied.get(profile);
			if (useCaseMap != null && useCaseMap.get(useCase) != null && useCaseMap.get(useCase).contains(functionality))
				return SecurityCache.CACHE_DENIED;
		}
		return SecurityCache.CACHE_FAULT;
	}

	/**
	 * Adiciona um <code>Profile</code>, <code>UseCase</code> ou uma <code>Functionality</code> no cache do sistema.
	 * 
	 * @param profile
	 *            role atribuida ao usuário que deseja executar a ação
	 * @param useCase
	 *            role atribuida a um caso de uso ou a uma pagina do sistema
	 * @param functionality
	 *            role atribuida a um método (funcionalidade) do sistema
	 * @param cache
	 *            cache ao qual deve ser adicionado os demais parametros (bloqueio ou liberação)
	 */
	private void add(String user, String profile, String useCase, String functionality, Map<String, Map<String, Map<String, List<String>>>> cache) {
		Map<String, Map<String, List<String>>> userUseCaseMap = grantedCache.get(user);
		if(userUseCaseMap == null){
			userUseCaseMap = new HashMap<String, Map<String, List<String>>>(0);
		}
		
		Map<String, List<String>> useCaseMap = userUseCaseMap.get(profile);
		// verifica se ja tem o profile
		if (useCaseMap == null) {
			// se não tiver o profile
			// adiciona o profile com o use case com a funcionalidade
			useCaseMap = createUseCasesMap(useCase, functionality);
		} else {
			// se tem profile... verifica se ja tem o use case
			List<String> functionalities = useCaseMap.get(useCase);
			if (functionalities == null) {
				// se não tiver o use case
				// adiciona o use case com a funcionalidade
				functionalities = new ArrayList<String>(0);
				functionalities.add(functionality);
			} else if (!functionalities.contains(functionality)) {
				// se sim... adiciona a funcionalidade
				functionalities.add(functionality);
			}
			useCaseMap.put(useCase, functionalities);
		}
		userUseCaseMap.put(profile, useCaseMap);
		
		cache.put(user, userUseCaseMap);
	}

	/**
	 * Cria um mapa com chave caso de uso e adiciona a funcionalidade em sua lista de funcionalidades permitidas
	 * 
	 * @param useCase
	 *            role atribuida a um caso de uso ou a uma pagina do sistema
	 * @param functionality
	 *            role atribuida a um metodo (funcionalidade) do sistema
	 * @return mapa
	 */
	private Map<String, List<String>> createUseCasesMap(String useCase, String functionality) {
		List<String> functionalities = new ArrayList<String>(0);
		functionalities.add(functionality);
		Map<String, List<String>> useCases = new HashMap<String, List<String>>(0);
		useCases.put(useCase, functionalities);
		return useCases;
	}

	/**
	 * Adiciona um profile ao cache do sistema. o profile é gravado como permitido caso do atributo granted seja true,
	 * caso contrario adiciona a permissão ao cache de funciinalidades bloqueadas.
	 * 
	 * @param profile
	 *            role atribuida a um grupo de usuários do sistema
	 * @param useCase
	 *            role atribuida a um caso de uso ou pagina do sistema
	 * @param functionality
	 *            role atribuida a um método (funcionalidade) do sistema
	 * @param granted
	 *            true para funcionalidade permitida e false para funcionalidade bloqueada
	 */
	public void addCache(String user, String profile, String useCase, String functionality, Boolean granted) {
		if (granted) {
			add(user, profile, useCase, functionality, grantedCache);
		} else {
			add(user, profile, useCase, functionality, deniedCache);
		}
	}

	/**
	 * Remove o profile do cache do sistema. Esta funcionalidade é utilizada quando é alterado alguma profile do
	 * sistema.
	 * 
	 * @param profile
	 *            role atribuida a um grupo de usuários do sistema.
	 */
	public void removeCache(String profile) {
		grantedCache.remove(profile);
		deniedCache.remove(profile);
	}

}