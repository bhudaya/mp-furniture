package centmp.depotmebel.adminvendor.controller;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bilmajdi.util.CipherUtil;
import com.google.gson.Gson;

import centmp.depotmebel.adminvendor.bean.SessionBean;
import centmp.depotmebel.adminvendor.bean.TrxBean;
import centmp.depotmebel.adminvendor.bean.UserBean;
import centmp.depotmebel.adminvendor.service.OrderService;
import centmp.depotmebel.adminvendor.util.SessionBeanUtil;
import centmp.depotmebel.core.enums.VENDOR_USER_TYPE;
import centmp.depotmebel.core.util.CommonUtil;


@Controller
@RequestMapping("order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	private static final String[] ACCEPTED_EXTENSIONS = new String[] {"png","jpg","jpeg"};
	
	@RequestMapping(value={"","/","/index"}, method=RequestMethod.GET)
	private String index(HttpServletRequest request, Model model){
		System.out.println("OrderController - index - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		
		List<TrxBean> trxList = orderService.orderList(sessionbean);
		System.out.println("trxList: "+new Gson().toJson(trxList));
		model.addAttribute("trxList", trxList);
		
		System.out.println();
		return "order/list";
	}
	
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	private String detail(HttpServletRequest request, Model model){
		System.out.println("OrderController - detail - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		
		TrxBean bean = new TrxBean();
		String x = request.getParameter("x");
		System.out.println("x: "+x);
		try {
			String decr = CipherUtil.decrypt(x);
			System.out.println("decr: "+decr);
			String[] split = decr.split(";");
			String itemId = split[1];
			
			bean = orderService.detail(itemId);
			System.out.println("bean: "+new Gson().toJson(bean));
			model.addAttribute("bean", bean);
		} catch (Exception e) {
			//e.printStackTrace();
		}

		System.out.println();
		return "order/view";
	}
	
	@RequestMapping(value="/toacknowledged", method=RequestMethod.POST)
	private String toAcknowledged(HttpServletRequest request, Model model){
		System.out.println("VendorController - toacknowledged - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		
		String itemId = request.getParameter("itemId");
		boolean result = orderService.toAcknowledged(sessionbean, itemId);
		if(result){model.addAttribute("message", "T");
		}else{model.addAttribute("message", "F");}
	
		System.out.println();
		return "plain";
	}
	
	@RequestMapping(value="/voprocess", method=RequestMethod.GET)
	private String voprocess(HttpServletRequest request, Model model){
		System.out.println("OrderController - voprocess - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		
		String x = request.getParameter("x");
		String m = request.getParameter("m");
		
		try {
			String decr = CipherUtil.decrypt(x);
			System.out.println("decr: "+decr);
			String[] split = decr.split(";");
			String lastStatus = split[0];
			String itemId = split[1];
			UserBean userban = sessionbean.getUser();
			String[] vendors = userban.getVendors();
			String userType = vendors[3];
			
			String errMsg =  (m!=null&&!m.isEmpty())?CipherUtil.decrypt(m):"";
			
			if(userType.equalsIgnoreCase(Integer.toString(VENDOR_USER_TYPE.OPERATOR.ordinal())) && lastStatus.equalsIgnoreCase("6")){
				TrxBean bean = orderService.voProcess(itemId);
				System.out.println("bean: "+new Gson().toJson(bean));
				
				model.addAttribute("errorMsg", errMsg);
				model.addAttribute("bean", bean);
				model.addAttribute("lastStatusItem", lastStatus);
				
				System.out.println();
				return "order/vo_process";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println();
		return "redirect:/order";
	}
	
	@RequestMapping(value="/voprocess", method=RequestMethod.POST)
	private String voprocessSubmit(HttpServletRequest request, Model model, @RequestParam("img_qc") MultipartFile imgQc){
		System.out.println("OrderController - voprocessSubmit - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		
		String itemId = request.getParameter("itemId");
		String statusItem = request.getParameter("statusItem");
		
		String urlParams = "";
		try {
			String urlParam1 = statusItem+";"+itemId;
			urlParams = URLEncoder.encode(CipherUtil.encrypt(urlParam1), "UTF-8");
			
			boolean fileValidate = CommonUtil.fileValidate(ACCEPTED_EXTENSIONS, imgQc.getOriginalFilename());
			if(!fileValidate){
				String msg = "File yang Anda Upload tidak valid, gunakan salah satu tipe file berikut: png, jpeg & jpg.";
				String mge = URLEncoder.encode(CipherUtil.encrypt(msg), "UTF-8");
				
				System.out.println("fileValidate: false");
				System.out.println();
				return "redirect:/order/voprocess?x="+urlParams+"&m="+mge;
			}
			
			boolean result = orderService.readyToQc(sessionbean, itemId, imgQc);
			if(!result){
				String msg = "Terjadi kesalahan pada sistem kami. Silahkan hubungi Admin.";
				String mge = URLEncoder.encode(CipherUtil.encrypt(msg), "UTF-8");
				System.out.println("orderService.readyToQc: false");
				
				System.out.println();
				return "redirect:/order/voprocess?x="+"&m="+mge;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/order/voprocess?x="+urlParams;
		}
		
		System.out.println();
		return "redirect:/order";
	}
	
	@RequestMapping(value="/tocourier", method=RequestMethod.GET)
	private String tocourier(HttpServletRequest request, Model model){
		System.out.println("OrderController - tocourier - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		
		String x = request.getParameter("x");
		try {
			String decr = CipherUtil.decrypt(x);
			String[] split = decr.split(";");
			String lastStatus = split[0];
			String itemId = split[1];
			UserBean userban = sessionbean.getUser();
			String[] vendors = userban.getVendors();
			String userType = vendors[3];
			
			if(userType.equalsIgnoreCase(Integer.toString(VENDOR_USER_TYPE.OPERATOR.ordinal())) && lastStatus.equalsIgnoreCase("3")){
				TrxBean bean = orderService.toCourierForm(itemId);
				System.out.println("bean-tokurir: "+new Gson().toJson(bean));
				model.addAttribute("bean", bean);
				System.out.println();
				return "order/tocourier";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println();
		return "redirect:/order";
	}
	
	@RequestMapping(value="/tocourier", method=RequestMethod.POST)
	private String tocourierSubmit(HttpServletRequest request, Model model){
		System.out.println("OrderController - tocourierSubmit - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		
		String itemId = request.getParameter("itemId");
		String courierId = request.getParameter("courier_id");
		orderService.toCourierSubmit(sessionbean, itemId, courierId);
		
		System.out.println();
		return "redirect:/order";
	}
	
	@RequestMapping(value="/pod", method=RequestMethod.GET)
	private String podForm(HttpServletRequest request, Model model){
		System.out.println("OrderController - podForm - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		
		String x = request.getParameter("x");
		String m = request.getParameter("m");
		
		try {
			String decr = CipherUtil.decrypt(x);
			String[] split = decr.split(";");
			String lastStatus = split[0];
			String itemId = split[1];
			UserBean userban = sessionbean.getUser();
			String[] vendors = userban.getVendors();
			String userType = vendors[3];
			
			String errMsg =  (m!=null&&!m.isEmpty())?CipherUtil.decrypt(m):"";
			
			if(userType.equalsIgnoreCase(Integer.toString(VENDOR_USER_TYPE.COURIER.ordinal())) && lastStatus.equalsIgnoreCase("4")){
				TrxBean bean = orderService.podForm(itemId);
				System.out.println("bean-pod: "+new Gson().toJson(bean));
				
				model.addAttribute("errorMsg", errMsg);
				model.addAttribute("bean", bean);
				model.addAttribute("lastStatusItem", lastStatus);
				
				System.out.println();
				return "order/pod";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println();
		return "redirect:/order";
	}
	
	@RequestMapping(value="/pod", method=RequestMethod.POST)
	private String podSubmit(HttpServletRequest request, Model model, @RequestParam("img_pod") MultipartFile imgPod){
		System.out.println("OrderController - podSubmit");
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request); SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean==null || (sessionbean!=null && !sessionbean.getErrorCode().equals("0"))) return sessionutil.redirectIfNull();
		
		String itemId = request.getParameter("itemId");
		String statusItem = request.getParameter("statusItem");
		String receiverName = request.getParameter("receiver_name");
		String receiverPhone = request.getParameter("receiver_phone");

		String urlParams = "";
		try {
			String urlParam1 = statusItem+";"+itemId;
			urlParams = URLEncoder.encode(CipherUtil.encrypt(urlParam1), "UTF-8");
			
			boolean fileValidate = CommonUtil.fileValidate(ACCEPTED_EXTENSIONS, imgPod.getOriginalFilename());
			if(!fileValidate){
				String msg = "File yang Anda Upload tidak valid, gunakan salah satu tipe file berikut: png, jpeg & jpg.";
				String mge = URLEncoder.encode(CipherUtil.encrypt(msg), "UTF-8");
				
				System.out.println("fileValidate: false");
				System.out.println();
				return "redirect:/order/pod?x="+urlParams+"&m="+mge;
			}
			
			if(!orderService.podFormSubmit(sessionbean, itemId, imgPod, receiverName, receiverPhone)){
				String msg = "Terjadi kesalahan pada sistem kami. Silahkan hubungi Admin.";
				String mge = URLEncoder.encode(CipherUtil.encrypt(msg), "UTF-8");
				
				System.out.println("orderService.podFormSubmit: false");
				System.out.println();
				return "redirect:/order/pod?x="+urlParams+"&m="+mge;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/order/pod?x="+urlParams;
		}
		
		System.out.println();
		return "redirect:/order";
	}

}
