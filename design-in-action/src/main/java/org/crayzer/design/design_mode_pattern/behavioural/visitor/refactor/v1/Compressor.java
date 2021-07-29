package org.crayzer.design.design_mode_pattern.behavioural.visitor.refactor.v1;

public class Compressor {

    public void compress(PPTFile pptFile) {
        //...
        System.out.println("Compress PPT.");
    }

    public void compress(PdfFile pdfFile) {
        //...
        System.out.println("Compress PDF.");
    }

    public void compress(WordFile wordFile) {
        //...
        System.out.println("Compress WORD.");
    }
}
