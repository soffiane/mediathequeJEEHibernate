<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/mediathequeTag.tld" prefix="mediatheque" %>
<html>
<head>
	<title>CyberMédiathèque - Création livre</title>
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
		<c:choose>
			<c:when test="${empty sessionScope.abonne}">
                        		<p><strong><center><h1>Veuillez vous connecter pour pouvoir acceder à cette fonctionnalité</h1></center></strong></p>
            </c:when>
			<c:otherwise>
            <form name="createBookForm" method="post" action="<%= request.getContextPath() %>/livres?action=ajouterLivre">

                <h2>Formulaire création livre</h2>

                <table border="0" cellspacing="4">

	                <!-- Personal information -->
	                <tr>
		                <td colspan="2"><strong>Informations sur le livre</strong></td>
	                </tr>
	                <tr>
		                <td>*Titre :</td>
		                <td><input type="text" name="titre" value=""></td>
	                </tr>
	                <tr>
		                <td>*Auteur :</td>
		                <td><input type="text" name="auteur" value=""></td>
	                </tr>
	                <tr>
		                <td>*Genre :</td>
		                <td><mediatheque:genre /></td>
	                </tr>
	                <tr>
		                <td>*Année :</td>
		                <td><input type="date" name="annee" value=""></td>
	                </tr>
	                <tr>
		                <td>*Editeur :</td>
		                <td><input type="text" name="editeur" value=""></td>
	                </tr>
	                <tr>
		                <td>Description :</td>
		                <td><textarea style="width:300;height:100;" rows="40" cols="50" name="description"></textarea></td>
	                </tr>
	                <tr>
		                <td>ISBN :</td>
		                <td><input type="text" size="13" name="isbn" value=""></td>
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