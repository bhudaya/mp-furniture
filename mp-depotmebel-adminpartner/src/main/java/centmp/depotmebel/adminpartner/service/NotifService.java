package centmp.depotmebel.adminpartner.service;

import java.util.List;

import centmp.depotmebel.core.model.NotifTask;

public interface NotifService {
	
	List<NotifTask> listAll();
	void productApprovalReduce();
	void readyQcReduce();

}
