package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.Province;


public class ProvinceDao extends CommonDao<Province>{
	
	public ProvinceDao(SessionFactory sessionFactory) {
		super(Province.class);
		setSessionFactory(sessionFactory);
	}
	
}
