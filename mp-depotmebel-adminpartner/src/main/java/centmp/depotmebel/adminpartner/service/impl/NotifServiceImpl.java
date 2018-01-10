package centmp.depotmebel.adminpartner.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bilmajdi.util.PropertyMessageUtil;

import centmp.depotmebel.adminpartner.service.NotifService;
import centmp.depotmebel.core.dao.NotifTaskDao;
import centmp.depotmebel.core.model.NotifTask;

@Service
public class NotifServiceImpl implements NotifService {
	
	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	private NotifTaskDao notifTaskDao; 
	
	@Override
	public List<NotifTask> listAll() {
		List<NotifTask> list = new ArrayList<>();
		
		try {
			notifTaskDao = new NotifTaskDao(sessionFactory);
			list = notifTaskDao.loadBy(Order.desc("num"), Restrictions.gt("num", Integer.valueOf(0)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public void productApprovalReduce() {
		try {
			String code = PropertyMessageUtil.getConfigProperties().getProperty("notif.product.create.code");
			notifTaskDao = new NotifTaskDao(sessionFactory);
			List<NotifTask> notifTaskList = notifTaskDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("code", code.trim()));
			if(notifTaskList.size()>0){
				NotifTask notiftask = notifTaskList.get(0);
				Integer numBefore = notiftask.getNum();
				
				Integer numUpdate = numBefore - 1;
				notiftask.setNum(numUpdate);
				notifTaskDao = new NotifTaskDao(sessionFactory);
				notifTaskDao.saveOrUpdate(notiftask);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void readyQcReduce() {
		try {
			notifTaskDao = new NotifTaskDao(sessionFactory);
			List<NotifTask> notifTaskList = notifTaskDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("code", "TX01"));
			if(notifTaskList.size()>0){
				NotifTask notiftask = notifTaskList.get(0);
				Integer numBefore = notiftask.getNum();
				
				Integer numUpdate = numBefore - 1;
				notiftask.setNum(numUpdate);
				notifTaskDao = new NotifTaskDao(sessionFactory);
				notifTaskDao.saveOrUpdate(notiftask);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
