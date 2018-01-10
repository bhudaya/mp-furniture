package centmp.depotmebel.adminpartner.bean;

import java.util.List;

public class VendorBean extends CommonBean {
	
	private String picName;
	private String picPhone;
	private String address;
	private String province;
	private String city;
	private String postalCode;
	
	private Integer creditLimit;
	private String creditLimitIdr;
	private Integer creditLimitRemain;
	private String creditLimitRemainIdr;
	private String deliveryCapacity;
	private String deliveryCapacityRemain;
	
	private List<String[]> coverages;
	private List<VendorUserBean> users;

	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	public String getPicPhone() {
		return picPhone;
	}
	public void setPicPhone(String picPhone) {
		this.picPhone = picPhone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public Integer getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(Integer creditLimit) {
		this.creditLimit = creditLimit;
	}
	public String getCreditLimitIdr() {
		return creditLimitIdr;
	}
	public void setCreditLimitIdr(String creditLimitIdr) {
		this.creditLimitIdr = creditLimitIdr;
	}
	public Integer getCreditLimitRemain() {
		return creditLimitRemain;
	}
	public void setCreditLimitRemain(Integer creditLimitRemain) {
		this.creditLimitRemain = creditLimitRemain;
	}
	public String getCreditLimitRemainIdr() {
		return creditLimitRemainIdr;
	}
	public void setCreditLimitRemainIdr(String creditLimitRemainIdr) {
		this.creditLimitRemainIdr = creditLimitRemainIdr;
	}
	public String getDeliveryCapacity() {
		return deliveryCapacity;
	}
	public void setDeliveryCapacity(String deliveryCapacity) {
		this.deliveryCapacity = deliveryCapacity;
	}
	public String getDeliveryCapacityRemain() {
		return deliveryCapacityRemain;
	}
	public void setDeliveryCapacityRemain(String deliveryCapacityRemain) {
		this.deliveryCapacityRemain = deliveryCapacityRemain;
	}
	public List<String[]> getCoverages() {
		return coverages;
	}
	public void setCoverages(List<String[]> coverages) {
		this.coverages = coverages;
	}
	public List<VendorUserBean> getUsers() {
		return users;
	}
	public void setUsers(List<VendorUserBean> users) {
		this.users = users;
	}
	
}
