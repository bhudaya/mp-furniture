package centmp.depotmebel.adminpartner.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

import centmp.depotmebel.adminpartner.bean.ProductBean;
import centmp.depotmebel.adminpartner.bean.ProductListBean;
import centmp.depotmebel.adminpartner.bean.SessionBean;
import centmp.depotmebel.adminpartner.service.TempProductService;
import centmp.depotmebel.adminpartner.util.SessionBeanUtil;
import centmp.depotmebel.core.util.CommonUtil;


@Controller
@RequestMapping("tempproduct")
public class TempProdController extends ParentController {
	
	@Autowired
	private TempProductService productService;
	
	@RequestMapping(value={"","/","/index"}, method=RequestMethod.GET)
	private String index(HttpServletRequest request, Model model){
		System.out.println("TempProdController - index - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		
		ProductListBean listbean = productService.listAll(sessionbean.getUser());
		model.addAttribute("beanlist", listbean);
		
		System.out.println();
		return "product/temp-list";
	}
	
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	private String detail(HttpServletRequest request, Model model, @RequestParam("q") String idenc){
		System.out.println("TempProdController - detail - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());

		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		taskList(model);
		
		ProductBean bean = productService.detail(idenc);
		System.out.println("bean: "+new Gson().toJson(bean));
		model.addAttribute("obj", bean);
		
		System.out.println();
		return "product/temp-detail";
	}
	
	@RequestMapping(value="/approval", method=RequestMethod.POST)
	private String approval(HttpServletRequest request, Model model){
		System.out.println("TempProdController - approval - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());

		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		
		String prodId = request.getParameter("modal_prodid");
		String approvalVal = request.getParameter("appr_status");
		productService.approvalUpdate(prodId, approvalVal);
		
		
		System.out.println();
		return "redirect:/product";
	}
	
	

}
