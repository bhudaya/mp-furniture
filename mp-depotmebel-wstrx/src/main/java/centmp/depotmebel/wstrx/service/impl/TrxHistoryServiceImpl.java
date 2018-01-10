package centmp.depotmebel.wstrx.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.time.FastDateFormat;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bilmajdi.util.CipherUtil;
import com.bilmajdi.util.PropertyMessageUtil;
import com.google.gson.Gson;

import centmp.depotmebel.core.dao.TrxDao;
import centmp.depotmebel.core.dao.TrxItemDao;
import centmp.depotmebel.core.dao.TrxStatusDao;
import centmp.depotmebel.core.enums.TRX_STATUS;
import centmp.depotmebel.core.json.request.TrxRequest;
import centmp.depotmebel.core.json.response.CustomerResponse;
import centmp.depotmebel.core.json.response.TrxItemResponse;
import centmp.depotmebel.core.json.response.TrxListResponse;
import centmp.depotmebel.core.json.response.TrxResponse;
import centmp.depotmebel.core.model.Trx;
import centmp.depotmebel.core.model.TrxItem;
import centmp.depotmebel.core.model.TrxStatus;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.core.util.ConstantApp;
import centmp.depotmebel.core.util.WSCustomer;
import centmp.depotmebel.wstrx.service.TrxHistoryService;

@Service
public class TrxHistoryServiceImpl implements TrxHistoryService {

	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	private TrxDao trxDao;
	private TrxStatusDao trxStatusDao;
	private TrxItemDao trxItemDao;

	@Override
	public TrxListResponse customer(String token) {
		TrxListResponse jsonResponse = new TrxListResponse();
		String errorCode = "-1";
		String exceptionMsg = "";
		
		try {
			List<String> params = new ArrayList<>();
			params.add(token);
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
					
					List<TrxResponse> trxRespList = new ArrayList<>();
					
					String sqlQuery = "SELECT a.order_no, a.created_time, a.expire_time, a.amount, "
							+ "a.address, a.postal_code, b.status_ "
							+ "FROM trx_ a, trx_status b "
							+ "where a.ID = b.trx AND a.customer_id = '"+customerId+"'";
					trxDao = new TrxDao(sessionFactory);
					List<?> queryList = trxDao.nativeQuery(sqlQuery);
					System.out.println("queryList: "+new Gson().toJson(queryList));
					for(int i=0;i<queryList.size();i++) {
						Object[] object = (Object[]) queryList.get(i);
						String orderNo = (String) object[0];
						Date createdTime = (Date) object[1];
						Date expireTime = (Date) object[2];
						BigDecimal amount = (BigDecimal) object[3];
						String address = (String) object[4];
						String postalCode = (String) object[5];
						Integer statusInt = (Integer) object[6];
						
						TrxResponse resp = new TrxResponse();
						resp.setOrderNo(orderNo);
						resp.setTrxTime(FastDateFormat.getInstance("dd-MM-yyyy, HH:mm").format(createdTime));
						resp.setExpireTime(FastDateFormat.getInstance("dd-MM-yyyy, HH:mm").format(expireTime));
						resp.setAmountTotal(amount.toString());
						resp.setLastStatus(TRX_STATUS.getValue(statusInt));
						resp.setAddress(address!=null?address:"");
						resp.setPostalCode(postalCode!=null?postalCode:"");
						trxRespList.add(resp);
					}
					
					jsonResponse.setTrxList(trxRespList);
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
	public TrxResponse detailToPay(HashMap<String, Object> hm) {
		TrxResponse jsonResponse = new TrxResponse();
		String errorCode = "-1";
		String exceptionMsg = "";
		
		try {
			String token = (String) hm.get(ConstantApp.TOKEN);
			TrxRequest reqparam = (TrxRequest) hm.get(ConstantApp.REQUEST_PARAMS);
			String orderNo = reqparam.getOrderNo()!=null?reqparam.getOrderNo().trim():"";
			
			List<String> params = new ArrayList<>();
			params.add(token);
			params.add(orderNo);
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
					
					Criterion crTrx1 = Restrictions.eq("orderNo", orderNo);
					Criterion crTrx2 = Restrictions.eq("customerId", customerId);
					
					trxDao = new TrxDao(sessionFactory);
					List<Trx> trxList = trxDao.loadBy(Order.asc("ID"), 1, crTrx1, crTrx2);
					if(trxList.size()<1){
						errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.12001");
					}else{
						Trx trx = trxList.get(0);
						Criterion crTrxStatus1 = Restrictions.eq("trx", trx);
						
						/*TrxStatus latesStatus = null;
						trxStatusDao = new TrxStatusDao(sessionFactory);
						List<TrxStatus> trxStatusList = trxStatusDao.loadBy(Order.desc("ID"), 1, crTrxStatus1);
						if(trxStatusList.size()>0){
							latesStatus = trxStatusList.get(0);
						}*/
						
						BigDecimal amountItem = BigDecimal.ZERO;
						List<TrxItemResponse> itemRespList = new ArrayList<>();
						
						trxItemDao = new TrxItemDao(sessionFactory);
						List<TrxItem> trxItemList = trxItemDao.loadBy(Order.asc("ID"), crTrxStatus1);
						for(int i=0;i<trxItemList.size();i++){
							TrxItem trxitem =trxItemList.get(i);
							
							BigDecimal price = trxitem.getPrice();
							amountItem = amountItem.add(price); 
							
							TrxItemResponse itemresp = new TrxItemResponse();
							itemresp.setProductName(trxitem.getProduct().getName());
							itemresp.setQuantity(trxitem.getQuantity().toString());
							itemresp.setPrice(Integer.toString(price.intValue()));
							itemRespList.add(itemresp);
						}
						
						/*String trxDeductId = "";
						String voucherCode = "";
						String voucherAmount = "0";
						String voucherAmountIdr = "0";
						trxDeductionDao = new TrxDeductionDao(sessionFactory);
						List<TrxDeduction> trxDeductList = trxDeductionDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("trx", trx));
						if(trxDeductList.size()>0){
							TrxDeduction trxDeduct = trxDeductList.get(0);
							trxDeductId = CipherUtil.encrypt(trxDeduct.getID().toString());
							voucherCode = trxDeduct.getVoucherCode();
							voucherAmount = Integer.toString(trxDeduct.getVoucherAmount().intValue());
							voucherAmountIdr = CommonUtil.currencyIDR(trxDeduct.getVoucherAmount().doubleValue());
						}*/
						
						jsonResponse.setOrderNo(trx.getOrderNo());
						jsonResponse.setAmountTrx(Integer.toString(amountItem.intValue()));
						//jsonResponse.setAmountTotal(Integer.toString(amount.intValue()));
						jsonResponse.setTrxTime(FastDateFormat.getInstance("yyyyMMdd-HHmmss").format(trx.getCreatedTime()));
						jsonResponse.setExpireTime(FastDateFormat.getInstance("yyyyMMdd-HHmmss").format(trx.getExpireTime()));
						jsonResponse.setCustomerName(trx.getCustomerName());
						jsonResponse.setCustomerEmail(trx.getCustomerEmail());
						jsonResponse.setCustomerPhone(trx.getCustomerPhone());
						jsonResponse.setItemRespList(itemRespList);
						//jsonResponse.setTrdDeductId(trxDeductId);
						//jsonResponse.setVoucherCode(voucherCode);
						//jsonResponse.setVoucherAmont(voucherAmount);
						//jsonResponse.setVoucherAmontIdr(voucherAmountIdr);
						
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
	public TrxResponse detailToCustomer(HashMap<String, Object> hm) {
		TrxResponse jsonResponse = new TrxResponse();
		String errorCode = "-1";
		String exceptionMsg = "";
		
		try {
			String token = (String) hm.get(ConstantApp.TOKEN);
			TrxRequest reqparam = (TrxRequest) hm.get(ConstantApp.REQUEST_PARAMS);
			String orderNo = reqparam.getOrderNo()!=null?reqparam.getOrderNo().trim():"";
			
			List<String> params = new ArrayList<>();
			params.add(token);
			params.add(orderNo);
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
					
					Criterion crTrx1 = Restrictions.eq("orderNo", orderNo);
					Criterion crTrx2 = Restrictions.eq("customerId", customerId);
					
					trxDao = new TrxDao(sessionFactory);
					List<Trx> trxList = trxDao.loadBy(Order.asc("ID"), 1, crTrx1, crTrx2);
					if(trxList.size()<1){
						errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.12001");
					}else{
						Trx trx = trxList.get(0);
						Criterion crTrxStatus1 = Restrictions.eq("trx", trx);
						
						
						StringBuffer address = new StringBuffer();
						if(trx.getAddress()!=null && !trx.getAddress().isEmpty()){
							address.append(trx.getAddress().replace("\n", "<br>"));
							if(trx.getCity()!=null){
								address.append(", ");address.append(trx.getCity().getName());
								address.append(" - ");address.append(trx.getCity().getProvince().getName());
								if(trx.getPostalCode()!=null)address.append(", ");address.append(trx.getPostalCode());
							}
						}
						
						String paidTime = "-";
						trxStatusDao = new TrxStatusDao(sessionFactory);
						Criterion crPaid = Restrictions.eq("status", TRX_STATUS.PAID);
						List<TrxStatus> trxStatusList = trxStatusDao.loadBy(Order.asc("ID"), 1, crTrxStatus1, crPaid);
						if(trxStatusList.size()>0){
							TrxStatus trxStatus = trxStatusList.get(0);
							paidTime = FastDateFormat.getInstance("yyyyMMdd-HHmmss").format(trxStatus.getDate_());
						}
						
						List<TrxItemResponse> itemRespList = new ArrayList<>();
						
						trxItemDao = new TrxItemDao(sessionFactory);
						List<TrxItem> trxItemList = trxItemDao.loadBy(Order.asc("ID"), crTrxStatus1);
						for(int i=0;i<trxItemList.size();i++){
							TrxItem trxitem =trxItemList.get(i);
							BigDecimal price = trxitem.getPrice();
							
							TrxItemResponse itemresp = new TrxItemResponse();
							itemresp.setProductName(trxitem.getProduct().getName());
							itemresp.setQuantity(trxitem.getQuantity().toString());
							itemresp.setPrice(Integer.toString(price.intValue()));
							
							/*itemresp.setProductName(product.getName());
							itemresp.setProductImg(productImg);
							itemresp.setAttrName(trxItem.getProductAttr().getLabel());
							itemresp.setPriceIdr(CommonUtil.currencyIDR(trxItem.getPrice().doubleValue()));
							itemresp.setLastStatus(lastStatus.toUpperCase());
							itemresp.setCustMsg(custMsg.replace("\n", "<br>"));
							itemresp.setCustMsgImg(custMsgImg);
							itemresp.setCustMsgImg2(custMsgImg2);
							itemresp.setImgPodUrl(imgPodUrl);
							itemresp.setReceiverName(trxItem.getReceiverName());
							itemresp.setReceiverPhone(trxItem.getReceiverPhone());*/
							
							itemRespList.add(itemresp);
						}
						
						jsonResponse.setOrderNo(trx.getOrderNo());
						jsonResponse.setTrxTime(FastDateFormat.getInstance("yyyyMMdd-HHmmss").format(trx.getCreatedTime()));
						jsonResponse.setExpireTime(FastDateFormat.getInstance("yyyyMMdd-HHmmss").format(trx.getExpireTime()));
						jsonResponse.setPaidTime(paidTime);
						jsonResponse.setAddress(address.toString().trim().isEmpty()?"-":address.toString().trim());
						jsonResponse.setItemRespList(itemRespList);
						
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
	
	
	

}
