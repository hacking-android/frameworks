/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

public class IllformedLocaleException
extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private int _errIdx;

    public IllformedLocaleException() {
        this._errIdx = -1;
    }

    public IllformedLocaleException(String string) {
        super(string);
        this._errIdx = -1;
    }

    public IllformedLocaleException(String charSequence, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        if (n < 0) {
            charSequence = "";
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(" [at index ");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append("]");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        stringBuilder.append((String)charSequence);
        super(stringBuilder.toString());
        this._errIdx = -1;
        this._errIdx = n;
    }

    public int getErrorIndex() {
        return this._errIdx;
    }
}

