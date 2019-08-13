/*
 * Decompiled with CFR 0.145.
 */
package android.security;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.StringParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.security.keymaster.KeymasterCertificateChain;
import android.security.keystore.ParcelableKeyGenParameterSpec;
import java.util.ArrayList;
import java.util.List;

public interface IKeyChainService
extends IInterface {
    public int attestKey(String var1, byte[] var2, int[] var3, KeymasterCertificateChain var4) throws RemoteException;

    public boolean containsCaAlias(String var1) throws RemoteException;

    public boolean deleteCaCertificate(String var1) throws RemoteException;

    public int generateKeyPair(String var1, ParcelableKeyGenParameterSpec var2) throws RemoteException;

    public List<String> getCaCertificateChainAliases(String var1, boolean var2) throws RemoteException;

    public byte[] getCaCertificates(String var1) throws RemoteException;

    public byte[] getCertificate(String var1) throws RemoteException;

    public byte[] getEncodedCaCertificate(String var1, boolean var2) throws RemoteException;

    public StringParceledListSlice getSystemCaAliases() throws RemoteException;

    public StringParceledListSlice getUserCaAliases() throws RemoteException;

    public boolean hasGrant(int var1, String var2) throws RemoteException;

    public String installCaCertificate(byte[] var1) throws RemoteException;

    public boolean installKeyPair(byte[] var1, byte[] var2, byte[] var3, String var4) throws RemoteException;

    public boolean isUserSelectable(String var1) throws RemoteException;

    public boolean removeKeyPair(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public String requestPrivateKey(String var1) throws RemoteException;

    public boolean reset() throws RemoteException;

    public void setGrant(int var1, String var2, boolean var3) throws RemoteException;

    public boolean setKeyPairCertificate(String var1, byte[] var2, byte[] var3) throws RemoteException;

    public void setUserSelectable(String var1, boolean var2) throws RemoteException;

    public static class Default
    implements IKeyChainService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public int attestKey(String string2, byte[] arrby, int[] arrn, KeymasterCertificateChain keymasterCertificateChain) throws RemoteException {
            return 0;
        }

        @Override
        public boolean containsCaAlias(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean deleteCaCertificate(String string2) throws RemoteException {
            return false;
        }

        @Override
        public int generateKeyPair(String string2, ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec) throws RemoteException {
            return 0;
        }

        @Override
        public List<String> getCaCertificateChainAliases(String string2, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public byte[] getCaCertificates(String string2) throws RemoteException {
            return null;
        }

        @Override
        public byte[] getCertificate(String string2) throws RemoteException {
            return null;
        }

        @Override
        public byte[] getEncodedCaCertificate(String string2, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public StringParceledListSlice getSystemCaAliases() throws RemoteException {
            return null;
        }

        @Override
        public StringParceledListSlice getUserCaAliases() throws RemoteException {
            return null;
        }

        @Override
        public boolean hasGrant(int n, String string2) throws RemoteException {
            return false;
        }

        @Override
        public String installCaCertificate(byte[] arrby) throws RemoteException {
            return null;
        }

        @Override
        public boolean installKeyPair(byte[] arrby, byte[] arrby2, byte[] arrby3, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isUserSelectable(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean removeKeyPair(String string2) throws RemoteException {
            return false;
        }

        @Override
        public String requestPrivateKey(String string2) throws RemoteException {
            return null;
        }

        @Override
        public boolean reset() throws RemoteException {
            return false;
        }

        @Override
        public void setGrant(int n, String string2, boolean bl) throws RemoteException {
        }

        @Override
        public boolean setKeyPairCertificate(String string2, byte[] arrby, byte[] arrby2) throws RemoteException {
            return false;
        }

        @Override
        public void setUserSelectable(String string2, boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IKeyChainService {
        private static final String DESCRIPTOR = "android.security.IKeyChainService";
        static final int TRANSACTION_attestKey = 7;
        static final int TRANSACTION_containsCaAlias = 16;
        static final int TRANSACTION_deleteCaCertificate = 12;
        static final int TRANSACTION_generateKeyPair = 6;
        static final int TRANSACTION_getCaCertificateChainAliases = 18;
        static final int TRANSACTION_getCaCertificates = 3;
        static final int TRANSACTION_getCertificate = 2;
        static final int TRANSACTION_getEncodedCaCertificate = 17;
        static final int TRANSACTION_getSystemCaAliases = 15;
        static final int TRANSACTION_getUserCaAliases = 14;
        static final int TRANSACTION_hasGrant = 20;
        static final int TRANSACTION_installCaCertificate = 9;
        static final int TRANSACTION_installKeyPair = 10;
        static final int TRANSACTION_isUserSelectable = 4;
        static final int TRANSACTION_removeKeyPair = 11;
        static final int TRANSACTION_requestPrivateKey = 1;
        static final int TRANSACTION_reset = 13;
        static final int TRANSACTION_setGrant = 19;
        static final int TRANSACTION_setKeyPairCertificate = 8;
        static final int TRANSACTION_setUserSelectable = 5;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IKeyChainService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IKeyChainService) {
                return (IKeyChainService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IKeyChainService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 20: {
                    return "hasGrant";
                }
                case 19: {
                    return "setGrant";
                }
                case 18: {
                    return "getCaCertificateChainAliases";
                }
                case 17: {
                    return "getEncodedCaCertificate";
                }
                case 16: {
                    return "containsCaAlias";
                }
                case 15: {
                    return "getSystemCaAliases";
                }
                case 14: {
                    return "getUserCaAliases";
                }
                case 13: {
                    return "reset";
                }
                case 12: {
                    return "deleteCaCertificate";
                }
                case 11: {
                    return "removeKeyPair";
                }
                case 10: {
                    return "installKeyPair";
                }
                case 9: {
                    return "installCaCertificate";
                }
                case 8: {
                    return "setKeyPairCertificate";
                }
                case 7: {
                    return "attestKey";
                }
                case 6: {
                    return "generateKeyPair";
                }
                case 5: {
                    return "setUserSelectable";
                }
                case 4: {
                    return "isUserSelectable";
                }
                case 3: {
                    return "getCaCertificates";
                }
                case 2: {
                    return "getCertificate";
                }
                case 1: 
            }
            return "requestPrivateKey";
        }

        public static boolean setDefaultImpl(IKeyChainService iKeyChainService) {
            if (Proxy.sDefaultImpl == null && iKeyChainService != null) {
                Proxy.sDefaultImpl = iKeyChainService;
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
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.hasGrant(((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string2 = ((Parcel)object).readString();
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.setGrant(n, string2, bl4);
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string3 = ((Parcel)object).readString();
                        bl4 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        object = this.getCaCertificateChainAliases(string3, bl4);
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string4 = ((Parcel)object).readString();
                        bl4 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        object = this.getEncodedCaCertificate(string4, bl4);
                        parcel.writeNoException();
                        parcel.writeByteArray((byte[])object);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.containsCaAlias(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSystemCaAliases();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((StringParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getUserCaAliases();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((StringParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.reset() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.deleteCaCertificate(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.removeKeyPair(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.installKeyPair(((Parcel)object).createByteArray(), ((Parcel)object).createByteArray(), ((Parcel)object).createByteArray(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.installCaCertificate(((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setKeyPairCertificate(((Parcel)object).readString(), ((Parcel)object).createByteArray(), ((Parcel)object).createByteArray()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string5 = ((Parcel)object).readString();
                        byte[] arrby = ((Parcel)object).createByteArray();
                        int[] arrn = ((Parcel)object).createIntArray();
                        object = new KeymasterCertificateChain();
                        n = this.attestKey(string5, arrby, arrn, (KeymasterCertificateChain)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        parcel.writeInt(1);
                        ((KeymasterCertificateChain)object).writeToParcel(parcel, 1);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string6 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? ParcelableKeyGenParameterSpec.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.generateKeyPair(string6, (ParcelableKeyGenParameterSpec)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string7 = ((Parcel)object).readString();
                        bl4 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.setUserSelectable(string7, bl4);
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isUserSelectable(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCaCertificates(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeByteArray((byte[])object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCertificate(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeByteArray((byte[])object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.requestPrivateKey(((Parcel)object).readString());
                parcel.writeNoException();
                parcel.writeString((String)object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IKeyChainService {
            public static IKeyChainService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public int attestKey(String string2, byte[] arrby, int[] arrn, KeymasterCertificateChain keymasterCertificateChain) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeByteArray(arrby);
                    parcel.writeIntArray(arrn);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().attestKey(string2, arrby, arrn, keymasterCertificateChain);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (parcel2.readInt() != 0) {
                        keymasterCertificateChain.readFromParcel(parcel2);
                    }
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean containsCaAlias(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(16, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().containsCaAlias(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean deleteCaCertificate(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(12, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().deleteCaCertificate(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public int generateKeyPair(String string2, ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (parcelableKeyGenParameterSpec != null) {
                        parcel.writeInt(1);
                        parcelableKeyGenParameterSpec.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().generateKeyPair(string2, parcelableKeyGenParameterSpec);
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
            public List<String> getCaCertificateChainAliases(String list, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString((String)((Object)list));
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        list = Stub.getDefaultImpl().getCaCertificateChainAliases((String)((Object)list), bl);
                        return list;
                    }
                    parcel2.readException();
                    list = parcel2.createStringArrayList();
                    return list;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public byte[] getCaCertificates(String arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrby);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrby = Stub.getDefaultImpl().getCaCertificates((String)arrby);
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

            @Override
            public byte[] getCertificate(String arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrby);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrby = Stub.getDefaultImpl().getCertificate((String)arrby);
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

            @Override
            public byte[] getEncodedCaCertificate(String arrby, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString((String)arrby);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrby = Stub.getDefaultImpl().getEncodedCaCertificate((String)arrby, bl);
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
            public StringParceledListSlice getSystemCaAliases() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(15, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        StringParceledListSlice stringParceledListSlice = Stub.getDefaultImpl().getSystemCaAliases();
                        parcel.recycle();
                        parcel2.recycle();
                        return stringParceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                StringParceledListSlice stringParceledListSlice = parcel.readInt() != 0 ? (StringParceledListSlice)StringParceledListSlice.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return stringParceledListSlice;
            }

            @Override
            public StringParceledListSlice getUserCaAliases() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(14, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        StringParceledListSlice stringParceledListSlice = Stub.getDefaultImpl().getUserCaAliases();
                        parcel.recycle();
                        parcel2.recycle();
                        return stringParceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                StringParceledListSlice stringParceledListSlice = parcel.readInt() != 0 ? (StringParceledListSlice)StringParceledListSlice.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return stringParceledListSlice;
            }

            @Override
            public boolean hasGrant(int n, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(20, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasGrant(n, string2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public String installCaCertificate(byte[] object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray((byte[])object);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().installCaCertificate((byte[])object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readString();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean installKeyPair(byte[] arrby, byte[] arrby2, byte[] arrby3, String string2) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeByteArray(arrby);
                        parcel2.writeByteArray(arrby2);
                        parcel2.writeByteArray(arrby3);
                        parcel2.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(10, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().installKeyPair(arrby, arrby2, arrby3, string2);
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
            public boolean isUserSelectable(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(4, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isUserSelectable(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean removeKeyPair(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(11, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().removeKeyPair(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public String requestPrivateKey(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().requestPrivateKey(string2);
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
            public boolean reset() throws RemoteException {
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
                    if (iBinder.transact(13, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().reset();
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
            public void setGrant(int n, String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeString(string2);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setGrant(n, string2, bl);
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
            public boolean setKeyPairCertificate(String string2, byte[] arrby, byte[] arrby2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        parcel.writeByteArray(arrby);
                        parcel.writeByteArray(arrby2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(8, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setKeyPairCertificate(string2, arrby, arrby2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void setUserSelectable(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUserSelectable(string2, bl);
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
        }

    }

}

