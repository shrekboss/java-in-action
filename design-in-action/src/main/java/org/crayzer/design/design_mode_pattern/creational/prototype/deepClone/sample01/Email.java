package org.crayzer.design.design_mode_pattern.creational.prototype.deepClone.sample01;

import java.io.*;

public class Email implements Serializable {

    private static final long serialVersionUID = -9015110121399696950L;

    private Attachment attachment;

    public Email() {
        this.attachment = new Attachment();
    }

    protected Object deepClone() {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;

        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);

            oos.writeObject(this);
            oos.flush();

            bais = new ByteArrayInputStream(baos.toByteArray());
            ois = new ObjectInputStream(bais);

            return ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (bais != null) {
                    bais.close();
                }
                if (oos != null) {
                    oos.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Attachment getAttachment() {
        return attachment;
    }
}
