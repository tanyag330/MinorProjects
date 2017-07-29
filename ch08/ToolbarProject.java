import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class ToolbarProject 
{
    Display display;
    Shell shell;
    ToolBar toolbar;
    ToolItem item1, item2, item3, item4;
    Text text;

    public static void main(String [] args) 
    {
        new ToolbarProject();
    }

    ToolbarProject()
    {
        display = new Display();
        shell = new Shell(display);
        shell.setText("SWT Toolbars");
        shell.setSize(240, 200);
            
        toolbar = new ToolBar(shell, SWT.NONE);
        item1 = new ToolItem(toolbar, SWT.PUSH);
        item1.setText("Item 1");
        item2 = new ToolItem(toolbar, SWT.PUSH);
        item2.setText("Item 2");
        item3 = new ToolItem(toolbar, SWT.PUSH);
        item3.setText("Item 3");
        item4 = new ToolItem(toolbar, SWT.PUSH);
        item4.setText("Item 4");
            
        toolbar.setBounds(0, 0, 200, 40);
            
        text = new Text(shell, SWT.BORDER);
        text.setBounds(0, 60, 200, 25);
            
        Listener listener = new Listener() {
            public void handleEvent(Event event) {
                ToolItem item =(ToolItem)event.widget;
                String string = item.getText();

                if(string.equals("Item 1")) 
                    text.setText("You clicked Item 1"); 
                else if(string.equals("Item 2")) 
                    text.setText("You clicked Item 2"); 
                else if(string.equals("Item 3")) 
                    text.setText("You clicked Item 3"); 
                else if(string.equals("Item 4")) 
                    text.setText("You clicked Item 4"); 
               }
            };
            
        item1.addListener(SWT.Selection, listener);
        item2.addListener(SWT.Selection, listener);
        item3.addListener(SWT.Selection, listener);
        item4.addListener(SWT.Selection, listener);

        shell.open();
        
        while (!shell.isDisposed()) {
             if (!display.readAndDispatch())
                 display.sleep();
        }
        display.dispose();
    }
}
