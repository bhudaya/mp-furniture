package centmp.depotmebel.adminpartner.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import centmp.depotmebel.adminpartner.bean.CommonBean;
import centmp.depotmebel.adminpartner.bean.PCategoryBean;
import centmp.depotmebel.adminpartner.bean.ProductBean;
import centmp.depotmebel.adminpartner.bean.ProductListBean;
import centmp.depotmebel.adminpartner.bean.SessionBean;
import centmp.depotmebel.adminpartner.bean.form.ProductForm;
import centmp.depotmebel.adminpartner.service.PCategoryService;
import centmp.depotmebel.adminpartner.service.ProductService;
import centmp.depotmebel.adminpartner.util.SessionBeanUtil;
import centmp.depotmebel.core.util.CommonUtil;


@Controller
@RequestMapping("product")
public class ProductController extends ParentController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private PCategoryService pcategoryService;
	
	@RequestMapping(value={"","/","/index"}, method=RequestMethod.GET)
	private String index(HttpServletRequest request, Model model){
		System.out.println("ProductController - index - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		taskList(model);
		
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("start", "0");
		hm.put("max", "50");
		ProductListBean prodlist = productService.list(hm);
		System.out.println("prodlist: "+new Gson().toJson(prodlist));
		model.addAttribute("prodbean", prodlist);
		
		
		System.out.println();
		return "product/list";
	}
	
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	private String detail(HttpServletRequest request, Model model, @RequestParam("q") String idenc){
		System.out.println("ProductController - index - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());

		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		taskList(model);
		
		ProductBean bean = productService.detail(idenc);
		System.out.println("bean: "+new Gson().toJson(bean));
		model.addAttribute("obj", bean);
		
		System.out.println();
		return "product/detail";
	}
	
	@RequestMapping(value="/sku/generate/json", method=RequestMethod.POST)
	private String skuGenerateJson(HttpServletRequest request, Model model){
		System.out.println("ProductController - skuGenerateJson - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());

		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		//readyToQcNum(model);
		
		List<String[]> attrList = new ArrayList<>();
		String attr_idcoll = request.getParameter("attr_idcoll");
		String[] attrSplit = attr_idcoll.split(";");
		for(int i=0;i<attrSplit.length;i++){
			String id = attrSplit[i].trim();
			if(!id.isEmpty()){
				String val = request.getParameter("attr_lbl_"+id);
				
				String[] attrs = new String[2];
				attrs[0] = id;
				attrs[1] = val;
				attrList.add(attrs);
			}
		}
		//System.out.println("attrList: "+new Gson().toJson(attrList));
		//System.out.println("attr_idcoll: "+attr_idcoll);
		ProductBean bean = productService.skuGeneate(attrList);
		String json = new Gson().toJson(bean);
		System.out.println("bean: "+json);
		model.addAttribute("message", json);
		
		System.out.println();
		return "plain";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	private String prodCreate(HttpServletRequest request, Model model){
		System.out.println("ProductController - prodCreate - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		taskList(model);
		
		List<PCategoryBean> pcategoryList = pcategoryService.listByActive();
		
		ProductForm form = new ProductForm();
		model.addAttribute("objForm", form);
		model.addAttribute("pcategoryList", pcategoryList);
		
		System.out.println();
		return "product/create";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	private String prodCreateSubmit(HttpServletRequest request, Model model, @RequestParam("pimages1_in") MultipartFile image1, 
			@RequestParam("pimages2_in") MultipartFile image2, @RequestParam("pimages3_in") MultipartFile image3){
		System.out.println("ProductController - prodCreateSubmit - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
	
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		//readyToQcNum(model);
		
		String name = request.getParameter("pname_in");
		String category = request.getParameter("pcategory_in");
		String subcategory = request.getParameter("psubcategory_in");
		String price = request.getParameter("pprice_in");
		String desc = request.getParameter("pdesc_in");
		
		List<MultipartFile> images = new ArrayList<>();
		if(image1!=null&&image1.getSize()>0)images.add(image1);
		if(image2!=null&&image2.getSize()>0)images.add(image2);
		if(image3!=null&&image3.getSize()>0)images.add(image3);
		
		String spec_idcoll = request.getParameter("spec_idcoll");
		List<String[]> specList = new ArrayList<>();
		String[] specSplit = spec_idcoll.split(";");
		for(int i=0;i<specSplit.length;i++){
			String id = specSplit[i].trim();
			if(!id.isEmpty()){
				String val = request.getParameter("spec_lbl_"+id);
				
				String[] specss = new String[2];
				specss[0] = id;
				specss[1] = val.trim();
				specList.add(specss);
			}
		}
		
		String attr_idcoll = request.getParameter("attr_idcoll");
		List<String[]> attrList = new ArrayList<>();
		String[] attrSplit = attr_idcoll.split(";");
		for(int i=0;i<attrSplit.length;i++){
			String id = attrSplit[i].trim();
			if(!id.isEmpty()){
				String val = request.getParameter("attr_lbl_"+id);
				
				String[] attrs = new String[2];
				attrs[0] = id;
				attrs[1] = val.trim();
				attrList.add(attrs);
			}
		}
		
		List<String[]> skuList = new ArrayList<>();
		String sku_idcoll = request.getParameter("sku_idcoll");
		String[] skuSplit = sku_idcoll.split(";");
		for(int i=0;i<skuSplit.length;i++){
			String id = skuSplit[i].trim();
			if(!id.isEmpty()){
				String label = request.getParameter("prodsku_label_"+id);
				String remark = request.getParameter("prodsku_remark_"+id);
				String stok = request.getParameter("prodsku_stok_"+id);
				String adtPrice = request.getParameter("prodsku_price_"+id);
				
				String[] arr = new String[4];
				arr[0] = label.trim();
				arr[1] = remark.trim();
				arr[2] = stok.trim();
				arr[3] = adtPrice.trim();
				skuList.add(arr);
			}
		}
		System.out.println("skuList: "+new Gson().toJson(skuList));
		
		
		ProductForm form = new ProductForm();
		form.setName(name.trim());
		form.setPcategoryId(category.trim());
		form.setPcategorySubId(subcategory.trim());
		form.setBasePrice(price.trim());
		form.setDesc(desc.trim());
		form.setImages(images);
		form.setSpecList(specList);
		form.setAttrList(attrList);
		form.setSkuList(skuList);
		
		String returnStr = "redirect:/product";
		CommonBean bean = productService.create(form);
		if(bean.getErrCode()==0){
			if(bean.getId()!=null&&!bean.getId().isEmpty()){
				try {
					returnStr = "redirect:/product/detail?q="+URLEncoder.encode(bean.getId(), "UTF-8")+"&s=0";
				} catch (UnsupportedEncodingException e) {}
			}
		}else{
			//perlu ditest lagi
			returnStr = "redirect:/product/create";
		}
		
		return returnStr;
	}

}
