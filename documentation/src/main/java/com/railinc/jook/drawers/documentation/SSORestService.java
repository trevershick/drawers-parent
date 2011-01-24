package com.railinc.jook.drawers.documentation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;


public class SSORestService extends HttpServlet {

	private static final long serialVersionUID = 8712181340575026147L;
	private String dataServicesUrl;
	private SAXParserFactory saxParserFactory;
	
	private Pattern pattern;
	public String getDataServicesUrl() {
		return dataServicesUrl;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String user = req.getRemoteUser();
		
		String pathInfo = req.getRequestURI();
		
		if (user == null) {
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		Matcher matcher = pattern.matcher(pathInfo); 
		if (!matcher.matches()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, pathInfo);
			return;
		}

		String format = matcher.group(1);
		
		// return the user's preferences
		List<Resource> appsForUser = getAppsForUser(user);
		
		// return the user's document mapping
		List<UserDocument> userDocs = retreiveUserDocumentationURLValuesforDocList(appsForUser);
		
		req.setAttribute("apps", userDocs);
		req.getRequestDispatcher(String.format("/documentation.%s.jsp", format)).forward(req, resp);
				
	}
	
	public List<UserDocument> retreiveUserDocumentationURLValuesforDocList(List<Resource> appsForUser) {
		
		HashMap<Integer, UserDocument> userDocuemntTypeMap = getAllUserDocumentationTypesAsMap();

		return updateUserDocumentationURLValuesforDocList(userDocuemntTypeMap, appsForUser);

	}
	
	private HashMap<Integer, UserDocument> getAllUserDocumentationTypesAsMap() {
		
		HashMap<Integer, UserDocument> documentationTypeMap = new HashMap<Integer, UserDocument>();
		
		UserDocument vo1 = new UserDocument();
		vo1.setKey(1);
		vo1.setName("RailSight");
		vo1.setRelativeUrl("https://www.railinc.com/rportal/alf_docs/RailSight/UG_RailSight.pdf");
		documentationTypeMap.put(1, vo1);
		
		UserDocument vo2 = new UserDocument();
		vo2.setKey(2);
		vo2.setName("UMLER/EMIS");
		vo2.setRelativeUrl("https://www.railinc.com/rportal/web/guest/umlerreferences");
		
		documentationTypeMap.put(2, vo2);
		
		/*
		 * I need to move all the possible URL to the database
		 * 
		 * List<Resource> allDocumentationTypes = retrieveAllDocumentationTypes();
		HashMap<Integer, UserDocument> documentationTypeMap = new HashMap<Integer, UserDocument>();
		for (Resource vo : allDocumentationTypes) {
			documentationTypeMap.put(vo.getKey(), vo);
		}*/
		return documentationTypeMap;
	}
	
	private List<UserDocument> updateUserDocumentationURLValuesforDocList(HashMap<Integer, UserDocument> userDocuemntTypeMap, List<Resource> appsForUser) {
		
		List<UserDocument> userDocs = new ArrayList<UserDocument>();
		// loop through the list of apps
		for(Resource appForUser : appsForUser){
			
			UserDocument userDoc = new UserDocument();
			// match the app with the user doc in the map
			if("RailSight".equals(appForUser.getName())){
				userDoc.setKey(userDocuemntTypeMap.get(1).getKey());
				userDoc.setName(userDocuemntTypeMap.get(1).getName());
				userDoc.setRelativeUrl(userDocuemntTypeMap.get(1).getRelativeUrl());
				
				userDocs.add(userDoc);
			}
			
		}
		
		return userDocs;
	}

	public void setDataServicesUrl(String dataServicesUrl) {
		this.dataServicesUrl = dataServicesUrl;
	}
	
	

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			Context ctx = new InitialContext();
			String url = (String) ctx.lookup("java:comp/env/dataServicesUrl");
			this.setDataServicesUrl(url);
		} catch (NamingException e) {
			throw new ServletException(e);
		}
		pattern = Pattern.compile("^.+\\.(html|json|xml)$");
	}

	public List<Resource> getAppsForUser(String userId) {
		String theUrl = MessageFormat.format(getDataServicesUrl() + "/SSOServices/sso/v1/resources/user/{0}", userId);
		HttpURLConnection conn = null;
		try {
			URL url = new URL(theUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			int responseCode = conn.getResponseCode();
			if (200 == responseCode) {
				InputStream xml = conn.getInputStream();
				
				
				try {
					return parseResources(xml);
				} finally {
					try {
					if (xml != null) { xml.close(); }
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				byte[] buffer = new byte[1024];
				int read = 0;
				InputStream is = conn.getErrorStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
				while (is != null && (read=is.read(buffer)) > -1) {
					baos.write(buffer,0,read);
				}
				if (is != null) {
					is.close();
				}
				log(String.format("error getting apps for %s, from : %s, HTTP response code : %d, message : ", userId, theUrl, responseCode,baos.toString()));
				baos.close();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != conn) {
				conn.disconnect();
			}
		}
		return null;
	}
	
	public List<Resource> parseResources(InputStream xml) {
		List<Resource> resources = new ArrayList<Resource>();
		if (null == xml) {
			return resources;
		}
		SSOResourcesSAXHandler handler = new SSOResourcesSAXHandler(resources);
		
		
		try {
			SAXParser parser = getParser();
			parser.parse(xml, handler);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
		Collections.sort(resources);
		return resources;
	}
	
	private SAXParser getParser() throws ParserConfigurationException, SAXException {
		return getParserFactory().newSAXParser();
	}
	
	private SAXParserFactory getParserFactory() {
		if (null == saxParserFactory) {
			saxParserFactory = SAXParserFactory.newInstance();
			saxParserFactory.setNamespaceAware(true);
		}
		return saxParserFactory;
		
	}


}
