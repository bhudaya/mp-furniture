package centmp.depotmebel.adminpartner.service;

import java.util.List;

import centmp.depotmebel.adminpartner.bean.CustomerBean;

public interface CustomerService {
	
	List<CustomerBean> requestApprovalUpgrades();
	CustomerBean detailApprovalUpgrades(String idencrypt);

}
