package br.ueg.portalLab.security.control;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.model.ItemGeografico;
import br.ueg.portalLab.model.NivelGeografico;
import br.ueg.portalLab.security.model.CasoDeUso;
import br.ueg.portalLab.security.model.CasoDeUsoFuncionalidade;
import br.ueg.portalLab.security.model.Funcionalidade;

@Service
public class CasoDeUsoControl extends Control<CasoDeUso> {

	GenericDAO<CasoDeUso> casoDeUsoDAO = null;
	
	private CasoDeUso selectedCasoDeUso;
	
	@SuppressWarnings("unchecked")
	public CasoDeUsoControl(MessagesControl control) {
		super(control);
		 //control.addController(new UsuarioValidatorControl((MessagesControl)control.getController(MessagesControl.class),1));
	}
	public CasoDeUsoControl(){
		
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
private GenericDAO<CasoDeUsoFuncionalidade> getCasoDeUsoFuncionalidadeDAO() {
	return (GenericDAO<CasoDeUsoFuncionalidade>)SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
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
		GenericDAO<Funcionalidade> funcionalidadeDAO = (GenericDAO<Funcionalidade>)SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		
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
