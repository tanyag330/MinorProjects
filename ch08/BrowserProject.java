import org.eclipse.swt.*;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.widgets.*;

public class BrowserProject 
{
    Display display;
    Shell shell;
    ToolBar toolbar;
    ToolItem go, forward, back, refresh, stop;
    Text text;
    Browser browser;

    public static void main(String[] args) 
    {
        new BrowserProject();
    }

    BrowserProject() 
    {
        display = new Display();
        shell = new Shell(display);
        shell.setText("The Browser Project");
        shell.setSize(600, 500);

        toolbar = new ToolBar(shell, SWT.NONE);
        toolbar.setBounds(5, 5, 200, 30);

        go = new ToolItem(toolbar, SWT.PUSH);
        go.setText("Go");
		
        forward = new ToolItem(toolbar, SWT.PUSH);
        forward.setText("Forward");

        back = new ToolItem(toolbar, SWT.PUSH);
        back.setText("Back");

        refresh = new ToolItem(toolbar, SWT.PUSH);
        refresh.setText("Refresh");

        stop = new ToolItem(toolbar, SWT.PUSH);
        stop.setText("Stop");

        text = new Text(shell, SWT.BORDER);
        text.setBounds(5, 35, 400, 25);

        browser = new Browser(shell, SWT.NONE);
        browser.setBounds(5, 75, 590, 400);
		
        Listener listener = new Listener() 
        {
            public void handleEvent(Event event) 
            {
                try{
                    ToolItem item = (ToolItem) event.widget;
                    String string = item.getText();

                    if (string.equals("Back")){
                        browser.back();
                    }

                    else if (string.equals("Forward")){
                        browser.forward();
                    }

                    else if (string.equals("Refresh")){
                        browser.refresh();
                    }

                    else if (string.equals("Stop")){
                        browser.stop();
                    }

                    else if (string.equals("Go")){
                        browser.setUrl(text.getText());
                    }
                }
                catch(Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        };

        go.addListener(SWT.Selection, listener);
        forward.addListener(SWT.Selection, listener);
        refresh.addListener(SWT.Selection, listener);
        back.addListener(SWT.Selection, listener);
        stop.addListener(SWT.Selection, listener);
		
        text.addListener(SWT.DefaultSelection, new Listener() 
            {
                public void handleEvent(Event e) {
                    browser.setUrl(text.getText());
                }
            }
        );
		
        shell.open();

        browser.setUrl("http://www.sun.com");

        while (!shell.isDisposed()) 
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
        }

        display.dispose();
    }
}