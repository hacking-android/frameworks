/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DEROutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BEROutputStream
extends DEROutputStream {
    public BEROutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    public void writeObject(Object object) throws IOException {
        block5 : {
            block3 : {
                block4 : {
                    block2 : {
                        if (object != null) break block2;
                        this.writeNull();
                        break block3;
                    }
                    if (!(object instanceof ASN1Primitive)) break block4;
                    ((ASN1Primitive)object).encode(this);
                    break block3;
                }
                if (!(object instanceof ASN1Encodable)) break block5;
                ((ASN1Encodable)object).toASN1Primitive().encode(this);
            }
            return;
        }
        throw new IOException("object not BEREncodable");
    }
}

