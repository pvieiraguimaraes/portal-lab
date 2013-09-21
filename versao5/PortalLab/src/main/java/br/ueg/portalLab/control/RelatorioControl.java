package br.ueg.portalLab.control;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.stereotype.Service;

import br.ueg.builderSoft.control.Control;
import br.ueg.builderSoft.control.SubControllerManager;
import br.ueg.builderSoft.util.control.MessagesControl;
import br.ueg.portalLab.model.Relatorio;

@Service
public class RelatorioControl extends Control<Relatorio> implements Serializable {

	public RelatorioControl() {
		
	}
	
	public RelatorioControl(MessagesControl pMessagesControl) {
		super(pMessagesControl);
	}

	public boolean actionGeneratereport(SubControllerManager<Relatorio> subControllerManager) {
		try {
			Connection connect = ((SessionFactoryImplementor) this
					.getPersistence().getSession().getSessionFactory())
					.getConnectionProvider().getConnection();

			List<JasperPrint> jaspers = new ArrayList<JasperPrint>();
			JRPdfExporter exporter = new JRPdfExporter();

			// Relatorio relatorio = ((Relatorio)
			// this.getMapFields().get("entity"));

			Map<String, Object> parametros = new HashMap<String, Object>();

			parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.FALSE);

			parametros.put("limit", "%a%");

			String separador = System.getProperty("file.separator");
			String subpath = separador + "jrxmls" + separador;
			Relatorio relatorio = (Relatorio) getMapFields().get("entity");
			String path = relatorio.getPath();
			JasperReport jr = JasperCompileManager.compileReport(path
					+ separador + "relatorio01.jrxml");
			JasperPrint jp = JasperFillManager.fillReport(jr, parametros,
					connect);
			jaspers.add(jp);
			connect.close();

			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
					jaspers);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					relatorio.getServletOutputStream());
			exporter.setParameter(
					JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS,
					Boolean.TRUE);

			exporter.exportReport();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
