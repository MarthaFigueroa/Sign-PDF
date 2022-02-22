import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JToolBar;

import com.gnostice.pdfone.MouseActivityEvent;
import com.gnostice.pdfone.MouseActivityHandler;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPage;
import com.gnostice.pdfone.PdfPasswordHandler;
import com.gnostice.pdfone.PdfPrinter;
import com.gnostice.pdfone.PdfRect;
import com.gnostice.pdfone.PdfViewer;
import com.gnostice.pdfone.PdfViewerPageHandler;

public final class SimplePdfViewerDemowithMouseActivity extends JFrame implements
    ActionListener, PdfPasswordHandler, MouseActivityHandler, PdfViewerPageHandler, WindowListener
{
    private static final long serialVersionUID = 1L;

    String docPath;

    PdfDocument d;

    PdfViewer viewer;
    
    PdfPrinter pdfPrinter;

    // Controls for toolbar
    JFileChooser fc;

    JButton btnLoad;

    JButton btnPrint;

    JButton btnClose;

    JButton btnFirstPage = null;

    JButton btnPreviousPage = null;

    JButton btnNextPage = null;

    JButton btnLastPage = null;
    
    JButton btnSaveDocument = null;

    JLabel lblCurrentPageNum = null;

    HashMap pageNumAndRects = new HashMap();
    
	//Color to Highlight the Selection on the viewer
    Color selectionColor;
    
    public static void main(String[] args)
    {
        SimplePdfViewerDemowithMouseActivity vd = new SimplePdfViewerDemowithMouseActivity();

        vd.setSize(1024, 740);
        vd.setTitle("Gnostice PDFOne Viewer");
        vd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vd.setVisible(true);
    }

    public SimplePdfViewerDemowithMouseActivity()
    {
        // File open dialog
        fc = new JFileChooser();
        fc.setCurrentDirectory(new File(System
            .getProperty("user.home")
            + File.separatorChar + "Desktop"));
        
        // Create a PdfDocument object
        d = new PdfDocument();
        d.setOnPasswordHandler(this);

        // Create a new viewer container
        viewer = new PdfViewer();
        
        // Set the document object to the viewer
        viewer.setDocument(d);
        
        viewer.setMouseActivityHandler(this);
        
        viewer.setViewerPageHandler(this);
        
        addWindowListener(this);
        
        try
        {
            viewer.setMouseInteractivityMode(PdfViewer.MOUSE_INTERACTIVITY_MODE_MARQUEE_RECT);
        }
        catch (PdfException e)
        {
            e.printStackTrace();
        }
        
        selectionColor = new Color(Color.yellow.getRed(), Color.yellow.getGreen(), Color.yellow.getBlue(), 123);
        
        // Create a new PdfPrinter object
        pdfPrinter = new PdfPrinter();
        
        // Set the document object to the viewer
        pdfPrinter.setDocument(d);

        // Prepare window
        getContentPane().setLayout(new BorderLayout());
        // Add viewer to Frame
        getContentPane().add(viewer, BorderLayout.CENTER);
        // Add toolbar at the top of the frame
        getContentPane().add(getTopToolBar(), BorderLayout.NORTH);
    }

    private JToolBar getTopToolBar()
    {
        // Create a toolbar
        JToolBar topToolbar = new JToolBar("Tools",
            JToolBar.HORIZONTAL);
        topToolbar.setFloatable(false);
        topToolbar.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Create a button on the toolbar
        btnLoad = new JButton("Open");
        btnLoad.setToolTipText("Open");
        // Ensure that actionPerformed method of this
        // class receives events from the button
        btnLoad.addActionListener(this);

        btnPrint = new JButton("Print");
        btnPrint.setToolTipText("Print");
        btnPrint.addActionListener(this);

        btnFirstPage = new JButton("<<");
        btnFirstPage.setToolTipText("First Page");
        btnFirstPage.addActionListener(this);

        btnPreviousPage = new JButton("<");
        btnPreviousPage.setToolTipText("Previous Page");
        btnPreviousPage.addActionListener(this);

        btnNextPage = new JButton(">");
        btnNextPage.setToolTipText("Next Page");
        btnNextPage.addActionListener(this);

        btnLastPage = new JButton(">>");
        btnLastPage.setToolTipText("Last Page");
        btnLastPage.addActionListener(this);
        
        btnSaveDocument = new JButton("Save");
        btnSaveDocument.setToolTipText("Save Document");
        btnSaveDocument.addActionListener(this);

        btnClose = new JButton("Close");
        btnClose.setToolTipText("Close");
        btnClose.addActionListener(this);

        lblCurrentPageNum = new JLabel();

        // Add items to toolbar
        topToolbar.add(btnLoad);
        topToolbar.add(btnPrint);
        topToolbar.add(btnClose);
        topToolbar.add(btnFirstPage);
        topToolbar.add(btnPreviousPage);
        topToolbar.add(btnNextPage);
        topToolbar.add(btnLastPage);
        topToolbar.add(btnSaveDocument);
        topToolbar.add(lblCurrentPageNum);

        return topToolbar;
    }

    // This method processes events sent by
    // buttons on the toolbar.
    // This method is from the ActionListener
    // interface implemented by this class
    public void actionPerformed(ActionEvent ae)
    {
        Object sourceButton = ae.getSource();

        try
        {
            if (ae.getSource() == btnLoad)
            {
                loadFile();
            }
            else if (ae.getSource() == btnPrint)
            {
                printFile();
            }
            else if (sourceButton == btnFirstPage)
            {
                viewFirstPage();
            }
            else if (sourceButton == btnPreviousPage)
            {
                viewPreviousPage();
            }
            else if (sourceButton == btnNextPage)
            {
                viewNextPage();
            }
            else if (sourceButton == btnLastPage)
            {
                viewLastPage();
            }
            else if (sourceButton == btnClose)
            {
                closeFile();
            }
            else if (sourceButton == btnSaveDocument)
            {
                saveFile();
            }
        }
        catch (PdfException pdfEx)
        {
            JOptionPane.showMessageDialog(this.getParent(), pdfEx
                .getMessage(), "Gnostice PDF Viewer",
                JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException ioEx)
        {
            JOptionPane.showMessageDialog(this.getParent(), ioEx
                .getMessage(), "Gnostice PDF Viewer",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // Display the first page of the document
    private void viewFirstPage() throws IOException, PdfException
    {
        viewer.firstPage();
        updatePageNumberLabel(viewer.getCurrentPage());
    }

    // Display the previous page of the document
    private void viewPreviousPage() throws IOException, PdfException
    {
        viewer.previousPage();
        updatePageNumberLabel(viewer.getCurrentPage());
    }

    // Display the next page of the document
    private void viewNextPage() throws IOException, PdfException
    {
        viewer.nextPage();
        updatePageNumberLabel(viewer.getCurrentPage());
    }

    // Display the last page of the document
    private void viewLastPage() throws IOException, PdfException
    {
        viewer.lastPage();
        updatePageNumberLabel(viewer.getCurrentPage());
    }

    // Update label with number of current page
    private void updatePageNumberLabel(int pageNum)
    {
        if (pageNum == 0)
        {
            lblCurrentPageNum.setText("");
        }
        else
        {
            lblCurrentPageNum.setText("Showing Page "
                + String.valueOf(pageNum) + " of "
                + String.valueOf(viewer.getPageCount()));
        }
    }

    // Use a prompt to obtain document password from the user
    public String onPassword(PdfDocument d, boolean[] flags)
    {
        JPanel panel = new JPanel(new FlowLayout());

        JPasswordField field = new JPasswordField(10);
        panel.add(new JLabel("Password: "));
        panel.add(field);

        field.requestFocus();

        JOptionPane.showMessageDialog(this, panel,
            "Gnostice PDF Viewer", JOptionPane.OK_OPTION
                | JOptionPane.QUESTION_MESSAGE);

        String pwd = "";

        char[] pin = field.getPassword();
        try
        {
            pwd = new String(pin);
        }
        finally
        {
            Arrays.fill(pin, ' ');
            field.setText("");
        }

        return pwd;
    }

    // Display a PDF document in the viewer
    protected void loadFile()
    {
        int fcState = fc.showOpenDialog(this);

        // Exit if a file has not been selected succesfully
        if (fcState != JFileChooser.APPROVE_OPTION)
        {
            return;
        }

        File selectedFile = fc.getSelectedFile();

        // Exit if it is not a valid file
        if ( !(selectedFile.exists() && selectedFile.isFile()))
        {
            JOptionPane.showMessageDialog(this, "The File \""
                + selectedFile.getAbsoluteFile()
                + "\" does not exist", "Gnostice PDF Viewer",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Remove the display of any other document
        closeFile();

        docPath = selectedFile.getAbsolutePath();
        setTitle(selectedFile.getName() + " - Gnostice PDF Viewer");

        try
        {
            // Load the PDF in the document object 
            d.load(docPath);
            
            // calling refresh() method on the viewer is optionally as
            // it will be refreshed automatically the document is
            // associated with the viewer using setDocument() method
//            viewer.refresh();
            
            // Update label with number of current page
            updatePageNumberLabel(viewer.getCurrentPage());
        }
        catch (PdfException pdfEx)
        {
            JOptionPane.showMessageDialog(this, pdfEx.getMessage(),
                "Gnostice PDF Viewer", JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException ioEx)
        {
            JOptionPane.showMessageDialog(this, ioEx.getMessage(),
                "Gnostice PDF Viewer", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Print the displayed document
    protected void printFile() throws IOException, PdfException
    {
        if (d != null && d.isLoaded())
        {
            if (pdfPrinter != null)
            {
                // Obtain print preference from user and
                // print document accordingly
                pdfPrinter.showPrintDialog(this, true);
            }
            else
            {
                JOptionPane.showMessageDialog(this,
                    "Could not create PdfPrinter object",
                    "PdfPrinter object creation failed",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this,
                "Document not loaded, Load a PDF Document to Print",
                "Gnostice PDF Viewer", JOptionPane.ERROR_MESSAGE);
            loadFile();
        }
    }

    // Remove the display of any document in viewer and
    // dispose close any I/O streams associated with
    // the document reader
    protected void closeFile()
    {
        pageNumAndRects.clear();
        
        lblCurrentPageNum.setText("");
        setTitle("Gnostice PDF Viewer");

        if (d != null && d.isLoaded())
        {
            try
            {
                // this will automatically dispose the objects
                // associated with the viewer and printer object
                d.close();
            }
            catch (Exception ioEx)
            {
                JOptionPane.showMessageDialog(this,
                    ioEx.getMessage(), "Gnostice PDF Viewer",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    protected void saveFile()
    {
        if(pageNumAndRects.size() > 0 && d != null)
        {
            Iterator it = pageNumAndRects.entrySet().iterator();
            while (it.hasNext()) 
            {
                Map.Entry pair = (Map.Entry)it.next();
                System.out.println(pair.getKey() + " = " + pair.getValue());
                List pageRects = (List) pair.getValue();
                for(int i = 0; i < pageRects.size(); i++)
                {
                    Rectangle r = (Rectangle) pageRects.get(i);
                    int pageNumber = (Integer) pair.getKey();
                    
                    try
                    {
                        PdfRect pdfRect = new PdfRect(r);
                        d.setBrushColor(selectionColor);
                        d.drawRect(pdfRect.getX(), pdfRect.getY(), pdfRect.width(), pdfRect.height(), true, false, String.valueOf(pageNumber));
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    catch (PdfException e)
                    {
                        e.printStackTrace();
                    }
                }
                it.remove(); // avoids a ConcurrentModificationException
            }

            try
            {
                File file = new File("Output_SimpleViewer_MouseActivity_Demo.pdf");
                d.save(file);
                
                JOptionPane.showMessageDialog(this, "Current Document is saved to "
                    + file.getAbsolutePath());
                
                closeFile();
                
                try
                {
                    // Load the PDF in the document object 
                    d.load(file.getAbsolutePath());
                    
                    // Update label with number of current page
                    updatePageNumberLabel(viewer.getCurrentPage());
                }
                catch (PdfException pdfEx)
                {
                    JOptionPane.showMessageDialog(this, pdfEx.getMessage(),
                        "Gnostice PDF Viewer", JOptionPane.ERROR_MESSAGE);
                }
                catch (IOException ioEx)
                {
                    JOptionPane.showMessageDialog(this, ioEx.getMessage(),
                        "Gnostice PDF Viewer", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (PdfException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void onMouseActivity(MouseActivityEvent mouseActivityEvent)
    { 
        int pageNumber = mouseActivityEvent.getPageNumber();
		
		//Based on the Type of the mouse-activity occurred SelectionWidth and SelectionHeight will vary in mouseActivityEvent
        if (mouseActivityEvent.getMouseActivityType() == MouseActivityEvent.MOUSEACTIVITY_TYPE_BUTTONUP)
        {
            System.out.println("ButtonUP at  " + mouseActivityEvent.getX() +" , "+mouseActivityEvent.getY()); 
        }
        else if (mouseActivityEvent.getMouseActivityType() == MouseActivityEvent.MOUSEACTIVITY_TYPE_BUTTONDOWN)
        {
            System.out.println("ButtonDown at  " + mouseActivityEvent.getX() +" , "+mouseActivityEvent.getY());
        }
        else if (mouseActivityEvent.getMouseActivityType() == MouseActivityEvent.MOUSEACTIVITY_TYPE_MOVED)
        {
            //System.out.println("Moved at  " + mouseActivityEvent.getX() +" , "+mouseActivityEvent.getY());
        }
        else if (mouseActivityEvent.getMouseActivityType() == MouseActivityEvent.MOUSEACTIVITY_TYPE_DRAGGING)
        {
            System.out.println("Dragging at  " + mouseActivityEvent.getX() +" , "+mouseActivityEvent.getY());
        }
        else if (mouseActivityEvent.getMouseActivityType() == MouseActivityEvent.MOUSEACTIVITY_TYPE_DRAGGED)
        {
            System.out.println("Dragged at  " + mouseActivityEvent.getX() +" , "+mouseActivityEvent.getY()+" , "+mouseActivityEvent.getSelectionWidth()+" , "+mouseActivityEvent.getSelectionHeight());
        }
        else if (mouseActivityEvent.getMouseActivityType() == MouseActivityEvent.MOUSEACTIVITY_TYPE_SELECTING)
        {
            System.out.println("Selecting at  " + mouseActivityEvent.getX() +" , "+mouseActivityEvent.getY());
        }
        else if (mouseActivityEvent.getMouseActivityType() == MouseActivityEvent.MOUSEACTIVITY_TYPE_SELECTED)
        {
            System.out.println("Selected at  " + mouseActivityEvent.getX() +" , "+mouseActivityEvent.getY()+" , "+mouseActivityEvent.getSelectionWidth()+" , "+mouseActivityEvent.getSelectionHeight());

            Rectangle r = new Rectangle(mouseActivityEvent.getX(), mouseActivityEvent.getY(), mouseActivityEvent.getSelectionWidth(), mouseActivityEvent.getSelectionHeight()); 
            
           //Adding the Selected rects to a Hash Map which is used to add images later while closing the file 
           if(pageNumAndRects.containsKey(pageNumber))
           {
               ((List) pageNumAndRects.get(pageNumber)).add(r);
           }
           else
           {
               ArrayList pageRects = new ArrayList();
               pageRects.add(r);
               pageNumAndRects.put(pageNumber, pageRects);
           }
        }
    }
    
    @Override
    public void afterRenderingOnGraphics(Graphics g, int pageNum,
        PdfPage pdfPage, PdfDocument document)
    {
            if(pageNumAndRects.containsKey(pageNum))
            {
                List pageRects = (List) pageNumAndRects.get(pageNum);
                for (Iterator iterator = pageRects.iterator(); iterator
                    .hasNext();)
                {
                    Rectangle imgRect = (Rectangle) iterator.next();
                    g.setColor(selectionColor);
                    g.fillRect(imgRect.x, imgRect.y, imgRect.width, imgRect.height);
                }
            }
    }

    @Override
    public void beforeRenderingOnGraphics(Graphics g, int pageNum,
        PdfPage pdfPage, PdfDocument document)
    {
        
    }

    @Override
    public void windowActivated(WindowEvent e)
    {
        
    }

    @Override
    public void windowClosed(WindowEvent e)
    {
        
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        closeFile();
    }

    @Override
    public void windowDeactivated(WindowEvent e)
    {
        
    }

    @Override
    public void windowDeiconified(WindowEvent e)
    {
        
    }

    @Override
    public void windowIconified(WindowEvent e)
    {
        
    }

    @Override
    public void windowOpened(WindowEvent e)
    {
        
    }
}