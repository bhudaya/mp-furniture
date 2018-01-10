package centmp.depotmebel.adminpartner.service.impl;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.multipart.MultipartFile;

import com.bilmajdi.util.BielUtil;
import com.bilmajdi.util.CipherUtil;
import com.bilmajdi.util.PropertyMessageUtil;
import com.google.gson.Gson;

import centmp.depotmebel.adminpartner.bean.CommonBean;
import centmp.depotmebel.adminpartner.bean.ProductBean;
import centmp.depotmebel.adminpartner.bean.ProductListBean;
import centmp.depotmebel.adminpartner.bean.form.ProductForm;
import centmp.depotmebel.adminpartner.service.ProductService;
import centmp.depotmebel.core.dao.PCategoryAttrDao;
import centmp.depotmebel.core.dao.PCategoryDao;
import centmp.depotmebel.core.dao.PCategorySpecDao;
import centmp.depotmebel.core.dao.PCategorySubDao;
import centmp.depotmebel.core.dao.ProductAttrDao;
import centmp.depotmebel.core.dao.ProductDao;
import centmp.depotmebel.core.dao.ProductImageDao;
import centmp.depotmebel.core.dao.ProductSkuDao;
import centmp.depotmebel.core.dao.ProductSpecDao;
import centmp.depotmebel.core.dao.VendorProductDao;
import centmp.depotmebel.core.dao.VendorProductTempDao;
import centmp.depotmebel.core.enums.APPROVAL;
import centmp.depotmebel.core.enums.STATUS;
import centmp.depotmebel.core.model.PCategory;
import centmp.depotmebel.core.model.PCategoryAttr;
import centmp.depotmebel.core.model.PCategorySpec;
import centmp.depotmebel.core.model.PCategorySub;
import centmp.depotmebel.core.model.Product;
import centmp.depotmebel.core.model.ProductAttr;
import centmp.depotmebel.core.model.ProductImage;
import centmp.depotmebel.core.model.ProductSku;
import centmp.depotmebel.core.model.ProductSpec;
import centmp.depotmebel.core.model.TempProduct;
import centmp.depotmebel.core.model.TempProductAttr;
import centmp.depotmebel.core.model.TempProductImage;
import centmp.depotmebel.core.model.TempProductSku;
import centmp.depotmebel.core.model.TempProductSpec;
import centmp.depotmebel.core.model.VendorProduct;
import centmp.depotmebel.core.model.VendorProductTemp;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.core.util.ImageUtil;
import centmp.depotmebel.core.util.ProductSKULooping;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	private PCategoryDao pcategoryDao;
	private PCategorySubDao pcategorySubDao;
	private PCategorySpecDao pcategorySpecDao;
	private PCategoryAttrDao pcategoryAttrDao;
	private ProductDao productDao;
	private ProductImageDao productImgDao;
	private ProductSpecDao productSpecDao;
	private ProductAttrDao productAttrDao;
	private ProductSkuDao productSkuDao;
	private VendorProductDao vendProductDao;
	private VendorProductTempDao vendProdTempDao;

	@Override
	public ProductListBean list(HashMap<String, Object> hm) {
		ProductListBean listbean = new ProductListBean();
		Integer sumProduct = 0;
		Integer sumPCategory = 0;
		Integer startRow = 0;
		Integer maxResult = 50;
		List<ProductBean> prodlist = new ArrayList<>();
		
		Criterion crStatusActive = Restrictions.eq("status", STATUS.ACTIVE);
		Criterion crStatusInactive = Restrictions.eq("status", STATUS.INACTIVE);
		Criterion crApprovalApprved = Restrictions.eq("approval", APPROVAL.APPROVED);
		
		try {
			pcategoryDao = new PCategoryDao(sessionFactory);
			sumPCategory = pcategoryDao.rowCount(crStatusActive);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Criterion crStatusThis = Restrictions.or(crStatusActive, crStatusInactive);
			productDao = new ProductDao(sessionFactory);
			sumProduct = productDao.rowCount(crStatusThis, crApprovalApprved);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Criterion crStatusThis = Restrictions.or(crStatusActive, crStatusInactive);
			productDao = new ProductDao(sessionFactory);
			List<Product> productList = productDao.loadBy(Order.desc("ID"), startRow, maxResult, crStatusThis, crApprovalApprved);
			for(Product prod: productList){
				ProductBean bean = new ProductBean();
				bean.setId(URLEncoder.encode(CipherUtil.encrypt(prod.getID().toString()), "UTF-8"));
				bean.setName(prod.getName());
				bean.setStatus(prod.getStatus().name());
				bean.setBasePriceIdr(CommonUtil.currencyIDR(prod.getBasePrice().doubleValue()));
				bean.setImage(PropertyMessageUtil.getConfigProperties().getProperty("img.product.list.default"));
				prodlist.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		listbean.setSumProduct(sumProduct);
		listbean.setSumCategory(sumPCategory);
		listbean.setStart(startRow);
		listbean.setMax(maxResult);
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
					arr[4] = productSku.getStock()!=null?productSku.getStock().toString():"";
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
	
	@SuppressWarnings("unchecked")
	@Override
	public CommonBean createFromTemp(HashMap<String, Object> hm) {
		CommonBean bean = new CommonBean(); 
		int errCode = -1;
		
		try {
			TempProduct tempprod = (TempProduct) hm.get("tempprod");
			List<TempProductImage> images = (List<TempProductImage>) hm.get("images");
			List<TempProductSpec> specList = (List<TempProductSpec>) hm.get("specList");
			List<TempProductAttr> attrList = (List<TempProductAttr>) hm.get("attrList");
			List<TempProductSku> skuList = (List<TempProductSku>) hm.get("skuList");
			
			Calendar calnow = Calendar.getInstance(BielUtil.timeZoneJakarta());
			
			Product prod = new Product();
			prod.setName(tempprod.getName());
			prod.setPcategory(tempprod.getPcategory());
			prod.setPcategorySub(tempprod.getPcategorySub());
			prod.setBasePrice(tempprod.getBasePrice());
			prod.setDescription(tempprod.getDescription());
			prod.setCreatedDate(calnow.getTime());
			prod.setStatus(STATUS.ACTIVE);
			prod.setApproval(APPROVAL.APPROVED);
			productDao = new ProductDao(sessionFactory);
			productDao.saveOrUpdate(prod);
			
			for(TempProductImage img: images) {
				ProductImage primg = new ProductImage();
				primg.setProduct(prod);
				primg.setFolder(img.getFolder());
				primg.setName(img.getName());
				primg.setCreatedDate(calnow.getTime());
				productImgDao = new ProductImageDao(sessionFactory);
				productImgDao.saveOrUpdate(primg);
			}
			
			for(TempProductSpec spec: specList) {
				ProductSpec prodspec = new ProductSpec();
				prodspec.setPcategorySpec(spec.getPcategorySpec());
				prodspec.setProduct(prod);
				prodspec.setValue(spec.getValue());
				prodspec.setCreatedDate(calnow.getTime());
				productSpecDao = new ProductSpecDao(sessionFactory);
				productSpecDao.saveOrUpdate(prodspec);
			}

			for(TempProductAttr attr: attrList) {
				ProductAttr prodAttr = new ProductAttr();
				prodAttr.setPcategoryAttr(attr.getPcategoryAttr());
				prodAttr.setProduct(prod);
				prodAttr.setValue(attr.getValue());
				prodAttr.setCreatedDate(calnow.getTime());
				productAttrDao = new ProductAttrDao(sessionFactory);
				productAttrDao.saveOrUpdate(prodAttr);
			}
			
			for(TempProductSku tempProductSku: skuList) {
				ProductSku prodsku = new ProductSku();
				prodsku.setProduct(prod);
				prodsku.setLabel(tempProductSku.getLabel());
				prodsku.setSku(tempProductSku.getSku());
				prodsku.setRemarks(tempProductSku.getRemarks());
				prodsku.setStock(tempProductSku.getStock());
				prodsku.setAddtPrice(tempProductSku.getAddtPrice());
				prodsku.setCreatedDate(calnow.getTime());
				productSkuDao = new ProductSkuDao(sessionFactory);
				productSkuDao.saveOrUpdate(prodsku);
				
				vendProdTempDao = new VendorProductTempDao(sessionFactory);
				List<VendorProductTemp> vendProdTemps = vendProdTempDao.loadBy(Order.asc("ID"), Restrictions.eq("productSku", tempProductSku));
				for(VendorProductTemp vendorProductTemp: vendProdTemps) {
					VendorProduct vendProd = new VendorProduct();
					vendProd.setVendor(vendorProductTemp.getVendor());
					vendProd.setProductSku(prodsku);
					vendProd.setStock(prodsku.getStock());
					vendProd.setIsUnlimited("N");
					vendProd.setIsActive("Y");
					vendProd.setCreatedDate(calnow.getTime());
					vendProductDao = new VendorProductDao(sessionFactory);
					vendProductDao.saveOrUpdate(vendProd);
				}
			}
			
			errCode = 0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		bean.setErrCode(errCode);
		return bean;
	}
	
	@Override
	public CommonBean create(ProductForm form) {
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
			
			Product prod = new Product();
			prod.setName(form.getName().trim());
			prod.setPcategory(pcategory);
			prod.setPcategorySub(pcategorySub);
			prod.setBasePrice(new BigDecimal(form.getBasePrice().trim()));
			prod.setDescription(form.getDesc()!=null&&!form.getDesc().trim().isEmpty()?form.getDesc():null);
			prod.setCreatedDate(calnow.getTime());
			prod.setStatus(STATUS.ACTIVE);
			prod.setApproval(APPROVAL.APPROVED);
			/*prod.setVendor(null);
			prod.setCreatedby(null);*/
			productDao = new ProductDao(sessionFactory);
			productDao.saveOrUpdate(prod);
			
			List<MultipartFile> images = form.getImages();
			for(MultipartFile multipartFile: images) {
				if(multipartFile!=null && multipartFile.getSize()!=0){
					String imageLocation = PropertyMessageUtil.getConfigProperties().getProperty("image.myimages.location");
					String imageDestinationFolder = PropertyMessageUtil.getConfigProperties().getProperty("image.product.folder");
					String location = imageLocation + imageDestinationFolder;
					String photoFolder = FastDateFormat.getInstance("yyyyMMdd-HHmmssSSS").format(Calendar.getInstance(BielUtil.timeZoneJakarta()).getTime());
					String photoName = ImageUtil.upload(multipartFile, location, photoFolder, null, null);
					
					ProductImage primg = new ProductImage();
					primg.setProduct(prod);
					primg.setFolder(photoFolder);
					primg.setName(photoName);
					primg.setCreatedDate(calnow.getTime());
					productImgDao = new ProductImageDao(sessionFactory);
					productImgDao.saveOrUpdate(primg);
				}
			}
			
			List<String[]> specList = form.getSpecList();
			for(String[] strings: specList){
				pcategorySpecDao = new PCategorySpecDao(sessionFactory);
				List<PCategorySpec> pcategorySpecList = pcategorySpecDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(strings[0])));
				if(pcategorySpecList.size()>0){
					PCategorySpec pcategorySpec = pcategorySpecList.get(0);
					
					ProductSpec prodspec = new ProductSpec();
					prodspec.setPcategorySpec(pcategorySpec);
					prodspec.setProduct(prod);
					prodspec.setValue(strings[1].trim());
					prodspec.setCreatedDate(calnow.getTime());
					productSpecDao = new ProductSpecDao(sessionFactory);
					productSpecDao.saveOrUpdate(prodspec);
				}
			}
			
			List<String[]> attrList = form.getAttrList();
			for(String[] strings: attrList){
				pcategoryAttrDao = new PCategoryAttrDao(sessionFactory);
				List<PCategoryAttr> pcategoryAttrList = pcategoryAttrDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(strings[0])));
				if(pcategoryAttrList.size()>0){
					PCategoryAttr pcategoryAttr = pcategoryAttrList.get(0);
					
					ProductAttr prodAttr = new ProductAttr();
					prodAttr.setPcategoryAttr(pcategoryAttr);
					prodAttr.setProduct(prod);
					prodAttr.setValue(strings[1].trim());
					prodAttr.setCreatedDate(calnow.getTime());
					productAttrDao = new ProductAttrDao(sessionFactory);
					productAttrDao.saveOrUpdate(prodAttr);
				}
			}
			
			List<String[]> skuList = form.getSkuList();
			for(int i=0;i<skuList.size();i++){
				String[] strings = skuList.get(i);
				String label = strings[0];
				String sku = generateSkuString(prod, label, (i+1));
				
				ProductSku prodsku = new ProductSku();
				prodsku.setProduct(prod);
				prodsku.setLabel(label);
				prodsku.setSku(sku);
				prodsku.setRemarks(strings[1]!=null&&!strings[1].isEmpty()?strings[1]:null);
				prodsku.setStock(strings[2]!=null&&!strings[2].isEmpty()?Integer.parseInt(strings[2]):0);
				prodsku.setAddtPrice(strings[3]!=null&&!strings[3].isEmpty()?new BigDecimal(strings[3]):BigDecimal.ZERO);
				prodsku.setCreatedDate(calnow.getTime());
				productSkuDao = new ProductSkuDao(sessionFactory);
				productSkuDao.saveOrUpdate(prodsku);
			}
			
			errCode = 0;
			id = prod.getID()!=null?CipherUtil.encrypt(prod.getID().toString()):"";
			
		} catch (Exception e) {
			e.printStackTrace();
			errCode = -1;
			errMsg = e.getLocalizedMessage();
		}
		
		bean.setErrCode(errCode);
		bean.setErrMsg(errMsg);
		bean.setId(id);
		
		return bean;
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
	
	private String generateSkuString(Product prod, String label, int count){
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
