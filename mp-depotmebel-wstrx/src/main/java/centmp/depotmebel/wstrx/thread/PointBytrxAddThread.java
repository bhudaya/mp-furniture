package centmp.depotmebel.wstrx.thread;

import com.bilmajdi.util.PropertyMessageUtil;
import com.google.gson.Gson;

import centmp.depotmebel.core.json.request.PointRequest;
import centmp.depotmebel.core.util.UrlConnect;

public class PointBytrxAddThread implements Runnable {

	private String email;
	private String trxCode;
	private String amount;
	
	public PointBytrxAddThread(String email, String trxCode, String amount) {
		this.email = email;
		this.trxCode = trxCode;
		this.amount = amount;
	}


	@Override
	public void run() {
		System.out.println("PointBytrxAddThread - Start");
		try {
			PointRequest paramReq = new PointRequest();
			paramReq.setEmail(email);
			paramReq.setTrxCode(trxCode);
			paramReq.setAmount(amount);
			String jsonParam = new Gson().toJson(paramReq);
			System.out.println("jsonParam: "+jsonParam);
			
			String centWs = PropertyMessageUtil.getConfigProperties().getProperty("centws.url");
			String uriPath = PropertyMessageUtil.getConfigProperties().getProperty("point.bytrx.add");
			String url = centWs + uriPath;
			UrlConnect.jsonType(url, jsonParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("PointBytrxAddThread - End");
	}

}
