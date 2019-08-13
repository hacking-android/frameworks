/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.HttpUrl;
import com.android.okhttp.MediaType;
import com.android.okhttp.RequestBody;
import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.ByteString;

public final class FormEncodingBuilder {
    private static final MediaType CONTENT_TYPE = MediaType.parse("application/x-www-form-urlencoded");
    private final Buffer content = new Buffer();

    public FormEncodingBuilder add(String string, String string2) {
        if (this.content.size() > 0L) {
            this.content.writeByte(38);
        }
        HttpUrl.canonicalize(this.content, string, 0, string.length(), " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, true);
        this.content.writeByte(61);
        HttpUrl.canonicalize(this.content, string2, 0, string2.length(), " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, true);
        return this;
    }

    public FormEncodingBuilder addEncoded(String string, String string2) {
        if (this.content.size() > 0L) {
            this.content.writeByte(38);
        }
        HttpUrl.canonicalize(this.content, string, 0, string.length(), " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, true);
        this.content.writeByte(61);
        HttpUrl.canonicalize(this.content, string2, 0, string2.length(), " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, true);
        return this;
    }

    public RequestBody build() {
        return RequestBody.create(CONTENT_TYPE, this.content.snapshot());
    }
}

