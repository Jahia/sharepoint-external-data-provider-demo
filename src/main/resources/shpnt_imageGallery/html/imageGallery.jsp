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
<%--@elvariable id="currentNode" type="org.jahia.services.content.JCRNodeWrapper"--%>
<%--@elvariable id="out" type="java.io.PrintWriter"--%>
<%--@elvariable id="script" type="org.jahia.services.render.scripting.Script"--%>
<%--@elvariable id="scriptInfo" type="java.lang.String"--%>
<%--@elvariable id="workspace" type="java.lang.String"--%>
<%--@elvariable id="renderContext" type="org.jahia.services.render.RenderContext"--%>
<%--@elvariable id="currentResource" type="org.jahia.services.render.Resource"--%>
<%--@elvariable id="url" type="org.jahia.services.render.URLGenerator"--%>

<template:addResources type="css" resources="http://cdnjs.cloudflare.com/ajax/libs/fotorama/4.6.4/fotorama.css"/>
<template:addResources type="javascript" resources="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"/>
<template:addResources type="javascript" resources="http://cdnjs.cloudflare.com/ajax/libs/fotorama/4.6.4/fotorama.js"/>

<!-- SQL-2 query -->
<jcr:sql var="res" sql="select * from [jnt:shAsset]"/>
<div style="margin:25px;">
    <h4>ASSETS GALLERY</h4>
<div class="fotorama"  data-width="450"
     data-height="300">
        <c:forEach items="${res.nodes}" var="sharepointAsset" varStatus="status">
            <img src="${sharepointAsset.properties['fileUrl'].string}" data-caption="${sharepointAsset.properties['title'].string}"/>
        </c:forEach>
</div>
</div>

