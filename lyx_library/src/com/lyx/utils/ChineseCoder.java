package com.lyx.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ChineseCoder {

	private static final boolean isChineseType(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	public static final boolean contains(String value) {
		char[] ch = value.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChineseType(c)) {
				return true;
			}
		}
		return false;
	}
	
	public static final String encode(String value,String charset){
		char[] ch = value.toCharArray();
		StringBuffer target = new StringBuffer()  ;
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChineseType(c)) {
				try {
					target.append(URLEncoder.encode(String.valueOf(c),charset))  ;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}else{
				target.append(c) ;
			}
		}
		return target.toString() ;
	}
	public static final String encode(String value){
		return encode(value,"UTF-8") ;
	}

}
