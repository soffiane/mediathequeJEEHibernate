<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table cellspacing="0" width="100%">
    <tr>
        <td align="left" valign="middle">
       		<a href="<%= request.getContextPath() %>/index.jsp"><img border="0" src="<%=request.getContextPath()%>/ImageServlet?imageName=banner_logo.png"/></a>	
        </td>
       <td align="right" valign="middle">
                        <A href="<%= request.getContextPath() %>/livres?action=afficherLivres">Consulter la cybermediatheque</A>&nbsp
                         <c:choose>
                        	<c:when test="${empty sessionScope.abonne}">
                        		<A href="<%= request.getContextPath() %>/accueil?action=createAccount">Créer un compte</A>&nbsp
                        	</c:when>
                        </c:choose>
                        <c:choose>
                        	<c:when test="${empty sessionScope.abonne}">
                        		<A href="<%= request.getContextPath() %>/accueil?action=connection">Se Connecter</A>&nbsp
                        	</c:when>
                        	<c:otherwise>
                        		<c:if test="${sessionScope.abonne.role != 'biblio' or sessionScope.abonne.role != 'admin'}">
	                        		<A href="<%= request.getContextPath() %>/accueil?action=account">Consulter son compte</A>&nbsp
	                        	</c:if>
	                       		<A href="<%= request.getContextPath() %>/deconnexion">Se deconnecter</A>
	                       	</c:otherwise>
	                     </c:choose>
		</td>        
    </tr>
    <tr>
   		 <td align="left" valign="middle">
        </td>
    	<td align="right" valign="middle">
    							<c:if test="${sessionScope.abonne.role == 'biblio' or sessionScope.abonne.role == 'admin'}">
                        			<A href="<%= request.getContextPath() %>/livres?action=afficherLivresEmpruntes">Consulter les livres empruntés</A>&nbsp
                        			<A href="<%= request.getContextPath() %>/abonne?action=afficherAbonnes">Consulter la liste des abonnés</A>&nbsp
                        		</c:if>
         </td>
    </tr>
</table>

<table cellspacing="0" width="100%">
    <tr>
        <td>
            <hr noShade SIZE=1>
        </td>
    </tr>
</table>
