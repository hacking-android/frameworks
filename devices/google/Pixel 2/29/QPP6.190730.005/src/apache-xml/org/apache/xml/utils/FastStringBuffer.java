/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import org.apache.xml.utils.XMLCharacterRecognizer;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

public class FastStringBuffer {
    private static final int CARRY_WS = 4;
    static final boolean DEBUG_FORCE_FIXED_CHUNKSIZE = true;
    static final int DEBUG_FORCE_INIT_BITS = 0;
    static final char[] SINGLE_SPACE = new char[]{' '};
    public static final int SUPPRESS_BOTH = 3;
    public static final int SUPPRESS_LEADING_WS = 1;
    public static final int SUPPRESS_TRAILING_WS = 2;
    char[][] m_array;
    int m_chunkBits = 15;
    int m_chunkMask;
    int m_chunkSize;
    int m_firstFree = 0;
    FastStringBuffer m_innerFSB = null;
    int m_lastChunk = 0;
    int m_maxChunkBits = 15;
    int m_rebundleBits = 2;

    public FastStringBuffer() {
        this(10, 15, 2);
    }

    public FastStringBuffer(int n) {
        this(n, 15, 2);
    }

    public FastStringBuffer(int n, int n2) {
        this(n, n2, 2);
    }

    public FastStringBuffer(int n, int n2, int n3) {
        this.m_array = new char[16][];
        n2 = n;
        if (n > n) {
            n2 = n;
        }
        this.m_chunkBits = n2;
        this.m_maxChunkBits = n;
        this.m_rebundleBits = n3;
        n = this.m_chunkSize = 1 << n2;
        this.m_chunkMask = n - 1;
        this.m_array[0] = new char[n];
    }

    private FastStringBuffer(FastStringBuffer fastStringBuffer) {
        this.m_chunkBits = fastStringBuffer.m_chunkBits;
        this.m_maxChunkBits = fastStringBuffer.m_maxChunkBits;
        this.m_rebundleBits = fastStringBuffer.m_rebundleBits;
        this.m_chunkSize = fastStringBuffer.m_chunkSize;
        this.m_chunkMask = fastStringBuffer.m_chunkMask;
        this.m_array = fastStringBuffer.m_array;
        this.m_innerFSB = fastStringBuffer.m_innerFSB;
        this.m_lastChunk = fastStringBuffer.m_lastChunk - 1;
        this.m_firstFree = fastStringBuffer.m_chunkSize;
        fastStringBuffer.m_array = new char[16][];
        fastStringBuffer.m_innerFSB = this;
        fastStringBuffer.m_lastChunk = 1;
        fastStringBuffer.m_firstFree = 0;
        fastStringBuffer.m_chunkBits += this.m_rebundleBits;
        fastStringBuffer.m_chunkSize = 1 << fastStringBuffer.m_chunkBits;
        fastStringBuffer.m_chunkMask = fastStringBuffer.m_chunkSize - 1;
    }

    private void getChars(int n, int n2, char[] arrc, int n3) {
    }

    static int sendNormalizedSAXcharacters(char[] arrc, int n, int n2, ContentHandler contentHandler, int n3) throws SAXException {
        int n4 = 0;
        boolean bl = (n3 & 1) != 0;
        int n5 = (n3 & 4) != 0 ? 1 : 0;
        int n6 = n;
        int n7 = n + n2;
        n = n5;
        n2 = n6;
        if (bl) {
            while (n6 < n7 && XMLCharacterRecognizer.isWhiteSpace(arrc[n6])) {
                ++n6;
            }
            n = n5;
            n2 = n6;
            if (n6 == n7) {
                return n3;
            }
        }
        while (n2 < n7) {
            n5 = n2;
            while ((n6 = n5) < n7 && !XMLCharacterRecognizer.isWhiteSpace(arrc[n6])) {
                n5 = n6 + 1;
            }
            n5 = n;
            if (n2 != n6) {
                n5 = n;
                if (n != 0) {
                    contentHandler.characters(SINGLE_SPACE, 0, 1);
                    n5 = 0;
                }
                contentHandler.characters(arrc, n2, n6 - n2);
            }
            n = n6;
            while ((n2 = n) < n7 && XMLCharacterRecognizer.isWhiteSpace(arrc[n2])) {
                n = n2 + 1;
            }
            n = n5;
            if (n6 == n2) continue;
            n = 1;
        }
        n2 = n4;
        if (n != 0) {
            n2 = 4;
        }
        return n2 | n3 & 2;
    }

    public static void sendNormalizedSAXcharacters(char[] arrc, int n, int n2, ContentHandler contentHandler) throws SAXException {
        FastStringBuffer.sendNormalizedSAXcharacters(arrc, n, n2, contentHandler, 3);
    }

    private final void setLength(int n, FastStringBuffer fastStringBuffer) {
        FastStringBuffer fastStringBuffer2;
        this.m_lastChunk = n >>> this.m_chunkBits;
        if (this.m_lastChunk == 0 && (fastStringBuffer2 = this.m_innerFSB) != null) {
            fastStringBuffer2.setLength(n, fastStringBuffer);
        } else {
            fastStringBuffer.m_chunkBits = this.m_chunkBits;
            fastStringBuffer.m_maxChunkBits = this.m_maxChunkBits;
            fastStringBuffer.m_rebundleBits = this.m_rebundleBits;
            fastStringBuffer.m_chunkSize = this.m_chunkSize;
            fastStringBuffer.m_chunkMask = this.m_chunkMask;
            fastStringBuffer.m_array = this.m_array;
            fastStringBuffer.m_innerFSB = this.m_innerFSB;
            fastStringBuffer.m_lastChunk = this.m_lastChunk;
            fastStringBuffer.m_firstFree = this.m_chunkMask & n;
        }
    }

    public final void append(char c) {
        int n;
        char[][] arrc;
        if (this.m_firstFree < this.m_chunkSize) {
            arrc = this.m_array[this.m_lastChunk];
        } else {
            char[][] arrarrc;
            arrc = this.m_array;
            n = arrc.length;
            if (this.m_lastChunk + 1 == n) {
                arrarrc = new char[n + 16][];
                System.arraycopy(arrc, 0, arrarrc, 0, n);
                this.m_array = arrarrc;
            }
            arrc = this.m_array;
            this.m_lastChunk = n = this.m_lastChunk + 1;
            arrarrc = arrc[n];
            arrc = arrarrc;
            if (arrarrc == null) {
                if (this.m_lastChunk == 1 << this.m_rebundleBits && this.m_chunkBits < this.m_maxChunkBits) {
                    this.m_innerFSB = new FastStringBuffer(this);
                }
                arrarrc = this.m_array;
                n = this.m_lastChunk;
                arrc = new char[this.m_chunkSize];
                arrarrc[n] = arrc;
            }
            this.m_firstFree = 0;
        }
        n = this.m_firstFree;
        this.m_firstFree = n + 1;
        arrc[n] = (char[])c;
    }

    public final void append(String string) {
        if (string == null) {
            return;
        }
        int n = string.length();
        if (n == 0) {
            return;
        }
        int n2 = 0;
        char[] arrc = this.m_array[this.m_lastChunk];
        int n3 = this.m_chunkSize - this.m_firstFree;
        while (n > 0) {
            int n4 = n3;
            if (n3 > n) {
                n4 = n;
            }
            string.getChars(n2, n2 + n4, this.m_array[this.m_lastChunk], this.m_firstFree);
            int n5 = n - n4;
            int n6 = n2 + n4;
            n = n5;
            n2 = n6;
            n3 = n4;
            if (n5 <= 0) continue;
            arrc = this.m_array;
            n3 = arrc.length;
            if (this.m_lastChunk + 1 == n3) {
                char[][] arrarrc = new char[n3 + 16][];
                System.arraycopy(arrc, 0, arrarrc, 0, n3);
                this.m_array = arrarrc;
            }
            arrc = this.m_array;
            this.m_lastChunk = n3 = this.m_lastChunk + 1;
            if (arrc[n3] == null) {
                if (this.m_lastChunk == 1 << this.m_rebundleBits && this.m_chunkBits < this.m_maxChunkBits) {
                    this.m_innerFSB = new FastStringBuffer(this);
                }
                this.m_array[this.m_lastChunk] = new char[this.m_chunkSize];
            }
            n3 = this.m_chunkSize;
            this.m_firstFree = 0;
            n = n5;
            n2 = n6;
        }
        this.m_firstFree += n3;
    }

    public final void append(StringBuffer stringBuffer) {
        if (stringBuffer == null) {
            return;
        }
        int n = stringBuffer.length();
        if (n == 0) {
            return;
        }
        int n2 = 0;
        char[] arrc = this.m_array[this.m_lastChunk];
        int n3 = this.m_chunkSize - this.m_firstFree;
        while (n > 0) {
            int n4 = n3;
            if (n3 > n) {
                n4 = n;
            }
            stringBuffer.getChars(n2, n2 + n4, this.m_array[this.m_lastChunk], this.m_firstFree);
            int n5 = n - n4;
            int n6 = n2 + n4;
            n = n5;
            n2 = n6;
            n3 = n4;
            if (n5 <= 0) continue;
            arrc = this.m_array;
            n3 = arrc.length;
            if (this.m_lastChunk + 1 == n3) {
                char[][] arrarrc = new char[n3 + 16][];
                System.arraycopy(arrc, 0, arrarrc, 0, n3);
                this.m_array = arrarrc;
            }
            arrc = this.m_array;
            this.m_lastChunk = n3 = this.m_lastChunk + 1;
            if (arrc[n3] == null) {
                if (this.m_lastChunk == 1 << this.m_rebundleBits && this.m_chunkBits < this.m_maxChunkBits) {
                    this.m_innerFSB = new FastStringBuffer(this);
                }
                this.m_array[this.m_lastChunk] = new char[this.m_chunkSize];
            }
            n3 = this.m_chunkSize;
            this.m_firstFree = 0;
            n = n5;
            n2 = n6;
        }
        this.m_firstFree += n3;
    }

    public final void append(FastStringBuffer fastStringBuffer) {
        if (fastStringBuffer == null) {
            return;
        }
        int n = fastStringBuffer.length();
        if (n == 0) {
            return;
        }
        int n2 = 0;
        char[] arrc = this.m_array[this.m_lastChunk];
        int n3 = this.m_chunkSize - this.m_firstFree;
        while (n > 0) {
            int n4;
            int n5 = n3;
            if (n3 > n) {
                n5 = n;
            }
            n3 = fastStringBuffer.m_chunkSize;
            int n6 = n2 + n3 - 1 >>> fastStringBuffer.m_chunkBits;
            int n7 = fastStringBuffer.m_chunkMask & n2;
            n3 = n4 = n3 - n7;
            if (n4 > n5) {
                n3 = n5;
            }
            System.arraycopy(fastStringBuffer.m_array[n6], n7, this.m_array[this.m_lastChunk], this.m_firstFree, n3);
            if (n3 != n5) {
                System.arraycopy(fastStringBuffer.m_array[n6 + 1], 0, this.m_array[this.m_lastChunk], this.m_firstFree + n3, n5 - n3);
            }
            n2 += n5;
            if ((n -= n5) > 0) {
                char[][] arrc2 = this.m_array;
                n5 = arrc2.length;
                if (this.m_lastChunk + 1 == n5) {
                    arrc = new char[n5 + 16][];
                    System.arraycopy(arrc2, 0, arrc, 0, n5);
                    this.m_array = arrc;
                }
                arrc = this.m_array;
                this.m_lastChunk = n5 = this.m_lastChunk + 1;
                if (arrc[n5] == null) {
                    if (this.m_lastChunk == 1 << this.m_rebundleBits && this.m_chunkBits < this.m_maxChunkBits) {
                        this.m_innerFSB = new FastStringBuffer(this);
                    }
                    this.m_array[this.m_lastChunk] = new char[this.m_chunkSize];
                }
                n5 = this.m_chunkSize;
                this.m_firstFree = 0;
            }
            n3 = n5;
        }
        this.m_firstFree += n3;
    }

    public final void append(char[] arrc, int n, int n2) {
        if (n2 == 0) {
            return;
        }
        int n3 = n;
        char[] arrc2 = this.m_array[this.m_lastChunk];
        n = this.m_chunkSize - this.m_firstFree;
        while (n2 > 0) {
            int n4 = n;
            if (n > n2) {
                n4 = n2;
            }
            System.arraycopy(arrc, n3, this.m_array[this.m_lastChunk], this.m_firstFree, n4);
            int n5 = n2 - n4;
            int n6 = n3 + n4;
            n2 = n5;
            n3 = n6;
            n = n4;
            if (n5 <= 0) continue;
            char[][] arrc3 = this.m_array;
            n = arrc3.length;
            if (this.m_lastChunk + 1 == n) {
                arrc2 = new char[n + 16][];
                System.arraycopy(arrc3, 0, arrc2, 0, n);
                this.m_array = arrc2;
            }
            arrc2 = this.m_array;
            this.m_lastChunk = n = this.m_lastChunk + 1;
            if (arrc2[n] == null) {
                if (this.m_lastChunk == 1 << this.m_rebundleBits && this.m_chunkBits < this.m_maxChunkBits) {
                    this.m_innerFSB = new FastStringBuffer(this);
                }
                this.m_array[this.m_lastChunk] = new char[this.m_chunkSize];
            }
            n = this.m_chunkSize;
            this.m_firstFree = 0;
            n2 = n5;
            n3 = n6;
        }
        this.m_firstFree += n;
    }

    public char charAt(int n) {
        FastStringBuffer fastStringBuffer;
        int n2 = n >>> this.m_chunkBits;
        if (n2 == 0 && (fastStringBuffer = this.m_innerFSB) != null) {
            return fastStringBuffer.charAt(this.m_chunkMask & n);
        }
        return this.m_array[n2][this.m_chunkMask & n];
    }

    protected String getOneChunkString(int n, int n2, int n3) {
        return new String(this.m_array[n], n2, n3);
    }

    public String getString(int n, int n2) {
        int n3 = this.m_chunkMask;
        int n4 = n & n3;
        n >>>= this.m_chunkBits;
        if (n4 + n2 < n3 && this.m_innerFSB == null) {
            return this.getOneChunkString(n, n4, n2);
        }
        return this.getString(new StringBuffer(n2), n, n4, n2).toString();
    }

    StringBuffer getString(StringBuffer stringBuffer, int n, int n2) {
        return this.getString(stringBuffer, n >>> this.m_chunkBits, this.m_chunkMask & n, n2);
    }

    StringBuffer getString(StringBuffer stringBuffer, int n, int n2, int n3) {
        FastStringBuffer fastStringBuffer;
        int n4 = this.m_chunkBits;
        int n5 = (n << n4) + n2 + n3;
        n3 = n5 >>> n4;
        n4 = this.m_chunkMask & n5;
        while (n < n3) {
            if (n == 0 && (fastStringBuffer = this.m_innerFSB) != null) {
                fastStringBuffer.getString(stringBuffer, n2, this.m_chunkSize - n2);
            } else {
                stringBuffer.append(this.m_array[n], n2, this.m_chunkSize - n2);
            }
            n2 = 0;
            ++n;
        }
        if (n3 == 0 && (fastStringBuffer = this.m_innerFSB) != null) {
            fastStringBuffer.getString(stringBuffer, n2, n4 - n2);
        } else if (n4 > n2) {
            stringBuffer.append(this.m_array[n3], n2, n4 - n2);
        }
        return stringBuffer;
    }

    public boolean isWhitespace(int n, int n2) {
        int n3 = n >>> this.m_chunkBits;
        int n4 = this.m_chunkMask & n;
        n = this.m_chunkSize - n4;
        while (n2 > 0) {
            FastStringBuffer fastStringBuffer;
            boolean bl;
            if (n2 <= n) {
                n = n2;
            }
            if (!(bl = n3 == 0 && (fastStringBuffer = this.m_innerFSB) != null ? fastStringBuffer.isWhitespace(n4, n) : XMLCharacterRecognizer.isWhiteSpace(this.m_array[n3], n4, n))) {
                return false;
            }
            n2 -= n;
            ++n3;
            n4 = 0;
            n = this.m_chunkSize;
        }
        return true;
    }

    public final int length() {
        return (this.m_lastChunk << this.m_chunkBits) + this.m_firstFree;
    }

    public final void reset() {
        this.m_lastChunk = 0;
        this.m_firstFree = 0;
        FastStringBuffer fastStringBuffer = this;
        while (fastStringBuffer.m_innerFSB != null) {
            fastStringBuffer = fastStringBuffer.m_innerFSB;
        }
        this.m_chunkBits = fastStringBuffer.m_chunkBits;
        this.m_chunkSize = fastStringBuffer.m_chunkSize;
        this.m_chunkMask = fastStringBuffer.m_chunkMask;
        this.m_innerFSB = null;
        this.m_array = new char[16][0];
        this.m_array[0] = new char[this.m_chunkSize];
    }

    public int sendNormalizedSAXcharacters(ContentHandler contentHandler, int n, int n2) throws SAXException {
        FastStringBuffer fastStringBuffer;
        int n3 = 1;
        int n4 = n + n2;
        n2 = this.m_chunkBits;
        int n5 = this.m_chunkMask;
        int n6 = n & n5;
        int n7 = n4 >>> n2;
        n5 &= n4;
        n2 = n >>> n2;
        n = n3;
        while (n2 < n7) {
            n = n2 == 0 && (fastStringBuffer = this.m_innerFSB) != null ? fastStringBuffer.sendNormalizedSAXcharacters(contentHandler, n6, this.m_chunkSize - n6) : FastStringBuffer.sendNormalizedSAXcharacters(this.m_array[n2], n6, this.m_chunkSize - n6, contentHandler, n);
            n6 = 0;
            ++n2;
        }
        if (n7 == 0 && (fastStringBuffer = this.m_innerFSB) != null) {
            n2 = fastStringBuffer.sendNormalizedSAXcharacters(contentHandler, n6, n5 - n6);
        } else {
            n2 = n;
            if (n5 > n6) {
                n2 = FastStringBuffer.sendNormalizedSAXcharacters(this.m_array[n7], n6, n5 - n6, contentHandler, n | 2);
            }
        }
        return n2;
    }

    public void sendSAXComment(LexicalHandler lexicalHandler, int n, int n2) throws SAXException {
        lexicalHandler.comment(this.getString(n, n2).toCharArray(), 0, n2);
    }

    public void sendSAXcharacters(ContentHandler contentHandler, int n, int n2) throws SAXException {
        FastStringBuffer fastStringBuffer;
        int n3 = n >>> this.m_chunkBits;
        int n4 = this.m_chunkMask;
        int n5 = n & n4;
        if (n5 + n2 < n4 && this.m_innerFSB == null) {
            contentHandler.characters(this.m_array[n3], n5, n2);
            return;
        }
        n4 = (n += n2) >>> this.m_chunkBits;
        int n6 = this.m_chunkMask & n;
        n2 = n5;
        for (n = n3; n < n4; ++n) {
            if (n == 0 && (fastStringBuffer = this.m_innerFSB) != null) {
                fastStringBuffer.sendSAXcharacters(contentHandler, n2, this.m_chunkSize - n2);
            } else {
                contentHandler.characters(this.m_array[n], n2, this.m_chunkSize - n2);
            }
            n2 = 0;
        }
        if (n4 == 0 && (fastStringBuffer = this.m_innerFSB) != null) {
            fastStringBuffer.sendSAXcharacters(contentHandler, n2, n6 - n2);
        } else if (n6 > n2) {
            contentHandler.characters(this.m_array[n4], n2, n6 - n2);
        }
    }

    public final void setLength(int n) {
        FastStringBuffer fastStringBuffer;
        this.m_lastChunk = n >>> this.m_chunkBits;
        if (this.m_lastChunk == 0 && (fastStringBuffer = this.m_innerFSB) != null) {
            fastStringBuffer.setLength(n, this);
        } else {
            this.m_firstFree = this.m_chunkMask & n;
            if (this.m_firstFree == 0 && (n = this.m_lastChunk) > 0) {
                this.m_lastChunk = n - 1;
                this.m_firstFree = this.m_chunkSize;
            }
        }
    }

    public final int size() {
        return (this.m_lastChunk << this.m_chunkBits) + this.m_firstFree;
    }

    public final String toString() {
        int n = (this.m_lastChunk << this.m_chunkBits) + this.m_firstFree;
        return this.getString(new StringBuffer(n), 0, 0, n).toString();
    }
}

