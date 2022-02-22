
import java.awt.Color;
import java.io.IOException;
import java.util.List;

import com.gnostice.pdfone.*;
import com.gnostice.pdfone.PdfAction.PdfEvent;
import com.gnostice.pdfone.encodings.PdfEncodings;
import com.gnostice.pdfone.fonts.PdfFont;

public class ManageFormFields
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /* Usage : java ManageFormFields <input file path> <output file path> */
    /* Supply PDF file with FormFields as "input file path" */
    public static void main(String[] args) throws IOException,
        PdfException
    {
        /////////////////////////////////////////////////
        // Open a document and modify its form fields  //
        /////////////////////////////////////////////////

        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();

            /* Load the PDF document into the document object */
            d.load(args[0]);
    
            /* Create a font */
            PdfFont font = PdfFont.create("Helvetica", 10, PdfEncodings.WINANSI);
            font.setColor(Color.BLACK);
    
            /* Retrieve all form fields from page 1 of the document */
            PdfPage p = d.getPage(1);
            List lis = p.getAllFormFields();
    
            for (int i = 0; i < lis.size(); i++)
            {
                /* Cast the retrieved form field object with its super class PdfFormField. */
                PdfFormField formField = (PdfFormField)lis.get(i);
    
                /*
                 Check whether the PdfFormField object is an instance of
                 PdfFormTextField
                */
                if(formField instanceof PdfFormTextField)
                {
                    /* Change the properties of the PdfFormTextField */
                    PdfFormTextField textField = (PdfFormTextField)formField;
                    textField.setBackgroundColor(Color.LIGHT_GRAY);
                    textField.setBorderColor(Color.DARK_GRAY);
                    textField.setFont(font);
                    textField.setName("Text Changed");
                    textField.setValue("DD/MM/YYYY");
                    textField.setDefaultValue("DD/MM/YYYY");
                    textField.setNoScroll(false);
                    textField.setNoSpellCheck(true);
                    textField.setAsFileSelectField(false);
                    textField.setComb(true);
                    textField.setMaxlen(10);
                    textField.setAlignment(PdfFormTextField.ALIGNMENT_CENTER);
                    textField.setAsPasswordField(false);
                    textField.setMultiline(false);
                }
                /*
                 Check whether the PdfFormField object is an instance of 
                 PdfFormComboBox
                */
                if(formField instanceof PdfFormComboBox)
                {
                    /* Change properties of the PdfFormComboBox */
                    PdfFormComboBox comboBox = (PdfFormComboBox)formField;
                    comboBox.setBackgroundColor(Color.LIGHT_GRAY);
                    comboBox.setBorderColor(Color.DARK_GRAY);
                    comboBox.setName("combo1");
                    comboBox.addItem("India");
                    comboBox.addItem("England");
                    comboBox.addItem("USA");
                    comboBox.addItem("Russia");
                    comboBox.addItem("Germany");
                    comboBox.setEditable(true);
                    comboBox.setValue("Select Your Country");
                    comboBox.setDefaultValue("Select Your Country");
                    comboBox.setFont(PdfFont.create("Helvetica", PdfFont.PLAIN, 10,
                        PdfEncodings.WINANSI));
                }
                /*
                 Check whether the PdfFormField object is an instance of
                 PdfFormListBox
                */
                if(formField instanceof PdfFormListBox)
                {
                    /* Change properties of the PdfFormListBox */
                    PdfFormListBox listBox = (PdfFormListBox)formField;
                    listBox.setBackgroundColor(Color.LIGHT_GRAY);
                    listBox.setBorderColor(Color.DARK_GRAY);
                    listBox.setName("list1");
                    listBox.addItem("Account/Finance");
                    listBox.addItem("Computer (Hardware)");
                    listBox.addItem("Computer (Sofftware)");
                    listBox.addItem("Government");
                    listBox.addItem("Research");
                    listBox.addItem("Sales");
                    listBox.addItem("Student");
                    listBox.addItem("Others");
                    listBox.setValue("listbox");
                    listBox.setFont(font);
                 }
                /*
                 Check whether the PdfFormField object is an instance of
                 PdfFormCheckBox
                */
                if(formField instanceof PdfFormCheckBox)
                {
                    /* Change properties of the PdfFormCheckBox */
                    PdfFormCheckBox checkBox = (PdfFormCheckBox) formField;
                    checkBox.setBackgroundColor(Color.LIGHT_GRAY);
                    checkBox.setBorderColor(Color.DARK_GRAY);
                    checkBox.setState(PdfFormField.BUTTON_STATE_ON);
                    checkBox.setName("check");
                }
                /*
                 Check whether the PdfFormField object is an instance of
                 PdfFormPushButton
                */
                if(formField instanceof PdfFormPushButton)
                {
                    /* Change properties of the PdfFormPushButton */
                    PdfFormPushButton pushButton = (PdfFormPushButton)formField;
                    pushButton.setBackgroundColor(Color.LIGHT_GRAY);
                    pushButton.setBorderColor(Color.DARK_GRAY);
                    /* Set JavaScript action for the PdfFormPushButton */
                    pushButton.addAction(PdfAction.JAVASCRIPT,
                            PdfEvent.ON_MOUSE_DOWN,
                            "app.alert('Gnostice PDFOne')");
                    pushButton.setName("button1");
                    pushButton.setNormalCaption("Javascript");
                    pushButton.setRolloverCaption("Rollover");
                    pushButton.setDownCaption("Down");
                    pushButton.setFont(font);
                }
                /*
                 Check whether the PdfFormField object is an instance of
                 PdfFormRadioButton
                */
    //            if(formField instanceof PdfFormRadioButton)
    //            {
    //                PdfFormRadioButton radioButton = (PdfFormRadioButton)formField;
    //                ArrayList list = (ArrayList)radioButton.getItems();
    //                for (int j = 0; j < list.size(); j++)
    //                {
    //                    PdfFormRadioItem radioItem = (PdfFormRadioItem)list.get(i);
    //                    /* Change properties of the PdfFormRadioItem */
    //                    radioItem.setBackgroundColor(Color.LIGHT_GRAY);
    //                    radioItem.setBorderColor(Color.DARK_GRAY);
    //                    radioItem.setFontColor(Color.BLACK);
    //                    radioItem.setSymbol(PdfFormRadioItem.SYMBOL_CIRCLE);
    //                }
    //                /* Change properties of the PdfFormRadioButton */
    //                radioButton.setVisible(true);
    //                radioButton.setPrintable(true);
    //                radioButton.setFieldName("radio");
    //                radioButton.setSelectedItemIndex(0);
    //                radioButton.setAtLeastOneSelectedItem(true);
    //            }
            }
    
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[1]);

            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java ManageFormFields" +
                    " <input file path> <output file path>");
        }
    }
}
