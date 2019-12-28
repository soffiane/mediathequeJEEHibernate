<%@ page isErrorPage="true" %>
<html>
<head>
	<title>Cybermediatheque confirmation</title>
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



            <h1><P><font color="#00FF00"><strong>

            <jsp:scriptlet>
                if (request.getParameter("message")!=null || !"".equals(request.getParameter("message")) ) {
                		out.println(request.getParameter("message"));
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