package centmp.depotmebel.adminvendor.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.FastDateFormat;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bilmajdi.util.BielUtil;
import com.bilmajdi.util.CipherUtil;
import com.bilmajdi.util.PropertyMessageUtil;
import com.google.gson.Gson;

import centmp.depotmebel.adminvendor.bean.SessionBean;
import centmp.depotmebel.adminvendor.bean.TrxBean;
import centmp.depotmebel.adminvendor.service.NotifService;
import centmp.depotmebel.adminvendor.service.OrderService;
import centmp.depotmebel.adminvendor.thread.CourierAssignedThread;
import centmp.depotmebel.adminvendor.thread.MailReadyQCThread;
import centmp.depotmebel.adminvendor.thread.OrderDeliveredThread;
import centmp.depotmebel.core.dao.ProductImageDao;
import centmp.depotmebel.core.dao.ProductSpecDao;
import centmp.depotmebel.core.dao.TrxItemDao;
import centmp.depotmebel.core.dao.TrxItemMsgDao;
import centmp.depotmebel.core.dao.TrxStatusDao;
import centmp.depotmebel.core.dao.TrxStatusItemDao;
import centmp.depotmebel.core.dao.VendorCoverageDao;
import centmp.depotmebel.core.dao.VendorUserDao;
import centmp.depotmebel.core.enums.TRX_ITEM_STATUS;
import centmp.depotmebel.core.enums.TRX_STATUS;
import centmp.depotmebel.core.enums.VENDOR_USER_TYPE;
import centmp.depotmebel.core.model.Product;
import centmp.depotmebel.core.model.ProductImage;
import centmp.depotmebel.core.model.ProductSpec;
import centmp.depotmebel.core.model.Trx;
import centmp.depotmebel.core.model.TrxItem;
import centmp.depotmebel.core.model.TrxItemMsg;
import centmp.depotmebel.core.model.TrxItemStatus;
import centmp.depotmebel.core.model.TrxStatus;
import centmp.depotmebel.core.model.Vendor;
import centmp.depotmebel.core.model.VendorCoverage;
import centmp.depotmebel.core.model.VendorUser;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.core.util.ImageUtil;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	@Autowired
	@Qualifier(value="taskExecutor")
	protected ThreadPoolTaskExecutor threadTask;
	
	@Autowired
	private NotifService notifService;
	
	private TrxItemDao trxItemDao;
	private TrxItemMsgDao itemMsgDao;
	private TrxStatusItemDao itemStatusDao;
	private TrxStatusDao trxStatusDao;
	private VendorCoverageDao vendCovergDao;
	private VendorUserDao vendorUserDao;
	private ProductSpecDao prodSpecDao;
	private ProductImageDao prodImgDao;
	
	
	@Override
	public List<TrxBean> orderList(SessionBean sessionBean) {
		List<TrxBean> list = new ArrayList<>();
		
		try {
			String[] vendors = sessionBean.getUser().getVendors();
			String vendorUserId = vendors[2];
			//String vendorUserType = vendors[4];
			
			
			vendorUserDao = new VendorUserDao(sessionFactory);
			List<VendorUser> vuserList = vendorUserDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(vendorUserId)));
			if(vuserList.size()>0){
				VendorUser vuser = vuserList.get(0);
				Vendor vendor = vuser.getVendor();
				
				Criterion cr1 = Restrictions.eq("vendor", vendor);
				Criterion[] crr = new Criterion[1];
				crr[0] = cr1;
				if(vuser.getType()==VENDOR_USER_TYPE.COURIER){
					crr = new Criterion[2];
					crr[0] = cr1;
					crr[1] = Restrictions.eq("courierId", vuser.getID());
				}
				
				trxItemDao = new TrxItemDao(sessionFactory);
				List<TrxItem> trxItemList = trxItemDao.loadBy(Order.desc("ID"), crr);
				for(TrxItem trxItem: trxItemList){
					Trx trx = trxItem.getTrx();
					String id = trx.getID().toString();
					String idEnc = URLEncoder.encode(CipherUtil.encrypt(id), "UTF-8");
					
					trxStatusDao = new TrxStatusDao(sessionFactory);
					List<TrxStatus> trxStatusList = trxStatusDao.loadBy(Order.desc("ID"), 1, Restrictions.eq("trx", trx));
					if(trxStatusList.size()>0){
						TrxStatus trxStatus = trxStatusList.get(0);
						TRX_STATUS status_ = trxStatus.getStatus(); 
						if(status_==TRX_STATUS.PAID || status_==TRX_STATUS.DONE){
							String sla = "-";
							if(status_==TRX_STATUS.PAID && trx.getCity()!=null){
								vendCovergDao = new VendorCoverageDao(sessionFactory);
								Criterion crVend = Restrictions.eq("vendor", vendor);
								Criterion crCity = Restrictions.eq("cityId", trx.getCity().getCode());
								List<VendorCoverage> vendCovergList = vendCovergDao.loadBy(Order.asc("ID"), 1, crVend, crCity);
								if(vendCovergList.size()>0){
									VendorCoverage vendCovrg = vendCovergList.get(0);
									Integer slaInt = vendCovrg.getSlaTime();
									
									Date paidDate = trxStatus.getDate_();
									Calendar calSla = Calendar.getInstance(BielUtil.timeZoneJakarta());
									calSla.setTime(paidDate);
									Integer hourNow = calSla.get(Calendar.HOUR_OF_DAY);
									calSla.set(Calendar.HOUR_OF_DAY, hourNow+slaInt);
									sla = FastDateFormat.getInstance("dd/MM/yyyy, HH:mm").format(calSla.getTime());
								}
							}
							
							String[] lastStatusItem = new String[2];
							lastStatusItem[0] = "1";
							lastStatusItem[1] = "NEW";
							itemStatusDao = new TrxStatusItemDao(sessionFactory);
							List<TrxItemStatus> itemStatusList= itemStatusDao.loadBy(Order.desc("ID"), 1, Restrictions.eq("trxItem", trxItem));
							if(itemStatusList.size()>0){
								TrxItemStatus tsi = itemStatusList.get(0);
								if(tsi.getStatus()!=TRX_ITEM_STATUS.ON_VENDOR){
									lastStatusItem[0] = Integer.toString(tsi.getStatus().ordinal());
									lastStatusItem[1] = TRX_ITEM_STATUS.getValue(tsi.getStatus().ordinal()).toUpperCase();
								}
							}
							
							String[] customer = new String[4];
							customer[0] = trx.getCustomerId();
							customer[1] = trx.getCustomerName();
							customer[2] = trx.getCustomerEmail();
							customer[3] = trx.getCustomerPhone();
							
							StringBuffer address = new StringBuffer();
							if(trx.getAddress()!=null){
								address.append(trx.getAddress().replace("\n", "<br>"));
								if(trx.getCity()!=null)
									address.append(",<br>");
									address.append(trx.getCity().getName());
								if(trx.getCity()!=null)
									address.append(" - ");
									address.append(trx.getCity().getProvince().getName());
								if(trx.getPostalCode()!=null)
									address.append(", ");
									address.append(trx.getPostalCode());
							}
							
							
							String[] product = new String[5];
							product[0] = trxItem.getProduct().getID().toString();
							product[1] = trxItem.getProduct().getName();
							product[2] = trxItem.getProductSku().getID().toString();
							product[3] = trxItem.getProductSku().getLabel();
							product[4] = trxItem.getProductSku().getSku();
							
							String urlParam1 = lastStatusItem[0]+";"+trxItem.getID();
							String urlParams = URLEncoder.encode(CipherUtil.encrypt(urlParam1), "UTF-8");
									
							
							TrxBean bean = new TrxBean();
							bean.setTrxId(idEnc);
							bean.setOrderNo(trx.getOrderNo());
							bean.setCustomer(customer);
							bean.setAddress(address.toString());
							bean.setItemId(trxItem.getID().toString());
							bean.setProduct(product);
							bean.setPrice(CommonUtil.currencyIDR(trxItem.getPrice().doubleValue()));
							bean.setStatus(lastStatusItem);
							bean.setSla(sla);
							bean.setUrlParam(urlParams);
							
							list.add(bean);
							
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public TrxBean detail(String itemId) {
		try {
			trxItemDao = new TrxItemDao(sessionFactory);
			List<TrxItem> trxItemList = trxItemDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(itemId)));
			if(trxItemList.size()>0){
				TrxItem trxItem = trxItemList.get(0);
				Trx trx = trxItem.getTrx();
				Product prod = trxItem.getProduct();
				//ProductSku prodSku = trxItem.getProductSku();
				String trxId = trx.getID().toString();
				
				
				String[] customer = new String[4];
				customer[0] = trx.getCustomerId();
				customer[1] = trx.getCustomerName();
				customer[2] = trx.getCustomerEmail();
				customer[3] = trx.getCustomerPhone();
				
				StringBuffer address = new StringBuffer();
				if(trx.getAddress()!=null){
					address.append(trx.getAddress().replace("\n", "<br>"));
					if(trx.getCity()!=null)
						address.append(",<br>");
						address.append(trx.getCity().getName());
					if(trx.getCity()!=null)
						address.append(" - ");
						address.append(trx.getCity().getProvince().getName());
					if(trx.getPostalCode()!=null)
						address.append(", ");
						address.append(trx.getPostalCode());
				}
					
				String[] product = new String[6];
				product[0] = prod.getID().toString();
				product[1] = prod.getName();
				product[2] = /*prodAttr.getID().toString()*/"-";
				product[3] = /*prodAttr.getLabel()*/"-";
				product[4] = /*prodAttr.getSku()*/"-";
				product[5] = /*prodAttr.getRemarks()*/"-";
				
				List<String[]> prodSpecs = new ArrayList<>();
				prodSpecDao = new ProductSpecDao(sessionFactory);
				List<ProductSpec> prodSpecList = prodSpecDao.loadBy(Order.asc("ID"), Restrictions.eq("product", prod));
				for(ProductSpec prodSpec: prodSpecList){
					String[] plspecs = new String[2];
					plspecs[0] = prodSpec.getPcategorySpec().getLabel();
					plspecs[1] = prodSpec.getValue();
					prodSpecs.add(plspecs);
				}
				
				String photoUrl = "https://placehold.it/400x200";
				String[] productImg = new String[1];
				prodImgDao = new ProductImageDao(sessionFactory);
				List<ProductImage> prodImgList = prodImgDao.loadBy(Order.asc("ID"), Restrictions.eq("product", prod));
				System.out.println("prodImgList["+prod.getID()+"]: "+prodImgList.size());
				productImg[0] = photoUrl;
				if(prodImgList.size()>0) {
					ProductImage prodImg = prodImgList.get(0);
					if(prodImg.getFolder()!=null && prodImg.getName()!=null){
						String mainUrl = PropertyMessageUtil.getConfigProperties().getProperty("image.myimages.url");
						String path = PropertyMessageUtil.getConfigProperties().getProperty("image.product.folder.url");
						productImg[0] = mainUrl + path + prodImg.getFolder() + "/" + prodImg.getName();
					}
				}
				
				String[] customMsg = new String[3];
				customMsg[0] = "-";
				customMsg[1] = photoUrl;
				customMsg[2] = "";
				itemMsgDao = new TrxItemMsgDao(sessionFactory);
				List<TrxItemMsg> itemMsgList = itemMsgDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("trxItem", trxItem));
				if(itemMsgList.size()>0){
					TrxItemMsg itemMsg = itemMsgList.get(0);
					customMsg[0] = itemMsg.getMesssage().replace("\n", "<br>");
					
					String mainUrl = PropertyMessageUtil.getConfigProperties().getProperty("image.myimages.url");
					String path = PropertyMessageUtil.getConfigProperties().getProperty("image.item.custommessage.folder.url");
					
					if(itemMsg.getImageName()!=null){
						customMsg[1] = mainUrl + path + itemMsg.getImageFolder() + "/" + itemMsg.getImageName();
					}
					if(itemMsg.getImageName2()!=null){
						customMsg[2] = mainUrl + path + itemMsg.getImageFolder() + "/" + itemMsg.getImageName2();
					}
				}
				
				String[] itemImg = new String[4];
				itemImg[0] = photoUrl;
				itemImg[1] = photoUrl;
				itemImg[2] = trxItem.getReceiverName()!=null?trxItem.getReceiverName():"-";
				itemImg[3] = trxItem.getReceiverPhone()!=null?trxItem.getReceiverPhone():"-";
				
				if(trxItem.getImgFolderQc()!=null && trxItem.getImgNameQc()!=null){
					String mainUrl = PropertyMessageUtil.getConfigProperties().getProperty("image.myimages.url");
					String path = PropertyMessageUtil.getConfigProperties().getProperty("image.item.qc.folder.url");
					itemImg[0] = mainUrl + path + trxItem.getImgFolderQc() + "/" + trxItem.getImgNameQc();
				}
				if(trxItem.getImgFolderPod()!=null && trxItem.getImgNamePod()!=null){
					String mainUrl = PropertyMessageUtil.getConfigProperties().getProperty("image.myimages.url");
					String path = PropertyMessageUtil.getConfigProperties().getProperty("image.item.pod.folder.url");
					itemImg[1] = mainUrl + path + trxItem.getImgFolderPod() + "/" + trxItem.getImgNamePod();
				}
				
				String[] courier = new String[0];
				vendorUserDao = new VendorUserDao(sessionFactory);
				List<VendorUser> venduserList = vendorUserDao.loadBy(Order.asc("ID"), Restrictions.eq("ID", trxItem.getCourierId()));
				if(venduserList.size()>0){
					VendorUser vuser = venduserList.get(0);
					courier = new String[3];
					courier[0] = vuser.getUser().getName();
					courier[1] = vuser.getUser().getPhone();
					courier[2] = vuser.getUser().getEmail();
				}
				
				TrxBean bean = new TrxBean();
				bean.setTrxId(URLEncoder.encode(CipherUtil.encrypt(trxId), "UTF-8"));
				bean.setOrderNo(trx.getOrderNo());
				bean.setItemId(itemId);
				bean.setCustomer(customer);
				bean.setAddress(address.toString());
				bean.setProduct(product);
				bean.setProductImg(productImg);
				bean.setProdSpecs(prodSpecs);
				bean.setCustomMsg(customMsg);
				bean.setItemImg(itemImg);
				bean.setCourier(courier);
				
				return bean;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean toAcknowledged(SessionBean sessionBean, String itemId) {
		try {
			trxItemDao = new TrxItemDao(sessionFactory);
			List<TrxItem> trxItemList = trxItemDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(itemId)));
			if(trxItemList.size()>0){
				TrxItem trxItem = trxItemList.get(0);
			
				String vendorUserId = null;
				if(sessionBean.getUser().getVendors()!=null && sessionBean.getUser().getVendors().length>0){
					String[] vendors = sessionBean.getUser().getVendors();
					vendorUserId = vendors[2];
				}
				
				TrxItemStatus tsi = new TrxItemStatus();
				tsi.setTrxItem(trxItem);
				tsi.setStatus(TRX_ITEM_STATUS.ACKNOWLEDGED);
				tsi.setDate_(Calendar.getInstance(BielUtil.timeZoneJakarta()).getTime());
				tsi.setByVendUser(vendorUserId);
				
				itemStatusDao = new TrxStatusItemDao(sessionFactory);
				itemStatusDao.saveOrUpdate(tsi);
				return true;
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public TrxBean voProcess(String itemId) {
		try {
			trxItemDao = new TrxItemDao(sessionFactory);
			List<TrxItem> trxItemList = trxItemDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(itemId)));
			if(trxItemList.size()>0){
				TrxItem trxItem = trxItemList.get(0);
				Trx trx = trxItem.getTrx();
				Product prod = trxItem.getProduct();
				//ProductAttr prodAttr = trxItem.getProductAttr();
				String trxId = trx.getID().toString();
				
				
				String[] customer = new String[4];
				customer[0] = trx.getCustomerId();
				customer[1] = trx.getCustomerName();
				customer[2] = trx.getCustomerEmail();
				customer[3] = trx.getCustomerPhone();
				
				StringBuffer address = new StringBuffer();
				if(trx.getAddress()!=null){
					address.append(trx.getAddress().replace("\n", "<br>"));
					if(trx.getCity()!=null)
						address.append(",<br>");
						address.append(trx.getCity().getName());
					if(trx.getCity()!=null)
						address.append(" - ");
						address.append(trx.getCity().getProvince().getName());
					if(trx.getPostalCode()!=null)
						address.append(", ");
						address.append(trx.getPostalCode());
				}
				
				String[] product = new String[6];
				product[0] = prod.getID().toString();
				product[1] = prod.getName();
				product[2] = "-";
				product[3] = "-";
				product[4] = "-";
				product[5] = "-";
				
				List<String[]> prodSpecs = new ArrayList<>();
				prodSpecDao = new ProductSpecDao(sessionFactory);
				List<ProductSpec> prodSpecList = prodSpecDao.loadBy(Order.asc("ID"), Restrictions.eq("product", prod));
				for(ProductSpec prodSpec: prodSpecList){
					String[] plspecs = new String[2];
					plspecs[0] = prodSpec.getPcategorySpec().getLabel();
					plspecs[1] = prodSpec.getValue();
					prodSpecs.add(plspecs);
				}
				
				String photoUrl = "https://placehold.it/400x200";
				String[] productImg = new String[1];
				prodImgDao = new ProductImageDao(sessionFactory);
				List<ProductImage> prodImgList = prodImgDao.loadBy(Order.asc("ID"), Restrictions.eq("product", prod));
				System.out.println("prodImgList["+prod.getID()+"]: "+prodImgList.size());
				productImg[0] = photoUrl;
				if(prodImgList.size()>0) {
					ProductImage prodImg = prodImgList.get(0);
					if(prodImg.getFolder()!=null && prodImg.getName()!=null){
						String mainUrl = PropertyMessageUtil.getConfigProperties().getProperty("image.myimages.url");
						String path = PropertyMessageUtil.getConfigProperties().getProperty("image.product.folder.url");
						productImg[0] = mainUrl + path + prodImg.getFolder() + "/" + prodImg.getName();
					}
				}
				
				String[] customMsg = new String[3];
				customMsg[0] = "-";
				customMsg[1] = photoUrl;
				customMsg[2] = "";
				itemMsgDao = new TrxItemMsgDao(sessionFactory);
				List<TrxItemMsg> itemMsgList = itemMsgDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("trxItem", trxItem));
				if(itemMsgList.size()>0){
					TrxItemMsg itemMsg = itemMsgList.get(0);
					customMsg[0] = itemMsg.getMesssage().replace("\n", "<br>");
					String mainUrl = PropertyMessageUtil.getConfigProperties().getProperty("image.myimages.url");
					String path = PropertyMessageUtil.getConfigProperties().getProperty("image.item.custommessage.folder.url");
					if(itemMsg.getImageName()!=null){
						customMsg[1] = mainUrl + path + itemMsg.getImageFolder() + "/" + itemMsg.getImageName();
					}
					if(itemMsg.getImageName2()!=null){
						customMsg[2] = mainUrl + path + itemMsg.getImageFolder() + "/" + itemMsg.getImageName2();
					}
				}
				
				String[] itemImg = new String[4];
				itemImg[0] = photoUrl;
				itemImg[1] = photoUrl;
				itemImg[2] = "-";
				itemImg[3] = "-";
				System.out.println("itemImg: "+new Gson().toJson(itemImg));
				
				TrxBean bean = new TrxBean();
				bean.setTrxId(URLEncoder.encode(CipherUtil.encrypt(trxId), "UTF-8"));
				bean.setOrderNo(trx.getOrderNo());
				bean.setItemId(itemId);
				bean.setCustomer(customer);
				bean.setAddress(address.toString());
				bean.setProduct(product);
				bean.setProductImg(productImg);
				bean.setProdSpecs(prodSpecs);
				bean.setCustomMsg(customMsg);
				bean.setItemImg(itemImg);
				
				return bean;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean readyToQc(SessionBean sessionBean, String itemId, MultipartFile imgQc) {
		try {
			if(imgQc!=null && imgQc.getSize()!=0){
				trxItemDao = new TrxItemDao(sessionFactory);
				List<TrxItem> trxItemList = trxItemDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(itemId)));
				
				if(trxItemList.size()>0){
					TrxItem trxItem = trxItemList.get(0);
					
					String imageLocation = PropertyMessageUtil.getConfigProperties().getProperty("image.myimages.location");
					String imageDestinationFolder = PropertyMessageUtil.getConfigProperties().getProperty("image.item.qc.folder");
					String location = imageLocation + imageDestinationFolder;
					String photoFolder = FastDateFormat.getInstance("yyyyMMdd-HHmmssSSS").format(Calendar.getInstance(BielUtil.timeZoneJakarta()).getTime());
					String photoName = ImageUtil.upload(imgQc, location, photoFolder, null, null);
					System.out.println("location: "+location);
					
					trxItem.setImgFolderQc(photoFolder);
					trxItem.setImgNameQc(photoName);
					trxItemDao = new TrxItemDao(sessionFactory);
					trxItemDao.saveOrUpdate(trxItem);
					
					String vendorUserId = null;
					if(sessionBean.getUser().getVendors()!=null && sessionBean.getUser().getVendors().length>0){
						String[] vendors = sessionBean.getUser().getVendors();
						vendorUserId = vendors[2];
					}
					
					TrxItemStatus tsi = new TrxItemStatus();
					tsi.setTrxItem(trxItem);
					tsi.setStatus(TRX_ITEM_STATUS.READY_QC);
					tsi.setDate_(Calendar.getInstance(BielUtil.timeZoneJakarta()).getTime());
					tsi.setByVendUser(vendorUserId);
					tsi.setIsReadyQc("Y");
					
					itemStatusDao = new TrxStatusItemDao(sessionFactory);
					itemStatusDao.saveOrUpdate(tsi);
					
					threadTask.execute(new MailReadyQCThread(sessionFactory, trxItem));
					
					notifService.trxReadyQCAdd();
					
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public TrxBean toCourierForm(String itemId) {
		try {
			trxItemDao = new TrxItemDao(sessionFactory);
			List<TrxItem> trxItemList = trxItemDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(itemId)));
			if(trxItemList.size()>0){
				TrxItem trxItem = trxItemList.get(0);
				Trx trx = trxItem.getTrx();
				Product prod = trxItem.getProduct();
				//ProductAttr prodAttr = trxItem.getProductAttr();
				
				String[] customer = new String[4];
				customer[0] = trx.getCustomerId();
				customer[1] = trx.getCustomerName();
				customer[2] = trx.getCustomerEmail();
				customer[3] = trx.getCustomerPhone();
				
				StringBuffer address = new StringBuffer();
				if(trx.getAddress()!=null){
					address.append(trx.getAddress().replace("\n", "<br>"));
					if(trx.getCity()!=null)
						address.append(",<br>");
						address.append(trx.getCity().getName());
					if(trx.getCity()!=null)
						address.append(" - ");
						address.append(trx.getCity().getProvince().getName());
					if(trx.getPostalCode()!=null)
						address.append(", ");
						address.append(trx.getPostalCode());
				}
				
				String[] product = new String[6];
				product[0] = prod.getID().toString();
				product[1] = prod.getName();
				product[2] = /*prodAttr.getID().toString()*/"-";
				product[3] = /*prodAttr.getLabel()*/"-";
				product[4] = /*prodAttr.getSku()*/"-";
				product[5] = /*prodAttr.getRemarks()*/"-";
				
				List<String[]> prodSpecs = new ArrayList<>();
				prodSpecDao = new ProductSpecDao(sessionFactory);
				List<ProductSpec> prodSpecList = prodSpecDao.loadBy(Order.asc("ID"), Restrictions.eq("product", prod));
				for(ProductSpec prodSpec: prodSpecList){
					String[] plspecs = new String[2];
					plspecs[0] = prodSpec.getPcategorySpec().getLabel();
					plspecs[1] = prodSpec.getValue();
					prodSpecs.add(plspecs);
				}
				
				String photoUrl = "https://placehold.it/400x200";
				String[] customMsg = new String[3];
				customMsg[0] = "-";
				customMsg[1] = photoUrl;
				customMsg[2] = "";
				itemMsgDao = new TrxItemMsgDao(sessionFactory);
				List<TrxItemMsg> itemMsgList = itemMsgDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("trxItem", trxItem));
				if(itemMsgList.size()>0){
					TrxItemMsg itemMsg = itemMsgList.get(0);
					customMsg[0] = itemMsg.getMesssage().replace("\n", "<br>");
					String mainUrl = PropertyMessageUtil.getConfigProperties().getProperty("image.myimages.url");
					String path = PropertyMessageUtil.getConfigProperties().getProperty("image.item.custommessage.folder.url");
					if(itemMsg.getImageName()!=null){
						customMsg[1] = mainUrl + path + itemMsg.getImageFolder() + "/" + itemMsg.getImageName();
					}
					if(itemMsg.getImageName2()!=null){
						customMsg[2] = mainUrl + path + itemMsg.getImageFolder() + "/" + itemMsg.getImageName2();
					}
				}
				
				String[] itemImg = new String[4];
				itemImg[0] = photoUrl; //QC
				itemImg[1] = photoUrl; //POD
				itemImg[2] = "-";
				itemImg[3] = "-";
				if(trxItem.getImgFolderQc()!=null && trxItem.getImgNameQc()!=null){
					String mainUrl = PropertyMessageUtil.getConfigProperties().getProperty("image.myimages.url");
					String path = PropertyMessageUtil.getConfigProperties().getProperty("image.item.qc.folder.url");
					itemImg[0] = mainUrl + path + trxItem.getImgFolderQc() + "/" + trxItem.getImgNameQc();
				}
				
				List<String[]> couriers = new ArrayList<>();
				vendorUserDao = new VendorUserDao(sessionFactory);
				Criterion cr1 = Restrictions.eq("vendor", trxItem.getVendor());
				Criterion cr2 = Restrictions.eq("type", VENDOR_USER_TYPE.COURIER);
				List<VendorUser> venduserList = vendorUserDao.loadBy(Order.asc("ID"), cr1, cr2);
				for(VendorUser vu: venduserList){
					String[] vuarr = new String[2];
					vuarr[0] = vu.getID().toString();
					vuarr[1] = vu.getUser().getName();
					couriers.add(vuarr);
				}
				
				TrxBean bean = new TrxBean();
				bean.setOrderNo(trx.getOrderNo());
				bean.setItemId(itemId);
				bean.setCustomer(customer);
				bean.setAddress(address.toString());
				bean.setProduct(product);
				bean.setProdSpecs(prodSpecs);
				bean.setCustomMsg(customMsg);
				bean.setItemImg(itemImg);
				bean.setCouriers(couriers);
				
				return bean;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean toCourierSubmit(SessionBean sessionBean, String itemId, String courierId) {
		try {
			trxItemDao = new TrxItemDao(sessionFactory);
			List<TrxItem> trxItemList = trxItemDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(itemId)));
			if(trxItemList.size()>0){
				TrxItem trxItem = trxItemList.get(0);
				trxItem.setCourierId(Long.valueOf(courierId));
				trxItemDao = new TrxItemDao(sessionFactory);
				trxItemDao.saveOrUpdate(trxItem);
				
				String vendorUserId = null;
				String operatorName = sessionBean.getUser().getName();
				String opeatorEmail = sessionBean.getUser().getEmail();
				if(sessionBean.getUser().getVendors()!=null && sessionBean.getUser().getVendors().length>0){
					String[] vendors = sessionBean.getUser().getVendors();
					vendorUserId = vendors[2];
				}
				
				TrxItemStatus tsi = new TrxItemStatus();
				tsi.setTrxItem(trxItem);
				tsi.setStatus(TRX_ITEM_STATUS.ON_COURIER);
				tsi.setDate_(Calendar.getInstance(BielUtil.timeZoneJakarta()).getTime());
				tsi.setByVendUser(vendorUserId);
				
				itemStatusDao = new TrxStatusItemDao(sessionFactory);
				itemStatusDao.saveOrUpdate(tsi);
				
				threadTask.execute(new CourierAssignedThread(sessionFactory, trxItem, operatorName, opeatorEmail, courierId));
				
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public TrxBean podForm(String itemId) {
		try {
			trxItemDao = new TrxItemDao(sessionFactory);
			List<TrxItem> trxItemList = trxItemDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(itemId)));
			if(trxItemList.size()>0){
				TrxItem trxItem = trxItemList.get(0);
				Trx trx = trxItem.getTrx();
				Product prod = trxItem.getProduct();
				//ProductAttr prodAttr = trxItem.getProductAttr();
				
				String[] customer = new String[4];
				customer[0] = trx.getCustomerId();
				customer[1] = trx.getCustomerName();
				customer[2] = trx.getCustomerEmail();
				customer[3] = trx.getCustomerPhone();
				
				StringBuffer address = new StringBuffer();
				if(trx.getAddress()!=null){
					address.append(trx.getAddress().replace("\n", "<br>"));
					if(trx.getCity()!=null)
						address.append(",<br>");
						address.append(trx.getCity().getName());
					if(trx.getCity()!=null)
						address.append(" - ");
						address.append(trx.getCity().getProvince().getName());
					if(trx.getPostalCode()!=null)
						address.append(", ");
						address.append(trx.getPostalCode());
				}
				
				String[] product = new String[6];
				product[0] = prod.getID().toString();
				product[1] = prod.getName();
				product[2] = /*prodAttr.getID().toString()*/"-";
				product[3] = /*prodAttr.getLabel()*/"-";
				product[4] = /*prodAttr.getSku()*/"-";
				product[5] = /*prodAttr.getRemarks()*/"-";
				
				List<String[]> prodSpecs = new ArrayList<>();
				prodSpecDao = new ProductSpecDao(sessionFactory);
				List<ProductSpec> prodSpecList = prodSpecDao.loadBy(Order.asc("ID"), Restrictions.eq("product", prod));
				for(ProductSpec prodSpec: prodSpecList){
					String[] plspecs = new String[2];
					plspecs[0] = prodSpec.getPcategorySpec().getLabel();
					plspecs[1] = prodSpec.getValue();
					prodSpecs.add(plspecs);
				}
				
				String photoUrl = "https://placehold.it/400x200";
				String[] customMsg = new String[3];
				customMsg[0] = "-";
				customMsg[1] = photoUrl;
				customMsg[2] = "";
				itemMsgDao = new TrxItemMsgDao(sessionFactory);
				List<TrxItemMsg> itemMsgList = itemMsgDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("trxItem", trxItem));
				if(itemMsgList.size()>0){
					TrxItemMsg itemMsg = itemMsgList.get(0);
					customMsg[0] = itemMsg.getMesssage().replace("\n", "<br>");
					String mainUrl = PropertyMessageUtil.getConfigProperties().getProperty("image.myimages.url");
					String path = PropertyMessageUtil.getConfigProperties().getProperty("image.item.custommessage.folder.url");
					if(itemMsg.getImageName()!=null){
						customMsg[1] = mainUrl + path + itemMsg.getImageFolder() + "/" + itemMsg.getImageName();
					}
					if(itemMsg.getImageName2()!=null){
						customMsg[2] = mainUrl + path + itemMsg.getImageFolder() + "/" + itemMsg.getImageName2();
					}
				}
				
				String[] itemImg = new String[4];
				itemImg[0] = photoUrl; //QC
				itemImg[1] = photoUrl; //POD
				itemImg[2] = "-";
				itemImg[3] = "-";
				if(trxItem.getImgFolderQc()!=null && trxItem.getImgNameQc()!=null){
					String mainUrl = PropertyMessageUtil.getConfigProperties().getProperty("image.myimages.url");
					String path = PropertyMessageUtil.getConfigProperties().getProperty("image.item.qc.folder.url");
					itemImg[0] = mainUrl + path + trxItem.getImgFolderQc() + "/" + trxItem.getImgNameQc();
				}
				
				String[] courier = new String[0];
				vendorUserDao = new VendorUserDao(sessionFactory);
				List<VendorUser> venduserList = vendorUserDao.loadBy(Order.asc("ID"), Restrictions.eq("ID", trxItem.getCourierId()));
				if(venduserList.size()>0){
					VendorUser vuser = venduserList.get(0);
					courier = new String[3];
					courier[0] = vuser.getUser().getName();
					courier[1] = vuser.getUser().getPhone();
					courier[2] = vuser.getUser().getEmail();
				}
				
				
				TrxBean bean = new TrxBean();
				bean.setOrderNo(trx.getOrderNo());
				bean.setItemId(itemId);
				bean.setCustomer(customer);
				bean.setAddress(address.toString());
				bean.setProduct(product);
				bean.setProdSpecs(prodSpecs);
				bean.setCustomMsg(customMsg);
				bean.setItemImg(itemImg);
				bean.setCourier(courier);
				
				return bean;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean podFormSubmit(SessionBean sessionBean, String itemId, MultipartFile imgPod, String receiverName,
			String receiverPhone) {
		try {
			if(imgPod!=null && imgPod.getSize()!=0){
				trxItemDao = new TrxItemDao(sessionFactory);
				List<TrxItem> trxItemList = trxItemDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(itemId)));
				
				if(trxItemList.size()>0){
					TrxItem trxItem = trxItemList.get(0);
					
					String imageLocation = PropertyMessageUtil.getConfigProperties().getProperty("image.myimages.location");
					String imageDestinationFolder = PropertyMessageUtil.getConfigProperties().getProperty("image.item.pod.folder");
					String location = imageLocation + imageDestinationFolder;
					String photoFolder = FastDateFormat.getInstance("yyyyMMdd-HHmmssSSS").format(Calendar.getInstance(BielUtil.timeZoneJakarta()).getTime());
					String photoName = ImageUtil.upload(imgPod, location, photoFolder, null, null);
					
					trxItem.setImgFolderPod(photoFolder);
					trxItem.setImgNamePod(photoName);
					trxItem.setReceiverName(receiverName!=null&&!receiverName.isEmpty()?receiverName:null);
					trxItem.setReceiverPhone(receiverPhone!=null&&!receiverPhone.isEmpty()?receiverPhone:null);
					trxItemDao = new TrxItemDao(sessionFactory);
					trxItemDao.saveOrUpdate(trxItem);
					
					String vendorUserId = null;
					if(sessionBean.getUser().getVendors()!=null && sessionBean.getUser().getVendors().length>0){
						String[] vendors = sessionBean.getUser().getVendors();
						vendorUserId = vendors[2];
					}
					
					TrxItemStatus tsi = new TrxItemStatus();
					tsi.setTrxItem(trxItem);
					tsi.setStatus(TRX_ITEM_STATUS.DELIVERED);
					tsi.setDate_(Calendar.getInstance(BielUtil.timeZoneJakarta()).getTime());
					tsi.setByVendUser(vendorUserId);
					
					itemStatusDao = new TrxStatusItemDao(sessionFactory);
					itemStatusDao.saveOrUpdate(tsi);
					
					TrxStatus trxStatus = new TrxStatus();
					trxStatus.setDate_(Calendar.getInstance(BielUtil.timeZoneJakarta()).getTime());
					trxStatus.setTrx(trxItem.getTrx());
					trxStatus.setStatus(TRX_STATUS.DONE);
					
					trxStatusDao = new TrxStatusDao(sessionFactory);
					trxStatusDao.saveOrUpdate(trxStatus);
					
					String courierName = sessionBean.getUser().getName();
					threadTask.execute(new OrderDeliveredThread(sessionFactory, trxItem, courierName, receiverName, receiverPhone));
					
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	

}
