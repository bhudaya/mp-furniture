package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.ProductSku;


public class ProductSkuDao extends CommonDao<ProductSku>{
	
	public ProductSkuDao(SessionFactory sessionFactory) {
		super(ProductSku.class);
		setSessionFactory(sessionFactory);
	}
	
}
