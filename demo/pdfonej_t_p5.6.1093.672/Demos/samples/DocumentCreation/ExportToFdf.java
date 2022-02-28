import java.awt.Color;
import java.io.File;
import java.io.IOException;

import com.gnostice.pdfone.*;
import com.gnostice.pdfone.PdfAction.PdfEvent;
import com.gnostice.pdfone.encodings.PdfEncodings;
import com.gnostice.pdfone.fonts.PdfFont;

public class ExportToFdf
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /* Usage : java ExportToFdf <output file path> <Fdf file path>*/
    public static void main(String[] args) throws IOException,
        PdfException
    {
        ///////////////////////////////////
        //  ExportToFdf of PDF Document  //
        ///////////////////////////////////

        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();

            /* Create a font and set its color */
            PdfFont font = PdfFont.create("Helvetica", 10, PdfEncodings.WINANSI);
            font.setColor(Color.BLACK);

            /* Create a new PdfPage */
            PdfPage p;
            p = new PdfPage(PdfPageSize.A3);
            p.setMeasurementUnit(PdfMeasurement.MU_INCHES);

            /* Create a PdfFormTextField object and set its properties */
            PdfFormTextField f0 = new PdfFormTextField(new PdfRect(3, 2.6, 4, .4));
            f0.setBackgroundColor(Color.LIGHT_GRAY);
            f0.setBorderColor(Color.DARK_GRAY);
            f0.setFont(font);
            f0.setName("Text1");
            f0.setValue("Type Your Name Here");
            f0.setDefaultValue("Type Your Name Here");
            f0.setNoScroll(false);
            f0.setNoSpellCheck(true);
            f0.setAsFileSelectField(false);
            f0.setAsPasswordField(false);
            f0.setMultiline(false);
            f0.setFlags(PdfFormField.FLAG_PRINT | PdfFormField.FLAG_READONLY);

            /* Add the PdfFormTextField to the page */
            p.addFormField(f0);

            /* Create and set properties for a second PdfFormTextField  object */
            PdfFormTextField f1 = new PdfFormTextField(new PdfRect(3, 3.6, 4, .4));
            f1.setBackgroundColor(Color.LIGHT_GRAY);
            f1.setBorderColor(Color.DARK_GRAY);
            f1.setFont(font);
            f1.setName("Text2");
            f1.setValue("DD/MM/YYYY");
            f1.setDefaultValue("DD/MM/YYYY");
            f1.setNoScroll(false);
            f1.setNoSpellCheck(true);
            f1.setAsFileSelectField(false);
            f1.setComb(true);
            f1.setMaxlen(10);
//            f1.setAlignment(PdfFormTextField.ALIGNMENT_CENTER);
            f1.setAsPasswordField(false);
            f1.setMultiline(false);

            /* Add the PdfFormTextField to the page */
            p.addFormField(f1);

            /* Create and set properties for a third PdfFormTextField */
            PdfFormTextField f3 = new PdfFormTextField(new PdfRect(3, 4.6, 4, .4));
            f3.setBackgroundColor(Color.LIGHT_GRAY);
            f3.setBorderColor(Color.DARK_GRAY);
            f3.setFont(font);
            f3.setName("Text3");
            f3.setValue("Type Your Email Here");
            f3.setDefaultValue("Type Your Email Here");
            f3.setNoScroll(false);
            f3.setNoSpellCheck(true);
            f3.setAsFileSelectField(false);
//            f3.setAlignment(PdfFormTextField.ALIGNMENT_CENTER);
            f3.setAsPasswordField(false);
            f3.setMultiline(false);

            /* Add the PdfFormTextField to the Page */
            p.addFormField(f3);

            /* Create and set properties for PdfFormComboBox */
            PdfFormComboBox f5 = new PdfFormComboBox(new PdfRect(3, 6.6, 4, .4));
            f5.setBackgroundColor(Color.LIGHT_GRAY);
            f5.setBorderColor(Color.DARK_GRAY);
            f5.setName("combo1");
            f5.addItem("India");
            f5.addItem("England");
            f5.addItem("USA");
            f5.addItem("Russia");
            f5.addItem("Germany");
            f5.setEditable(true);
            f5.setValue("Select Your Country");
            f5.setDefaultValue("Select Your Country");
            f5.setFont(PdfFont.create("Helvetica", PdfFont.PLAIN, 10,
                PdfEncodings.WINANSI));

            /* Add the PdfFormComboBox to the page */
            p.addFormField(f5);

            /* Create and properties for PdfFormListBox */
            PdfFormListBox f6 = new PdfFormListBox(new PdfRect(3, 8, 4, 1));
            f6.setBackgroundColor(Color.LIGHT_GRAY);
            f6.setBorderColor(Color.DARK_GRAY);
            f6.setName("list1");
            f6.addItem("Account/Finance");
            f6.addItem("Computer (Hardware)");
            f6.addItem("Computer (Sofftware)");
            f6.addItem("Government");
            f6.addItem("Research");
            f6.addItem("Sales");
            f6.addItem("Student");
            f6.addItem("Others");
            f6.setFlags(PdfFormField.FLAG_READONLY);
            f6.setValue("listbox");
            f6.setFont(font);

            /* Add the PdfFormListBox to the page */
            p.addFormField(f6);

            /* Create and set properties for PdfFormCheckBox */
            PdfFormCheckBox f7 = new PdfFormCheckBox(new PdfRect(3, 10, .3, .3));
            f7.setBackgroundColor(Color.LIGHT_GRAY);
            f7.setBorderColor(Color.DARK_GRAY);
            f7.setState(PdfFormField.BUTTON_STATE_ON);
            f7.setName("check");

            /* Add the PdfFormCheckBox to the page */
            p.addFormField(f7);

            /* Create and set properties for the PdfFormCheckBox */
            PdfFormPushButton f8 = new PdfFormPushButton(new PdfRect(
                    2.5, 11, 1, .5));
            f8.setBackgroundColor(Color.LIGHT_GRAY);
            f8.setBorderColor(Color.DARK_GRAY);

            /* Set JavaScript action to the PdfFormPushButton */
            f8.addAction(PdfAction.JAVASCRIPT, PdfEvent.ON_MOUSE_DOWN, "app.alert('Gnostice PDFOne on Mouse Down')");
            f8.setName("button1");
            f8.setNormalCaption("Javascript");
            f8.setRolloverCaption("Rollover");
            f8.setDownCaption("Down");
            f8.setFont(font);

            /* Add the PdfFormPushButton to the page */
            p.addFormField(f8);

            /* Create and set properties for another PdfFormCheckBox */
            PdfFormPushButton f9 = new PdfFormPushButton(new PdfRect(4.5, 11, 1, .5));
            f9.setBackgroundColor(Color.LIGHT_GRAY);
            f9.setBorderColor(Color.DARK_GRAY);
            /* Set Reset action to the PdfFormPushButton */
            f9.addActionFormReset(PdfEvent.ON_MOUSE_ENTER);
            f9.setName("button2");
            f9.setNormalCaption("Reset");
            f9.setRolloverCaption("Rollover");
            f9.setDownCaption("Down");
            f9.setFont(font);
            /* Add the PdfFormPushButton to the page */
            p.addFormField(f9);

            /* Create and set properties for another PdfFormCheckBox */
            PdfFormPushButton f10 = new PdfFormPushButton(new PdfRect(6.5, 11, 1, .5));
            f10.setBackgroundColor(Color.LIGHT_GRAY);
            f10.setBorderColor(Color.DARK_GRAY);
            /* Set Submit action to the PdfFormPushButton */
            f10.addAction(PdfAction.URI, PdfEvent.ON_MOUSE_DOWN, "http://www.gnostice.com");
            f10.setName("button3");
            f10.setNormalCaption("Submit");
            f10.setRolloverCaption("Rollover");
            f10.setDownCaption("Down");
            f10.setFont(font);
            /* Add the PdfFormPushButton to the page */
            p.addFormField(f10);

            /* Create a font */
            PdfFont tf = PdfFont.create("Arial", 20, PdfEncodings.WINANSI);
            tf.setColor(Color.MAGENTA);
            p.writeText("FORM Creation Demo", tf, 3.7, 1);
            tf.setSize(12);
            tf.setColor(Color.BLACK);

            /* Write text to the page */
            p.writeText("Name:", tf, 2, 2.7);
            p.writeText("Date of Birth:", tf, 1.5, 3.7);
            p.writeText("EMail:", tf, 2, 4.7);
            p.writeText("Country:", tf, 2, 6.7);
            p.writeText("Profession:", tf, 1.8, 8.3);
            p.writeText("Yes, I want to subscribe", tf, 3.6, 9.75);

            /* Add page to document */
            d.add(p);

            /*
             * For exporting the name and value of the formfields present
             * in the document
             */
            d.exportToFDF(new File(args[1]));

            d.setOpenAfterSave(true);

            /* Save the document object */ 
            d.save(args[0]);

            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java ExportToFdf " +
                    "<output file path> <Fdf file path>");
        }
     }

}
