package centmp.depotmebel.adminvendor.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import centmp.depotmebel.adminvendor.bean.ProductBean;
import centmp.depotmebel.adminvendor.bean.ProductListBean;
import centmp.depotmebel.adminvendor.bean.SessionBean;
import centmp.depotmebel.adminvendor.service.ProductService;
import centmp.depotmebel.adminvendor.util.SessionBeanUtil;
import centmp.depotmebel.core.util.CommonUtil;


@Controller
@RequestMapping("product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value={"","/","/index"}, method=RequestMethod.GET)
	private String index(HttpServletRequest request, Model model){
		System.out.println("ProductController - index - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("start", "0");
		hm.put("max", "50");
		hm.put("userbean", sessionbean.getUser());
		ProductListBean prodlist = productService.list(hm);
		System.out.println("prodlist: "+new Gson().toJson(prodlist));
		model.addAttribute("prodbean", prodlist);
		
		System.out.println();
		return "product/list";
	}
	
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	private String detail(HttpServletRequest request, Model model){
		System.out.println("ProductController - index - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());

		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		//readyToQcNum(model);
		
		String idenc = request.getParameter("q");
		String fromSave = request.getParameter("s");
		
		ProductBean bean = new ProductBean();
		if(!idenc.isEmpty()){
			bean = productService.detail(idenc);
			System.out.println("bean: "+new Gson().toJson(bean));
		}
		
		model.addAttribute("obj", bean);
		if(fromSave!=null&&!fromSave.isEmpty())model.addAttribute("fromSave", fromSave);
		
		System.out.println();
		return "product/detail";
	}
	
}
