package centmp.depotmebel.adminvendor.util;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import com.google.gson.Gson;

import centmp.depotmebel.adminvendor.bean.SessionBean;
import centmp.depotmebel.adminvendor.bean.UserBean;


public class SessionBeanUtil {

	public final String sessionVar  = "sessionVendor_";
	public String errorCode = "errorCode";
	public String errorMsg = "errorMsg";
	public String username = "username";
	public String userObj = "user_vendor_";
	
	private HttpServletRequest request;

	public SessionBeanUtil(HttpServletRequest request) {
		this.request = request;
	}
	
	public void setSession(HashMap<String, Object> hm){
		String  errorCode = (String) hm.get(this.errorCode);
		String errorMsg = (String) hm.get(this.errorMsg);
		String username = (String) hm.get(this.username);
		UserBean user = (UserBean) hm.get(this.userObj);
		
		SessionBean sessionbean = new SessionBean();
		sessionbean.setErrorCode(errorCode);
		sessionbean.setErrorMsg(errorMsg);
		sessionbean.setUsername(username);
		sessionbean.setUser(user);
		
		request.getSession().setAttribute(sessionVar, sessionbean);
	}
	
	public SessionBean getSession(){
		SessionBean sessionbean = null;
		if(request!=null){
			sessionbean = (SessionBean) request.getSession().getAttribute(sessionVar);
			System.out.println(sessionVar+": "+new Gson().toJson(sessionbean));
			
			/*System.out.println("1: "+request.getRequestURI());
			System.out.println("2: "+request.getRequestURL());
			System.out.println("3: "+request.getContextPath());
			System.out.println("4: "+request.getLocalName());
			System.out.println("5: "+request.getScheme());
			System.out.println("7: "+request.getServletPath());*/
			
			String servletPath = request.getServletPath()!=null?request.getServletPath():"";
			
			request.setAttribute("mnActiveDashboard", "");
			request.setAttribute("mnActiveProduct", "");
			request.setAttribute("mnActiveVendor", "");
			request.setAttribute("mnActiveOrder", "");
			request.setAttribute("mnActiveOrderVdr", "");
			
			request.setAttribute("mnActiveUsers", "");
			request.setAttribute("mnActiveEditProf", "");
			
			if(servletPath.startsWith("/home")){
				request.setAttribute("mnActiveDashboard", "active");
			}else if(servletPath.startsWith("/product")){
				request.setAttribute("mnActiveProduct", "active");
			}else if(servletPath.startsWith("/tempproduct")){
				request.setAttribute("mnActiveProduct", "active");
			}else if(servletPath.startsWith("/vendor")){
				request.setAttribute("mnActiveVendor", "active");
			}else if(servletPath.startsWith("/order")){
				String split[] = servletPath.split("/order");
				if(split.length>1 && split[1].equalsIgnoreCase("/vendor")){
					request.setAttribute("mnActiveOrderVdr", "active");
				}else{
					request.setAttribute("mnActiveOrder", "active");
				}
			}else if(servletPath.startsWith("/users")){
				String split[] = servletPath.split("/users");
				if(split.length>1 && split[1].equalsIgnoreCase("/edit")){
					request.setAttribute("mnActiveEditProf", "active");
				}else{
					request.setAttribute("mnActiveUsers", "active");
				}
				
			}
			
			//request.setAttribute("sessionbean", sessionbean);
		}
		
		return sessionbean;
	}
	
	public String redirectIfNull(){
		return "redirect:/login";
	}
	
	public String redirectIfNotNull(String pathUrl){
		//String url = PropertyMessageUtil.getConfigProperties().getProperty("redirect.default.url");
		return "redirect:/"+pathUrl;
	}
	
}
