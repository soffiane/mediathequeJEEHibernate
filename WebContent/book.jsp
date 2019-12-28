<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page errorPage="error.jsp"%>
<html>
<head>
<title>Cybermediatheque - Detail Livre</title>
</head>
<body>

	<table cellspacing="0" cellpadding="5" width="100%">
		<%--HEADER--%>
		<tr>
			<td colspan="3"><jsp:include page="common/header.jsp" /></td>
		</tr>

		<tr>
			<%--NAVIGATION--%>
			<td valign="top" width="20%"></td>

			<td align="left" valign="top" width="60%">
				<%--CENTRAL BODY--%> <jsp:useBean id="livre"
					class="com.cybermediatheque.domain.Livre" scope="request" />

				<P>
					<strong>${livre.titre}</strong> - <strong>${livre.auteur}</strong> - <strong>${livre.genre.code}</strong>
				</P>
				<TABLE cellSpacing=0 cellPadding=1 width="100%" border=0>
					<TR>
						<TD>
							<TABLE cellSpacing=0 cellPadding=2 width="100%" border=0>
								<TR>
									<TD width="20%"><IMG src="${requestScope.image}"></TD>
								</TR>
								<p>${livre.description}</p>
							</TABLE>
						</TD>
					</TR>
				</TABLE> 
				<c:choose>
					<c:when test="${empty sessionScope.abonne}">
                        		<p><strong>Veuillez vous connecter pour pouvoir reserver ce livre</strong></p>
                        	</c:when>
					<c:otherwise>
						<c:if test="${sessionScope.abonne.role != 'biblio' and sessionScope.abonne.role != 'admin'}">
							<c:if test="${livre.quantite != 0 && empty requestScope.emprunts}">
								<c:if test="${sessionScope.abonne.isBloque == false}">
									<A href="<%= request.getContextPath() %>/emprunts?action=reserverLivre&livreId=${livre.id}"><input type="button" name="Réserver "value="Réserver"/></A>&nbsp
								</c:if>
								<c:if test="${sessionScope.abonne.isBloque == true}">
									<p><strong>Vous ne pouvez pas réserver de livres</strong></p>
								</c:if>
	                        </c:if>
							<c:if
								test="${livre.quantite != 0 && not empty requestScope.emprunts}">
								<c:forEach var="emprunt" items="${requestScope.emprunts}">
								<c:set var="refreshSent" value="true"/>
									<c:if test="${emprunt.pk.document.id == livre.id}">
												<c:set var="refreshSent" value="false"/>
	                        					<p><strong>Vous avez deja emprunté ce livre</strong></p>
	                        		</c:if>
	                        	</c:forEach>
	                        	<c:if test="${refreshSent}">
										<c:if test="${sessionScope.abonne.isBloque == false}">
											<A
												href="<%= request.getContextPath() %>/emprunts?action=reserverLivre&livreId=${livre.id}"><input type="button" name="Réserver "value="Réserver"/></A>&nbsp
	                        			</c:if>	
	                        			<c:if test="${sessionScope.abonne.isBloque == true}">
											<p><strong>Vous ne pouvez pas réserver de livres</strong></p>
										</c:if>
								</c:if>
							</c:if>
							<c:if test="${livre.quantite == 0 && empty requestScope.emprunts}">
								<c:if test="${requestScope.alerte == null }">
									<A
										href="<%= request.getContextPath() %>/alertes?action=creerAlerte&livreId=${livre.id}"><input type="button" name="Créer une alerte "value="Créer une alerte"/></A>&nbsp
	                       		</c:if>
	                       		<c:if test="${requestScope.alerte != null }">
	                       			<p><strong>Vous avez deja crée une alerte sur ce livre</strong></p>
	                       		</c:if>
	                       	</c:if>
							<c:if
								test="${livre.quantite == 0 && not empty requestScope.emprunts}">
								<c:set var="refreshSent" value="true"/>
								<c:forEach var="emprunt" items="${requestScope.emprunts}">
									<c:if test="${emprunt.pk.document.id == livre.id}">
												<c:set var="refreshSent" value="false"/>
	                        					<p><strong>Vous avez deja emprunté ce livre</strong></p>
	                        		</c:if>
	                        	</c:forEach>
	                        	<c:if test="${refreshSent}">
										<A
											href="<%= request.getContextPath() %>/alertes?action=creerAlerte&livreId=${livre.id}"><input type="button" name="Créer une alerte "value="Créer une alerte"/></A>&nbsp
								</c:if>
							</c:if>
							<c:if
								test="${requestScope.blob != 0}">
							<A
									href="<%= request.getContextPath() %>/livres?action=telechargerLivre&livreId=${livre.id}"><input type="button" name="Télécharger livre "value="Télécharger livre"/></A>&nbsp
							</c:if>
						</c:if>	
						<c:if test="${sessionScope.abonne.role == 'biblio' or sessionScope.abonne.role == 'admin'}">
							<!-- modifier quantité et supprimer livre  -->
							<TABLE cellSpacing=0 cellPadding=20 width="100%" border=0>
						                <TR>
						                    <TD vAlign=top>
						                        <FORM name=modifierQuantiteForm action="${pageContext.request.contextPath}/livres?action=modifierQuantite&livreId=${livre.id}"  method=post>
						                            <TABLE cellSpacing=0 cellPadding=5 border=0>
						                            	<TR>
						                                    <TD align=left><B>Il y a ${livre.quantite} exemplaires de ce livre</B></TD>
						                                </TR>
						                                <TR>
						                                    <TD align=left><B>Quantité:</B>   <INPUT type="number" name=quantite></TD>
						                                </TR>
						                                <TR>
						                                    <TD align=middle colSpan=2><INPUT type=submit value="Modifier quantité" name=submit></TD>
						                                </TR>
						                            </TABLE>
						                        </FORM>
						                    </TD>
						                <TD vAlign=top>
						                <A
									href="<%= request.getContextPath() %>/livres?action=supprimerLivre&livreId=${livre.id}"><input type="button" name="Supprimer livre "value="Supprimer livre"/></A>&nbsp
						                    </TD>
						                    <TD vAlign=top>
						                <A
									href="<%= request.getContextPath() %>/livres?action=modifierLivre&livreId=${livre.id}"><input type="button" name="Modifier livre "value="Modifier livre"/></A>&nbsp
						                    </TD>
						                   </TR>
						            </TABLE>
						</c:if>	
					</c:otherwise>
				</c:choose>
				 <%--FOOTER--%>
			</td>
			<td></td>
		</tr>
		<tr>
			<td colspan="3"><jsp:include page="common/footer.jsp" /></td>
		</tr>
	</table>
</body>
</html>