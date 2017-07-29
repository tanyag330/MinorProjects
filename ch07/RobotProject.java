import java.io.*; 
import java.awt.*; 
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;
import java.awt.event.*; 

class RobotProject extends JFrame implements ActionListener
{ 
    JButton jButton = new JButton("Go"); 
    JTextArea jTextArea = new JTextArea(""); 
    JTextArea helpInfo = new JTextArea(""); 
    JTextField jFileName = new JTextField(""); 
    JLabel jLabel = new JLabel("The Robot"); 
    JLabel prompt = new JLabel("Commands:"); 
    JLabel usage = new JLabel("Usage:"); 
    JLabel jFileNameLabel = new JLabel("Command file:"); 
    String commands[] = new String[1024];
    int numberCommands;

    public static void main(String args[]) 
    { 
        new RobotProject(); 
    }
 
    RobotProject()
    {
        jButton.addActionListener(this); 
        getContentPane().setLayout(null); 
        jTextArea.setEditable(true);
        jTextArea.setFont(new Font("Times Roman", Font.BOLD, 10));
        getContentPane().add(jButton); 
        getContentPane().add(jTextArea); 
        getContentPane().add(jLabel); 
        getContentPane().add(jFileName); 
        getContentPane().add(prompt); 
        getContentPane().add(helpInfo); 
        getContentPane().add(usage); 
        getContentPane().add(jFileNameLabel); 
        jLabel.setBounds(30, 0, 120, 60);
        jButton.setBounds(100, 450, 80, 40);
        jLabel.setFont(new Font("Times Roman", Font.BOLD, 24));
        prompt.setBounds(10, 50, 80, 20);
        usage.setBounds(100, 50, 80, 20);
        jTextArea.setBounds(10, 70, 80, 420);
        jFileName.setBounds(100, 425, 80, 20);
        jFileNameLabel.setBounds(95, 405, 90, 20);

        helpInfo.setBounds(100, 70, 80, 335);
        helpInfo.setEditable(false);
        helpInfo.setText("Type text:\n    t:abc\n    t:ALTDN"
            +  "\n    t:ALTUP"
            +  "\n    t:CTRLDN"
            +  "\n    t:CTRLUP"
            +  "\n    t:TAB"
            + "\n    t:ENTER"
            + "\n    t:ESCAPE"
            + "\nMove mouse:\n    m:x,y\n"
            + "Left Click:\n    c:\n"
            + "Right Click:\n    r:\n"
            + "Wait n sec's:\n    w:n\n"
            + "Cap screen:\n    s:\n"
            + "Beep:\n    b:");

        setTitle("Robot");
        setSize(200,520); 
        setVisible(true); 

        this.addWindowListener( 
            new WindowAdapter(){ 
            public void windowClosing(WindowEvent e)
                { 
                    System.exit(0); 
                }
            }
        );
    }
 
    public void actionPerformed(ActionEvent e) 
    { 
        try{

            setVisible(false); 

            Robot robot = new Robot();
            robot.delay(500);

            if (!jFileName.getText().equals("")){
                BufferedReader bufferedFile = new BufferedReader(
                    new FileReader(jFileName.getText())); 

                int commandIndex = 0;
                String inline = "";

                while((inline = bufferedFile.readLine()) != null){
                    commands[commandIndex++] = inline;
                }

                numberCommands = commandIndex;

            } 
            else {
                 commands = jTextArea.getText().split("\n");
                 numberCommands = commands.length;
            }


            for (int loopIndex = 0; loopIndex < numberCommands; 
                loopIndex++){

                String operation = commands[loopIndex].substring(0, 1);
                String data = commands[loopIndex].substring(2);

                switch(operation.toCharArray()[0])
                {
                    case 't':
                        if(data.equals("ALTDN")){
                            robot.keyPress(KeyEvent.VK_ALT);
                        }

                        else if(data.equals("ALTUP")){
                            robot.keyRelease(KeyEvent.VK_ALT);
                        }

                        if(data.equals("CTRLDN")){
                            robot.keyPress(KeyEvent.VK_CONTROL);
                        }

                        else if(data.equals("CTRLUP")){
                            robot.keyRelease(KeyEvent.VK_CONTROL);
                        }

                        else if(data.equals("ENTER")){
                                robot.keyPress(KeyEvent.VK_ENTER);
                                robot.keyRelease(KeyEvent.VK_ENTER);
                        }

                        else if(data.equals("TAB")){
                                robot.keyPress(KeyEvent.VK_TAB);
                                robot.keyRelease(KeyEvent.VK_TAB);
                        }

                        else if(data.equals("ESCAPE")){
                                robot.keyPress(KeyEvent.VK_ESCAPE);
                                robot.keyRelease(KeyEvent.VK_ESCAPE);
                        }

                        else{
                            char chars[] = data.toCharArray();
                            for(int charIndex = 0; 
                                charIndex < chars.length;
                                charIndex++){

                                if(chars[charIndex] >= 'a' && 
                                    chars[charIndex] <= 'z'){
                                    robot.keyPress((int) 
                                        chars[charIndex] 
                                        - ('a' -'A'));
                                    robot.keyRelease((int) 
                                        chars[charIndex] 
                                        - ('a' -'A'));
                                }

                                else if(chars[charIndex] >= 'A' && 
                                    chars[charIndex] <= 'Z'){
                                    robot.keyPress(KeyEvent.VK_SHIFT);
                                    robot.keyPress((int) 
                                        chars[charIndex]);
                                    robot.keyRelease((int) chars
                                        [charIndex]);
                                    robot.keyRelease
                                        (KeyEvent.VK_SHIFT);
                                }

                                else{
                                robot.keyPress((int) 
                                    chars[charIndex]);
                                robot.keyRelease((int) 
                                    chars[charIndex]);
                                robot.delay(100);
                                }
                            }
                        }            

                        break;

                    case 'm':
                        String coords[] = data.split(",");
                        int x = Integer.parseInt(coords[0]);
                        int y = Integer.parseInt(coords[1]);
                        robot.mouseMove(x, y);
                        robot.delay(500);

                        break;

                    case 'c':
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        robot.delay(500);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);

                        break;

                    case 'r':
                        robot.mousePress(InputEvent.BUTTON3_MASK);
                        robot.delay(500);
                        robot.mouseRelease(InputEvent.BUTTON3_MASK);

                        break;

                    case 'w':
                        int numberSeconds = Integer.parseInt(data);

                        robot.delay(numberSeconds * 1000);

                        break;

                    case 'b':
                        Toolkit.getDefaultToolkit().beep();

                        break;

                    case 's':
                        Dimension screenSize = 
                            Toolkit.getDefaultToolkit().
                            getScreenSize();

                        BufferedImage bufferedImage = 
                            robot.createScreenCapture(new 
                                Rectangle(screenSize));

                        File outputFile = new File("cap.png"); 

                        ImageIO.write(bufferedImage, "PNG",
                            outputFile); 

                        break;
    
                    default:
                        System.out.println(
                            "I didn't understand that command.");
                }
            }
        } 

        catch (Exception ex){System.out.println("Error: " + 
            ex.getMessage());}

        setVisible(true); 
    }
}