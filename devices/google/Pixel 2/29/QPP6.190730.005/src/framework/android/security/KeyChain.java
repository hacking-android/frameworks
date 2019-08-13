/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.org.conscrypt.TrustedCertificateStore
 */
package android.security;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.security.IKeyChainAliasCallback;
import android.security.IKeyChainService;
import android.security.KeyChainAliasCallback;
import android.security.KeyChainException;
import android.security.KeyStore;
import android.security.keystore.AndroidKeyStoreProvider;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import com.android.org.conscrypt.TrustedCertificateStore;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.io.Serializable;
import java.security.KeyPair;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.security.auth.x500.X500Principal;

public final class KeyChain {
    public static final String ACCOUNT_TYPE = "com.android.keychain";
    private static final String ACTION_CHOOSER = "com.android.keychain.CHOOSER";
    private static final String ACTION_INSTALL = "android.credentials.INSTALL";
    public static final String ACTION_KEYCHAIN_CHANGED = "android.security.action.KEYCHAIN_CHANGED";
    public static final String ACTION_KEY_ACCESS_CHANGED = "android.security.action.KEY_ACCESS_CHANGED";
    public static final String ACTION_STORAGE_CHANGED = "android.security.STORAGE_CHANGED";
    public static final String ACTION_TRUST_STORE_CHANGED = "android.security.action.TRUST_STORE_CHANGED";
    private static final String CERT_INSTALLER_PACKAGE = "com.android.certinstaller";
    public static final String EXTRA_ALIAS = "alias";
    public static final String EXTRA_CERTIFICATE = "CERT";
    public static final String EXTRA_ISSUERS = "issuers";
    public static final String EXTRA_KEY_ACCESSIBLE = "android.security.extra.KEY_ACCESSIBLE";
    public static final String EXTRA_KEY_ALIAS = "android.security.extra.KEY_ALIAS";
    public static final String EXTRA_KEY_TYPES = "key_types";
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_PKCS12 = "PKCS12";
    public static final String EXTRA_RESPONSE = "response";
    public static final String EXTRA_SENDER = "sender";
    public static final String EXTRA_URI = "uri";
    private static final String KEYCHAIN_PACKAGE = "com.android.keychain";
    public static final int KEY_ATTESTATION_CANNOT_ATTEST_IDS = 3;
    public static final int KEY_ATTESTATION_CANNOT_COLLECT_DATA = 2;
    public static final int KEY_ATTESTATION_FAILURE = 4;
    public static final int KEY_ATTESTATION_MISSING_CHALLENGE = 1;
    public static final int KEY_ATTESTATION_SUCCESS = 0;
    public static final int KEY_GEN_FAILURE = 7;
    public static final int KEY_GEN_INVALID_ALGORITHM_PARAMETERS = 4;
    public static final int KEY_GEN_MISSING_ALIAS = 1;
    public static final int KEY_GEN_NO_KEYSTORE_PROVIDER = 5;
    public static final int KEY_GEN_NO_SUCH_ALGORITHM = 3;
    public static final int KEY_GEN_STRONGBOX_UNAVAILABLE = 6;
    public static final int KEY_GEN_SUCCESS = 0;
    public static final int KEY_GEN_SUPERFLUOUS_ATTESTATION_CHALLENGE = 2;

    private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    public static KeyChainConnection bind(Context context) throws InterruptedException {
        return KeyChain.bindAsUser(context, Process.myUserHandle());
    }

    public static KeyChainConnection bindAsUser(Context context, UserHandle userHandle) throws InterruptedException {
        if (context != null) {
            KeyChain.ensureNotOnMainThread(context);
            LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(1);
            ServiceConnection serviceConnection = new ServiceConnection(){
                volatile boolean mConnectedAtLeastOnce = false;

                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    if (!this.mConnectedAtLeastOnce) {
                        this.mConnectedAtLeastOnce = true;
                        try {
                            BlockingQueue.this.put(IKeyChainService.Stub.asInterface(Binder.allowBlocking(iBinder)));
                        }
                        catch (InterruptedException interruptedException) {
                            // empty catch block
                        }
                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                }
            };
            Intent intent = new Intent(IKeyChainService.class.getName());
            ComponentName componentName = intent.resolveSystemService(context.getPackageManager(), 0);
            intent.setComponent(componentName);
            if (componentName != null && context.bindServiceAsUser(intent, serviceConnection, 1, userHandle)) {
                return new KeyChainConnection(context, serviceConnection, (IKeyChainService)linkedBlockingQueue.take());
            }
            throw new AssertionError((Object)"could not bind to KeyChainService");
        }
        throw new NullPointerException("context == null");
    }

    /*
     * WARNING - void declaration
     */
    public static void choosePrivateKeyAlias(Activity activity, KeyChainAliasCallback object, String[] object22, Principal[] arrprincipal, Uri uri, String string2) {
        if (activity != null) {
            if (object != null) {
                void var5_7;
                void var3_5;
                void var4_6;
                Intent intent = new Intent(ACTION_CHOOSER);
                intent.setPackage("com.android.keychain");
                intent.putExtra(EXTRA_RESPONSE, new AliasResponse((KeyChainAliasCallback)((Object)object)));
                intent.putExtra(EXTRA_URI, (Parcelable)var4_6);
                intent.putExtra(EXTRA_ALIAS, (String)var5_7);
                intent.putExtra(EXTRA_KEY_TYPES, (String[])object22);
                object = new ArrayList<byte[]>();
                if (var3_5 != null) {
                    for (void var2_4 : var3_5) {
                        if (var2_4 instanceof X500Principal) {
                            object.add(((X500Principal)var2_4).getEncoded());
                            continue;
                        }
                        throw new IllegalArgumentException(String.format("Issuer %s is of type %s, not X500Principal", var2_4.toString(), var2_4.getClass()));
                    }
                }
                intent.putExtra(EXTRA_ISSUERS, object);
                intent.putExtra(EXTRA_SENDER, PendingIntent.getActivity(activity, 0, new Intent(), 0));
                activity.startActivity(intent);
                return;
            }
            throw new NullPointerException("response == null");
        }
        throw new NullPointerException("activity == null");
    }

    public static void choosePrivateKeyAlias(Activity activity, KeyChainAliasCallback keyChainAliasCallback, String[] arrstring, Principal[] arrprincipal, String charSequence, int n, String string2) {
        Object object = null;
        if (charSequence != null) {
            Uri.Builder builder = new Uri.Builder();
            object = new StringBuilder();
            ((StringBuilder)object).append((String)charSequence);
            if (n != -1) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(":");
                ((StringBuilder)charSequence).append(n);
                charSequence = ((StringBuilder)charSequence).toString();
            } else {
                charSequence = "";
            }
            ((StringBuilder)object).append((String)charSequence);
            object = builder.authority(((StringBuilder)object).toString()).build();
        }
        KeyChain.choosePrivateKeyAlias(activity, keyChainAliasCallback, arrstring, arrprincipal, object, string2);
    }

    public static Intent createInstallIntent() {
        Intent intent = new Intent(ACTION_INSTALL);
        intent.setClassName(CERT_INSTALLER_PACKAGE, "com.android.certinstaller.CertInstallerMain");
        return intent;
    }

    private static void ensureNotOnMainThread(Context context) {
        Looper looper = Looper.myLooper();
        if (looper != null && looper == context.getMainLooper()) {
            throw new IllegalStateException("calling this from your main thread can lead to deadlock");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static X509Certificate[] getCertificateChain(Context object, String object2) throws KeyChainException, InterruptedException {
        Object object3;
        IKeyChainService iKeyChainService;
        block15 : {
            if (object2 == null) {
                throw new NullPointerException("alias == null");
            }
            object = KeyChain.bind(((Context)object).getApplicationContext());
            try {
                iKeyChainService = ((KeyChainConnection)object).getService();
                object3 = iKeyChainService.getCertificate((String)object2);
                if (object3 != null) break block15;
            }
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    if (object == null) throw throwable2;
                    try {
                        KeyChain.$closeResource(throwable, (AutoCloseable)object);
                        throw throwable2;
                    }
                    catch (RuntimeException runtimeException) {
                        throw new KeyChainException(runtimeException);
                    }
                    catch (RemoteException remoteException) {
                        throw new KeyChainException(remoteException);
                    }
                }
            }
            KeyChain.$closeResource(null, (AutoCloseable)object);
            return null;
        }
        object2 = iKeyChainService.getCaCertificates((String)object2);
        KeyChain.$closeResource(null, (AutoCloseable)object);
        try {
            object = KeyChain.toCertificate((byte[])object3);
            if (object2 != null && ((Object)object2).length != 0) {
                object2 = KeyChain.toCertificates((byte[])object2);
                object3 = new ArrayList(object2.size() + 1);
                ((ArrayList)object3).add(object);
                ((ArrayList)object3).addAll(object2);
                return ((ArrayList)object3).toArray(new X509Certificate[((ArrayList)object3).size()]);
            }
            object2 = new TrustedCertificateStore();
            object = object2.getCertificateChain((X509Certificate)object);
            return object.toArray(new X509Certificate[object.size()]);
        }
        catch (RuntimeException | CertificateException exception) {
            throw new KeyChainException(exception);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static KeyPair getKeyPair(Context object, String string2) throws KeyChainException, InterruptedException {
        block11 : {
            if (string2 == null) throw new NullPointerException("alias == null");
            if (object == null) throw new NullPointerException("context == null");
            object = KeyChain.bind(((Context)object).getApplicationContext());
            try {
                string2 = ((KeyChainConnection)object).getService().requestPrivateKey(string2);
            }
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    if (object == null) throw throwable2;
                    try {
                        KeyChain.$closeResource(throwable, (AutoCloseable)object);
                        throw throwable2;
                    }
                    catch (RuntimeException runtimeException) {
                        throw new KeyChainException(runtimeException);
                    }
                    catch (RemoteException remoteException) {
                        throw new KeyChainException(remoteException);
                    }
                }
            }
            KeyChain.$closeResource(null, (AutoCloseable)object);
            if (string2 != null) break block11;
            return null;
        }
        try {
            return AndroidKeyStoreProvider.loadAndroidKeyStoreKeyPairFromKeystore(KeyStore.getInstance(), string2, -1);
        }
        catch (KeyPermanentlyInvalidatedException | RuntimeException | UnrecoverableKeyException exception) {
            throw new KeyChainException(exception);
        }
    }

    public static PrivateKey getPrivateKey(Context object, String string2) throws KeyChainException, InterruptedException {
        if ((object = KeyChain.getKeyPair((Context)object, string2)) != null) {
            return ((KeyPair)object).getPrivate();
        }
        return null;
    }

    @Deprecated
    public static boolean isBoundKeyAlgorithm(String string2) {
        if (!KeyChain.isKeyAlgorithmSupported(string2)) {
            return false;
        }
        return KeyStore.getInstance().isHardwareBacked(string2);
    }

    public static boolean isKeyAlgorithmSupported(String string2) {
        boolean bl = "EC".equals(string2 = string2.toUpperCase(Locale.US)) || "RSA".equals(string2);
        return bl;
    }

    public static X509Certificate toCertificate(byte[] object) {
        if (object != null) {
            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])object);
                object = (X509Certificate)certificateFactory.generateCertificate(byteArrayInputStream);
                return object;
            }
            catch (CertificateException certificateException) {
                throw new AssertionError(certificateException);
            }
        }
        throw new IllegalArgumentException("bytes == null");
    }

    public static Collection<X509Certificate> toCertificates(byte[] object) {
        if (object != null) {
            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])object);
                object = certificateFactory.generateCertificates(byteArrayInputStream);
                return object;
            }
            catch (CertificateException certificateException) {
                throw new AssertionError(certificateException);
            }
        }
        throw new IllegalArgumentException("bytes == null");
    }

    private static class AliasResponse
    extends IKeyChainAliasCallback.Stub {
        private final KeyChainAliasCallback keyChainAliasResponse;

        private AliasResponse(KeyChainAliasCallback keyChainAliasCallback) {
            this.keyChainAliasResponse = keyChainAliasCallback;
        }

        @Override
        public void alias(String string2) {
            this.keyChainAliasResponse.alias(string2);
        }
    }

    public static class KeyChainConnection
    implements Closeable {
        private final Context context;
        private final IKeyChainService service;
        private final ServiceConnection serviceConnection;

        protected KeyChainConnection(Context context, ServiceConnection serviceConnection, IKeyChainService iKeyChainService) {
            this.context = context;
            this.serviceConnection = serviceConnection;
            this.service = iKeyChainService;
        }

        @Override
        public void close() {
            this.context.unbindService(this.serviceConnection);
        }

        public IKeyChainService getService() {
            return this.service;
        }
    }

}

