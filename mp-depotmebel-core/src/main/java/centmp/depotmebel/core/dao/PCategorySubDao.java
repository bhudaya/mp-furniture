package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.PCategorySub;


public class PCategorySubDao extends CommonDao<PCategorySub>{
	
	public PCategorySubDao(SessionFactory sessionFactory) {
		super(PCategorySub.class);
		setSessionFactory(sessionFactory);
	}
	
}
