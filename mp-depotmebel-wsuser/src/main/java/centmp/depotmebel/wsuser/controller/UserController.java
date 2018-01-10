package centmp.depotmebel.wsuser.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bilmajdi.json.response.BaseJsonResponse;

import centmp.depotmebel.core.json.request.UserRequest;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.wsuser.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "insertnew", method = RequestMethod.POST)
	public @ResponseBody BaseJsonResponse insertnew(@Valid @RequestBody UserRequest requestParams) {
		System.out.println(this.getClass().getName()+" - insertnew - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		BaseJsonResponse result = userService.insertNew(requestParams);
		
		System.out.println();
		return result;
	}
}
