package br.ueg.builderSoft.security.control;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.security.model.CasoDeUso;
import br.ueg.builderSoft.security.model.CasoDeUsoFuncionalidade;
import br.ueg.builderSoft.security.model.Funcionalidade;
import br.ueg.builderSoft.security.model.GrupoUsuario;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.sets.SpringFactory;

@Service
public class CasoDeUsoControl extends Control<CasoDeUso> {

	GenericDAO<CasoDeUso> casoDeUsoDAO = null;
	
	private CasoDeUso selectedCasoDeUso;
	
	public CasoDeUsoControl(MessagesControl control) {
		super(control);
		 //control.addController(new UsuarioValidatorControl((MessagesControl)control.getController(MessagesControl.class),1));
	}
	public CasoDeUsoControl(){
		
	}
	@SuppressWarnings("unchecked")
	@Override
	protected GenericDAO<CasoDeUso> getPersistence() {
		if(persistence==null){
			this.persistence = (GenericDAO<CasoDeUso>) SpringFactory.getInstance().getBean("genericDAOSecurity", GenericDAO.class);
		}
		return persistence;
	}

	
//	public Set<CategoriaUsuario> getListCategoriaUsuario(){
//		
//		Set<CategoriaUsuario> list = new HashSet<CategoriaUsuario>(nivelGeoDAO.getList(new CategoriaUsuario()));
//		return list;
//	}
	public boolean isFuncionalidadePresenteCasoDeUso(Funcionalidade funcionalidade){
		if(selectedCasoDeUso== null) return false;
		
		CasoDeUsoFuncionalidade cu = new CasoDeUsoFuncionalidade();
		cu.setCasoDeUso(selectedCasoDeUso);
		cu.setFuncionalidade(funcionalidade);
		List<CasoDeUsoFuncionalidade> casoDeUsoFuncionalidadeList = getCasoDeUsoFuncionalidadeDAO().findByEntity(cu);
		if(casoDeUsoFuncionalidadeList!=null && casoDeUsoFuncionalidadeList.size()>0){
			return true;
		}else{
			return false;
		}

	}
/**
 * @return
 */
@SuppressWarnings("unchecked")
private GenericDAO<CasoDeUsoFuncionalidade> getCasoDeUsoFuncionalidadeDAO() {
	return (GenericDAO<CasoDeUsoFuncionalidade>)SpringFactory.getInstance().getBean("genericDAOSecurity", GenericDAO.class);
}
	public Set<CasoDeUsoFuncionalidade> getCasoDeUsoFuncionalidades(){
		HashSet<CasoDeUsoFuncionalidade> list = new HashSet<CasoDeUsoFuncionalidade>(0);
		
		CasoDeUsoFuncionalidade cafu = new CasoDeUsoFuncionalidade();
		
		cafu.setCasoDeUso(this.getSelectedCasoDeUso());
		List<CasoDeUsoFuncionalidade> listCasoDeUso = getCasoDeUsoFuncionalidadeDAO().findByEntity(cafu);
		
		if(listCasoDeUso!=null && listCasoDeUso.size()>0){
			list = new HashSet<CasoDeUsoFuncionalidade>(listCasoDeUso);
		}		
		return list;
	}

	@SuppressWarnings("unchecked")
	public Set<CasoDeUsoFuncionalidade> getFuncionalidadeList(){
		GenericDAO<Funcionalidade> funcionalidadeDAO = (GenericDAO<Funcionalidade>)SpringFactory.getInstance().getBean("genericDAOSecurity", GenericDAO.class);
		
		List<Funcionalidade> listFuncionalidades = funcionalidadeDAO.getList(new Funcionalidade());
		Set<CasoDeUsoFuncionalidade> listCasoDeUsoFuncionalidade = new HashSet<CasoDeUsoFuncionalidade>();
		long numFuncs = 0;
		for(Funcionalidade f: listFuncionalidades){
			if(!isFuncionalidadePresenteCasoDeUso(f)){
				CasoDeUsoFuncionalidade casoDeUsoFuncionalidade = new CasoDeUsoFuncionalidade();
				casoDeUsoFuncionalidade.setControleInsercaoPadroa(false);
				casoDeUsoFuncionalidade.setCasoDeUso(this.getSelectedCasoDeUso());
				casoDeUsoFuncionalidade.setFuncionalidade(f);
				casoDeUsoFuncionalidade.setId(numFuncs++);
				listCasoDeUsoFuncionalidade.add(casoDeUsoFuncionalidade);
			}
		}
		
		
		return listCasoDeUsoFuncionalidade;
	}
	/**
	 * @return the selectedCasoDeUso
	 */
	public CasoDeUso getSelectedCasoDeUso() {
		return selectedCasoDeUso;
	}
	/**
	 * @param selectedCasoDeUso the selectedCasoDeUso to set
	 */
	public void setSelectedCasoDeUso(CasoDeUso selectedCasoDeUso) {
		this.selectedCasoDeUso = selectedCasoDeUso;
	}
}
