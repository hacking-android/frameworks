/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.security.keymaster.KeymasterArguments;
import android.security.keymaster.KeymasterBlob;
import android.security.keystore.IKeystoreCertificateChainCallback;
import android.security.keystore.IKeystoreExportKeyCallback;
import android.security.keystore.IKeystoreKeyCharacteristicsCallback;
import android.security.keystore.IKeystoreOperationResultCallback;
import android.security.keystore.IKeystoreResponseCallback;
import java.util.ArrayList;
import java.util.List;

public interface IKeystoreService
extends IInterface {
    public int abort(IKeystoreResponseCallback var1, IBinder var2) throws RemoteException;

    public int addAuthToken(byte[] var1) throws RemoteException;

    public int addRngEntropy(IKeystoreResponseCallback var1, byte[] var2, int var3) throws RemoteException;

    public int attestDeviceIds(IKeystoreCertificateChainCallback var1, KeymasterArguments var2) throws RemoteException;

    public int attestKey(IKeystoreCertificateChainCallback var1, String var2, KeymasterArguments var3) throws RemoteException;

    public int begin(IKeystoreOperationResultCallback var1, IBinder var2, String var3, int var4, boolean var5, KeymasterArguments var6, byte[] var7, int var8) throws RemoteException;

    public int cancelConfirmationPrompt(IBinder var1) throws RemoteException;

    public int clear_uid(long var1) throws RemoteException;

    public int del(String var1, int var2) throws RemoteException;

    public int exist(String var1, int var2) throws RemoteException;

    public int exportKey(IKeystoreExportKeyCallback var1, String var2, int var3, KeymasterBlob var4, KeymasterBlob var5, int var6) throws RemoteException;

    public int finish(IKeystoreOperationResultCallback var1, IBinder var2, KeymasterArguments var3, byte[] var4, byte[] var5) throws RemoteException;

    public int generateKey(IKeystoreKeyCharacteristicsCallback var1, String var2, KeymasterArguments var3, byte[] var4, int var5, int var6) throws RemoteException;

    public byte[] get(String var1, int var2) throws RemoteException;

    public int getKeyCharacteristics(IKeystoreKeyCharacteristicsCallback var1, String var2, KeymasterBlob var3, KeymasterBlob var4, int var5) throws RemoteException;

    public int getState(int var1) throws RemoteException;

    public long getmtime(String var1, int var2) throws RemoteException;

    public String grant(String var1, int var2) throws RemoteException;

    public int importKey(IKeystoreKeyCharacteristicsCallback var1, String var2, KeymasterArguments var3, int var4, byte[] var5, int var6, int var7) throws RemoteException;

    public int importWrappedKey(IKeystoreKeyCharacteristicsCallback var1, String var2, byte[] var3, String var4, byte[] var5, KeymasterArguments var6, long var7, long var9) throws RemoteException;

    public int insert(String var1, byte[] var2, int var3, int var4) throws RemoteException;

    public boolean isConfirmationPromptSupported() throws RemoteException;

    public int isEmpty(int var1) throws RemoteException;

    public int is_hardware_backed(String var1) throws RemoteException;

    public String[] list(String var1, int var2) throws RemoteException;

    public int listUidsOfAuthBoundKeys(List<String> var1) throws RemoteException;

    public int lock(int var1) throws RemoteException;

    public int onDeviceOffBody() throws RemoteException;

    public int onKeyguardVisibilityChanged(boolean var1, int var2) throws RemoteException;

    public int onUserAdded(int var1, int var2) throws RemoteException;

    public int onUserPasswordChanged(int var1, String var2) throws RemoteException;

    public int onUserRemoved(int var1) throws RemoteException;

    public int presentConfirmationPrompt(IBinder var1, String var2, byte[] var3, String var4, int var5) throws RemoteException;

    public int reset() throws RemoteException;

    public int ungrant(String var1, int var2) throws RemoteException;

    public int unlock(int var1, String var2) throws RemoteException;

    public int update(IKeystoreOperationResultCallback var1, IBinder var2, KeymasterArguments var3, byte[] var4) throws RemoteException;

    public static class Default
    implements IKeystoreService {
        @Override
        public int abort(IKeystoreResponseCallback iKeystoreResponseCallback, IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override
        public int addAuthToken(byte[] arrby) throws RemoteException {
            return 0;
        }

        @Override
        public int addRngEntropy(IKeystoreResponseCallback iKeystoreResponseCallback, byte[] arrby, int n) throws RemoteException {
            return 0;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public int attestDeviceIds(IKeystoreCertificateChainCallback iKeystoreCertificateChainCallback, KeymasterArguments keymasterArguments) throws RemoteException {
            return 0;
        }

        @Override
        public int attestKey(IKeystoreCertificateChainCallback iKeystoreCertificateChainCallback, String string2, KeymasterArguments keymasterArguments) throws RemoteException {
            return 0;
        }

        @Override
        public int begin(IKeystoreOperationResultCallback iKeystoreOperationResultCallback, IBinder iBinder, String string2, int n, boolean bl, KeymasterArguments keymasterArguments, byte[] arrby, int n2) throws RemoteException {
            return 0;
        }

        @Override
        public int cancelConfirmationPrompt(IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override
        public int clear_uid(long l) throws RemoteException {
            return 0;
        }

        @Override
        public int del(String string2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public int exist(String string2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public int exportKey(IKeystoreExportKeyCallback iKeystoreExportKeyCallback, String string2, int n, KeymasterBlob keymasterBlob, KeymasterBlob keymasterBlob2, int n2) throws RemoteException {
            return 0;
        }

        @Override
        public int finish(IKeystoreOperationResultCallback iKeystoreOperationResultCallback, IBinder iBinder, KeymasterArguments keymasterArguments, byte[] arrby, byte[] arrby2) throws RemoteException {
            return 0;
        }

        @Override
        public int generateKey(IKeystoreKeyCharacteristicsCallback iKeystoreKeyCharacteristicsCallback, String string2, KeymasterArguments keymasterArguments, byte[] arrby, int n, int n2) throws RemoteException {
            return 0;
        }

        @Override
        public byte[] get(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public int getKeyCharacteristics(IKeystoreKeyCharacteristicsCallback iKeystoreKeyCharacteristicsCallback, String string2, KeymasterBlob keymasterBlob, KeymasterBlob keymasterBlob2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public int getState(int n) throws RemoteException {
            return 0;
        }

        @Override
        public long getmtime(String string2, int n) throws RemoteException {
            return 0L;
        }

        @Override
        public String grant(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public int importKey(IKeystoreKeyCharacteristicsCallback iKeystoreKeyCharacteristicsCallback, String string2, KeymasterArguments keymasterArguments, int n, byte[] arrby, int n2, int n3) throws RemoteException {
            return 0;
        }

        @Override
        public int importWrappedKey(IKeystoreKeyCharacteristicsCallback iKeystoreKeyCharacteristicsCallback, String string2, byte[] arrby, String string3, byte[] arrby2, KeymasterArguments keymasterArguments, long l, long l2) throws RemoteException {
            return 0;
        }

        @Override
        public int insert(String string2, byte[] arrby, int n, int n2) throws RemoteException {
            return 0;
        }

        @Override
        public boolean isConfirmationPromptSupported() throws RemoteException {
            return false;
        }

        @Override
        public int isEmpty(int n) throws RemoteException {
            return 0;
        }

        @Override
        public int is_hardware_backed(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public String[] list(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public int listUidsOfAuthBoundKeys(List<String> list) throws RemoteException {
            return 0;
        }

        @Override
        public int lock(int n) throws RemoteException {
            return 0;
        }

        @Override
        public int onDeviceOffBody() throws RemoteException {
            return 0;
        }

        @Override
        public int onKeyguardVisibilityChanged(boolean bl, int n) throws RemoteException {
            return 0;
        }

        @Override
        public int onUserAdded(int n, int n2) throws RemoteException {
            return 0;
        }

        @Override
        public int onUserPasswordChanged(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int onUserRemoved(int n) throws RemoteException {
            return 0;
        }

        @Override
        public int presentConfirmationPrompt(IBinder iBinder, String string2, byte[] arrby, String string3, int n) throws RemoteException {
            return 0;
        }

        @Override
        public int reset() throws RemoteException {
            return 0;
        }

        @Override
        public int ungrant(String string2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public int unlock(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public int update(IKeystoreOperationResultCallback iKeystoreOperationResultCallback, IBinder iBinder, KeymasterArguments keymasterArguments, byte[] arrby) throws RemoteException {
            return 0;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IKeystoreService {
        private static final String DESCRIPTOR = "android.security.keystore.IKeystoreService";
        static final int TRANSACTION_abort = 25;
        static final int TRANSACTION_addAuthToken = 26;
        static final int TRANSACTION_addRngEntropy = 17;
        static final int TRANSACTION_attestDeviceIds = 30;
        static final int TRANSACTION_attestKey = 29;
        static final int TRANSACTION_begin = 22;
        static final int TRANSACTION_cancelConfirmationPrompt = 34;
        static final int TRANSACTION_clear_uid = 16;
        static final int TRANSACTION_del = 4;
        static final int TRANSACTION_exist = 5;
        static final int TRANSACTION_exportKey = 21;
        static final int TRANSACTION_finish = 24;
        static final int TRANSACTION_generateKey = 18;
        static final int TRANSACTION_get = 2;
        static final int TRANSACTION_getKeyCharacteristics = 19;
        static final int TRANSACTION_getState = 1;
        static final int TRANSACTION_getmtime = 14;
        static final int TRANSACTION_grant = 12;
        static final int TRANSACTION_importKey = 20;
        static final int TRANSACTION_importWrappedKey = 32;
        static final int TRANSACTION_insert = 3;
        static final int TRANSACTION_isConfirmationPromptSupported = 35;
        static final int TRANSACTION_isEmpty = 11;
        static final int TRANSACTION_is_hardware_backed = 15;
        static final int TRANSACTION_list = 6;
        static final int TRANSACTION_listUidsOfAuthBoundKeys = 37;
        static final int TRANSACTION_lock = 9;
        static final int TRANSACTION_onDeviceOffBody = 31;
        static final int TRANSACTION_onKeyguardVisibilityChanged = 36;
        static final int TRANSACTION_onUserAdded = 27;
        static final int TRANSACTION_onUserPasswordChanged = 8;
        static final int TRANSACTION_onUserRemoved = 28;
        static final int TRANSACTION_presentConfirmationPrompt = 33;
        static final int TRANSACTION_reset = 7;
        static final int TRANSACTION_ungrant = 13;
        static final int TRANSACTION_unlock = 10;
        static final int TRANSACTION_update = 23;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IKeystoreService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IKeystoreService) {
                return (IKeystoreService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IKeystoreService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 37: {
                    return "listUidsOfAuthBoundKeys";
                }
                case 36: {
                    return "onKeyguardVisibilityChanged";
                }
                case 35: {
                    return "isConfirmationPromptSupported";
                }
                case 34: {
                    return "cancelConfirmationPrompt";
                }
                case 33: {
                    return "presentConfirmationPrompt";
                }
                case 32: {
                    return "importWrappedKey";
                }
                case 31: {
                    return "onDeviceOffBody";
                }
                case 30: {
                    return "attestDeviceIds";
                }
                case 29: {
                    return "attestKey";
                }
                case 28: {
                    return "onUserRemoved";
                }
                case 27: {
                    return "onUserAdded";
                }
                case 26: {
                    return "addAuthToken";
                }
                case 25: {
                    return "abort";
                }
                case 24: {
                    return "finish";
                }
                case 23: {
                    return "update";
                }
                case 22: {
                    return "begin";
                }
                case 21: {
                    return "exportKey";
                }
                case 20: {
                    return "importKey";
                }
                case 19: {
                    return "getKeyCharacteristics";
                }
                case 18: {
                    return "generateKey";
                }
                case 17: {
                    return "addRngEntropy";
                }
                case 16: {
                    return "clear_uid";
                }
                case 15: {
                    return "is_hardware_backed";
                }
                case 14: {
                    return "getmtime";
                }
                case 13: {
                    return "ungrant";
                }
                case 12: {
                    return "grant";
                }
                case 11: {
                    return "isEmpty";
                }
                case 10: {
                    return "unlock";
                }
                case 9: {
                    return "lock";
                }
                case 8: {
                    return "onUserPasswordChanged";
                }
                case 7: {
                    return "reset";
                }
                case 6: {
                    return "list";
                }
                case 5: {
                    return "exist";
                }
                case 4: {
                    return "del";
                }
                case 3: {
                    return "insert";
                }
                case 2: {
                    return "get";
                }
                case 1: 
            }
            return "getState";
        }

        public static boolean setDefaultImpl(IKeystoreService iKeystoreService) {
            if (Proxy.sDefaultImpl == null && iKeystoreService != null) {
                Proxy.sDefaultImpl = iKeystoreService;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = new ArrayList();
                        n = this.listUidsOfAuthBoundKeys((List<String>)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        n = this.onKeyguardVisibilityChanged(bl, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isConfirmationPromptSupported() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.cancelConfirmationPrompt(((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.presentConfirmationPrompt(((Parcel)object).readStrongBinder(), ((Parcel)object).readString(), ((Parcel)object).createByteArray(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IKeystoreKeyCharacteristicsCallback iKeystoreKeyCharacteristicsCallback = IKeystoreKeyCharacteristicsCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string2 = ((Parcel)object).readString();
                        byte[] arrby = ((Parcel)object).createByteArray();
                        String string3 = ((Parcel)object).readString();
                        byte[] arrby2 = ((Parcel)object).createByteArray();
                        KeymasterArguments keymasterArguments = ((Parcel)object).readInt() != 0 ? KeymasterArguments.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.importWrappedKey(iKeystoreKeyCharacteristicsCallback, string2, arrby, string3, arrby2, keymasterArguments, ((Parcel)object).readLong(), ((Parcel)object).readLong());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.onDeviceOffBody();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IKeystoreCertificateChainCallback iKeystoreCertificateChainCallback = IKeystoreCertificateChainCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? KeymasterArguments.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.attestDeviceIds(iKeystoreCertificateChainCallback, (KeymasterArguments)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IKeystoreCertificateChainCallback iKeystoreCertificateChainCallback = IKeystoreCertificateChainCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string4 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? KeymasterArguments.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.attestKey(iKeystoreCertificateChainCallback, string4, (KeymasterArguments)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.onUserRemoved(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.onUserAdded(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.addAuthToken(((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.abort(IKeystoreResponseCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IKeystoreOperationResultCallback iKeystoreOperationResultCallback = IKeystoreOperationResultCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        KeymasterArguments keymasterArguments = ((Parcel)object).readInt() != 0 ? KeymasterArguments.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.finish(iKeystoreOperationResultCallback, iBinder, keymasterArguments, ((Parcel)object).createByteArray(), ((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IKeystoreOperationResultCallback iKeystoreOperationResultCallback = IKeystoreOperationResultCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        KeymasterArguments keymasterArguments = ((Parcel)object).readInt() != 0 ? KeymasterArguments.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.update(iKeystoreOperationResultCallback, iBinder, keymasterArguments, ((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IKeystoreOperationResultCallback iKeystoreOperationResultCallback = IKeystoreOperationResultCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        String string5 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        bl = ((Parcel)object).readInt() != 0;
                        KeymasterArguments keymasterArguments = ((Parcel)object).readInt() != 0 ? KeymasterArguments.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.begin(iKeystoreOperationResultCallback, iBinder, string5, n, bl, keymasterArguments, ((Parcel)object).createByteArray(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IKeystoreExportKeyCallback iKeystoreExportKeyCallback = IKeystoreExportKeyCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string6 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        KeymasterBlob keymasterBlob = ((Parcel)object).readInt() != 0 ? KeymasterBlob.CREATOR.createFromParcel((Parcel)object) : null;
                        KeymasterBlob keymasterBlob2 = ((Parcel)object).readInt() != 0 ? KeymasterBlob.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.exportKey(iKeystoreExportKeyCallback, string6, n, keymasterBlob, keymasterBlob2, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IKeystoreKeyCharacteristicsCallback iKeystoreKeyCharacteristicsCallback = IKeystoreKeyCharacteristicsCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string7 = ((Parcel)object).readString();
                        KeymasterArguments keymasterArguments = ((Parcel)object).readInt() != 0 ? KeymasterArguments.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.importKey(iKeystoreKeyCharacteristicsCallback, string7, keymasterArguments, ((Parcel)object).readInt(), ((Parcel)object).createByteArray(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IKeystoreKeyCharacteristicsCallback iKeystoreKeyCharacteristicsCallback = IKeystoreKeyCharacteristicsCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string8 = ((Parcel)object).readString();
                        KeymasterBlob keymasterBlob = ((Parcel)object).readInt() != 0 ? KeymasterBlob.CREATOR.createFromParcel((Parcel)object) : null;
                        KeymasterBlob keymasterBlob3 = ((Parcel)object).readInt() != 0 ? KeymasterBlob.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getKeyCharacteristics(iKeystoreKeyCharacteristicsCallback, string8, keymasterBlob, keymasterBlob3, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IKeystoreKeyCharacteristicsCallback iKeystoreKeyCharacteristicsCallback = IKeystoreKeyCharacteristicsCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string9 = ((Parcel)object).readString();
                        KeymasterArguments keymasterArguments = ((Parcel)object).readInt() != 0 ? KeymasterArguments.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.generateKey(iKeystoreKeyCharacteristicsCallback, string9, keymasterArguments, ((Parcel)object).createByteArray(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.addRngEntropy(IKeystoreResponseCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).createByteArray(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.clear_uid(((Parcel)object).readLong());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.is_hardware_backed(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getmtime(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.ungrant(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.grant(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isEmpty(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.unlock(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.lock(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.onUserPasswordChanged(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.reset();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.list(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.exist(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.del(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.insert(((Parcel)object).readString(), ((Parcel)object).createByteArray(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.get(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeByteArray((byte[])object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = this.getState(((Parcel)object).readInt());
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IKeystoreService {
            public static IKeystoreService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int abort(IKeystoreResponseCallback iKeystoreResponseCallback, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder2 = iKeystoreResponseCallback != null ? iKeystoreResponseCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().abort(iKeystoreResponseCallback, iBinder);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int addAuthToken(byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().addAuthToken(arrby);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
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
            public int addRngEntropy(IKeystoreResponseCallback iKeystoreResponseCallback, byte[] arrby, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iKeystoreResponseCallback != null ? iKeystoreResponseCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeByteArray(arrby);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().addRngEntropy(iKeystoreResponseCallback, arrby, n);
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
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int attestDeviceIds(IKeystoreCertificateChainCallback iKeystoreCertificateChainCallback, KeymasterArguments keymasterArguments) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iKeystoreCertificateChainCallback != null ? iKeystoreCertificateChainCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (keymasterArguments != null) {
                        parcel.writeInt(1);
                        keymasterArguments.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().attestDeviceIds(iKeystoreCertificateChainCallback, keymasterArguments);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
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
            public int attestKey(IKeystoreCertificateChainCallback iKeystoreCertificateChainCallback, String string2, KeymasterArguments keymasterArguments) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iKeystoreCertificateChainCallback != null ? iKeystoreCertificateChainCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (keymasterArguments != null) {
                        parcel.writeInt(1);
                        keymasterArguments.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().attestKey(iKeystoreCertificateChainCallback, string2, keymasterArguments);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
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
            public int begin(IKeystoreOperationResultCallback iKeystoreOperationResultCallback, IBinder iBinder, String string2, int n, boolean bl, KeymasterArguments keymasterArguments, byte[] arrby, int n2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var1_6;
                block12 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder2 = iKeystoreOperationResultCallback != null ? iKeystoreOperationResultCallback.asBinder() : null;
                    parcel2.writeStrongBinder(iBinder2);
                    try {
                        parcel2.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeInt(n);
                        int n3 = bl ? 1 : 0;
                        parcel2.writeInt(n3);
                        if (keymasterArguments != null) {
                            parcel2.writeInt(1);
                            keymasterArguments.writeToParcel(parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        parcel2.writeByteArray(arrby);
                        parcel2.writeInt(n2);
                        if (!this.mRemote.transact(22, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            n = Stub.getDefaultImpl().begin(iKeystoreOperationResultCallback, iBinder, string2, n, bl, keymasterArguments, arrby, n2);
                            parcel.recycle();
                            parcel2.recycle();
                            return n;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        parcel.recycle();
                        parcel2.recycle();
                        return n;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_6;
            }

            @Override
            public int cancelConfirmationPrompt(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().cancelConfirmationPrompt(iBinder);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int clear_uid(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().clear_uid(l);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int del(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().del(string2, n);
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
            public int exist(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().exist(string2, n);
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

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public int exportKey(IKeystoreExportKeyCallback iKeystoreExportKeyCallback, String string2, int n, KeymasterBlob keymasterBlob, KeymasterBlob keymasterBlob2, int n2) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block16 : {
                    block15 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        IBinder iBinder = iKeystoreExportKeyCallback != null ? iKeystoreExportKeyCallback.asBinder() : null;
                        parcel2.writeStrongBinder(iBinder);
                        try {
                            parcel2.writeString(string2);
                        }
                        catch (Throwable throwable) {
                            break block16;
                        }
                        try {
                            parcel2.writeInt(n);
                            if (keymasterBlob != null) {
                                parcel2.writeInt(1);
                                keymasterBlob.writeToParcel(parcel2, 0);
                            } else {
                                parcel2.writeInt(0);
                            }
                            if (keymasterBlob2 != null) {
                                parcel2.writeInt(1);
                                keymasterBlob2.writeToParcel(parcel2, 0);
                                break block15;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        if (!this.mRemote.transact(21, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            n = Stub.getDefaultImpl().exportKey(iKeystoreExportKeyCallback, string2, n, keymasterBlob, keymasterBlob2, n2);
                            parcel.recycle();
                            parcel2.recycle();
                            return n;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        parcel.recycle();
                        parcel2.recycle();
                        return n;
                    }
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_7;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int finish(IKeystoreOperationResultCallback iKeystoreOperationResultCallback, IBinder iBinder, KeymasterArguments keymasterArguments, byte[] arrby, byte[] arrby2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder2 = iKeystoreOperationResultCallback != null ? iKeystoreOperationResultCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    parcel.writeStrongBinder(iBinder);
                    if (keymasterArguments != null) {
                        parcel.writeInt(1);
                        keymasterArguments.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeByteArray(arrby);
                    parcel.writeByteArray(arrby2);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().finish(iKeystoreOperationResultCallback, iBinder, keymasterArguments, arrby, arrby2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
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
            public int generateKey(IKeystoreKeyCharacteristicsCallback iKeystoreKeyCharacteristicsCallback, String string2, KeymasterArguments keymasterArguments, byte[] arrby, int n, int n2) throws RemoteException {
                Parcel parcel;
                void var1_8;
                Parcel parcel2;
                block16 : {
                    block15 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        IBinder iBinder = iKeystoreKeyCharacteristicsCallback != null ? iKeystoreKeyCharacteristicsCallback.asBinder() : null;
                        parcel2.writeStrongBinder(iBinder);
                        try {
                            parcel2.writeString(string2);
                            if (keymasterArguments != null) {
                                parcel2.writeInt(1);
                                keymasterArguments.writeToParcel(parcel2, 0);
                                break block15;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
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
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        if (!this.mRemote.transact(18, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            n = Stub.getDefaultImpl().generateKey(iKeystoreKeyCharacteristicsCallback, string2, keymasterArguments, arrby, n, n2);
                            parcel.recycle();
                            parcel2.recycle();
                            return n;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        parcel.recycle();
                        parcel2.recycle();
                        return n;
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
            public byte[] get(String arrby, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrby);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrby = Stub.getDefaultImpl().get((String)arrby, n);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int getKeyCharacteristics(IKeystoreKeyCharacteristicsCallback iKeystoreKeyCharacteristicsCallback, String string2, KeymasterBlob keymasterBlob, KeymasterBlob keymasterBlob2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iKeystoreKeyCharacteristicsCallback != null ? iKeystoreKeyCharacteristicsCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (keymasterBlob != null) {
                        parcel.writeInt(1);
                        keymasterBlob.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (keymasterBlob2 != null) {
                        parcel.writeInt(1);
                        keymasterBlob2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getKeyCharacteristics(iKeystoreKeyCharacteristicsCallback, string2, keymasterBlob, keymasterBlob2, n);
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
            public int getState(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getState(n);
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
            public long getmtime(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getmtime(string2, n);
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String grant(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().grant(string2, n);
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

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public int importKey(IKeystoreKeyCharacteristicsCallback iKeystoreKeyCharacteristicsCallback, String string2, KeymasterArguments keymasterArguments, int n, byte[] arrby, int n2, int n3) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block14 : {
                    block13 : {
                        parcel = Parcel.obtain();
                        parcel2 = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        IBinder iBinder = iKeystoreKeyCharacteristicsCallback != null ? iKeystoreKeyCharacteristicsCallback.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                        try {
                            parcel.writeString(string2);
                            if (keymasterArguments != null) {
                                parcel.writeInt(1);
                                keymasterArguments.writeToParcel(parcel, 0);
                                break block13;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeByteArray(arrby);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            n = Stub.getDefaultImpl().importKey(iKeystoreKeyCharacteristicsCallback, string2, keymasterArguments, n, arrby, n2, n3);
                            parcel2.recycle();
                            parcel.recycle();
                            return n;
                        }
                        parcel2.readException();
                        n = parcel2.readInt();
                        parcel2.recycle();
                        parcel.recycle();
                        return n;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_7;
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
            public int importWrappedKey(IKeystoreKeyCharacteristicsCallback iKeystoreKeyCharacteristicsCallback, String string2, byte[] arrby, String string3, byte[] arrby2, KeymasterArguments keymasterArguments, long l, long l2) throws RemoteException {
                Parcel parcel2;
                void var1_4;
                Parcel parcel;
                block8 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iKeystoreKeyCharacteristicsCallback != null ? iKeystoreKeyCharacteristicsCallback.asBinder() : null;
                    parcel2.writeStrongBinder(iBinder);
                    try {
                        parcel2.writeString(string2);
                        parcel2.writeByteArray(arrby);
                        parcel2.writeString(string3);
                        parcel2.writeByteArray(arrby2);
                        if (keymasterArguments != null) {
                            parcel2.writeInt(1);
                            keymasterArguments.writeToParcel(parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        parcel2.writeLong(l);
                        parcel2.writeLong(l2);
                        if (!this.mRemote.transact(32, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            int n = Stub.getDefaultImpl().importWrappedKey(iKeystoreKeyCharacteristicsCallback, string2, arrby, string3, arrby2, keymasterArguments, l, l2);
                            parcel.recycle();
                            parcel2.recycle();
                            return n;
                        }
                        parcel.readException();
                        int n = parcel.readInt();
                        parcel.recycle();
                        parcel2.recycle();
                        return n;
                    }
                    catch (Throwable throwable) {}
                    break block8;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_4;
            }

            @Override
            public int insert(String string2, byte[] arrby, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeByteArray(arrby);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().insert(string2, arrby, n, n2);
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
            public boolean isConfirmationPromptSupported() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(35, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isConfirmationPromptSupported();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public int isEmpty(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().isEmpty(n);
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
            public int is_hardware_backed(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().is_hardware_backed(string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] list(String arrstring, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrstring);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrstring = Stub.getDefaultImpl().list((String)arrstring, n);
                        return arrstring;
                    }
                    parcel2.readException();
                    arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int listUidsOfAuthBoundKeys(List<String> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().listUidsOfAuthBoundKeys(list);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    parcel2.readStringList(list);
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int lock(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().lock(n);
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
            public int onDeviceOffBody() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().onDeviceOffBody();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int onKeyguardVisibilityChanged(boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(36, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().onKeyguardVisibilityChanged(bl, n);
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
            public int onUserAdded(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().onUserAdded(n, n2);
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
            public int onUserPasswordChanged(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().onUserPasswordChanged(n, string2);
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
            public int onUserRemoved(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().onUserRemoved(n);
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
            public int presentConfirmationPrompt(IBinder iBinder, String string2, byte[] arrby, String string3, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeByteArray(arrby);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().presentConfirmationPrompt(iBinder, string2, arrby, string3, n);
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
            public int reset() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().reset();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int ungrant(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().ungrant(string2, n);
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
            public int unlock(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().unlock(n, string2);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int update(IKeystoreOperationResultCallback iKeystoreOperationResultCallback, IBinder iBinder, KeymasterArguments keymasterArguments, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder2 = iKeystoreOperationResultCallback != null ? iKeystoreOperationResultCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    parcel.writeStrongBinder(iBinder);
                    if (keymasterArguments != null) {
                        parcel.writeInt(1);
                        keymasterArguments.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().update(iKeystoreOperationResultCallback, iBinder, keymasterArguments, arrby);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

