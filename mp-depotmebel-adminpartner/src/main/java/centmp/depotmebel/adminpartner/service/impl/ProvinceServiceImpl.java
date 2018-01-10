package centmp.depotmebel.adminpartner.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import centmp.depotmebel.adminpartner.service.ProvinceService;
import centmp.depotmebel.core.dao.ProvinceDao;
import centmp.depotmebel.core.model.Province;


@Service(value="provinceService")
public class ProvinceServiceImpl implements ProvinceService {

	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	private ProvinceDao provinceDao;
	
	@Override
	public List<String[]> listForOption(String provCode) {
		List<String[]> list = new ArrayList<>();
		
		try {
			provinceDao = new ProvinceDao(sessionFactory);
			List<Province> provList = provinceDao.loadBy(Order.asc("name"), Restrictions.eq("isActive", "Y"));
			
			for(Province prov: provList){
				String code = prov.getCode();
				String selected = "";
				if(code.equalsIgnoreCase(provCode)){
					selected = "selected";
				}
				
				String[] str = new String[3];
				str[0] = code;
				str[1] = prov.getName();
				str[2] = selected;
				
				list.add(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
