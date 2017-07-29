<HTML>
    <HEAD>
        <TITLE>
            Using Java in JSP
        </TITLE>
    </HEAD>

    <BODY>
        <H1>Using Java in JSP</H1>
        <%
            int temperature = 72;

            if (temperature < 90 && temperature > 60) {
                out.println("Time for a picnic!");
            }
        %>
    </BODY>
</HTML>
