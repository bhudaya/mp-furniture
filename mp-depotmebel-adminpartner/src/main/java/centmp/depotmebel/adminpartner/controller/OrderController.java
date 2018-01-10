package centmp.depotmebel.adminpartner.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bilmajdi.util.CipherUtil;
import com.google.gson.Gson;

import centmp.depotmebel.adminpartner.bean.OrderBean;
import centmp.depotmebel.adminpartner.bean.OrderListBean;
import centmp.depotmebel.adminpartner.bean.SessionBean;
import centmp.depotmebel.adminpartner.service.OrderService;
import centmp.depotmebel.adminpartner.util.SessionBeanUtil;
import centmp.depotmebel.core.util.CommonUtil;

@Controller
@RequestMapping("order")
public class OrderController extends ParentController {
	
	@Autowired
	private OrderService orderService;

	
	@RequestMapping(value={"","/","/index"}, method=RequestMethod.GET)
	private String index(HttpServletRequest request, Model model){
		System.out.println("OrderController - index - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		taskList(model);
		
		OrderListBean listbean = orderService.list();
		int readyQcNum = orderService.readyQcNum();
		
		listbean.setReadyQcNum(readyQcNum);
		System.out.println("bean: "+new Gson().toJson(listbean));
		model.addAttribute("listbean", listbean);
			
		System.out.println();
		return "order/list";
	}
	
	@RequestMapping(value="view", method=RequestMethod.GET)
	private String view(HttpServletRequest request, Model model){
		System.out.println("OrderController - view - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		taskList(model);
		
		String x = request.getParameter("x");
		System.out.println("x: "+x);
		OrderBean bean = orderService.detail(x);
		System.out.println("bean: "+new Gson().toJson(bean));
		model.addAttribute("bean", bean);
		try {
			model.addAttribute("x_", URLEncoder.encode(x, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	
		System.out.println();
		return "order/view";
	}
	
	@RequestMapping(value="unallocateds", method=RequestMethod.GET)
	private String unallocatedList(HttpServletRequest request, Model model){
		System.out.println("OrderController - unallocatedList- "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		taskList(model);
		
		OrderListBean listbean = orderService.unallocatedList();
		System.out.println("bean: "+new Gson().toJson(listbean));
		model.addAttribute("listbean", listbean);
		
		System.out.println();
		return "order/list-unallocated";
	}
	
	@RequestMapping(value="setvendor", method=RequestMethod.POST)
	private String setVendor(HttpServletRequest request, Model model){
		System.out.println("OrderController - setVendor - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		
		String itemId = request.getParameter("itemId");
		String vendorId = request.getParameter("vendorId");
		String res = orderService.setVendor(itemId, vendorId);
		model.addAttribute("message", res);
		
		System.out.println();
		return "plain";
	}
	
	@RequestMapping(value="qclist", method=RequestMethod.GET)
	private String qcList(HttpServletRequest request, Model model){
		System.out.println("OrderController - qcList - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		taskList(model);
		
		OrderListBean orderList = orderService.qcList();
		System.out.println("orderList: "+new Gson().toJson(orderList));
		model.addAttribute("qcList", orderList);
		
		System.out.println();
		return "order/qclist";
	}
	
	@RequestMapping(value="readyqc", method=RequestMethod.GET)
	private String qcDetail(HttpServletRequest request, Model model){
		System.out.println("OrderController - qcDetail - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		taskList(model);
		
		String itemId = "-1";
		try {
			String x = request.getParameter("x");
			itemId = CipherUtil.decrypt(x);
			System.out.println("itemId: "+itemId);
		} catch (Exception e) {e.printStackTrace();}
		
		OrderBean orderbean = orderService.qcDetail(itemId);
		System.out.println("orderbean: "+new Gson().toJson(orderbean));
		model.addAttribute("bean", orderbean);
		
		System.out.println();
		return "order/readyqc";
	}
	
	@RequestMapping(value="passqc", method=RequestMethod.POST)
	private String passqc(HttpServletRequest request, Model model){
		System.out.println("OrderController - passqc");
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		
		String itemId = request.getParameter("itemId");
		System.out.println("itemId: "+itemId);
		orderService.passQc(itemId);
		
		System.out.println();
		return "plain";
	}
	
}
