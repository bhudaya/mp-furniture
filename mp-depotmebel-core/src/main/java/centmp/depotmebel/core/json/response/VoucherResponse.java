package centmp.depotmebel.core.json.response;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.bilmajdi.json.response.BaseJsonResponse;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class VoucherResponse extends BaseJsonResponse {
	
	private static final long serialVersionUID = -997059104146025374L;
	
	private String customerEmail;
	private String customerName;
	private String deductionValue;
	
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getDeductionValue() {
		return deductionValue;
	}
	public void setDeductionValue(String deductionValue) {
		this.deductionValue = deductionValue;
	}
}
