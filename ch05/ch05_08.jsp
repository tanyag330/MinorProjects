<HTML> 
    <HEAD>
        <TITLE>A hit counter using applications</TITLE>
    </HEAD> 

    <BODY>
        <H1>A hit counter using applications</H1>
        <% 
        Integer counter =
            (Integer)application.getAttribute("counter");
        if (counter == null) {
            counter = new Integer(1);
        } else {
            counter = new Integer(counter.intValue() + 1);
        }

        application.setAttribute("counter", counter);
        %>
        Number of times you've been here: <%=counter%> 
    </BODY> 
</HTML>
