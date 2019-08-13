/*
 * Decompiled with CFR 0.145.
 */
package java.nio.charset;

public class IllegalCharsetNameException
extends IllegalArgumentException {
    private static final long serialVersionUID = 1457525358470002989L;
    private String charsetName;

    public IllegalCharsetNameException(String string) {
        super(String.valueOf(string));
        this.charsetName = string;
    }

    public String getCharsetName() {
        return this.charsetName;
    }
}

