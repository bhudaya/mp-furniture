package centmp.depotmebel.adminpartner.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bilmajdi.util.CipherUtil;
import com.google.gson.Gson;

import centmp.depotmebel.adminpartner.bean.CustomerBean;
import centmp.depotmebel.adminpartner.service.CustomerService;
import centmp.depotmebel.core.dao.AccountUpgradeDao;
import centmp.depotmebel.core.json.request.CustomerRequest;
import centmp.depotmebel.core.model.AccountUpgrade;
import centmp.depotmebel.core.util.ConstantApp;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	private AccountUpgradeDao accntUpgradeDao;

	@Override
	public List<CustomerBean> requestApprovalUpgrades() {
		List<CustomerBean> list = new ArrayList<>();
		
		try {
			accntUpgradeDao = new AccountUpgradeDao(sessionFactory);
			List<AccountUpgrade> auList = accntUpgradeDao.loadBy(Order.asc("ID"), Restrictions.eq("isDone", "N"));
			for(AccountUpgrade accountUpgrade: auList) {
				CustomerRequest request = new Gson().fromJson(accountUpgrade.getJson(), CustomerRequest.class);
				String id = CipherUtil.encrypt(ConstantApp.CUSTOMER_ID_COMBINE+accountUpgrade.getID().toString());
				
				CustomerBean bean = new CustomerBean();
				bean.setId(URLEncoder.encode(id, "UTF-8"));
				bean.setName(accountUpgrade.getCustomerName());
				bean.setCorptName(request.getCorptName());
				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public CustomerBean detailApprovalUpgrades(String idencrypt) {
		CustomerBean bean = new CustomerBean();
		
		try {
			String decrypt = CipherUtil.decrypt(idencrypt);
			String replace = decrypt.replace(ConstantApp.CUSTOMER_ID_COMBINE, "");
			Long id = Long.valueOf(replace);
			
			accntUpgradeDao = new AccountUpgradeDao(sessionFactory);
			List<AccountUpgrade> auList = accntUpgradeDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", id));
			if(auList.size()>0){
				AccountUpgrade au = auList.get(0);
				CustomerRequest customerreq = new Gson().fromJson(au.getJson(), CustomerRequest.class);
				bean.setId(au.getCustomerId());
				bean.setName(au.getCustomerName());
				bean.setEmail(au.getCustomerEmail());
				bean.setPhone(au.getCustomerPhone());
				bean.setCorptName(customerreq.getCorptName());
				bean.setCorptLegalName(customerreq.getCorptLegalName());
				bean.setCorptCategory(customerreq.getCorptCategory());
				bean.setCorptNpwp(customerreq.getCorptNpwp());
				bean.setCorptContacts(customerreq.getCorptContacts());
				bean.setCorptAttachments(customerreq.getCorptAttachments());
				bean.setCorptUsers(customerreq.getCorptUsers());
				bean.setStore(customerreq.getStore());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean;
	}

}
