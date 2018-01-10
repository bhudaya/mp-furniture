package centmp.depotmebel.core.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="trxitem_")
public class TrxItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private Long ID;
	
	@ManyToOne
	@JoinColumn(name="trx")
	private Trx trx;
	
	@Column(name="customer_id", length=15)
	private String customerId;
	
	@Column(name="created_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;
	
	@ManyToOne
	@JoinColumn(name="vendor")
	private Vendor vendor;
	
	@ManyToOne
	@JoinColumn(name="product")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name="product_sku")
	private ProductSku productSku;
	
	@Column(name="sku", length=70)
	private String skuId;
	
	@Column(name="quantity", nullable=false)
	private Integer quantity;
	
	@Column(name="price")
	private BigDecimal price;
	
	@Column(name="img_folder_qc", length=100)
	private String imgFolderQc;
	
	@Column(name="img_name_qc", length=100)
	private String imgNameQc;
	
	@Column(name="img_folder_pod", length=100)
	private String imgFolderPod;
	
	@Column(name="img_name_pod", length=100)
	private String imgNamePod;
	
	@Column(name="courier_id")
	private Long courierId;
	
	@Column(name="city_id", length=5)
	private String cityId;
	
	@Column(name="recv_name", length=50)
	private String receiverName;
	
	@Column(name="recv_phone", length=15)
	private String receiverPhone;
	
	@Column(name="is_use_qc", length=2)
	private String isUseQc;

	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public Trx getTrx() {
		return trx;
	}
	public void setTrx(Trx trx) {
		this.trx = trx;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public ProductSku getProductSku() {
		return productSku;
	}
	public void setProductSku(ProductSku productSku) {
		this.productSku = productSku;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Long getCourierId() {
		return courierId;
	}
	public void setCourierId(Long courierId) {
		this.courierId = courierId;
	}
	public String getImgFolderQc() {
		return imgFolderQc;
	}
	public void setImgFolderQc(String imgFolderQc) {
		this.imgFolderQc = imgFolderQc;
	}
	public String getImgNameQc() {
		return imgNameQc;
	}
	public void setImgNameQc(String imgNameQc) {
		this.imgNameQc = imgNameQc;
	}
	public String getImgFolderPod() {
		return imgFolderPod;
	}
	public void setImgFolderPod(String imgFolderPod) {
		this.imgFolderPod = imgFolderPod;
	}
	public String getImgNamePod() {
		return imgNamePod;
	}
	public void setImgNamePod(String imgNamePod) {
		this.imgNamePod = imgNamePod;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getIsUseQc() {
		return isUseQc;
	}
	public void setIsUseQc(String isUseQc) {
		this.isUseQc = isUseQc;
	}
}
