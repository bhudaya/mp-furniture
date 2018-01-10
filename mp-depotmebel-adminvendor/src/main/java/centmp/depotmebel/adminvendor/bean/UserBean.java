package centmp.depotmebel.adminvendor.bean;


public class UserBean extends CommonBean {
	
	private String username;
	private String pic;
	private String password;
	private String bgId;
	
	private String roleId;
	private String roleName;
	private String vendors[];

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBgId() {
		return bgId;
	}
	public void setBgId(String bgId) {
		this.bgId = bgId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String[] getVendors() {
		return vendors;
	}
	public void setVendors(String[] vendors) {
		this.vendors = vendors;
	}
	
}
