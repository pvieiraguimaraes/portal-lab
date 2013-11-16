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
	
	private void run(HttpServletRequest request, HttpServletResponse response) throws IOException{
		MiniTemplator templator = null, templateAux = null;
		String htmlResult = "";
		HashMap<String, Object> parameters = new HashMap<>();
		try {
			templator = GeneratorPage.initTemplator(path + separator + TEMPLATE);
			
			String page = request.getParameter("page");
			page  = page!=null?page:"index";
			
			if(page.equalsIgnoreCase("colecao"))
				parameters.put("itemid", request.getParameter("itemid"));
			
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			
			GeneratorPage generator = new GeneratorPage(page, getPathTemplate(page), templator, pathMedia, parameters);
			templateAux = generator.generatePage();
			
			templator.setVariable("content", this.parserGlossario.processaHTML(templateAux.generateOutput()));
			
			htmlResult = templator.generateOutput();
			
			out.println(htmlResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
