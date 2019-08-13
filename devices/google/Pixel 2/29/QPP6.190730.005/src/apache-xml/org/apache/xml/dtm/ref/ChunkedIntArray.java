/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref;

import java.io.PrintStream;
import java.util.Objects;
import org.apache.xml.res.XMLMessages;

final class ChunkedIntArray {
    static final int chunkalloc = 1024;
    static final int lowbits = 10;
    static final int lowmask = 1023;
    ChunksVector chunks = new ChunksVector();
    final int[] fastArray = new int[1024];
    int lastUsed = 0;
    final int slotsize;

    ChunkedIntArray(int n) {
        this.slotsize = 4;
        Objects.requireNonNull(this);
        if (4 >= n) {
            Objects.requireNonNull(this);
            if (4 > n) {
                PrintStream printStream = System.out;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("*****WARNING: ChunkedIntArray(");
                stringBuilder.append(n);
                stringBuilder.append(") wasting ");
                Objects.requireNonNull(this);
                stringBuilder.append(4 - n);
                stringBuilder.append(" words per slot");
                printStream.println(stringBuilder.toString());
            }
            this.chunks.addElement(this.fastArray);
            return;
        }
        throw new ArrayIndexOutOfBoundsException(XMLMessages.createXMLMessage("ER_CHUNKEDINTARRAY_NOT_SUPPORTED", new Object[]{Integer.toString(n)}));
    }

    int appendSlot(int n, int n2, int n3, int n4) {
        int n5 = (this.lastUsed + 1) * 4;
        int n6 = n5 >> 10;
        n5 &= 1023;
        if (n6 > this.chunks.size() - 1) {
            this.chunks.addElement(new int[1024]);
        }
        int[] arrn = this.chunks.elementAt(n6);
        arrn[n5] = n;
        arrn[n5 + 1] = n2;
        arrn[n5 + 2] = n3;
        arrn[n5 + 3] = n4;
        this.lastUsed = n = this.lastUsed + 1;
        return n;
    }

    void discardLast() {
        --this.lastUsed;
    }

    int readEntry(int n, int n2) throws ArrayIndexOutOfBoundsException {
        if (n2 < 4) {
            return this.chunks.elementAt(n >> 10)[((n *= 4) & 1023) + n2];
        }
        throw new ArrayIndexOutOfBoundsException(XMLMessages.createXMLMessage("ER_OFFSET_BIGGER_THAN_SLOT", null));
    }

    void readSlot(int n, int[] arrn) {
        int n2 = n * 4;
        n = n2 >> 10;
        if (n > this.chunks.size() - 1) {
            this.chunks.addElement(new int[1024]);
        }
        System.arraycopy(this.chunks.elementAt(n), n2 & 1023, arrn, 0, 4);
    }

    int slotsUsed() {
        return this.lastUsed;
    }

    int specialFind(int n, int n2) {
        int n3;
        block2 : {
            do {
                n3 = n;
                if (n <= 0) break block2;
                n *= 4;
            } while ((n = this.chunks.elementAt(n >> 10)[(n & 1023) + 1]) != n2);
            n3 = n;
        }
        if (n3 <= 0) {
            return n2;
        }
        return -1;
    }

    void writeEntry(int n, int n2, int n3) throws ArrayIndexOutOfBoundsException {
        if (n2 < 4) {
            this.chunks.elementAt((int)(n >> 10))[((n *= 4) & 1023) + n2] = n3;
            return;
        }
        throw new ArrayIndexOutOfBoundsException(XMLMessages.createXMLMessage("ER_OFFSET_BIGGER_THAN_SLOT", null));
    }

    void writeSlot(int n, int n2, int n3, int n4, int n5) {
        int n6 = n * 4;
        n = n6 >> 10;
        n6 &= 1023;
        if (n > this.chunks.size() - 1) {
            this.chunks.addElement(new int[1024]);
        }
        int[] arrn = this.chunks.elementAt(n);
        arrn[n6] = n2;
        arrn[n6 + 1] = n3;
        arrn[n6 + 2] = n4;
        arrn[n6 + 3] = n5;
    }

    class ChunksVector {
        final int BLOCKSIZE;
        int[][] m_map = new int[64][];
        int m_mapSize = 64;
        int pos = 0;

        ChunksVector() {
            this.BLOCKSIZE = 64;
        }

        void addElement(int[] arrn) {
            int[][] arrn2;
            int n;
            if (this.pos >= this.m_mapSize) {
                int n2;
                int n3 = this.m_mapSize;
                while ((n2 = this.pos) >= (n = this.m_mapSize)) {
                    this.m_mapSize = n + 64;
                }
                arrn2 = new int[n][];
                System.arraycopy(this.m_map, 0, arrn2, 0, n3);
                this.m_map = arrn2;
            }
            arrn2 = this.m_map;
            n = this.pos;
            arrn2[n] = arrn;
            this.pos = n + 1;
        }

        final int[] elementAt(int n) {
            return this.m_map[n];
        }

        final int size() {
            return this.pos;
        }
    }

}

