<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.*"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Liste des livres de la cybermediatheque</title>
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
					<h1>Liste des livres de la cybermediatheque</h1>
					<ul>
								<c:forEach var="livre" items="${requestScope.livres}">
										<A href="${pageContext.request.contextPath}/livres?action=afficherDetailLivre&id=${livre.id}"><li>Titre : <c:out value="${livre.titre}"/> Auteur : <c:out value="${livre.auteur}"/> Editeur : <c:out value="${livre.editeur}"/> Année : <c:out value="${livre.annee}"/>
										Genre : <c:out value="${livre.genre.code}"/> </li></a>
								</c:forEach>
					</ul>
					<c:choose>
					<c:when test="${not empty sessionScope.abonne}">
				                        <c:if test="${sessionScope.abonne.role == 'biblio' or sessionScope.abonne.role == 'admin'}">
					<A
									href="<%= request.getContextPath() %>/createBook.jsp"><input type="button" name="Créer un livre" value="Ajouter un livre"/></A>&nbsp
					</c:if>
					</c:when>
					</c:choose>
				</td>
				
				<TD vAlign=top width=20%>
			                	<P><strong>Rechercher livre par auteur</strong></P>
			                        <FORM name=findAllByAuthorForm action="${pageContext.request.contextPath}/livres?action=afficherLivresParAuteur" method=post>
			                            <TABLE cellSpacing=0 cellPadding=5 border=0>
			                                <TR>
			                                    <TD align=right><B>Auteur :</B></TD>
			                                    <TD><INPUT size=15 name=auteur></TD>
			                                </TR>
			                                <TR>
			                                    <TD align=middle colSpan=2><INPUT type=submit value="Recherche par auteur" name=submit></TD>
			                                </TR>
			                            </TABLE>
			                        </FORM>
			                        <c:choose>
			                        <c:when test="${not empty sessionScope.abonne}">
				                        <c:if test="${sessionScope.abonne.role == 'biblio' or sessionScope.abonne.role == 'admin'}">
					                        <P><strong>Rechercher livre par ISBN</strong></P>
					                        <FORM name=findAllByAuthorForm action="${pageContext.request.contextPath}/livres?action=afficherLivresParISBN" method=post>
					                            <TABLE cellSpacing=0 cellPadding=5 border=0>
					                                <TR>
					                                    <TD align=right><B>ISBN :</B></TD>
					                                    <TD><INPUT size=15 name=isbn></TD>
					                                </TR>
					                                <TR>
					                                    <TD align=middle colSpan=2><INPUT type=submit value="Recherche par ISBN" name=submit></TD>
					                                </TR>
					                            </TABLE>
					                        </FORM>
				                        </c:if>
				                      </c:when>
				                      </c:choose>
			      </TD>
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