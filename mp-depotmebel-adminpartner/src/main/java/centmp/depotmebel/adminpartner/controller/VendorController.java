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

import centmp.depotmebel.adminpartner.bean.SessionBean;
import centmp.depotmebel.adminpartner.bean.VendorBean;
import centmp.depotmebel.adminpartner.bean.VendorListBean;
import centmp.depotmebel.adminpartner.bean.form.VendorForm;
import centmp.depotmebel.adminpartner.service.ProvinceService;
import centmp.depotmebel.adminpartner.service.VendorService;
import centmp.depotmebel.adminpartner.util.SessionBeanUtil;
import centmp.depotmebel.core.enums.VENDOR_USER_TYPE;
import centmp.depotmebel.core.util.CommonUtil;

@Controller
@RequestMapping("vendor")
public class VendorController extends ParentController {
	
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private VendorService vendorService;

	@RequestMapping(value={"","/","/index"}, method=RequestMethod.GET)
	private String index(HttpServletRequest request, Model model){
		System.out.println("VendorController - index - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		taskList(model);
		
		VendorListBean listbean = vendorService.listAll();
		List<VendorBean> vendor_list = listbean.getVendorList();
		model.addAttribute("objList", vendor_list);
		
		System.out.println();
		return "vendor/list";
	}
	
	@RequestMapping(value="add", method=RequestMethod.GET)
	private String add(HttpServletRequest request, Model model){
		System.out.println("VendorController - add - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		taskList(model);
		
		List<String[]> provinceList = provinceService.listForOption("");
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("userRoles", VENDOR_USER_TYPE.toStringArray2d());
		model.addAttribute("userRolesStr", VENDOR_USER_TYPE.toStringForJs());
		
		System.out.println();
		return "vendor/add";
	}
	
	@RequestMapping(value="add", method=RequestMethod.POST)
	private String addSubmit(HttpServletRequest request, Model model){
		System.out.println("VendorController - addSubmit - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		taskList(model);
		
		String name = request.getParameter("name_in");
		String picName = request.getParameter("pic_name_in");
		String picPhone = request.getParameter("pic_phone_in");
		String address = request.getParameter("address_in");
		String prov = request.getParameter("prov_in");
		String city = request.getParameter("city_in");
		String postal = request.getParameter("postal_in");
		String creditLimit = request.getParameter("crdlmt_in");
		String deliverCapacity = request.getParameter("delvr_in");
		
		List<String[]> coverageList = new ArrayList<>();
		String cvr_idcoll = request.getParameter("cvr_idcoll");
		String[] coverage_split = cvr_idcoll.split(";");
		for(int i=0;i<coverage_split.length;i++){
			String seq = coverage_split[i].trim();
			String cityId = request.getParameter("pcvr_city_in_"+seq);
			String sla = request.getParameter("pcvr_sla_in_"+seq);
			
			if((cityId!=null&&!cityId.isEmpty())&&(sla!=null&&!sla.isEmpty())){
				String[] str = new String[2];
				str[0] = cityId;
				str[1] = sla;
				coverageList.add(str);
			}
		}
		
		List<String[]> vuserList = new ArrayList<>();
		String huser_seqcoll = request.getParameter("huser_nocoll");
		String[] huser_seqcoll_split = huser_seqcoll.split(";");
		for(int i=0;i<huser_seqcoll_split.length;i++){
			String seq = huser_seqcoll_split[i].trim();
			String vu_name = request.getParameter("nr_name_"+seq);
			String vu_email = request.getParameter("nr_email_"+seq);
			String vu_phone = request.getParameter("nr_phone_"+seq);
			String vu_role = request.getParameter("nr_role_"+seq);
			
			String[] str = new String[4];
			str[0] = vu_name!=null?vu_name.trim():"";
			str[1] = vu_email!=null?vu_email.trim():"";
			str[2] = vu_phone!=null?vu_phone.trim():"";
			str[3] = vu_role;
			vuserList.add(str);
		}
		System.out.println("vuserList: "+new Gson().toJson(vuserList));
		
		VendorForm form = new VendorForm();
		form.setName(name);
		form.setPicName(picName);
		form.setPicPhone(picPhone);
		form.setAddress(address);
		form.setProvince(prov);
		form.setCity(city);
		form.setPostalCode(postal);
		form.setCreditLimit(creditLimit);
		form.setDeliveryCapacity(deliverCapacity);
		form.setCoverageList(coverageList);
		form.setUserList(vuserList);
		vendorService.create(form, sessionbean.getUser());
		
		System.out.println();
		return "redirect:/vendor";
	}
	
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	private String detail(HttpServletRequest request, Model model, @RequestParam("q") String idenc){
		System.out.println("VendorController - detail");
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		taskList(model);
		
		VendorBean bean = vendorService.getById(idenc);
		System.out.println("VendorBean: "+new Gson().toJson(bean));
		
		model.addAttribute("obj", bean);
		model.addAttribute("userRoles", VENDOR_USER_TYPE.toStringArray2d());
		
		System.out.println();
		return "vendor/view";
	}
	
	@RequestMapping(value="list/json", method=RequestMethod.POST)
	private String listJson(HttpServletRequest request, Model model){
		System.out.println("VendorController - listJson");

		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		
		VendorListBean listbean = vendorService.listJson();
		String res = new Gson().toJson(listbean);
		System.out.println("Res: "+res);
		model.addAttribute("message", res);
		
		System.out.println();
		return "plain";
	}
	
}
