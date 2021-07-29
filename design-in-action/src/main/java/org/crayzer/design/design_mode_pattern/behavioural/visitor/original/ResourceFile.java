package org.crayzer.design.design_mode_pattern.behavioural.visitor.original;

public abstract class ResourceFile {
    protected String filePath;

    public ResourceFile(String filePath) {
        this.filePath = filePath;
    }

    public abstract void extract2txt();
}

