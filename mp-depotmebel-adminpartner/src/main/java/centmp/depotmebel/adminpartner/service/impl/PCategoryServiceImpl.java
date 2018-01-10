package centmp.depotmebel.adminpartner.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.time.FastDateFormat;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bilmajdi.util.BielUtil;
import com.bilmajdi.util.CipherUtil;

import centmp.depotmebel.adminpartner.bean.PCategoryBean;
import centmp.depotmebel.adminpartner.bean.form.PCategoryForm;
import centmp.depotmebel.adminpartner.service.PCategoryService;
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
	public List<PCategoryBean> listAll() {
		List<PCategoryBean> list = new ArrayList<>();
		
		try {
			pcategoryDao = new PCategoryDao(sessionFactory);
			List<PCategory> pcategoryList = pcategoryDao.loadAll(Order.asc("ID"));
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
	public List<PCategoryBean> listByActive() {
		List<PCategoryBean> list = new ArrayList<>();
		
		try {
			pcategoryDao = new PCategoryDao(sessionFactory);
			List<PCategory> pcategoryList = pcategoryDao.loadBy(Order.asc("ID"), Restrictions.eq("status", STATUS.ACTIVE));
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
	public PCategoryForm detail(String id) {
		PCategoryForm form = new PCategoryForm();
		
		try {
			String decrp = CipherUtil.decrypt(id);
			pcategoryDao = new PCategoryDao(sessionFactory);
			List<PCategory> pcategoryList = pcategoryDao.loadBy(Order.asc("ID"), Restrictions.eq("ID", Long.valueOf(decrp)));
			if(pcategoryList.size()>0){
				PCategory pcategory = pcategoryList.get(0);
				
				Criterion crCategory = Restrictions.eq("pcategory", pcategory);
				List<String[]> subList = new ArrayList<>();
				List<String[]> specList = new ArrayList<>();
				List<String[]> attrList = new ArrayList<>();
				
				pcategorySubDao = new PCategorySubDao(sessionFactory);
				List<PCategorySub> pcategorySubList = pcategorySubDao.loadBy(Order.asc("ID"), crCategory);
				for(PCategorySub obj: pcategorySubList){
					String[] arr = new String[2];
					arr[0] = URLEncoder.encode(CipherUtil.encrypt(obj.getID().toString()), "UTF-8");
					arr[1] = obj.getName();
					subList.add(arr);
				}
				
				pcategorySpecDao = new PCategorySpecDao(sessionFactory);
				List<PCategorySpec> pcategorySpecList = pcategorySpecDao.loadBy(Order.asc("ID"), crCategory);
				for(PCategorySpec obj: pcategorySpecList){
					String[] arr = new String[2];
					arr[0] = URLEncoder.encode(CipherUtil.encrypt(obj.getID().toString()), "UTF-8");
					arr[1] = obj.getLabel();
					specList.add(arr);
				}
				
				pcategoryAttrDao = new PCategoryAttrDao(sessionFactory);
				List<PCategoryAttr> pcategoryAttrList = pcategoryAttrDao.loadBy(Order.asc("ID"), crCategory);
				for(PCategoryAttr obj: pcategoryAttrList){
					String[] arr = new String[2];
					arr[0] = URLEncoder.encode(CipherUtil.encrypt(obj.getID().toString()), "UTF-8");
					arr[1] = obj.getLabel();
					attrList.add(arr);
				}
				
				form.setName(pcategory.getName());
				form.setStatus(pcategory.getStatus().name());
				form.setCreatedDateStr(FastDateFormat.getInstance("dd/MM/yyyy, HH:mm").format(pcategory.getCreatedDate()));
				form.setSubList(subList);
				form.setSpecList(specList);
				form.setAttrList(attrList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return form;
	}
	
	@Override
	public PCategoryBean create(PCategoryForm form) {
		
		try {
			Calendar calnow = Calendar.getInstance(BielUtil.timeZoneJakarta());
			
			PCategory pcategory = new PCategory();
			pcategory.setName(form.getName());
			pcategory.setCreatedDate(calnow.getTime());
			pcategory.setStatus(STATUS.ACTIVE);
			pcategoryDao = new PCategoryDao(sessionFactory);
			pcategoryDao.saveOrUpdate(pcategory);
			
			for(String[] str: form.getSubList()){
				if(str[1]!=null&&!str[1].trim().isEmpty()){
					PCategorySub sub = new PCategorySub();
					sub.setPcategory(pcategory);
					sub.setName(str[1].trim());
					sub.setCreatedDate(calnow.getTime());
					pcategorySubDao = new PCategorySubDao(sessionFactory);
					pcategorySubDao.saveOrUpdate(sub);
				}
			}
			
			for(String[] str: form.getSpecList()){
				if(str[1]!=null&&!str[1].trim().isEmpty()){
					PCategorySpec obj = new PCategorySpec();
					obj.setPcategory(pcategory);
					obj.setLabel(str[1].trim());
					obj.setCreatedDate(calnow.getTime());
					pcategorySpecDao = new PCategorySpecDao(sessionFactory);
					pcategorySpecDao.saveOrUpdate(obj);
				}
			}
			
			for(String[] str: form.getAttrList()){
				if(str[1]!=null&&!str[1].trim().isEmpty()){
					PCategoryAttr obj = new PCategoryAttr();
					obj.setPcategory(pcategory);
					obj.setLabel(str[1].trim());
					obj.setCreatedDate(calnow.getTime());
					pcategoryAttrDao = new PCategoryAttrDao(sessionFactory);
					pcategoryAttrDao.saveOrUpdate(obj);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
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
				List<PCategorySub> pcategorySubList = pcategorySubDao.loadBy(Order.asc("ID"), Restrictions.eq("pcategory", pcategory));
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
				List<PCategorySpec> pcategorySubList = pcategorySpecDao.loadBy(Order.asc("ID"), Restrictions.eq("pcategory", pcategory));
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
				List<PCategoryAttr> pcategoryAttrList = pcategoryAttrDao.loadBy(Order.asc("ID"), Restrictions.eq("pcategory", pcategory));
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
