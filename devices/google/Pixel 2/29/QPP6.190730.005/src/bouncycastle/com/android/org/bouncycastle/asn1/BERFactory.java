/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.BERSequence;
import com.android.org.bouncycastle.asn1.BERSet;

class BERFactory {
    static final BERSequence EMPTY_SEQUENCE = new BERSequence();
    static final BERSet EMPTY_SET = new BERSet();

    BERFactory() {
    }

    static BERSequence createSequence(ASN1EncodableVector object) {
        object = ((ASN1EncodableVector)object).size() < 1 ? EMPTY_SEQUENCE : new BERSequence((ASN1EncodableVector)object);
        return object;
    }

    static BERSet createSet(ASN1EncodableVector object) {
        object = ((ASN1EncodableVector)object).size() < 1 ? EMPTY_SET : new BERSet((ASN1EncodableVector)object);
        return object;
    }
}

