/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.math.BigInteger;
import java.security.Key;
import java.security.interfaces.DSAKey;
import java.security.interfaces.DSAParams;
import java.security.interfaces.ECKey;
import java.security.interfaces.RSAKey;
import java.security.spec.ECParameterSpec;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHKey;
import javax.crypto.spec.DHParameterSpec;
import sun.security.util.Length;

public final class KeyUtil {
    public static final int getKeySize(Key object) {
        int n;
        block7 : {
            block10 : {
                block9 : {
                    block8 : {
                        block6 : {
                            int n2;
                            n = n2 = -1;
                            if (object instanceof Length) {
                                try {
                                    n2 = n = ((Length)object).length();
                                }
                                catch (UnsupportedOperationException unsupportedOperationException) {
                                    // empty catch block
                                }
                                n = n2;
                                if (n2 >= 0) {
                                    return n2;
                                }
                            }
                            if (!(object instanceof SecretKey)) break block6;
                            object = (SecretKey)object;
                            n2 = n;
                            if ("RAW".equals(object.getFormat())) {
                                n2 = n;
                                if (object.getEncoded() != null) {
                                    n2 = object.getEncoded().length * 8;
                                }
                            }
                            n = n2;
                            break block7;
                        }
                        if (!(object instanceof RSAKey)) break block8;
                        n = ((RSAKey)object).getModulus().bitLength();
                        break block7;
                    }
                    if (!(object instanceof ECKey)) break block9;
                    if ((object = ((ECKey)object).getParams()) == null) break block7;
                    n = ((ECParameterSpec)object).getOrder().bitLength();
                    break block7;
                }
                if (!(object instanceof DSAKey)) break block10;
                n = (object = ((DSAKey)object).getParams()) != null ? object.getP().bitLength() : -1;
                break block7;
            }
            if (!(object instanceof DHKey)) break block7;
            n = ((DHKey)object).getParams().getP().bitLength();
        }
        return n;
    }
}

