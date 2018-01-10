package centmp.depotmebel.core.dao;

import org.hibernate.SessionFactory;

import centmp.depotmebel.core.model.AccountUpgrade;

public class AccountUpgradeDao extends CommonDao<AccountUpgrade>{
	
	public AccountUpgradeDao(SessionFactory sessionFactory) {
		super(AccountUpgrade.class);
		setSessionFactory(sessionFactory);
	}
	
}
