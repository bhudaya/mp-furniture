package centmp.depotmebel.adminpartner.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
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
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.bilmajdi.util.BielUtil;
import com.bilmajdi.util.CipherUtil;
import com.bilmajdi.util.PropertyMessageUtil;
import com.google.gson.Gson;

import centmp.depotmebel.adminpartner.bean.OrderBean;
import centmp.depotmebel.adminpartner.bean.OrderItemBean;
import centmp.depotmebel.adminpartner.bean.OrderListBean;
import centmp.depotmebel.adminpartner.service.NotifService;
import centmp.depotmebel.adminpartner.service.OrderService;
import centmp.depotmebel.adminpartner.thread.MailPassQCThread;
import centmp.depotmebel.adminpartner.thread.TrxSetVendorThread;
import centmp.depotmebel.core.dao.ProductImageDao;
import centmp.depotmebel.core.dao.TrxDao;
import centmp.depotmebel.core.dao.TrxDeductionDao;
import centmp.depotmebel.core.dao.TrxItemDao;
import centmp.depotmebel.core.dao.TrxItemMsgDao;
import centmp.depotmebel.core.dao.TrxStatusDao;
import centmp.depotmebel.core.dao.TrxStatusItemDao;
import centmp.depotmebel.core.dao.VendorCrdLimitDao;
import centmp.depotmebel.core.dao.VendorDao;
import centmp.depotmebel.core.dao.VendorDlverLimitDao;
import centmp.depotmebel.core.dao.VendorUserDao;
import centmp.depotmebel.core.enums.TRX_ITEM_STATUS;
import centmp.depotmebel.core.enums.TRX_STATUS;
import centmp.depotmebel.core.model.City;
import centmp.depotmebel.core.model.Product;
import centmp.depotmebel.core.model.ProductImage;
import centmp.depotmebel.core.model.ProductSku;
import centmp.depotmebel.core.model.Trx;
import centmp.depotmebel.core.model.TrxDeduction;
import centmp.depotmebel.core.model.TrxItem;
import centmp.depotmebel.core.model.TrxItemMsg;
import centmp.depotmebel.core.model.TrxItemStatus;
import centmp.depotmebel.core.model.TrxStatus;
import centmp.depotmebel.core.model.Vendor;
import centmp.depotmebel.core.model.VendorCrdLimit;
import centmp.depotmebel.core.model.VendorDlverLimit;
import centmp.depotmebel.core.model.VendorUser;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.core.util.ConstantApp;

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
	
	private TrxDao trxDao;
	private TrxStatusDao trxStatusDao;
	private TrxDeductionDao trxDeductionDao;
	private TrxItemDao trxItemDao;
	private TrxItemMsgDao itemMsgDao;
	private TrxStatusItemDao itemStatusDao;
	private VendorDao vendorDao;
	private VendorUserDao vendorUserDao;
	private ProductImageDao prodImgDao;

	@Override
	public OrderListBean list() {
		OrderListBean bean = new OrderListBean();
		List<OrderBean> allList = new ArrayList<>();
		Integer numUnallocated = 0;
		
		try {
			String sqlQuery = "SELECT COUNT(*) FROM trx_ a, trxitem_ b, trx_status c "
					+ "WHERE a.ID = b.trx AND a.ID = c.trx AND b.vendor IS NULL AND c.status_ = '1'";
			trxItemDao = new TrxItemDao(sessionFactory);
			List<?> numUnallocatedList = trxItemDao.nativeQuery(sqlQuery);
			System.out.println("numUnallocatedList: "+numUnallocatedList);
			BigInteger numUnallocatedBi = (BigInteger) numUnallocatedList.get(0);
			numUnallocated = numUnallocatedBi!=null?numUnallocatedBi.intValue():0;
			
			trxItemDao = new TrxItemDao(sessionFactory);
			Criterion cr1 = Restrictions.isNotNull("trx");
			List<TrxItem> trxItemList = trxItemDao.loadBy(Order.desc("ID"), 500, cr1);
			System.out.println("trxItemList: "+trxItemList.size());
			for (TrxItem trxItem: trxItemList) {
				Trx trx = trxItem.getTrx();
				
				String address = "-";
				if(trx.getAddress()!=null){
					StringBuffer addressBuff = new StringBuffer();
					addressBuff.append(trx.getAddress().replace("\n", "<br>"));
					addressBuff.append(" ");
					if(trx.getCity()!=null)addressBuff.append(trx.getCity().getName());
					if(trx.getCity()!=null)addressBuff.append(" - ");
					if(trx.getCity()!=null)addressBuff.append(trx.getCity().getProvince().getName());
					if(trx.getPostalCode()!=null&&!trx.getPostalCode().isEmpty())addressBuff.append(", ");
					if(trx.getPostalCode()!=null&&!trx.getPostalCode().isEmpty())addressBuff.append(trx.getPostalCode());
				}
				
				
				String[] customer = new String[5];
				customer[0] = trx.getCustomerId();
				customer[1] = trx.getCustomerName();
				customer[2] = trx.getCustomerEmail();
				customer[3] = trx.getCustomerPhone();
				customer[4] = address.toString().trim();
				
				String[] itemArr = new String[2];
				itemArr[0] = trxItem.getProduct().getName();
				itemArr[1] = trxItem.getProductSku().getLabel();
				
				trxStatusDao = new TrxStatusDao(sessionFactory);
				List<TrxStatus> statusList = trxStatusDao.loadBy(Order.desc("ID"), 1, Restrictions.eq("trx", trx));
				TrxStatus trxStatus = statusList.size()>0?statusList.get(0):null;
				
				String sla = "-";
				String[] status = new String[0];
				if(trxStatus!=null){
					status = new String[3];
					status[0] = Integer.toString(trxStatus.getStatus().ordinal());
					status[1] = TRX_STATUS.getValue(trxStatus.getStatus().ordinal()).toUpperCase();
					status[2] = FastDateFormat.getInstance("dd/MM/yyy, HH:mm").format(trxStatus.getDate_());
					
					if(trxStatus.getStatus()==TRX_STATUS.PAID){
						
						//status item, start to 4
						itemStatusDao = new TrxStatusItemDao(sessionFactory);
						List<TrxItemStatus> statusItemList = itemStatusDao.loadBy(Order.desc("ID"), 1, Restrictions.eq("trxItem", trxItem));
						if(statusItemList.size()>0){
							TrxItemStatus statusItem = statusItemList.get(0);
							System.out.println("statusItem: "+statusItem.getStatus().name());
							if(statusItem.getStatus()!=TRX_ITEM_STATUS.ON_VENDOR){
								status[0] = Integer.toString(statusItem.getStatus().ordinal()+4);
								status[1] = TRX_ITEM_STATUS.getValue(statusItem.getStatus().ordinal()).toUpperCase();
								status[2] = FastDateFormat.getInstance("dd/MM/yyy, HH:mm").format(statusItem.getDate_());
							}
						}
					}
				}
				
				OrderBean orderban = new OrderBean();
				orderban.setId(URLEncoder.encode(CipherUtil.encrypt(ConstantApp.TRX_ID_COMBINE+trx.getID().toString()), "UTF-8"));
				orderban.setNo(trx.getOrderNo());
				orderban.setCustomer(customer);
				orderban.setVendor(trxItem.getVendor()!=null?trxItem.getVendor().getName():"-");
				orderban.setStatus(status);
				orderban.setItemArray(itemArr);
				orderban.setCreated(FastDateFormat.getInstance("dd/MM/yyyy, HH:mm").format(trx.getCreatedTime()));
				orderban.setSla(sla);
				allList.add(orderban);
			}
			
			bean.setAllList(allList);
			bean.setUnalloactedNum(numUnallocated);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean;
	}

	@Override
	public OrderBean detail(String idencr) {
		OrderBean bean = new OrderBean();
		
		try {
			String decrypt = CipherUtil.decrypt(idencr); 
			String id = decrypt.replace(ConstantApp.TRX_ID_COMBINE, "");
			
			trxDao = new TrxDao(sessionFactory);
			List<Trx> trxList = trxDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(id)));
			if(trxList.size()>0){
				Trx trx = trxList.get(0);
				City city = trx.getCity();
				String postalCode = trx.getPostalCode();
				Criterion crTrx = Restrictions.eq("trx", trx);
				
				String[] customer = new String[4];
				customer[0] = trx.getCustomerId();
				customer[1] = trx.getCustomerName();
				customer[2] = trx.getCustomerEmail();
				customer[3] = trx.getCustomerPhone();
				
				StringBuffer address = new StringBuffer();
				if(trx.getAddress()!=null){
					address.append(trx.getAddress().replace("\n", "<br>"));
					if(city!=null) {
						address.append(" - "+city.getName());
						address.append(" - "+city.getProvince().getName());
					}
					if(postalCode!=null) address.append(", "+postalCode);
				}
				
				List<String[]> statusList = new ArrayList<>();
				trxStatusDao = new TrxStatusDao(sessionFactory);
				List<TrxStatus> trxStatusList = trxStatusDao.loadBy(Order.asc("ID"), crTrx);
				for(TrxStatus trxStatus: trxStatusList){
					String notes = "-";
					if(trxStatus.getStatus()==TRX_STATUS.PAID){
						notes = "PadiPay Code: "+trxStatus.getPadipayCode();
					}
					
					String[] status = new String[4];
					status[0] = Integer.toString(trxStatus.getStatus().ordinal());
					status[1] = TRX_STATUS.getValue(trxStatus.getStatus().ordinal()).toUpperCase();
					status[2] = FastDateFormat.getInstance("dd/MM/yyyy, HH:mm").format(trxStatus.getDate_());
					status[3] = notes;
					statusList.add(status);
				}
				String[] lastStatusList = statusList.get(statusList.size()-1);
				System.out.println("lastStatusList: "+new Gson().toJson(lastStatusList));
				
				List<String[]> itemsUnalloc = new ArrayList<>();
				List<OrderItemBean> itemBeanList = new ArrayList<>();
				BigDecimal amountTrx = BigDecimal.ZERO;
				
				trxItemDao = new TrxItemDao(sessionFactory);
				List<TrxItem> trxItemList = trxItemDao.loadBy(Order.asc("ID"), crTrx);
				for(TrxItem trxItem: trxItemList){
					Vendor vendor = trxItem.getVendor();
					String itemId = trxItem.getID().toString();
					Product prod = trxItem.getProduct();
					ProductSku prodSku = trxItem.getProductSku();
					Integer quantity = trxItem.getQuantity();
					BigDecimal price = trxItem.getPrice();
					amountTrx = amountTrx.add(price);
					
					String lastItemStatus = "new";
					if(vendor!=null){
						itemStatusDao = new TrxStatusItemDao(sessionFactory);
						List<TrxItemStatus> itemStatusList = itemStatusDao.loadBy(Order.desc("ID"), 1, Restrictions.eq("trxItem", trxItem));
						if(itemStatusList.size()>0){
							TrxItemStatus tsi = itemStatusList.get(0);
							lastItemStatus = TRX_ITEM_STATUS.getValue(tsi.getStatus().ordinal());
						}
					}
					
					
					if(vendor==null && lastStatusList[0].equalsIgnoreCase("1")){//Sudah dibayar
						String[] arr = new String[8];
						arr[0] = itemId;
						arr[1] = prod.getID().toString();
						arr[2] = prod.getName();
						arr[3] = vendor!=null?vendor.getID().toString():"";
						arr[4] = vendor!=null?vendor.getName():"-";
						arr[5] = quantity.toString();
						arr[6] = CommonUtil.currencyIDR(price.doubleValue());
						arr[7] = lastItemStatus;
						itemsUnalloc.add(arr);
					}
					
					
					String[] productArr = new String[2];
					productArr[0] = prod.getID().toString();
					productArr[1] = prod.getName();
					
					String[] productSkuArr = new String[3];
					productSkuArr[0] = prodSku.getID().toString();
					productSkuArr[1] = prodSku.getLabel();
					productSkuArr[2] = prodSku.getSku();
					
					String[] vendorArr = new String[2];
					vendorArr[0] = vendor!=null?vendor.getID().toString():"";
					vendorArr[1] = vendor!=null?vendor.getName():"-";
					
					List<String[]> statusHistory = new ArrayList<>();
					itemStatusDao = new TrxStatusItemDao(sessionFactory);
					List<TrxItemStatus> itemStatusList = itemStatusDao.loadBy(Order.asc("ID"), Restrictions.eq("trxItem", trxItem));
					for(TrxItemStatus statusItem: itemStatusList){
						
						int ordinal = statusItem.getStatus().ordinal();
						String class_ = "";
						if(ordinal==0){
							class_ = "text-red";
						}else if(ordinal==1){
							class_ = "text-orange";
						}else if(ordinal==2){
							class_ = "text-blue";
						}else if(ordinal==3){
							class_ = "text-blue";
						}else if(ordinal==4){
							class_ = "text-yellow";
						}else if(ordinal==5){
							class_ = "text-green";
						}
						
						String userId = "";
						if(statusItem.getByVendUser()!=null){
							vendorUserDao = new VendorUserDao(sessionFactory);
							List<VendorUser> vuList = vendorUserDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(statusItem.getByVendUser())));
							if(vuList.size()>0){
								VendorUser vuser = vuList.get(0);
								userId = " by "+vuser.getUser().getName();
							}
						}
						
						String[] shArr = new String[5];
						shArr[0] = Integer.toString(ordinal);
						shArr[1] = TRX_ITEM_STATUS.getValue(statusItem.getStatus().ordinal()).toUpperCase();
						shArr[2] = FastDateFormat.getInstance("dd/MM/yyyy, HH:mm").format(statusItem.getDate_());
						shArr[3] = userId;
						shArr[4] = class_;
						statusHistory.add(shArr);
						
						if(ordinal==5){
							String recvName = trxItem.getReceiverName()!=null?trxItem.getReceiverName():"";
							String recvPhone = trxItem.getReceiverPhone()!=null?"("+trxItem.getReceiverPhone()+")":"";
							
							String[] shArr_ = new String[5];
							shArr_[0] = Integer.toString(ordinal);
							shArr_[1] = "RECEIVED";
							shArr_[2] = FastDateFormat.getInstance("dd/MM/yyyy, HH:mm").format(statusItem.getDate_());
							shArr_[3] = "by "+recvName+" "+recvPhone;
							shArr_[4] = "text-green";
							statusHistory.add(shArr_);
						}
					}
					
					String imgDefaultUrl = "https://placehold.it/120x90";
					String imgCmUrl = imgDefaultUrl;
					String imgCmUrl2 = imgDefaultUrl;
					String imgQcUrl = imgDefaultUrl;
					String imgPodUrl = imgDefaultUrl;
					
					if(trxItem.getImgFolderQc()!=null&&trxItem.getImgNameQc()!=null){
						String mainUrl = PropertyMessageUtil.getConfigProperties().getProperty("image.myimages.url");
						String path = PropertyMessageUtil.getConfigProperties().getProperty("image.item.qc.folder.url");
						imgQcUrl = mainUrl + path + trxItem.getImgFolderQc() + "/" + trxItem.getImgNameQc();
					}
					
					if(trxItem.getImgFolderPod()!=null&&trxItem.getImgNamePod()!=null){
						String mainUrl = PropertyMessageUtil.getConfigProperties().getProperty("image.myimages.url");
						String path = PropertyMessageUtil.getConfigProperties().getProperty("image.item.pod.folder.url");
						imgPodUrl = mainUrl + path + trxItem.getImgFolderPod() + "/" + trxItem.getImgNamePod();
					}
					

					String cm = "-";
					itemMsgDao = new TrxItemMsgDao(sessionFactory);
					List<TrxItemMsg> itemMsgList = itemMsgDao.loadBy(Order.desc("ID"), 1, Restrictions.eq("trxItem", trxItem));
					if(itemMsgList.size()>0){
						TrxItemMsg itemMsg = itemMsgList.get(0);
						cm = itemMsg.getMesssage()!=null?itemMsg.getMesssage():"";
						imgCmUrl = "";imgCmUrl2 = "";
						
						String mainUrl = PropertyMessageUtil.getConfigProperties().getProperty("image.myimages.url");
						String path = PropertyMessageUtil.getConfigProperties().getProperty("image.item.custommessage.folder.url");
						
						if(itemMsg.getImageName()!=null){
							imgCmUrl = mainUrl + path + itemMsg.getImageFolder() + "/" + itemMsg.getImageName();
						}
						if(itemMsg.getImageName2()!=null){
							imgCmUrl2 = mainUrl + path + itemMsg.getImageFolder() + "/" + itemMsg.getImageName2();
						}
					}
					
					String[] customMsg = new String[3];
					customMsg[0] = cm.replace("\n", "<br>");
					customMsg[1] = imgCmUrl;
					customMsg[2] = imgCmUrl2;
					
					OrderItemBean itemBean = new OrderItemBean();
					itemBean.setProduct(productArr);
					itemBean.setProdAttr(productSkuArr);
					itemBean.setPrice(CommonUtil.currencyIDR(trxItem.getPrice().doubleValue()));
					itemBean.setVendor(vendorArr);
					itemBean.setStatusHistory(statusHistory);
					itemBean.setImgQcUrl(imgQcUrl);
					itemBean.setImgPodUrl(imgPodUrl);
					itemBean.setCustomMsg(customMsg);
					itemBeanList.add(itemBean);
				}
				
				BigDecimal amountDisc = BigDecimal.ZERO;
				trxDeductionDao = new TrxDeductionDao(sessionFactory);
				List<TrxDeduction> trxDeductList = trxDeductionDao.loadBy(Order.desc("ID"), 1, Restrictions.eq("trx", trx));
				if(trxDeductList.size()>0){
					TrxDeduction trxdeduct = trxDeductList.get(0);
					amountDisc = trxdeduct.getVoucherAmount();
				}
				
				String[] amounts = new String[3];
				amounts[0] = CommonUtil.currencyIDR(amountTrx.doubleValue());
				amounts[1] = CommonUtil.currencyIDR(amountDisc.doubleValue());
				amounts[2] = CommonUtil.currencyIDR(trx.getAmount().doubleValue());
				
				bean.setId(id);
				bean.setNo(trx.getOrderNo());
				bean.setCustomer(customer);
				bean.setAddress(address.toString());
				bean.setNotes(trx.getNotes()!=null&&!trx.getNotes().isEmpty()?trx.getNotes():"-");
				bean.setCreated(FastDateFormat.getInstance("dd/MM/yyyy HH:mm").format(trx.getCreatedTime()));
				bean.setStatusList(statusList);
				bean.setItemsUnalloc(itemsUnalloc);
				bean.setItemBeanList(itemBeanList);
				bean.setAmounts(amounts);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	@Override
	public OrderListBean unallocatedList() {
		OrderListBean bean = new OrderListBean();
		List<OrderBean> unallocatedList = new ArrayList<>();
		
		try {
			Criterion crTrxNoNull = Restrictions.isNotNull("trx");
			Criterion crVendorNull = Restrictions.isNull("vendor");
			Criterion crTrxStatusPaid = Restrictions.eq("status", TRX_STATUS.PAID);
			
			trxItemDao = new TrxItemDao(sessionFactory);
			List<TrxItem> itemUnallocatedList = trxItemDao.loadBy(Order.desc("ID"), 50, crTrxNoNull, crVendorNull);
			for(TrxItem ti: itemUnallocatedList){
				Trx trx = ti.getTrx();
				
				trxStatusDao = new TrxStatusDao(sessionFactory);
				List<TrxStatus> trxStatusList = trxStatusDao.loadBy(Order.desc("ID"), 1, Restrictions.eq("trx", trx), crTrxStatusPaid);
				if(trxStatusList.size()>0){
					TrxStatus trxStatus = trxStatusList.get(0);
					
					String[] customer = new String[4];
					customer[0] = trx.getCustomerId();
					customer[1] = trx.getCustomerName();
					customer[2] = trx.getCustomerEmail();
					customer[3] = trx.getCustomerPhone();
					
					String[] status = new String[0];
					if(trxStatus!=null){
						status = new String[2];
						status[0] = TRX_STATUS.getValue(trxStatus.getStatus().ordinal()).toUpperCase();
						status[1] = FastDateFormat.getInstance("dd/MM/yyy, HHmm").format(trxStatus.getDate_());
					}
					
					OrderBean orderban = new OrderBean();
					orderban.setId(URLEncoder.encode(CipherUtil.encrypt(trx.getID().toString()),"UTF-8"));
					orderban.setNo(trx.getOrderNo());
					orderban.setCustomer(customer);
					orderban.setStatus(status);
					unallocatedList.add(orderban);
				}
			}
			bean.setUnalloactedList(unallocatedList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean;
	}
	
	@Override
	public String setVendor(String itemId, String vendorId) {
		try {
			trxItemDao = new TrxItemDao(sessionFactory);
			List<TrxItem> trxItemList = trxItemDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(itemId)));
			if(trxItemList.size()>0){
				TrxItem item = trxItemList.get(0);
				
				vendorDao = new VendorDao(sessionFactory);
				List<Vendor> vendorList = vendorDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(vendorId)));
				if(vendorList.size()>0){
					Vendor vendor = vendorList.get(0);
					
					item.setVendor(vendor);
					trxItemDao = new TrxItemDao(sessionFactory);
					trxItemDao.saveOrUpdate(item);
					
					TrxItemStatus statusItem = new TrxItemStatus();
					statusItem.setDate_(Calendar.getInstance(BielUtil.timeZoneJakarta()).getTime());
					statusItem.setStatus(TRX_ITEM_STATUS.ON_VENDOR);
					statusItem.setTrxItem(item);
					itemStatusDao = new TrxStatusItemDao(sessionFactory);
					itemStatusDao.saveOrUpdate(statusItem);
					
					/*
					 * Update SLA -Start
					 */
					Criterion crVend = Restrictions.eq("vendor", vendor);
					
					VendorDlverLimitDao capctyLmtDao = new VendorDlverLimitDao(sessionFactory);
					List<VendorDlverLimit> capctyLmtList = capctyLmtDao.loadBy(Order.asc("ID"), 1, crVend);
					if(capctyLmtList.size()>0){
						VendorDlverLimit dlverLimit = capctyLmtList.get(0);
						Integer before = dlverLimit.getBalance();
						Integer update = before-item.getQuantity();
						
						dlverLimit.setBalance(update);
						capctyLmtDao = new VendorDlverLimitDao(sessionFactory);
						capctyLmtDao.saveOrUpdate(dlverLimit);
					}
					
					VendorCrdLimitDao crdLmitDao = new VendorCrdLimitDao(sessionFactory);
					List<VendorCrdLimit> crdLmitList = crdLmitDao.loadBy(Order.asc("ID"), 1, crVend);
					if(crdLmitList.size()>0){
						VendorCrdLimit crdLimit = crdLmitList.get(0);
						BigDecimal before = crdLimit.getBalance();
						BigDecimal update = before.subtract(item.getProduct().getBasePrice());
						
						crdLimit.setBalance(update);
						crdLmitDao = new VendorCrdLimitDao(sessionFactory);
						crdLmitDao.saveOrUpdate(crdLimit);
					}
					/*
					 * Update SLA -End
					 */
					
					
					//Send Email Set/Assign Vendor
					Calendar calnow = Calendar.getInstance(BielUtil.timeZoneJakarta());
					threadTask.execute(new TrxSetVendorThread(sessionFactory, item, calnow.getTime(), vendor));
					
					String[] items = new String[5];
					items[0] = item.getProduct().getName();
					items[1] = vendor.getName();
					items[2] = item.getQuantity().toString();
					items[3] = CommonUtil.currencyIDR(item.getPrice().doubleValue());
					items[4] = TRX_ITEM_STATUS.getValue(TRX_ITEM_STATUS.ON_VENDOR.ordinal());
					String gson = new Gson().toJson(items);
					return gson;
					
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public int readyQcNum() {
		try {
			itemStatusDao = new TrxStatusItemDao(sessionFactory);
			Criterion cr1 = Restrictions.eq("isReadyQc", "Y");
			int num = itemStatusDao.rowCount(cr1);
			System.out.println("readyQcNum: "+num);
			return num;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public OrderListBean qcList() {
		OrderListBean listbean = new OrderListBean();
		
		try {
			itemStatusDao = new TrxStatusItemDao(sessionFactory);
			List<TrxItemStatus> itemStatusList = itemStatusDao.loadBy(Order.asc("ID"), Restrictions.eq("isReadyQc", "Y"));
			
			List<OrderBean> allList = new ArrayList<>();
			for(TrxItemStatus tsi: itemStatusList){
				TrxItem trxItem = tsi.getTrxItem();
				Trx trx = trxItem.getTrx();
				
				System.out.println("trxItem: "+trxItem.getID());
				System.out.println("Vendor: "+trxItem.getVendor());
				
				String[] items = new String[4];
				items[0] = URLEncoder.encode(CipherUtil.encrypt(trxItem.getID().toString()), "UTF-8") ; //ItemId
				items[1] = /*trxItem.getProductAttr().getLabel()*/"-"; //Item Name
				items[2] = trxItem.getProduct().getName(); //Product Name
				items[3] = trxItem.getVendor().getName(); //Vendor Name
				
				OrderBean orderbean = new OrderBean();
				orderbean.setNo(trx.getOrderNo());
				orderbean.setItemArray(items);
				allList.add(orderbean);
			}
			
			listbean.setAllList(allList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listbean;
	}

	@Override
	public OrderBean qcDetail(String itemId) {
		OrderBean bean = new OrderBean();
		
		try {
			
			trxItemDao = new TrxItemDao(sessionFactory);
			List<TrxItem> trxItemList = trxItemDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(itemId)));
			if(trxItemList.size()>0){
				TrxItem trxItem = trxItemList.get(0);
				Trx trx = trxItem.getTrx();
				Product prod = trxItem.getProduct();
				//ProductAttr prodAttr = trxItem.getProductAttr();
				
				itemStatusDao = new TrxStatusItemDao(sessionFactory);
				List<TrxItemStatus> itemStatusList = itemStatusDao.loadBy(Order.asc("ID"), Restrictions.eq("isReadyQc", "Y"));
				if(itemStatusList.size()>0){
					
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
					product[0] = prod.getID().toString();
					product[1] = prod.getName();
					product[2] = /*prodAttr.getID().toString()*/"-";
					product[3] = /*prodAttr.getLabel()*/"-";
					product[4] = /*prodAttr.getSku()*/"-";
					
					String[] productImg = new String[1];
					
					prodImgDao = new ProductImageDao(sessionFactory);
					List<ProductImage> prodImgList = prodImgDao.loadBy(Order.asc("ID"), Restrictions.eq("product", prod));
					if(prodImgList.size()>0) {
						ProductImage prodImg = prodImgList.get(0);
						String photoUrl = "https://placehold.it/200x200";
						if(prodImg.getFolder()!=null && prodImg.getName()!=null){
							String mainUrl = PropertyMessageUtil.getConfigProperties().getProperty("image.myimages.url");
							String path = PropertyMessageUtil.getConfigProperties().getProperty("image.product.folder.url");
							photoUrl = mainUrl + path + prodImg.getFolder() + "/" + prodImg.getName();
						}
						productImg[0] = photoUrl;
					}
					
					String photoUrl = "https://placehold.it/150x150";
					if(trxItem.getImgFolderQc()!=null && trxItem.getImgNameQc()!=null){
						String mainUrl = PropertyMessageUtil.getConfigProperties().getProperty("image.myimages.url");
						String path = PropertyMessageUtil.getConfigProperties().getProperty("image.item.qc.folder.url");
						photoUrl = mainUrl + path + trxItem.getImgFolderQc() + "/" + trxItem.getImgNameQc();
					}
					
					
					String[] items = new String[5];
					items[0] = trxItem.getID().toString(); //ItemId
					items[1] = /*trxItem.getProductAttr().getLabel()*/"-"; //Item Name
					items[2] = trxItem.getProduct().getName(); //Product Name
					items[3] = trxItem.getVendor().getName(); //Vendor Name
					items[4] = photoUrl; //Photo
					
					bean.setNo(trx.getOrderNo());
					bean.setCustomer(customer);
					bean.setAddress(address.toString());
					bean.setNotes(trx.getNotes()!=null&&!trx.getNotes().isEmpty()?trx.getNotes():"-");
					bean.setItemArray(items);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean;
	}

	@Override
	public void passQc(String itemId) {
		try {
			trxItemDao = new TrxItemDao(sessionFactory);
			List<TrxItem> trxItemList = trxItemDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(itemId)));
			if(trxItemList.size()>0){
				TrxItem trxItem = trxItemList.get(0);
				
				itemStatusDao = new TrxStatusItemDao(sessionFactory);
				List<TrxItemStatus> tsiList = itemStatusDao.loadBy(Order.asc("ID"), Restrictions.eq("trxItem", trxItem));
				for(TrxItemStatus tsi: tsiList){
					tsi.setIsReadyQc("N");
					itemStatusDao = new TrxStatusItemDao(sessionFactory);
					itemStatusDao.saveOrUpdate(tsi);
				}
				
				TrxItemStatus tsi = new TrxItemStatus();
				tsi.setTrxItem(trxItem);
				tsi.setStatus(TRX_ITEM_STATUS.PASS_QC);
				tsi.setDate_(Calendar.getInstance(BielUtil.timeZoneJakarta()).getTime());
				
				itemStatusDao = new TrxStatusItemDao(sessionFactory);
				itemStatusDao.saveOrUpdate(tsi);
				
				threadTask.execute(new MailPassQCThread(sessionFactory, trxItem));
				
				notifService.readyQcReduce();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
