/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.security.Credentials;
import android.security.GateKeeper;
import android.security.KeyStore;
import android.security.keymaster.KeyCharacteristics;
import android.security.keymaster.KeymasterArguments;
import android.security.keystore.AndroidKeyStoreLoadStoreParameter;
import android.security.keystore.AndroidKeyStoreProvider;
import android.security.keystore.DelegatingX509Certificate;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProtection;
import android.security.keystore.SecureKeyImportUnavailableException;
import android.security.keystore.WrappedKeyEntry;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.crypto.SecretKey;

public class AndroidKeyStoreSpi
extends KeyStoreSpi {
    public static final String NAME = "AndroidKeyStore";
    private KeyStore mKeyStore;
    private int mUid = -1;

    private Certificate getCertificateForPrivateKeyEntry(String string2, byte[] object) {
        if ((object = AndroidKeyStoreSpi.toCertificate(object)) == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("USRPKEY_");
        stringBuilder.append(string2);
        string2 = stringBuilder.toString();
        if (this.mKeyStore.contains(string2, this.mUid)) {
            return AndroidKeyStoreSpi.wrapIntoKeyStoreCertificate(string2, this.mUid, (X509Certificate)object);
        }
        return object;
    }

    private Certificate getCertificateForTrustedCertificateEntry(byte[] arrby) {
        return AndroidKeyStoreSpi.toCertificate(arrby);
    }

    private static KeyProtection getLegacyKeyProtectionParameter(PrivateKey object) throws KeyStoreException {
        block4 : {
            block3 : {
                block2 : {
                    if (!"EC".equalsIgnoreCase((String)(object = object.getAlgorithm()))) break block2;
                    object = new KeyProtection.Builder(12);
                    ((KeyProtection.Builder)object).setDigests("NONE", "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512");
                    break block3;
                }
                if (!"RSA".equalsIgnoreCase((String)object)) break block4;
                object = new KeyProtection.Builder(15);
                ((KeyProtection.Builder)object).setDigests("NONE", "MD5", "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512");
                ((KeyProtection.Builder)object).setEncryptionPaddings("NoPadding", "PKCS1Padding", "OAEPPadding");
                ((KeyProtection.Builder)object).setSignaturePaddings("PKCS1", "PSS");
                ((KeyProtection.Builder)object).setRandomizedEncryptionRequired(false);
            }
            ((KeyProtection.Builder)object).setUserAuthenticationRequired(false);
            return ((KeyProtection.Builder)object).build();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported key algorithm: ");
        stringBuilder.append((String)object);
        throw new KeyStoreException(stringBuilder.toString());
    }

    private Date getModificationDate(String string2) {
        long l = this.mKeyStore.getmtime(string2, this.mUid);
        if (l == -1L) {
            return null;
        }
        return new Date(l);
    }

    private Set<String> getUniqueAliases() {
        String[] arrstring = this.mKeyStore.list("", this.mUid);
        if (arrstring == null) {
            return new HashSet<String>();
        }
        HashSet<String> hashSet = new HashSet<String>(arrstring.length);
        for (String string2 : arrstring) {
            int n = string2.indexOf(95);
            if (n != -1 && string2.length() > n) {
                hashSet.add(new String(string2.substring(n + 1)));
                continue;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("invalid alias: ");
            stringBuilder.append(string2);
            Log.e(NAME, stringBuilder.toString());
        }
        return hashSet;
    }

    private boolean isCertificateEntry(String string2) {
        if (string2 != null) {
            KeyStore keyStore = this.mKeyStore;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CACERT_");
            stringBuilder.append(string2);
            return keyStore.contains(stringBuilder.toString(), this.mUid);
        }
        throw new NullPointerException("alias == null");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isKeyEntry(String string2) {
        KeyStore keyStore = this.mKeyStore;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("USRPKEY_");
        stringBuilder.append(string2);
        if (keyStore.contains(stringBuilder.toString(), this.mUid)) return true;
        keyStore = this.mKeyStore;
        stringBuilder = new StringBuilder();
        stringBuilder.append("USRSKEY_");
        stringBuilder.append(string2);
        if (!keyStore.contains(stringBuilder.toString(), this.mUid)) return false;
        return true;
    }

    /*
     * Exception decompiling
     */
    private void setPrivateKeyEntry(String var1_1, PrivateKey var2_6, Certificate[] var3_13, KeyStore.ProtectionParameter var4_14) throws KeyStoreException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [5[TRYBLOCK]], but top level block is 24[FORLOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    private void setSecretKeyEntry(String var1_1, SecretKey var2_6, KeyStore.ProtectionParameter var3_7) throws KeyStoreException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [5[TRYBLOCK]], but top level block is 13[WHILELOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private void setWrappedKeyEntry(String object, WrappedKeyEntry wrappedKeyEntry, KeyStore.ProtectionParameter arrby) throws KeyStoreException {
        if (arrby == null) {
            arrby = new byte[32];
            KeymasterArguments keymasterArguments = new KeymasterArguments();
            Object object2 = wrappedKeyEntry.getTransformation().split("/");
            CharSequence charSequence = object2[0];
            if ("RSA".equalsIgnoreCase((String)charSequence)) {
                keymasterArguments.addEnum(268435458, 1);
            } else if ("EC".equalsIgnoreCase((String)charSequence)) {
                keymasterArguments.addEnum(268435458, 1);
            }
            if (((String[])object2).length > 1) {
                charSequence = object2[1];
                if ("ECB".equalsIgnoreCase((String)charSequence)) {
                    keymasterArguments.addEnums(536870916, 1);
                } else if ("CBC".equalsIgnoreCase((String)charSequence)) {
                    keymasterArguments.addEnums(536870916, 2);
                } else if ("CTR".equalsIgnoreCase((String)charSequence)) {
                    keymasterArguments.addEnums(536870916, 3);
                } else if ("GCM".equalsIgnoreCase((String)charSequence)) {
                    keymasterArguments.addEnums(536870916, 32);
                }
            }
            if (((String[])object2).length > 2 && !"NoPadding".equalsIgnoreCase((String)(object2 = object2[2]))) {
                if ("PKCS7Padding".equalsIgnoreCase((String)object2)) {
                    keymasterArguments.addEnums(536870918, 64);
                } else if ("PKCS1Padding".equalsIgnoreCase((String)object2)) {
                    keymasterArguments.addEnums(536870918, 4);
                } else if ("OAEPPadding".equalsIgnoreCase((String)object2)) {
                    keymasterArguments.addEnums(536870918, 2);
                }
            }
            if (((KeyGenParameterSpec)(object2 = (KeyGenParameterSpec)wrappedKeyEntry.getAlgorithmParameterSpec())).isDigestsSpecified() && !"NONE".equalsIgnoreCase((String)(object2 = ((KeyGenParameterSpec)object2).getDigests()[0]))) {
                if ("MD5".equalsIgnoreCase((String)object2)) {
                    keymasterArguments.addEnums(536870917, 1);
                } else if ("SHA-1".equalsIgnoreCase((String)object2)) {
                    keymasterArguments.addEnums(536870917, 2);
                } else if ("SHA-224".equalsIgnoreCase((String)object2)) {
                    keymasterArguments.addEnums(536870917, 3);
                } else if ("SHA-256".equalsIgnoreCase((String)object2)) {
                    keymasterArguments.addEnums(536870917, 4);
                } else if ("SHA-384".equalsIgnoreCase((String)object2)) {
                    keymasterArguments.addEnums(536870917, 5);
                } else if ("SHA-512".equalsIgnoreCase((String)object2)) {
                    keymasterArguments.addEnums(536870917, 6);
                }
            }
            object2 = this.mKeyStore;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("USRPKEY_");
            ((StringBuilder)charSequence).append((String)object);
            charSequence = ((StringBuilder)charSequence).toString();
            object = wrappedKeyEntry.getWrappedKeyBytes();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("USRPKEY_");
            stringBuilder.append(wrappedKeyEntry.getWrappingKeyAlias());
            int n = ((KeyStore)object2).importWrappedKey((String)charSequence, (byte[])object, stringBuilder.toString(), arrby, keymasterArguments, GateKeeper.getSecureUserId(), 0L, this.mUid, new KeyCharacteristics());
            if (n != -100) {
                if (n == 1) {
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to import wrapped key. Keystore error code: ");
                ((StringBuilder)object).append(n);
                throw new KeyStoreException(((StringBuilder)object).toString());
            }
            throw new SecureKeyImportUnavailableException("Could not import wrapped key");
        }
        throw new KeyStoreException("Protection parameters are specified inside wrapped keys");
    }

    private static X509Certificate toCertificate(byte[] object) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])object);
            object = (X509Certificate)certificateFactory.generateCertificate(byteArrayInputStream);
            return object;
        }
        catch (CertificateException certificateException) {
            Log.w(NAME, "Couldn't parse certificate in keystore", certificateException);
            return null;
        }
    }

    private static Collection<X509Certificate> toCertificates(byte[] object) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])object);
            object = certificateFactory.generateCertificates(byteArrayInputStream);
            return object;
        }
        catch (CertificateException certificateException) {
            Log.w(NAME, "Couldn't parse certificates in keystore", certificateException);
            return new ArrayList<X509Certificate>();
        }
    }

    private static KeyStoreX509Certificate wrapIntoKeyStoreCertificate(String object, int n, X509Certificate x509Certificate) {
        object = x509Certificate != null ? new KeyStoreX509Certificate((String)object, n, x509Certificate) : null;
        return object;
    }

    @Override
    public Enumeration<String> engineAliases() {
        return Collections.enumeration(this.getUniqueAliases());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean engineContainsAlias(String string2) {
        if (string2 == null) throw new NullPointerException("alias == null");
        Object object = this.mKeyStore;
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("USRPKEY_");
        ((StringBuilder)object2).append(string2);
        if (((KeyStore)object).contains(((StringBuilder)object2).toString(), this.mUid)) return true;
        object2 = this.mKeyStore;
        object = new StringBuilder();
        ((StringBuilder)object).append("USRSKEY_");
        ((StringBuilder)object).append(string2);
        if (((KeyStore)object2).contains(((StringBuilder)object).toString(), this.mUid)) return true;
        object2 = this.mKeyStore;
        object = new StringBuilder();
        ((StringBuilder)object).append("USRCERT_");
        ((StringBuilder)object).append(string2);
        if (((KeyStore)object2).contains(((StringBuilder)object).toString(), this.mUid)) return true;
        object = this.mKeyStore;
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("CACERT_");
        ((StringBuilder)object2).append(string2);
        if (!((KeyStore)object).contains(((StringBuilder)object2).toString(), this.mUid)) return false;
        return true;
    }

    @Override
    public void engineDeleteEntry(String string2) throws KeyStoreException {
        if (Credentials.deleteAllTypesForAlias(this.mKeyStore, string2, this.mUid)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to delete entry: ");
        stringBuilder.append(string2);
        throw new KeyStoreException(stringBuilder.toString());
    }

    @Override
    public Certificate engineGetCertificate(String arrby) {
        if (arrby != null) {
            Object object = this.mKeyStore;
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("USRCERT_");
            ((StringBuilder)object2).append((String)arrby);
            object2 = ((KeyStore)object).get(((StringBuilder)object2).toString(), this.mUid);
            if (object2 != null) {
                return this.getCertificateForPrivateKeyEntry((String)arrby, (byte[])object2);
            }
            object2 = this.mKeyStore;
            object = new StringBuilder();
            ((StringBuilder)object).append("CACERT_");
            ((StringBuilder)object).append((String)arrby);
            arrby = ((KeyStore)object2).get(((StringBuilder)object).toString(), this.mUid);
            if (arrby != null) {
                return this.getCertificateForTrustedCertificateEntry(arrby);
            }
            return null;
        }
        throw new NullPointerException("alias == null");
    }

    @Override
    public String engineGetCertificateAlias(Certificate serializable) {
        int n;
        byte[] arrby;
        Object object;
        Object object2;
        Object object3;
        byte[] arrby2;
        int n2;
        block8 : {
            if (serializable == null) {
                return null;
            }
            if (!"X.509".equalsIgnoreCase(((Certificate)serializable).getType())) {
                return null;
            }
            try {
                arrby = ((Certificate)serializable).getEncoded();
                if (arrby == null) {
                    return null;
                }
                serializable = new HashSet();
                object = this.mKeyStore.list("USRCERT_", this.mUid);
                int n3 = 0;
                if (object == null) break block8;
            }
            catch (CertificateEncodingException certificateEncodingException) {
                return null;
            }
            n = ((String[])object).length;
            for (n2 = 0; n2 < n; ++n2) {
                object2 = object[n2];
                arrby2 = this.mKeyStore;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("USRCERT_");
                ((StringBuilder)object3).append((String)object2);
                arrby2 = arrby2.get(((StringBuilder)object3).toString(), this.mUid);
                if (arrby2 == null) continue;
                serializable.add(object2);
                if (!Arrays.equals(arrby2, arrby)) continue;
                return object2;
            }
        }
        object2 = this.mKeyStore.list("CACERT_", this.mUid);
        if (object != null) {
            n = ((String[])object2).length;
            for (n2 = n3; n2 < n; ++n2) {
                object = object2[n2];
                if (serializable.contains(object)) continue;
                object3 = this.mKeyStore;
                arrby2 = new StringBuilder();
                arrby2.append("CACERT_");
                arrby2.append((String)object);
                arrby2 = ((KeyStore)object3).get(arrby2.toString(), this.mUid);
                if (arrby2 == null || !Arrays.equals(arrby2, arrby)) continue;
                return object;
            }
        }
        return null;
    }

    @Override
    public Certificate[] engineGetCertificateChain(String arrobject) {
        if (arrobject != null) {
            X509Certificate x509Certificate = (X509Certificate)this.engineGetCertificate((String)arrobject);
            if (x509Certificate == null) {
                return null;
            }
            Iterator iterator = this.mKeyStore;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CACERT_");
            stringBuilder.append((String)arrobject);
            arrobject = ((KeyStore)((Object)iterator)).get(stringBuilder.toString(), this.mUid, true);
            if (arrobject != null) {
                iterator = AndroidKeyStoreSpi.toCertificates(arrobject);
                arrobject = new Certificate[iterator.size() + 1];
                iterator = iterator.iterator();
                int n = 1;
                while (iterator.hasNext()) {
                    arrobject[n] = (byte)((Certificate)iterator.next());
                    ++n;
                }
            } else {
                arrobject = new Certificate[]{(byte)x509Certificate};
            }
            return arrobject;
        }
        throw new NullPointerException("alias == null");
    }

    @Override
    public Date engineGetCreationDate(String string2) {
        if (string2 != null) {
            Serializable serializable = new StringBuilder();
            ((StringBuilder)serializable).append("USRPKEY_");
            ((StringBuilder)serializable).append(string2);
            serializable = this.getModificationDate(((StringBuilder)serializable).toString());
            if (serializable != null) {
                return serializable;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("USRSKEY_");
            ((StringBuilder)serializable).append(string2);
            serializable = this.getModificationDate(((StringBuilder)serializable).toString());
            if (serializable != null) {
                return serializable;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("USRCERT_");
            ((StringBuilder)serializable).append(string2);
            serializable = this.getModificationDate(((StringBuilder)serializable).toString());
            if (serializable != null) {
                return serializable;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("CACERT_");
            ((StringBuilder)serializable).append(string2);
            return this.getModificationDate(((StringBuilder)serializable).toString());
        }
        throw new NullPointerException("alias == null");
    }

    @Override
    public Key engineGetKey(String object, char[] object2) throws NoSuchAlgorithmException, UnrecoverableKeyException {
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("USRPKEY_");
        ((StringBuilder)object2).append((String)object);
        String string2 = ((StringBuilder)object2).toString();
        object2 = string2;
        if (!this.mKeyStore.contains(string2, this.mUid)) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("USRSKEY_");
            ((StringBuilder)object2).append((String)object);
            object2 = object = ((StringBuilder)object2).toString();
            if (!this.mKeyStore.contains((String)object, this.mUid)) {
                return null;
            }
        }
        try {
            object = AndroidKeyStoreProvider.loadAndroidKeyStoreKeyFromKeystore(this.mKeyStore, (String)object2, this.mUid);
            return object;
        }
        catch (KeyPermanentlyInvalidatedException keyPermanentlyInvalidatedException) {
            throw new UnrecoverableKeyException(keyPermanentlyInvalidatedException.getMessage());
        }
    }

    @Override
    public boolean engineIsCertificateEntry(String string2) {
        boolean bl = !this.isKeyEntry(string2) && this.isCertificateEntry(string2);
        return bl;
    }

    @Override
    public boolean engineIsKeyEntry(String string2) {
        return this.isKeyEntry(string2);
    }

    @Override
    public void engineLoad(InputStream inputStream, char[] arrc) throws IOException, NoSuchAlgorithmException, CertificateException {
        if (inputStream == null) {
            if (arrc == null) {
                this.mKeyStore = KeyStore.getInstance();
                this.mUid = -1;
                return;
            }
            throw new IllegalArgumentException("password not supported");
        }
        throw new IllegalArgumentException("InputStream not supported");
    }

    @Override
    public void engineLoad(KeyStore.LoadStoreParameter loadStoreParameter) throws IOException, NoSuchAlgorithmException, CertificateException {
        int n = -1;
        if (loadStoreParameter != null) {
            if (loadStoreParameter instanceof AndroidKeyStoreLoadStoreParameter) {
                n = ((AndroidKeyStoreLoadStoreParameter)loadStoreParameter).getUid();
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported param type: ");
                stringBuilder.append(loadStoreParameter.getClass());
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
        this.mKeyStore = KeyStore.getInstance();
        this.mUid = n;
    }

    @Override
    public void engineSetCertificateEntry(String string2, Certificate object) throws KeyStoreException {
        if (!this.isKeyEntry(string2)) {
            if (object != null) {
                StringBuilder stringBuilder;
                byte[] arrby;
                try {
                    arrby = ((Certificate)object).getEncoded();
                    object = this.mKeyStore;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("CACERT_");
                }
                catch (CertificateEncodingException certificateEncodingException) {
                    throw new KeyStoreException(certificateEncodingException);
                }
                stringBuilder.append(string2);
                if (((KeyStore)object).put(stringBuilder.toString(), arrby, this.mUid, 0)) {
                    return;
                }
                throw new KeyStoreException("Couldn't insert certificate; is KeyStore initialized?");
            }
            throw new NullPointerException("cert == null");
        }
        throw new KeyStoreException("Entry exists and is not a trusted certificate");
    }

    @Override
    public void engineSetEntry(String charSequence, KeyStore.Entry entry, KeyStore.ProtectionParameter protectionParameter) throws KeyStoreException {
        block4 : {
            block8 : {
                block6 : {
                    block7 : {
                        block5 : {
                            if (entry == null) break block4;
                            Credentials.deleteAllTypesForAlias(this.mKeyStore, (String)charSequence, this.mUid);
                            if (entry instanceof KeyStore.TrustedCertificateEntry) {
                                this.engineSetCertificateEntry((String)charSequence, ((KeyStore.TrustedCertificateEntry)entry).getTrustedCertificate());
                                return;
                            }
                            if (!(entry instanceof KeyStore.PrivateKeyEntry)) break block5;
                            entry = (KeyStore.PrivateKeyEntry)entry;
                            this.setPrivateKeyEntry((String)charSequence, ((KeyStore.PrivateKeyEntry)entry).getPrivateKey(), ((KeyStore.PrivateKeyEntry)entry).getCertificateChain(), protectionParameter);
                            break block6;
                        }
                        if (!(entry instanceof KeyStore.SecretKeyEntry)) break block7;
                        this.setSecretKeyEntry((String)charSequence, ((KeyStore.SecretKeyEntry)entry).getSecretKey(), protectionParameter);
                        break block6;
                    }
                    if (!(entry instanceof WrappedKeyEntry)) break block8;
                    this.setWrappedKeyEntry((String)charSequence, (WrappedKeyEntry)entry, protectionParameter);
                }
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Entry must be a PrivateKeyEntry, SecretKeyEntry or TrustedCertificateEntry; was ");
            ((StringBuilder)charSequence).append(entry);
            throw new KeyStoreException(((StringBuilder)charSequence).toString());
        }
        throw new KeyStoreException("entry == null");
    }

    @Override
    public void engineSetKeyEntry(String string2, Key key, char[] arrc, Certificate[] arrcertificate) throws KeyStoreException {
        block6 : {
            block5 : {
                block4 : {
                    if (arrc != null && arrc.length > 0) {
                        throw new KeyStoreException("entries cannot be protected with passwords");
                    }
                    if (!(key instanceof PrivateKey)) break block4;
                    this.setPrivateKeyEntry(string2, (PrivateKey)key, arrcertificate, null);
                    break block5;
                }
                if (!(key instanceof SecretKey)) break block6;
                this.setSecretKeyEntry(string2, (SecretKey)key, null);
            }
            return;
        }
        throw new KeyStoreException("Only PrivateKey and SecretKey are supported");
    }

    @Override
    public void engineSetKeyEntry(String string2, byte[] arrby, Certificate[] arrcertificate) throws KeyStoreException {
        throw new KeyStoreException("Operation not supported because key encoding is unknown");
    }

    @Override
    public int engineSize() {
        return this.getUniqueAliases().size();
    }

    @Override
    public void engineStore(OutputStream outputStream, char[] arrc) throws IOException, NoSuchAlgorithmException, CertificateException {
        throw new UnsupportedOperationException("Can not serialize AndroidKeyStore to OutputStream");
    }

    static class KeyStoreX509Certificate
    extends DelegatingX509Certificate {
        private final String mPrivateKeyAlias;
        private final int mPrivateKeyUid;

        KeyStoreX509Certificate(String string2, int n, X509Certificate x509Certificate) {
            super(x509Certificate);
            this.mPrivateKeyAlias = string2;
            this.mPrivateKeyUid = n;
        }

        @Override
        public PublicKey getPublicKey() {
            PublicKey publicKey = super.getPublicKey();
            return AndroidKeyStoreProvider.getAndroidKeyStorePublicKey(this.mPrivateKeyAlias, this.mPrivateKeyUid, publicKey.getAlgorithm(), publicKey.getEncoded());
        }
    }

}

