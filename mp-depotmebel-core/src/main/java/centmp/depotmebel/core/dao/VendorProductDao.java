package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.VendorProduct;

public class VendorProductDao extends CommonDao<VendorProduct>{
	
	public VendorProductDao(SessionFactory sessionFactory) {
		super(VendorProduct.class);
		setSessionFactory(sessionFactory);
	}
	
}
