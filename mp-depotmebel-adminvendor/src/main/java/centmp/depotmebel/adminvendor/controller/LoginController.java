package centmp.depotmebel.adminvendor.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bilmajdi.util.PropertyMessageUtil;
import com.google.gson.Gson;

import centmp.depotmebel.adminvendor.bean.CommonBean;
import centmp.depotmebel.adminvendor.bean.SessionBean;
import centmp.depotmebel.adminvendor.bean.UserBean;
import centmp.depotmebel.adminvendor.bean.form.CommonForm;
import centmp.depotmebel.adminvendor.service.UserService;
import centmp.depotmebel.adminvendor.util.SessionBeanUtil;

@Controller
@RequestMapping("login")
public class LoginController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value={"","/","/index"}, method=RequestMethod.GET)
	private String index(HttpServletRequest request, Model model){
		System.out.println("LoginController - index");

		SessionBeanUtil sessionutil = new SessionBeanUtil(request);SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean!=null && sessionbean.getErrorCode().equals("0"))return sessionutil.redirectIfNotNull("home");
		
		model.addAttribute("form_", new CommonForm());
		
		System.out.println();
		return "login";
	}
	
	@RequestMapping(value = {"","/","/index"}, method = RequestMethod.POST)
	public String authentication(HttpServletRequest request, Model model) {
		System.out.println("LoginController - authentication");
	
		String useDummy = PropertyMessageUtil.getConfigProperties().getProperty("login.use.dummy");
		if(useDummy.equalsIgnoreCase("true")){
			loginDummy(request);
			return "redirect:/order";
		}
		
		String username = request.getParameter("username")!=null?request.getParameter("username"):"";
		String password = request.getParameter("password")!=null?request.getParameter("password"):"";
		
		/*if(username.equalsIgnoreCase("085781141187") && password.equalsIgnoreCase("aku")){
			loginDummy(request);
			return "redirect:/home";
		}
		
		if(username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin123")){
			loginDummy2(request);
			return "redirect:/home";
		}*/
		
		CommonForm form = new CommonForm();
		form.setName(username);
		form.setPassword(password);
		CommonBean cbean = userService.login(request, form);
		System.out.println("userService-login: "+new Gson().toJson(cbean));
		if(cbean.getErrCode()!=0){
			model.addAttribute("form_", form);
			model.addAttribute("errorMsg", cbean.getErrMsg());
			return "login";
		}
		
		System.out.println();
		return "redirect:/home";
	}
	
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		
		request.getSession().invalidate();
		
		return "redirect:/login";
	}
	
	
	private void loginDummy(HttpServletRequest request){
		SessionBeanUtil sessionutil = new SessionBeanUtil(request);
		HashMap<String, Object> hm = new HashMap<>();
		hm.put(sessionutil.errorCode, "0");
		hm.put(sessionutil.username, "085781141187");
		UserBean centUserBean = new UserBean();
		centUserBean.setId("1000");
		centUserBean.setName("Makhsus Bilmajdi");
		centUserBean.setPhone("085781141187");
		centUserBean.setEmail("biel.makhsus@gmail.com");
		hm.put(sessionutil.userObj, centUserBean); 
		
		sessionutil.setSession(hm);
	}
	
	public void loginDummy2(HttpServletRequest request){
		SessionBeanUtil sessionutil = new SessionBeanUtil(request);
		HashMap<String, Object> hm = new HashMap<>();
		hm.put(sessionutil.errorCode, "0");
		hm.put(sessionutil.username, "085781141187");
		UserBean centUserBean = new UserBean();
		centUserBean.setId("1001");
		centUserBean.setName("Admin Tara Porter");
		centUserBean.setPhone("-");
		centUserBean.setEmail("-");
		hm.put(sessionutil.userObj, centUserBean); 
		
		sessionutil.setSession(hm);
	}
	
}
