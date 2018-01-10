package centmp.depotmebel.core.json.response;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.bilmajdi.json.response.BaseJsonResponse;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class TrxItemListResponse extends BaseJsonResponse {
	private static final long serialVersionUID = 1L;
	
	
	private List<TrxItemResponse> cartList;
	
	public List<TrxItemResponse> getCartList() {
		return cartList;
	}
	public void setCartList(List<TrxItemResponse> cartList) {
		this.cartList = cartList;
	}
	
}
