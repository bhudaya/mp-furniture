package centmp.depotmebel.core.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.bilmajdi.util.TrustToCertificate;

public class UrlConnect {

	
	public static String jsonType(String url, String jsonParam) throws UrlConnException{
		String result = null;
		
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Content-Type", "application/json");
			
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(jsonParam);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			if(responseCode!=HttpURLConnection.HTTP_OK){
				throw new UrlConnException(13, "HTTP "+responseCode+": Problem Connection");
			}else{
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				result = response.toString();
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			throw new UrlConnException(12, "Problem connection: URL Invalid");
		} catch (IOException e) {
			throw new UrlConnException(11, "Problem connection: "+e.getLocalizedMessage());
		} 
		
		return result;
	}
	
	public static String jsonType(String url, String jsonParam, String token) throws UrlConnException{
		String result = null;
		
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("token", token);
			
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(jsonParam);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			if(responseCode!=HttpURLConnection.HTTP_OK){
				throw new UrlConnException(13, "HTTP "+responseCode+": Problem Connection");
			}else{
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				result = response.toString();
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			throw new UrlConnException(12, "Problem connection: URL Invalid");
		} catch (IOException e) {
			throw new UrlConnException(11, "Problem connection: "+e.getLocalizedMessage());
		} 
		
		return result;
	}
	
	public static String formTypePost(String url, String postParams) throws UrlConnException{
		String result = null;
		
		try {
			URL obj = new URL(url);
			
			TrustToCertificate.doTrustToCertificates();
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
			conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", Integer.toString(postParams.length()));
			conn.setDoOutput(true);
			
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();
			
			//boolean redirect = false;
			
			int responseCode = conn.getResponseCode();
			//System.out.println("responseCode: "+responseCode);
			/*if (responseCode != HttpURLConnection.HTTP_OK) {
				if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP || responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_SEE_OTHER)
					redirect = true;
			}
			
			System.out.println("redirect: "+redirect);
			if(redirect){
				String newUrl = conn.getHeaderField("Location");
				System.out.println("newUrl: "+newUrl);
			}*/
			
			if(responseCode!=HttpURLConnection.HTTP_OK){
				throw new UrlConnException(13, "HTTP "+responseCode+": Problem Connection");
			}else{
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
			 
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				
				result = response.toString();
			}
			
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			throw new UrlConnException(12, "Problem connection: URL Invalid");
		} catch (IOException e) {
			throw new UrlConnException(11, "Problem connection: "+e.getLocalizedMessage());
		} catch (Exception e) {
			throw new UrlConnException(11, "Exception connection: "+e.getLocalizedMessage());
		} 
		return result;
	}
	
}
