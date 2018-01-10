package centmp.depotmebel.adminvendor.bean;

import java.util.List;

public class PCategoryBean extends CommonBean {
	
	private List<String[]> subCategoryList;
	
	public List<String[]> getSubCategoryList() {
		return subCategoryList;
	}
	public void setSubCategoryList(List<String[]> subCategoryList) {
		this.subCategoryList = subCategoryList;
	}
}
