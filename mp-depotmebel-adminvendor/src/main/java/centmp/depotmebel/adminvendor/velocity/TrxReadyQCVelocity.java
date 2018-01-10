package centmp.depotmebel.adminvendor.velocity;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import centmp.depotmebel.core.model.Trx;
import centmp.depotmebel.core.model.Vendor;

public class TrxReadyQCVelocity {

	private VelocityEngine velocityEngine;
	
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	
	public String content(Trx trx, String name, String paidDate, String statusDesc, Vendor vendor){
		String result = "";
		
		try {
			Template template = velocityEngine.getTemplate("./emailtemplates/ready_qc.vm");
			System.out.println("template: "+template);
			
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("name", " Admin");
			velocityContext.put("orderno", trx.getOrderNo());
			//velocityContext.put("detail", bean.getDetail());
			velocityContext.put("paidDate", paidDate);
			velocityContext.put("statusDesc", statusDesc);
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
