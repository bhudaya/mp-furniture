package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.TrxStatus;

public class TrxStatusDao extends CommonDao<TrxStatus>{
	
	public TrxStatusDao(SessionFactory sessionFactory) {
		super(TrxStatus.class);
		setSessionFactory(sessionFactory);
	}
	
}
