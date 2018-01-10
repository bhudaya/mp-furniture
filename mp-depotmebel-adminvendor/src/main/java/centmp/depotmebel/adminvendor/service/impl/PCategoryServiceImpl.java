package centmp.depotmebel.adminvendor.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.time.FastDateFormat;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bilmajdi.util.CipherUtil;

import centmp.depotmebel.adminvendor.bean.PCategoryBean;
import centmp.depotmebel.adminvendor.service.PCategoryService;
import centmp.depotmebel.core.dao.PCategoryAttrDao;
import centmp.depotmebel.core.dao.PCategoryDao;
import centmp.depotmebel.core.dao.PCategorySpecDao;
import centmp.depotmebel.core.dao.PCategorySubDao;
import centmp.depotmebel.core.enums.STATUS;
import centmp.depotmebel.core.model.PCategory;
import centmp.depotmebel.core.model.PCategoryAttr;
import centmp.depotmebel.core.model.PCategorySpec;
import centmp.depotmebel.core.model.PCategorySub;

@Service
public class PCategoryServiceImpl implements PCategoryService {
	
	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	private PCategoryDao pcategoryDao;
	private PCategorySubDao pcategorySubDao;
	private PCategorySpecDao pcategorySpecDao;
	private PCategoryAttrDao pcategoryAttrDao;
	
	@Override
	public List<PCategoryBean> listByActive() {
		List<PCategoryBean> list = new ArrayList<>();
		
		try {
			pcategoryDao = new PCategoryDao(sessionFactory);
			List<PCategory> pcategoryList = pcategoryDao.loadBy(Order.asc("name"), Restrictions.eq("status", STATUS.ACTIVE));
			for(PCategory pcategory: pcategoryList) {
				PCategoryBean bean = new PCategoryBean();
				bean.setId(URLEncoder.encode(CipherUtil.encrypt(pcategory.getID().toString()),"UTF-8"));
				bean.setName(pcategory.getName());
				bean.setStatus(pcategory.getStatus().name());
				bean.setCreatedDate(FastDateFormat.getInstance("dd/MM/yyyy, HH:mm").format(pcategory.getCreatedDate()));
				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public List<String[]> subCategoryList(String pcategoryId) {
		List<String[]> list = new ArrayList<>();
		
		try {
			String iddecr = CipherUtil.decrypt(pcategoryId);
			pcategoryDao = new PCategoryDao(sessionFactory);
			List<PCategory> pcategoryList = pcategoryDao.loadBy(Order.asc("ID"), Restrictions.eq("ID", Long.valueOf(iddecr)));
			if(pcategoryList.size()>0){
				PCategory pcategory = pcategoryList.get(0);
				
				pcategorySubDao = new PCategorySubDao(sessionFactory);
				List<PCategorySub> pcategorySubList = pcategorySubDao.loadBy(Order.asc("name"), Restrictions.eq("pcategory", pcategory));
				for(PCategorySub pcategorySub: pcategorySubList){
					String[] str = new String[2];
					str[0] = URLEncoder.encode(CipherUtil.encrypt(pcategorySub.getID().toString()),"UTF-8");
					str[1] = pcategorySub.getName();
					list.add(str);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public List<String[]> specList(String pcategoryId) {
		List<String[]> list = new ArrayList<>();
		
		try {
			String iddecr = CipherUtil.decrypt(pcategoryId);
			pcategoryDao = new PCategoryDao(sessionFactory);
			List<PCategory> pcategoryList = pcategoryDao.loadBy(Order.asc("ID"), Restrictions.eq("ID", Long.valueOf(iddecr)));
			if(pcategoryList.size()>0){
				PCategory pcategory = pcategoryList.get(0);
				
				pcategorySpecDao = new PCategorySpecDao(sessionFactory);
				List<PCategorySpec> pcategorySubList = pcategorySpecDao.loadBy(Order.asc("label"), Restrictions.eq("pcategory", pcategory));
				for(PCategorySpec pcategorySpec: pcategorySubList){
					String[] str = new String[3];
					str[0] = URLEncoder.encode(CipherUtil.encrypt(pcategorySpec.getID().toString()),"UTF-8");
					str[1] = pcategorySpec.getLabel();
					str[2] = pcategorySpec.getID().toString();
					list.add(str);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public List<String[]> attrList(String pcategoryId) {
		List<String[]> list = new ArrayList<>();
		
		try {
			String iddecr = CipherUtil.decrypt(pcategoryId);
			pcategoryDao = new PCategoryDao(sessionFactory);
			List<PCategory> pcategoryList = pcategoryDao.loadBy(Order.asc("ID"), Restrictions.eq("ID", Long.valueOf(iddecr)));
			if(pcategoryList.size()>0){
				PCategory pcategory = pcategoryList.get(0);
				
				pcategoryAttrDao = new PCategoryAttrDao(sessionFactory);
				List<PCategoryAttr> pcategoryAttrList = pcategoryAttrDao.loadBy(Order.asc("label"), Restrictions.eq("pcategory", pcategory));
				for(PCategoryAttr pcategoryAttr: pcategoryAttrList){
					String[] str = new String[3];
					str[0] = URLEncoder.encode(CipherUtil.encrypt(pcategoryAttr.getID().toString()),"UTF-8");
					str[1] = pcategoryAttr.getLabel();
					str[2] = pcategoryAttr.getID().toString();
					list.add(str);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
}
