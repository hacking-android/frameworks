/*
 * Decompiled with CFR 0.145.
 */
package java.text;

public class ParseException
extends Exception {
    private static final long serialVersionUID = 2703218443322787634L;
    private int errorOffset;

    public ParseException(String string, int n) {
        super(string);
        this.errorOffset = n;
    }

    public int getErrorOffset() {
        return this.errorOffset;
    }
}

