<%@ page errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/mediathequeTag.tld" prefix="mediatheque" %>
<html>
<head>
	<title>CyberMédiathèque - Création compte abonné</title>
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
        <td valign="top" width="20%">
    	</td>

        <td align="left" width="60%">
        <%--CENTRAL BODY--%>

            <form name="customerForm" method="post" action="<%= request.getContextPath() %>/abonne?action=creerAbonne">

                <h2>Formulaire création compte abonné</h2>

                <table border="0" cellspacing="4">

	                <!-- Personal information -->
	                <tr>
		                <td colspan="2"><strong>Informations personnelles</strong></td>
	                </tr>
	                <tr>
		                <td>*Login :</td>
		                <td><input type="text" name="id_abonne" value=""></td>
	                </tr>
	                <tr>
		                <td>*Mot de passe :</td>
		                <td><input type="text" name="password" value=""></td>
	                </tr>
	                <tr>
		                <td>*Confirmez Mot de passe :</td>
		                <td><input type="text" name="password2" value=""></td>
	                </tr>
	                <tr>
		                <td>*Nom :</td>
		                <td><input type="text" name="nom" value=""></td>
	                </tr>
	                <tr>
		                <td>*Prénom :</td>
		                <td><input type="text" name="prenom" value=""></td>
	                </tr>
	                <tr>
		                <td>*Date de naissance :</td>
		                <td><input type="date" name="dateNaissance" value="1900-01-01"></td>
	                </tr>
	                <tr>
		                <td>*Email :</td>
		                <td><input type="text" name="email" value=""></td>
	                </tr>
                </table>
                <p>
                <input type="submit" name="Submit" value="Valider">
            </form>



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