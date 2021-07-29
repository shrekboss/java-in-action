package org.crayzer.design.design_mode_pattern.behavioural.visitor.refactor.v2;

public abstract class ResourceFile {
    protected String filePath;
    public ResourceFile(String filePath) {
        this.filePath = filePath;
    }
    public abstract void accept(Visitor vistor);
}

//...PPTFile、WordFile跟PdfFile类似，这里就省略了...



