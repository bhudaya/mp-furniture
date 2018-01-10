package centmp.depotmebel.adminvendor.thread;

import java.util.List;

import org.apache.commons.lang.time.FastDateFormat;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bilmajdi.util.PropertyMessageUtil;
import com.google.gson.Gson;

import centmp.depotmebel.adminvendor.velocity.TrxReadyQCVelocity;
import centmp.depotmebel.core.dao.TrxStatusDao;
import centmp.depotmebel.core.dao.UserDao;
import centmp.depotmebel.core.enums.USER_TYPE;
import centmp.depotmebel.core.json.request.EmailRequest;
import centmp.depotmebel.core.model.TrxItem;
import centmp.depotmebel.core.model.TrxStatus;
import centmp.depotmebel.core.model.User;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.core.util.UrlConnect;

public class MailReadyQCThread implements Runnable {

	private SessionFactory sessionFactory;
	private TrxItem trxItem;

	
	public MailReadyQCThread(SessionFactory sessionFactory, TrxItem trxItem) {
		this.sessionFactory = sessionFactory;
		this.trxItem = trxItem;
	}

	@Override
	public void run() {
		System.out.println("TrxSetVendorThread - Start - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-velocity.xml");
		System.out.println("context: "+context);
		
		TrxReadyQCVelocity velocity = (TrxReadyQCVelocity) context.getBean("trxReadyQcVelocity");
		
		try {
			UserDao userDao = new UserDao(sessionFactory);
			List<User> userList = userDao.loadBy(Order.asc("ID"), Restrictions.eq("role", USER_TYPE.PARTNER_ADMIN));
			int userSize = userList.size();
			
			String[] sendToArr = new String[userSize];
			for(int i=0;i<userSize;i++){
				User user = userList.get(i);
				sendToArr[i] = user.getEmail();
			}
			
			String paidDateStr = "";
			TrxStatusDao trxStatusDao = new TrxStatusDao(sessionFactory);
			List<TrxStatus> trxStatusList = trxStatusDao.loadBy(Order.desc("ID"), 1, Restrictions.eq("trx", this.trxItem.getTrx()));
			if(trxStatusList.size()>0){
				TrxStatus trxStatus = trxStatusList.get(0);
				paidDateStr = FastDateFormat.getInstance("dd-MM-yyyy, HH:mm").format(trxStatus.getDate_());
			}
			
			String statusDesc = "Ready to QC ["+this.trxItem.getVendor().getName()+"]";
			String content = velocity.content(trxItem.getTrx(), "Admin", paidDateStr, statusDesc, trxItem.getVendor());
			
			String centwsUrl = PropertyMessageUtil.getConfigProperties().getProperty("centws.url");
			String sendMailPath = PropertyMessageUtil.getConfigProperties().getProperty("email.send");
			String url = centwsUrl + sendMailPath;
			
			EmailRequest request = new EmailRequest();
			request.setDestinations(sendToArr);
			request.setSubject("Pesanan Siap QC : ["+trxItem.getTrx().getOrderNo()+"]");
			request.setContent(content);
			String jsonParam = new Gson().toJson(request);
			String response = UrlConnect.jsonType(url, jsonParam);
			System.out.println("WS-Response: "+response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("InterruptedException: "+e.getMessage());
		}
		
		System.out.println("TrxSetVendorThread - End - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
	}

}
