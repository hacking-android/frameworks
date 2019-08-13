/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.x509.extension;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1String;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.asn1.x509.Extension;
import com.android.org.bouncycastle.asn1.x509.GeneralName;
import com.android.org.bouncycastle.util.Integers;
import java.io.IOException;
import java.io.Serializable;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class X509ExtensionUtil {
    public static ASN1Primitive fromExtensionValue(byte[] arrby) throws IOException {
        return ASN1Primitive.fromByteArray(((ASN1OctetString)ASN1Primitive.fromByteArray(arrby)).getOctets());
    }

    private static Collection getAlternativeNames(byte[] object) throws CertificateParsingException {
        if (object == null) {
            return Collections.EMPTY_LIST;
        }
        try {
            Serializable serializable = new ArrayList();
            Object object2 = DERSequence.getInstance(X509ExtensionUtil.fromExtensionValue((byte[])object)).getObjects();
            while (object2.hasMoreElements()) {
                block11 : {
                    object = GeneralName.getInstance(object2.nextElement());
                    ArrayList<Object> arrayList = new ArrayList<Object>();
                    arrayList.add(Integers.valueOf(((GeneralName)object).getTagNo()));
                    switch (((GeneralName)object).getTagNo()) {
                        default: {
                            break block11;
                        }
                        case 8: {
                            arrayList.add(ASN1ObjectIdentifier.getInstance(((GeneralName)object).getName()).getId());
                            break;
                        }
                        case 7: {
                            arrayList.add(DEROctetString.getInstance(((GeneralName)object).getName()).getOctets());
                            break;
                        }
                        case 4: {
                            arrayList.add(X500Name.getInstance(((GeneralName)object).getName()).toString());
                            break;
                        }
                        case 1: 
                        case 2: 
                        case 6: {
                            arrayList.add(((ASN1String)((Object)((GeneralName)object).getName())).getString());
                            break;
                        }
                        case 0: 
                        case 3: 
                        case 5: {
                            arrayList.add(((GeneralName)object).getName().toASN1Primitive());
                        }
                    }
                    serializable.add(arrayList);
                    continue;
                }
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Bad tag number: ");
                ((StringBuilder)serializable).append(((GeneralName)object).getTagNo());
                object2 = new IOException(((StringBuilder)serializable).toString());
                throw object2;
            }
            object = Collections.unmodifiableCollection(serializable);
            return object;
        }
        catch (Exception exception) {
            throw new CertificateParsingException(exception.getMessage());
        }
    }

    public static Collection getIssuerAlternativeNames(X509Certificate x509Certificate) throws CertificateParsingException {
        return X509ExtensionUtil.getAlternativeNames(x509Certificate.getExtensionValue(Extension.issuerAlternativeName.getId()));
    }

    public static Collection getSubjectAlternativeNames(X509Certificate x509Certificate) throws CertificateParsingException {
        return X509ExtensionUtil.getAlternativeNames(x509Certificate.getExtensionValue(Extension.subjectAlternativeName.getId()));
    }
}

