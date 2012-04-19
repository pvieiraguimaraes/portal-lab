package br.ueg.builderSoft.security.view.composer;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.util.GenericForwardComposer;

import br.ueg.builderSoft.security.access.decision.CurrentUserDecisionManager;
import br.ueg.builderSoft.util.sets.SpringFactory;

@SuppressWarnings("rawtypes")
public abstract class BaseComposer extends GenericForwardComposer {

	private static final long serialVersionUID = 3014876491772775910L;
	
	
	@Autowired
	private CurrentUserDecisionManager currentUserDecisionManager;
	

	public boolean isUseCaseGranted(String useCase) {
		return getCurrentUserDecisionManager().decide(useCase);
	}


	
//	protected void setVisibleComponentsByFuncionality(Component[] components, String[] funcionalities){
//		int i = 0;
//		for(Component component :components){
//			component.setVisible(isFunctionalityGranted(funcionalities[i]));
//			i++;
//		}
//	}
//	


	public Boolean isFunctionalityGranted(String useCase, String functionality) {
		return getCurrentUserDecisionManager().decide(useCase, functionality);
	}





	protected CurrentUserDecisionManager getCurrentUserDecisionManager() {
		if (currentUserDecisionManager == null)
			currentUserDecisionManager = (CurrentUserDecisionManager) SpringFactory.getInstance().getBean("CurrentUserDecisionManager",CurrentUserDecisionManager.class);
		return currentUserDecisionManager;
	}

	public void setCurrentUserDecisionManager(CurrentUserDecisionManager currentUserDecisionManager) {
		this.currentUserDecisionManager = currentUserDecisionManager;
	}
	

}
