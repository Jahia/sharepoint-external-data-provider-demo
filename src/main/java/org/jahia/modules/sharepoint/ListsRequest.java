package org.jahia.modules.sharepoint;

import java.io.StringWriter;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.dom.ElementNSImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.microsoft.schemas.sharepoint.soap.ListsSoap;
import com.microsoft.schemas.sharepoint.soap.UpdateListItems;
import com.microsoft.schemas.sharepoint.soap.UpdateListItems.Updates;
import com.microsoft.schemas.sharepoint.soap.UpdateListItemsResponse.UpdateListItemsResult;

public class ListsRequest {

	   private Document rootDocument;
	   private Element rootDocContent;
	 


	
	/**
	 * This class creates a generic XML SOAP request pre-formatted for SharePoint
	 * Lists web services requests (aka CAML query). What remains to be added are
	 * the specific parameters (XML Elements with attributes).
	 * For an example of a CAML Doc http://msdn.microsoft.com/en-us/library/lists.lists.updatelistitems.aspx
	 * @param requestType Either New, Update or Delete
	 * @throws Exception
	 */
	public ListsRequest(String requestType) throws Exception {
	    if (requestType != null) {
	        if (requestType.equals("New") || requestType.equals("Update") || requestType.equals("Delete")) {
	            try {
	                Element rootElement = null;
	                DocumentBuilder docBuilder = null;
	                DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	                docBuilder = dbfac.newDocumentBuilder();
	                rootDocument = docBuilder.newDocument();
	 
	                //Creates the root element
	                rootElement = rootDocument.createElement("Batch");
	                rootDocument.appendChild(rootElement);
	 
	                //Creates the batch attributes
	               /// rootElement.setAttribute("ListVersion", "1");
	                rootElement.setAttribute("OnError", "Continue");
	              //  rootElement.setAttribute("ViewName", "AssetsJahia");
	                rootDocContent = rootDocument.createElement("Method");
	                rootDocContent.setAttribute("Cmd", requestType);
	                rootDocContent.setAttribute("ID", "1");
	                rootDocument.getElementsByTagName("Batch").item(0).appendChild(rootDocContent);
	            } catch (ParserConfigurationException ex) {
	                ex.printStackTrace();
	                throw (new Exception(ex.toString()));
	            }
	        } else {
	            String err = "Unsupported request type";
	            throw (new Exception(err));
	        }
	    } else {
	        String err = "Null parameters";
	        throw (new Exception(err));
	    }
	}

	
	/**
	 * This function will insert the given item in the SharePoint that corresponds
	 * to the list name given (or list GUID).
	 * @param port an already authentificated SharePoint SOAP port
	 * @param listName SharePoint list name or list GUID (guid must be enclosed in braces)
	 * @param itemAttributes This represents the content of the item that need to be inserted.
	 * The key represents the type of attribute (SharePoint column name) and the
	 * value corresponds to the item attribute value.
	 * @throws Exception 
	 */
	public static void updateListItem(ListsSoap port, String listName, Map<String, String> itemAttributes) throws Exception {
	 
	    //Parameters validity check
	    if (port != null && listName != null && itemAttributes != null && !itemAttributes.isEmpty()) {
	 
	            //Building the CAML query with one item to add, and printing request
	            ListsRequest newCompanyRequest = new ListsRequest("Update");
	            newCompanyRequest.createListItem(itemAttributes);
	            System.out.println("REQUEST:" + xmlToString(newCompanyRequest.getRootDocument()));
	 
	            //initializing the Web Service operation here
	            Updates updates = new Updates();
	 
	            //Preparing the request for the update
	            Object docObj = (Object) newCompanyRequest.getRootDocument().getDocumentElement();
	            updates.getContent().add(0, docObj);
	 
	            //Sending the insert request to the Lists.UpdateListItems Web Service
	            UpdateListItemsResult result = port.updateListItems(listName, updates);
	 
	            /*
	             *Printing the response in the console.
	             *If successful, the inserted item will be returned
	             */
	            System.out.println("RESPONSE : "
	                    + xmlToString((Document) (((ElementNSImpl) result.getContent().get(0)).getOwnerDocument())));

	    }
	}
	    
	/**
	 * Creates a SharePoint list item in the CAML format, and adds it to the rootRequest.
	 * In SharePoint, this corresponds to a line in a list. The parameters given
	 * here would correspond respectively to the name of the column where to
	 * insert the info, and then the info itself.
	 * The requestTypeElement should already be initialized before calling this
	 * method.
	 * XML example output:
	 * < Field Name="ID" >4< Field >
	 * < Field Name="Field_Name" >Value< /Field >
	 * @param fields Contains a HashMap with attribute names as keys, and attributes
	 * values as content
	 * @return true if the item has been successfully added to the caml request
	 */
	public boolean createListItem(Map<String, String> fields) {
	    //params check
	    if (fields != null && getRootDocContent() != null && this.getRootDocument() != null && !fields.isEmpty()) {
	        Element createdElement = null;
	        //Adds attribute by attribute to fields
	        for (Map.Entry<String, String> aField : fields.entrySet()) {
	            createdElement = this.getRootDocument().createElement("Field");
	            createdElement.setAttribute("Name", aField.getKey());
	            Text attributeValue = getRootDocument().createTextNode("" + aField.getValue());
	            createdElement.appendChild(attributeValue);
	            this.getRootDocContent().appendChild(createdElement);
	        }
	        return true;
	    }
	    return false;
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
		    	//LOGGER.severe(ex.toString());
		    }
		    return returnString;
		}

	
    /**
     * @return the rootDocument
     */
    public Document getRootDocument() {
        return rootDocument;
    }
 
    /**
     * @return the rootDocContent
     */
    public Element getRootDocContent() {
        return rootDocContent;
    }

}
