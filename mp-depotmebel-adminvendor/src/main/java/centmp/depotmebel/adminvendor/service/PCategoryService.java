package centmp.depotmebel.adminvendor.service;

import java.util.List;

import centmp.depotmebel.adminvendor.bean.PCategoryBean;

public interface PCategoryService {
	
	List<PCategoryBean> listByActive();
	List<String[]> subCategoryList(String pcategoryId);
	List<String[]> specList(String pcategoryId);
	List<String[]> attrList(String pcategoryId);
}
