package centmp.depotmebel.wstrx.thread;

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

import centmp.depotmebel.core.dao.TrxItemDao;
import centmp.depotmebel.core.dao.UserDao;
import centmp.depotmebel.core.dao.VendorUserDao;
import centmp.depotmebel.core.enums.STATUS;
import centmp.depotmebel.core.enums.USER_TYPE;
import centmp.depotmebel.core.enums.VENDOR_USER_TYPE;
import centmp.depotmebel.core.json.request.EmailRequest;
import centmp.depotmebel.core.model.Trx;
import centmp.depotmebel.core.model.TrxItem;
import centmp.depotmebel.core.model.User;
import centmp.depotmebel.core.model.Vendor;
import centmp.depotmebel.core.model.VendorUser;
import centmp.depotmebel.core.util.UrlConnect;
import centmp.depotmebel.wstrx.velocity.TrxPaidVelocity;

public class TrxPaidThread implements Runnable {

	private SessionFactory sessionFactory;
	private Trx trx;
	private Date date;
	
	public TrxPaidThread(SessionFactory sessionFactory, Trx trx, Date date) {
		this.sessionFactory = sessionFactory;
		this.trx = trx;
		this.date = date;
	}

	@Override
	public void run() {
		
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-velocity.xml");
		System.out.println("context: "+context);
		
		TrxPaidVelocity velocity = (TrxPaidVelocity) context.getBean("trxPaidVelocity");
		//System.out.println("velocity: "+velocity);
		
		String url = "";
		String paidDate = FastDateFormat.getInstance("dd-MM-yyyy, HH:mm").format(date);
		int numNoVendor = 0;
		List<TrxItem> itemList = new ArrayList<>();
		
		try {
			String centwsUrl = PropertyMessageUtil.getConfigProperties().getProperty("centws.url");
			String sendMailPath = PropertyMessageUtil.getConfigProperties().getProperty("email.send");
			url = centwsUrl + sendMailPath;
			
			TrxItemDao itemDao = new TrxItemDao(sessionFactory);
			itemList = itemDao.loadBy(Order.asc("ID"), Restrictions.eq("trx", trx));
			for(TrxItem trxItem: itemList) {
				if(trxItem.getVendor()==null)numNoVendor++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * Send to Customer
		 */
		try {
			String content = velocity.customerContent(trx);
			
			EmailRequest request = new EmailRequest();
			request.setDestination(trx.getCustomerEmail());
			request.setSubject("Pembayaran Anda untuk No Order ["+trx.getOrderNo()+"] SUKSES");
			request.setContent(content);
			String jsonParam = new Gson().toJson(request);
			String response = UrlConnect.jsonType(url, jsonParam);
			System.out.println("WS-Response: "+response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		/*
		 * Send to Partner
		 */
		try {
			Criterion cr1 = Restrictions.eq("role", USER_TYPE.PARTNER_ADMIN);
			Criterion cr2 = Restrictions.eq("status", STATUS.ACTIVE);
			UserDao userDao = new UserDao(sessionFactory);
			List<User> userList = userDao.loadBy(Order.asc("ID"), cr1, cr2);
			if(userList.size()>0){
				String[] to = new String[userList.size()];
				for(int i=0;i<userList.size();i++) {
					User user = userList.get(i);
					to[i] = user.getEmail(); 
				}
				
				String unallocatedSubject = "";
				String statusDesc = "On Vendor";
				if(numNoVendor>0){
					unallocatedSubject = " - Vendor belum teridentifikasi";
					statusDesc = "Vendor belum teralokasi (Unallocated)";
				}
				
				String content = velocity.partnerContent(trx, paidDate, statusDesc);
				
				EmailRequest request = new EmailRequest();
				request.setDestinations(to);
				request.setSubject("Pesanan Baru: ["+trx.getOrderNo()+"]"+unallocatedSubject);
				request.setContent(content);
				String jsonParam = new Gson().toJson(request);
				String response = UrlConnect.jsonType(url, jsonParam);
				System.out.println("WS-Response: "+response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * Send to Vendor
		 */
		try {
			if(numNoVendor<1){
				List<String> vendorUsers = new ArrayList<>();
				
				for(TrxItem trxItem: itemList) {
					Vendor vendor = trxItem.getVendor();
					if(vendor!=null){
						Criterion cr1 = Restrictions.eq("vendor", vendor);
						Criterion cr2 = Restrictions.eq("type", VENDOR_USER_TYPE.ADMIN);
						Criterion cr3 = Restrictions.eq("type", VENDOR_USER_TYPE.OPERATOR);
						Criterion cr4 = Restrictions.or(cr2, cr3);
						
						VendorUserDao venduserDao = new VendorUserDao(sessionFactory);
						List<VendorUser> vendusers = venduserDao.loadBy(Order.asc("ID"), cr1, cr4);
						for(VendorUser vendorUser: vendusers) {
							vendorUsers.add(vendorUser.getUser().getEmail());
						}
					}
				}
				
				String[] to = vendorUsers.toArray(new String[vendorUsers.size()]);
				String content = velocity.vendorContent(trx, paidDate);
				
				EmailRequest request = new EmailRequest();
				request.setDestinations(to);
				request.setSubject("Pesanan Baru: ["+trx.getOrderNo()+"]");
				request.setContent(content);
				String jsonParam = new Gson().toJson(request);
				String response = UrlConnect.jsonType(url, jsonParam);
				System.out.println("WS-Response: "+response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("InterruptedException: "+e.getMessage());
		}
	}

}
