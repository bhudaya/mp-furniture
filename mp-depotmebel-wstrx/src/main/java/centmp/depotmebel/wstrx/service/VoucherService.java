package centmp.depotmebel.wstrx.service;

import java.util.HashMap;

import centmp.depotmebel.core.json.response.TrxResponse;

public interface VoucherService {

	TrxResponse validate(HashMap<String, Object> hm);
	TrxResponse edit(HashMap<String, Object> hm);
	
}
