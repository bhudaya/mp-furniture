package centmp.depotmebel.adminpartner.bean;

import java.util.List;

public class ProductListBean extends CommonBean {
	
	private Integer sumProduct;
	private Integer sumCategory;
	
	private Integer start;
	private Integer max;
	private List<ProductBean> products;
	
	public Integer getSumProduct() {
		return sumProduct;
	}
	public void setSumProduct(Integer sumProduct) {
		this.sumProduct = sumProduct;
	}
	public Integer getSumCategory() {
		return sumCategory;
	}
	public void setSumCategory(Integer sumCategory) {
		this.sumCategory = sumCategory;
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
