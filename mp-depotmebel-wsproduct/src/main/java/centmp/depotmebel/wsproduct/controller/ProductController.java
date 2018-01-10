package centmp.depotmebel.wsproduct.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import centmp.depotmebel.core.json.request.ProductRequest;
import centmp.depotmebel.core.json.response.ProductListResponse;
import centmp.depotmebel.core.json.response.ProductResponse;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.core.util.ConstantApp;
import centmp.depotmebel.wsproduct.service.ProductService;

@Controller
@RequestMapping("product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "catalog", method = RequestMethod.POST)
	public @ResponseBody ProductListResponse catalog(@Valid @RequestBody ProductRequest requestParams) {
		System.out.println(this.getClass().getName()+" - catalog - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		HashMap<String, Object> hm = new HashMap<>();
		hm.put(ConstantApp.REQUEST_PARAMS, requestParams);
		ProductListResponse result = productService.catalog(hm);
		
		System.out.println();
		return result;
	}
	
	@RequestMapping(value = "catalog/search", method = RequestMethod.POST)
	public @ResponseBody ProductListResponse catalogSearch(@Valid @RequestBody ProductRequest requestParams) {
		System.out.println(this.getClass().getName()+" - catalogSearch - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		HashMap<String, Object> hm = new HashMap<>();
		hm.put(ConstantApp.REQUEST_PARAMS, requestParams);
		ProductListResponse result = productService.catalogBySearch(hm);
		
		System.out.println();
		return result;
	}
	
	@RequestMapping(value = "latest", method = RequestMethod.POST)
	public @ResponseBody ProductListResponse latest(@Valid @RequestBody ProductRequest requestParams) {
		System.out.println(this.getClass().getName()+" - latest - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		HashMap<String, Object> hm = new HashMap<>();
		hm.put(ConstantApp.REQUEST_PARAMS, requestParams);
		ProductListResponse result = productService.latest(hm);
		
		System.out.println();
		return result;
	}
	
	@RequestMapping(value = "detail", method = RequestMethod.POST)
	public @ResponseBody ProductResponse detail(@Valid @RequestBody ProductRequest requestParams) {
		System.out.println(this.getClass().getName()+" - detail - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		HashMap<String, Object> hm = new HashMap<>();
		hm.put(ConstantApp.REQUEST_PARAMS, requestParams);
		ProductResponse result = productService.detail(hm);
		
		System.out.println();
		return result;
	}
	
	@RequestMapping(value = "sku", method = RequestMethod.POST)
	public @ResponseBody ProductResponse skuList(@Valid @RequestBody ProductRequest requestParams) {
		System.out.println(this.getClass().getName()+" - skuList - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		HashMap<String, Object> hm = new HashMap<>();
		hm.put(ConstantApp.REQUEST_PARAMS, requestParams);
		ProductResponse result = productService.skuList(hm);
		
		System.out.println();
		return result;
	}

}
