package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.ProductAttr;


public class ProductAttrDao extends CommonDao<ProductAttr>{
	
	public ProductAttrDao(SessionFactory sessionFactory) {
		super(ProductAttr.class);
		setSessionFactory(sessionFactory);
	}
	
}
