package centmp.depotmebel.core.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import centmp.depotmebel.core.enums.APPROVAL;
import centmp.depotmebel.core.enums.STATUS;


@Entity
@Table(name="product_")
public class Product implements Serializable {
	private static final long serialVersionUID = 4587537784637331808L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private Long ID;
	
	@Column(name="name", nullable=false, length=50)
	private String name;
	
	@Column(name="created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="status_", length=10, nullable=false)
	private STATUS status;
	
	@ManyToOne
	@JoinColumn(name="pcategory")
	private PCategory pcategory;
	
	@ManyToOne
	@JoinColumn(name="pcategory_sub")
	private PCategorySub pcategorySub;
	
	@Column(name="base_price")
	private BigDecimal basePrice;
	
	@Column(name="desc_")
	@Type(type="text")
	private String description;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="approval", length=10)
	private APPROVAL approval;
	
	/*@ManyToOne
	@JoinColumn(name="vendor", referencedColumnName="ID")
	private Vendor vendor;
	
	@ManyToOne
	@JoinColumn(name="createdby", referencedColumnName="ID")
	private VendorUser createdby;*/
	
	@Column(name="tag_")
	@Type(type="text")
	private String tag;
	
	@Column(name="weight")
	private BigDecimal weight;
	
	
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public STATUS getStatus() {
		return status;
	}
	public void setStatus(STATUS status) {
		this.status = status;
	}
	public PCategory getPcategory() {
		return pcategory;
	}
	public void setPcategory(PCategory pcategory) {
		this.pcategory = pcategory;
	}
	public PCategorySub getPcategorySub() {
		return pcategorySub;
	}
	public void setPcategorySub(PCategorySub pcategorySub) {
		this.pcategorySub = pcategorySub;
	}
	public BigDecimal getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(BigDecimal basePrice) {
		this.basePrice = basePrice;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public APPROVAL getApproval() {
		return approval;
	}
	public void setApproval(APPROVAL approval) {
		this.approval = approval;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	
}
