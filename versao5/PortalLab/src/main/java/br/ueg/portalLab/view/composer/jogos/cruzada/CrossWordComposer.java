package br.ueg.portalLab.view.composer.jogos.cruzada;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.builderSoft.view.zk.composer.ComposerController;
import br.ueg.portalLab.control.jogos.cruzada.CrossWordControl;
import br.ueg.portalLab.model.jogo.cruzada.Answer;
import br.ueg.portalLab.model.jogo.cruzada.CrossWord;
import br.ueg.portalLab.model.jogo.cruzada.Square;
import br.ueg.portalLab.model.jogo.cruzada.TypeAnswer;
import br.ueg.portalLab.view.component.jogos.cruzadas.ZKCrossWordComponent;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class CrossWordComposer extends ComposerController<CrossWord> {

	protected ArrayList<Integer> numbers20 = new ArrayList<Integer>(
			Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
					16, 17, 18, 19, 20));

	@AttributeView(key = "name", isEntityValue = true, fieldType = String.class, isVisible = true)
	private String fldNome;

	@AttributeView(key = "crossXDimension", isEntityValue = true, fieldType = Integer.class, isVisible = true)
	private Integer fldCrossXDimension;

	@AttributeView(key = "crossYDimension", isEntityValue = true, fieldType = Integer.class, isVisible = true)
	private Integer fldCrossYDimension;

	@AttributeView(key = "searchField", isEntityValue = false, fieldType = String.class, isVisible = true)
	private String searchField;
	@AttributeView(key = "squares", isEntityValue = false, fieldType = HashMap.class, isVisible = true)
	private HashMap<String,Square> squares = new HashMap<String,Square>();
	private String titleItensList = "Lista de Cruzadinhas";
	@Wire
	protected Window winCrossword;
	@AttributeView(key = "actualAnswer", isEntityValue = false, fieldType = Answer.class, isVisible = true)
	private Answer actualAnswer;
	private Square actualSquare;
	private TypeAnswer answerType;

	public String getFldNome() {
		return fldNome;
	}

	public void setFldNome(String fldNome) {
		this.fldNome = fldNome;
	}

	/*
	 * public Set<AgrupadorItensTaxonomicos> getFldItens() { return fldItens; }
	 * 
	 * public void setFldItens(Set<AgrupadorItensTaxonomicos> fldItens) {
	 * this.fldItens = fldItens; }
	 */
	
	 public String getSearchField() { return searchField; }
	 

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getTitleItensList() {
		return titleItensList;
	}

	public void setTitleItensList(String titleItensList) {
		this.titleItensList = titleItensList;
	}

	/*
	 * public AgrupadorItensTaxonomicos getSelectedAgrupadorItem() { return
	 * selectedAgrupadorItem; }
	 * 
	 * public void setSelectedAgrupadorItem( AgrupadorItensTaxonomicos
	 * selectedAgrupadorItem) { this.selectedAgrupadorItem =
	 * selectedAgrupadorItem; }
	 */

	/*
	 * public void initSetItens(){ setFldItens(getSelectedEntity().getItens());
	 * setTitleItensList("Lista de Itens Taxonomicos do Agrupador " +
	 * getSelectedEntity().getNome()); binder.loadAll(); }
	 */

	/*
	 * public void showWinItensTaxonomicos(){ Map<String, Object> data = new
	 * HashMap<String, Object>(); data.put("agrupador", getSelectedEntity());
	 * data.put("parentComposer", this);
	 * 
	 * Executions.createComponents("/pages/agrupador/treeItemTaxonomico.zul",
	 * null, data); }
	 */

	/*
	 * public void delteAgrupadorItem(){ if(doAction("DELETEAGRUPADORITEM")){
	 * getFldItens().remove(getSelectedAgrupadorItem()); binder.loadAll(); } }
	 */

	@Override
	public Control<CrossWord> getControl() {
		return SpringFactory.getInstance().getBean("crossWordControl",
				CrossWordControl.class);
	}

	@Override
	public Control<CrossWord> getNewControl(MessagesControl pMessagesControl) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return CrossWord.class;
	}

	@Override
	public Window getEditForm() {
		return winCrossword;
	}

	@Override
	public void setEditForm(Window form) {
		this.winCrossword = form;
	}

	public Integer getFldCrossXDimension() {
		return fldCrossXDimension;
	}

	public void setFldCrossXDimension(Integer fldCrossXDimension) {
		this.fldCrossXDimension = fldCrossXDimension;
	}

	public ArrayList<Integer> getListNumbers20() {
		return numbers20;
	}

	public void newCrossWord() {
		if (verifyEntityCrossword()) {
			ZKCrossWordComponent crossword = getCrosswordComponent();
			this.getSelectedEntity().setAnswers(new ArrayList<Answer>());
			this.getSelectedEntity().setSquares(new HashMap<String, Square>());
			this.getSelectedEntity().setCrossXDimension(
					fldCrossXDimension);
			this.getSelectedEntity().setCrossYDimension(
					fldCrossYDimension);
			Hbox modosCruzadinhaButtons = (Hbox) winCrossword
					.getFellow("modosCruzadinhasForm");
			modosCruzadinhaButtons.setVisible(true);
			crossword.loadCrossWord(this.getSelectedEntity());
		}
	}

	private boolean verifyEntityCrossword() {
		if ((fldCrossXDimension > 0 && fldCrossYDimension <= 20)
				&& (fldCrossXDimension) > 0 &&
					fldCrossYDimension <= 20)
			return true;
		else {
			Messagebox
					.show("Defina a quantidade de linhas e colunas com um número entre 1 e 20");
			return false;
		}
	}

	public Integer getFldCrossYDimension() {
		return fldCrossYDimension;
	}

	public void setFldCrossYDimension(Integer fldCrossYDimension) {
		this.fldCrossYDimension = fldCrossYDimension;
	}

	public void loadSquare() {
		ZKCrossWordComponent crossword = getCrosswordComponent();
		this.actualSquare = crossword.getClickedSquare();
		initCrossForm();
	}

	private void initCrossForm() {
		getCrossFormBox().setVisible(true);
		setAnswerType(null);
		getAnswerForm().setVisible(false);
		binder.saveAll();
	}

	private void clearCrossForm() {
		this.actualAnswer = null;
		this.actualSquare = null;
		getCrossFormBox().setVisible(false);
		setAnswerType(null);
		getAnswerForm().setVisible(false);
	}

	public void loadAnswer() {
		if (answerType == TypeAnswer.HORIZONTAL) {
			if (this.actualSquare.getHorizontalAnswer() != null) {
				this.actualAnswer = this.actualSquare.getHorizontalAnswer();
			} else {
				createNewAnswer();
			}
		} else {
			if (this.actualSquare.getVerticalAnswer() != null) {
				this.actualAnswer = this.actualSquare.getVerticalAnswer();
			} else {
				createNewAnswer();
			}
		}
		getAnswerForm().setVisible(true);
		binder.loadAll();
	}

	private void createNewAnswer() {
		this.actualAnswer = new Answer();
		this.actualAnswer.setType(answerType);
		Integer posX = Integer
				.parseInt(this.actualSquare.getId().split("\\.")[0]);
		Integer posY = Integer
				.parseInt(this.actualSquare.getId().split("\\.")[1]);
		;
		this.actualAnswer.setStartPositionX(posX);
		this.actualAnswer.setStartPositionY(posY);
		binder.loadAll();
	}

	public Vbox getCrossFormBox() {
		return (Vbox) winCrossword.getFellow("crossFormBox");
	}

	public HashMap<String, Square> getSquares() {
		return squares;
	}

	public void setSquares(HashMap<String, Square> squares) {
		this.squares = squares;
	}

	public Vbox getAnswerForm() {
		return (Vbox) winCrossword.getFellow("answerForm");
	}

	public Answer getActualAnswer() {
		return actualAnswer;
	}

	public void setActualAnswer(Answer actualAnswer) {
		this.actualAnswer = actualAnswer;
	}

	public Square getActualSquare() {
		return actualSquare;
	}

	public void setActualSquare(Square actualSquare) {
		this.actualSquare = actualSquare;
	}

	public TypeAnswer getAnswerType() {
		return answerType;
	}

	public void setAnswerType(TypeAnswer answerType) {
		this.answerType = answerType;
	}

	public ZKCrossWordComponent getCrosswordComponent() {
		return (ZKCrossWordComponent) winCrossword.getFellow("crossword");
	}

	private void reloadCrossWord() {
		getCrosswordComponent().loadCrossWord(this.getSelectedEntity());
		setSquares(getCrosswordComponent().getCrossword().getSquares());
		clearCrossForm();

	}

	public void setPreviewMode() {
		getCrosswordComponent().setAdmMode(false);
		reloadCrossWord();
	}

	public void setAdmMode() {
		getCrosswordComponent().setAdmMode(true);
		reloadCrossWord();
	}

	public List<TypeAnswer> getAnswerTypes() {
		return Arrays.asList(TypeAnswer.values());
	}

	public void saveAnswer() {
		if (this.doAction("validateAnswer",false)) {
			Integer answerPosition = getSelectedEntity().getAnswers().indexOf(
					this.actualAnswer);
			if (answerPosition != -1) {
				getSelectedEntity().getAnswers().set(answerPosition,
						this.actualAnswer);
			} else {
				getSelectedEntity().getAnswers().add(this.actualAnswer);
			}
			reloadCrossWord();
		}
	}
	
	public boolean doAction(@BindingParam("action") String action,boolean hideEditForm) {
		boolean result = false;
		binder.saveAll();	
		if (genericControl.doAction(action, initializeEntity())) {
			verifyListing(action);
			if (hideEditForm)
			{
				hideEditForm();
			}
			result = true;
				
		}
		binder.loadAll();
		return result;
	}

}
