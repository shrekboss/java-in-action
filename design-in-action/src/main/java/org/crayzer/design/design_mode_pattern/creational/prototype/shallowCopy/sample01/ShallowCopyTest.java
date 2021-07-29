package org.crayzer.design.design_mode_pattern.creational.prototype.shallowCopy.sample01;

public class ShallowCopyTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        Email email = new Email();
        Email copyEmail = (Email) email.clone();

        System.out.println("email == copyEmail ?");
        System.out.println(email == copyEmail);

        System.out.println("email.getAttachment() == copyEmail.getAttachment() ?");
        System.out.println(email.getAttachment() == copyEmail.getAttachment());

    }
}
