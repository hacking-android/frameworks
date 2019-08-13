/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.util;

import com.android.org.bouncycastle.asn1.ASN1ApplicationSpecific;
import com.android.org.bouncycastle.asn1.ASN1Boolean;
import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Enumerated;
import com.android.org.bouncycastle.asn1.ASN1External;
import com.android.org.bouncycastle.asn1.ASN1GeneralizedTime;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1Set;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.ASN1UTCTime;
import com.android.org.bouncycastle.asn1.BERApplicationSpecific;
import com.android.org.bouncycastle.asn1.BEROctetString;
import com.android.org.bouncycastle.asn1.BERSequence;
import com.android.org.bouncycastle.asn1.BERSet;
import com.android.org.bouncycastle.asn1.BERTaggedObject;
import com.android.org.bouncycastle.asn1.DERApplicationSpecific;
import com.android.org.bouncycastle.asn1.DERBMPString;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DERGraphicString;
import com.android.org.bouncycastle.asn1.DERIA5String;
import com.android.org.bouncycastle.asn1.DERNull;
import com.android.org.bouncycastle.asn1.DERPrintableString;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.DERSet;
import com.android.org.bouncycastle.asn1.DERT61String;
import com.android.org.bouncycastle.asn1.DERUTF8String;
import com.android.org.bouncycastle.asn1.DERVideotexString;
import com.android.org.bouncycastle.asn1.DERVisibleString;
import com.android.org.bouncycastle.asn1.DLApplicationSpecific;
import com.android.org.bouncycastle.util.Strings;
import com.android.org.bouncycastle.util.encoders.Hex;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Enumeration;

public class ASN1Dump {
    private static final int SAMPLE_SIZE = 32;
    private static final String TAB = "    ";

    static void _dumpAsString(String object, boolean bl, ASN1Primitive object2, StringBuffer stringBuffer) {
        String string = Strings.lineSeparator();
        if (object2 instanceof ASN1Sequence) {
            Enumeration enumeration = ((ASN1Sequence)object2).getObjects();
            CharSequence charSequence = new StringBuilder();
            charSequence.append((String)object);
            charSequence.append(TAB);
            charSequence = charSequence.toString();
            stringBuffer.append((String)object);
            if (object2 instanceof BERSequence) {
                stringBuffer.append("BER Sequence");
            } else if (object2 instanceof DERSequence) {
                stringBuffer.append("DER Sequence");
            } else {
                stringBuffer.append("Sequence");
            }
            stringBuffer.append(string);
            while (enumeration.hasMoreElements()) {
                object = enumeration.nextElement();
                if (object != null && !object.equals(DERNull.INSTANCE)) {
                    if (object instanceof ASN1Primitive) {
                        ASN1Dump._dumpAsString((String)charSequence, bl, (ASN1Primitive)object, stringBuffer);
                        continue;
                    }
                    ASN1Dump._dumpAsString((String)charSequence, bl, ((ASN1Encodable)object).toASN1Primitive(), stringBuffer);
                    continue;
                }
                stringBuffer.append((String)charSequence);
                stringBuffer.append("NULL");
                stringBuffer.append(string);
            }
        } else if (object2 instanceof ASN1TaggedObject) {
            CharSequence charSequence = new StringBuilder();
            charSequence.append((String)object);
            charSequence.append(TAB);
            charSequence = charSequence.toString();
            stringBuffer.append((String)object);
            if (object2 instanceof BERTaggedObject) {
                stringBuffer.append("BER Tagged [");
            } else {
                stringBuffer.append("Tagged [");
            }
            object = (ASN1TaggedObject)object2;
            stringBuffer.append(Integer.toString(((ASN1TaggedObject)object).getTagNo()));
            stringBuffer.append(']');
            if (!((ASN1TaggedObject)object).isExplicit()) {
                stringBuffer.append(" IMPLICIT ");
            }
            stringBuffer.append(string);
            if (((ASN1TaggedObject)object).isEmpty()) {
                stringBuffer.append((String)charSequence);
                stringBuffer.append("EMPTY");
                stringBuffer.append(string);
            } else {
                ASN1Dump._dumpAsString((String)charSequence, bl, ((ASN1TaggedObject)object).getObject(), stringBuffer);
            }
        } else if (object2 instanceof ASN1Set) {
            Enumeration enumeration = ((ASN1Set)object2).getObjects();
            CharSequence charSequence = new StringBuilder();
            charSequence.append((String)object);
            charSequence.append(TAB);
            charSequence = charSequence.toString();
            stringBuffer.append((String)object);
            if (object2 instanceof BERSet) {
                stringBuffer.append("BER Set");
            } else if (object2 instanceof DERSet) {
                stringBuffer.append("DER Set");
            } else {
                stringBuffer.append("Set");
            }
            stringBuffer.append(string);
            while (enumeration.hasMoreElements()) {
                object = enumeration.nextElement();
                if (object == null) {
                    stringBuffer.append((String)charSequence);
                    stringBuffer.append("NULL");
                    stringBuffer.append(string);
                    continue;
                }
                if (object instanceof ASN1Primitive) {
                    ASN1Dump._dumpAsString((String)charSequence, bl, (ASN1Primitive)object, stringBuffer);
                    continue;
                }
                ASN1Dump._dumpAsString((String)charSequence, bl, ((ASN1Encodable)object).toASN1Primitive(), stringBuffer);
            }
        } else if (object2 instanceof ASN1OctetString) {
            ASN1OctetString aSN1OctetString = (ASN1OctetString)object2;
            if (object2 instanceof BEROctetString) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append("BER Constructed Octet String[");
                ((StringBuilder)object2).append(aSN1OctetString.getOctets().length);
                ((StringBuilder)object2).append("] ");
                stringBuffer.append(((StringBuilder)object2).toString());
            } else {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append("DER Octet String[");
                ((StringBuilder)object2).append(aSN1OctetString.getOctets().length);
                ((StringBuilder)object2).append("] ");
                stringBuffer.append(((StringBuilder)object2).toString());
            }
            if (bl) {
                stringBuffer.append(ASN1Dump.dumpBinaryDataAsString((String)object, aSN1OctetString.getOctets()));
            } else {
                stringBuffer.append(string);
            }
        } else if (object2 instanceof ASN1ObjectIdentifier) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append("ObjectIdentifier(");
            stringBuilder.append(((ASN1ObjectIdentifier)object2).getId());
            stringBuilder.append(")");
            stringBuilder.append(string);
            stringBuffer.append(stringBuilder.toString());
        } else if (object2 instanceof ASN1Boolean) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append("Boolean(");
            stringBuilder.append(((ASN1Boolean)object2).isTrue());
            stringBuilder.append(")");
            stringBuilder.append(string);
            stringBuffer.append(stringBuilder.toString());
        } else if (object2 instanceof ASN1Integer) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append("Integer(");
            stringBuilder.append(((ASN1Integer)object2).getValue());
            stringBuilder.append(")");
            stringBuilder.append(string);
            stringBuffer.append(stringBuilder.toString());
        } else if (object2 instanceof DERBitString) {
            DERBitString dERBitString = (DERBitString)object2;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append("DER Bit String[");
            ((StringBuilder)object2).append(dERBitString.getBytes().length);
            ((StringBuilder)object2).append(", ");
            ((StringBuilder)object2).append(dERBitString.getPadBits());
            ((StringBuilder)object2).append("] ");
            stringBuffer.append(((StringBuilder)object2).toString());
            if (bl) {
                stringBuffer.append(ASN1Dump.dumpBinaryDataAsString((String)object, dERBitString.getBytes()));
            } else {
                stringBuffer.append(string);
            }
        } else if (object2 instanceof DERIA5String) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append("IA5String(");
            stringBuilder.append(((DERIA5String)object2).getString());
            stringBuilder.append(") ");
            stringBuilder.append(string);
            stringBuffer.append(stringBuilder.toString());
        } else if (object2 instanceof DERUTF8String) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append("UTF8String(");
            stringBuilder.append(((DERUTF8String)object2).getString());
            stringBuilder.append(") ");
            stringBuilder.append(string);
            stringBuffer.append(stringBuilder.toString());
        } else if (object2 instanceof DERPrintableString) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append("PrintableString(");
            stringBuilder.append(((DERPrintableString)object2).getString());
            stringBuilder.append(") ");
            stringBuilder.append(string);
            stringBuffer.append(stringBuilder.toString());
        } else if (object2 instanceof DERVisibleString) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append("VisibleString(");
            stringBuilder.append(((DERVisibleString)object2).getString());
            stringBuilder.append(") ");
            stringBuilder.append(string);
            stringBuffer.append(stringBuilder.toString());
        } else if (object2 instanceof DERBMPString) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append("BMPString(");
            stringBuilder.append(((DERBMPString)object2).getString());
            stringBuilder.append(") ");
            stringBuilder.append(string);
            stringBuffer.append(stringBuilder.toString());
        } else if (object2 instanceof DERT61String) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append("T61String(");
            stringBuilder.append(((DERT61String)object2).getString());
            stringBuilder.append(") ");
            stringBuilder.append(string);
            stringBuffer.append(stringBuilder.toString());
        } else if (object2 instanceof DERGraphicString) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append("GraphicString(");
            stringBuilder.append(((DERGraphicString)object2).getString());
            stringBuilder.append(") ");
            stringBuilder.append(string);
            stringBuffer.append(stringBuilder.toString());
        } else if (object2 instanceof DERVideotexString) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append("VideotexString(");
            stringBuilder.append(((DERVideotexString)object2).getString());
            stringBuilder.append(") ");
            stringBuilder.append(string);
            stringBuffer.append(stringBuilder.toString());
        } else if (object2 instanceof ASN1UTCTime) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append("UTCTime(");
            stringBuilder.append(((ASN1UTCTime)object2).getTime());
            stringBuilder.append(") ");
            stringBuilder.append(string);
            stringBuffer.append(stringBuilder.toString());
        } else if (object2 instanceof ASN1GeneralizedTime) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append("GeneralizedTime(");
            stringBuilder.append(((ASN1GeneralizedTime)object2).getTime());
            stringBuilder.append(") ");
            stringBuilder.append(string);
            stringBuffer.append(stringBuilder.toString());
        } else if (object2 instanceof BERApplicationSpecific) {
            stringBuffer.append(ASN1Dump.outputApplicationSpecific("BER", (String)object, bl, (ASN1Primitive)object2, string));
        } else if (object2 instanceof DERApplicationSpecific) {
            stringBuffer.append(ASN1Dump.outputApplicationSpecific("DER", (String)object, bl, (ASN1Primitive)object2, string));
        } else if (object2 instanceof DLApplicationSpecific) {
            stringBuffer.append(ASN1Dump.outputApplicationSpecific("", (String)object, bl, (ASN1Primitive)object2, string));
        } else if (object2 instanceof ASN1Enumerated) {
            ASN1Enumerated aSN1Enumerated = (ASN1Enumerated)object2;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append("DER Enumerated(");
            ((StringBuilder)object2).append(aSN1Enumerated.getValue());
            ((StringBuilder)object2).append(")");
            ((StringBuilder)object2).append(string);
            stringBuffer.append(((StringBuilder)object2).toString());
        } else if (object2 instanceof ASN1External) {
            object2 = (ASN1External)object2;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append("External ");
            stringBuilder.append(string);
            stringBuffer.append(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append(TAB);
            object = stringBuilder.toString();
            if (((ASN1External)object2).getDirectReference() != null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append((String)object);
                stringBuilder.append("Direct Reference: ");
                stringBuilder.append(((ASN1External)object2).getDirectReference().getId());
                stringBuilder.append(string);
                stringBuffer.append(stringBuilder.toString());
            }
            if (((ASN1External)object2).getIndirectReference() != null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append((String)object);
                stringBuilder.append("Indirect Reference: ");
                stringBuilder.append(((ASN1External)object2).getIndirectReference().toString());
                stringBuilder.append(string);
                stringBuffer.append(stringBuilder.toString());
            }
            if (((ASN1External)object2).getDataValueDescriptor() != null) {
                ASN1Dump._dumpAsString((String)object, bl, ((ASN1External)object2).getDataValueDescriptor(), stringBuffer);
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append("Encoding: ");
            stringBuilder.append(((ASN1External)object2).getEncoding());
            stringBuilder.append(string);
            stringBuffer.append(stringBuilder.toString());
            ASN1Dump._dumpAsString((String)object, bl, ((ASN1External)object2).getExternalContent(), stringBuffer);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append(object2.toString());
            stringBuilder.append(string);
            stringBuffer.append(stringBuilder.toString());
        }
    }

    private static String calculateAscString(byte[] arrby, int n, int n2) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = n; i != n + n2; ++i) {
            if (arrby[i] < 32 || arrby[i] > 126) continue;
            stringBuffer.append((char)arrby[i]);
        }
        return stringBuffer.toString();
    }

    public static String dumpAsString(Object object) {
        return ASN1Dump.dumpAsString(object, false);
    }

    public static String dumpAsString(Object object, boolean bl) {
        AbstractStringBuilder abstractStringBuilder;
        block4 : {
            block3 : {
                block2 : {
                    abstractStringBuilder = new StringBuffer();
                    if (!(object instanceof ASN1Primitive)) break block2;
                    ASN1Dump._dumpAsString("", bl, (ASN1Primitive)object, (StringBuffer)abstractStringBuilder);
                    break block3;
                }
                if (!(object instanceof ASN1Encodable)) break block4;
                ASN1Dump._dumpAsString("", bl, ((ASN1Encodable)object).toASN1Primitive(), (StringBuffer)abstractStringBuilder);
            }
            return ((StringBuffer)abstractStringBuilder).toString();
        }
        abstractStringBuilder = new StringBuilder();
        ((StringBuilder)abstractStringBuilder).append("unknown object type ");
        ((StringBuilder)abstractStringBuilder).append(object.toString());
        return ((StringBuilder)abstractStringBuilder).toString();
    }

    private static String dumpBinaryDataAsString(String string, byte[] arrby) {
        String string2 = Strings.lineSeparator();
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(TAB);
        string = stringBuilder.toString();
        stringBuffer.append(string2);
        for (int i = 0; i < arrby.length; i += 32) {
            if (arrby.length - i > 32) {
                stringBuffer.append(string);
                stringBuffer.append(Strings.fromByteArray(Hex.encode(arrby, i, 32)));
                stringBuffer.append(TAB);
                stringBuffer.append(ASN1Dump.calculateAscString(arrby, i, 32));
                stringBuffer.append(string2);
                continue;
            }
            stringBuffer.append(string);
            stringBuffer.append(Strings.fromByteArray(Hex.encode(arrby, i, arrby.length - i)));
            for (int j = arrby.length - i; j != 32; ++j) {
                stringBuffer.append("  ");
            }
            stringBuffer.append(TAB);
            stringBuffer.append(ASN1Dump.calculateAscString(arrby, i, arrby.length - i));
            stringBuffer.append(string2);
        }
        return stringBuffer.toString();
    }

    private static String outputApplicationSpecific(String object, String string, boolean bl, ASN1Primitive object2, String charSequence) {
        ASN1ApplicationSpecific aSN1ApplicationSpecific = ASN1ApplicationSpecific.getInstance(object2);
        object2 = new StringBuffer();
        if (aSN1ApplicationSpecific.isConstructed()) {
            try {
                ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(aSN1ApplicationSpecific.getObject(16));
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append((String)object);
                stringBuilder.append(" ApplicationSpecific[");
                stringBuilder.append(aSN1ApplicationSpecific.getApplicationTag());
                stringBuilder.append("]");
                stringBuilder.append((String)charSequence);
                ((StringBuffer)object2).append(stringBuilder.toString());
                object = aSN1Sequence.getObjects();
                while (object.hasMoreElements()) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(string);
                    ((StringBuilder)charSequence).append(TAB);
                    ASN1Dump._dumpAsString(((StringBuilder)charSequence).toString(), bl, (ASN1Primitive)object.nextElement(), (StringBuffer)object2);
                }
            }
            catch (IOException iOException) {
                ((StringBuffer)object2).append(iOException);
            }
            return ((StringBuffer)object2).toString();
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(string);
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append(" ApplicationSpecific[");
        ((StringBuilder)object2).append(aSN1ApplicationSpecific.getApplicationTag());
        ((StringBuilder)object2).append("] (");
        ((StringBuilder)object2).append(Strings.fromByteArray(Hex.encode(aSN1ApplicationSpecific.getContents())));
        ((StringBuilder)object2).append(")");
        ((StringBuilder)object2).append((String)charSequence);
        return ((StringBuilder)object2).toString();
    }
}

