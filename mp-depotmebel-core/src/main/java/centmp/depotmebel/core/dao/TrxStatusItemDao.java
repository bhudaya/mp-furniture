package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.TrxItemStatus;

public class TrxStatusItemDao extends CommonDao<TrxItemStatus>{
	
	public TrxStatusItemDao(SessionFactory sessionFactory) {
		super(TrxItemStatus.class);
		setSessionFactory(sessionFactory);
	}
	
}
