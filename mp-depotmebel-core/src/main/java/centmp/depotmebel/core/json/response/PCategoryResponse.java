package centmp.depotmebel.core.json.response;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.bilmajdi.json.response.BaseJsonResponse;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PCategoryResponse extends BaseJsonResponse {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String sumProduct;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSumProduct() {
		return sumProduct;
	}
	public void setSumProduct(String sumProduct) {
		this.sumProduct = sumProduct;
	}
}
