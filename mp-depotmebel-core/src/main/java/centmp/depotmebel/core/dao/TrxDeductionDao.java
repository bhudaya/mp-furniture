package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.TrxDeduction;


public class TrxDeductionDao extends CommonDao<TrxDeduction>{
	
	public TrxDeductionDao(SessionFactory sessionFactory) {
		super(TrxDeduction.class);
		setSessionFactory(sessionFactory);
	}
	
}
