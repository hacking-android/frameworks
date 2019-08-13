/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.euicc.apdu;

import com.android.internal.telephony.uicc.euicc.apdu.ApduCommand;
import java.util.ArrayList;
import java.util.List;

public class RequestBuilder {
    private static final int CLA_STORE_DATA = 128;
    private static final int INS_STORE_DATA = 226;
    private static final int MAX_APDU_DATA_LEN = 255;
    private static final int MAX_EXT_APDU_DATA_LEN = 65535;
    private static final int P1_STORE_DATA_END = 145;
    private static final int P1_STORE_DATA_INTERM = 17;
    private final int mChannel;
    private final List<ApduCommand> mCommands = new ArrayList<ApduCommand>();
    private final int mMaxApduDataLen;

    RequestBuilder(int n, boolean bl) {
        this.mChannel = n;
        n = bl ? 65535 : 255;
        this.mMaxApduDataLen = n;
    }

    public void addApdu(int n, int n2, int n3, int n4) {
        this.mCommands.add(new ApduCommand(this.mChannel, n, n2, n3, n4, 0, ""));
    }

    public void addApdu(int n, int n2, int n3, int n4, int n5, String string) {
        this.mCommands.add(new ApduCommand(this.mChannel, n, n2, n3, n4, n5, string));
    }

    public void addApdu(int n, int n2, int n3, int n4, String string) {
        this.mCommands.add(new ApduCommand(this.mChannel, n, n2, n3, n4, string.length() / 2, string));
    }

    public void addStoreData(String string) {
        int n = this.mMaxApduDataLen * 2;
        int n2 = 0;
        int n3 = string.length() / 2;
        int n4 = 1;
        if (n3 != 0) {
            n4 = this.mMaxApduDataLen;
            n4 = (n3 + n4 - 1) / n4;
        }
        for (n3 = 1; n3 < n4; ++n3) {
            this.addApdu(128, 226, 17, n3 - 1, string.substring(n2, n2 + n));
            n2 += n;
        }
        this.addApdu(128, 226, 145, n4 - 1, string.substring(n2));
    }

    List<ApduCommand> getCommands() {
        return this.mCommands;
    }
}

