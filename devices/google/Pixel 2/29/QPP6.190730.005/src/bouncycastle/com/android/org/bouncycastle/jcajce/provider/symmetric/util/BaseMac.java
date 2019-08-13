/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.symmetric.util;

import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.Mac;
import com.android.org.bouncycastle.crypto.macs.HMac;
import com.android.org.bouncycastle.crypto.params.AEADParameters;
import com.android.org.bouncycastle.crypto.params.KeyParameter;
import com.android.org.bouncycastle.crypto.params.ParametersWithIV;
import com.android.org.bouncycastle.jcajce.PKCS12Key;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.ClassUtil;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.PBE;
import com.android.org.bouncycastle.jcajce.spec.AEADParameterSpec;
import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import javax.crypto.MacSpi;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;

public class BaseMac
extends MacSpi
implements PBE {
    private static final Class gcmSpecClass = ClassUtil.loadClass(BaseMac.class, "javax.crypto.spec.GCMParameterSpec");
    private int keySize = 160;
    private Mac macEngine;
    private int pbeHash = 1;
    private int scheme = 2;

    protected BaseMac(Mac mac) {
        this.macEngine = mac;
    }

    protected BaseMac(Mac mac, int n, int n2, int n3) {
        this.macEngine = mac;
        this.scheme = n;
        this.pbeHash = n2;
        this.keySize = n3;
    }

    private static Hashtable copyMap(Map map) {
        Hashtable hashtable = new Hashtable();
        for (Object k : map.keySet()) {
            hashtable.put(k, map.get(k));
        }
        return hashtable;
    }

    @Override
    protected byte[] engineDoFinal() {
        byte[] arrby = new byte[this.engineGetMacLength()];
        this.macEngine.doFinal(arrby, 0);
        return arrby;
    }

    @Override
    protected int engineGetMacLength() {
        return this.macEngine.getMacSize();
    }

    @Override
    protected void engineInit(Key object, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        block25 : {
            block31 : {
                block36 : {
                    block33 : {
                        Object object2;
                        Object object3;
                        block35 : {
                            block34 : {
                                block32 : {
                                    block27 : {
                                        block28 : {
                                            block30 : {
                                                block29 : {
                                                    block26 : {
                                                        SecretKey secretKey;
                                                        if (object == null) break block25;
                                                        if (!(object instanceof PKCS12Key)) break block26;
                                                        try {
                                                            secretKey = (SecretKey)object;
                                                        }
                                                        catch (Exception exception) {
                                                            throw new InvalidKeyException("PKCS12 requires a SecretKey/PBEKey");
                                                        }
                                                        try {
                                                            object2 = object3 = (PBEParameterSpec)algorithmParameterSpec;
                                                        }
                                                        catch (Exception exception) {
                                                            throw new InvalidAlgorithmParameterException("PKCS12 requires a PBEParameterSpec");
                                                        }
                                                        if (secretKey instanceof PBEKey) {
                                                            object2 = object3;
                                                            if (object3 == null) {
                                                                object2 = new PBEParameterSpec(((PBEKey)secretKey).getSalt(), ((PBEKey)secretKey).getIterationCount());
                                                            }
                                                        }
                                                        int n = 1;
                                                        int n2 = 160;
                                                        object3 = this.macEngine;
                                                        int n3 = n;
                                                        int n4 = n2;
                                                        if (object3 instanceof HMac) {
                                                            n3 = n;
                                                            n4 = n2;
                                                            if (!object3.getAlgorithmName().startsWith("SHA-1")) {
                                                                if (this.macEngine.getAlgorithmName().startsWith("SHA-224")) {
                                                                    n3 = 7;
                                                                    n4 = 224;
                                                                } else if (this.macEngine.getAlgorithmName().startsWith("SHA-256")) {
                                                                    n3 = 4;
                                                                    n4 = 256;
                                                                } else if (this.macEngine.getAlgorithmName().startsWith("SHA-384")) {
                                                                    n3 = 8;
                                                                    n4 = 384;
                                                                } else if (this.macEngine.getAlgorithmName().startsWith("SHA-512")) {
                                                                    n3 = 9;
                                                                    n4 = 512;
                                                                } else {
                                                                    object = new StringBuilder();
                                                                    ((StringBuilder)object).append("no PKCS12 mapping for HMAC: ");
                                                                    ((StringBuilder)object).append(this.macEngine.getAlgorithmName());
                                                                    throw new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
                                                                }
                                                            }
                                                        }
                                                        object2 = PBE.Util.makePBEMacParameters(secretKey, 2, n3, n4, (PBEParameterSpec)object2);
                                                        break block27;
                                                    }
                                                    if (!(object instanceof BCPBEKey)) break block28;
                                                    object2 = (BCPBEKey)object;
                                                    if (((BCPBEKey)object2).getParam() == null) break block29;
                                                    object2 = ((BCPBEKey)object2).getParam();
                                                    break block27;
                                                }
                                                if (!(algorithmParameterSpec instanceof PBEParameterSpec)) break block30;
                                                object2 = PBE.Util.makePBEMacParameters((BCPBEKey)object2, algorithmParameterSpec);
                                                break block27;
                                            }
                                            throw new InvalidAlgorithmParameterException("PBE requires PBE parameters to be set.");
                                        }
                                        if (algorithmParameterSpec instanceof PBEParameterSpec) break block31;
                                        object2 = new KeyParameter(object.getEncoded());
                                    }
                                    object3 = object2 instanceof ParametersWithIV ? (KeyParameter)((ParametersWithIV)object2).getParameters() : (KeyParameter)object2;
                                    if (!(algorithmParameterSpec instanceof AEADParameterSpec)) break block32;
                                    object = (AEADParameterSpec)algorithmParameterSpec;
                                    object = new AEADParameters((KeyParameter)object3, ((AEADParameterSpec)object).getMacSizeInBits(), ((AEADParameterSpec)object).getNonce(), ((AEADParameterSpec)object).getAssociatedData());
                                    break block33;
                                }
                                if (!(algorithmParameterSpec instanceof IvParameterSpec)) break block34;
                                object = new ParametersWithIV((CipherParameters)object3, ((IvParameterSpec)algorithmParameterSpec).getIV());
                                break block33;
                            }
                            if (algorithmParameterSpec != null) break block35;
                            object = new KeyParameter(object.getEncoded());
                            break block33;
                        }
                        object = gcmSpecClass;
                        if (object != null && ((Class)object).isAssignableFrom(algorithmParameterSpec.getClass())) {
                            try {
                                object = gcmSpecClass.getDeclaredMethod("getTLen", new Class[0]);
                                object2 = gcmSpecClass.getDeclaredMethod("getIV", new Class[0]);
                                object = new AEADParameters((KeyParameter)object3, (Integer)((Method)object).invoke(algorithmParameterSpec, new Object[0]), (byte[])((Method)object2).invoke(algorithmParameterSpec, new Object[0]));
                            }
                            catch (Exception exception) {
                                throw new InvalidAlgorithmParameterException("Cannot process GCMParameterSpec.");
                            }
                        }
                        if (!(algorithmParameterSpec instanceof PBEParameterSpec)) break block36;
                        object = object2;
                    }
                    try {
                        this.macEngine.init((CipherParameters)object);
                        return;
                    }
                    catch (Exception exception) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("cannot initialize MAC: ");
                        ((StringBuilder)object).append(exception.getMessage());
                        throw new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
                    }
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("unknown parameter type: ");
                ((StringBuilder)object).append(algorithmParameterSpec.getClass().getName());
                throw new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("inappropriate parameter type: ");
            ((StringBuilder)object).append(algorithmParameterSpec.getClass().getName());
            throw new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
        }
        throw new InvalidKeyException("key is null");
    }

    @Override
    protected void engineReset() {
        this.macEngine.reset();
    }

    @Override
    protected void engineUpdate(byte by) {
        this.macEngine.update(by);
    }

    @Override
    protected void engineUpdate(byte[] arrby, int n, int n2) {
        this.macEngine.update(arrby, n, n2);
    }
}

