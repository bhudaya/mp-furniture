package centmp.depotmebel.wstrx.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import centmp.depotmebel.core.json.request.TrxRequest;
import centmp.depotmebel.core.json.response.TrxResponse;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.core.util.ConstantApp;
import centmp.depotmebel.wstrx.service.VoucherService;

@Controller
@RequestMapping("voucher")
public class VoucherController {
	
	@Autowired
	private VoucherService voucherService;

	@RequestMapping(value = "validate", method = RequestMethod.POST)
	public @ResponseBody TrxResponse validate(@RequestHeader(value="token") String token, @Valid @RequestBody TrxRequest requestParams) {
		
		System.out.println();
		System.out.println(" validate at wstrx " + this.getClass().getName()+" - validate - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		System.out.println(" token : "+ token);

		
		HashMap<String, Object> hm = new HashMap<>();
		hm.put(ConstantApp.TOKEN, token);
		hm.put(ConstantApp.REQUEST_PARAMS, requestParams);
		TrxResponse result = voucherService.validate(hm);
		
		System.out.println();
		return result;
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public @ResponseBody TrxResponse edit(@RequestHeader(value="token") String token, @Valid @RequestBody TrxRequest requestParams) {
		System.out.println(this.getClass().getName()+" - edit - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		HashMap<String, Object> hm = new HashMap<>();
		hm.put(ConstantApp.TOKEN, token);
		hm.put(ConstantApp.REQUEST_PARAMS, requestParams);
		TrxResponse result = voucherService.edit(hm);
		
		System.out.println();
		return result;
	}
}
