package centmp.depotmebel.wstrx.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
import centmp.depotmebel.core.dao.TrxDeductionDao;
import centmp.depotmebel.core.json.request.TrxRequest;
import centmp.depotmebel.core.json.request.VoucherRequest;
import centmp.depotmebel.core.json.response.CustomerResponse;
import centmp.depotmebel.core.json.response.TrxResponse;
import centmp.depotmebel.core.json.response.VoucherResponse;
import centmp.depotmebel.core.model.Trx;
import centmp.depotmebel.core.model.TrxDeduction;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.core.util.ConstantApp;
import centmp.depotmebel.core.util.UrlConnect;
import centmp.depotmebel.core.util.WSCustomer;
import centmp.depotmebel.wstrx.service.VoucherService;

@Service
public class VoucherServiceImpl implements VoucherService {
	
	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	private TrxDao trxDao;
 	private TrxDeductionDao trxDeductionDao;

	@Override
	public TrxResponse validate(HashMap<String, Object> hm) {
		TrxResponse jsonResponse = new TrxResponse();
		String errorCode = "-1";
		String exceptionMsg = "";
		
		try {
			String token = (String) hm.get(ConstantApp.TOKEN);
			TrxRequest reqparam = (TrxRequest) hm.get(ConstantApp.REQUEST_PARAMS);
			String orderNo = reqparam.getOrderNo()!=null?reqparam.getOrderNo().trim():"";
			String voucherCode = reqparam.getVoucherCode()!=null?reqparam.getVoucherCode().trim():"";
			
			List<String> params = new ArrayList<>();
			params.add(token);
			params.add(orderNo);
			params.add(voucherCode);
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
					
					trxDao =  new TrxDao(sessionFactory);
					List<Trx> trxList = trxDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("orderNo", orderNo));
					if(trxList.size()<1){
						errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.12002");
					}else{
						Trx trx = trxList.get(0);
						BigDecimal amountTrx = trx.getAmount();
						
						trxDeductionDao = new TrxDeductionDao(sessionFactory);
						Criterion cr1 = Restrictions.eq("trx", trx);
						Criterion cr2 = Restrictions.eq("voucherCode", voucherCode);
						Integer countTrxDeduc = trxDeductionDao.rowCount(cr1, cr2);
						if(countTrxDeduc>0){
							errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.12003");
						}else{
							VoucherRequest requestParam = new VoucherRequest();
							requestParam.setCustomerId(CipherUtil.encrypt(customerId));
							requestParam.setTrxAmount(Integer.toString(amountTrx.intValue()));
							requestParam.setVoucherCode(voucherCode);
							String jsonParam = new Gson().toJson(requestParam);
							
							String centWs = PropertyMessageUtil.getConfigProperties().getProperty("centws.url");
							String uriPath = PropertyMessageUtil.getConfigProperties().getProperty("voucher.validate.path");
							String url = centWs + uriPath;
							String result = UrlConnect.jsonType(url, jsonParam,token);
							VoucherResponse voucherResp = new Gson().fromJson(result, VoucherResponse.class);
							BigDecimal deductValBg = BigDecimal.ZERO;
							BigDecimal amountTotalBg = amountTrx.setScale(0, RoundingMode.HALF_UP);
							
							if(voucherResp.getErrorCode()!=0){
								System.out.println("VoucherResult: "+result);
								errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.12004");
								exceptionMsg = voucherResp.getErrorMsg();
							}else{
								String deductValStr = voucherResp.getDeductionValue();
								deductValBg = new BigDecimal(deductValStr);
								amountTotalBg = amountTrx.subtract(deductValBg).setScale(0, RoundingMode.HALF_UP);
								
								TrxDeduction trxDeduct = new TrxDeduction();
								trxDeduct.setTrx(trx);
								trxDeduct.setVoucherCode(voucherCode);
								trxDeduct.setVoucherAmount(deductValBg);
								trxDeductionDao = new TrxDeductionDao(sessionFactory);
								trxDeductionDao.saveOrUpdate(trxDeduct);
								
								trx.setAmount(amountTotalBg);
								trxDao = new TrxDao(sessionFactory);
								trxDao.saveOrUpdate(trx);
								
								errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.0");
							}
							
							jsonResponse.setOrderNo(trx.getOrderNo());
							jsonResponse.setTrxTime(FastDateFormat.getInstance("yyyyMMdd-HHmmss").format(trx.getExpireTime()));
							jsonResponse.setAmountTrx(Integer.toString(amountTrx.intValue()));
							jsonResponse.setVoucherAmont(Integer.toString(deductValBg.intValue()));
							jsonResponse.setVoucherAmontIdr(CommonUtil.currencyIDR(deductValBg.doubleValue()));
							jsonResponse.setAmountTotal(Integer.toString(amountTotalBg.intValue()));
						}
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
	public TrxResponse edit(HashMap<String, Object> hm) {
		TrxResponse jsonResponse = new TrxResponse();
		String errorCode = "-1";
		String exceptionMsg = "";
		
		try {
			String token = (String) hm.get(ConstantApp.TOKEN);
			TrxRequest reqparam = (TrxRequest) hm.get(ConstantApp.REQUEST_PARAMS);
			String trxDeductIdEncr = reqparam.getTrxDeductId()!=null?reqparam.getTrxDeductId().trim():"";
			String voucherCode = reqparam.getVoucherCode()!=null?reqparam.getVoucherCode().trim():"";
			
			List<String> params = new ArrayList<>();
			params.add(token);
			params.add(trxDeductIdEncr);
			params.add(voucherCode);
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
					
					String trxDeductId = CipherUtil.decrypt(trxDeductIdEncr);
					Criterion cr1 = Restrictions.eq("ID", Long.valueOf(trxDeductId));
					
					trxDeductionDao = new TrxDeductionDao(sessionFactory);
					List<TrxDeduction> trxDeductList = trxDeductionDao.loadBy(Order.asc("ID"), 1, cr1);
					if(trxDeductList.size()<1){
						errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.12005");
					}else{
						TrxDeduction trxDeduct = trxDeductList.get(0);
						Trx trx = trxDeduct.getTrx();
						String voucherCodeOld = trxDeduct.getVoucherCode();
						BigDecimal amountFirst = trx.getAmount().add(trxDeduct.getVoucherAmount());
						
						if(trxDeduct.getVoucherCode().equalsIgnoreCase(voucherCode)){
							errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.12005");
						}else {
							VoucherRequest requestParam = new VoucherRequest();
							requestParam.setCustomerId(CipherUtil.encrypt(customerId));
							requestParam.setTrxAmount(Integer.toString(amountFirst.intValue()));
							requestParam.setVoucherCode(voucherCode);
							requestParam.setVoucherCodeOld(voucherCodeOld);
							String jsonParam = new Gson().toJson(requestParam);
							
							String centWs = PropertyMessageUtil.getConfigProperties().getProperty("cent.ws.url");
							String uriPath = PropertyMessageUtil.getConfigProperties().getProperty("voucher.edit.path");
							String url = centWs + uriPath;
							String result = UrlConnect.jsonType(url, jsonParam);
							VoucherResponse voucherResp = new Gson().fromJson(result, VoucherResponse.class);
							
							BigDecimal deductValBg = BigDecimal.ZERO;
							BigDecimal amountTotalBg = amountFirst.setScale(0, RoundingMode.HALF_UP);
							
							if(voucherResp.getErrorCode()!=0){
								System.out.println("VoucherResult: "+result);
								errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.12004");
								exceptionMsg = voucherResp.getErrorMsg();
							}else{
								String deductValStr = voucherResp.getDeductionValue();
								deductValBg = new BigDecimal(deductValStr);
								amountTotalBg = amountFirst.subtract(deductValBg).setScale(0, RoundingMode.HALF_UP);
								
								trxDeduct.setVoucherCode(voucherCode);
								trxDeduct.setVoucherAmount(deductValBg);
								trxDeductionDao = new TrxDeductionDao(sessionFactory);
								trxDeductionDao.saveOrUpdate(trxDeduct);
								
								trx.setAmount(amountTotalBg);
								trxDao = new TrxDao(sessionFactory);
								trxDao.saveOrUpdate(trx);
								
								errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.0");
							}
							
							jsonResponse.setOrderNo(trx.getOrderNo());
							jsonResponse.setTrxTime(FastDateFormat.getInstance("yyyyMMdd-HHmmss").format(trx.getExpireTime()));
							jsonResponse.setAmountTrx(Integer.toString(amountFirst.intValue()));
							jsonResponse.setVoucherAmont(Integer.toString(deductValBg.intValue()));
							jsonResponse.setVoucherAmontIdr(CommonUtil.currencyIDR(deductValBg.doubleValue()));
							jsonResponse.setAmountTotal(Integer.toString(amountTotalBg.intValue()));
						}
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
