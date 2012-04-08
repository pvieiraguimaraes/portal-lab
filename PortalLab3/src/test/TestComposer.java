package test;


import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Label;
import org.zkoss.zul.SimpleListModel;

public class TestComposer extends GenericForwardComposer<Component>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4161924648609270965L;
	private Label msg;
  	private Combobox cb;
  	
  	@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
  	public void renderComboBox(Component comp){
  		String[] _dict = { 
				"abacus", "accuracy", "acuity", "adage", "afar", "after", "apple",
				"bible", "bird", "bingle", "blog",
				"cabane", "cape", "cease", "cedar",
				"dacron", "defacto", "definable", "deluxe",
				"each", "eager", "effect", "efficacy",
				"far", "far from",
				"girl", "gigantean", "giant",
				"home", "honest", "huge",
				"information", "inner",
				"jump", "jungle", "jungle fever",
				"kaka", "kale", "kame",
				"lamella", "lane", "lemma",
				"master", "maxima", "music",
				"nerve", "new", "number",
				"omega", "opera",
				"pea", "peace", "peaceful",
				"rock", "RIA",
				"sound", "spread", "student", "super",
				"tea", "teacher",
				"unit", "universe",
				"vector", "victory",
				"wake", "wee", "weak", "web2.0",
				"xeme",
				"yea", "yellow",
				"zebra", "zk",
				
			};
		
		
		
		
		List<MyItem> list = new ArrayList<MyItem>();
		for (int i = 0; i < _dict.length; i++)
			list.add(new MyItem(i, _dict[i]));
			
      	comp.setAttribute("dictModel", new SimpleListModel(list) {
			protected boolean inSubModel(Object key, Object value) {
				String idx = key.toString();
				MyItem myItem = (MyItem)value;
				return idx.length() > 0 && myItem.getName().startsWith(idx);
			}
		});
		
		
		comp.setAttribute("renderer",new ComboitemRenderer () {
			public void render(Comboitem item, Object data) throws Exception {
				MyItem myItem = (MyItem)data;
				item.setLabel(myItem.getName());
				item.setValue(myItem);
			}

			@Override
			public void render(Comboitem item, Object data, int index)
					throws Exception {
				// TODO Auto-generated method stub
				this.render(item, data);
			}
		});
  	}
  
	
	public void doBeforeComposeChildren(Component comp) throws Exception {
		super.doBeforeComposeChildren(comp);
       // if(comp instanceof Comboitem)	{
        	renderComboBox(comp);
        //}
         	
	}
  
  	public void onSelect$cb() {
    		msg.setValue(cb.getSelectedItem().getValue().toString());
        }
}
