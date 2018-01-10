package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.TempProductSpec;


public class TempProductSpecDao extends CommonDao<TempProductSpec>{
	
	public TempProductSpecDao(SessionFactory sessionFactory) {
		super(TempProductSpec.class);
		setSessionFactory(sessionFactory);
	}
	
}
