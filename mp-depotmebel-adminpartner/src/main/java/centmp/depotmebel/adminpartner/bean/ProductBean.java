package centmp.depotmebel.adminpartner.bean;

import java.util.List;

public class ProductBean extends CommonBean {
	
	private String statusIndex;
	private String pcategoryId;
	private String pcategoryName;
	private String pcategorySubId;
	private String pcategorySubName;
	private String basePrice;
	private String basePriceIdr;
	private String desc;
	private String vendorName;
	
	
	private String image;
	private List<String[]> images;
	private List<String[]> specs;
	private List<String[]> attrList;
	private List<String[]> skuList;
	
	public String getStatusIndex() {
		return statusIndex;
	}
	public void setStatusIndex(String statusIndex) {
		this.statusIndex = statusIndex;
	}
	public String getPcategoryId() {
		return pcategoryId;
	}
	public void setPcategoryId(String pcategoryId) {
		this.pcategoryId = pcategoryId;
	}
	public String getPcategoryName() {
		return pcategoryName;
	}
	public void setPcategoryName(String pcategoryName) {
		this.pcategoryName = pcategoryName;
	}
	public String getPcategorySubId() {
		return pcategorySubId;
	}
	public void setPcategorySubId(String pcategorySubId) {
		this.pcategorySubId = pcategorySubId;
	}
	public String getPcategorySubName() {
		return pcategorySubName;
	}
	public void setPcategorySubName(String pcategorySubName) {
		this.pcategorySubName = pcategorySubName;
	}
	public String getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(String basePrice) {
		this.basePrice = basePrice;
	}
	public String getBasePriceIdr() {
		return basePriceIdr;
	}
	public void setBasePriceIdr(String basePriceIdr) {
		this.basePriceIdr = basePriceIdr;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public List<String[]> getImages() {
		return images;
	}
	public void setImages(List<String[]> images) {
		this.images = images;
	}
	public List<String[]> getSpecs() {
		return specs;
	}
	public void setSpecs(List<String[]> specs) {
		this.specs = specs;
	}
	public List<String[]> getAttrList() {
		return attrList;
	}
	public void setAttrList(List<String[]> attrList) {
		this.attrList = attrList;
	}
	public List<String[]> getSkuList() {
		return skuList;
	}
	public void setSkuList(List<String[]> skuList) {
		this.skuList = skuList;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	
}
