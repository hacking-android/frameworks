/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1SequenceParser;
import com.android.org.bouncycastle.asn1.ASN1StreamParser;
import com.android.org.bouncycastle.asn1.BERSequence;
import java.io.IOException;

public class BERSequenceParser
implements ASN1SequenceParser {
    private ASN1StreamParser _parser;

    BERSequenceParser(ASN1StreamParser aSN1StreamParser) {
        this._parser = aSN1StreamParser;
    }

    @Override
    public ASN1Primitive getLoadedObject() throws IOException {
        return new BERSequence(this._parser.readVector());
    }

    @Override
    public ASN1Encodable readObject() throws IOException {
        return this._parser.readObject();
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        try {
            ASN1Primitive aSN1Primitive = this.getLoadedObject();
            return aSN1Primitive;
        }
        catch (IOException iOException) {
            throw new IllegalStateException(iOException.getMessage());
        }
    }
}

