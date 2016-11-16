/**
 * ==========================================================================================
 * =                            JAHIA'S ENTERPRISE DISTRIBUTION                             =
 * ==========================================================================================
 *
 *                                  http://www.jahia.com
 *
 * JAHIA'S ENTERPRISE DISTRIBUTIONS LICENSING - IMPORTANT INFORMATION
 * ==========================================================================================
 *
 *     Copyright (C) 2002-2016 Jahia Solutions Group. All rights reserved.
 *
 *     This file is part of a Jahia's Enterprise Distribution.
 *
 *     Jahia's Enterprise Distributions must be used in accordance with the terms
 *     contained in the Jahia Solutions Group Terms & Conditions as well as
 *     the Jahia Sustainable Enterprise License (JSEL).
 *
 *     For questions regarding licensing, support, production usage...
 *     please contact our team at sales@jahia.com or go to http://www.jahia.com/license.
 *
 * ==========================================================================================
 */
package org.jahia.modules.sharepoint.admin;

import org.hibernate.validator.constraints.NotEmpty;
import org.jahia.modules.external.admin.mount.AbstractMountPointFactory;
import org.jahia.modules.external.admin.mount.validator.LocalJCRFolder;
import org.jahia.services.content.JCRNodeWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;

/**
 * Created by Carlos Monzon
 */
public class MountPointFactory extends AbstractMountPointFactory {
    private static final Logger logger = LoggerFactory.getLogger(MountPointFactory.class);
    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String PORT = "port";
    public static final String PROTOCOL = "protocol";
    public static final String HOST = "host";
    public static final String LIBRARY_NAME = "libraryName";
    public static final String WSDL_URL = "wsdlUrl";



    @NotEmpty
    private String name;

    @LocalJCRFolder
    private String localPath;

    @NotEmpty
    private String user;

    @NotEmpty
    private String password;

    @NotEmpty
    private String port;

    @NotEmpty
    private String protocol;

    @NotEmpty
    private String host;

    @NotEmpty
    private String libraryName;

    @NotEmpty
    private String wsdlUrl;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLocalPath() {
        return localPath;
    }

    @Override
    public String getMountNodeType() {
        return "jnt:sharepointMountPoint";
    }

    @Override
    public void setProperties(JCRNodeWrapper mountNode) throws RepositoryException {
        mountNode.setProperty(USER, user);
        mountNode.setProperty(PASSWORD, password);
        mountNode.setProperty(PORT, port);
        mountNode.setProperty(PROTOCOL, protocol);
        mountNode.setProperty(HOST, host);
        mountNode.setProperty(LIBRARY_NAME, libraryName);
        mountNode.setProperty(WSDL_URL,wsdlUrl);
    }

    @Override
    public void populate(JCRNodeWrapper nodeWrapper) throws RepositoryException {
        super.populate(nodeWrapper);
        this.name = getName(nodeWrapper.getName());
        try {
            this.localPath = nodeWrapper.getProperty("mountPoint").getNode().getPath();
        }catch (PathNotFoundException e) {
            logger.error("No local path defined for this mount point");
        }
        this.password = nodeWrapper.getPropertyAsString(PASSWORD);
        this.port = nodeWrapper.getPropertyAsString(PORT);
        this.user = nodeWrapper.getPropertyAsString(USER);
        this.protocol = nodeWrapper.getPropertyAsString(PROTOCOL);
        this.host = nodeWrapper.getPropertyAsString(HOST);
        this.libraryName = nodeWrapper.getPropertyAsString(LIBRARY_NAME);
        this.wsdlUrl = nodeWrapper.getPropertyAsString(WSDL_URL);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password= password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port= port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getWsdlUrl() {
        return wsdlUrl;
    }

    public void setWsdlUrl(String wsdlUrl) {
        this.wsdlUrl= wsdlUrl;
    }
}
