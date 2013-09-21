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
	 * @param exceptionOccurred se ocorreu exce��o de banco de dados (opcional,
	 * usando apenas no DAO)
	 * @return o ID do objeto salvado
	 * @throws DataIntegrityViolationException 
	 * @throws Exception 
	 */
	public long save(E entity, Boolean... exceptionOccurred) throws DataIntegrityViolationException, Exception;
	/**
	 * Método para atualizar
	 * @param entity qualquer entidade que extenda Entity
	 * @param exceptionOccurred se ocorreu exce��o de banco de dados (opcional,
	 * usando apenas no DAO)
	 * @throws DataIntegrityViolationException 
	 * @throws Exception 
	 */
	public void update(E entity, Boolean... exceptionOccurred) throws DataIntegrityViolationException, Exception;
	/**
	 * Método para excluir
	 * @param entity qualquer entidade que extenda Entity
	 * @param exceptionOccurred se ocorreu exce��o de banco de dados (opcional,
	 * usando apenas no DAO)
	 */
	public void delete(E entity, Boolean... exceptionOccurred);
	/**
	 * Método de busca usando Criteria do Hibernate
	 * @param entity qualquer entidade que extenda Entity
	 * @param value valor que representa a chave de busca
	 * @param exceptionOccurred se ocorreu exce��o de banco de dados (opcional,
	 * usando apenas no DAO)
	 * @return lista de objetos encontrados
	 */
	public List<E> findByCriteria(E entity, String value, Boolean... exceptionOccurred);
	/**
	 * Método de busca usando HQL do Hibernate
	 * @param entity qualquer entidade que extenda Entity
	 * @param value valor que representa a chave de busca
	 * @param exceptionOccurred se ocorreu exce��o de banco de dados (opcional,
	 * usando apenas no DAO)
	 * @return lista de objetos encontrados
	 */
	public List<E> findByHQL(E entity, String value, Boolean... exceptionOccurred);
	/**
	 * Método para listagem
	 * @param entity objeto que extenda Entity para carregar os registros do mesmo
	 * @param exceptionOccurred se ocorreu exce��o de banco de dados (opcional,
	 * usando apenas no DAO)
	 * @return lista de todos registros da entidade
	 */
	List<E> getList(E entity, Boolean... exceptionOccurred);
	
	/**
	 * Método utilizado para retornar uma lista fazendo busca pelos atributos
	 * preenchido das entidade passada.
	 * @param entity entidade passada para poder fazer a busca
	 * @param exceptionOccurred se ocorreu exce��o de banco de dados (opcional,
	 * usando apenas no DAO)
	 * @return List<E> lista de entidade
	 */
	public List<E> findByEntity(Entity entity, Boolean... exceptionOccurred);
	
	/**
	 * Método utilizado para retornar uma lista fazendo busca por uma SQL nativa.
	 * @param sql SQL para pesquisa no banco
	 * @param exceptionOccurred se ocorreu exce��o de banco de dados (opcional,
	 * usando apenas no DAO)
	 * @return List<E> lista de resultados
	 */
	public List<E> findByNativeSQL(String sql, Boolean... exceptionOccurred);

}
