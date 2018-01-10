package centmp.depotmebel.adminpartner.service;

import centmp.depotmebel.adminpartner.bean.CommonBean;
import centmp.depotmebel.adminpartner.bean.UserBean;
import centmp.depotmebel.adminpartner.bean.VendorBean;
import centmp.depotmebel.adminpartner.bean.VendorListBean;
import centmp.depotmebel.adminpartner.bean.form.VendorForm;

public interface VendorService {
	
	VendorListBean listAll();
	CommonBean create(VendorForm form, UserBean userbean);
	VendorBean getById(String id);
	
	VendorListBean listJson();
}
