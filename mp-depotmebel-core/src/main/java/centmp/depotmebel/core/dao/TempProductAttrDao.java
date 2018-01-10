package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.TempProductAttr;


public class TempProductAttrDao extends CommonDao<TempProductAttr>{
	
	public TempProductAttrDao(SessionFactory sessionFactory) {
		super(TempProductAttr.class);
		setSessionFactory(sessionFactory);
	}
	
}
