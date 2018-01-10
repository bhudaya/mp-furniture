package centmp.depotmebel.core.enums;


public enum VENDOR_USER_TYPE {

	ADMIN("Admin"),
	OPERATOR("Operator"),
	COURIER("Kurir");
	
	private String value;
	
	VENDOR_USER_TYPE(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
	
	public static VENDOR_USER_TYPE setByOrdinal(Integer index){
		if(index==0){
			return VENDOR_USER_TYPE.ADMIN;
		}else if(index==1){
			return VENDOR_USER_TYPE.OPERATOR;
		}else if(index==2){
			return VENDOR_USER_TYPE.COURIER;
		}else{
			return VENDOR_USER_TYPE.ADMIN;
		}
	}
	
	public static String[][] toStringArray2d(){
		String[][] arr = new String[VENDOR_USER_TYPE.values().length][2];
		
		int i=0;
		for(VENDOR_USER_TYPE vu: VENDOR_USER_TYPE.values()){
			String s = vu.name();
			
			arr[i][0] = ""+i;
			arr[i][1] = s;
			
			i++;
		}
		
		return arr;
	}
	
	public static String toStringForJs(){
		StringBuffer buffer = new StringBuffer();
		//buffer.append("[");
		
		int i=0;
		int size = VENDOR_USER_TYPE.values().length;
		for(VENDOR_USER_TYPE vu: VENDOR_USER_TYPE.values()){
			String s = vu.name();
			//buffer.append("[\""+i+"\",\""+s+"\"]");
			buffer.append(""+i+"-"+s+"");
			if(i!=(size-1)) buffer.append(";");
			i++;
		}
		
		//buffer.append("]");
		return buffer.toString();
	}
	
}
