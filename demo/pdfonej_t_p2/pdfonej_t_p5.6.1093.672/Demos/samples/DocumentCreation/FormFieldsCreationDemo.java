import java.awt.Color;
import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfFormCheckBox;
import com.gnostice.pdfone.PdfFormComboBox;
import com.gnostice.pdfone.PdfFormField;
import com.gnostice.pdfone.PdfFormListBox;
import com.gnostice.pdfone.PdfFormPushButton;
import com.gnostice.pdfone.PdfFormRadioButton;
import com.gnostice.pdfone.PdfFormTextField;
import com.gnostice.pdfone.PdfPage;
import com.gnostice.pdfone.PdfRect;
import com.gnostice.pdfone.PdfTextFormatter;
import com.gnostice.pdfone.PdfAction.PdfEvent;
import com.gnostice.pdfone.encodings.PdfEncodings;
import com.gnostice.pdfone.fonts.PdfFont;

/*
 * FormFieldsCreateSample demonstrates - how to create a Pdf Document
 * with all the form fields.
 */
public class FormFieldsCreationDemo
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }
    
    public static void main(String[] args)throws PdfException, IOException
    {
        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();

            /* set the override field appearance */
            d.setOverrideFieldAppearanceStreams(true);

            /* create a font object to write text */
            PdfFont font = PdfFont.create("Helvetica", PdfFont.BOLD, 20,
                PdfEncodings.WINANSI);

            /* Create a PdfPage instance with the PdfPage*/
            PdfPage page = new PdfPage();

            /* write text to set heading of the form*/
            page.writeText("Personal Information Form", font,
                PdfTextFormatter.CENTER, 0, 80);

            /* create a font using which forms data can be displayed */
            PdfFont formFont = PdfFont.create("TimesNewRoman",
                PdfFont.PLAIN, 10, PdfEncodings.WINANSI);

            /* set the font as page's font object */
            page.setFont(formFont);

            /* initialize x and y coordinates to place the labels and form
             fields at differet positions*/
            double xCoord = 200;
            double yCoord = 120;

            page.writeText("Name:", xCoord, yCoord);

             /* create form textfield to enter name */
            PdfFormTextField nameField = new PdfFormTextField(
                new PdfRect(xCoord + 100, yCoord, 75, 20), "name_tf");
            nameField.setBorderColor(Color.BLACK);
            nameField.setBackgroundColor(Color.lightGray);
            nameField.setFont(formFont);
            nameField.setValue("sunil");// set's initial value of the text field

            /*add nameField to the first page created*/
            page.addFormField(nameField);

            yCoord += 30;
            page.writeText("DOB:", xCoord, yCoord);

            /* create form textfield to enter date of birth */
            PdfFormTextField dobField = new PdfFormTextField(new PdfRect(
                xCoord + 100, yCoord, 75, 20), "dob_tf");
            dobField.setBorderColor(Color.BLACK);
            dobField.setBackgroundColor(Color.lightGray);
            dobField.setMaxlen(10);
            dobField.setFont(formFont);
            dobField.setValue("16/12/1983");// set's initial value of the
                                            // dobField
            
            /*add nameField to the first page created*/
            page.addFormField(dobField);
            
            yCoord += 30;
            
            page.writeText("Gender:",xCoord, yCoord);
            
            page.writeText("Male:",xCoord+100, yCoord);
     
            /*
             * create form RadioButtonField which later can be added to
             * RadioButtonGroup Field
             */
            PdfFormRadioButton rBut1 = new PdfFormRadioButton(
                new PdfRect(xCoord + 125, yCoord, 15, 15), "male");
            rBut1.setBorderColor(Color.BLACK);
            rBut1.setBackgroundColor(Color.lightGray);
            //set the initial state of the button to on
            rBut1.setState(PdfFormRadioButton.BUTTON_STATE_ON);

            page.writeText("Female:",xCoord+165, yCoord);
            
            /*
             * create form RadioButtonField which later can be added to
             * RadioButtonGroup Field
             */
            PdfFormRadioButton rBut2 = new PdfFormRadioButton(
                new PdfRect(xCoord + 200, yCoord, 15, 15), "female");
            rBut2.setBorderColor(Color.BLACK);
            rBut2.setBackgroundColor(Color.lightGray);
            //set the state of the button to off
            rBut2.setState(PdfFormRadioButton.BUTTON_STATE_OFF);
            
            /*
             * create PdfFormField of type RadioGroup to which 
             * RadioButtonFields should be added
             */
            // note only one button should be selected at all times if no
            // flags are set
            PdfFormField rButGrp = new PdfFormField(PdfFormField.TYPE_RADIOGROUP, "gender");
            //add radio child to radio button group using addChild() method
            rButGrp.addChildField(rBut1);
            rButGrp.addChildField(rBut2);
            
            /*add PdfRadioButton Group  rButGrp to the first page created*/
            page.addFormField(rButGrp);

            yCoord += 30;

            page.writeText("Age:", xCoord, yCoord);

            /* create form textfield to enter ageField */
            PdfFormTextField ageField = new PdfFormTextField(new PdfRect(
                xCoord + 100, yCoord, 75, 20), "age_tf");
            ageField.setBorderColor(Color.BLACK);
            ageField.setBackgroundColor(Color.lightGray);
            ageField.setMaxlen(3);
            ageField.setFont(formFont);
            ageField.setValue("23");// set's initial value of the form
                                    // text

            /*add PdfTextField ageField to the first page created*/
            page.addFormField(ageField);

            yCoord += 30;
            
            page.writeText("Profession:", xCoord, yCoord);
            
            /* create PdfFormComboBox  to create list of profession */
            PdfFormComboBox profcb = new PdfFormComboBox(new PdfRect(
                xCoord + 100, yCoord, 100, 15), "prof_cb");
            profcb.setBorderColor(Color.BLACK);
            profcb.setBackgroundColor(Color.lightGray);
            profcb.setFont(formFont);

             /*add items to the combobox */
            profcb.addItem("Software Engineer", true);// initial selected item.
            profcb.addItem("Network Engineer");
            profcb.addItem("Civil Engineer");
            profcb.addItem("Mechanical Engineer");
            page.addFormField(profcb);
            
            
            yCoord += 30;
            
            page.writeText("Domain:", xCoord, yCoord);

            /* Create an instance of PdfFormListBox to create a list box */
            PdfFormListBox lb = new PdfFormListBox(new PdfRect(xCoord +100, yCoord, 100, 50));
            lb.setBackgroundColor(Color.LIGHT_GRAY);
            lb.setBorderColor(Color.DARK_GRAY);
            lb.setFont(formFont);
            lb.setName("list1");
            /*add initial items to the listbox */
            lb.addItem("Account/Finance");
            lb.addItem("Computer (Hardware)");
            lb.addItem("Computer (Sofftware)", true);// set as initial
                                                        // selected item
            lb.addItem("Government");
            lb.addItem("Research");
            lb.addItem("Sales");
            lb.addItem("Student");
            lb.addItem("Others");
            lb.setFlags(PdfFormField.FLAG_READONLY);
            lb.setValue("listbox");
            
            page.addFormField(lb);
            
            yCoord += 70;
            page.writeText("Laguages known:", xCoord, yCoord);
            page.writeText("C:", xCoord+100, yCoord);
            
            /* Create an instance of PdfFormCheckBox to create a check box */
            PdfFormCheckBox cb1 = new PdfFormCheckBox(new PdfRect(
                xCoord + 115, yCoord, 15, 15), "cb1");
            cb1.setBackgroundColor(Color.lightGray);
            cb1.setBorderColor(Color.BLACK);
            //set the initial state of the check box to ON
            cb1.setState(PdfFormField.BUTTON_STATE_ON);
            
            page.writeText("Java:", xCoord+150, yCoord);
            /* Create an instance of PdfFormCheckBox to create a check box */
            PdfFormCheckBox cb2 = new PdfFormCheckBox(new PdfRect(
                xCoord + 175, yCoord, 15, 15), "cb2");
            cb2.setBackgroundColor(Color.lightGray);
            cb2.setBorderColor(Color.BLACK);
//          set the initial state of the check box to ON
            cb2.setState(PdfFormField.BUTTON_STATE_ON);
            
            /* Create an instance of PdfFormCheckBox to create a check box */
            page.writeText(".NET:", xCoord+200, yCoord);
            PdfFormCheckBox cb3 = new PdfFormCheckBox(new PdfRect(
                xCoord + 230, yCoord, 15, 15), "cb3");
            cb3.setBackgroundColor(Color.lightGray);
            cb3.setBorderColor(Color.BLACK);
//          set the initial state of the check box to OFF
            cb3.setState(PdfFormField.BUTTON_STATE_OFF);
            
            //add all the three instances of PdfCheckBoxes to the page
            page.addFormField(cb1);
            page.addFormField(cb2);
            page.addFormField(cb3);
            
            yCoord += 30;
            
            page.writeText("Address:", xCoord, yCoord);

            /* Create an instance of PdfFormTextField to create a multi line textfield*/
            PdfFormTextField adrField = new PdfFormTextField(new PdfRect(
                xCoord + 100, yCoord, 100, 50), "adr_tf");
            adrField.setFont(formFont);
            adrField.setBorderColor(Color.BLACK);
            adrField.setBackgroundColor(Color.lightGray);
            adrField.setMultiline(true);// set this text field as Textfield
                                        // area.

            adrField
                .setValue("Gnostice Information Technologies Pvt. Ltd \n #45 1 st Floor "
                    + "Sankey Road, Palace Orchards. \n Banglore- 560 003 India \n");

            page.addFormField(adrField);

            /* Create Submit Push Button */
            yCoord += 75;
            PdfFormPushButton sendButn = new PdfFormPushButton(
                new PdfRect(xCoord+50, yCoord, 40, 20));
            sendButn.setBorderColor(Color.BLACK);
            sendButn.setBackgroundColor(Color.lightGray);
            sendButn.setFont(formFont);
            /* Set Submit action to the PdfFormPushButton */
            sendButn.addActionFormSubmit(
              PdfEvent.ON_MOUSE_UP,
              "http://www.gnostice.com/newsletters/demos" +
              "/200802/pdfone_net/forms_submit_test.asp");
            sendButn.setName("btnSubmit");
            sendButn.setNormalCaption("Submit");
            page.addFormField(sendButn);

            /* Create Reset Push Button */
            PdfFormPushButton resetButn = new PdfFormPushButton(
                new PdfRect(xCoord + 125, yCoord, 40, 20));
            resetButn.setBorderColor(Color.BLACK);
            resetButn.setBackgroundColor(Color.lightGray);
            resetButn.setFont(formFont);
            /* Set Reset action to the PdfFormPushButton */
            resetButn.addActionFormReset(PdfEvent.ON_MOUSE_UP);
            resetButn.setName("btnReset");
            resetButn.setNormalCaption("Clear");
            resetButn.setAlternateName("Reset Form Fields");
            page.addFormField(resetButn); // Add to Page

            d.add(page);

            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[0]);

            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (Exception e)
        {
            System.out.println("Usage : java FormFieldsCreationDemo " +
            "<input file path> ");
        }
    }
}
