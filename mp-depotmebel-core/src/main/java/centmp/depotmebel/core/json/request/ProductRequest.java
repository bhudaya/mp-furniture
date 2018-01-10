package centmp.depotmebel.core.json.request;

public class ProductRequest {
	
	private String source;
	private String sourceDesc;
	private String[] categoryIds;
	private String searchVal;
	private String maxResult;
	
	private String productId;
	private String[] attrValues;
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSourceDesc() {
		return sourceDesc;
	}
	public void setSourceDesc(String sourceDesc) {
		this.sourceDesc = sourceDesc;
	}
	public String[] getCategoryIds() {
		return categoryIds;
	}
	public void setCategoryIds(String[] categoryIds) {
		this.categoryIds = categoryIds;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String[] getAttrValues() {
		return attrValues;
	}
	public void setAttrValues(String[] attrValues) {
		this.attrValues = attrValues;
	}
	public String getMaxResult() {
		return maxResult;
	}
	public void setMaxResult(String maxResult) {
		this.maxResult = maxResult;
	}

}
