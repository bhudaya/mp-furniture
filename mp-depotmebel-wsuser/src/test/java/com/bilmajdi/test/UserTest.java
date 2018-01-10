package com.bilmajdi.test;

import java.util.Calendar;

import org.apache.commons.lang.time.FastDateFormat;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bilmajdi.json.response.BaseJsonResponse;
import com.bilmajdi.util.CipherUtil;
import com.google.gson.Gson;

import centmp.depotmebel.core.json.request.UserRequest;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.wsuser.service.UserService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"classpath:spring/spring-*.xml"
})
public class UserTest {
	
	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	@Autowired
	private UserService userService;
	
	
	@Test
	public void run_() throws Exception {
		System.out.println();
		System.out.println(this.getClass().getName()+" - run_ - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		insertNew();
		
		System.out.println();
	}
	
	public void insertNew() throws Exception{
		Calendar calnow = Calendar.getInstance();
		String datenow = FastDateFormat.getInstance("yyyyMMdd").format(calnow.getTime());
		String name = "Makhsus";
		String combine = datenow+"_"+name;
		String createdBy = CipherUtil.encrypt(combine);
		
		try {
			UserRequest req = new UserRequest();
			req.setName("Nabil M");
			req.setEmail("makhsus.bilmajdi@outlook.com");
			req.setPhone("085781141187");
			req.setCreatedBy(createdBy);
			System.out.println("req: "+new Gson().toJson(req));
			
			BaseJsonResponse resp = userService.insertNew(req);
			System.out.println("req: "+new Gson().toJson(resp));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
