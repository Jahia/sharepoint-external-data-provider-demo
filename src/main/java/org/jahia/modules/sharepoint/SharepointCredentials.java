package org.jahia.modules.sharepoint;

import org.apache.commons.lang3.StringUtils;
/**
 * The Class SharepointCredentials.
 */
public class SharepointCredentials {

	
	/** The sharepoint username. */
	private String sharepointUsername;
	
	/** The sharepoint password. */
	private String sharepointPassword;
	
	/** The sharepoint endpoint. */
	private String sharepointHost;
	
	private String sharepointPort;
	
	private String sharepointProtocol;

	private String sharepointWsdl;
		

	/**
	 * Instantiates a new sharepoint credentials.
	 *
	 * @param sharepointUsername the sharepoint username
	 * @param sharepointPassword the sharepoint password
	 */
	public SharepointCredentials(String sharepointUsername, String sharepointPassword, String sharepointHost, String sharepointProtocol, String sharepointWsdl) {
		this(sharepointUsername, sharepointPassword, sharepointHost, "", sharepointProtocol, sharepointWsdl);
	}
	
	public SharepointCredentials(String sharepointUsername, String sharepointPassword, String sharepointHost, String sharepointPort, String sharepointProtocol, String sharepointWsdl) {
		this.sharepointUsername = sharepointUsername;
		this.sharepointPassword = sharepointPassword;
		this.sharepointHost = sharepointHost;
		this.sharepointPort = sharepointPort;
		this.sharepointProtocol = sharepointProtocol;
		this.sharepointWsdl = sharepointWsdl;
	}

	/**
	 * Gets the sharepoint username.
	 *
	 * @return the sharepointUsername
	 */
	public String getSharepointUsername() {
		return sharepointUsername;
	}

	/**
	 * Sets the sharepoint username.
	 *
	 * @param sharepointUsername the sharepointUsername to set
	 */
	public void setSharepointUsername(String sharepointUsername) {
		this.sharepointUsername = sharepointUsername;
	}

	/**
	 * Gets the sharepoint password.
	 *
	 * @return the sharepointPassword
	 */
	public String getSharepointPassword() {
		return sharepointPassword;
	}

	/**
	 * Sets the sharepoint password.
	 *
	 * @param sharepointPassword the sharepointPassword to set
	 */
	public void setSharepointPassword(String sharepointPassword) {
		this.sharepointPassword = sharepointPassword;
	}

	/**
	 * @return the sharepointHost
	 */
	public String getSharepointHost() {
		return sharepointHost;
	}

	/**
	 * @param sharepointHost the sharepointHost to set
	 */
	public void setSharepointHost(String sharepointHost) {
		this.sharepointHost = sharepointHost;
	}

	/**
	 * @return the sharepointPort
	 */
	public String getSharepointPort() {
		return StringUtils.isEmpty(sharepointPort) ? "80" : sharepointPort;
	}
	
	/**
	 * @return the sharepointPort
	 */
	public int getSharepointPortInt() {
		return Integer.parseInt(getSharepointPort());
	}

	/**
	 * @param sharepointPort the sharepointPort to set
	 */
	public void setSharepointPort(String sharepointPort) {
		this.sharepointPort = sharepointPort;
	}

	/**
	 * @return the sharepointProtocol
	 */
	public String getSharepointProtocol() {
		return sharepointProtocol;
	}

	/**
	 * @param sharepointProtocol the sharepointProtocol to set
	 */
	public void setSharepointProtocol(String sharepointProtocol) {
		this.sharepointProtocol = sharepointProtocol;
	}
	/**
	 * @return the sharepointWsdl
	 */
	public String getSharepointWsdl() {
		return sharepointWsdl;
	}

	/**
	 * @param sharepointWsdl the sharepointProtocol to set
	 */
	public void setSharepointWsdl(String sharepointWsdl) {
		this.sharepointWsdl = sharepointWsdl;
	}


	public String getSharepointUrl() {
		return sharepointProtocol + "://" + sharepointHost + (getSharepointPort() != "80" ? (":" + getSharepointPort()) : "");
	}
	
	
	
	
}
