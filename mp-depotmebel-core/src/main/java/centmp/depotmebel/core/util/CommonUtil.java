package centmp.depotmebel.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.log4j.Logger;

import com.bilmajdi.util.PropertyMessageUtil;

public class CommonUtil {
	final static Logger logger = Logger.getLogger(CommonUtil.class);
	
	/*private static void logInfo(String message){
		logger.info(message);
		System.out.println(message);
	}*/

	public static boolean checkMandatoryParameter(List<String> params){
		int i=0;
		for(String param: params){
			//logInfo("param: "+param);
			if(param==null || param.equalsIgnoreCase("")){
				System.out.println("index: "+i);
				return false;
			}
			i++;
		}
		return true;
	}
	
	public static TimeZone timeZoneJakarta(){
		TimeZone tzJakarta = TimeZone.getTimeZone("Asia/Jakarta");
		return tzJakarta;
	}
	
	public static String generateIdBySecureRandom() throws NoSuchAlgorithmException{
		SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
		//System.out.println("prng: "+prng.nextInt());
		
		//generate a random number
		String randomNum = new Integer(prng.nextInt()).toString();

	    //get its digest
	    MessageDigest sha = MessageDigest.getInstance("SHA-1");
	    byte[] aInput =  sha.digest(randomNum.getBytes());
	    //System.out.println("lngth: "+aInput.length);
		
	    StringBuilder result = new StringBuilder();
	    char[] digits = {'0','1','3','5','7','9','m','a','k','h','s','u','b','i','e','l'};
	    for (int idx = 0; idx < aInput.length; ++idx) {
	    	byte b = aInput[idx];
	    	result.append(digits[ (b&0xf0) >> 4 ]);
	    	result.append(digits[ b&0x0f]);
	    }
	    
	    return result.toString();
	}
	
	public static String generateIdBySecureRandom2() throws NoSuchAlgorithmException{
		SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
		//System.out.println("prng: "+prng.nextInt());
		
		//generate a random number
		String randomNum = new Integer(prng.nextInt()).toString();

	    //get its digest
	    MessageDigest sha = MessageDigest.getInstance("SHA-1");
	    byte[] aInput =  sha.digest(randomNum.getBytes());
	    //System.out.println("lngth: "+aInput.length);
		
	    StringBuilder result = new StringBuilder();
	    char[] digits = {'0','1','2','3','4','5','6','7','8','9','b','i','l','m','j','d'};
	    for (int idx = 0; idx < aInput.length; ++idx) {
	    	byte b = aInput[idx];
	    	result.append(digits[ (b&0xf0) >> 4 ]);
	    	result.append(digits[ b&0x0f]);
	    }
	    
	    return result.toString();
	}

	public static String currencyIDR(double value){
		String result = "0";
		try {
			String pattern1 = "#,###.##";
			DecimalFormat decimalformatter = new DecimalFormat(pattern1);
			result = decimalformatter.format(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String currencyIDRWithRP(double value){
		String result = "0";
		try {
			DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
			DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
			
			formatRp.setCurrencySymbol("Rp ");
			formatRp.setMonetaryDecimalSeparator(',');
			formatRp.setGroupingSeparator('.');
			
			kursIndonesia.setDecimalFormatSymbols(formatRp);
			
			result = kursIndonesia.format(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String generateOrderNo(String id, String seq){
		
		Random random = new Random();
		int[] int_ = {1,2,3,4,5,6,8,9};
		int index1 = random.nextInt(int_.length);
		int index2 = random.nextInt(int_.length);
		Integer r1 = int_[index1];
		Integer r2 = int_[index2];
		String rand = r1.toString()+r2.toString();
		
		int idlen = id.length();
		int seqlen = seq.length();
		
		int idmax = 3;
		int seqmax = 4;
		
		StringBuffer idbuff = new StringBuffer();
		for(int i=0;i<(idmax-idlen);i++){
			idbuff.append("0");
		}
		idbuff.append(id);
		
		StringBuffer seqbuff = new StringBuffer();
		for(int i=0;i<(seqmax-seqlen);i++){
			seqbuff.append("0");
		}
		seqbuff.append(seq);
		
		return rand + idbuff.toString() + seqbuff.toString();
	}
	
	public static boolean fileValidate(String[] ACCEPTED_EXTENSIONS, String fileName){
		
		try {
			int acceptableContentType = 0;
			int index = fileName.lastIndexOf('.');
			String incomingExtension = fileName.substring(index + 1);
            for(String extendsion : ACCEPTED_EXTENSIONS){
                if(extendsion.equalsIgnoreCase(incomingExtension)){
                	acceptableContentType++;
                }           
            }
            
            if(acceptableContentType>0){
            	return true;
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static List<String> sourceRequest(){
		List<String> list = new ArrayList<>();
		
		try {
			String get_ = PropertyMessageUtil.getConfigProperties().getProperty("source.request.list");
			String split[] = get_.split(",");
			
			for(int i=0;i<split.length;i++){
				String s = split[i].trim();
				if(!s.isEmpty()){
					list.add(s);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static boolean sourceValidate(String source){
		boolean result = false;
		
		try {
			int i=0;
			List<String> list = sourceRequest();
			for (String string : list) {
				if(string.equalsIgnoreCase(source)){
					i++;
				}
			}
			
			if(i>0){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static String currentTimeToString_ddMMyyy_HHmmss(){
		String result = "";
		Calendar calnow = Calendar.getInstance(timeZoneJakarta());
		result = FastDateFormat.getInstance("dd/MM/yyyy, HH:mm:ss").format(calnow);
		return result;
	}
	
}
