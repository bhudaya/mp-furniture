package centmp.depotmebel.core.json.response;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.bilmajdi.json.response.BaseJsonResponse;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class TrxResponse extends BaseJsonResponse {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String orderNo;
	private String trxTime;
	private String expireTime;
	private String paidTime;
	private String amountTrx;
	private String amountTotal;
	private String lastStatus;
	
	private String customerName;
	private String customerEmail;
	private String customerPhone;
	private String address;
	private String city;
	private String province;
	private String postalCode;
	
	/*private String padipayDomain;
	private String merchantCode;
	private String merchantPass;
	private String returnUrl;
	private String callBackUrl;*/
	
	private String trdDeductId;
	private String voucherCode;
	private String voucherAmont;
	private String voucherAmontIdr;
	
	private List<TrxItemResponse> itemRespList;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getTrxTime() {
		return trxTime;
	}
	public void setTrxTime(String trxTime) {
		this.trxTime = trxTime;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getAmountTrx() {
		return amountTrx;
	}
	public void setAmountTrx(String amountTrx) {
		this.amountTrx = amountTrx;
	}
	public String getAmountTotal() {
		return amountTotal;
	}
	public void setAmountTotal(String amountTotal) {
		this.amountTotal = amountTotal;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getLastStatus() {
		return lastStatus;
	}
	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}
	public List<TrxItemResponse> getItemRespList() {
		return itemRespList;
	}
	public void setItemRespList(List<TrxItemResponse> itemRespList) {
		this.itemRespList = itemRespList;
	}
	public String getPaidTime() {
		return paidTime;
	}
	public void setPaidTime(String paidTime) {
		this.paidTime = paidTime;
	}
	public String getTrdDeductId() {
		return trdDeductId;
	}
	public void setTrdDeductId(String trdDeductId) {
		this.trdDeductId = trdDeductId;
	}
	public String getVoucherCode() {
		return voucherCode;
	}
	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}
	public String getVoucherAmont() {
		return voucherAmont;
	}
	public void setVoucherAmont(String voucherAmont) {
		this.voucherAmont = voucherAmont;
	}
	public String getVoucherAmontIdr() {
		return voucherAmontIdr;
	}
	public void setVoucherAmontIdr(String voucherAmontIdr) {
		this.voucherAmontIdr = voucherAmontIdr;
	}
	
}
