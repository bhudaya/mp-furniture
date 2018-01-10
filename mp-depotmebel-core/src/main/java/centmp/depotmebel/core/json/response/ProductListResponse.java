package centmp.depotmebel.core.json.response;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.bilmajdi.json.response.BaseJsonResponse;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ProductListResponse extends BaseJsonResponse {
	private static final long serialVersionUID = 1L;
	
	private String size;
	private List<ProductResponse> productList;
	
	
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public List<ProductResponse> getProductList() {
		return productList;
	}
	public void setProductList(List<ProductResponse> productList) {
		this.productList = productList;
	}
	
}
