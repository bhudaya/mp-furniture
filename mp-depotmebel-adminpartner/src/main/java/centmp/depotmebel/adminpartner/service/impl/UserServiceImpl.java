package centmp.depotmebel.adminpartner.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bilmajdi.util.BielUtil;
import com.bilmajdi.util.PropertyMessageUtil;
import com.bilmajdi.util.Sha;
import com.google.gson.Gson;

import centmp.depotmebel.adminpartner.bean.CommonBean;
import centmp.depotmebel.adminpartner.bean.UserBean;
import centmp.depotmebel.adminpartner.bean.form.CommonForm;
import centmp.depotmebel.adminpartner.service.UserService;
import centmp.depotmebel.adminpartner.util.SessionBeanUtil;
import centmp.depotmebel.core.dao.UserDao;
import centmp.depotmebel.core.dao.VendorUserDao;
import centmp.depotmebel.core.enums.STATUS;
import centmp.depotmebel.core.enums.USER_TYPE;
import centmp.depotmebel.core.enums.VENDOR_USER_TYPE;
import centmp.depotmebel.core.model.User;
import centmp.depotmebel.core.model.Vendor;
import centmp.depotmebel.core.model.VendorUser;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	private UserDao userDao;
	private VendorUserDao vendUserDao;
	
	private Integer errCode = 0;
	private String errMsg = "";
	
	@Override
	public CommonBean login(HttpServletRequest request, CommonForm form) {
		CommonBean bean = new CommonBean();
		errCode = 0;
		errMsg = "";
		UserBean userbean = null;
		
		try {
			Criterion cr1 = Restrictions.eq("email", form.getName());
			Criterion cr2 = Restrictions.eq("phone", form.getName());
			Criterion cr3 = Restrictions.or(cr1, cr2);
			Criterion cr4 = Restrictions.eq("role", USER_TYPE.PARTNER_ADMIN);
			
			userDao = new UserDao(sessionFactory);
			List<User> userList = userDao.loadBy(Order.asc("ID"), cr3, cr4);
			System.out.println("userList: "+userList.size());
			
			if(userList.size()<1){
				errCode = 12;errMsg = "[12] Username not found";
			}else{
				User user = userList.get(0);
				String pass_ = user.getPassword();
				String combine = user.getEmail()+form.getPassword();
				String hash_ = Sha.hash256(combine);
				
				if(!pass_.equalsIgnoreCase(hash_)){
					errCode = 13;errMsg = "[13] Invalid password";
				}else{
					userbean = new UserBean();
					userbean.setName(user.getName());
					userbean.setUsername(form.getName());
					userbean.setEmail(user.getEmail());
					userbean.setPhone(user.getPhone());
					userbean.setRoleId(Integer.toString(user.getRole().ordinal()));
					userbean.setRoleName(user.getRole().value());
					//userbean.setVendors(vendors);
					
					System.out.println("userbean_: "+new Gson().toJson(userbean));
					
					SessionBeanUtil sessionutil = new SessionBeanUtil(request);
					HashMap<String, Object> hmsess = new HashMap<>();
					hmsess.put(sessionutil.errorCode, errCode.toString());
					hmsess.put(sessionutil.errorMsg, errMsg);
					hmsess.put(sessionutil.username, form.getName());
					hmsess.put(sessionutil.userObj, userbean);
					sessionutil.setSession(hmsess);
					
					System.out.println("new gson: "+new Gson().toJson(hmsess));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			errCode = 11;errMsg = "[11] "+e.getMessage();
		}
		
		bean.setErrCode(errCode);
		bean.setErrMsg(errMsg);
		
		return bean;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public CommonBean insertNew(HashMap<String, Object> hm, UserBean userbean) {
		try {
			List<String[]> userFormList = (List<String[]>) hm.get("userFormList");
			Vendor vendor = (Vendor) hm.get("vendor");
			
			Calendar calnow = Calendar.getInstance(BielUtil.timeZoneJakarta());
			for(String[] vuserstr: userFormList) {
				String name = vuserstr[0];
				String email = vuserstr[1];
				String phone = vuserstr[2];
				
				Criterion cr1 = Restrictions.eq("email", email);
				Criterion cr2 = Restrictions.eq("phone", phone);
				Criterion cr3 = Restrictions.or(cr1, cr2);
				userDao = new UserDao(sessionFactory);
				Integer count = userDao.rowCount(cr3);
				if(count<1){
					String passDefault = PropertyMessageUtil.getConfigProperties().getProperty("user.password.default");
					String passHash = Sha.hash256(email+passDefault);
					
					USER_TYPE role = USER_TYPE.VENDOR_ADMIN;
					Integer index = Integer.parseInt((String) vuserstr[3])+2;
					String roleIndex = index.toString();
					if(roleIndex!=null && !roleIndex.isEmpty()){
						role = USER_TYPE.setByOrdinal(Integer.parseInt(roleIndex));
					}
					
					User user = new User();
					user.setName(name.trim());
					user.setEmail(email.trim());
					user.setPhone(phone.trim());
					user.setPassword(passHash);
					user.setCreatedTime(calnow.getTime());
					user.setRole(role);
					user.setStatus(STATUS.ACTIVE);
					user.setCreatedBy(userbean.getId());
					userDao = new UserDao(sessionFactory);
					userDao.saveOrUpdate(user);
					
					VendorUser vuser = new VendorUser();
					vuser.setVendor(vendor);
					vuser.setUser(user);
					vuser.setType(VENDOR_USER_TYPE.setByOrdinal(Integer.parseInt(vuserstr[3])));
					vuser.setCreatedDate(calnow.getTime());
					vendUserDao = new VendorUserDao(sessionFactory);
					vendUserDao.saveOrUpdate(vuser);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
