import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.lang.Math;

public class Slapshot extends Frame implements ActionListener, MouseListener, MouseMotionListener, Runnable 
{
    Menu menu1;
    MenuBar menubar;
    MenuItem menuitem0, menuitem1, menuitem2, menuitem3;
    Image memoryImage;
    Image backGroundImage;
    Image[] gifImages = new Image[2];
    Graphics memoryGraphics;
    Thread thread;
    MediaTracker tracker;
    Vector<Puck> pucks = new Vector<Puck>();
    int yourScore = 0;
    int theirScore = 0;
    int offsetX = 0;
    int offsetY = 0;
    int speed = 50;
    int maxVelocity = 10;
    Label label1, label2;
    int retVal = 0;
    boolean dragging = false;
    boolean stop = true;
    boolean runOK = true;
    OkCancelDialog textDialog;

    public static void main(String[] args)
    {
        new Slapshot();
    }

    Slapshot() 
    {
        menubar = new MenuBar();

        menu1 = new Menu("File");

        menuitem0 = new MenuItem("Start");
        menu1.add(menuitem0);
        menuitem0.addActionListener(this);

        menuitem1 = new MenuItem("End");
        menu1.add(menuitem1);
        menuitem1.addActionListener(this);

        menuitem2 = new MenuItem("Set speed...");
        menu1.add(menuitem2);
        menuitem2.addActionListener(this);

        menuitem3 = new MenuItem("Exit");
        menu1.add(menuitem3);
        menuitem3.addActionListener(this);

        menubar.add(menu1);
        setMenuBar(menubar); 

        addMouseListener(this);
        addMouseMotionListener(this);

        textDialog = new OkCancelDialog(this, 
            "Set speed (1-100)", true);

        setLayout(null);

        label1 = new Label();
        label1.setText("0");
        label1.setBounds(180, 310, 20, 20);
        label1.setVisible(false);
        add(label1);

        label2 = new Label();
        label2.setText("0");
        label2.setBounds(400, 310, 20, 20);
        label2.setVisible(false);
        add(label2);

        tracker = new MediaTracker(this);
        backGroundImage = Toolkit.getDefaultToolkit().
            getImage("rink.gif");
        tracker.addImage(backGroundImage, 0);
    
        gifImages[0] = Toolkit.getDefaultToolkit().
            getImage("puck.gif");
        tracker.addImage(gifImages[0], 0);

        gifImages[1] = Toolkit.getDefaultToolkit().
            getImage("blocker.gif");
        tracker.addImage(gifImages[1], 0);

        try {
            tracker.waitForID(0);
        }catch (InterruptedException e) {
            System.out.println(e);
        }
    
        setTitle("Slapshot!");

        setResizable(false);

        setSize(backGroundImage.getWidth(this), 
            backGroundImage.getHeight(this));

        setVisible(true);

        memoryImage = createImage(getSize().width, getSize
            ().height);
        memoryGraphics = memoryImage.getGraphics();

        thread = new Thread(this);
        thread.start();
  
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(
                WindowEvent e){
                    runOK = false;
                    System.exit(0);
                }
            }
        );
    }

    public void init()
    {
        Point position, velocity;
        pucks = new Vector<Puck>();

        Rectangle edges = new Rectangle(10 + getInsets().left,  
            getInsets().top, getSize().width - (getInsets().left
            + getInsets().right), getSize().height - (getInsets().top 
            + getInsets().bottom));

        for (int loopIndex = 0; loopIndex < 12; loopIndex++){
    
            pucks.add(new Puck(gifImages[0], 
                0, maxVelocity, edges, this));

            try {
                Thread.sleep(20);
            }
            catch (Exception exp) {
                System.out.println(exp.getMessage());
            }
        }

        pucks.add(new Puck(gifImages[1], 1, maxVelocity, edges, 
            this));

        pucks.add(new Puck(gifImages[1], 2, maxVelocity, edges, 
            this));
    }

    public void run() 
    {
        Puck puck;

        while (runOK) {
            if(!stop){

                int numberLeft;
                for (int loopIndex = 0; loopIndex < 12; loopIndex++){
                    puck = (Puck)pucks.elementAt(loopIndex);

                    if(puck.gone()){
                        continue;
                    }

                    retVal = puck.slide(pucks.elementAt
                        (13).rectangle, pucks.elementAt
                        (12).rectangle);

                    numberLeft = 0;
                    for (int loopIndex2 = 0; loopIndex2 < 12; 
                        loopIndex2++){
                        if(!((Puck)pucks.elementAt(loopIndex2))
                            .gone()){
                            numberLeft++;
                        }
                    }

                    if(retVal < 0){
                        if(yourScore + theirScore + numberLeft == 11){
                            label1.setText(String.valueOf
                            (++yourScore));
                        }
                    }

                    if(retVal > 0){
                        if(yourScore + theirScore + numberLeft == 11){
                            label2.setText(String.valueOf
                                (++theirScore));
                        }
                    }

                    int struckPuck = -1;

                    for (int loopIndex3 = 0; loopIndex3 < 13; 
                        loopIndex3++){
                        Puck testPuck = (Puck)pucks.elementAt
                            (loopIndex3);

                        if (puck == testPuck || testPuck.gone()){
                            continue;
                        }

                        if(puck.rectangle.intersects
                            (testPuck.rectangle)){
                            struckPuck = loopIndex3;
                        }
                    }

                    if (struckPuck >= 0){
                        Puck puck1 = (Puck)pucks.elementAt(struckPuck);
                        Puck puck2 = (Puck)pucks.elementAt(loopIndex);

                        if(puck2.immovable()){
                            puck1.velocity.x = -puck1.velocity.x;
      
                            retVal = puck1.slide(pucks.elementAt
                                (13).rectangle, pucks.elementAt
                                (12).rectangle);

                            numberLeft = 0;
                            for (int loopIndex4 = 0; loopIndex4 < 12; 
                                loopIndex4++){
                                if(!((Puck)pucks.elementAt
                                    (loopIndex4)).gone()){
                                    numberLeft++;
                                }
                            }

                            if(retVal < 0){
                                if(yourScore + theirScore + numberLeft 
                                    == 11){
                                    label1.setText(String.valueOf
                                        (++yourScore));
                                }
                            }

                            if(retVal > 0){
                                if(yourScore + theirScore + numberLeft 
                                    == 11){
                                    label2.setText(String.valueOf
                                        (++theirScore));
                                }
                            }

                        } else if(puck1.immovable()){
                            puck2.velocity.x = -puck2.velocity.x;

                            retVal = puck2.slide(pucks.elementAt(13).
                                rectangle, pucks.elementAt
                                (12).rectangle);

                            numberLeft = 0;
                            for (int loopIndex5 = 0; loopIndex5 < 12; 
                                loopIndex5++){
                                if(!((Puck)pucks.elementAt 
                                    (loopIndex5)).gone()){
                                    numberLeft++;
                                }
                            }

                            if(retVal < 0){
                                if(yourScore + theirScore + numberLeft 
                                    == 11){
                                    label1.setText(String.valueOf
                                        (++yourScore));
                                }
                            }

                            if(retVal > 0){
                                if(yourScore + theirScore + numberLeft 
                                    == 11){
                                    label2.setText(String.valueOf
                                        (++theirScore));
                                }
                            }
                        } 
                        else {
                            retVal = puck1.slide(pucks.elementAt
                               (13).rectangle, pucks.elementAt
                               (12).rectangle);

                            numberLeft = 0;

                            for (int loopIndex6 = 0; loopIndex6 < 12;  
                                loopIndex6++){
                                if(!((Puck)pucks.elementAt(loopIndex6))
                                    .gone()){
                                    numberLeft++;
                                }
                            }

                            if(retVal < 0){
                                if(yourScore + theirScore + numberLeft 
                                    == 11){
                                    label1.setText(String.valueOf
                                        (++yourScore));
                                }
                            }

                            if(retVal > 0){
                                if(yourScore + theirScore + numberLeft 
                                    == 11){
                                label2.setText(String.valueOf
                                    (++theirScore));
                                }
                            }

                            retVal = puck2.slide(pucks.elementAt
                                (13).rectangle, pucks.elementAt
                                (12).rectangle);

                            numberLeft = 0;
                            for (int loopIndex7 = 0; loopIndex7 < 12; 
                                loopIndex7++){
                                if(!((Puck)pucks.elementAt
                                    (loopIndex7)).gone()){
                                    numberLeft++;
                                }
                            }

                            if(retVal < 0){
                                if(yourScore + theirScore + numberLeft 
                                    == 11){
                                    label1.setText(String.valueOf
                                        (++yourScore));
                                }
                            }

                            if(retVal > 0){
                                if(yourScore + theirScore + numberLeft 
                                    == 11){
                                    label2.setText(String.valueOf
                                        (++theirScore));
                                }
                            }
                        }
                    }

                    int lowestTime = 10000;
                    int impactY = -1;

                    for (int loopIndex3 = 0; loopIndex3 < 12; 
                        loopIndex3++){
                        Puck movingPuck = (Puck)pucks.elementAt
                            (loopIndex3);
                        Rectangle r = movingPuck.rectangle;
                        Point mPosition = new Point(r.x, r.y);
                        Point mVelocity = movingPuck.velocity;

                        if(mVelocity.x > 0 && !movingPuck.gone()){
                            int yHit = (mVelocity.y / mVelocity.x) *
                            (backGroundImage.getWidth(this) - 
                                mPosition.x) + mPosition.y;

                            if(yHit > 115 && yHit < 223){
                                int time = (backGroundImage.getWidth
                                    (this) - mPosition.x) 
                                    / mVelocity.x;
                                if(time <= lowestTime){
                                    impactY = yHit;
                                }      
                            }
                        }

                        if(impactY > 0){
                            Puck block = pucks.elementAt(12);
                            int blockPosition = block.rectangle.y;

                            if(blockPosition < impactY){
                                block.slide(Math.min(blockPosition + 
                                    40, impactY));
                            } else {
                                block.slide(Math.max(blockPosition - 
                                    40, impactY));
                            }
                            repaint();
                        }
                        label2.setText(String.valueOf(theirScore));
                    }
                }
                repaint();
                try {
                    Thread.sleep(speed);
                }
                catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
  
    public void update(Graphics g) 
    {
        memoryGraphics.drawImage(backGroundImage, 0, 0, this);

        for (int loopIndex = 0; loopIndex < pucks.size(); loopIndex++){
            if(!stop){
                ((Puck)pucks.elementAt(loopIndex)).drawPuckImage
                    (memoryGraphics);
            } 
        }

        g.drawImage(memoryImage, 0, 0, this);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == menuitem0){
            if(!stop){
                stop = true;
                repaint();
            }
            init();
            label1.setVisible(true);
            label2.setVisible(true);
            stop = false;
            label1.setText("0");
            label2.setText("0");
            yourScore = 0;
            theirScore = 0;
        }

        if(e.getSource() == menuitem1){
            stop = true;
            label1.setText("0");
            label2.setText("0");
            yourScore = 0;
            theirScore = 0;
            repaint();
        }

        if(e.getSource() == menuitem2){
            textDialog.setVisible(true);
            if(!textDialog.data.equals("")){
                int newSpeed = Integer.parseInt(textDialog.data);
                newSpeed = 101 - newSpeed;
                if(newSpeed >= 1 && newSpeed <= 100){
                    speed = newSpeed;
                }
            }
        }

        if(e.getSource() == menuitem3){
            runOK = false;
            System.exit(0);
        }
    }

    public void mousePressed(MouseEvent e)
    {
        Rectangle r1 = pucks.elementAt(13).rectangle;
        if(r1.contains(new Point(e.getX(), e.getY()))){
              offsetX = e.getX() - r1.x;
              offsetY = e.getY() - r1.y;
              dragging = true;
        }
    }

    public void mouseReleased(MouseEvent e)
    {
            dragging = false; 
    }

    public void mouseDragged(MouseEvent e)
    {
        if(dragging){
            int newY = e.getY() - offsetY;

            pucks.elementAt(13).slide(newY);
            repaint();
        }
    }

    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseMoved(MouseEvent e){}
}

class Puck 
{
    Image image1;
    Component rink;
    Rectangle rectangle;
    Point velocity;
    Point location;
    Rectangle edges;
    Random random; 
    boolean outOfAction = false;
    boolean doNotMove = false;
    int maxVelocity;

    public Puck(Image image1, int type, int maxVelocity, 
        Rectangle edges, Component rink)
    {
        this.rink = rink;
        this.image1 = image1;
        if (type > 0){
            doNotMove = true;
        }
        this.maxVelocity = maxVelocity;
        this.edges = edges;

        random = new Random(System.currentTimeMillis());

        if(type == 0){
            location = new Point(100 + (Math.abs(random.nextInt()) 
                % 300), 100 + (Math.abs(100 + random.nextInt()) % 
                100));

            this.velocity = new Point(random.nextInt() % maxVelocity, 
                random.nextInt() % maxVelocity);

            while(velocity.x == 0){
                velocity.x = random.nextInt(maxVelocity / 2) 
                - maxVelocity / 2;
            }
        }

        if(type == 1){
            location = new Point(((Slapshot)rink).backGroundImage
                .getWidth(rink) - 18, 
                ((Slapshot)rink).backGroundImage.getHeight
                (rink)/2);
            this.velocity = new Point(0, 0);
        }

        if(type == 2){
            location = new Point(10, 
                ((Slapshot)rink).backGroundImage.getHeight(rink)/2);
            this.velocity = new Point(0, 0);
        }

        this.rectangle = new Rectangle(location.x, location.y,
            image1.getWidth(rink), image1.getHeight(rink));
    }

    public int slide(Rectangle blocker, Rectangle blocker2) 
    {
        Point position = new Point(rectangle.x, rectangle.y);
        int returnValue = 0;

        if (doNotMove){
            return returnValue;
        }

        if(random.nextInt(100) <= 1){
            velocity.x += random.nextInt() % maxVelocity; 

            velocity.x = Math.min(velocity.x, maxVelocity);
            velocity.x = Math.max(velocity.x, -maxVelocity);

            while(velocity.x == 0){
                velocity.x = random.nextInt(maxVelocity / 2) 
                - maxVelocity / 2;
            }

            velocity.y += random.nextInt() % maxVelocity / 2; 

            velocity.y = Math.min(velocity.y, maxVelocity / 2);
            velocity.y = Math.max(velocity.y, -(maxVelocity / 2));
        }
    
        position.x += velocity.x;
        position.y += velocity.y;

        if (position.x < edges.x + 5) {

            if(position.y > 120 && position.y < 225){
                if(!rectangle.intersects(blocker)){
                    returnValue = 1;
                    outOfAction = true;
                    return returnValue;
                }
            }
            position.x = edges.x;

            if(velocity.x > 0){
                velocity.x = -6;
            }else{
                velocity.x = 6;
            }
        }else if ((position.x + rectangle.width)
            > (edges.x + edges.width - 5)){
            if(position.y > 120 && position.y < 225){
                if(!rectangle.intersects(blocker2)){
                    returnValue = -1;
                    outOfAction = true;
                    return returnValue;
                }
            }
            position.x = edges.x + edges.width - rectangle.width;

            if(velocity.x > 0){
                velocity.x = -6;
            }else{
                velocity.x = 6;
            }
        }
    
        if (position.y < edges.y){
            position.y = edges.y;
            velocity.y = -velocity.y;

        }else if ((position.y + rectangle.height)
            > (edges.y + edges.height)){
            position.y = edges.y + edges.height - 
                rectangle.height;
            velocity.y = -velocity.y;
        }
    
        this.rectangle = new Rectangle(position.x, position.y,
            image1.getWidth(rink), image1.getHeight(rink));
        return returnValue;
    }
  
    public void slide(int y)
    {
        rectangle.y = y;
    }

    public boolean gone()
    {
        return outOfAction;
    }

    public boolean immovable()
    {
        return doNotMove;
    }

    public void drawPuckImage(Graphics g)
    {
        if(!outOfAction){
            g.drawImage(image1, rectangle.x, 
                rectangle.y, rink);
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
