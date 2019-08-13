/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import com.android.internal.util.HexDump;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class SmsHeader {
    public static final int ELT_ID_APPLICATION_PORT_ADDRESSING_16_BIT = 5;
    public static final int ELT_ID_APPLICATION_PORT_ADDRESSING_8_BIT = 4;
    public static final int ELT_ID_CHARACTER_SIZE_WVG_OBJECT = 25;
    public static final int ELT_ID_COMPRESSION_CONTROL = 22;
    public static final int ELT_ID_CONCATENATED_16_BIT_REFERENCE = 8;
    public static final int ELT_ID_CONCATENATED_8_BIT_REFERENCE = 0;
    public static final int ELT_ID_ENHANCED_VOICE_MAIL_INFORMATION = 35;
    public static final int ELT_ID_EXTENDED_OBJECT = 20;
    public static final int ELT_ID_EXTENDED_OBJECT_DATA_REQUEST_CMD = 26;
    public static final int ELT_ID_HYPERLINK_FORMAT_ELEMENT = 33;
    public static final int ELT_ID_LARGE_ANIMATION = 14;
    public static final int ELT_ID_LARGE_PICTURE = 16;
    public static final int ELT_ID_NATIONAL_LANGUAGE_LOCKING_SHIFT = 37;
    public static final int ELT_ID_NATIONAL_LANGUAGE_SINGLE_SHIFT = 36;
    public static final int ELT_ID_OBJECT_DISTR_INDICATOR = 23;
    public static final int ELT_ID_PREDEFINED_ANIMATION = 13;
    public static final int ELT_ID_PREDEFINED_SOUND = 11;
    public static final int ELT_ID_REPLY_ADDRESS_ELEMENT = 34;
    public static final int ELT_ID_REUSED_EXTENDED_OBJECT = 21;
    public static final int ELT_ID_RFC_822_EMAIL_HEADER = 32;
    public static final int ELT_ID_SMALL_ANIMATION = 15;
    public static final int ELT_ID_SMALL_PICTURE = 17;
    public static final int ELT_ID_SMSC_CONTROL_PARAMS = 6;
    public static final int ELT_ID_SPECIAL_SMS_MESSAGE_INDICATION = 1;
    public static final int ELT_ID_STANDARD_WVG_OBJECT = 24;
    public static final int ELT_ID_TEXT_FORMATTING = 10;
    public static final int ELT_ID_UDH_SOURCE_INDICATION = 7;
    public static final int ELT_ID_USER_DEFINED_SOUND = 12;
    public static final int ELT_ID_USER_PROMPT_INDICATOR = 19;
    public static final int ELT_ID_VARIABLE_PICTURE = 18;
    public static final int ELT_ID_WIRELESS_CTRL_MSG_PROTOCOL = 9;
    public static final int PORT_WAP_PUSH = 2948;
    public static final int PORT_WAP_WSP = 9200;
    @UnsupportedAppUsage
    public ConcatRef concatRef;
    @UnsupportedAppUsage
    public int languageShiftTable;
    @UnsupportedAppUsage
    public int languageTable;
    public ArrayList<MiscElt> miscEltList = new ArrayList();
    @UnsupportedAppUsage
    public PortAddrs portAddrs;
    public ArrayList<SpecialSmsMsg> specialSmsMsgList = new ArrayList();

    @UnsupportedAppUsage
    public static SmsHeader fromByteArray(byte[] object) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])object);
        object = new SmsHeader();
        while (byteArrayInputStream.available() > 0) {
            Object object2;
            int n = byteArrayInputStream.read();
            int n2 = byteArrayInputStream.read();
            if (n != 0) {
                if (n != 1) {
                    if (n != 4) {
                        if (n != 5) {
                            if (n != 8) {
                                if (n != 36) {
                                    if (n != 37) {
                                        object2 = new MiscElt();
                                        ((MiscElt)object2).id = n;
                                        ((MiscElt)object2).data = new byte[n2];
                                        byteArrayInputStream.read(((MiscElt)object2).data, 0, n2);
                                        object.miscEltList.add((MiscElt)object2);
                                        continue;
                                    }
                                    object.languageTable = byteArrayInputStream.read();
                                    continue;
                                }
                                object.languageShiftTable = byteArrayInputStream.read();
                                continue;
                            }
                            object2 = new ConcatRef();
                            ((ConcatRef)object2).refNumber = byteArrayInputStream.read() << 8 | byteArrayInputStream.read();
                            ((ConcatRef)object2).msgCount = byteArrayInputStream.read();
                            ((ConcatRef)object2).seqNumber = byteArrayInputStream.read();
                            ((ConcatRef)object2).isEightBits = false;
                            if (((ConcatRef)object2).msgCount == 0 || ((ConcatRef)object2).seqNumber == 0 || ((ConcatRef)object2).seqNumber > ((ConcatRef)object2).msgCount) continue;
                            object.concatRef = object2;
                            continue;
                        }
                        object2 = new PortAddrs();
                        ((PortAddrs)object2).destPort = byteArrayInputStream.read() << 8 | byteArrayInputStream.read();
                        ((PortAddrs)object2).origPort = byteArrayInputStream.read() << 8 | byteArrayInputStream.read();
                        ((PortAddrs)object2).areEightBits = false;
                        object.portAddrs = object2;
                        continue;
                    }
                    object2 = new PortAddrs();
                    ((PortAddrs)object2).destPort = byteArrayInputStream.read();
                    ((PortAddrs)object2).origPort = byteArrayInputStream.read();
                    ((PortAddrs)object2).areEightBits = true;
                    object.portAddrs = object2;
                    continue;
                }
                object2 = new SpecialSmsMsg();
                ((SpecialSmsMsg)object2).msgIndType = byteArrayInputStream.read();
                ((SpecialSmsMsg)object2).msgCount = byteArrayInputStream.read();
                object.specialSmsMsgList.add((SpecialSmsMsg)object2);
                continue;
            }
            object2 = new ConcatRef();
            ((ConcatRef)object2).refNumber = byteArrayInputStream.read();
            ((ConcatRef)object2).msgCount = byteArrayInputStream.read();
            ((ConcatRef)object2).seqNumber = byteArrayInputStream.read();
            ((ConcatRef)object2).isEightBits = true;
            if (((ConcatRef)object2).msgCount == 0 || ((ConcatRef)object2).seqNumber == 0 || ((ConcatRef)object2).seqNumber > ((ConcatRef)object2).msgCount) continue;
            object.concatRef = object2;
        }
        return object;
    }

    @UnsupportedAppUsage
    public static byte[] toByteArray(SmsHeader object) {
        PortAddrs portAddrs;
        if (((SmsHeader)object).portAddrs == null && ((SmsHeader)object).concatRef == null && ((SmsHeader)object).specialSmsMsgList.isEmpty() && ((SmsHeader)object).miscEltList.isEmpty() && ((SmsHeader)object).languageShiftTable == 0 && ((SmsHeader)object).languageTable == 0) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(140);
        ConcatRef object22 = ((SmsHeader)object).concatRef;
        if (object22 != null) {
            if (object22.isEightBits) {
                byteArrayOutputStream.write(0);
                byteArrayOutputStream.write(3);
                byteArrayOutputStream.write(object22.refNumber);
            } else {
                byteArrayOutputStream.write(8);
                byteArrayOutputStream.write(4);
                byteArrayOutputStream.write(object22.refNumber >>> 8);
                byteArrayOutputStream.write(object22.refNumber & 255);
            }
            byteArrayOutputStream.write(object22.msgCount);
            byteArrayOutputStream.write(object22.seqNumber);
        }
        if ((portAddrs = ((SmsHeader)object).portAddrs) != null) {
            if (portAddrs.areEightBits) {
                byteArrayOutputStream.write(4);
                byteArrayOutputStream.write(2);
                byteArrayOutputStream.write(portAddrs.destPort);
                byteArrayOutputStream.write(portAddrs.origPort);
            } else {
                byteArrayOutputStream.write(5);
                byteArrayOutputStream.write(4);
                byteArrayOutputStream.write(portAddrs.destPort >>> 8);
                byteArrayOutputStream.write(portAddrs.destPort & 255);
                byteArrayOutputStream.write(portAddrs.origPort >>> 8);
                byteArrayOutputStream.write(portAddrs.origPort & 255);
            }
        }
        if (((SmsHeader)object).languageShiftTable != 0) {
            byteArrayOutputStream.write(36);
            byteArrayOutputStream.write(1);
            byteArrayOutputStream.write(((SmsHeader)object).languageShiftTable);
        }
        if (((SmsHeader)object).languageTable != 0) {
            byteArrayOutputStream.write(37);
            byteArrayOutputStream.write(1);
            byteArrayOutputStream.write(((SmsHeader)object).languageTable);
        }
        for (SpecialSmsMsg specialSmsMsg : ((SmsHeader)object).specialSmsMsgList) {
            byteArrayOutputStream.write(1);
            byteArrayOutputStream.write(2);
            byteArrayOutputStream.write(specialSmsMsg.msgIndType & 255);
            byteArrayOutputStream.write(specialSmsMsg.msgCount & 255);
        }
        for (MiscElt miscElt : ((SmsHeader)object).miscEltList) {
            byteArrayOutputStream.write(miscElt.id);
            byteArrayOutputStream.write(miscElt.data.length);
            byteArrayOutputStream.write(miscElt.data, 0, miscElt.data.length);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public String toString() {
        StringBuilder stringBuilder;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("UserDataHeader ");
        stringBuilder2.append("{ ConcatRef ");
        if (this.concatRef == null) {
            stringBuilder2.append("unset");
        } else {
            StringBuilder object2 = new StringBuilder();
            object2.append("{ refNumber=");
            object2.append(this.concatRef.refNumber);
            stringBuilder2.append(object2.toString());
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(", msgCount=");
            stringBuilder3.append(this.concatRef.msgCount);
            stringBuilder2.append(stringBuilder3.toString());
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append(", seqNumber=");
            stringBuilder4.append(this.concatRef.seqNumber);
            stringBuilder2.append(stringBuilder4.toString());
            StringBuilder stringBuilder5 = new StringBuilder();
            stringBuilder5.append(", isEightBits=");
            stringBuilder5.append(this.concatRef.isEightBits);
            stringBuilder2.append(stringBuilder5.toString());
            stringBuilder2.append(" }");
        }
        stringBuilder2.append(", PortAddrs ");
        if (this.portAddrs == null) {
            stringBuilder2.append("unset");
        } else {
            StringBuilder stringBuilder6 = new StringBuilder();
            stringBuilder6.append("{ destPort=");
            stringBuilder6.append(this.portAddrs.destPort);
            stringBuilder2.append(stringBuilder6.toString());
            StringBuilder stringBuilder7 = new StringBuilder();
            stringBuilder7.append(", origPort=");
            stringBuilder7.append(this.portAddrs.origPort);
            stringBuilder2.append(stringBuilder7.toString());
            StringBuilder stringBuilder8 = new StringBuilder();
            stringBuilder8.append(", areEightBits=");
            stringBuilder8.append(this.portAddrs.areEightBits);
            stringBuilder2.append(stringBuilder8.toString());
            stringBuilder2.append(" }");
        }
        if (this.languageShiftTable != 0) {
            StringBuilder stringBuilder9 = new StringBuilder();
            stringBuilder9.append(", languageShiftTable=");
            stringBuilder9.append(this.languageShiftTable);
            stringBuilder2.append(stringBuilder9.toString());
        }
        if (this.languageTable != 0) {
            StringBuilder stringBuilder10 = new StringBuilder();
            stringBuilder10.append(", languageTable=");
            stringBuilder10.append(this.languageTable);
            stringBuilder2.append(stringBuilder10.toString());
        }
        for (SpecialSmsMsg specialSmsMsg : this.specialSmsMsgList) {
            stringBuilder2.append(", SpecialSmsMsg ");
            stringBuilder = new StringBuilder();
            stringBuilder.append("{ msgIndType=");
            stringBuilder.append(specialSmsMsg.msgIndType);
            stringBuilder2.append(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(", msgCount=");
            stringBuilder.append(specialSmsMsg.msgCount);
            stringBuilder2.append(stringBuilder.toString());
            stringBuilder2.append(" }");
        }
        for (MiscElt miscElt : this.miscEltList) {
            stringBuilder2.append(", MiscElt ");
            stringBuilder = new StringBuilder();
            stringBuilder.append("{ id=");
            stringBuilder.append(miscElt.id);
            stringBuilder2.append(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(", length=");
            stringBuilder.append(miscElt.data.length);
            stringBuilder2.append(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(", data=");
            stringBuilder.append(HexDump.toHexString(miscElt.data));
            stringBuilder2.append(stringBuilder.toString());
            stringBuilder2.append(" }");
        }
        stringBuilder2.append(" }");
        return stringBuilder2.toString();
    }

    public static class ConcatRef {
        public boolean isEightBits;
        @UnsupportedAppUsage
        public int msgCount;
        @UnsupportedAppUsage
        public int refNumber;
        @UnsupportedAppUsage
        public int seqNumber;
    }

    public static class MiscElt {
        public byte[] data;
        public int id;
    }

    public static class PortAddrs {
        public boolean areEightBits;
        @UnsupportedAppUsage
        public int destPort;
        @UnsupportedAppUsage
        public int origPort;
    }

    public static class SpecialSmsMsg {
        public int msgCount;
        public int msgIndType;
    }

}

