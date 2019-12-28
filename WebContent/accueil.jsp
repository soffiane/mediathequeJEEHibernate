<%@ page errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Cyber Mediatheque</title>
</head>
<body>

<table cellspacing="0" cellpadding="5" width="100%">
    <%--HEADER--%>
	<tr>
		<td colspan="3">
            <jsp:include page="common/header.jsp"/>
		</td>
	</tr>

	<tr>
        <%--NAVIGATION--%>
        <td valign="top" width="20%">
    	</td>

        <td align="center" width="60%">
        <%--CENTRAL BODY--%>



            <c:if test="${not empty sessionScope.abonne}">
                <h2><center>Bienvenue <strong>${sessionScope.abonne.prenom} ${sessionScope.abonne.nom}</strong></center></h2>
            </c:if>

    <%--FOOTER--%>
    	</td>
        <td></td>
    </tr>
    <tr>
    	<td colspan="3">
    		<jsp:include page="common/footer.jsp"/>
    	</td>
    </tr>
</table>
</body>
</html>
