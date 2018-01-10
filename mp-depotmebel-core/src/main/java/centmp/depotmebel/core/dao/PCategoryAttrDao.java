package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.PCategoryAttr;


public class PCategoryAttrDao extends CommonDao<PCategoryAttr>{
	
	public PCategoryAttrDao(SessionFactory sessionFactory) {
		super(PCategoryAttr.class);
		setSessionFactory(sessionFactory);
	}
	
}
