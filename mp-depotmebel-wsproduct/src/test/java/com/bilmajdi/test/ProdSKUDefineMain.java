package com.bilmajdi.test;

import java.util.ArrayList;
import java.util.List;

public class ProdSKUDefineMain {
	
	public static void main(String[] args) {
		
		List<String> list = new ArrayList<>();
		list.add("A, B");
		list.add("11, 12");
		list.add("X");
		
		for(int a=0;a<list.size();a++){
			String value = list.get(a);
			System.out.println("Val: "+value);
			String[] split = value.split(",");
			for (String item: split) {
				System.out.println("item: "+item);
			}
		}
	}

}
