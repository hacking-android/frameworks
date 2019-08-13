/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Exception;
import com.android.org.bouncycastle.asn1.ASN1ParsingException;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1StreamParser;
import com.android.org.bouncycastle.asn1.DLExternal;
import com.android.org.bouncycastle.asn1.InMemoryRepresentable;
import java.io.IOException;

public class DERExternalParser
implements ASN1Encodable,
InMemoryRepresentable {
    private ASN1StreamParser _parser;

    public DERExternalParser(ASN1StreamParser aSN1StreamParser) {
        this._parser = aSN1StreamParser;
    }

    @Override
    public ASN1Primitive getLoadedObject() throws IOException {
        try {
            DLExternal dLExternal = new DLExternal(this._parser.readVector());
            return dLExternal;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new ASN1Exception(illegalArgumentException.getMessage(), illegalArgumentException);
        }
    }

    public ASN1Encodable readObject() throws IOException {
        return this._parser.readObject();
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        try {
            ASN1Primitive aSN1Primitive = this.getLoadedObject();
            return aSN1Primitive;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new ASN1ParsingException("unable to get DER object", illegalArgumentException);
        }
        catch (IOException iOException) {
            throw new ASN1ParsingException("unable to get DER object", iOException);
        }
    }
}

