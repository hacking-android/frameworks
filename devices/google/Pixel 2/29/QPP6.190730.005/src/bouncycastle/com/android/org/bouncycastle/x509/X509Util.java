/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DERNull;
import com.android.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.org.bouncycastle.jce.X509Principal;
import com.android.org.bouncycastle.util.Strings;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import javax.security.auth.x500.X500Principal;

class X509Util {
    private static Hashtable algorithms = new Hashtable();
    private static Set noParams;
    private static Hashtable params;

    static {
        params = new Hashtable();
        noParams = new HashSet();
        algorithms.put("MD5WITHRSAENCRYPTION", PKCSObjectIdentifiers.md5WithRSAEncryption);
        algorithms.put("MD5WITHRSA", PKCSObjectIdentifiers.md5WithRSAEncryption);
        algorithms.put("SHA1WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha1WithRSAEncryption);
        algorithms.put("SHA1WITHRSA", PKCSObjectIdentifiers.sha1WithRSAEncryption);
        algorithms.put("SHA224WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha224WithRSAEncryption);
        algorithms.put("SHA224WITHRSA", PKCSObjectIdentifiers.sha224WithRSAEncryption);
        algorithms.put("SHA256WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha256WithRSAEncryption);
        algorithms.put("SHA256WITHRSA", PKCSObjectIdentifiers.sha256WithRSAEncryption);
        algorithms.put("SHA384WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha384WithRSAEncryption);
        algorithms.put("SHA384WITHRSA", PKCSObjectIdentifiers.sha384WithRSAEncryption);
        algorithms.put("SHA512WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha512WithRSAEncryption);
        algorithms.put("SHA512WITHRSA", PKCSObjectIdentifiers.sha512WithRSAEncryption);
        algorithms.put("SHA1WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        algorithms.put("SHA224WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        algorithms.put("SHA256WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        algorithms.put("SHA384WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        algorithms.put("SHA512WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        algorithms.put("SHA1WITHDSA", X9ObjectIdentifiers.id_dsa_with_sha1);
        algorithms.put("DSAWITHSHA1", X9ObjectIdentifiers.id_dsa_with_sha1);
        algorithms.put("SHA224WITHDSA", NISTObjectIdentifiers.dsa_with_sha224);
        algorithms.put("SHA256WITHDSA", NISTObjectIdentifiers.dsa_with_sha256);
        algorithms.put("SHA384WITHDSA", NISTObjectIdentifiers.dsa_with_sha384);
        algorithms.put("SHA512WITHDSA", NISTObjectIdentifiers.dsa_with_sha512);
        algorithms.put("SHA1WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA1);
        algorithms.put("ECDSAWITHSHA1", X9ObjectIdentifiers.ecdsa_with_SHA1);
        algorithms.put("SHA224WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA224);
        algorithms.put("SHA256WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA256);
        algorithms.put("SHA384WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA384);
        algorithms.put("SHA512WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA512);
        noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA1);
        noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA224);
        noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA256);
        noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA384);
        noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA512);
        noParams.add(X9ObjectIdentifiers.id_dsa_with_sha1);
        noParams.add(NISTObjectIdentifiers.dsa_with_sha224);
        noParams.add(NISTObjectIdentifiers.dsa_with_sha256);
        noParams.add(NISTObjectIdentifiers.dsa_with_sha384);
        noParams.add(NISTObjectIdentifiers.dsa_with_sha512);
        AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE);
        params.put("SHA1WITHRSAANDMGF1", X509Util.creatPSSParams(algorithmIdentifier, 20));
        algorithmIdentifier = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha224, DERNull.INSTANCE);
        params.put("SHA224WITHRSAANDMGF1", X509Util.creatPSSParams(algorithmIdentifier, 28));
        algorithmIdentifier = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256, DERNull.INSTANCE);
        params.put("SHA256WITHRSAANDMGF1", X509Util.creatPSSParams(algorithmIdentifier, 32));
        algorithmIdentifier = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha384, DERNull.INSTANCE);
        params.put("SHA384WITHRSAANDMGF1", X509Util.creatPSSParams(algorithmIdentifier, 48));
        algorithmIdentifier = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha512, DERNull.INSTANCE);
        params.put("SHA512WITHRSAANDMGF1", X509Util.creatPSSParams(algorithmIdentifier, 64));
    }

    X509Util() {
    }

    static byte[] calculateSignature(ASN1ObjectIdentifier object, String string, String string2, PrivateKey privateKey, SecureRandom secureRandom, ASN1Encodable aSN1Encodable) throws IOException, NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        if (object != null) {
            object = X509Util.getSignatureInstance(string, string2);
            if (secureRandom != null) {
                ((Signature)object).initSign(privateKey, secureRandom);
            } else {
                ((Signature)object).initSign(privateKey);
            }
            ((Signature)object).update(aSN1Encodable.toASN1Primitive().getEncoded("DER"));
            return ((Signature)object).sign();
        }
        throw new IllegalStateException("no signature algorithm specified");
    }

    static byte[] calculateSignature(ASN1ObjectIdentifier object, String string, PrivateKey privateKey, SecureRandom secureRandom, ASN1Encodable aSN1Encodable) throws IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        if (object != null) {
            object = X509Util.getSignatureInstance(string);
            if (secureRandom != null) {
                ((Signature)object).initSign(privateKey, secureRandom);
            } else {
                ((Signature)object).initSign(privateKey);
            }
            ((Signature)object).update(aSN1Encodable.toASN1Primitive().getEncoded("DER"));
            return ((Signature)object).sign();
        }
        throw new IllegalStateException("no signature algorithm specified");
    }

    static X509Principal convertPrincipal(X500Principal principal) {
        try {
            principal = new X509Principal(principal.getEncoded());
            return principal;
        }
        catch (IOException iOException) {
            throw new IllegalArgumentException("cannot convert principal");
        }
    }

    private static RSASSAPSSparams creatPSSParams(AlgorithmIdentifier algorithmIdentifier, int n) {
        return new RSASSAPSSparams(algorithmIdentifier, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, algorithmIdentifier), new ASN1Integer(n), new ASN1Integer(1L));
    }

    static Iterator getAlgNames() {
        Enumeration enumeration = algorithms.keys();
        ArrayList arrayList = new ArrayList();
        while (enumeration.hasMoreElements()) {
            arrayList.add(enumeration.nextElement());
        }
        return arrayList.iterator();
    }

    static ASN1ObjectIdentifier getAlgorithmOID(String string) {
        if (algorithms.containsKey(string = Strings.toUpperCase(string))) {
            return (ASN1ObjectIdentifier)algorithms.get(string);
        }
        return new ASN1ObjectIdentifier(string);
    }

    static Implementation getImplementation(String charSequence, String string) throws NoSuchAlgorithmException {
        Provider[] arrprovider = Security.getProviders();
        for (int i = 0; i != arrprovider.length; ++i) {
            Implementation implementation = X509Util.getImplementation((String)charSequence, Strings.toUpperCase(string), arrprovider[i]);
            if (implementation != null) {
                return implementation;
            }
            try {
                X509Util.getImplementation((String)charSequence, string, arrprovider[i]);
                continue;
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                // empty catch block
            }
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("cannot find implementation ");
        ((StringBuilder)charSequence).append(string);
        throw new NoSuchAlgorithmException(((StringBuilder)charSequence).toString());
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Implementation getImplementation(String class_, String charSequence, Provider provider) throws NoSuchAlgorithmException {
        void var1_7;
        void var2_9;
        CharSequence charSequence2;
        String string = Strings.toUpperCase(charSequence);
        do {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("Alg.Alias.");
            ((StringBuilder)charSequence2).append((String)((Object)class_));
            ((StringBuilder)charSequence2).append(".");
            ((StringBuilder)charSequence2).append((String)var1_7);
            charSequence2 = var2_9.getProperty(((StringBuilder)charSequence2).toString());
            if (charSequence2 == null) break;
            CharSequence charSequence3 = charSequence2;
        } while (true);
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)((Object)class_));
        ((StringBuilder)charSequence2).append(".");
        ((StringBuilder)charSequence2).append((String)var1_7);
        charSequence2 = var2_9.getProperty(((StringBuilder)charSequence2).toString());
        if (charSequence2 == null) {
            class_ = new StringBuilder();
            ((StringBuilder)((Object)class_)).append("cannot find implementation ");
            ((StringBuilder)((Object)class_)).append((String)var1_7);
            ((StringBuilder)((Object)class_)).append(" for provider ");
            ((StringBuilder)((Object)class_)).append(var2_9.getName());
            throw new NoSuchAlgorithmException(((StringBuilder)((Object)class_)).toString());
        }
        try {
            class_ = var2_9.getClass().getClassLoader();
            class_ = class_ != null ? ((ClassLoader)((Object)class_)).loadClass((String)charSequence2) : Class.forName((String)charSequence2);
            return new Implementation(class_.newInstance(), (Provider)var2_9);
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("algorithm ");
            stringBuilder.append((String)var1_7);
            stringBuilder.append(" in provider ");
            stringBuilder.append(var2_9.getName());
            stringBuilder.append(" but class \"");
            stringBuilder.append((String)charSequence2);
            stringBuilder.append("\" inaccessible!");
            throw new IllegalStateException(stringBuilder.toString());
        }
        catch (ClassNotFoundException classNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("algorithm ");
            stringBuilder.append((String)var1_7);
            stringBuilder.append(" in provider ");
            stringBuilder.append(var2_9.getName());
            stringBuilder.append(" but no class \"");
            stringBuilder.append((String)charSequence2);
            stringBuilder.append("\" found!");
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    static Provider getProvider(String string) throws NoSuchProviderException {
        Serializable serializable = Security.getProvider(string);
        if (serializable != null) {
            return serializable;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Provider ");
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append(" not found");
        throw new NoSuchProviderException(((StringBuilder)serializable).toString());
    }

    static AlgorithmIdentifier getSigAlgID(ASN1ObjectIdentifier aSN1ObjectIdentifier, String string) {
        if (noParams.contains(aSN1ObjectIdentifier)) {
            return new AlgorithmIdentifier(aSN1ObjectIdentifier);
        }
        if (params.containsKey(string = Strings.toUpperCase(string))) {
            return new AlgorithmIdentifier(aSN1ObjectIdentifier, (ASN1Encodable)params.get(string));
        }
        return new AlgorithmIdentifier(aSN1ObjectIdentifier, DERNull.INSTANCE);
    }

    static Signature getSignatureInstance(String string) throws NoSuchAlgorithmException {
        return Signature.getInstance(string);
    }

    static Signature getSignatureInstance(String string, String string2) throws NoSuchProviderException, NoSuchAlgorithmException {
        if (string2 != null) {
            return Signature.getInstance(string, string2);
        }
        return Signature.getInstance(string);
    }

    static class Implementation {
        Object engine;
        Provider provider;

        Implementation(Object object, Provider provider) {
            this.engine = object;
            this.provider = provider;
        }

        Object getEngine() {
            return this.engine;
        }

        Provider getProvider() {
            return this.provider;
        }
    }

}

