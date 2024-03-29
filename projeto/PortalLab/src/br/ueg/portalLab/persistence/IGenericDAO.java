package br.ueg.portalLab.persistence;

import java.util.List;

import br.ueg.portalLab.model.Entity;

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
	 */
	public long save(E entity);
	/**
	 * M�todo para atualizar
	 * @param qualquer entidade que extenda Entity
	 */
	public void update(E entity);
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

}
