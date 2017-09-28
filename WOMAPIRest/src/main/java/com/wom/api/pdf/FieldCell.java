package com.wom.api.pdf;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfWriter;


public class FieldCell implements PdfPCellEvent {
        PdfFormField formField;
        PdfWriter writer;
        int width;
       
        public FieldCell(PdfFormField field, int width, PdfWriter writer2){
                this.formField = field;
                this.width = width;
                this.writer = writer2;
        }
        public void cellLayout(PdfPCell cell, Rectangle rect,
                PdfContentByte[] canvas){
                try{
                        formField.setWidget(
                                new Rectangle(rect.getLeft(),
                                        rect.getBottom(),
                                        rect.getLeft()+width,
                                        rect.getTop()),
                                        PdfAnnotation
                                        .HIGHLIGHT_NONE);
                       
                        writer.addAnnotation(formField);
                }catch(Exception e){
                        System.out.println(e);
                }
        }
} 