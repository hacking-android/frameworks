/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.framed;

import com.android.okhttp.okio.ByteString;

public final class Header {
    public static final ByteString RESPONSE_STATUS = ByteString.encodeUtf8(":status");
    public static final ByteString TARGET_AUTHORITY;
    public static final ByteString TARGET_HOST;
    public static final ByteString TARGET_METHOD;
    public static final ByteString TARGET_PATH;
    public static final ByteString TARGET_SCHEME;
    public static final ByteString VERSION;
    final int hpackSize;
    public final ByteString name;
    public final ByteString value;

    static {
        TARGET_METHOD = ByteString.encodeUtf8(":method");
        TARGET_PATH = ByteString.encodeUtf8(":path");
        TARGET_SCHEME = ByteString.encodeUtf8(":scheme");
        TARGET_AUTHORITY = ByteString.encodeUtf8(":authority");
        TARGET_HOST = ByteString.encodeUtf8(":host");
        VERSION = ByteString.encodeUtf8(":version");
    }

    public Header(ByteString byteString, ByteString byteString2) {
        this.name = byteString;
        this.value = byteString2;
        this.hpackSize = byteString.size() + 32 + byteString2.size();
    }

    public Header(ByteString byteString, String string) {
        this(byteString, ByteString.encodeUtf8(string));
    }

    public Header(String string, String string2) {
        this(ByteString.encodeUtf8(string), ByteString.encodeUtf8(string2));
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Header;
        boolean bl2 = false;
        if (bl) {
            object = (Header)object;
            if (this.name.equals(((Header)object).name) && this.value.equals(((Header)object).value)) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public int hashCode() {
        return (17 * 31 + this.name.hashCode()) * 31 + this.value.hashCode();
    }

    public String toString() {
        return String.format("%s: %s", this.name.utf8(), this.value.utf8());
    }
}

