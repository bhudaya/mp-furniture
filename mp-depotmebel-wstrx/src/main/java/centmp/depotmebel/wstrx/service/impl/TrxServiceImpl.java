package centmp.depotmebel.wstrx.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.time.FastDateFormat;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.bilmajdi.util.BielUtil;
import com.bilmajdi.util.CipherUtil;
import com.bilmajdi.util.PropertyMessageUtil;
import com.google.gson.Gson;

import centmp.depotmebel.core.dao.OrderNoDao;
import centmp.depotmebel.core.dao.TrxDao;
import centmp.depotmebel.core.dao.TrxItemDao;
import centmp.depotmebel.core.dao.TrxStatusDao;
import centmp.depotmebel.core.enums.TRX_STATUS;
import centmp.depotmebel.core.json.request.TrxItemRequest;
import centmp.depotmebel.core.json.request.TrxRequest;
import centmp.depotmebel.core.json.response.CustomerResponse;
import centmp.depotmebel.core.json.response.TrxResponse;
import centmp.depotmebel.core.model.OrderNo;
import centmp.depotmebel.core.model.Trx;
import centmp.depotmebel.core.model.TrxItem;
import centmp.depotmebel.core.model.TrxStatus;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.core.util.ConstantApp;
import centmp.depotmebel.core.util.WSCustomer;
import centmp.depotmebel.wstrx.service.TrxService;
import centmp.depotmebel.wstrx.thread.PointBytrxAddThread;
import centmp.depotmebel.wstrx.thread.TrxPaidThread;
import centmp.depotmebel.wstrx.thread.VendorBalanceThread;

@Service
public class TrxServiceImpl implements TrxService {
	
	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	@Autowired
	@Qualifier(value="taskExecutor")
	protected ThreadPoolTaskExecutor threadTask;
	
	private OrderNoDao orderNoDao;
	private TrxItemDao trxItemDao;
	private TrxDao trxDao;
	private TrxStatusDao trxStatusDao;

	@Override
	public TrxResponse checkout(HashMap<String, Object> hm) {
		TrxResponse jsonResponse = new TrxResponse();
		String errorCode = "-1";
		String exceptionMsg = "";
		
		try {
			String token = (String) hm.get(ConstantApp.TOKEN);
			TrxRequest reqparam = (TrxRequest) hm.get(ConstantApp.REQUEST_PARAMS);
			/*String address = reqparam.getAddress()!=null?reqparam.getAddress().trim():"";
			String postalCode = reqparam.getPostalCode()!=null?reqparam.getPostalCode().trim():"";*/
			String notes = reqparam.getNotes()!=null?reqparam.getNotes().trim():"";
			List<TrxItemRequest> trxItems = reqparam.getTrxItemList();
			
			List<String> params = new ArrayList<>();
			params.add(token);
			//if(trxitemIds.size()<1)params.add("");
			if(trxItems.size()<1)params.add("");
			if(!CommonUtil.checkMandatoryParameter(params)){
				errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10002");
				exceptionMsg = "Mandatory parameter can't be null or empty";
			}else{
				CustomerResponse customerresp = WSCustomer.findByToken(token);
				if(customerresp.getErrorCode()!=0){
					errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10006");
					exceptionMsg = customerresp.getTechnicalMsg();
				}else{
					String customerIdEncr = CipherUtil.decrypt(customerresp.getId());
					String customerId = customerIdEncr.replace(ConstantApp.CUSTOMER_ID_COMBINE, "");
					String customerName = customerresp.getName();
					String customerEmail = customerresp.getEmail();
					String customerPhone = customerresp.getPhone();
					Calendar calnow = Calendar.getInstance(BielUtil.timeZoneJakarta());
					
					OrderNo orderno = new OrderNo();
					Integer dateSeq = 1;
					Integer seq = 1;
					String dateStr = FastDateFormat.getInstance("yyyyMM").format(calnow);
					orderNoDao = new OrderNoDao(sessionFactory);
					List<OrderNo> orderNoList = orderNoDao.loadBy(Order.desc("ID"), 1, Restrictions.eq("date", dateStr));
					if(orderNoList.size()>0){
						orderno = orderNoList.get(0);
						Integer seqLast = orderno.getSeq();
						dateSeq = orderno.getDateSeq();
						seq = seqLast + 1;
					}else{
						orderNoDao = new OrderNoDao(sessionFactory);
						OrderNo on_ = orderNoDao.lastRow("ID");
						if(on_!=null){
							dateSeq = on_.getDateSeq()+1;
						}
					}
					String orderNo_ = CommonUtil.generateOrderNo(dateSeq.toString(), seq.toString());
					
					orderno.setDate(dateStr);
					orderno.setDateSeq(dateSeq);
					orderno.setSeq(seq);
					orderNoDao = new OrderNoDao(sessionFactory);
					orderNoDao.saveOrUpdate(orderno);
					System.out.println("OrderNO: "+orderNo_);
					
					BigDecimal amount = BigDecimal.ZERO;
					List<TrxItem> trxItemCollect = new ArrayList<>();
					/*for (String trxitemId: trxitemIds) {
						String decrypt = CipherUtil.decrypt(trxitemId);
						String iditem = decrypt.replace(ConstantApp.TRXITEM_ID_COMBINE, "");
						
						trxItemDao = new TrxItemDao(sessionFactory);
						List<TrxItem> trxItemList = trxItemDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(iditem)));
						if(trxItemList.size()>0){
							TrxItem trxitem =trxItemList.get(0);
							
							BigDecimal price = trxitem.getPrice();
							amount = amount.add(price);
							
							trxItemCollect.add(trxitem);
						}	
					}*/
					for(TrxItemRequest trxItemReq: trxItems){
						String decrypt = CipherUtil.decrypt(trxItemReq.getId());
						String iditem = decrypt.replace(ConstantApp.TRXITEM_ID_COMBINE, "");
						
						trxItemDao = new TrxItemDao(sessionFactory);
						List<TrxItem> trxItemList = trxItemDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(iditem)));
						if(trxItemList.size()>0){
							TrxItem trxitem =trxItemList.get(0);
							if(trxItemReq.getQuantity()!=null)trxitem.setQuantity(Integer.parseInt(trxItemReq.getQuantity()));
							if(trxItemReq.getPrice()!=null)trxitem.setPrice(new BigDecimal(trxItemReq.getPrice()));
							trxItemDao = new TrxItemDao(sessionFactory);
							trxItemDao.saveOrUpdate(trxitem);
							
							BigDecimal price = trxitem.getPrice();
							amount = amount.add(price);
							
							trxItemCollect.add(trxitem);
						}
					}
					
					String expireMnt = PropertyMessageUtil.getConfigProperties().getProperty("trx.expire.minute");
					Integer expireMntInt = Integer.parseInt(expireMnt);
					Integer mntNow = calnow.get(Calendar.MINUTE);
					Calendar calExp = Calendar.getInstance(BielUtil.timeZoneJakarta());
					calExp.set(Calendar.MINUTE, mntNow+expireMntInt);
					String expireTime = FastDateFormat.getInstance("yyyyMMdd-HHmmss").format(calExp.getTime());
					
					Trx trx = new Trx();
					trx.setOrderNo(orderNo_);
					trx.setCustomerId(customerId);
					trx.setCustomerName(customerName);
					trx.setCustomerEmail(customerEmail);
					trx.setCustomerPhone(customerPhone);
					/*trx.setAddress(!address.isEmpty()?address:null);
					trx.setCity(city);
					trx.setPostalCode(!postalCode.isEmpty()?postalCode:null);*/
					trx.setAmount(amount);
					trx.setCreatedTime(calnow.getTime());
					trx.setExpireTime(calExp.getTime());
					trxDao = new TrxDao(sessionFactory);
					trxDao.saveOrUpdate(trx);
					
					TrxStatus trxstatus = new TrxStatus();
					trxstatus.setTrx(trx);
					trxstatus.setDate_(calnow.getTime());
					trxstatus.setNotes(notes);
					trxstatus.setStatus(TRX_STATUS.CREATED);
					trxStatusDao = new TrxStatusDao(sessionFactory);
					trxStatusDao.saveOrUpdate(trxstatus);
					
					for(TrxItem ti: trxItemCollect){
						ti.setTrx(trx);
						trxItemDao = new TrxItemDao(sessionFactory);
						trxItemDao.saveOrUpdate(ti);
					}
					
					jsonResponse.setOrderNo(orderNo_);
					jsonResponse.setAmountTrx(Integer.toString(amount.intValue()));
					jsonResponse.setAmountTotal(Integer.toString(amount.intValue()));
					jsonResponse.setTrxTime(FastDateFormat.getInstance("yyyyMMdd-HHmmss").format(calnow.getTime()));
					jsonResponse.setExpireTime(expireTime);
					jsonResponse.setCustomerName(customerName);
					jsonResponse.setCustomerEmail(customerEmail);
					jsonResponse.setCustomerPhone(customerPhone);
					//jsonResponse.setAddress(address);
					//jsonResponse.setCity(city!=null?city.getName():"");
					//jsonResponse.setProvince(city!=null?city.getProvince().getName():"");
					//jsonResponse.setPostalCode(postalCode);
					//jsonResponse.setItemRespList(itemRespList);
					
					errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.0");
				}
			}	
		} catch (Exception e) {
			errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10001");
			exceptionMsg = e.getLocalizedMessage()!=null?e.getLocalizedMessage():"NULL";
			e.printStackTrace();
		}
		
		jsonResponse.setErrorCode(Integer.parseInt(errorCode));
		jsonResponse.setErrorMsg(PropertyMessageUtil.getMessageProperties().getProperty("code.error.msg."+errorCode));
		jsonResponse.setTechnicalMsg(exceptionMsg.isEmpty()?PropertyMessageUtil.getMessageProperties().getProperty("code.techn.msg."+errorCode):exceptionMsg);
	
		return jsonResponse;
	}
	
	@Override
	public TrxResponse updateAddress(HashMap<String, Object> hm) {
		TrxResponse jsonResponse = new TrxResponse();
		String errorCode = "-1";
		String exceptionMsg = "";
		
		try {
			String token = (String) hm.get(ConstantApp.TOKEN);
			TrxRequest reqparam = (TrxRequest) hm.get(ConstantApp.REQUEST_PARAMS);
			String orderNo = reqparam.getOrderNo()!=null?reqparam.getOrderNo().trim():"";
			String address = reqparam.getAddress()!=null?reqparam.getAddress().trim():"";
			String postalCode = reqparam.getPostalCode()!=null?reqparam.getPostalCode().trim():"";
			
			List<String> params = new ArrayList<>();
			params.add(token);
			params.add(orderNo);
			params.add(address);
			params.add(postalCode);
			if(!CommonUtil.checkMandatoryParameter(params)){
				errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10002");
				exceptionMsg = "Mandatory parameter can't be null or empty";
			}else{
				CustomerResponse customerresp = WSCustomer.findByToken(token);
				if(customerresp.getErrorCode()!=0){
					errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10006");
					exceptionMsg = customerresp.getTechnicalMsg();
				}else{
					
					trxDao =  new TrxDao(sessionFactory);
					List<Trx> trxList = trxDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("orderNo", orderNo));
					if(trxList.size()<1){
						errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.12001");
					}else{
						
						Trx trx = trxList.get(0);
						trx.setAddress(address);
						trx.setPostalCode(postalCode);
						trxDao =  new TrxDao(sessionFactory);
						trxDao.saveOrUpdate(trx);
						
						errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.0");
					}
					
				}
			}	
			
		} catch (Exception e) {
			errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10001");
			exceptionMsg = e.getLocalizedMessage()!=null?e.getLocalizedMessage():"NULL";
			e.printStackTrace();
		}
		
		jsonResponse.setErrorCode(Integer.parseInt(errorCode));
		jsonResponse.setErrorMsg(PropertyMessageUtil.getMessageProperties().getProperty("code.error.msg."+errorCode));
		jsonResponse.setTechnicalMsg(exceptionMsg.isEmpty()?PropertyMessageUtil.getMessageProperties().getProperty("code.techn.msg."+errorCode):exceptionMsg);
	
		return jsonResponse;
	}
	
	@Override
	public TrxResponse updateExpireTime(HashMap<String, Object> hm) {
		TrxResponse jsonResponse = new TrxResponse();
		String errorCode = "-1";
		String exceptionMsg = "";
		
		try {
			String token = (String) hm.get(ConstantApp.TOKEN);
			TrxRequest reqparam = (TrxRequest) hm.get(ConstantApp.REQUEST_PARAMS);
			String orderNo = reqparam.getOrderNo()!=null?reqparam.getOrderNo().trim():"";
			String expTime = reqparam.getExpireTime()!=null?reqparam.getExpireTime().trim():"";
			
			List<String> params = new ArrayList<>();
			params.add(token);
			params.add(orderNo);
			params.add(expTime);
			if(!CommonUtil.checkMandatoryParameter(params)){
				errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10002");
				exceptionMsg = "Mandatory parameter can't be null or empty";
			}else{
				CustomerResponse customerresp = WSCustomer.findByToken(token);
				if(customerresp.getErrorCode()!=0){
					errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10006");
					exceptionMsg = customerresp.getTechnicalMsg();
				}else{
					trxDao =  new TrxDao(sessionFactory);
					List<Trx> trxList = trxDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("orderNo", orderNo));
					if(trxList.size()<1){
						errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.12001");
					}else{
						
						Date date = new SimpleDateFormat("yyyyMMdd-HHmmss").parse(expTime);
						Trx trx = trxList.get(0);
						trx.setExpireTime(date);
						
						trxDao = new TrxDao(sessionFactory);
						trxDao.saveOrUpdate(trx);
						
						errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.0");
					}
				}
			}	
			
		} catch (Exception e) {
			errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10001");
			exceptionMsg = e.getLocalizedMessage()!=null?e.getLocalizedMessage():"NULL";
			e.printStackTrace();
		}
		
		jsonResponse.setErrorCode(Integer.parseInt(errorCode));
		jsonResponse.setErrorMsg(PropertyMessageUtil.getMessageProperties().getProperty("code.error.msg."+errorCode));
		jsonResponse.setTechnicalMsg(exceptionMsg.isEmpty()?PropertyMessageUtil.getMessageProperties().getProperty("code.techn.msg."+errorCode):exceptionMsg);
	
		return jsonResponse;
	}
	
	@Override
	public String paidFromPadipay(HashMap<String, Object> hm) {
		try {
			System.out.println("hm: "+new Gson().toJson(hm));
			String resultCode = (String) hm.get("resultCode");
			String resultDesc = (String) hm.get("resultDesc");
			String invoiceCode = (String) hm.get("invoiceCode");
			String padipayCode = (String) hm.get("padipayCode");
			
			trxDao = new TrxDao(sessionFactory);
			List<Trx> trxList = trxDao.loadBy(Order.asc("ID"), Restrictions.eq("orderNo", invoiceCode));
			if(trxList.size()<1){
				System.out.println("orderNo/invoiceCode not found");
			}else{
				Trx trx = trxList.get(0);
				Calendar calnow = Calendar.getInstance(BielUtil.timeZoneJakarta());
				
				TRX_STATUS status = null;
				if(resultCode.equalsIgnoreCase("00")){
					status = TRX_STATUS.PAID;
					
					//Send Email
					threadTask.execute(new TrxPaidThread(sessionFactory, trx, calnow.getTime()));
					
					//Update vendor_dlvrcpcty_limit.credit_limit_use, 
					threadTask.execute(new VendorBalanceThread(sessionFactory, trx, calnow.getTime()));
					
					//Add Point
					String email = trx.getCustomerEmail();
					String trxCode = trx.getOrderNo();
					String amount = Integer.toString(trx.getAmount().intValue());
					threadTask.execute(new PointBytrxAddThread(email, trxCode, amount));
				}
				
				TrxStatus trxstatus = new TrxStatus();
				trxstatus.setTrx(trx);
				trxstatus.setDate_(calnow.getTime());
				trxstatus.setNotes("resultCode: "+resultCode+"; resultDesc: "+resultDesc);
				trxstatus.setPadipayCode(padipayCode);
				trxstatus.setStatus(status);
				
				trxStatusDao = new TrxStatusDao(sessionFactory);
				trxStatusDao.saveOrUpdate(trxstatus);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
