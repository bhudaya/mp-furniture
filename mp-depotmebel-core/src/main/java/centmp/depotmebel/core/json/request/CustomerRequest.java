package centmp.depotmebel.core.json.request;

import java.util.List;

public class CustomerRequest {
	
	private String id;
	private String customerId;
	private String email;
	private String password;
	private String name;
	private String phone;
	
	private String corptName;
	private String corptLegalName;
	private String corptCategory;
	private String corptNpwp;
	private List<CorporateContactRequest> corptContacts;
	private List<CorporateFileRequest> corptAttachments;
	private List<CorporateUserRequest> corptUsers;
	private String store;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCorptName() {
		return corptName;
	}
	public void setCorptName(String corptName) {
		this.corptName = corptName;
	}
	public String getCorptLegalName() {
		return corptLegalName;
	}
	public void setCorptLegalName(String corptLegalName) {
		this.corptLegalName = corptLegalName;
	}
	public String getCorptCategory() {
		return corptCategory;
	}
	public void setCorptCategory(String corptCategory) {
		this.corptCategory = corptCategory;
	}
	public String getCorptNpwp() {
		return corptNpwp;
	}
	public void setCorptNpwp(String corptNpwp) {
		this.corptNpwp = corptNpwp;
	}
	public List<CorporateContactRequest> getCorptContacts() {
		return corptContacts;
	}
	public void setCorptContacts(List<CorporateContactRequest> corptContacts) {
		this.corptContacts = corptContacts;
	}
	public List<CorporateFileRequest> getCorptAttachments() {
		return corptAttachments;
	}
	public void setCorptAttachments(List<CorporateFileRequest> corptAttachments) {
		this.corptAttachments = corptAttachments;
	}
	public List<CorporateUserRequest> getCorptUsers() {
		return corptUsers;
	}
	public void setCorptUsers(List<CorporateUserRequest> corptUsers) {
		this.corptUsers = corptUsers;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	
	
}
