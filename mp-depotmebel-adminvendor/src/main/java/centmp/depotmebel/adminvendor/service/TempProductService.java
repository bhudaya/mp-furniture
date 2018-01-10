package centmp.depotmebel.adminvendor.service;

import java.util.List;

import centmp.depotmebel.adminvendor.bean.ProductBean;
import centmp.depotmebel.adminvendor.bean.ProductListBean;
import centmp.depotmebel.adminvendor.bean.UserBean;
import centmp.depotmebel.adminvendor.bean.form.ProductForm;

public interface TempProductService {
	
	ProductListBean listAll(UserBean userbean);
	ProductBean skuGeneate(List<String[]> attrList);
	ProductBean create(UserBean userbean, ProductForm form);
	ProductBean detail(String id);

}
