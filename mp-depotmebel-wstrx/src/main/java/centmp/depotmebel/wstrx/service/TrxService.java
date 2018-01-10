package centmp.depotmebel.wstrx.service;

import java.util.HashMap;

import centmp.depotmebel.core.json.response.TrxResponse;

public interface TrxService {
	
	TrxResponse checkout(HashMap<String, Object> hm);
	TrxResponse updateAddress(HashMap<String, Object> hm);
	TrxResponse updateExpireTime(HashMap<String, Object> hm);
	String paidFromPadipay(HashMap<String, Object> hm);

}
