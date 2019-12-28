<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.*"%>
<%@ page errorPage="error.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cybermediatheque - Liste documents empruntés</title>
</head>
<body>
	<table cellspacing="0" cellpadding="5" width="100%">
		<%--HEADER--%>
		<tr>
			<td colspan="3"><jsp:include page="common/header.jsp" /></td>
		</tr>

		<tr>
		
		 <td align="left" valign="top" width="60%">
        
         <TABLE cellSpacing=0 cellPadding=20 width="100%" border=1>
         	<tr>
         		<td rowspan="2" valign="top" align="left" width=80%>
				<%--CENTRAL BODY--%>
				<h1>Liste documents empruntés</h1>
					<jsp:useBean id="now" class="java.util.Date"/>
						<ul>
							<c:choose>
								<c:when test="${not empty requestScope.emprunts}">
									<c:forEach var="emprunt" items="${requestScope.emprunts}">
											<li>Titre : <c:out value="${emprunt.pk.document.titre}"/> Auteur : <c:out value="${emprunt.pk.document.auteur}"/> Editeur : <c:out value="${emprunt.pk.document.editeur}"/> Année : <c:out value="${emprunt.pk.document.annee}"/>
											emprunté par <b><c:out value="${emprunt.pk.abonne.nom}"/> <c:out value="${emprunt.pk.abonne.prenom}"/> </b> à rendre avant le <b><c:out value="${emprunt.dateRetour }"/></b> <c:if test="${emprunt.dateRetour < now}"> <strong><font color="#FF0000">  EN RETARD !!!!  </font></strong></c:if> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<A href="${pageContext.request.contextPath}/emprunts?action=rendreLivre&livreId=${emprunt.document.id}&abonneId=${emprunt.abonne.id}"><input type="button" name="Rendre livre "value="Rendre livre"/></A>
											</li>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<h3>Aucun document en retard</h3>
								</c:otherwise>
							</c:choose>
						</ul>
					</td>
				</tr>
			</table>
		<tr>
			<td colspan="3"><jsp:include page="common/footer.jsp" /></td>
		</tr>
		</td>
	</table>
</body>
</html>