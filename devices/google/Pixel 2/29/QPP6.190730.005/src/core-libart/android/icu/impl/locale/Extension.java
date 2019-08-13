/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.locale;

public class Extension {
    private char _key;
    protected String _value;

    protected Extension(char c) {
        this._key = c;
    }

    Extension(char c, String string) {
        this._key = c;
        this._value = string;
    }

    public String getID() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this._key);
        stringBuilder.append("-");
        stringBuilder.append(this._value);
        return stringBuilder.toString();
    }

    public char getKey() {
        return this._key;
    }

    public String getValue() {
        return this._value;
    }

    public String toString() {
        return this.getID();
    }
}

