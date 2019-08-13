/*
 * Decompiled with CFR 0.145.
 */
package sun.util.locale;

public class LocaleSyntaxException
extends Exception {
    private static final long serialVersionUID = 1L;
    private int index = -1;

    public LocaleSyntaxException(String string) {
        this(string, 0);
    }

    public LocaleSyntaxException(String string, int n) {
        super(string);
        this.index = n;
    }

    public int getErrorIndex() {
        return this.index;
    }
}

