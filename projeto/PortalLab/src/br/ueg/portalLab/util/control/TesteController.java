package br.ueg.portalLab.util.control;

import java.util.List;


public class TesteController implements SubController {
	
	private String justify;
	private String description;

	/**
	 * @return the justify
	 */
	public String getJustify() {
		return justify;
	}

	/**
	 * @param justify the justify to set
	 */
	public void setJustify(String justify) {
		this.justify = justify;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean doAction(List<Object> atributes, String action) {
		System.out.println("Sua justificativa é: " + this.justify);
		System.out.println("Seu comentário foi:" + this.description);
		return true;
	}

}
