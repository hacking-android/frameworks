/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.dh;

import com.android.org.bouncycastle.crypto.params.DHParameters;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Fingerprint;
import com.android.org.bouncycastle.util.Strings;
import java.math.BigInteger;

class DHUtil {
    DHUtil() {
    }

    private static String generateKeyFingerprint(BigInteger bigInteger, DHParameters dHParameters) {
        return new Fingerprint(Arrays.concatenate(bigInteger.toByteArray(), dHParameters.getP().toByteArray(), dHParameters.getG().toByteArray())).toString();
    }

    static String privateKeyToString(String string, BigInteger bigInteger, DHParameters dHParameters) {
        StringBuffer stringBuffer = new StringBuffer();
        String string2 = Strings.lineSeparator();
        bigInteger = dHParameters.getG().modPow(bigInteger, dHParameters.getP());
        stringBuffer.append(string);
        stringBuffer.append(" Private Key [");
        stringBuffer.append(DHUtil.generateKeyFingerprint(bigInteger, dHParameters));
        stringBuffer.append("]");
        stringBuffer.append(string2);
        stringBuffer.append("              Y: ");
        stringBuffer.append(bigInteger.toString(16));
        stringBuffer.append(string2);
        return stringBuffer.toString();
    }

    static String publicKeyToString(String string, BigInteger bigInteger, DHParameters dHParameters) {
        StringBuffer stringBuffer = new StringBuffer();
        String string2 = Strings.lineSeparator();
        stringBuffer.append(string);
        stringBuffer.append(" Public Key [");
        stringBuffer.append(DHUtil.generateKeyFingerprint(bigInteger, dHParameters));
        stringBuffer.append("]");
        stringBuffer.append(string2);
        stringBuffer.append("             Y: ");
        stringBuffer.append(bigInteger.toString(16));
        stringBuffer.append(string2);
        return stringBuffer.toString();
    }
}

