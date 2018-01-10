package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.ProductImage;


public class ProductImageDao extends CommonDao<ProductImage>{
	
	public ProductImageDao(SessionFactory sessionFactory) {
		super(ProductImage.class);
		setSessionFactory(sessionFactory);
	}
	
}
