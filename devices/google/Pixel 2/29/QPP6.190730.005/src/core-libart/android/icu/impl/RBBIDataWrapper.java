/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUBinary;
import android.icu.impl.Trie2;
import android.icu.text.RuleBasedBreakIterator;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;

public final class RBBIDataWrapper {
    public static final int ACCEPTING = 0;
    public static final int DATA_FORMAT = 1114794784;
    public static final int DH_CATCOUNT = 3;
    public static final int DH_FORMATVERSION = 1;
    public static final int DH_FTABLE = 4;
    public static final int DH_FTABLELEN = 5;
    public static final int DH_LENGTH = 2;
    public static final int DH_MAGIC = 0;
    public static final int DH_RTABLE = 6;
    public static final int DH_RTABLELEN = 7;
    public static final int DH_RULESOURCE = 10;
    public static final int DH_RULESOURCELEN = 11;
    public static final int DH_SIZE = 20;
    public static final int DH_STATUSTABLE = 12;
    public static final int DH_STATUSTABLELEN = 13;
    public static final int DH_TRIE = 8;
    public static final int DH_TRIELEN = 9;
    public static final int FORMAT_VERSION = 83886080;
    private static final IsAcceptable IS_ACCEPTABLE = new IsAcceptable();
    public static final int LOOKAHEAD = 1;
    public static final int NEXTSTATES = 4;
    public static final int RBBI_BOF_REQUIRED = 2;
    public static final int RBBI_LOOKAHEAD_HARD_BREAK = 1;
    public static final int RESERVED = 3;
    public static final int TAGIDX = 2;
    public RBBIStateTable fFTable;
    public RBBIDataHeader fHeader;
    public RBBIStateTable fRTable;
    public String fRuleSource;
    public int[] fStatusTable;
    public Trie2 fTrie;

    RBBIDataWrapper() {
    }

    private void dumpCharCategories(PrintStream printStream) {
        int n = this.fHeader.fCatCount;
        String[] arrstring = new String[n + 1];
        int n2 = 0;
        int n3 = 0;
        int n4 = -1;
        Object object = new int[n + 1];
        for (n = 0; n <= this.fHeader.fCatCount; ++n) {
            arrstring[n] = "";
        }
        printStream.println("\nCharacter Categories");
        printStream.println("--------------------");
        n = 0;
        while (n <= 1114111) {
            int n5 = this.fTrie.get(n) & -16385;
            if (n5 >= 0 && n5 <= this.fHeader.fCatCount) {
                if (n5 != n4) {
                    if (n4 >= 0) {
                        StringBuilder stringBuilder;
                        if (arrstring[n4].length() > object[n4] + 70) {
                            object[n4] = arrstring[n4].length() + 10;
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(arrstring[n4]);
                            stringBuilder.append("\n       ");
                            arrstring[n4] = stringBuilder.toString();
                        }
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(arrstring[n4]);
                        stringBuilder.append(" ");
                        stringBuilder.append(Integer.toHexString(n2));
                        arrstring[n4] = stringBuilder.toString();
                        if (n3 != n2) {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(arrstring[n4]);
                            stringBuilder.append("-");
                            stringBuilder.append(Integer.toHexString(n3));
                            arrstring[n4] = stringBuilder.toString();
                        }
                    }
                    n4 = n5;
                    n2 = n;
                }
                n3 = n++;
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Error, bad category ");
            ((StringBuilder)object).append(Integer.toHexString(n5));
            ((StringBuilder)object).append(" for char ");
            ((StringBuilder)object).append(Integer.toHexString(n));
            printStream.println(((StringBuilder)object).toString());
            break;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(arrstring[n4]);
        ((StringBuilder)object).append(" ");
        ((StringBuilder)object).append(Integer.toHexString(n2));
        arrstring[n4] = ((StringBuilder)object).toString();
        if (n3 != n2) {
            object = new StringBuilder();
            ((StringBuilder)object).append(arrstring[n4]);
            ((StringBuilder)object).append("-");
            ((StringBuilder)object).append(Integer.toHexString(n3));
            arrstring[n4] = ((StringBuilder)object).toString();
        }
        for (n = 0; n <= this.fHeader.fCatCount; ++n) {
            object = new StringBuilder();
            ((StringBuilder)object).append(RBBIDataWrapper.intToString(n, 5));
            ((StringBuilder)object).append("  ");
            ((StringBuilder)object).append(arrstring[n]);
            printStream.println(((StringBuilder)object).toString());
        }
        printStream.println();
    }

    private void dumpRow(PrintStream printStream, RBBIStateTable rBBIStateTable, int n) {
        StringBuilder stringBuilder = new StringBuilder(this.fHeader.fCatCount * 5 + 20);
        stringBuilder.append(RBBIDataWrapper.intToString(n, 4));
        int n2 = this.getRowIndex(n);
        if (rBBIStateTable.fTable[n2 + 0] != 0) {
            stringBuilder.append(RBBIDataWrapper.intToString(rBBIStateTable.fTable[n2 + 0], 5));
        } else {
            stringBuilder.append("     ");
        }
        if (rBBIStateTable.fTable[n2 + 1] != 0) {
            stringBuilder.append(RBBIDataWrapper.intToString(rBBIStateTable.fTable[n2 + 1], 5));
        } else {
            stringBuilder.append("     ");
        }
        stringBuilder.append(RBBIDataWrapper.intToString(rBBIStateTable.fTable[n2 + 2], 5));
        for (n = 0; n < this.fHeader.fCatCount; ++n) {
            stringBuilder.append(RBBIDataWrapper.intToString(rBBIStateTable.fTable[n2 + 4 + n], 5));
        }
        printStream.println(stringBuilder);
    }

    private void dumpTable(PrintStream printStream, RBBIStateTable rBBIStateTable) {
        if (rBBIStateTable != null && rBBIStateTable.fTable.length != 0) {
            int n;
            StringBuilder stringBuilder = new StringBuilder(" Row  Acc Look  Tag");
            for (n = 0; n < this.fHeader.fCatCount; ++n) {
                stringBuilder.append(RBBIDataWrapper.intToString(n, 5));
            }
            printStream.println(stringBuilder.toString());
            for (n = 0; n < stringBuilder.length(); ++n) {
                printStream.print("-");
            }
            printStream.println();
            for (n = 0; n < rBBIStateTable.fNumStates; ++n) {
                this.dumpRow(printStream, rBBIStateTable, n);
            }
            printStream.println();
        } else {
            printStream.println("  -- null -- ");
        }
    }

    public static boolean equals(RBBIStateTable rBBIStateTable, RBBIStateTable rBBIStateTable2) {
        if (rBBIStateTable == rBBIStateTable2) {
            return true;
        }
        if (rBBIStateTable != null && rBBIStateTable2 != null) {
            return rBBIStateTable.equals(rBBIStateTable2);
        }
        return false;
    }

    public static RBBIDataWrapper get(ByteBuffer byteBuffer) throws IOException {
        RBBIDataWrapper rBBIDataWrapper = new RBBIDataWrapper();
        ICUBinary.readHeader(byteBuffer, 1114794784, IS_ACCEPTABLE);
        rBBIDataWrapper.fHeader = new RBBIDataHeader();
        rBBIDataWrapper.fHeader.fMagic = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fFormatVersion[0] = byteBuffer.get();
        rBBIDataWrapper.fHeader.fFormatVersion[1] = byteBuffer.get();
        rBBIDataWrapper.fHeader.fFormatVersion[2] = byteBuffer.get();
        rBBIDataWrapper.fHeader.fFormatVersion[3] = byteBuffer.get();
        rBBIDataWrapper.fHeader.fLength = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fCatCount = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fFTable = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fFTableLen = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fRTable = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fRTableLen = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fTrie = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fTrieLen = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fRuleSource = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fRuleSourceLen = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fStatusTable = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fStatusTableLen = byteBuffer.getInt();
        ICUBinary.skipBytes(byteBuffer, 24);
        if (rBBIDataWrapper.fHeader.fMagic == 45472 && IS_ACCEPTABLE.isDataVersionAcceptable(rBBIDataWrapper.fHeader.fFormatVersion)) {
            if (rBBIDataWrapper.fHeader.fFTable >= 80 && rBBIDataWrapper.fHeader.fFTable <= rBBIDataWrapper.fHeader.fLength) {
                ICUBinary.skipBytes(byteBuffer, rBBIDataWrapper.fHeader.fFTable - 80);
                int n = rBBIDataWrapper.fHeader.fFTable;
                rBBIDataWrapper.fFTable = RBBIStateTable.get(byteBuffer, rBBIDataWrapper.fHeader.fFTableLen);
                int n2 = rBBIDataWrapper.fHeader.fFTableLen;
                ICUBinary.skipBytes(byteBuffer, rBBIDataWrapper.fHeader.fRTable - (n + n2));
                n2 = rBBIDataWrapper.fHeader.fRTable;
                rBBIDataWrapper.fRTable = RBBIStateTable.get(byteBuffer, rBBIDataWrapper.fHeader.fRTableLen);
                n = rBBIDataWrapper.fHeader.fRTableLen;
                ICUBinary.skipBytes(byteBuffer, rBBIDataWrapper.fHeader.fTrie - (n2 + n));
                n2 = rBBIDataWrapper.fHeader.fTrie;
                byteBuffer.mark();
                rBBIDataWrapper.fTrie = Trie2.createFromSerialized(byteBuffer);
                byteBuffer.reset();
                if (n2 <= rBBIDataWrapper.fHeader.fStatusTable) {
                    ICUBinary.skipBytes(byteBuffer, rBBIDataWrapper.fHeader.fStatusTable - n2);
                    n2 = rBBIDataWrapper.fHeader.fStatusTable;
                    rBBIDataWrapper.fStatusTable = ICUBinary.getInts(byteBuffer, rBBIDataWrapper.fHeader.fStatusTableLen / 4, 3 & rBBIDataWrapper.fHeader.fStatusTableLen);
                    if ((n2 += rBBIDataWrapper.fHeader.fStatusTableLen) <= rBBIDataWrapper.fHeader.fRuleSource) {
                        ICUBinary.skipBytes(byteBuffer, rBBIDataWrapper.fHeader.fRuleSource - n2);
                        n2 = rBBIDataWrapper.fHeader.fRuleSource;
                        rBBIDataWrapper.fRuleSource = ICUBinary.getString(byteBuffer, rBBIDataWrapper.fHeader.fRuleSourceLen / 2, 1 & rBBIDataWrapper.fHeader.fRuleSourceLen);
                        if (RuleBasedBreakIterator.fDebugEnv != null && RuleBasedBreakIterator.fDebugEnv.indexOf("data") >= 0) {
                            rBBIDataWrapper.dump(System.out);
                        }
                        return rBBIDataWrapper;
                    }
                    throw new IOException("Break iterator Rule data corrupt");
                }
                throw new IOException("Break iterator Rule data corrupt");
            }
            throw new IOException("Break iterator Rule data corrupt");
        }
        throw new IOException("Break Iterator Rule Data Magic Number Incorrect, or unsupported data version.");
    }

    public static String intToHexString(int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder(n2);
        stringBuilder.append(Integer.toHexString(n));
        while (stringBuilder.length() < n2) {
            stringBuilder.insert(0, ' ');
        }
        return stringBuilder.toString();
    }

    public static String intToString(int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder(n2);
        stringBuilder.append(n);
        while (stringBuilder.length() < n2) {
            stringBuilder.insert(0, ' ');
        }
        return stringBuilder.toString();
    }

    public void dump(PrintStream printStream) {
        if (this.fFTable != null) {
            printStream.println("RBBI Data Wrapper dump ...");
            printStream.println();
            printStream.println("Forward State Table");
            this.dumpTable(printStream, this.fFTable);
            printStream.println("Reverse State Table");
            this.dumpTable(printStream, this.fRTable);
            this.dumpCharCategories(printStream);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Source Rules: ");
            stringBuilder.append(this.fRuleSource);
            printStream.println(stringBuilder.toString());
            return;
        }
        throw new NullPointerException();
    }

    public int getRowIndex(int n) {
        return (this.fHeader.fCatCount + 4) * n;
    }

    private static final class IsAcceptable
    implements ICUBinary.Authenticate {
        private IsAcceptable() {
        }

        @Override
        public boolean isDataVersionAcceptable(byte[] arrby) {
            boolean bl = false;
            if ((arrby[0] << 24) + (arrby[1] << 16) + (arrby[2] << 8) + arrby[3] == 83886080) {
                bl = true;
            }
            return bl;
        }
    }

    public static final class RBBIDataHeader {
        public int fCatCount;
        int fFTable;
        int fFTableLen;
        byte[] fFormatVersion = new byte[4];
        int fLength;
        int fMagic = 0;
        int fRTable;
        int fRTableLen;
        int fRuleSource;
        int fRuleSourceLen;
        int fStatusTable;
        int fStatusTableLen;
        int fTrie;
        int fTrieLen;
    }

    public static class RBBIStateTable {
        public int fFlags;
        public int fNumStates;
        public int fReserved;
        public int fRowLen;
        public short[] fTable;

        static RBBIStateTable get(ByteBuffer byteBuffer, int n) throws IOException {
            if (n == 0) {
                return null;
            }
            if (n >= 16) {
                RBBIStateTable rBBIStateTable = new RBBIStateTable();
                rBBIStateTable.fNumStates = byteBuffer.getInt();
                rBBIStateTable.fRowLen = byteBuffer.getInt();
                rBBIStateTable.fFlags = byteBuffer.getInt();
                rBBIStateTable.fReserved = byteBuffer.getInt();
                rBBIStateTable.fTable = ICUBinary.getShorts(byteBuffer, (n -= 16) / 2, n & 1);
                return rBBIStateTable;
            }
            throw new IOException("Invalid RBBI state table length.");
        }

        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof RBBIStateTable)) {
                return false;
            }
            object = (RBBIStateTable)object;
            if (this.fNumStates != ((RBBIStateTable)object).fNumStates) {
                return false;
            }
            if (this.fRowLen != ((RBBIStateTable)object).fRowLen) {
                return false;
            }
            if (this.fFlags != ((RBBIStateTable)object).fFlags) {
                return false;
            }
            if (this.fReserved != ((RBBIStateTable)object).fReserved) {
                return false;
            }
            return Arrays.equals(this.fTable, ((RBBIStateTable)object).fTable);
        }

        public int put(DataOutputStream dataOutputStream) throws IOException {
            int n;
            dataOutputStream.writeInt(this.fNumStates);
            dataOutputStream.writeInt(this.fRowLen);
            dataOutputStream.writeInt(this.fFlags);
            dataOutputStream.writeInt(this.fReserved);
            int n2 = this.fRowLen * this.fNumStates / 2;
            for (n = 0; n < n2; ++n) {
                dataOutputStream.writeShort(this.fTable[n]);
            }
            n = this.fRowLen * this.fNumStates + 16;
            while (n % 8 != 0) {
                dataOutputStream.writeByte(0);
                ++n;
            }
            return n;
        }
    }

}

