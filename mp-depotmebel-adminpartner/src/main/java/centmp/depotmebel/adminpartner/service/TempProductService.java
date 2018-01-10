package centmp.depotmebel.adminpartner.service;


import centmp.depotmebel.adminpartner.bean.ProductBean;
import centmp.depotmebel.adminpartner.bean.ProductListBean;
import centmp.depotmebel.adminpartner.bean.UserBean;

public interface TempProductService {
	
	ProductListBean listAll(UserBean userbean);
	ProductBean detail(String id);
	ProductBean approvalUpdate(String prodId, String val);

}
