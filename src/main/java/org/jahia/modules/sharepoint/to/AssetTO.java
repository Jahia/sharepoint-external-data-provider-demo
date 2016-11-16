package org.jahia.modules.sharepoint.to;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.jahia.modules.external.ExternalData;
import org.jahia.modules.sharepoint.SharepointCredentials;
import org.jahia.modules.sharepoint.util.FileUtil;
import org.w3c.dom.NamedNodeMap;

/**
 * The Class AssetTO.
 */
public class AssetTO {

	/** The author. */
	private String author;

	/** The content type. */
	private String contentType;

	/** The created. */
	private String created;

	/** The modified. */
	private String modified;

	/** The file type. */
	private String fileType;

	/** The id. */
	private String id;

	/** The keywords. */
	private String keywords;

	/** The file name. */
	private String fileName;

	/** The name or title. */
	private String nameOrTitle;

	/** The title. */
	private String title;

	/** The comments. */
	private String comments;

	/** The copy right. */
	private String copyRight;

	/** The url part. */
	private String urlPart;

	/** The image byte array. */
	private byte[] imageByteArray;

	/** The credentials. */
	private SharepointCredentials credentials;

	/** The file url. */
	private String fileUrl;


	private Map<String,String> attrtibutesMap;


	private static final String ID              = "id";
	private static final String AUTHOR            = "author";
	private static final String FILE_TYPE            = "fileType";
	private static final String FILE_NAME     = "fileName";
	private static final String TITLE      = "title";
	private static final String COMMENTS   = "comments";

	/**
	 * Instantiates a new asset to.
	 *
	 * @param attributes the attributes
	 * @param credentials the credentials
	 */
	public AssetTO(NamedNodeMap attributes, SharepointCredentials credentials) {
		this.credentials = credentials;
		fillObject(attributes);
	}


	public AssetTO(ExternalData data) {
		if(data.getProperties().containsKey(TITLE)) {this.title = data.getProperties().containsKey(TITLE) ? data.getProperties().get(TITLE)[0] : "";}
		if(data.getProperties().containsKey(COMMENTS)) {this.comments = data.getProperties().containsKey(COMMENTS) ? data.getProperties().get(COMMENTS)[0] : "";}
		if(data.getProperties().containsKey(AUTHOR)) {this.author = data.getProperties().containsKey(AUTHOR) ? data.getProperties().get(AUTHOR)[0] : "";}
		if(data.getProperties().containsKey(ID)) {this.id = data.getProperties().containsKey(ID) ? data.getProperties().get(ID)[0] : "";}
	}


	/**
	 * Fill object.
	 *
	 * @param attributes the attributes
	 */
/*	public void fillObject(NamedNodeMap attributes) {

		try{this.author      = StringUtils.defaultIfEmpty(attributes.getNamedItem("ows__Author").getNodeValue(),"no data");}catch(Exception e){}
		try{this.contentType = StringUtils.defaultIfEmpty(attributes.getNamedItem("ows_ContentType").getNodeValue(),"no data");         }catch(Exception e){}
		try{this.created     = StringUtils.defaultIfEmpty(attributes.getNamedItem("ows_Created").getNodeValue(),"no data");             }catch(Exception e){}
		try{this.modified    = StringUtils.defaultIfEmpty(attributes.getNamedItem("ows_Modified").getNodeValue(),"no data");            }catch(Exception e){}
		try{this.fileType    = StringUtils.defaultIfEmpty(attributes.getNamedItem("ows_FileType").getNodeValue(),"no data");            }catch(Exception e){}
		try{this.id 		 = StringUtils.defaultIfEmpty(attributes.getNamedItem("ows_ID").getNodeValue(),"no data");                  }catch(Exception e){}
		try{this.keywords    = StringUtils.defaultIfEmpty(attributes.getNamedItem("ows_Keywords").getNodeValue(),"no data");            }catch(Exception e){}
		try{this.fileName    = StringUtils.defaultIfEmpty(attributes.getNamedItem("ows_LinkFilename").getNodeValue(),"no data");        }catch(Exception e){}
		try{this.nameOrTitle = StringUtils.defaultIfEmpty(attributes.getNamedItem("ows_NameOrTitle").getNodeValue(),"no data");         }catch(Exception e){}
		try{this.title       = StringUtils.defaultIfEmpty(attributes.getNamedItem("ows_Title").getNodeValue(),"no data");               }catch(Exception e){}
		try{this.comments    = StringUtils.defaultIfEmpty(attributes.getNamedItem("ows__Comments").getNodeValue(),"no data");           }catch(Exception e){}
		try{this.copyRight   = StringUtils.defaultIfEmpty(attributes.getNamedItem("ows_wic_System_Copyright").getNodeValue(),"no data");}catch(Exception e){}
		try{this.urlPart     = StringUtils.defaultIfEmpty(attributes.getNamedItem("ows_ThumbnailOnForm").getNodeValue(),"no data");     }catch(Exception e){}
		try{this.imageByteArray = FileUtil.getFile(credentials, this.urlPart); }catch(Exception e){ e.printStackTrace(); }
		try{this.fileUrl = StringUtils.defaultIfEmpty(credentials.getSharepointUrl() + "/" + urlPart,"no data"); }catch(Exception e){}
	}
*/
	public void fillObject(NamedNodeMap attributes) {
		try{this.author      = attributes.getNamedItem("ows__Author")        != null ? StringUtils.defaultString(attributes.getNamedItem("ows__Author").getNodeValue()) : "";             }catch(Exception e){}
		try{this.contentType = attributes.getNamedItem("ows_ContentType")    != null ?  StringUtils.defaultString(attributes.getNamedItem("ows_ContentType").getNodeValue()) : "" ;       }catch(Exception e){}
		try{this.created     = attributes.getNamedItem("ows_Created")        != null ?  StringUtils.defaultString(attributes.getNamedItem("ows_Created").getNodeValue()) : "" ;             }catch(Exception e){}
		try{this.modified    = attributes.getNamedItem("ows_Modified")       != null ?  StringUtils.defaultString(attributes.getNamedItem("ows_Modified").getNodeValue()) : "" ;     }catch(Exception e){}
		try{this.fileType    = attributes.getNamedItem("ows_FileType")       != null ?  StringUtils.defaultString(attributes.getNamedItem("ows_FileType").getNodeValue()) : "" ;         }catch(Exception e){}
		try{this.id    = attributes.getNamedItem("ows_ID")       != null ?  StringUtils.defaultString(attributes.getNamedItem("ows_ID").getNodeValue()) : "" ;          }catch(Exception e){}
		try{this.keywords    = attributes.getNamedItem("ows_Keywords")       != null ?  StringUtils.defaultString(attributes.getNamedItem("ows_Keywords").getNodeValue()) : "" ;         }catch(Exception e){}
		try{this.fileName    = attributes.getNamedItem("ows_LinkFilename")   != null ?  StringUtils.defaultString(attributes.getNamedItem("ows_LinkFilename").getNodeValue()) : "" ;         }catch(Exception e){}
		try{this.nameOrTitle = attributes.getNamedItem("ows_NameOrTitle")    != null ?  StringUtils.defaultString(attributes.getNamedItem("ows_NameOrTitle").getNodeValue()) : "" ;     }catch(Exception e){}
		try{this.title       = attributes.getNamedItem("ows_Title")          != null ?  StringUtils.defaultString(attributes.getNamedItem("ows_Title").getNodeValue()) : "" ;            }catch(Exception e){}
		try{this.comments    = attributes.getNamedItem("ows__Comments")      != null ?  StringUtils.defaultString(attributes.getNamedItem("ows__Comments").getNodeValue()) : "" ;         }catch(Exception e){}
		try{this.copyRight   = attributes.getNamedItem("ows_wic_System_Copyright") != null ?  StringUtils.defaultString(attributes.getNamedItem("ows_wic_System_Copyright").getNodeValue()) : "" ; }catch(Exception e){}
		try{this.urlPart     = attributes.getNamedItem("ows_ThumbnailOnForm")  != null ?  StringUtils.defaultString(attributes.getNamedItem("ows_ThumbnailOnForm").getNodeValue()) : "" ;     }catch(Exception e){}
		try{ if(StringUtils.isNotBlank(this.urlPart)) {this.imageByteArray = FileUtil.getFile(credentials, this.urlPart);} }catch(Exception e){ e.printStackTrace(); }
		try{ if(StringUtils.isNotBlank(this.urlPart)) {this.fileUrl = credentials.getSharepointUrl() + "/" + urlPart;} }catch(Exception e){}
	}

	public Map<String, String> getAssetAttributes(){
		attrtibutesMap = new HashMap<String,String>();
		if(StringUtils.isNotEmpty(getId()))          attrtibutesMap.put("ID", getId());
		if(StringUtils.isNotEmpty(getKeywords()))    attrtibutesMap.put("Keywords", getKeywords());
		if(StringUtils.isNotEmpty(getNameOrTitle())) attrtibutesMap.put("NameOrTitle", getNameOrTitle());
		if(StringUtils.isNotEmpty(getTitle()))       attrtibutesMap.put("Title", getTitle());
		if(StringUtils.isNotEmpty(getAuthor()))      attrtibutesMap.put("_Author", getAuthor());
		if(StringUtils.isNotEmpty(getComments()))    attrtibutesMap.put("_Comments", getComments());
		return attrtibutesMap;
	}


	/**
	 * Gets the author.
	 *
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}


	/**
	 * Sets the author.
	 *
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}


	/**
	 * Gets the content type.
	 *
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}


	/**
	 * Sets the content type.
	 *
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	/**
	 * Gets the created.
	 *
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}


	/**
	 * Sets the created.
	 *
	 * @param created the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}


	/**
	 * Gets the modified.
	 *
	 * @return the modified
	 */
	public String getModified() {
		return modified;
	}


	/**
	 * Sets the modified.
	 *
	 * @param modified the modified to set
	 */
	public void setModified(String modified) {
		this.modified = modified;
	}


	/**
	 * Gets the file type.
	 *
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}


	/**
	 * Sets the file type.
	 *
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * Gets the keywords.
	 *
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}


	/**
	 * Sets the keywords.
	 *
	 * @param keywords the keywords to set
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}


	/**
	 * Gets the file name.
	 *
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}


	/**
	 * Sets the file name.
	 *
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	/**
	 * Gets the name or title.
	 *
	 * @return the nameOrTitle
	 */
	public String getNameOrTitle() {
		return nameOrTitle;
	}


	/**
	 * Sets the name or title.
	 *
	 * @param nameOrTitle the nameOrTitle to set
	 */
	public void setNameOrTitle(String nameOrTitle) {
		this.nameOrTitle = nameOrTitle;
	}


	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}


	/**
	 * Sets the title.
	 *
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}


	/**
	 * Gets the comments.
	 *
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}


	/**
	 * Sets the comments.
	 *
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}


	/**
	 * Gets the copy right.
	 *
	 * @return the copyRight
	 */
	public String getCopyRight() {
		return copyRight;
	}


	/**
	 * Sets the copy right.
	 *
	 * @param copyRight the copyRight to set
	 */
	public void setCopyRight(String copyRight) {
		this.copyRight = copyRight;
	}


	/**
	 * Gets the url part.
	 *
	 * @return the urlPart
	 */
	public String getUrlPart() {
		return urlPart;
	}


	/**
	 * Sets the url part.
	 *
	 * @param urlPart the urlPart to set
	 */
	public void setUrlPart(String urlPart) {
		this.urlPart = urlPart;
	}


	/**
	 * Gets the image byte array.
	 *
	 * @return the imageByteArray
	 */
	public byte[] getImageByteArray() {
		return imageByteArray;
	}


	/**
	 * Sets the image byte array.
	 *
	 * @param imageByteArray the imageByteArray to set
	 */
	public void setImageByteArray(byte[] imageByteArray) {
		this.imageByteArray = imageByteArray;
	}


	/**
	 * Gets the file url.
	 *
	 * @return the file url
	 */
	public String getFileUrl() {
		return fileUrl;
	}


	/**
	 * Sets the file url.
	 *
	 * @param fileUrl the new file url
	 */
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("AssetTO[\n");
		builder.append("NameOrTitle: " + getTitle() + "\n");
		builder.append("FileNale: " + getFileName() + "\n");
		builder.append("Path: " + getUrlPart() + "\n");
		builder.append("ContentType: " + getContentType() + "\n");
		builder.append("FileType: " + getFileType() + "\n");
		builder.append("Author: " + getAuthor() + "\n");
		builder.append("ImageByteArray: " + getImageByteArray() + "\n");
		builder.append("]\n");
		return builder.toString();
	}



}
