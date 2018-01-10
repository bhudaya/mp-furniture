package centmp.depotmebel.core.enums;

public enum TRX_STATUS {
	
	//No Ordinal
	CREATED,
	PAID,
	EXPIRE,
	DONE;
	
	private TRX_STATUS() {}
	
	
	public static String getValue(int index){
		String r = "created";
		if(index==1){r = "paid";}
		if(index==2){r = "expire";}
		if(index==3){r = "done";}
		return r;
	}
}
