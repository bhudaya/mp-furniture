package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.TrxItemMsg;


public class TrxItemMsgDao extends CommonDao<TrxItemMsg>{
	
	public TrxItemMsgDao(SessionFactory sessionFactory) {
		super(TrxItemMsg.class);
		setSessionFactory(sessionFactory);
	}
	
}
