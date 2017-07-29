import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class LabelProject 
{
      Display display;
      Shell shell;
      Label label;

	public static void main(String [] args) 
      {
          new LabelProject();
      }

      LabelProject()
      {
       display = new Display();
       shell = new Shell(display);
       shell.setSize(200, 200);
       label = new Label(shell, SWT.CENTER);

       label.setText("An SWT label, very cool.");

       label.setBounds(5, 20, 180, 40);

       shell.open();

       while(!shell.isDisposed()) {
          if(!display.readAndDispatch()) display.sleep();
       }
       display.dispose();
    }
}