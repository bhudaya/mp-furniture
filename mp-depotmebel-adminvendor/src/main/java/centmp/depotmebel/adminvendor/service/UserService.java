package centmp.depotmebel.adminvendor.service;

import javax.servlet.http.HttpServletRequest;

import centmp.depotmebel.adminvendor.bean.CommonBean;
import centmp.depotmebel.adminvendor.bean.form.CommonForm;

public interface UserService {
	
	CommonBean login(HttpServletRequest request, CommonForm form);

}
