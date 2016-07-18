package com.example.elviscoa.muqrsrs.Library;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;

import com.example.elviscoa.muqrsrs.Class.SixXTrilogy;
import com.example.elviscoa.muqrsrs.Class.Util;
import com.example.elviscoa.muqrsrs.Database.Database;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.elviscoa.muqrsrs.R;
/**
 * Created by soluciones on 7/14/2016.
 */
public class GenerarPDF {
    private static Font FONTF = new Font(Font.FontFamily.TIMES_ROMAN, 12);
    private static final String NOMBRE_DIRECTORIO = "MUQCSRS";
    private static Font bigFont = FontFactory.getFont(FontFactory.HELVETICA, "Windows-1254", 12, Font.BOLD);
    private static Font smallFont = FontFactory.getFont(FontFactory.HELVETICA, "Windows-1254", 8, Font.NORMAL);

    //SixXTrilogy
    private static String patientID;
    private static String planID;
    private static String energy;
    private static String dzero;
    private static String dosisPrescrita;
    private static String normalizacion;
    private static String pesoMaximoDosis;
    private static ArrayList<SixXTrilogy> sixXTrilogyArrayList;

    private static Font BOLDFONT = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);

    public static String getFileName () {
        return new SimpleDateFormat("'MUQCSRS'yyyyMMddhhmm'.pdf'").format(new Date());
    }

    public static void GenerarPDF(Context context, Database dbHandler, String dateact) {
        try {
            Document document = new Document(PageSize.LETTER,20,20,20,20);
            PdfWriter.getInstance(document, new FileOutputStream(crearFichero()));
            fillGereralData(dbHandler, dateact);
            fillArcData(dbHandler,dateact);
            document.open();
            addMetaData(document);
            addImagePDF(document,context);
            addTitlePage(document);
            addContent(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    private static void addMetaData(Document document) {
        document.addTitle("MUQCSRS");
        document.addSubject("MUQCSRS Reference");
        document.addKeywords("PDF, MUQCSRS");
        document.addAuthor("Elvis Coa");
        document.addCreator("Elvis Coa");
    }

    private static void addTitlePage(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("MU QC SRS Report", BOLDFONT));
        addEmptyLine(preface, 1);
        document.add(preface);

    }

    private static void addImagePDF (Document document,Context context){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.logopdf);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitMapData = stream.toByteArray();

        try {
            Image img= Image.getInstance(bitMapData);
            img.scaleAbsolute(550f,150f);
            document.add(img);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    private static void addContent(Document document) throws DocumentException {

        Paragraph paragraph = new Paragraph();;

        Font underlineFont = new Font(Font.FontFamily.HELVETICA,12,Font.UNDERLINE);
        paragraph.add(new Chunk("Patient ID:"));
        paragraph.add(new Chunk(patientID+"\n",underlineFont));
        paragraph.add(new Chunk("Plan ID:"));
        paragraph.add(new Chunk(planID+"\n",underlineFont));
        addEmptyLine(paragraph, 1);
        paragraph.add(new Chunk("D'o (cGy/MU):"));
        paragraph.add(new Chunk("  "+dzero+"  ", underlineFont));
        paragraph.add(new Chunk("; Dose per fraction:"));
        paragraph.add(new Chunk("  "+dosisPrescrita+"  ",underlineFont));
        paragraph.add(new Chunk("; % Norm:"));
        paragraph.add(new Chunk("  "+normalizacion+"  \n",underlineFont));

        paragraph.add(new Chunk("Energy (MV):"));
        paragraph.add(new Chunk("  "+energy+"  ", underlineFont));
        paragraph.add(new Chunk("; Total Weight:"));
        paragraph.add(new Chunk("  "+pesoMaximoDosis+"  ", underlineFont));
        //add a table
        addEmptyLine(paragraph, 2);
        createFirstTable(paragraph);
        addEmptyLine(paragraph, 1);
        paragraph.add(new Paragraph("MU QC SRS"));
        paragraph.add(new Paragraph("Dosimetric"));
        paragraph.add(new Paragraph("Parameters"));

        // add a table
        addEmptyLine(paragraph, 1);
        createParametersTable(paragraph);
        addEmptyLine(paragraph, 3);
        paragraph.add(new Chunk("Prepared by:"));
        paragraph.add(new Chunk("              \n", underlineFont));

        // now add all this to the document
        document.add(paragraph);
    }

    private static void createFirstTable(Paragraph paragraph) throws BadElementException {
        PdfPTable table = new PdfPTable(10);

        PdfPCell cellHeader = new PdfPCell(new Phrase("Arc ID"));
        cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellHeader);

        cellHeader = new PdfPCell(new Phrase("Arc 1"));
        cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellHeader);

        cellHeader = new PdfPCell(new Phrase("Arc 2"));
        cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellHeader);

        cellHeader = new PdfPCell(new Phrase("Arc 3"));
        cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellHeader);

        cellHeader = new PdfPCell(new Phrase("Arc 4"));
        cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellHeader);

        cellHeader = new PdfPCell(new Phrase("Arc 5"));
        cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellHeader);

        cellHeader = new PdfPCell(new Phrase("Arc 6"));
        cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellHeader);

        cellHeader = new PdfPCell(new Phrase("Arc 7"));
        cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellHeader);

        cellHeader = new PdfPCell(new Phrase("Arc 8"));
        cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellHeader);

        cellHeader = new PdfPCell(new Phrase("Arc 9"));
        cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellHeader);
        table.setHeaderRows(1);

        table.addCell("Cone (mm)");
        for (int i=0;i<9;i++){
            table.addCell(sixXTrilogyArrayList.get(i).getCono());
        }
        table.addCell("Depth (cm)");
        for (int i=0;i<9;i++){
            table.addCell(sixXTrilogyArrayList.get(i).getProfundidad());
        }
        table.addCell("Weight Factor");
        for (int i=0;i<9;i++){
            table.addCell(sixXTrilogyArrayList.get(i).getPeso_del_arco());
        }
        table.addCell("MU TPS");
        for (int i=0;i<9;i++){
            table.addCell(sixXTrilogyArrayList.get(i).getMu_tps());
        }
        paragraph.add(table);

    }

    private static void createParametersTable(Paragraph paragraph) throws BadElementException {
        PdfPTable table = new PdfPTable(10);

        PdfPCell cellHeader = new PdfPCell(new Phrase("Arc ID"));
        cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellHeader);

        cellHeader = new PdfPCell(new Phrase("Arc 1"));
        cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellHeader);

        cellHeader = new PdfPCell(new Phrase("Arc 2"));
        cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellHeader);

        cellHeader = new PdfPCell(new Phrase("Arc 3"));
        cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellHeader);

        cellHeader = new PdfPCell(new Phrase("Arc 4"));
        cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellHeader);

        cellHeader = new PdfPCell(new Phrase("Arc 5"));
        cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellHeader);

        cellHeader = new PdfPCell(new Phrase("Arc 6"));
        cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellHeader);

        cellHeader = new PdfPCell(new Phrase("Arc 7"));
        cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellHeader);

        cellHeader = new PdfPCell(new Phrase("Arc 8"));
        cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellHeader);

        cellHeader = new PdfPCell(new Phrase("Arc 9"));
        cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellHeader);
        table.setHeaderRows(1);

        table.addCell("S(c,p)");
        for (int i=0;i<9;i++){
            table.addCell(sixXTrilogyArrayList.get(i).getOutputfactor());
        }
        table.addCell("Aver. TMR");
        for (int i=0;i<9;i++){
            table.addCell(sixXTrilogyArrayList.get(i).getTmr());
        }
        table.addCell("MU QC SRS");
        for (int i=0;i<9;i++){
            table.addCell(sixXTrilogyArrayList.get(i).getMu_qc_srs());
        }
        table.addCell("%Delta");
        for (int i=0;i<9;i++){
            Double a,b;
            a= Double.valueOf(sixXTrilogyArrayList.get(i).getMu_qc_srs());
            b= Double.valueOf(sixXTrilogyArrayList.get(i).getMu_tps());
            table.addCell(String.valueOf(new Util().roundThreeDecimals(((b - a) / a)*100)));
        }
        paragraph.add(table);

    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public static File crearFichero() throws IOException {
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null){
            fichero = new File(ruta, getFileName());
        }
        /**else{
         fichero = new File(actividad.getFilesDir(), NOMBRE_DOCUMENTO);
         }*/
        return fichero;
    }

    public static File getRuta() {

        // El fichero sera almacenado en un directorio dentro del directorio
        // Descargas
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            ruta = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    NOMBRE_DIRECTORIO);

            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {

                        return null;
                    }
                }
            }
        }
        return ruta;
    }

    private static void fillGereralData(Database dbHandler, String date){
        Cursor c = dbHandler.getGeneralData(date);
        if (c.moveToFirst()){
            do{
                Log.d("Database Gen", c.getString(0) + " " + c.getString(1)
                        + " " + c.getString(2) + " " + c.getString(3)
                        + " " + c.getString(4) + " " + c.getString(5)
                        + " " + c.getString(6) + " " + c.getString(7));
                patientID=c.getString(0);
                planID=c.getString(1);
                energy=c.getString(3);
                dzero=c.getString(4);
                dosisPrescrita=c.getString(5);
                normalizacion=c.getString(6);
                pesoMaximoDosis=c.getString(7);
            }while (c.moveToNext());
        }
        dbHandler.close();
    }

    private static void fillArcData(Database dbHandler, String date){
        Cursor c = dbHandler.getArc(date);
        sixXTrilogyArrayList= new ArrayList<SixXTrilogy>();
        if (c.moveToFirst()){
            do{
                Log.d("Database Arc", c.getString(0) + " " + c.getString(1)
                        + " " + c.getString(2) + " " + c.getString(3)
                        + " " + c.getString(4) + " " + c.getString(5)
                        + " " + c.getString(6));
                SixXTrilogy sixXTrilogy = new SixXTrilogy(c.getString(0),c.getString(1),c.getString(2),
                        c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7));
                sixXTrilogyArrayList.add(sixXTrilogy);
            }while (c.moveToNext());
        }
        dbHandler.close();
    }

}