package org.crayzer.design.design_mode_pattern.behavioural.visitor.original;

public class WordFile extends ResourceFile {
    public WordFile(String filePath) {
        super(filePath);
    }

    @Override
    public void extract2txt() {
        //...
        System.out.println("Extract WORD.");
    }
}
