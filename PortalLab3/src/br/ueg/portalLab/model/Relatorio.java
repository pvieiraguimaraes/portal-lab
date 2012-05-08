package br.ueg.portalLab.model;

import javax.servlet.ServletOutputStream;

import br.ueg.builderSoft.model.Entity;
import br.ueg.builderSoft.util.annotation.Attribute;

public class Relatorio extends Entity {

	private static final long serialVersionUID = 1L;

	@Attribute(Required = true, SearchField = false)
	private String path;
	@Attribute(Required = true, SearchField = false)
	private ServletOutputStream servletOutputStream;

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub

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
