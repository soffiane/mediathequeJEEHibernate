<%@ page errorPage="error.jsp"%>
<html>
<head>
	<title>Connexion</title>
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

        <td align="center" valign="top" width="60%">
        <%--CENTRAL BODY--%>



            
            <TABLE cellSpacing=0 cellPadding=20 width="100%" border=1>
                <TR>
                    <TD vAlign=top>
                    <P><strong>Connection à la cybermédiathque</strong></P>
                        <FORM name=loginAbonneForm action="${pageContext.request.contextPath}/connection"  method=post>
                            <TABLE cellSpacing=0 cellPadding=5 border=0>
                                <TR>
                                    <TD align=right><B>Login:</B></TD>
                                    <TD><INPUT size=15 name=abonneId> </TD>
                                </TR>
                                <TR>
                                    <TD align=right><B>Mot de passe:</B></TD>
                                    <TD><INPUT type=password size=15 name=password></TD>
                                </TR>
                                <TR>
                                    <TD align=middle colSpan=2><INPUT type=submit value="Se Connecter" name=submit></TD>
                                </TR>
                            </TABLE>
                        </FORM>
                    </TD>
                <TD vAlign=top>
                <P><strong>Recuperation du mot de passe</strong></P>
                        <FORM name=retrievePasswordForm action="${pageContext.request.contextPath}/abonne?action=retrievePassword" method=post>
                            <TABLE cellSpacing=0 cellPadding=5 border=0>
                                <TR>
                                    <TD align=right><B>Email :</B></TD>
                                    <TD><INPUT size=15 name=emailMdpOublie></TD>
                                </TR>
                                <TR>
                                    <TD align=middle colSpan=2><INPUT type=submit value="Mot de passe oublié ?" name=submit></TD>
                                </TR>
                            </TABLE>
                        </FORM>
                    </TD>
                   </TR>
            </TABLE>



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