package org.crayzer.design.design_mode_pattern.behavioural.visitor.refactor.v1;

public class WordFile extends ResourceFile {
    public WordFile(String filePath) {
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
