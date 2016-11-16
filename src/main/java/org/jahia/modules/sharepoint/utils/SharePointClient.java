package org.jahia.modules.sharepoint.utils;

import java.io.StringWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.BindingProvider;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import com.microsoft.schemas.sharepoint.soap.GetListItems;
import com.microsoft.schemas.sharepoint.soap.GetListItemsResponse;
import com.microsoft.schemas.sharepoint.soap.Lists;
import com.microsoft.schemas.sharepoint.soap.ListsSoap;



public class SharePointClient {
  //Test settings.
	private static String username = "administrador";
	private static String password = "jahia.password2016";
	private static String BasesharepointUrl = "http://192.168.3.15";//"https://mysharepoint.com/Book Names";
	private static ListsSoap listsoapstub;

	
	private static SharePointClient getInstance(){
		return(new SharePointClient());
	}
	public static void main(String[] args) {
		try {
			//configureProxySettings();
	
			//Authenticator.setDefault(new SimpleAuthenticator(username, password));
			
			
			//Authenticating and Opening the SOAP port of the Copy Web Service
			listsoapstub = getSPListSoapStub(username, password, BasesharepointUrl);
			SharePointClient.displaySharePointList();
			System.out.println("#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#");
			SharePointClient.displaySharePointList2();
			
		
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(ex);
		}
	}
	
	private static Logger LOGGER = Logger.getLogger(SharePointClient.class.getName());

	public static URL convertToURLEscapingIllegalCharacters(String string){
	    try {
	        String decodedURL = URLDecoder.decode(string, "UTF-8");
	        URL url = new URL(decodedURL);
	        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef()); 
	        return uri.toURL(); 
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return null;
	    }
	}

	public static void configureProxySettings()
	{
		  System.out.println("Configuring Proxy settings");
	      /*System.getProperties().put("http.proxyHost",proxyHost);
	      System.getProperties().put("http.proxyPort",proxyPort);
	      System.getProperties().put("https.proxyHost",proxyHost);
	      System.getProperties().put("https.proxyPort",proxyPort);*/
	      
	}

	public static ListsSoap getSPListSoapStub(String username, String password, String url) throws Exception {
	    ListsSoap port = null;
	    if (username != null && password != null) {
	        try {
	        	URL wsdlURL = new URL(getInstance().getClass().getClassLoader().getResource("wsdl/lists.wsdl").toExternalForm());
	        	Lists service = new Lists(wsdlURL);	
	            port = service.getListsSoap();
	            
	    		if (LOGGER.isLoggable(Level.INFO)) {
	    			LOGGER.info("LISTS Web Service Auth Username: " + username);
	    		}
	            ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, username);
	            ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);
	           // ((BindingProvider) port).getRequestContext().put(org.apache.axis2.transport.http.HTTPConstants.CHUNKED, "false");
	            
	            
	            URL convertedurl = convertToURLEscapingIllegalCharacters(url+"/_vti_bin/lists.asmx");
	            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, convertedurl.toString());
			 
	        } catch (Exception e) {
	        	e.printStackTrace();
	            throw new Exception("Error: " + e.toString());
	        }
	    } else {
	        throw new Exception("Couldn't authenticate: Invalid connection details given.");
	    }
	    return port;
	}
	

	 
	/**
	 * Creates a string from an XML file with start and end indicators
	 * @param docToString document to convert
	 * @return string of the xml document
	 */
	public static String xmlToString(Document docToString) {
	    String returnString = "";
	    try {
	        //create string from xml tree
	        //Output the XML
	        //set up a transformer
	        TransformerFactory transfac = TransformerFactory.newInstance();
	        Transformer trans;
	        trans = transfac.newTransformer();
	        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	        trans.setOutputProperty(OutputKeys.INDENT, "yes");
	        StringWriter sw = new StringWriter();
	        StreamResult streamResult = new StreamResult(sw);
	        DOMSource source = new DOMSource(docToString);
	        trans.transform(source, streamResult);
	        String xmlString = sw.toString();
	        //print the XML
	        returnString = returnString + xmlString;
	    } catch (TransformerException ex) {
	    	LOGGER.severe(ex.toString());
	    }
	    return returnString;
	}

	/**
	 * Connects to a SharePoint Lists Web Service through the given open port,
	 * and reads all the elements of the given list. Only the given column names
	 * are displayed.
	 */ 
	public static void displaySharePointList() throws Exception {
	        try {
	        	
	        	//System.out.println(listsoapstub.getListCollection());

 		// you can also give id of "Documents" node
		//{44131435-EAFB-4244-AA39-F431F55ADA9B}
		//
		String listName ="Tasks";//"Documents";
		String rowLimit = "150";
		ArrayList<String> listColumnNames = new ArrayList<String>();
		listColumnNames.add("Title");
		listColumnNames.add("Status");

				
	         //Here are additional parameters that may be set
	         String viewName = "";
	         GetListItems.ViewFields viewFields = null;
	         GetListItems.Query query = null;
	         GetListItems.QueryOptions queryOptions = null;
	         String webID = "";
	             
	         //Calling the List Web Service
	         GetListItemsResponse.GetListItemsResult result = listsoapstub.getListItems(listName, viewName, query, viewFields, rowLimit, queryOptions, webID);
	         Object listResult = result.getContent().get(0);
	            if ((listResult != null) && (listResult instanceof Element)) {
	                Element node = (Element) listResult;

	                //Dumps the retrieved info in the console
	                Document document = node.getOwnerDocument();
	                LOGGER.info("SharePoint Online Lists Web Service Response:" + SharePointClient.xmlToString(document));

	                //selects a list of nodes which have z:row elements
	                NodeList list = node.getElementsByTagName("z:row");
	                LOGGER.info("=> " + list.getLength() + " results from SharePoint Online");

	                //Displaying every result received from SharePoint, with its ID
	                for (int i = 0; i < list.getLength(); i++) {

	                    //Gets the attributes of the current row/element
	                    NamedNodeMap attributes = list.item(i).getAttributes();
	                    LOGGER.info("******** Item ID: " + attributes.getNamedItem("ows_ID").getNodeValue()+" ********");

	                    //Displays all the attributes of the list item that correspond to the column names given
	                    for (String columnName : listColumnNames) {
	                        String internalColumnName = "ows_" + columnName;
	                        if (attributes.getNamedItem(internalColumnName) != null) {
	                        	LOGGER.info(columnName + ": " + attributes.getNamedItem(internalColumnName).getNodeValue());
	                        } else {
	                            throw new Exception("Couldn't find the '" + columnName + "' column in the '" + listName + "' list in SharePoint.\n");
	                        }
	                    }
	                }
	            } else {
	                throw new Exception(listName + " list response from SharePoint is either null or corrupt\n");
	            }
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	            throw new Exception("Exception. See stacktrace.\n" + ex.toString() + "\n");
	        }
}
	
	/**
	 * Checks-out the specified file
	 * @param port Lists web service port
	 * @param pageUrl
	 * @return true if the operation succeeded; otherwise, false. 
	 */
	public static boolean checkOutFile(ListsSoap port, String pageUrl) {
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Checking-out pageUrl=" + pageUrl);
		}
		String checkoutToLocal = "true";
		String lastModified    = "";
		boolean result = port.checkOutFile(pageUrl, checkoutToLocal, lastModified);
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Check-out result = " + result);
		}
		return result;
	}
	
	/**
	 * Undo checked-out file
	 * @param port Lists web service port
	 * @param pageUrl
	 * @return true if the operation succeeded; otherwise, false. 
	 */
	public static boolean undoCheckOutFile(ListsSoap port, String pageUrl) {
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Undo checkout pageUrl=" + pageUrl);
		}
		boolean result = port.undoCheckOut(pageUrl);
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Undo checkout result = " + result);
		}
		return result;
	}
	
	/**
	 * Checks-in the specified file
	 * @param port Lists web service port
	 * @param pageUrl
	 * @param comment
	 * @return true if the operation succeeded; otherwise, false. 
	 */
	public static boolean checkInFile(ListsSoap port, String pageUrl, String comment) {
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Checking-in pageUrl=" + pageUrl + " comment=" + comment);
		}
		// checkinType = values 0, 1 or 2, where 0 = MinorCheckIn, 1 = MajorCheckIn, and 2 = OverwriteCheckIn.
		String checkinType = "0";
		boolean result = port.checkInFile(pageUrl, comment, checkinType);
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Check-in result = " + result);
		}
		return result;
	}
	

	




public static void displaySharePointList2() throws Exception {
    try {
    	
    	//System.out.println(listsoapstub.getListCollection());

	// you can also give id of "Documents" node
//{44131435-EAFB-4244-AA39-F431F55ADA9B}
//
String listName ="AssetsJahia";//"Documents";
String rowLimit = "150";
ArrayList<String> listColumnNames = new ArrayList<String>();
listColumnNames.add("NameOrTitle");
listColumnNames.add("Author");
listColumnNames.add("Title");
listColumnNames.add("_Author");
listColumnNames.add("_Comments");


		
     //Here are additional parameters that may be set
     String viewName = "";
     GetListItems.ViewFields viewFields = null;
     GetListItems.Query query = null;
     GetListItems.QueryOptions queryOptions = null;
     String webID = "";
         
     //Calling the List Web Service
     GetListItemsResponse.GetListItemsResult result = listsoapstub.getListItems(listName, viewName, query, viewFields, rowLimit, queryOptions, webID);
     Object listResult = result.getContent().get(0);

        if ((listResult != null) && (listResult instanceof Element)) {
            Element node = (Element) listResult;

            //Dumps the retrieved info in the console
            Document document = node.getOwnerDocument();
            LOGGER.info("SharePoint Online Lists Web Service Response:" + SharePointClient.xmlToString(document));

            //selects a list of nodes which have z:row elements
            NodeList list = node.getElementsByTagName("z:row");
            LOGGER.info("=> " + list.getLength() + " results from SharePoint Online");

            //Displaying every result received from SharePoint, with its ID
            for (int i = 0; i < list.getLength(); i++) {

                //Gets the attributes of the current row/element
                NamedNodeMap attributes = list.item(i).getAttributes();
                LOGGER.info("******** Item ID: " + attributes.getNamedItem("ows_ID").getNodeValue()+" ********");
         
                
                //Displays all the attributes of the list item that correspond to the column names given
                for (String columnName : listColumnNames) {
                    String internalColumnName = "ows_" + columnName;
                    if (attributes.getNamedItem(internalColumnName) != null) {
                    	LOGGER.info(columnName + ": " + attributes.getNamedItem(internalColumnName).getNodeValue());
                    } else {
                        throw new Exception("Couldn't find the '" + columnName + "' column in the '" + listName + "' list in SharePoint.\n");
                    }
                }
            }
        } else {
            throw new Exception(listName + " list response from SharePoint is either null or corrupt\n");
        }
    } catch (Exception ex) {
    	ex.printStackTrace();
        throw new Exception("Exception. See stacktrace.\n" + ex.toString() + "\n");
    }
}




}

