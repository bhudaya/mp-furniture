package centmp.depotmebel.core.json.response;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.bilmajdi.json.response.BaseJsonResponse;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class TrxListResponse extends BaseJsonResponse {
	private static final long serialVersionUID = 1L;
	
	private List<TrxResponse> trxList;
	
	public List<TrxResponse> getTrxList() {
		return trxList;
	}
	public void setTrxList(List<TrxResponse> trxList) {
		this.trxList = trxList;
	}
}
