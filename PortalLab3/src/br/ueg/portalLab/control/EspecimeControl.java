package br.ueg.portalLab.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.control.SubController;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.model.Coletor;
import br.ueg.portalLab.model.Especime;
import br.ueg.portalLab.model.GrupoEnderecoFisico;
import br.ueg.portalLab.model.Laboratorio;
import br.ueg.portalLab.model.Usuario;
import br.ueg.portalLab.security.model.CasoDeUso;
import br.ueg.portalLab.security.model.CasoDeUsoFuncionalidade;
import br.ueg.portalLab.security.model.Funcionalidade;
import br.ueg.portalLab.security.model.GrupoUsuario;
import br.ueg.portalLab.security.model.UsuarioPermissao;
import br.ueg.portalLab.util.control.EspecimeValidatorControl;
import br.ueg.portalLab.util.control.UsuarioValidatorControl;

@Service
public class EspecimeControl extends Control<Especime> {

	private Especime selctedEspecime;
	
	
	public EspecimeControl(MessagesControl control) {
		super(control);		 
	}
	public EspecimeControl(){
		
	}
	

	/* (non-Javadoc)
	 * @see br.ueg.builderSoft.control.Control#getListValidator()
	 */
	@Override
	public List<SubController> getListValidator() {
		List<SubController> validators = super.getListValidator();

		validators.add((SubController) new EspecimeValidatorControl(this.getMessagesControl(),1));
		return validators;
	}
	
	
	/**
	 * Verifica se a entidade passada está presente na lista(feito por 
	 * problemas de utilização de Lazy no hibernate)
	 * @param entity
	 * @param list
	 * @return
	 */
	public boolean isEntityPresentInList(Entity entity, Set<Entity> list){
		if(list== null) return false;
		for(Entity localEntity : list){
			if(localEntity.getId().equals(entity.getId())){
				return true;
			}
		}
		return false;

	}
	
	/**
	 * Metodo utilizado para retorar a lista de itens da entidade
	 * que não estão associados ao Especime ainda.
	 * O metodo pega o tipo do item da lista e solicita uma listagem 
	 * dessa entidade removendo os itens presente na lista.
	 * Obs.: Esse metodo deve ser chamada com um lista que tenha
	 * pelo menos um item, pois o item será utilizado para determinar o tipo
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Entity> getListToEntityField(Set<Entity> list){
		if (list==null || (list!=null && list.size()==0)) return null;
		
		GenericDAO<Entity> genericDAO = (GenericDAO<Entity>)SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		
		Entity entityType = list.iterator().next();
		
		List<Entity> resultList = new ArrayList<Entity>(0);
		
		for(Entity e : genericDAO.getList(entityType)){
			if(!this.isEntityPresentInList(e, list)){
				resultList.add(e);
			}
		}
		
		return resultList;
	}
	/**
	 * @return
	 */
	private GenericDAO<GrupoEnderecoFisico> getGrupoEnderecoFisicoDAO() {
		return (GenericDAO<GrupoEnderecoFisico>)SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
	}
	
	
	
	public List<GrupoEnderecoFisico> getGrupoEnderecoFisicoList(Laboratorio lab){
		GenericDAO<GrupoEnderecoFisico> grupoEnderecoFisicoDAO = this.getGrupoEnderecoFisicoDAO();
		GrupoEnderecoFisico gef = new GrupoEnderecoFisico();
		gef.setLaboratorio(lab);
		List<GrupoEnderecoFisico> findByEntity = grupoEnderecoFisicoDAO.findByEntity(gef);
		//List<GrupoEnderecoFisico> findByEntity = grupoEnderecoFisicoDAO.getListFK(gef.getClass());
		return findByEntity;
	}
	
	
		
	
	
	/**
	 * @return the selectedGrupoUsuario
	 */
	public Especime getSelectedEspecime() {
		return selctedEspecime;
	}
	/**
	 * @param selectedEspecime the selectedGrupoUsuario to set
	 */
	public void setSelectedEspecime(Especime selectedEspecime) {
		this.selctedEspecime = selectedEspecime;
	}	
}
