//---------------------------------------------------------------------------------------
// Copyright (c) 2001-2021 by PDFTron Systems Inc. All Rights Reserved.
// Consult legal.txt regarding legal and license information.
//---------------------------------------------------------------------------------------

import com.pdftron.common.PDFNetException;
import com.pdftron.pdf.Convert;
import com.pdftron.pdf.DocumentConversion;
import com.pdftron.pdf.PDFDoc;
import com.pdftron.pdf.PDFNet;
import com.pdftron.pdf.OfficeToPDFOptions;
import com.pdftron.sdf.SDFDoc;

//---------------------------------------------------------------------------------------
// The following sample illustrates how to use the PDF::Convert utility class 
// to convert MS Office files to PDF and replace templated tags present in the document
// with content supplied via json
//
// For a detailed specification of the template format and supported features,
// see: https://www.pdftron.com/documentation/core/guides/generate-via-template/data-model/
//
// This conversion is performed entirely within the PDFNet and has *no* external or
// system dependencies dependencies -- Conversion results will be the same whether
// on Windows, Linux or Android.
//
// Please contact us if you have any questions. 
//---------------------------------------------------------------------------------------
public class OfficeTemplateTest {

    static String input_path = "../../TestFiles/";
    static String output_path = "../../TestFiles/Output/";
    static String input_filename = "SYH_Letter.docx";
    static String output_filename = "SYH_Letter.pdf";

    public static void main(String[] args) {
        PDFNet.initialize(PDFTronLicense.Key());
        PDFNet.setResourcesPath("../../../Resources");

        try {
            OfficeToPDFOptions options = new OfficeToPDFOptions();

            String json = new StringBuilder()
                .append("{\"dest_given_name\": \"Janice N.\", \"dest_street_address\": \"187 Duizelstraat\", \"dest_surname\": \"Symonds\", \"dest_title\": \"Ms.\", \"land_location\": \"225 Parc St., Rochelle, QC \",")
                .append("\"lease_problem\": \"According to the city records, the lease was initiated in September 2010 and never terminated\", \"logo\": {\"image_url\": \"" + input_path + "logo_red.png\", \"width\" : 64, \"height\" : 64},")
                .append("\"sender_name\": \"Arnold Smith\"}").toString();

            options.setTemplateParamsJson(json);

            // perform the conversion with content dictionary
            PDFDoc pdfdoc = new PDFDoc();
            Convert.officeToPdf(pdfdoc, input_path + input_filename, options);

            // save the result
            pdfdoc.save(output_path + output_filename, SDFDoc.SaveMode.INCREMENTAL, null);
            // output PDF pdfdoc
        }
            catch (PDFNetException e) {
            e.printStackTrace();
            System.out.println(e);
        }

        // And we're done!
        System.out.println("Done conversion " + output_path + output_filename);

        PDFNet.terminate();
    }

}
