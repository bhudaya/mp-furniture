package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.OrderNo;

public class OrderNoDao extends CommonDao<OrderNo>{
	
	public OrderNoDao(SessionFactory sessionFactory) {
		super(OrderNo.class);
		setSessionFactory(sessionFactory);
	}
	
}
