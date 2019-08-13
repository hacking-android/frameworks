/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.bouncycastle.jce;

import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.asn1.x509.X509Name;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;
import java.security.Principal;
import java.util.Hashtable;
import java.util.Vector;

public class X509Principal
extends X509Name
implements Principal {
    public X509Principal(X500Name x500Name) {
        super((ASN1Sequence)x500Name.toASN1Primitive());
    }

    public X509Principal(X509Name x509Name) {
        super((ASN1Sequence)x509Name.toASN1Primitive());
    }

    public X509Principal(String string) {
        super(string);
    }

    public X509Principal(Hashtable hashtable) {
        super(hashtable);
    }

    public X509Principal(Vector vector, Hashtable hashtable) {
        super(vector, hashtable);
    }

    public X509Principal(Vector vector, Vector vector2) {
        super(vector, vector2);
    }

    public X509Principal(boolean bl, String string) {
        super(bl, string);
    }

    public X509Principal(boolean bl, Hashtable hashtable, String string) {
        super(bl, hashtable, string);
    }

    @UnsupportedAppUsage
    public X509Principal(byte[] arrby) throws IOException {
        super(X509Principal.readSequence(new ASN1InputStream(arrby)));
    }

    private static ASN1Sequence readSequence(ASN1InputStream object) throws IOException {
        try {
            object = ASN1Sequence.getInstance(((ASN1InputStream)object).readObject());
            return object;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("not an ASN.1 Sequence: ");
            stringBuilder.append(illegalArgumentException);
            throw new IOException(stringBuilder.toString());
        }
    }

    @Override
    public byte[] getEncoded() {
        try {
            byte[] arrby = this.getEncoded("DER");
            return arrby;
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException.toString());
        }
    }

    @Override
    public String getName() {
        return this.toString();
    }
}

