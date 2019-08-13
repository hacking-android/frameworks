/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DERGeneralizedTime;
import com.android.org.bouncycastle.asn1.DERIA5String;
import com.android.org.bouncycastle.asn1.DERPrintableString;
import com.android.org.bouncycastle.asn1.DERUTF8String;
import com.android.org.bouncycastle.asn1.x509.X509Name;
import com.android.org.bouncycastle.asn1.x509.X509NameEntryConverter;
import java.io.IOException;

public class X509DefaultEntryConverter
extends X509NameEntryConverter {
    @Override
    public ASN1Primitive getConvertedValue(ASN1ObjectIdentifier aSN1ObjectIdentifier, String object) {
        if (((String)object).length() != 0 && ((String)object).charAt(0) == '#') {
            try {
                object = this.convertHexEncoded((String)object, 1);
                return object;
            }
            catch (IOException iOException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("can't recode value for oid ");
                stringBuilder.append(aSN1ObjectIdentifier.getId());
                throw new RuntimeException(stringBuilder.toString());
            }
        }
        String string = object;
        if (((String)object).length() != 0) {
            string = object;
            if (((String)object).charAt(0) == '\\') {
                string = ((String)object).substring(1);
            }
        }
        if (!aSN1ObjectIdentifier.equals(X509Name.EmailAddress) && !aSN1ObjectIdentifier.equals(X509Name.DC)) {
            if (aSN1ObjectIdentifier.equals(X509Name.DATE_OF_BIRTH)) {
                return new DERGeneralizedTime(string);
            }
            if (!(aSN1ObjectIdentifier.equals(X509Name.C) || aSN1ObjectIdentifier.equals(X509Name.SN) || aSN1ObjectIdentifier.equals(X509Name.DN_QUALIFIER) || aSN1ObjectIdentifier.equals(X509Name.TELEPHONE_NUMBER))) {
                return new DERUTF8String(string);
            }
            return new DERPrintableString(string);
        }
        return new DERIA5String(string);
    }
}

