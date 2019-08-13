/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1ParsingException;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1StreamParser;
import com.android.org.bouncycastle.asn1.ASN1TaggedObjectParser;
import java.io.IOException;

public class BERTaggedObjectParser
implements ASN1TaggedObjectParser {
    private boolean _constructed;
    private ASN1StreamParser _parser;
    private int _tagNumber;

    BERTaggedObjectParser(boolean bl, int n, ASN1StreamParser aSN1StreamParser) {
        this._constructed = bl;
        this._tagNumber = n;
        this._parser = aSN1StreamParser;
    }

    @Override
    public ASN1Primitive getLoadedObject() throws IOException {
        return this._parser.readTaggedObject(this._constructed, this._tagNumber);
    }

    @Override
    public ASN1Encodable getObjectParser(int n, boolean bl) throws IOException {
        if (bl) {
            if (this._constructed) {
                return this._parser.readObject();
            }
            throw new IOException("Explicit tags must be constructed (see X.690 8.14.2)");
        }
        return this._parser.readImplicit(this._constructed, n);
    }

    @Override
    public int getTagNo() {
        return this._tagNumber;
    }

    public boolean isConstructed() {
        return this._constructed;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        try {
            ASN1Primitive aSN1Primitive = this.getLoadedObject();
            return aSN1Primitive;
        }
        catch (IOException iOException) {
            throw new ASN1ParsingException(iOException.getMessage());
        }
    }
}

