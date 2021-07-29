package org.crayzer.design.design_mode_pattern.creational.prototype.shallowCopy.sample01;

public class Email implements Cloneable {
    private Attachment attachment;

    public Email() {
        this.attachment = new Attachment();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Attachment getAttachment() {
        return attachment;
    }
}
