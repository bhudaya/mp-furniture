package centmp.depotmebel.core.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="trx_deduction")
public class TrxDeduction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private Long ID;
	
	@ManyToOne
	@JoinColumn(name="trx")
	private Trx trx;
	
	@Column(name="voucher_code", length=20)
	private String voucherCode;
	
	@Column(name="voucher_amount", length=20)
	private BigDecimal voucherAmount;
	
	
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
	public String getVoucherCode() {
		return voucherCode;
	}
	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}
	public BigDecimal getVoucherAmount() {
		return voucherAmount;
	}
	public void setVoucherAmount(BigDecimal voucherAmount) {
		this.voucherAmount = voucherAmount;
	}
	
}
