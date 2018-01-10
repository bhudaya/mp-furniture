package centmp.depotmebel.adminpartner.bean;

import java.util.List;

import centmp.depotmebel.core.json.request.CorporateContactRequest;
import centmp.depotmebel.core.json.request.CorporateFileRequest;
import centmp.depotmebel.core.json.request.CorporateUserRequest;

public class CustomerBean extends CommonBean {
	
	private String corptName;
	private String corptLegalName;
	private String corptCategory;
	private String corptNpwp;
	
	private List<CorporateContactRequest> corptContacts;
	private List<CorporateFileRequest> corptAttachments;
	private List<CorporateUserRequest> corptUsers;
	private String store;

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
