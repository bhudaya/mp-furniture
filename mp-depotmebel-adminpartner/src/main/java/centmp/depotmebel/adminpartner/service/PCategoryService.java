package centmp.depotmebel.adminpartner.service;

import java.util.List;

import centmp.depotmebel.adminpartner.bean.PCategoryBean;
import centmp.depotmebel.adminpartner.bean.form.PCategoryForm;

public interface PCategoryService {
	
	List<PCategoryBean> listAll();
	List<PCategoryBean> listByActive();
	
	PCategoryForm detail(String id);
	PCategoryBean create(PCategoryForm form);
	
	List<String[]> subCategoryList(String pcategoryId);
	List<String[]> specList(String pcategoryId);
	List<String[]> attrList(String pcategoryId);
	
}
