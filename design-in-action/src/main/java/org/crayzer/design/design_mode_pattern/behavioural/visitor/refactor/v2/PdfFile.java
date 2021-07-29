package org.crayzer.design.design_mode_pattern.behavioural.visitor.refactor.v2;

public class PdfFile extends ResourceFile {
    public PdfFile(String filePath) {
        super(filePath);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    //...
}
