package br.ueg.portalsite;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.source_code.miniTemplator.MiniTemplator;
import br.ueg.portalsite.utils.ParserGlossario;

@SuppressWarnings("serial")
public class ServletControl extends HttpServlet {
	
	private static final String TEMPLATE = "templates/template.html";
	private ServletContext servletContext;
	private String separator;
	private String path;
	private String pathMedia;
	private ParserGlossario parserGlossario = new ParserGlossario();
	
	public ServletControl() {
		super();
	}
	
	@Override
    public void init(){
    	servletContext = getServletContext();
    	separator = System.getProperty("file.separator");
		path = servletContext.getRealPath("/");
		pathMedia = "media/";
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getContextPath();
		run(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		run(request,response);
	}
	
	private String getPathTemplate(String templateName){
		if(templateName == "index")
			return path + separator + templateName + ".html";
		return path + separator + "templates" + separator + templateName + ".html";
	}
	
	private void run(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		MiniTemplator templator = null, templateAux = null;
		String htmlResult = "";
		boolean withoutTemplate = false;
		HashMap<String, Object> parameters = new HashMap<>();
		try {
			templator = GeneratorPage.initTemplator(path + separator + TEMPLATE);

			String page = request.getParameter("page");
			page = page != null ? page : "index";

			//CARREGAMENTO DAS PAGINAS QUE NAO TEM TEMPLATE
			if(page.equalsIgnoreCase("containerdetalhecolecao") || page.equalsIgnoreCase("itemcolecao"))
				withoutTemplate = true;

			String pathTemplate = getPathTemplate(page);
			
			if (page.equalsIgnoreCase("detalhecolecao") || page.equalsIgnoreCase("containerdetalhecolecao")) {
				parameters.put("itemid", request.getParameter("itemid"));
				if (request.getParameter("pagina") == null
						|| request.getParameter("pagina") == "")
					parameters.put("pagina", "1");
				else
					parameters.put("pagina", request.getParameter("pagina"));
				if (request.getParameter("nPagina") == null
						|| request.getParameter("nPagina") == "")
					parameters.put("nPagina", "10");
				else
					parameters.put("nPagina", request.getParameter("nPagina"));
			}

			if (page.equalsIgnoreCase("detalheitemcolecao"))
				parameters.put("itemcolecaoid", request.getParameter("itemcolecaoid"));

			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			
			GeneratorPage generator = new GeneratorPage(page, pathTemplate, templator, pathMedia, parameters);
			templateAux = generator.generatePage();

			String processaHTML = this.parserGlossario.processaHTML(templateAux.generateOutput());
			templator.setVariable("content", processaHTML);
			templator.setVariable("absoluteSitePath", "/PortalSite/");

			htmlResult = templator.generateOutput();
			if (withoutTemplate)
				out.println(processaHTML);
			else
				out.println(htmlResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
