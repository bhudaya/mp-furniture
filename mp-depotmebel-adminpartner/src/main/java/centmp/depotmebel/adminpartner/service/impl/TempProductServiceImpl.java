package centmp.depotmebel.adminpartner.service.impl;

import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
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

import com.bilmajdi.util.CipherUtil;
import com.bilmajdi.util.PropertyMessageUtil;

import centmp.depotmebel.adminpartner.bean.CommonBean;
import centmp.depotmebel.adminpartner.bean.ProductBean;
import centmp.depotmebel.adminpartner.bean.ProductListBean;
import centmp.depotmebel.adminpartner.bean.UserBean;
import centmp.depotmebel.adminpartner.service.NotifService;
import centmp.depotmebel.adminpartner.service.ProductService;
import centmp.depotmebel.adminpartner.service.TempProductService;
import centmp.depotmebel.core.dao.TempProductAttrDao;
import centmp.depotmebel.core.dao.TempProductDao;
import centmp.depotmebel.core.dao.TempProductImgDao;
import centmp.depotmebel.core.dao.TempProductSkuDao;
import centmp.depotmebel.core.dao.TempProductSpecDao;
import centmp.depotmebel.core.dao.VendorDao;
import centmp.depotmebel.core.enums.APPROVAL;
import centmp.depotmebel.core.enums.STATUS;
import centmp.depotmebel.core.model.TempProduct;
import centmp.depotmebel.core.model.TempProductAttr;
import centmp.depotmebel.core.model.TempProductImage;
import centmp.depotmebel.core.model.TempProductSku;
import centmp.depotmebel.core.model.TempProductSpec;
import centmp.depotmebel.core.util.CommonUtil;

@Service
public class TempProductServiceImpl implements TempProductService {
	
	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	@Autowired
	private ProductService productService;
	@Autowired
	private NotifService notifService;

	private TempProductDao tempProdDao;
	private TempProductSpecDao tempProdSpecDao;
	private TempProductAttrDao tempProdAttrDao;
	private TempProductSkuDao tempProdSkuDao;
	private TempProductImgDao tempProdImgDao;
	private VendorDao vendorDao;
	
	@Override
	public ProductListBean listAll(UserBean userbean) {
		ProductListBean listbean = new ProductListBean();
		
		try {
			List<ProductBean> prodlist = new ArrayList<>();
			
			String sqlQuery = "SELECT DISTINCT(a.ID) "
					+ "FROM temp_product_ a, temp_product_sku b, vendor_product_temp c "
					+ "WHERE a.ID = b.temp_product and b.ID = c.temp_product_sku "
					+ "AND a.approval = '0'";
			
			tempProdDao = new TempProductDao(sessionFactory);
			List<?> liss = tempProdDao.nativeQuery(sqlQuery);
			
			for (Object object : liss) {
				BigInteger id = (BigInteger) object;
				
				Criterion cr1 = Restrictions.eq("ID", id.longValue());
				List<TempProduct> prodList = tempProdDao.loadBy(Order.desc("ID"), cr1);
				for(TempProduct prod: prodList) {
					
					String vendorName = "-";
					String sqlQuery2 = "SELECT d.name "
							+ "FROM temp_product_sku b, vendor_product_temp c, vendor_ d "
							+ "WHERE b.temp_product = '"+prod.getID()+"' AND c.temp_product_sku = b.ID "
							+ "AND d.ID = c.vendor LIMIT 0,1";
					vendorDao = new VendorDao(sessionFactory);
					List<?> vendList = vendorDao.nativeQuery(sqlQuery2);
					if(vendList.size()>0) vendorName = (String) vendList.get(0);
					
					ProductBean bean = new ProductBean();
					bean.setId(URLEncoder.encode(CipherUtil.encrypt(prod.getID().toString()), "UTF-8"));
					bean.setName(prod.getName());
					bean.setPcategoryName(prod.getPcategory().getName());
					bean.setPcategorySubName(prod.getPcategorySub().getName());
					bean.setBasePriceIdr(CommonUtil.currencyIDR(prod.getBasePrice().doubleValue()));
					bean.setVendorName(vendorName);
					bean.setCreatedDate(FastDateFormat.getInstance("dd/MM/yyyy, HH:mm").format(prod.getCreatedDate()));
					//bean.setImage(PropertyMessageUtil.getConfigProperties().getProperty("img.product.list.default"));
					prodlist.add(bean);
				}
			}	
			
			/*tempProdDao = new TempProductDao(sessionFactory);
			Criterion cr1 = Restrictions.eq("approval", APPROVAL.REQUEST);
					
			List<TempProduct> prodList = tempProdDao.loadBy(Order.desc("ID"), cr1);
			for(TempProduct prod: prodList) {
				
				
			}*/
			
			listbean.setProducts(prodlist);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listbean;
	}
	
	@Override
	public ProductBean detail(String id) {
		ProductBean bean = new ProductBean();
		
		try {
			String decrp = CipherUtil.decrypt(id);
			tempProdDao = new TempProductDao(sessionFactory);
			List<TempProduct> productList = tempProdDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(decrp)));
			if(productList.size()>0){
				TempProduct product = productList.get(0);
				Criterion crProduct = Restrictions.eq("tempProduct", product);
				
				List<String[]> images = new ArrayList<>();
				List<String[]> specs = new ArrayList<>();
				List<String[]> attrList = new ArrayList<>();
				List<String[]> skuList = new ArrayList<>();
				
				tempProdImgDao = new TempProductImgDao(sessionFactory);
				List<TempProductImage> imageList = tempProdImgDao.loadBy(Order.asc("ID"), crProduct);
				for (TempProductImage img : imageList) {
					String imgId = img.getID().toString();
					String photoUrl = PropertyMessageUtil.getConfigProperties().getProperty("img.product.detail.default");
					if(img.getFolder()!=null && img.getName()!=null){
						String mainUrl = PropertyMessageUtil.getConfigProperties().getProperty("image.myimages.url");
						String path = PropertyMessageUtil.getConfigProperties().getProperty("image.product.folder.url");
						photoUrl = mainUrl + path + img.getFolder() + "/" + img.getName();
					}
					
					String[] str = new String[2];
					str[0] = imgId;
					str[1] = photoUrl;
					images.add(str);
				}
				int sizeImg = imageList.size();
				for(int i=0;i<(3-sizeImg);i++){
					String imgId = "";
					String photoUrl = PropertyMessageUtil.getConfigProperties().getProperty("img.product.detail.default");
					String[] str = new String[2];
					str[0] = imgId;
					str[1] = photoUrl;
					images.add(str);
				}
				
				tempProdSpecDao = new TempProductSpecDao(sessionFactory);
				List<TempProductSpec> specList = tempProdSpecDao.loadBy(Order.asc("ID"), crProduct);
				for(TempProductSpec productSpec: specList) {
					String[] arr = new String[3];
					arr[0] = productSpec.getID().toString();
					arr[1] = productSpec.getPcategorySpec().getLabel();
					arr[2] = productSpec.getValue();
					specs.add(arr);
				}
				
				tempProdAttrDao = new TempProductAttrDao(sessionFactory);
				List<TempProductAttr> prodAttrList = tempProdAttrDao.loadBy(Order.asc("ID"), crProduct);
				for(TempProductAttr productAttr: prodAttrList) {
					String[] arr = new String[3];
					arr[0] = productAttr.getID().toString();
					arr[1] = productAttr.getPcategoryAttr().getLabel();
					arr[2] = productAttr.getValue();
					attrList.add(arr);
				}
				
				tempProdSkuDao = new TempProductSkuDao(sessionFactory);
				List<TempProductSku> prodSkuList = tempProdSkuDao.loadBy(Order.asc("ID"), crProduct);
				for(TempProductSku productSku: prodSkuList) {
					String[] arr = new String[6];
					arr[0] = productSku.getID().toString();
					arr[1] = productSku.getLabel();
					arr[2] = productSku.getSku();
					arr[3] = productSku.getRemarks();
					arr[4] = productSku.getStock()!=null?productSku.getStock().toString():"-";
					arr[5] = CommonUtil.currencyIDR(productSku.getAddtPrice().doubleValue());
					skuList.add(arr);
				}
				
				bean.setId(URLEncoder.encode(CipherUtil.encrypt(product.getID().toString()), "UTF-8"));
				bean.setName(product.getName());
				bean.setPcategoryName(product.getPcategory().getName());
				bean.setPcategorySubName(product.getPcategorySub().getName());
				bean.setBasePriceIdr(CommonUtil.currencyIDR(product.getBasePrice().doubleValue()));
				bean.setDesc(product.getDescription()!=null?product.getDescription():"-");
				bean.setStatus(product.getStatus().name());
				bean.setImages(images);
				bean.setSpecs(specs);
				bean.setAttrList(attrList);
				bean.setSkuList(skuList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean;
	}
	
	@Override
	public ProductBean approvalUpdate(String prodId, String val) {
		ProductBean bean = new ProductBean();
		
		try {
			String decrp = CipherUtil.decrypt(URLDecoder.decode(prodId, "UTF-8"));
			tempProdDao = new TempProductDao(sessionFactory);
			List<TempProduct> productList = tempProdDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(decrp)));
			if(productList.size()>0){
				APPROVAL apprvl = APPROVAL.REQUEST;
				STATUS stts = STATUS.INACTIVE;
				if(val.equalsIgnoreCase("1")){
					apprvl = APPROVAL.APPROVED;
					stts = STATUS.ACTIVE;
				}else if(val.equalsIgnoreCase("2")){
					apprvl = APPROVAL.REJECTED;
				}
				
				TempProduct product = productList.get(0);
				product.setApproval(apprvl);
				product.setStatus(stts);
				tempProdDao = new TempProductDao(sessionFactory);
				tempProdDao.saveOrUpdate(product);
				
				if(val.equalsIgnoreCase("1")){
					Criterion crProd = Restrictions.eq("tempProduct", product);
					
					tempProdImgDao = new TempProductImgDao(sessionFactory);
					List<TempProductImage> prodImgList = tempProdImgDao.loadBy(Order.asc("ID"), crProd);  
					
					tempProdSpecDao = new TempProductSpecDao(sessionFactory);
					List<TempProductSpec> prodSpecList = tempProdSpecDao.loadBy(Order.asc("ID"), crProd);
					
					tempProdAttrDao = new TempProductAttrDao(sessionFactory);
					List<TempProductAttr> prodAttrList = tempProdAttrDao.loadBy(Order.asc("ID"), crProd);
					
					tempProdSkuDao = new TempProductSkuDao(sessionFactory);
					List<TempProductSku> prodSkuList = tempProdSkuDao.loadBy(Order.asc("ID"), crProd);
					
					HashMap<String, Object> hm = new HashMap<>();
					hm.put("tempprod", product);
					hm.put("images", prodImgList);
					hm.put("specList", prodSpecList);
					hm.put("attrList", prodAttrList);
					hm.put("skuList", prodSkuList);
					CommonBean commonbean = productService.createFromTemp(hm);
					if(commonbean.getErrCode()==0){
						notifService.productApprovalReduce();
					}
				}else{
					notifService.productApprovalReduce();
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean;
	}
	
}
