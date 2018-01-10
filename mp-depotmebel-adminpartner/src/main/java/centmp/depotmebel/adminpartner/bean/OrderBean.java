package centmp.depotmebel.adminpartner.bean;

import java.util.List;

public class OrderBean {

	private String id;
	private String no;
	private String created;
	private String address;
	private String notes;
	private String sla;
	
	private String[] customer;
	private String vendor;
	
	private String[] status;
	private List<String[]> statusList;
	
	private List<String[]> items;
	private String[] itemArray;
	private List<String[]> itemsUnalloc;
	private List<OrderItemBean> itemBeanList;
	
	private String[] amounts;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String[] getCustomer() {
		return customer;
	}
	public void setCustomer(String[] customer) {
		this.customer = customer;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String[] getStatus() {
		return status;
	}
	public void setStatus(String[] status) {
		this.status = status;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public List<String[]> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<String[]> statusList) {
		this.statusList = statusList;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public List<String[]> getItems() {
		return items;
	}
	public void setItems(List<String[]> items) {
		this.items = items;
	}
	public List<String[]> getItemsUnalloc() {
		return itemsUnalloc;
	}
	public void setItemsUnalloc(List<String[]> itemsUnalloc) {
		this.itemsUnalloc = itemsUnalloc;
	}
	public String[] getItemArray() {
		return itemArray;
	}
	public void setItemArray(String[] itemArray) {
		this.itemArray = itemArray;
	}
	public List<OrderItemBean> getItemBeanList() {
		return itemBeanList;
	}
	public void setItemBeanList(List<OrderItemBean> itemBeanList) {
		this.itemBeanList = itemBeanList;
	}
	public String getSla() {
		return sla;
	}
	public void setSla(String sla) {
		this.sla = sla;
	}
	public String[] getAmounts() {
		return amounts;
	}
	public void setAmounts(String[] amounts) {
		this.amounts = amounts;
	}
}
