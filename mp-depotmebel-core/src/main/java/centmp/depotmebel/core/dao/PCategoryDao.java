package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.PCategory;


public class PCategoryDao extends CommonDao<PCategory>{
	
	public PCategoryDao(SessionFactory sessionFactory) {
		super(PCategory.class);
		setSessionFactory(sessionFactory);
	}
	
}
