package centmp.depotmebel.adminpartner.bean.form;

import java.util.List;

public class PCategoryForm extends CommonForm {
	
	private String createdDateStr;
	
	private List<String[]> subList;
	private List<String[]> specList;
	private List<String[]> attrList;
	
	public String getCreatedDateStr() {
		return createdDateStr;
	}
	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
	}
	public List<String[]> getSubList() {
		return subList;
	}
	public void setSubList(List<String[]> subList) {
		this.subList = subList;
	}
	public List<String[]> getSpecList() {
		return specList;
	}
	public void setSpecList(List<String[]> specList) {
		this.specList = specList;
	}
	public List<String[]> getAttrList() {
		return attrList;
	}
	public void setAttrList(List<String[]> attrList) {
		this.attrList = attrList;
	}
	
}
