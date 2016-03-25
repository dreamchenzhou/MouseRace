package com.dreamchen.useful.mouserace.multithread;

import java.io.File;

public abstract class FileBase {
	public static final String COUNT_TAG_NOMAL ="COUNT_TAG_NOMAL"; 
	
	public static final String COUNT_TAG_10_M = "COUNT_TAG_10_M";
	
	public static final String COUNT_TAG_50_M ="COUNT_TAG_50_M";
	
	public static final String COUNT_TAG_100_M = "COUNT_TAG_100_M";
	
	public static final String COUNT_TAG_500_M = "COUNT_TAG_500_M";
	
	public static final int 	BUFFER_SIZE_SMALL = 65;
	
	public static final int BUFFER_SIZE_MIDDLE = 256;
	
	public static final int BUFFER_SIZE_LARGE = 1024;
	
	public static  String getcountTag(File file){
		if(file==null){
			throw new IllegalArgumentException("The file is null!");
		}
		long length = file.length();
		long M = 1024*1024;
		if(length<=M){
			return COUNT_TAG_NOMAL;
		}else if(length>M&&length<=10*M){
			return COUNT_TAG_10_M;
		}else if(length>10*M&&length<=100*M){
			return COUNT_TAG_50_M;
		}else if(length>100*M&&length<500*M){
			return COUNT_TAG_100_M;
		}else{
			throw new IllegalStateException("The file is too large!");
		}
	}
}
