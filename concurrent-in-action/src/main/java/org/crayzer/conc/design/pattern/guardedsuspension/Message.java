package org.crayzer.conc.design.pattern.guardedsuspension;

public class Message{
    Integer id;
    String content;

    public Message(int id, String s) {
        this.id = id;
        this.content = s;
    }
}