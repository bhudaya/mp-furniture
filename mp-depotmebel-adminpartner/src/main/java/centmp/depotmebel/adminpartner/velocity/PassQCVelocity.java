package centmp.depotmebel.adminpartner.velocity;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import centmp.depotmebel.adminpartner.bean.MailTrxBean;
import centmp.depotmebel.core.model.Trx;
import centmp.depotmebel.core.model.Vendor;

public class PassQCVelocity {

	private VelocityEngine velocityEngine;
	
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	
	public String content(Trx trx, String paidDate, Vendor vendor, MailTrxBean detail){
		String result = "";
		
		try {
			Template template = velocityEngine.getTemplate("./emailtemplates/pass_qc.vm");
			System.out.println("template: "+template);
			
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("name", "Admin "+vendor.getName());
			velocityContext.put("orderno", trx.getOrderNo());
			velocityContext.put("detail", detail);
			velocityContext.put("paidDate", paidDate);
			velocityContext.put("statusDesc", "QC Passed");
			velocityContext.put("vendorName", vendor.getName());
			
			StringWriter stringWriter = new StringWriter();
			template.merge(velocityContext, stringWriter);
			
			result = stringWriter.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
