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

import centmp.depotmebel.core.dao.PCategorySubDao;
import centmp.depotmebel.core.dao.ProductDao;
import centmp.depotmebel.core.enums.STATUS;
import centmp.depotmebel.core.model.PCategorySub;
import centmp.depotmebel.core.model.Product;
import centmp.depotmebel.core.util.CommonUtil;
import centmp.depotmebel.core.util.DaoException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"classpath:spring/spring-*.xml"
})
public class ProductSaveTest {
	
	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	private PCategorySubDao pcategorySubDao;
	private ProductDao productDao;
	
	
	@Test
	public void run_() throws Exception {
		System.out.println();
		System.out.println(this.getClass().getName()+" - run_ - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		saveProduct();
		
		System.out.println();
	}
	
	public PCategorySub getCategorySub(String id) throws NumberFormatException, DaoException{
		PCategorySub categorySub = null;
		
		pcategorySubDao = new PCategorySubDao(sessionFactory);
		List<PCategorySub> pcategorySubList = pcategorySubDao.loadBy(Order.asc("ID"), 1, Restrictions.eq("ID", Long.valueOf(id)));
		if(pcategorySubList.size()>0){
			categorySub = pcategorySubList.get(0);
		}
		
		return categorySub;
	}
	
	
	public void saveProduct() throws NumberFormatException, DaoException{
		PCategorySub pcategorySub = getCategorySub("1"); 
		
		if(pcategorySub!=null){
			Product prod = new Product();
			prod.setName("Meja L 160");
			prod.setPcategory(pcategorySub.getPcategory());
			prod.setPcategorySub(pcategorySub);
			prod.setCreatedDate(Calendar.getInstance().getTime());
			prod.setStatus(STATUS.ACTIVE);
			prod.setBasePrice(new BigDecimal(1400000));
			prod.setDescription("Nope");
			productDao = new ProductDao(sessionFactory);
			productDao.saveOrUpdate(prod);
		}
		
	}

}
