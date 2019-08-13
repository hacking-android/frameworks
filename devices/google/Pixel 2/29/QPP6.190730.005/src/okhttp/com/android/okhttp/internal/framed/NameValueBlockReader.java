/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.framed;

import com.android.okhttp.internal.framed.Header;
import com.android.okhttp.internal.framed.Spdy3;
import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.BufferedSource;
import com.android.okhttp.okio.ByteString;
import com.android.okhttp.okio.ForwardingSource;
import com.android.okhttp.okio.InflaterSource;
import com.android.okhttp.okio.Okio;
import com.android.okhttp.okio.Source;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

class NameValueBlockReader {
    private int compressedLimit;
    private final InflaterSource inflaterSource;
    private final BufferedSource source;

    public NameValueBlockReader(BufferedSource bufferedSource) {
        this.inflaterSource = new InflaterSource(new ForwardingSource(bufferedSource){

            @Override
            public long read(Buffer buffer, long l) throws IOException {
                if (NameValueBlockReader.this.compressedLimit == 0) {
                    return -1L;
                }
                if ((l = super.read(buffer, Math.min(l, (long)NameValueBlockReader.this.compressedLimit))) == -1L) {
                    return -1L;
                }
                NameValueBlockReader.access$022(NameValueBlockReader.this, l);
                return l;
            }
        }, new Inflater(){

            @Override
            public int inflate(byte[] arrby, int n, int n2) throws DataFormatException {
                int n3;
                int n4 = n3 = super.inflate(arrby, n, n2);
                if (n3 == 0) {
                    n4 = n3;
                    if (this.needsDictionary()) {
                        this.setDictionary(Spdy3.DICTIONARY);
                        n4 = super.inflate(arrby, n, n2);
                    }
                }
                return n4;
            }
        });
        this.source = Okio.buffer(this.inflaterSource);
    }

    static /* synthetic */ int access$022(NameValueBlockReader nameValueBlockReader, long l) {
        int n;
        nameValueBlockReader.compressedLimit = n = (int)((long)nameValueBlockReader.compressedLimit - l);
        return n;
    }

    private void doneReading() throws IOException {
        if (this.compressedLimit > 0) {
            this.inflaterSource.refill();
            if (this.compressedLimit != 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("compressedLimit > 0: ");
                stringBuilder.append(this.compressedLimit);
                throw new IOException(stringBuilder.toString());
            }
        }
    }

    private ByteString readByteString() throws IOException {
        int n = this.source.readInt();
        return this.source.readByteString(n);
    }

    public void close() throws IOException {
        this.source.close();
    }

    public List<Header> readNameValueBlock(int n) throws IOException {
        this.compressedLimit += n;
        int n2 = this.source.readInt();
        if (n2 >= 0) {
            if (n2 <= 1024) {
                ArrayList<Header> arrayList = new ArrayList<Header>(n2);
                for (n = 0; n < n2; ++n) {
                    ByteString byteString = this.readByteString().toAsciiLowercase();
                    ByteString byteString2 = this.readByteString();
                    if (byteString.size() != 0) {
                        arrayList.add(new Header(byteString, byteString2));
                        continue;
                    }
                    throw new IOException("name.size == 0");
                }
                this.doneReading();
                return arrayList;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("numberOfPairs > 1024: ");
            stringBuilder.append(n2);
            throw new IOException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("numberOfPairs < 0: ");
        stringBuilder.append(n2);
        throw new IOException(stringBuilder.toString());
    }

}

