package org.crayzer.design.design_mode_pattern.behavioural.visitor.refactor.v3;

public class WordFile extends ResourceFile {
    public WordFile(String filePath) {
        super(filePath);
    }

    @Override
    public ResourceFileType getType() {
        return ResourceFileType.WORD;
    }

    //...
}
