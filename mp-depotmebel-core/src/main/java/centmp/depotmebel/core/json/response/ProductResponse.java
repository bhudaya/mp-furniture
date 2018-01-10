package centmp.depotmebel.core.json.response;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.bilmajdi.json.response.BaseJsonResponse;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ProductResponse extends BaseJsonResponse {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String description;
	private String basePrice;
	private String categoryName;
	private String subCategoryName;
	private String[] images;
	
	private List<ProductSpecResponse> specifications;
	private List<ProductAtrrResponse> attributes;
	private List<String[]> skuList;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(String basePrice) {
		this.basePrice = basePrice;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getSubCategoryName() {
		return subCategoryName;
	}
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	public String[] getImages() {
		return images;
	}
	public void setImages(String[] images) {
		this.images = images;
	}
	public List<ProductSpecResponse> getSpecifications() {
		return specifications;
	}
	public void setSpecifications(List<ProductSpecResponse> specifications) {
		this.specifications = specifications;
	}
	public List<ProductAtrrResponse> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<ProductAtrrResponse> attributes) {
		this.attributes = attributes;
	}
	public List<String[]> getSkuList() {
		return skuList;
	}
	public void setSkuList(List<String[]> skuList) {
		this.skuList = skuList;
	}
}
