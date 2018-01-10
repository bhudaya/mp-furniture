package centmp.depotmebel.core.model;

import java.io.Serializable;
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
@Table(name="vendor_product_temp")
public class VendorProductTemp implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private Long ID;
	
	@ManyToOne
	@JoinColumn(name="vendor")
	private Vendor vendor;
	
	@ManyToOne
	@JoinColumn(name="temp_product_sku")
	private TempProductSku productSku;
	
	@Column(name="stock")
	private Integer stock;
	
	@Column(name="is_unlimited", length=2)
	private String isUnlimited;
	
	@Column(name="is_active", length=2)
	private String isActive;
	
	@Column(name="created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	public TempProductSku getProductSku() {
		return productSku;
	}
	public void setProductSku(TempProductSku productSku) {
		this.productSku = productSku;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public String getIsUnlimited() {
		return isUnlimited;
	}
	public void setIsUnlimited(String isUnlimited) {
		this.isUnlimited = isUnlimited;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}
