package centmp.depotmebel.core.json.response;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.bilmajdi.json.response.BaseJsonResponse;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PCategoryListResponse extends BaseJsonResponse {
	private static final long serialVersionUID = 1L;
	
	private List<PCategoryResponse> pcategoryList;
	
	public List<PCategoryResponse> getPcategoryList() {
		return pcategoryList;
	}
	public void setPcategoryList(List<PCategoryResponse> pcategoryList) {
		this.pcategoryList = pcategoryList;
	}
	
}
