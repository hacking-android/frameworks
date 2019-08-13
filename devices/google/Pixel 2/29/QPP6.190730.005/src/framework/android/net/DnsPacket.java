/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.ParseException;
import android.text.TextUtils;
import com.android.internal.util.BitUtils;
import java.nio.Buffer;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.List;

public abstract class DnsPacket {
    public static final int ANSECTION = 1;
    public static final int ARSECTION = 3;
    public static final int NSSECTION = 2;
    private static final int NUM_SECTIONS = 4;
    public static final int QDSECTION = 0;
    private static final String TAG = DnsPacket.class.getSimpleName();
    protected final DnsHeader mHeader;
    protected final List<DnsRecord>[] mRecords;

    protected DnsPacket(byte[] object) throws ParseException {
        if (object != null) {
            Object object2;
            try {
                object = ByteBuffer.wrap(object);
                object2 = new DnsHeader((ByteBuffer)object);
                this.mHeader = object2;
            }
            catch (BufferUnderflowException bufferUnderflowException) {
                throw new ParseException("Parse Header fail, bad input data", bufferUnderflowException);
            }
            this.mRecords = new ArrayList[4];
            for (int i = 0; i < 4; ++i) {
                int n = this.mHeader.getRecordCount(i);
                if (n > 0) {
                    this.mRecords[i] = new ArrayList<DnsRecord>(n);
                }
                for (int j = 0; j < n; ++j) {
                    try {
                        object2 = this.mRecords[i];
                        DnsRecord dnsRecord = new DnsRecord(i, (ByteBuffer)object);
                        object2.add(dnsRecord);
                        continue;
                    }
                    catch (BufferUnderflowException bufferUnderflowException) {
                        throw new ParseException("Parse record fail", bufferUnderflowException);
                    }
                }
            }
            return;
        }
        throw new ParseException("Parse header failed, null input data");
    }

    public class DnsHeader {
        private static final String TAG = "DnsHeader";
        public final int flags;
        public final int id;
        private final int[] mRecordCount;
        public final int rcode;

        DnsHeader(ByteBuffer byteBuffer) throws BufferUnderflowException {
            this.id = BitUtils.uint16(byteBuffer.getShort());
            this.flags = BitUtils.uint16(byteBuffer.getShort());
            this.rcode = this.flags & 15;
            this.mRecordCount = new int[4];
            for (int i = 0; i < 4; ++i) {
                this.mRecordCount[i] = BitUtils.uint16(byteBuffer.getShort());
            }
        }

        public int getRecordCount(int n) {
            return this.mRecordCount[n];
        }
    }

    public class DnsRecord {
        private static final int MAXLABELCOUNT = 128;
        private static final int MAXLABELSIZE = 63;
        private static final int MAXNAMESIZE = 255;
        private static final int NAME_COMPRESSION = 192;
        private static final int NAME_NORMAL = 0;
        private static final String TAG = "DnsRecord";
        private final DecimalFormat byteFormat = new DecimalFormat();
        public final String dName;
        private final byte[] mRdata;
        public final int nsClass;
        public final int nsType;
        private final FieldPosition pos = new FieldPosition(0);
        public final long ttl;

        DnsRecord(int n, ByteBuffer byteBuffer) throws BufferUnderflowException, ParseException {
            this.dName = this.parseName(byteBuffer, 0);
            if (this.dName.length() <= 255) {
                this.nsType = BitUtils.uint16(byteBuffer.getShort());
                this.nsClass = BitUtils.uint16(byteBuffer.getShort());
                if (n != 0) {
                    this.ttl = BitUtils.uint32(byteBuffer.getInt());
                    this.mRdata = new byte[BitUtils.uint16(byteBuffer.getShort())];
                    byteBuffer.get(this.mRdata);
                } else {
                    this.ttl = 0L;
                    this.mRdata = null;
                }
                return;
            }
            DnsPacket.this = new StringBuilder();
            ((StringBuilder)DnsPacket.this).append("Parse name fail, name size is too long: ");
            ((StringBuilder)DnsPacket.this).append(this.dName.length());
            throw new ParseException(((StringBuilder)DnsPacket.this).toString());
        }

        private String labelToString(byte[] arrby) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < arrby.length; ++i) {
                int n = BitUtils.uint8(arrby[i]);
                if (n > 32 && n < 127) {
                    if (n != 34 && n != 46 && n != 59 && n != 92 && n != 40 && n != 41 && n != 64 && n != 36) {
                        stringBuffer.append((char)n);
                        continue;
                    }
                    stringBuffer.append('\\');
                    stringBuffer.append((char)n);
                    continue;
                }
                stringBuffer.append('\\');
                this.byteFormat.format((long)n, stringBuffer, this.pos);
            }
            return stringBuffer.toString();
        }

        private String parseName(ByteBuffer object, int n) throws BufferUnderflowException, ParseException {
            if (n <= 128) {
                int n2 = BitUtils.uint8(((ByteBuffer)object).get());
                int n3 = n2 & 192;
                if (n2 == 0) {
                    return "";
                }
                if (n3 != 0 && n3 != 192) {
                    throw new ParseException("Parse name fail, bad label type");
                }
                if (n3 == 192) {
                    if ((n2 = ((n2 & -193) << 8) + BitUtils.uint8(((ByteBuffer)object).get())) < (n3 = ((Buffer)object).position()) - 2) {
                        ((Buffer)object).position(n2);
                        String string2 = this.parseName((ByteBuffer)object, n + 1);
                        ((Buffer)object).position(n3);
                        return string2;
                    }
                    throw new ParseException("Parse compression name fail, invalid compression");
                }
                Object object2 = new byte[n2];
                ((ByteBuffer)object).get((byte[])object2);
                object2 = this.labelToString((byte[])object2);
                if (((String)object2).length() <= 63) {
                    if (TextUtils.isEmpty((CharSequence)(object = this.parseName((ByteBuffer)object, n + 1)))) {
                        object = object2;
                    } else {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append((String)object2);
                        stringBuilder.append(".");
                        stringBuilder.append((String)object);
                        object = stringBuilder.toString();
                    }
                    return object;
                }
                throw new ParseException("Parse name fail, invalid label length");
            }
            throw new ParseException("Failed to parse name, too many labels");
        }

        public byte[] getRR() {
            Object object = this.mRdata;
            object = object == null ? null : (byte[])object.clone();
            return object;
        }
    }

}

