/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.io.IOException;
import java.util.ArrayList;

class DerIndefLenConverter {
    private static final int CLASS_MASK = 192;
    private static final int FORM_MASK = 32;
    private static final int LEN_LONG = 128;
    private static final int LEN_MASK = 127;
    private static final int SKIP_EOC_BYTES = 2;
    private static final int TAG_MASK = 31;
    private byte[] data;
    private int dataPos;
    private int dataSize;
    private int index;
    private ArrayList<Object> ndefsList = new ArrayList();
    private byte[] newData;
    private int newDataPos;
    private int numOfTotalLenBytes = 0;
    private int unresolved = 0;

    DerIndefLenConverter() {
    }

    private byte[] getLengthBytes(int n) {
        byte[] arrby;
        if (n < 128) {
            arrby = new byte[]{(byte)n};
        } else if (n < 256) {
            arrby = new byte[2];
            int n2 = 0 + 1;
            arrby[0] = (byte)-127;
            arrby[n2] = (byte)n;
        } else if (n < 65536) {
            arrby = new byte[3];
            int n3 = 0 + 1;
            arrby[0] = (byte)-126;
            int n4 = n3 + 1;
            arrby[n3] = (byte)(n >> 8);
            arrby[n4] = (byte)n;
        } else if (n < 16777216) {
            arrby = new byte[4];
            int n5 = 0 + 1;
            arrby[0] = (byte)-125;
            int n6 = n5 + 1;
            arrby[n5] = (byte)(n >> 16);
            n5 = n6 + 1;
            arrby[n6] = (byte)(n >> 8);
            arrby[n5] = (byte)n;
        } else {
            arrby = new byte[5];
            int n7 = 0 + 1;
            arrby[0] = (byte)-124;
            int n8 = n7 + 1;
            arrby[n7] = (byte)(n >> 24);
            n7 = n8 + 1;
            arrby[n8] = (byte)(n >> 16);
            n8 = n7 + 1;
            arrby[n7] = (byte)(n >> 8);
            arrby[n8] = (byte)n;
        }
        return arrby;
    }

    private int getNumOfLenBytes(int n) {
        n = n < 128 ? 1 : (n < 256 ? 2 : (n < 65536 ? 3 : (n < 16777216 ? 4 : 5)));
        return n;
    }

    private boolean isEOC(int n) {
        boolean bl = (n & 31) == 0 && (n & 32) == 0 && (n & 192) == 0;
        return bl;
    }

    static boolean isIndefinite(int n) {
        boolean bl = DerIndefLenConverter.isLongForm(n) && (n & 127) == 0;
        return bl;
    }

    static boolean isLongForm(int n) {
        boolean bl = (n & 128) == 128;
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int parseLength() throws IOException {
        int n = 0;
        int n2 = this.dataPos;
        if (n2 == this.dataSize) {
            return 0;
        }
        byte[] arrby = this.data;
        this.dataPos = n2 + 1;
        if (DerIndefLenConverter.isIndefinite(n2 = arrby[n2] & 255)) {
            this.ndefsList.add(new Integer(this.dataPos));
            ++this.unresolved;
            return 0;
        }
        if (!DerIndefLenConverter.isLongForm(n2)) return n2 & 127;
        int n3 = n2 & 127;
        if (n3 > 4) throw new IOException("Too much data");
        if (this.dataSize - this.dataPos < n3 + 1) throw new IOException("Too little data");
        for (n2 = 0; n2 < n3; ++n2) {
            arrby = this.data;
            int n4 = this.dataPos;
            this.dataPos = n4 + 1;
            n = (n << 8) + (arrby[n4] & 255);
        }
        if (n < 0) throw new IOException("Invalid length bytes");
        return n;
    }

    private void parseTag() throws IOException {
        int n = this.dataPos;
        if (n == this.dataSize) {
            return;
        }
        if (this.isEOC(this.data[n]) && this.data[this.dataPos + 1] == 0) {
            int n2 = 0;
            byte[] arrby = null;
            for (n = this.ndefsList.size() - 1; n >= 0 && !((arrby = this.ndefsList.get(n)) instanceof Integer); --n) {
                n2 += ((byte[])arrby).length - 3;
            }
            if (n >= 0) {
                arrby = this.getLengthBytes(this.dataPos - (Integer)arrby + n2);
                this.ndefsList.set(n, arrby);
                --this.unresolved;
                this.numOfTotalLenBytes += arrby.length - 3;
            } else {
                throw new IOException("EOC does not have matching indefinite-length tag");
            }
        }
        ++this.dataPos;
    }

    private void parseValue(int n) {
        this.dataPos += n;
    }

    private void writeLength(int n) {
        if (n < 128) {
            byte[] arrby = this.newData;
            int n2 = this.newDataPos;
            this.newDataPos = n2 + 1;
            arrby[n2] = (byte)n;
        } else if (n < 256) {
            byte[] arrby = this.newData;
            int n3 = this.newDataPos;
            this.newDataPos = n3 + 1;
            arrby[n3] = (byte)-127;
            n3 = this.newDataPos;
            this.newDataPos = n3 + 1;
            arrby[n3] = (byte)n;
        } else if (n < 65536) {
            byte[] arrby = this.newData;
            int n4 = this.newDataPos;
            this.newDataPos = n4 + 1;
            arrby[n4] = (byte)-126;
            n4 = this.newDataPos;
            this.newDataPos = n4 + 1;
            arrby[n4] = (byte)(n >> 8);
            n4 = this.newDataPos;
            this.newDataPos = n4 + 1;
            arrby[n4] = (byte)n;
        } else if (n < 16777216) {
            byte[] arrby = this.newData;
            int n5 = this.newDataPos;
            this.newDataPos = n5 + 1;
            arrby[n5] = (byte)-125;
            n5 = this.newDataPos;
            this.newDataPos = n5 + 1;
            arrby[n5] = (byte)(n >> 16);
            n5 = this.newDataPos;
            this.newDataPos = n5 + 1;
            arrby[n5] = (byte)(n >> 8);
            n5 = this.newDataPos;
            this.newDataPos = n5 + 1;
            arrby[n5] = (byte)n;
        } else {
            byte[] arrby = this.newData;
            int n6 = this.newDataPos;
            this.newDataPos = n6 + 1;
            arrby[n6] = (byte)-124;
            n6 = this.newDataPos;
            this.newDataPos = n6 + 1;
            arrby[n6] = (byte)(n >> 24);
            n6 = this.newDataPos;
            this.newDataPos = n6 + 1;
            arrby[n6] = (byte)(n >> 16);
            n6 = this.newDataPos;
            this.newDataPos = n6 + 1;
            arrby[n6] = (byte)(n >> 8);
            n6 = this.newDataPos;
            this.newDataPos = n6 + 1;
            arrby[n6] = (byte)n;
        }
    }

    private void writeLengthAndValue() throws IOException {
        int n = this.dataPos;
        if (n == this.dataSize) {
            return;
        }
        int n2 = 0;
        Object object = this.data;
        this.dataPos = n + 1;
        int n3 = object[n] & 255;
        if (DerIndefLenConverter.isIndefinite(n3)) {
            object = this.ndefsList;
            n2 = this.index;
            this.index = n2 + 1;
            object = (byte[])((ArrayList)object).get(n2);
            System.arraycopy((byte[])object, 0, this.newData, this.newDataPos, ((Object)object).length);
            this.newDataPos += ((Object)object).length;
            return;
        }
        if (DerIndefLenConverter.isLongForm(n3)) {
            for (n = 0; n < (n3 & 127); ++n) {
                object = this.data;
                int n4 = this.dataPos;
                this.dataPos = n4 + 1;
                n2 = (n2 << 8) + (object[n4] & 255);
            }
            if (n2 < 0) {
                throw new IOException("Invalid length bytes");
            }
        } else {
            n2 = n3 & 127;
        }
        this.writeLength(n2);
        this.writeValue(n2);
    }

    private void writeTag() {
        int n;
        int n2 = this.dataPos;
        if (n2 == this.dataSize) {
            return;
        }
        byte[] arrby = this.data;
        this.dataPos = n2 + 1;
        if (this.isEOC(n2 = arrby[n2]) && (arrby = this.data)[n = this.dataPos] == 0) {
            this.dataPos = n + 1;
            this.writeTag();
        } else {
            arrby = this.newData;
            n = this.newDataPos;
            this.newDataPos = n + 1;
            arrby[n] = (byte)n2;
        }
    }

    private void writeValue(int n) {
        for (int i = 0; i < n; ++i) {
            byte[] arrby = this.newData;
            int n2 = this.newDataPos;
            this.newDataPos = n2 + 1;
            byte[] arrby2 = this.data;
            int n3 = this.dataPos;
            this.dataPos = n3 + 1;
            arrby[n2] = arrby2[n3];
        }
    }

    byte[] convert(byte[] arrby) throws IOException {
        int n;
        int n2;
        block3 : {
            this.data = arrby;
            this.dataPos = 0;
            this.index = 0;
            this.dataSize = this.data.length;
            n2 = 0;
            do {
                n = n2;
                if (this.dataPos >= this.dataSize) break block3;
                this.parseTag();
                this.parseValue(this.parseLength());
            } while (this.unresolved != 0);
            n = this.dataSize;
            n2 = this.dataPos;
            n -= n2;
            this.dataSize = n2;
        }
        if (this.unresolved == 0) {
            int n3;
            this.newData = new byte[this.dataSize + this.numOfTotalLenBytes + n];
            this.dataPos = 0;
            this.newDataPos = 0;
            this.index = 0;
            while ((n2 = this.dataPos) < (n3 = this.dataSize)) {
                this.writeTag();
                this.writeLengthAndValue();
            }
            System.arraycopy(arrby, n3, this.newData, this.numOfTotalLenBytes + n3, n);
            return this.newData;
        }
        throw new IOException("not all indef len BER resolved");
    }
}

