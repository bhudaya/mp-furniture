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
import centmp.depotmebel.core.json.response.TrxItemListResponse;
import centmp.depotmebel.core.json.response.TrxItemResponse;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.core.util.ConstantApp;
import centmp.depotmebel.wstrx.service.CartService;

@Controller
@RequestMapping("cart")
public class CartController {
	
	@Autowired
	private CartService cartService;

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public @ResponseBody TrxItemResponse add(@RequestHeader(value="token") String token, @Valid @RequestBody TrxRequest requestParams) {
		System.out.println(this.getClass().getName()+" - add - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		HashMap<String, Object> hm = new HashMap<>();
		hm.put(ConstantApp.TOKEN, token);
		hm.put(ConstantApp.REQUEST_PARAMS, requestParams);
		TrxItemResponse result = cartService.add(hm);
		
		System.out.println();
		return result;
	}
	
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public @ResponseBody TrxItemListResponse list(@RequestHeader(value="token") String token) {
		System.out.println(this.getClass().getName()+" - list - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		HashMap<String, Object> hm = new HashMap<>();
		hm.put(ConstantApp.TOKEN, token);
		TrxItemListResponse result = cartService.list(hm);
		
		System.out.println();
		return result;
	}
	
	@RequestMapping(value = "remove/item", method = RequestMethod.POST)
	public @ResponseBody TrxItemResponse removeItem(@RequestHeader(value="token") String token, @Valid @RequestBody TrxRequest requestParams) {
		System.out.println(this.getClass().getName()+" - removeItem - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		HashMap<String, Object> hm = new HashMap<>();
		hm.put(ConstantApp.TOKEN, token);
		hm.put(ConstantApp.REQUEST_PARAMS, requestParams);
		TrxItemResponse result = cartService.removeItem(hm);
		
		System.out.println();
		return result;
	}
	
	@RequestMapping(value = "remove/all", method = RequestMethod.POST)
	public @ResponseBody TrxItemResponse removeAll(@RequestHeader(value="token") String token) {
		System.out.println(this.getClass().getName()+" - removeAll - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		HashMap<String, Object> hm = new HashMap<>();
		hm.put(ConstantApp.TOKEN, token);
		TrxItemResponse result = cartService.removeAll(hm);
		
		System.out.println();
		return result;
	}
}
