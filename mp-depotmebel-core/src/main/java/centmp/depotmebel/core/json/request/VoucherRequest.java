package centmp.depotmebel.core.json.request;

public class VoucherRequest {
	
	private String voucherCode;
	private String voucherCodeOld;
	private String customerId;
	private String trxAmount;
	
	public String getVoucherCode() {
		return voucherCode;
	}
	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getTrxAmount() {
		return trxAmount;
	}
	public void setTrxAmount(String trxAmount) {
		this.trxAmount = trxAmount;
	}
	public String getVoucherCodeOld() {
		return voucherCodeOld;
	}
	public void setVoucherCodeOld(String voucherCodeOld) {
		this.voucherCodeOld = voucherCodeOld;
	}

}
