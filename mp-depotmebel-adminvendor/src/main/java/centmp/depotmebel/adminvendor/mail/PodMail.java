package centmp.depotmebel.adminvendor.mail;

import java.io.File;
import java.io.StringWriter;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import centmp.depotmebel.core.model.TrxItem;

public class PodMail {

	private JavaMailSender mailSender;
	private VelocityEngine velocityEngine;
	
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	
	public void sendToCustomer(TrxItem trxItem, String receiverName, String receiverPhone, 
			String pdfName, File file){
		MimeMessage message = mailSender.createMimeMessage();
		//System.out.println("MimeMessage: "+message);
		
		Template template = velocityEngine.getTemplate("./emailtemplates/order_delivered_cust.vm");
		//System.out.println("template: "+template);
		
		try {
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("name", trxItem.getTrx().getCustomerName());
			velocityContext.put("orderno", trxItem.getTrx().getOrderNo());
			velocityContext.put("receiverName", receiverName);
			velocityContext.put("receiverPhone", receiverPhone);
			
			StringWriter stringWriter = new StringWriter();
			template.merge(velocityContext, stringWriter);
			
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("CENTSMILE TEAM <noreply@centsmile.com>");
			helper.setTo(trxItem.getTrx().getCustomerEmail());
			//if(setBcc!=null&&setBcc.equalsIgnoreCase("true"))helper.setBcc(bcc);
			helper.setSubject("Pesanan Terkirim : ["+trxItem.getTrx().getOrderNo()+"]");
			helper.setText(stringWriter.toString(), true);
			helper.addAttachment(pdfName, file);
			
			mailSender.send(message);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}	
	}
	
	public void sendToPartner(TrxItem trxItem, String[] sendTo, String paidDate, String courier, 
			String receiverName, String receiverPhone){
		MimeMessage message = mailSender.createMimeMessage();
		//System.out.println("MimeMessage: "+message);
		
		Template template = velocityEngine.getTemplate("./emailtemplates/order_delivered_tap.vm");
		//System.out.println("template: "+template);
		
		try {
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("name", "Admin");
			velocityContext.put("orderno", trxItem.getTrx().getOrderNo());
			velocityContext.put("paidDate", paidDate);
			velocityContext.put("courier", courier);
			velocityContext.put("receiverName", receiverName);
			velocityContext.put("receiverPhone", receiverPhone);
			StringWriter stringWriter = new StringWriter();
			template.merge(velocityContext, stringWriter);
			
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("CENTSMILE TEAM <noreply@centsmile.com>");
			helper.setTo(sendTo);
			//if(setBcc!=null&&setBcc.equalsIgnoreCase("true"))helper.setBcc(bcc);
			helper.setSubject("Pesanan Terkirim : ["+trxItem.getTrx().getOrderNo()+"]");
			helper.setText(stringWriter.toString(), true);
			
			mailSender.send(message);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}	
	}
	
	public void sendToVendor(TrxItem trxItem, String name, String sendTo, String paidDate, String vendorName,
			String courier, String receiverName, String receiverPhone){
		MimeMessage message = mailSender.createMimeMessage();
		//System.out.println("MimeMessage: "+message);
		
		Template template = velocityEngine.getTemplate("./emailtemplates/order_delivered_vendor.vm");
		//System.out.println("template: "+template);
		
		try {
			
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("name", name);
			velocityContext.put("orderno", trxItem.getTrx().getOrderNo());
			velocityContext.put("paidDate", paidDate);
			velocityContext.put("vendorName", vendorName);
			velocityContext.put("courier", courier);
			velocityContext.put("receiverName", receiverName);
			velocityContext.put("receiverPhone", receiverPhone);
			StringWriter stringWriter = new StringWriter();
			template.merge(velocityContext, stringWriter);
			
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("CENTSMILE TEAM <noreply@centsmile.com>");
			helper.setTo(sendTo);
			//if(setBcc!=null&&setBcc.equalsIgnoreCase("true"))helper.setBcc(bcc);
			helper.setSubject("Pesanan Terkirim : ["+trxItem.getTrx().getOrderNo()+"]");
			helper.setText(stringWriter.toString(), true);
			
			mailSender.send(message);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}	
	}
	
}
