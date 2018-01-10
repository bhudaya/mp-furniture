package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.VendorCrdLimit;

public class VendorCrdLimitDao extends CommonDao<VendorCrdLimit>{
	
	public VendorCrdLimitDao(SessionFactory sessionFactory) {
		super(VendorCrdLimit.class);
		setSessionFactory(sessionFactory);
	}
	
}
