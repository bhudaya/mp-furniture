package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.VendorUser;

public class VendorUserDao extends CommonDao<VendorUser>{
	
	public VendorUserDao(SessionFactory sessionFactory) {
		super(VendorUser.class);
		setSessionFactory(sessionFactory);
	}
	
}
