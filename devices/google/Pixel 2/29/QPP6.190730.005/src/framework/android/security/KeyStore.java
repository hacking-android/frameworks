/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.security.-$
 *  android.security.-$$Lambda
 *  android.security.-$$Lambda$wddj3-hVVrg0MkscpMtYt3BzY8Y
 *  com.android.org.bouncycastle.asn1.ASN1InputStream
 *  com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  com.android.org.bouncycastle.asn1.ASN1Primitive
 *  com.android.org.bouncycastle.asn1.pkcs.PrivateKeyInfo
 *  com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier
 */
package android.security;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.os.UserHandle;
import android.security.-$;
import android.security.GateKeeper;
import android.security.KeyStoreException;
import android.security._$$Lambda$wddj3_hVVrg0MkscpMtYt3BzY8Y;
import android.security.keymaster.ExportResult;
import android.security.keymaster.KeyCharacteristics;
import android.security.keymaster.KeymasterArguments;
import android.security.keymaster.KeymasterBlob;
import android.security.keymaster.KeymasterCertificateChain;
import android.security.keymaster.KeymasterDefs;
import android.security.keymaster.OperationResult;
import android.security.keystore.IKeystoreCertificateChainCallback;
import android.security.keystore.IKeystoreExportKeyCallback;
import android.security.keystore.IKeystoreKeyCharacteristicsCallback;
import android.security.keystore.IKeystoreOperationResultCallback;
import android.security.keystore.IKeystoreResponseCallback;
import android.security.keystore.IKeystoreService;
import android.security.keystore.KeyExpiredException;
import android.security.keystore.KeyNotYetValidException;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.security.keystore.KeystoreResponse;
import android.security.keystore.UserNotAuthenticatedException;
import android.util.Log;
import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AlgorithmId;

public class KeyStore {
    public static final int CANNOT_ATTEST_IDS = -66;
    public static final int CONFIRMATIONUI_ABORTED = 2;
    public static final int CONFIRMATIONUI_CANCELED = 1;
    public static final int CONFIRMATIONUI_IGNORED = 4;
    public static final int CONFIRMATIONUI_OK = 0;
    public static final int CONFIRMATIONUI_OPERATION_PENDING = 3;
    public static final int CONFIRMATIONUI_SYSTEM_ERROR = 5;
    public static final int CONFIRMATIONUI_UIERROR = 65536;
    public static final int CONFIRMATIONUI_UIERROR_MALFORMED_UTF8_ENCODING = 65539;
    public static final int CONFIRMATIONUI_UIERROR_MESSAGE_TOO_LONG = 65538;
    public static final int CONFIRMATIONUI_UIERROR_MISSING_GLYPH = 65537;
    public static final int CONFIRMATIONUI_UNEXPECTED = 7;
    public static final int CONFIRMATIONUI_UNIMPLEMENTED = 6;
    public static final int FLAG_CRITICAL_TO_DEVICE_ENCRYPTION = 8;
    public static final int FLAG_ENCRYPTED = 1;
    public static final int FLAG_NONE = 0;
    public static final int FLAG_SOFTWARE = 2;
    public static final int FLAG_STRONGBOX = 16;
    public static final int HARDWARE_TYPE_UNAVAILABLE = -68;
    public static final int KEY_ALREADY_EXISTS = 16;
    public static final int KEY_NOT_FOUND = 7;
    public static final int KEY_PERMANENTLY_INVALIDATED = 17;
    public static final int LOCKED = 2;
    @UnsupportedAppUsage
    public static final int NO_ERROR = 1;
    public static final int OP_AUTH_NEEDED = 15;
    public static final int PERMISSION_DENIED = 6;
    public static final int PROTOCOL_ERROR = 5;
    public static final int SYSTEM_ERROR = 4;
    private static final String TAG = "KeyStore";
    public static final int UID_SELF = -1;
    public static final int UNDEFINED_ACTION = 9;
    public static final int UNINITIALIZED = 3;
    public static final int VALUE_CORRUPTED = 8;
    public static final int WRONG_PASSWORD = 10;
    private final IKeystoreService mBinder;
    private final Context mContext;
    private int mError = 1;
    private IBinder mToken;

    private KeyStore(IKeystoreService iKeystoreService) {
        this.mBinder = iKeystoreService;
        this.mContext = KeyStore.getApplicationContext();
    }

    private int generateKeyInternal(String object, KeymasterArguments keymasterArguments, byte[] arrby, int n, int n2, KeyCharacteristics keyCharacteristics) throws RemoteException, ExecutionException, InterruptedException {
        KeyCharacteristicsPromise keyCharacteristicsPromise;
        block7 : {
            keyCharacteristicsPromise = new KeyCharacteristicsPromise();
            this.mBinder.asBinder().linkToDeath(keyCharacteristicsPromise, 0);
            n = this.mBinder.generateKey(keyCharacteristicsPromise, (String)object, keymasterArguments, arrby, n, n2);
            if (n != 1) {
                object = new StringBuilder();
                ((StringBuilder)object).append("generateKeyInternal failed on request ");
                ((StringBuilder)object).append(n);
                Log.e(TAG, ((StringBuilder)object).toString());
                return n;
            }
            object = keyCharacteristicsPromise.getFuture().get();
            n = ((KeyCharacteristicsCallbackResult)object).getKeystoreResponse().getErrorCode();
            if (n == 1) break block7;
            object = new StringBuilder();
            ((StringBuilder)object).append("generateKeyInternal failed on response ");
            ((StringBuilder)object).append(n);
            Log.e(TAG, ((StringBuilder)object).toString());
            return n;
        }
        if ((object = ((KeyCharacteristicsCallbackResult)object).getKeyCharacteristics()) == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("generateKeyInternal got empty key cheractariestics ");
            ((StringBuilder)object).append(n);
            Log.e(TAG, ((StringBuilder)object).toString());
            return 4;
        }
        keyCharacteristics.shallowCopyFrom((KeyCharacteristics)object);
        return 1;
        finally {
            this.mBinder.asBinder().unlinkToDeath(keyCharacteristicsPromise, 0);
        }
    }

    private String getAlgorithmFromPKCS8(byte[] object) {
        try {
            Object object2 = new ByteArrayInputStream((byte[])object);
            Object object3 = new ASN1InputStream((InputStream)object2);
            object2 = PrivateKeyInfo.getInstance((Object)object3.readObject()).getPrivateKeyAlgorithm().getAlgorithm().getId();
            object = new ObjectIdentifier((String)object2);
            object3 = new AlgorithmId((ObjectIdentifier)object);
            object = ((AlgorithmId)object3).getName();
            return object;
        }
        catch (IOException iOException) {
            Log.e(TAG, "getAlgorithmFromPKCS8 Failed to parse key data");
            Log.e(TAG, Log.getStackTraceString(iOException));
            return null;
        }
    }

    @UnsupportedAppUsage
    public static Context getApplicationContext() {
        Application application = ActivityThread.currentApplication();
        if (application != null) {
            return application;
        }
        throw new IllegalStateException("Failed to obtain application Context from ActivityThread");
    }

    private long getFaceOnlySid() {
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.biometrics.face")) {
            return 0L;
        }
        FaceManager faceManager = this.mContext.getSystemService(FaceManager.class);
        if (faceManager == null) {
            return 0L;
        }
        return faceManager.getAuthenticatorId();
    }

    private long getFingerprintOnlySid() {
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.fingerprint")) {
            return 0L;
        }
        FingerprintManager fingerprintManager = this.mContext.getSystemService(FingerprintManager.class);
        if (fingerprintManager == null) {
            return 0L;
        }
        return fingerprintManager.getAuthenticatorId();
    }

    @UnsupportedAppUsage
    public static KeyStore getInstance() {
        return new KeyStore(IKeystoreService.Stub.asInterface(ServiceManager.getService("android.security.keystore")));
    }

    @UnsupportedAppUsage
    public static KeyStoreException getKeyStoreException(int n) {
        if (n > 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 6) {
                                if (n != 7) {
                                    if (n != 8) {
                                        if (n != 15) {
                                            if (n != 17) {
                                                return new KeyStoreException(n, String.valueOf(n));
                                            }
                                            return new KeyStoreException(n, "Key permanently invalidated");
                                        }
                                        return new KeyStoreException(n, "Operation requires authorization");
                                    }
                                    return new KeyStoreException(n, "Key blob corrupted");
                                }
                                return new KeyStoreException(n, "Key not found");
                            }
                            return new KeyStoreException(n, "Permission denied");
                        }
                        return new KeyStoreException(n, "System error");
                    }
                    return new KeyStoreException(n, "Keystore not initialized");
                }
                return new KeyStoreException(n, "User authentication required");
            }
            return new KeyStoreException(n, "OK");
        }
        if (n != -16) {
            return new KeyStoreException(n, KeymasterDefs.getErrorMessage(n));
        }
        return new KeyStoreException(n, "Invalid user authentication validity duration");
    }

    private IBinder getToken() {
        synchronized (this) {
            IBinder iBinder;
            if (this.mToken == null) {
                iBinder = new Binder();
                this.mToken = iBinder;
            }
            iBinder = this.mToken;
            return iBinder;
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private int importKeyInternal(String object, KeymasterArguments keymasterArguments, int n, byte[] arrby, int n2, int n3, KeyCharacteristics keyCharacteristics) throws RemoteException, ExecutionException, InterruptedException {
        void var1_4;
        KeyCharacteristicsPromise keyCharacteristicsPromise;
        block9 : {
            block8 : {
                block7 : {
                    block6 : {
                        keyCharacteristicsPromise = new KeyCharacteristicsPromise();
                        this.mBinder.asBinder().linkToDeath(keyCharacteristicsPromise, 0);
                        n = this.mBinder.importKey(keyCharacteristicsPromise, (String)object, keymasterArguments, n, arrby, n2, n3);
                        if (n == 1) break block6;
                        this.mBinder.asBinder().unlinkToDeath(keyCharacteristicsPromise, 0);
                        return n;
                    }
                    object = keyCharacteristicsPromise.getFuture().get();
                    n = ((KeyCharacteristicsCallbackResult)object).getKeystoreResponse().getErrorCode();
                    if (n == 1) break block7;
                    this.mBinder.asBinder().unlinkToDeath(keyCharacteristicsPromise, 0);
                    return n;
                }
                object = ((KeyCharacteristicsCallbackResult)object).getKeyCharacteristics();
                if (object != null) break block8;
                this.mBinder.asBinder().unlinkToDeath(keyCharacteristicsPromise, 0);
                return 4;
            }
            try {
                keyCharacteristics.shallowCopyFrom((KeyCharacteristics)object);
                this.mBinder.asBinder().unlinkToDeath(keyCharacteristicsPromise, 0);
            }
            catch (Throwable throwable) {}
            return 1;
            break block9;
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        this.mBinder.asBinder().unlinkToDeath(keyCharacteristicsPromise, 0);
        throw var1_4;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private int importWrappedKeyInternal(String object, byte[] arrby, String string2, byte[] arrby2, KeymasterArguments keymasterArguments, long l, long l2, KeyCharacteristics keyCharacteristics) throws RemoteException, ExecutionException, InterruptedException {
        void var1_4;
        KeyCharacteristicsPromise keyCharacteristicsPromise;
        block9 : {
            block8 : {
                block7 : {
                    int n;
                    block6 : {
                        keyCharacteristicsPromise = new KeyCharacteristicsPromise();
                        this.mBinder.asBinder().linkToDeath(keyCharacteristicsPromise, 0);
                        n = this.mBinder.importWrappedKey(keyCharacteristicsPromise, (String)object, arrby, string2, arrby2, keymasterArguments, l, l2);
                        if (n == 1) break block6;
                        this.mBinder.asBinder().unlinkToDeath(keyCharacteristicsPromise, 0);
                        return n;
                    }
                    object = keyCharacteristicsPromise.getFuture().get();
                    n = ((KeyCharacteristicsCallbackResult)object).getKeystoreResponse().getErrorCode();
                    if (n == 1) break block7;
                    this.mBinder.asBinder().unlinkToDeath(keyCharacteristicsPromise, 0);
                    return n;
                }
                object = ((KeyCharacteristicsCallbackResult)object).getKeyCharacteristics();
                if (object != null) break block8;
                this.mBinder.asBinder().unlinkToDeath(keyCharacteristicsPromise, 0);
                return 4;
            }
            try {
                keyCharacteristics.shallowCopyFrom((KeyCharacteristics)object);
                this.mBinder.asBinder().unlinkToDeath(keyCharacteristicsPromise, 0);
            }
            catch (Throwable throwable) {}
            return 1;
            break block9;
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        this.mBinder.asBinder().unlinkToDeath(keyCharacteristicsPromise, 0);
        throw var1_4;
    }

    private KeymasterArguments makeLegacyArguments(String string2) {
        KeymasterArguments keymasterArguments = new KeymasterArguments();
        keymasterArguments.addEnum(268435458, KeyProperties.KeyAlgorithm.toKeymasterAsymmetricKeyAlgorithm(string2));
        keymasterArguments.addEnum(536870913, 2);
        keymasterArguments.addEnum(536870913, 3);
        keymasterArguments.addEnum(536870913, 0);
        keymasterArguments.addEnum(536870913, 1);
        keymasterArguments.addEnum(536870918, 1);
        if (string2.equalsIgnoreCase("RSA")) {
            keymasterArguments.addEnum(536870918, 2);
            keymasterArguments.addEnum(536870918, 4);
            keymasterArguments.addEnum(536870918, 5);
            keymasterArguments.addEnum(536870918, 3);
        }
        keymasterArguments.addEnum(536870917, 0);
        keymasterArguments.addEnum(536870917, 1);
        keymasterArguments.addEnum(536870917, 2);
        keymasterArguments.addEnum(536870917, 3);
        keymasterArguments.addEnum(536870917, 4);
        keymasterArguments.addEnum(536870917, 5);
        keymasterArguments.addEnum(536870917, 6);
        keymasterArguments.addBoolean(1879048695);
        keymasterArguments.addDate(1610613137, new Date(Long.MAX_VALUE));
        keymasterArguments.addDate(1610613138, new Date(Long.MAX_VALUE));
        keymasterArguments.addDate(1610613136, new Date(0L));
        return keymasterArguments;
    }

    /*
     * Exception decompiling
     */
    public int abort(IBinder var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
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

    public int addAuthToken(byte[] arrby) {
        try {
            int n = this.mBinder.addAuthToken(arrby);
            return n;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Cannot connect to keystore", remoteException);
            return 4;
        }
    }

    /*
     * Exception decompiling
     */
    public boolean addRngEntropy(byte[] var1_1, int var2_5) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
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
    public int attestDeviceIds(KeymasterArguments var1_1, KeymasterCertificateChain var2_5) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
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
    public int attestKey(String var1_1, KeymasterArguments var2_5, KeymasterCertificateChain var3_6) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
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

    public OperationResult begin(String string2, int n, boolean bl, KeymasterArguments keymasterArguments, byte[] arrby) {
        if (arrby == null) {
            arrby = new byte[]{};
        }
        if (keymasterArguments == null) {
            keymasterArguments = new KeymasterArguments();
        }
        return this.begin(string2, n, bl, keymasterArguments, arrby, -1);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public OperationResult begin(String object, int n, boolean bl, KeymasterArguments keymasterArguments, byte[] arrby, int n2) {
        void var1_8;
        OperationPromise operationPromise;
        block16 : {
            block15 : {
                block14 : {
                    block13 : {
                        block12 : {
                            operationPromise = new OperationPromise();
                            this.mBinder.asBinder().linkToDeath(operationPromise, 0);
                            if (keymasterArguments == null) {
                                keymasterArguments = new KeymasterArguments();
                            }
                            if (arrby != null) break block12;
                            arrby = new byte[]{};
                        }
                        n = this.mBinder.begin(operationPromise, this.getToken(), (String)object, n, bl, keymasterArguments, arrby, n2);
                        if (n != 1) break block13;
                        object = operationPromise.getFuture().get();
                        this.mBinder.asBinder().unlinkToDeath(operationPromise, 0);
                        return object;
                    }
                    try {
                        object = new OperationResult(n);
                        this.mBinder.asBinder().unlinkToDeath(operationPromise, 0);
                    }
                    catch (InterruptedException | ExecutionException exception) {
                        break block14;
                    }
                    catch (RemoteException remoteException) {
                        break block15;
                    }
                    return object;
                    catch (Throwable throwable) {
                        break block16;
                    }
                    catch (InterruptedException | ExecutionException exception) {
                        // empty catch block
                    }
                }
                Log.e(TAG, "Begin completed with exception", (Throwable)object);
                this.mBinder.asBinder().unlinkToDeath(operationPromise, 0);
                return null;
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
            try {
                Log.w(TAG, "Cannot connect to keystore", (Throwable)object);
                this.mBinder.asBinder().unlinkToDeath(operationPromise, 0);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            return null;
        }
        this.mBinder.asBinder().unlinkToDeath(operationPromise, 0);
        throw var1_8;
    }

    public int cancelConfirmationPrompt(IBinder iBinder) {
        try {
            int n = this.mBinder.cancelConfirmationPrompt(iBinder);
            return n;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Cannot connect to keystore", remoteException);
            return 5;
        }
    }

    public boolean clearUid(int n) {
        boolean bl = false;
        try {
            n = this.mBinder.clear_uid(n);
            if (n == 1) {
                bl = true;
            }
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Cannot connect to keystore", remoteException);
            return false;
        }
    }

    public boolean contains(String string2) {
        return this.contains(string2, -1);
    }

    public boolean contains(String string2, int n) {
        boolean bl = false;
        try {
            n = this.mBinder.exist(string2, n);
            if (n == 1) {
                bl = true;
            }
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Cannot connect to keystore", remoteException);
            return false;
        }
    }

    @UnsupportedAppUsage
    public boolean delete(String string2) {
        return this.delete(string2, -1);
    }

    public boolean delete(String string2, int n) {
        boolean bl;
        n = this.delete2(string2, n);
        boolean bl2 = bl = true;
        if (n != 1) {
            bl2 = n == 7 ? bl : false;
        }
        return bl2;
    }

    int delete2(String string2, int n) {
        try {
            n = this.mBinder.del(string2, n);
            return n;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Cannot connect to keystore", remoteException);
            return 4;
        }
    }

    public ExportResult exportKey(String string2, int n, KeymasterBlob keymasterBlob, KeymasterBlob keymasterBlob2) {
        return this.exportKey(string2, n, keymasterBlob, keymasterBlob2, -1);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public ExportResult exportKey(String object, int n, KeymasterBlob keymasterBlob, KeymasterBlob keymasterBlob2, int n2) {
        void var1_11;
        ExportKeyPromise exportKeyPromise;
        block20 : {
            block19 : {
                block18 : {
                    block17 : {
                        block16 : {
                            exportKeyPromise = new ExportKeyPromise();
                            this.mBinder.asBinder().linkToDeath(exportKeyPromise, 0);
                            if (keymasterBlob == null) {
                                keymasterBlob = new KeymasterBlob(new byte[0]);
                            }
                            if (keymasterBlob2 != null) break block16;
                            keymasterBlob2 = new KeymasterBlob(new byte[0]);
                        }
                        n = this.mBinder.exportKey(exportKeyPromise, (String)object, n, keymasterBlob, keymasterBlob2, n2);
                        if (n != 1) break block17;
                        object = exportKeyPromise.getFuture().get();
                        this.mBinder.asBinder().unlinkToDeath(exportKeyPromise, 0);
                        return object;
                    }
                    try {
                        object = new ExportResult(n);
                        this.mBinder.asBinder().unlinkToDeath(exportKeyPromise, 0);
                    }
                    catch (InterruptedException | ExecutionException exception) {
                        break block18;
                    }
                    catch (RemoteException remoteException) {
                        break block19;
                    }
                    return object;
                    catch (Throwable throwable) {
                        break block20;
                    }
                    catch (InterruptedException | ExecutionException exception) {
                        break block18;
                    }
                    catch (RemoteException remoteException) {
                        break block19;
                    }
                    catch (Throwable throwable) {
                        break block20;
                    }
                    catch (InterruptedException | ExecutionException exception) {
                        // empty catch block
                    }
                }
                Log.e(TAG, "ExportKey completed with exception", (Throwable)object);
                this.mBinder.asBinder().unlinkToDeath(exportKeyPromise, 0);
                return null;
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
            try {
                Log.w(TAG, "Cannot connect to keystore", (Throwable)object);
                this.mBinder.asBinder().unlinkToDeath(exportKeyPromise, 0);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            return null;
        }
        this.mBinder.asBinder().unlinkToDeath(exportKeyPromise, 0);
        throw var1_11;
    }

    public OperationResult finish(IBinder iBinder, KeymasterArguments keymasterArguments, byte[] arrby) {
        return this.finish(iBinder, keymasterArguments, arrby, null);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public OperationResult finish(IBinder object, KeymasterArguments keymasterArguments, byte[] arrby, byte[] arrby2) {
        OperationPromise operationPromise;
        void var1_10;
        block16 : {
            void var1_7;
            block18 : {
                block17 : {
                    int n;
                    block15 : {
                        block14 : {
                            operationPromise = new OperationPromise();
                            this.mBinder.asBinder().linkToDeath(operationPromise, 0);
                            if (keymasterArguments == null) {
                                keymasterArguments = new KeymasterArguments();
                            }
                            if (arrby2 != null) break block14;
                            arrby2 = new byte[]{};
                        }
                        if (arrby == null) {
                            arrby = new byte[]{};
                        }
                        n = this.mBinder.finish(operationPromise, (IBinder)object, keymasterArguments, arrby, arrby2);
                        if (n != 1) break block15;
                        object = operationPromise.getFuture().get();
                        this.mBinder.asBinder().unlinkToDeath(operationPromise, 0);
                        return object;
                    }
                    try {
                        object = new OperationResult(n);
                        this.mBinder.asBinder().unlinkToDeath(operationPromise, 0);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    catch (InterruptedException | ExecutionException exception) {
                        break block17;
                    }
                    catch (RemoteException remoteException) {
                        break block18;
                    }
                    return object;
                    catch (Throwable throwable) {
                        break block16;
                    }
                    catch (InterruptedException | ExecutionException exception) {
                        // empty catch block
                    }
                }
                Log.e(TAG, "Finish completed with exception", (Throwable)var1_7);
                this.mBinder.asBinder().unlinkToDeath(operationPromise, 0);
                return null;
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
            try {
                Log.w(TAG, "Cannot connect to keystore", (Throwable)var1_7);
                this.mBinder.asBinder().unlinkToDeath(operationPromise, 0);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            return null;
        }
        this.mBinder.asBinder().unlinkToDeath(operationPromise, 0);
        throw var1_10;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int generateKey(String string2, KeymasterArguments keymasterArguments, byte[] arrby, int n, int n2, KeyCharacteristics keyCharacteristics) {
        void var1_9;
        block15 : {
            void var1_7;
            block14 : {
                if (arrby == null) {
                    arrby = new byte[]{};
                }
                if (keymasterArguments == null) {
                    keymasterArguments = new KeymasterArguments();
                }
                int n3 = this.generateKeyInternal(string2, keymasterArguments, arrby, n, n2, keyCharacteristics);
                if (n3 != 16) return n3;
                IKeystoreService iKeystoreService = this.mBinder;
                try {
                    iKeystoreService.del(string2, n);
                    return this.generateKeyInternal(string2, keymasterArguments, arrby, n, n2, keyCharacteristics);
                }
                catch (InterruptedException | ExecutionException exception) {
                    break block14;
                }
                catch (RemoteException remoteException) {
                    break block15;
                }
                catch (InterruptedException | ExecutionException exception) {
                    break block14;
                }
                catch (RemoteException remoteException) {
                    break block15;
                }
                catch (InterruptedException | ExecutionException exception) {
                    break block14;
                }
                catch (RemoteException remoteException) {
                    break block15;
                }
                catch (InterruptedException | ExecutionException exception) {
                }
                catch (RemoteException remoteException) {
                    break block15;
                }
            }
            Log.e(TAG, "generateKey completed with exception", (Throwable)var1_7);
            return 4;
        }
        Log.w(TAG, "Cannot connect to keystore", (Throwable)var1_9);
        return 4;
    }

    public int generateKey(String string2, KeymasterArguments keymasterArguments, byte[] arrby, int n, KeyCharacteristics keyCharacteristics) {
        return this.generateKey(string2, keymasterArguments, arrby, -1, n, keyCharacteristics);
    }

    @UnsupportedAppUsage
    public byte[] get(String string2) {
        return this.get(string2, -1);
    }

    public byte[] get(String string2, int n) {
        return this.get(string2, n, false);
    }

    public byte[] get(String arrby, int n, boolean bl) {
        if (arrby == null) {
            arrby = "";
        }
        try {
            arrby = this.mBinder.get((String)arrby, n);
            return arrby;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            if (!bl || serviceSpecificException.errorCode != 7) {
                Log.w(TAG, "KeyStore exception", serviceSpecificException);
            }
            return null;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Cannot connect to keystore", remoteException);
            return null;
        }
    }

    public byte[] get(String string2, boolean bl) {
        return this.get(string2, -1, bl);
    }

    public InvalidKeyException getInvalidKeyException(String string2, int n, int n2) {
        return this.getInvalidKeyException(string2, n, KeyStore.getKeyStoreException(n2));
    }

    public InvalidKeyException getInvalidKeyException(String list, int n, KeyStoreException object) {
        int n2 = ((KeyStoreException)object).getErrorCode();
        if (n2 != 2) {
            if (n2 != 3) {
                if (n2 != 15) {
                    switch (n2) {
                        default: {
                            return new InvalidKeyException("Keystore operation failed", (Throwable)object);
                        }
                        case -24: {
                            return new KeyNotYetValidException();
                        }
                        case -25: {
                            return new KeyExpiredException();
                        }
                        case -26: 
                    }
                }
                if ((n = this.getKeyCharacteristics((String)((Object)list), null, null, n, (KeyCharacteristics)(object = new KeyCharacteristics()))) != 1) {
                    return new InvalidKeyException("Failed to obtained key characteristics", KeyStore.getKeyStoreException(n));
                }
                list = ((KeyCharacteristics)object).getUnsignedLongs(-1610612234);
                if (list.isEmpty()) {
                    return new KeyPermanentlyInvalidatedException();
                }
                long l = GateKeeper.getSecureUserId();
                if (l != 0L && list.contains(KeymasterArguments.toUint64(l))) {
                    return new UserNotAuthenticatedException();
                }
                l = this.getFingerprintOnlySid();
                if (l != 0L && list.contains(KeymasterArguments.toUint64(l))) {
                    return new UserNotAuthenticatedException();
                }
                l = this.getFaceOnlySid();
                if (l != 0L && list.contains(KeymasterArguments.toUint64(l))) {
                    return new UserNotAuthenticatedException();
                }
                return new KeyPermanentlyInvalidatedException();
            }
            return new KeyPermanentlyInvalidatedException();
        }
        return new UserNotAuthenticatedException();
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int getKeyCharacteristics(String object, KeymasterBlob keymasterBlob, KeymasterBlob keymasterBlob2, int n, KeyCharacteristics keyCharacteristics) {
        void var1_11;
        KeyCharacteristicsPromise keyCharacteristicsPromise;
        block22 : {
            block24 : {
                block23 : {
                    block21 : {
                        block20 : {
                            block19 : {
                                block18 : {
                                    keyCharacteristicsPromise = new KeyCharacteristicsPromise();
                                    this.mBinder.asBinder().linkToDeath(keyCharacteristicsPromise, 0);
                                    if (keymasterBlob == null) {
                                        keymasterBlob = new KeymasterBlob(new byte[0]);
                                    }
                                    if (keymasterBlob2 != null) break block18;
                                    keymasterBlob2 = new KeymasterBlob(new byte[0]);
                                }
                                n = this.mBinder.getKeyCharacteristics(keyCharacteristicsPromise, (String)object, keymasterBlob, keymasterBlob2, n);
                                if (n == 1) break block19;
                                this.mBinder.asBinder().unlinkToDeath(keyCharacteristicsPromise, 0);
                                return n;
                            }
                            object = keyCharacteristicsPromise.getFuture().get();
                            n = ((KeyCharacteristicsCallbackResult)object).getKeystoreResponse().getErrorCode();
                            if (n == 1) break block20;
                            this.mBinder.asBinder().unlinkToDeath(keyCharacteristicsPromise, 0);
                            return n;
                        }
                        object = ((KeyCharacteristicsCallbackResult)object).getKeyCharacteristics();
                        if (object != null) break block21;
                        this.mBinder.asBinder().unlinkToDeath(keyCharacteristicsPromise, 0);
                        return 4;
                    }
                    try {
                        keyCharacteristics.shallowCopyFrom((KeyCharacteristics)object);
                        this.mBinder.asBinder().unlinkToDeath(keyCharacteristicsPromise, 0);
                    }
                    catch (Throwable throwable) {
                        break block22;
                    }
                    catch (InterruptedException | ExecutionException exception) {
                        break block23;
                    }
                    catch (RemoteException remoteException) {
                        break block24;
                    }
                    return 1;
                    catch (InterruptedException | ExecutionException exception) {
                        break block23;
                    }
                    catch (RemoteException remoteException) {
                        break block24;
                    }
                    catch (Throwable throwable) {
                        break block22;
                    }
                    catch (InterruptedException | ExecutionException exception) {
                        // empty catch block
                    }
                }
                Log.e(TAG, "GetKeyCharacteristics completed with exception", (Throwable)object);
                this.mBinder.asBinder().unlinkToDeath(keyCharacteristicsPromise, 0);
                return 4;
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
            try {
                Log.w(TAG, "Cannot connect to keystore", (Throwable)object);
                this.mBinder.asBinder().unlinkToDeath(keyCharacteristicsPromise, 0);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            return 4;
        }
        this.mBinder.asBinder().unlinkToDeath(keyCharacteristicsPromise, 0);
        throw var1_11;
    }

    public int getKeyCharacteristics(String string2, KeymasterBlob keymasterBlob, KeymasterBlob keymasterBlob2, KeyCharacteristics keyCharacteristics) {
        return this.getKeyCharacteristics(string2, keymasterBlob, keymasterBlob2, -1, keyCharacteristics);
    }

    public int getLastError() {
        return this.mError;
    }

    public long getmtime(String string2) {
        return this.getmtime(string2, -1);
    }

    public long getmtime(String string2, int n) {
        try {
            long l = this.mBinder.getmtime(string2, n);
            if (l == -1L) {
                return -1L;
            }
            return 1000L * l;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Cannot connect to keystore", remoteException);
            return -1L;
        }
    }

    public String grant(String string2, int n) {
        try {
            string2 = this.mBinder.grant(string2, n);
            if (string2 == "") {
                return null;
            }
            return string2;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Cannot connect to keystore", remoteException);
            return null;
        }
    }

    public int importKey(String string2, KeymasterArguments keymasterArguments, int n, byte[] arrby, int n2, int n3, KeyCharacteristics keyCharacteristics) {
        int n4;
        block4 : {
            int n5;
            n4 = n5 = this.importKeyInternal(string2, keymasterArguments, n, arrby, n2, n3, keyCharacteristics);
            if (n5 != 16) break block4;
            try {
                this.mBinder.del(string2, n2);
                n4 = this.importKeyInternal(string2, keymasterArguments, n, arrby, n2, n3, keyCharacteristics);
            }
            catch (InterruptedException | ExecutionException exception) {
                Log.e(TAG, "ImportKey completed with exception", exception);
                return 4;
            }
            catch (RemoteException remoteException) {
                Log.w(TAG, "Cannot connect to keystore", remoteException);
                return 4;
            }
        }
        return n4;
    }

    public int importKey(String string2, KeymasterArguments keymasterArguments, int n, byte[] arrby, int n2, KeyCharacteristics keyCharacteristics) {
        return this.importKey(string2, keymasterArguments, n, arrby, -1, n2, keyCharacteristics);
    }

    public boolean importKey(String string2, byte[] arrby, int n, int n2) {
        String string3 = this.getAlgorithmFromPKCS8(arrby);
        if (string3 == null) {
            return false;
        }
        n = this.importKey(string2, this.makeLegacyArguments(string3), 1, arrby, n, n2, new KeyCharacteristics());
        if (n != 1) {
            Log.e(TAG, Log.getStackTraceString(new KeyStoreException(n, "legacy key import failed")));
            return false;
        }
        return true;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int importWrappedKey(String string2, byte[] arrby, String string3, byte[] arrby2, KeymasterArguments keymasterArguments, long l, long l2, int n, KeyCharacteristics keyCharacteristics) {
        void var1_9;
        block10 : {
            void var1_7;
            block9 : {
                n = this.importWrappedKeyInternal(string2, arrby, string3, arrby2, keymasterArguments, l, l2, keyCharacteristics);
                if (n != 16) return n;
                IKeystoreService iKeystoreService = this.mBinder;
                try {
                    iKeystoreService.del(string2, -1);
                    return this.importWrappedKeyInternal(string2, arrby, string3, arrby2, keymasterArguments, l, l2, keyCharacteristics);
                }
                catch (InterruptedException | ExecutionException exception) {
                    break block9;
                }
                catch (RemoteException remoteException) {
                    break block10;
                }
                catch (InterruptedException | ExecutionException exception) {
                    break block9;
                }
                catch (RemoteException remoteException) {
                    break block10;
                }
                catch (InterruptedException | ExecutionException exception) {
                    // empty catch block
                }
            }
            Log.e(TAG, "ImportWrappedKey completed with exception", (Throwable)var1_7);
            return 4;
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        Log.w(TAG, "Cannot connect to keystore", (Throwable)var1_9);
        return 4;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int insert(String var1_1, byte[] var2_3, int var3_4, int var4_5) {
        var5_6 = var2_3;
        if (var2_3 != null) ** GOTO lbl5
        try {
            var5_6 = new byte[]{};
lbl5: // 2 sources:
            var7_8 = var6_7 = this.mBinder.insert(var1_1, var5_6, var3_4, var4_5);
            if (var6_7 != 16) return var7_8;
            this.mBinder.del(var1_1, var3_4);
            return this.mBinder.insert(var1_1, var5_6, var3_4, var4_5);
        }
        catch (RemoteException var1_2) {
            Log.w("KeyStore", "Cannot connect to keystore", var1_2);
            return 4;
        }
    }

    public boolean isConfirmationPromptSupported() {
        try {
            boolean bl = this.mBinder.isConfirmationPromptSupported();
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Cannot connect to keystore", remoteException);
            return false;
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public boolean isEmpty() {
        return this.isEmpty(UserHandle.myUserId());
    }

    public boolean isEmpty(int n) {
        boolean bl = false;
        try {
            n = this.mBinder.isEmpty(n);
            if (n != 0) {
                bl = true;
            }
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Cannot connect to keystore", remoteException);
            return false;
        }
    }

    public boolean isHardwareBacked() {
        return this.isHardwareBacked("RSA");
    }

    public boolean isHardwareBacked(String string2) {
        boolean bl = false;
        try {
            int n = this.mBinder.is_hardware_backed(string2.toUpperCase(Locale.US));
            if (n == 1) {
                bl = true;
            }
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Cannot connect to keystore", remoteException);
            return false;
        }
    }

    public boolean isUnlocked() {
        boolean bl = this.state() == State.UNLOCKED;
        return bl;
    }

    public String[] list(String string2) {
        return this.list(string2, -1);
    }

    public String[] list(String arrstring, int n) {
        try {
            arrstring = this.mBinder.list((String)arrstring, n);
            return arrstring;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            Log.w(TAG, "KeyStore exception", serviceSpecificException);
            return null;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Cannot connect to keystore", remoteException);
            return null;
        }
    }

    @UnsupportedAppUsage
    public int[] listUidsOfAuthBoundKeys() {
        ArrayList<String> arrayList;
        block4 : {
            arrayList = new ArrayList<String>();
            int n = this.mBinder.listUidsOfAuthBoundKeys(arrayList);
            if (n == 1) break block4;
            try {
                Log.w(TAG, String.format("listUidsOfAuthBoundKeys failed with error code %d", n));
                return null;
            }
            catch (ServiceSpecificException serviceSpecificException) {
                Log.w(TAG, "KeyStore exception", serviceSpecificException);
                return null;
            }
            catch (RemoteException remoteException) {
                Log.w(TAG, "Cannot connect to keystore", remoteException);
                return null;
            }
        }
        return arrayList.stream().mapToInt(_$$Lambda$wddj3_hVVrg0MkscpMtYt3BzY8Y.INSTANCE).toArray();
    }

    public boolean lock() {
        return this.lock(UserHandle.myUserId());
    }

    public boolean lock(int n) {
        boolean bl = false;
        try {
            n = this.mBinder.lock(n);
            if (n == 1) {
                bl = true;
            }
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Cannot connect to keystore", remoteException);
            return false;
        }
    }

    public void onDeviceOffBody() {
        try {
            this.mBinder.onDeviceOffBody();
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Cannot connect to keystore", remoteException);
        }
    }

    public void onUserAdded(int n) {
        this.onUserAdded(n, -1);
    }

    public void onUserAdded(int n, int n2) {
        try {
            this.mBinder.onUserAdded(n, n2);
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Cannot connect to keystore", remoteException);
        }
    }

    public boolean onUserPasswordChanged(int n, String string2) {
        String string3 = string2;
        if (string2 == null) {
            string3 = "";
        }
        boolean bl = false;
        try {
            n = this.mBinder.onUserPasswordChanged(n, string3);
            if (n == 1) {
                bl = true;
            }
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Cannot connect to keystore", remoteException);
            return false;
        }
    }

    public boolean onUserPasswordChanged(String string2) {
        return this.onUserPasswordChanged(UserHandle.getUserId(Process.myUid()), string2);
    }

    public void onUserRemoved(int n) {
        try {
            this.mBinder.onUserRemoved(n);
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Cannot connect to keystore", remoteException);
        }
    }

    public int presentConfirmationPrompt(IBinder iBinder, String string2, byte[] arrby, String string3, int n) {
        try {
            n = this.mBinder.presentConfirmationPrompt(iBinder, string2, arrby, string3, n);
            return n;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Cannot connect to keystore", remoteException);
            return 5;
        }
    }

    public boolean put(String string2, byte[] arrby, int n, int n2) {
        n = this.insert(string2, arrby, n, n2);
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public boolean reset() {
        boolean bl = false;
        try {
            int n = this.mBinder.reset();
            if (n == 1) {
                bl = true;
            }
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Cannot connect to keystore", remoteException);
            return false;
        }
    }

    @UnsupportedAppUsage
    public State state() {
        return this.state(UserHandle.myUserId());
    }

    @UnsupportedAppUsage
    public State state(int n) {
        try {
            n = this.mBinder.getState(n);
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        return State.UNINITIALIZED;
                    }
                    throw new AssertionError(this.mError);
                }
                return State.LOCKED;
            }
            return State.UNLOCKED;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Cannot connect to keystore", remoteException);
            throw new AssertionError(remoteException);
        }
    }

    public boolean ungrant(String string2, int n) {
        boolean bl = false;
        try {
            n = this.mBinder.ungrant(string2, n);
            if (n == 1) {
                bl = true;
            }
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Cannot connect to keystore", remoteException);
            return false;
        }
    }

    public boolean unlock(int n, String string2) {
        boolean bl = false;
        if (string2 == null) {
            string2 = "";
        }
        try {
            n = this.mError = this.mBinder.unlock(n, string2);
            if (n == 1) {
                bl = true;
            }
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Cannot connect to keystore", remoteException);
            return false;
        }
    }

    @UnsupportedAppUsage
    public boolean unlock(String string2) {
        return this.unlock(UserHandle.getUserId(Process.myUid()), string2);
    }

    /*
     * Exception decompiling
     */
    public OperationResult update(IBinder var1_1, KeymasterArguments var2_5, byte[] var3_6) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
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

    private class CertificateChainPromise
    extends IKeystoreCertificateChainCallback.Stub
    implements IBinder.DeathRecipient {
        private final CompletableFuture<KeyAttestationCallbackResult> future = new CompletableFuture();

        private CertificateChainPromise() {
        }

        @Override
        public void binderDied() {
            this.future.completeExceptionally(new RemoteException("Keystore died"));
        }

        public final CompletableFuture<KeyAttestationCallbackResult> getFuture() {
            return this.future;
        }

        @Override
        public void onFinished(KeystoreResponse keystoreResponse, KeymasterCertificateChain keymasterCertificateChain) throws RemoteException {
            this.future.complete(new KeyAttestationCallbackResult(keystoreResponse, keymasterCertificateChain));
        }
    }

    private class ExportKeyPromise
    extends IKeystoreExportKeyCallback.Stub
    implements IBinder.DeathRecipient {
        private final CompletableFuture<ExportResult> future = new CompletableFuture();

        private ExportKeyPromise() {
        }

        @Override
        public void binderDied() {
            this.future.completeExceptionally(new RemoteException("Keystore died"));
        }

        public final CompletableFuture<ExportResult> getFuture() {
            return this.future;
        }

        @Override
        public void onFinished(ExportResult exportResult) throws RemoteException {
            this.future.complete(exportResult);
        }
    }

    private class KeyAttestationCallbackResult {
        private KeymasterCertificateChain certificateChain;
        private KeystoreResponse keystoreResponse;

        public KeyAttestationCallbackResult(KeystoreResponse keystoreResponse, KeymasterCertificateChain keymasterCertificateChain) {
            this.keystoreResponse = keystoreResponse;
            this.certificateChain = keymasterCertificateChain;
        }

        public KeymasterCertificateChain getCertificateChain() {
            return this.certificateChain;
        }

        public KeystoreResponse getKeystoreResponse() {
            return this.keystoreResponse;
        }

        public void setCertificateChain(KeymasterCertificateChain keymasterCertificateChain) {
            this.certificateChain = keymasterCertificateChain;
        }

        public void setKeystoreResponse(KeystoreResponse keystoreResponse) {
            this.keystoreResponse = keystoreResponse;
        }
    }

    private class KeyCharacteristicsCallbackResult {
        private KeyCharacteristics keyCharacteristics;
        private KeystoreResponse keystoreResponse;

        public KeyCharacteristicsCallbackResult(KeystoreResponse keystoreResponse, KeyCharacteristics keyCharacteristics) {
            this.keystoreResponse = keystoreResponse;
            this.keyCharacteristics = keyCharacteristics;
        }

        public KeyCharacteristics getKeyCharacteristics() {
            return this.keyCharacteristics;
        }

        public KeystoreResponse getKeystoreResponse() {
            return this.keystoreResponse;
        }

        public void setKeyCharacteristics(KeyCharacteristics keyCharacteristics) {
            this.keyCharacteristics = keyCharacteristics;
        }

        public void setKeystoreResponse(KeystoreResponse keystoreResponse) {
            this.keystoreResponse = keystoreResponse;
        }
    }

    private class KeyCharacteristicsPromise
    extends IKeystoreKeyCharacteristicsCallback.Stub
    implements IBinder.DeathRecipient {
        private final CompletableFuture<KeyCharacteristicsCallbackResult> future = new CompletableFuture();

        private KeyCharacteristicsPromise() {
        }

        @Override
        public void binderDied() {
            this.future.completeExceptionally(new RemoteException("Keystore died"));
        }

        public final CompletableFuture<KeyCharacteristicsCallbackResult> getFuture() {
            return this.future;
        }

        @Override
        public void onFinished(KeystoreResponse keystoreResponse, KeyCharacteristics keyCharacteristics) throws RemoteException {
            this.future.complete(new KeyCharacteristicsCallbackResult(keystoreResponse, keyCharacteristics));
        }
    }

    private class KeystoreResultPromise
    extends IKeystoreResponseCallback.Stub
    implements IBinder.DeathRecipient {
        private final CompletableFuture<KeystoreResponse> future = new CompletableFuture();

        private KeystoreResultPromise() {
        }

        @Override
        public void binderDied() {
            this.future.completeExceptionally(new RemoteException("Keystore died"));
        }

        public final CompletableFuture<KeystoreResponse> getFuture() {
            return this.future;
        }

        @Override
        public void onFinished(KeystoreResponse keystoreResponse) throws RemoteException {
            this.future.complete(keystoreResponse);
        }
    }

    private class OperationPromise
    extends IKeystoreOperationResultCallback.Stub
    implements IBinder.DeathRecipient {
        private final CompletableFuture<OperationResult> future = new CompletableFuture();

        private OperationPromise() {
        }

        @Override
        public void binderDied() {
            this.future.completeExceptionally(new RemoteException("Keystore died"));
        }

        public final CompletableFuture<OperationResult> getFuture() {
            return this.future;
        }

        @Override
        public void onFinished(OperationResult operationResult) throws RemoteException {
            this.future.complete(operationResult);
        }
    }

    public static enum State {
        UNLOCKED,
        LOCKED,
        UNINITIALIZED;
        
    }

}

