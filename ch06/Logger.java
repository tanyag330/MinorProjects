import java.io.*;
import java.util.*;
import java.security.*;
import javax.servlet.*;
import javax.servlet.http.*;

public final class Logger implements Filter 
{
    private FilterConfig filterConfig = null;

    public void doFilter(ServletRequest request, 
        ServletResponse response, FilterChain chain)
        throws IOException, ServletException 
    {
        String browser = "";
        String authType = "none";
        String username = "Not known";
        Date date1 = new Date();
        long start = System.currentTimeMillis();
        String address =  request.getRemoteAddr();
        String file = ((HttpServletRequest) request).getRequestURI();
        String host = ((HttpServletRequest) request).getRemoteHost();
        
        if(((HttpServletRequest)request).getHeader
            ("User-Agent").indexOf("MSIE") >= 0){
            browser = "Internet Explorer";
        }
        if(((HttpServletRequest)request).getHeader
            ("User-Agent").indexOf("Netscape") >= 0){
            browser = "Netscape Navigator";
        }

        String type = ((HttpServletRequest)request).getAuthType();

        if(type != null){
            Principal principal = 
                ((HttpServletRequest)request).getUserPrincipal();
            authType = type;
            username = principal.getName();
        }

        filterConfig.getServletContext().log(
            "User access at " + date1.toString() + 
            " Authentication type: " + authType +
            " User name: " + username +
            " User IP: " + address +      
            " Accessing: " + file + 
            " Host: " + host +
            " Browser: " + browser +
            " Milliseconds used: " + (System.currentTimeMillis() 
            - start) 
        );
    

        String filename = "C:\\logs\\log.txt";
        FileWriter filewriter = new FileWriter(filename, true);
        filewriter.write("User access at " + date1.toString() + 
            "User access at " + date1.toString() + 
            " Authentication type: " + authType +
            " User name: " + username +
            " User IP: " + address +      
            " Accessing: " + file + 
            " Host: " + host +
            " Browser: " + browser +
            " Milliseconds used: " + (System.currentTimeMillis() 
            - start) 
        );

        filewriter.close();

        chain.doFilter(request, response);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<BR>");
        out.println("<BR>");
        out.println("<BR>");
        out.println("<BR>");
        out.println("<BR>");
        out.println("<HR>");
        out.println("<CENTER>");
        out.println("<FONT SIZE = 2>");
        out.println("<I>");
        out.println("Your access to this page has been logged.");
        out.println("</I>");
        out.println("</FONT>");
        out.println("</CENTER>");
    }

    public void destroy() { }

    public void init(FilterConfig filterConfig) 
    {
         this.filterConfig = filterConfig;
    }
}
