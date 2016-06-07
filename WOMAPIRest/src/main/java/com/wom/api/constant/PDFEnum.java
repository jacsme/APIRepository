package com.wom.api.constant;

public enum PDFEnum {
	
	CENTER("CENTER"), LEFT("LEFT"), RIGHT("RIGHT"), MIDDLE("MIDDLE"), NONE("NONE"), NOBORDER("NOBORDER"), 
	BOTTOMBORDER("BOTTOMBORDER"), GREYBORDER("GREYBORDER"), BGCOLOR("BGCOLOR");
	
	private String mainType;
	
	private PDFEnum(String mType){
		mainType = mType;
	}
	
	public String getMainType(){
		return mainType;
	}

}
