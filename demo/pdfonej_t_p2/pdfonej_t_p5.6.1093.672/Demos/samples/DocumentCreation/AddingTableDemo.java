import java.awt.Color;
import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfCell;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfImage;
import com.gnostice.pdfone.PdfMeasurement;
import com.gnostice.pdfone.PdfPage;
import com.gnostice.pdfone.PdfTable;
import com.gnostice.pdfone.PdfTextFormatter;
import com.gnostice.pdfone.encodings.PdfEncodings;
import com.gnostice.pdfone.fonts.PdfFont;

/*
 * This program illustrates creation of a PdfDocument with PdfTable of
 * row*columns cells added to a page with diiferent cell attributes
 */
public class AddingTableDemo
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    // pass PdfFileName.pdf in arg[0] and image to be used in args[1]
    public static void main(String[] args) throws PdfException,
        IOException
    {
        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();

            /* Create a PdfPage instance with the PdfPage*/
            PdfPage page1 = new PdfPage();

            /*create a PdfFont object to write text with*/
            PdfFont cellFont1 = PdfFont.create("TimesNewRoman",
                PdfFont.PLAIN, 12, PdfEncodings.CP1252);
            /*set the color of the font*/
            cellFont1.setColor(Color.YELLOW);

            /*create a PdfFont object to write text with*/
            PdfFont cellFont2 = PdfFont.create("Helvetica", PdfFont.BOLD,
                10, PdfEncodings.CP1252);
            /*set the color of the font*/
            cellFont2.setColor(Color.GREEN);

            /* create a PdfFont object to write text with*/
            PdfFont cellFont3 = PdfFont.create("Courier", PdfFont.PLAIN
                | PdfFont.ITALIC, 12, PdfEncodings.CP1252);
            /*set the color of the font*/
            cellFont3.setColor(Color.RED);

            /*create another font which can be set to table */
            PdfFont tableFont = PdfFont.create("TimesNewRoman",
                PdfFont.BOLD, 15, PdfEncodings.CP1252);
            /*set the color of the font*/
            tableFont.setColor(Color.BLUE);

            PdfTable table1 = new PdfTable(4, new double[] { 100, 100,
                100, 100 }, 30, PdfMeasurement.MU_POINTS);
            table1.setCellLeftMargin(5);
            table1.setCellRightMargin(5);
            table1.setCellTopMargin(5);
            table1.setCellBottomMargin(5);

            /*set the font of the table*/
             table1.setFont(tableFont);

            /*
             * add cells with different attributes to the table using
             * addCell method empty cell with col span 4 and row span
             * 1
             */
             table1.addCell(1, 4);

            /* empty cell with only back ground color set*/
            table1.addCell(1, 4, Color.LIGHT_GRAY);

            /* cell with text*/
            table1
                .addCell(
                    2,
                    4,
                    "Font used will be table font if "
                        + "set else page font if set else document font else default font");

             /*cell with text and background color set*/
            table1.addCell(1, 1, "Cell Text", Color.LIGHT_GRAY);

             /*cell with text, background color and textformat*/
            table1.addCell(1, 4, "Cell Text", Color.LIGHT_GRAY,
                cellFont1, PdfTextFormatter.CENTER);
            /* cell with text, textformat*/
            table1.addCell(1, 1, "Right Formatted",
                PdfTextFormatter.RIGHT);
            /* cell with text, custom font and background color*/
            table1.addCell(1, 1, "Cell Text with font2", cellFont2,
                Color.LIGHT_GRAY);

            /* cell with text, custom font and alignment*/
            table1.addCell(1, 1, "Cell Text with font3", cellFont3,
                PdfTextFormatter.LEFT);
            /* cell with text, background color, and format*/
            table1.addCell(1, 1,
                "Cell Text with font1 and left formatted",
                Color.LIGHT_GRAY, cellFont1, PdfTextFormatter.LEFT);

/*             create a PdfCell object with attributes set to add it to
             table later
*/            PdfCell cellObj = new PdfCell(1, 4);
            cellObj.setText("Cell object Text");
            cellObj.setAlignment(PdfTextFormatter.LEFT_TO_RIGHT);
            cellObj.setFont(PdfFont.create("Arial", PdfFont.BOLD
                | PdfFont.ITALIC, 15, PdfEncodings.CP1252));
            cellObj.setBackgroundColor(Color.LIGHT_GRAY);
            cellObj.setCellBottomMargin(3);
            cellObj.setCellRightMargin(7);
            cellObj.setCellLeftMargin(2);
            cellObj.setCellTopMargin(2);

            /* adding a cell object*/
            table1.addCell(cellObj);

             /*cells with image added to them*/
            PdfImage cellImg = PdfImage.create(args[1]);

            /*add cell with image*/
            table1.addCell(2, 1, cellImg);

            /* text ,font, image, backgroundcolor, alignment*/
            table1.addCell(2, 1, "cell text with image", cellFont2,
                cellImg, Color.gray, PdfTextFormatter.CENTER);

            /*
             * text, font, image, backgroundcolor, text alignmet,
             * whether image shold fitincell
             */
            table1.addCell(2, 1, "cell text with image", cellFont1,
                    cellImg, Color.LIGHT_GRAY, PdfTextFormatter.CENTER,
                    false);

            /* add cell with text, image, color*/
            table1.addCell(2, 1, "cell text with image", cellImg,
                Color.LIGHT_GRAY, PdfTextFormatter.RIGHT);

            /* add cell with text, font, imagePath*/
            table1.addCell(2, 1, "cell text with image", tableFont,
                args[2]);

             /*add cell with text, font, imagePath*/
            table1.addCell(2, 1, "cell text with image", cellFont3,
                args[2]);

             /*add cell with text, and imagePath*/
            table1.addCell(2, 1, "cell text with image",
                args[2]);

            /*add table to page */
            page1.addTable(table1, tableFont, 100, 100);

            //add page to the document
            d.add(page1);

            //set the document to open after save
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[0]);

            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out
            .println("Usage : java AddingTableDemo "
                + "<output file path> <image file path 1> <image file path 2>");
            
        }
    }
}
