package centmp.depotmebel.wsproduct.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import centmp.depotmebel.core.json.request.PCategoryRequest;
import centmp.depotmebel.core.json.response.PCategoryListResponse;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.core.util.ConstantApp;
import centmp.depotmebel.wsproduct.service.PCategoryService;

@Controller
@RequestMapping("category")
public class PCategoryController {
	
	@Autowired
	private PCategoryService pCategoryService;
	
	@RequestMapping(value = "list/all", method = RequestMethod.POST)
	public @ResponseBody PCategoryListResponse listAll(@Valid @RequestBody PCategoryRequest requestParams) {
		System.out.println(this.getClass().getName()+" - listAll - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		HashMap<String, Object> hm = new HashMap<>();
		hm.put(ConstantApp.REQUEST_PARAMS, requestParams);
		PCategoryListResponse result = pCategoryService.listAll(hm);
		
		System.out.println();
		return result;
	}
	
	@RequestMapping(value = "list/withsumprod", method = RequestMethod.POST)
	public @ResponseBody PCategoryListResponse listWithSumProd(@Valid @RequestBody PCategoryRequest requestParams) {
		System.out.println(this.getClass().getName()+" - listWithSumProd - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		HashMap<String, Object> hm = new HashMap<>();
		hm.put(ConstantApp.REQUEST_PARAMS, requestParams);
		PCategoryListResponse result = pCategoryService.listWithSumProd(hm);
		
		System.out.println();
		return result;
	}

}
