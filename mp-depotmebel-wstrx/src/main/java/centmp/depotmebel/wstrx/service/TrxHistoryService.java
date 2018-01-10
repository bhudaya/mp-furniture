package centmp.depotmebel.wstrx.service;

import java.util.HashMap;

import centmp.depotmebel.core.json.response.TrxListResponse;
import centmp.depotmebel.core.json.response.TrxResponse;

public interface TrxHistoryService {
	
	TrxListResponse customer(String token);
	TrxResponse detailToPay(HashMap<String, Object> hm);
	TrxResponse detailToCustomer(HashMap<String, Object> hm);
}
