package org.crayzer.design.design_mode_pattern.behavioural.visitor.original;

public class PdfFile extends ResourceFile {
    public PdfFile(String filePath) {
        super(filePath);
    }

    @Override
    public void extract2txt() {
        //...
        System.out.println("Extract PDF.");
    }
}
