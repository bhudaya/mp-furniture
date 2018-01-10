package com.bilmajdi.test;

import com.bilmajdi.util.CipherUtil;

public class CipherDecryptTest {
	
	public static void main(String[] args) {
		String key = "f2JYWA+Kal0g8bmJUmbasQ==";
		try {
			System.out.println("HH: "+CipherUtil.decrypt(key));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
