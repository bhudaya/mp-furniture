package centmp.depotmebel.adminpartner.bean;

import java.util.List;

public class VendorListBean extends CommonBean {
	
	private Integer start;
	private Integer max;
	private Integer sumData;
	private List<VendorBean> vendorList;
	
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	public Integer getSumData() {
		return sumData;
	}
	public void setSumData(Integer sumData) {
		this.sumData = sumData;
	}
	public List<VendorBean> getVendorList() {
		return vendorList;
	}
	public void setVendorList(List<VendorBean> vendorList) {
		this.vendorList = vendorList;
	}

}
