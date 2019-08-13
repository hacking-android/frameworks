/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1Set;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.DERSet;
import com.android.org.bouncycastle.asn1.DLSequence;
import com.android.org.bouncycastle.asn1.DLSet;

class DERFactory {
    static final ASN1Sequence EMPTY_SEQUENCE = new DERSequence();
    static final ASN1Set EMPTY_SET = new DERSet();

    DERFactory() {
    }

    static ASN1Sequence createSequence(ASN1EncodableVector object) {
        object = ((ASN1EncodableVector)object).size() < 1 ? EMPTY_SEQUENCE : new DLSequence((ASN1EncodableVector)object);
        return object;
    }

    static ASN1Set createSet(ASN1EncodableVector object) {
        object = ((ASN1EncodableVector)object).size() < 1 ? EMPTY_SET : new DLSet((ASN1EncodableVector)object);
        return object;
    }
}

