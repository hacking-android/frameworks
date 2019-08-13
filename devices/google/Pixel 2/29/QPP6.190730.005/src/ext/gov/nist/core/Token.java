/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.core;

public class Token {
    protected int tokenType;
    protected String tokenValue;

    public int getTokenType() {
        return this.tokenType;
    }

    public String getTokenValue() {
        return this.tokenValue;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("tokenValue = ");
        stringBuilder.append(this.tokenValue);
        stringBuilder.append("/tokenType = ");
        stringBuilder.append(this.tokenType);
        return stringBuilder.toString();
    }
}

