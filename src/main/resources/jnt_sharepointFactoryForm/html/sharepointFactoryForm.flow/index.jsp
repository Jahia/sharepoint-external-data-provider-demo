<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="template" uri="http://www.jahia.org/tags/templateLib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="jcr" uri="http://www.jahia.org/tags/jcr" %>
<%@ taglib prefix="ui" uri="http://www.jahia.org/tags/uiComponentsLib" %>
<%@ taglib prefix="functions" uri="http://www.jahia.org/tags/functions" %>
<%@ taglib prefix="query" uri="http://www.jahia.org/tags/queryLib" %>
<%@ taglib prefix="utility" uri="http://www.jahia.org/tags/utilityLib" %>
<%@ taglib prefix="s" uri="http://www.jahia.org/tags/search" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--@elvariable id="currentNode" type="org.jahia.services.content.JCRNodeWrapper"--%>
<%--@elvariable id="out" type="java.io.PrintWriter"--%>
<%--@elvariable id="script" type="org.jahia.services.render.scripting.Script"--%>
<%--@elvariable id="scriptInfo" type="java.lang.String"--%>
<%--@elvariable id="workspace" type="java.lang.String"--%>
<%--@elvariable id="renderContext" type="org.jahia.services.render.RenderContext"--%>
<%--@elvariable id="currentResource" type="org.jahia.services.render.Resource"--%>
<%--@elvariable id="url" type="org.jahia.services.render.URLGenerator"--%>
<%--@elvariable id="flowRequestContext" type="org.springframework.webflow.execution.RequestContext"--%>
<%--@elvariable id="sharepointFactory" type="org.jahia.modules.sharepoint.admin.MountPointFactory"--%>

<template:addResources type="javascript" resources="jquery.min.js"/>
<template:addResources type="javascript" resources="admin/angular.min.js"/>
<template:addResources type="javascript" resources="admin/app/folderPicker.js"/>
<template:addResources type="css" resources="admin/app/folderPicker.css"/>

<%--@elvariable id="url" type="org.jahia.services.render.URLGenerator"--%>
<c:url var="urlCurrentModule" value="${url.server}${url.currentModule}/"/>


<h1 class="page-header"><fmt:message key="sharepoint_mountPoint"/></h1>
<div class="folderPickerApp" ng-app="folderPicker">
    <%@ include file="errors.jspf" %>

    <fmt:message var="selectTarget" key="sharepoint_mountPoint.selectTarget"/>

    <c:set var="i18NSelectTarget" value="${functions:escapeJavaScript(selectTarget)}"/>
    <div class="box-1" ng-controller="folderPickerCtrl" ng-init='init(${localFolders}, "${sharepointFactory.localPath}", "localPath", true, "${i18NSelectTarget}")'>
        <form:form modelAttribute="sharepointFactory" method="post">
            <fieldset title="local">
                <div class="container-fluid">

                    <div class="row-fluid">
                        <form:label path="name">
                            <fmt:message key="sharepoint_mountPoint.name"/> <span style="color: red">*</span>
                        </form:label>
                        <form:input path="name"/>
                    </div>
                    <div class="row-fluid">
                        <form:label path="user">
                            <fmt:message key="sharepoint_mountPoint.user"/> <span style="color: red">*</span>
                        </form:label>
                        <form:input path="user"/>
                    </div>

                    <div class="row-fluid">
                        <form:label path="password">
                            <fmt:message key="sharepoint_mountPoint.password"/> <span style="color: red">*</span>
                        </form:label>
                        <form:input path="password"/>
                    </div>

                    <div class="row-fluid">
                        <form:label path="host">
                            <fmt:message key="sharepoint_mountPoint.host"/> <span style="color: red">*</span>
                        </form:label>
                        <form:input path="host"/>
                    </div>

                    <div class="row-fluid">
                        <form:label path="port">
                            <fmt:message key="sharepoint_mountPoint.port"/> <span style="color: red">*</span>
                        </form:label>
                        <form:input path="port"/>
                    </div>

                    <div class="row-fluid">
                        <form:label path="protocol">
                            <fmt:message key="sharepoint_mountPoint.protocol"/> <span style="color: red">*</span>
                        </form:label>
                        <form:input path="protocol" />
                    </div>

                    <div class="row-fluid">
                        <form:label path="libraryName">
                            <fmt:message key="sharepoint_mountPoint.libraryName"/> <span style="color: red">*</span>
                        </form:label>
                        <form:input path="libraryName" />
                    </div>
                    <div class="row-fluid">
                        <form:label path="wsdlUrl">
                            <fmt:message key="sharepoint_mountPoint.wsdlUrl"/> <span style="color: red">*</span>
                        </form:label>
                        <form:input path="wsdlUrl" />
                    </div>

                    <div class="row-fluid">
                        <jsp:include page="/modules/external-provider/angular/folderPicker.jsp"/>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="container-fluid">
                    <button class="btn btn-primary" type="submit" name="_eventId_save">
                        <fmt:message key="label.save"/>
                    </button>
                    <button class="btn" type="submit" name="_eventId_cancel">
                        <fmt:message key="label.cancel"/>
                    </button>
                </div>
            </fieldset>
        </form:form>
    </div>
</div>