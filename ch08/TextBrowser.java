import java.io.*;
import java.net.*;
import java.util.Date;

class TextBrowser
{
    public static void main(String args[]) throws Exception
    {
        int character;
        URL url = new URL("http://www.sun.com/index.html");
        URLConnection urlconnection = url.openConnection();

        InputStream in = urlconnection.getInputStream();

        while ((character = in.read()) != -1) {
            System.out.print((char) character);
        }

        in.close();
    }
}