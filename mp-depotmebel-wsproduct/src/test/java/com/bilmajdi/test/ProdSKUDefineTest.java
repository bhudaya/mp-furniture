package com.bilmajdi.test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import centmp.depotmebel.core.dao.ProductAttrDao;
import centmp.depotmebel.core.dao.ProductDao;
import centmp.depotmebel.core.dao.ProductSkuDao;
import centmp.depotmebel.core.model.Product;
import centmp.depotmebel.core.model.ProductAttr;
import centmp.depotmebel.core.model.ProductSku;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.core.util.DaoException;
import centmp.depotmebel.core.util.ProductSKULooping;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"classpath:spring/spring-*.xml"
})
public class ProdSKUDefineTest {
	
	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	private ProductDao productDao;
	private ProductAttrDao prodAttrDao;
	private ProductSkuDao prodSkuDao;
	
	@Test
	public void run_() throws Exception {
		System.out.println();
		System.out.println(this.getClass().getName()+" - run_ - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		define_();
		
		System.out.println();
	}
	
	
	public void define_() throws NumberFormatException, DaoException{
		productDao = new ProductDao(sessionFactory);
		List<Product> productList = productDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf("1")));
		if (productList.size()>0) {
			Product product = productList.get(0);
			
			prodAttrDao = new ProductAttrDao(sessionFactory);
			List<ProductAttr> productAttrList = prodAttrDao.loadBy(Order.asc("ID"), Restrictions.eq("product", product));
			System.out.println("productAttrList-size: "+productAttrList.size());
			ProductSKULooping skuLooping = new ProductSKULooping();
			List<String> generatesSkus = skuLooping.result(productAttrList);
			//System.out.println("HASIL: "+new Gson().toJson(generatesSkus));
			
			for(String str: generatesSkus){
				ProductSku sku = new ProductSku();
				sku.setLabel(str);
				sku.setProduct(product);
				sku.setCreatedDate(Calendar.getInstance().getTime());
				sku.setSku("");
				sku.setRemarks("-");
				sku.setStock(5);
				sku.setAddtPrice(new BigDecimal("100000"));
				prodSkuDao = new ProductSkuDao(sessionFactory);
				prodSkuDao.saveOrUpdate(sku);
			}
		}
	}

}
 