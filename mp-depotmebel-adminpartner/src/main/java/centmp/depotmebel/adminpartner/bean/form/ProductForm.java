package centmp.depotmebel.adminpartner.bean.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ProductForm extends CommonForm {
	
	private String pcategoryId;
	private String pcategorySubId;
	private String basePrice;
	private String desc;
	
	private List<MultipartFile> images;
	private List<String[]> specList;
	private List<String[]> attrList;
	private List<String[]> skuList;
	public String getPcategoryId() {
		return pcategoryId;
	}
	public void setPcategoryId(String pcategoryId) {
		this.pcategoryId = pcategoryId;
	}
	public String getPcategorySubId() {
		return pcategorySubId;
	}
	public void setPcategorySubId(String pcategorySubId) {
		this.pcategorySubId = pcategorySubId;
	}
	public String getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(String basePrice) {
		this.basePrice = basePrice;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<MultipartFile> getImages() {
		return images;
	}
	public void setImages(List<MultipartFile> images) {
		this.images = images;
	}
	public List<String[]> getSpecList() {
		return specList;
	}
	public void setSpecList(List<String[]> specList) {
		this.specList = specList;
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
}
