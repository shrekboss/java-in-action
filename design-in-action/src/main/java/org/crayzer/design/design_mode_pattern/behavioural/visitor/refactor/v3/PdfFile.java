package org.crayzer.design.design_mode_pattern.behavioural.visitor.refactor.v3;

public class PdfFile extends ResourceFile {
    public PdfFile(String filePath) {
        super(filePath);
    }

    @Override
    public ResourceFileType getType() {
        return ResourceFileType.PDF;
    }

    //...
}
