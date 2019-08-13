/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.x509.Extensions;
import com.android.org.bouncycastle.asn1.x509.GeneralName;
import com.android.org.bouncycastle.util.Strings;

public class GeneralNames
extends ASN1Object {
    private final GeneralName[] names;

    private GeneralNames(ASN1Sequence aSN1Sequence) {
        this.names = new GeneralName[aSN1Sequence.size()];
        for (int i = 0; i != aSN1Sequence.size(); ++i) {
            this.names[i] = GeneralName.getInstance(aSN1Sequence.getObjectAt(i));
        }
    }

    public GeneralNames(GeneralName generalName) {
        this.names = new GeneralName[]{generalName};
    }

    public GeneralNames(GeneralName[] arrgeneralName) {
        this.names = this.copy(arrgeneralName);
    }

    private GeneralName[] copy(GeneralName[] arrgeneralName) {
        GeneralName[] arrgeneralName2 = new GeneralName[arrgeneralName.length];
        System.arraycopy(arrgeneralName, 0, arrgeneralName2, 0, arrgeneralName2.length);
        return arrgeneralName2;
    }

    public static GeneralNames fromExtensions(Extensions extensions, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return GeneralNames.getInstance(extensions.getExtensionParsedValue(aSN1ObjectIdentifier));
    }

    public static GeneralNames getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return GeneralNames.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static GeneralNames getInstance(Object object) {
        if (object instanceof GeneralNames) {
            return (GeneralNames)object;
        }
        if (object != null) {
            return new GeneralNames(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public GeneralName[] getNames() {
        return this.copy(this.names);
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return new DERSequence(this.names);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String string = Strings.lineSeparator();
        stringBuffer.append("GeneralNames:");
        stringBuffer.append(string);
        for (int i = 0; i != this.names.length; ++i) {
            stringBuffer.append("    ");
            stringBuffer.append(this.names[i]);
            stringBuffer.append(string);
        }
        return stringBuffer.toString();
    }
}

