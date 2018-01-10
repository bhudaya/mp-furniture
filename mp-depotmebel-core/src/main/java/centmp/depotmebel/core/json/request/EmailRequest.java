package centmp.depotmebel.core.json.request;

public class EmailRequest {
	
	private String destination;
	private String[] destinations;
	private String bbc;
	private String[] bbcs;
	private String subject;
	private String content;
	
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String[] getDestinations() {
		return destinations;
	}
	public void setDestinations(String[] destinations) {
		this.destinations = destinations;
	}
	public String getBbc() {
		return bbc;
	}
	public void setBbc(String bbc) {
		this.bbc = bbc;
	}
	public String[] getBbcs() {
		return bbcs;
	}
	public void setBbcs(String[] bbcs) {
		this.bbcs = bbcs;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
