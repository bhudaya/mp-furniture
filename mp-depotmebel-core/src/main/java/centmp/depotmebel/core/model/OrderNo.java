package centmp.depotmebel.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="order_no")
public class OrderNo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private Long ID;
	
	@Column(name="date", length=10)
	private String date;
	
	@Column(name="date_seq", length=10)
	private Integer dateSeq;
	
	@Column(name="seq", length=10)
	private Integer seq;
	

	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Integer getDateSeq() {
		return dateSeq;
	}
	public void setDateSeq(Integer dateSeq) {
		this.dateSeq = dateSeq;
	}
	
}
