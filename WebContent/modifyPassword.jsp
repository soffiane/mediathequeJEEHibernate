<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page errorPage="error.jsp"%>

<html>
<head>
	<title>CyberMediatheque - Modification mot de passe compte</title>
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

            <form name="abonneForm" method="post" action="<%= request.getContextPath() %>/abonne?action=updatePassword">

                <h2>Formulaire modification mot de passe abonné</h2>

                <table border="0" cellspacing="4">

	                <tr>
	                    <td>Customer Id</td>
	                    <td>${sessionScope.abonne.id}</td>
	                </tr>
	                <tr>
		                <td>*Ancien mot de passe :</td>
		                <td><input type="password" name="ancienMdp" ></td>
	                </tr>
	                <tr>
		                <td>*Nouveau mot de passe :</td>
		                <td><input type="password" name="nouveauMdp1" ></td>
	                </tr>
	                <tr>
		                <td>*Repetez Nouveau mot de passe :</td>
		                <td><input type="password" name="nouveauMdp2" ></td>
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