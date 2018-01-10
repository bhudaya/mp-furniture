package centmp.depotmebel.wstrx.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bilmajdi.json.response.BaseJsonResponse;
import com.bilmajdi.util.PropertyMessageUtil;

import centmp.depotmebel.core.util.CommonUtil;


@ControllerAdvice
public class GlobalExceptionController {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public BaseJsonResponse handleAllException(Exception ex) {
		System.out.println(this.getClass().getName()+" - handleAllException - "+CommonUtil.currentTimeToString_ddMMyyy_HHmmss());
		ex.printStackTrace();
		
		String status = "10001";
		String message  = PropertyMessageUtil.getMessageProperties().getProperty("code.error.msg."+status);
		
		BaseJsonResponse handleException = new BaseJsonResponse();
		handleException.setErrorCode(Integer.parseInt(status));
		handleException.setErrorMsg("[Global] "+message);
		handleException.setTechnicalMsg(ex.getLocalizedMessage());
		
		System.out.println();
		return handleException;
	}
}
