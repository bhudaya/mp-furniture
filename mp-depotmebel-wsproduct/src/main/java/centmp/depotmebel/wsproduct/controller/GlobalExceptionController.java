package centmp.depotmebel.wsproduct.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bilmajdi.json.response.BaseJsonResponse;
import com.bilmajdi.util.PropertyMessageUtil;


@ControllerAdvice
public class GlobalExceptionController {
	final static Logger logger = Logger.getLogger(GlobalExceptionController.class);
	
	private void logInfo(String message){
		logger.info(message);
		System.out.println(message);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public BaseJsonResponse handleAllException(Exception ex) {
		logInfo("Class: "+this.getClass());
		logInfo("Method: handleAllException");
		logInfo("Exception: "+ex.getLocalizedMessage());
		ex.printStackTrace();
		
		String status = "10001";
		String message  = PropertyMessageUtil.getMessageProperties().getProperty("code.error.msg."+status);
		
		BaseJsonResponse handleException = new BaseJsonResponse();
		handleException.setErrorCode(Integer.parseInt(status));
		handleException.setErrorMsg("[Global] "+message);
		handleException.setTechnicalMsg(ex.getLocalizedMessage());
		
		return handleException;
	}
}
