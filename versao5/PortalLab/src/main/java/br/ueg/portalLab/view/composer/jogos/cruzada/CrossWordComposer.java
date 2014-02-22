package br.ueg.portalLab.view.composer.jogos.cruzada;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.hibernate.collection.internal.PersistentBag;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.hibernate.HibernateUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
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

	protected ArrayList<Integer> numbers30 = new ArrayList<Integer>(
			Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
					16, 17, 18, 19, 20,21,22,23,24,25,26,27,28,29,30));

	@AttributeView(key = "name", isEntityValue = true, fieldType = String.class, isVisible = true)
	private String fldName = "";

	@AttributeView(key = "buscaCruzadinha", isSearchField = true, isEntityValue = false, fieldType = String.class, isVisible = true)
	private String fldBuscaCruzadinha = "%";

	public String getFldBuscaCruzadinha() {
		return fldBuscaCruzadinha;
	}

	public void setFldBuscaCruzadinha(String fldBuscaCruzadinha) {
		this.fldBuscaCruzadinha = fldBuscaCruzadinha;
	}

	@AttributeView(key = "crossXDimension", isEntityValue = true, fieldType = Integer.class, isVisible = true)
	private Integer fldCrossXDimension = 0;

	@AttributeView(key = "crossYDimension", isEntityValue = true, fieldType = Integer.class, isVisible = true)
	private Integer fldCrossYDimension = 0;

	@AttributeView(key = "searchField", isEntityValue = false, fieldType = String.class, isVisible = true)
	private String searchField;
	@AttributeView(key = "squares", isEntityValue = false, fieldType = HashMap.class, isVisible = true)
	private HashMap<String, Square> squares = new HashMap<String, Square>();
	private String titleItensList = "Lista de Cruzadinhas";
	@Wire
	protected Window winCrossword;
	@AttributeView(key = "actualAnswer", isEntityValue = false, fieldType = Answer.class, isVisible = true)
	private Answer actualAnswer;
	private Square actualSquare;
	private TypeAnswer answerType;
	@AttributeView(key = "answers", isEntityValue = true, fieldType = List.class, isVisible = true)
	private List<Answer> fldAnswers = new ArrayList<Answer>();

	public String getFldName() {
		return fldName;
	}

	public void setFldName(String fldName) {
		this.fldName = fldName;
	}

	/*
	 * public Set<AgrupadorItensTaxonomicos> getFldItens() { return fldItens; }
	 * 
	 * public void setFldItens(Set<AgrupadorItensTaxonomicos> fldItens) {
	 * this.fldItens = fldItens; }
	 */

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getTitleItensList() {
		return titleItensList;
	}

	public void setTitleItensList(String titleItensList) {
		this.titleItensList = titleItensList;
	}

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

	public ArrayList<Integer> getListNumbers30() {
		return numbers30;
	}

	public void newCrossWord() {
		if (verifyEntityCrossword()) {
			ZKCrossWordComponent crossword = getCrosswordComponent();
			generateNewCrossword();
			Hbox modosCruzadinhaButtons = (Hbox) winCrossword
					.getFellow("modosCruzadinhasForm");
			modosCruzadinhaButtons.setVisible(true);
			crossword.loadCrossWord(this.getSelectedEntity());
		}
	}

	public void generateNewCrossword()
	{
		this.getSelectedEntity().setAnswers(new ArrayList<Answer>());
		this.getSelectedEntity().setSquares(new HashMap<String, Square>());
		this.getSelectedEntity().setCrossXDimension(fldCrossXDimension);
		this.getSelectedEntity().setCrossYDimension(fldCrossYDimension);
	}
	
	private boolean verifyEntityCrossword() {
		if ((fldCrossXDimension > 0 && fldCrossYDimension <= 30)
				&& (fldCrossXDimension) > 0 && fldCrossYDimension <= 30)
			return true;
		else {
			Messagebox
					.show("Defina a quantidade de linhas e colunas com um número entre 1 e 30");
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
		getComboType().setRawValue(null);
		getAnswerForm().setVisible(false);
		binder.loadAll();
	}

	private void clearCrossForm() {
		this.actualAnswer = null;
		this.actualSquare = null;
		getCrossFormBox().setVisible(false);
		setAnswerType(null);
		getAnswerForm().setVisible(false);
	}

	public void loadAnswer() {
		getBtnRemoveAnswerButton().setVisible(false);
		if (answerType == TypeAnswer.HORIZONTAL) {
			if (this.actualSquare.getHorizontalAnswer() != null) {
				this.actualAnswer = this.actualSquare.getHorizontalAnswer();
				getBtnRemoveAnswerButton().setVisible(true);
			} else {
				createNewAnswer();
			}
		} else {
			if (this.actualSquare.getVerticalAnswer() != null) {
				this.actualAnswer = this.actualSquare.getVerticalAnswer();
				getBtnRemoveAnswerButton().setVisible(true);
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
		this.actualAnswer.setFixe(false);
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

	public Combobox getComboType() {
		return (Combobox) winCrossword.getFellow("comboType");
	}

	private void reloadCrossWord() {
		getSelectedEntity().setAnswers(fldAnswers);
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
		//actualAnswer.setValue(actualAnswer.getValue().toUpperCase());
		if (this.doAction("validateAnswer", false)) {
			actualAnswer.setCrossword(getSelectedEntity());
			Integer answerPosition = fldAnswers.indexOf(this.actualAnswer);
			if (answerPosition != -1) {
				fldAnswers.set(answerPosition, this.actualAnswer);
			} else {
				fldAnswers.add(this.actualAnswer);
			}
			reloadCrossWord();
		}
	}

	public void removeAnswer() {
		fldAnswers.remove(actualAnswer);
		reloadCrossWord();
	}

	public boolean doAction(@BindingParam("action") String action,
			boolean hideEditForm) {
		boolean result = false;
		// binder.saveAll();

		if (genericControl.doAction(action, initializeEntity())) {
			verifyListing(action);
			if (hideEditForm) {
				hideEditForm();
			}
			result = true;

		}
		binder.loadAll();
		return result;
	}
	
	@Override
	public void hideEditForm(){
		getEditForm().setVisible(false);
		clearEditForm();
	}
	
	@Override
	public boolean editEntity(){
		binder.saveAll();
		loadEditForm();
		this.genericControl.associateEntityToAttributeView(this.getSelectedEntity());
		binder.loadComponent(this.getEditForm());
		//TODO descobrir uma forma de não fazer isso(ler tudo, deveria funcionar só com o comando acima, 
		//quando o formulário é construido automaticamente.
		binder.loadAll();
		loadCrossForm();
		//binder.saveAll();
		this.showEditForm();
		return true;
	}

	private void loadCrossForm()
	{
		Hbox boxDefinitionLarge = (Hbox) winCrossword.getFellow("hboxDefinicaoTamanho");
		boxDefinitionLarge.setVisible(false);
		Hbox modosCruzadinhaButtons = (Hbox) winCrossword
				.getFellow("modosCruzadinhasForm");
		modosCruzadinhaButtons.setVisible(true);
		getCrosswordComponent().loadCrossWord(this.getSelectedEntity());
		
	}
	
	private void clearEditForm() {
		setFldCrossXDimension(0);
		setFldCrossYDimension(0);
		setFldName("");
		setSquares(new HashMap<String, Square>());
		setFldAnswers(new ArrayList<Answer>());
		//binder.saveAll();
		winCrossword.getChildren().clear();
		/*
		clearComboCrosswordXY();
		clearCrossForm();
		getComboType().setRawValue(null);
		getCrossFormBox().setVisible(false);
		this.setSelectedEntity(this.initializeEntity());
		this.genericControl.associateEntityToAttributeView(this.getSelectedEntity());
		Hbox modosCruzadinhaButtons = (Hbox) winCrossword
				.getFellow("modosCruzadinhasForm");
		modosCruzadinhaButtons.setVisible(false);
		//getCrosswordComponent().loadCrossWord(getSelectedEntity());
		 */
	}

	public Button getBtnRemoveAnswerButton() {
		return (Button) winCrossword.getFellow("btnRemove");
	}

	public void newEntity() {
		loadEditForm();
		super.newEntity();

	}
	public void testCrossWord(){
		binder.saveAll();
		Executions.sendRedirect("/public/crossword.zul?id="+this.getSelectedEntity().getId());
	}

	private void loadEditForm() {
		Executions.createComponents("/pages/jogocruzadinha/editForm.zul", winCrossword,null);
		binder = new AnnotateDataBinder(component);
		binder.setLoadOnSave(false);
		
	}

	/*
	private void clearComboCrosswordXY() {
		//( (Combobox) winCrossword.getFellow("cmbCrossX")).setRawValue(0) ;
		//( (Combobox) winCrossword.getFellow("cmbCrossY")).setRawValue(0) ;
	}
	*/

	public List<Answer> getFldAnswers() {
		return fldAnswers;
	}

	public void setFldAnswers(List<Answer> fldAnswers) {
		this.fldAnswers = fldAnswers;
	}

	/*
	@Override
	public boolean saveEntity() {
		if (this.doAction("SAVE")) {
			this.hideEditForm();
			return true;
		}
		return false;
	}
	*/
	
	}
