package centmp.depotmebel.adminpartner.service;

import centmp.depotmebel.adminpartner.bean.OrderBean;
import centmp.depotmebel.adminpartner.bean.OrderListBean;

public interface OrderService {
	
	OrderListBean list();
	OrderBean detail(String idencr);
	
	OrderListBean unallocatedList();
	String setVendor(String itemId, String vendorId);
	
	int readyQcNum();
	OrderListBean qcList();
	OrderBean qcDetail(String itemId);
	void passQc(String itemId);
	
}
