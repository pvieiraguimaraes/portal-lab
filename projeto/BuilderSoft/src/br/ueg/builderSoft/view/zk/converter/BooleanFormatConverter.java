package br.ueg.builderSoft.view.zk.converter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.util.Locales;
import org.zkoss.util.TimeZones;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.metainfo.Annotation;
import org.zkoss.zk.ui.sys.ComponentCtrl;
import org.zkoss.zkplus.databind.TypeConverter;

public class BooleanFormatConverter implements TypeConverter {

	@Override
	public Object coerceToBean(Object paramObject, Component paramComponent) {
		return paramObject;
	}

	@Override
	public Object coerceToUi(Object paramObject, Component paramComponent) {
		Boolean bool = null;
	    if (!(paramObject instanceof Boolean)) {
	      bool = Boolean.valueOf((String)paramObject);
	    }else{
	    	bool = (Boolean) paramObject;
	    }

	    Annotation annot = ((ComponentCtrl)paramComponent).getAnnotation(null, "valor");
	    String valores = null;
	    if (annot != null) {
	      valores = annot.getAttribute("value");
	    }else{
	    	valores="sim:não";
	    }

	    String retorno[] = valores.split(":");
	    if(bool.booleanValue()==true){
	    	return retorno[0];
	    }
	    else{
	    	return retorno[1];
	    }
	}

}
