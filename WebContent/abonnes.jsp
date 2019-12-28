<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.*"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Liste des abonnés de la cybermediatheque</title>
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

        <td align="left" valign="top" width="60%">
        
         <TABLE cellSpacing=0 cellPadding=20 width="100%" border=1>
        
			<tr>
				<td rowspan="2" valign="top" align="left" width=80%>
					<h1>Liste des abonnés de la cybermediatheque</h1>
					<ul>
								<c:forEach var="abonne" items="${requestScope.abonnes}">
										<A href="${pageContext.request.contextPath}/abonne?action=afficherDetailAbonne&id=${abonne.id}"><li>Nom : <c:out value="${abonne.nom}"/> Prenom : <c:out value="${abonne.prenom}"/> 
									 </li></a>
								</c:forEach>
					</ul>
			</tr>
		</table>
      </td>  
       <tr>
    	<td colspan="3">
    		<jsp:include page="common/footer.jsp"/>
    	</td>
    </tr>
</table>
</body>
</html>