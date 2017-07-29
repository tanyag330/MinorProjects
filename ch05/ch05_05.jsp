<HTML>
  <HEAD>
    <TITLE>Reading Text Using Text Fields</TITLE>
  </HEAD>

    <BODY>
        <H1>Reading Text Using Text Fields</H1>
        Your name is 
        <% out.println(request.getParameter("text")); %>
   </BODY>
</HTML>
