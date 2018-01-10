package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.TempProductSku;


public class TempProductSkuDao extends CommonDao<TempProductSku>{
	
	public TempProductSkuDao(SessionFactory sessionFactory) {
		super(TempProductSku.class);
		setSessionFactory(sessionFactory);
	}
	
}
