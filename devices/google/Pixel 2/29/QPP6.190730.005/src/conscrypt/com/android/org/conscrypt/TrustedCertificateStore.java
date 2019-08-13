/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.ConscryptCertStore;
import com.android.org.conscrypt.Hex;
import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.OpenSSLX509Certificate;
import com.android.org.conscrypt.io.IoUtils;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;

public class TrustedCertificateStore
implements ConscryptCertStore {
    private static final CertificateFactory CERT_FACTORY;
    private static final String PREFIX_SYSTEM = "system:";
    private static final String PREFIX_USER = "user:";
    private final File addedDir;
    private final File deletedDir;
    private final File systemDir;

    static {
        try {
            CERT_FACTORY = CertificateFactory.getInstance("X509");
            return;
        }
        catch (CertificateException certificateException) {
            throw new AssertionError(certificateException);
        }
    }

    @UnsupportedAppUsage
    public TrustedCertificateStore() {
        this(PreloadHolder.defaultCaCertsSystemDir, PreloadHolder.defaultCaCertsAddedDir, PreloadHolder.defaultCaCertsDeletedDir);
    }

    public TrustedCertificateStore(File file, File file2, File file3) {
        this.systemDir = file;
        this.addedDir = file2;
        this.deletedDir = file3;
    }

    private void addAliases(Set<String> set, String string, File arrstring) {
        if ((arrstring = arrstring.list()) == null) {
            return;
        }
        for (String string2 : arrstring) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(string2);
            string2 = stringBuilder.toString();
            if (!this.containsAlias(string2)) continue;
            set.add(string2);
        }
    }

    private boolean containsAlias(String string, boolean bl) {
        bl = this.getCertificate(string, bl) != null;
        return bl;
    }

    private static OpenSSLX509Certificate convertToOpenSSLIfNeeded(X509Certificate x509Certificate) throws CertificateException {
        if (x509Certificate == null) {
            return null;
        }
        if (x509Certificate instanceof OpenSSLX509Certificate) {
            return (OpenSSLX509Certificate)x509Certificate;
        }
        try {
            x509Certificate = OpenSSLX509Certificate.fromX509Der(x509Certificate.getEncoded());
            return x509Certificate;
        }
        catch (Exception exception) {
            throw new CertificateException(exception);
        }
    }

    private File file(File file, String string, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append('.');
        stringBuilder.append(n);
        return new File(file, stringBuilder.toString());
    }

    private File fileForAlias(String object) {
        block4 : {
            block7 : {
                block6 : {
                    block5 : {
                        if (object == null) break block4;
                        if (!TrustedCertificateStore.isSystem((String)object)) break block5;
                        object = new File(this.systemDir, ((String)object).substring(PREFIX_SYSTEM.length()));
                        break block6;
                    }
                    if (!TrustedCertificateStore.isUser((String)object)) break block7;
                    object = new File(this.addedDir, ((String)object).substring(PREFIX_USER.length()));
                }
                if (((File)object).exists() && !this.isTombstone((File)object)) {
                    return object;
                }
                return null;
            }
            return null;
        }
        throw new NullPointerException("alias == null");
    }

    private <T> T findCert(File file, X500Principal x500Principal, CertSelector certSelector, Class<T> class_) {
        Serializable serializable = null;
        String string = this.hash(x500Principal);
        int n = 0;
        x500Principal = serializable;
        do {
            File file2;
            if (!(file2 = this.file(file, string, n)).isFile()) {
                if (class_ == Boolean.class) {
                    return (T)Boolean.FALSE;
                }
                if (class_ == File.class) {
                    return (T)file2;
                }
                if (class_ == Set.class) {
                    return (T)x500Principal;
                }
                return null;
            }
            if (this.isTombstone(file2)) {
                serializable = x500Principal;
            } else {
                X509Certificate x509Certificate = this.readCertificate(file2);
                if (x509Certificate == null) {
                    serializable = x500Principal;
                } else {
                    serializable = x500Principal;
                    if (certSelector.match(x509Certificate)) {
                        if (class_ == X509Certificate.class) {
                            return (T)x509Certificate;
                        }
                        if (class_ == Boolean.class) {
                            return (T)Boolean.TRUE;
                        }
                        if (class_ == File.class) {
                            return (T)file2;
                        }
                        if (class_ == Set.class) {
                            serializable = x500Principal;
                            if (x500Principal == null) {
                                serializable = new HashSet();
                            }
                            serializable.add(x509Certificate);
                        } else {
                            throw new AssertionError();
                        }
                    }
                }
            }
            ++n;
            x500Principal = serializable;
        } while (true);
    }

    private String hash(X500Principal x500Principal) {
        return Hex.intToHexString(NativeCrypto.X509_NAME_hash_old(x500Principal), 8);
    }

    private boolean isDeletedSystemCertificate(X509Certificate x509Certificate) {
        return this.getCertificateFile(this.deletedDir, x509Certificate).exists();
    }

    private static boolean isSelfIssuedCertificate(OpenSSLX509Certificate openSSLX509Certificate) {
        long l = openSSLX509Certificate.getContext();
        boolean bl = NativeCrypto.X509_check_issued(l, openSSLX509Certificate, l, openSSLX509Certificate) == 0;
        return bl;
    }

    public static final boolean isSystem(String string) {
        return string.startsWith(PREFIX_SYSTEM);
    }

    private boolean isTombstone(File file) {
        boolean bl = file.length() == 0L;
        return bl;
    }

    public static final boolean isUser(String string) {
        return string.startsWith(PREFIX_USER);
    }

    private X509Certificate readCertificate(File object) {
        Object object2;
        if (!((File)object).isFile()) {
            return null;
        }
        Object object3 = null;
        Object object4 = null;
        Object object5 = object2 = null;
        Object object6 = object3;
        Object object7 = object4;
        object5 = object2;
        object6 = object3;
        object7 = object4;
        object5 = object2;
        object6 = object3;
        object7 = object4;
        FileInputStream fileInputStream = new FileInputStream((File)object);
        object5 = object2;
        object6 = object3;
        object7 = object4;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        object5 = object = bufferedInputStream;
        object6 = object;
        object7 = object;
        try {
            object2 = (X509Certificate)CERT_FACTORY.generateCertificate((InputStream)object);
        }
        catch (Throwable throwable) {
            IoUtils.closeQuietly(object5);
            throw throwable;
        }
        catch (CertificateException certificateException) {
            IoUtils.closeQuietly(object6);
            return null;
        }
        catch (IOException iOException) {
            IoUtils.closeQuietly(object7);
            return null;
        }
        IoUtils.closeQuietly((Closeable)object);
        return object2;
    }

    private void removeUnnecessaryTombstones(String object) throws IOException {
        if (TrustedCertificateStore.isUser((String)object)) {
            int n = ((String)object).lastIndexOf(46);
            if (n != -1) {
                int n2;
                CharSequence charSequence = ((String)object).substring(PREFIX_USER.length(), n);
                n = n2 = Integer.parseInt(((String)object).substring(n + 1));
                if (this.file(this.addedDir, (String)charSequence, n2 + 1).exists()) {
                    return;
                }
                while (n >= 0 && this.isTombstone((File)(object = this.file(this.addedDir, (String)charSequence, n)))) {
                    if (((File)object).delete()) {
                        --n;
                        continue;
                    }
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Could not remove ");
                    ((StringBuilder)charSequence).append(object);
                    throw new IOException(((StringBuilder)charSequence).toString());
                }
                return;
            }
            throw new AssertionError(object);
        }
        throw new AssertionError(object);
    }

    public static void setDefaultUserDirectory(File file) {
        PreloadHolder.defaultCaCertsAddedDir = new File(file, "cacerts-added");
        PreloadHolder.defaultCaCertsDeletedDir = new File(file, "cacerts-removed");
    }

    private void writeCertificate(File file, X509Certificate x509Certificate) throws IOException, CertificateException {
        FileOutputStream fileOutputStream;
        Object object = file.getParentFile();
        ((File)object).mkdirs();
        ((File)object).setReadable(true, false);
        ((File)object).setExecutable(true, false);
        Object var4_5 = null;
        object = var4_5;
        object = var4_5;
        try {
            fileOutputStream = new FileOutputStream(file);
            object = fileOutputStream;
        }
        catch (Throwable throwable) {
            IoUtils.closeQuietly((Closeable)object);
            throw throwable;
        }
        ((OutputStream)fileOutputStream).write(x509Certificate.getEncoded());
        IoUtils.closeQuietly(fileOutputStream);
        file.setReadable(true, false);
    }

    public Set<String> aliases() {
        HashSet<String> hashSet = new HashSet<String>();
        this.addAliases(hashSet, PREFIX_USER, this.addedDir);
        this.addAliases(hashSet, PREFIX_SYSTEM, this.systemDir);
        return hashSet;
    }

    public Set<String> allSystemAliases() {
        HashSet<String> hashSet = new HashSet<String>();
        String[] arrstring = this.systemDir.list();
        if (arrstring == null) {
            return hashSet;
        }
        for (String string : arrstring) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX_SYSTEM);
            stringBuilder.append(string);
            string = stringBuilder.toString();
            if (!this.containsAlias(string, true)) continue;
            hashSet.add(string);
        }
        return hashSet;
    }

    public boolean containsAlias(String string) {
        return this.containsAlias(string, false);
    }

    public void deleteCertificateEntry(String object) throws IOException, CertificateException {
        if (object == null) {
            return;
        }
        File file = this.fileForAlias((String)object);
        if (file == null) {
            return;
        }
        if (TrustedCertificateStore.isSystem((String)object)) {
            object = this.readCertificate(file);
            if (object == null) {
                return;
            }
            file = this.getCertificateFile(this.deletedDir, (X509Certificate)object);
            if (file.exists()) {
                return;
            }
            this.writeCertificate(file, (X509Certificate)object);
            return;
        }
        if (TrustedCertificateStore.isUser((String)object)) {
            new FileOutputStream(file).close();
            this.removeUnnecessaryTombstones((String)object);
            return;
        }
    }

    @Override
    public Set<X509Certificate> findAllIssuers(X509Certificate object) {
        Object object2 = null;
        Object object3 = new CertSelector((X509Certificate)object){
            final /* synthetic */ X509Certificate val$c;
            {
                this.val$c = x509Certificate;
            }

            @Override
            public boolean match(X509Certificate x509Certificate) {
                try {
                    this.val$c.verify(x509Certificate.getPublicKey());
                    return true;
                }
                catch (Exception exception) {
                    return false;
                }
            }
        };
        X500Principal x500Principal = ((X509Certificate)object).getIssuerX500Principal();
        object3 = this.findCert(this.addedDir, x500Principal, (CertSelector)object3, Set.class);
        if (object3 != null) {
            object2 = object3;
        }
        object = new CertSelector((X509Certificate)object){
            final /* synthetic */ X509Certificate val$c;
            {
                this.val$c = x509Certificate;
            }

            @Override
            public boolean match(X509Certificate x509Certificate) {
                block3 : {
                    try {
                        if (!TrustedCertificateStore.this.isDeletedSystemCertificate(x509Certificate)) break block3;
                        return false;
                    }
                    catch (Exception exception) {
                        return false;
                    }
                }
                this.val$c.verify(x509Certificate.getPublicKey());
                return true;
            }
        };
        object3 = this.findCert(this.systemDir, x500Principal, (CertSelector)object, Set.class);
        object = object2;
        if (object3 != null) {
            if (object2 != null) {
                object2.addAll(object3);
                object = object2;
            } else {
                object = object3;
            }
        }
        if (object == null) {
            object = Collections.emptySet();
        }
        return object;
    }

    public X509Certificate findIssuer(X509Certificate serializable) {
        CertSelector certSelector = new CertSelector((X509Certificate)serializable){
            final /* synthetic */ X509Certificate val$c;
            {
                this.val$c = x509Certificate;
            }

            @Override
            public boolean match(X509Certificate x509Certificate) {
                try {
                    this.val$c.verify(x509Certificate.getPublicKey());
                    return true;
                }
                catch (Exception exception) {
                    return false;
                }
            }
        };
        X509Certificate x509Certificate = this.findCert(this.addedDir, (X500Principal)(serializable = serializable.getIssuerX500Principal()), certSelector, X509Certificate.class);
        if (x509Certificate != null) {
            return x509Certificate;
        }
        if ((serializable = this.findCert(this.systemDir, (X500Principal)serializable, certSelector, X509Certificate.class)) != null && !this.isDeletedSystemCertificate((X509Certificate)serializable)) {
            return serializable;
        }
        return null;
    }

    public Certificate getCertificate(String string) {
        return this.getCertificate(string, false);
    }

    public Certificate getCertificate(String string, boolean bl) {
        Serializable serializable = this.fileForAlias(string);
        if (!(serializable == null || TrustedCertificateStore.isUser(string) && this.isTombstone((File)serializable))) {
            if (!((serializable = this.readCertificate((File)serializable)) == null || TrustedCertificateStore.isSystem(string) && !bl && this.isDeletedSystemCertificate((X509Certificate)serializable))) {
                return serializable;
            }
            return null;
        }
        return null;
    }

    public String getCertificateAlias(Certificate certificate) {
        return this.getCertificateAlias(certificate, false);
    }

    public String getCertificateAlias(Certificate serializable, boolean bl) {
        if (serializable != null && serializable instanceof X509Certificate) {
            Serializable serializable2 = (X509Certificate)serializable;
            serializable = this.getCertificateFile(this.addedDir, (X509Certificate)serializable2);
            if (((File)serializable).exists()) {
                serializable2 = new StringBuilder();
                ((StringBuilder)serializable2).append(PREFIX_USER);
                ((StringBuilder)serializable2).append(((File)serializable).getName());
                return ((StringBuilder)serializable2).toString();
            }
            if (!bl && this.isDeletedSystemCertificate((X509Certificate)serializable2)) {
                return null;
            }
            if (((File)(serializable2 = this.getCertificateFile(this.systemDir, (X509Certificate)serializable2))).exists()) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append(PREFIX_SYSTEM);
                ((StringBuilder)serializable).append(((File)serializable2).getName());
                return ((StringBuilder)serializable).toString();
            }
            return null;
        }
        return null;
    }

    @UnsupportedAppUsage
    public List<X509Certificate> getCertificateChain(X509Certificate x509Certificate) throws CertificateException {
        LinkedHashSet<X509Certificate> linkedHashSet = new LinkedHashSet<X509Certificate>();
        x509Certificate = TrustedCertificateStore.convertToOpenSSLIfNeeded(x509Certificate);
        linkedHashSet.add(x509Certificate);
        while (!TrustedCertificateStore.isSelfIssuedCertificate((OpenSSLX509Certificate)x509Certificate) && (x509Certificate = TrustedCertificateStore.convertToOpenSSLIfNeeded(this.findIssuer(x509Certificate))) != null && !linkedHashSet.contains(x509Certificate)) {
            linkedHashSet.add(x509Certificate);
        }
        return new ArrayList<X509Certificate>(linkedHashSet);
    }

    public File getCertificateFile(File file, final X509Certificate x509Certificate) {
        CertSelector certSelector = new CertSelector(){

            @Override
            public boolean match(X509Certificate x509Certificate2) {
                return x509Certificate2.equals(x509Certificate);
            }
        };
        return this.findCert(file, x509Certificate.getSubjectX500Principal(), certSelector, File.class);
    }

    public Date getCreationDate(String object) {
        if (!this.containsAlias((String)object)) {
            return null;
        }
        if ((object = this.fileForAlias((String)object)) == null) {
            return null;
        }
        long l = ((File)object).lastModified();
        if (l == 0L) {
            return null;
        }
        return new Date(l);
    }

    @Override
    public X509Certificate getTrustAnchor(final X509Certificate x509Certificate) {
        CertSelector certSelector = new CertSelector(){

            @Override
            public boolean match(X509Certificate x509Certificate2) {
                return x509Certificate2.getPublicKey().equals(x509Certificate.getPublicKey());
            }
        };
        X509Certificate x509Certificate2 = this.findCert(this.addedDir, x509Certificate.getSubjectX500Principal(), certSelector, X509Certificate.class);
        if (x509Certificate2 != null) {
            return x509Certificate2;
        }
        if ((x509Certificate = this.findCert(this.systemDir, x509Certificate.getSubjectX500Principal(), certSelector, X509Certificate.class)) != null && !this.isDeletedSystemCertificate(x509Certificate)) {
            return x509Certificate;
        }
        return null;
    }

    public void installCertificate(X509Certificate serializable) throws IOException, CertificateException {
        if (serializable != null) {
            if (this.getCertificateFile(this.systemDir, (X509Certificate)serializable).exists()) {
                File file = this.getCertificateFile(this.deletedDir, (X509Certificate)serializable);
                if (file.exists()) {
                    if (file.delete()) {
                        return;
                    }
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("Could not remove ");
                    ((StringBuilder)serializable).append(file);
                    throw new IOException(((StringBuilder)serializable).toString());
                }
                return;
            }
            File file = this.getCertificateFile(this.addedDir, (X509Certificate)serializable);
            if (file.exists()) {
                return;
            }
            this.writeCertificate(file, (X509Certificate)serializable);
            return;
        }
        throw new NullPointerException("cert == null");
    }

    public boolean isUserAddedCertificate(X509Certificate x509Certificate) {
        return this.getCertificateFile(this.addedDir, x509Certificate).exists();
    }

    public Set<String> userAliases() {
        HashSet<String> hashSet = new HashSet<String>();
        this.addAliases(hashSet, PREFIX_USER, this.addedDir);
        return hashSet;
    }

    private static interface CertSelector {
        public boolean match(X509Certificate var1);
    }

    private static class PreloadHolder {
        private static File defaultCaCertsAddedDir;
        private static File defaultCaCertsDeletedDir;
        private static File defaultCaCertsSystemDir;

        static {
            CharSequence charSequence = System.getenv("ANDROID_ROOT");
            String string = System.getenv("ANDROID_DATA");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append("/etc/security/cacerts");
            defaultCaCertsSystemDir = new File(stringBuilder.toString());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append("/misc/keychain");
            TrustedCertificateStore.setDefaultUserDirectory(new File(((StringBuilder)charSequence).toString()));
        }

        private PreloadHolder() {
        }
    }

}

