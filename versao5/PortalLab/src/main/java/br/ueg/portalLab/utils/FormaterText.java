package br.ueg.portalLab.utils;

public class FormaterText {

	public static Integer getXPosition(String formatedXYstring)
	{
		return Integer.parseInt(formatedXYstring.split("\\.", 2)[0]);
	}
	
	public static Integer getYPosition(String formatedXYstring)
	{
		return Integer.parseInt(formatedXYstring.split("\\.", 2)[1]);
	}
	
	
}
