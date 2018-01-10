package centmp.depotmebel.core.json.request;

public class PointRequest {
	
	private String email;
	private String trxCode;
	private String amount;
	
	private String id;
	private String factor;
	private String minTrx;
	private String maxTrx;
	private String pointAdded;
	private String startDate;
	private String endDate;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTrxCode() {
		return trxCode;
	}
	public void setTrxCode(String trxCode) {
		this.trxCode = trxCode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFactor() {
		return factor;
	}
	public void setFactor(String factor) {
		this.factor = factor;
	}
	public String getMinTrx() {
		return minTrx;
	}
	public void setMinTrx(String minTrx) {
		this.minTrx = minTrx;
	}
	public String getMaxTrx() {
		return maxTrx;
	}
	public void setMaxTrx(String maxTrx) {
		this.maxTrx = maxTrx;
	}
	public String getPointAdded() {
		return pointAdded;
	}
	public void setPointAdded(String pointAdded) {
		this.pointAdded = pointAdded;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
