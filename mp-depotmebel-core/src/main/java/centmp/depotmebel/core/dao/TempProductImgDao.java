package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.TempProductImage;


public class TempProductImgDao extends CommonDao<TempProductImage>{
	
	public TempProductImgDao(SessionFactory sessionFactory) {
		super(TempProductImage.class);
		setSessionFactory(sessionFactory);
	}
	
}
