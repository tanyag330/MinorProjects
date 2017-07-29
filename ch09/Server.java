import java.io.*;
import java.net.*;

public class Server
{    
    public static void main(String[] args ) 
    {
        try {    
            ServerSocket socket = new ServerSocket(80);

            Socket insocket = socket.accept();
            PrintWriter out = new PrintWriter (
                insocket.getOutputStream(), true);

            out.println("<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.0 " 
                + "transitional//EN'>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>");
            out.println("A new web page");
            out.println("</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>");
            out.println("A custom web server! Not bad.");
            out.println("</h1>");
            out.println("</body>");
            out.println("</html>");
            insocket.close();
        }
        catch (Exception e) {} 
     } 
}


