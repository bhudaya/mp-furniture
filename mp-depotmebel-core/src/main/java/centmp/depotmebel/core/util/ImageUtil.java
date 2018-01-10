package centmp.depotmebel.core.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.multipart.MultipartFile;

import com.bilmajdi.util.RandomUtil;


public class ImageUtil {
	static Logger logger = Logger.getLogger(ImageUtil.class);
	
	
	private static void logInfo(String message){
		logger.info(message);
		System.out.println(message);
	}
	
	
	/*
	 * Reference
	 * http://www.mkyong.com/java/how-to-resize-an-image-in-java/
	 */
	public static BufferedImage resizeImage(BufferedImage originalImage, int type, int imgWidth, int imgHeight){
		BufferedImage resizedImage = new BufferedImage(imgWidth, imgHeight, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, imgWidth, imgHeight, null);
		g.dispose();
			
		return resizedImage;
	}
	
	
	public static String upload(MultipartFile multipartFile, String location, String imageFolder, 
			String[] resolutionsFor, ThreadPoolTaskExecutor threadTaskExecutor){
		String imageName = null;
		
		try {
			if(multipartFile!=null && multipartFile.getSize()!=0){
				String originalName = multipartFile.getOriginalFilename();
				String pathname = location + imageFolder + "/";
				
				//Cek & Create Folder Path
	        	File cekFolder = new File(pathname);
	            if(!cekFolder.exists()){
	            	cekFolder.mkdirs();
	            }
	            
	            File filePathNameOld = new File(pathname + originalName);
	            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(filePathNameOld));
	            stream.write(multipartFile.getBytes());
	            stream.close();
	            logInfo("Upload "+originalName+" Success");
				
	            
	            int indexOriginalName = originalName.lastIndexOf('.');
    			String incomingExtension = originalName.substring(indexOriginalName + 1);
    			String newName = RandomUtil.generateRandom(new boolean[]{true, true, false}, 9) +"."+ incomingExtension;
    			File filePathNameNew = new File(pathname + newName);
    			filePathNameOld.renameTo(filePathNameNew);
    			
    			imageName = newName;
    			
    			if(resolutionsFor!=null && resolutionsFor.length>0 && threadTaskExecutor!=null){
    				
    				String[] resolutionFor = resolutionsFor;
        			
        			HashMap<String, Object> hmThread = new HashMap<>();
        			hmThread.put("file", filePathNameNew);
        			hmThread.put("pathname", pathname);
        			hmThread.put("resolutionFor", resolutionFor);
        			threadTaskExecutor.execute(new ImageUploadThread(hmThread));
    			}
			}
			
		} catch (IOException ie) {
			ie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return imageName;
	}
	
	
}