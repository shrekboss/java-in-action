package org.crayzer.design.design_mode_pattern.behavioural.visitor.refactor.v3;

public abstract class ResourceFile {
    protected String filePath;
    public ResourceFile(String filePath) {
        this.filePath = filePath;
    }
    public abstract ResourceFileType getType();
}

