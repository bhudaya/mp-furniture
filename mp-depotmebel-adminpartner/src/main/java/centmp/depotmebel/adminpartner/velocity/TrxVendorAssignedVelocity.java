package centmp.depotmebel.adminpartner.velocity;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import centmp.depotmebel.core.model.Trx;

public class TrxVendorAssignedVelocity {

	private VelocityEngine velocityEngine;
	
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	
	public String content(Trx trx, String paidDate){
		String result = "";
		
		try {
			Template template = velocityEngine.getTemplate("./emailtemplates/trx_set_vendor.vm");
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
