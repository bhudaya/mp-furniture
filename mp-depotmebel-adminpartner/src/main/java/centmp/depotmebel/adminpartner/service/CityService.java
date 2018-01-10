package centmp.depotmebel.adminpartner.service;

import java.util.List;

import centmp.depotmebel.core.model.City;

public interface CityService {
	
	City findById(String cityId);
	List<String[]> listByProvinceId(String provincId, String citySelected);
	
}
