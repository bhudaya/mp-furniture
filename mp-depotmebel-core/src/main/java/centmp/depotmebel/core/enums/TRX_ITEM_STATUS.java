package centmp.depotmebel.core.enums;

public enum TRX_ITEM_STATUS {
	
	//No Ordinal
	WAITING_FOR_ASSIGNMENT,
	ON_VENDOR,
	READY_QC,
	PASS_QC,
	ON_COURIER,
	DELIVERED,
	ACKNOWLEDGED;
	
	private TRX_ITEM_STATUS() {}
	
	
	public static String getValue(int index){
		String r = "waiting for assignment";
		if(index==1){r = "on vendor";}
		else if(index==2){r = "ready qc";}
		else if(index==3){r = "pass qc";}
		else if(index==4){r = "on courier";}
		else if(index==5){r = "delivered";}
		else if(index==6){r = "acknowledged";}
		return r;
	}
}
