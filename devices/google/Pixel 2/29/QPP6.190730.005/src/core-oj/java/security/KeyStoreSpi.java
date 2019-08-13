/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.Enumeration;
import javax.crypto.SecretKey;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public abstract class KeyStoreSpi {
    public abstract Enumeration<String> engineAliases();

    public abstract boolean engineContainsAlias(String var1);

    public abstract void engineDeleteEntry(String var1) throws KeyStoreException;

    public boolean engineEntryInstanceOf(String string, Class<? extends KeyStore.Entry> class_) {
        if (class_ == KeyStore.TrustedCertificateEntry.class) {
            return this.engineIsCertificateEntry(string);
        }
        boolean bl = true;
        boolean bl2 = true;
        if (class_ == KeyStore.PrivateKeyEntry.class) {
            if (!this.engineIsKeyEntry(string) || this.engineGetCertificate(string) == null) {
                bl2 = false;
            }
            return bl2;
        }
        if (class_ == KeyStore.SecretKeyEntry.class) {
            bl2 = this.engineIsKeyEntry(string) && this.engineGetCertificate(string) == null ? bl : false;
            return bl2;
        }
        return false;
    }

    public abstract Certificate engineGetCertificate(String var1);

    public abstract String engineGetCertificateAlias(Certificate var1);

    public abstract Certificate[] engineGetCertificateChain(String var1);

    public abstract Date engineGetCreationDate(String var1);

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public KeyStore.Entry engineGetEntry(String arrcertificate, KeyStore.ProtectionParameter object) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableEntryException {
        if (!this.engineContainsAlias((String)arrcertificate)) {
            return null;
        }
        if (object == null && this.engineIsCertificateEntry((String)arrcertificate)) {
            return new KeyStore.TrustedCertificateEntry(this.engineGetCertificate((String)arrcertificate));
        }
        if (object != null && !(object instanceof KeyStore.PasswordProtection)) throw new UnsupportedOperationException();
        if (this.engineIsCertificateEntry((String)arrcertificate)) throw new UnsupportedOperationException("trusted certificate entries are not password-protected");
        if (!this.engineIsKeyEntry((String)arrcertificate)) throw new UnsupportedOperationException();
        char[] arrc = null;
        if (object != null) {
            arrc = ((KeyStore.PasswordProtection)object).getPassword();
        }
        if ((object = this.engineGetKey((String)arrcertificate, arrc)) instanceof PrivateKey) {
            arrcertificate = this.engineGetCertificateChain((String)arrcertificate);
            return new KeyStore.PrivateKeyEntry((PrivateKey)object, arrcertificate);
        }
        if (!(object instanceof SecretKey)) throw new UnsupportedOperationException();
        return new KeyStore.SecretKeyEntry((SecretKey)object);
    }

    public abstract Key engineGetKey(String var1, char[] var2) throws NoSuchAlgorithmException, UnrecoverableKeyException;

    public abstract boolean engineIsCertificateEntry(String var1);

    public abstract boolean engineIsKeyEntry(String var1);

    public abstract void engineLoad(InputStream var1, char[] var2) throws IOException, NoSuchAlgorithmException, CertificateException;

    public void engineLoad(KeyStore.LoadStoreParameter object) throws IOException, NoSuchAlgorithmException, CertificateException {
        block4 : {
            block7 : {
                block3 : {
                    block6 : {
                        block5 : {
                            if (object == null) {
                                this.engineLoad(null, null);
                                return;
                            }
                            if (!(object instanceof KeyStore.SimpleLoadStoreParameter)) break block4;
                            if (!((object = object.getProtectionParameter()) instanceof KeyStore.PasswordProtection)) break block5;
                            object = ((KeyStore.PasswordProtection)object).getPassword();
                            break block6;
                        }
                        if (!(object instanceof KeyStore.CallbackHandlerProtection)) break block7;
                        object = ((KeyStore.CallbackHandlerProtection)object).getCallbackHandler();
                        PasswordCallback passwordCallback = new PasswordCallback("Password: ", false);
                        try {
                            object.handle(new Callback[]{passwordCallback});
                            object = passwordCallback.getPassword();
                            passwordCallback.clearPassword();
                            if (object == null) break block3;
                        }
                        catch (UnsupportedCallbackException unsupportedCallbackException) {
                            throw new NoSuchAlgorithmException("Could not obtain password", unsupportedCallbackException);
                        }
                    }
                    this.engineLoad(null, (char[])object);
                    return;
                }
                throw new NoSuchAlgorithmException("No password provided");
            }
            throw new NoSuchAlgorithmException("ProtectionParameter must be PasswordProtection or CallbackHandlerProtection");
        }
        throw new UnsupportedOperationException();
    }

    public abstract void engineSetCertificateEntry(String var1, Certificate var2) throws KeyStoreException;

    public void engineSetEntry(String charSequence, KeyStore.Entry entry, KeyStore.ProtectionParameter object) throws KeyStoreException {
        if (object != null && !(object instanceof KeyStore.PasswordProtection)) {
            throw new KeyStoreException("unsupported protection parameter");
        }
        KeyStore.PasswordProtection passwordProtection = null;
        if (object != null) {
            passwordProtection = (KeyStore.PasswordProtection)object;
        }
        object = passwordProtection == null ? null : passwordProtection.getPassword();
        if (entry instanceof KeyStore.TrustedCertificateEntry) {
            this.engineSetCertificateEntry((String)charSequence, ((KeyStore.TrustedCertificateEntry)entry).getTrustedCertificate());
            return;
        }
        if (entry instanceof KeyStore.PrivateKeyEntry) {
            this.engineSetKeyEntry((String)charSequence, ((KeyStore.PrivateKeyEntry)entry).getPrivateKey(), (char[])object, ((KeyStore.PrivateKeyEntry)entry).getCertificateChain());
            return;
        }
        if (entry instanceof KeyStore.SecretKeyEntry) {
            this.engineSetKeyEntry((String)charSequence, ((KeyStore.SecretKeyEntry)entry).getSecretKey(), (char[])object, null);
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("unsupported entry type: ");
        ((StringBuilder)charSequence).append(entry.getClass().getName());
        throw new KeyStoreException(((StringBuilder)charSequence).toString());
    }

    public abstract void engineSetKeyEntry(String var1, Key var2, char[] var3, Certificate[] var4) throws KeyStoreException;

    public abstract void engineSetKeyEntry(String var1, byte[] var2, Certificate[] var3) throws KeyStoreException;

    public abstract int engineSize();

    public abstract void engineStore(OutputStream var1, char[] var2) throws IOException, NoSuchAlgorithmException, CertificateException;

    public void engineStore(KeyStore.LoadStoreParameter loadStoreParameter) throws IOException, NoSuchAlgorithmException, CertificateException {
        throw new UnsupportedOperationException();
    }
}

