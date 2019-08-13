/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.net.INetworkPolicyListener;
import android.net.NetworkPolicy;
import android.net.NetworkQuotaInfo;
import android.net.NetworkState;
import android.net.NetworkTemplate;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.SubscriptionPlan;

public interface INetworkPolicyManager
extends IInterface {
    public void addUidPolicy(int var1, int var2) throws RemoteException;

    public void factoryReset(String var1) throws RemoteException;

    public NetworkPolicy[] getNetworkPolicies(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public NetworkQuotaInfo getNetworkQuotaInfo(NetworkState var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean getRestrictBackground() throws RemoteException;

    public int getRestrictBackgroundByCaller() throws RemoteException;

    public SubscriptionPlan[] getSubscriptionPlans(int var1, String var2) throws RemoteException;

    public String getSubscriptionPlansOwner(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public int getUidPolicy(int var1) throws RemoteException;

    public int[] getUidsWithPolicy(int var1) throws RemoteException;

    public boolean isUidNetworkingBlocked(int var1, boolean var2) throws RemoteException;

    public void onTetheringChanged(String var1, boolean var2) throws RemoteException;

    public void registerListener(INetworkPolicyListener var1) throws RemoteException;

    public void removeUidPolicy(int var1, int var2) throws RemoteException;

    public void setDeviceIdleMode(boolean var1) throws RemoteException;

    @UnsupportedAppUsage
    public void setNetworkPolicies(NetworkPolicy[] var1) throws RemoteException;

    @UnsupportedAppUsage
    public void setRestrictBackground(boolean var1) throws RemoteException;

    public void setSubscriptionOverride(int var1, int var2, int var3, long var4, String var6) throws RemoteException;

    public void setSubscriptionPlans(int var1, SubscriptionPlan[] var2, String var3) throws RemoteException;

    @UnsupportedAppUsage
    public void setUidPolicy(int var1, int var2) throws RemoteException;

    public void setWifiMeteredOverride(String var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public void snoozeLimit(NetworkTemplate var1) throws RemoteException;

    public void unregisterListener(INetworkPolicyListener var1) throws RemoteException;

    public static class Default
    implements INetworkPolicyManager {
        @Override
        public void addUidPolicy(int n, int n2) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void factoryReset(String string2) throws RemoteException {
        }

        @Override
        public NetworkPolicy[] getNetworkPolicies(String string2) throws RemoteException {
            return null;
        }

        @Override
        public NetworkQuotaInfo getNetworkQuotaInfo(NetworkState networkState) throws RemoteException {
            return null;
        }

        @Override
        public boolean getRestrictBackground() throws RemoteException {
            return false;
        }

        @Override
        public int getRestrictBackgroundByCaller() throws RemoteException {
            return 0;
        }

        @Override
        public SubscriptionPlan[] getSubscriptionPlans(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getSubscriptionPlansOwner(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getUidPolicy(int n) throws RemoteException {
            return 0;
        }

        @Override
        public int[] getUidsWithPolicy(int n) throws RemoteException {
            return null;
        }

        @Override
        public boolean isUidNetworkingBlocked(int n, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public void onTetheringChanged(String string2, boolean bl) throws RemoteException {
        }

        @Override
        public void registerListener(INetworkPolicyListener iNetworkPolicyListener) throws RemoteException {
        }

        @Override
        public void removeUidPolicy(int n, int n2) throws RemoteException {
        }

        @Override
        public void setDeviceIdleMode(boolean bl) throws RemoteException {
        }

        @Override
        public void setNetworkPolicies(NetworkPolicy[] arrnetworkPolicy) throws RemoteException {
        }

        @Override
        public void setRestrictBackground(boolean bl) throws RemoteException {
        }

        @Override
        public void setSubscriptionOverride(int n, int n2, int n3, long l, String string2) throws RemoteException {
        }

        @Override
        public void setSubscriptionPlans(int n, SubscriptionPlan[] arrsubscriptionPlan, String string2) throws RemoteException {
        }

        @Override
        public void setUidPolicy(int n, int n2) throws RemoteException {
        }

        @Override
        public void setWifiMeteredOverride(String string2, int n) throws RemoteException {
        }

        @Override
        public void snoozeLimit(NetworkTemplate networkTemplate) throws RemoteException {
        }

        @Override
        public void unregisterListener(INetworkPolicyListener iNetworkPolicyListener) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements INetworkPolicyManager {
        private static final String DESCRIPTOR = "android.net.INetworkPolicyManager";
        static final int TRANSACTION_addUidPolicy = 2;
        static final int TRANSACTION_factoryReset = 22;
        static final int TRANSACTION_getNetworkPolicies = 9;
        static final int TRANSACTION_getNetworkQuotaInfo = 17;
        static final int TRANSACTION_getRestrictBackground = 12;
        static final int TRANSACTION_getRestrictBackgroundByCaller = 14;
        static final int TRANSACTION_getSubscriptionPlans = 18;
        static final int TRANSACTION_getSubscriptionPlansOwner = 20;
        static final int TRANSACTION_getUidPolicy = 4;
        static final int TRANSACTION_getUidsWithPolicy = 5;
        static final int TRANSACTION_isUidNetworkingBlocked = 23;
        static final int TRANSACTION_onTetheringChanged = 13;
        static final int TRANSACTION_registerListener = 6;
        static final int TRANSACTION_removeUidPolicy = 3;
        static final int TRANSACTION_setDeviceIdleMode = 15;
        static final int TRANSACTION_setNetworkPolicies = 8;
        static final int TRANSACTION_setRestrictBackground = 11;
        static final int TRANSACTION_setSubscriptionOverride = 21;
        static final int TRANSACTION_setSubscriptionPlans = 19;
        static final int TRANSACTION_setUidPolicy = 1;
        static final int TRANSACTION_setWifiMeteredOverride = 16;
        static final int TRANSACTION_snoozeLimit = 10;
        static final int TRANSACTION_unregisterListener = 7;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INetworkPolicyManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INetworkPolicyManager) {
                return (INetworkPolicyManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INetworkPolicyManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 23: {
                    return "isUidNetworkingBlocked";
                }
                case 22: {
                    return "factoryReset";
                }
                case 21: {
                    return "setSubscriptionOverride";
                }
                case 20: {
                    return "getSubscriptionPlansOwner";
                }
                case 19: {
                    return "setSubscriptionPlans";
                }
                case 18: {
                    return "getSubscriptionPlans";
                }
                case 17: {
                    return "getNetworkQuotaInfo";
                }
                case 16: {
                    return "setWifiMeteredOverride";
                }
                case 15: {
                    return "setDeviceIdleMode";
                }
                case 14: {
                    return "getRestrictBackgroundByCaller";
                }
                case 13: {
                    return "onTetheringChanged";
                }
                case 12: {
                    return "getRestrictBackground";
                }
                case 11: {
                    return "setRestrictBackground";
                }
                case 10: {
                    return "snoozeLimit";
                }
                case 9: {
                    return "getNetworkPolicies";
                }
                case 8: {
                    return "setNetworkPolicies";
                }
                case 7: {
                    return "unregisterListener";
                }
                case 6: {
                    return "registerListener";
                }
                case 5: {
                    return "getUidsWithPolicy";
                }
                case 4: {
                    return "getUidPolicy";
                }
                case 3: {
                    return "removeUidPolicy";
                }
                case 2: {
                    return "addUidPolicy";
                }
                case 1: 
            }
            return "setUidPolicy";
        }

        public static boolean setDefaultImpl(INetworkPolicyManager iNetworkPolicyManager) {
            if (Proxy.sDefaultImpl == null && iNetworkPolicyManager != null) {
                Proxy.sDefaultImpl = iNetworkPolicyManager;
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
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        n = this.isUidNetworkingBlocked(n, bl4) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.factoryReset(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setSubscriptionOverride(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readLong(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSubscriptionPlansOwner(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setSubscriptionPlans(((Parcel)object).readInt(), ((Parcel)object).createTypedArray(SubscriptionPlan.CREATOR), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSubscriptionPlans(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? NetworkState.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getNetworkQuotaInfo((NetworkState)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkQuotaInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setWifiMeteredOverride(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl4 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.setDeviceIdleMode(bl4);
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getRestrictBackgroundByCaller();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        bl4 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.onTetheringChanged(string2, bl4);
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getRestrictBackground() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl4 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.setRestrictBackground(bl4);
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? NetworkTemplate.CREATOR.createFromParcel((Parcel)object) : null;
                        this.snoozeLimit((NetworkTemplate)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNetworkPolicies(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setNetworkPolicies(((Parcel)object).createTypedArray(NetworkPolicy.CREATOR));
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterListener(INetworkPolicyListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerListener(INetworkPolicyListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getUidsWithPolicy(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeIntArray((int[])object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getUidPolicy(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeUidPolicy(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addUidPolicy(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.setUidPolicy(((Parcel)object).readInt(), ((Parcel)object).readInt());
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements INetworkPolicyManager {
            public static INetworkPolicyManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void addUidPolicy(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addUidPolicy(n, n2);
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
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void factoryReset(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().factoryReset(string2);
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public NetworkPolicy[] getNetworkPolicies(String arrnetworkPolicy) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrnetworkPolicy);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrnetworkPolicy = Stub.getDefaultImpl().getNetworkPolicies((String)arrnetworkPolicy);
                        return arrnetworkPolicy;
                    }
                    parcel2.readException();
                    arrnetworkPolicy = parcel2.createTypedArray(NetworkPolicy.CREATOR);
                    return arrnetworkPolicy;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public NetworkQuotaInfo getNetworkQuotaInfo(NetworkState parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((NetworkState)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        NetworkQuotaInfo networkQuotaInfo = Stub.getDefaultImpl().getNetworkQuotaInfo((NetworkState)parcelable);
                        parcel2.recycle();
                        parcel.recycle();
                        return networkQuotaInfo;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        NetworkQuotaInfo networkQuotaInfo = NetworkQuotaInfo.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public boolean getRestrictBackground() throws RemoteException {
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
                    if (iBinder.transact(12, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().getRestrictBackground();
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
            public int getRestrictBackgroundByCaller() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getRestrictBackgroundByCaller();
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
            public SubscriptionPlan[] getSubscriptionPlans(int n, String arrsubscriptionPlan) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString((String)arrsubscriptionPlan);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrsubscriptionPlan = Stub.getDefaultImpl().getSubscriptionPlans(n, (String)arrsubscriptionPlan);
                        return arrsubscriptionPlan;
                    }
                    parcel2.readException();
                    arrsubscriptionPlan = parcel2.createTypedArray(SubscriptionPlan.CREATOR);
                    return arrsubscriptionPlan;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getSubscriptionPlansOwner(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getSubscriptionPlansOwner(n);
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getUidPolicy(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getUidPolicy(n);
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
            public int[] getUidsWithPolicy(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int[] arrn = Stub.getDefaultImpl().getUidsWithPolicy(n);
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
            public boolean isUidNetworkingBlocked(int n, boolean bl) throws RemoteException {
                Parcel parcel;
                boolean bl2;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        bl2 = true;
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n2);
                    if (this.mRemote.transact(23, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().isUidNetworkingBlocked(n, bl);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                bl = n != 0 ? bl2 : false;
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void onTetheringChanged(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTetheringChanged(string2, bl);
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
            public void registerListener(INetworkPolicyListener iNetworkPolicyListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNetworkPolicyListener != null ? iNetworkPolicyListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerListener(iNetworkPolicyListener);
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
            public void removeUidPolicy(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeUidPolicy(n, n2);
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
            public void setDeviceIdleMode(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDeviceIdleMode(bl);
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
            public void setNetworkPolicies(NetworkPolicy[] arrnetworkPolicy) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedArray((Parcelable[])arrnetworkPolicy, 0);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setNetworkPolicies(arrnetworkPolicy);
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
            public void setRestrictBackground(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRestrictBackground(bl);
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
            public void setSubscriptionOverride(int n, int n2, int n3, long l, String string2) throws RemoteException {
                Parcel parcel;
                void var6_12;
                Parcel parcel2;
                block14 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeInt(n3);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeString(string2);
                        if (!this.mRemote.transact(21, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().setSubscriptionOverride(n, n2, n3, l, string2);
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
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var6_12;
            }

            @Override
            public void setSubscriptionPlans(int n, SubscriptionPlan[] arrsubscriptionPlan, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeTypedArray((Parcelable[])arrsubscriptionPlan, 0);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSubscriptionPlans(n, arrsubscriptionPlan, string2);
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
            public void setUidPolicy(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUidPolicy(n, n2);
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
            public void setWifiMeteredOverride(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setWifiMeteredOverride(string2, n);
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
            public void snoozeLimit(NetworkTemplate networkTemplate) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (networkTemplate != null) {
                        parcel.writeInt(1);
                        networkTemplate.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().snoozeLimit(networkTemplate);
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
            public void unregisterListener(INetworkPolicyListener iNetworkPolicyListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNetworkPolicyListener != null ? iNetworkPolicyListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterListener(iNetworkPolicyListener);
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

