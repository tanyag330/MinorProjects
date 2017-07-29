import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;

public class ButtonProject 
{
    Display display;
    Shell shell;
    Button button;
    Text text;

    public static void main(String [] args) 
    {
        new ButtonProject();
    }

    ButtonProject()
    {
       display = new Display();
       shell = new Shell(display);
       shell.setSize(240, 150);
       shell.setText("SWT Buttons");

       button = new Button(shell, SWT.PUSH);
       button.setText("Click here");
       button.setBounds(10, 35, 60, 30);

       text = new Text(shell, SWT.SHADOW_IN);
       text.setBounds(80, 40, 120, 20);
       
       button.addSelectionListener(new SelectionListener()
       {
          public void widgetSelected(SelectionEvent event)
          {
            text.setText("Thanks for clicking.");
          }

          public void widgetDefaultSelected(SelectionEvent event)
          {
            text.setText("Thanks for clicking.");
          }
       });

       shell.open();
       while(!shell.isDisposed()) {
          if(!display.readAndDispatch()) display.sleep();
       }
       display.dispose();
    }
}