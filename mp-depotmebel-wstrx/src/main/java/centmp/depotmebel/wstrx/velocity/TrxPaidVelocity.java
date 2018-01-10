package centmp.depotmebel.wstrx.velocity;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import centmp.depotmebel.core.model.Trx;
import centmp.depotmebel.core.util.CommonUtil;

public class TrxPaidVelocity {

	private VelocityEngine velocityEngine;
	
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	
	
	public String customerContent(Trx trx){
		String result = "";
		
		try {
			Template template = velocityEngine.getTemplate("./emailtemplates/trx_paid_customer.vm");
			System.out.println("template: "+template);
			
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("name", trx.getCustomerName());
			velocityContext.put("orderno", trx.getOrderNo());
			velocityContext.put("amountIdr", CommonUtil.currencyIDR(trx.getAmount().doubleValue()));
			
			StringWriter stringWriter = new StringWriter();
			template.merge(velocityContext, stringWriter);
			
			result = stringWriter.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String partnerContent(Trx trx, String paidDate, String statusDesc){
		String result = "";
		
		try {
			Template template = velocityEngine.getTemplate("./emailtemplates/trx_paid_partner.vm");
			System.out.println("template: "+template);
			
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("name", trx.getCustomerName());
			velocityContext.put("orderno", trx.getOrderNo());
			velocityContext.put("paidDate", paidDate);
			velocityContext.put("statusDesc", statusDesc);
			
			StringWriter stringWriter = new StringWriter();
			template.merge(velocityContext, stringWriter);
			
			result = stringWriter.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String vendorContent(Trx trx, String paidDate){
		String result = "";
		
		try {
			Template template = velocityEngine.getTemplate("./emailtemplates/trx_paid_vendor.vm");
			System.out.println("template: "+template);
			
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("name", trx.getCustomerName());
			velocityContext.put("orderno", trx.getOrderNo());
			velocityContext.put("paidDate", paidDate);
			
			StringWriter stringWriter = new StringWriter();
			template.merge(velocityContext, stringWriter);
			
			result = stringWriter.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
