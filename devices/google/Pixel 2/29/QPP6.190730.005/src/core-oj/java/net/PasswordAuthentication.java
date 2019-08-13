/*
 * Decompiled with CFR 0.145.
 */
package java.net;

public final class PasswordAuthentication {
    private char[] password;
    private String userName;

    public PasswordAuthentication(String string, char[] arrc) {
        this.userName = string;
        this.password = (char[])arrc.clone();
    }

    public char[] getPassword() {
        return this.password;
    }

    public String getUserName() {
        return this.userName;
    }
}

