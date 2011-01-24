<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${ empty apps }">
You either have no apps selected or there was an error loading them.
</c:if>
<ul class="mydocumentationdrawer">
	<c:forEach items="${apps }" var="a">
	<li><a href="${a.relativeUrl }" target="_blank">${a.name }</a></li>
	</c:forEach>
</ul>