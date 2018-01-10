package centmp.depotmebel.adminvendor.service.impl;

import java.math.BigInteger;
import java.net.URLEncoder;
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

import centmp.depotmebel.adminvendor.bean.ProductBean;
import centmp.depotmebel.adminvendor.bean.ProductListBean;
import centmp.depotmebel.adminvendor.bean.UserBean;
import centmp.depotmebel.adminvendor.service.ProductService;
import centmp.depotmebel.core.dao.ProductAttrDao;
import centmp.depotmebel.core.dao.ProductDao;
import centmp.depotmebel.core.dao.ProductSkuDao;
import centmp.depotmebel.core.dao.ProductSpecDao;
import centmp.depotmebel.core.dao.TempProductDao;
import centmp.depotmebel.core.enums.APPROVAL;
import centmp.depotmebel.core.enums.STATUS;
import centmp.depotmebel.core.model.Product;
import centmp.depotmebel.core.model.ProductAttr;
import centmp.depotmebel.core.model.ProductSku;
import centmp.depotmebel.core.model.ProductSpec;
import centmp.depotmebel.core.util.CommonUtil;


@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;

	private ProductDao productDao;
	private ProductSpecDao productSpecDao;
	private ProductAttrDao productAttrDao;
	private ProductSkuDao productSkuDao;
	private TempProductDao tempProdDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public ProductListBean list(HashMap<String, Object> hm) {
		ProductListBean listbean = new ProductListBean();
		
		Integer startRow = 0;
		Integer maxResult = 50;
		Integer sumApproved = 0;
		Integer sumRejected = 0;
		Integer sumApproval = 0;
		
		List<ProductBean> prodlist = new ArrayList<>();
		
		try {
			Criterion cr = Restrictions.eq("approval", APPROVAL.APPROVED);
			productDao = new ProductDao(sessionFactory);
			sumApproved = productDao.rowCount(cr); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Criterion cr = Restrictions.eq("approval", APPROVAL.REQUEST);
			tempProdDao = new TempProductDao(sessionFactory);
			sumApproval = tempProdDao.rowCount(cr); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*try {
			Criterion cr = Restrictions.eq("approval", APPROVAL.REJECTED);
			tempProdDao = new TempProductDao(sessionFactory);
			sumRejected = tempProdDao.rowCount(cr); 
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		try {
			UserBean userbean = (UserBean) hm.get("userbean");
			
			String sqlQuery1 = "SELECT DISTINCT(a.ID) "
					+ "FROM product_ a, product_sku b, vendor_product c "
					+ "WHERE a.ID = b.product AND b.ID = c.product_sku AND "
					+ "c.vendor = '"+userbean.getVendors()[0]+"'";
			productDao = new ProductDao(sessionFactory);
			List<?> idList = (List<Product>) productDao.nativeQuery(sqlQuery1);
			//System.out.println("vendList: "+new Gson().toJson(idList));
			for(Object obj: idList){
				BigInteger id = (BigInteger) obj;
				
				Criterion crStatusActive = Restrictions.eq("status", STATUS.ACTIVE);
				Criterion crId = Restrictions.eq("ID", id.longValue());
				productDao = new ProductDao(sessionFactory);
				List<Product> productList = productDao.loadBy(Order.desc("ID"), crStatusActive, crId);
				for(Product prod: productList){
					
					ProductBean bean = new ProductBean();
					bean.setId(URLEncoder.encode(CipherUtil.encrypt(prod.getID().toString()), "UTF-8"));
					bean.setName(prod.getName());
					bean.setStatus(prod.getStatus().name());
					bean.setBasePriceIdr(CommonUtil.currencyIDR(prod.getBasePrice().doubleValue()));
					bean.setImage(PropertyMessageUtil.getConfigProperties().getProperty("img.product.list.default"));
					prodlist.add(bean);
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		listbean.setStart(startRow);
		listbean.setMax(maxResult);
		listbean.setSumApproved(sumApproved);
		listbean.setSumRejected(sumRejected);
		listbean.setSumApproval(sumApproval);
		listbean.setProducts(prodlist);
		
		return listbean;
	}
	
	@Override
	public ProductBean detail(String id) {
		ProductBean bean = new ProductBean();
		
		try {
			String decrp = CipherUtil.decrypt(id);
			productDao = new ProductDao(sessionFactory);
			List<Product> productList = productDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(decrp)));
			if(productList.size()>0){
				Product product = productList.get(0);
				Criterion crProduct = Restrictions.eq("product", product);
				
				List<String[]> images = new ArrayList<>();
				List<String[]> specs = new ArrayList<>();
				List<String[]> attrList = new ArrayList<>();
				List<String[]> skuList = new ArrayList<>();
				
				for(int i=0;i<3;i++){
					String imgId = "";
					String photoUrl = PropertyMessageUtil.getConfigProperties().getProperty("img.product.detail.default");
					String[] str = new String[2];
					str[0] = imgId;
					str[1] = photoUrl;
					images.add(str);
				}
				
				productSpecDao = new ProductSpecDao(sessionFactory);
				List<ProductSpec> specList = productSpecDao.loadBy(Order.asc("ID"), crProduct);
				for(ProductSpec productSpec: specList) {
					String[] arr = new String[3];
					arr[0] = productSpec.getID().toString();
					arr[1] = productSpec.getPcategorySpec().getLabel();
					arr[2] = productSpec.getValue();
					specs.add(arr);
				}
				
				productAttrDao = new ProductAttrDao(sessionFactory);
				List<ProductAttr> prodAttrList = productAttrDao.loadBy(Order.asc("ID"), crProduct);
				for(ProductAttr productAttr: prodAttrList) {
					String[] arr = new String[3];
					arr[0] = productAttr.getID().toString();
					arr[1] = productAttr.getPcategoryAttr().getLabel();
					arr[2] = productAttr.getValue();
					attrList.add(arr);
				}
				
				productSkuDao = new ProductSkuDao(sessionFactory);
				List<ProductSku> prodSkuList = productSkuDao.loadBy(Order.asc("ID"), crProduct);
				for(ProductSku productSku: prodSkuList) {
					String[] arr = new String[6];
					arr[0] = productSku.getID().toString();
					arr[1] = productSku.getLabel();
					arr[2] = productSku.getSku();
					arr[3] = productSku.getRemarks();
					arr[4] = productSku.getStock().toString();
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

}
