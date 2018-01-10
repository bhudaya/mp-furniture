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

import org.hibernate.annotations.Type;

@Entity
@Table(name="product_sku")
public class ProductSku implements Serializable {
	private static final long serialVersionUID = 4587537784637331808L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private Long ID;
	
	@ManyToOne
	@JoinColumn(name="product", nullable=false)
	private Product product;
	
	@Column(name="created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Column(name="label", nullable=false, length=100)
	private String label;
	
	@Column(name="sku", length=100)
	private String sku;
	
	@Column(name="remarks")
	@Type(type="text")
	private String remarks;
	
	@Column(name="stock")
	private Integer stock;
	
	@Column(name="additional_price")
	private BigDecimal addtPrice;
	
	
	
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public BigDecimal getAddtPrice() {
		return addtPrice;
	}
	public void setAddtPrice(BigDecimal addtPrice) {
		this.addtPrice = addtPrice;
	}
	
	
}
