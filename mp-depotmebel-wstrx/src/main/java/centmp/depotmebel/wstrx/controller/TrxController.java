package centmp.depotmebel.wstrx.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import centmp.depotmebel.core.json.request.TrxRequest;
import centmp.depotmebel.core.json.response.TrxListResponse;
import centmp.depotmebel.core.json.response.TrxResponse;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.core.util.ConstantApp;
import centmp.depotmebel.wstrx.service.TrxHistoryService;
import centmp.depotmebel.wstrx.service.TrxService;

@Controller
@RequestMapping("trx")
public class TrxController {
	
	@Autowired
	private TrxService trxService;
	@Autowired
	private TrxHistoryService trxHistoryService; 
	

	@RequestMapping(value = "checkout", method = RequestMethod.POST)
	public @ResponseBody TrxResponse checkout(@RequestHeader(value="token") String token, @Valid @RequestBody TrxRequest requestParams) {
		System.out.println(this.getClass().getName()+" - checkout - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		HashMap<String, Object> hm = new HashMap<>();
		hm.put(ConstantApp.TOKEN, token);
		hm.put(ConstantApp.REQUEST_PARAMS, requestParams);
		TrxResponse result = trxService.checkout(hm);
		
		System.out.println();
		return result;
	}
	
	@RequestMapping(value = "address/update", method = RequestMethod.POST)
	public @ResponseBody TrxResponse updateAddress(@RequestHeader(value="token") String token, @Valid @RequestBody TrxRequest requestParams) {
		System.out.println(this.getClass().getName()+" - updateAddress - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		HashMap<String, Object> hm = new HashMap<>();
		hm.put(ConstantApp.TOKEN, token);
		hm.put(ConstantApp.REQUEST_PARAMS, requestParams);
		TrxResponse result = trxService.updateAddress(hm);
		
		System.out.println();
		return result;
	}
	
	@RequestMapping(value = "expiretime/update", method = RequestMethod.POST)
	public @ResponseBody TrxResponse updateExpireTime(@RequestHeader(value="token") String token, @Valid @RequestBody TrxRequest requestParams) {
		System.out.println(this.getClass().getName()+" - updateExpireTime - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		HashMap<String, Object> hm = new HashMap<>();
		hm.put(ConstantApp.TOKEN, token);
		hm.put(ConstantApp.REQUEST_PARAMS, requestParams);
		TrxResponse result = trxService.updateExpireTime(hm);
		
		System.out.println();
		return result;
	}
	
	@RequestMapping(value = "padipay/response", method = RequestMethod.POST)
	private String padipayResponse(HttpServletRequest request, Model model){
		System.out.println(this.getClass().getName()+" - padipayResponse - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		String resultCode = request.getParameter("resultCode");
		String resultDesc = request.getParameter("resultDesc");
		String invoiceCode = request.getParameter("invoiceCode");
		String padipayCode = request.getParameter("padipayCode");
		
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("resultCode", resultCode);
		hm.put("resultDesc", resultDesc);
		hm.put("invoiceCode", invoiceCode);
		hm.put("padipayCode", padipayCode);
		trxService.paidFromPadipay(hm);
		
		System.out.println("");
		return "plain";
	}
	
	@RequestMapping(value = "customer", method = RequestMethod.POST)
	public @ResponseBody TrxListResponse customerOwn(@RequestHeader(value="token") String token) {
		System.out.println(this.getClass().getName()+" - customerOwn - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		TrxListResponse result = trxHistoryService.customer(token);
		
		System.out.println();
		return result;
	}
	
	@RequestMapping(value = "detail/topay", method = RequestMethod.POST)
	public @ResponseBody TrxResponse detailToPay(@RequestHeader(value="token") String token, @Valid @RequestBody TrxRequest requestParams) {
		System.out.println(this.getClass().getName()+" - detailToPay - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		HashMap<String, Object> hm = new HashMap<>();
		hm.put(ConstantApp.TOKEN, token);
		hm.put(ConstantApp.REQUEST_PARAMS, requestParams);
		TrxResponse result = trxHistoryService.detailToPay(hm);
		
		System.out.println();
		return result;
	}
	
	@RequestMapping(value = "detail/tocustomer", method = RequestMethod.POST)
	public @ResponseBody TrxResponse detailToCustomer(@RequestHeader(value="token") String token, @Valid @RequestBody TrxRequest requestParams) {
		System.out.println(this.getClass().getName()+" - detailToCustomer - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		HashMap<String, Object> hm = new HashMap<>();
		hm.put(ConstantApp.TOKEN, token);
		hm.put(ConstantApp.REQUEST_PARAMS, requestParams);
		TrxResponse result = trxHistoryService.detailToCustomer(hm);
		
		System.out.println();
		return result;
	}
	
}
