package com.bilmajdi.test;

import com.google.gson.Gson;

import centmp.depotmebel.core.json.request.ProductRequest;

public class ProductReqJsonTest {
	
	public static void main(String[] args) {
		String[] categoryIds = new String[]{"1","2"};
		ProductRequest req = new ProductRequest();
		req.setSource("WEB");
		req.setSourceDesc("depotmebel.com");
		req.setCategoryIds(categoryIds);
		System.out.println("JSON: "+new Gson().toJson(req));
	}

}
