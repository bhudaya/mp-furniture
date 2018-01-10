package centmp.depotmebel.wsproduct.service;

import java.util.HashMap;

import centmp.depotmebel.core.json.response.ProductListResponse;
import centmp.depotmebel.core.json.response.ProductResponse;

public interface ProductService {
	
	ProductListResponse catalog(HashMap<String, Object> hm);
	ProductListResponse catalogBySearch(HashMap<String, Object> hm);
	ProductListResponse latest(HashMap<String, Object> hm);
	
	ProductResponse detail(HashMap<String, Object> hm);
	ProductResponse skuList(HashMap<String, Object> hm);
	
}
