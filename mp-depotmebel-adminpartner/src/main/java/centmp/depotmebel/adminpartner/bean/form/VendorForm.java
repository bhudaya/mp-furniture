package centmp.depotmebel.adminpartner.bean.form;

import java.util.List;

public class VendorForm extends CommonForm {

	private String picName;
	private String picPhone;
	private String address;
	private String province;
	private String city;
	private String postalCode;
	private String creditLimit;
	private String deliveryCapacity;
	private List<String[]> coverageList;
	private List<String[]> userList;
	
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
	public String getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}
	public String getDeliveryCapacity() {
		return deliveryCapacity;
	}
	public void setDeliveryCapacity(String deliveryCapacity) {
		this.deliveryCapacity = deliveryCapacity;
	}
	public List<String[]> getCoverageList() {
		return coverageList;
	}
	public void setCoverageList(List<String[]> coverageList) {
		this.coverageList = coverageList;
	}
	public List<String[]> getUserList() {
		return userList;
	}
	public void setUserList(List<String[]> userList) {
		this.userList = userList;
	}
	
}
