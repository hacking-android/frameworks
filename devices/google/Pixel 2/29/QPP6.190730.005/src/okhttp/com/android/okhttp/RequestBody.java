/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.MediaType;
import com.android.okhttp.internal.Util;
import com.android.okhttp.okio.BufferedSink;
import com.android.okhttp.okio.ByteString;
import com.android.okhttp.okio.Okio;
import com.android.okhttp.okio.Source;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public abstract class RequestBody {
    public static RequestBody create(final MediaType mediaType, final ByteString byteString) {
        return new RequestBody(){

            @Override
            public long contentLength() throws IOException {
                return byteString.size();
            }

            @Override
            public MediaType contentType() {
                return mediaType;
            }

            @Override
            public void writeTo(BufferedSink bufferedSink) throws IOException {
                bufferedSink.write(byteString);
            }
        };
    }

    public static RequestBody create(final MediaType mediaType, final File file) {
        if (file != null) {
            return new RequestBody(){

                @Override
                public long contentLength() {
                    return file.length();
                }

                @Override
                public MediaType contentType() {
                    return mediaType;
                }

                @Override
                public void writeTo(BufferedSink bufferedSink) throws IOException {
                    Source source;
                    Source source2 = null;
                    try {
                        source2 = source = Okio.source(file);
                    }
                    catch (Throwable throwable) {
                        Util.closeQuietly(source2);
                        throw throwable;
                    }
                    bufferedSink.writeAll(source);
                    Util.closeQuietly(source);
                }
            };
        }
        throw new NullPointerException("content == null");
    }

    public static RequestBody create(MediaType mediaType, String string) {
        Charset charset = Util.UTF_8;
        Object object = mediaType;
        if (mediaType != null) {
            Charset charset2;
            charset = charset2 = mediaType.charset();
            object = mediaType;
            if (charset2 == null) {
                charset = Util.UTF_8;
                object = new StringBuilder();
                ((StringBuilder)object).append(mediaType);
                ((StringBuilder)object).append("; charset=utf-8");
                object = MediaType.parse(((StringBuilder)object).toString());
            }
        }
        return RequestBody.create((MediaType)object, string.getBytes(charset));
    }

    public static RequestBody create(MediaType mediaType, byte[] arrby) {
        return RequestBody.create(mediaType, arrby, 0, arrby.length);
    }

    public static RequestBody create(final MediaType mediaType, final byte[] arrby, final int n, final int n2) {
        if (arrby != null) {
            Util.checkOffsetAndCount(arrby.length, n, n2);
            return new RequestBody(){

                @Override
                public long contentLength() {
                    return n2;
                }

                @Override
                public MediaType contentType() {
                    return mediaType;
                }

                @Override
                public void writeTo(BufferedSink bufferedSink) throws IOException {
                    bufferedSink.write(arrby, n, n2);
                }
            };
        }
        throw new NullPointerException("content == null");
    }

    public long contentLength() throws IOException {
        return -1L;
    }

    public abstract MediaType contentType();

    public abstract void writeTo(BufferedSink var1) throws IOException;

}

