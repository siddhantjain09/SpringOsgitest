<%@ include file="/WEB-INF/jsp/init.jsp" %>

<p>
	<b><liferay-ui:message key="testcomp.caption" /></b>
</p>

<portlet:renderURL var="editEntryURL">
    <portlet:param name="mvcRenderCommandName" value="/hello" />
</portlet:renderURL>


<a href="<%=editEntryURL%>">test</a>