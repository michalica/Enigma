import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.sun.xml.internal.txw2.Document;

public class FileUploadHandler extends HttpServlet {
	private final String UPLOAD_DIRECTORY = "/usr/local/apache-tomcat-9.0.12/uploads";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// process only if its multipart content
		if (ServletFileUpload.isMultipartContent(request)) {
			try {
				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				File temp = null;
				File encrypted = null;
				File decrypted = null;
				String fileName = null;
				String switcher = "";
				String fromKey = "";
				String keyGlobal = "";

				for (FileItem item : multiparts) {
					if (!item.isFormField()) {
						fileName = item.getName();
						String name = new File(item.getName()).getName();
						temp = new File(UPLOAD_DIRECTORY + File.separator + name);
						item.write(temp);
						encrypted = new File(UPLOAD_DIRECTORY + File.separator + name + ".enc");
					} else {
						if (item.getFieldName().equals("fname")) {
							fromKey = item.getString();
						} else if (item.getFieldName().equals("checkbox")) {
							switcher = item.getString();
						}
					}
				}
				String fileToDownload = "";
				String fileNameToDownload = "";
				System.out.println(switcher);
				if (switcher.equals("on")) {
					 /* Decodes a Base64 encoded String into a byte array */
				  byte[] decodedKey = Base64.getDecoder().decode(fromKey);

				  /* Constructs a secret key from the given byte array */
				  SecretKey key = new SecretKeySpec(decodedKey, 0,
				    decodedKey.length, "AES");
					  
					CryptoUtils.decrypt(key, temp, encrypted);
					fileToDownload = UPLOAD_DIRECTORY + File.separator + fileName;
					System.out.println(fileNameToDownload);
					fileNameToDownload = fileName.split(".enc")[0];
				} else {
					SecretKey key = this.getPublicKey();
					System.out.print(Base64.getEncoder().encodeToString(key.getEncoded()));
					CryptoUtils.encrypt(key, temp, encrypted);
					fileToDownload = UPLOAD_DIRECTORY + File.separator + fileName + ".enc";
					fileNameToDownload = fileName + ".enc";
					response.setHeader("Content-Disposition", "attachment; filename=\"" + fileNameToDownload + "\"");
					response.setHeader("key", Base64.getEncoder().encodeToString(key.getEncoded()));
					keyGlobal = Base64.getEncoder().encodeToString(key.getEncoded());

				}
				response.setContentType("text/plain");
				request.setAttribute("InfoLog", keyGlobal);
				
				OutputStream out = response.getOutputStream();
				FileInputStream in = new FileInputStream(encrypted);
				byte[] buffer = new byte[4096];
				int length;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
				in.close();
				out.flush();

				// File uploaded successfully
				request.setAttribute("message", "File Uploaded Successfully");

//				System.out.println(fileToDownload);
//				File file = new File(fileToDownload);
//				FileInputStream fileIn = new FileInputStream(file);
//				ServletOutputStream outRead = response.getOutputStream();
//
//				byte[] outputByte = new byte[4096];
//				//copy binary contect to output stream
//				while(fileIn.read(outputByte, 0, 4096) != -1)
//				{
//					out.write(outputByte, 0, 4096);
//				}
//				fileIn.close();
//				out.flush();
//				out.close();
//				PrintWriter outA = response.getWriter();
				response.setContentType("APPLICATION/OCTET-STREAM");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileToDownload + "\"");
				response.setHeader("key", keyGlobal);

				// use inline if you want to view the content in browser, helpful for
				// pdf file

				FileInputStream fileInputStream = new FileInputStream(fileToDownload);

				int i;
				while ((i = fileInputStream.read()) != -1) {
					out.write(i);
				}
				fileInputStream.close();
				in.close();
				out.flush();
				
			} catch (Exception ex) {
				System.out.println(ex);
				request.setAttribute("message", "File Upload Failed due to " + ex);
			}

		} else {
			System.out.println("dasda");
			request.setAttribute("message", "Sorry this Servlet only handles file upload request");
		}
//		request.getRequestDispatcher("/result.jsp").forward(request, response);

	}

	public static SecretKey getPublicKey() throws Exception {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128);
		SecretKey key = keyGen.generateKey();
		return key;
	}

}