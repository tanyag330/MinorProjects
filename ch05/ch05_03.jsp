<HTML>
  <HEAD>
    <TITLE>Using Recursion in JSP</TITLE>
  </HEAD>

  <BODY>
    <H1>Using Recursion in JSP</H1>
    <%!
    int factorial(int n)
    {
        if (n == 1) {
            return n;
        }
        else {
            return n * factorial(n - 1);
        }
    }
    %>

    <%
        out.println("The factorial of 6 is " + factorial(6));
    %>
  </BODY>
</HTML>
