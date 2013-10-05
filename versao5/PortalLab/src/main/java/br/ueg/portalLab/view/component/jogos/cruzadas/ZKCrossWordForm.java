package br.ueg.portalLab.view.component.jogos.cruzadas;

import java.util.List;

import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Vlayout;

import br.ueg.portalLab.model.jogo.cruzada.Answer;

// @org.springframework.stereotype.Component
// @Scope("desktop")
public class ZKCrossWordForm extends GenericForwardComposer{

	private Vlayout actualContainer;
	private List<Answer> answers;
	
	

	public void setActualContainer(Vlayout croswordFormContainer) {
		this.actualContainer = croswordFormContainer;
		
	}
	
	public void selectHint(Answer answer)
	{
		Integer hintId = getAnswers().indexOf(answer);
		Label hintLabel = (Label) this.actualContainer.getFellow("hint" + hintId);
		clearAllHintMark();
		hintLabel.setSclass("hintLabelMark");
		
	}

	private void clearAllHintMark() {
		for(Integer i = 0; i< answers.size(); i++)
		{
			Label hintLabel = (Label) this.actualContainer.getFellow("hint" + i);
			hintLabel.setSclass("hintLabel");
		}
		
	}

	public void cronstructForm(List<Answer> answers,
			Vlayout croswordFormContainer) {
		croswordFormContainer.getChildren().clear();
		setAnswers(answers);
		for(Integer i = 0; i< answers.size(); i++)
		{
			Label hint = new Label();
			hint.setValue(answers.get(i).getHint());
			hint.setId("hint" + i.toString());
			actualContainer.appendChild(hint);
			hint.setSclass("hintLabel");
		}
		
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	
}
