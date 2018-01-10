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

import centmp.depotmebel.core.enums.STATUS;


@Entity
@Table(name="vendor_")
public class Vendor implements Serializable {
	private static final long serialVersionUID = 4587537784637331808L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private Long ID;
	
	@Column(name="name", nullable=false, length=50)
	private String name;
	
	@Column(name="pic_name", length=40)
	private String picName;
	
	@Column(name="pic_phone", length=20)
	private String picPhone;
	
	@Column(name="address")
	@Type(type="text")
	private String address;
	
	@ManyToOne
	@JoinColumn(name="city", referencedColumnName="code")
	private City city;
	
	@Column(name="postal_code", length=8)
	private String postalCode;
	
	@Column(name="credit_limit")
	private BigDecimal creditLimit;
	
	@Column(name="delivery_capacity")
	private Integer deliveryCapacity;
	
	@Column(name="bg_id", length=10)
	private String bgId;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="status", length=2, nullable=false)
	private STATUS status;
	
	@Column(name="created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	
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
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	public String getPicPhone() {
		return picPhone;
	}
	public void setPicPhone(String picPhone) {
		this.picPhone = picPhone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public BigDecimal getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}
	public Integer getDeliveryCapacity() {
		return deliveryCapacity;
	}
	public void setDeliveryCapacity(Integer deliveryCapacity) {
		this.deliveryCapacity = deliveryCapacity;
	}
	public String getBgId() {
		return bgId;
	}
	public void setBgId(String bgId) {
		this.bgId = bgId;
	}
	public STATUS getStatus() {
		return status;
	}
	public void setStatus(STATUS status) {
		this.status = status;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}
