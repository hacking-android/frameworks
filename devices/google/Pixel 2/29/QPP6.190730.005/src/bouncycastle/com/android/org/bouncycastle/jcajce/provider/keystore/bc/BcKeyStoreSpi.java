/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.keystore.bc;

import com.android.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.Mac;
import com.android.org.bouncycastle.crypto.PBEParametersGenerator;
import com.android.org.bouncycastle.crypto.digests.SHA1Digest;
import com.android.org.bouncycastle.crypto.generators.PKCS12ParametersGenerator;
import com.android.org.bouncycastle.crypto.io.DigestInputStream;
import com.android.org.bouncycastle.crypto.io.DigestOutputStream;
import com.android.org.bouncycastle.crypto.io.MacInputStream;
import com.android.org.bouncycastle.crypto.io.MacOutputStream;
import com.android.org.bouncycastle.crypto.macs.HMac;
import com.android.org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import com.android.org.bouncycastle.jcajce.util.JcaJceHelper;
import com.android.org.bouncycastle.jce.interfaces.BCKeyStore;
import com.android.org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.io.Streams;
import com.android.org.bouncycastle.util.io.TeeOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class BcKeyStoreSpi
extends KeyStoreSpi
implements BCKeyStore {
    static final int CERTIFICATE = 1;
    static final int KEY = 2;
    private static final String KEY_CIPHER = "PBEWithSHAAnd3-KeyTripleDES-CBC";
    static final int KEY_PRIVATE = 0;
    static final int KEY_PUBLIC = 1;
    private static final int KEY_SALT_SIZE = 20;
    static final int KEY_SECRET = 2;
    private static final int MIN_ITERATIONS = 1024;
    static final int NULL = 0;
    static final int SEALED = 4;
    static final int SECRET = 3;
    private static final String STORE_CIPHER = "PBEWithSHAAndTwofish-CBC";
    private static final int STORE_SALT_SIZE = 20;
    private static final int STORE_VERSION = 2;
    private final JcaJceHelper helper = new DefaultJcaJceHelper();
    protected SecureRandom random = CryptoServicesRegistrar.getSecureRandom();
    protected Hashtable table = new Hashtable();
    protected int version;

    public BcKeyStoreSpi(int n) {
        this.version = n;
    }

    static /* synthetic */ Key access$100(BcKeyStoreSpi bcKeyStoreSpi, DataInputStream dataInputStream) throws IOException {
        return bcKeyStoreSpi.decodeKey(dataInputStream);
    }

    private Certificate decodeCertificate(DataInputStream object) throws IOException {
        Object object2 = ((DataInputStream)object).readUTF();
        byte[] arrby = new byte[((DataInputStream)object).readInt()];
        ((DataInputStream)object).readFully(arrby);
        try {
            object2 = this.helper.createCertificateFactory((String)object2);
            object = new ByteArrayInputStream(arrby);
            object = ((CertificateFactory)object2).generateCertificate((InputStream)object);
            return object;
        }
        catch (CertificateException certificateException) {
            throw new IOException(certificateException.toString());
        }
        catch (NoSuchProviderException noSuchProviderException) {
            throw new IOException(noSuchProviderException.toString());
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private Key decodeKey(DataInputStream var1_1) throws IOException {
        var2_3 = var1_1.read();
        var3_4 = var1_1.readUTF();
        var4_5 = var1_1.readUTF();
        var5_6 = new byte[var1_1.readInt()];
        var1_1.readFully(var5_6);
        if (!var3_4.equals("PKCS#8") && !var3_4.equals("PKCS8")) {
            if (!var3_4.equals("X.509") && !var3_4.equals("X509")) {
                if (var3_4.equals("RAW")) {
                    return new SecretKeySpec(var5_6, (String)var4_5);
                }
                var1_1 = new StringBuilder();
                var1_1.append("Key format ");
                var1_1.append(var3_4);
                var1_1.append(" not recognised!");
                throw new IOException(var1_1.toString());
            }
            var1_1 = new X509EncodedKeySpec(var5_6);
        } else {
            var1_1 = new PKCS8EncodedKeySpec(var5_6);
        }
        if (var2_3 == 0) return BouncyCastleProvider.getPrivateKey(PrivateKeyInfo.getInstance(var5_6));
        if (var2_3 == 1) return BouncyCastleProvider.getPublicKey(SubjectPublicKeyInfo.getInstance(var5_6));
        if (var2_3 != 2) ** GOTO lbl26
        try {
            return this.helper.createSecretKeyFactory((String)var4_5).generateSecret((KeySpec)var1_1);
lbl26: // 1 sources:
            var4_5 = new StringBuilder();
            var4_5.append("Key type ");
            var4_5.append(var2_3);
            var4_5.append(" not recognised!");
            var1_1 = new IOException(var4_5.toString());
            throw var1_1;
        }
        catch (Exception var1_2) {}
        var4_5 = new StringBuilder();
        var4_5.append("Exception creating key: ");
        var4_5.append(var1_2.toString());
        throw new IOException(var4_5.toString());
    }

    private void encodeCertificate(Certificate certificate, DataOutputStream dataOutputStream) throws IOException {
        try {
            byte[] arrby = certificate.getEncoded();
            dataOutputStream.writeUTF(certificate.getType());
            dataOutputStream.writeInt(arrby.length);
            dataOutputStream.write(arrby);
            return;
        }
        catch (CertificateEncodingException certificateEncodingException) {
            throw new IOException(certificateEncodingException.toString());
        }
    }

    private void encodeKey(Key key, DataOutputStream dataOutputStream) throws IOException {
        byte[] arrby = key.getEncoded();
        if (key instanceof PrivateKey) {
            dataOutputStream.write(0);
        } else if (key instanceof PublicKey) {
            dataOutputStream.write(1);
        } else {
            dataOutputStream.write(2);
        }
        dataOutputStream.writeUTF(key.getFormat());
        dataOutputStream.writeUTF(key.getAlgorithm());
        dataOutputStream.writeInt(arrby.length);
        dataOutputStream.write(arrby);
    }

    public Enumeration engineAliases() {
        return this.table.keys();
    }

    @Override
    public boolean engineContainsAlias(String string) {
        boolean bl = this.table.get(string) != null;
        return bl;
    }

    @Override
    public void engineDeleteEntry(String string) throws KeyStoreException {
        if (this.table.get(string) == null) {
            return;
        }
        this.table.remove(string);
    }

    @Override
    public Certificate engineGetCertificate(String arrcertificate) {
        if ((arrcertificate = (StoreEntry)this.table.get(arrcertificate)) != null) {
            if (arrcertificate.getType() == 1) {
                return (Certificate)arrcertificate.getObject();
            }
            if ((arrcertificate = arrcertificate.getCertificateChain()) != null) {
                return arrcertificate[0];
            }
        }
        return null;
    }

    @Override
    public String engineGetCertificateAlias(Certificate certificate) {
        Enumeration enumeration = this.table.elements();
        while (enumeration.hasMoreElements()) {
            Certificate[] arrcertificate;
            StoreEntry storeEntry = (StoreEntry)enumeration.nextElement();
            if (!(storeEntry.getObject() instanceof Certificate ? ((Certificate)storeEntry.getObject()).equals(certificate) : (arrcertificate = storeEntry.getCertificateChain()) != null && arrcertificate[0].equals(certificate))) continue;
            return storeEntry.getAlias();
        }
        return null;
    }

    @Override
    public Certificate[] engineGetCertificateChain(String object) {
        if ((object = (StoreEntry)this.table.get(object)) != null) {
            return ((StoreEntry)object).getCertificateChain();
        }
        return null;
    }

    @Override
    public Date engineGetCreationDate(String object) {
        if ((object = (StoreEntry)this.table.get(object)) != null) {
            return ((StoreEntry)object).getDate();
        }
        return null;
    }

    @Override
    public Key engineGetKey(String object, char[] arrc) throws NoSuchAlgorithmException, UnrecoverableKeyException {
        if ((object = (StoreEntry)this.table.get(object)) != null && ((StoreEntry)object).getType() != 1) {
            return (Key)((StoreEntry)object).getObject(arrc);
        }
        return null;
    }

    @Override
    public boolean engineIsCertificateEntry(String object) {
        return (object = (StoreEntry)this.table.get(object)) != null && ((StoreEntry)object).getType() == 1;
    }

    @Override
    public boolean engineIsKeyEntry(String object) {
        return (object = (StoreEntry)this.table.get(object)) != null && ((StoreEntry)object).getType() != 1;
    }

    @Override
    public void engineLoad(InputStream object, char[] arrobject) throws IOException {
        this.table.clear();
        if (object == null) {
            return;
        }
        DataInputStream dataInputStream = new DataInputStream((InputStream)object);
        int n = dataInputStream.readInt();
        if (n != 2 && n != 0 && n != 1) {
            throw new IOException("Wrong version of key store.");
        }
        int n2 = dataInputStream.readInt();
        if (n2 > 0) {
            object = new byte[n2];
            dataInputStream.readFully((byte[])object);
            n2 = dataInputStream.readInt();
            HMac hMac = new HMac(new SHA1Digest());
            if (arrobject != null && arrobject.length != 0) {
                arrobject = PBEParametersGenerator.PKCS12PasswordToBytes(arrobject);
                PKCS12ParametersGenerator pKCS12ParametersGenerator = new PKCS12ParametersGenerator(new SHA1Digest());
                pKCS12ParametersGenerator.init((byte[])arrobject, (byte[])object, n2);
                object = n != 2 ? ((PBEParametersGenerator)pKCS12ParametersGenerator).generateDerivedMacParameters(hMac.getMacSize()) : ((PBEParametersGenerator)pKCS12ParametersGenerator).generateDerivedMacParameters(hMac.getMacSize() * 8);
                Arrays.fill((byte[])arrobject, (byte)0);
                hMac.init((CipherParameters)object);
                this.loadStore(new MacInputStream(dataInputStream, hMac));
                object = new byte[hMac.getMacSize()];
                hMac.doFinal((byte[])object, 0);
                arrobject = new byte[hMac.getMacSize()];
                dataInputStream.readFully((byte[])arrobject);
                if (!Arrays.constantTimeAreEqual(object, (byte[])arrobject)) {
                    this.table.clear();
                    throw new IOException("KeyStore integrity check failed.");
                }
            } else {
                this.loadStore(dataInputStream);
                dataInputStream.readFully(new byte[hMac.getMacSize()]);
            }
            return;
        }
        throw new IOException("Invalid salt detected");
    }

    @Override
    public void engineSetCertificateEntry(String string, Certificate serializable) throws KeyStoreException {
        StoreEntry storeEntry = (StoreEntry)this.table.get(string);
        if (storeEntry != null && storeEntry.getType() != 1) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("key store already has a key entry with alias ");
            ((StringBuilder)serializable).append(string);
            throw new KeyStoreException(((StringBuilder)serializable).toString());
        }
        this.table.put(string, new StoreEntry(string, (Certificate)serializable));
    }

    @Override
    public void engineSetKeyEntry(String string, Key key, char[] arrc, Certificate[] arrcertificate) throws KeyStoreException {
        if (key instanceof PrivateKey && arrcertificate == null) {
            throw new KeyStoreException("no certificate chain for private key");
        }
        try {
            Hashtable hashtable = this.table;
            StoreEntry storeEntry = new StoreEntry(string, key, arrc, arrcertificate);
            hashtable.put(string, storeEntry);
            return;
        }
        catch (Exception exception) {
            throw new KeyStoreException(exception.toString());
        }
    }

    @Override
    public void engineSetKeyEntry(String string, byte[] arrby, Certificate[] arrcertificate) throws KeyStoreException {
        this.table.put(string, new StoreEntry(string, arrby, arrcertificate));
    }

    @Override
    public int engineSize() {
        return this.table.size();
    }

    @Override
    public void engineStore(OutputStream object, char[] arrobject) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream((OutputStream)object);
        byte[] arrby = new byte[20];
        int n = (this.random.nextInt() & 1023) + 1024;
        this.random.nextBytes(arrby);
        dataOutputStream.writeInt(this.version);
        dataOutputStream.writeInt(arrby.length);
        dataOutputStream.write(arrby);
        dataOutputStream.writeInt(n);
        object = new HMac(new SHA1Digest());
        MacOutputStream macOutputStream = new MacOutputStream((Mac)object);
        PKCS12ParametersGenerator pKCS12ParametersGenerator = new PKCS12ParametersGenerator(new SHA1Digest());
        arrobject = PBEParametersGenerator.PKCS12PasswordToBytes(arrobject);
        pKCS12ParametersGenerator.init((byte[])arrobject, arrby, n);
        if (this.version < 2) {
            ((HMac)object).init(((PBEParametersGenerator)pKCS12ParametersGenerator).generateDerivedMacParameters(((HMac)object).getMacSize()));
        } else {
            ((HMac)object).init(((PBEParametersGenerator)pKCS12ParametersGenerator).generateDerivedMacParameters(((HMac)object).getMacSize() * 8));
        }
        for (n = 0; n != arrobject.length; ++n) {
            arrobject[n] = (char)(false ? 1 : 0);
        }
        this.saveStore(new TeeOutputStream(dataOutputStream, macOutputStream));
        arrobject = new byte[((HMac)object).getMacSize()];
        ((HMac)object).doFinal((byte[])arrobject, 0);
        dataOutputStream.write((byte[])arrobject);
        dataOutputStream.close();
    }

    protected void loadStore(InputStream arrcertificate) throws IOException {
        DataInputStream dataInputStream = new DataInputStream((InputStream)arrcertificate);
        int n = dataInputStream.read();
        while (n > 0) {
            String string = dataInputStream.readUTF();
            Date date = new Date(dataInputStream.readLong());
            int n2 = dataInputStream.readInt();
            if (n2 != 0) {
                arrcertificate = new Certificate[n2];
                for (int i = 0; i != n2; ++i) {
                    arrcertificate[i] = this.decodeCertificate(dataInputStream);
                }
            } else {
                arrcertificate = null;
            }
            if (n != 1) {
                byte[] arrby;
                if (n != 2) {
                    if (n != 3 && n != 4) {
                        throw new IOException("Unknown object type in store.");
                    }
                    arrby = new byte[dataInputStream.readInt()];
                    dataInputStream.readFully(arrby);
                    this.table.put(string, new StoreEntry(string, date, n, arrby, arrcertificate));
                } else {
                    arrby = this.decodeKey(dataInputStream);
                    this.table.put(string, new StoreEntry(string, date, 2, arrby, arrcertificate));
                }
            } else {
                arrcertificate = this.decodeCertificate(dataInputStream);
                this.table.put(string, new StoreEntry(string, date, 1, (Object)arrcertificate));
            }
            n = dataInputStream.read();
        }
    }

    protected Cipher makePBECipher(String object, int n, char[] object2, byte[] arrby, int n2) throws IOException {
        try {
            PBEKeySpec pBEKeySpec = new PBEKeySpec((char[])object2);
            object2 = this.helper.createSecretKeyFactory((String)object);
            PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(arrby, n2);
            object = this.helper.createCipher((String)object);
            ((Cipher)object).init(n, (Key)((SecretKeyFactory)object2).generateSecret(pBEKeySpec), pBEParameterSpec);
            return object;
        }
        catch (Exception exception) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Error initialising store of key store: ");
            ((StringBuilder)object2).append(exception);
            throw new IOException(((StringBuilder)object2).toString());
        }
    }

    protected void saveStore(OutputStream outputStream) throws IOException {
        Enumeration enumeration = this.table.elements();
        outputStream = new DataOutputStream(outputStream);
        while (enumeration.hasMoreElements()) {
            int n;
            StoreEntry storeEntry = (StoreEntry)enumeration.nextElement();
            ((DataOutputStream)outputStream).write(storeEntry.getType());
            ((DataOutputStream)outputStream).writeUTF(storeEntry.getAlias());
            ((DataOutputStream)outputStream).writeLong(storeEntry.getDate().getTime());
            Object[] arrobject = storeEntry.getCertificateChain();
            if (arrobject == null) {
                ((DataOutputStream)outputStream).writeInt(0);
            } else {
                ((DataOutputStream)outputStream).writeInt(arrobject.length);
                for (n = 0; n != arrobject.length; ++n) {
                    this.encodeCertificate(arrobject[n], (DataOutputStream)outputStream);
                }
            }
            n = storeEntry.getType();
            if (n != 1) {
                if (n != 2) {
                    if (n != 3 && n != 4) {
                        throw new IOException("Unknown object type in store.");
                    }
                    arrobject = (byte[])storeEntry.getObject();
                    ((DataOutputStream)outputStream).writeInt(arrobject.length);
                    ((FilterOutputStream)outputStream).write((byte[])arrobject);
                    continue;
                }
                this.encodeKey((Key)storeEntry.getObject(), (DataOutputStream)outputStream);
                continue;
            }
            this.encodeCertificate((Certificate)storeEntry.getObject(), (DataOutputStream)outputStream);
        }
        ((DataOutputStream)outputStream).write(0);
    }

    @Override
    public void setRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
    }

    public static class BouncyCastleStore
    extends BcKeyStoreSpi {
        public BouncyCastleStore() {
            super(1);
        }

        @Override
        public void engineLoad(InputStream object, char[] object2) throws IOException {
            this.table.clear();
            if (object == null) {
                return;
            }
            Object object3 = new DataInputStream((InputStream)object);
            int n = object3.readInt();
            if (n != 2 && n != 0 && n != 1) {
                throw new IOException("Wrong version of key store.");
            }
            byte[] arrby = new byte[object3.readInt()];
            if (arrby.length == 20) {
                object3.readFully(arrby);
                int n2 = object3.readInt();
                if (n2 >= 0 && n2 <= 65536) {
                    object = n == 0 ? "OldPBEWithSHAAndTwofish-CBC" : BcKeyStoreSpi.STORE_CIPHER;
                    object2 = new CipherInputStream((InputStream)object3, this.makePBECipher((String)object, 2, (char[])object2, arrby, n2));
                    object3 = new SHA1Digest();
                    this.loadStore(new DigestInputStream((InputStream)object2, (Digest)object3));
                    object = new byte[object3.getDigestSize()];
                    object3.doFinal((byte[])object, 0);
                    object3 = new byte[object3.getDigestSize()];
                    Streams.readFully((InputStream)object2, object3);
                    if (Arrays.constantTimeAreEqual(object, object3)) {
                        return;
                    }
                    this.table.clear();
                    throw new IOException("KeyStore integrity check failed.");
                }
                throw new IOException("Key store corrupted.");
            }
            throw new IOException("Key store corrupted.");
        }

        @Override
        public void engineStore(OutputStream object, char[] object2) throws IOException {
            DataOutputStream dataOutputStream = new DataOutputStream((OutputStream)object);
            object = new byte[20];
            int n = (this.random.nextInt() & 1023) + 1024;
            this.random.nextBytes((byte[])object);
            dataOutputStream.writeInt(this.version);
            dataOutputStream.writeInt(((byte[])object).length);
            dataOutputStream.write((byte[])object);
            dataOutputStream.writeInt(n);
            object = new CipherOutputStream(dataOutputStream, this.makePBECipher(BcKeyStoreSpi.STORE_CIPHER, 1, (char[])object2, (byte[])object, n));
            object2 = new DigestOutputStream(new SHA1Digest());
            this.saveStore(new TeeOutputStream((OutputStream)object, (OutputStream)object2));
            ((CipherOutputStream)object).write(((DigestOutputStream)object2).getDigest());
            ((CipherOutputStream)object).close();
        }
    }

    public static class Std
    extends BcKeyStoreSpi {
        public Std() {
            super(2);
        }
    }

    private class StoreEntry {
        String alias;
        Certificate[] certChain;
        Date date = new Date();
        Object obj;
        int type;

        StoreEntry(String object, Key key, char[] object2, Certificate[] arrobject) throws Exception {
            this.type = 4;
            this.alias = object;
            this.certChain = arrobject;
            arrobject = new byte[20];
            BcKeyStoreSpi.this.random.setSeed(System.currentTimeMillis());
            BcKeyStoreSpi.this.random.nextBytes((byte[])arrobject);
            int n = (BcKeyStoreSpi.this.random.nextInt() & 1023) + 1024;
            object = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream((OutputStream)object);
            dataOutputStream.writeInt(arrobject.length);
            dataOutputStream.write((byte[])arrobject);
            dataOutputStream.writeInt(n);
            object2 = new DataOutputStream(new CipherOutputStream(dataOutputStream, BcKeyStoreSpi.this.makePBECipher(BcKeyStoreSpi.KEY_CIPHER, 1, (char[])object2, (byte[])arrobject, n)));
            BcKeyStoreSpi.this.encodeKey(key, (DataOutputStream)object2);
            ((FilterOutputStream)object2).close();
            this.obj = ((ByteArrayOutputStream)object).toByteArray();
        }

        StoreEntry(String string, Certificate certificate) {
            this.type = 1;
            this.alias = string;
            this.obj = certificate;
            this.certChain = null;
        }

        StoreEntry(String string, Date date, int n, Object object) {
            this.alias = string;
            this.date = date;
            this.type = n;
            this.obj = object;
        }

        StoreEntry(String string, Date date, int n, Object object, Certificate[] arrcertificate) {
            this.alias = string;
            this.date = date;
            this.type = n;
            this.obj = object;
            this.certChain = arrcertificate;
        }

        StoreEntry(String string, byte[] arrby, Certificate[] arrcertificate) {
            this.type = 3;
            this.alias = string;
            this.obj = arrby;
            this.certChain = arrcertificate;
        }

        String getAlias() {
            return this.alias;
        }

        Certificate[] getCertificateChain() {
            return this.certChain;
        }

        Date getDate() {
            return this.date;
        }

        Object getObject() {
            return this.obj;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        Object getObject(char[] object) throws NoSuchAlgorithmException, UnrecoverableKeyException {
            if ((object == null || ((Object)object).length == 0) && (object2 /* !! */  = this.obj) instanceof Key) {
                return object2 /* !! */ ;
            }
            if (this.type != 4) throw new RuntimeException("forget something!");
            object3 = new DataInputStream(new ByteArrayInputStream((byte[])this.obj));
            object2 /* !! */  = new byte[object3.readInt()];
            object3.readFully(object2 /* !! */ );
            n = object3.readInt();
            object4 = BcKeyStoreSpi.this.makePBECipher("PBEWithSHAAnd3-KeyTripleDES-CBC", 2, (char[])object, object2 /* !! */ , n);
            object2 /* !! */  = new CipherInputStream((InputStream)object3, (Cipher)object4);
            try {
                object3 = BcKeyStoreSpi.this;
                object4 = new DataInputStream((InputStream)object2 /* !! */ );
                return BcKeyStoreSpi.access$100((BcKeyStoreSpi)object3, (DataInputStream)object4);
            }
            catch (Exception exception) {
                object5 = new ByteArrayInputStream((byte[])this.obj);
                object4 = new DataInputStream((InputStream)object5);
                object3 = new byte[object4.readInt()];
                object4.readFully((byte[])object3);
                n = object4.readInt();
                object6 = BcKeyStoreSpi.this.makePBECipher("BrokenPBEWithSHAAnd3-KeyTripleDES-CBC", 2, (char[])object, (byte[])object3, n);
                object7 = new CipherInputStream((InputStream)object4, (Cipher)object6);
                try {
                    object6 = BcKeyStoreSpi.this;
                    dataInputStream = new DataInputStream((InputStream)object7);
                    object7 = BcKeyStoreSpi.access$100((BcKeyStoreSpi)object6, dataInputStream);
                    object5 = object4;
                    ** GOTO lbl43
                }
                catch (Exception exception2) {
                    object4 = new ByteArrayInputStream((byte[])this.obj);
                    object5 = new DataInputStream((InputStream)object4);
                    object3 = new byte[object5.readInt()];
                    object5.readFully((byte[])object3);
                    n = object5.readInt();
                    object6 = BcKeyStoreSpi.this.makePBECipher("OldPBEWithSHAAnd3-KeyTripleDES-CBC", 2, (char[])object, (byte[])object3, n);
                    object7 = new CipherInputStream((InputStream)object5, (Cipher)object6);
                    object6 = BcKeyStoreSpi.this;
                    dataInputStream = new DataInputStream((InputStream)object7);
                    object7 = BcKeyStoreSpi.access$100((BcKeyStoreSpi)object6, dataInputStream);
                    object5 = object4;
lbl43: // 2 sources:
                    if (object7 == null) ** GOTO lbl57
                    try {
                        object4 = new ByteArrayOutputStream();
                        object5 = new DataOutputStream((OutputStream)object4);
                        object5.writeInt(((byte[])object3).length);
                        object5.write((byte[])object3);
                        object5.writeInt(n);
                        object3 = BcKeyStoreSpi.this.makePBECipher("PBEWithSHAAnd3-KeyTripleDES-CBC", 1, (char[])object, (byte[])object3, n);
                        object = new CipherOutputStream((OutputStream)object5, (Cipher)object3);
                        object3 = new DataOutputStream((OutputStream)object);
                        BcKeyStoreSpi.access$000(BcKeyStoreSpi.this, (Key)object7, (DataOutputStream)object3);
                        object3.close();
                        this.obj = object4.toByteArray();
                        return object7;
lbl57: // 1 sources:
                        object = new UnrecoverableKeyException("no match");
                        throw object;
                    }
                    catch (Exception exception3) {
                        throw new UnrecoverableKeyException("no match");
                    }
                    catch (Exception exception4) {
                        // empty catch block
                    }
                }
            }
            throw new UnrecoverableKeyException("no match");
        }

        int getType() {
            return this.type;
        }
    }

    public static class Version1
    extends BcKeyStoreSpi {
        public Version1() {
            super(1);
        }
    }

}

