package br.ueg.portalLab.security.control;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.security.model.CasoDeUso;
import br.ueg.portalLab.security.model.CasoDeUsoFuncionalidade;
import br.ueg.portalLab.security.model.Funcionalidade;
import br.ueg.portalLab.security.model.GrupoUsuario;

@Service
public class GrupoUsuarioControl extends Control<GrupoUsuario> {

	GenericDAO<GrupoUsuario> grupoUsuarioDAO = null;
	
	private GrupoUsuario selectedGrupoUsuario;
	
	private CasoDeUso selectedCasoDeUso;
	
	public GrupoUsuarioControl(MessagesControl control) {
		super(control);
		 //control.addController(new UsuarioValidatorControl((MessagesControl)control.getController(MessagesControl.class),1));
	}
	public GrupoUsuarioControl(){
		
	}

	
//	public Set<CategoriaUsuario> getListCategoriaUsuario(){
//		
//		Set<CategoriaUsuario> list = new HashSet<CategoriaUsuario>(nivelGeoDAO.getList(new CategoriaUsuario()));
//		return list;
//	}
	public boolean isFuncionalidadePresenteGrupoUsuario(Funcionalidade funcionalidade){
		if(selectedGrupoUsuario== null) return false;
		Set<CasoDeUsoFuncionalidade> funcGrupoUsuarioList = this.getSelectedGrupoUsuario().getFuncionalidades();
		for(CasoDeUsoFuncionalidade cafu : funcGrupoUsuarioList){
			CasoDeUso casoDeUso = cafu.getCasoDeUso();
			Funcionalidade funcionalidade2 = cafu.getFuncionalidade();
			if(casoDeUso.getId().equals(this.getSelectedCasoDeUso().getId()) && funcionalidade2.getId().equals(funcionalidade.getId())){
				return true;
			}
		}
		return false;

	}
	/**
	 * @return
	 */
	private GenericDAO<CasoDeUsoFuncionalidade> getCasoDeUsoFuncionalidadeDAO() {
		return (GenericDAO<CasoDeUsoFuncionalidade>)SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
	}
	
//	public Set<CasoDeUsoFuncionalidade> getCasoDeUsoFuncionalidades(){
//		HashSet<CasoDeUsoFuncionalidade> list = new HashSet<CasoDeUsoFuncionalidade>(0);
//		
//		Set<CasoDeUsoFuncionalidade> listGrupoUsuario = this.getSelectedGrupoUsuario().getFuncionalidades();
//		
//		if(listGrupoUsuario!=null && listGrupoUsuario.size()>0){
//			list = new HashSet<CasoDeUsoFuncionalidade>(listGrupoUsuario);
//		}		
//		return list;
//	}

	@SuppressWarnings("unchecked")
	public Set<CasoDeUsoFuncionalidade> getFuncionalidadeList(){
		GenericDAO<CasoDeUsoFuncionalidade> casoDeUsoFuncionalidadeDAO = (GenericDAO<CasoDeUsoFuncionalidade>)SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		
		Set<CasoDeUsoFuncionalidade> listCasoDeUsoFuncionalidade = new HashSet<CasoDeUsoFuncionalidade>();
		
		if(this.getSelectedCasoDeUso()!=null){
			CasoDeUsoFuncionalidade cafu = new CasoDeUsoFuncionalidade();
			cafu.setCasoDeUso(this.getSelectedCasoDeUso());			
			List<CasoDeUsoFuncionalidade> listFuncionalidades = casoDeUsoFuncionalidadeDAO.findByEntity(cafu);
			long numFuncs = 0;
			for(CasoDeUsoFuncionalidade f: listFuncionalidades){
				if(!isFuncionalidadePresenteGrupoUsuario(f.getFuncionalidade())){					
					listCasoDeUsoFuncionalidade.add(f);
				}
			}			
		}
		
		
		return listCasoDeUsoFuncionalidade;
	}
	@SuppressWarnings("unchecked")
	public Set<CasoDeUso> getCasoDeUsoList(){
		GenericDAO<CasoDeUso> casoDeUsoDAO = (GenericDAO<CasoDeUso>)SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		
		Set<CasoDeUso> listCasoDeUso = new HashSet<CasoDeUso>();
		
		listCasoDeUso.addAll(casoDeUsoDAO.getList(new CasoDeUso()));
		
		
		
		return listCasoDeUso;
	}
	
	
	/**
	 * @return the selectedGrupoUsuario
	 */
	public GrupoUsuario getSelectedGrupoUsuario() {
		return selectedGrupoUsuario;
	}
	/**
	 * @param selectedGrupoUsuario the selectedGrupoUsuario to set
	 */
	public void setSelectedGrupoUsuario(GrupoUsuario selectedGrupoUsuario) {
		this.selectedGrupoUsuario = selectedGrupoUsuario;
	}
	public CasoDeUso getSelectedCasoDeUso() {
		return selectedCasoDeUso;
	}
	public void setSelectedCasoDeUso(CasoDeUso selectedCasoDeUso) {
		this.selectedCasoDeUso = selectedCasoDeUso;
	}
}
