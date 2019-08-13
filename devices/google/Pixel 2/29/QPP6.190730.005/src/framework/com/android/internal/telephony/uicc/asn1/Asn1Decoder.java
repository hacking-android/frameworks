/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.asn1;

import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.telephony.uicc.asn1.Asn1Node;
import com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException;

public final class Asn1Decoder {
    private final int mEnd;
    private int mPosition;
    private final byte[] mSrc;

    public Asn1Decoder(String string2) {
        this(IccUtils.hexStringToBytes(string2));
    }

    public Asn1Decoder(byte[] arrby) {
        this(arrby, 0, arrby.length);
    }

    public Asn1Decoder(byte[] arrby, int n, int n2) {
        if (n >= 0 && n2 >= 0 && n + n2 <= arrby.length) {
            this.mSrc = arrby;
            this.mPosition = n;
            this.mEnd = n + n2;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Out of the bounds: bytes=[");
        stringBuilder.append(arrby.length);
        stringBuilder.append("], offset=");
        stringBuilder.append(n);
        stringBuilder.append(", length=");
        stringBuilder.append(n2);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public int getPosition() {
        return this.mPosition;
    }

    public boolean hasNextNode() {
        boolean bl = this.mPosition < this.mEnd;
        return bl;
    }

    public Asn1Node nextNode() throws InvalidAsn1DataException {
        block10 : {
            Object object;
            Object object2;
            block11 : {
                int n;
                int n2;
                block13 : {
                    block12 : {
                        if (this.mPosition >= this.mEnd) break block10;
                        int n3 = this.mPosition;
                        object2 = this.mSrc;
                        object = n2 = n3 + 1;
                        if ((object2[n3] & 31) == 31) {
                            do {
                                object = n2;
                                if (n2 >= this.mEnd) break;
                                n = this.mSrc[n2];
                                object = ++n2;
                            } while ((n & 128) != 0);
                        }
                        if (object >= this.mEnd) break block11;
                        try {
                            n = IccUtils.bytesToInt(this.mSrc, n3, object - n3);
                            object2 = this.mSrc;
                            n2 = object + 1;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Cannot parse tag at position: ");
                            ((StringBuilder)object2).append(n3);
                            throw new InvalidAsn1DataException(0, ((StringBuilder)object2).toString(), illegalArgumentException);
                        }
                        object = object2[object];
                        if ((object & 128) == 0) break block12;
                        n3 = object & 127;
                        if (n2 + n3 > this.mEnd) break block13;
                        try {
                            object = IccUtils.bytesToInt((byte[])object2, n2, n3);
                            n2 += n3;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Cannot parse length at position: ");
                            ((StringBuilder)object2).append(n2);
                            throw new InvalidAsn1DataException(n, ((StringBuilder)object2).toString(), illegalArgumentException);
                        }
                    }
                    if (n2 + object <= this.mEnd) {
                        object2 = new Asn1Node(n, this.mSrc, n2, (int)object);
                        this.mPosition = n2 + object;
                        return object2;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Incomplete data at position: ");
                    ((StringBuilder)object2).append(n2);
                    ((StringBuilder)object2).append(", expected bytes: ");
                    ((StringBuilder)object2).append((int)object);
                    ((StringBuilder)object2).append(", actual bytes: ");
                    ((StringBuilder)object2).append(this.mEnd - n2);
                    throw new InvalidAsn1DataException(n, ((StringBuilder)object2).toString());
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Cannot parse length at position: ");
                ((StringBuilder)object2).append(n2);
                throw new InvalidAsn1DataException(n, ((StringBuilder)object2).toString());
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Invalid length at position: ");
            ((StringBuilder)object2).append((int)object);
            throw new InvalidAsn1DataException(0, ((StringBuilder)object2).toString());
        }
        throw new IllegalStateException("No bytes to parse.");
    }
}

