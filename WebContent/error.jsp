<%@ page isErrorPage="true" %>
<html>
<head>
	<title>Cybermediatheque Error</title>
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

        <td align="center" valign="top" width="60%">
        <%--CENTRAL BODY--%>



            <h1><P><strong><font color="#FF0000">Une erreur s'est produite !!!</font></strong></P></h1>
            <h1><P><strong>

            <jsp:scriptlet>
                if (request.getParameter("exception")==null || "".equals(request.getParameter("exception")) ) {
                	if ( pageContext.getException() != null ) {
                		out.println(pageContext.getException().getMessage());
                	}
                    else
                    	out.println("Reessayez plus tard");
                } else {
                    out.println(request.getParameter("exception"));
                }
            </jsp:scriptlet>

            </strong></P></h1>

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