/*
 * Decompiled with CFR 0.145.
 */
package java.nio.charset;

public class UnsupportedCharsetException
extends IllegalArgumentException {
    private static final long serialVersionUID = 1490765524727386367L;
    private String charsetName;

    public UnsupportedCharsetException(String string) {
        super(String.valueOf(string));
        this.charsetName = string;
    }

    public String getCharsetName() {
        return this.charsetName;
    }
}

