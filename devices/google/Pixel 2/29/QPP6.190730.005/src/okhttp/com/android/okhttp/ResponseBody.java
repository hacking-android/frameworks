/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.MediaType;
import com.android.okhttp.internal.Util;
import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.BufferedSource;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

public abstract class ResponseBody
implements Closeable {
    private Reader reader;

    private Charset charset() {
        Charset charset;
        MediaType mediaType = this.contentType();
        Charset charset2 = charset = Util.UTF_8;
        if (mediaType != null) {
            charset2 = mediaType.charset(charset);
        }
        return charset2;
    }

    public static ResponseBody create(final MediaType mediaType, final long l, final BufferedSource bufferedSource) {
        if (bufferedSource != null) {
            return new ResponseBody(){

                @Override
                public long contentLength() {
                    return l;
                }

                @Override
                public MediaType contentType() {
                    return mediaType;
                }

                @Override
                public BufferedSource source() {
                    return bufferedSource;
                }
            };
        }
        throw new NullPointerException("source == null");
    }

    public static ResponseBody create(MediaType object, String string) {
        Charset charset = Util.UTF_8;
        Object object2 = object;
        if (object != null) {
            Charset charset2;
            charset = charset2 = ((MediaType)object).charset();
            object2 = object;
            if (charset2 == null) {
                charset = Util.UTF_8;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(object);
                ((StringBuilder)object2).append("; charset=utf-8");
                object2 = MediaType.parse(((StringBuilder)object2).toString());
            }
        }
        object = new Buffer().writeString(string, charset);
        return ResponseBody.create((MediaType)object2, ((Buffer)object).size(), (BufferedSource)object);
    }

    public static ResponseBody create(MediaType mediaType, byte[] arrby) {
        Buffer buffer = new Buffer().write(arrby);
        return ResponseBody.create(mediaType, arrby.length, buffer);
    }

    public final InputStream byteStream() throws IOException {
        return this.source().inputStream();
    }

    public final byte[] bytes() throws IOException {
        long l = this.contentLength();
        if (l <= Integer.MAX_VALUE) {
            BufferedSource bufferedSource = this.source();
            byte[] arrby = bufferedSource.readByteArray();
            if (l != -1L && l != (long)arrby.length) {
                throw new IOException("Content-Length and stream length disagree");
            }
            return arrby;
            finally {
                Util.closeQuietly(bufferedSource);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot buffer entire body for content length: ");
        stringBuilder.append(l);
        throw new IOException(stringBuilder.toString());
    }

    public final Reader charStream() throws IOException {
        Reader reader = this.reader;
        if (reader == null) {
            this.reader = reader = new InputStreamReader(this.byteStream(), this.charset());
        }
        return reader;
    }

    @Override
    public void close() throws IOException {
        this.source().close();
    }

    public abstract long contentLength() throws IOException;

    public abstract MediaType contentType();

    public abstract BufferedSource source() throws IOException;

    public final String string() throws IOException {
        return new String(this.bytes(), this.charset().name());
    }

}

