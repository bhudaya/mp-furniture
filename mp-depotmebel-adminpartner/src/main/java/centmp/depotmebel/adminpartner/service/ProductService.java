package centmp.depotmebel.adminpartner.service;

import java.util.HashMap;
import java.util.List;

import centmp.depotmebel.adminpartner.bean.CommonBean;
import centmp.depotmebel.adminpartner.bean.ProductBean;
import centmp.depotmebel.adminpartner.bean.ProductListBean;
import centmp.depotmebel.adminpartner.bean.form.ProductForm;

public interface ProductService {
	
	ProductListBean list(HashMap<String, Object> hm);
	ProductBean detail(String id);
	
	CommonBean createFromTemp(HashMap<String, Object> hm);
	CommonBean create(ProductForm form);
	
	ProductBean skuGeneate(List<String[]> attrList);
	
}
