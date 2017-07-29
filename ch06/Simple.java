import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Simple implements Filter 
{
  private FilterConfig filterConfig = null;

  public void doFilter(ServletRequest request, ServletResponse response,
    FilterChain chain)
    throws IOException, ServletException 
  {

    chain.doFilter(request, response);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
    out.println("The filter wrote this.");
  }

  public void destroy() { }

  public void init(FilterConfig filterConfig) {
    this.filterConfig = filterConfig;
  }
}
