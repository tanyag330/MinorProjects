import java.io.*;
import java.awt.*;
import java.net.*;
import java.awt.event.*;

class Intercom2 extends Frame implements Runnable, ActionListener
{
    private Thread thread;
    private Button button1, button2;
    private TextArea textarea1, textarea2;
    private TextField textfield1;
    private Label label1, label2, label3;
    Socket socket;

    InputStream in;
    OutputStream out;
    int character;
    char[] chars = new char[1];

    public static void main(String[] args)
    {
        new Intercom2();
    }

    public Intercom2() 
    {
        setLayout(null);

        label1 = new Label("Intercom 1 IP address:");
        label1.setBounds(20, 80, 125, 20);
        add(label1);

        textfield1 = new TextField("127.0.0.1");
        textfield1.setBounds(150, 80, 100, 20);
        add(textfield1);

        button1 = new Button("Connect");
        button1.setBounds(255, 80, 80, 20);
        add(button1);
        button1.addActionListener(this);

        button2 = new Button("Send");
        button2.setBounds(160, 390, 60, 20);
        add(button2);
        button2.addActionListener(this);

        textarea1 = new TextArea("", 7, 45, 
            TextArea.SCROLLBARS_VERTICAL_ONLY);
        textarea1.setBounds(20, 110, 340, 120);
        add(textarea1);

        label2 = new Label();
        label2.setBounds(20, 240, 100, 20);
        label2.setText("Type here:");
        add(label2);

        textarea2 = new TextArea("", 7, 45, 
            TextArea.SCROLLBARS_VERTICAL_ONLY);
        textarea2.setBounds(20, 260, 340, 120);
        add(textarea2);

        label3 = new Label("Intercom 2");
        label3.setFont(new Font("Times New Roman", Font.BOLD, 36));
        label3.setBounds(100, 35, 200, 30);
        add(label3);

        setSize(400, 430);

        setTitle("Intercom 2");
        setVisible(true);
        textarea2.requestFocus();

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(
                WindowEvent e){
                    System.exit(0);
                }
            }
        );
    }

    public void run() 
    {
        try{
            while ((character = in.read()) != -1) {
                chars[0] = (char)character;
                textarea1.append(new String(chars));
            }
        }
        catch(Exception ex)
            {textarea1.setText(ex.getMessage());}
    }

    public void actionPerformed(ActionEvent event)
    {
        if(event.getSource() == button1){
            try{
                socket = new Socket(textfield1.getText(), 8765);
                textfield1.setText("Connecting....");

                in = socket.getInputStream();
                out = socket.getOutputStream();

                thread = new Thread(this);
                thread.start();
            }
            catch (IOException ioe){
                textarea1.setText("Intercom 1 must be running and\n"
                + "accessible before running Intercom 2.");
                textfield1.setText("Not connected");
            }
            catch (Exception e){
                textarea1.setText(e.getMessage());
            }

            if(socket != null && socket.isConnected()){
                textfield1.setText("Connected");
            }
        }

        if(event.getSource() == button2){
            try{
                String str = textarea2.getText() + "\n";
                byte buffer[] = str.getBytes();
                out.write(buffer);
                textarea2.setText("");
                textarea2.requestFocus();
            }
            catch(Exception ex)
                {textarea1.setText(ex.getMessage());}
        }
    }
}
