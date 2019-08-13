/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.euicc.apdu;

class ApduCommand {
    public final int channel;
    public final int cla;
    public final String cmdHex;
    public final int ins;
    public final int p1;
    public final int p2;
    public final int p3;

    ApduCommand(int n, int n2, int n3, int n4, int n5, int n6, String string) {
        this.channel = n;
        this.cla = n2;
        this.ins = n3;
        this.p1 = n4;
        this.p2 = n5;
        this.p3 = n6;
        this.cmdHex = string;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ApduCommand(channel=");
        stringBuilder.append(this.channel);
        stringBuilder.append(", cla=");
        stringBuilder.append(this.cla);
        stringBuilder.append(", ins=");
        stringBuilder.append(this.ins);
        stringBuilder.append(", p1=");
        stringBuilder.append(this.p1);
        stringBuilder.append(", p2=");
        stringBuilder.append(this.p2);
        stringBuilder.append(", p3=");
        stringBuilder.append(this.p3);
        stringBuilder.append(", cmd=");
        stringBuilder.append(this.cmdHex);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}

