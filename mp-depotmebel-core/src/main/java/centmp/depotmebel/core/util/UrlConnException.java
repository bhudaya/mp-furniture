package centmp.depotmebel.core.util;

public class UrlConnException extends Exception {
	private static final long serialVersionUID = 1241452314371487746L;
	
	private Integer code;

	public UrlConnException(Integer code, String message) {
		super(message);
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
}
