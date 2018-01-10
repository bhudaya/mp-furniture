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
@Table(name="temp_product_spec")
public class TempProductSpec implements Serializable {
	private static final long serialVersionUID = 4587537784637331808L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private Long ID;
	
	@ManyToOne
	@JoinColumn(name="temp_product", nullable=false)
	private TempProduct tempProduct;
	
	@ManyToOne
	@JoinColumn(name="pcategory_spec", nullable=false)
	private PCategorySpec pcategorySpec;
	
	@Column(name="value", length=100)
	private String value;
	
	@Column(name="created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public TempProduct getTempProduct() {
		return tempProduct;
	}
	public void setTempProduct(TempProduct tempProduct) {
		this.tempProduct = tempProduct;
	}
	public PCategorySpec getPcategorySpec() {
		return pcategorySpec;
	}
	public void setPcategorySpec(PCategorySpec pcategorySpec) {
		this.pcategorySpec = pcategorySpec;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}
