package br.ueg.portalLab.view.composer.jogos.memoria;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.builderSoft.view.zk.composer.ComposerController;
import br.ueg.portalLab.control.jogos.memoria.MemoriaControl;
import br.ueg.portalLab.model.jogo.memoria.Memory;
import br.ueg.portalLab.model.jogo.memoria.Piece;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class MemoriaComposer extends ComposerController<Memory> {

	@AttributeView(key = "name", isEntityValue = true, fieldType = String.class, isVisible=true, caption="memory_nameColumn")
	private String fldName;
	
	@AttributeView(key = "numberPieces", isEntityValue = true, fieldType = Integer.class, isVisible=true, caption="memory_numberPiecesColumn")
	private Integer fldNumberPieces;
	
	@AttributeView(key = "piece", isEntityValue = false, fieldType = Piece.class, isVisible=true)
	private Piece piece;
	
	private Piece selectedPeca;
	
	private Window winMemoria;
	
	private Window winPeca;
	
	private Textbox fldPathImage;
	
	private Textbox fldPathImageSucceed;
	
	public String getFldName() {
		return fldName;
	}

	public void setFldName(String fldName) {
		this.fldName = fldName;
	}

	public Integer getFldNumberPieces() {
		return fldNumberPieces;
	}

	public void setFldNumberPieces(Integer fldNumberPieces) {
		this.fldNumberPieces = fldNumberPieces;
	}

	public Piece getSelectedPeca() {
		return selectedPeca;
	}

	public void setSelectedPeca(Piece selectedPeca) {
		this.selectedPeca = selectedPeca;
	}

	public Window getWinPeca() {
		return winPeca;
	}

	public void setWinPeca(Window winPeca) {
		this.winPeca = winPeca;
	}
	
	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public Textbox getFldPathImage() {
		return fldPathImage;
	}

	public void setFldPathImage(Textbox fldPathImage) {
		this.fldPathImage = fldPathImage;
	}

	public Textbox getFldPathImageSucceed() {
		return fldPathImageSucceed;
	}

	public void setFldPathImageSucceed(Textbox fldPathImageSucceed) {
		this.fldPathImageSucceed = fldPathImageSucceed;
	}

	public void showPieceForm(){
		if(getSelectedEntity() != null){
			setPiece(new Piece());
			showEditForm(getWinPeca());
		}
	}

	public void setImagePiece(UploadEvent event){
		getPiece().setImage(event.getMedia().getStreamData());
		((Textbox) getWinPeca().getFellow("fldPathImage")).setValue(event.getMedia().getName());
	}
	
	public void setImageSucceedPiece(UploadEvent event){
		getPiece().setImageSucceed(event.getMedia().getStreamData());
		((Textbox) getWinPeca().getFellow("fldPathImageSucceed")).setValue(event.getMedia().getName());
	}
	
	@Override
	public Control<Memory> getControl() {
		return SpringFactory.getInstance().getBean("memoriaControl", MemoriaControl.class);
	}
	
	@Override
	public Control<Memory> getNewControl(MessagesControl pMessagesControl) {
		return SpringFactory.getInstance().getBean("memoriaControl", MemoriaControl.class);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEntityClass() {
		return Memory.class;
	}

	@Override
	public Window getEditForm() {
		return winMemoria;
	}

	@Override
	public void setEditForm(Window form) {
		winMemoria = form;
	}

}
