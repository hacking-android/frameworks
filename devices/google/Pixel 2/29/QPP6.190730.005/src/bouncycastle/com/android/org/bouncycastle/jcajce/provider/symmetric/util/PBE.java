/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.symmetric.util;

import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.PBEParametersGenerator;
import com.android.org.bouncycastle.crypto.digests.AndroidDigestFactory;
import com.android.org.bouncycastle.crypto.generators.OpenSSLPBEParametersGenerator;
import com.android.org.bouncycastle.crypto.generators.PKCS12ParametersGenerator;
import com.android.org.bouncycastle.crypto.generators.PKCS5S1ParametersGenerator;
import com.android.org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import com.android.org.bouncycastle.crypto.params.DESParameters;
import com.android.org.bouncycastle.crypto.params.KeyParameter;
import com.android.org.bouncycastle.crypto.params.ParametersWithIV;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey;
import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public interface PBE {
    public static final int MD5 = 0;
    public static final int OPENSSL = 3;
    public static final int PKCS12 = 2;
    public static final int PKCS5S1 = 0;
    public static final int PKCS5S1_UTF8 = 4;
    public static final int PKCS5S2 = 1;
    public static final int PKCS5S2_UTF8 = 5;
    public static final int SHA1 = 1;
    public static final int SHA224 = 7;
    public static final int SHA256 = 4;
    public static final int SHA384 = 8;
    public static final int SHA3_224 = 10;
    public static final int SHA3_256 = 11;
    public static final int SHA3_384 = 12;
    public static final int SHA3_512 = 13;
    public static final int SHA512 = 9;

    public static class Util {
        private static byte[] convertPassword(int n, PBEKeySpec arrby) {
            arrby = n == 2 ? PBEParametersGenerator.PKCS12PasswordToBytes(arrby.getPassword()) : (n != 5 && n != 4 ? PBEParametersGenerator.PKCS5PasswordToBytes(arrby.getPassword()) : PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(arrby.getPassword()));
            return arrby;
        }

        public static AlgorithmParameterSpec getParameterSpecFromPBEParameterSpec(PBEParameterSpec algorithmParameterSpec) {
            try {
                algorithmParameterSpec = (AlgorithmParameterSpec)PBE.class.getClassLoader().loadClass("javax.crypto.spec.PBEParameterSpec").getMethod("getParameterSpec", new Class[0]).invoke(algorithmParameterSpec, new Object[0]);
                return algorithmParameterSpec;
            }
            catch (Exception exception) {
                return null;
            }
        }

        /*
         * WARNING - void declaration
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private static PBEParametersGenerator makePBEGenerator(int n, int n2) {
            void var2_17;
            if (n != 0 && n != 4) {
                if (n != 1 && n != 5) {
                    if (n == 2) {
                        if (n2 != 0) {
                            if (n2 != 1) {
                                if (n2 != 4) {
                                    if (n2 != 7) {
                                        if (n2 != 8) {
                                            if (n2 != 9) throw new IllegalStateException("unknown digest scheme for PBE encryption.");
                                            PKCS12ParametersGenerator pKCS12ParametersGenerator = new PKCS12ParametersGenerator(AndroidDigestFactory.getSHA512());
                                            return var2_17;
                                        } else {
                                            PKCS12ParametersGenerator pKCS12ParametersGenerator = new PKCS12ParametersGenerator(AndroidDigestFactory.getSHA384());
                                        }
                                        return var2_17;
                                    } else {
                                        PKCS12ParametersGenerator pKCS12ParametersGenerator = new PKCS12ParametersGenerator(AndroidDigestFactory.getSHA224());
                                    }
                                    return var2_17;
                                } else {
                                    PKCS12ParametersGenerator pKCS12ParametersGenerator = new PKCS12ParametersGenerator(AndroidDigestFactory.getSHA256());
                                }
                                return var2_17;
                            } else {
                                PKCS12ParametersGenerator pKCS12ParametersGenerator = new PKCS12ParametersGenerator(AndroidDigestFactory.getSHA1());
                            }
                            return var2_17;
                        } else {
                            PKCS12ParametersGenerator pKCS12ParametersGenerator = new PKCS12ParametersGenerator(AndroidDigestFactory.getMD5());
                        }
                        return var2_17;
                    } else {
                        OpenSSLPBEParametersGenerator openSSLPBEParametersGenerator = new OpenSSLPBEParametersGenerator();
                    }
                    return var2_17;
                } else if (n2 != 0) {
                    if (n2 != 1) {
                        if (n2 != 4) {
                            if (n2 != 7) {
                                if (n2 != 8) {
                                    if (n2 != 9) throw new IllegalStateException("unknown digest scheme for PBE PKCS5S2 encryption.");
                                    PKCS5S2ParametersGenerator pKCS5S2ParametersGenerator = new PKCS5S2ParametersGenerator(AndroidDigestFactory.getSHA512());
                                    return var2_17;
                                } else {
                                    PKCS5S2ParametersGenerator pKCS5S2ParametersGenerator = new PKCS5S2ParametersGenerator(AndroidDigestFactory.getSHA384());
                                }
                                return var2_17;
                            } else {
                                PKCS5S2ParametersGenerator pKCS5S2ParametersGenerator = new PKCS5S2ParametersGenerator(AndroidDigestFactory.getSHA224());
                            }
                            return var2_17;
                        } else {
                            PKCS5S2ParametersGenerator pKCS5S2ParametersGenerator = new PKCS5S2ParametersGenerator(AndroidDigestFactory.getSHA256());
                        }
                        return var2_17;
                    } else {
                        PKCS5S2ParametersGenerator pKCS5S2ParametersGenerator = new PKCS5S2ParametersGenerator(AndroidDigestFactory.getSHA1());
                    }
                    return var2_17;
                } else {
                    PKCS5S2ParametersGenerator pKCS5S2ParametersGenerator = new PKCS5S2ParametersGenerator(AndroidDigestFactory.getMD5());
                }
                return var2_17;
            } else if (n2 != 0) {
                if (n2 != 1) throw new IllegalStateException("PKCS5 scheme 1 only supports MD2, MD5 and SHA1.");
                PKCS5S1ParametersGenerator pKCS5S1ParametersGenerator = new PKCS5S1ParametersGenerator(AndroidDigestFactory.getSHA1());
                return var2_17;
            } else {
                PKCS5S1ParametersGenerator pKCS5S1ParametersGenerator = new PKCS5S1ParametersGenerator(AndroidDigestFactory.getMD5());
            }
            return var2_17;
        }

        public static CipherParameters makePBEMacParameters(BCPBEKey bCPBEKey, AlgorithmParameterSpec algorithmParameterSpec) {
            if (algorithmParameterSpec != null && algorithmParameterSpec instanceof PBEParameterSpec) {
                algorithmParameterSpec = (PBEParameterSpec)algorithmParameterSpec;
                PBEParametersGenerator pBEParametersGenerator = Util.makePBEGenerator(bCPBEKey.getType(), bCPBEKey.getDigest());
                pBEParametersGenerator.init(bCPBEKey.getEncoded(), ((PBEParameterSpec)algorithmParameterSpec).getSalt(), ((PBEParameterSpec)algorithmParameterSpec).getIterationCount());
                return pBEParametersGenerator.generateDerivedMacParameters(bCPBEKey.getKeySize());
            }
            throw new IllegalArgumentException("Need a PBEParameter spec with a PBE key.");
        }

        public static CipherParameters makePBEMacParameters(SecretKey object, int n, int n2, int n3, PBEParameterSpec pBEParameterSpec) {
            PBEParametersGenerator pBEParametersGenerator = Util.makePBEGenerator(n, n2);
            byte[] arrby = object.getEncoded();
            pBEParametersGenerator.init(object.getEncoded(), pBEParameterSpec.getSalt(), pBEParameterSpec.getIterationCount());
            object = pBEParametersGenerator.generateDerivedMacParameters(n3);
            for (n = 0; n != arrby.length; ++n) {
                arrby[n] = (byte)(false ? 1 : 0);
            }
            return object;
        }

        public static CipherParameters makePBEMacParameters(PBEKeySpec object, int n, int n2, int n3) {
            PBEParametersGenerator pBEParametersGenerator = Util.makePBEGenerator(n, n2);
            byte[] arrby = Util.convertPassword(n, (PBEKeySpec)object);
            pBEParametersGenerator.init(arrby, ((PBEKeySpec)object).getSalt(), ((PBEKeySpec)object).getIterationCount());
            object = pBEParametersGenerator.generateDerivedMacParameters(n3);
            for (n = 0; n != arrby.length; ++n) {
                arrby[n] = (byte)(false ? 1 : 0);
            }
            return object;
        }

        public static CipherParameters makePBEParameters(BCPBEKey object, AlgorithmParameterSpec object2, String string) {
            block7 : {
                block10 : {
                    Object object3;
                    block8 : {
                        AlgorithmParameterSpec algorithmParameterSpec;
                        block9 : {
                            if (object2 == null || !(object2 instanceof PBEParameterSpec)) break block7;
                            algorithmParameterSpec = (PBEParameterSpec)object2;
                            object3 = Util.makePBEGenerator(((BCPBEKey)object).getType(), ((BCPBEKey)object).getDigest());
                            object2 = ((BCPBEKey)object).getEncoded();
                            if (((BCPBEKey)object).shouldTryWrongPKCS12()) {
                                object2 = new byte[2];
                            }
                            ((PBEParametersGenerator)object3).init((byte[])object2, algorithmParameterSpec.getSalt(), algorithmParameterSpec.getIterationCount());
                            if (((BCPBEKey)object).getIvSize() == 0) break block8;
                            object3 = ((PBEParametersGenerator)object3).generateDerivedParameters(((BCPBEKey)object).getKeySize(), ((BCPBEKey)object).getIvSize());
                            algorithmParameterSpec = Util.getParameterSpecFromPBEParameterSpec(algorithmParameterSpec);
                            if (((BCPBEKey)object).getType() == 1) break block9;
                            object2 = object3;
                            if (((BCPBEKey)object).getType() != 5) break block10;
                        }
                        object2 = object3;
                        if (algorithmParameterSpec instanceof IvParameterSpec) {
                            object = (ParametersWithIV)object3;
                            object2 = (IvParameterSpec)algorithmParameterSpec;
                            object2 = new ParametersWithIV((KeyParameter)((ParametersWithIV)object).getParameters(), ((IvParameterSpec)object2).getIV());
                        }
                        break block10;
                    }
                    object2 = ((PBEParametersGenerator)object3).generateDerivedParameters(((BCPBEKey)object).getKeySize());
                }
                if (string.startsWith("DES")) {
                    if (object2 instanceof ParametersWithIV) {
                        DESParameters.setOddParity(((KeyParameter)((ParametersWithIV)object2).getParameters()).getKey());
                    } else {
                        DESParameters.setOddParity(((KeyParameter)object2).getKey());
                    }
                }
                return object2;
            }
            throw new IllegalArgumentException("Need a PBEParameter spec with a PBE key.");
        }

        public static CipherParameters makePBEParameters(PBEKeySpec object, int n, int n2, int n3, int n4) {
            PBEParametersGenerator pBEParametersGenerator = Util.makePBEGenerator(n, n2);
            byte[] arrby = Util.convertPassword(n, (PBEKeySpec)object);
            pBEParametersGenerator.init(arrby, ((PBEKeySpec)object).getSalt(), ((PBEKeySpec)object).getIterationCount());
            object = n4 != 0 ? pBEParametersGenerator.generateDerivedParameters(n3, n4) : pBEParametersGenerator.generateDerivedParameters(n3);
            for (n = 0; n != arrby.length; ++n) {
                arrby[n] = (byte)(false ? 1 : 0);
            }
            return object;
        }

        public static CipherParameters makePBEParameters(byte[] object, int n, int n2, int n3, int n4, AlgorithmParameterSpec object2, String string) throws InvalidAlgorithmParameterException {
            block6 : {
                block9 : {
                    block7 : {
                        AlgorithmParameterSpec algorithmParameterSpec;
                        block8 : {
                            if (object2 == null || !(object2 instanceof PBEParameterSpec)) break block6;
                            algorithmParameterSpec = (PBEParameterSpec)object2;
                            object2 = Util.makePBEGenerator(n, n2);
                            ((PBEParametersGenerator)object2).init((byte[])object, algorithmParameterSpec.getSalt(), algorithmParameterSpec.getIterationCount());
                            if (n4 == 0) break block7;
                            object2 = ((PBEParametersGenerator)object2).generateDerivedParameters(n3, n4);
                            algorithmParameterSpec = Util.getParameterSpecFromPBEParameterSpec(algorithmParameterSpec);
                            if (n == 1) break block8;
                            object = object2;
                            if (n != 5) break block9;
                        }
                        object = object2;
                        if (algorithmParameterSpec instanceof IvParameterSpec) {
                            object = (ParametersWithIV)object2;
                            object2 = (IvParameterSpec)algorithmParameterSpec;
                            object = new ParametersWithIV((KeyParameter)((ParametersWithIV)object).getParameters(), ((IvParameterSpec)object2).getIV());
                        }
                        break block9;
                    }
                    object = ((PBEParametersGenerator)object2).generateDerivedParameters(n3);
                }
                if (string.startsWith("DES")) {
                    if (object instanceof ParametersWithIV) {
                        DESParameters.setOddParity(((KeyParameter)((ParametersWithIV)object).getParameters()).getKey());
                    } else {
                        DESParameters.setOddParity(((KeyParameter)object).getKey());
                    }
                }
                return object;
            }
            throw new InvalidAlgorithmParameterException("Need a PBEParameter spec with a PBE key.");
        }
    }

}

