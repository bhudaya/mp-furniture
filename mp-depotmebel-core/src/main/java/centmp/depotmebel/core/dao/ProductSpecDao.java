package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.ProductSpec;


public class ProductSpecDao extends CommonDao<ProductSpec>{
	
	public ProductSpecDao(SessionFactory sessionFactory) {
		super(ProductSpec.class);
		setSessionFactory(sessionFactory);
	}
	
}
