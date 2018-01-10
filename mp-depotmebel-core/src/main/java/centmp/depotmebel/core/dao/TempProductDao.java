package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.TempProduct;


public class TempProductDao extends CommonDao<TempProduct>{
	
	public TempProductDao(SessionFactory sessionFactory) {
		super(TempProduct.class);
		setSessionFactory(sessionFactory);
	}
	
}
