package centmp.depotmebel.wsproduct.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bilmajdi.util.CipherUtil;
import com.bilmajdi.util.PropertyMessageUtil;

import centmp.depotmebel.core.dao.PCategoryDao;
import centmp.depotmebel.core.dao.ProductDao;
import centmp.depotmebel.core.enums.STATUS;
import centmp.depotmebel.core.json.request.PCategoryRequest;
import centmp.depotmebel.core.json.response.PCategoryListResponse;
import centmp.depotmebel.core.json.response.PCategoryResponse;
import centmp.depotmebel.core.model.PCategory;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.core.util.ConstantApp;
import centmp.depotmebel.wsproduct.service.PCategoryService;

@Service
public class PCategoryServiceImpl implements PCategoryService {

	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	private PCategoryDao pcategoryDao;
	private ProductDao productDao;
	
	
	@Override
	public PCategoryListResponse listAll(HashMap<String, Object> hm) {
		PCategoryListResponse jsonResponse = new PCategoryListResponse();
		String errorCode = "-1";
		String exceptionMsg = "";
		
		try {
			PCategoryRequest reqparam = (PCategoryRequest) hm.get(ConstantApp.REQUEST_PARAMS);
			String source = reqparam.getSource()!=null?reqparam.getSource().trim():"";
			String sourceDesc = reqparam.getSourceDesc()!=null?reqparam.getSourceDesc().trim():"";
			
			List<String> params = new ArrayList<>();
			params.add(source);
			params.add(sourceDesc);
			if(!CommonUtil.checkMandatoryParameter(params)){
				errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10002");
				exceptionMsg = "Mandatory parameter can't be null or empty";
			}else{
				System.out.println("Source: "+source+"; Description: "+sourceDesc);
				boolean sourceValidate = CommonUtil.sourceValidate(source);
				if(!sourceValidate){
					errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10004");
				}else{
					List<PCategoryResponse> pcategoryRespList = new ArrayList<>();
					Criterion cr1 = Restrictions.eq("status", STATUS.ACTIVE);
					
					pcategoryDao = new PCategoryDao(sessionFactory);
					List<PCategory> pcategoryList = pcategoryDao.loadBy(Order.asc("name"), cr1);
					for(PCategory pcategory: pcategoryList){
						StringBuffer buffer = new StringBuffer();
						buffer.append(ConstantApp.PCATEGORY_ID_COMBINE);
						buffer.append(pcategory.getID().toString());
						String id = CipherUtil.encrypt(buffer.toString());
						
						PCategoryResponse resp = new PCategoryResponse();
						resp.setId(id);
						resp.setName(pcategory.getName());
						pcategoryRespList.add(resp);
					}
					
					jsonResponse.setPcategoryList(pcategoryRespList);
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
	
	@Override
	public PCategoryListResponse listWithSumProd(HashMap<String, Object> hm) {
		PCategoryListResponse jsonResponse = new PCategoryListResponse();
		String errorCode = "-1";
		String exceptionMsg = "";
		
		try {
			PCategoryRequest reqparam = (PCategoryRequest) hm.get(ConstantApp.REQUEST_PARAMS);
			String source = reqparam.getSource()!=null?reqparam.getSource().trim():"";
			String sourceDesc = reqparam.getSourceDesc()!=null?reqparam.getSourceDesc().trim():"";
			
			List<String> params = new ArrayList<>();
			params.add(source);
			params.add(sourceDesc);
			if(!CommonUtil.checkMandatoryParameter(params)){
				errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10002");
				exceptionMsg = "Mandatory parameter can't be null or empty";
			}else{
				System.out.println("Source: "+source+"; Description: "+sourceDesc);
				boolean sourceValidate = CommonUtil.sourceValidate(source);
				if(!sourceValidate){
					errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10004");
				}else{
					List<PCategoryResponse> pcategoryRespList = new ArrayList<>();
					Criterion cr1 = Restrictions.eq("status", STATUS.ACTIVE);
					
					pcategoryDao = new PCategoryDao(sessionFactory);
					List<PCategory> pcategoryList = pcategoryDao.loadBy(Order.asc("name"), cr1);
					for(PCategory pcategory: pcategoryList){
						StringBuffer buffer = new StringBuffer();
						buffer.append(ConstantApp.PCATEGORY_ID_COMBINE);
						buffer.append(pcategory.getID().toString());
						String id = CipherUtil.encrypt(buffer.toString());
						
						Criterion cr2 = Restrictions.eq("pcategory", pcategory);
						productDao = new ProductDao(sessionFactory);
						Integer sumProd = productDao.rowCount(cr1, cr2);
						
						PCategoryResponse resp = new PCategoryResponse();
						resp.setId(id);
						resp.setName(pcategory.getName());
						resp.setSumProduct(sumProd.toString());
						pcategoryRespList.add(resp);
					}
					
					jsonResponse.setPcategoryList(pcategoryRespList);
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
