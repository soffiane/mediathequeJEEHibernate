<%@ page errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/mediathequeTag.tld" prefix="mediatheque" %>
<html>
<head>
	<title>CyberMédiathèque - Modification livre</title>
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

            <form name="modifyBookForm" method="post" action="<%= request.getContextPath() %>/livres?action=validerModifierLivre">

                <h2>Formulaire modification livre</h2>

                <table border="0" cellspacing="4">
					<input type="hidden" name="idLivre" value="${requestScope.livre.id}"/>
	                <!-- Personal information -->
	                <tr>
		                <td colspan="2"><strong>Informations sur le livre</strong></td>
	                </tr>
	                <tr>
		                <td>*Titre :</td>
		                <td><input type="text" name="titre" value="${requestScope.livre.titre}"></td>
	                </tr>
	                <tr>
		                <td>*Auteur :</td>
		                <td><input type="text" name="auteur" value="${requestScope.livre.auteur}"></td>
	                </tr>
	                <tr>
		                <td>*Genre :</td>
		                <td><mediatheque:genre /></td>
	                </tr>
	                <tr>
		                <td>*Année :</td>
		                <td><input type="date" name="annee" value="${requestScope.livre.annee}"></td>
	                </tr>
	                <tr>
		                <td>*Editeur :</td>
		                <td><input type="text" name="editeur" value="${requestScope.livre.editeur}"></td>
	                </tr>
	                <tr>
		                <td>Description :</td>
		                <td><textarea style="width:300;height:100;" rows="40" cols="50" name="description" >${requestScope.livre.description}</textarea></td>
	                </tr>
	                <tr>
		                <td>ISBN :</td>
		                <td><input type="text" size="13" name="isbn" value="${requestScope.livre.isbn}"></td>
	                </tr>
	                 <tr>
		                <td>Document (format pdf uniquement) :</td>
		                <td><input type="file" name="document" value=""></td>
	                </tr>
	                <tr>
		                <td>Image :</td>
		                <td><input type="file" name="image" value=""></td>
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