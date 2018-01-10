package centmp.depotmebel.wsuser.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bilmajdi.json.response.BaseJsonResponse;
import com.bilmajdi.util.CipherUtil;
import com.bilmajdi.util.PropertyMessageUtil;
import com.google.gson.Gson;

import centmp.depotmebel.core.dao.AccountUpgradeDao;
import centmp.depotmebel.core.dao.NotifTaskDao;
import centmp.depotmebel.core.json.request.CorporateContactRequest;
import centmp.depotmebel.core.json.request.CorporateUserRequest;
import centmp.depotmebel.core.json.request.CustomerRequest;
import centmp.depotmebel.core.json.response.CustomerResponse;
import centmp.depotmebel.core.model.AccountUpgrade;
import centmp.depotmebel.core.model.NotifTask;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.core.util.ConstantApp;
import centmp.depotmebel.core.util.WSCustomer;
import centmp.depotmebel.wsuser.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	private AccountUpgradeDao accntUpgradeDao;
	private NotifTaskDao notifTaskDao;

	@Override
	public BaseJsonResponse accountUpgradeNew(HashMap<String, Object> hm) {
		BaseJsonResponse jsonResponse = new BaseJsonResponse();
		String errorCode = "-1";
		String exceptionMsg = "";
		
		try {
			String token = (String) hm.get(ConstantApp.TOKEN);
			CustomerRequest reqparam = (CustomerRequest) hm.get(ConstantApp.REQUEST_PARAMS);
			String corptName = reqparam.getCorptName()!=null?reqparam.getCorptName().trim():"";
			String corptLegalName = reqparam.getCorptLegalName()!=null?reqparam.getCorptLegalName().trim():"";
			String corptCategory = reqparam.getCorptCategory()!=null?reqparam.getCorptCategory().trim():"";
			String corptNpwp = reqparam.getCorptNpwp()!=null?reqparam.getCorptNpwp().trim():"";
			List<CorporateContactRequest> corptContacts = reqparam.getCorptContacts();
			//List<CorporateFileRequest> corptFiles = reqparam.getCorptAttachments();
			List<CorporateUserRequest> corptUsers = reqparam.getCorptUsers();
			String store = reqparam.getStore()!=null?reqparam.getStore().trim():"";
			
			List<String> params = new ArrayList<>();
			params.add(token);
			params.add(corptName);
			params.add(corptLegalName);
			params.add(corptCategory);
			params.add(corptNpwp);
			params.add(store);
			if(corptContacts.size()<1)params.add("");
			if(corptUsers.size()<1)params.add("");
			if(!CommonUtil.checkMandatoryParameter(params)){
				errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10002");
				exceptionMsg = "Mandatory parameter can't be null or empty";
			}else{
				String json = new Gson().toJson(reqparam);
				System.out.println("reqparam: "+json);
				
				CustomerResponse customerresp = WSCustomer.findByToken(token);
				System.out.println("customerresp: "+new Gson().toJson(customerresp));
				if(customerresp!=null&&customerresp.getErrorCode()!=0){
					errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10006");
					exceptionMsg = customerresp.getTechnicalMsg();
				}else{
					String customerIdEncr = CipherUtil.decrypt(customerresp.getId());
					String customerId = customerIdEncr.replace(ConstantApp.CUSTOMER_ID_COMBINE, "");
				
					AccountUpgrade au = new AccountUpgrade();
					au.setCustomerId(customerId);
					au.setCustomerName(customerresp.getName());
					au.setCustomerEmail(customerresp.getEmail());
					au.setCustomerPhone(customerresp.getPhone());
					au.setJson(json);
					au.setIsDone("N");
					accntUpgradeDao = new AccountUpgradeDao(sessionFactory);
					accntUpgradeDao.saveOrUpdate(au);
					
					notifTaskDao = new NotifTaskDao(sessionFactory);
					List<NotifTask> notifTaskList = notifTaskDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("code", "US01"));
					if(notifTaskList.size()>0){
						NotifTask notiftask = notifTaskList.get(0);
						Integer numBefore = notiftask.getNum();
						
						Integer numUpdate = numBefore + 1;
						notiftask.setNum(numUpdate);
						notifTaskDao = new NotifTaskDao(sessionFactory);
						notifTaskDao.saveOrUpdate(notiftask);
					}
					
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

}
