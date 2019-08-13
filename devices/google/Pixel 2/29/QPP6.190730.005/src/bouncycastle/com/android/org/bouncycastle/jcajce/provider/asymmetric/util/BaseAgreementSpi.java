/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.util;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.kisa.KISAObjectIdentifiers;
import com.android.org.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import com.android.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.org.bouncycastle.asn1.ntt.NTTObjectIdentifiers;
import com.android.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.crypto.DerivationFunction;
import com.android.org.bouncycastle.crypto.DerivationParameters;
import com.android.org.bouncycastle.crypto.params.DESParameters;
import com.android.org.bouncycastle.crypto.params.KDFParameters;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Integers;
import com.android.org.bouncycastle.util.Strings;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.crypto.KeyAgreementSpi;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

public abstract class BaseAgreementSpi
extends KeyAgreementSpi {
    private static final Map<String, ASN1ObjectIdentifier> defaultOids = new HashMap<String, ASN1ObjectIdentifier>();
    private static final Hashtable des;
    private static final Map<String, Integer> keySizes;
    private static final Map<String, String> nameTable;
    private static final Hashtable oids;
    protected final String kaAlgorithm;
    protected final DerivationFunction kdf;
    protected byte[] ukmParameters;

    static {
        keySizes = new HashMap<String, Integer>();
        nameTable = new HashMap<String, String>();
        oids = new Hashtable();
        des = new Hashtable();
        Integer n = Integers.valueOf(64);
        Integer n2 = Integers.valueOf(128);
        Integer n3 = Integers.valueOf(192);
        Integer n4 = Integers.valueOf(256);
        keySizes.put("DES", n);
        keySizes.put("DESEDE", n3);
        keySizes.put("BLOWFISH", n2);
        keySizes.put("AES", n4);
        keySizes.put(NISTObjectIdentifiers.id_aes128_ECB.getId(), n2);
        keySizes.put(NISTObjectIdentifiers.id_aes192_ECB.getId(), n3);
        keySizes.put(NISTObjectIdentifiers.id_aes256_ECB.getId(), n4);
        keySizes.put(NISTObjectIdentifiers.id_aes128_CBC.getId(), n2);
        keySizes.put(NISTObjectIdentifiers.id_aes192_CBC.getId(), n3);
        keySizes.put(NISTObjectIdentifiers.id_aes256_CBC.getId(), n4);
        keySizes.put(NISTObjectIdentifiers.id_aes128_CFB.getId(), n2);
        keySizes.put(NISTObjectIdentifiers.id_aes192_CFB.getId(), n3);
        keySizes.put(NISTObjectIdentifiers.id_aes256_CFB.getId(), n4);
        keySizes.put(NISTObjectIdentifiers.id_aes128_OFB.getId(), n2);
        keySizes.put(NISTObjectIdentifiers.id_aes192_OFB.getId(), n3);
        keySizes.put(NISTObjectIdentifiers.id_aes256_OFB.getId(), n4);
        keySizes.put(NISTObjectIdentifiers.id_aes128_wrap.getId(), n2);
        keySizes.put(NISTObjectIdentifiers.id_aes192_wrap.getId(), n3);
        keySizes.put(NISTObjectIdentifiers.id_aes256_wrap.getId(), n4);
        keySizes.put(NISTObjectIdentifiers.id_aes128_CCM.getId(), n2);
        keySizes.put(NISTObjectIdentifiers.id_aes192_CCM.getId(), n3);
        keySizes.put(NISTObjectIdentifiers.id_aes256_CCM.getId(), n4);
        keySizes.put(NISTObjectIdentifiers.id_aes128_GCM.getId(), n2);
        keySizes.put(NISTObjectIdentifiers.id_aes192_GCM.getId(), n3);
        keySizes.put(NISTObjectIdentifiers.id_aes256_GCM.getId(), n4);
        keySizes.put(NTTObjectIdentifiers.id_camellia128_wrap.getId(), n2);
        keySizes.put(NTTObjectIdentifiers.id_camellia192_wrap.getId(), n3);
        keySizes.put(NTTObjectIdentifiers.id_camellia256_wrap.getId(), n4);
        keySizes.put(KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap.getId(), n2);
        keySizes.put(PKCSObjectIdentifiers.id_alg_CMS3DESwrap.getId(), n3);
        keySizes.put(PKCSObjectIdentifiers.des_EDE3_CBC.getId(), n3);
        keySizes.put(OIWObjectIdentifiers.desCBC.getId(), n);
        keySizes.put(PKCSObjectIdentifiers.id_hmacWithSHA1.getId(), Integers.valueOf(160));
        keySizes.put(PKCSObjectIdentifiers.id_hmacWithSHA256.getId(), n4);
        keySizes.put(PKCSObjectIdentifiers.id_hmacWithSHA384.getId(), Integers.valueOf(384));
        keySizes.put(PKCSObjectIdentifiers.id_hmacWithSHA512.getId(), Integers.valueOf(512));
        defaultOids.put("DESEDE", PKCSObjectIdentifiers.des_EDE3_CBC);
        defaultOids.put("AES", NISTObjectIdentifiers.id_aes256_CBC);
        defaultOids.put("CAMELLIA", NTTObjectIdentifiers.id_camellia256_cbc);
        defaultOids.put("SEED", KISAObjectIdentifiers.id_seedCBC);
        defaultOids.put("DES", OIWObjectIdentifiers.desCBC);
        nameTable.put(MiscObjectIdentifiers.cast5CBC.getId(), "CAST5");
        nameTable.put(MiscObjectIdentifiers.as_sys_sec_alg_ideaCBC.getId(), "IDEA");
        nameTable.put(MiscObjectIdentifiers.cryptlib_algorithm_blowfish_ECB.getId(), "Blowfish");
        nameTable.put(MiscObjectIdentifiers.cryptlib_algorithm_blowfish_CBC.getId(), "Blowfish");
        nameTable.put(MiscObjectIdentifiers.cryptlib_algorithm_blowfish_CFB.getId(), "Blowfish");
        nameTable.put(MiscObjectIdentifiers.cryptlib_algorithm_blowfish_OFB.getId(), "Blowfish");
        nameTable.put(OIWObjectIdentifiers.desECB.getId(), "DES");
        nameTable.put(OIWObjectIdentifiers.desCBC.getId(), "DES");
        nameTable.put(OIWObjectIdentifiers.desCFB.getId(), "DES");
        nameTable.put(OIWObjectIdentifiers.desOFB.getId(), "DES");
        nameTable.put(OIWObjectIdentifiers.desEDE.getId(), "DESede");
        nameTable.put(PKCSObjectIdentifiers.des_EDE3_CBC.getId(), "DESede");
        nameTable.put(PKCSObjectIdentifiers.id_alg_CMS3DESwrap.getId(), "DESede");
        nameTable.put(PKCSObjectIdentifiers.id_alg_CMSRC2wrap.getId(), "RC2");
        nameTable.put(PKCSObjectIdentifiers.id_hmacWithSHA1.getId(), "HmacSHA1");
        nameTable.put(PKCSObjectIdentifiers.id_hmacWithSHA224.getId(), "HmacSHA224");
        nameTable.put(PKCSObjectIdentifiers.id_hmacWithSHA256.getId(), "HmacSHA256");
        nameTable.put(PKCSObjectIdentifiers.id_hmacWithSHA384.getId(), "HmacSHA384");
        nameTable.put(PKCSObjectIdentifiers.id_hmacWithSHA512.getId(), "HmacSHA512");
        nameTable.put(NTTObjectIdentifiers.id_camellia128_cbc.getId(), "Camellia");
        nameTable.put(NTTObjectIdentifiers.id_camellia192_cbc.getId(), "Camellia");
        nameTable.put(NTTObjectIdentifiers.id_camellia256_cbc.getId(), "Camellia");
        nameTable.put(NTTObjectIdentifiers.id_camellia128_wrap.getId(), "Camellia");
        nameTable.put(NTTObjectIdentifiers.id_camellia192_wrap.getId(), "Camellia");
        nameTable.put(NTTObjectIdentifiers.id_camellia256_wrap.getId(), "Camellia");
        nameTable.put(KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap.getId(), "SEED");
        nameTable.put(KISAObjectIdentifiers.id_seedCBC.getId(), "SEED");
        nameTable.put(KISAObjectIdentifiers.id_seedMAC.getId(), "SEED");
        nameTable.put(NISTObjectIdentifiers.id_aes128_wrap.getId(), "AES");
        nameTable.put(NISTObjectIdentifiers.id_aes128_CCM.getId(), "AES");
        nameTable.put(NISTObjectIdentifiers.id_aes128_CCM.getId(), "AES");
        oids.put("DESEDE", PKCSObjectIdentifiers.des_EDE3_CBC);
        oids.put("AES", NISTObjectIdentifiers.id_aes256_CBC);
        oids.put("DES", OIWObjectIdentifiers.desCBC);
        des.put("DES", "DES");
        des.put("DESEDE", "DES");
        des.put(OIWObjectIdentifiers.desCBC.getId(), "DES");
        des.put(PKCSObjectIdentifiers.des_EDE3_CBC.getId(), "DES");
        des.put(PKCSObjectIdentifiers.id_alg_CMS3DESwrap.getId(), "DES");
    }

    public BaseAgreementSpi(String string, DerivationFunction derivationFunction) {
        this.kaAlgorithm = string;
        this.kdf = derivationFunction;
    }

    protected static String getAlgorithm(String string) {
        if (string.indexOf(91) > 0) {
            return string.substring(0, string.indexOf(91));
        }
        if (string.startsWith(NISTObjectIdentifiers.aes.getId())) {
            return "AES";
        }
        String string2 = nameTable.get(Strings.toUpperCase(string));
        if (string2 != null) {
            return string2;
        }
        return string;
    }

    protected static int getKeySize(String string) {
        if (string.indexOf(91) > 0) {
            return Integer.parseInt(string.substring(string.indexOf(91) + 1, string.indexOf(93)));
        }
        if (!keySizes.containsKey(string = Strings.toUpperCase(string))) {
            return -1;
        }
        return keySizes.get(string);
    }

    private byte[] getSharedSecretBytes(byte[] object, String arrby, int n) throws NoSuchAlgorithmException {
        if (this.kdf != null) {
            if (n >= 0) {
                arrby = new byte[n / 8];
                KDFParameters kDFParameters = new KDFParameters((byte[])object, this.ukmParameters);
                this.kdf.init(kDFParameters);
                this.kdf.generateBytes(arrby, 0, arrby.length);
                Arrays.clear((byte[])object);
                return arrby;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("unknown algorithm encountered: ");
            ((StringBuilder)object).append((String)arrby);
            throw new NoSuchAlgorithmException(((StringBuilder)object).toString());
        }
        if (n > 0) {
            arrby = new byte[n / 8];
            System.arraycopy((byte[])object, (int)0, (byte[])arrby, (int)0, (int)arrby.length);
            Arrays.clear((byte[])object);
            return arrby;
        }
        return object;
    }

    protected static byte[] trimZeroes(byte[] arrby) {
        int n;
        if (arrby[0] != 0) {
            return arrby;
        }
        for (n = 0; n < arrby.length && arrby[n] == 0; ++n) {
        }
        byte[] arrby2 = new byte[arrby.length - n];
        System.arraycopy((byte[])arrby, (int)n, (byte[])arrby2, (int)0, (int)arrby2.length);
        return arrby2;
    }

    protected abstract byte[] calcSecret();

    @Override
    protected int engineGenerateSecret(byte[] object, int n) throws IllegalStateException, ShortBufferException {
        byte[] arrby = this.engineGenerateSecret();
        if (((byte[])object).length - n >= arrby.length) {
            System.arraycopy((byte[])arrby, (int)0, (byte[])object, (int)n, (int)arrby.length);
            return arrby.length;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(this.kaAlgorithm);
        ((StringBuilder)object).append(" key agreement: need ");
        ((StringBuilder)object).append(arrby.length);
        ((StringBuilder)object).append(" bytes");
        throw new ShortBufferException(((StringBuilder)object).toString());
    }

    @Override
    protected SecretKey engineGenerateSecret(String object) throws NoSuchAlgorithmException {
        String string = Strings.toUpperCase((String)object);
        byte[] arrby = object;
        if (oids.containsKey(string)) {
            arrby = ((ASN1ObjectIdentifier)oids.get(string)).getId();
        }
        int n = BaseAgreementSpi.getKeySize((String)arrby);
        arrby = this.getSharedSecretBytes(this.calcSecret(), (String)arrby, n);
        if (des.containsKey(object = BaseAgreementSpi.getAlgorithm((String)object))) {
            DESParameters.setOddParity(arrby);
        }
        return new SecretKeySpec(arrby, (String)object);
    }

    @Override
    protected byte[] engineGenerateSecret() throws IllegalStateException {
        if (this.kdf != null) {
            byte[] arrby = this.calcSecret();
            try {
                arrby = this.getSharedSecretBytes(arrby, null, arrby.length * 8);
                return arrby;
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                throw new IllegalStateException(noSuchAlgorithmException.getMessage());
            }
        }
        return this.calcSecret();
    }
}

