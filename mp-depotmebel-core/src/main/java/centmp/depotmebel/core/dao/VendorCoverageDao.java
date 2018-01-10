package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.VendorCoverage;

public class VendorCoverageDao extends CommonDao<VendorCoverage>{
	
	public VendorCoverageDao(SessionFactory sessionFactory) {
		super(VendorCoverage.class);
		setSessionFactory(sessionFactory);
	}
	
}
