/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1GeneralizedTime;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.StreamUtil;
import com.android.org.bouncycastle.util.Strings;
import java.io.IOException;
import java.util.Date;

public class DERGeneralizedTime
extends ASN1GeneralizedTime {
    public DERGeneralizedTime(String string) {
        super(string);
    }

    public DERGeneralizedTime(Date date) {
        super(date);
    }

    public DERGeneralizedTime(byte[] arrby) {
        super(arrby);
    }

    private byte[] getDERTime() {
        if (this.time[this.time.length - 1] == 90) {
            if (!this.hasMinutes()) {
                byte[] arrby = new byte[this.time.length + 4];
                System.arraycopy((byte[])this.time, (int)0, (byte[])arrby, (int)0, (int)(this.time.length - 1));
                System.arraycopy((byte[])Strings.toByteArray("0000Z"), (int)0, (byte[])arrby, (int)(this.time.length - 1), (int)5);
                return arrby;
            }
            if (!this.hasSeconds()) {
                byte[] arrby = new byte[this.time.length + 2];
                System.arraycopy((byte[])this.time, (int)0, (byte[])arrby, (int)0, (int)(this.time.length - 1));
                System.arraycopy((byte[])Strings.toByteArray("00Z"), (int)0, (byte[])arrby, (int)(this.time.length - 1), (int)3);
                return arrby;
            }
            if (this.hasFractionalSeconds()) {
                int n;
                for (n = this.time.length - 2; n > 0 && this.time[n] == 48; --n) {
                }
                if (this.time[n] == 46) {
                    byte[] arrby = new byte[n + 1];
                    System.arraycopy((byte[])this.time, (int)0, (byte[])arrby, (int)0, (int)n);
                    arrby[n] = (byte)90;
                    return arrby;
                }
                byte[] arrby = new byte[n + 2];
                System.arraycopy((byte[])this.time, (int)0, (byte[])arrby, (int)0, (int)(n + 1));
                arrby[n + 1] = (byte)90;
                return arrby;
            }
            return this.time;
        }
        return this.time;
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.writeEncoded(24, this.getDERTime());
    }

    @Override
    int encodedLength() {
        int n = this.getDERTime().length;
        return StreamUtil.calculateBodyLength(n) + 1 + n;
    }
}

