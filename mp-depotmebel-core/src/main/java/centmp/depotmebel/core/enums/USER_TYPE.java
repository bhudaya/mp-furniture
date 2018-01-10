package centmp.depotmebel.core.enums;


public enum USER_TYPE {

	SUPER_ADMIN("Superadmin"),
	PARTNER_ADMIN("Partner Admin"),
	VENDOR_ADMIN("Vendor Admin"),
	VENDOR_OPERATOR("Vendor Operator"),
	VENDOR_COURIER("Vendor Courier");
	
	private String value;
	
	USER_TYPE(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
	public static USER_TYPE setByOrdinal(Integer index){
		if(index==0){
			return USER_TYPE.SUPER_ADMIN;
		}else if(index==1){
			return USER_TYPE.PARTNER_ADMIN;
		}else if(index==2){
			return USER_TYPE.VENDOR_ADMIN;
		}else if(index==3){
			return USER_TYPE.VENDOR_OPERATOR;
		}else if(index==4){
			return USER_TYPE.VENDOR_COURIER;
		}else{
			return null;
		}
	}
	
}
