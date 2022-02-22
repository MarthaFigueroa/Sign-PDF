import com.spire.pdf.*;

public class WrapTextToExcel {
    public static void main(String[] args) {
        String input = "data/WrapText.pdf";
        String output = "output/WrapTextToExcel.xlsx";
        //Load Pdf document
        PdfDocument pdf = new PdfDocument();
        pdf.loadFromFile(input);
        //Set lines determine cell
        pdf.getConvertOptions().setLinesDetermineCell(true);
        //Save to Excel file
        pdf.saveToFile(output, FileFormat.XLSX);
    }
}
