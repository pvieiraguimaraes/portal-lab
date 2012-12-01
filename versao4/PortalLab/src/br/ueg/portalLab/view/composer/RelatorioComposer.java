package br.ueg.portalLab.view.composer;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.util.annotation.AttributeView;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.builderSoft.view.zk.composer.ComposerController;
import br.ueg.portalLab.control.RelatorioControl;
import br.ueg.portalLab.model.Relatorio;

@Component
@Scope("session")
public class RelatorioComposer extends ComposerController<Relatorio>{

	private static final long serialVersionUID = -5475941344452281167L;
	
	private RelatorioControl relatorioControl;
	
	@AttributeView(key= "path", isEntityValue = true, fieldType = String.class)
	private String path;
	@AttributeView(key="servletOutputStream", isEntityValue=true, fieldType= ServletOutputStream.class)
	private ServletOutputStream servletOutputStream;
	
	public RelatorioComposer() {
	}

	@Override
	public Control<Relatorio> getNewControl(MessagesControl pMessagesControl) {
		return null;
	}

	@Override
	public Class getEntityClass() {
		return Relatorio.class;
	}

	@Override
	public Window getEditForm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEditForm(Window form) {
		// TODO Auto-generated method stub
		
	}
	
	public void generateReport() {

		Object objeto = Executions.getCurrent().getNativeResponse();
		HttpServletResponse httpServletResponse = (HttpServletResponse) objeto;
		try {
			setServletOutputStream(httpServletResponse.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setPath("C:\\Users\\Diego\\iniciacao\\PortalLab3\\WebContent\\jrxmls");
		this.doAction("generatereport");
		
		
	}
	
	@Override
	public void hideEditForm() {
		
	}

	public RelatorioControl getLocalControl() {
		return (RelatorioControl) getControl();
	}
	
	@Override
	public Control<Relatorio> getControl() {
		if (this.relatorioControl == null) {
			this.relatorioControl = (RelatorioControl) SpringFactory
					.getInstance().getBean("relatorioControl",
							RelatorioControl.class);
		}
		return this.relatorioControl;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ServletOutputStream getServletOutputStream() {
		return servletOutputStream;
	}

	public void setServletOutputStream(ServletOutputStream servletOutputStream) {
		this.servletOutputStream = servletOutputStream;
	}

}
