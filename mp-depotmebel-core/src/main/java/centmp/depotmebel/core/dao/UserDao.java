package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.User;


public class UserDao extends CommonDao<User>{
	
	public UserDao(SessionFactory sessionFactory) {
		super(User.class);
		setSessionFactory(sessionFactory);
	}
	
}
