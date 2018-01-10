package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.TrxItem;

public class TrxItemDao extends CommonDao<TrxItem>{
	
	public TrxItemDao(SessionFactory sessionFactory) {
		super(TrxItem.class);
		setSessionFactory(sessionFactory);
	}
	
}
