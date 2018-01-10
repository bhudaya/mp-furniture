package centmp.depotmebel.adminpartner.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import centmp.depotmebel.adminpartner.bean.CommonBean;
import centmp.depotmebel.adminpartner.bean.UserBean;
import centmp.depotmebel.adminpartner.bean.form.CommonForm;

public interface UserService {
	
	CommonBean login(HttpServletRequest request, CommonForm form);
	CommonBean insertNew(HashMap<String, Object> hm, UserBean userbean);
	
}
