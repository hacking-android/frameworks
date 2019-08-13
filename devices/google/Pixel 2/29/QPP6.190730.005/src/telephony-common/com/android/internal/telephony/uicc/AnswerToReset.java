/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.Rlog
 *  android.util.ArrayMap
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.uicc.IccUtils
 */
package com.android.internal.telephony.uicc;

import android.telephony.Rlog;
import android.util.ArrayMap;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.uicc.IccUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class AnswerToReset {
    private static final int B2_MASK = 2;
    private static final int B7_MASK = 64;
    private static final int B8_MASK = 128;
    public static final byte DIRECT_CONVENTION = 59;
    private static final int EXTENDED_APDU_INDEX = 2;
    public static final int INTERFACE_BYTES_MASK = 240;
    public static final byte INVERSE_CONVENTION = 63;
    private static final String TAG = "AnswerToReset";
    private static final int TAG_CARD_CAPABILITIES = 7;
    public static final int TA_MASK = 16;
    public static final int TB_MASK = 32;
    public static final int TC_MASK = 64;
    public static final int TD_MASK = 128;
    public static final int T_MASK = 15;
    public static final int T_VALUE_FOR_GLOBAL_INTERFACE = 15;
    private static final boolean VDBG = false;
    private Byte mCheckByte;
    private byte mFormatByte;
    private HistoricalBytes mHistoricalBytes;
    private ArrayList<InterfaceByte> mInterfaceBytes = new ArrayList();
    private boolean mIsDirectConvention;
    private boolean mIsEuiccSupported;
    private boolean mOnlyTEqualsZero = true;

    private AnswerToReset() {
    }

    private static String byteToStringHex(Byte object) {
        object = object == null ? null : IccUtils.byteToHex((byte)((Byte)object));
        return object;
    }

    private void checkIsEuiccSupported() {
        for (int i = 0; i < this.mInterfaceBytes.size() - 1; ++i) {
            if (this.mInterfaceBytes.get(i).getTD() == null || (this.mInterfaceBytes.get(i).getTD() & 15) != 15 || this.mInterfaceBytes.get(i + 1).getTB() == null || (this.mInterfaceBytes.get(i + 1).getTB() & 128) == 0 || (this.mInterfaceBytes.get(i + 1).getTB() & 2) == 0) continue;
            this.mIsEuiccSupported = true;
            return;
        }
    }

    private static void log(String string) {
        Rlog.d((String)TAG, (String)string);
    }

    private static void loge(String string) {
        Rlog.e((String)TAG, (String)string);
    }

    public static AnswerToReset parseAtr(String string) {
        AnswerToReset answerToReset = new AnswerToReset();
        if (answerToReset.parseAtrString(string)) {
            return answerToReset;
        }
        return null;
    }

    private boolean parseAtrString(String string) {
        if (string == null) {
            AnswerToReset.loge("The input ATR string can not be null");
            return false;
        }
        if (string.length() % 2 != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("The length of input ATR string ");
            stringBuilder.append(string.length());
            stringBuilder.append(" is not even.");
            AnswerToReset.loge(stringBuilder.toString());
            return false;
        }
        if (string.length() < 4) {
            AnswerToReset.loge("Valid ATR string must at least contains TS and T0.");
            return false;
        }
        Object object = IccUtils.hexStringToBytes((String)string);
        if (object == null) {
            return false;
        }
        int n = this.parseConventionByte((byte[])object, 0);
        if (n == -1) {
            return false;
        }
        if ((n = this.parseFormatByte((byte[])object, n)) == -1) {
            return false;
        }
        if ((n = this.parseInterfaceBytes((byte[])object, n)) == -1) {
            return false;
        }
        if ((n = this.parseHistoricalBytes((byte[])object, n)) == -1) {
            return false;
        }
        if ((n = this.parseCheckBytes((byte[])object, n)) == -1) {
            return false;
        }
        if (n != ((byte[])object).length) {
            AnswerToReset.loge("Unexpected bytes after the check byte.");
            return false;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Successfully parsed the ATR string ");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(" into ");
        ((StringBuilder)object).append(this.toString());
        AnswerToReset.log(((StringBuilder)object).toString());
        this.checkIsEuiccSupported();
        return true;
    }

    private int parseCheckBytes(byte[] arrby, int n) {
        if (n < arrby.length) {
            this.mCheckByte = arrby[n];
            ++n;
        } else {
            if (!this.mOnlyTEqualsZero) {
                AnswerToReset.loge("Check byte must be present because T equals to values other than 0.");
                return -1;
            }
            AnswerToReset.log("Check byte can be absent because T=0.");
        }
        return n;
    }

    private int parseConventionByte(byte[] object, int n) {
        byte by;
        block6 : {
            block5 : {
                block4 : {
                    if (n >= ((byte[])object).length) {
                        AnswerToReset.loge("Failed to read the convention byte.");
                        return -1;
                    }
                    by = object[n];
                    if (by != 59) break block4;
                    this.mIsDirectConvention = true;
                    break block5;
                }
                if (by != 63) break block6;
                this.mIsDirectConvention = false;
            }
            return n + 1;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unrecognized convention byte ");
        ((StringBuilder)object).append(IccUtils.byteToHex((byte)by));
        AnswerToReset.loge(((StringBuilder)object).toString());
        return -1;
    }

    private int parseFormatByte(byte[] arrby, int n) {
        if (n >= arrby.length) {
            AnswerToReset.loge("Failed to read the format byte.");
            return -1;
        }
        this.mFormatByte = arrby[n];
        return n + 1;
    }

    private int parseHistoricalBytes(byte[] arrby, int n) {
        int n2 = this.mFormatByte & 15;
        if (n2 + n > arrby.length) {
            AnswerToReset.loge("Failed to read the historical bytes.");
            return -1;
        }
        if (n2 > 0) {
            this.mHistoricalBytes = HistoricalBytes.parseHistoricalBytes(arrby, n, n2);
        }
        return n + n2;
    }

    private int parseInterfaceBytes(byte[] arrby, int n) {
        byte by = this.mFormatByte;
        do {
            Object object;
            block13 : {
                block12 : {
                    if ((by & 240) == 0) break block12;
                    object = new InterfaceByte();
                    int n2 = n;
                    if ((by & 16) != 0) {
                        if (n >= arrby.length) {
                            AnswerToReset.loge("Failed to read the byte for TA.");
                            return -1;
                        }
                        ((InterfaceByte)object).setTA(arrby[n]);
                        n2 = n + 1;
                    }
                    n = n2;
                    if ((by & 32) != 0) {
                        if (n2 >= arrby.length) {
                            AnswerToReset.loge("Failed to read the byte for TB.");
                            return -1;
                        }
                        ((InterfaceByte)object).setTB(arrby[n2]);
                        n = n2 + 1;
                    }
                    n2 = n;
                    if ((by & 64) != 0) {
                        if (n >= arrby.length) {
                            AnswerToReset.loge("Failed to read the byte for TC.");
                            return -1;
                        }
                        ((InterfaceByte)object).setTC(arrby[n]);
                        n2 = n + 1;
                    }
                    n = n2;
                    if ((by & 128) != 0) {
                        if (n2 >= arrby.length) {
                            AnswerToReset.loge("Failed to read the byte for TD.");
                            return -1;
                        }
                        ((InterfaceByte)object).setTD(arrby[n2]);
                        n = n2 + 1;
                    }
                    this.mInterfaceBytes.add((InterfaceByte)object);
                    object = ((InterfaceByte)object).getTD();
                    if (object != null) break block13;
                }
                return n;
            }
            by = (Byte)object;
            if ((by & 15) == 0) continue;
            this.mOnlyTEqualsZero = false;
        } while (true);
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter.println("AnswerToReset:");
        printWriter.println(this.toString());
        printWriter.flush();
    }

    public Byte getCheckByte() {
        return this.mCheckByte;
    }

    public byte getConventionByte() {
        byte by;
        byte by2;
        byte by3 = this.mIsDirectConvention ? (by2 = 59) : (by = 63);
        return by3;
    }

    public byte getFormatByte() {
        return this.mFormatByte;
    }

    public HistoricalBytes getHistoricalBytes() {
        return this.mHistoricalBytes;
    }

    public List<InterfaceByte> getInterfaceBytes() {
        return this.mInterfaceBytes;
    }

    public boolean isEuiccSupported() {
        return this.mIsEuiccSupported;
    }

    public boolean isExtendedApduSupported() {
        byte[] arrby = this.mHistoricalBytes;
        boolean bl = false;
        boolean bl2 = false;
        if (arrby == null) {
            return false;
        }
        if ((arrby = arrby.getValue(7)) != null && arrby.length >= 3) {
            if (this.mIsDirectConvention) {
                if ((arrby[2] & 64) > 0) {
                    bl2 = true;
                }
                return bl2;
            }
            bl2 = bl;
            if ((arrby[2] & 2) > 0) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("AnswerToReset:{");
        stringBuffer.append("mConventionByte=");
        stringBuffer.append(IccUtils.byteToHex((byte)this.getConventionByte()));
        stringBuffer.append(",");
        stringBuffer.append("mFormatByte=");
        stringBuffer.append(AnswerToReset.byteToStringHex(this.mFormatByte));
        stringBuffer.append(",");
        stringBuffer.append("mInterfaceBytes={");
        byte[] arrby = this.mInterfaceBytes.iterator();
        while (arrby.hasNext()) {
            stringBuffer.append(arrby.next().toString());
        }
        stringBuffer.append("},");
        stringBuffer.append("mHistoricalBytes={");
        arrby = this.mHistoricalBytes;
        if (arrby != null) {
            arrby = arrby.getRawData();
            int n = arrby.length;
            for (int i = 0; i < n; ++i) {
                stringBuffer.append(IccUtils.byteToHex((byte)arrby[i]));
                stringBuffer.append(",");
            }
        }
        stringBuffer.append("},");
        stringBuffer.append("mCheckByte=");
        stringBuffer.append(AnswerToReset.byteToStringHex(this.mCheckByte));
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public static class HistoricalBytes {
        private static final int LENGTH_MASK = 15;
        private static final int TAG_MASK = 240;
        private final byte mCategory;
        private final ArrayMap<Integer, byte[]> mNodes;
        private final byte[] mRawData;

        private HistoricalBytes(byte[] arrby, ArrayMap<Integer, byte[]> arrayMap, byte by) {
            this.mRawData = arrby;
            this.mNodes = arrayMap;
            this.mCategory = by;
        }

        private static HistoricalBytes parseHistoricalBytes(byte[] arrby, int n, int n2) {
            if (n2 > 0 && n + n2 <= arrby.length) {
                ArrayMap arrayMap = new ArrayMap();
                int n3 = n + 1;
                while (n3 < n + n2 && n3 > 0) {
                    n3 = HistoricalBytes.parseLtvNode(n3, (ArrayMap<Integer, byte[]>)arrayMap, arrby, n + n2 - 1);
                }
                if (n3 < 0) {
                    return null;
                }
                byte[] arrby2 = new byte[n2];
                System.arraycopy(arrby, n, arrby2, 0, n2);
                return new HistoricalBytes(arrby2, (ArrayMap<Integer, byte[]>)arrayMap, arrby2[0]);
            }
            return null;
        }

        private static int parseLtvNode(int n, ArrayMap<Integer, byte[]> arrayMap, byte[] arrby, int n2) {
            if (n > n2) {
                return -1;
            }
            byte by = arrby[n];
            int n3 = n + 1;
            if (n3 + (n = arrby[n] & 15) <= n2 + 1 && n != 0) {
                byte[] arrby2 = new byte[n];
                System.arraycopy(arrby, n3, arrby2, 0, n);
                arrayMap.put((Object)((by & 240) >> 4), (Object)arrby2);
                return n3 + n;
            }
            return -1;
        }

        public byte getCategory() {
            return this.mCategory;
        }

        public byte[] getRawData() {
            return this.mRawData;
        }

        public byte[] getValue(int n) {
            return (byte[])this.mNodes.get((Object)n);
        }
    }

    public static class InterfaceByte {
        private Byte mTA;
        private Byte mTB;
        private Byte mTC;
        private Byte mTD;

        private InterfaceByte() {
            this.mTA = null;
            this.mTB = null;
            this.mTC = null;
            this.mTD = null;
        }

        @VisibleForTesting
        public InterfaceByte(Byte by, Byte by2, Byte by3, Byte by4) {
            this.mTA = by;
            this.mTB = by2;
            this.mTC = by3;
            this.mTD = by4;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                if (!(Objects.equals(this.mTA, ((InterfaceByte)(object = (InterfaceByte)object)).getTA()) && Objects.equals(this.mTB, ((InterfaceByte)object).getTB()) && Objects.equals(this.mTC, ((InterfaceByte)object).getTC()) && Objects.equals(this.mTD, ((InterfaceByte)object).getTD()))) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public Byte getTA() {
            return this.mTA;
        }

        public Byte getTB() {
            return this.mTB;
        }

        public Byte getTC() {
            return this.mTC;
        }

        public Byte getTD() {
            return this.mTD;
        }

        public int hashCode() {
            return Objects.hash(this.mTA, this.mTB, this.mTC, this.mTD);
        }

        public void setTA(Byte by) {
            this.mTA = by;
        }

        public void setTB(Byte by) {
            this.mTB = by;
        }

        public void setTC(Byte by) {
            this.mTC = by;
        }

        public void setTD(Byte by) {
            this.mTD = by;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("{");
            stringBuffer.append("TA=");
            stringBuffer.append(AnswerToReset.byteToStringHex(this.mTA));
            stringBuffer.append(",");
            stringBuffer.append("TB=");
            stringBuffer.append(AnswerToReset.byteToStringHex(this.mTB));
            stringBuffer.append(",");
            stringBuffer.append("TC=");
            stringBuffer.append(AnswerToReset.byteToStringHex(this.mTC));
            stringBuffer.append(",");
            stringBuffer.append("TD=");
            stringBuffer.append(AnswerToReset.byteToStringHex(this.mTD));
            stringBuffer.append("}");
            return stringBuffer.toString();
        }
    }

}

