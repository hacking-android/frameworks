/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.Headers;
import com.android.okhttp.MediaType;
import com.android.okhttp.RequestBody;
import com.android.okhttp.internal.Util;
import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.BufferedSink;
import com.android.okhttp.okio.ByteString;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class MultipartBuilder {
    public static final MediaType ALTERNATIVE;
    private static final byte[] COLONSPACE;
    private static final byte[] CRLF;
    private static final byte[] DASHDASH;
    public static final MediaType DIGEST;
    public static final MediaType FORM;
    public static final MediaType MIXED;
    public static final MediaType PARALLEL;
    private final ByteString boundary;
    private final List<RequestBody> partBodies = new ArrayList<RequestBody>();
    private final List<Headers> partHeaders = new ArrayList<Headers>();
    private MediaType type = MIXED;

    static {
        MIXED = MediaType.parse("multipart/mixed");
        ALTERNATIVE = MediaType.parse("multipart/alternative");
        DIGEST = MediaType.parse("multipart/digest");
        PARALLEL = MediaType.parse("multipart/parallel");
        FORM = MediaType.parse("multipart/form-data");
        COLONSPACE = new byte[]{58, 32};
        CRLF = new byte[]{13, 10};
        DASHDASH = new byte[]{45, 45};
    }

    public MultipartBuilder() {
        this(UUID.randomUUID().toString());
    }

    public MultipartBuilder(String string) {
        this.boundary = ByteString.encodeUtf8(string);
    }

    private static StringBuilder appendQuotedString(StringBuilder stringBuilder, String string) {
        stringBuilder.append('\"');
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c != '\n') {
                if (c != '\r') {
                    if (c != '\"') {
                        stringBuilder.append(c);
                        continue;
                    }
                    stringBuilder.append("%22");
                    continue;
                }
                stringBuilder.append("%0D");
                continue;
            }
            stringBuilder.append("%0A");
        }
        stringBuilder.append('\"');
        return stringBuilder;
    }

    public MultipartBuilder addFormDataPart(String string, String string2) {
        return this.addFormDataPart(string, null, RequestBody.create(null, string2));
    }

    public MultipartBuilder addFormDataPart(String string, String string2, RequestBody requestBody) {
        if (string != null) {
            StringBuilder stringBuilder = new StringBuilder("form-data; name=");
            MultipartBuilder.appendQuotedString(stringBuilder, string);
            if (string2 != null) {
                stringBuilder.append("; filename=");
                MultipartBuilder.appendQuotedString(stringBuilder, string2);
            }
            return this.addPart(Headers.of("Content-Disposition", stringBuilder.toString()), requestBody);
        }
        throw new NullPointerException("name == null");
    }

    public MultipartBuilder addPart(Headers headers, RequestBody requestBody) {
        if (requestBody != null) {
            if (headers != null && headers.get("Content-Type") != null) {
                throw new IllegalArgumentException("Unexpected header: Content-Type");
            }
            if (headers != null && headers.get("Content-Length") != null) {
                throw new IllegalArgumentException("Unexpected header: Content-Length");
            }
            this.partHeaders.add(headers);
            this.partBodies.add(requestBody);
            return this;
        }
        throw new NullPointerException("body == null");
    }

    public MultipartBuilder addPart(RequestBody requestBody) {
        return this.addPart(null, requestBody);
    }

    public RequestBody build() {
        if (!this.partHeaders.isEmpty()) {
            return new MultipartRequestBody(this.type, this.boundary, this.partHeaders, this.partBodies);
        }
        throw new IllegalStateException("Multipart body must have at least one part.");
    }

    public MultipartBuilder type(MediaType mediaType) {
        if (mediaType != null) {
            if (mediaType.type().equals("multipart")) {
                this.type = mediaType;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("multipart != ");
            stringBuilder.append(mediaType);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        throw new NullPointerException("type == null");
    }

    private static final class MultipartRequestBody
    extends RequestBody {
        private final ByteString boundary;
        private long contentLength = -1L;
        private final MediaType contentType;
        private final List<RequestBody> partBodies;
        private final List<Headers> partHeaders;

        public MultipartRequestBody(MediaType mediaType, ByteString byteString, List<Headers> list, List<RequestBody> list2) {
            if (mediaType != null) {
                this.boundary = byteString;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(mediaType);
                stringBuilder.append("; boundary=");
                stringBuilder.append(byteString.utf8());
                this.contentType = MediaType.parse(stringBuilder.toString());
                this.partHeaders = Util.immutableList(list);
                this.partBodies = Util.immutableList(list2);
                return;
            }
            throw new NullPointerException("type == null");
        }

        private long writeOrCountBytes(BufferedSink bufferedSink, boolean bl) throws IOException {
            long l;
            long l2 = 0L;
            BufferedSink bufferedSink2 = null;
            if (bl) {
                bufferedSink2 = bufferedSink = new Buffer();
            }
            int n = this.partHeaders.size();
            for (int i = 0; i < n; ++i) {
                Object object = this.partHeaders.get(i);
                RequestBody requestBody = this.partBodies.get(i);
                bufferedSink.write(DASHDASH);
                bufferedSink.write(this.boundary);
                bufferedSink.write(CRLF);
                if (object != null) {
                    int n2 = ((Headers)object).size();
                    for (int j = 0; j < n2; ++j) {
                        bufferedSink.writeUtf8(((Headers)object).name(j)).write(COLONSPACE).writeUtf8(((Headers)object).value(j)).write(CRLF);
                    }
                }
                if ((object = requestBody.contentType()) != null) {
                    bufferedSink.writeUtf8("Content-Type: ").writeUtf8(((MediaType)object).toString()).write(CRLF);
                }
                if ((l = requestBody.contentLength()) != -1L) {
                    bufferedSink.writeUtf8("Content-Length: ").writeDecimalLong(l).write(CRLF);
                } else if (bl) {
                    ((Buffer)bufferedSink2).clear();
                    return -1L;
                }
                bufferedSink.write(CRLF);
                if (bl) {
                    l2 += l;
                } else {
                    this.partBodies.get(i).writeTo(bufferedSink);
                }
                bufferedSink.write(CRLF);
            }
            bufferedSink.write(DASHDASH);
            bufferedSink.write(this.boundary);
            bufferedSink.write(DASHDASH);
            bufferedSink.write(CRLF);
            l = l2;
            if (bl) {
                l = l2 + ((Buffer)bufferedSink2).size();
                ((Buffer)bufferedSink2).clear();
            }
            return l;
        }

        @Override
        public long contentLength() throws IOException {
            long l = this.contentLength;
            if (l != -1L) {
                return l;
            }
            this.contentLength = l = this.writeOrCountBytes(null, true);
            return l;
        }

        @Override
        public MediaType contentType() {
            return this.contentType;
        }

        @Override
        public void writeTo(BufferedSink bufferedSink) throws IOException {
            this.writeOrCountBytes(bufferedSink, false);
        }
    }

}

