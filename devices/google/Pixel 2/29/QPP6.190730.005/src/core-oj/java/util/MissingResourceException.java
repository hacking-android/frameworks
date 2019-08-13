/*
 * Decompiled with CFR 0.145.
 */
package java.util;

public class MissingResourceException
extends RuntimeException {
    private static final long serialVersionUID = -4876345176062000401L;
    private String className;
    private String key;

    public MissingResourceException(String string, String string2, String string3) {
        super(string);
        this.className = string2;
        this.key = string3;
    }

    MissingResourceException(String string, String string2, String string3, Throwable throwable) {
        super(string, throwable);
        this.className = string2;
        this.key = string3;
    }

    public String getClassName() {
        return this.className;
    }

    public String getKey() {
        return this.key;
    }
}

