package org.crayzer.design.design_mode_pattern.behavioural.visitor.refactor.v3;

public class PPTFile extends ResourceFile {
    public PPTFile(String filePath) {
        super(filePath);
    }

    @Override
    public ResourceFileType getType() {
        return ResourceFileType.PPT;
    }

    //...
}
