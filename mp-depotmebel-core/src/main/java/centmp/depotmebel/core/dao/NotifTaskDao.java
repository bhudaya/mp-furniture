package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.NotifTask;


public class NotifTaskDao extends CommonDao<NotifTask>{
	
	public NotifTaskDao(SessionFactory sessionFactory) {
		super(NotifTask.class);
		setSessionFactory(sessionFactory);
	}
	
}
