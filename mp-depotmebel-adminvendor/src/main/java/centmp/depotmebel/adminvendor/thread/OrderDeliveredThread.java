package centmp.depotmebel.adminvendor.thread;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bilmajdi.util.PropertyMessageUtil;

import centmp.depotmebel.adminvendor.mail.PodMail;
import centmp.depotmebel.adminvendor.util.PdfPod;
import centmp.depotmebel.core.dao.TrxItemDao;
import centmp.depotmebel.core.model.TrxItem;
import centmp.depotmebel.core.util.CommonUtil;

public class OrderDeliveredThread implements Runnable {

	private SessionFactory sessionFactory;
	private TrxItem trxItem;
	private String courierName;
	private String receiverName;
	private String receiverPhone;
	
	public OrderDeliveredThread(SessionFactory sessionFactory, TrxItem trxItem, String courierName,
			String receiverName, String receiverPhone) {
		this.sessionFactory = sessionFactory;
		this.trxItem = trxItem;
		this.courierName = courierName;
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
	}

	@Override
	public void run() {
		System.out.println("OrderDeliveredThread - Start - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-velocity.xml");
		System.out.println("context: "+context);
		
		PodMail mail = (PodMail) context.getBean("podMail");
		
		try {
			BigDecimal amountItem = BigDecimal.ZERO;
			List<String[]> itemPdfList = new ArrayList<>();
			TrxItemDao itemDao = new TrxItemDao(sessionFactory);
			List<TrxItem> itemList = itemDao.loadBy(Order.asc("ID"), Restrictions.eq("trx", trxItem.getTrx()));
			for(TrxItem trxItem_: itemList) {
				
				amountItem = amountItem.add(trxItem_.getPrice());
				
				String[] str = new String[4];
				str[0] = trxItem_.getProduct().getName();
				str[1] = ""; //Custom Message
				str[2] = trxItem_.getQuantity().toString();
				str[3] = CommonUtil.currencyIDR(trxItem_.getPrice().doubleValue());
				itemPdfList.add(str);
			}
			
			String filepath = PropertyMessageUtil.getConfigProperties().getProperty("path.pdf.invoice");
			String filename = "TRX"+this.trxItem.getTrx().getOrderNo()+".pdf";
			
			HashMap<String, Object> hm = new HashMap<>();
			hm.put("filepath", filepath);
			hm.put("filename", filename);
			hm.put("orderNo", trxItem.getTrx().getOrderNo());
			hm.put("customerName", trxItem.getTrx().getCustomerName());
			hm.put("customerEmail", trxItem.getTrx().getCustomerEmail());
			hm.put("customerPhone", trxItem.getTrx().getCustomerPhone());
			hm.put("address", trxItem.getTrx().getAddress());
			hm.put("province", trxItem.getTrx().getCity()!=null?trxItem.getTrx().getCity().getProvince().getName():"-");
			hm.put("city", trxItem.getTrx().getCity()!=null?trxItem.getTrx().getCity().getName():"-");
			hm.put("postalCode", trxItem.getTrx().getPostalCode());
			hm.put("itemList", itemPdfList);
			hm.put("amountItem", CommonUtil.currencyIDR(amountItem.doubleValue()));
			hm.put("amountVoucher", "0");
			hm.put("amountTotal", CommonUtil.currencyIDR(trxItem.getTrx().getAmount().doubleValue()));
			
			File path = new File(filepath);
			if(!path.exists()){
				path.mkdirs();
			}
			
			String pdfFile = PdfPod.invoice(hm);
			File file = new File(pdfFile);
			System.out.println("file: "+file);
			
			/*String paidDateStr = "";
			TrxStatusDao trxStatusDao = new TrxStatusDao(sessionFactory);
			List<TrxStatus> trxStatusList = trxStatusDao.loadBy(Order.desc("ID"), 1, Restrictions.eq("trx", this.trxItem.getTrx()));
			if(trxStatusList.size()>0){
				TrxStatus trxStatus = trxStatusList.get(0);
				paidDateStr = FastDateFormat.getInstance("dd-MM-yyyy, HH:mm").format(trxStatus.getDate_());
			}*/
			
			/*List<String> partnerEmails = new ArrayList<>();
			UserDao userDao = new UserDao(sessionFactory);
			Criterion crPt1 = Restrictions.eq("status", STATUS.ACTIVE);
			Criterion crPt2 = Restrictions.eq("role", USER_TYPE.PARTNER_ADMIN);
			List<User> userList = userDao.loadBy(Order.asc("ID"), crPt1, crPt2);
			for (User user: userList) {
				partnerEmails.add(user.getEmail());
			}
			String[] toPartners = partnerEmails.toArray(new String[partnerEmails.size()]);

			String vendorUserName = "";
			String vendorUserEmail = "";
			Vendor vendor = this.trxItem.getVendor();
			VendorUserDao venduserDao = new VendorUserDao(sessionFactory);
			List<VendorUser> vendUserList = venduserDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("vendor", vendor), Restrictions.eq("type", VENDOR_USER_TYPE.OPERATOR));
			if(vendUserList.size()>0){
				VendorUser vendorUser = vendUserList.get(0);
				vendorUserEmail =  vendorUser.getUser().getEmail();
				vendorUserName = vendorUser.getUser().getName();
			}*/
			
			mail.sendToCustomer(trxItem, receiverName, receiverPhone, filename, file);
			//mail.sendToPartner(trxItem, toPartners, paidDateStr, courierName, receiverName, receiverPhone);
			//mail.sendToVendor(trxItem, vendorUserName, vendorUserEmail, paidDateStr, trxItem.getVendor().getName(), courierName, receiverName, receiverPhone);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("InterruptedException: "+e.getMessage());
		}
		
		System.out.println("OrderDeliveredThread - End - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
	}
}
