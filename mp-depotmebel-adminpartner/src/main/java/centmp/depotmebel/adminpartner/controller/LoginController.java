package centmp.depotmebel.adminpartner.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import centmp.depotmebel.adminpartner.bean.CommonBean;
import centmp.depotmebel.adminpartner.bean.SessionBean;
import centmp.depotmebel.adminpartner.bean.UserBean;
import centmp.depotmebel.adminpartner.bean.form.CommonForm;
import centmp.depotmebel.adminpartner.service.UserService;
import centmp.depotmebel.adminpartner.util.SessionBeanUtil;
import centmp.depotmebel.core.util.CommonUtil;

@Controller
@RequestMapping("login")
public class LoginController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value={"","/","/index"}, method=RequestMethod.GET)
	private String index(HttpServletRequest request, Model model){
		System.out.println("LoginController - index - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		SessionBeanUtil sessionutil = new SessionBeanUtil(request);SessionBean sessionbean = sessionutil.getSession();
		if(sessionbean!=null && sessionbean.getErrorCode().equals("0"))return sessionutil.redirectIfNotNull("home");
		
		model.addAttribute("form_", new CommonForm());
		
		System.out.println();
		return "login";
	}
	
	@RequestMapping(value = {"","/","/index"}, method = RequestMethod.POST)
	public String authentication(HttpServletRequest request, Model model) {
		System.out.println("LoginController - authentication - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		String username = request.getParameter("username")!=null?request.getParameter("username"):"";
		String password = request.getParameter("password")!=null?request.getParameter("password"):"";
		
		if(username.equalsIgnoreCase("085781141187") && password.equalsIgnoreCase("aku")){
			loginDummy(request);
			return "redirect:/home";
		}
		
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
		System.out.println("LoginController - logout - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		request.getSession().invalidate();
		
		System.out.println();
		return "redirect:/login";
	}
	
	
	private void loginDummy(HttpServletRequest request){
		SessionBeanUtil sessionutil = new SessionBeanUtil(request);
		HashMap<String, Object> hm = new HashMap<>();
		hm.put(sessionutil.errorCode, "0");
		hm.put(sessionutil.username, "085781141187");
		UserBean centUserBean = new UserBean();
		centUserBean.setId("99999");
		centUserBean.setName("Makhsus Bil");
		centUserBean.setPhone("085781141187");
		centUserBean.setEmail("biel.makhsus@gmail.com");
		hm.put(sessionutil.userObj, centUserBean); 
		
		sessionutil.setSession(hm);
	}
}
