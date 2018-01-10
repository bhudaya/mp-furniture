package centmp.depotmebel.core.json.response;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.bilmajdi.json.response.BaseJsonResponse;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class TrxItemResponse extends BaseJsonResponse {
	private static final long serialVersionUID = 1L;
	
	private String trxItemId;
	private String productName;
	private String categoryName;
	private String productImg;
	private String skuLabel;
	private String price;
	private String priceIdr;
	private String cityId;
	private String quantity;
	
	private ProductResponse productDetail;
	
	public String getTrxItemId() {
		return trxItemId;
	}
	public void setTrxItemId(String trxItemId) {
		this.trxItemId = trxItemId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getProductImg() {
		return productImg;
	}
	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}
	public String getSkuLabel() {
		return skuLabel;
	}
	public void setSkuLabel(String skuLabel) {
		this.skuLabel = skuLabel;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPriceIdr() {
		return priceIdr;
	}
	public void setPriceIdr(String priceIdr) {
		this.priceIdr = priceIdr;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public ProductResponse getProductDetail() {
		return productDetail;
	}
	public void setProductDetail(ProductResponse productDetail) {
		this.productDetail = productDetail;
	}
}
