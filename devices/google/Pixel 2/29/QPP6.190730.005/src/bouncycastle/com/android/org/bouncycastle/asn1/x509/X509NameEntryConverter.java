/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DERPrintableString;
import com.android.org.bouncycastle.util.Strings;
import java.io.IOException;

public abstract class X509NameEntryConverter {
    protected boolean canBePrintable(String string) {
        return DERPrintableString.isPrintableString(string);
    }

    protected ASN1Primitive convertHexEncoded(String string, int n) throws IOException {
        string = Strings.toLowerCase(string);
        byte[] arrby = new byte[(string.length() - n) / 2];
        for (int i = 0; i != arrby.length; ++i) {
            char c = string.charAt(i * 2 + n);
            char c2 = string.charAt(i * 2 + n + 1);
            arrby[i] = c < 'a' ? (byte)((byte)(c - 48 << 4)) : (byte)((byte)(c - 97 + 10 << 4));
            arrby[i] = c2 < 'a' ? (byte)((byte)(arrby[i] | (byte)(c2 - 48))) : (byte)((byte)(arrby[i] | (byte)(c2 - 97 + 10)));
        }
        return new ASN1InputStream(arrby).readObject();
    }

    public abstract ASN1Primitive getConvertedValue(ASN1ObjectIdentifier var1, String var2);
}

