package centmp.depotmebel.wstrx.service;

import java.util.HashMap;

import centmp.depotmebel.core.json.response.TrxItemListResponse;
import centmp.depotmebel.core.json.response.TrxItemResponse;

public interface CartService {
	
	TrxItemResponse add(HashMap<String, Object> hm);
	TrxItemListResponse list(HashMap<String, Object> hm);
	TrxItemResponse removeItem(HashMap<String, Object> hm);
	TrxItemResponse removeAll(HashMap<String, Object> hm);

}
