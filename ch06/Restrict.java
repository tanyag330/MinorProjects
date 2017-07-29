import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public final class Restrict implements Filter 
{
  public void doFilter(ServletRequest request, ServletResponse response,
    FilterChain chain)
    throws IOException, ServletException 
  {
 
    GregorianCalendar calendar = new GregorianCalendar();
    Date date1 = new Date();
    calendar.setTime(date1);
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    if(hour < 9 || hour > 17) {   
        chain.doFilter(request, response);
    } else {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<HTML>");
        out.println("<HEAD>");
        out.println("<TITLE>");
        out.println("The game is not available");
        out.println("</TITLE>");
        out.println("</HEAD>");
        out.println("<BODY>");
        out.println("<H1>The game is not available</H1>");
        out.println("Sorry, that resource may not be accessed now.");
        out.println("</BODY>");
        out.println("</HTML>");
    }    
  }

  public void destroy() 
  { 
  }

  public void init(FilterConfig filterConfig) 
  {
  }
}
