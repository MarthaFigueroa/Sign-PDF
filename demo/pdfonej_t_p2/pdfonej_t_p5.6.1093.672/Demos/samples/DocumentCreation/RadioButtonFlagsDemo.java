import java.awt.Color;
import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfFormField;
import com.gnostice.pdfone.PdfFormRadioButton;
import com.gnostice.pdfone.PdfFormTextField;
import com.gnostice.pdfone.PdfPage;
import com.gnostice.pdfone.PdfRect;

/*
 * PdfRadioButtonFlagsSampleDemo program demonstrates - how to create a Pdf Document
 * with PdfRadioButtons by setting field flags to the parent.
 */
public class RadioButtonFlagsDemo
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }   
    public static void main(String[] args) throws PdfException,
        IOException
    {
        sampleDemo1(args);
//        sampleDemo2(args);
//        sampleDemo3(args);
    }

    /*
     * sampleDemo1() demonstrates how to create a PdfRadioButtonGroup
     * by setting field flags to it
     */
    
    public static void sampleDemo1(String[] args)
    {
        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();
            
            /*
             * create a page object
             */
            PdfPage p = new PdfPage();

            //co-ordinates
            int xCoord = 100, yCoord = 100;
            
            //write text
            p.writeText("Radio1:", xCoord, yCoord);
            
            // create a radio button form field object with the
            // attributes
            PdfFormRadioButton r1 = new PdfFormRadioButton("radio1");
            r1.setBackgroundColor(Color.GRAY);
            r1.setBorderColor(Color.BLACK);
            r1.setState(PdfFormRadioButton.BUTTON_STATE_ON);
            r1.setRect(new PdfRect(xCoord+50, yCoord, 15, 15));

            p.writeText("Radio2:", xCoord, yCoord += 20);

            // create a radio button form field object with the
            // attributes

            PdfFormRadioButton r2 = new PdfFormRadioButton("radio1");
            r2.setBackgroundColor(Color.GRAY);
            r2.setBorderColor(Color.BLACK);
            r2.setState(PdfFormRadioButton.BUTTON_STATE_OFF);
            r2.setRect(new PdfRect(xCoord+50, yCoord , 15, 15));
            
            p.writeText("Radio3:", xCoord, yCoord += 20);

            // create a radio button form field object with the
            // attributes

            PdfFormRadioButton r3 = new PdfFormRadioButton("radio3");
            r3.setBackgroundColor(Color.GRAY);
            r3.setBorderColor(Color.BLACK);
            r3.setState(PdfFormRadioButton.BUTTON_STATE_OFF);
            r3.setRect(new PdfRect(xCoord + 50, yCoord, 15, 15));
            
            p.writeText("Radio4:", xCoord, yCoord += 20);

            // create a radio button form field object with the
            // attributes

            PdfFormRadioButton r4 = new PdfFormRadioButton("radio4");
            r4.setBackgroundColor(Color.GRAY);
            r4.setBorderColor(Color.BLACK);
            r4.setState(PdfFormRadioButton.BUTTON_STATE_OFF);
            r4.setRect(new PdfRect(xCoord+50, yCoord, 15, 15));
            
            PdfFormField radioFormField = new PdfFormField(
                PdfFormField.TYPE_RADIOGROUP, "rGrp");
            // by setting RadioInUnison flag all the radio buttons
            // with same name will behave same
            radioFormField.setRadioInUnison(true);
            radioFormField.addChildField(r1);
            radioFormField.addChildField(r2);
            radioFormField.addChildField(r3);
            radioFormField.addChildField(r4);
            
            p.addFormField(radioFormField);
            d.add(p);
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[0]);

            /* Dispose the I/O files associated with this document object */
            d.close();
            
        }
        catch (Exception e)
        {
            System.out
            .println("Usage : java RadioButtonFlagsDemo "
                + "<input file path>");
        }
    }
    
    /*
     * sampleDemo2() demonstrates how to create a PdfRadioButtonGroup
     * by setting field flags to it
     */
    
    public static void sampleDemo2(String[] args)
    {
        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();
            
            /*
             * set the override field appearance to false to see the
             * original appearance of radio buttons
             */
            d.setOverrideFieldAppearanceStreams(false);

            
            PdfPage p = new PdfPage();

            int xCoord = 100, yCoord = 100;
            
            p.writeText("Text Field:", xCoord, yCoord);
            
            PdfFormTextField tx1 = new PdfFormTextField("text1");
            tx1.setBackgroundColor(Color.GRAY);
            tx1.setBorderColor(Color.GRAY);
            tx1.setValue("Lokesh");
            tx1.setRect(xCoord+50, yCoord, 50, 20);
           
            p.addFormField(tx1);
            
            p.writeText("Radio1:", xCoord, yCoord += 20);
            
            PdfFormRadioButton r1 = new PdfFormRadioButton("radio1");
            r1.setBackgroundColor(Color.GRAY);
            r1.setBorderColor(Color.BLACK);
            r1.setState(PdfFormRadioButton.BUTTON_STATE_ON);
            r1.setRect(new PdfRect(xCoord+50, yCoord, 15, 15));
            
            p.writeText("Radio2:", xCoord, yCoord += 20);
            
            PdfFormRadioButton r2 = new PdfFormRadioButton("radio1");
            r2.setBackgroundColor(Color.GRAY);
            r2.setBorderColor(Color.BLACK);
            r2.setState(PdfFormRadioButton.BUTTON_STATE_OFF);
            r2.setRect(new PdfRect(xCoord+50, yCoord , 15, 15));
            
            p.writeText("Radio3:", xCoord, yCoord += 20);
            
            PdfFormRadioButton r3 = new PdfFormRadioButton("radio3");
            r3.setBackgroundColor(Color.GRAY);
            r3.setBorderColor(Color.BLACK);
            r3.setState(PdfFormRadioButton.BUTTON_STATE_OFF);
            r3.setRect(new PdfRect(xCoord + 50, yCoord, 15, 15));
            
            p.writeText("Radio4:", xCoord, yCoord += 20);

            PdfFormRadioButton r4 = new PdfFormRadioButton("radio4");
            r4.setBackgroundColor(Color.GRAY);
            r4.setBorderColor(Color.BLACK);
            r4.setState(PdfFormRadioButton.BUTTON_STATE_OFF);
            r4.setRect(new PdfRect(xCoord+50, yCoord, 15, 15));
            
            PdfFormField radioFormField = new PdfFormField(
                PdfFormField.TYPE_RADIOGROUP, "rGrp");
            // by setting RadioNoToggleToOff flag zero or one radio
            // buttons can be in on state and allows to deselects a
            // radio button in ON state
            radioFormField.setRadioNoToggleToOff(true);
            radioFormField.addChildField(r1);
            radioFormField.addChildField(r2);
            radioFormField.addChildField(r3);
            radioFormField.addChildField(r4);
            p.addFormField(radioFormField);

            d.add(p);
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[0]);

            /* Dispose the I/O files associated with this document object */
            d.close();
            
        }
        catch (Exception e)
        {
            System.out
            .println("Usage : java RadioButtonFlagsDemo "
                + "<input file path>");
        }
    }
    
    /*
     * sampleDemo3() demonstrates how to create a PdfRadioButtonGroup
     * by setting field flags RadioNoToggleToOff and RadioInUnison.
     */
    
    public static void sampleDemo3(String[] args)
    {
        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();
            
            /*
             * set the override field appearance to false to see the
             * original appearance of radio buttons
             */
            d.setOverrideFieldAppearanceStreams(false);

            
            PdfPage p = new PdfPage();

            int xCoord = 100, yCoord = 100;
            
            p.writeText("Text Field:", xCoord, yCoord);
            
            PdfFormTextField tx1 = new PdfFormTextField("text1");
            tx1.setBackgroundColor(Color.GRAY);
            tx1.setBorderColor(Color.GRAY);
            tx1.setValue("Lokesh");
            tx1.setRect(xCoord+50, yCoord, 50, 20);
           
            p.addFormField(tx1);
            
            p.writeText("Radio1:", xCoord, yCoord += 20);
            
            PdfFormRadioButton r1 = new PdfFormRadioButton("radio1");
            r1.setBackgroundColor(Color.GRAY);
            r1.setBorderColor(Color.BLACK);
            r1.setState(PdfFormRadioButton.BUTTON_STATE_ON);
            r1.setRect(new PdfRect(xCoord+50, yCoord, 15, 15));
            
            p.writeText("Radio2:", xCoord, yCoord += 20);
            
            PdfFormRadioButton r2 = new PdfFormRadioButton("radio1");
            r2.setBackgroundColor(Color.GRAY);
            r2.setBorderColor(Color.BLACK);
            r2.setState(PdfFormRadioButton.BUTTON_STATE_OFF);
            r2.setRect(new PdfRect(xCoord+50, yCoord , 15, 15));
            
            p.writeText("Radio3:", xCoord, yCoord += 20);
            
            PdfFormRadioButton r3 = new PdfFormRadioButton("radio3");
            r3.setBackgroundColor(Color.GRAY);
            r3.setBorderColor(Color.BLACK);
            r3.setState(PdfFormRadioButton.BUTTON_STATE_OFF);
            r3.setRect(new PdfRect(xCoord + 50, yCoord, 15, 15));
            
            p.writeText("Radio4:", xCoord, yCoord += 20);

            PdfFormRadioButton r4 = new PdfFormRadioButton("radio4");
            r4.setBackgroundColor(Color.GRAY);
            r4.setBorderColor(Color.BLACK);
            r4.setState(PdfFormRadioButton.BUTTON_STATE_OFF);
            r4.setRect(new PdfRect(xCoord+50, yCoord, 15, 15));
            
            PdfFormField radioFormField = new PdfFormField(
                PdfFormField.TYPE_RADIOGROUP, "rGrp");
            // by setting RadioNoToggleToOff flag zero or one radio
            // buttons can be in on state and allows to deselects a
            // radio button in ON state
            radioFormField.setRadioNoToggleToOff(true);
            radioFormField.setRadioInUnison(true);
            radioFormField.addChildField(r1);
            radioFormField.addChildField(r2);
            radioFormField.addChildField(r3);
            radioFormField.addChildField(r4);
            p.addFormField(radioFormField);

            d.add(p);
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[0]);

            /* Dispose the I/O files associated with this document object */
            d.close();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out
            .println("Usage : java RadioButtonFlagsDemo "
                + "<input file path>");
        }
        
    }

}
