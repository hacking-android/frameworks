/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.locale;

public class ParseStatus {
    int _errorIndex = -1;
    String _errorMsg = null;
    int _parseLength = 0;

    public int getErrorIndex() {
        return this._errorIndex;
    }

    public String getErrorMessage() {
        return this._errorMsg;
    }

    public int getParseLength() {
        return this._parseLength;
    }

    public boolean isError() {
        boolean bl = this._errorIndex >= 0;
        return bl;
    }

    public void reset() {
        this._parseLength = 0;
        this._errorIndex = -1;
        this._errorMsg = null;
    }
}

