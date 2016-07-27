package com.example.elviscoa.muqrsrs.Activity;

/**
 * Created by soluciones on 7/24/2016.
 */
import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import com.example.elviscoa.muqrsrs.R;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class ExtractPageContent {

    /** The original PDF that will be parsed. */
    public static final String PREFACE = "resources/pdfs/preface.pdf";
    /** The resulting text file. */
    public static final String RESULT = "results/part4/chapter15/preface.txt";

    /**
     * Parses a PDF to a plain text file.
     * @param pdf the original PDF
     * @param txt the resulting text
     * @throws IOException
     */
    public void parsePdf(String pdf, String txt) throws IOException {
        PdfReader reader = new PdfReader(pdf);
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        PrintWriter out = new PrintWriter(new FileOutputStream(txt));
        TextExtractionStrategy strategy;
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
            out.println(strategy.getResultantText());
        }
        reader.close();
        out.flush();
        out.close();
    }

    public void read (File INPUTFILE,Context context){
        try {
            PdfReader reader = new PdfReader("android.resource://" + context.getPackageName() + "/" + R.raw.srs);
            int n = reader.getNumberOfPages();
            String str=PdfTextExtractor.getTextFromPage(reader, 2); //Extracting the content from a particular page.
            System.out.println(str);
            reader.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Main method.
     * @param    args    no arguments needed
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        new ExtractPageContent().parsePdf(PREFACE, RESULT);
    }
}