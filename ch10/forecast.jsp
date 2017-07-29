<%@ page contentType="image/jpeg" import="java.io.*, java.awt.*, java.awt.image.*,java.net.*,com.sun.image.codec.jpeg.*" %>

<%
    Cookie[] cookies = request.getCookies();
    boolean foundCookie = false;
    String zip = "";

    if(request.getParameter("zip") != null){
        zip = request.getParameter("zip");
        drawImage(zip, response);
        return;
    }

    if(cookies != null){
        for(int loopIndex = 0; loopIndex < cookies.length; loopIndex++)           
        { 
            Cookie cookie1 = cookies[loopIndex];
            if (cookie1.getName().equals("zipCookie")) {
                zip = cookie1.getValue();
                foundCookie = true;
            }
        }  
    }

    if (!foundCookie) {
        if(request.getParameter("textField") != null){
            Cookie cookie1 = new Cookie("zipCookie", 
                request.getParameter("textField"));
            cookie1.setMaxAge(365 * 24 * 60 * 60);
            response.addCookie(cookie1); 
            drawImage(request.getParameter("textField"), response);
        }
        else{

%>
    <HTML>
        <HEAD>
            <META HTTP-EQUIV="Expires" CONTENT="-1">
            <TITLE>
                Forecaster
            </TITLE>
        </HEAD>

        <BODY>
            <H1>
                Forecaster
            </H1>
            <FORM NAME="form1" METHOD="POST">
                Please enter your five-digit zip code:
                <INPUT TYPE="TEXT" NAME="textField"></INPUT>
                <BR>
                <BR>
                <INPUT TYPE="SUBMIT" VALUE="Submit">
            </FORM>
        </BODY>
    </HTML>

<%
        }
    }
    else{
        drawImage(zip, response);
    }
%>

<%!
public void drawImage(String zip, HttpServletResponse response)
{
    String hiTemperature[] = new String[4];
    String loTemperature[] = new String[4];

    try {

        int character;

        URL url = new URL
            ("http://www.srh.noaa.gov/zipcity.php?inputstring=" 
            + zip);

        URLConnection urlconnection = url.openConnection();

        InputStream in = urlconnection.getInputStream();
        String input = "";
        String hiSearch;
        String loSearch;
        String inchar;
        char[] cc = new char[1];

        while ((character = in.read()) != -1) {
            char z = (char)character;
            cc[0] = z;
            inchar = new String(cc);
            input += inchar;
        }

        in.close();

        if(input.indexOf("Hi <font color=\"#FF0000\">") >= 0){
            hiSearch = "Hi <font color=\"#FF0000\">";
        }
        else{
            hiSearch= "Hi: <span class=\"red\">";
        }

        int currentPosition = 0;

        for(int loopIndex = 0; loopIndex < 4; loopIndex++){
            int location = input.indexOf(hiSearch, 
                currentPosition);
            int end = input.indexOf("&deg;", location);
            hiTemperature[loopIndex] = input.substring(location + 
                hiSearch.length(), end);
            currentPosition = end + 1;
        }

        if(input.indexOf("Lo <font color=\"#0033CC\">") >= 0){
            loSearch = "Lo <font color=\"#0033CC\">";
        }
        else{
            loSearch= "Lo: <span class=\"blue\">";
        }

        currentPosition = 0;

        for(int loopIndex = 0; loopIndex < 4; loopIndex++){
            int location = input.indexOf(loSearch, 
                currentPosition);
            int end = input.indexOf("&deg;", location);
            loTemperature[loopIndex] = input.substring(location + 
                loSearch.length(), end);
            currentPosition = end + 1;
        }

        boolean evening = false;

        if(input.indexOf(loSearch) < input.indexOf(hiSearch)){
            evening = true;
            hiTemperature[3] = hiTemperature[2];
            hiTemperature[2] = hiTemperature[1];
            hiTemperature[1] = hiTemperature[0];
        }

        BufferedImage image = new BufferedImage(225, 201, 
             BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        g.setColor(Color.white);
        g.fillRect(0, 0, 224, 201);

        g.setColor(Color.gray);

        for(int loopIndex = 0; loopIndex < 21; loopIndex++){
            g.drawLine(25, loopIndex * 10, 224, loopIndex * 10);
            g.drawLine(loopIndex * 10 + 25, 0, loopIndex * 10 
                + 25, 199);
        }

        g.setColor(Color.blue);
        Font font = new Font("Courier", Font.PLAIN, 18);
        g.setFont(font);

        for(int loopIndex = 20; loopIndex < 200; loopIndex += 20){
            g.drawString(String.valueOf(100 - loopIndex / 2), 0, 
                loopIndex + 5);
        }

        g.setColor(Color.red);

        if(!evening){
            g.drawOval(65 - 4, 200 - (Integer.parseInt(
                hiTemperature[0]) * 2) - 4, 8, 8);
        }
        g.drawOval(105 - 4, 200 - (Integer.parseInt(hiTemperature[1]) * 
            2) - 4, 8, 8);
        g.drawOval(145 - 4, 200 - (Integer.parseInt(hiTemperature[2]) * 
            2) - 4, 8, 8);
        g.drawOval(185 - 4, 200 - (Integer.parseInt(hiTemperature[3]) * 
            2) - 4, 8, 8);

        if(!evening){
            g.drawLine(65, 200 - (Integer.parseInt(
                hiTemperature[0]) * 2), 105, 200 - 
                (Integer.parseInt(hiTemperature[1]) * 2));
        }
        g.drawLine(105, 200 - (Integer.parseInt(hiTemperature[1]) * 2), 
            145, 200 - (Integer.parseInt(hiTemperature[2]) * 2));
        g.drawLine(145, 200 - (Integer.parseInt(hiTemperature[2]) * 2),
            185, 200 - (Integer.parseInt(hiTemperature[3]) * 2));

        g.setColor(Color.blue);

        g.drawOval(65 - 4, 200 - (Integer.parseInt(loTemperature[0]) * 
            2) - 4, 8, 8);
        g.drawOval(105 - 4, 200 - (Integer.parseInt(loTemperature[1]) * 
            2) - 4, 8, 8);
        g.drawOval(145 - 4, 200 - (Integer.parseInt(loTemperature[2]) * 
            2) - 4, 8, 8);
        g.drawOval(185 - 4, 200 - (Integer.parseInt(loTemperature[3]) * 
            2) - 4, 8, 8);

        g.drawLine(65, 200 - (Integer.parseInt(loTemperature[0]) * 2), 
            105, 200 - (Integer.parseInt(loTemperature[1]) * 2));
        g.drawLine(105, 200 - (Integer.parseInt(loTemperature[1]) * 2), 
            145, 200 - (Integer.parseInt(loTemperature[2]) * 2));
        g.drawLine(145, 200 - (Integer.parseInt(loTemperature[2]) * 2),
            185, 200 - (Integer.parseInt(loTemperature[3]) * 2));

        g.setColor(Color.white);
        g.fillRect(55, 160, 140, 30);
        g.setColor(Color.blue);
        g.drawRect(55, 160, 140, 30);

        font = new Font("Courier", Font.PLAIN, 12);
        g.setFont(font);
        g.drawString("Four-Day Forecast", 65, 172);

        font = new Font("Courier", Font.PLAIN, 9);
        g.setFont(font);
        g.drawString("Source: Nat. Weather Srvce.", 58, 185);

        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder
            (response.getOutputStream());
        encoder.encode(image);

    } 
    catch (Exception e) {
        System.out.println(e.getMessage());
    }
}
%>