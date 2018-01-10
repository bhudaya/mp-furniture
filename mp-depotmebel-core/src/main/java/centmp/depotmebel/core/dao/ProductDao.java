package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.Product;


public class ProductDao extends CommonDao<Product>{
	
	public ProductDao(SessionFactory sessionFactory) {
		super(Product.class);
		setSessionFactory(sessionFactory);
	}
	
}
