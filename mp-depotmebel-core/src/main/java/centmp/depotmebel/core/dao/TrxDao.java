package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.Trx;


public class TrxDao extends CommonDao<Trx>{
	
	public TrxDao(SessionFactory sessionFactory) {
		super(Trx.class);
		setSessionFactory(sessionFactory);
	}
	
}
