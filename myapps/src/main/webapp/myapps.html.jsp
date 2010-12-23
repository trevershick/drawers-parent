<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul class="myappsdrawer">
	<c:forEach items="${apps }" var="a">
	<li><a href="${a.relativeUrl }">${a.name }</a></li>
	</c:forEach>
</ul>