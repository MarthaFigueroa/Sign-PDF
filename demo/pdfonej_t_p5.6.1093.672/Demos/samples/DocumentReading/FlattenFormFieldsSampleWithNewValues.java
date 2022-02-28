import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfFormCheckBox;
import com.gnostice.pdfone.PdfFormComboBox;
import com.gnostice.pdfone.PdfFormField;
import com.gnostice.pdfone.PdfFormPushButton;
import com.gnostice.pdfone.PdfFormRadioButton;
import com.gnostice.pdfone.PdfFormTextField;
import com.gnostice.pdfone.PdfAction.PdfEvent;

/*
 * FlattenFormFieldsSampleWithNewValues demonstrates - how to
 * create a Pdf Document with all the form fields flattened with a
 * modified values set to them.
 */
public class FlattenFormFieldsSampleWithNewValues
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }   
    public static void main(String[] args) throws PdfException,
        IOException
    {
        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument document = new PdfDocument();

            /* Load the PDF document into the document object */
            document.load(args[0]);
            
            /* get the arraylist of form fields from the page */
            ArrayList formFieldsList = (ArrayList) document
                .getAllFormFields();

//          check for the desired formfield name to modify the values of it.
            for (Iterator iter = formFieldsList.iterator(); iter
                .hasNext();)
            {
                PdfFormField tempField = (PdfFormField) iter.next();
                if (tempField instanceof PdfFormTextField)
                {
                    if (tempField.getName().equals("name_tf"))
                    {
                        tempField.setValue("vyshak");
                    }
                    else if (tempField.getName().equals("dob_tf"))
                    {
                        ((PdfFormTextField) tempField)
                            .setValue("12/2/1983");
                    }
                    else if (((PdfFormTextField) tempField).getName()
                        .equalsIgnoreCase("age_tf"))
                    {
                        ((PdfFormTextField) tempField).setValue("24");
                    }
                    else if (((PdfFormTextField) tempField).getName()
                        .equalsIgnoreCase("adr_tf"))
                    {
                        ((PdfFormTextField) tempField)
                            .setValue("h.no:26, laxmipuram ulsoor, Banglore.");
                    }
                }
                else if (tempField instanceof PdfFormComboBox)
                {
                    if (((PdfFormComboBox) tempField).getName()
                        .equalsIgnoreCase("prof_cb"))
                    {
                        ((PdfFormComboBox) tempField)
                            .addItem("Doctor");
                    }
                }
                else if(tempField.getType() == PdfFormField.TYPE_RADIOGROUP)
                {
                    List list = tempField.getChildList();
                    
                    for (Iterator iterator = list.iterator(); iterator
                        .hasNext();)
                    {
                        PdfFormRadioButton element = (PdfFormRadioButton) iterator.next();
                        
                        if(element.getOnStateName().equals("female"))
                        {
                            element.setState(PdfFormField.BUTTON_STATE_ON);
                        }
                    }
                }
                else if(tempField instanceof PdfFormCheckBox)
                {
                    PdfFormCheckBox checkBox = (PdfFormCheckBox)tempField;
                    if(checkBox.getName().equalsIgnoreCase("cb1"))
                    {
                        checkBox.setState(PdfFormField.BUTTON_STATE_OFF);
                    }
                }
                else if(tempField instanceof PdfFormPushButton)
                {
                    if(tempField.getName().equalsIgnoreCase("btnSubmit"))
                    {
                        PdfFormPushButton sendBtn = (PdfFormPushButton) tempField;
                        //modify the submit action of submit button
                        sendBtn.addActionFormSubmit(PdfEvent.ON_MOUSE_UP,
                                "http://www.gnostice.com/newsletters/demos/" +
                                "200804/forms_test.asp");              
                    }
                }
            }
            /*
             * flattens all the form fields of the document with the
             * existing values
             */
            document.flattenFormFields(true);
            //or you can also use 
//            document.flattenFormFields();
            
            /*set the document to open after save*/
            document.setOpenAfterSave(true);
            
            /* Save the document object */ 
            document.save(args[1]);

            /* Dispose the I/O files associated with this document object */
            document.close();
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out
                .println("Usage : java FlattenFormFieldsSampleWithNewValues "
                    + "<input file path> <output file path>");
        }
    }

}
