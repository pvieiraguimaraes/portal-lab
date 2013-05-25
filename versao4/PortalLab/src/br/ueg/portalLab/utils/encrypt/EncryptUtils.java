package br.ueg.portalLab.utils.encrypt;

import java.util.Iterator;
import java.util.List;


import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.util.encrypt.EncryptPassword;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.model.Usuario;

public class EncryptUtils {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void encryptAllPasswords(){
		
		GenericDAO<Entity> usuarioDAO = (GenericDAO<Entity>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		List list =  usuarioDAO.getList(Usuario.class);
		
		for(Iterator<Usuario> i = list.iterator(); i.hasNext();){
			Usuario user = i.next();
			user.setSenha(EncryptPassword.encrypt(user.getSenha()));
			
			try {
				usuarioDAO.save(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		EncryptUtils.encryptAllPasswords();
	}
}
