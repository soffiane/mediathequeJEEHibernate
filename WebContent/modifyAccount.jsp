<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page errorPage="error.jsp"%>

<html>
<head>
	<title>CyberMediatheque - Modification informations compte</title>
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
                        		<p><strong><center><h1>Veuillez vous connecter pour pouvoir acceder à cette fonctionnalité</h1></center></strong></p>
            </c:when>
			<c:otherwise>

            <form name="abonneForm" method="post" action="<%= request.getContextPath() %>/abonne?action=updateAbonne">

                <h2>Formulaire modification informations abonné</h2>

                <table border="0" cellspacing="4">

	                <tr>
	                    <td>Customer Id</td>
	                    <td>${sessionScope.abonne.id}</td>
	                </tr>
	                <tr>
		                <td>*Nom :</td>
		                <td><input type="text" name="nom" value="${sessionScope.abonne.nom}"></td>
	                </tr>
	                <tr>
		                <td>*Prenom :</td>
		                <td><input type="text" name="prenom" value="${sessionScope.abonne.prenom}"></td>
	                </tr>
	                <tr>
		                <td>Date de naissance :</td>
		                <td><input type="datetime" name="dateNaissance" value="<fmt:formatDate value="${abonne.dateNaissance}" pattern="yyyy-MM-dd" />"></td>
	                </tr>
	                <tr>
		                <td>Email :</td>
		                <td><input type="text" name="email" value="${sessionScope.abonne.email}"></td>
	                </tr>


                </table>
                <p>
                <input type="submit" name="Submit" value="Enregistrer">
            </form>
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