/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Exception;
import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.BERApplicationSpecificParser;
import com.android.org.bouncycastle.asn1.BERFactory;
import com.android.org.bouncycastle.asn1.BEROctetStringParser;
import com.android.org.bouncycastle.asn1.BERSequenceParser;
import com.android.org.bouncycastle.asn1.BERSetParser;
import com.android.org.bouncycastle.asn1.BERTaggedObject;
import com.android.org.bouncycastle.asn1.BERTaggedObjectParser;
import com.android.org.bouncycastle.asn1.DERExternalParser;
import com.android.org.bouncycastle.asn1.DERFactory;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.DEROctetStringParser;
import com.android.org.bouncycastle.asn1.DERSequenceParser;
import com.android.org.bouncycastle.asn1.DERSetParser;
import com.android.org.bouncycastle.asn1.DERTaggedObject;
import com.android.org.bouncycastle.asn1.DLApplicationSpecific;
import com.android.org.bouncycastle.asn1.DefiniteLengthInputStream;
import com.android.org.bouncycastle.asn1.InMemoryRepresentable;
import com.android.org.bouncycastle.asn1.IndefiniteLengthInputStream;
import com.android.org.bouncycastle.asn1.StreamUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ASN1StreamParser {
    private final InputStream _in;
    private final int _limit;
    private final byte[][] tmpBuffers;

    public ASN1StreamParser(InputStream inputStream) {
        this(inputStream, StreamUtil.findLimit(inputStream));
    }

    public ASN1StreamParser(InputStream inputStream, int n) {
        this._in = inputStream;
        this._limit = n;
        this.tmpBuffers = new byte[11][];
    }

    public ASN1StreamParser(byte[] arrby) {
        this(new ByteArrayInputStream(arrby), arrby.length);
    }

    private void set00Check(boolean bl) {
        InputStream inputStream = this._in;
        if (inputStream instanceof IndefiniteLengthInputStream) {
            ((IndefiniteLengthInputStream)inputStream).setEofOn00(bl);
        }
    }

    ASN1Encodable readImplicit(boolean bl, int n) throws IOException {
        InputStream inputStream;
        block11 : {
            block12 : {
                block13 : {
                    block10 : {
                        block7 : {
                            block8 : {
                                block9 : {
                                    inputStream = this._in;
                                    if (inputStream instanceof IndefiniteLengthInputStream) {
                                        if (bl) {
                                            return this.readIndef(n);
                                        }
                                        throw new IOException("indefinite-length primitive encoding encountered");
                                    }
                                    if (!bl) break block7;
                                    if (n == 4) break block8;
                                    if (n == 16) break block9;
                                    if (n == 17) {
                                        return new DERSetParser(this);
                                    }
                                    break block10;
                                }
                                return new DERSequenceParser(this);
                            }
                            return new BEROctetStringParser(this);
                        }
                        if (n == 4) break block11;
                        if (n == 16) break block12;
                        if (n == 17) break block13;
                    }
                    throw new ASN1Exception("implicit tagging not implemented");
                }
                throw new ASN1Exception("sequences must use constructed encoding (see X.690 8.9.1/8.10.1)");
            }
            throw new ASN1Exception("sets must use constructed encoding (see X.690 8.11.1/8.12.1)");
        }
        return new DEROctetStringParser((DefiniteLengthInputStream)inputStream);
    }

    ASN1Encodable readIndef(int n) throws IOException {
        if (n != 4) {
            if (n != 8) {
                if (n != 16) {
                    if (n == 17) {
                        return new BERSetParser(this);
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("unknown BER object encountered: 0x");
                    stringBuilder.append(Integer.toHexString(n));
                    throw new ASN1Exception(stringBuilder.toString());
                }
                return new BERSequenceParser(this);
            }
            return new DERExternalParser(this);
        }
        return new BEROctetStringParser(this);
    }

    public ASN1Encodable readObject() throws IOException {
        int n;
        int n2 = this._in.read();
        if (n2 == -1) {
            return null;
        }
        boolean bl = false;
        this.set00Check(false);
        int n3 = ASN1InputStream.readTagNumber(this._in, n2);
        if ((n2 & 32) != 0) {
            bl = true;
        }
        if ((n = ASN1InputStream.readLength(this._in, this._limit)) < 0) {
            if (bl) {
                ASN1StreamParser aSN1StreamParser = new ASN1StreamParser(new IndefiniteLengthInputStream(this._in, this._limit), this._limit);
                if ((n2 & 64) != 0) {
                    return new BERApplicationSpecificParser(n3, aSN1StreamParser);
                }
                if ((n2 & 128) != 0) {
                    return new BERTaggedObjectParser(true, n3, aSN1StreamParser);
                }
                return aSN1StreamParser.readIndef(n3);
            }
            throw new IOException("indefinite-length primitive encoding encountered");
        }
        Object object = new DefiniteLengthInputStream(this._in, n);
        if ((n2 & 64) != 0) {
            return new DLApplicationSpecific(bl, n3, ((DefiniteLengthInputStream)object).toByteArray());
        }
        if ((n2 & 128) != 0) {
            return new BERTaggedObjectParser(bl, n3, new ASN1StreamParser((InputStream)object));
        }
        if (bl) {
            if (n3 != 4) {
                if (n3 != 8) {
                    if (n3 != 16) {
                        if (n3 == 17) {
                            return new DERSetParser(new ASN1StreamParser((InputStream)object));
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("unknown tag ");
                        ((StringBuilder)object).append(n3);
                        ((StringBuilder)object).append(" encountered");
                        throw new IOException(((StringBuilder)object).toString());
                    }
                    return new DERSequenceParser(new ASN1StreamParser((InputStream)object));
                }
                return new DERExternalParser(new ASN1StreamParser((InputStream)object));
            }
            return new BEROctetStringParser(new ASN1StreamParser((InputStream)object));
        }
        if (n3 != 4) {
            try {
                object = ASN1InputStream.createPrimitiveDERObject(n3, (DefiniteLengthInputStream)object, this.tmpBuffers);
                return object;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw new ASN1Exception("corrupted stream detected", illegalArgumentException);
            }
        }
        return new DEROctetStringParser((DefiniteLengthInputStream)object);
    }

    ASN1Primitive readTaggedObject(boolean bl, int n) throws IOException {
        if (!bl) {
            return new DERTaggedObject(false, n, new DEROctetString(((DefiniteLengthInputStream)this._in).toByteArray()));
        }
        Object object = this.readVector();
        if (this._in instanceof IndefiniteLengthInputStream) {
            object = ((ASN1EncodableVector)object).size() == 1 ? new BERTaggedObject(true, n, ((ASN1EncodableVector)object).get(0)) : new BERTaggedObject(false, n, BERFactory.createSequence((ASN1EncodableVector)object));
            return object;
        }
        object = ((ASN1EncodableVector)object).size() == 1 ? new DERTaggedObject(true, n, ((ASN1EncodableVector)object).get(0)) : new DERTaggedObject(false, n, DERFactory.createSequence((ASN1EncodableVector)object));
        return object;
    }

    ASN1EncodableVector readVector() throws IOException {
        ASN1Encodable aSN1Encodable;
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        while ((aSN1Encodable = this.readObject()) != null) {
            if (aSN1Encodable instanceof InMemoryRepresentable) {
                aSN1EncodableVector.add(((InMemoryRepresentable)((Object)aSN1Encodable)).getLoadedObject());
                continue;
            }
            aSN1EncodableVector.add(aSN1Encodable.toASN1Primitive());
        }
        return aSN1EncodableVector;
    }
}

