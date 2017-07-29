<%@page import = "java.util.*" session="true"%>
<HTML> 
    <HEAD>
        <TITLE>A hit counter using sessions</TITLE>
    </HEAD> 

    <BODY>
        <H1>A hit counter using sessions</H1>
        <% 
        Integer counter =
            (Integer)session.getAttribute("counter");
        if (counter == null) {
            counter = new Integer(1);
        } else {
            counter = new Integer(counter.intValue() + 1);
        }

        session.setAttribute("counter", counter);
        %>
        Number of times you've been here: <%=counter%> 
    </BODY> 
</HTML>
