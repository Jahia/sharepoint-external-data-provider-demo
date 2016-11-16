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




<!-- SQL-2 query -->
<jcr:sql var="res" sql="select * from [jnt:shAsset]"/>
<div style="margin: 20px;">
    <h4><th>ASSETS FROM SHAREPOINT</th></h4>
    <table id="activitiesTable" class="table table-striped table-bordered">
        <thead>
        <th class="strava-align">Title</th>
        <th class="strava-align">File Name</th>
        <th class="strava-align">Image</th>
        </thead>
        <tbody>
        <c:forEach items="${res.nodes}" var="sharepointAsset" varStatus="status">
            <tr>
                <td class="strava-align"><input type="text" class="form-control" id="assetTitle" value="${sharepointAsset.properties['title'].string}" ></td>
                <td class="strava-align"><input type="text" class="form-control" id="assetFileName" value="${sharepointAsset.properties['fileName'].string}" ></td>
                <td class="strava-align"><a href="${sharepointAsset.properties['fileUrl'].string}"><img height="150" width="200" src="${sharepointAsset.properties['fileUrl'].string}"/></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>