package centmp.depotmebel.adminpartner.bean;

import java.util.List;

public class OrderItemBean {
	
	private String itemId;
	private String createdDate;
	private String[] customer;
	private String[] vendor;
	private String[] product;
	private String[] prodAttr;
	private Integer quantity;
	private String price;
	private String[] customMsg;
	private String imgQcUrl;
	private String imgPodUrl;
	private List<String[]> statusHistory;
	
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String[] getCustomer() {
		return customer;
	}
	public void setCustomer(String[] customer) {
		this.customer = customer;
	}
	public String[] getVendor() {
		return vendor;
	}
	public void setVendor(String[] vendor) {
		this.vendor = vendor;
	}
	public String[] getProduct() {
		return product;
	}
	public void setProduct(String[] product) {
		this.product = product;
	}
	public String[] getProdAttr() {
		return prodAttr;
	}
	public void setProdAttr(String[] prodAttr) {
		this.prodAttr = prodAttr;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String[] getCustomMsg() {
		return customMsg;
	}
	public void setCustomMsg(String[] customMsg) {
		this.customMsg = customMsg;
	}
	public String getImgQcUrl() {
		return imgQcUrl;
	}
	public void setImgQcUrl(String imgQcUrl) {
		this.imgQcUrl = imgQcUrl;
	}
	public String getImgPodUrl() {
		return imgPodUrl;
	}
	public void setImgPodUrl(String imgPodUrl) {
		this.imgPodUrl = imgPodUrl;
	}
	public List<String[]> getStatusHistory() {
		return statusHistory;
	}
	public void setStatusHistory(List<String[]> statusHistory) {
		this.statusHistory = statusHistory;
	}

}
