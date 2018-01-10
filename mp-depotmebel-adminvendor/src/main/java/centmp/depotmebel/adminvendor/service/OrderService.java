package centmp.depotmebel.adminvendor.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import centmp.depotmebel.adminvendor.bean.SessionBean;
import centmp.depotmebel.adminvendor.bean.TrxBean;

public interface OrderService {
	
	List<TrxBean> orderList(SessionBean sessionBean);
	TrxBean detail(String itemId);
	
	boolean toAcknowledged(SessionBean sessionBean, String itemId);
	TrxBean voProcess(String itemId);
	boolean readyToQc(SessionBean sessionBean, String itemId, MultipartFile imgQc);
	
	TrxBean toCourierForm(String itemId);
	boolean toCourierSubmit(SessionBean sessionBean, String itemId, String courierId);
	
	TrxBean podForm(String itemId);
	boolean podFormSubmit(SessionBean sessionBean, String itemId, MultipartFile imgPod, 
			String receiverName, String receiverPhone);
}
