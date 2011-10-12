<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<b>This App</b>
<ul class="mydocumentationdrawer">
<li><a href="http://www.google.com" target="_blank">Jook Test</a></li>
</ul>
<c:if test="${ not empty apps }">
<br/>
<b>All your apps</b>
<ul class="mydocumentationdrawer">
	<c:forEach items="${apps }" var="a">
	<li><a href="${a.relativeUrl }" target="_blank">${a.name }</a></li>
	</c:forEach>
</ul>
</c:if>