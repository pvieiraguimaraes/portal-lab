package br.ueg.portalLab.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.zkoss.util.media.Media;

import br.ueg.builderSoft.config.ConfigPortalLab;
import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.control.SubControllerManager;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.control.SubController;
import br.ueg.builderSoft.util.reflection.Reflection;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.model.EspecieImagem;
import br.ueg.portalLab.model.Especime;
import br.ueg.portalLab.model.EspecimeDeterminador;
import br.ueg.portalLab.model.Estacao;
import br.ueg.portalLab.model.GrupoEnderecoFisico;
import br.ueg.portalLab.model.ItemGeografico;
import br.ueg.portalLab.model.ItemTaxonomico;
import br.ueg.portalLab.model.Laboratorio;
import br.ueg.portalLab.util.control.EspecimeValidatorControl;

@Service
@Scope("desktop")
public class EspecimeControl extends Control<Especime> {

	@Override
	public boolean actionCanceledit(
			SubControllerManager<Especime> subControllersManager) {
		
		ItemTaxonomico itta = ((Especime) this.getMapFields().get("entity")).getEpitetoEspecifico();
		this.refreshFields(itta);
		
		return super.actionCanceledit(subControllersManager);
	}
	@SuppressWarnings("unchecked")
	public void refreshFields(Entity entity){
		if(entity==null)return;
		for(Field f: Hibernate.getClass(entity).getDeclaredFields()){
			if(f.getType().getSimpleName().equalsIgnoreCase("set")){
				String fieldName = f.getName();
				try {
					Set<Entity> fieldValue = (Set<Entity>)Reflection.getFieldValue(entity, fieldName);
					Set<Entity> aux = new HashSet<Entity>(fieldValue);
					for(Entity e: aux){
						if(e.isNew()){
							fieldValue.remove(e);
						}
					}
					
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
	@Override
	public boolean actionSave(
			SubControllerManager<Especime> subControllerManager) {
		boolean retorno = false;
		Especime entity = (Especime) this.getMapFields().get("entity");
		Set<EspecieImagem> especieImagens = (Set<EspecieImagem>) this.getMapFields().get("especieImagem");
		ItemTaxonomico selectedItemTaxonomicoMedia = (ItemTaxonomico) this.getMapFields().get("selectedItemTaxonomicoMedia");
		

		if(!entity.isNew()){
			retorno = saveEspecieImagem(especieImagens, selectedItemTaxonomicoMedia);	
			if(retorno){
				retorno =  super.actionSave(subControllerManager);
								
			}
		}else{
			retorno = saveEspecieImagem(especieImagens, selectedItemTaxonomicoMedia);
			if(retorno){
				retorno = this.saveDeterminadoresForNewEntity(entity, subControllerManager);					
			}			
		}
		return retorno;
	}
	
	public boolean saveDeterminadoresForNewEntity(Especime entity, SubControllerManager<Especime> subControllerManager){
		boolean retorno = false;
		Set<EspecimeDeterminador> espDeterminadores = entity.getEspecimeDeterminadores();
		entity.setEspecimeDeterminadores(new HashSet<EspecimeDeterminador>());
		retorno = super.actionSave(subControllerManager);
		if(retorno){
			for(EspecimeDeterminador espDet: espDeterminadores){
				espDet.setEspecime(entity);
			}
			entity.setEspecimeDeterminadores(espDeterminadores);
			retorno = super.actionSave(subControllerManager);
		}				
		return retorno;
	}
	
	public boolean saveEspecieImagem(Set<EspecieImagem> especieImagens,ItemTaxonomico selectedItemTaxonomicoMedia){
		boolean retorno = true;
		if(selectedItemTaxonomicoMedia==null ) return false;
		if(selectedItemTaxonomicoMedia.getId().equals(0L)) return true;
		
		GenericDAO<EspecieImagem> especieImagemDAO = (GenericDAO<EspecieImagem>) SpringFactory.getInstance().getBean("genericDAO",GenericDAO.class);
		GenericDAO<ItemTaxonomico> itemTaxonomicoDAO = (GenericDAO<ItemTaxonomico>) SpringFactory.getInstance().getBean("genericDAO",GenericDAO.class);
		
		
		if(especieImagens!=null){
			for(EspecieImagem ei: especieImagens){
				if(ei.isNew()){
					ei.setItemTaxonomico(selectedItemTaxonomicoMedia);
					
					ei.setNome(ei.getMedia().getName());
					
					int writeImagemToDiskReturn = ei.writeImagemToDisk();
					if(writeImagemToDiskReturn==1){
						/*try {
							//especieImagemDAO.save(ei);
						} catch (Exception e) {
							this.getMessagesControl().addMessageError("especieImage_salvar");
							e.printStackTrace();
							return false;
						}*/
					}else{
						if(writeImagemToDiskReturn==0)
							this.getMessagesControl().addMessageError("especieImage_salvar");
						if(writeImagemToDiskReturn==2)
							this.getMessagesControl().addMessageError("especieImage_salvar_existe");
						return false;
					}
				}								
			}
			
			boolean especieImagemExiste = false;;
			try {
				itemTaxonomicoDAO.update(selectedItemTaxonomicoMedia);
			} catch (Exception e) {
				// TODO Auto-generated catch block				
				this.getMessagesControl().addMessageError("especieImage_salvar");
				e.printStackTrace();
				return false;
				
			}
			//remove as imagens removidas
			itemTaxonomicoDAO.refresh(selectedItemTaxonomicoMedia);
			for(EspecieImagem itemEspecieImage : new ArrayList<EspecieImagem>(selectedItemTaxonomicoMedia.getImagens())){
				for(EspecieImagem vEspecieImagem: especieImagens){
					if(itemEspecieImage.getId().equals(vEspecieImagem.getId())){
						especieImagemExiste = true;
						break;
					}
				}
				if(!especieImagemExiste){
					int deleteImagemFromDiscReturn = itemEspecieImage.deleteImagemFromDisk();
					if(deleteImagemFromDiscReturn==1){
						especieImagemDAO.delete(itemEspecieImage);
					}else if(deleteImagemFromDiscReturn==0){
						this.getMessagesControl().addMessageError("especieImage_remove");
						return false;
					}else if(deleteImagemFromDiscReturn==2){
						this.getMessagesControl().addMessageError("especieImage_remove_ano_existe");
						return false;
					}
					//TODO EspecieImagem remover imagem do diret�rio
				}
				especieImagemExiste = false;
			}
		}
		return retorno;
	}
	
	
	@SuppressWarnings("unchecked")
	public Set<EspecieImagem> getEspecieImagemFromItemTaxonomico(ItemTaxonomico it){
		if(it==null) return new HashSet<EspecieImagem>();
		GenericDAO<ItemTaxonomico> itemTaxonomicoDAO = (GenericDAO<ItemTaxonomico>) SpringFactory.getInstance().getBean("genericDAO",GenericDAO.class);
		itemTaxonomicoDAO.refresh(it);
		return it.getImagens();
	}

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
	 * Verifica se a entidade passada est� presente na lista(feito por 
	 * problemas de utiliza��o de Lazy no hibernate)
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
	 * que n�o est�o associados ao Especime ainda.
	 * O metodo pega o tipo do item da lista e solicita uma listagem 
	 * dessa entidade removendo os itens presente na lista.
	 * Obs.: Esse metodo deve ser chamada com um lista que tenha
	 * pelo menos um item, pois o item ser� utilizado para determinar o tipo
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Entity> getListToEntityField(Set<Entity> list){
		if (list==null || (list!=null && list.size()==0)) return null;
		
		GenericDAO<Entity> genericDAO = (GenericDAO<Entity>)SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		
		Entity entityType = list.iterator().next();
		
		List<Entity> resultList = new ArrayList<Entity>(0);
		
		for(Entity e : genericDAO.getList(Hibernate.getClass(entityType))){
			if(!this.isEntityPresentInList(e, list)){
				resultList.add(e);
			}
		}
		
		return resultList;
	}
	
	/**
	 * Metodo utilizado para retorar a lista de itens da entidade
	 * que n�o est�o associados ao Especime ainda.
	 * O metodo faz uma listagem de objetos do tipo entity passado como parametro
	 * e remove os itens que perten�am a lista passada, sendo que um atributo de cada 
	 * item da lista deve ser do tipo da entidade passada.	 
	 * @param list
	 * @param entiy
	 * @return
	 */
	
	public List<Entity> getListToEntityField(Set<Entity> list, Class<? extends Entity> entityClass){

		Set<Entity> auxList = new HashSet<Entity>(0);
		
		for(Entity entity : list){
			Entity auxEntity=null;
			try {
				auxEntity = (Entity) Reflection.getFieldValue(entity, entityClass.getSimpleName().toLowerCase());
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				auxEntity = entity;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			if(auxEntity!=null){
				auxList.add(auxEntity);
			}
		}
		if(auxList.size()==0){
			Entity e;
			try {
				e = entityClass.newInstance();
				e.setId(0L);
				auxList.add(e);
			} catch (InstantiationException e1) {				
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}

		}
		
		return this.getListToEntityField(auxList);
	}
	/**
	 * @return
	 */
	private GenericDAO<GrupoEnderecoFisico> getGrupoEnderecoFisicoDAO() {
		return (GenericDAO<GrupoEnderecoFisico>)SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<GrupoEnderecoFisico> getGrupoEnderecoFisicoList(Laboratorio lab){
		GenericDAO<GrupoEnderecoFisico> grupoEnderecoFisicoDAO = this.getGrupoEnderecoFisicoDAO();
		GrupoEnderecoFisico gef = new GrupoEnderecoFisico();
		gef.setLaboratorio(lab);
		List<GrupoEnderecoFisico> findByEntity = grupoEnderecoFisicoDAO.findByEntity(gef);
		Collections.sort(findByEntity);
		//List<GrupoEnderecoFisico> findByEntity = grupoEnderecoFisicoDAO.getListFK(gef.getClass());
		return findByEntity;
	}
	
	
//	public void cancelEditEntity(){
//		this.getPersistence().getSession().refresh(selctedEspecime);
//	}
	
	
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
	
	/* metodos para aba geografia */
	@SuppressWarnings("unchecked")
	public GenericDAO<ItemGeografico> getItemGeograficoDAO(){
		return (GenericDAO<ItemGeografico>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
	}
	
	public List<ItemGeografico> getPaisList(){
		GenericDAO<ItemGeografico> itemGeograficoDAO = this.getItemGeograficoDAO();
		String qry = "from ItemGeografico i where i.pai is null order by i.nome";	
		List<ItemGeografico> list = itemGeograficoDAO.findByHQL(qry);
		if(list==null) new ArrayList<ItemGeografico>(0);
		return list;
	}
	
	public List<ItemGeografico> getFilhoList(ItemGeografico pai){
		List<ItemGeografico> list = null;
		if(pai!=null){
			GenericDAO<ItemGeografico> itemGeograficoDAO = this.getItemGeograficoDAO();
			String qry = "from ItemGeografico i where i.pai="+pai.getId()+" order by i.nome";
			list = itemGeograficoDAO.findByHQL(qry);
		}
		if(list==null) new ArrayList<ItemGeografico>(0);
		return list;
	}
	
	/* metodos guia taxonomia */
	@SuppressWarnings("unchecked")
	public GenericDAO<ItemTaxonomico> getItemTaxonomicoDAO(){
		return (GenericDAO<ItemTaxonomico>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
	}
	
	public List<ItemTaxonomico> getReinoList(){
		GenericDAO<ItemTaxonomico> itemTaxonomicoDAO = this.getItemTaxonomicoDAO();
		String qry = "from ItemTaxonomico i where i.pai is null order by i.nome";	
		List<ItemTaxonomico> list = itemTaxonomicoDAO.findByHQL(qry);
		if(list==null) new ArrayList<ItemGeografico>(0);
		return list;
	}
	
	public List<ItemTaxonomico> getFilhoList(ItemTaxonomico pai){
		List<ItemTaxonomico> list = null;
		if(pai!=null){
			GenericDAO<ItemTaxonomico> itemTaxonomicoDAO = this.getItemTaxonomicoDAO();
			String qry = "from ItemTaxonomico i where i.pai="+pai.getId()+" order by i.nome";
			list = itemTaxonomicoDAO.findByHQL(qry);
		}
		if(list==null) new ArrayList<ItemTaxonomico>(0);
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<Entity> getEstacaoList(){
		GenericDAO<Entity> estacaoDAO = (GenericDAO<Entity>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		return estacaoDAO.getList(Estacao.class);
	}
}
