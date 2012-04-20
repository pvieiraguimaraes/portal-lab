package br.ueg.builderSoft.security.control;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.builderSoft.security.model.Usuario;
import br.ueg.builderSoft.security.model.CasoDeUso;
import br.ueg.builderSoft.security.model.CasoDeUsoFuncionalidade;
import br.ueg.builderSoft.security.model.Funcionalidade;
import br.ueg.builderSoft.security.model.GrupoUsuario;
import br.ueg.builderSoft.security.model.UsuarioPermissao;

@SuppressWarnings("unused")
@Service
public class UsuarioControl extends Control<Usuario> {

	private Usuario selectedUsuario;
	
	private CasoDeUso selectedCasoDeUso;
	
	public UsuarioControl(MessagesControl control) {
		super(control);
		 //nivelGeoDAO = (GenericDAO<CategoriaUsuario>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
	}
	public UsuarioControl(){
		
	}
	@SuppressWarnings("unchecked")
	@Override
	protected GenericDAO<Usuario> getPersistence() {
		if(persistence==null){
			this.persistence = (GenericDAO<Usuario>) SpringFactory.getInstance().getBean("genericDAOSecurity", GenericDAO.class);
		}
		return persistence;
	}
	
	
	
	public boolean isFuncionalidadePresenteUsuario(Funcionalidade funcionalidade){
		if(selectedUsuario== null) return false;
		Set<UsuarioPermissao> funcUsuarioList = this.getSelectedUsuario().getPermissoes();
		for(UsuarioPermissao up : funcUsuarioList){
			CasoDeUso casoDeUso = up.getCasoDeUsoFuncionalidade().getCasoDeUso();
			Funcionalidade funcionalidade2 = up.getCasoDeUsoFuncionalidade().getFuncionalidade();
			if(casoDeUso.getId().equals(this.getSelectedCasoDeUso().getId()) && funcionalidade2.getId().equals(funcionalidade.getId())){
				return true;
			}
		}
		return false;

	}
	/**
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	private GenericDAO<CasoDeUsoFuncionalidade> getCasoDeUsoFuncionalidadeDAO() {
		return (GenericDAO<CasoDeUsoFuncionalidade>)SpringFactory.getInstance().getBean("genericDAOSecurity", GenericDAO.class);
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
	public Set<UsuarioPermissao> getUsuarioPermissaoList(){
		GenericDAO<CasoDeUsoFuncionalidade> casoDeUsoFuncionalidadeDAO = (GenericDAO<CasoDeUsoFuncionalidade>)SpringFactory.getInstance().getBean("genericDAOSecurity", GenericDAO.class);
		
		Set<UsuarioPermissao> listUsuarioPermissao = new HashSet<UsuarioPermissao>(0);
		
		if(this.getSelectedCasoDeUso()!=null){
			CasoDeUsoFuncionalidade cafu = new CasoDeUsoFuncionalidade();
			cafu.setCasoDeUso(this.getSelectedCasoDeUso());			
			List<CasoDeUsoFuncionalidade> listCasoDeUsoFuncionalidades = casoDeUsoFuncionalidadeDAO.findByEntity(cafu);
			long numUserPermissoes = 0;
			for(CasoDeUsoFuncionalidade f: listCasoDeUsoFuncionalidades){
				if(!isFuncionalidadePresenteUsuario(f.getFuncionalidade())){	
					UsuarioPermissao up = new UsuarioPermissao();
					up.setId(numUserPermissoes++);
					up.setUsuario(this.getSelectedUsuario());
					up.setCasoDeUsoFuncionalidade(f);
					listUsuarioPermissao.add(up);
				}
			}			
		}
		
		
		return listUsuarioPermissao;
	}
	
	
	@SuppressWarnings("unchecked")
	public Set<CasoDeUso> getCasoDeUsoList(){
		GenericDAO<CasoDeUso> casoDeUsoDAO = (GenericDAO<CasoDeUso>)SpringFactory.getInstance().getBean("genericDAOSecurity", GenericDAO.class);
		
		Set<CasoDeUso> listCasoDeUso = new HashSet<CasoDeUso>();
		
		listCasoDeUso.addAll(casoDeUsoDAO.getList(new CasoDeUso()));
		
		
		
		return listCasoDeUso;
	}
	
	@SuppressWarnings("unchecked")
	public Set<GrupoUsuario> getGrupoUsuarioList(){
		Set<GrupoUsuario> listGrupoUsuario = new HashSet<GrupoUsuario>();
		if(this.getSelectedUsuario()!=null){
			GenericDAO<GrupoUsuario> grupoUsuarioDAO = (GenericDAO<GrupoUsuario>)SpringFactory.getInstance().getBean("genericDAOSecurity", GenericDAO.class);				
			
			List<GrupoUsuario> list =  grupoUsuarioDAO.getList(new GrupoUsuario());
			listGrupoUsuario.addAll(list);
			for(GrupoUsuario gu : list){
				for(GrupoUsuario gg: this.getSelectedUsuario().getGrupos()){
					if(gg.getId()!=null && gg.getId().equals(gu.getId())){
						listGrupoUsuario.remove(gu);
					}
				}
			}			
		}
		
		return listGrupoUsuario;
	}
		
	
	
	/**
	 * @return the selectedGrupoUsuario
	 */
	public Usuario getSelectedUsuario() {
		return selectedUsuario;
	}
	/**
	 * @param selectedUsuario the selectedGrupoUsuario to set
	 */
	public void setSelectedUsuario(Usuario selectedUsuario) {
		this.selectedUsuario = selectedUsuario;
	}
	public CasoDeUso getSelectedCasoDeUso() {
		return selectedCasoDeUso;
	}
	public void setSelectedCasoDeUso(CasoDeUso selectedCasoDeUso) {
		this.selectedCasoDeUso = selectedCasoDeUso;
	}
}
