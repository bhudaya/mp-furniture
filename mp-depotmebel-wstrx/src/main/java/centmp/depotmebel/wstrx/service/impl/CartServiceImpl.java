package centmp.depotmebel.wstrx.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bilmajdi.util.BielUtil;
import com.bilmajdi.util.CipherUtil;
import com.bilmajdi.util.PropertyMessageUtil;
import com.google.gson.Gson;

import centmp.depotmebel.core.dao.ProductAttrDao;
import centmp.depotmebel.core.dao.ProductDao;
import centmp.depotmebel.core.dao.ProductImageDao;
import centmp.depotmebel.core.dao.ProductSkuDao;
import centmp.depotmebel.core.dao.TrxItemDao;
import centmp.depotmebel.core.dao.TrxItemMsgDao;
import centmp.depotmebel.core.dao.TrxStatusItemDao;
import centmp.depotmebel.core.dao.VendorCoverageDao;
import centmp.depotmebel.core.dao.VendorCrdLimitDao;
import centmp.depotmebel.core.dao.VendorDlverLimitDao;
import centmp.depotmebel.core.dao.VendorProductDao;
import centmp.depotmebel.core.enums.STATUS;
import centmp.depotmebel.core.enums.TRX_ITEM_STATUS;
import centmp.depotmebel.core.json.request.TrxRequest;
import centmp.depotmebel.core.json.response.CustomerResponse;
import centmp.depotmebel.core.json.response.ProductAtrrResponse;
import centmp.depotmebel.core.json.response.ProductResponse;
import centmp.depotmebel.core.json.response.TrxItemListResponse;
import centmp.depotmebel.core.json.response.TrxItemResponse;
import centmp.depotmebel.core.model.Product;
import centmp.depotmebel.core.model.ProductAttr;
import centmp.depotmebel.core.model.ProductImage;
import centmp.depotmebel.core.model.ProductSku;
import centmp.depotmebel.core.model.TrxItem;
import centmp.depotmebel.core.model.TrxItemMsg;
import centmp.depotmebel.core.model.TrxItemStatus;
import centmp.depotmebel.core.model.Vendor;
import centmp.depotmebel.core.model.VendorCoverage;
import centmp.depotmebel.core.model.VendorCrdLimit;
import centmp.depotmebel.core.model.VendorDlverLimit;
import centmp.depotmebel.core.model.VendorProduct;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.core.util.ConstantApp;
import centmp.depotmebel.core.util.WSCustomer;
import centmp.depotmebel.wstrx.service.CartService;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	private ProductDao productDao;
	private ProductImageDao productImgDao;
	private ProductSkuDao productSkuDao;
	private ProductAttrDao productAttrDao;
	private TrxItemDao trxItemDao;
	private TrxItemMsgDao itemMsgDao;
	private TrxStatusItemDao statusItemDao;
	private VendorProductDao vendProdDao;
	private VendorCoverageDao vendCvrgDao;
	private VendorCrdLimitDao vendCrdLmtDao;
	private VendorDlverLimitDao vendCpctDao;
	
	@Override
	public TrxItemResponse add(HashMap<String, Object> hm) {
		TrxItemResponse jsonResponse = new TrxItemResponse();
		String errorCode = "-1";
		String exceptionMsg = "";
		
		try {
			String token = (String) hm.get(ConstantApp.TOKEN);
			TrxRequest reqparam = (TrxRequest) hm.get(ConstantApp.REQUEST_PARAMS);
			String productIdPrm = reqparam.getProductId()!=null?reqparam.getProductId().trim():"";
			String skuIdPrm = reqparam.getSkuId()!=null?reqparam.getSkuId().trim():"";
			String cityId = reqparam.getCityId()!=null?reqparam.getCityId().trim():"";
			String quantity = reqparam.getQuantity()!=null?reqparam.getQuantity().trim():"";
			String msgCustom = reqparam.getMsgCustom()!=null?reqparam.getMsgCustom().trim():"";
			String msgFolder = reqparam.getMsgImgFolder()!=null?reqparam.getMsgImgFolder().trim():"";
			String msgImgName = reqparam.getMsgImgName()!=null?reqparam.getMsgImgName().trim():"";
			String msgImgName2 = reqparam.getMsgImgName2()!=null?reqparam.getMsgImgName2().trim():"";
			
			List<String> params = new ArrayList<>();
			params.add(token);
			params.add(productIdPrm);
			params.add(cityId);
			params.add(quantity);
			if(!CommonUtil.checkMandatoryParameter(params)){
				errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10002");
				exceptionMsg = "Mandatory parameter can't be null or empty";
			}else{
				System.out.println("reqparam: "+new Gson().toJson(reqparam));
				
				CustomerResponse customerresp = WSCustomer.findByToken(token);
				System.out.println("customerresp: "+new Gson().toJson(customerresp));
				if(customerresp!=null&&customerresp.getErrorCode()!=0){
					errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10006");
					exceptionMsg = customerresp.getTechnicalMsg();
				}else{
					String customerIdEncr = CipherUtil.decrypt(customerresp.getId());
					String customerId = customerIdEncr.replace(ConstantApp.CUSTOMER_ID_COMBINE, "");
					
					String productIdDecr = CipherUtil.decrypt(productIdPrm);
					String productId = productIdDecr.replace(ConstantApp.PRODUCT_ID_COMBINE, "");
					
					String skuIdDecr = !skuIdPrm.isEmpty()?CipherUtil.decrypt(skuIdPrm):"";
					String skuId = !skuIdDecr.isEmpty()?skuIdDecr.replace(ConstantApp.PRODUCT_ATTR_ID_COMBINE, ""):null;
					
					productDao = new ProductDao(sessionFactory);
					Criterion crProdId = Restrictions.eq("ID", Long.valueOf(productId));
					Criterion crActive = Restrictions.eq("status", STATUS.ACTIVE);
					List<Product> prodList = productDao.loadBy(Order.asc("ID"), 1, crProdId, crActive);
					if(prodList.size()<1){
						errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.11001");
					}else{
						Product product = prodList.get(0);
						Integer quantityInt = Integer.parseInt(quantity);
						
						Vendor vendor = null;
						ProductSku productSku = null;
						
						BigDecimal priceBase = product.getBasePrice();
						BigDecimal priceSum = priceBase;
						//Integer stock = -1;
						//String isUnlimited = "N";
						
						Criterion crProd = Restrictions.eq("product", product);
						Criterion crVp1 = Restrictions.eq("isActive", "Y");
						
						if(skuId!=null&&!skuId.isEmpty()){
							Criterion crSkuId = Restrictions.eq("ID", Long.valueOf(skuId));
							productSkuDao = new ProductSkuDao(sessionFactory);
							List<ProductSku> productSkuList = productSkuDao.loadBy(Order.asc("ID"), 1, crSkuId);
							if(productSkuList.size()>0){
								productSku = productSkuList.get(0);
								
								BigDecimal priceAddt = productSku.getAddtPrice();
								priceSum = priceBase.add(priceAddt);
								
								Criterion crVp2 = Restrictions.eq("productSku", productSku);
								vendProdDao = new VendorProductDao(sessionFactory);
								List<VendorProduct> vendProdList = vendProdDao.loadBy(Order.asc("ID"), crVp1, crVp2);
								if(vendProdList.size()>0){
									VendorProduct vendProd = vendProdList.get(0);
									
									vendor = vendProd.getVendor();
									//isUnlimited = vendProd.getIsUnlimited();
								}
							}
						}else{
							/*
							 * Cari Vendor, stock, isUnlimited
							 */
							
							
							//Cari Vendor dari Vendor_Product & yang aktif
							List<Vendor> foundVendList1 = new ArrayList<>();
							productSkuDao = new ProductSkuDao(sessionFactory);
							List<ProductSku> productSkuList = productSkuDao.loadBy(Order.asc("ID"), 1, crProd);
							for(ProductSku productSku2: productSkuList) {
								Criterion crVp2 = Restrictions.eq("productSku", productSku2);
								Criterion crVp3 = Restrictions.isNotNull("vendor");
								
								Criterion crVp4 = Restrictions.eq("isUnlimited", "Y");
								Criterion crVp5 = Restrictions.not(crVp4);
								Criterion crVp6 = Restrictions.gt("stock", 0);
								Criterion crVp7 = Restrictions.and(crVp5, crVp6);
								Disjunction disj = Restrictions.disjunction();
								disj.add(crVp4);
								disj.add(crVp7);
								
								vendProdDao = new VendorProductDao(sessionFactory);
								List<VendorProduct> vendProdList = vendProdDao.loadBy(Order.desc("stock"), crVp1, crVp2, crVp3, disj);
								for(VendorProduct vendorProduct: vendProdList) {
									if(vendorProduct.getVendor().getStatus()==STATUS.ACTIVE){
										foundVendList1.add(vendorProduct.getVendor());
										
										//isUnlimited = vendProd.getIsUnlimited();
										
										productSku = vendorProduct.getProductSku();
										BigDecimal priceAddt = productSku.getAddtPrice();
										priceSum = priceBase.add(priceAddt);
									}
								}
							}
							System.out.println("foundVendList1: "+foundVendList1.size());
							
							//Dicari yang lokasinya sama dengan area yang dipilih user.
							List<Vendor> foundVendList2 = new ArrayList<>();
							if(foundVendList1.size()>0){
								for(Vendor foundVendor: foundVendList1) {
									vendCvrgDao = new VendorCoverageDao(sessionFactory);
									Criterion cr1 = Restrictions.eq("vendor", foundVendor);
									Criterion cr2 = Restrictions.eq("cityId", cityId);
									List<VendorCoverage> vendCovrgList = vendCvrgDao.loadBy(Order.asc("slaTime"), cr1, cr2);
									for(VendorCoverage vendCvrg: vendCovrgList){
										if(vendCvrg.getVendor().getStatus()==STATUS.ACTIVE){
											foundVendList2.add(vendCvrg.getVendor());
										}
									}
								}
								System.out.println("foundVendList2: "+foundVendList2.size());
							}
							
							
							//Dicari yang credit limitnya > harga item. yang terbesar
							List<Vendor> foundVendList3 = new ArrayList<>();
							if(foundVendList2.size()>0){
								for(Vendor vend: foundVendList2){
									vendCrdLmtDao = new VendorCrdLimitDao(sessionFactory);
									Criterion cr1 = Restrictions.eq("vendor", vend);
									Criterion cr2 = Restrictions.gt("balance", priceBase);
									List<VendorCrdLimit> vendCrdLmtList = vendCrdLmtDao.loadBy(Order.desc("balance"), cr1, cr2);
									for(VendorCrdLimit vcr: vendCrdLmtList){
										if(vcr.getVendor().getStatus()==STATUS.ACTIVE){
											foundVendList3.add(vcr.getVendor());
										}
									}
								}
								System.out.println("foundVendList3: "+foundVendList3.size());
							}
							
							
							//Dicari yang delivery capacity limitnya > quantity item. yang terbesar.
							List<Vendor> foundVendList4 = new ArrayList<>();
							if(foundVendList3.size()>0){
								for(Vendor vend: foundVendList3){
									vendCpctDao = new VendorDlverLimitDao(sessionFactory);
									Criterion cr1 = Restrictions.eq("vendor", vend);
									Criterion cr2 = Restrictions.gt("balance", quantityInt);
									List<VendorDlverLimit> vendCpctList = vendCpctDao.loadBy(Order.desc("balance"), cr1, cr2);
									for(VendorDlverLimit vdl: vendCpctList){
										if(vdl.getVendor().getStatus()==STATUS.ACTIVE){
											foundVendList4.add(vdl.getVendor());
										}
									}
								}
								System.out.println("foundVendList4: "+foundVendList4.size());
							}
							
							if(foundVendList4.size()>0){
								Vendor vendorget = foundVendList4.get(0);
								System.out.println("VendID: "+vendorget.getID());
								//vendor = foundVendList2.get(0
								if(vendorget.getStatus()==STATUS.ACTIVE){
									vendor = vendorget;
								}
							}
						}
						
						String isUseQc = PropertyMessageUtil.getConfigProperties().getProperty("trxitem.use.qc");
						BigDecimal price = priceSum.multiply(new BigDecimal(quantityInt));
						
						TrxItem item = new TrxItem();
						item.setCustomerId(customerId);
						item.setVendor(vendor);
						item.setProduct(product);
						item.setProductSku(productSku);
						item.setSkuId(skuId);
						item.setCityId(cityId);
						item.setQuantity(quantityInt);
						item.setPrice(price);
						item.setCreatedTime(Calendar.getInstance(BielUtil.timeZoneJakarta()).getTime());
						item.setIsUseQc(isUseQc);
						
						trxItemDao = new TrxItemDao(sessionFactory);
						trxItemDao.saveOrUpdate(item);
						
						if((msgCustom!=null&&!msgCustom.isEmpty()) || (msgFolder!=null&&!msgFolder.isEmpty()) ||  
								(msgImgName!=null&&!msgImgName.isEmpty()) || (msgImgName2!=null&&!msgImgName2.isEmpty()) ){
							TrxItemMsg itemMsg = new TrxItemMsg();
							itemMsg.setTrxItem(item);
							itemMsg.setMesssage(msgCustom!=null&&!msgCustom.isEmpty()?msgCustom:null);
							itemMsg.setImageFolder(msgFolder);
							itemMsg.setImageName(msgImgName);
							itemMsg.setImageName2(msgImgName2);
							
							itemMsgDao = new TrxItemMsgDao(sessionFactory);
							itemMsgDao.saveOrUpdate(itemMsg);
						}
						
						TrxItemStatus tsi = new TrxItemStatus();
						tsi.setDate_(Calendar.getInstance(BielUtil.timeZoneJakarta()).getTime());
						tsi.setTrxItem(item);
						tsi.setIsReadyQc("N");
						if(vendor!=null){
							tsi.setStatus(TRX_ITEM_STATUS.ON_VENDOR);
						}else{
							tsi.setStatus(TRX_ITEM_STATUS.WAITING_FOR_ASSIGNMENT);
						}
						statusItemDao = new TrxStatusItemDao(sessionFactory);
						statusItemDao.saveOrUpdate(tsi);
						
						jsonResponse.setTrxItemId(CipherUtil.encrypt(ConstantApp.TRXITEM_ID_COMBINE+item.getID().toString()));//TrxItemId
						errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.0");
					}
				}
			}
			
		} catch (Exception e) {
			errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10001");
			exceptionMsg = e.getLocalizedMessage()!=null?e.getLocalizedMessage():"NULL";
			e.printStackTrace();
		}
		
		jsonResponse.setErrorCode(Integer.parseInt(errorCode));
		jsonResponse.setErrorMsg(PropertyMessageUtil.getMessageProperties().getProperty("code.error.msg."+errorCode));
		jsonResponse.setTechnicalMsg(exceptionMsg.isEmpty()?PropertyMessageUtil.getMessageProperties().getProperty("code.techn.msg."+errorCode):exceptionMsg);
	
		return jsonResponse;
	}
	
	@Override
	public TrxItemListResponse list(HashMap<String, Object> hm) {
		TrxItemListResponse jsonResponse = new TrxItemListResponse();
		String errorCode = "-1";
		String exceptionMsg = "";
		
		try {
			String token = (String) hm.get(ConstantApp.TOKEN);
			
			List<String> params = new ArrayList<>();
			params.add(token);
			if(!CommonUtil.checkMandatoryParameter(params)){
				errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10002");
				exceptionMsg = "Mandatory parameter can't be null or empty";
			}else{
				CustomerResponse customerresp = WSCustomer.findByToken(token);
				System.out.println("customerresp: "+new Gson().toJson(customerresp));
				
				if(customerresp.getErrorCode()!=0){
					errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10006");
					exceptionMsg = customerresp.getTechnicalMsg();
				}else{
					String customerIdEncr = CipherUtil.decrypt(customerresp.getId());
					String customerId = customerIdEncr.replace(ConstantApp.CUSTOMER_ID_COMBINE, "");
				
					List<TrxItemResponse> cartList = new ArrayList<>();
					trxItemDao = new TrxItemDao(sessionFactory);
					Criterion cr1 = Restrictions.eq("customerId", customerId);
					Criterion cr2 = Restrictions.isNull("trx");
					List<TrxItem> trxItemList = trxItemDao.loadBy(Order.desc("ID"), cr1, cr2);
					for(TrxItem trxItem: trxItemList) {
						Product product = trxItem.getProduct();
						BigDecimal price = trxItem.getPrice();
						
						//String imgUrl = PropertyMessageUtil.getConfigProperties().getProperty("img.product.cart.default");
						String imgUrl = "";
						productImgDao = new ProductImageDao(sessionFactory);
						List<ProductImage> prodImgList = productImgDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("product", product));
						if(prodImgList.size()>0){
							ProductImage pimage = prodImgList.get(0);
							if(pimage.getFolder()!=null && pimage.getName()!=null){
								String mainUrl = PropertyMessageUtil.getConfigProperties().getProperty("image.myimages.url");
								String path = PropertyMessageUtil.getConfigProperties().getProperty("image.product.folder.url");
								imgUrl = mainUrl + path + pimage.getFolder() + "/" + pimage.getName();
							}
						}
						
						List<ProductAtrrResponse> attributes = new ArrayList<>();
						productAttrDao = new ProductAttrDao(sessionFactory);
						List<ProductAttr> prodAttrList = productAttrDao.loadBy(Order.asc("ID"), Restrictions.eq("product", product));
						for(ProductAttr productAttr: prodAttrList) {
							ProductAtrrResponse attrresp = new ProductAtrrResponse();
							attrresp.setLabel(productAttr.getPcategoryAttr().getLabel());
							attrresp.setValue(productAttr.getValue());
							attributes.add(attrresp);
						}
						
						ProductResponse productDetail = new ProductResponse();
						productDetail.setId(product.getID().toString());
						productDetail.setName(product.getName());
						productDetail.setDescription(product.getDescription()!=null?product.getDescription():"");
						productDetail.setBasePrice(Integer.toString(product.getBasePrice().intValue()));
						productDetail.setCategoryName(product.getPcategory().getName());
						productDetail.setSubCategoryName(product.getPcategorySub().getName());
						productDetail.setAttributes(attributes);
						
						TrxItemResponse resp = new TrxItemResponse();
						resp.setTrxItemId(CipherUtil.encrypt(ConstantApp.TRXITEM_ID_COMBINE+trxItem.getID().toString()));
						resp.setProductName(product.getName());
						resp.setProductImg(imgUrl);
						resp.setCategoryName(product.getPcategory().getName());
						resp.setSkuLabel(trxItem.getProductSku()!=null?trxItem.getProductSku().getLabel():"");
						resp.setPrice(Integer.toString(price.intValue()));
						resp.setPriceIdr(CommonUtil.currencyIDR(price.doubleValue()));
						resp.setCityId(trxItem.getCityId());
						resp.setQuantity(trxItem.getQuantity().toString());
						resp.setProductDetail(productDetail);
						cartList.add(resp);
					}
					
					jsonResponse.setCartList(cartList);
					errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.0");
				}
			}	
		} catch (Exception e) {
			errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10001");
			exceptionMsg = e.getLocalizedMessage()!=null?e.getLocalizedMessage():"NULL";
			e.printStackTrace();
		}
		
		System.out.println("errorCode: "+errorCode);
		
		jsonResponse.setErrorCode(Integer.parseInt(errorCode));
		jsonResponse.setErrorMsg(PropertyMessageUtil.getMessageProperties().getProperty("code.error.msg."+errorCode));
		jsonResponse.setTechnicalMsg(exceptionMsg.isEmpty()?PropertyMessageUtil.getMessageProperties().getProperty("code.techn.msg."+errorCode):exceptionMsg);
	
		return jsonResponse;
	}
	
	@Override
	public TrxItemResponse removeItem(HashMap<String, Object> hm) {
		TrxItemResponse jsonResponse = new TrxItemResponse();
		String errorCode = "-1";
		String exceptionMsg = "";
		
		try {
			String token = (String) hm.get(ConstantApp.TOKEN);
			TrxRequest reqparam = (TrxRequest) hm.get(ConstantApp.REQUEST_PARAMS);
			String trxItemIdPrm = reqparam.getTrxItemId()!=null?reqparam.getTrxItemId().trim():"";
			
			List<String> params = new ArrayList<>();
			params.add(token);
			params.add(trxItemIdPrm);
			if(!CommonUtil.checkMandatoryParameter(params)){
				errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10002");
				exceptionMsg = "Mandatory parameter can't be null or empty";
			}else{
				CustomerResponse customerresp = WSCustomer.findByToken(token);
				if(customerresp.getErrorCode()!=0){
					errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10006");
					exceptionMsg = customerresp.getTechnicalMsg();
				}else{
					String decrypt = CipherUtil.decrypt(trxItemIdPrm);
					String trxItemId = decrypt.replace(ConstantApp.TRXITEM_ID_COMBINE, "");
					
					trxItemDao = new TrxItemDao(sessionFactory);
					Criterion cr1 = Restrictions.eq("ID", Long.valueOf(trxItemId));
					Criterion cr2 = Restrictions.isNull("trx");
					List<TrxItem> trxItemList = trxItemDao.loadBy(Order.asc("ID"), 1, cr1, cr2);
					if(trxItemList.size()<1){
						errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10005");
					}else{
						TrxItem trxItem = trxItemList.get(0);
						Criterion cr3 = Restrictions.eq("trxItem", trxItem);
						
						statusItemDao = new TrxStatusItemDao(sessionFactory);
						List<TrxItemStatus> itemStatusList = statusItemDao.loadBy(Order.asc("ID"), cr3);
						for (TrxItemStatus trxItemStatus : itemStatusList) {
							statusItemDao = new TrxStatusItemDao(sessionFactory);
							statusItemDao.delete(trxItemStatus);
						}
						
						itemMsgDao = new TrxItemMsgDao(sessionFactory);
						List<TrxItemMsg> itemMsgList = itemMsgDao.loadBy(Order.asc("ID"), cr3);
						for (TrxItemMsg itemMsg : itemMsgList) {
							itemMsgDao = new TrxItemMsgDao(sessionFactory);
							itemMsgDao.delete(itemMsg);
						}
						
						trxItemDao = new TrxItemDao(sessionFactory);
						trxItemDao.delete(trxItem);
						
						errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.0");
					}
				}
			}	
		} catch (Exception e) {
			errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10001");
			exceptionMsg = e.getLocalizedMessage()!=null?e.getLocalizedMessage():"NULL";
			e.printStackTrace();
		}
		
		jsonResponse.setErrorCode(Integer.parseInt(errorCode));
		jsonResponse.setErrorMsg(PropertyMessageUtil.getMessageProperties().getProperty("code.error.msg."+errorCode));
		jsonResponse.setTechnicalMsg(exceptionMsg.isEmpty()?PropertyMessageUtil.getMessageProperties().getProperty("code.techn.msg."+errorCode):exceptionMsg);
	
		return jsonResponse;
	}
	
	@Override
	public TrxItemResponse removeAll(HashMap<String, Object> hm) {
		TrxItemResponse jsonResponse = new TrxItemResponse();
		String errorCode = "-1";
		String exceptionMsg = "";
		
		try {
			String token = (String) hm.get(ConstantApp.TOKEN);
			
			List<String> params = new ArrayList<>();
			params.add(token);
			if(!CommonUtil.checkMandatoryParameter(params)){
				errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10002");
				exceptionMsg = "Mandatory parameter can't be null or empty";
			}else{
				CustomerResponse customerresp = WSCustomer.findByToken(token);
				System.out.println("customerresp: "+new Gson().toJson(customerresp));
				
				if(customerresp.getErrorCode()!=0){
					errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10006");
					exceptionMsg = customerresp.getTechnicalMsg();
				}else{
					String customerIdEncr = CipherUtil.decrypt(customerresp.getId());
					String customerId = customerIdEncr.replace(ConstantApp.CUSTOMER_ID_COMBINE, "");
					
					trxItemDao = new TrxItemDao(sessionFactory);
					Criterion cr1 = Restrictions.eq("customerId", customerId);
					Criterion cr2 = Restrictions.isNull("trx");
					List<TrxItem> trxItemList = trxItemDao.loadBy(Order.desc("ID"), cr1, cr2);
					for(TrxItem trxItem: trxItemList) {
						Criterion cr3 = Restrictions.eq("trxItem", trxItem);
						
						statusItemDao = new TrxStatusItemDao(sessionFactory);
						List<TrxItemStatus> itemStatusList = statusItemDao.loadBy(Order.asc("ID"), cr3);
						for (TrxItemStatus trxItemStatus : itemStatusList) {
							statusItemDao = new TrxStatusItemDao(sessionFactory);
							statusItemDao.delete(trxItemStatus);
						}
						
						itemMsgDao = new TrxItemMsgDao(sessionFactory);
						List<TrxItemMsg> itemMsgList = itemMsgDao.loadBy(Order.asc("ID"), cr3);
						for (TrxItemMsg itemMsg : itemMsgList) {
							itemMsgDao = new TrxItemMsgDao(sessionFactory);
							itemMsgDao.delete(itemMsg);
						}
						
						trxItemDao = new TrxItemDao(sessionFactory);
						trxItemDao.delete(trxItem);
					}
					
					errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.0");
				}
			}	
		} catch (Exception e) {
			errorCode = PropertyMessageUtil.getMessageProperties().getProperty("code.error.10001");
			exceptionMsg = e.getLocalizedMessage()!=null?e.getLocalizedMessage():"NULL";
			e.printStackTrace();
		}
		
		jsonResponse.setErrorCode(Integer.parseInt(errorCode));
		jsonResponse.setErrorMsg(PropertyMessageUtil.getMessageProperties().getProperty("code.error.msg."+errorCode));
		jsonResponse.setTechnicalMsg(exceptionMsg.isEmpty()?PropertyMessageUtil.getMessageProperties().getProperty("code.techn.msg."+errorCode):exceptionMsg);
	
		return jsonResponse;
	}

}
