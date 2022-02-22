import java.awt.Color;
import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfAction;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfGotoAction;
import com.gnostice.pdfone.PdfJavascriptAction;
import com.gnostice.pdfone.PdfLaunchAction;
import com.gnostice.pdfone.PdfLinkAnnot;
import com.gnostice.pdfone.PdfMeasurement;
import com.gnostice.pdfone.PdfNamedAction;
import com.gnostice.pdfone.PdfPage;
import com.gnostice.pdfone.PdfPageSize;
import com.gnostice.pdfone.PdfRect;
import com.gnostice.pdfone.PdfRemoteGotoAction;
import com.gnostice.pdfone.PdfURIAction;
import com.gnostice.pdfone.encodings.PdfEncodings;
import com.gnostice.pdfone.fonts.PdfFont;

public class CreatingLinkAnnotationActions
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    public static void main(String[] args) throws IOException,
        PdfException
    {
        //////////////////////////////////////////
        // Creating actions of Link Annotations //
        //////////////////////////////////////////
        
        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();
    
            // Create some Font objects
            PdfFont font_Helvetica = PdfFont.create("Helvetica", 15,
                PdfEncodings.WINANSI);
            font_Helvetica.setColor(Color.BLACK);
    
            PdfFont font_Courier = PdfFont.create("COURIERNEW", 15,
                PdfEncodings.WINANSI);
            font_Courier.setColor(Color.blue);
    
            PdfFont font_HelveticaSmall = PdfFont.create("Helvetica", 10,
                PdfEncodings.WINANSI);
            font_HelveticaSmall.setColor(Color.red);
    
            PdfPage p = new PdfPage(PdfPageSize.A4);
            p.setMeasurementUnit(PdfMeasurement.MU_POINTS);
    
            p.writeText("Link Annotations", font_Helvetica, 220, 100);
    
            /* create a GoTo Link Annotations and set its Properties */
            p.writeText("click here", font_Courier, 100, 150);
            p.writeText(
                "GoTo Action: Navigates within the current document",
                font_HelveticaSmall, 210, 155);
            PdfLinkAnnot linkAnnotGoto = new PdfLinkAnnot(new PdfRect(
                100, 155, 90, 12), Color.red);
            linkAnnotGoto
                .setHighlightMode(PdfLinkAnnot.HIGHLIGHT_MODE_INVERT);
            // create an instance of PdfGotoAction
            PdfGotoAction goto_action = new PdfGotoAction(3);
            // add the action object to linkAnnotGoto
            linkAnnotGoto.addAction(goto_action);
    
            /* Create a LinkAnnotation of type Named Action */
            p.writeText("Click here", font_Courier, 100, 175);
            p.writeText("Named Action: Navigates to Next, " +
                    "Previous, Last or First page of the document",
                    font_HelveticaSmall, 210, 180);
            PdfLinkAnnot linkAnnotNamed = new PdfLinkAnnot(
                new PdfRect(100, 180, 90, 12), Color.RED);
            linkAnnotNamed.setHighlightMode(PdfLinkAnnot.HIGHLIGHT_MODE_INVERT);
            // create an instance of PdfNamedAction
            PdfNamedAction named_action = new PdfNamedAction(
                PdfAction.NAMED_NEXTPAGE);
            // add it to linkAnnotNamed
            linkAnnotNamed.addAction(named_action);
    
            /* Create a LinkAnnotation of type RemoteGoTo Action */
            p.writeText("Click here", font_Courier, 100, 200);
            p.writeText("RemetoGoTo Action: Opens another PDF Document",
                font_HelveticaSmall, 210, 205);
            PdfLinkAnnot linkAnnotGoToR = new PdfLinkAnnot(
                new PdfRect(100, 205, 90, 12), Color.red);
            linkAnnotGoToR.setHighlightMode(PdfLinkAnnot.HIGHLIGHT_MODE_INVERT);
            // create an instance of PdfRemoteGotoAction
            PdfRemoteGotoAction remoteGoto_action = new PdfRemoteGotoAction(
                "bookmarks.pdf", 1, true);
            // add it to linkAnnotGoToR
            linkAnnotGoToR.addAction(remoteGoto_action);
    
            /* Create a Link Annotation of type JavaScript */
            p.writeText("Click here", font_Courier, 100, 225);
            p.writeText("JavaScript Action: Executes JavaScript Actions",
                font_HelveticaSmall, 210, 230);
            PdfLinkAnnot linkAnnotJavaScript = new PdfLinkAnnot(
                new PdfRect(100, 230, 90, 12), Color.RED);
            linkAnnotJavaScript.setHighlightMode(PdfLinkAnnot.HIGHLIGHT_MODE_INVERT);
            // create an instance of PdfJavascriptAction and add it to
            // linkAnnotJavaScript
            PdfJavascriptAction js_action = new PdfJavascriptAction(
                "app.alert('Gnostice Information Technologies')");
            linkAnnotJavaScript.addAction(js_action);
            linkAnnotJavaScript.addActionJavaScript("app.alert('Smart " +
                    "needs smarter solutions')");
    
            /* Create a Link Annotation of type URI */
            p.writeText("Click here", font_Courier, 100, 250);
            p.writeText("URI Action: Opens specified URI",
                font_HelveticaSmall, 210, 255);
            PdfLinkAnnot linkAnnotURI = new PdfLinkAnnot(new PdfRect(100,
                255, 90, 12), Color.red);
            linkAnnotURI.setHighlightMode(PdfLinkAnnot.HIGHLIGHT_MODE_INVERT);
            // create an instance of PdfURIAction and add it to
            // linkAnnotURI
            PdfURIAction uri_action1 = new PdfURIAction("http://www.gnostice.com");
            PdfURIAction uri_action2 = new PdfURIAction("http://www.google.com");
            linkAnnotURI.addAction(uri_action1);
            linkAnnotURI.addAction(uri_action2);
    
            /* Create a Link Annotation of type Launch */
            p.writeText("Click here", font_Courier, 100, 275);
            p.writeText("Launch Action: Launches any file " +
                    "in there respective applications",
                    font_HelveticaSmall, 210, 280);
            PdfLinkAnnot linkAnnotLaunch = new PdfLinkAnnot(
                new PdfRect(100, 280, 90, 12), Color.red);
            linkAnnotLaunch.setHighlightMode(PdfLinkAnnot.HIGHLIGHT_MODE_INVERT);
            // create an instance of PdfLaunchAction and add it to
            // linkAnnotLaunch
            PdfLaunchAction launch_action = new PdfLaunchAction(
                "c:\\windows\\notepad.exe", false);
            linkAnnotLaunch.addAction(launch_action);
    
            p.addAnnotation(linkAnnotGoto);
            p.addAnnotation(linkAnnotNamed);
            p.addAnnotation(linkAnnotGoToR);
            p.addAnnotation(linkAnnotJavaScript);
            p.addAnnotation(linkAnnotURI);
            p.addAnnotation((linkAnnotLaunch));
            d.add(p);
            
            p = new PdfPage();
            d.add(p);
            p.writeText("Page " + d.getPageCount());
            
            p = new PdfPage();
            d.add(p);
            p.writeText("Page " + d.getPageCount());
    
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[0]);

            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Usage: java CreatingActions " +
                    "<output file path>");
        }
    }
}
