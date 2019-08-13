/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.aidl.IImsCapabilityCallback;
import android.telephony.ims.aidl.IImsMmTelListener;
import android.telephony.ims.aidl.IImsSmsListener;
import android.telephony.ims.feature.CapabilityChangeRequest;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsEcbm;
import com.android.ims.internal.IImsMultiEndpoint;
import com.android.ims.internal.IImsUt;

public interface IImsMmTelFeature
extends IInterface {
    public void acknowledgeSms(int var1, int var2, int var3) throws RemoteException;

    public void acknowledgeSmsReport(int var1, int var2, int var3) throws RemoteException;

    public void addCapabilityCallback(IImsCapabilityCallback var1) throws RemoteException;

    public void changeCapabilitiesConfiguration(CapabilityChangeRequest var1, IImsCapabilityCallback var2) throws RemoteException;

    public ImsCallProfile createCallProfile(int var1, int var2) throws RemoteException;

    public IImsCallSession createCallSession(ImsCallProfile var1) throws RemoteException;

    public IImsEcbm getEcbmInterface() throws RemoteException;

    public int getFeatureState() throws RemoteException;

    public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException;

    public String getSmsFormat() throws RemoteException;

    public IImsUt getUtInterface() throws RemoteException;

    public void onSmsReady() throws RemoteException;

    public void queryCapabilityConfiguration(int var1, int var2, IImsCapabilityCallback var3) throws RemoteException;

    public int queryCapabilityStatus() throws RemoteException;

    public void removeCapabilityCallback(IImsCapabilityCallback var1) throws RemoteException;

    public void sendSms(int var1, int var2, String var3, String var4, boolean var5, byte[] var6) throws RemoteException;

    public void setListener(IImsMmTelListener var1) throws RemoteException;

    public void setSmsListener(IImsSmsListener var1) throws RemoteException;

    public void setUiTtyMode(int var1, Message var2) throws RemoteException;

    public int shouldProcessCall(String[] var1) throws RemoteException;

    public static class Default
    implements IImsMmTelFeature {
        @Override
        public void acknowledgeSms(int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void acknowledgeSmsReport(int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void addCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void changeCapabilitiesConfiguration(CapabilityChangeRequest capabilityChangeRequest, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
        }

        @Override
        public ImsCallProfile createCallProfile(int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public IImsCallSession createCallSession(ImsCallProfile imsCallProfile) throws RemoteException {
            return null;
        }

        @Override
        public IImsEcbm getEcbmInterface() throws RemoteException {
            return null;
        }

        @Override
        public int getFeatureState() throws RemoteException {
            return 0;
        }

        @Override
        public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
            return null;
        }

        @Override
        public String getSmsFormat() throws RemoteException {
            return null;
        }

        @Override
        public IImsUt getUtInterface() throws RemoteException {
            return null;
        }

        @Override
        public void onSmsReady() throws RemoteException {
        }

        @Override
        public void queryCapabilityConfiguration(int n, int n2, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
        }

        @Override
        public int queryCapabilityStatus() throws RemoteException {
            return 0;
        }

        @Override
        public void removeCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
        }

        @Override
        public void sendSms(int n, int n2, String string2, String string3, boolean bl, byte[] arrby) throws RemoteException {
        }

        @Override
        public void setListener(IImsMmTelListener iImsMmTelListener) throws RemoteException {
        }

        @Override
        public void setSmsListener(IImsSmsListener iImsSmsListener) throws RemoteException {
        }

        @Override
        public void setUiTtyMode(int n, Message message) throws RemoteException {
        }

        @Override
        public int shouldProcessCall(String[] arrstring) throws RemoteException {
            return 0;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsMmTelFeature {
        private static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsMmTelFeature";
        static final int TRANSACTION_acknowledgeSms = 17;
        static final int TRANSACTION_acknowledgeSmsReport = 18;
        static final int TRANSACTION_addCapabilityCallback = 11;
        static final int TRANSACTION_changeCapabilitiesConfiguration = 13;
        static final int TRANSACTION_createCallProfile = 3;
        static final int TRANSACTION_createCallSession = 4;
        static final int TRANSACTION_getEcbmInterface = 7;
        static final int TRANSACTION_getFeatureState = 2;
        static final int TRANSACTION_getMultiEndpointInterface = 9;
        static final int TRANSACTION_getSmsFormat = 19;
        static final int TRANSACTION_getUtInterface = 6;
        static final int TRANSACTION_onSmsReady = 20;
        static final int TRANSACTION_queryCapabilityConfiguration = 14;
        static final int TRANSACTION_queryCapabilityStatus = 10;
        static final int TRANSACTION_removeCapabilityCallback = 12;
        static final int TRANSACTION_sendSms = 16;
        static final int TRANSACTION_setListener = 1;
        static final int TRANSACTION_setSmsListener = 15;
        static final int TRANSACTION_setUiTtyMode = 8;
        static final int TRANSACTION_shouldProcessCall = 5;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsMmTelFeature asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsMmTelFeature) {
                return (IImsMmTelFeature)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsMmTelFeature getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 20: {
                    return "onSmsReady";
                }
                case 19: {
                    return "getSmsFormat";
                }
                case 18: {
                    return "acknowledgeSmsReport";
                }
                case 17: {
                    return "acknowledgeSms";
                }
                case 16: {
                    return "sendSms";
                }
                case 15: {
                    return "setSmsListener";
                }
                case 14: {
                    return "queryCapabilityConfiguration";
                }
                case 13: {
                    return "changeCapabilitiesConfiguration";
                }
                case 12: {
                    return "removeCapabilityCallback";
                }
                case 11: {
                    return "addCapabilityCallback";
                }
                case 10: {
                    return "queryCapabilityStatus";
                }
                case 9: {
                    return "getMultiEndpointInterface";
                }
                case 8: {
                    return "setUiTtyMode";
                }
                case 7: {
                    return "getEcbmInterface";
                }
                case 6: {
                    return "getUtInterface";
                }
                case 5: {
                    return "shouldProcessCall";
                }
                case 4: {
                    return "createCallSession";
                }
                case 3: {
                    return "createCallProfile";
                }
                case 2: {
                    return "getFeatureState";
                }
                case 1: 
            }
            return "setListener";
        }

        public static boolean setDefaultImpl(IImsMmTelFeature iImsMmTelFeature) {
            if (Proxy.sDefaultImpl == null && iImsMmTelFeature != null) {
                Proxy.sDefaultImpl = iImsMmTelFeature;
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
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1598968902) {
                Object var5_5 = null;
                Object var6_6 = null;
                Object object3 = null;
                IImsCallSession iImsCallSession = null;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onSmsReady();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSmsFormat();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.acknowledgeSmsReport(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.acknowledgeSms(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readString();
                        object3 = ((Parcel)object).readString();
                        boolean bl = ((Parcel)object).readInt() != 0;
                        this.sendSms(n, n2, (String)object2, (String)object3, bl, ((Parcel)object).createByteArray());
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setSmsListener(IImsSmsListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.queryCapabilityConfiguration(((Parcel)object).readInt(), ((Parcel)object).readInt(), IImsCapabilityCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? CapabilityChangeRequest.CREATOR.createFromParcel((Parcel)object) : null;
                        this.changeCapabilitiesConfiguration((CapabilityChangeRequest)object2, IImsCapabilityCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeCapabilityCallback(IImsCapabilityCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addCapabilityCallback(IImsCapabilityCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.queryCapabilityStatus();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object3 = this.getMultiEndpointInterface();
                        ((Parcel)object2).writeNoException();
                        object = iImsCallSession;
                        if (object3 != null) {
                            object = object3.asBinder();
                        }
                        ((Parcel)object2).writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Message.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setUiTtyMode(n, (Message)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object3 = this.getEcbmInterface();
                        ((Parcel)object2).writeNoException();
                        object = var5_5;
                        if (object3 != null) {
                            object = object3.asBinder();
                        }
                        ((Parcel)object2).writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object3 = this.getUtInterface();
                        ((Parcel)object2).writeNoException();
                        object = var6_6;
                        if (object3 != null) {
                            object = object3.asBinder();
                        }
                        ((Parcel)object2).writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.shouldProcessCall(((Parcel)object).createStringArray());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        iImsCallSession = this.createCallSession((ImsCallProfile)object);
                        ((Parcel)object2).writeNoException();
                        object = object3;
                        if (iImsCallSession != null) {
                            object = iImsCallSession.asBinder();
                        }
                        ((Parcel)object2).writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.createCallProfile(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ImsCallProfile)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getFeatureState();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.setListener(IImsMmTelListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                ((Parcel)object2).writeNoException();
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IImsMmTelFeature {
            public static IImsMmTelFeature sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void acknowledgeSms(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(17, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().acknowledgeSms(n, n2, n3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void acknowledgeSmsReport(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(18, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().acknowledgeSmsReport(n, n2, n3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void addCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCapabilityCallback != null ? iImsCapabilityCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(11, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().addCapabilityCallback(iImsCapabilityCallback);
                    return;
                }
                finally {
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
            public void changeCapabilitiesConfiguration(CapabilityChangeRequest capabilityChangeRequest, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (capabilityChangeRequest != null) {
                        parcel.writeInt(1);
                        capabilityChangeRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iImsCapabilityCallback != null ? iImsCapabilityCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(13, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().changeCapabilitiesConfiguration(capabilityChangeRequest, iImsCapabilityCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public ImsCallProfile createCallProfile(int n, int n2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        if (this.mRemote.transact(3, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ImsCallProfile imsCallProfile = Stub.getDefaultImpl().createCallProfile(n, n2);
                        parcel.recycle();
                        parcel2.recycle();
                        return imsCallProfile;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ImsCallProfile imsCallProfile = parcel.readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return imsCallProfile;
            }

            @Override
            public IImsCallSession createCallSession(ImsCallProfile object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ImsCallProfile)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().createCallSession((ImsCallProfile)object);
                        return object;
                    }
                    parcel2.readException();
                    object = IImsCallSession.Stub.asInterface(parcel2.readStrongBinder());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public IImsEcbm getEcbmInterface() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IImsEcbm iImsEcbm = Stub.getDefaultImpl().getEcbmInterface();
                        return iImsEcbm;
                    }
                    parcel2.readException();
                    IImsEcbm iImsEcbm = IImsEcbm.Stub.asInterface(parcel2.readStrongBinder());
                    return iImsEcbm;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getFeatureState() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getFeatureState();
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IImsMultiEndpoint iImsMultiEndpoint = Stub.getDefaultImpl().getMultiEndpointInterface();
                        return iImsMultiEndpoint;
                    }
                    parcel2.readException();
                    IImsMultiEndpoint iImsMultiEndpoint = IImsMultiEndpoint.Stub.asInterface(parcel2.readStrongBinder());
                    return iImsMultiEndpoint;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getSmsFormat() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getSmsFormat();
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
            public IImsUt getUtInterface() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IImsUt iImsUt = Stub.getDefaultImpl().getUtInterface();
                        return iImsUt;
                    }
                    parcel2.readException();
                    IImsUt iImsUt = IImsUt.Stub.asInterface(parcel2.readStrongBinder());
                    return iImsUt;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void onSmsReady() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(20, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSmsReady();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void queryCapabilityConfiguration(int n, int n2, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iImsCapabilityCallback != null ? iImsCapabilityCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(14, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().queryCapabilityConfiguration(n, n2, iImsCapabilityCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public int queryCapabilityStatus() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().queryCapabilityStatus();
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
            public void removeCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCapabilityCallback != null ? iImsCapabilityCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(12, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().removeCapabilityCallback(iImsCapabilityCallback);
                    return;
                }
                finally {
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
            public void sendSms(int n, int n2, String string2, String string3, boolean bl, byte[] arrby) throws RemoteException {
                Parcel parcel;
                void var3_11;
                block15 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeString(string3);
                        int n3 = bl ? 1 : 0;
                        parcel.writeInt(n3);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel.writeByteArray(arrby);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        if (!this.mRemote.transact(16, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().sendSms(n, n2, string2, string3, bl, arrby);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var3_11;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setListener(IImsMmTelListener iImsMmTelListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsMmTelListener != null ? iImsMmTelListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setListener(iImsMmTelListener);
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
            public void setSmsListener(IImsSmsListener iImsSmsListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsSmsListener != null ? iImsSmsListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSmsListener(iImsSmsListener);
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
            public void setUiTtyMode(int n, Message message) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (message != null) {
                        parcel.writeInt(1);
                        message.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUiTtyMode(n, message);
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
            public int shouldProcessCall(String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().shouldProcessCall(arrstring);
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

