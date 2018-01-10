package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.Vendor;

public class VendorDao extends CommonDao<Vendor>{
	
	public VendorDao(SessionFactory sessionFactory) {
		super(Vendor.class);
		setSessionFactory(sessionFactory);
	}
	
}
