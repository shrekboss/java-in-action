package org.crayzer.design.design_mode_pattern.creational.prototype.deepClone.sample01;

public class DeepTest {
    public static void main(String[] args) {
        Email email = new Email();
        Email copyEmail = (Email) email.deepClone();

        System.out.println("email == copyEmail ?");
        System.out.println(email == copyEmail);

        System.out.println("email.getAttachment() == copyEmail.getAttachment() ?");
        System.out.println(email.getAttachment() == copyEmail.getAttachment());
    }
}
