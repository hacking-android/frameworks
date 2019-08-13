/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

public class NumberFormatException
extends IllegalArgumentException {
    static final long serialVersionUID = -2848938806368998894L;

    public NumberFormatException() {
    }

    public NumberFormatException(String string) {
        super(string);
    }

    static NumberFormatException forInputString(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("For input string: \"");
        stringBuilder.append(string);
        stringBuilder.append("\"");
        return new NumberFormatException(stringBuilder.toString());
    }
}

