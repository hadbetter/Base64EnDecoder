import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Base64.Decoder;

import org.apache.commons.codec.binary.Base64;

public class UcloudFileManager {
	
	private Gui gui;
	
	public UcloudFileManager() throws Exception {
	}
	
	public void loadGui() throws Exception {
		gui = new Gui(this);
	}
	
	public String binaryFileToBase64(String filePath) throws Exception { //File file) throws Exception {
		String base64Str = null;
		FileInputStream fis = null;
		ByteArrayOutputStream baos = null;
		
		try {
			fis = new FileInputStream(new File(filePath));// + fileName));//file);
			baos = new ByteArrayOutputStream();
			
			int length = 0;
			byte[] buf = new byte[1024];
			while ((length = fis.read(buf)) != -1) {
				baos.write(buf, 0, length);
			}
			
			byte[] fileArray = baos.toByteArray();
			base64Str = new String(Base64.encodeBase64(fileArray));
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (baos != null) {
				baos.close();
			}
		}
		
		return base64Str;
	}
	
	public void createBase64TextFile(String filePath, String base64) throws Exception {
		FileOutputStream fos = null;
		ByteArrayInputStream bais = null;
		
		try {
			fos = new FileOutputStream(new File(filePath + "_base64.txt"));
			fos.write(base64.getBytes());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fos.close();
		}
	}
	
	public void createBase64TextFile(String filePath, String fileName, String base64) throws Exception {
		FileOutputStream fos = null;
		ByteArrayInputStream bais = null;
		
		try {
			fos = new FileOutputStream(new File(filePath + "_base64.txt"));
			fos.write(base64.getBytes());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fos.close();
		}
	}
	
	public void base64ToBinaryFile(String base64Str, String filePath, String fileName) throws Exception {
		
		BufferedOutputStream bos = null;
		try {
			File file = new File(filePath + fileName);//"d:/zipfile2.zip");
			bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(Base64.decodeBase64(base64Str));
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			bos.close();
		}
	}
	
	public void base64TxtToBinaryFile(String filePath, String fileName) throws Exception {
		
		BufferedOutputStream bos = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(filePath + "/" + fileName));
			
			byte[] buf = new byte[fis.available()];
			while (fis.read(buf) != -1) {
				
			}
			
			String base64 = new String(buf);
			System.out.println("### BASE64 : " + base64);
			
			String createFileName = fileName.replaceAll("_base64.txt", "");
			System.out.println("CREATE FILE NAME : " + createFileName);
			
			File fuck = new File(filePath + "/" + createFileName);
			bos = new BufferedOutputStream(new FileOutputStream(fuck));
			bos.write(Base64.decodeBase64(base64));
			
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		} finally {
			fis.close();
			bos.close();
		}
	}
	
	public static void main(String[] args) throws Exception {
		UcloudFileManager ucloudFileManager = new UcloudFileManager();
		ucloudFileManager.loadGui();
		
//		String fileName = "zipfile.zip";
//		
//		String base64 = ucloudFileManager.binaryFileToBase64("d:/" + fileName);
//		
//		ucloudFileManager.createBase64TextFile("d:/" + fileName, base64);
//		
//		ucloudFileManager.base64ToBinaryFile(base64, "d:/create/", fileName);
		
	}
	
//	public static void main(String[] args) throws Exception {
//		
//		UcloudFileManager ucloudFileManager = new UcloudFileManager();
//		String base64Str = ucloudFileManager.toBase64String(new File("d:/zipfile.zip"));//file/file.txt"));
//		System.out.println("BASE64 : " + base64Str);
//		
//		ucloudFileManager.toFile(base64Str, "d:/", "zipFile.zip");
//		
//	}
}
