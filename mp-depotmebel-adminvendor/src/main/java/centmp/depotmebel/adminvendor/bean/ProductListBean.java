package centmp.depotmebel.adminvendor.bean;

import java.util.List;

public class ProductListBean extends CommonBean {
	
	private Integer sumApproved;
	private Integer sumRejected;
	private Integer sumApproval;
	
	private Integer start;
	private Integer max;
	private List<ProductBean> products;
	
	
	public Integer getSumApproved() {
		return sumApproved;
	}
	public void setSumApproved(Integer sumApproved) {
		this.sumApproved = sumApproved;
	}
	public Integer getSumApproval() {
		return sumApproval;
	}
	public void setSumApproval(Integer sumApproval) {
		this.sumApproval = sumApproval;
	}
	public Integer getSumRejected() {
		return sumRejected;
	}
	public void setSumRejected(Integer sumRejected) {
		this.sumRejected = sumRejected;
	}
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
	public List<ProductBean> getProducts() {
		return products;
	}
	public void setProducts(List<ProductBean> products) {
		this.products = products;
	}

}
