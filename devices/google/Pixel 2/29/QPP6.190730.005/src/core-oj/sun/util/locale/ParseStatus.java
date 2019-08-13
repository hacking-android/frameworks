/*
 * Decompiled with CFR 0.145.
 */
package sun.util.locale;

public class ParseStatus {
    int errorIndex;
    String errorMsg;
    int parseLength;

    public ParseStatus() {
        this.reset();
    }

    public int getErrorIndex() {
        return this.errorIndex;
    }

    public String getErrorMessage() {
        return this.errorMsg;
    }

    public int getParseLength() {
        return this.parseLength;
    }

    public boolean isError() {
        boolean bl = this.errorIndex >= 0;
        return bl;
    }

    public void reset() {
        this.parseLength = 0;
        this.errorIndex = -1;
        this.errorMsg = null;
    }
}

