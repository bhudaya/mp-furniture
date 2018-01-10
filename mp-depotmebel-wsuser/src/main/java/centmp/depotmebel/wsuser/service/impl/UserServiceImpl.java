package centmp.depotmebel.wsuser.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.time.FastDateFormat;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bilmajdi.json.response.BaseJsonResponse;
import com.bilmajdi.util.CipherUtil;
import com.bilmajdi.util.PropertyMessageUtil;
import com.bilmajdi.util.Sha;

import centmp.depotmebel.core.dao.UserDao;
import centmp.depotmebel.core.enums.STATUS;
import centmp.depotmebel.core.enums.USER_TYPE;
import centmp.depotmebel.core.json.request.UserRequest;
import centmp.depotmebel.core.model.User;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.wsuser.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	private UserDao userDao;
	

	@Override
	public BaseJsonResponse insertNew(UserRequest reqparam) {
		BaseJsonResponse jsonResponse = new BaseJsonResponse();
		String errorCode = "-1";
		String exceptionMsg = "";
		
		try {
			String name = reqparam.getName()!=null?reqparam.getName().trim():"";
			String email = reqparam.getEmail()!=null?reqparam.getEmail().trim():"";
			String phone = reqparam.getPhone()!=null?reqparam.getPhone().trim():"";
			String createdBy = reqparam.getCreatedBy()!=null?reqparam.getCreatedBy().trim():"";
			
			List<String> params = new ArrayList<>();
			params.add(name);
			params.add(email);
			params.add(phone);
			params.add(createdBy);
			if(!CommonUtil.checkMandatoryParameter(params)){
				errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10002");
				exceptionMsg = "Mandatory parameter can't be null or empty";
			}else{
				String decrypt = CipherUtil.decrypt(createdBy);
				String[] split = decrypt.split("_");
				String datenow_ = split[0];
				String createdByName = split[1];
				
				Calendar calnow = Calendar.getInstance();
				String datenow = FastDateFormat.getInstance("yyyyMMdd").format(calnow.getTime());
				if(!datenow_.equalsIgnoreCase(datenow)){
					errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10001");
					exceptionMsg = "Field 'CreateBy' Invalid";
					System.out.println("DISINI: "+exceptionMsg);
				}else{
					String defaultPass = PropertyMessageUtil.getConfigProperties().getProperty("user.password.default");
					String password = Sha.hash256(email+defaultPass);
					
					User user = new User();
					user.setName(name);
					user.setPhone(phone);
					user.setEmail(email);
					user.setPassword(password);
					user.setRole(USER_TYPE.PARTNER_ADMIN);
					user.setCreatedTime(calnow.getTime());
					user.setCreatedBy(createdByName);
					user.setStatus(STATUS.ACTIVE);
					userDao = new UserDao(sessionFactory);
					userDao.saveOrUpdate(user);
					
					errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.0");
				}
			}
		} catch (Exception e) {
			errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10001");
			exceptionMsg = e.getLocalizedMessage();
			e.printStackTrace();
		}
		
		jsonResponse.setErrorCode(Integer.parseInt(errorCode));
		jsonResponse.setErrorMsg(PropertyMessageUtil.getMessageProperties().getProperty("code.error.msg."+errorCode));
		jsonResponse.setTechnicalMsg(exceptionMsg.isEmpty()?PropertyMessageUtil.getMessageProperties().getProperty("code.techn.msg."+errorCode):exceptionMsg);
	
		return jsonResponse;
	}

}
