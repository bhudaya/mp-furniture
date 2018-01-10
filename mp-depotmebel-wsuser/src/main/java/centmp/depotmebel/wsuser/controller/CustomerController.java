package centmp.depotmebel.wsuser.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bilmajdi.json.response.BaseJsonResponse;

import centmp.depotmebel.core.json.request.CustomerRequest;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.core.util.ConstantApp;
import centmp.depotmebel.wsuser.service.CustomerService;

@Controller
@RequestMapping("customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = "upgradeaccount/new", method = RequestMethod.POST)
	public @ResponseBody BaseJsonResponse upgradeAccountNew(@RequestHeader(value="token") String token, @Valid @RequestBody CustomerRequest requestParams) {
		System.out.println(this.getClass().getName()+" - upgradeAccountNew - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		HashMap<String, Object> hm = new HashMap<>();
		hm.put(ConstantApp.TOKEN, token);
		hm.put(ConstantApp.REQUEST_PARAMS, requestParams);
		BaseJsonResponse result = customerService.accountUpgradeNew(hm);
		
		System.out.println();
		return result;
	}
}
