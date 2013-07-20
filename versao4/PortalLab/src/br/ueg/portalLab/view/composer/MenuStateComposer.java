package br.ueg.portalLab.view.composer;

import java.awt.Menu;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.West;

@SuppressWarnings("rawtypes")
@Component
@Scope("session")
public class MenuStateComposer extends GenericForwardComposer {

	public static final String MENU_STATE_COMPOSER_MENU_OPEN = "MenuStateComposer.MenuOpen";

	/**
	 * 
	 */
	private static final long serialVersionUID = -7013854473654426346L;


	/**
	 * @return the menuOpen
	 */
	public static boolean getMenuOpen() {
		Boolean vMenuState = (Boolean) Executions.getCurrent().getSession().getAttribute(MENU_STATE_COMPOSER_MENU_OPEN);
		if(vMenuState==null) vMenuState = new Boolean(false);
		return vMenuState;
	}

	/**
	 * @param menuOpen the menuOpen to set
	 */
	public static void setMenuOpen(boolean menuOpen) {
	    Executions.getCurrent().getSession().setAttribute(MENU_STATE_COMPOSER_MENU_OPEN, new Boolean(menuOpen));
	}
	
	public static void changeMenuSate(){
		MenuStateComposer.setMenuOpen(!MenuStateComposer.getMenuOpen());
	}
	
	public static void updateMenu(West west){
		west.setOpen(MenuStateComposer.getMenuOpen());		
	}
}
