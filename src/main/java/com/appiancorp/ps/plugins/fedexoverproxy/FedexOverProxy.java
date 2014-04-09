package com.appiancorp.ps.plugins.fedexoverproxy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.appiancorp.suiteapi.common.Name;
import com.appiancorp.suiteapi.content.ContentConstants;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.knowledge.Document;
import com.appiancorp.suiteapi.knowledge.DocumentDataType;
import com.appiancorp.suiteapi.knowledge.FolderDataType;
import com.appiancorp.suiteapi.process.exceptions.SmartServiceException;
import com.appiancorp.suiteapi.process.framework.AppianSmartService;
import com.appiancorp.suiteapi.process.framework.Input;
import com.appiancorp.suiteapi.process.framework.MessageContainer;
import com.appiancorp.suiteapi.process.framework.Order;
import com.appiancorp.suiteapi.process.framework.Required;
import com.appiancorp.suiteapi.process.framework.SmartServiceContext;
import com.appiancorp.suiteapi.process.palette.PaletteInfo;

@PaletteInfo(paletteCategory = "Category", palette = "Palette") 
//@Order ({"serverHostName", "serverPort", "ftpUsername", "ftpPassword", "ftpServerFilePath", "destinationFolder", "documentName", "documentDescription"})
public class FedexOverProxy extends AppianSmartService {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(BlankPlugin.class);

	@SuppressWarnings("unused")
	private final SmartServiceContext smartServiceCtx;
	private final ContentService contentService;

	private Long destinationFolder;
	private String serverHostName;
	private Long serverPort;
	private String ftpUsername;
	private String ftpPassword;
	private String ftpServerFilePath;
	private String documentName;
	private String documentDescription;
	private Long doc;

	@Override
	public void run() throws SmartServiceException {

	}

	public BlankPlugin(SmartServiceContext smartServiceCtx, ContentService cs) {
		super();
		this.smartServiceCtx = smartServiceCtx;
		this.contentService = cs;
	}

	public void onSave(MessageContainer messages) {
	}

	public void validate(MessageContainer messages) {
	}

	/*@Input(required=Required.ALWAYS)
	@Name("destinationFolder")
	@FolderDataType
	public void setDestinationFolder(Long val) {
		this.destinationFolder = val;
	}*/

	private SmartServiceException createException(Throwable t, String key, Object... args) {
		return new SmartServiceException.Builder(getClass(), t).userMessage(key, args).build();
	}
	
	public static SOAPMessage sendSOAPMessage(SOAPMessage message, String url, final Proxy p) throws SOAPException, MalformedURLException {
	    SOAPConnectionFactory factory = SOAPConnectionFactory.newInstance();
	    SOAPConnection connection = factory.createConnection();
	
	    URL endpoint = new URL(null, url, new URLStreamHandler() {
	        protected URLConnection openConnection(URL url) throws IOException {
	            // The url is the parent of this stream handler, so must
	            // create clone
	            URL clone = new URL(url.toString());
	
	            URLConnection connection = null;
	            if (p.address().toString().equals("0.0.0.0/0.0.0.0:80")) {
	                connection = clone.openConnection();
	            } else
	                connection = clone.openConnection(p);
	            connection.setConnectTimeout(5 * 1000); // 5 sec
	            connection.setReadTimeout(5 * 1000); // 5 sec
	            // Custom header
	            connection.addRequestProperty("Developer-Mood", "Happy");
	            return connection;
	        }
	    });
	
	    try {
	        SOAPMessage response = connection.call(message, endpoint);
	        connection.close();
	        return response;
	    } catch (Exception e) {
	        // Re-try if the connection failed
	        SOAPMessage response = connection.call(message, endpoint);
	        connection.close();
	        return response;
	    }
	}
}