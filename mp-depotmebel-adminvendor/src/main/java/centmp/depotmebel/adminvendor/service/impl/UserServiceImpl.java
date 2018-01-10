package centmp.depotmebel.adminvendor.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bilmajdi.util.Sha;
import com.google.gson.Gson;

import centmp.depotmebel.adminvendor.bean.CommonBean;
import centmp.depotmebel.adminvendor.bean.UserBean;
import centmp.depotmebel.adminvendor.bean.form.CommonForm;
import centmp.depotmebel.adminvendor.service.UserService;
import centmp.depotmebel.adminvendor.util.SessionBeanUtil;
import centmp.depotmebel.core.dao.UserDao;
import centmp.depotmebel.core.dao.VendorUserDao;
import centmp.depotmebel.core.enums.USER_TYPE;
import centmp.depotmebel.core.model.User;
import centmp.depotmebel.core.model.VendorUser;

@Service(value="userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	private UserDao userDao;
	private VendorUserDao vendorUserDao;
	
	private Integer errCode = 0;
	private String errMsg = "";

	@Override
	public CommonBean login(HttpServletRequest request, CommonForm form) {
		CommonBean bean = new CommonBean();
		errCode = 0;
		errMsg = "";
		UserBean userbean = null;
		
		try {
			Disjunction disjnc1 = Restrictions.disjunction();
			disjnc1.add(Restrictions.eq("email", form.getName()));
			disjnc1.add(Restrictions.eq("phone", form.getName()));
			
			Disjunction disjnc2 = Restrictions.disjunction();
			disjnc2.add(Restrictions.eq("role", USER_TYPE.VENDOR_ADMIN));
			disjnc2.add(Restrictions.eq("role", USER_TYPE.VENDOR_OPERATOR));
			disjnc2.add(Restrictions.eq("role", USER_TYPE.VENDOR_COURIER));
			
			userDao = new UserDao(sessionFactory);
			List<User> userList = userDao.loadBy(Order.asc("ID"), disjnc1, disjnc2);
			System.out.println("userList: "+userList.size());
			
			if(userList.size()<1){
				errCode = 12;errMsg = "Username not found";
			}else{
				User user = userList.get(0);
				String pass_ = user.getPassword();
				String hash_ = Sha.hash256(user.getEmail()+form.getPassword());
				
				System.out.println("pass: "+user.getPassword());
				System.out.println("hash: "+hash_);
				
				if(!pass_.equalsIgnoreCase(hash_)){
					errCode = 13;errMsg = "Invalid password";
				}else{
					System.out.println("User ["+user.getID()+"]: "+user.getName());
					
					String[] vendors = new String[0];
					vendorUserDao = new VendorUserDao(sessionFactory);
					List<VendorUser> vuserList = vendorUserDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("user", user));
					if(vuserList.size()>0){
						VendorUser  vendoruser = vuserList.get(0);
						
						vendors = new String[4];
						vendors[0] = vendoruser.getVendor().getID().toString();//VendorId
						vendors[1] = vendoruser.getVendor().getName();//VendorName
						vendors[2] = vendoruser.getID().toString();//VendorUserId
						vendors[3] = Integer.toString(vendoruser.getType().ordinal());//VendorUserRole
					}
					
					
					userbean = new UserBean();
					userbean.setId(user.getID().toString());
					userbean.setName(user.getName());
					userbean.setUsername(form.getName());
					userbean.setEmail(user.getEmail());
					userbean.setPhone(user.getPhone());
					userbean.setRoleId(Integer.toString(user.getRole().ordinal()));
					userbean.setRoleName(user.getRole().value());
					userbean.setVendors(vendors);
					
					System.out.println("userbean_: "+new Gson().toJson(userbean));
					
					SessionBeanUtil sessionutil = new SessionBeanUtil(request);
					HashMap<String, Object> hmsess = new HashMap<>();
					hmsess.put(sessionutil.errorCode, errCode.toString());
					hmsess.put(sessionutil.errorMsg, errMsg);
					hmsess.put(sessionutil.username, form.getName());
					hmsess.put(sessionutil.userObj, userbean);
					sessionutil.setSession(hmsess);
					
					System.out.println("new gson: "+new Gson().toJson(hmsess));
					System.out.println();
				}
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
			errCode = 11;errMsg = e.getMessage();
		}
		
		bean.setErrCode(errCode);
		bean.setErrMsg(errMsg);
		
		return bean;
	}

}
