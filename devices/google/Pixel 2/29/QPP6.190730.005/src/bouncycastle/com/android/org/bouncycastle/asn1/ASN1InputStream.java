/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1BitString;
import com.android.org.bouncycastle.asn1.ASN1Boolean;
import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Enumerated;
import com.android.org.bouncycastle.asn1.ASN1Exception;
import com.android.org.bouncycastle.asn1.ASN1GeneralizedTime;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1StreamParser;
import com.android.org.bouncycastle.asn1.ASN1UTCTime;
import com.android.org.bouncycastle.asn1.BERApplicationSpecificParser;
import com.android.org.bouncycastle.asn1.BEROctetString;
import com.android.org.bouncycastle.asn1.BEROctetStringParser;
import com.android.org.bouncycastle.asn1.BERSequenceParser;
import com.android.org.bouncycastle.asn1.BERSetParser;
import com.android.org.bouncycastle.asn1.BERTaggedObjectParser;
import com.android.org.bouncycastle.asn1.BERTags;
import com.android.org.bouncycastle.asn1.DERBMPString;
import com.android.org.bouncycastle.asn1.DERExternalParser;
import com.android.org.bouncycastle.asn1.DERFactory;
import com.android.org.bouncycastle.asn1.DERGeneralString;
import com.android.org.bouncycastle.asn1.DERGraphicString;
import com.android.org.bouncycastle.asn1.DERIA5String;
import com.android.org.bouncycastle.asn1.DERNull;
import com.android.org.bouncycastle.asn1.DERNumericString;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.DERPrintableString;
import com.android.org.bouncycastle.asn1.DERT61String;
import com.android.org.bouncycastle.asn1.DERUTF8String;
import com.android.org.bouncycastle.asn1.DERUniversalString;
import com.android.org.bouncycastle.asn1.DERVideotexString;
import com.android.org.bouncycastle.asn1.DERVisibleString;
import com.android.org.bouncycastle.asn1.DLApplicationSpecific;
import com.android.org.bouncycastle.asn1.DLExternal;
import com.android.org.bouncycastle.asn1.DefiniteLengthInputStream;
import com.android.org.bouncycastle.asn1.IndefiniteLengthInputStream;
import com.android.org.bouncycastle.asn1.LazyEncodedSequence;
import com.android.org.bouncycastle.asn1.StreamUtil;
import com.android.org.bouncycastle.util.io.Streams;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ASN1InputStream
extends FilterInputStream
implements BERTags {
    private final boolean lazyEvaluate;
    private final int limit;
    private final byte[][] tmpBuffers;

    @UnsupportedAppUsage
    public ASN1InputStream(InputStream inputStream) {
        this(inputStream, StreamUtil.findLimit(inputStream));
    }

    public ASN1InputStream(InputStream inputStream, int n) {
        this(inputStream, n, false);
    }

    public ASN1InputStream(InputStream inputStream, int n, boolean bl) {
        super(inputStream);
        this.limit = n;
        this.lazyEvaluate = bl;
        this.tmpBuffers = new byte[11][];
    }

    public ASN1InputStream(InputStream inputStream, boolean bl) {
        this(inputStream, StreamUtil.findLimit(inputStream), bl);
    }

    @UnsupportedAppUsage
    public ASN1InputStream(byte[] arrby) {
        this((InputStream)new ByteArrayInputStream(arrby), arrby.length);
    }

    public ASN1InputStream(byte[] arrby, boolean bl) {
        this(new ByteArrayInputStream(arrby), arrby.length, bl);
    }

    static ASN1Primitive createPrimitiveDERObject(int n, DefiniteLengthInputStream object, byte[][] arrby) throws IOException {
        if (n != 10) {
            if (n != 12) {
                if (n != 30) {
                    switch (n) {
                        default: {
                            switch (n) {
                                default: {
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("unknown tag ");
                                    ((StringBuilder)object).append(n);
                                    ((StringBuilder)object).append(" encountered");
                                    throw new IOException(((StringBuilder)object).toString());
                                }
                                case 28: {
                                    return new DERUniversalString(((DefiniteLengthInputStream)object).toByteArray());
                                }
                                case 27: {
                                    return new DERGeneralString(((DefiniteLengthInputStream)object).toByteArray());
                                }
                                case 26: {
                                    return new DERVisibleString(((DefiniteLengthInputStream)object).toByteArray());
                                }
                                case 25: {
                                    return new DERGraphicString(((DefiniteLengthInputStream)object).toByteArray());
                                }
                                case 24: {
                                    return new ASN1GeneralizedTime(((DefiniteLengthInputStream)object).toByteArray());
                                }
                                case 23: {
                                    return new ASN1UTCTime(((DefiniteLengthInputStream)object).toByteArray());
                                }
                                case 22: {
                                    return new DERIA5String(((DefiniteLengthInputStream)object).toByteArray());
                                }
                                case 21: {
                                    return new DERVideotexString(((DefiniteLengthInputStream)object).toByteArray());
                                }
                                case 20: {
                                    return new DERT61String(((DefiniteLengthInputStream)object).toByteArray());
                                }
                                case 19: {
                                    return new DERPrintableString(((DefiniteLengthInputStream)object).toByteArray());
                                }
                                case 18: 
                            }
                            return new DERNumericString(((DefiniteLengthInputStream)object).toByteArray());
                        }
                        case 6: {
                            return ASN1ObjectIdentifier.fromOctetString(ASN1InputStream.getBuffer((DefiniteLengthInputStream)object, arrby));
                        }
                        case 5: {
                            return DERNull.INSTANCE;
                        }
                        case 4: {
                            return new DEROctetString(((DefiniteLengthInputStream)object).toByteArray());
                        }
                        case 3: {
                            return ASN1BitString.fromInputStream(((DefiniteLengthInputStream)object).getRemaining(), (InputStream)object);
                        }
                        case 2: {
                            return new ASN1Integer(((DefiniteLengthInputStream)object).toByteArray(), false);
                        }
                        case 1: 
                    }
                    return ASN1Boolean.fromOctetString(ASN1InputStream.getBuffer((DefiniteLengthInputStream)object, arrby));
                }
                return new DERBMPString(ASN1InputStream.getBMPCharBuffer((DefiniteLengthInputStream)object));
            }
            return new DERUTF8String(((DefiniteLengthInputStream)object).toByteArray());
        }
        return ASN1Enumerated.fromOctetString(ASN1InputStream.getBuffer((DefiniteLengthInputStream)object, arrby));
    }

    private static char[] getBMPCharBuffer(DefiniteLengthInputStream definiteLengthInputStream) throws IOException {
        int n;
        int n2;
        int n3 = definiteLengthInputStream.getRemaining() / 2;
        char[] arrc = new char[n3];
        for (int i = 0; i < n3 && (n = definiteLengthInputStream.read()) >= 0 && (n2 = definiteLengthInputStream.read()) >= 0; ++i) {
            arrc[i] = (char)(n << 8 | n2 & 255);
        }
        return arrc;
    }

    private static byte[] getBuffer(DefiniteLengthInputStream definiteLengthInputStream, byte[][] arrby) throws IOException {
        int n = definiteLengthInputStream.getRemaining();
        if (definiteLengthInputStream.getRemaining() < arrby.length) {
            byte[] arrby2;
            byte[] arrby3 = arrby2 = arrby[n];
            if (arrby2 == null) {
                arrby3 = new byte[n];
                arrby[n] = arrby3;
            }
            Streams.readFully(definiteLengthInputStream, arrby3);
            return arrby3;
        }
        return definiteLengthInputStream.toByteArray();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static int readLength(InputStream object, int n) throws IOException {
        int n2 = ((InputStream)object).read();
        if (n2 < 0) throw new EOFException("EOF found when length expected");
        if (n2 == 128) {
            return -1;
        }
        int n3 = n2;
        if (n2 <= 127) return n3;
        int n4 = n2 & 127;
        if (n4 <= 4) {
            n3 = 0;
            for (n2 = 0; n2 < n4; ++n2) {
                int n5 = ((InputStream)object).read();
                if (n5 < 0) throw new EOFException("EOF found reading length");
                n3 = (n3 << 8) + n5;
            }
            if (n3 < 0) throw new IOException("corrupted stream - negative length found");
            if (n3 < n) return n3;
            throw new IOException("corrupted stream - out of bounds length found");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("DER length more than 4 bytes: ");
        ((StringBuilder)object).append(n4);
        throw new IOException(((StringBuilder)object).toString());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static int readTagNumber(InputStream inputStream, int n) throws IOException {
        int n2;
        n = n2 = n & 31;
        if (n2 != 31) return n;
        n = 0;
        n2 = inputStream.read();
        if ((n2 & 127) == 0) throw new IOException("corrupted stream - invalid high tag number found");
        while (n2 >= 0 && (n2 & 128) != 0) {
            n = (n | n2 & 127) << 7;
            n2 = inputStream.read();
        }
        if (n2 < 0) throw new EOFException("EOF found inside tag value.");
        n |= n2 & 127;
        return n;
    }

    ASN1EncodableVector buildDEREncodableVector(DefiniteLengthInputStream definiteLengthInputStream) throws IOException {
        return new ASN1InputStream(definiteLengthInputStream).buildEncodableVector();
    }

    ASN1EncodableVector buildEncodableVector() throws IOException {
        ASN1Primitive aSN1Primitive;
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        while ((aSN1Primitive = this.readObject()) != null) {
            aSN1EncodableVector.add(aSN1Primitive);
        }
        return aSN1EncodableVector;
    }

    protected ASN1Primitive buildObject(int n, int n2, int n3) throws IOException {
        boolean bl = (n & 32) != 0;
        Object object = new DefiniteLengthInputStream(this, n3);
        if ((n & 64) != 0) {
            return new DLApplicationSpecific(bl, n2, ((DefiniteLengthInputStream)object).toByteArray());
        }
        if ((n & 128) != 0) {
            return new ASN1StreamParser((InputStream)object).readTaggedObject(bl, n2);
        }
        if (bl) {
            if (n2 != 4) {
                if (n2 != 8) {
                    if (n2 != 16) {
                        if (n2 == 17) {
                            return DERFactory.createSet(this.buildDEREncodableVector((DefiniteLengthInputStream)object));
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("unknown tag ");
                        ((StringBuilder)object).append(n2);
                        ((StringBuilder)object).append(" encountered");
                        throw new IOException(((StringBuilder)object).toString());
                    }
                    if (this.lazyEvaluate) {
                        return new LazyEncodedSequence(((DefiniteLengthInputStream)object).toByteArray());
                    }
                    return DERFactory.createSequence(this.buildDEREncodableVector((DefiniteLengthInputStream)object));
                }
                return new DLExternal(this.buildDEREncodableVector((DefiniteLengthInputStream)object));
            }
            object = this.buildDEREncodableVector((DefiniteLengthInputStream)object);
            ASN1OctetString[] arraSN1OctetString = new ASN1OctetString[((ASN1EncodableVector)object).size()];
            for (n = 0; n != arraSN1OctetString.length; ++n) {
                arraSN1OctetString[n] = (ASN1OctetString)((ASN1EncodableVector)object).get(n);
            }
            return new BEROctetString(arraSN1OctetString);
        }
        return ASN1InputStream.createPrimitiveDERObject(n2, (DefiniteLengthInputStream)object, this.tmpBuffers);
    }

    int getLimit() {
        return this.limit;
    }

    protected void readFully(byte[] arrby) throws IOException {
        if (Streams.readFully(this, arrby) == arrby.length) {
            return;
        }
        throw new EOFException("EOF encountered in middle of object");
    }

    protected int readLength() throws IOException {
        return ASN1InputStream.readLength(this, this.limit);
    }

    @UnsupportedAppUsage
    public ASN1Primitive readObject() throws IOException {
        int n = this.read();
        if (n <= 0) {
            if (n != 0) {
                return null;
            }
            throw new IOException("unexpected end-of-contents marker");
        }
        int n2 = ASN1InputStream.readTagNumber(this, n);
        boolean bl = (n & 32) != 0;
        int n3 = this.readLength();
        if (n3 < 0) {
            if (bl) {
                ASN1StreamParser aSN1StreamParser = new ASN1StreamParser(new IndefiniteLengthInputStream(this, this.limit), this.limit);
                if ((n & 64) != 0) {
                    return new BERApplicationSpecificParser(n2, aSN1StreamParser).getLoadedObject();
                }
                if ((n & 128) != 0) {
                    return new BERTaggedObjectParser(true, n2, aSN1StreamParser).getLoadedObject();
                }
                if (n2 != 4) {
                    if (n2 != 8) {
                        if (n2 != 16) {
                            if (n2 == 17) {
                                return new BERSetParser(aSN1StreamParser).getLoadedObject();
                            }
                            throw new IOException("unknown BER object encountered");
                        }
                        return new BERSequenceParser(aSN1StreamParser).getLoadedObject();
                    }
                    return new DERExternalParser(aSN1StreamParser).getLoadedObject();
                }
                return new BEROctetStringParser(aSN1StreamParser).getLoadedObject();
            }
            throw new IOException("indefinite-length primitive encoding encountered");
        }
        try {
            ASN1Primitive aSN1Primitive = this.buildObject(n, n2, n3);
            return aSN1Primitive;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new ASN1Exception("corrupted stream detected", illegalArgumentException);
        }
    }
}

