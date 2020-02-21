package ru.prox.utils;

public class SocialUltils {
    public String getLoginFromEmail(String email) {
        char[] trueLogin = new char[email.indexOf("@")];
        email.getChars(0, email.indexOf("@"), trueLogin,0);
        return new String(trueLogin);
    }
}
