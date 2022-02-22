import java.io.*;
import java.util.List;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfAction;
import com.gnostice.pdfone.PdfAnnot;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfLinkAnnot;
import com.gnostice.pdfone.PdfPage;
import com.gnostice.pdfone.PdfReader;
import com.gnostice.pdfone.PdfRemoteGotoAction;


public class ManagingLinkAnnotationActions
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }
    
    // Usage: java ManagingLinkAnnotationActions <input file path> <output file path>
    public static void main(String[] args) throws IOException, PdfException
    {
        //////////////////////////////////////////////////////
        // Managin actions associated with Link Annotations //
        //////////////////////////////////////////////////////

        try
        {
            PdfReader r = PdfReader.fileReader(args[0]);
            r.setOutFilePath(args[1]);
            PdfDocument d = new PdfDocument(r);
            
            PdfPage p = d.getPage(1);
            
            List annotList = p.getAllAnnotations(PdfAnnot.ANNOT_TYPE_LINK);
            
            for (int i = 0, limit = annotList.size(); i < limit; i++)
            {
                PdfLinkAnnot linkAnnot = (PdfLinkAnnot) annotList.get(i);
                
                // Get all actions associated with the linkAnnot. 
                // Using getAllActions(int), you can get the
                // actions of particular type
                // Example: getAllActions(PdfAction.REMOTE_GOTO)
                List al = linkAnnot.getAllActions();
                
                for (int j = 0, size = al.size(); j < size; j++)
                {
                    // Type cast the object type to PdfAction
                    PdfAction actionObj = (PdfAction) al.get(j);
                    
                    // check the action type of the object.
                    // in this case i want to handle only RemoteGoTo action
                    if (actionObj.getActionType() == PdfAction.REMOTE_GOTO)
                    {
                        // Type cast the actionObj type to PdfRemoteGotoAction
                        PdfRemoteGotoAction remoteGoToAction = ((PdfRemoteGotoAction) actionObj);
                        // change the remote file path associated with this action object
                        remoteGoToAction.setPdfFilePath("C:\\Thumbnails_Sample.pdf");
                        // change the destination page number
                        remoteGoToAction.setPageNo(2);
                    }
                }
                
                // Actions can be removed from the link annotations
                linkAnnot.removeAllActions(PdfAction.LAUNCH);
            }
            
            d.setOpenAfterSave(true);
            d.write();
            r.dispose();
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Usage: java " +
                    "ManagingLinkAnnotationActions " +
                    "<intput file path> <output file path>");
        }
    }
}
