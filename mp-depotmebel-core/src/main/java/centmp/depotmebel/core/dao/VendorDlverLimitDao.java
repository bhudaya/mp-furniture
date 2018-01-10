package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.VendorDlverLimit;

public class VendorDlverLimitDao extends CommonDao<VendorDlverLimit>{
	
	public VendorDlverLimitDao(SessionFactory sessionFactory) {
		super(VendorDlverLimit.class);
		setSessionFactory(sessionFactory);
	}
	
}
