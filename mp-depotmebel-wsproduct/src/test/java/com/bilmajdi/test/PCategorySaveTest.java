package com.bilmajdi.test;

import java.util.Calendar;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import centmp.depotmebel.core.dao.PCategoryAttrDao;
import centmp.depotmebel.core.dao.PCategoryDao;
import centmp.depotmebel.core.dao.PCategorySpecDao;
import centmp.depotmebel.core.dao.PCategorySubDao;
import centmp.depotmebel.core.enums.STATUS;
import centmp.depotmebel.core.model.PCategory;
import centmp.depotmebel.core.model.PCategoryAttr;
import centmp.depotmebel.core.model.PCategorySpec;
import centmp.depotmebel.core.model.PCategorySub;
import centmp.depotmebel.core.util.CommonUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"classpath:spring/spring-*.xml"
})
public class PCategorySaveTest {
	
	@Autowired
	@Qualifier(value = "sessionFactory")
	protected SessionFactory sessionFactory;
	
	private PCategoryDao pcategoryDao;
	private PCategorySubDao pcategorySubDao;
	private PCategorySpecDao pcategorySpecDao;
	private PCategoryAttrDao pcategoryAttrDao;
	
	@Test
	public void run_() throws Exception {
		System.out.println();
		System.out.println(this.getClass().getName()+" - run_ - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		
		saveCategory();
		
		System.out.println();
	}
	
	public void saveCategory(){
		PCategory pcategory = new PCategory();
		pcategory.setName("Meja");
		pcategory.setCreatedDate(Calendar.getInstance().getTime());
		pcategory.setStatus(STATUS.ACTIVE);
		pcategoryDao = new PCategoryDao(sessionFactory);
		pcategoryDao.saveOrUpdate(pcategory);
		
		PCategorySub pcategorySub = new PCategorySub();
		pcategorySub.setPcategory(pcategory);
		pcategorySub.setName("Meja Kantor");
		pcategorySub.setCreatedDate(Calendar.getInstance().getTime());
		pcategorySubDao = new PCategorySubDao(sessionFactory);
		pcategorySubDao.saveOrUpdate(pcategorySub);
		
		pcategorySub = new PCategorySub();
		pcategorySub.setPcategory(pcategory);
		pcategorySub.setName("Meja Sekolah");
		pcategorySub.setCreatedDate(Calendar.getInstance().getTime());
		pcategorySubDao = new PCategorySubDao(sessionFactory);
		pcategorySubDao.saveOrUpdate(pcategorySub);
		
		PCategorySpec pcategorySpec = new PCategorySpec();
		pcategorySpec.setPcategory(pcategory);
		pcategorySpec.setLabel("Weight");
		pcategorySpec.setCreatedDate(Calendar.getInstance().getTime());
		pcategorySpecDao = new PCategorySpecDao(sessionFactory);
		pcategorySpecDao.saveOrUpdate(pcategorySpec);
		
		pcategorySpec = new PCategorySpec();
		pcategorySpec.setPcategory(pcategory);
		pcategorySpec.setLabel("Dimension");
		pcategorySpec.setCreatedDate(Calendar.getInstance().getTime());
		pcategorySpecDao = new PCategorySpecDao(sessionFactory);
		pcategorySpecDao.saveOrUpdate(pcategorySpec);
		
		PCategoryAttr pcategoryAttr = new PCategoryAttr();
		pcategoryAttr.setPcategory(pcategory);
		pcategoryAttr.setLabel("Material");
		pcategoryAttr.setCreatedDate(Calendar.getInstance().getTime());
		pcategoryAttrDao = new PCategoryAttrDao(sessionFactory);
		pcategoryAttrDao.saveOrUpdate(pcategoryAttr);
		
		pcategoryAttr = new PCategoryAttr();
		pcategoryAttr.setPcategory(pcategory);
		pcategoryAttr.setLabel("Colour");
		pcategoryAttr.setCreatedDate(Calendar.getInstance().getTime());
		pcategoryAttrDao = new PCategoryAttrDao(sessionFactory);
		pcategoryAttrDao.saveOrUpdate(pcategoryAttr);
	}

}
