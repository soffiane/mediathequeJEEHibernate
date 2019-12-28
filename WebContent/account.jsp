<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page errorPage="error.jsp"%>
<html>
<head>
	<title>CyberMediatheque - Consultation compte abonné</title>
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

        <td align="left" width="60%">
        <%--CENTRAL BODY--%>
		 <c:choose>
				<c:when test="${empty sessionScope.abonne}">
                        <p><strong>Veuillez vous connecter pour utiliser cette fonctionnalité</strong></p>
                </c:when>
				<c:otherwise>

             <jsp:useBean id="abonne" class="com.cybermediatheque.domain.Abonne" scope="session" />

            <h2>Informations personnelles</h2>
            
            <table id="abonneProfile" border="0" cellspacing="4">
            
            	<tr>
            		 <td>Identifiant :</td><td>${abonne.id}</td>
            	</tr>

                <!-- Personal information -->
	            <tr>
		            <td>Nom :</td><td>${abonne.nom}</td>
	            </tr>
	            <tr>
		            <td>Prenom :</td><td>${abonne.prenom}</td>
	            </tr>
	            <tr>
		            <td>Date de naissance :</td><td><fmt:formatDate value="${abonne.dateNaissance}" pattern="yyyy-MM-dd" /></td>
	            </tr>
	            <tr>
		            <td>Email :</td><td>${abonne.email}</td>
	            </tr>

            </table>
            <c:choose>
				<c:when test="${empty sessionScope.abonne}">
                        <p><strong>Veuillez vous connecter pour utiliser cette fonctionnalité</strong></p>
                </c:when>
                <c:otherwise>
			            <c:if test="${sessionScope.abonne.role != 'biblio' and sessionScope.abonne.role != 'admin'}">
			            	<p><a href="modifyAccount.jsp"><input type="button" name="Modifier vos informations personnelles" value="Modifier vos informations personnelles"/></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="modifyPassword.jsp"><input type="button" name="Modifier votre mot de passe" value="Modifier votre mot de passe"/></a></p>
			            </c:if>
					            <c:if test="${sessionScope.abonne.role == 'biblio' or sessionScope.abonne.role == 'admin'}">
					            	<c:if test="${abonne.isBloque == 'true'}">
					            		<p><A href="<%= request.getContextPath() %>/abonne?action=debloquerAbonne&abonneId=${abonne.id}"><input type="button" name="Débloquer abonné" value="Débloquer abonné"/></a></p>
					            	</c:if>
					            	<c:if test="${abonne.isBloque == 'false'}">
					            		<p><A href="<%= request.getContextPath() %>/abonne?action=bloquerAbonne&abonneId=${abonne.id}"><input type="button" name="Bloquer abonné" value="Bloquer abonné"/></a></p>
					            	</c:if>
					            </c:if>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>

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