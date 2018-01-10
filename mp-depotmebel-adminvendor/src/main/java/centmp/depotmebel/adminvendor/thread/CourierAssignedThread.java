package centmp.depotmebel.adminvendor.thread;

import java.util.List;

import org.apache.commons.lang.time.FastDateFormat;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bilmajdi.util.PropertyMessageUtil;
import com.google.gson.Gson;

import centmp.depotmebel.adminvendor.velocity.CourierAssignedVelocity;
import centmp.depotmebel.core.dao.TrxStatusDao;
import centmp.depotmebel.core.dao.VendorUserDao;
import centmp.depotmebel.core.json.request.EmailRequest;
import centmp.depotmebel.core.model.TrxItem;
import centmp.depotmebel.core.model.TrxStatus;
import centmp.depotmebel.core.model.VendorUser;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.core.util.UrlConnect;

public class CourierAssignedThread implements Runnable {

	private SessionFactory sessionFactory;
	private TrxItem trxItem;
	private String operatorName;
	private String operatorEmail;
	private String courierId;

	public CourierAssignedThread(SessionFactory sessionFactory, TrxItem trxItem, String operatorName, String operatorEmail, String courierId) {
		this.sessionFactory = sessionFactory;
		this.trxItem = trxItem;
		this.operatorName = operatorName;
		this.operatorEmail = operatorEmail;
		this.courierId = courierId;
	}

	@Override
	public void run() {
		System.out.println("CourierAssignedThread - Start - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-velocity.xml");
		System.out.println("context: "+context);
		
		CourierAssignedVelocity velocity = (CourierAssignedVelocity) context.getBean("courierAssignedVelocity");
		
		try {
			String paidDateStr = "";
			TrxStatusDao trxStatusDao = new TrxStatusDao(sessionFactory);
			List<TrxStatus> trxStatusList = trxStatusDao.loadBy(Order.desc("ID"), 1, Restrictions.eq("trx", this.trxItem.getTrx()));
			if(trxStatusList.size()>0){
				TrxStatus trxStatus = trxStatusList.get(0);
				paidDateStr = FastDateFormat.getInstance("dd-MM-yyyy, HH:mm").format(trxStatus.getDate_());
			}
			
			String centwsUrl = PropertyMessageUtil.getConfigProperties().getProperty("centws.url");
			String sendMailPath = PropertyMessageUtil.getConfigProperties().getProperty("email.send");
			String url = centwsUrl + sendMailPath;
			
			String toCourier = "";
			String courierName = "";
			VendorUserDao vendUserDao = new VendorUserDao(sessionFactory);
			Criterion crVendUserId = Restrictions.eq("ID", Long.valueOf(courierId));
			List<VendorUser> vendUserList = vendUserDao.loadBy(Order.asc("ID"), 1, crVendUserId);
			if(vendUserList.size()>0){
				VendorUser vendUser = vendUserList.get(0);
				toCourier = vendUser.getUser().getEmail();
				courierName = vendUser.getUser().getName();
			}
			
			/*
			 * To Partner
			 */
			String contectToPartner = velocity.contentPartner(trxItem, paidDateStr, trxItem.getVendor().getName(), operatorName, courierName);
			
			EmailRequest request = new EmailRequest();
			request.setDestination(operatorEmail);
			request.setSubject("Pesanan Untuk Dikirimkan : ["+trxItem.getTrx().getOrderNo()+"]");
			request.setContent(contectToPartner);
			String jsonParam = new Gson().toJson(request);
			String response = UrlConnect.jsonType(url, jsonParam);
			System.out.println("WS-Response: "+response);
			
			
			/*
			 * To Courier
			 */
			String contectToCourier = velocity.contentCourier(trxItem, courierName, paidDateStr, trxItem.getVendor(), operatorName);
			
			request = new EmailRequest();
			request.setDestination(toCourier);
			request.setSubject("Pesanan Untuk Dikirimkan : ["+trxItem.getTrx().getOrderNo()+"]");
			request.setContent(contectToCourier);
			jsonParam = new Gson().toJson(request);
			response = UrlConnect.jsonType(url, jsonParam);
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
		
		System.out.println("CourierAssignedThread - End - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
	}

}
