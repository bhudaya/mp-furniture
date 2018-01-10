package centmp.depotmebel.core.model;

import java.io.Serializable;
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

import centmp.depotmebel.core.enums.TRX_STATUS;


@Entity
@Table(name="trx_status")
public class TrxStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private Long ID;
	
	@ManyToOne
	@JoinColumn(name="trx")
	private Trx trx;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="status_", length=2, nullable=false)
	private TRX_STATUS status;
	
	@Column(name="date_")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_;
	
	@Column(name="notes")
	@Type(type="text")
	private String notes;
	
	@Column(name="padipay_code", length=100)
	private String padipayCode;

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
	public TRX_STATUS getStatus() {
		return status;
	}
	public void setStatus(TRX_STATUS status) {
		this.status = status;
	}
	public Date getDate_() {
		return date_;
	}
	public void setDate_(Date date_) {
		this.date_ = date_;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getPadipayCode() {
		return padipayCode;
	}
	public void setPadipayCode(String padipayCode) {
		this.padipayCode = padipayCode;
	}
}
