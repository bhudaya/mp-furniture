package centmp.depotmebel.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import centmp.depotmebel.core.dao.PCategorySpecDao;
import centmp.depotmebel.core.dao.PCategorySubDao;
import centmp.depotmebel.core.enums.STATUS;
import centmp.depotmebel.core.util.DaoException;


@Entity
@Table(name="pcategory_")
public class PCategory implements Serializable {
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
	@Column(name="status_", length=5, nullable=false)
	private STATUS status;
	
	
	@OneToMany(mappedBy="pcategory", cascade=CascadeType.ALL)
	private List<PCategorySub> subCategories = new ArrayList<>();
	
	@OneToMany(mappedBy="pcategory", cascade=CascadeType.ALL)
	private List<PCategorySpec> specs = new ArrayList<>();
	
	
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
	public List<PCategorySub> getSubCategories() {
		return subCategories;
	}
	public void setSubCategories(List<PCategorySub> subCategories) {
		this.subCategories = subCategories;
	}
	public List<PCategorySpec> getSpecs() {
		return specs;
	}
	public void setSpecs(List<PCategorySpec> specs) {
		this.specs = specs;
	}
	
	public List<PCategorySub> getSubCategories(SessionFactory sf, Order order, Criterion ... cr) {
		PCategorySubDao dao = new PCategorySubDao(sf);
		List<PCategorySub> list = new ArrayList<>();
		try {
			list = dao.loadBy(order, cr);
		} catch (DaoException e) {}
		return list;
	}
	public List<PCategorySpec> getSpecs(SessionFactory sf, Order order, Criterion ... cr) {
		PCategorySpecDao dao = new PCategorySpecDao(sf);
		List<PCategorySpec> list = new ArrayList<>();
		try {
			list = dao.loadBy(order, cr);
		} catch (DaoException e) {}
		return list;
	}
}
