/*
 * Decompiled with CFR 0.145.
 */
package org.w3c.dom.ls;

public class LSException
extends RuntimeException {
    public static final short PARSE_ERR = 81;
    public static final short SERIALIZE_ERR = 82;
    public short code;

    public LSException(short s, String string) {
        super(string);
        this.code = s;
    }
}

