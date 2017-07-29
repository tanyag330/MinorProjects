import java.io.*;
import java.awt.*; 
import java.net.*;
import java.awt.image.*;
import java.awt.event.*;

public class Forecast extends Frame
{
    OkCancelDialog textDialog;
    BufferedImage image = null;

    public static void main(String[] args)
    {
        new Forecast();
    }

    public Forecast()
    {
        String zip ="";
        File zipFile = new File("zip.txt");
        String hiTemperature[] = new String[4];
        String loTemperature[] = new String[4];

        try {

            if(zipFile.exists()){
                FileReader filereader = new FileReader("zip.txt");
                BufferedReader bufferedreader = new 
                    BufferedReader(filereader);
                zip = bufferedreader.readLine();
            }
            else
            {
                textDialog = new OkCancelDialog(this, 
                    "Enter your five-digit zip code", true);
                textDialog.setVisible(true);
                zip = textDialog.data.trim();
                FileOutputStream fileoutputstream = new 
                    FileOutputStream("zip.txt");
                fileoutputstream.write(zip.getBytes());
            }

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

            image = new BufferedImage(225, 201, 
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
            g.drawOval(105 - 4, 200 - (Integer.parseInt(
                hiTemperature[1]) * 2) - 4, 8, 8);
            g.drawOval(145 - 4, 200 - (Integer.parseInt(
                hiTemperature[2]) * 2) - 4, 8, 8);
            g.drawOval(185 - 4, 200 - (Integer.parseInt(
                hiTemperature[3]) * 2) - 4, 8, 8);

            if(!evening){
                g.drawLine(65, 200 - (Integer.parseInt(
                    hiTemperature[0]) * 2), 105, 200 - 
                    (Integer.parseInt(hiTemperature[1]) * 2));
            }
            g.drawLine(105, 200 - (Integer.parseInt(hiTemperature[1]) * 
                2), 145, 200 - (Integer.parseInt(hiTemperature[2]) * 
                2));
            g.drawLine(145, 200 - (Integer.parseInt(hiTemperature[2]) * 
                2), 185, 200 - (Integer.parseInt(hiTemperature[3]) * 
                2));

            g.setColor(Color.blue);

            g.drawOval(65 - 4, 200 - (Integer.parseInt(
                loTemperature[0]) * 2) - 4, 8, 8);
            g.drawOval(105 - 4, 200 - (Integer.parseInt(
                loTemperature[1]) * 2) - 4, 8, 8);
            g.drawOval(145 - 4, 200 - (Integer.parseInt(
                loTemperature[2]) * 2) - 4, 8, 8);
            g.drawOval(185 - 4, 200 - (Integer.parseInt(
                loTemperature[3]) * 2) - 4, 8, 8);

            g.drawLine(65, 200 - (Integer.parseInt(loTemperature[0]) * 
                2), 105, 200 - (Integer.parseInt(loTemperature[1]) * 
                2));
            g.drawLine(105, 200 - (Integer.parseInt(loTemperature[1]) * 
                2), 145, 200 - (Integer.parseInt(loTemperature[2]) * 
                2));
            g.drawLine(145, 200 - (Integer.parseInt(loTemperature[2]) * 
                2), 185, 200 - (Integer.parseInt(loTemperature[3]) * 
                2));

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

            setTitle("The Forecaster");

            setResizable(false);

            setSize(250, 240);

            setVisible(true);

            this.addWindowListener(new WindowAdapter(){
                public void windowClosing(
                    WindowEvent e){
                        System.exit(0);
                    }
                }
            );
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void paint(Graphics g) 
    {
        if(image != null){
            g.drawImage(image, 10, 30, this);
        }
    }
}

class OkCancelDialog extends Dialog implements ActionListener
{
    Button ok, cancel;
    TextField text;
    public String data;

    OkCancelDialog(Frame hostFrame, String title, boolean dModal)
    {
        super(hostFrame, title, dModal);
        setSize(280, 100);
        setLayout(new FlowLayout());
        text = new TextField(30);
        add(text);
        ok = new Button("OK");
        add(ok);
        ok.addActionListener((ActionListener)this);
        cancel = new Button("Cancel");
        add(cancel);
        cancel.addActionListener(this);
        data = new String("");
    }

    public void actionPerformed(ActionEvent event)
    {
        if(event.getSource() == ok){
            data = text.getText();
        } else {
            data = "";
        }
        setVisible(false);
    }
}