package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.City;


public class CityDao extends CommonDao<City>{
	
	public CityDao(SessionFactory sessionFactory) {
		super(City.class);
		setSessionFactory(sessionFactory);
	}
	
}
