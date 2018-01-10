package centmp.depotmebel.adminpartner.thread;

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

import centmp.depotmebel.adminpartner.bean.MailTrxBean;
import centmp.depotmebel.adminpartner.velocity.PassQCVelocity;
import centmp.depotmebel.core.dao.TrxStatusDao;
import centmp.depotmebel.core.dao.VendorUserDao;
import centmp.depotmebel.core.enums.VENDOR_USER_TYPE;
import centmp.depotmebel.core.json.request.EmailRequest;
import centmp.depotmebel.core.model.TrxItem;
import centmp.depotmebel.core.model.TrxStatus;
import centmp.depotmebel.core.model.VendorUser;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.core.util.UrlConnect;

public class MailPassQCThread implements Runnable {
	
	private SessionFactory sessionFactory;
	private TrxItem trxItem;

	
	public MailPassQCThread(SessionFactory sessionFactory, TrxItem trxItem) {
		this.sessionFactory = sessionFactory;
		this.trxItem = trxItem;
	}

	@Override
	public void run() {
		System.out.println("MailPassQCThread - Start - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-velocity.xml");
		System.out.println("context: "+context);
		
		PassQCVelocity velocity = (PassQCVelocity) context.getBean("passQcVelocity");
		
		try {
			Criterion cr1 = Restrictions.eq("vendor", trxItem.getVendor());
			Criterion cr2 = Restrictions.eq("type", VENDOR_USER_TYPE.ADMIN);
			Criterion cr3 = Restrictions.eq("type", VENDOR_USER_TYPE.OPERATOR);
			Criterion cr4 = Restrictions.or(cr2, cr3);
			
			String[] to = new String[0];
			VendorUserDao vendUserDao = new VendorUserDao(sessionFactory);
			List<VendorUser> userList = vendUserDao.loadBy(Order.asc("ID"), cr1, cr4);
			if(userList.size()>0){
				to = new String[userList.size()];
				
				for(int i=0;i<userList.size();i++) {
					VendorUser venduser = userList.get(i);
					to[i] = venduser.getUser().getEmail();
				}
			}
			
			
			String paidDateStr = "";
			TrxStatusDao trxStatusDao = new TrxStatusDao(sessionFactory);
			List<TrxStatus> trxStatusList = trxStatusDao.loadBy(Order.desc("ID"), 1, Restrictions.eq("trx", this.trxItem.getTrx()));
			if(trxStatusList.size()>0){
				TrxStatus trxStatus = trxStatusList.get(0);
				paidDateStr = FastDateFormat.getInstance("dd-MM-yyyy, HH:mm").format(trxStatus.getDate_());
			}
			
			MailTrxBean detail = new MailTrxBean();
			detail.setProductName(this.trxItem.getProduct().getName());
			detail.setQuantity(this.trxItem.getQuantity().toString());
			detail.setPrice(CommonUtil.currencyIDR(this.trxItem.getPrice().doubleValue()));
			detail.setNotes("-");
			
			String content = velocity.content(trxItem.getTrx(), paidDateStr, trxItem.getVendor(), detail);
			
			String centwsUrl = PropertyMessageUtil.getConfigProperties().getProperty("centws.url");
			String sendMailPath = PropertyMessageUtil.getConfigProperties().getProperty("email.send");
			String url = centwsUrl + sendMailPath;
			
			EmailRequest request = new EmailRequest();
			request.setDestinations(to);
			request.setSubject("Pesanan Baru: ["+trxItem.getTrx().getOrderNo()+"]");
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
		
		System.out.println("MailPassQCThread - End - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
	}

}
