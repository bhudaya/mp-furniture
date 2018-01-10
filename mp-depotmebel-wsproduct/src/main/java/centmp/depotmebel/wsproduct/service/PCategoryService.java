package centmp.depotmebel.wsproduct.service;

import java.util.HashMap;

import centmp.depotmebel.core.json.response.PCategoryListResponse;

public interface PCategoryService {
	
	PCategoryListResponse listAll(HashMap<String, Object> hm);
	PCategoryListResponse listWithSumProd(HashMap<String, Object> hm);
	
}
