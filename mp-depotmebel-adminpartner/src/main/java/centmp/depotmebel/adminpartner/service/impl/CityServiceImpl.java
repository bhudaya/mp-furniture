package centmp.depotmebel.adminpartner.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import centmp.depotmebel.adminpartner.service.CityService;
import centmp.depotmebel.core.dao.CityDao;
import centmp.depotmebel.core.dao.ProvinceDao;
import centmp.depotmebel.core.model.City;
import centmp.depotmebel.core.model.Province;

@Service
public class CityServiceImpl implements CityService {
	
	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	private ProvinceDao provinceDao;
	private CityDao cityDao;
	
	@Override
	public City findById(String cityId) {
		City city = null;
		
		try {
			Criterion cr1 = Restrictions.eq("code", cityId);
			
			cityDao = new CityDao(sessionFactory);
			List<City> cityList = cityDao.loadBy(Order.asc("name"), cr1);
			if(cityList.size()>0){
				City city_ = cityList.get(0);
				if(city_.getIsActive().equalsIgnoreCase("Y")){
					city = city_;
				}else{
					System.out.println("city not active");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return city;
	}

	@Override
	public List<String[]> listByProvinceId(String provincId, String citySelected) {
		List<String[]> list = new ArrayList<>();
		
		try {
			provinceDao = new ProvinceDao(sessionFactory);
			List<Province> provList = provinceDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("code", provincId));
			
			if(provList.size()>0){
				Province prov = provList.get(0);
				
				Criterion cr1 = Restrictions.eq("province", prov);
				Criterion cr2 = Restrictions.eq("isActive", "Y");
				
				cityDao = new CityDao(sessionFactory);
				List<City> cityList = cityDao.loadBy(Order.asc("name"), cr1, cr2);
				
				for(City city: cityList){
					String selected = "";
					if(city.getCode().equalsIgnoreCase(citySelected)){
						selected = "selected";
					}
					
					String[] str = new String[3];
					str[0] = city.getCode();
					str[1] = city.getName();
					str[2] = selected;
					
					list.add(str);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
}
