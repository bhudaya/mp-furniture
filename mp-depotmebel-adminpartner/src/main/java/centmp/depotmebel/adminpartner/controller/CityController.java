package centmp.depotmebel.adminpartner.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import centmp.depotmebel.adminpartner.bean.SessionBean;
import centmp.depotmebel.adminpartner.service.CityService;
import centmp.depotmebel.adminpartner.util.SessionBeanUtil;
import centmp.depotmebel.core.util.CommonUtil;


@Controller
@RequestMapping("city")
public class CityController {
	
	@Autowired
	private CityService cityService;
	
	@RequestMapping(value="load/byprov", method=RequestMethod.POST)
	private String loadByProvId(HttpServletRequest request, Model model){
		System.out.println("CityController - loadByProvId - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		//taskList(model);
		
		String provinciId = request.getParameter("provId");
		List<String[]> cityList = cityService.listByProvinceId(provinciId, "");
		String json = new Gson().toJson(cityList);
		model.addAttribute("message", json);
		
		System.out.println();
		return "plain";
	}

}
