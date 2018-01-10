package centmp.depotmebel.wsproduct.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bilmajdi.util.CipherUtil;
import com.bilmajdi.util.PropertyMessageUtil;

import centmp.depotmebel.core.dao.ProductAttrDao;
import centmp.depotmebel.core.dao.ProductDao;
import centmp.depotmebel.core.dao.ProductSkuDao;
import centmp.depotmebel.core.dao.ProductSpecDao;
import centmp.depotmebel.core.enums.STATUS;
import centmp.depotmebel.core.json.request.ProductRequest;
import centmp.depotmebel.core.json.response.ProductAtrrResponse;
import centmp.depotmebel.core.json.response.ProductListResponse;
import centmp.depotmebel.core.json.response.ProductResponse;
import centmp.depotmebel.core.json.response.ProductSpecResponse;
import centmp.depotmebel.core.model.Product;
import centmp.depotmebel.core.model.ProductAttr;
import centmp.depotmebel.core.model.ProductSku;
import centmp.depotmebel.core.model.ProductSpec;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.core.util.ConstantApp;
import centmp.depotmebel.wsproduct.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	private ProductDao productDao;
	private ProductSpecDao productSpecDao;
	private ProductAttrDao productAttrDao;
	private ProductSkuDao productSkuDao;
	
	@Override
	public ProductListResponse catalog(HashMap<String, Object> hm) {
		ProductListResponse jsonResponse = new ProductListResponse();
		String errorCode = "-1";
		String exceptionMsg = "";
		
		try {
			ProductRequest reqparam = (ProductRequest) hm.get(ConstantApp.REQUEST_PARAMS);
			String source = reqparam.getSource()!=null?reqparam.getSource().trim():"";
			String sourceDesc = reqparam.getSourceDesc()!=null?reqparam.getSourceDesc().trim():"";
			String[] categoryIds = reqparam.getCategoryIds();
			
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
					List<ProductResponse> productRespList = new ArrayList<>();
					List<Product> productList = new ArrayList<>();
					
					if(categoryIds.length==1&&categoryIds[0].equalsIgnoreCase("ALL")){
						Criterion cr2 = Restrictions.eq("status", STATUS.ACTIVE);
						productDao = new ProductDao(sessionFactory);
						productList = productDao.loadBy(Order.asc("ID"), cr2);
					}else{
						for(String categoryIdDecr: categoryIds){
							String decrypt = CipherUtil.decrypt(categoryIdDecr);
							String pcategoryId = decrypt.replace(ConstantApp.PCATEGORY_ID_COMBINE, "");
							
							productDao = new ProductDao(sessionFactory);
							Criterion cr1 = Restrictions.eq("pcategory.ID", Long.valueOf(pcategoryId));
							Criterion cr2 = Restrictions.eq("status", STATUS.ACTIVE);
							List<Product> productList_ = productDao.loadBy(Order.asc("ID"), cr1, cr2);
							productList.addAll(productList_);
						}
					}
					
					for (Product product : productList) {
						StringBuffer bufferProdId = new StringBuffer();
						bufferProdId.append(ConstantApp.PRODUCT_ID_COMBINE);
						bufferProdId.append(product.getID().toString());
						
						String imageDefaultUri = PropertyMessageUtil.getConfigProperties().getProperty("image.default.url");
						String imageResolutionDefault = PropertyMessageUtil.getConfigProperties().getProperty("image.product.resolution.default");
						String imageUrl = imageDefaultUri+imageResolutionDefault;
						
						String[] images = new String[3];
						images[0] = imageUrl;
						images[1] = "";
						images[2] = "";
						
						ProductResponse resp = new ProductResponse();
						resp.setId(CipherUtil.encrypt(bufferProdId.toString()));
						resp.setName(product.getName());
						resp.setDescription(product.getDescription()!=null?product.getDescription():"");
						resp.setBasePrice(Integer.toString(product.getBasePrice().intValue()));
						resp.setImages(images);
						productRespList.add(resp);
					}
					
					
					jsonResponse.setProductList(productRespList);
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
	public ProductListResponse catalogBySearch(HashMap<String, Object> hm) {
		ProductListResponse jsonResponse = new ProductListResponse();
		String errorCode = "-1";
		String exceptionMsg = "";
		
		try {
			ProductRequest reqparam = (ProductRequest) hm.get(ConstantApp.REQUEST_PARAMS);
			String source = reqparam.getSource()!=null?reqparam.getSource().trim():"";
			String sourceDesc = reqparam.getSourceDesc()!=null?reqparam.getSourceDesc().trim():"";
			String[] categoryIds = reqparam.getCategoryIds();
			String searchVal = reqparam.getSearchVal()!=null?reqparam.getSearchVal().trim():"";
			
			List<String> params = new ArrayList<>();
			params.add(source);
			params.add(sourceDesc);
			params.add(searchVal);
			if(!CommonUtil.checkMandatoryParameter(params)){
				errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10002");
				exceptionMsg = "Mandatory parameter can't be null or empty";
			}else{
				System.out.println("Source: "+source+"; Description: "+sourceDesc);
				boolean sourceValidate = CommonUtil.sourceValidate(source);
				if(!sourceValidate){
					errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10004");
				}else{
					List<ProductResponse> productRespList = new ArrayList<>();
					List<Product> productList = new ArrayList<>();
					
					Criterion cr2 = Restrictions.eq("status", STATUS.ACTIVE);
					Criterion cr3 = Restrictions.like("name", searchVal, MatchMode.ANYWHERE);
					
					if(categoryIds.length==1&&categoryIds[0].equalsIgnoreCase("ALL")){
						productDao = new ProductDao(sessionFactory);
						productList = productDao.loadBy(Order.asc("ID"), cr2, cr3);
					}else{
						for(String categoryIdDecr: categoryIds){
							String decrypt = CipherUtil.decrypt(categoryIdDecr);
							String pcategoryId = decrypt.replace(ConstantApp.PCATEGORY_ID_COMBINE, "");
							
							productDao = new ProductDao(sessionFactory);
							Criterion cr1 = Restrictions.eq("pcategory.ID", Long.valueOf(pcategoryId));
							List<Product> productList_ = productDao.loadBy(Order.asc("ID"), cr1, cr2, cr3);
							productList.addAll(productList_);
						}
					}
					
					for (Product product : productList) {
						StringBuffer bufferProdId = new StringBuffer();
						bufferProdId.append(ConstantApp.PRODUCT_ID_COMBINE);
						bufferProdId.append(product.getID().toString());
						
						String imageDefaultUri = PropertyMessageUtil.getConfigProperties().getProperty("image.default.url");
						String imageResolutionDefault = PropertyMessageUtil.getConfigProperties().getProperty("image.product.resolution.default");
						String imageUrl = imageDefaultUri+imageResolutionDefault;
						
						String[] images = new String[3];
						images[0] = imageUrl;
						images[1] = "";
						images[2] = "";
						
						ProductResponse resp = new ProductResponse();
						resp.setId(CipherUtil.encrypt(bufferProdId.toString()));
						resp.setName(product.getName());
						resp.setDescription(product.getDescription()!=null?product.getDescription():"");
						resp.setBasePrice(Integer.toString(product.getBasePrice().intValue()));
						resp.setImages(images);
						productRespList.add(resp);
					}
					
					jsonResponse.setProductList(productRespList);
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
	public ProductListResponse latest(HashMap<String, Object> hm) {
		ProductListResponse jsonResponse = new ProductListResponse();
		String errorCode = "-1";
		String exceptionMsg = "";
		
		try {
			ProductRequest reqparam = (ProductRequest) hm.get(ConstantApp.REQUEST_PARAMS);
			String source = reqparam.getSource()!=null?reqparam.getSource().trim():"";
			String sourceDesc = reqparam.getSourceDesc()!=null?reqparam.getSourceDesc().trim():"";
			String maxResultStr = reqparam.getMaxResult()!=null?reqparam.getMaxResult().trim():"";
			
			List<String> params = new ArrayList<>();
			params.add(source);
			params.add(sourceDesc);
			params.add(maxResultStr);
			if(!CommonUtil.checkMandatoryParameter(params)){
				errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10002");
				exceptionMsg = "Mandatory parameter can't be null or empty";
			}else{
				System.out.println("Source: "+source+"; Description: "+sourceDesc);
				boolean sourceValidate = CommonUtil.sourceValidate(source);
				if(!sourceValidate){
					errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10004");
				}else{
					String maxResultConf = PropertyMessageUtil.getConfigProperties().getProperty("product.latest.max");
					Integer maxResultPrm = Integer.parseInt(maxResultStr);
					Integer maxResultCon = Integer.parseInt(maxResultConf);
					Integer maxResult = maxResultPrm;
					if(maxResultPrm>maxResultCon){
						maxResult = maxResultCon;
					}
					
					List<ProductResponse> productRespList = new ArrayList<>();
					productDao = new ProductDao(sessionFactory);
					List<Product> productList = productDao.loadBy(Order.desc("ID"), maxResult, Restrictions.eq("status", STATUS.ACTIVE));
					for(Product product: productList) {
						StringBuffer bufferProdId = new StringBuffer();
						bufferProdId.append(ConstantApp.PRODUCT_ID_COMBINE);
						bufferProdId.append(product.getID().toString());
						
						String imageDefaultUri = PropertyMessageUtil.getConfigProperties().getProperty("image.default.url");
						String imageResolutionDefault = PropertyMessageUtil.getConfigProperties().getProperty("image.product.resolution.default");
						String imageUrl = imageDefaultUri+imageResolutionDefault;
						
						String[] images = new String[3];
						images[0] = imageUrl;
						images[1] = "";
						images[2] = "";
						
						ProductResponse resp = new ProductResponse();
						resp.setId(CipherUtil.encrypt(bufferProdId.toString()));
						resp.setName(product.getName());
						resp.setDescription(product.getDescription()!=null?product.getDescription():"");
						resp.setBasePrice(Integer.toString(product.getBasePrice().intValue()));
						resp.setImages(images);
						productRespList.add(resp);
					}
					
					jsonResponse.setSize(Integer.toString(productRespList.size()));
					jsonResponse.setProductList(productRespList);
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
	public ProductResponse detail(HashMap<String, Object> hm) {
		ProductResponse jsonResponse = new ProductResponse();
		String errorCode = "-1";
		String exceptionMsg = "";
		
		try {
			ProductRequest reqparam = (ProductRequest) hm.get(ConstantApp.REQUEST_PARAMS);
			String productIdParam = reqparam.getProductId()!=null?reqparam.getProductId().trim():"";
			
			List<String> params = new ArrayList<>();
			params.add(productIdParam);
			if(!CommonUtil.checkMandatoryParameter(params)){
				errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10002");
				exceptionMsg = "Mandatory parameter can't be null or empty";
			}else{
				String decrypt = CipherUtil.decrypt(productIdParam);
				String productId = decrypt.replace(ConstantApp.PRODUCT_ID_COMBINE, "");
				
				productDao = new ProductDao(sessionFactory);
				Criterion cr1 = Restrictions.eq("ID", Long.valueOf(productId));
				List<Product> productList = productDao.loadBy(Order.asc("ID"), cr1);
				if(productList.size()<1){
					errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.11001");
				}else{
					Product product = productList.get(0);
					if(product.getStatus()!=STATUS.ACTIVE){
						errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.11002");
					}else{
						Criterion cr2 = Restrictions.eq("product", product);
						
						StringBuffer bufferProdId = new StringBuffer();
						bufferProdId.append(ConstantApp.PRODUCT_ID_COMBINE);
						bufferProdId.append(product.getID().toString());
						
						List<ProductSpecResponse> specs = new ArrayList<>();
						productSpecDao = new ProductSpecDao(sessionFactory);
						List<ProductSpec> prodSpecs = productSpecDao.loadBy(Order.asc("ID"), cr2);
						for(ProductSpec prodspec: prodSpecs){
							if(prodspec.getPcategorySpec()!=null){
								ProductSpecResponse specResp = new ProductSpecResponse();
								specResp.setLabel(prodspec.getPcategorySpec().getLabel());
								specResp.setValue(prodspec.getValue());
								specs.add(specResp);
							}
						}
						
						List<ProductAtrrResponse> attributes = new ArrayList<>();
						productAttrDao = new ProductAttrDao(sessionFactory);
						List<ProductAttr> productAttrList = productAttrDao.loadBy(Order.asc("ID"), cr2);
						for (ProductAttr productAttr: productAttrList) {
							if(productAttr.getPcategoryAttr()!=null){
								ProductAtrrResponse attrResp = new ProductAtrrResponse();
								attrResp.setLabel(productAttr.getPcategoryAttr().getLabel());
								attrResp.setValue(productAttr.getValue());
								attributes.add(attrResp);
							}
						}
						
						String imageDefaultUri = PropertyMessageUtil.getConfigProperties().getProperty("image.default.url");
						String imageResolutionDefault = PropertyMessageUtil.getConfigProperties().getProperty("image.product.resolution.default");
						String imageUrl = imageDefaultUri+imageResolutionDefault;
						String[] images = new String[3];
						images[0] = imageUrl;
						images[1] = imageDefaultUri+"60x60";
						images[2] = imageDefaultUri+"60x60";
						
						jsonResponse.setId(CipherUtil.encrypt(bufferProdId.toString()));
						jsonResponse.setName(product.getName());
						jsonResponse.setBasePrice(Integer.toString(product.getBasePrice().intValue()));
						jsonResponse.setDescription(product.getDescription()!=null?product.getDescription():"");
						jsonResponse.setCategoryName(product.getPcategory().getName());
						jsonResponse.setSubCategoryName(product.getPcategorySub().getName());
						jsonResponse.setImages(images);
						jsonResponse.setSpecifications(specs);
						jsonResponse.setAttributes(attributes);
						errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.0");
					}
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
	public ProductResponse skuList(HashMap<String, Object> hm) {
		ProductResponse jsonResponse = new ProductResponse();
		String errorCode = "-1";
		String exceptionMsg = "";
		
		try {
			ProductRequest reqparam = (ProductRequest) hm.get(ConstantApp.REQUEST_PARAMS);
			String productIdParam = reqparam.getProductId()!=null?reqparam.getProductId().trim():"";
			String[] attrValues = reqparam.getAttrValues();
			
			List<String> params = new ArrayList<>();
			params.add(productIdParam);
			if(!CommonUtil.checkMandatoryParameter(params)){
				errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10002");
				exceptionMsg = "Mandatory parameter can't be null or empty";
			}else{
				String decrypt = CipherUtil.decrypt(productIdParam);
				String productId = decrypt.replace(ConstantApp.PRODUCT_ID_COMBINE, "");
				
				productDao = new ProductDao(sessionFactory);
				Criterion cr1 = Restrictions.eq("ID", Long.valueOf(productId));
				List<Product> productList = productDao.loadBy(Order.asc("ID"), cr1);
				if(productList.size()<1){
					errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.11001");
				}else{
					Product product = productList.get(0);
					if(product.getStatus()!=STATUS.ACTIVE){
						errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.11002");
					}else{
						Criterion cr2 = Restrictions.eq("product", product);
						
						productAttrDao = new ProductAttrDao(sessionFactory);
						Integer sumAttr = productAttrDao.rowCount(cr2);
						System.out.println("sumAttr: "+sumAttr);
						
						if(sumAttr!=attrValues.length){
							errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.11003");
						}else{
							StringBuffer buffer = new StringBuffer();
							for (String attrlbl: attrValues) {
								buffer.append(attrlbl.trim());
								buffer.append(" ");
							}
							
							System.out.println("buffer: "+buffer);
							
							List<String[]> skuList = new ArrayList<>();
							productSkuDao = new ProductSkuDao(sessionFactory);
							Criterion cr3 = Restrictions.like("label", buffer.toString().trim(), MatchMode.ANYWHERE);
							List<ProductSku> prodSkuList = productSkuDao.loadBy(Order.asc("addtPrice"), cr2, cr3);
							for (ProductSku productSku : prodSkuList) {
								if(productSku.getStock()!=null&&productSku.getStock()>0){
									StringBuffer bufferSkuId = new StringBuffer();
									bufferSkuId.append(ConstantApp.PRODUCT_ATTR_ID_COMBINE);
									bufferSkuId.append(productSku.getID().toString());
									
									String[] arr = new String[6];
									arr[0] = CipherUtil.encrypt(bufferSkuId.toString());
									arr[1] = productSku.getLabel();
									arr[2] = productSku.getSku()!=null?productSku.getSku():"";
									arr[3] = productSku.getRemarks()!=null?productSku.getRemarks():"";
									arr[4] = productSku.getStock().toString();
									arr[5] = productSku.getAddtPrice()!=null?Integer.toString(productSku.getAddtPrice().intValue()):"0";
									skuList.add(arr);
								}
							}
							
							jsonResponse.setSkuList(skuList);
							errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.0");
						}
					}
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
