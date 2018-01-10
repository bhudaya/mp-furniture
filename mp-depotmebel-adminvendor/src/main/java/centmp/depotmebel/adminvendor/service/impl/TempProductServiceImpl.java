package centmp.depotmebel.adminvendor.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bilmajdi.util.BielUtil;
import com.bilmajdi.util.CipherUtil;
import com.bilmajdi.util.PropertyMessageUtil;
import com.google.gson.Gson;

import centmp.depotmebel.adminvendor.bean.ProductBean;
import centmp.depotmebel.adminvendor.bean.ProductListBean;
import centmp.depotmebel.adminvendor.bean.UserBean;
import centmp.depotmebel.adminvendor.bean.form.ProductForm;
import centmp.depotmebel.adminvendor.service.NotifService;
import centmp.depotmebel.adminvendor.service.TempProductService;
import centmp.depotmebel.core.dao.PCategoryAttrDao;
import centmp.depotmebel.core.dao.PCategoryDao;
import centmp.depotmebel.core.dao.PCategorySpecDao;
import centmp.depotmebel.core.dao.PCategorySubDao;
import centmp.depotmebel.core.dao.TempProductAttrDao;
import centmp.depotmebel.core.dao.TempProductDao;
import centmp.depotmebel.core.dao.TempProductImgDao;
import centmp.depotmebel.core.dao.TempProductSkuDao;
import centmp.depotmebel.core.dao.TempProductSpecDao;
import centmp.depotmebel.core.dao.VendorProductTempDao;
import centmp.depotmebel.core.dao.VendorUserDao;
import centmp.depotmebel.core.enums.APPROVAL;
import centmp.depotmebel.core.enums.STATUS;
import centmp.depotmebel.core.model.PCategory;
import centmp.depotmebel.core.model.PCategoryAttr;
import centmp.depotmebel.core.model.PCategorySpec;
import centmp.depotmebel.core.model.PCategorySub;
import centmp.depotmebel.core.model.ProductAttr;
import centmp.depotmebel.core.model.TempProduct;
import centmp.depotmebel.core.model.TempProductAttr;
import centmp.depotmebel.core.model.TempProductImage;
import centmp.depotmebel.core.model.TempProductSku;
import centmp.depotmebel.core.model.TempProductSpec;
import centmp.depotmebel.core.model.VendorProductTemp;
import centmp.depotmebel.core.model.VendorUser;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.core.util.ImageUtil;
import centmp.depotmebel.core.util.ProductSKULooping;

@Service
public class TempProductServiceImpl implements TempProductService {
	
	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	@Autowired
	private NotifService notifService;

	private PCategoryDao pcategoryDao;
	private PCategorySubDao pcategorySubDao;
	private PCategorySpecDao pcategorySpecDao;
	private PCategoryAttrDao pcategoryAttrDao;
	private TempProductDao tempProdDao;
	private TempProductSpecDao tempProdSpecDao;
	private TempProductAttrDao tempProdAttrDao;
	private TempProductSkuDao tempProdSkuDao;
	private TempProductImgDao tempProdImgDao;
	private VendorProductTempDao vendProdTempDao;
	private VendorUserDao vendorUserDao;
	
	@Override
	public ProductListBean listAll(UserBean userbean) {
		ProductListBean listbean = new ProductListBean();
		
		try {
			List<ProductBean> prodlist = new ArrayList<>();
			
			String vendorId = userbean.getVendors()[0];
			
			String sqlQuery = "SELECT DISTINCT(a.ID) "
					+ "FROM temp_product_ a, temp_product_sku b, vendor_product_temp c "
					+ "WHERE a.ID = b.temp_product and b.ID = c.temp_product_sku "
					+ "AND c.vendor = '"+vendorId+"' AND a.approval = '0'";
			
			tempProdDao = new TempProductDao(sessionFactory);
			List<?> liss = tempProdDao.nativeQuery(sqlQuery);
			
			for (Object object : liss) {
				BigInteger id = (BigInteger) object;
				
				Criterion cr1 = Restrictions.eq("ID", id.longValue());
				tempProdDao = new TempProductDao(sessionFactory);
				List<TempProduct> prodList = tempProdDao.loadBy(Order.desc("ID"), cr1);
				for(TempProduct prod: prodList) {
					String status = "";
					if(prod.getApproval()==APPROVAL.REQUEST){status = "On Process";}
					else if(prod.getApproval()==APPROVAL.REJECTED){status = "Rejected";}
					
					
					ProductBean bean = new ProductBean();
					bean.setId(URLEncoder.encode(CipherUtil.encrypt(prod.getID().toString()), "UTF-8"));
					bean.setName(prod.getName());
					bean.setPcategoryName(prod.getPcategory().getName());
					bean.setPcategorySubName(prod.getPcategorySub().getName());
					bean.setBasePriceIdr(CommonUtil.currencyIDR(prod.getBasePrice().doubleValue()));
					bean.setStatus(status);
					//bean.setImage(PropertyMessageUtil.getConfigProperties().getProperty("img.product.list.default"));
					prodlist.add(bean);
				}
			}
			
			listbean.setProducts(prodlist);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listbean;
	}
	
	@Override
	public ProductBean skuGeneate(List<String[]> attrList) {
		ProductBean bean = null;
		
		try {
			List<ProductAttr> prodAttrList = new ArrayList<>();
			for (String[] attrs: attrList) {
				String attrId = attrs[0];
				String attrVal = attrs[1];
				
				ProductAttr attr = new ProductAttr();
				attr.setID(Long.valueOf(attrId));
				attr.setValue(attrVal);
				prodAttrList.add(attr);
			}
			
			ProductSKULooping skuLooping = new ProductSKULooping();
			List<String> generatesSkus = skuLooping.result(prodAttrList);
			//System.out.println("generatesSkus: "+new Gson().toJson(generatesSkus));
			
			StringBuffer bufferId = new StringBuffer();
			List<String[]> skuList = new ArrayList<>();
			for(int i=0;i<generatesSkus.size();i++){
				String str = generatesSkus.get(i);
				String id = Integer.toString(i+1);
				bufferId.append(id+";");
				
				String[] arr = new String[2];
				arr[0] = id;
				arr[1] = str;
				skuList.add(arr);
			}
			
			bean = new ProductBean();
			bean.setDesc(bufferId.toString());
			bean.setSkuList(skuList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean;
	}
	
	@Override
	public ProductBean create(UserBean userbean, ProductForm form) {
		ProductBean bean = new ProductBean();
		int errCode = -1;
		String errMsg = "";
		String id = "";
		
		try {
			System.out.println("form: "+new Gson().toJson(form));
			
			String pcategoryId = CipherUtil.decrypt(URLDecoder.decode(form.getPcategoryId(), "UTF-8"));
			String pcategorySubId = CipherUtil.decrypt(URLDecoder.decode(form.getPcategorySubId(), "UTF-8"));
			
			PCategory pcategory = null;
			PCategorySub pcategorySub = null;
			Calendar calnow = Calendar.getInstance(BielUtil.timeZoneJakarta());
			
			pcategoryDao = new PCategoryDao(sessionFactory);
			List<PCategory> pcategoryList = pcategoryDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(pcategoryId)));
			if(pcategoryList.size()>0)pcategory = pcategoryList.get(0);
			
			if(pcategorySubId!=null&&!pcategorySubId.isEmpty()){
				pcategorySubDao = new PCategorySubDao(sessionFactory);
				List<PCategorySub> pcategorySubList = pcategorySubDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(pcategorySubId)));
				if(pcategorySubList.size()>0)pcategorySub = pcategorySubList.get(0);
			}
			
			VendorUser vendorUser = null;
			vendorUserDao = new VendorUserDao(sessionFactory);
			List<VendorUser> vendorUserList = vendorUserDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("user.ID", Long.valueOf(userbean.getId())));
			System.out.println("vendorUserList: "+vendorUserList.get(0).getVendor().getName());
			if(vendorUserList.size()>0)vendorUser=vendorUserList.get(0);
				
			
			TempProduct prod = new TempProduct();
			prod.setName(form.getName().trim());
			prod.setPcategory(pcategory);
			prod.setPcategorySub(pcategorySub);
			prod.setBasePrice(new BigDecimal(form.getBasePrice().trim()));
			prod.setDescription(form.getDesc()!=null&&!form.getDesc().trim().isEmpty()?form.getDesc():null);
			prod.setCreatedDate(calnow.getTime());
			prod.setStatus(STATUS.INACTIVE);
			prod.setApproval(APPROVAL.REQUEST);
			tempProdDao = new TempProductDao(sessionFactory);
			tempProdDao.saveOrUpdate(prod);
			
			List<MultipartFile> images = form.getImages();
			for(MultipartFile multipartFile: images) {
				if(multipartFile!=null && multipartFile.getSize()!=0){
					String imageLocation = PropertyMessageUtil.getConfigProperties().getProperty("image.myimages.location");
					String imageDestinationFolder = PropertyMessageUtil.getConfigProperties().getProperty("image.product.folder");
					String location = imageLocation + imageDestinationFolder;
					String photoFolder = FastDateFormat.getInstance("yyyyMMdd-HHmmssSSS").format(Calendar.getInstance(BielUtil.timeZoneJakarta()).getTime());
					String photoName = ImageUtil.upload(multipartFile, location, photoFolder, null, null);
					
					TempProductImage primg = new TempProductImage();
					primg.setTempProduct(prod);
					primg.setFolder(photoFolder);
					primg.setName(photoName);
					primg.setCreatedDate(calnow.getTime());
					tempProdImgDao = new TempProductImgDao(sessionFactory);
					tempProdImgDao.saveOrUpdate(primg);
				}
			}
			
			List<String[]> specList = form.getSpecList();
			for(String[] strings: specList){
				pcategorySpecDao = new PCategorySpecDao(sessionFactory);
				List<PCategorySpec> pcategorySpecList = pcategorySpecDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(strings[0])));
				if(pcategorySpecList.size()>0){
					PCategorySpec pcategorySpec = pcategorySpecList.get(0);
					
					TempProductSpec prodspec = new TempProductSpec();
					prodspec.setPcategorySpec(pcategorySpec);
					prodspec.setTempProduct(prod);
					prodspec.setValue(strings[1].trim());
					prodspec.setCreatedDate(calnow.getTime());
					tempProdSpecDao = new TempProductSpecDao(sessionFactory);
					tempProdSpecDao.saveOrUpdate(prodspec);
				}
			}
			
			List<String[]> attrList = form.getAttrList();
			for(String[] strings: attrList){
				pcategoryAttrDao = new PCategoryAttrDao(sessionFactory);
				List<PCategoryAttr> pcategoryAttrList = pcategoryAttrDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(strings[0])));
				if(pcategoryAttrList.size()>0){
					PCategoryAttr pcategoryAttr = pcategoryAttrList.get(0);
					
					TempProductAttr prodAttr = new TempProductAttr();
					prodAttr.setPcategoryAttr(pcategoryAttr);
					prodAttr.setTempProduct(prod);
					prodAttr.setValue(strings[1].trim());
					prodAttr.setCreatedDate(calnow.getTime());
					tempProdAttrDao = new TempProductAttrDao(sessionFactory);
					tempProdAttrDao.saveOrUpdate(prodAttr);
				}
			}
			
			List<String[]> skuList = form.getSkuList();
			for(int i=0;i<skuList.size();i++){
				String[] strings = skuList.get(i);
				String label = strings[0];
				String sku = generateSkuString(prod, label, (i+1));
				
				TempProductSku prodsku = new TempProductSku();
				prodsku.setTempProduct(prod);
				prodsku.setLabel(label);
				prodsku.setSku(sku);
				prodsku.setRemarks(strings[1]!=null&&!strings[1].isEmpty()?strings[1]:null);
				prodsku.setStock(strings[2]!=null&&!strings[2].isEmpty()?Integer.parseInt(strings[2]):0);
				prodsku.setAddtPrice(strings[3]!=null&&!strings[3].isEmpty()?new BigDecimal(strings[3]):BigDecimal.ZERO);
				prodsku.setCreatedDate(calnow.getTime());
				tempProdSkuDao = new TempProductSkuDao(sessionFactory);
				tempProdSkuDao.saveOrUpdate(prodsku);
				
				VendorProductTemp vendProd = new VendorProductTemp();
				vendProd.setVendor(vendorUser.getVendor());
				vendProd.setProductSku(prodsku);
				vendProd.setStock(strings[2]!=null&&!strings[2].isEmpty()?Integer.parseInt(strings[2]):0);
				vendProd.setIsUnlimited("N");
				vendProd.setIsActive("Y");
				vendProd.setCreatedDate(calnow.getTime());
				vendProdTempDao = new VendorProductTempDao(sessionFactory);
				vendProdTempDao.saveOrUpdate(vendProd);
			}
			
			
			errCode = 0;
			id = prod.getID()!=null?CipherUtil.encrypt(prod.getID().toString()):"";
			
		} catch (Exception e) {
			e.printStackTrace();
			errCode = -1;
			errMsg = e.getLocalizedMessage();
		}
		
		try {
			if(!id.isEmpty())notifService.productApprovalAdd();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		bean.setErrCode(errCode);
		bean.setErrMsg(errMsg);
		bean.setId(id);
		
		return bean;
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
	
	
	
	
	
	
	
	
	private String generateSkuString(TempProduct prod, String label, int count){
		StringBuffer result = new StringBuffer();
		String initial = "", middle = "", last_ = "";;
		
		String[] labels = label.split(" ");
		if(labels.length>1){initial = labels[0].substring(0, 1)+labels[1].substring(0, 1);
		}else{initial = labels.length==1?labels[0].substring(0, 1)+"0":"XX";}
		
		String prodIdStr = prod.getID().toString();
		StringBuffer bufferMiddle = new StringBuffer();
		for(int i=0;i<(5-prodIdStr.length());i++){
			bufferMiddle.append("0");
		}
		bufferMiddle.append(prodIdStr);
		middle = bufferMiddle.toString();
		
		StringBuffer bufferLast = new StringBuffer();
		if(count>9){
			bufferLast.append(Integer.toString(count));
		}else{
			bufferLast.append("0");
			bufferLast.append(Integer.toString(count));
		}
		last_ = bufferLast.toString();
		
		result.append(initial);
		result.append(middle);
		result.append(last_);
		
		return result.toString();
	}
	
}
