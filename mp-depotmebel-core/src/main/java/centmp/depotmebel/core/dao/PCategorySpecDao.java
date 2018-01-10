package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.PCategorySpec;


public class PCategorySpecDao extends CommonDao<PCategorySpec>{
	
	public PCategorySpecDao(SessionFactory sessionFactory) {
		super(PCategorySpec.class);
		setSessionFactory(sessionFactory);
	}
	
}
