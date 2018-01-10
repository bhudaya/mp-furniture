package centmp.depotmebel.adminvendor.util;

import java.io.FileOutputStream;
import java.util.HashMap;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfPod {
	
	private static Font fontHeaderCent = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
	private static Font fontCommonBold = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
	private static Font fontCommonNormal = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL);
	private static Font fontTable1Bold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
	private static Font fontTable1Normal = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
	
	@SuppressWarnings("unchecked")
	public static String invoice(HashMap<String, Object> hm){
		try {
			String filepath = (String) hm.get("filepath");
			String filename = (String) hm.get("filename");
			String orderNo = (String) hm.get("orderNo");
			String customerName = (String) hm.get("customerName");
			String customerEmail = (String) hm.get("customerEmail");
			String customerPhone = (String) hm.get("customerPhone");
			String address = (String) hm.get("address");
			String province = (String) hm.get("province");
			String city = (String) hm.get("city");
			String postalCode = (String) hm.get("postalCode");
			java.util.List<String[]> itemList = (java.util.List<String[]>) hm.get("itemList");
			String amountItem = (String) hm.get("amountItem");
			String amountVoucher = (String) hm.get("amountVoucher");
			String amountTotal = (String) hm.get("amountTotal");
			
			Document document = new Document(PageSize.A4.rotate());
	        PdfWriter.getInstance(document, new FileOutputStream(filepath + filename));
	        document.open();
	        
	        Paragraph p = new Paragraph("CENTSmile", fontHeaderCent);
	        document.add(p);
	        
	        Paragraph pOrderNo = new Paragraph("Nomor Pemesanan: "+orderNo, fontCommonBold);
	        document.add(pOrderNo);
	        
	        PdfPTable table1 = new PdfPTable(3);
	        table1.setWidthPercentage(100);
	        table1.setSpacingBefore(30);
	        
	        PdfPCell cellHeader1 = new PdfPCell();
	        cellHeader1.addElement(new Phrase("Informasi Pemesan", fontCommonBold));
	        cellHeader1.setBorder(0);
	        
	        PdfPCell cellTengah = new PdfPCell();
	        cellTengah.setBorder(0);
	        
	        PdfPCell cellHeader2 = new PdfPCell();
	        cellHeader2.addElement(new Phrase("Alamat Pengiriman", fontCommonBold));
	        cellHeader2.setBorder(0);
	        
	        table1.addCell(cellHeader1);
	        table1.addCell(cellTengah);
	        table1.addCell(cellHeader2);
	        
	        PdfPCell cellPemesan = new PdfPCell();
	        Chunk chunkPemesan = new Chunk();
	        chunkPemesan.append(customerName);
	        chunkPemesan.append(""+Chunk.NEWLINE);
	        chunkPemesan.append(customerEmail);
	        chunkPemesan.append(""+Chunk.NEWLINE);
	        chunkPemesan.append(customerPhone);
	        chunkPemesan.setFont(fontCommonNormal);
	        cellPemesan.setBorder(0);
	        cellPemesan.addElement(chunkPemesan);
	        
	        PdfPCell cellPengirim = new PdfPCell();
	        Chunk chunkPengirim = new Chunk();
	        chunkPengirim.append(address);
	        chunkPengirim.append(""+Chunk.NEWLINE);
	        chunkPengirim.append(city+" - "+province+", "+postalCode);
	        chunkPengirim.setFont(fontCommonNormal);
	        cellPengirim.setBorder(0);
	        cellPengirim.addElement(chunkPengirim);
	        
	        table1.addCell(cellPemesan);
	        table1.addCell(cellTengah);
	        table1.addCell(cellPengirim);
	        document.add(table1);
	        
	        Paragraph lblItemBelanja = new Paragraph("Item Belanja Anda", fontTable1Bold);
	        lblItemBelanja.setSpacingBefore(30);
	        document.add(lblItemBelanja);
	        
	        //Table Item Belanja
	        float[] columnWidths = {1, 10, 4, 6};
	        PdfPTable tblItems = new PdfPTable(columnWidths);
	        tblItems.setWidthPercentage(100);
	        tblItems.setSpacingBefore(5);
	        tblItems.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
	        tblItems.getDefaultCell().setBorderWidthTop(0);
	        tblItems.getDefaultCell().setBorderWidthLeft(0);
	        tblItems.getDefaultCell().setBorderWidthRight(0);
	        tblItems.getDefaultCell().setBorderWidthBottom(1);
	        tblItems.getDefaultCell().setFixedHeight(20);
	        tblItems.addCell("#");
	        tblItems.addCell("Item Product");
	        tblItems.addCell("Jumlah");
	        tblItems.addCell("Sub Total");
	        
	        tblItems.getDefaultCell().setBackgroundColor(GrayColor.WHITE);
	        tblItems.getDefaultCell().setBorder(0);
	        for(int i=0;i<itemList.size();i++){
	        	String[] item = itemList.get(i);
	        	Chunk chunkProd = new Chunk();
	        	chunkProd.append(item[0]);//Product Name
	        	chunkProd.append(""+Chunk.NEWLINE);
	        	if(!item[1].isEmpty()) chunkProd.append("Pesan: "+item[1]); //Custom Message
	        	PdfPCell cellProd = new PdfPCell();
	        	cellProd.addElement(chunkProd);
	        	cellProd.setBorder(0);
	        	
	        	tblItems.getDefaultCell().setBorderWidthTop(0);
	        	tblItems.addCell(""+(i+1));
	            tblItems.addCell(cellProd);
	            tblItems.addCell(item[2]); //Quantity
	            tblItems.addCell(item[3]); //Amount
	        }
	        
	        PdfPCell cellSubTot = new PdfPCell();
	        cellSubTot.setColspan(3);
	        cellSubTot.setBorder(0);
	        cellSubTot.setBackgroundColor(new BaseColor(0));
	        cellSubTot.addElement(new Chunk("Sub Total", fontTable1Bold));
	        tblItems.getDefaultCell().setBackgroundColor(new GrayColor(0.9f));
	        tblItems.addCell(cellSubTot);
	        tblItems.addCell(amountItem);
	        
	        PdfPCell cellVoucher = new PdfPCell();
	        cellVoucher.setColspan(3);
	        cellVoucher.setBorder(0);
	        cellVoucher.setBackgroundColor(new BaseColor(0));
	        cellVoucher.addElement(new Chunk("Diskon Voucher", fontTable1Bold));
	        tblItems.getDefaultCell().setBackgroundColor(new GrayColor(0.9f));
	        tblItems.addCell(cellVoucher);
	        tblItems.addCell("- "+amountVoucher);
	        
	        PdfPCell cellTotal = new PdfPCell();
	        cellTotal.setColspan(3);
	        cellTotal.setBorder(0);
	        cellTotal.setBackgroundColor(new BaseColor(0));
	        cellTotal.addElement(new Chunk("TOTAL", fontTable1Bold));
	        tblItems.getDefaultCell().setBackgroundColor(new GrayColor(0.9f));
	        tblItems.addCell(cellTotal);
	        tblItems.addCell(amountTotal);
	        
	        document.add(tblItems);
	        
	        Paragraph notes1 = new Paragraph("Catatan:", fontCommonBold);
	        notes1.setSpacingBefore(20);
	        document.add(notes1);
	        
	        List list = new List(List.UNORDERED);
	        list.add(new ListItem("Harga diatas sudah termasuk 10%", fontTable1Normal));
	        list.add(new ListItem("Pengerjaan sesuai dengan pesanan. Kesalahan pada detil pesanan diluar tanggung jawab kami.", fontTable1Normal));
	        document.add(list);
	        
	        document.close();
	        
	        return filepath+filename;
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	

}
