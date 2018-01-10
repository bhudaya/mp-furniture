package centmp.depotmebel.adminvendor.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import centmp.depotmebel.adminvendor.bean.SessionBean;
import centmp.depotmebel.adminvendor.service.PCategoryService;
import centmp.depotmebel.adminvendor.util.SessionBeanUtil;
import centmp.depotmebel.core.util.CommonUtil;


@Controller
@RequestMapping("pcategory")
public class PCategoryController {
	
	@Autowired
	private PCategoryService pcategoryService;

	@RequestMapping(value="/sub/listbypcategory/json", method=RequestMethod.POST)
	private String subListByPCategoryJson(HttpServletRequest request, Model model){
		System.out.println("PCategoryController - subListByPCategoryJson - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		//readyToQcNum(model);
		
		String pcategoryId = request.getParameter("pcategoryId");
		List<String[]> list = pcategoryService.subCategoryList(pcategoryId);
		String json = new Gson().toJson(list);
		model.addAttribute("message", json);
		
		System.out.println();
		return "plain";
	}
	
	@RequestMapping(value="/spec/listbypcategory/json", method=RequestMethod.POST)
	private String specListByPCategoryJson(HttpServletRequest request, Model model){
		System.out.println("PCategoryController - specListByPCategoryJson - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		//readyToQcNum(model);
		
		String pcategoryId = request.getParameter("pcategoryId");
		List<String[]> list = pcategoryService.specList(pcategoryId);
		String json = new Gson().toJson(list);
		model.addAttribute("message", json);
		
		System.out.println();
		return "plain";
	}
	
	@RequestMapping(value="/attr/listbypcategory/json", method=RequestMethod.POST)
	private String attrListByPCategoryJson(HttpServletRequest request, Model model){
		System.out.println("PCategoryController - attrListByPCategoryJson - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		//readyToQcNum(model);
		
		String pcategoryId = request.getParameter("pcategoryId");
		List<String[]> list = pcategoryService.attrList(pcategoryId);
		String json = new Gson().toJson(list);
		model.addAttribute("message", json);
		
		System.out.println();
		return "plain";
	}
}
