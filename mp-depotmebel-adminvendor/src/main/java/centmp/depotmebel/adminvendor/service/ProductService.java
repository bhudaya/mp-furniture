package centmp.depotmebel.adminvendor.service;

import java.util.HashMap;

import centmp.depotmebel.adminvendor.bean.ProductBean;
import centmp.depotmebel.adminvendor.bean.ProductListBean;

public interface ProductService {
	
	ProductListBean list(HashMap<String, Object> hm);
	ProductBean detail(String id);

}
