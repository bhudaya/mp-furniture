package centmp.depotmebel.wstrx.thread;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import centmp.depotmebel.core.dao.TrxItemDao;
import centmp.depotmebel.core.dao.VendorCrdLimitDao;
import centmp.depotmebel.core.dao.VendorDlverLimitDao;
import centmp.depotmebel.core.model.Trx;
import centmp.depotmebel.core.model.TrxItem;
import centmp.depotmebel.core.model.Vendor;
import centmp.depotmebel.core.model.VendorCrdLimit;
import centmp.depotmebel.core.model.VendorDlverLimit;

public class VendorBalanceThread implements Runnable {

	private SessionFactory sessionFactory;
	private Trx trx;
	private Date date;
	
	public VendorBalanceThread(SessionFactory sessionFactory, Trx trx, Date date) {
		this.sessionFactory = sessionFactory;
		this.trx = trx;
		this.date = date;
	}

	@Override
	public void run() {
		try {
			TrxItemDao itemDao = new TrxItemDao(sessionFactory);
			List<TrxItem> itemList = itemDao.loadBy(Order.asc("ID"), Restrictions.eq("trx", trx));
			for(TrxItem trxItem: itemList) {
				Vendor vendor = trxItem.getVendor();
				if(vendor!=null){
					Criterion cr1 = Restrictions.eq("vendor", vendor);
					
					VendorCrdLimitDao crdLimitDao = new VendorCrdLimitDao(sessionFactory);
					List<VendorCrdLimit> crdLimitList = crdLimitDao.loadBy(Order.asc("ID"), 1, cr1);
					if(crdLimitList.size()>0){
						VendorCrdLimit crdLimit = crdLimitList.get(0);
						
						BigDecimal before = crdLimit.getBalance();
						BigDecimal update = before.subtract(trxItem.getProduct().getBasePrice());
						
						crdLimit.setBalance(update);
						crdLimit.setUpdatedDate(date);
						crdLimitDao = new VendorCrdLimitDao(sessionFactory);
						crdLimitDao.saveOrUpdate(crdLimit);
					}
					
					VendorDlverLimitDao dlvrLimitDao = new VendorDlverLimitDao(sessionFactory);
					List<VendorDlverLimit> dlvrLimitList = dlvrLimitDao.loadBy(Order.asc("ID"), 1, cr1);
					if(dlvrLimitList.size()>0){
						VendorDlverLimit dlvrLimit = dlvrLimitList.get(0);
						
						Integer before = dlvrLimit.getBalance();
						Integer update = before-trxItem.getQuantity();
						
						dlvrLimit.setBalance(update);
						dlvrLimit.setUpdatedDate(date);
						dlvrLimitDao = new VendorDlverLimitDao(sessionFactory);
						dlvrLimitDao.saveOrUpdate(dlvrLimit);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
