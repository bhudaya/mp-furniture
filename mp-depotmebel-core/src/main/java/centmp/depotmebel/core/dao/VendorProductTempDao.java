package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.VendorProductTemp;

public class VendorProductTempDao extends CommonDao<VendorProductTemp>{
	
	public VendorProductTempDao(SessionFactory sessionFactory) {
		super(VendorProductTemp.class);
		setSessionFactory(sessionFactory);
	}
	
}
