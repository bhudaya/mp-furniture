package centmp.depotmebel.core.json.request;

import java.util.List;

public class TrxRequest {

	private String productId;
	private String skuId;
	private String cityId;
	private String quantity;
	private String msgCustom;
	private String msgImgFolder;
	private String msgImgName;
	private String msgImgName2;
	
	private String orderNo;
	private String expireTime;
	private String address;
	private String postalCode;
	private String notes;
	
	private String trxItemId;
	private List<String> trxItemIdList;
	private List<TrxItemRequest> trxItemList;
	
	private String trxDeductId;
	private String voucherCode;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getMsgCustom() {
		return msgCustom;
	}
	public void setMsgCustom(String msgCustom) {
		this.msgCustom = msgCustom;
	}
	public String getMsgImgFolder() {
		return msgImgFolder;
	}
	public void setMsgImgFolder(String msgImgFolder) {
		this.msgImgFolder = msgImgFolder;
	}
	public String getMsgImgName() {
		return msgImgName;
	}
	public void setMsgImgName(String msgImgName) {
		this.msgImgName = msgImgName;
	}
	public String getMsgImgName2() {
		return msgImgName2;
	}
	public void setMsgImgName2(String msgImgName2) {
		this.msgImgName2 = msgImgName2;
	}
	public String getTrxItemId() {
		return trxItemId;
	}
	public void setTrxItemId(String trxItemId) {
		this.trxItemId = trxItemId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public List<String> getTrxItemIdList() {
		return trxItemIdList;
	}
	public void setTrxItemIdList(List<String> trxItemIdList) {
		this.trxItemIdList = trxItemIdList;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	public List<TrxItemRequest> getTrxItemList() {
		return trxItemList;
	}
	public void setTrxItemList(List<TrxItemRequest> trxItemList) {
		this.trxItemList = trxItemList;
	}
	public String getTrxDeductId() {
		return trxDeductId;
	}
	public void setTrxDeductId(String trxDeductId) {
		this.trxDeductId = trxDeductId;
	}
	public String getVoucherCode() {
		return voucherCode;
	}
	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}
	
}
