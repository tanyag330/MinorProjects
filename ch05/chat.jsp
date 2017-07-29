<%
if(request.getParameter("t") != null && 
    request.getParameter("t").equals("1")){
%>
    <HTML>
        <HEAD>
            <meta HTTP-EQUIV="Refresh" CONTENT="5">
        </HEAD>
        <BODY>
<%
    String text0 = (String) application.getAttribute("text0");
    String text1 = (String) application.getAttribute("text1");
    String text2 = (String) application.getAttribute("text2");
    String text3 = (String) application.getAttribute("text3");
    String text4 = (String) application.getAttribute("text4");
    String text5 = (String) application.getAttribute("text5");
    String text6 = (String) application.getAttribute("text6");
    String text7 = (String) application.getAttribute("text7");

    out.println("<center><h1>Chat Room</h1></center>");

    out.println("<center><table width='90%'>");

    out.println("<tr><td><font size=2>");

    if(text0 != null){
        out.println(text0);
    }

    out.println("</font></td></tr>");

    out.println("<tr><td><font size=2>");

    if(text1 != null){
        out.println(text1);
    }

    out.println("</font></td></tr>");

    out.println("<tr><td><font size=2>");

    if(text2 != null){
        out.println(text2);
    }

    out.println("</font></td></tr>");

    out.println("<tr><td><font size=2>");

    if(text3 != null){
        out.println(text3);
    }

    out.println("</font></td></tr>");

    out.println("<tr><td><font size=2>");

    if(text4 != null){
        out.println(text4);
    }
    out.println("</font></td></tr>");

    out.println("<tr><td><font size=2>");

    if(text5 != null){
        out.println(text5);
    }

    out.println("</font></td></tr>");

    out.println("<tr><td><font size=2>");

    if(text6 != null){
        out.println(text6);
    }

    out.println("</font></td></tr>");

    out.println("<tr><td><font size=2>");

    if(text7 != null){
        out.println(text7);
    }

    out.println("</font></td></tr>");

    out.println("</table></center>");
%>
        </BODY>
    </HTML>
<%
}
else{
    if(request.getParameter("textarea1") != null){
        String name = request.getParameter("text1");

    String text0 = (String) application.getAttribute("text0");
    String text1 = (String) application.getAttribute("text1");
    String text2 = (String) application.getAttribute("text2");
    String text3 = (String) application.getAttribute("text3");
    String text4 = (String) application.getAttribute("text4");
    String text5 = (String) application.getAttribute("text5");
    String text6 = (String) application.getAttribute("text6");
    String text7 = (String) application.getAttribute("text7");

    application.setAttribute("text7", text6);
    application.setAttribute("text6", text5);
    application.setAttribute("text5", text4);
    application.setAttribute("text4", text3);
    application.setAttribute("text3", text2);
    application.setAttribute("text2", text1);
    application.setAttribute("text1", text0);

    application.setAttribute("text0", "<B>" + name + ":</B> " +
        request.getParameter("textarea1"));
    }
%>
    <HTML>
        <HEAD>
        </HEAD>
        <BODY>
            <FORM NAME="form1" METHOD="POST">
                <CENTER>
                    Your name:
<%
    if(request.getParameter("text1") != null){
%>
                    <INPUT TYPE="TEXT" NAME="text1" 
                    VALUE=<% 
                    out.println(request.getParameter("text1")); %> 
                    ></INPUT>
<%
    }
    else{
%>
                    <INPUT TYPE="TEXT" NAME="text1" VALUE="Guest">
                    </INPUT>
<%
    }
%> 
                    <BR>
                    <TEXTAREA NAME="textarea1" 
                    ROWS="4" COLS="60"></TEXTAREA>
                    <BR>
                    <INPUT TYPE="SUBMIT" VALUE="Submit">
                </CENTER>
            </FORM>
            <script language="javascript">
                document.form1.textarea1.focus()
            </script>
        </BODY>
    </HTML>
<%
    }
%> 



