/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.app.trust.IStrongAuthTracker;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.security.keystore.recovery.KeyChainProtectionParams;
import android.security.keystore.recovery.KeyChainSnapshot;
import android.security.keystore.recovery.RecoveryCertPath;
import android.security.keystore.recovery.WrappedApplicationKey;
import com.android.internal.widget.ICheckCredentialProgressCallback;
import com.android.internal.widget.VerifyCredentialResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ILockSettings
extends IInterface {
    public VerifyCredentialResponse checkCredential(byte[] var1, int var2, int var3, ICheckCredentialProgressCallback var4) throws RemoteException;

    public boolean checkVoldPassword(int var1) throws RemoteException;

    public void closeSession(String var1) throws RemoteException;

    public String generateKey(String var1) throws RemoteException;

    public String generateKeyWithMetadata(String var1, byte[] var2) throws RemoteException;

    @UnsupportedAppUsage
    public boolean getBoolean(String var1, boolean var2, int var3) throws RemoteException;

    public byte[] getHashFactor(byte[] var1, int var2) throws RemoteException;

    public String getKey(String var1) throws RemoteException;

    public KeyChainSnapshot getKeyChainSnapshot() throws RemoteException;

    @UnsupportedAppUsage
    public long getLong(String var1, long var2, int var4) throws RemoteException;

    public int[] getRecoverySecretTypes() throws RemoteException;

    public Map getRecoveryStatus() throws RemoteException;

    public boolean getSeparateProfileChallengeEnabled(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public String getString(String var1, String var2, int var3) throws RemoteException;

    public int getStrongAuthForUser(int var1) throws RemoteException;

    public boolean hasPendingEscrowToken(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean havePassword(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean havePattern(int var1) throws RemoteException;

    public String importKey(String var1, byte[] var2) throws RemoteException;

    public String importKeyWithMetadata(String var1, byte[] var2, byte[] var3) throws RemoteException;

    public void initRecoveryServiceWithSigFile(String var1, byte[] var2, byte[] var3) throws RemoteException;

    public Map recoverKeyChainSnapshot(String var1, byte[] var2, List<WrappedApplicationKey> var3) throws RemoteException;

    public void registerStrongAuthTracker(IStrongAuthTracker var1) throws RemoteException;

    public void removeKey(String var1) throws RemoteException;

    public void requireStrongAuth(int var1, int var2) throws RemoteException;

    public void resetKeyStore(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void setBoolean(String var1, boolean var2, int var3) throws RemoteException;

    public void setLockCredential(byte[] var1, int var2, byte[] var3, int var4, int var5, boolean var6) throws RemoteException;

    @UnsupportedAppUsage
    public void setLong(String var1, long var2, int var4) throws RemoteException;

    public void setRecoverySecretTypes(int[] var1) throws RemoteException;

    public void setRecoveryStatus(String var1, int var2) throws RemoteException;

    public void setSeparateProfileChallengeEnabled(int var1, boolean var2, byte[] var3) throws RemoteException;

    public void setServerParams(byte[] var1) throws RemoteException;

    public void setSnapshotCreatedPendingIntent(PendingIntent var1) throws RemoteException;

    @UnsupportedAppUsage
    public void setString(String var1, String var2, int var3) throws RemoteException;

    public byte[] startRecoverySessionWithCertPath(String var1, String var2, RecoveryCertPath var3, byte[] var4, byte[] var5, List<KeyChainProtectionParams> var6) throws RemoteException;

    public void systemReady() throws RemoteException;

    public void unregisterStrongAuthTracker(IStrongAuthTracker var1) throws RemoteException;

    public void userPresent(int var1) throws RemoteException;

    public VerifyCredentialResponse verifyCredential(byte[] var1, int var2, long var3, int var5) throws RemoteException;

    public VerifyCredentialResponse verifyTiedProfileChallenge(byte[] var1, int var2, long var3, int var5) throws RemoteException;

    public static class Default
    implements ILockSettings {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public VerifyCredentialResponse checkCredential(byte[] arrby, int n, int n2, ICheckCredentialProgressCallback iCheckCredentialProgressCallback) throws RemoteException {
            return null;
        }

        @Override
        public boolean checkVoldPassword(int n) throws RemoteException {
            return false;
        }

        @Override
        public void closeSession(String string2) throws RemoteException {
        }

        @Override
        public String generateKey(String string2) throws RemoteException {
            return null;
        }

        @Override
        public String generateKeyWithMetadata(String string2, byte[] arrby) throws RemoteException {
            return null;
        }

        @Override
        public boolean getBoolean(String string2, boolean bl, int n) throws RemoteException {
            return false;
        }

        @Override
        public byte[] getHashFactor(byte[] arrby, int n) throws RemoteException {
            return null;
        }

        @Override
        public String getKey(String string2) throws RemoteException {
            return null;
        }

        @Override
        public KeyChainSnapshot getKeyChainSnapshot() throws RemoteException {
            return null;
        }

        @Override
        public long getLong(String string2, long l, int n) throws RemoteException {
            return 0L;
        }

        @Override
        public int[] getRecoverySecretTypes() throws RemoteException {
            return null;
        }

        @Override
        public Map getRecoveryStatus() throws RemoteException {
            return null;
        }

        @Override
        public boolean getSeparateProfileChallengeEnabled(int n) throws RemoteException {
            return false;
        }

        @Override
        public String getString(String string2, String string3, int n) throws RemoteException {
            return null;
        }

        @Override
        public int getStrongAuthForUser(int n) throws RemoteException {
            return 0;
        }

        @Override
        public boolean hasPendingEscrowToken(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean havePassword(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean havePattern(int n) throws RemoteException {
            return false;
        }

        @Override
        public String importKey(String string2, byte[] arrby) throws RemoteException {
            return null;
        }

        @Override
        public String importKeyWithMetadata(String string2, byte[] arrby, byte[] arrby2) throws RemoteException {
            return null;
        }

        @Override
        public void initRecoveryServiceWithSigFile(String string2, byte[] arrby, byte[] arrby2) throws RemoteException {
        }

        @Override
        public Map recoverKeyChainSnapshot(String string2, byte[] arrby, List<WrappedApplicationKey> list) throws RemoteException {
            return null;
        }

        @Override
        public void registerStrongAuthTracker(IStrongAuthTracker iStrongAuthTracker) throws RemoteException {
        }

        @Override
        public void removeKey(String string2) throws RemoteException {
        }

        @Override
        public void requireStrongAuth(int n, int n2) throws RemoteException {
        }

        @Override
        public void resetKeyStore(int n) throws RemoteException {
        }

        @Override
        public void setBoolean(String string2, boolean bl, int n) throws RemoteException {
        }

        @Override
        public void setLockCredential(byte[] arrby, int n, byte[] arrby2, int n2, int n3, boolean bl) throws RemoteException {
        }

        @Override
        public void setLong(String string2, long l, int n) throws RemoteException {
        }

        @Override
        public void setRecoverySecretTypes(int[] arrn) throws RemoteException {
        }

        @Override
        public void setRecoveryStatus(String string2, int n) throws RemoteException {
        }

        @Override
        public void setSeparateProfileChallengeEnabled(int n, boolean bl, byte[] arrby) throws RemoteException {
        }

        @Override
        public void setServerParams(byte[] arrby) throws RemoteException {
        }

        @Override
        public void setSnapshotCreatedPendingIntent(PendingIntent pendingIntent) throws RemoteException {
        }

        @Override
        public void setString(String string2, String string3, int n) throws RemoteException {
        }

        @Override
        public byte[] startRecoverySessionWithCertPath(String string2, String string3, RecoveryCertPath recoveryCertPath, byte[] arrby, byte[] arrby2, List<KeyChainProtectionParams> list) throws RemoteException {
            return null;
        }

        @Override
        public void systemReady() throws RemoteException {
        }

        @Override
        public void unregisterStrongAuthTracker(IStrongAuthTracker iStrongAuthTracker) throws RemoteException {
        }

        @Override
        public void userPresent(int n) throws RemoteException {
        }

        @Override
        public VerifyCredentialResponse verifyCredential(byte[] arrby, int n, long l, int n2) throws RemoteException {
            return null;
        }

        @Override
        public VerifyCredentialResponse verifyTiedProfileChallenge(byte[] arrby, int n, long l, int n2) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements ILockSettings {
        private static final String DESCRIPTOR = "com.android.internal.widget.ILockSettings";
        static final int TRANSACTION_checkCredential = 9;
        static final int TRANSACTION_checkVoldPassword = 12;
        static final int TRANSACTION_closeSession = 41;
        static final int TRANSACTION_generateKey = 27;
        static final int TRANSACTION_generateKeyWithMetadata = 28;
        static final int TRANSACTION_getBoolean = 4;
        static final int TRANSACTION_getHashFactor = 15;
        static final int TRANSACTION_getKey = 31;
        static final int TRANSACTION_getKeyChainSnapshot = 26;
        static final int TRANSACTION_getLong = 5;
        static final int TRANSACTION_getRecoverySecretTypes = 38;
        static final int TRANSACTION_getRecoveryStatus = 36;
        static final int TRANSACTION_getSeparateProfileChallengeEnabled = 17;
        static final int TRANSACTION_getString = 6;
        static final int TRANSACTION_getStrongAuthForUser = 23;
        static final int TRANSACTION_hasPendingEscrowToken = 24;
        static final int TRANSACTION_havePassword = 14;
        static final int TRANSACTION_havePattern = 13;
        static final int TRANSACTION_importKey = 29;
        static final int TRANSACTION_importKeyWithMetadata = 30;
        static final int TRANSACTION_initRecoveryServiceWithSigFile = 25;
        static final int TRANSACTION_recoverKeyChainSnapshot = 40;
        static final int TRANSACTION_registerStrongAuthTracker = 18;
        static final int TRANSACTION_removeKey = 32;
        static final int TRANSACTION_requireStrongAuth = 20;
        static final int TRANSACTION_resetKeyStore = 8;
        static final int TRANSACTION_setBoolean = 1;
        static final int TRANSACTION_setLockCredential = 7;
        static final int TRANSACTION_setLong = 2;
        static final int TRANSACTION_setRecoverySecretTypes = 37;
        static final int TRANSACTION_setRecoveryStatus = 35;
        static final int TRANSACTION_setSeparateProfileChallengeEnabled = 16;
        static final int TRANSACTION_setServerParams = 34;
        static final int TRANSACTION_setSnapshotCreatedPendingIntent = 33;
        static final int TRANSACTION_setString = 3;
        static final int TRANSACTION_startRecoverySessionWithCertPath = 39;
        static final int TRANSACTION_systemReady = 21;
        static final int TRANSACTION_unregisterStrongAuthTracker = 19;
        static final int TRANSACTION_userPresent = 22;
        static final int TRANSACTION_verifyCredential = 10;
        static final int TRANSACTION_verifyTiedProfileChallenge = 11;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ILockSettings asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ILockSettings) {
                return (ILockSettings)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ILockSettings getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 41: {
                    return "closeSession";
                }
                case 40: {
                    return "recoverKeyChainSnapshot";
                }
                case 39: {
                    return "startRecoverySessionWithCertPath";
                }
                case 38: {
                    return "getRecoverySecretTypes";
                }
                case 37: {
                    return "setRecoverySecretTypes";
                }
                case 36: {
                    return "getRecoveryStatus";
                }
                case 35: {
                    return "setRecoveryStatus";
                }
                case 34: {
                    return "setServerParams";
                }
                case 33: {
                    return "setSnapshotCreatedPendingIntent";
                }
                case 32: {
                    return "removeKey";
                }
                case 31: {
                    return "getKey";
                }
                case 30: {
                    return "importKeyWithMetadata";
                }
                case 29: {
                    return "importKey";
                }
                case 28: {
                    return "generateKeyWithMetadata";
                }
                case 27: {
                    return "generateKey";
                }
                case 26: {
                    return "getKeyChainSnapshot";
                }
                case 25: {
                    return "initRecoveryServiceWithSigFile";
                }
                case 24: {
                    return "hasPendingEscrowToken";
                }
                case 23: {
                    return "getStrongAuthForUser";
                }
                case 22: {
                    return "userPresent";
                }
                case 21: {
                    return "systemReady";
                }
                case 20: {
                    return "requireStrongAuth";
                }
                case 19: {
                    return "unregisterStrongAuthTracker";
                }
                case 18: {
                    return "registerStrongAuthTracker";
                }
                case 17: {
                    return "getSeparateProfileChallengeEnabled";
                }
                case 16: {
                    return "setSeparateProfileChallengeEnabled";
                }
                case 15: {
                    return "getHashFactor";
                }
                case 14: {
                    return "havePassword";
                }
                case 13: {
                    return "havePattern";
                }
                case 12: {
                    return "checkVoldPassword";
                }
                case 11: {
                    return "verifyTiedProfileChallenge";
                }
                case 10: {
                    return "verifyCredential";
                }
                case 9: {
                    return "checkCredential";
                }
                case 8: {
                    return "resetKeyStore";
                }
                case 7: {
                    return "setLockCredential";
                }
                case 6: {
                    return "getString";
                }
                case 5: {
                    return "getLong";
                }
                case 4: {
                    return "getBoolean";
                }
                case 3: {
                    return "setString";
                }
                case 2: {
                    return "setLong";
                }
                case 1: 
            }
            return "setBoolean";
        }

        public static boolean setDefaultImpl(ILockSettings iLockSettings) {
            if (Proxy.sDefaultImpl == null && iLockSettings != null) {
                Proxy.sDefaultImpl = iLockSettings;
                return true;
            }
            return false;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public String getTransactionName(int n) {
            return Stub.getDefaultTransactionName(n);
        }

        @Override
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                boolean bl4 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.closeSession(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.recoverKeyChainSnapshot(((Parcel)object).readString(), ((Parcel)object).createByteArray(), ((Parcel)object).createTypedArrayList(WrappedApplicationKey.CREATOR));
                        parcel.writeNoException();
                        parcel.writeMap((Map)object);
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        String string3 = ((Parcel)object).readString();
                        RecoveryCertPath recoveryCertPath = ((Parcel)object).readInt() != 0 ? RecoveryCertPath.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.startRecoverySessionWithCertPath(string2, string3, recoveryCertPath, ((Parcel)object).createByteArray(), ((Parcel)object).createByteArray(), ((Parcel)object).createTypedArrayList(KeyChainProtectionParams.CREATOR));
                        parcel.writeNoException();
                        parcel.writeByteArray((byte[])object);
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getRecoverySecretTypes();
                        parcel.writeNoException();
                        parcel.writeIntArray((int[])object);
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setRecoverySecretTypes(((Parcel)object).createIntArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getRecoveryStatus();
                        parcel.writeNoException();
                        parcel.writeMap((Map)object);
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setRecoveryStatus(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setServerParams(((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setSnapshotCreatedPendingIntent((PendingIntent)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeKey(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getKey(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.importKeyWithMetadata(((Parcel)object).readString(), ((Parcel)object).createByteArray(), ((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.importKey(((Parcel)object).readString(), ((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.generateKeyWithMetadata(((Parcel)object).readString(), ((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.generateKey(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getKeyChainSnapshot();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((KeyChainSnapshot)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.initRecoveryServiceWithSigFile(((Parcel)object).readString(), ((Parcel)object).createByteArray(), ((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.hasPendingEscrowToken(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getStrongAuthForUser(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.userPresent(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.systemReady();
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.requireStrongAuth(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterStrongAuthTracker(IStrongAuthTracker.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerStrongAuthTracker(IStrongAuthTracker.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getSeparateProfileChallengeEnabled(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.setSeparateProfileChallengeEnabled(n, bl4, ((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getHashFactor(((Parcel)object).createByteArray(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeByteArray((byte[])object);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.havePassword(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.havePattern(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.checkVoldPassword(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.verifyTiedProfileChallenge(((Parcel)object).createByteArray(), ((Parcel)object).readInt(), ((Parcel)object).readLong(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((VerifyCredentialResponse)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.verifyCredential(((Parcel)object).createByteArray(), ((Parcel)object).readInt(), ((Parcel)object).readLong(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((VerifyCredentialResponse)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.checkCredential(((Parcel)object).createByteArray(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ICheckCredentialProgressCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((VerifyCredentialResponse)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.resetKeyStore(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        byte[] arrby = ((Parcel)object).createByteArray();
                        n = ((Parcel)object).readInt();
                        byte[] arrby2 = ((Parcel)object).createByteArray();
                        n2 = ((Parcel)object).readInt();
                        int n3 = ((Parcel)object).readInt();
                        bl4 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.setLockCredential(arrby, n, arrby2, n2, n3, bl4);
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getString(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getLong(((Parcel)object).readString(), ((Parcel)object).readLong(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string4 = ((Parcel)object).readString();
                        bl4 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        n = this.getBoolean(string4, bl4, ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setString(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setLong(((Parcel)object).readString(), ((Parcel)object).readLong(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                String string5 = ((Parcel)object).readString();
                bl4 = bl3;
                if (((Parcel)object).readInt() != 0) {
                    bl4 = true;
                }
                this.setBoolean(string5, bl4, ((Parcel)object).readInt());
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ILockSettings {
            public static ILockSettings sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public VerifyCredentialResponse checkCredential(byte[] object, int n, int n2, ICheckCredentialProgressCallback iCheckCredentialProgressCallback) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block7 : {
                    IBinder iBinder;
                    block6 : {
                        block5 : {
                            parcel2 = Parcel.obtain();
                            parcel = Parcel.obtain();
                            try {
                                parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                                parcel2.writeByteArray((byte[])object);
                                parcel2.writeInt(n);
                                parcel2.writeInt(n2);
                                if (iCheckCredentialProgressCallback == null) break block5;
                            }
                            catch (Throwable throwable) {
                                parcel.recycle();
                                parcel2.recycle();
                                throw throwable;
                            }
                            iBinder = iCheckCredentialProgressCallback.asBinder();
                            break block6;
                        }
                        iBinder = null;
                    }
                    parcel2.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(9, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block7;
                    object = Stub.getDefaultImpl().checkCredential((byte[])object, n, n2, iCheckCredentialProgressCallback);
                    parcel.recycle();
                    parcel2.recycle();
                    return object;
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? VerifyCredentialResponse.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public boolean checkVoldPassword(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(12, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().checkVoldPassword(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void closeSession(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(41, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().closeSession(string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String generateKey(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().generateKey(string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String generateKeyWithMetadata(String string2, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().generateKeyWithMetadata(string2, arrby);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean getBoolean(String string2, boolean bl, int n) throws RemoteException {
                Parcel parcel;
                boolean bl2;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        bl2 = true;
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (this.mRemote.transact(4, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().getBoolean(string2, bl, n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                bl = n != 0 ? bl2 : false;
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public byte[] getHashFactor(byte[] arrby, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray(arrby);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrby = Stub.getDefaultImpl().getHashFactor(arrby, n);
                        return arrby;
                    }
                    parcel2.readException();
                    arrby = parcel2.createByteArray();
                    return arrby;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public String getKey(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getKey(string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public KeyChainSnapshot getKeyChainSnapshot() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(26, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        KeyChainSnapshot keyChainSnapshot = Stub.getDefaultImpl().getKeyChainSnapshot();
                        parcel.recycle();
                        parcel2.recycle();
                        return keyChainSnapshot;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                KeyChainSnapshot keyChainSnapshot = parcel.readInt() != 0 ? KeyChainSnapshot.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return keyChainSnapshot;
            }

            @Override
            public long getLong(String string2, long l, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        l = Stub.getDefaultImpl().getLong(string2, l, n);
                        return l;
                    }
                    parcel2.readException();
                    l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int[] getRecoverySecretTypes() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(38, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int[] arrn = Stub.getDefaultImpl().getRecoverySecretTypes();
                        return arrn;
                    }
                    parcel2.readException();
                    int[] arrn = parcel2.createIntArray();
                    return arrn;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Map getRecoveryStatus() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(36, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Map map = Stub.getDefaultImpl().getRecoveryStatus();
                        return map;
                    }
                    parcel2.readException();
                    HashMap hashMap = parcel2.readHashMap(this.getClass().getClassLoader());
                    return hashMap;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean getSeparateProfileChallengeEnabled(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(17, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().getSeparateProfileChallengeEnabled(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public String getString(String string2, String string3, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getString(string2, string3, n);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getStrongAuthForUser(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getStrongAuthForUser(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean hasPendingEscrowToken(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(24, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasPendingEscrowToken(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean havePassword(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(14, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().havePassword(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean havePattern(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(13, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().havePattern(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public String importKey(String string2, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().importKey(string2, arrby);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String importKeyWithMetadata(String string2, byte[] arrby, byte[] arrby2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeByteArray(arrby);
                    parcel.writeByteArray(arrby2);
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().importKeyWithMetadata(string2, arrby, arrby2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void initRecoveryServiceWithSigFile(String string2, byte[] arrby, byte[] arrby2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeByteArray(arrby);
                    parcel.writeByteArray(arrby2);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().initRecoveryServiceWithSigFile(string2, arrby, arrby2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Map recoverKeyChainSnapshot(String object, byte[] arrby, List<WrappedApplicationKey> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    parcel.writeByteArray(arrby);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().recoverKeyChainSnapshot((String)object, arrby, list);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readHashMap(this.getClass().getClassLoader());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerStrongAuthTracker(IStrongAuthTracker iStrongAuthTracker) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iStrongAuthTracker != null ? iStrongAuthTracker.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerStrongAuthTracker(iStrongAuthTracker);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void removeKey(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeKey(string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void requireStrongAuth(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requireStrongAuth(n, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void resetKeyStore(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resetKeyStore(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setBoolean(String string2, boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setBoolean(string2, bl, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
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
            @Override
            public void setLockCredential(byte[] arrby, int n, byte[] arrby2, int n2, int n3, boolean bl) throws RemoteException {
                Parcel parcel;
                void var1_9;
                Parcel parcel2;
                block16 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeByteArray(arrby);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeByteArray(arrby2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n3);
                        int n4 = bl ? 1 : 0;
                        parcel2.writeInt(n4);
                    }
                    catch (Throwable throwable) {}
                    try {
                        if (!this.mRemote.transact(7, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().setLockCredential(arrby, n, arrby2, n2, n3, bl);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_9;
            }

            @Override
            public void setLong(String string2, long l, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setLong(string2, l, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setRecoverySecretTypes(int[] arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeIntArray(arrn);
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRecoverySecretTypes(arrn);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setRecoveryStatus(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRecoveryStatus(string2, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setSeparateProfileChallengeEnabled(int n, boolean bl, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSeparateProfileChallengeEnabled(n, bl, arrby);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setServerParams(byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setServerParams(arrby);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setSnapshotCreatedPendingIntent(PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSnapshotCreatedPendingIntent(pendingIntent);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setString(String string2, String string3, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setString(string2, string3, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
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
            @Override
            public byte[] startRecoverySessionWithCertPath(String arrby, String string2, RecoveryCertPath recoveryCertPath, byte[] arrby2, byte[] arrby3, List<KeyChainProtectionParams> list) throws RemoteException {
                Parcel parcel;
                void var1_8;
                Parcel parcel2;
                block16 : {
                    block15 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel2.writeString((String)arrby);
                        }
                        catch (Throwable throwable) {
                            break block16;
                        }
                        try {
                            parcel2.writeString(string2);
                            if (recoveryCertPath != null) {
                                parcel2.writeInt(1);
                                recoveryCertPath.writeToParcel(parcel2, 0);
                                break block15;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel2.writeByteArray(arrby2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeByteArray(arrby3);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeTypedList(list);
                        if (!this.mRemote.transact(39, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            arrby = Stub.getDefaultImpl().startRecoverySessionWithCertPath((String)arrby, string2, recoveryCertPath, arrby2, arrby3, list);
                            parcel.recycle();
                            parcel2.recycle();
                            return arrby;
                        }
                        parcel.readException();
                        arrby = parcel.createByteArray();
                        parcel.recycle();
                        parcel2.recycle();
                        return arrby;
                    }
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_8;
            }

            @Override
            public void systemReady() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().systemReady();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void unregisterStrongAuthTracker(IStrongAuthTracker iStrongAuthTracker) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iStrongAuthTracker != null ? iStrongAuthTracker.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterStrongAuthTracker(iStrongAuthTracker);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void userPresent(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().userPresent(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public VerifyCredentialResponse verifyCredential(byte[] object, int n, long l, int n2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeByteArray((byte[])object);
                        parcel2.writeInt(n);
                        parcel2.writeLong(l);
                        parcel2.writeInt(n2);
                        if (this.mRemote.transact(10, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().verifyCredential((byte[])object, n, l, n2);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? VerifyCredentialResponse.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public VerifyCredentialResponse verifyTiedProfileChallenge(byte[] object, int n, long l, int n2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeByteArray((byte[])object);
                        parcel2.writeInt(n);
                        parcel2.writeLong(l);
                        parcel2.writeInt(n2);
                        if (this.mRemote.transact(11, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().verifyTiedProfileChallenge((byte[])object, n, l, n2);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? VerifyCredentialResponse.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }
        }

    }

}

