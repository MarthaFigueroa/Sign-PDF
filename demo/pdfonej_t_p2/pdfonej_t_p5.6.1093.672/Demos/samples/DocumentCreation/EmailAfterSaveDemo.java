import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfEmailHandler;
import com.gnostice.pdfone.PdfEmailSettings;
import com.gnostice.pdfone.PdfException;

public class EmailAfterSaveDemo implements PdfEmailHandler
{
    public static void main(String[] args) throws IOException,
        PdfException
    {
        PdfDocument d = new PdfDocument();
//        d.load("InputDocument.pdf");
         
        d.writeText("EmailAfterSave Demo", 10, 30);
        
        // Method 1: Set your smtp settings 
//        PdfEmailSettings emailSettings = d.getEmailSettings();
//        emailSettings.setHost("smtp.gmail.com");
//        emailSettings.setPortNumber(465);
//        emailSettings.setSSLEnabled(true);
//        emailSettings.setAuthenticationRequired(true);
        
        // Method 2: Get the emailSettings with the GMail settings applied
        PdfEmailSettings emailSettings = d
            .getEmailSettings(PdfEmailSettings.EMAIL_SETTINGS_TYPE_GMAIL);
        
        emailSettings.setUserName("SENDER_USER_NAME");
        emailSettings.setPassword("SENDER_PASSWORD");
        emailSettings.setFromAddress("SENDER_EMAIL_ADDRESS");
        emailSettings.setFromName("SENDER_NAME");
//         emailSettings.setDebugEnable(true);
         emailSettings.setSubject("EmailAfterSave Demo");
         emailSettings.setBody("<p>This is a demo for <b>EmailAfterSave</b> feature!</p>");
         emailSettings.addRecipient(PdfEmailSettings.RECIPIENT_TYPE_TO, "RECIPIENT_EMAIL_ADDRESS1");
//         emailSettings.addRecipient(PdfEmailSettings.RECIPIENT_TYPE_TO, "RECIPIENT_EMAIL_ADDRESS2");
//         emailSettings.addAttachment("ATTACHMENT_FILE", "");
         
         d.setEmailHandler(new EmailAfterSaveDemo());
         
         d.setEmailAfterSave(true);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        d.save(outputStream);
         d.save("EmailAfterSaveDemoOutput.pdf");
         d.close();
    }
    
    public void beforeEmail(PdfDocument d,
        PdfEmailSettings emailSettings)
    {
    }

    public void onEmailError(PdfDocument d,
        PdfEmailSettings emailSettings, Throwable cause)
    {
        cause.printStackTrace();
    }

    public void afterEmail(PdfDocument d,
        PdfEmailSettings emailSettings, boolean emailSent)
    {
        if (emailSent)
        {
            System.out.println("Email has been sent successfully!");
        }
        else
        {
            System.out.println("Email could not be sent");
        }
    }
}
