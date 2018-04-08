package com.tzplatform.utils.common;

public class SHAEncrypt {
	public String encode(String passWord) {
		if (passWord == null || "".equals(passWord)) {
			return "";
		} else {
			return SHA1Util.hex_sha1(passWord);
		}
	}
	
	
	public static void main(String[] args){
		SHAEncrypt scr = new SHAEncrypt();
		String str = "91206974";
		System.out.println(scr.encode(str));
	}
}
