package org.jahia.modules.sharepoint;

import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.BindingProvider;

import org.apache.commons.lang.StringUtils;
import org.jahia.modules.sharepoint.to.AssetTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import com.microsoft.schemas.sharepoint.soap.GetListItems;
import com.microsoft.schemas.sharepoint.soap.GetListItemsResponse;
import com.microsoft.schemas.sharepoint.soap.Lists;
import com.microsoft.schemas.sharepoint.soap.ListsSoap;



public class SharepointList {

	private SharepointCredentials credentials;
	private ListsSoap listsoapstub;
	private List<AssetTO> assets;
	private GetListItemsResponse.GetListItemsResult result;
	private Object listResult;
	private static final String ROW_LIMIT = "150";
	private static final String VIEW_NAME = "";
	private static final GetListItems.ViewFields VIEW_FIELD = null;
	private static final GetListItems.Query QUERY = null;
	private static final GetListItems.QueryOptions QUERY_OPTIONS = null;
	private static final String WEB_ID = "";
	private static String WSDL_LIST = "";
	private static final String ASMX_LIST = "/_vti_bin/lists.asmx";

	public SharepointList(SharepointCredentials credentials) {
		this.credentials = credentials;

		if(!StringUtils.isEmpty(this.credentials.getSharepointWsdl()) ){
			WSDL_LIST = this.credentials.getSharepointWsdl();
		}
	}




	public ListsSoap getListSoap() throws Exception{
		if(listsoapstub == null){
			listsoapstub = getSPListSoapStub(credentials.getSharepointUsername(),credentials.getSharepointPassword(), credentials.getSharepointUrl());
		}
		return listsoapstub;
	}

	public List<AssetTO> getAssetsByList(String listName){
		assets = new ArrayList<AssetTO>();
		try {
			//listsoapstub = getSPListSoapStub(credentials.getSharepointUsername(),credentials.getSharepointPassword(), credentials.getSharepointUrl());
			result = getListSoap().getListItems(listName, VIEW_NAME, QUERY, VIEW_FIELD, ROW_LIMIT, QUERY_OPTIONS, WEB_ID);//listsoapstub.getListItems(listName, VIEW_NAME, QUERY, VIEW_FIELD, ROW_LIMIT, QUERY_OPTIONS, WEB_ID);
			listResult = result.getContent().get(0);

			if ((listResult != null) && (listResult instanceof Element)) {
				Element node = (Element) listResult;

				//selects a list of nodes which have z:row elements
				NodeList list = node.getElementsByTagName("z:row");

				//Displaying every result received from SharePoint, with its ID
				for (int i = 0; i < list.getLength(); i++) {
					//Gets the attributes of the current row/element
					NamedNodeMap attributes = list.item(i).getAttributes();
					assets.add(new AssetTO(attributes, credentials));
				}

			} else {
				throw new Exception(listName + " list response from SharePoint is either null or corrupt\n");
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		return assets;
	}


	private static URL convertToURLEscapingIllegalCharacters(String string){
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


	private static ListsSoap getSPListSoapStub(String username, String password, String url) throws Exception {
		ListsSoap port = null;
		if (username != null && password != null) {
			try {
				URL wsdlURL = new URL(WSDL_LIST);
				Lists service = new Lists(wsdlURL);
				port = service.getListsSoap();

				((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, username);
				((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);

				URL convertedurl = convertToURLEscapingIllegalCharacters(url + ASMX_LIST);
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


	/*** *+*+*+**+*+*+**+*+*+**+*+*+**+*+*+*  NEW PART FOR UPDATE  *+*+*+**+*+*+**+*+*+**+*+*+**+*+*+* ***/

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
	public void updateListItem(String listName, AssetTO asset) throws Exception {
		//Parameters validity check
		if (getListSoap() != null && asset.getAssetAttributes() != null && !asset.getAssetAttributes().isEmpty()) {
			try {
				ListsRequest.updateListItem(getListSoap(), listName, asset.getAssetAttributes());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


}

