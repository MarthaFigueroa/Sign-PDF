import com.spire.pdf.*;

public class ManyPagesToOneSheet {
	public static void main(String[] args) {
		String input="data/ManyPagesWithWrapText.pdf";
		String output = "output/ManyPagesToOneSheet.xlsx";
		//Load Pdf document
		PdfDocument pdf = new PdfDocument();
		pdf.loadFromFile(input);
		//Convert Pdf document with multi pages to one sheet
		pdf.getConvertOptions().setConvertToOneSheet(true);
		//Save to Excel
		pdf.saveToFile(output, FileFormat.XLSX);
	}
}
