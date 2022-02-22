import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JToolBar;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPasswordHandler;
import com.gnostice.pdfone.PdfPrinter;
import com.gnostice.pdfone.PdfViewer;
import com.gnostice.pdfone.PdfViewerChangeHandler;

/*
 * This class implements PdfViewerChangeHandler to listen to the
 * changes of PdfViewer by overriding the method onPageChange
 */
public final class PdfViewerChangeHandlerDemo extends JFrame
    implements ActionListener, PdfPasswordHandler,
    PdfViewerChangeHandler
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
    
    JButton btnZoomOut = null;

    JButton btnZoomIn = null;

    JLabel lblCurrentPageNum = null;
    
    JLabel lblCurrentZoomVal = null;

    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    public static void main(String[] args)
    {
        PdfViewerChangeHandlerDemo vd = new PdfViewerChangeHandlerDemo();

        vd.setSize(1024, 740);
        vd.setTitle("PDFOne Viewer");
        vd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vd.setVisible(true);
    }

    public PdfViewerChangeHandlerDemo()
    {
        // File open dialog
        fc = new JFileChooser();
        fc.setCurrentDirectory(new File(System
            .getProperty("user.home")
            + File.separatorChar + "Desktop"));

        // Create the document object to load the PDF documents
        d = new PdfDocument();
        
        // Set this class as the password handler so that password for
        // the encrypted file will be taken from the method onPassword
        // of ths class
        d.setOnPasswordHandler(this);
        
        // Create a new viewer container
        viewer = new PdfViewer();
        viewer.setDocument(d);
        viewer.setViewerChangeHandler(this);
        
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

        btnClose = new JButton("Close");
        btnClose.setToolTipText("Close");
        btnClose.addActionListener(this);
        
        btnZoomOut = new JButton("ZoomOut");
        btnZoomOut.setToolTipText("Zoom Out");
        btnZoomOut.addActionListener(this);
        
        btnZoomIn = new JButton("ZoomIn");
        btnZoomIn.setToolTipText("Zoom In");
        btnZoomIn.addActionListener(this);

        lblCurrentPageNum = new JLabel();
        
        lblCurrentZoomVal = new JLabel();

        // Add items to toolbar
        topToolbar.add(btnLoad);
        topToolbar.add(btnPrint);
        topToolbar.add(btnClose);
        topToolbar.add(btnFirstPage);
        topToolbar.add(btnPreviousPage);
        topToolbar.add(btnNextPage);
        topToolbar.add(btnLastPage);
        topToolbar.add(lblCurrentPageNum);
        topToolbar.add(btnZoomOut);
        topToolbar.add(lblCurrentZoomVal);
        topToolbar.add(btnZoomIn);
        
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
            else if (sourceButton == btnZoomOut)
            {
                viewer.zoomOut();
            }
            else if (sourceButton == btnZoomIn)
            {
                viewer.zoomIn();
            }
            else if (sourceButton == btnClose)
            {
                closeFile();
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
    }

    // Display the previous page of the document
    private void viewPreviousPage() throws IOException, PdfException
    {
        viewer.previousPage();
    }

    // Display the next page of the document
    private void viewNextPage() throws IOException, PdfException
    {
        viewer.nextPage();
    }

    // Display the last page of the document
    private void viewLastPage() throws IOException, PdfException
    {
        viewer.lastPage();
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
            // Load a document
            d.load(docPath);
            
            // calling refresh() method on the viewer is optionally as
            // it will be refreshed automatically the document is
            // associated with the viewer using setDocument() method
//            viewer.refresh();
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
        lblCurrentPageNum.setText("");
        setTitle("Gnostice PDF Viewer");

        if (d != null)
        {
            try
            {
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

    public void onPageChange(PdfViewer viewer, int changedPageNum)
    {
    }

    public void onZoomChange(PdfViewer viewer,
        double changedZoomFacter)
    {
    }

    public void onPageViewChange(PdfViewer viewer, int changedPageView)
    {
    }

    public void onRotationChange(PdfViewer viewer, int rotationAngle)
    {
    }

    public void onDpiChange(PdfViewer viewer, int dpi)
    {
    }

    public void onChange(PdfViewer viewer, int pageNum,
        double zoomFacter, int rotationAngle, int pageView)
    {
    }

    public void onPageLayoutChange(PdfViewer viewer, int changedPageLayout)
    {
        // TODO Auto-generated method stub
        
    }

    public void onViewHistoryChange(PdfViewer viewer,
        boolean hasPreviousView, boolean hasNextView)
    {
        // TODO Auto-generated method stub

    }

    public void onChange(PdfViewer viewer, int pageNum,
        double zoomFactor, int rotationAngle, int pageView,
        int pageLayout, int pageColumnsCustomCount,
        boolean showGapsBetweenPages,
        boolean showCoverPageDuringSideBySide,
        boolean showPageBordersWhenNoPageGaps)
    {
        // TODO Auto-generated method stub
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
        
        if (d == null)
        {
            lblCurrentZoomVal.setText("");
        }
        else
        {
            lblCurrentZoomVal.setText(String.valueOf(zoomFactor));
        }
    }

}