package org.crayzer.design.design_mode_pattern.behavioural.visitor.refactor.v1;

public class PPTFile extends ResourceFile {
    public PPTFile(String filePath) {
        super(filePath);
    }
    //...

    @Override
    public void accept(Extractor extractor) {
        extractor.extract2txt(this);
    }

    @Override
    public void accept(Compressor compressor) {
        compressor.compress(this);
    }
}
