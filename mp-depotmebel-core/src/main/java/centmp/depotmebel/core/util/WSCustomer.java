package centmp.depotmebel.core.util;

import com.bilmajdi.util.PropertyMessageUtil;
import com.google.gson.Gson;

import centmp.depotmebel.core.json.response.CustomerResponse;

public class WSCustomer {
	
	public static CustomerResponse findByToken(String token){
		CustomerResponse jsonResponse = new CustomerResponse();
		int errorCode = -1;
		String errorMsg = ""; 
		String techMsg = "";
		
		try {
			String jsonParam = "";
			
			String centWs = PropertyMessageUtil.getConfigProperties().getProperty("centws.url");
			String uriPath = PropertyMessageUtil.getConfigProperties().getProperty("customer.findbytoken");
			String url = centWs + uriPath;
			System.out.println("url: "+url);

			String response = UrlConnect.jsonType(url, jsonParam, token);
			System.out.println("WS-Response: "+response);
			
			if(response!=null&&!response.isEmpty()){
				jsonResponse = new Gson().fromJson(response, CustomerResponse.class);
				errorCode = jsonResponse.getErrorCode();
				errorMsg = jsonResponse.getErrorMsg();
				techMsg = jsonResponse.getTechnicalMsg();
			}
			
		} catch (UrlConnException e1) {
			e1.printStackTrace();
			errorMsg = "Connection Problem";
			techMsg = e1.getLocalizedMessage();
		} catch (Exception e2) {
			e2.printStackTrace();
			errorMsg = "Exception";
			techMsg = e2.getLocalizedMessage();
		}
		
		
		jsonResponse.setErrorCode(errorCode);
		jsonResponse.setErrorMsg(errorMsg);
		jsonResponse.setTechnicalMsg(techMsg);
		
		return jsonResponse;
	}

}
