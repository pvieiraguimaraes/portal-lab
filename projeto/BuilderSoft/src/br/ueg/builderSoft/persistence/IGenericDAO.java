package br.ueg.builderSoft.persistence;

import java.util.List;

import br.ueg.builderSoft.model.Entity;

/**
 * Interface para cria��o da classe de persist�ncia para um CRUD b�sico
 * 
 * @author Diego
 *
 * @param <Entity>
 */
public interface IGenericDAO<E extends Entity> {
	
	/**
	 * M�todo para salvar
	 * @param qualquer entidade que extenda Entity
	 * @return o ID do objeto salvado
	 * @throws DataIntegrityViolationException 
	 * @throws Exception 
	 */
	public long save(E entity) throws DataIntegrityViolationException, Exception;
	/**
	 * M�todo para atualizar
	 * @param qualquer entidade que extenda Entity
	 * @throws DataIntegrityViolationException 
	 * @throws Exception 
	 */
	public void update(E entity) throws DataIntegrityViolationException, Exception;
	/**
	 * M�todo para excluir
	 * @param qualquer entidade que extenda Entity
	 */
	public void delete(E entity);
	/**
	 * M�todo de busca usando Criteria do Hibernate
	 * @param qualquer entidade que extenda Entity
	 * @param valor que representa a chave de busca
	 * @return lista de objetos encontrados
	 */
	public List<E> findByCriteria(E entity, String value);
	/**
	 * M�todo de busca usando HQL do Hibernate
	 * @param qualquer entidade que extenda Entity
	 * @param valor que representa a chave de busca
	 * @return lista de objetos encontrados
	 */
	public List<E> findByHQL(E entity, String value);
	/**
	 * M�todo para listagem
	 * @param objeto que extenda Entity para carregar os registros do mesmo
	 * @return lista de todos registros da entidade
	 */
	List<E> getList(E entity);
	
	/**
	 * M�todo utilizado para retornar uma lista fazendo busca pelos atributos
	 * preenchido das entidade passada.
	 * @param entity
	 * @return
	 */
	public List<E> findByEntity(Entity entity);

}
