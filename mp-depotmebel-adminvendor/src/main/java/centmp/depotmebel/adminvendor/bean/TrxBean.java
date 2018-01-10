package centmp.depotmebel.adminvendor.bean;

import java.util.List;

public class TrxBean {

	private String trxId;
	private String orderNo;
	private String itemId;
	private String price;
	private String[] itemImg;
	private String[] status;
	private String sla;
	
	private String[] customer;
	private String address;
	
	private String[] product;
	private String[] productImg;
	private List<String[]> prodSpecs;
	private String[] customMsg;
	
	private String[] courier;
	private List<String[]> couriers;
	
	private String urlParam;
	
	
	public String getTrxId() {
		return trxId;
	}
	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String[] getCustomer() {
		return customer;
	}
	public void setCustomer(String[] customer) {
		this.customer = customer;
	}
	public String[] getProduct() {
		return product;
	}
	public void setProduct(String[] product) {
		this.product = product;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String[] getStatus() {
		return status;
	}
	public void setStatus(String[] status) {
		this.status = status;
	}
	public String getUrlParam() {
		return urlParam;
	}
	public void setUrlParam(String urlParam) {
		this.urlParam = urlParam;
	}
	public String[] getProductImg() {
		return productImg;
	}
	public void setProductImg(String[] productImg) {
		this.productImg = productImg;
	}
	public List<String[]> getCouriers() {
		return couriers;
	}
	public void setCouriers(List<String[]> couriers) {
		this.couriers = couriers;
	}
	public String[] getItemImg() {
		return itemImg;
	}
	public void setItemImg(String[] itemImg) {
		this.itemImg = itemImg;
	}
	public List<String[]> getProdSpecs() {
		return prodSpecs;
	}
	public void setProdSpecs(List<String[]> prodSpecs) {
		this.prodSpecs = prodSpecs;
	}
	public String[] getCustomMsg() {
		return customMsg;
	}
	public void setCustomMsg(String[] customMsg) {
		this.customMsg = customMsg;
	}
	public String[] getCourier() {
		return courier;
	}
	public void setCourier(String[] courier) {
		this.courier = courier;
	}
	public String getSla() {
		return sla;
	}
	public void setSla(String sla) {
		this.sla = sla;
	}
}
