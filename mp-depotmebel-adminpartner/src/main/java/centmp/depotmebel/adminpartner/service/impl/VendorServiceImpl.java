package centmp.depotmebel.adminpartner.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.bilmajdi.util.BielUtil;
import com.bilmajdi.util.PropertyMessageUtil;

import centmp.depotmebel.adminpartner.bean.CommonBean;
import centmp.depotmebel.adminpartner.bean.UserBean;
import centmp.depotmebel.adminpartner.bean.VendorBean;
import centmp.depotmebel.adminpartner.bean.VendorListBean;
import centmp.depotmebel.adminpartner.bean.VendorUserBean;
import centmp.depotmebel.adminpartner.bean.form.VendorForm;
import centmp.depotmebel.adminpartner.service.CityService;
import centmp.depotmebel.adminpartner.service.UserService;
import centmp.depotmebel.adminpartner.service.VendorService;
import centmp.depotmebel.core.dao.VendorCoverageDao;
import centmp.depotmebel.core.dao.VendorCrdLimitDao;
import centmp.depotmebel.core.dao.VendorDao;
import centmp.depotmebel.core.dao.VendorDlverLimitDao;
import centmp.depotmebel.core.dao.VendorUserDao;
import centmp.depotmebel.core.enums.STATUS;
import centmp.depotmebel.core.model.City;
import centmp.depotmebel.core.model.Vendor;
import centmp.depotmebel.core.model.VendorCoverage;
import centmp.depotmebel.core.model.VendorCrdLimit;
import centmp.depotmebel.core.model.VendorDlverLimit;
import centmp.depotmebel.core.model.VendorUser;
import centmp.depotmebel.core.util.CommonUtil;

@Service
public class VendorServiceImpl implements VendorService {
	
	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	@Autowired
	private CityService cityService;
	@Autowired
	private UserService userService;
	
	private VendorDao vendorDao;
	private VendorCrdLimitDao vendCrdLimitDao;
	private VendorDlverLimitDao vendDlverLimitDao;
	private VendorCoverageDao vendCoverageDao;
	private VendorUserDao vendUserDao;
	
	@Override
	public VendorListBean listAll() {
		VendorListBean beanlist = new VendorListBean();
		
		Integer sumData = 0;
		List<VendorBean> list = new ArrayList<>();
		
		try {
			vendorDao =  new VendorDao(sessionFactory);
			List<Vendor> vendorList = vendorDao.loadAll(Order.asc("ID"));
			for (Vendor vendor : vendorList) {
				VendorBean bean = new VendorBean();
				bean.setId(vendor.getID().toString());
				bean.setName(vendor.getName());
				bean.setPicName(vendor.getPicName());
				bean.setAddress(vendor.getAddress());
				bean.setStatus(vendor.getStatus().name().toUpperCase());
				list.add(bean);
			}
			
			sumData = vendorList.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		beanlist.setSumData(sumData);
		beanlist.setVendorList(list);
		
		return beanlist;
	}
	
	@Override
	public CommonBean create(VendorForm form, UserBean userbean) {
		
		try {
			City city = cityService.findById(form.getCity());
			Calendar calnow = Calendar.getInstance(BielUtil.timeZoneJakarta());
			String creditLimitStr = form.getCreditLimit().replace(".", "");
			String bgId = PropertyMessageUtil.getConfigProperties().getProperty("bg.id");
			
			Vendor vendor = new Vendor();
			vendor.setName(form.getName());
			vendor.setPicName(form.getPicName());
			vendor.setPicPhone(form.getPicPhone());
			vendor.setAddress(form.getAddress());
			vendor.setCity(city);
			vendor.setPostalCode(form.getPostalCode());
			vendor.setCreditLimit(new BigDecimal(creditLimitStr));
			vendor.setDeliveryCapacity(Integer.parseInt(form.getDeliveryCapacity()));
			vendor.setBgId(bgId);
			vendor.setStatus(STATUS.ACTIVE);
			vendor.setCreatedDate(calnow.getTime());
			vendorDao = new VendorDao(sessionFactory);
			vendorDao.saveOrUpdate(vendor);
			
			VendorCrdLimit creditLimit = new VendorCrdLimit();
			creditLimit.setVendor(vendor);
			creditLimit.setBalance(new BigDecimal(creditLimitStr));
			creditLimit.setUpdatedDate(calnow.getTime());
			vendCrdLimitDao = new VendorCrdLimitDao(sessionFactory);
			vendCrdLimitDao.saveOrUpdate(creditLimit);
			
			VendorDlverLimit deliverLimit = new VendorDlverLimit();
			deliverLimit.setVendor(vendor);
			deliverLimit.setBalance(Integer.parseInt(form.getDeliveryCapacity()));
			deliverLimit.setUpdatedDate(calnow.getTime());
			vendDlverLimitDao = new VendorDlverLimitDao(sessionFactory);
			vendDlverLimitDao.saveOrUpdate(deliverLimit);
			
			List<String[]> coverageFormList = form.getCoverageList();
			for(String[] arr: coverageFormList) {
				VendorCoverage coverage = new VendorCoverage();
				coverage.setVendor(vendor);
				coverage.setCityId(arr[0]);
				coverage.setSlaTime(Integer.parseInt(arr[1]));
				coverage.setCreatedDate(calnow.getTime());
				vendCoverageDao = new VendorCoverageDao(sessionFactory);
				vendCoverageDao.saveOrUpdate(coverage);
			}
			
			List<String[]> userFormList = form.getUserList();
			HashMap<String, Object> hm = new HashMap<>();
			hm.put("userFormList", userFormList);
			hm.put("vendor", vendor);
			userService.insertNew(hm, userbean);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public VendorBean getById(String id) {
		VendorBean bean = new VendorBean();
		
		try {
			vendorDao = new VendorDao(sessionFactory);
			List<Vendor> vendorList = vendorDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(id)));
			
			if(vendorList.size()>0){
				Vendor vendor = vendorList.get(0);
				Criterion crVendor = Restrictions.eq("vendor", vendor);
				
				String creditLimitRemainIdr = "";
				vendCrdLimitDao = new VendorCrdLimitDao(sessionFactory);
				List<VendorCrdLimit> creditLmtList = vendCrdLimitDao.loadBy(Order.asc("ID"), 1, crVendor);
				if (creditLmtList.size()>0) {
					VendorCrdLimit creditLimit = creditLmtList.get(0);
					creditLimitRemainIdr = CommonUtil.currencyIDR(creditLimit.getBalance().doubleValue());
				}
				
				String dlverCapacityRemain = "";
				vendDlverLimitDao = new VendorDlverLimitDao(sessionFactory);
				List<VendorDlverLimit> deliverLmtList = vendDlverLimitDao.loadBy(Order.asc("ID"), 1, crVendor);
				if (deliverLmtList.size()>0) {
					VendorDlverLimit deliverLimit = deliverLmtList.get(0);
					dlverCapacityRemain = deliverLimit.getBalance().toString();
				}
				
				List<String[]> coverages = new ArrayList<>();
				vendCoverageDao = new VendorCoverageDao(sessionFactory);
				List<VendorCoverage> coverageList = vendCoverageDao.loadBy(Order.asc("ID"), 1, crVendor);
				for(VendorCoverage coverage: coverageList) {
					City city = cityService.findById(coverage.getCityId());
					
					String[] arr = new String[4];
					arr[0] = coverage.getID().toString();
					arr[1] = city.getName();
					arr[2] = coverage.getSlaTime().toString();
					arr[3] = city.getCode();
					coverages.add(arr);
				}
				
				List<VendorUserBean> users = new ArrayList<>();
				vendUserDao = new VendorUserDao(sessionFactory);
				List<VendorUser> vuserList = vendUserDao.loadBy(Order.asc("type"), crVendor);
				for(VendorUser vu: vuserList){
					VendorUserBean user = new VendorUserBean();
					user.setId(vu.getID().toString());
					user.setName(vu.getUser().getName());
					user.setEmail(vu.getUser().getEmail());
					user.setPhone(vu.getUser().getPhone());
					user.setTypeId(vu.getType().ordinal());
					user.setTypeName(vu.getType().value());
					users.add(user);
				}
				
				bean.setId(vendor.getID().toString());
				bean.setName(vendor.getName());
				bean.setPicName(vendor.getPicName());
				bean.setPicPhone(vendor.getPicPhone());
				bean.setAddress(vendor.getAddress());
				bean.setProvince(vendor.getCity()!=null?vendor.getCity().getProvince().getName():"-");
				bean.setCity(vendor.getCity()!=null?vendor.getCity().getName():"-");
				bean.setPostalCode(vendor.getPostalCode());
				bean.setStatus(vendor.getStatus().name());
				bean.setCreatedDate(FastDateFormat.getInstance("dd/MM/yyyy, HH:mm").format(vendor.getCreatedDate()));
				bean.setCreditLimitIdr(CommonUtil.currencyIDR(vendor.getCreditLimit().doubleValue()));
				bean.setCreditLimitRemainIdr(creditLimitRemainIdr);
				bean.setDeliveryCapacity(vendor.getDeliveryCapacity().toString());
				bean.setDeliveryCapacityRemain(dlverCapacityRemain);
				bean.setCoverages(coverages);
				bean.setUsers(users);
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean;
	}
	
	@Override
	public VendorListBean listJson() {
		VendorListBean bean = new VendorListBean();
		
		try {
			List<VendorBean> beanlist = new ArrayList<>();
			
			Criterion[] cr = new Criterion[1];
			cr[0] = Restrictions.eq("status", STATUS.ACTIVE);
			vendorDao = new VendorDao(sessionFactory);
			List<Vendor> vendorList = vendorDao.loadBy(Order.asc("ID"), cr);
			for(Vendor v: vendorList){
				
				VendorBean vendor = new VendorBean();
				vendor.setId(v.getID().toString());
				vendor.setName(v.getName());
				beanlist.add(vendor);
			}
			
			bean.setVendorList(beanlist);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean;
	}

}
