//---------------------------------------------------------------------------------------
// Copyright (c) 2001-2021 by PDFTron Systems Inc. All Rights Reserved.
// Consult legal.txt regarding legal and license information.
//---------------------------------------------------------------------------------------

import com.pdftron.common.PDFNetException;
import com.pdftron.pdf.*;
import com.pdftron.sdf.Obj;
import com.pdftron.sdf.ObjSet;
import com.pdftron.sdf.SDFDoc;

//---------------------------------------------------------------------------------------
// The following sample illustrates how to convert AdvancedImaging documents to PDF format using
// the AdvancedImaging class.
// 
// 'pdftron.PDF.AdvancedImaging' is an optional PDFNet Add-On utility class that can be 
// used to convert AdvancedImaging documents into PDF documents by using an external module (AdvancedImaging).
//
// AdvancedImaging modules can be downloaded from http://www.pdftron.com/pdfnet/downloads.html.
//---------------------------------------------------------------------------------------
public class AdvancedImagingTest {
	
    public static void main(String[] args)
	{
		System.getProperty("sun.arch.data.model");

		String input_file_name = "xray.dcm";
		String output_file_name = "xray.dcm.pdf";
		if (args.length != 0)
		{
			input_file_name = args[0];
			output_file_name = input_file_name + ".pdf";
		}
		PDFNet.initialize(PDFTronLicense.Key());
		try {
			PDFNet.addResourceSearchPath("../../../Lib/");
			if(!AdvancedImagingModule.isModuleAvailable())
			{
                System.out.println();
                System.out.println("Unable to run AdvancedImagingTest: PDFTron SDK AdvancedImaging module not available.");
                System.out.println("---------------------------------------------------------------");
                System.out.println("The AdvancedImaging module is an optional add-on, available for download");
                System.out.println("at http://www.pdftron.com/. If you have already downloaded this");
                System.out.println("module, ensure that the SDK is able to find the required files");
                System.out.println("using the PDFNet::AddResourceSearchPath() function." );
                System.out.println();
			}
		} catch (PDFNetException e) {
            System.out.println("AdvancedImaging module not available, error:");
            e.printStackTrace();
            System.out.println(e);
        }

        // Relative path to the folder containing test files.
        String input_path = "../../TestFiles/AdvancedImaging/";
        String output_path = "../../TestFiles/Output/";
        String outputFile;
        boolean printerInstalled = false;

        System.out.println("-------------------------------------------------");
		
        // Convert an AdvancedImaging document to pdf (Windows platform only)
        try {
			System.out.println("Converting DICOM document to PDF");
			
			PDFDoc doc = new PDFDoc();
			
			AdvancedImagingConvertOptions opts = new AdvancedImagingConvertOptions();
			Convert.fromDICOM(doc, input_path + input_file_name, opts);
			
			outputFile = output_path + output_file_name;
			doc.save(outputFile, SDFDoc.SaveMode.LINEARIZED, null);
			// output PDF doc
			System.out.println("Result saved in " + outputFile);
			doc.close();
        } catch (PDFNetException e) {
            System.out.println("Unable to convert DICOM document, error:");
            e.printStackTrace();
            System.out.println(e);
        }

        System.out.println("Done.");
        PDFNet.terminate();
    }
}
