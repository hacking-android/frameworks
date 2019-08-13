/*
 * Decompiled with CFR 0.145.
 */
package android.privacy;

import android.privacy.DifferentialPrivacyConfig;

public interface DifferentialPrivacyEncoder {
    public byte[] encodeBits(byte[] var1);

    public byte[] encodeBoolean(boolean var1);

    public byte[] encodeString(String var1);

    public DifferentialPrivacyConfig getConfig();

    public boolean isInsecureEncoderForTest();
}

