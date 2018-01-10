package centmp.depotmebel.adminpartner.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import centmp.depotmebel.adminpartner.bean.CustomerBean;
import centmp.depotmebel.adminpartner.bean.SessionBean;
import centmp.depotmebel.adminpartner.service.CustomerService;
import centmp.depotmebel.adminpartner.util.SessionBeanUtil;
import centmp.depotmebel.core.util.CommonUtil;


@Controller
@RequestMapping("customer")
public class CustomerController extends ParentController {
	
	@Autowired
	private CustomerService customerService;
	
	
	@RequestMapping(value="upgrade/list", method=RequestMethod.GET)
	private String requestUpgradeList(HttpServletRequest request, Model model){
		System.out.println(this.getClass().getName()+" - requestUpgradeList - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		taskList(model);
		
		List<CustomerBean> list = customerService.requestApprovalUpgrades();
		model.addAttribute("list", list);
		
		System.out.println();
		return "customer/approval-list";
	}
	
	@RequestMapping(value="upgrade/detail", method=RequestMethod.GET)
	private String requestUpgradeDetail(HttpServletRequest request, Model model){
		System.out.println(this.getClass().getName()+" - requestUpgradeDetail - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		taskList(model);
		
		String idencr = request.getParameter("id");
		CustomerBean bean = customerService.detailApprovalUpgrades(idencr);
		System.out.println("bean: "+new Gson().toJson(bean));
		model.addAttribute("bean", bean);
		
		System.out.println();
		return "customer/approval-form";
	}

}
