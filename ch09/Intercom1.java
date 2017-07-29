import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

public class Intercom1 extends Frame implements Runnable, ActionListener
{    
    private Thread thread;
    private Button button1;
    private TextArea textarea1, textarea2;
    private Label label1, label2;
    ServerSocket socket;
    PrintWriter out;

    Socket insocket;

    public static void main(String[] args)
    {
        new Intercom1();
    }

    public Intercom1() 
    {
        setLayout(null);

        button1 = new Button("Send");
        button1.setBounds(160, 360, 60, 20);
        add(button1);
        button1.addActionListener(this);

        textarea1 = new TextArea("", 7, 45, 
            TextArea.SCROLLBARS_VERTICAL_ONLY);
        textarea1.setBounds(20, 80, 340, 120);
        add(textarea1);

        label1 = new Label();
        label1.setBounds(20, 210, 100, 20);
        label1.setText("Type here:");
        add(label1);

        textarea2 = new TextArea("", 7, 45, 
            TextArea.SCROLLBARS_VERTICAL_ONLY);
        textarea2.setBounds(20, 230, 340, 120);
        add(textarea2);

        label2 = new Label("Intercom 1");
        label2.setFont(new Font("Times New Roman", Font.BOLD, 36));
        label2.setBounds(100, 35, 200, 30);
        add(label2);

        setSize(400, 400);

        setTitle("Intercom 1");

        setVisible(true);
        textarea2.requestFocus();

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(
                WindowEvent e){
                    System.exit(0);
                    try{
                        socket.close();
                    }catch(Exception ex){}
                }
            }
        );

        try {    
            socket = new ServerSocket(8765);

            insocket = socket.accept( );

            out = new PrintWriter (insocket.getOutputStream(), true);

            thread = new Thread(this);
            thread.start();

        }
        catch (Exception e) 
        {
            textarea1.setText(e.getMessage());
        } 
     } 

    public void run() 
    {
        String instring;
        try {    
       
            BufferedReader in = new BufferedReader (new 
                InputStreamReader(insocket.getInputStream()));
            while((instring = in.readLine()) != null){
                textarea1.append(instring + "\n");
            }
        }catch (Exception e) 
        {
            textarea1.setText(e.getMessage());
        } 

    }

    public void actionPerformed(ActionEvent event)
    {
        if(event.getSource() == button1){
            String text = textarea2.getText();
            textarea2.setText("");
            out.println(text);
            textarea2.requestFocus();
        }
    }
}


