package br.ueg.builderSoft.persistence;

import java.util.List;

import br.ueg.builderSoft.model.Entity;

/**
 * Interface para criação da classe de persistência para um CRUD básico
 * 
 * @author Diego
 *
 * @param <E> Entidade utilizada para ser persistida
 */
public interface IGenericDAO<E extends Entity> {
	
	/**
	 * Método para salvar
	 * @param entity entidade que extenda Entity
	 * @return o ID do objeto salvado
	 * @throws DataIntegrityViolationException 
	 * @throws Exception 
	 */
	public long save(E entity) throws DataIntegrityViolationException, Exception;
	/**
	 * Método para atualizar
	 * @param entity qualquer entidade que extenda Entity
	 * @throws DataIntegrityViolationException 
	 * @throws Exception 
	 */
	public void update(E entity) throws DataIntegrityViolationException, Exception;
	/**
	 * Método para excluir
	 * @param entity qualquer entidade que extenda Entity
	 */
	public void delete(E entity);
	/**
	 * Método de busca usando Criteria do Hibernate
	 * @param entity qualquer entidade que extenda Entity
	 * @param value valor que representa a chave de busca
	 * @return lista de objetos encontrados
	 */
	public List<E> findByCriteria(E entity, String value);
	/**
	 * Método de busca usando HQL do Hibernate
	 * @param entity qualquer entidade que extenda Entity
	 * @param value valor que representa a chave de busca
	 * @return lista de objetos encontrados
	 */
	public List<E> findByHQL(E entity, String value);
	/**
	 * Método para listagem
	 * @param entity objeto que extenda Entity para carregar os registros do mesmo
	 * @return lista de todos registros da entidade
	 */
	List<E> getList(E entity);
	
	/**
	 * Método utilizado para retornar uma lista fazendo busca pelos atributos
	 * preenchido das entidade passada.
	 * @param entity entidade passada para poder fazer a busca
	 * @return List<E> lista de entidade
	 */
	public List<E> findByEntity(Entity entity);
	
	/**
	 * Método utilizado para retornar uma lista fazendo busca por uma SQL nativa.
	 * @param sql SQL para pesquisa no banco
	 * @return List<E> lista de resultados
	 */
	public List<E> findByNativeSQL(String sql);

}
