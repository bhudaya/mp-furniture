package centmp.depotmebel.adminpartner.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.FastDateFormat;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bilmajdi.util.PropertyMessageUtil;
import com.google.gson.Gson;

import centmp.depotmebel.adminpartner.velocity.TrxVendorAssignedVelocity;
import centmp.depotmebel.core.dao.VendorUserDao;
import centmp.depotmebel.core.enums.VENDOR_USER_TYPE;
import centmp.depotmebel.core.json.request.EmailRequest;
import centmp.depotmebel.core.model.TrxItem;
import centmp.depotmebel.core.model.Vendor;
import centmp.depotmebel.core.model.VendorUser;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.core.util.UrlConnect;

public class TrxSetVendorThread implements Runnable {

	private SessionFactory sessionFactory;
	private TrxItem trxItem;
	private Date date;
	private Vendor vendor;
	
	public TrxSetVendorThread(SessionFactory sessionFactory, TrxItem trxItem, Date date, Vendor vendor) {
		this.sessionFactory = sessionFactory;
		this.trxItem = trxItem;
		this.date = date;
		this.vendor = vendor;
	}

	@Override
	public void run() {
		System.out.println("TrxSetVendorThread - Start - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-velocity.xml");
		System.out.println("context: "+context);
		
		TrxVendorAssignedVelocity velocity = (TrxVendorAssignedVelocity) context.getBean("trxVendorAssignedVelocity");
		//System.out.println("velocity: "+velocity);
		
		String url = "";
		String paidDate = FastDateFormat.getInstance("dd-MM-yyyy, HH:mm").format(date);
		
		try {
			String centwsUrl = PropertyMessageUtil.getConfigProperties().getProperty("centws.url");
			String sendMailPath = PropertyMessageUtil.getConfigProperties().getProperty("email.send");
			url = centwsUrl + sendMailPath;
			
			Criterion cr1 = Restrictions.eq("vendor", vendor);
			Criterion cr2 = Restrictions.eq("type", VENDOR_USER_TYPE.ADMIN);
			Criterion cr3 = Restrictions.eq("type", VENDOR_USER_TYPE.OPERATOR);
			Criterion cr4 = Restrictions.or(cr2, cr3);
			
			List<String> vendorUsers = new ArrayList<>();
			VendorUserDao venduserDao = new VendorUserDao(sessionFactory);
			List<VendorUser> vendusers = venduserDao.loadBy(Order.asc("ID"), cr1, cr4);
			for(VendorUser vendorUser: vendusers) {
				vendorUsers.add(vendorUser.getUser().getEmail());
			}
			
			String[] to = vendorUsers.toArray(new String[vendorUsers.size()]);
			String content = velocity.content(trxItem.getTrx(), paidDate);
			
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
		
		System.out.println("TrxSetVendorThread - End - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
	}

}
