/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import org.apache.xml.serializer.Encodings;

public final class EncodingInfo {
    final String javaName;
    private InEncoding m_encoding;
    private final char m_highCharInContiguousGroup;
    final String name;

    public EncodingInfo(String string, String string2, char c) {
        this.name = string;
        this.javaName = string2;
        this.m_highCharInContiguousGroup = c;
    }

    private static boolean inEncoding(char c, char c2, String string) {
        boolean bl;
        try {
            String string2 = new String(new char[]{c, c2});
            bl = EncodingInfo.inEncoding(c, string2.getBytes(string));
        }
        catch (Exception exception) {
            bl = false;
        }
        return bl;
    }

    private static boolean inEncoding(char c, String string) {
        boolean bl;
        block2 : {
            try {
                String string2 = new String(new char[]{c});
                bl = EncodingInfo.inEncoding(c, string2.getBytes(string));
            }
            catch (Exception exception) {
                bl = false;
                if (string != null) break block2;
                bl = true;
            }
        }
        return bl;
    }

    private static boolean inEncoding(char c, byte[] arrby) {
        boolean bl = arrby != null && arrby.length != 0 ? (arrby[0] == 0 ? false : arrby[0] != 63 || c == '?') : false;
        return bl;
    }

    public final char getHighChar() {
        return this.m_highCharInContiguousGroup;
    }

    public boolean isInEncoding(char c) {
        if (this.m_encoding == null) {
            this.m_encoding = new EncodingImpl();
        }
        return this.m_encoding.isInEncoding(c);
    }

    public boolean isInEncoding(char c, char c2) {
        if (this.m_encoding == null) {
            this.m_encoding = new EncodingImpl();
        }
        return this.m_encoding.isInEncoding(c, c2);
    }

    private class EncodingImpl
    implements InEncoding {
        private static final int RANGE = 128;
        private InEncoding m_after;
        private final boolean[] m_alreadyKnown = new boolean[128];
        private InEncoding m_before;
        private final String m_encoding;
        private final int m_explFirst;
        private final int m_explLast;
        private final int m_first;
        private final boolean[] m_isInEncoding = new boolean[128];
        private final int m_last;

        private EncodingImpl() {
            this(encodingInfo.javaName, 0, Integer.MAX_VALUE, 0);
        }

        private EncodingImpl(String string, int n, int n2, int n3) {
            this.m_first = n;
            this.m_last = n2;
            this.m_explFirst = n3;
            this.m_explLast = n3 + 127;
            this.m_encoding = string;
            if (EncodingInfo.this.javaName != null) {
                n = this.m_explFirst;
                if (n >= 0 && n <= 127 && ("UTF8".equals(EncodingInfo.this.javaName) || "UTF-16".equals(EncodingInfo.this.javaName) || "ASCII".equals(EncodingInfo.this.javaName) || "US-ASCII".equals(EncodingInfo.this.javaName) || "Unicode".equals(EncodingInfo.this.javaName) || "UNICODE".equals(EncodingInfo.this.javaName) || EncodingInfo.this.javaName.startsWith("ISO8859"))) {
                    for (n = 1; n < 127; ++n) {
                        n2 = n - this.m_explFirst;
                        if (n2 < 0 || n2 >= 128) continue;
                        this.m_alreadyKnown[n2] = true;
                        this.m_isInEncoding[n2] = true;
                    }
                }
                if (EncodingInfo.this.javaName == null) {
                    for (n = 0; n < (EncodingInfo.this = this.m_alreadyKnown).length; ++n) {
                        EncodingInfo.this[n] = true;
                        this.m_isInEncoding[n] = true;
                    }
                }
            }
        }

        @Override
        public boolean isInEncoding(char c) {
            int n;
            boolean bl;
            int n2 = Encodings.toCodePoint(c);
            if (n2 < (n = this.m_explFirst)) {
                if (this.m_before == null) {
                    this.m_before = new EncodingImpl(this.m_encoding, this.m_first, n - 1, n2);
                }
                bl = this.m_before.isInEncoding(c);
            } else {
                int n3 = this.m_explLast;
                if (n3 < n2) {
                    if (this.m_after == null) {
                        this.m_after = new EncodingImpl(this.m_encoding, n3 + 1, this.m_last, n2);
                    }
                    bl = this.m_after.isInEncoding(c);
                } else if (this.m_alreadyKnown[n = n2 - n]) {
                    bl = this.m_isInEncoding[n];
                } else {
                    bl = EncodingInfo.inEncoding(c, this.m_encoding);
                    this.m_alreadyKnown[n] = true;
                    this.m_isInEncoding[n] = bl;
                }
            }
            return bl;
        }

        @Override
        public boolean isInEncoding(char c, char c2) {
            boolean bl;
            int n;
            int n2 = Encodings.toCodePoint(c, c2);
            if (n2 < (n = this.m_explFirst)) {
                if (this.m_before == null) {
                    this.m_before = new EncodingImpl(this.m_encoding, this.m_first, n - 1, n2);
                }
                bl = this.m_before.isInEncoding(c, c2);
            } else {
                int n3 = this.m_explLast;
                if (n3 < n2) {
                    if (this.m_after == null) {
                        this.m_after = new EncodingImpl(this.m_encoding, n3 + 1, this.m_last, n2);
                    }
                    bl = this.m_after.isInEncoding(c, c2);
                } else {
                    n3 = n2 - n;
                    if (this.m_alreadyKnown[n3]) {
                        bl = this.m_isInEncoding[n3];
                    } else {
                        bl = EncodingInfo.inEncoding(c, c2, this.m_encoding);
                        this.m_alreadyKnown[n3] = true;
                        this.m_isInEncoding[n3] = bl;
                    }
                }
            }
            return bl;
        }
    }

    private static interface InEncoding {
        public boolean isInEncoding(char var1);

        public boolean isInEncoding(char var1, char var2);
    }

}

