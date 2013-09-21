package br.ueg.portalLab.persistence;

import java.util.List;

import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.security.manager.IProfile;
import br.ueg.portalLab.model.Usuario;
import br.ueg.portalLab.security.model.CasoDeUsoFuncionalidade;
import br.ueg.portalLab.security.model.GrupoUsuario;
import br.ueg.portalLab.security.model.UsuarioPermissao;

public class ControleAcesso extends GenericDAO<Usuario> implements IProfile {
	
	
	public ControleAcesso(){	
	}
	
	@Override
	public boolean isGrantedFunctionality(String user, String profile, String useCase, String functionality) {
		
		Usuario usuario = new Usuario();
		usuario.setLogin(user);
		this.getSession().clear();
		List<Usuario> list = this.findByEntity(usuario);
		if(list!=null && list.size()>0){
			Usuario u = list.get(0);
			for(UsuarioPermissao up: u.getPermissoes()){				
				if(up.getCasoDeUsoFuncionalidade().getCasoDeUso().getNome().equals(useCase)){
					if(functionality!=null 
							&& up.getCasoDeUsoFuncionalidade().getFuncionalidade().getNome().equalsIgnoreCase(functionality)
							&& up.getPermitido()){						
						return true;
					}
					if(functionality!=null && !up.getPermitido()) return false;
					if(functionality==null){
						return true;
					}
				}				
			}
			
			
			for(GrupoUsuario g: u.getGrupos()){
				for(CasoDeUsoFuncionalidade cf: g.getFuncionalidades()){
					if(cf.getCasoDeUso().getNome().equalsIgnoreCase(useCase)){
						if(functionality!=null && cf.getFuncionalidade().getNome().equalsIgnoreCase(functionality)){
							return true;
						}					
						if(functionality==null){
							return true;
						}
					}
				}
			}			
		}
		return false;
	}

	@Override
	public boolean isGrantedUseCase(String user, String profile, String useCase) {
		// TODO Auto-generated method stub
		return this.isGrantedFunctionality(user, profile, useCase, null);
	}
	

}
