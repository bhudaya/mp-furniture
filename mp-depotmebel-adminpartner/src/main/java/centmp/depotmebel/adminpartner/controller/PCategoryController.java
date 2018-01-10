package centmp.depotmebel.adminpartner.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

import centmp.depotmebel.adminpartner.bean.PCategoryBean;
import centmp.depotmebel.adminpartner.bean.SessionBean;
import centmp.depotmebel.adminpartner.bean.form.PCategoryForm;
import centmp.depotmebel.adminpartner.service.PCategoryService;
import centmp.depotmebel.adminpartner.util.SessionBeanUtil;
import centmp.depotmebel.core.util.CommonUtil;


@Controller
@RequestMapping("category")
public class PCategoryController extends ParentController {
	
	@Autowired
	private PCategoryService pcategoryService;
	
	
	@RequestMapping(value={"","/","/index"}, method=RequestMethod.GET)
	private String index(HttpServletRequest request, Model model){
		System.out.println("PCategoryController - index - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		taskList(model);
		
		List<PCategoryBean> list = pcategoryService.listAll();
		model.addAttribute("beanlist", list);
		
		System.out.println();
		return "pcategory/list";
	}
	
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	private String detail(HttpServletRequest request, Model model, @RequestParam("q") String idenc){
		System.out.println("PCategoryController - detail - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());

		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		taskList(model);
		
		PCategoryForm form = pcategoryService.detail(idenc);
		model.addAttribute("obj", form);
		
		System.out.println();
		return "pcategory/detail";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	private String create(HttpServletRequest request, Model model){
		System.out.println("PCategoryController - create - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());

		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		taskList(model);
		
		/*PCategoryForm form = pcategoryService.detail(idenc);
		model.addAttribute("obj", form);*/
		
		System.out.println();
		return "pcategory/create";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	private String createSubmit(HttpServletRequest request, Model model){
		System.out.println("PCategoryController - createSubmit - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());

		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		taskList(model);
		
		String name = request.getParameter("pname_in");
		System.out.println("name: "+name);
		
		List<String[]> subList = new ArrayList<>();
		String sub_idcoll = request.getParameter("sub_idcoll");
		String[] subSplit = sub_idcoll.split(";");
		for(int i=0;i<subSplit.length;i++){
			String id = subSplit[i].trim();
			if(!id.isEmpty()){
				String val = request.getParameter("sub_name_hd"+id);
				
				String[] attrs = new String[2];
				attrs[0] = id;
				attrs[1] = val;
				subList.add(attrs);
			}
		}
		System.out.println("subList: "+new Gson().toJson(subList));
		
		List<String[]> specList = new ArrayList<>();
		String spec_idcoll = request.getParameter("spec_idcoll");
		String[] specSplit = spec_idcoll.split(";");
		for(int i=0;i<specSplit.length;i++){
			String id = specSplit[i].trim();
			if(!id.isEmpty()){
				String val = request.getParameter("spec_name_hd"+id);
				
				String[] attrs = new String[2];
				attrs[0] = id;
				attrs[1] = val;
				specList.add(attrs);
			}
		}
		System.out.println("specList: "+new Gson().toJson(specList));
		
		List<String[]> attrList = new ArrayList<>();
		String attr_idcoll = request.getParameter("attr_idcoll");
		String[] attrSplit = attr_idcoll.split(";");
		for(int i=0;i<attrSplit.length;i++){
			String id = attrSplit[i].trim();
			if(!id.isEmpty()){
				String val = request.getParameter("attr_name_hd"+id);
				
				String[] attrs = new String[2];
				attrs[0] = id;
				attrs[1] = val;
				attrList.add(attrs);
			}
		}
		System.out.println("attrList: "+new Gson().toJson(attrList));
		
		PCategoryForm form = new PCategoryForm();
		form.setName(name);
		form.setSubList(subList);
		form.setSpecList(specList);
		form.setAttrList(attrList);
		pcategoryService.create(form);
		
		System.out.println();
		return "redirect:/category/create";
	}
	
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
