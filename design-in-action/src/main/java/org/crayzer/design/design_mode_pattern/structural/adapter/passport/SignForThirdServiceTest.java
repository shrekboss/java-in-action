package org.crayzer.design.design_mode_pattern.structural.adapter.passport;

public class SignForThirdServiceTest extends SignService {
    public static void main(String[] args) {
        SignForThirdService third = new SignForThirdService();

        System.out.println(third.loginForQQ("sdfgdgfwresdf9123sdf"));
    }
}
