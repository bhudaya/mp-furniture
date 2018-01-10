package centmp.depotmebel.adminvendor.velocity;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import centmp.depotmebel.core.model.TrxItem;
import centmp.depotmebel.core.model.Vendor;

public class CourierAssignedVelocity {

	private VelocityEngine velocityEngine;
	
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	
	public String contentPartner(TrxItem trxitem, String paidDate, String vendorName, String operator, String courier){
		String result = "";
		
		try {
			Template template = velocityEngine.getTemplate("./emailtemplates/courier_assigned_partner.vm");
			System.out.println("template: "+template);
			
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("name", "Admin");
			velocityContext.put("orderno", trxitem.getTrx().getOrderNo());
			velocityContext.put("paidDate", paidDate);
			velocityContext.put("statusDesc", "On Delivery");
			velocityContext.put("vendorName", vendorName);
			velocityContext.put("operator", operator);
			velocityContext.put("courier", courier);
			
			StringWriter stringWriter = new StringWriter();
			template.merge(velocityContext, stringWriter);
			
			result = stringWriter.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String contentCourier(TrxItem trxitem, String name, String paidDate, Vendor vendor, String operator){
		String result = "";
		
		try {
			Template template = velocityEngine.getTemplate("./emailtemplates/courier_assigned_partner.vm");
			System.out.println("template: "+template);
			
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("name", name);
			velocityContext.put("orderno", trxitem.getTrx().getOrderNo());
			velocityContext.put("paidDate", paidDate);
			velocityContext.put("statusDesc", "On Delivery");
			velocityContext.put("vendorName", vendor.getName());
			velocityContext.put("operator", operator);
			
			StringWriter stringWriter = new StringWriter();
			template.merge(velocityContext, stringWriter);
			
			result = stringWriter.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
