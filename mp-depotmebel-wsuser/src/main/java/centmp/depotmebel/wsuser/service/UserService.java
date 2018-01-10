package centmp.depotmebel.wsuser.service;

import com.bilmajdi.json.response.BaseJsonResponse;

import centmp.depotmebel.core.json.request.UserRequest;

public interface UserService {

	BaseJsonResponse insertNew(UserRequest requestParams);
	
}
