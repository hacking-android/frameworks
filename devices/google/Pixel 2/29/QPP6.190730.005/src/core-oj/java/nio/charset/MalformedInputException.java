/*
 * Decompiled with CFR 0.145.
 */
package java.nio.charset;

import java.nio.charset.CharacterCodingException;

public class MalformedInputException
extends CharacterCodingException {
    private static final long serialVersionUID = -3438823399834806194L;
    private int inputLength;

    public MalformedInputException(int n) {
        this.inputLength = n;
    }

    public int getInputLength() {
        return this.inputLength;
    }

    @Override
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Input length = ");
        stringBuilder.append(this.inputLength);
        return stringBuilder.toString();
    }
}

