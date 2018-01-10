package centmp.depotmebel.core.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.bilmajdi.util.PropertyMessageUtil;
import com.google.gson.Gson;

public class ImageUploadThread implements Runnable {
	
	private HashMap<String, Object> hm;
	

	public ImageUploadThread(HashMap<String, Object> hm) {
		this.hm = hm;
	}



	@Override
	public void run() {
		executeUploadImageGeneral();
	}
	
	
	private void executeUploadImageGeneral(){
		System.out.println("Start - executeUploadImageGeneral ");
		
		try {
			File file = (File) hm.get("file");
			String pathname  = (String) hm.get("pathname");
			String[] resolutionFor = (String[]) hm.get("resolutionFor");
			System.out.println("resolutionFor: "+new Gson().toJson(resolutionFor));
			
			BufferedImage bufferedImage = ImageIO.read(file);
			int type = bufferedImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
			
			for(int i=0;i<resolutionFor.length;i++){
				String resolution = resolutionFor[i];
				String[] split = resolution.split("x");
				
				if(split.length==2){
					
					int resWidth = Integer.parseInt(split[0]);
					int resHeight = Integer.parseInt(split[1]);
					String formatDefault = PropertyMessageUtil.getConfigProperties().getProperty("image.default.format").trim();
					String extensionDefault = PropertyMessageUtil.getConfigProperties().getProperty("image.default.extension").trim();
					
					String imagePath = pathname + resWidth+"x"+resHeight + extensionDefault;
					File imageFile = new File(imagePath);
					//System.out.println("imageFile: "+imageFile);
					BufferedImage resizedImage = ImageUtil.resizeImage(bufferedImage, type, resWidth, resHeight); 
					boolean write = ImageIO.write(resizedImage, formatDefault, imageFile);
					System.out.println("write: "+write);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Finish - executeUploadImageGeneral ");
	}

}
