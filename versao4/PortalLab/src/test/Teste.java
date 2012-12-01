package test;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.zkoss.zul.Image;
import org.zkoss.zul.Window;

import br.ueg.builderSoft.persistence.GenericDAO;
import br.ueg.builderSoft.security.access.voter.RoleVoter;
import br.ueg.builderSoft.util.sets.SpringFactory;
import br.ueg.portalLab.model.EspecieImagem;
import br.ueg.portalLab.model.ItemGeografico;
import br.ueg.portalLab.model.NivelGeografico;
import br.ueg.portalLab.model.Usuario;

import br.ueg.portalLab.security.model.CasoDeUso;
import br.ueg.portalLab.security.model.CasoDeUsoFuncionalidade;
import br.ueg.portalLab.security.model.Funcionalidade;
import br.ueg.portalLab.view.managed.TipoDeMontagemMB;

public class Teste {
	public static void main(String[] args){
		//insertNivel();
		//insertAuto2();
		//listAuto1();
		//listAuto2();
		//listAuto3();
		//listAuto1_1();
		//testeSpring1();
		//testeUsuario();
		//insertAuto3();
		//removeAuto3();
		testeEspecieImagem();
		
	}
	public static void testeEspecieImagem(){
		EspecieImagem ei = new EspecieImagem();
		ei.setNome("d:\\programas\\PortalLab\\Jellyfish.jpg");
		
		ei.getMedia();
		System.out.println("X:"+ei.getMedia().getName());
		
	}
	public static void insertAuto3(){
		GenericDAO<CasoDeUso> casoDeUsoDAO = (GenericDAO<CasoDeUso>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		GenericDAO<Funcionalidade> funcionalidadeDAO = (GenericDAO<Funcionalidade>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
	
		CasoDeUso c1 = new CasoDeUso();
		c1 = casoDeUsoDAO.getByID(c1, 3L);
		Funcionalidade func = funcionalidadeDAO.getByID(new Funcionalidade(), 2L);
		
		CasoDeUsoFuncionalidade cuf = new CasoDeUsoFuncionalidade();
		cuf.setCasoDeUso(c1);
		cuf.setFuncionalidade(func);
		
		c1.getFuncionalidades().add(cuf);
		
		try {
			casoDeUsoDAO.update(c1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public static void removeAuto3(){
		GenericDAO<CasoDeUso> casoDeUsoDAO = (GenericDAO<CasoDeUso>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		GenericDAO<Funcionalidade> funcionalidadeDAO = (GenericDAO<Funcionalidade>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
	
		CasoDeUso c1 = new CasoDeUso();
		c1 = casoDeUsoDAO.getByID(c1, 3L);
		Funcionalidade func = funcionalidadeDAO.getByID(new Funcionalidade(), 1L);
		for(CasoDeUsoFuncionalidade cf : c1.getFuncionalidades()){
			System.out.println("CAFU:"+cf.getFuncionalidade().getNome());
			c1.getFuncionalidades().remove(cf);
		}
		try {casoDeUsoDAO.save(c1);
			//casoDeUsoDAO.delete(c1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void testeUsuario(){
		GenericDAO<Usuario> usuarioDAO = (GenericDAO<Usuario>)SpringFactory.getInstance().getBean("genericDAO",GenericDAO.class);
		Usuario u = new Usuario();
		for(Usuario u2: usuarioDAO.getList(u)){
			System.out.println("U:"+u2.getLogin());
		}
	}
	
public static void testeSpring1(){
	RoleVoter roleVoter = (RoleVoter) SpringFactory.getInstance().getBean("profiler", RoleVoter.class);
	
}
@SuppressWarnings("unchecked")
public static void insertAuto1(){
		
		GenericDAO<NivelGeografico> nivelGeoDAO = (GenericDAO<NivelGeografico>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		GenericDAO<ItemGeografico> itemGeoDAO = (GenericDAO<ItemGeografico>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		
		NivelGeografico nivel = new NivelGeografico();
		nivel.setId(1L);
		
		
		nivel  = (NivelGeografico) nivelGeoDAO.getByID(nivel, 1L);
		
		
		ItemGeografico ite = new ItemGeografico(0,"Brasil",nivel,null);
		
		try {
			itemGeoDAO.save(ite);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*for (ClasseAtividade list : ativ.getList(new ClasseAtividade())) {
			System.out.println("teste:" + list.getNome());
		}*/
	}
@SuppressWarnings("unchecked")
public static void insertAuto2(){
	GenericDAO<NivelGeografico> nivelGeoDAO = (GenericDAO<NivelGeografico>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
	GenericDAO<ItemGeografico> itemGeoDAO = (GenericDAO<ItemGeografico>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
	
	NivelGeografico nivel = new NivelGeografico();	
	nivel  = (NivelGeografico) nivelGeoDAO.getByID(nivel, 2L);
	
	ItemGeografico itemPai = new ItemGeografico();
	itemPai = (ItemGeografico) itemGeoDAO.getByID(itemPai, 1L);
	
	
	
	ItemGeografico ite = new ItemGeografico(0,"Goias",nivel,itemPai);
	
	try {
		itemGeoDAO.save(ite);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

@SuppressWarnings("unchecked")
public static void listAuto1(){
	
	//SpringFactory.getInstance().getBean("hibernateTemplate", HibernateTemplate.class).getSessionFactory();
	
	GenericDAO<NivelGeografico> nivelGeoDAO = (GenericDAO<NivelGeografico>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
	NivelGeografico nivel = new NivelGeografico();	
	nivel  = (NivelGeografico) nivelGeoDAO.getByID(nivel, 2L);
	
	for(ItemGeografico list : nivel.getListItensGeograficos()){
		System.out.println("Nome:"+list.getNome());
		for(ItemGeografico list2 : list.getFilhosItensGeograficos()){
			System.out.println("\tfilho:"+list2.getNome());
			System.out.println("\t\tNivel:"+list2.getNivelGeografico().getNome());
			System.out.println("\t\tPai:"+list2.getPai().getNome());
		}
	}
	
	//SpringFactory.getInstance().getBean("hibernateTemplate", HibernateTemplate.class).getSessionFactory().close();
}
@SuppressWarnings("unchecked")
public static void listAuto1_1(){
	GenericDAO<ItemGeografico> nivelGeoDAO = (GenericDAO<ItemGeografico>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
	ItemGeografico item = new ItemGeografico();	
	item  = (ItemGeografico) nivelGeoDAO.getByID(item, 1L);
	System.out.println(item.getNivelGeografico().getNome()+":"+item.getNome());
	listItemGeograficosTree(item, "\t");
}

public static void listItemGeograficosTree(ItemGeografico item, String tab){
	if(item == null) return;
	if(tab ==null) tab ="";
	for(ItemGeografico i: item.getFilhosItensGeograficos()){
		System.out.println(tab+i.getNivelGeografico().getNome()+":"+i.getNome());
		Teste.listItemGeograficosTree(i,tab+"\t");		
	}
}


//lazy funcionando com query
public static void listAuto2(){
	
SessionFactory session =  SpringFactory.getInstance().getBean("hibernateTemplate", HibernateTemplate.class).getSessionFactory();

	
	//GenericDAO<NivelGeografico> nivelGeoDAO = (GenericDAO<NivelGeografico>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
	//  (GenericDAO<NivelGeografico>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
	Query q = session.openSession().createQuery("from NivelGeografico nivelgeografico where nivelgeografico.id = :id").setLong("id", 2L);
	
	
	//NivelGeografico nivel = new NivelGeografico();	
	NivelGeografico nivel = (NivelGeografico) q.uniqueResult();
	
	for(ItemGeografico list : nivel.getListItensGeograficos()){
		System.out.println("Nome:"+list.getNome());
		for(ItemGeografico list2 : list.getFilhosItensGeograficos()){
			System.out.println("\tfilho:"+list2.getNome());
			System.out.println("\t\tpai:"+list2.getNivelGeografico().getNome());
		}
	}
	
	session.close();
}

//lazy funcionando com criteria
@SuppressWarnings("unchecked")
public static void listAuto3(){
	
SessionFactory session =  SpringFactory.getInstance().getBean("hibernateTemplate", HibernateTemplate.class).getSessionFactory();

NivelGeografico nivel = new NivelGeografico();	
	//GenericDAO<NivelGeografico> nivelGeoDAO = (GenericDAO<NivelGeografico>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
	//  (GenericDAO<NivelGeografico>) SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
List<NivelGeografico> searchs = new ArrayList<NivelGeografico>();
for (int i = 0; i < nivel.getSearchColumnTable(nivel).size(); i++) {
	//DetachedCriteria criteria = DetachedCriteria.forClass(nivel.getClass()).add(Restrictions.like(nivel.getSearchColumnEntity(nivel).get(i), "%2%"));
	Criteria criteria =   session.openSession().createCriteria(NivelGeografico.class).add(Restrictions.idEq(2L));
	searchs.addAll(criteria.list());
}
	
	
	//NivelGeografico nivel = new NivelGeografico();	
	 nivel = (NivelGeografico)searchs.get(0);
	
	for(ItemGeografico list : nivel.getListItensGeograficos()){
		System.out.println("Nome:"+list.getNome());
		for(ItemGeografico list2 : list.getFilhosItensGeograficos()){
			System.out.println("\tfilho:"+list2.getNome());
			System.out.println("\t\tpai:"+list2.getNivelGeografico().getNome());
		}
	}
	
	session.close();
}

	
	@SuppressWarnings("unchecked")
	public static void insertNivel(){
		GenericDAO<NivelGeografico> nivelDAO = SpringFactory.getInstance().getBean("genericDAO", GenericDAO.class);
		NivelGeografico nivel = new NivelGeografico(0, "Estado", new Boolean(true));
		try {
			nivelDAO.save(nivel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	public static void testeMB1(){
		TipoDeMontagemMB teste = new TipoDeMontagemMB();
	}
}
