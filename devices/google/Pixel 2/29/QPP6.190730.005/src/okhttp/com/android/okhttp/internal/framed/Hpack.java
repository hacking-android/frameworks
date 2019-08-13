/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.framed;

import com.android.okhttp.internal.framed.Header;
import com.android.okhttp.internal.framed.Huffman;
import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.BufferedSource;
import com.android.okhttp.okio.ByteString;
import com.android.okhttp.okio.Okio;
import com.android.okhttp.okio.Source;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

final class Hpack {
    private static final Map<ByteString, Integer> NAME_TO_FIRST_INDEX;
    private static final int PREFIX_4_BITS = 15;
    private static final int PREFIX_5_BITS = 31;
    private static final int PREFIX_6_BITS = 63;
    private static final int PREFIX_7_BITS = 127;
    private static final Header[] STATIC_HEADER_TABLE;

    static {
        STATIC_HEADER_TABLE = new Header[]{new Header(Header.TARGET_AUTHORITY, ""), new Header(Header.TARGET_METHOD, "GET"), new Header(Header.TARGET_METHOD, "POST"), new Header(Header.TARGET_PATH, "/"), new Header(Header.TARGET_PATH, "/index.html"), new Header(Header.TARGET_SCHEME, "http"), new Header(Header.TARGET_SCHEME, "https"), new Header(Header.RESPONSE_STATUS, "200"), new Header(Header.RESPONSE_STATUS, "204"), new Header(Header.RESPONSE_STATUS, "206"), new Header(Header.RESPONSE_STATUS, "304"), new Header(Header.RESPONSE_STATUS, "400"), new Header(Header.RESPONSE_STATUS, "404"), new Header(Header.RESPONSE_STATUS, "500"), new Header("accept-charset", ""), new Header("accept-encoding", "gzip, deflate"), new Header("accept-language", ""), new Header("accept-ranges", ""), new Header("accept", ""), new Header("access-control-allow-origin", ""), new Header("age", ""), new Header("allow", ""), new Header("authorization", ""), new Header("cache-control", ""), new Header("content-disposition", ""), new Header("content-encoding", ""), new Header("content-language", ""), new Header("content-length", ""), new Header("content-location", ""), new Header("content-range", ""), new Header("content-type", ""), new Header("cookie", ""), new Header("date", ""), new Header("etag", ""), new Header("expect", ""), new Header("expires", ""), new Header("from", ""), new Header("host", ""), new Header("if-match", ""), new Header("if-modified-since", ""), new Header("if-none-match", ""), new Header("if-range", ""), new Header("if-unmodified-since", ""), new Header("last-modified", ""), new Header("link", ""), new Header("location", ""), new Header("max-forwards", ""), new Header("proxy-authenticate", ""), new Header("proxy-authorization", ""), new Header("range", ""), new Header("referer", ""), new Header("refresh", ""), new Header("retry-after", ""), new Header("server", ""), new Header("set-cookie", ""), new Header("strict-transport-security", ""), new Header("transfer-encoding", ""), new Header("user-agent", ""), new Header("vary", ""), new Header("via", ""), new Header("www-authenticate", "")};
        NAME_TO_FIRST_INDEX = Hpack.nameToFirstIndex();
    }

    private Hpack() {
    }

    private static ByteString checkLowercase(ByteString byteString) throws IOException {
        int n = byteString.size();
        for (int i = 0; i < n; ++i) {
            byte by = byteString.getByte(i);
            if (by < 65 || by > 90) continue;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PROTOCOL_ERROR response malformed: mixed case name: ");
            stringBuilder.append(byteString.utf8());
            throw new IOException(stringBuilder.toString());
        }
        return byteString;
    }

    private static Map<ByteString, Integer> nameToFirstIndex() {
        Header[] arrheader;
        LinkedHashMap<ByteString, Integer> linkedHashMap = new LinkedHashMap<ByteString, Integer>(STATIC_HEADER_TABLE.length);
        for (int i = 0; i < (arrheader = STATIC_HEADER_TABLE).length; ++i) {
            if (linkedHashMap.containsKey(arrheader[i].name)) continue;
            linkedHashMap.put(Hpack.STATIC_HEADER_TABLE[i].name, i);
        }
        return Collections.unmodifiableMap(linkedHashMap);
    }

    static final class Reader {
        Header[] dynamicTable = new Header[8];
        int dynamicTableByteCount = 0;
        int headerCount = 0;
        private final List<Header> headerList = new ArrayList<Header>();
        private int headerTableSizeSetting;
        private int maxDynamicTableByteCount;
        int nextHeaderIndex = this.dynamicTable.length - 1;
        private final BufferedSource source;

        Reader(int n, Source source) {
            this.headerTableSizeSetting = n;
            this.maxDynamicTableByteCount = n;
            this.source = Okio.buffer(source);
        }

        private void adjustDynamicTableByteCount() {
            int n = this.maxDynamicTableByteCount;
            int n2 = this.dynamicTableByteCount;
            if (n < n2) {
                if (n == 0) {
                    this.clearDynamicTable();
                } else {
                    this.evictToRecoverBytes(n2 - n);
                }
            }
        }

        private void clearDynamicTable() {
            this.headerList.clear();
            Arrays.fill(this.dynamicTable, null);
            this.nextHeaderIndex = this.dynamicTable.length - 1;
            this.headerCount = 0;
            this.dynamicTableByteCount = 0;
        }

        private int dynamicTableIndex(int n) {
            return this.nextHeaderIndex + 1 + n;
        }

        private int evictToRecoverBytes(int n) {
            int n2 = 0;
            int n3 = 0;
            if (n > 0) {
                int n4 = n;
                n = n3;
                for (n2 = this.dynamicTable.length - 1; n2 >= this.nextHeaderIndex && n4 > 0; --n2) {
                    n4 -= this.dynamicTable[n2].hpackSize;
                    this.dynamicTableByteCount -= this.dynamicTable[n2].hpackSize;
                    --this.headerCount;
                    ++n;
                }
                Header[] arrheader = this.dynamicTable;
                n2 = this.nextHeaderIndex;
                System.arraycopy(arrheader, n2 + 1, arrheader, n2 + 1 + n, this.headerCount);
                this.nextHeaderIndex += n;
                n2 = n;
            }
            return n2;
        }

        private ByteString getName(int n) {
            if (this.isStaticHeader(n)) {
                return Hpack.access$000()[n].name;
            }
            return this.dynamicTable[this.dynamicTableIndex((int)(n - Hpack.access$000().length))].name;
        }

        private void insertIntoDynamicTable(int n, Header header) {
            int n2;
            this.headerList.add(header);
            int n3 = n2 = header.hpackSize;
            if (n != -1) {
                n3 = n2 - this.dynamicTable[this.dynamicTableIndex((int)n)].hpackSize;
            }
            if (n3 > (n2 = this.maxDynamicTableByteCount)) {
                this.clearDynamicTable();
                return;
            }
            int n4 = this.evictToRecoverBytes(this.dynamicTableByteCount + n3 - n2);
            if (n == -1) {
                n = this.headerCount;
                Header[] arrheader = this.dynamicTable;
                if (n + 1 > arrheader.length) {
                    Header[] arrheader2 = new Header[arrheader.length * 2];
                    System.arraycopy(arrheader, 0, arrheader2, arrheader.length, arrheader.length);
                    this.nextHeaderIndex = this.dynamicTable.length - 1;
                    this.dynamicTable = arrheader2;
                }
                n = this.nextHeaderIndex;
                this.nextHeaderIndex = n - 1;
                this.dynamicTable[n] = header;
                ++this.headerCount;
            } else {
                n2 = this.dynamicTableIndex(n);
                this.dynamicTable[n + (n2 + n4)] = header;
            }
            this.dynamicTableByteCount += n3;
        }

        private boolean isStaticHeader(int n) {
            boolean bl = true;
            if (n < 0 || n > STATIC_HEADER_TABLE.length - 1) {
                bl = false;
            }
            return bl;
        }

        private int readByte() throws IOException {
            return this.source.readByte() & 255;
        }

        private void readIndexedHeader(int n) throws IOException {
            Object object;
            block4 : {
                block3 : {
                    block2 : {
                        if (!this.isStaticHeader(n)) break block2;
                        Header header = STATIC_HEADER_TABLE[n];
                        this.headerList.add(header);
                        break block3;
                    }
                    int n2 = this.dynamicTableIndex(n - STATIC_HEADER_TABLE.length);
                    if (n2 < 0 || n2 > ((Header[])(object = this.dynamicTable)).length - 1) break block4;
                    this.headerList.add((Header)object[n2]);
                }
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Header index too large ");
            ((StringBuilder)object).append(n + 1);
            throw new IOException(((StringBuilder)object).toString());
        }

        private void readLiteralHeaderWithIncrementalIndexingIndexedName(int n) throws IOException {
            this.insertIntoDynamicTable(-1, new Header(this.getName(n), this.readByteString()));
        }

        private void readLiteralHeaderWithIncrementalIndexingNewName() throws IOException {
            this.insertIntoDynamicTable(-1, new Header(Hpack.checkLowercase(this.readByteString()), this.readByteString()));
        }

        private void readLiteralHeaderWithoutIndexingIndexedName(int n) throws IOException {
            ByteString byteString = this.getName(n);
            ByteString byteString2 = this.readByteString();
            this.headerList.add(new Header(byteString, byteString2));
        }

        private void readLiteralHeaderWithoutIndexingNewName() throws IOException {
            ByteString byteString = Hpack.checkLowercase(this.readByteString());
            ByteString byteString2 = this.readByteString();
            this.headerList.add(new Header(byteString, byteString2));
        }

        public List<Header> getAndResetHeaderList() {
            ArrayList<Header> arrayList = new ArrayList<Header>(this.headerList);
            this.headerList.clear();
            return arrayList;
        }

        void headerTableSizeSetting(int n) {
            this.headerTableSizeSetting = n;
            this.maxDynamicTableByteCount = n;
            this.adjustDynamicTableByteCount();
        }

        int maxDynamicTableByteCount() {
            return this.maxDynamicTableByteCount;
        }

        ByteString readByteString() throws IOException {
            int n = this.readByte();
            boolean bl = (n & 128) == 128;
            n = this.readInt(n, 127);
            if (bl) {
                return ByteString.of(Huffman.get().decode(this.source.readByteArray(n)));
            }
            return this.source.readByteString(n);
        }

        void readHeaders() throws IOException {
            while (!this.source.exhausted()) {
                int n = this.source.readByte() & 255;
                if (n != 128) {
                    if ((n & 128) == 128) {
                        this.readIndexedHeader(this.readInt(n, 127) - 1);
                        continue;
                    }
                    if (n == 64) {
                        this.readLiteralHeaderWithIncrementalIndexingNewName();
                        continue;
                    }
                    if ((n & 64) == 64) {
                        this.readLiteralHeaderWithIncrementalIndexingIndexedName(this.readInt(n, 63) - 1);
                        continue;
                    }
                    if ((n & 32) == 32) {
                        this.maxDynamicTableByteCount = this.readInt(n, 31);
                        if ((n = this.maxDynamicTableByteCount) >= 0 && n <= this.headerTableSizeSetting) {
                            this.adjustDynamicTableByteCount();
                            continue;
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Invalid dynamic table size update ");
                        stringBuilder.append(this.maxDynamicTableByteCount);
                        throw new IOException(stringBuilder.toString());
                    }
                    if (n != 16 && n != 0) {
                        this.readLiteralHeaderWithoutIndexingIndexedName(this.readInt(n, 15) - 1);
                        continue;
                    }
                    this.readLiteralHeaderWithoutIndexingNewName();
                    continue;
                }
                throw new IOException("index == 0");
            }
        }

        int readInt(int n, int n2) throws IOException {
            int n3;
            if ((n &= n2) < n2) {
                return n;
            }
            n = 0;
            while (((n3 = this.readByte()) & 128) != 0) {
                n2 += (n3 & 127) << n;
                n += 7;
            }
            return n2 + (n3 << n);
        }
    }

    static final class Writer {
        private final Buffer out;

        Writer(Buffer buffer) {
            this.out = buffer;
        }

        void writeByteString(ByteString byteString) throws IOException {
            this.writeInt(byteString.size(), 127, 0);
            this.out.write(byteString);
        }

        void writeHeaders(List<Header> list) throws IOException {
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                ByteString byteString = list.get((int)i).name.toAsciiLowercase();
                Integer n2 = (Integer)NAME_TO_FIRST_INDEX.get(byteString);
                if (n2 != null) {
                    this.writeInt(n2 + 1, 15, 0);
                    this.writeByteString(list.get((int)i).value);
                    continue;
                }
                this.out.writeByte(0);
                this.writeByteString(byteString);
                this.writeByteString(list.get((int)i).value);
            }
        }

        void writeInt(int n, int n2, int n3) throws IOException {
            if (n < n2) {
                this.out.writeByte(n3 | n);
                return;
            }
            this.out.writeByte(n3 | n2);
            n -= n2;
            while (n >= 128) {
                this.out.writeByte(n & 127 | 128);
                n >>>= 7;
            }
            this.out.writeByte(n);
        }
    }

}

